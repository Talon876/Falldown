package org.nolat.falldown;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class NativeExtractor {

    private static final Logger log = Logger.getLogger(NativeExtractor.class);

    public static String extractNatives() {
        String nativeDirPath = System.getProperty("user.home") + "/." + App.getAppname().toLowerCase() + "/natives/"
                + App.getVersion() + "/";
        File nativeDir = new File(nativeDirPath);
        nativeDir.mkdirs();

        String pathToJar = NativeExtractor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            String decodedPathToJar = URLDecoder.decode(pathToJar, "UTF-8");
            log.info("Loading jar at " + decodedPathToJar);
            JarFile self = new JarFile(decodedPathToJar);
            for (Enumeration<JarEntry> list = self.entries(); list.hasMoreElements();) {
                ZipEntry entry = list.nextElement();
                if (entry.getName().startsWith("natives/")) {
                    try {
                        String[] tokens = entry.getName().split("/");
                        String fileName = tokens[tokens.length - 1];
                        extractFromJar("/natives/" + fileName, nativeDirPath + fileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.error(e);
                    }
                }
            }

        } catch (Exception e) {
            log.error(e);
        }

        return nativeDirPath;
    }

    private static void extractFromJar(String jarPath, String fsPath) throws IOException {
        try {
            InputStream in = NativeExtractor.class.getResourceAsStream(jarPath);
            File fileOut = new File(fsPath);
            if (!fileOut.exists()) { //only copy if the file doesn't already exist.
                log.info("Moving " + jarPath + " from jar to " + fsPath);
                OutputStream out = FileUtils.openOutputStream(fileOut);
                IOUtils.copy(in, out);
                in.close();
                out.close();
            }
        } catch (Exception e) {
            log.error(e);
        }
    }
}
