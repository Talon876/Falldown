package org.nolat.falldown;

import org.apache.log4j.Logger;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Config {

    private static final Logger log = Logger.getLogger(Config.class);
    public static final String Title = "Falldown";
    public static final Point Resolution = new Point(800, 600);
    public static final boolean Fullscreen = false;

    public static final int Difficulty = 3;

    public static Color PlayerColor = Color.white;
    public static float PlayerSpeed = 0.35f;
    public static float WallSpeed = 0.08f;
    public static float Gravity = 0.25f;
    public static int WallSpawn = 1000; // 1000ms

    public static Image WallTexture;
    public static Image Background;
    public static Image PlayerTexture;

    public static void init() {
        try {
            WallTexture = new Image("images/wallsegment.png");
            Background = new Image("images/background.jpg");
            PlayerTexture = new Image("images/ball.png");
        } catch (SlickException e) {
            log.error(e);
        }
    }
}
