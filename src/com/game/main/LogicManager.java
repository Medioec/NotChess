package com.game.main;

import com.game.main.HighlightMask.MaskType;
import com.game.main.Units.UnitType;

import java.awt.event.MouseEvent;

import java.util.Random;

public class LogicManager {
    ObjectManager objectManager;
    private static int moveCount = 0;
    private static int checkedMoveCount = 0;
    private static int turnCount;
    private static int playerCount;
    private static int selectedUnitID;
    private static int selectedTerrainID;
    private static int activeUnitID;
    private static int targetUnitID;
    private static Coord newCoord;
    private static Coord oldCoord;
    private static int currentTurnPlayer;
    private static boolean validMove;
    private static turnPhase phase;
    public static final int INITIATE_ATK_BONUS = 10;
    public static final double EFFECTIVE_DAMAGE_MODIFIER = 1.4;

    public enum turnPhase {
        SELECT,
        MOVE,
        ATTACK,
        CONFIRMATION,
        GAMEOVER;
    }

    public LogicManager(ObjectManager objectManager) {
        this.objectManager = objectManager;
        moveCount = 0;
        checkedMoveCount = -1;
        turnCount = 0;
        currentTurnPlayer = 1;
        activeUnitID = -1;
        selectedUnitID = -1;
        targetUnitID = -1;
        selectedTerrainID = -1;
        validMove = false;
        phase = turnPhase.SELECT;
    }

    public int getPlayerCount(){
        return playerCount;
    }

    public void setPlayerCount(int count){
        playerCount = count;
    }

    public void updateTurnCount(){
        turnCount++;
        currentTurnPlayer = turnCount % playerCount + 1;
    }

    public int getTurnCount(){
        return turnCount;
    }

    public turnPhase getTurnPhase(){
        return phase;
    }

    public void setTurnPhase(turnPhase newPhase){
        phase = newPhase;
    }

    public int getCurrentTurnPlayer(){
        return currentTurnPlayer;
    }

    public void setPhaseSelect() {
        phase = turnPhase.SELECT;
        if(!checkMoveableUnits()){
            otherPlayerTurn();
        }
    }

    public void setPhaseMove() {
        phase = turnPhase.MOVE;
    }

    public void setPhaseAttack() {
        phase = turnPhase.ATTACK;
    }

    public void setPhaseConfirmation(){
        phase = turnPhase.CONFIRMATION;
    }

    public void setPhaseGameOver(){
        phase = turnPhase.GAMEOVER;
    }

    public boolean isPhase(turnPhase turnPhase){
        if(phase == turnPhase) return true;
        else return false;
    }

    public void setUnitActive(Units unit) {
        Units activeUnit = objectManager.getUnitByID(activeUnitID);
        if(activeUnit != null){
            HighlightMask.removeMask(activeUnit.getCoord());
            new HighlightMask(activeUnit.getCoord(), MaskType.MOVEABLEMASK);
        }
        activeUnitID = unit.id;
        HighlightMask.removeMask(unit.getCoord());
        new HighlightMask(unit.getCoord(), MaskType.ACTIVEMASK);
    }

    public void unsetUnitActive(){
        activeUnitID = -1;
    }

    public void setUnitMoveable(Units unit){
        HighlightMask.removeMask(unit.getCoord());
        new HighlightMask(unit.getCoord(), MaskType.MOVEABLEMASK);
        if (unit.id == activeUnitID){
            unit.setMoveable(true);
            activeUnitID = -1;
        }
    }

    // call functions to set units moveable, and change ui
    public void startPlayerTurn() {
        objectManager.setAllUnitsNotMoveable();
        objectManager.setUnitsMoveable(currentTurnPlayer);
        Game.unitInfoDisplay.initialize();
        setPhaseSelect();
    }

    // set change over to next player
    public void otherPlayerTurn() {
        selectedUnitID = -1;
        activeUnitID = -1;
        targetUnitID = -1;
        HighlightMask.resetAllMask();
        updateTurnCount();
        startPlayerTurn();
    }

