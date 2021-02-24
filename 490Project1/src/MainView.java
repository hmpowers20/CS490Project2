import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.Scanner;

public class MainView extends JComponent implements PropertyChangeListener
{
    DefaultTableModel model;
    JTextField currentProcess;
    JTextField timeRemaining;

    final String[] colNames = {"Process Name", "Service Time"};

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

        JPanel cpuPanel = new JPanel();
        cpuPanel.setLayout(new GridLayout(2, 1));

        model = new DefaultTableModel(colNames, 0);
        JTable table = new JTable(model);

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new GridLayout(2, 1));

        cpuPanel.add(currentProcess);
        cpuPanel.add(timeRemaining);
        adminPanel.add(new JScrollPane(table));
        adminPanel.add(cpuPanel);
        adminPanel.setPreferredSize(new Dimension(400, 200));
        mainPanel.add(adminPanel, BorderLayout.CENTER);

        JLabel timeUnit = new JLabel("1 time unit (ms) = ");
        JTextArea time = new JTextArea();

        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridLayout(2,1));
        timePanel.add(timeUnit);
        timePanel.add(time);
        timePanel.setPreferredSize(new Dimension(200, 50));
        mainPanel.add(timePanel, BorderLayout.WEST);

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

                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                ProcessManager.instance.addProcess(new CPUProcess(name, length, priority));
                            }
                        },
                        arrivalTime * 1000
                );
            }
        }
    }

    public void propertyChange(PropertyChangeEvent event)
    {
        String propertyName = event.getPropertyName();
        if (propertyName == "processes") {
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
        else if (propertyName == "cpu1Process")
        {
            CPUProcess p = (CPUProcess)event.getNewValue();
            currentProcess.setText("Executing " + p.name);
            timeRemaining.setText("Time Remaining: " + p.getDuration());
        }
    }
}
