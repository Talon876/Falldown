package org.nolat.falldown;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

public class Player {
    public Vector2f position = new Vector2f(400, 64);
    public Color color = Config.PlayerColor;
    private final int size = 32;
    private final float speedMod = 1f;

    public Player() {

    }

    public void update(GameContainer gc, int delta) {
        if (position.x < 0) {
            position.x = 0;
        } else if (position.x > Config.Resolution.getX() - size) {
            position.x = Config.Resolution.getX() - size;
        }
        if (position.y > Config.Resolution.getY() - size) {
            position.y = Config.Resolution.getY() - size;
        }

        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_RIGHT)) {
            position.x += Config.PlayerSpeed * speedMod;
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            position.x -= Config.PlayerSpeed * speedMod;
        }

        if (checkWalls()) {
            System.out.println("Y U NO WORK");
            position.y -= Config.WallSpeed;
            if (position.y < 0) {
                position.y = 350;
            }
        } else {
            position.y += Config.Gravity;
        }
    }

    private boolean checkWalls() {
        boolean wallBelowPlayer = false;
        List<Vector2f> closeWalls = new ArrayList<Vector2f>();
        for (Platform platform : PlatformManager.platforms) {
            for (int segment : platform.walls) {
                Vector2f temp = new Vector2f(segment, platform.positionY);
                closeWalls.add(temp);
            }
        }
        if (closeWalls.size() > 0) {
            for (int i = closeWalls.size() - 1; i >= 0; i--) {
                if (closeWalls.get(i).getY() < position.y) {
                    closeWalls.remove(closeWalls.get(i));
                }
            }
        }
        if (closeWalls.size() > 0) {
            for (Vector2f wall : closeWalls) {
                if (position.y - size < wall.y && wall.y - (position.y - size) < 26) {
                    if (position.x + size > wall.x && position.x - size < wall.x + Config.WallTexture.getWidth()) {
                        wallBelowPlayer = true;
                        return wallBelowPlayer;
                    }
                }
            }
        }
        return wallBelowPlayer;
    }

    public void render(Graphics g) {
        Config.PlayerTexture.draw(position.x, position.y, size, size);
    }
}
