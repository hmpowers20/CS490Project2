import javax.swing.*;

public class Program {

    public static void main(String[] args)
    {
        JFrame window = Window.createWindow();  // create the window JFrame
        window.add(new MainView());
        window.pack();
    }
}