    public void initializeGameLogic(){
        moveCount = 0;
        checkedMoveCount = -1;
        turnCount = 0;
        currentTurnPlayer = 1;
        activeUnitID = -1;
        selectedUnitID = -1;
        targetUnitID = -1;
        selectedTerrainID = -1;
        validMove = false;
        phase = turnPhase.SELECT;
    }

    public void showMoveRange(Coord coord, int mvmt) {
        int nextMvmt;
        if(mvmt > 0){
            for(int i = 0; i < 4; i++){
                int[] result = checkAdjacentValidMoveMask(i, coord);
                Coord nextCoord = new Coord(result[1], result[2]);
                if(result[0] == 1||result[0] == 0){
                    new HighlightMask(nextCoord, MaskType.MOVEMASK);
                    nextMvmt = mvmt - 1;
                    showMoveRange(nextCoord, nextMvmt);
                } else if (result[0] == 4){
                    nextMvmt = mvmt - 1;
                    showMoveRange(nextCoord, nextMvmt);
                }
            }
        }
    }
    //method to check adjacent space for valid movement,
    //takes a direction and a coordinate/int array as attributes
    //returns 1 in first array element if unobstructed, 0 if obstructed by ally and 2 if obstructed by others, 4 if existing mask in place
    //returns x coord of checked space in 2nd array element, y coord in 3rd
    public int[] checkAdjacentValidMoveMask(int i, Coord inCoord){
        int[] direction = {0, 0};
        Coord coord = new Coord(inCoord.x, inCoord.y);
        switch(i){
            case 0:
            direction[0] = 0;
            direction[1] = -1;
            break;
            case 1:
            direction[0] = 1;
            direction[1] = 0;
            break;
            case 2:
            direction[0] = 0;
            direction[1] = 1;
            break;
            case 3:
            direction[0] = -1;
            direction[1] = 0;
            break;
        }
        coord.x += direction[0];
        coord.y += direction[1];
        //check if out of map bounds
        if(coord.y < 0 || coord.y > Game.map.getSizeY() - 1||coord.x < 0 || coord.x > Game.map.getSizeX() - 1){
            int[] result = {2, coord.x, coord.y};
            return result;
        }
        //check if existing mask exists
        if(HighlightMask.getMaskMap(coord) == 1||HighlightMask.getMaskMap(coord) == 2){
            int[] result = {4, coord.x, coord.y};
            return result;
        }
        //check if terrain passable
        int terrainID = Game.map.getTerrainID(coord);
        if(terrainID < -1){
            int[] result = {2, coord.x, coord.y};
            return result;
        }
        //check if unit passable, enemies not passable
        int unitID = Game.map.getUnitID(coord);
        Units tempUnit = Game.objectManager.getUnitByID(unitID);
        Units selectedUnit = Game.objectManager.getUnitByID(LogicManager.selectedUnitID);
        if (tempUnit != null){
            if(tempUnit.getPlayer() != selectedUnit.getPlayer()){
                int[] result = {2, coord.x, coord.y};
                return result;
            } else {
                int[] result = {0, coord.x, coord.y};
                return result;
            }
        } else {
            int[] result = {1, coord.x, coord.y};
            return result;
        }
    }

    public int[] checkAdjacentValidMove(int i, Coord inCoord){
        int[] direction = {0, 0};
        Coord coord = new Coord(inCoord.x, inCoord.y);
        switch(i){
            case 0:
            direction[0] = 0;
            direction[1] = -1;
            break;
            case 1:
            direction[0] = 1;
            direction[1] = 0;
            break;
            case 2:
            direction[0] = 0;
            direction[1] = 1;
            break;
            case 3:
            direction[0] = -1;
            direction[1] = 0;
            break;
        }
        coord.x += direction[0];
        coord.y += direction[1];
        //check if out of map bounds
        if(coord.y < 0 || coord.y > Game.map.getSizeY() - 1||coord.x < 0 || coord.x > Game.map.getSizeX() - 1){
            int[] result = {2, coord.x, coord.y};
            return result;
        }
        //check if terrain passable
        int terrainID = Game.map.getTerrainID(coord);
        if(terrainID < -1){
            int[] result = {2, coord.x, coord.y};
            return result;
        }
        //check if unit passable, enemies not passable
        int unitID = Game.map.getUnitID(coord);
        Units tempUnit = Game.objectManager.getUnitByID(unitID);
        Units activeUnit = Game.objectManager.getUnitByID(LogicManager.activeUnitID);
        if (tempUnit != null){
            if(tempUnit.getPlayer() != activeUnit.getPlayer()){
                int[] result = {2, coord.x, coord.y};
                return result;
            } else {
                int[] result = {0, coord.x, coord.y};
                return result;
            }
        } else {
            int[] result = {1, coord.x, coord.y};
            return result;
        }
    }

