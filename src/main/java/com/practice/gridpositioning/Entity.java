package com.practice.gridpositioning;


import java.awt.*;

public class Entity {
    char name;
    Point position;

    public Entity(char name, Point position) {
        this.name = name;
        this.position = position;
    }

    public char getName() {
        return name;
    }

    public void setName(char name) {
        this.name = name;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
