/*********************************************************
 CS 490 Semester Project - Phase 3
 Contributors: Aaron Wells, Haley Powers, Taylor Buchanan
 Due Date (Phase 3): 04/19/2021
 CS 490-02 -- Professor Allen
 *********************************************************/

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Queue;
import java.util.Scanner;

/***********************************
 This class creates the GUI display.
 ***********************************/
public class MainView extends JComponent implements PropertyChangeListener
{
    DefaultTableModel processQueue1;
    DefaultTableModel processQueue2;
    JTextField currentProcess;
    JTextField timeRemaining;
    JTextField currentProcess2;
    JTextField timeRemaining2;
    JLabel systemReport1;
    JLabel systemReport2;

    JTextField timeUnitInput;
    JTextField timeQuantumInput;
    String[] statsHeader = {"Process Name","Arrival Time","Service Time","Finish Time","TAT","nTAT"};
    DefaultTableModel reportTable1;
    DefaultTableModel reportTable2;

    final String[] colNames = {"Process Name", "Service Time"};

    private static DecimalFormat df = new DecimalFormat("0.00");
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
        startButton.addActionListener(actionEvent -> ProcessScheduler.instance.setCpuPause(false));
        pauseButton.addActionListener(actionEvent -> ProcessScheduler.instance.setCpuPause(true));
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

        adminPanel.setPreferredSize(new Dimension(300, 200));
        mainPanel.add(adminPanel, BorderLayout.EAST);

        //Process Queue
        JPanel queuePanel = new JPanel();
        queuePanel.setLayout(new FlowLayout());

        JPanel processTable1 = new JPanel();
        JPanel processTable2 = new JPanel();
        processTable1.setLayout(new BoxLayout(processTable1, BoxLayout.PAGE_AXIS));
        processTable2.setLayout(new BoxLayout(processTable2, BoxLayout.PAGE_AXIS));

        JLabel processHeader1 = new JLabel("Process Queue #1:");
        JLabel processHeader2 = new JLabel("Process Queue #2:");

        processQueue1 = new DefaultTableModel(colNames, 0);
        JScrollPane table1 = new JScrollPane(new JTable(processQueue1));
        table1.setPreferredSize(new Dimension(300, 300));

        processQueue2 = new DefaultTableModel(colNames, 0);
        JScrollPane table2 = new JScrollPane(new JTable(processQueue2));
        table2.setPreferredSize(new Dimension(300, 300));

