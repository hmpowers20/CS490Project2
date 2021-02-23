import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JComponent {

    public static JFrame mainMenu() {
        JFrame window = new JFrame("Phase 1");   // create the window JFrame
        JPanel buttons = new JPanel();
        JButton startProgram = new JButton("Start System");
        JButton endProgram = new JButton("End Program");
        startProgram.setFont(new Font("Courier", Font.PLAIN, 30));
        endProgram.setFont(new Font("Courier", Font.PLAIN, 30));
        startProgram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Window.createWindow();
                window.dispose();

            }
        });
        endProgram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(1);
                window.dispose();

            }
        });

        window.add(startProgram,BorderLayout.PAGE_START);
        window.add(endProgram,BorderLayout.PAGE_START);
        window.setResizable(true);
        window.setLayout(new FlowLayout());
        window.setVisible(true);


        return window;
    }
}