    public boolean checkValidMove(Coord startCoord, Coord endCoord, int mvmt){
        validMove = false;
        recursiveMovementCheck(startCoord, endCoord, mvmt);
        return validMove;
    }

    public void recursiveMovementCheck(Coord startCoord, Coord endCoord, int mvmt) {
        int nextMvmt;
        if(mvmt > 0){
            for(int i = 0; i < 4; i++){
                int[] result = checkAdjacentValidMove(i, startCoord);
                Coord nextCoord = new Coord(result[1], result[2]);
                if(result[0] == 1 && nextCoord.equals(endCoord)){
                    validMove = true;
                } else if(result[0]!=2){
                    nextMvmt = mvmt - 1;
                    recursiveMovementCheck(nextCoord, endCoord, nextMvmt);
                }
            }
        }
    }

    public void showTargetInRange(Units activeUnit, int range){
        for(int i = -range; i <= range; i++){
            int tempx = i;
            int remainder = range - Math.abs(i);
            for(int j = - remainder ; j <= remainder; j++){
                int tempy = j;
                Coord targetCoord = new Coord(tempx + activeUnit.getCoord().x, tempy + activeUnit.getCoord().y);
                if (targetCoord.isValid()){
                    int tempID = Game.map.getUnitID(targetCoord);
                    if(tempID != -1){
                        Units tempUnit = Game.objectManager.getUnitByID(tempID);
                        if(tempUnit.getPlayer() != activeUnit.getPlayer()){
                            new HighlightMask(tempUnit.getCoord(), MaskType.ATTACKCONFIRMMASK);
                        }
                        else if(activeUnit.getUnitType() == UnitType.MEDIC && tempUnit != activeUnit){
                            new HighlightMask(tempUnit.getCoord(), MaskType.ATTACKCONFIRMMASK);
                        }
                    }
                }
                
            }
        }
        //System.out.println("Showing Attack Targets");
    }

    public boolean checkTargetInRange(Units activeUnit, Units selectedUnit){
        if(Math.abs((activeUnit.getCoord().x - selectedUnit.getCoord().x)) + 
            Math.abs((activeUnit.getCoord().y - selectedUnit.getCoord().y)) <= activeUnit.getRange()){
            return true;
        }
        else {
            return false;
        }
    }

