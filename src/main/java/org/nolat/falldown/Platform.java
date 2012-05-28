package org.nolat.falldown;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.nolat.tools.Toolkit;

public class Platform {
    public List<Integer> walls = new ArrayList<Integer>();
    public float positionY = 390;

    public Color colorA = Color.white;
    public Color colorB = Color.white;
    public Color color = Color.white;

    private static final Logger log = Logger.getLogger(Platform.class);

    public Platform() {
        setupWalls();
    }

    public Platform(int positionY) {
        this.positionY = positionY;
        setupWalls();
    }

    private void setupWalls() {
        colorA = randomColor();
        colorB = inverseColor(colorA);
        int tries = 0;
        while (walls.size() < 1 && tries < 10) {

            walls.clear();

            for (int i = 0; i < Config.Resolution.getX(); i += Config.WallTexture.getWidth()) {
                int choice = Toolkit.randomRange(0, Config.Difficulty);
                if (choice != 0) {
                    walls.add(i);
                }
            }
            tries++;
        }
        if (walls.size() >= Config.Resolution.getX() / Config.WallTexture.getWidth() - 2) {
            walls.remove(Toolkit.randomRange(0, walls.size() - 1));
        }
    }

    private Color randomColor() {
        int r = Toolkit.randomRange(0, 255);
        int g = Toolkit.randomRange(0, 255);
        int b = Toolkit.randomRange(0, 255);
        return new Color(r, g, b);
    }

    private Color inverseColor(Color color) {
        Color inverse = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
        return inverse;
    }

    public void update(GameContainer gc, int delta) {
        positionY -= Config.WallSpeed;
    }

    public void render(GameContainer gc, Graphics g) {
        for (int wallPosition : walls) {
            Config.WallTexture.draw(wallPosition, positionY, color);
        }
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
