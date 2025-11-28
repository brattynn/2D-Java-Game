package com.pd;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.Serial;

//Main class
public class Game extends Canvas implements Runnable {

    @Serial
    private static final long serialVersionUID = 1550691097823471818L;

    //Create the Aspect Ratio
    public static final int WIDTH = 640;
    public static final int HEIGHT =  WIDTH / 12 * 9;

    private Thread thread;
    private boolean running = false;

    public Game() {
        new Window(WIDTH, HEIGHT, "Game Build", this);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    //The Game Loop
    public void run() {
        long lastTime = System.nanoTime();			//Timestamp of the last loop iteration
        double amountOfTicks = 60.0;				//Targets 60 game logic updates per second
        double ns = 1000000000 / amountOfTicks;		//How many nanoseconds should pass between ticks
        double delta = 0;							//Accumulates time to know when to tick
        long timer = System.currentTimeMillis();	//Used to count one-second intervals
        int frames = 0;								//Counts how many frames were rendered this second

        //This part checks how much real time has passed since the last frame and adds it into delta.
        //If enough time has accumulated to equal 1 tick’s worth, then it runs tick().
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            //This does exactly 60 ticks per second because a tick only runs when enough time has passed.
            //If the game lags, delta becomes >1, so it runs multiple tick() calls to catch up.
            while(delta >= 1) {
                tick();
                delta --;
            }

            //This does exactly 60 ticks per second because a tick only runs when enough time has passed.
            //If the game lags, delta becomes >1, so it runs multiple tick() calls to catch up.
            if(running) {
                render();
            }
            frames++;

            //Every 1 second, prints how many frames were rendered.
            //Does not affect the game loop — just for debugging.
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {

    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.green);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {

        new Game();
    }
}