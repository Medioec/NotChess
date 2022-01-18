package com.game.main;

public class Board {
    public void createMap1() {
        double rightBound = Game.WIDTH / 4.0 * 3.0;
        double bottomBound = Game.HEIGHT;
        double midx = rightBound / 2.0;
        double midy = bottomBound / 2.0;
        int sizex = 15;
        int sizey = 10;
        double mapmidx = (sizex * Map.SQUARELENGTH) / 2.0;
        double mapmidy = (sizey * Map.SQUARELENGTH) / 2.0;
        int x = (int) (midx - mapmidx);
        int y = (int) (midy - mapmidy);
        Map map = new Map(x, y, sizex, sizey, ObjectType.GameObject);
        Game.map = map;
        LogicManager.playerCount = 2;
        Map.mapData = new int[15][10][2];
        // initialize mapData with -1 in each space
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 10; j++) {
                Map.mapData[i][j][0] = -1;
                Map.mapData[i][j][1] = -1;
            }
        }

        int[][] p1Soldier = { { 1, 1 }, { 1, 2 }, { 1, 3 }, { 1, 4 }, { 1, 5 }, { 1, 6 }, { 1, 7 }, { 1, 8 } };

        for (int[] i : p1Soldier) {

            Soldier soldier = new Soldier(i[0], i[1], ObjectType.GameObject, 1);
            Map.mapData[i[0]][i[1]][0] = soldier.getId();
        }

        int[][] p1Knight = { { 1, 0 }, { 1, 9 }, { 0, 0 }, { 0, 9 } };
        for (int[] i : p1Knight) {
            Knight knight = new Knight(i[0], i[1], ObjectType.GameObject, 1);
            Map.mapData[i[0]][i[1]][0] = knight.getId();
        }

        int[][] p1Archer = { { 0, 1 }, { 0, 2 }, { 0, 7 }, { 0, 8 } };
        for (int[] i : p1Archer) {
            Archer archer = new Archer(i[0], i[1], ObjectType.GameObject, 1);
            Map.mapData[i[0]][i[1]][0] = archer.getId();
        }

        int[][] p1Medic = { { 0, 3 }, { 0, 4 }, { 0, 6 } };
        for (int[] i : p1Medic) {
            Medic medic = new Medic(i[0], i[1], ObjectType.GameObject, 1);
            Map.mapData[i[0]][i[1]][0] = medic.getId();
        }

        King p1king = new King(0, 5, ObjectType.GameObject, 1);
        Map.mapData[0][5][0] = p1king.getId();

        int[][] p2Soldier = { { 13, 1 }, { 13, 2 }, { 13, 3 }, { 13, 4 }, { 13, 5 }, { 13, 6 }, { 13, 7 }, { 13, 8 } };

        for (int[] i : p2Soldier) {

            Soldier soldier = new Soldier(i[0], i[1], ObjectType.GameObject, 2);
            Map.mapData[i[0]][i[1]][0] = soldier.getId();
        }

        int[][] p2Knight = { { 13, 0 }, { 13, 9 }, { 14, 0 }, { 14, 9 } };
        for (int[] i : p2Knight) {
            Knight knight = new Knight(i[0], i[1], ObjectType.GameObject, 2);
            Map.mapData[i[0]][i[1]][0] = knight.getId();
        }

        int[][] p2Archer = { { 14, 1 }, { 14, 2 }, { 14, 7 }, { 14, 8 } };
        for (int[] i : p2Archer) {
            Archer archer = new Archer(i[0], i[1], ObjectType.GameObject, 2);
            Map.mapData[i[0]][i[1]][0] = archer.getId();
        }

        int[][] p2Medic = { { 14, 3 }, { 14, 5 }, { 14, 6 } };
        for (int[] i : p2Medic) {
            Medic medic = new Medic(i[0], i[1], ObjectType.GameObject, 2);
            Map.mapData[i[0]][i[1]][0] = medic.getId();
        }

        King p2king = new King(14, 4, ObjectType.GameObject, 2);
        Map.mapData[14][4][0] = p2king.getId();

    }

}
