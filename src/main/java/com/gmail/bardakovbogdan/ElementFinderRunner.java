package com.gmail.bardakovbogdan;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ElementFinderRunner {
    private static Logger LOGGER = LoggerFactory.getLogger(ElementFinderRunner.class);

    private static final String CONSTANT_ATTRIBUTE = "class";
    private static final String ATRRIBUTE_THAT_SHOULD_BE_PRESENT = "rel";

    public static void main(String[] args) {
        findElementAndLogResult(args[0], args[1], args[2]);
    }

    private static void findElementAndLogResult(String originalFileName, String targetFileName, String elementId) {
        FileFinder fileFinder =  new FileFinder();
        Optional<Element> element = JsoupFindById.findElementById(fileFinder.getFile(originalFileName), elementId);
        if(element.isPresent()) {
            String queryString = String.format("a[%s=\"%s\"]", CONSTANT_ATTRIBUTE, element.get().attr(CONSTANT_ATTRIBUTE));
            Optional<Elements> elements = JsoupCssSelect.findElementsByQuery(fileFinder.getFile(targetFileName), queryString);
            if(elements.isPresent()) {
                for (Element targetElement : elements.get()) {
                    if (targetElement.hasAttr(ATRRIBUTE_THAT_SHOULD_BE_PRESENT)) {
                        targetElement.parents().forEach(parentElement -> LOGGER.info("Parent of target element is " + parentElement.tag()));
                        LOGGER.info("Target element is " + targetElement.tag());
                    }
                }
            } else {
                LOGGER.error("There are no suitable elements in file " + targetFileName);
            }
        } else {
            LOGGER.error("element is absent in original file!");
        }
    }
}
