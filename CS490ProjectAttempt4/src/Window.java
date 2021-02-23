import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

public class Window extends parseF {
    /**
     * Creates a window with desired attributes.
     * @return JFrame representing our window.
     */
    public static JFrame createWindow() {
        JFrame window = new JFrame("Phase 1");   // create the window JFrame

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        JButton startButton = new JButton("Start System");
        JButton pauseButton = new JButton("Pause System");
        JButton endProgram = new JButton ("Exit Program");
        JLabel timeUnit = new JLabel("1 time unit (ms) = ");
        JTextArea time = new JTextArea();

        JTextField systemReport = new JTextField("This will eventually show system report stats (finished processes, current throughput, etc.)");
        window.add(startButton,BorderLayout.PAGE_START);
        window.add(pauseButton,BorderLayout.PAGE_START);
        window.add(endProgram,BorderLayout.PAGE_END);
        window.add(timeUnit,BorderLayout.EAST);
        window.add(time,BorderLayout.EAST);
        window.add(systemReport,BorderLayout.SOUTH);
        ////////////////////////////////////////////////
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    parseF.File();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        endProgram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(1);
                window.dispose();
            }
        });


        JPanel tablePanel = new JPanel();
        window.setLayout(new GridBagLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Waiting Process Queue", TitledBorder.CENTER, TitledBorder.TOP));
        String[] colNames = {"Process Name", "Service Time"};
        String[][] data = {
                {"Process A","10"},
                {"Process B","14"},
                {"Process C","4"},
        };
        JTable table = new JTable(data,colNames);
        tablePanel.add(new JScrollPane(table));
        window.add(tablePanel);
        window.setSize(550,400);
        window.setResizable(true);
        window.setLayout(new FlowLayout());
        window.setVisible(true);
        table.setVisible(true);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // ensure that the window closes completely when exited
        return window;

    }
}