    public void showSpotAttackRange(Coord coord, int range){
        for(int i = -range; i <= range; i++){
            int tempx = i;
            int remainder = range - Math.abs(i);
            for(int j = - remainder ; j <= remainder; j++){
                int tempy = j;
                Coord targetCoord = new Coord(tempx + coord.x, tempy + coord.y);
                if(targetCoord.x >= 0 && targetCoord.x < Game.map.getSizeX() && targetCoord.y >= 0 && targetCoord.y < Game.map.getSizeY()){
                    if(HighlightMask.getMaskMap(targetCoord) == 0){
                    new HighlightMask(targetCoord, MaskType.ATTACKMASK);
                    }
                }
            }
        }
        for(int i = -range; i <= range; i++){
            int tempy = i;
            int remainder = range - Math.abs(i);
            for(int j = - remainder ; j <= remainder; j++){
                int tempx = j;
                Coord targetCoord = new Coord(tempx + coord.x, tempy + coord.y);
                if(targetCoord.x >= 0 && targetCoord.x < Game.map.getSizeX() && targetCoord.y >= 0 && targetCoord.y < Game.map.getSizeY()){
                    if(HighlightMask.getMaskMap(targetCoord) == 0){
                    new HighlightMask(targetCoord, MaskType.ATTACKMASK);
                    }
                }
            }
        }
    }
    //scan whole map mask
    public void showAttackOnMovement(int range){
        for(int i = 0; i < Game.map.getSizeX(); i++){
            int prev = 0;
            for(int j = 0; j < Game.map.getSizeY(); j++){
                if(HighlightMask.getMaskMap(new Coord(i, j)) == 1){
                    if(prev == 0){
                        showSpotAttackRange(new Coord(i, j), range);
                        prev = 1;
                    }
                } else {
                    if(prev == 1){
                        showSpotAttackRange(new Coord(i, j - 1), range);
                        prev = 0;
                    }
                }
            }
        }
        for(int i = 0; i < Game.map.getSizeY(); i++){
            int prev = 0;
            for(int j = 0; j < Game.map.getSizeX(); j++){
                if(HighlightMask.getMaskMap(new Coord(j, i)) == 1){
                    if(prev == 0){
                        showSpotAttackRange(new Coord(j, i), range);
                        prev = 1;
                    }
                } else {
                    if(prev == 1){
                        showSpotAttackRange(new Coord(j - 1, i), range);
                        prev = 0;
                    }
                }
            }
        }
    }

    public void moveUnit(Units unit, Coord coord){
        newCoord = coord;
        oldCoord = unit.getCoord();
        unit.setCoord(coord);
        Game.map.setUnitData(oldCoord, -1);
        Game.map.setUnitData(newCoord, unit.id);
        HighlightMask.shiftMoveableMask(oldCoord, newCoord);
    }

    public void calculateCombatSummary(Units activeUnit, Units targetUnit){
        CombatSummary.setPreCombatData(activeUnit, targetUnit, checkTargetInRange(targetUnit, activeUnit));
    }

    public void performCombat(Units activeUnit, Units targetUnit) {
        String result1 = "";
        String result2 = "";
        double modifier1 = 1;
        double modifier2 = 1;
        if(activeUnit.getUnitType() == UnitType.ARCHER && targetUnit.getUnitType() == UnitType.SOLDIER || 
            activeUnit.getUnitType() == UnitType.KNIGHT && targetUnit.getUnitType() == UnitType.ARCHER ||
            activeUnit.getUnitType() == UnitType.SOLDIER && targetUnit.getUnitType() == UnitType.KNIGHT){
            modifier1 = EFFECTIVE_DAMAGE_MODIFIER;
        }
        if(targetUnit.getUnitType() == UnitType.ARCHER && activeUnit.getUnitType() == UnitType.SOLDIER || 
            targetUnit.getUnitType() == UnitType.KNIGHT && activeUnit.getUnitType() == UnitType.ARCHER ||
            targetUnit.getUnitType() == UnitType.SOLDIER && activeUnit.getUnitType() == UnitType.KNIGHT){
            modifier2 = EFFECTIVE_DAMAGE_MODIFIER;
        }
        if(activeUnit.getUnitType() == UnitType.MEDIC && activeUnit.getPlayer() == targetUnit.getPlayer()){
            targetUnit.setHp(targetUnit.getHp() + (activeUnit.getAtk() + LogicManager.INITIATE_ATK_BONUS) * 2);
            if(targetUnit.getHp() > targetUnit.getHp()) targetUnit.setHp(targetUnit.getMaxHp());
        }
        else {
            // add combat code
            Random r = new Random();
            int r1 = r.nextInt(99);
            int r2 = r.nextInt(99);
            boolean canCounter = checkTargetInRange(targetUnit, activeUnit);
            int damageDone = (int)( (double) (activeUnit.getAtk() + INITIATE_ATK_BONUS) * modifier1);
            int damageReceived = (int)( (double)targetUnit.getAtk() * modifier2);
            if(canCounter){
                //calc activeunit hp
                if (r1 < targetUnit.getHit() - activeUnit.getAvd()){
                    activeUnit.setHp(activeUnit.getHp() - damageReceived);
                    result1 = "Hit";
                } else {
                    result1 = "Miss";
                }
                //calc targetunit hp
                if (r2 < activeUnit.getHit() - targetUnit.getAvd()){
                    targetUnit.setHp(targetUnit.getHp() - damageDone);
                    result2 = "Hit";
                } else {
                    result2 = "Miss";
                }
                if(activeUnit.getHp() < 0) activeUnit.setHp(0);
                if(targetUnit.getHp() < 0) targetUnit.setHp(0);
            }
            else {
                result1 = "---";
                if (r2 < activeUnit.getHit() - targetUnit.getAvd()){
                    targetUnit.setHp(targetUnit.getHp() - damageDone);
                    result2 = "Hit";
                } else {
                    result2 = "Miss";
                }
                if(targetUnit.getHp() < 0) targetUnit.setHp(0);
            }
        }
        CombatSummary.setPostCombatData(activeUnit, targetUnit, result1, result2);
    }

