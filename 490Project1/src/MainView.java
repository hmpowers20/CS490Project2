/*********************************************************
CS 490 Semester Project - Phases 1 & 2
Contributors: Aaron Wells, Haley Powers, Taylor Buchanan
Due Date (Phase 2): 03/26/2021
CS 490-02 -- Professor Allen
 *********************************************************/
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.Scanner;

/***********************************
This class creates the GUI display.
 ***********************************/
public class MainView extends JComponent implements PropertyChangeListener
{
    DefaultTableModel model;
    JTextField currentProcess;
    JTextField timeRemaining;
    JTextField currentProcess2;
    JTextField timeRemaining2;

    JTextField timeUnitInput;

    final String[] colNames = {"Process Name", "Service Time"};
    /******************************************************************************************************************
    The MainView constructor contains all of the buttons, displays, and calls the necessary methods to update the GUI.
     ******************************************************************************************************************/
    public MainView()
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JButton startButton = new JButton("Start System");
        JButton pauseButton = new JButton("Pause System");
        JButton endProgram = new JButton ("Exit Program");
        startButton.addActionListener(actionEvent -> ProcessManager.instance.setCpuPause(false));
        pauseButton.addActionListener(actionEvent -> ProcessManager.instance.setCpuPause(true));
        endProgram.addActionListener(actionEvent -> System.exit(1));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));
        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(endProgram);
        buttonPanel.setPreferredSize(new Dimension(400,50));
        mainPanel.add(buttonPanel, BorderLayout.PAGE_START);

        currentProcess = new JTextField("Executing:");
        timeRemaining = new JTextField("Time Remaining:");

        currentProcess2 = new JTextField("Executing:");
        timeRemaining2 = new JTextField("Time Remaining:");

        JPanel cpuPanel = new JPanel();
        cpuPanel.setLayout(new BoxLayout(cpuPanel, BoxLayout.PAGE_AXIS));

        JPanel cpuPanel2 = new JPanel();
        cpuPanel2.setLayout(new BoxLayout(cpuPanel2, BoxLayout.PAGE_AXIS));

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new GridLayout(2, 1));

        //CPU 1
        JLabel cpu1Label = new JLabel("CPU #1");

        cpuPanel.add(cpu1Label);
        cpuPanel.add(currentProcess);
        cpuPanel.add(timeRemaining);

        adminPanel.add(cpuPanel);

        //CPU 2
        JLabel cpu2Label = new JLabel("CPU #2");

        cpuPanel2.add(cpu2Label);
        cpuPanel2.add(currentProcess2);
        cpuPanel2.add(timeRemaining2);

        adminPanel.add(cpuPanel2);

        adminPanel.setPreferredSize(new Dimension(400, 200));
        mainPanel.add(adminPanel, BorderLayout.EAST);

        //Process Queue
        model = new DefaultTableModel(colNames, 0);
        JTable table = new JTable(model);

        JPanel queuePanel = new JPanel();
        queuePanel.setLayout(new FlowLayout());

        queuePanel.add(new JScrollPane(table));
        mainPanel.add(queuePanel, BorderLayout.CENTER);

        //Time unit field
        JLabel timeUnit = new JLabel("1 time unit (ms) = ");

        timeUnitInput = new JTextField("100");
        timeUnitInput.setPreferredSize(new Dimension(40, 20));
        timeUnitInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int timeUnit = 1;
                try {
                    timeUnit = Integer.parseInt(timeUnitInput.getText());
                }
                catch (NumberFormatException ex)
                {
                    timeUnit = 1;
                }
                if (timeUnit <= 0)
                    timeUnit = 1;
                ProcessScheduler.instance.timeUnit = timeUnit;
            }
        });

        JPanel timePanel = new JPanel();
        timePanel.setLayout(new FlowLayout());
        timePanel.add(timeUnit);
        timePanel.add(timeUnitInput);
        timePanel.setPreferredSize(new Dimension(200, 50));
        mainPanel.add(timePanel, BorderLayout.WEST);

        //This is the stats panel GUI stuff
        JTextField systemReport = new JTextField("This will eventually show system report stats (finished processes, current throughput, etc.)");

        JPanel reportPanel = new JPanel();
        reportPanel.setLayout(new GridLayout(1, 1));
        reportPanel.add(systemReport);
        reportPanel.setPreferredSize(new Dimension(20,20));
        mainPanel.add(reportPanel, BorderLayout.PAGE_END);

        add(mainPanel);
        setLayout(new FlowLayout());

        ProcessManager.instance.addPropertyChangeListener(this);
        parseFile();
    }

    /****************************************************************************************************************
    The parseFile method reads in the file and extracts the process information needed to populate the GUI displays.
     ****************************************************************************************************************/
    public void parseFile()
    {
        Scanner input;
        try {
            input = new Scanner(new File("src/test12.txt"));
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
            return;
        }

        input.useDelimiter(",|\\n");

        while (input.hasNext())
        {
            {
                int arrivalTime = Integer.parseInt(input.next().trim());
                String name = input.next();
                int length = Integer.parseInt(input.next().trim());
                int priority = Integer.parseInt(input.next().trim());

                ProcessManager.instance.addArrivingProcess(new CPUProcess(name, length, priority, arrivalTime));
            }
        }
    }

    /***********************************************************************
    The propertyChange method contains all of the logic to update the GUI.
     ***********************************************************************/
    @Override
    public void propertyChange(PropertyChangeEvent event)
    {
        String propertyName = event.getPropertyName();
        if (propertyName.equals("processes")) {
            Queue<CPUProcess> processes = (Queue<CPUProcess>) event.getNewValue();
            int rows = model.getRowCount();
            for (int i = 0; i < rows; i++) {
                model.removeRow(0);
            }
            for (CPUProcess process : processes) {
                model.addRow(new String[]{process.name, Double.toString(process.getDuration())});
            }
            model.fireTableDataChanged();
        }
        else if (propertyName.equals("cpu1Process"))
        {
            CPUProcess p = (CPUProcess)event.getNewValue();
            currentProcess.setText("Executing " + p.name);
            timeRemaining.setText("Time Remaining: " + p.getDuration());
        }
        else if(propertyName.equals("cpu2Process")){
            CPUProcess p = (CPUProcess)event.getNewValue();//*************NEEDS WORK************************
            currentProcess2.setText("Executing " + p.name);
            timeRemaining2.setText("Time Remaining: " + p.getDuration());
        }
    }
}
