/*********************************************************
 CS 490 Semester Project - Phases 1 & 2
 Contributors: Aaron Wells, Haley Powers, Taylor Buchanan
 Due Date (Phase 2): 03/26/2021
 CS 490-02 -- Professor Allen
 *********************************************************/

import javax.swing.*;
import java.awt.*;

public class Window {
    /**
     * Creates a window with desired attributes.
     * @return JFrame representing our window.
     */
    public static JFrame createWindow() {
        JFrame window = new JFrame("Phase 2");   // create the window JFrame
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // ensure that the window closes completely when exited

        window.setResizable(false);     // Resizable is set to false so the user is prevented from changing the size of the JFrame.
        window.setLayout(new FlowLayout());
        window.setVisible(true);
        return window;
    }
}
