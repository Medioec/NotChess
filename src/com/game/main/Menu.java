package com.game.main;

import com.game.main.Boxes.Edges;
import com.game.main.Boxes.Fill;
import com.game.main.LogicManager.turnPhase;
import com.game.main.Text.Content;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;

class Menu {
    public static Button playButton;
    public static Button stopButton;
    public static Button endButton;
    public static Boxes infoPanel;
    public static Boxes menuBG;
    public static Text turnDisplay;
    public static Text turnCounter;
    public static Text phase;

    public void createPlayButton() {
        int x = Game.WIDTH - Game.WIDTH / 32 * 7;
        int y = Game.HEIGHT / 4;
        int width = Game.WIDTH / 32 * 6;
        int height = Game.HEIGHT / 16;

        playButton = new Button(x, y, width, height, ObjectType.MainMenu);
        playButton.setBGColor(Color.blue);
        playButton.setLabel("Play", "Calibri", 30, Color.white);
        playButton.setLabelPos(x + width / 16 * 6, y + width / 16 * 2);
    }

    public void createStopButton() {
        int x = Game.WIDTH / 32 * 29;
        int y = Game.HEIGHT / 32;
        int width = Game.WIDTH / 32 * 2;
        int height = Game.HEIGHT / 32 * 1;

        stopButton = new Button(x, y, width, height, ObjectType.GameMenu);
        stopButton.setBGColor(Color.red);
        stopButton.setLabel("Stop Game", "Calibri", 15, Color.white);
        stopButton.setLabelPos(x + width / 16, y + width / 16 * 3);
        //System.out.println("Created Stop Button");
    }

    public void createTurnEndButton(){
        int x = Game.WIDTH / 32 * 25;
        int y = Game.HEIGHT / 32;
        int width = Game.WIDTH / 32 * 2;
        int height = Game.HEIGHT / 32 * 1;

        endButton = new Button(x, y, width, height, ObjectType.GameMenu);
        endButton.setBGColor(Color.red);
        endButton.setLabel("End Turn", "Calibri", 15, Color.white);
        endButton.setLabelPos(x + width / 16 * 2, y + width / 16 * 3);
        //System.out.println("Created End Turn Button");
    }

    public void createInfoPanel(){
        int x = Game.WIDTH / 32 * 25;
        int y = Game.HEIGHT / 32 * 3;
        int width = Game.WIDTH / 32 * 6;
        int height = Game.HEIGHT / 32 * 11;

        infoPanel = new Boxes(x, y, width, height, ObjectType.GameMenu);
        infoPanel.setBGColor(Color.BLACK);
        infoPanel.setEdges(Edges.Rounded, 3, 3);
        infoPanel.setFill(Fill.Nofill);
    }

    public void createTurnDisplay(){
        int x = Game.WIDTH / 64 * 51;
        int y = Game.HEIGHT / 32 * 4;
        turnDisplay = new Text(ObjectType.GameMenu);
        turnDisplay.setPos(x, y);
        turnDisplay.content = Content.PLAYERTURN;
        turnCounter = new Text(ObjectType.GameMenu);
        x = Game.WIDTH / 64 * 58;
        turnCounter.setPos(x, y);
        turnCounter.content = Content.TURNCOUNTER;
        x = Game.WIDTH / 64 * 53;
        y = Game.HEIGHT / 32 * 5;
        phase = new Text(ObjectType.GameMenu);
        phase.content = Content.PHASE;
        phase.setPos(x, y);

    }

    public void createUnitInfoDisplay(Units unit){
        Game.unitInfoDisplay = new UnitInfoDisplay(ObjectType.GameMenu);
        //Game.unitInfoDisplay.setPos(x, y);
        Game.unitInfoDisplay.setDisplayStrings(unit);
    }

    public void createCombatSummary(){
        Game.combatSummary = new CombatSummary(ObjectType.GameMenu);

    }

    public void createTitle() {
        int x = Game.WIDTH / 32 * 5;
        int y = Game.HEIGHT / 8 * 3;
        int width = Game.WIDTH / 32 * 6;
        int height = Game.HEIGHT / 16;

        Button title = new Button(x, y, width, height, ObjectType.MainMenu);
        title.setBGColor(new Color(0, 0, 0, 0));
        title.setLabel("Not Chess", "Calibri", 80, Color.red);
        title.setLabelPos(x + width / 16 * 6, y + width / 16 * 2);
    }

    public void createMenuBG(ObjectType ObjectType) {
        menuBG = new Boxes(Game.WIDTH - Game.WIDTH / 4, 0, Game.WIDTH / 4, 720, ObjectType);
        menuBG.setBGColor(Color.white);
        menuBG.setEdges(Edges.Default);
        menuBG.setFill(Fill.Fill);
    }

    public void createGameHelp(){
        new GameHelp(ObjectType.GameObject);
    }

    public void removeMainMenu() {
        for (int i = 0; i < Game.objectManager.list.size(); i++) {
            GameObject object = Game.objectManager.list.get(i);
            if (object.type == ObjectType.MainMenu) {
                Game.objectManager.removeObject(object);
                i--;
            }
        }
    }

    public void createMainMenu() {
        createMenuBG(ObjectType.MainMenu);
        createPlayButton();
        createTitle();
        //System.out.println("Created Main Menu");
    }

    public void removeGameMenu() {
        for (int i = 0; i < Game.objectManager.list.size(); i++) {
            GameObject object = Game.objectManager.list.get(i);
            if (object.type == ObjectType.GameMenu) {
                Game.objectManager.removeObject(object);
                i--;
            }
        }
    }

    public void createGameMenu() {
        createMenuBG(ObjectType.GameMenu);
        createStopButton();
        createTurnEndButton();
        createInfoPanel();
        createTurnDisplay();
        createUnitInfoDisplay(null);
        createCombatSummary();
        createGameHelp();
        //System.out.println("Created Game Menu");
    }
}

class Text extends GameObject {

    public String textString = "";
    public Color fontColor;
    public Font font;
    public Content content;
    public String help = "Defeat the enemy King\n1.3x Damage:\nArcher -> Soldier\nSoldier -> Knight\nKnight -> Archer";

    public Text(ObjectType type){
        super(type);
        x = 0;
        y = 0;
        this.setFont("Calibri", 20, Color.BLACK);
        Game.objectManager.addObject(this);
    }

    @Override
    public void render(Graphics2D g) {
        LogicManager logicManager = Game.logicManager;
        if(content == Content.PLAYERTURN){
            textString = "Player " + logicManager.getCurrentTurnPlayer() + " turn";
        }
        else if(content == Content.TURNCOUNTER){
            textString = "Turn " + (logicManager.getTurnCount() / logicManager.getPlayerCount() + 1);
        }
        else if(content == Content.PHASE){
            if(logicManager.getTurnPhase() == turnPhase.CONFIRMATION){
                textString = "Combat Preview";
            } else if(logicManager.getTurnPhase() == turnPhase.GAMEOVER){
                textString = "Game Over";
            } else {
                textString = "Phase: " + logicManager.getTurnPhase();
            }
        } else if(content == Content.HELP) {
            textString = help;
        }
        
        g.setColor(fontColor);
        g.setFont(font);
        g.drawString(textString, x, y);
    }

    @Override
    public void tick() {

    }

    public static enum Content{
        PLAYERTURN,
        TURNCOUNTER,
        PHASE,
        UNITINFODISPLAY,
        HELP;
    }

    public void setFont(String font, int fontSize, Color fontColor) {
        this.font = new Font(font, Font.BOLD, fontSize);
        this.fontColor = fontColor;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setText(String string){
        textString = string;
    }
    

}
