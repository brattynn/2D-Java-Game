package com.pd;

import java.awt.Canvas;
import java.awt.Dimension;
import java.io.Serial;

import javax.swing.JFrame;

//Purpose for this class: To create the game window

public class Window extends Canvas{

    @Serial
    private static final long serialVersionUID = -240840600533728354L;

    public Window(int width, int height, String title, Game game) {
        JFrame frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//Stops the game
        frame.setResizable(false);	//Allows no resizing of the game window
        frame.setLocationRelativeTo(null); //Sets the game to the center of the screen
        frame.add(game);	//Adds the game class into the frame
        frame.setVisible(true);
        game.start();
    }


}
