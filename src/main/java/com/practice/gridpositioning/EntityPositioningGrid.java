package com.practice.gridpositioning;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.generate;

public class EntityPositioningGrid {

    public static final String PIPE = "|";
    private static final String NEWLINE = "\n";
    public static final char BAR = '-';
    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private static final int UP = 3;
    private static final int DOWN = 4;
    private static final int BACKWARD = -1;
    private static final int FORWARD = 1;
    private static final int MAP_SIZE_MARGIN = 2;
    public static final String CAN_T_MOVE_MESSAGE = "You can't go to %s, face your enimy!";

    private List<String> entityTitles;
    private final int gridSize;
    private char[][] grid;
    private List<Entity> entities;

    EntityPositioningGrid(String... entities) {
        this.entityTitles = Stream.of(entities).collect(toList());
        this.gridSize = entities.length;
        this.grid = new char[gridSize][gridSize];
        initGrid();
    }

    private void initGrid() {
        Random random = new Random();
        entities = entityTitles.stream()
                .map(entity -> getPosition(entity, random))
                .collect(toList());
        entities.forEach(this::updatePosition);
    }

    private void updatePosition(Entity entity) {
        grid[(int) entity.getPosition().getX()][(int) entity.getPosition().getY()] = entity.getName();
    }


    private Entity getPosition(String entity, Random random) {
        Point position = getRandomPoint(random);
        return new Entity(entity.toUpperCase().charAt(0), position);
    }


    private Point getRandomPoint(Random random) {
        Point point = new Point(random.nextInt(gridSize), random.nextInt(gridSize));
        if (positionTaken(point)) {
            return getRandomPoint(random);
        }
        return point;
    }

    private boolean positionTaken(Point point) {
        return grid[(int) point.getX()][(int) point.getY()] != '\u0000';
    }

    public void print() {
        printHLine();
        for (int i = 0; i < gridSize; i++) {
            System.out.print(PIPE);
            for (int j = 0; j < gridSize; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.print(PIPE + NEWLINE);
        }
        printHLine();
    }

    private void printHLine() {
        System.out.println(
                generate(() -> "-").limit(entities.size() + MAP_SIZE_MARGIN).collect(joining())
        );
    }

    public void updateUserPosition(int direction) {
        entities.stream()
                .filter(entity -> entity.getName() == 'U')
                .findFirst()
                .ifPresent(entity -> move(entity, direction));
        print();
    }

    private void move(Entity userEntity, int direction) {

        switch (direction) {
            case LEFT:
                moveLeft(userEntity); break;
            case RIGHT:
                moveRight(userEntity); break;
            case UP:
                moveUp(userEntity); break;
            case DOWN:
                moveDown(userEntity); break;
        }
    }
    private void moveLeft(Entity userEntity) {
        if (canMoveLeft(userEntity)) {
            System.out.println("Moving left from " + userEntity.getPosition());
            moveHorizontally(userEntity, BACKWARD);
        } else {
            System.out.println(String.format(CAN_T_MOVE_MESSAGE, "LEFT"));
        }
    }


    private void moveRight(Entity userEntity) {
        if (canMoveRight(userEntity)) {
            System.out.println("Moving right from " + userEntity.getPosition());
            moveHorizontally(userEntity, FORWARD);
        } else {
            System.out.println(String.format(CAN_T_MOVE_MESSAGE, "RIGHT"));
        }
    }

    private void moveUp(Entity userEntity) {
        if (canMoveUp(userEntity)) {
            System.out.println("Moving up from " + userEntity.getPosition());
            moveVertically(userEntity, BACKWARD);
        } else {
            System.out.println(String.format(CAN_T_MOVE_MESSAGE, "UP"));
        }
    }

    private void moveDown(Entity userEntity) {
        if (canMoveDown(userEntity)) {
            System.out.println("Moving down from " + userEntity.getPosition());
            moveVertically(userEntity, FORWARD);
        } else {
            System.out.println(String.format(CAN_T_MOVE_MESSAGE, "DOWN"));
        }
    }

    private boolean canMoveLeft(Entity userEntity) {
        return ((int) userEntity.getPosition().getY()) > 0;
    }

    private boolean canMoveRight(Entity userEntity) {
        return ((int) userEntity.getPosition().getY()) < gridSize;
    }

    private boolean canMoveUp(Entity userEntity) {
        return ((int) userEntity.getPosition().getX()) > 0;
    }

    private boolean canMoveDown(Entity userEntity) {
        return ((int) userEntity.getPosition().getX()) < gridSize;
    }

    private void moveHorizontally(Entity userEntity, int distance) {
        grid[(int) userEntity.getPosition().getX()][(int) userEntity.getPosition().getY()] = '\u0000';
        userEntity.setPosition(new Point((int) userEntity.getPosition().getX(), (int) userEntity.getPosition().getY() + distance));
        grid[(int) userEntity.getPosition().getX()][(int) userEntity.getPosition().getY()] = userEntity.getName();
        System.out.printf("Moved to (%s, %s)\n", userEntity.getPosition().getX(), userEntity.getPosition().getY());
    }

    private void moveVertically(Entity userEntity, int distance) {
        grid[(int) userEntity.getPosition().getX()][(int) userEntity.getPosition().getY()] = '\u0000';
        userEntity.setPosition(new Point((int) userEntity.getPosition().getX() + distance, (int) userEntity.getPosition().getY()));
        grid[(int) userEntity.getPosition().getX()][(int) userEntity.getPosition().getY()] = userEntity.getName();
        System.out.printf("Moved to (%s, %s)\n", userEntity.getPosition().getX(), userEntity.getPosition().getY());
    }
}