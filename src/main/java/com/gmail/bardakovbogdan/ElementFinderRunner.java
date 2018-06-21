package com.gmail.bardakovbogdan;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ElementFinderRunner {
    private static Logger LOGGER = LoggerFactory.getLogger(ElementFinderRunner.class);

    private static final String CONSTANT_ATTRIBUTE = "class";
    private static final String ATTRIBUTE_THAT_SHOULD_BE_PRESENT = "rel";

    public static void main(String[] args) {
        ElementFinderRunner runner = new ElementFinderRunner();
        runner.findElement(args[0], args[1], args[2]);
    }

    private void findElement(String originalFileName, String targetFileName, String elementId) {
        FileFinder fileFinder =  FileFinder.getInstance();
        Optional<Element> element = JsoupFindById.findElementById(fileFinder.getFile(originalFileName), elementId);
        if(element.isPresent()) {
            findTargetElementAndLogResult(targetFileName, fileFinder, element.get());
        } else {
            LOGGER.error("element is absent in original file!");
        }
    }

    private void findTargetElementAndLogResult(String targetFileName, FileFinder fileFinder, Element element) {
        String queryString = String.format("a[%s=\"%s\"]", CONSTANT_ATTRIBUTE, element.attr(CONSTANT_ATTRIBUTE));
        Optional<Elements> elements = JsoupCssSelect.findElementsByQuery(fileFinder.getFile(targetFileName), queryString);
        if (elements.isPresent()) {
            findUniqueTargetAttribute(elements);
        } else {
            LOGGER.error("There are no suitable elements in file " + targetFileName);
        }
    }

    private void findUniqueTargetAttribute(Optional<Elements> elements) {
        for (Element targetElement : elements.get()) {
            if (targetElement.hasAttr(ATTRIBUTE_THAT_SHOULD_BE_PRESENT)) {
                targetElement.parents().forEach(parentElement -> LOGGER.info("Parent of target element is " + parentElement.tag()));
                LOGGER.info("Target element is " + targetElement.tag());
            }
        }
    }
}