        processTable1.add(processHeader1);
        processTable1.add(table1);
        processTable2.add(processHeader2);
        processTable2.add(table2);
        queuePanel.add(processTable1);
        queuePanel.add(processTable2);
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
                int timeUnit;
                try {
                    timeUnit = Integer.parseInt(timeUnitInput.getText());
                }
                catch (NumberFormatException ex)
                {
                    timeUnit = 1;
                }
                if (timeUnit <= 0)
                    timeUnit = 1;
                ProcessScheduler.timeUnit = timeUnit;
            }
        });

        JLabel timeQuantum = new JLabel("1 time quantum (time units) = ");

        timeQuantumInput = new JTextField("10");
        timeQuantumInput.setPreferredSize(new Dimension(40, 20));
        timeQuantumInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int timeQuantum;
                try {
                    timeQuantum = Integer.parseInt(timeQuantumInput.getText());
                }
                catch (NumberFormatException ex)
                {
                    timeQuantum = 1000;
                }
                if (timeQuantum <= 0)
                    timeQuantum = 1000;
                RRProcessManager.timeQuantum = timeQuantum;
            }
        });

        systemReport1 = new JLabel("CPU 1 nTAT: 0.00");
        systemReport1.setPreferredSize(new Dimension(150, 20));
        systemReport2 = new JLabel("CPU 2 nTAT: 0.00");
        systemReport2.setPreferredSize(new Dimension(150, 20));

        JPanel timePanel = new JPanel();
        timePanel.setLayout(new FlowLayout());
        timePanel.add(timeUnit);
        timePanel.add(timeUnitInput);
        timePanel.add(timeQuantum);
        timePanel.add(timeQuantumInput);
        timePanel.add(systemReport1);
        timePanel.add(systemReport2);
        timePanel.setPreferredSize(new Dimension(250, 50));
        mainPanel.add(timePanel, BorderLayout.WEST);

        //This is the stats panel GUI stuff
        JPanel reportPanel = new JPanel();
        JPanel statsPanel1 = new JPanel();
        JPanel statsPanel2 = new JPanel();
        statsPanel1.setLayout(new BoxLayout(statsPanel1, BoxLayout.PAGE_AXIS));
        statsPanel2.setLayout(new BoxLayout(statsPanel2, BoxLayout.PAGE_AXIS));

        JLabel statsHeader1 = new JLabel("Report Table for CPU #1");
        JLabel statsHeader2 = new JLabel("Report Table for CPU #2");

        reportTable1 = new DefaultTableModel(statsHeader, 0);
        JScrollPane statsTable1 = new JScrollPane(new JTable(reportTable1));
        statsTable1.setPreferredSize(new Dimension(300, 300));

        reportTable2 = new DefaultTableModel(statsHeader, 0);
        JScrollPane statsTable2 = new JScrollPane(new JTable(reportTable2));
        statsTable2.setPreferredSize(new Dimension(300, 300));

        statsPanel1.add(statsHeader1);
        statsPanel1.add(statsTable1);
        statsPanel2.add(statsHeader2);
        statsPanel2.add(statsTable2);

        reportPanel.add(statsPanel1);
        reportPanel.add(statsPanel2);

        reportPanel.setLayout(new FlowLayout());
        mainPanel.add(reportPanel, BorderLayout.PAGE_END);

        add(mainPanel);
        setLayout(new FlowLayout());

        ProcessScheduler.instance.addPropertyChangeListener(this);
        parseFile();
    }

    /****************************************************************************************************************
     The parseFile method reads in the file and extracts the process information needed to populate the GUI displays.
     ****************************************************************************************************************/
    public void parseFile()
    {
        Scanner input;
        try {
            input = new Scanner(new File("test12.txt"));
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

                ProcessScheduler.instance.addArrivingProcess(new CPUProcess(name, length, priority, arrivalTime));
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
        if (propertyName.startsWith("processes")) {
            Queue<CPUProcess> processes = (Queue<CPUProcess>)event.getNewValue();
            if (Integer.parseInt(propertyName.substring(9, 10)) == 0)
            {
                int rows = processQueue1.getRowCount();
                for (int i = 0; i < rows; i++) {
                    processQueue1.removeRow(0);
                }
                for (CPUProcess process : processes) {
                    processQueue1.addRow(new String[]{process.name, Double.toString(process.getRemainingDuration())});
                }
                processQueue1.fireTableDataChanged();
            }
            else
            {
                int rows = processQueue2.getRowCount();
                for (int i = 0; i < rows; i++) {
                    processQueue2.removeRow(0);
                }
                for (CPUProcess process : processes) {
                    processQueue2.addRow(new String[]{process.name, Double.toString(process.getRemainingDuration())});
                }
                processQueue2.fireTableDataChanged();
            }
        }
        else if (propertyName.startsWith("finishedProcesses")) {
            java.util.List<CPUProcess> processes = (java.util.List<CPUProcess>) event.getNewValue();
            if (Integer.parseInt(propertyName.substring(17, 18)) == 0) {
                int rows = reportTable1.getRowCount();
                for (int i = 0; i < rows; i++) {
                    reportTable1.removeRow(0);
                }
                for (CPUProcess process : processes) {
                    reportTable1.addRow(new String[]{
                            process.name,
                            Double.toString(process.getEntryTime()),
                            Double.toString(process.getDuration()),
                            Double.toString(process.getFinishTime()),
                            Double.toString(process.getTAT()),
                            Double.toString(process.getnTAT())});
                }
                reportTable1.fireTableDataChanged();
            }
            else
            {
                int rows = reportTable2.getRowCount();
                for (int i = 0; i < rows; i++) {
                    reportTable2.removeRow(0);
                }
                for (CPUProcess process : processes) {
                    reportTable2.addRow(new String[]{
                            process.name,
                            Double.toString(process.getEntryTime()),
                            Double.toString(process.getDuration()),
                            Double.toString(process.getFinishTime()),
                            Double.toString(process.getTAT()),
                            Double.toString(process.getnTAT())});
                }
                reportTable2.fireTableDataChanged();
            }
        }
        else if (propertyName.startsWith("averageNTAT"))
        {
            double value = (double)event.getNewValue();
            if (Integer.parseInt(propertyName.substring(11, 12)) == 0)
            {
                systemReport1.setText("CPU 1 nTAT: " + df.format(value));
            }
            else
            {
                systemReport2.setText("CPU 2 nTAT: " + df.format(value));
            }
        }
        else if (propertyName.equals("cpu1Process"))
        {
            CPUProcess p = (CPUProcess)event.getNewValue();
            currentProcess.setText("Executing " + p.name);
            timeRemaining.setText("Time Remaining: " + p.getRemainingDuration());
            
        }
        else if(propertyName.equals("cpu2Process")){
            CPUProcess p = (CPUProcess)event.getNewValue();
            currentProcess2.setText("Executing " + p.name);
            timeRemaining2.setText("Time Remaining: " + p.getRemainingDuration());
        }
    }
}