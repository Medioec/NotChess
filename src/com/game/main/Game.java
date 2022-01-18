package com.game.main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Color;

public class Game extends Canvas implements Runnable {

    public static final int HEIGHT = 720, WIDTH = HEIGHT / 9 * 16;
    public static final double FRAMERATE = 60.0;
    public static final double NANOS_IN_1_S = 1000000000;

    public enum STATE {
        Menu,
        Game;
    }

    public static STATE gameState = STATE.Menu;

    private Thread thread;
    private boolean running = false;

    public Game game;
    public static ObjectManager objectManager = new ObjectManager();
    public static LogicManager logicManager = new LogicManager(objectManager);
    public static Menu Menu = new Menu();
    public static MouseInput Mouse = new MouseInput();
    public static Board Board = new Board();
    public static Map map;
    public static UnitInfoDisplay unitInfoDisplay;
    public static CombatSummary combatSummary;

    public static void setStateMenu() {
        gameState = STATE.Menu;
        HighlightMask.resetMoveMask();
        Menu.removeGameMenu();
        objectManager.removeGameObjects();
        Menu.createMainMenu();
    }

    public static void setStateGame() {
        gameState = STATE.Game;
        HighlightMask.resetMoveMask();
        Board.createMap1();
        Menu.removeMainMenu();
        Menu.createGameMenu();
        logicManager.initializeGameLogic();
        logicManager.startPlayerTurn();
    }

    public Game() {

        this.addMouseListener(Mouse);

        setStateMenu();

        new Window(WIDTH, HEIGHT, "Test Game", this);

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = FRAMERATE;
        double ns = NANOS_IN_1_S / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        //int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                render();
                //frames++;
                delta--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("FPS: " + frames);
                //frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        objectManager.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        // draw BG
        g.setColor(new Color(100, 150, 255));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        // Display all game objects
        objectManager.render(g);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Game();
    }
}
