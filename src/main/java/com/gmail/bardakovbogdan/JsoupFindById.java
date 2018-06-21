package com.gmail.bardakovbogdan;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class JsoupFindById {
    private static Logger LOGGER = LoggerFactory.getLogger(JsoupFindById.class);

    static Optional<Element> findElementById(File htmlFile, String targetElementId) {
        try {
            String CHARSET_NAME = "utf8";
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return Optional.of(doc.getElementById(targetElementId));

        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            return Optional.empty();
        }
    }
}
