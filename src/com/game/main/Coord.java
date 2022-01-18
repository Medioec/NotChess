package com.game.main;

public class Coord {
    int x;
    int y;
    public Coord(int x, int y){
        this.x = x;
        this.y = y;
    }
    public boolean equals(Coord coord){
        return x == coord.x && y == coord.y;
    }

    public boolean isValid(){
        if(x >= 0 && x < Game.map.sizex && y >= 0 && y < Game.map.sizey){
            return true;
        }
        return false;
    }
}
