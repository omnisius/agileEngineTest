package com.gmail.bardakovbogdan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

class FileFinder {
    private static Logger LOGGER = LoggerFactory.getLogger(FileFinder.class);

    private static FileFinder ourInstance = new FileFinder();

    static FileFinder getInstance() {
        return ourInstance;
    }

    private FileFinder() {
    }

    File getFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            return new File(classLoader.getResource(fileName).getFile());
        } catch (NullPointerException e) {
            LOGGER.error("File " + fileName + " is absent. Program cannot be finished. Please check your parameters");
            throw e;
        }
    }
}
