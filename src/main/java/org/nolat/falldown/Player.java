package org.nolat.falldown;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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
    private int score = 0;

    private static final Logger log = Logger.getLogger(Player.class);

    public Player() {

    }

    public void update(GameContainer gc, int delta) {
        if (position.x - size / 2 < 0) {
            position.x = size / 2;
        } else if (position.x > Config.Resolution.getX() - size / 2) {
            position.x = Config.Resolution.getX() - size / 2;
        }
        if (position.y > Config.Resolution.getY() - size / 2) {
            position.y = Config.Resolution.getY() - size / 2;
        }

        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_RIGHT)) {
            position.x += Config.PlayerSpeed * speedMod;
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            position.x -= Config.PlayerSpeed * speedMod;
        }

        if (checkWalls()) {
            position.y -= Config.WallSpeed;
            if (position.y < 0) {
                position.y = 350;
                // TODO Save high scores
                setScore(0);
            }
        } else {
            position.y += Config.Gravity;
        }
    }

    private boolean checkWalls() {
        boolean wallBelowPlayer = false;
        List<Vector2f> closeWalls = new ArrayList<Vector2f>();

        // put all closeby wall segments in a list
        for (Platform platform : PlatformManager.platforms) {
            for (int segment : platform.walls) {
                Vector2f temp = new Vector2f(segment, platform.positionY);
                closeWalls.add(temp);
            }
        }

        // remove walls above player
        if (closeWalls.size() > 0) {
            for (int i = closeWalls.size() - 1; i >= 0; i--) {
                if (closeWalls.get(i).getY() < position.y) {
                    closeWalls.remove(closeWalls.get(i));
                }
            }
        }

        // determine if there is a wall right below the player
        if (closeWalls.size() > 0) {
            for (Vector2f wall : closeWalls) {
                // is the player above the wall?
                if ((position.y - size / 2) < wall.y && (wall.y - (position.y - size / 2) < 26)) {
                    if (position.x + size / 2 > wall.x
                            && position.x - size / 2 < wall.x + Config.WallTexture.getWidth()) {
                        wallBelowPlayer = true;
                        return wallBelowPlayer;
                    }
                }
            }
        }
        return wallBelowPlayer;
    }

    public void render(Graphics g) {
        Config.PlayerTexture.draw(position.x - size / 2, position.y - size / 2, size, size);
        g.drawString("Score: " + score, 20, 20);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
