package org.nolat.falldown;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.nolat.tools.Toolkit;

public class PlatformManager {
    static CopyOnWriteArrayList<Platform> platforms = new CopyOnWriteArrayList<Platform>();

    public static List<Platform> getPlatforms() {
        return platforms;
    }

    public static void spawnPlatform(int positionY) {
        Platform p = new Platform(positionY);
        platforms.add(p);
    }

    public static void update(GameContainer gc, int delta) {
        for (int i = platforms.size() - 1; i >= 0; i--) {
            platforms.get(i).update(gc, delta);
            if (platforms.get(i).positionY < 0) {
                platforms.remove(platforms.get(i));
            }
        }
    }

    public static void render(GameContainer gc, Graphics g) {
        for (Platform platform : platforms) {
            platform.setColor(lerpBetweenColors(platform.colorA, platform.colorB, platform.positionY
                    / Config.Resolution.getY()));
            platform.render(gc, g);
        }
    }

    private static Color lerpBetweenColors(Color colorA, Color colorB, float amount) {
        int r = (int) Toolkit.lerp(colorA.getRed(), colorB.getRed(), amount);
        int g = (int) Toolkit.lerp(colorA.getGreen(), colorB.getGreen(), amount);
        int b = (int) Toolkit.lerp(colorA.getBlue(), colorB.getBlue(), amount);
        return new Color(r, g, b);
    }
}
