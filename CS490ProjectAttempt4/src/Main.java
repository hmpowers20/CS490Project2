import javax.swing.*;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {



        parseF myParser = new parseF();
        //myParser.File();
        MainView mainScreen = new MainView();
        JFrame mainWindow = MainView.mainMenu();  // create the window JFrame
        mainWindow.add(mainScreen);
        mainWindow.pack();


        System.out.println("end");

    }

}