import javax.swing.*;
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
    final String[] colNames = {"Process Name", "Service Time"};

    public MainView()
    {
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        JButton startButton = new JButton("Start System");
        JButton pauseButton = new JButton("Pause System");
        JButton endProgram = new JButton ("Exit Program");
        JLabel timeUnit = new JLabel("1 time unit (ms) = ");
        JTextArea time = new JTextArea();

        JTextField systemReport = new JTextField("This will eventually show system report stats (finished processes, current throughput, etc.)");
        add(startButton,BorderLayout.PAGE_START);
        add(pauseButton,BorderLayout.PAGE_START);
        add(endProgram,BorderLayout.PAGE_END);
        add(timeUnit,BorderLayout.EAST);
        add(time,BorderLayout.EAST);
        add(systemReport,BorderLayout.SOUTH);
        ////////////////////////////////////////////////
        startButton.addActionListener(actionEvent -> ProcessManager.instance.setCpuPause(false));
        pauseButton.addActionListener(actionEvent -> ProcessManager.instance.setCpuPause(true));
        endProgram.addActionListener(actionEvent -> System.exit(1));


        JPanel tablePanel = new JPanel();
        setLayout(new GridBagLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Waiting Process Queue", TitledBorder.CENTER, TitledBorder.TOP));
        model = new DefaultTableModel(colNames, 0);
        JTable table = new JTable(model);
        tablePanel.add(new JScrollPane(table));
        table.setVisible(true);
        add(new JScrollPane(table));

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
        Queue<CPUProcess> processes = (Queue<CPUProcess>)event.getNewValue();
        int rows = model.getRowCount();
        for (int i = 0; i < rows; i++)
        {
            model.removeRow(0);
        }
        for (CPUProcess process : processes)
        {
            model.addRow(new String[] { process.name, Double.toString(process.getDuration()) });
        }
        model.fireTableDataChanged();
    }
}