    public void postCombatCleanup(Units active, Units target){
        HighlightMask.removeMask(active.getCoord());
        HighlightMask.resetAttackConfirmMask();
        HighlightMask.resetMoveMask();
        HighlightMask.resetAttackMask();
        if(active.getHp() <= 0) {
            Game.objectManager.unitDefeated(active);
        }
        if(target.getHp() <= 0) {
            Game.objectManager.unitDefeated(target);
            if(active.getHp() > 0){
                increaseUnitLevel(active);
            }
        }
        unsetUnitActive();
    }

    public void increaseUnitLevel(Units active){
        active.increaseUnitLevel();
    }

    public void unitDefeated(Units unit){
        int terrainID = Game.map.getTerrainID(unit.getCoord());
        Game.map.setMapData(unit.getCoord(), -1, terrainID);
        objectManager.removeUnit(unit);
    }

    public boolean checkMoveableUnits(){
        return Game.objectManager.checkMoveableUnits();
    }

    public boolean isGameOver(){
        if(objectManager.getKingCount() == 1) return true;
        else return false;
    }

    public void processMouseClick(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
        Coord coord;

        if (Game.gameState == Game.State.Menu) {
            if (MouseInput.mouseOver(mx, my, Menu.playButton)) {
                Game.setStateGame();
            }
        } else if (Game.gameState == Game.State.Game) {
            // check for click on stop button
            if (MouseInput.mouseOver(mx, my, Menu.stopButton)) {
                Game.setStateMenu();
            }
            if (MouseInput.mouseOver(mx, my, Menu.endButton) && !this.isGameOver()){
                this.otherPlayerTurn();
            }
            // get map object
            Map map = Game.map;
            if(map!=null){
                // check for click on map
                if (MouseInput.mouseOver(mx, my, map)) {
                    // translate click location to map coords
                    coord = map.convToCoord(mx, my);
                    //System.out.print(coord.x + "," + coord.y);
                    // get map data of square clicked
                    LogicManager.selectedUnitID = Game.map.getUnitID(coord);
                    LogicManager.selectedTerrainID = Game.map.getTerrainID(coord);
                    //System.out.print("Selected Unit ID = " + LogicManager.selectedUnitID);
                    //System.out.println("   Selected Terrain ID = " + LogicManager.selectedTerrainID);

                    // check if unit moveable
                    Units selectedUnit = Game.objectManager.getUnitByID(LogicManager.selectedUnitID);
                    Game.unitInfoDisplay.setDisplayStrings(selectedUnit);
                    Units activeUnit = Game.objectManager.getUnitByID(LogicManager.activeUnitID);
                    //if (unit != null) {
                    // Select phase
                    if(this.isPhase(turnPhase.SELECT)){
                        Game.combatSummary.visible = false;
                        Game.unitInfoDisplay.visible = true;
                        // if moveable, set active and show movement range
                        if(selectedUnit != null){
                            if(selectedUnit.getMoveable() == true){
                                this.setUnitActive(selectedUnit);
                                this.setPhaseMove();
                                HighlightMask.resetMoveMask();
                                HighlightMask.resetAttackMask();
                                this.showMoveRange(coord, selectedUnit.getMvmt());
                                this.showAttackOnMovement(selectedUnit.getRange());
                                //System.out.println("Selected Unit: " + LogicManager.activeUnitID + " Move Phase");
                            } else if(selectedUnit.getMoveable() == false){
                                this.unsetUnitActive();
                                this.setPhaseSelect();
                                HighlightMask.resetMoveMask();
                                HighlightMask.resetAttackMask();
                                this.showMoveRange(coord, selectedUnit.getMvmt());
                                this.showAttackOnMovement(selectedUnit.getRange());
                            }
                        }
                        else if(selectedUnit == null){
                            HighlightMask.resetMoveMask();
                            HighlightMask.resetAttackMask();
                        }
                    }
                    // Move phase
                    else if(this.isPhase(turnPhase.MOVE)){
                        if(activeUnit == null || activeUnit.getMoveable() == false){
                            activeUnit = null;
                            HighlightMask.resetMoveMask();
                            HighlightMask.resetAttackMask();
                            this.setPhaseSelect();

                        }
                        else if(selectedUnit == null){
                            //on empty space, moveunit if unit moveable by current player and destination is valid
                            if(activeUnit.getMoveable() == true){
                                coord = map.convToCoord(mx, my);
                                LogicManager.validMove = false;
                                this.checkValidMove(activeUnit.getCoord(), coord, activeUnit.getMvmt());
                                if (LogicManager.validMove) {
                                    //System.out.println("Execute Move");
                                    this.moveUnit(activeUnit, coord);
                                    HighlightMask.resetMoveMask();
                                    HighlightMask.resetAttackMask();
                                    activeUnit.setMoveable(false);
                                    this.showTargetInRange(activeUnit, activeUnit.getRange());
                                    this.setPhaseAttack();
                                }
                                else {
                                    HighlightMask.resetMoveMask();
                                    HighlightMask.resetAttackMask();
                                    this.setUnitMoveable(activeUnit);
                                    this.setPhaseSelect();
                                }
                            } 
                        }
                        //if active unit, set moveable to false and switch to attack
                        else if(selectedUnit != null){
                            if(selectedUnit == activeUnit){
                                HighlightMask.resetMoveMask();
                                HighlightMask.resetAttackMask();
                                oldCoord = activeUnit.getCoord();
                                newCoord = activeUnit.getCoord();
                                activeUnit.setMoveable(false);
                                this.setPhaseAttack();
                                this.showTargetInRange(activeUnit, activeUnit.getRange());
                            }
                            //if not active unit, show ranges
                            //if belong to player switch active unit
                            //if belong to other player, go back to select phase
                            else if(selectedUnit != activeUnit){
                                HighlightMask.resetMoveMask();
                                HighlightMask.resetAttackMask();
                                this.showMoveRange(coord, selectedUnit.getMvmt());
                                this.showAttackOnMovement(selectedUnit.getRange());
                                if(selectedUnit.getPlayer() == LogicManager.currentTurnPlayer) {
                                    if(selectedUnit.getMoveable()){
                                        this.setUnitActive(selectedUnit);
                                    }
                                    else if(!selectedUnit.getMoveable()){
                                        this.setUnitMoveable(activeUnit);
                                        this.setPhaseSelect();
                                    }
                                    
                                }
                                else {
                                    this.setUnitMoveable(activeUnit);
                                    this.setPhaseSelect();
                                }
                            }
                        }
                    }
                    // Attack phase
                    else if (this.isPhase(turnPhase.ATTACK)){
                        if(selectedUnit == null){
                            HighlightMask.resetMoveMask();
                            HighlightMask.resetAttackMask();
                            HighlightMask.resetAttackConfirmMask();
                            this.moveUnit(activeUnit, LogicManager.oldCoord);
                            this.setUnitMoveable(activeUnit);
                            this.setPhaseSelect();
                        }
                        else if(selectedUnit != null){
                            if(selectedUnit == activeUnit){
                                HighlightMask.resetMoveMask();
                                HighlightMask.resetAttackMask();
                                HighlightMask.resetAttackConfirmMask();
                                HighlightMask.removeMask(coord);
                                this.unsetUnitActive();
                                this.setPhaseSelect();
                            }
                            else if(selectedUnit.getPlayer() != LogicManager.currentTurnPlayer || activeUnit.getUnitType() == UnitType.MEDIC){
                                //check valid attack
                                if (this.checkTargetInRange(activeUnit, selectedUnit)){
                                    LogicManager.targetUnitID = LogicManager.selectedUnitID;
                                    Units targetUnit = selectedUnit;
                                    //display combat summary
                                    this.calculateCombatSummary(activeUnit, targetUnit);
                                    Game.unitInfoDisplay.toggleVisible();
                                    Game.combatSummary.toggleVisible();
                                    this.setPhaseConfirmation();
                                }
                                HighlightMask.resetMoveMask();
                                HighlightMask.resetAttackMask();
                                this.showMoveRange(coord, selectedUnit.getMvmt());
                                this.showAttackOnMovement(selectedUnit.getRange());
                            }
                            else if(selectedUnit.getPlayer() == LogicManager.currentTurnPlayer){
                                HighlightMask.resetMoveMask();
                                HighlightMask.resetAttackMask();
                                this.showMoveRange(coord, selectedUnit.getMvmt());
                                this.showAttackOnMovement(selectedUnit.getRange());
                            }
                        }
                    }
                    // Confirmation phase
                    else if (this.isPhase(turnPhase.CONFIRMATION)){
                        if(selectedUnit == null){
                            //remove combat summary
                            HighlightMask.resetMoveMask();
                            HighlightMask.resetAttackMask();
                            Game.unitInfoDisplay.toggleVisible();
                            Game.combatSummary.toggleVisible();
                            //this.setPhaseAttack();
                            HighlightMask.resetAttackConfirmMask();
                            this.moveUnit(activeUnit, LogicManager.oldCoord);
                            this.setUnitMoveable(activeUnit);
                            this.setPhaseSelect();
                        }
                        else if(selectedUnit != null){
                            if(selectedUnit == activeUnit){
                                //remove combat summary
                                Game.unitInfoDisplay.toggleVisible();
                                Game.combatSummary.toggleVisible();
                                this.setPhaseAttack();
                            }
                            else if(selectedUnit != activeUnit){
                                if(selectedUnit.getPlayer() != LogicManager.currentTurnPlayer || activeUnit.getUnitType() == UnitType.MEDIC){
                                    Units target = Game.objectManager.getUnitByID(LogicManager.targetUnitID);
                                    if(selectedUnit == target){
                                        this.performCombat(activeUnit, selectedUnit);
                                        this.postCombatCleanup(activeUnit, selectedUnit);
                                        if(this.isGameOver()){
                                            HighlightMask.resetAllMask();
                                            this.setPhaseGameOver();
                                        } else {
                                            this.setPhaseSelect();
                                        }
                                    }
                                    else {
                                        if(this.checkTargetInRange(activeUnit, selectedUnit)){
                                            LogicManager.targetUnitID = LogicManager.selectedUnitID;
                                            this.calculateCombatSummary(activeUnit, selectedUnit);
                                            HighlightMask.resetMoveMask();
                                            HighlightMask.resetAttackMask();
                                            this.showMoveRange(coord, selectedUnit.getMvmt());
                                            this.showAttackOnMovement(selectedUnit.getRange());
                                        } else {
                                            HighlightMask.resetMoveMask();
                                            HighlightMask.resetAttackMask();
                                            Game.combatSummary.toggleVisible();
                                            Game.unitInfoDisplay.toggleVisible();
                                            this.showMoveRange(coord, selectedUnit.getMvmt());
                                            this.showAttackOnMovement(selectedUnit.getRange());
                                            this.setPhaseAttack();
                                        }
                                    }
                                    
                                }
                                else if(selectedUnit.getPlayer() == LogicManager.currentTurnPlayer){
                                    HighlightMask.resetMoveMask();
                                    HighlightMask.resetAttackMask();
                                    Game.combatSummary.toggleVisible();
                                    Game.unitInfoDisplay.toggleVisible();
                                    this.showMoveRange(coord, selectedUnit.getMvmt());
                                    this.showAttackOnMovement(selectedUnit.getRange());
                                    this.setPhaseAttack();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
