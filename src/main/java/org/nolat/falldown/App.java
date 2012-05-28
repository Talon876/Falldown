package org.nolat.falldown;

import org.apache.log4j.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class App {

    private static final Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) {
        log.info("Set org.lwjgl.librarypath to " + System.getProperty("user.dir") + "/natives/");
        System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/natives/");
        try {
            AppGameContainer app = new AppGameContainer(new Falldown(Config.Title));
            app.setDisplayMode(Config.Resolution.getX(), Config.Resolution.getY(), Config.Fullscreen);
            app.start();
        } catch (SlickException e) {
            log.error(e);
        }
    }
}
