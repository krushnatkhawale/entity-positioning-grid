package com.practice.gridpositioning;

public class App {
    public static void main(String[] args) {
        EntityPositioningGrid grid = new EntityPositioningGrid("U", "A", "B", "C", "D", "E");
        grid.print();
        grid.updateUserPosition(1);
        grid.updateUserPosition(3);
    }
}
