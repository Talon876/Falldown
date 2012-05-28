package org.nolat.falldown;

import java.util.Timer;
import java.util.TimerTask;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Falldown extends BasicGame {

    Player player;
    Timer newPlatformTimer;

    public Falldown(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        Config.init();
        player = new Player();
        PlatformManager.spawnPlatform(600);
        newPlatformTimer = new Timer("NewPlatformSpawner");
        TimerTask newPlatformTask = new TimerTask() {

            @Override
            public void run() {
                PlatformManager.spawnPlatform(620);
            }

        };
        newPlatformTimer.schedule(newPlatformTask, Config.WallSpawn, Config.WallSpawn);

    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        player.update(gc, delta);
        PlatformManager.update(gc, delta);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        Config.Background.draw(0, 0);
        PlatformManager.render(gc, g);
        player.render(g);
    }

}
