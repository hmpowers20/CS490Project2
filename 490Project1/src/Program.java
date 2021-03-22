/*********************************************************
 CS 490 Semester Project - Phases 1 & 2
 Contributors: Aaron Wells, Haley Powers, Taylor Buchanan
 Due Date (Phase 2): 03/26/2021
 CS 490-02 -- Professor Allen
 *********************************************************/

import javax.swing.*;

/****************************************************
This class creates the window that contains the GUI.
 ****************************************************/
public class Program {

    /*********************************************************
    This method contains the main() and constructs the window.
     *********************************************************/
    public static void main(String[] args)
    {
        JFrame window = Window.createWindow();  // create the window JFrame
        window.add(new MainView());
        window.pack();
    }
}
