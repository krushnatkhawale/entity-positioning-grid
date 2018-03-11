package com.practice.gridpositioning;

public class App {
    public static void main(String[] args) {
        EntityPositioningGrid grid = new EntityPositioningGrid("U", "A", "B", "C", "D", "E");
        grid.print();
        grid.updateUserPosition(1); // L
        grid.updateUserPosition(3); //U
        grid.updateUserPosition(3); //U
        grid.updateUserPosition(3); //U
        grid.updateUserPosition(2); //R
        grid.updateUserPosition(2); //R
        grid.updateUserPosition(2); //R
    }
}
