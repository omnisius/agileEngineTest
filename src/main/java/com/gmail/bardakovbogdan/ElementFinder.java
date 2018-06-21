package com.gmail.bardakovbogdan;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;

public class ElementFinder {
    private static Logger LOGGER = LoggerFactory.getLogger(ElementFinder.class);

    private static final String CONSTANT_ATTRIBUTE = "class";

    public static void main(String[] args) {
        ElementFinder finder =  new ElementFinder();
        Optional<Element> element = JsoupFindById.findElementById(finder.getFile( args[0]), "make-everything-ok-button");
        if(element.isPresent()) {
            String queryString = String.format("a[%s=\"%s\"]", CONSTANT_ATTRIBUTE, element.get().attr(CONSTANT_ATTRIBUTE));
            Optional<Elements> elements = JsoupCssSelect.findElementsByQuery(finder.getFile(args[1]), queryString);
            elements.ifPresent(attrs -> LOGGER.info("Target element attrs: [{}]", attrs));

        } else {
            LOGGER.error("element is absent in original file!");
        }
    }

    private File getFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("samples/" + fileName).getFile());
    }
}
