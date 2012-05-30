package org.nolat.falldown;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class App {

    private static final Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) {
        String nativeDir = NativeExtractor.extractNatives();
        log.info("Set org.lwjgl.librarypath to " + nativeDir);
        System.setProperty("org.lwjgl.librarypath", nativeDir);
        try {
            AppGameContainer app = new AppGameContainer(new Falldown(getAppname() + " - " + getVersion()));
            app.setDisplayMode(Config.Resolution.getX(), Config.Resolution.getY(), Config.Fullscreen);
            app.start();
        } catch (SlickException e) {
            log.error(e);
        }
    }

    public static String getVersion() {
        String version = "";
        final Properties pom = new Properties();
        try {
            pom.load(App.class.getResourceAsStream("/META-INF/maven/org.nolat/Falldown/pom.properties"));
            version = pom.getProperty("version");
        } catch (IOException e) {
            log.error(e);
        }
        return version;
    }

    public static String getAppname() {
        String appname = "";
        final Properties pom = new Properties();
        try {
            pom.load(App.class.getResourceAsStream("/META-INF/maven/org.nolat/Falldown/pom.properties"));
            appname = pom.getProperty("artifactId");
        } catch (IOException e) {
            appname = "DEV";
            log.error(e);
        }
        return appname;
    }
}
