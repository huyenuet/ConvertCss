package org.w3c.sac.parser;

/**
 * Created by smart on 05/02/2018.
 */

//package com.windrealm;

        import org.w3c.css.sac.*;
        import org.w3c.css.sac.helpers.*;
        import java.net.*;
        import java.io.*;

/**
 * <p>
 * This example count the number of property in the style rules (outside a media
 * rule).
 * </p>
 * <p>
 * This class is based on the DemoSAC.java found <a
 * href="http://www.w3.org/TR/SAC/">on this page</a>. The original example did
 * not compile out of the box for me, and I had to made some modifications. This
 * class is the modified version.
 * </p>
 * <p>
 * This class needs 2 JAR files to compile properly: sac.jar and flute.jar. Both
 * files can be downloaded from <a href="http://www.w3.org/Style/CSS/SAC/">the
 * SAC website</a>.
 * </p>
 *
 * @author Andrew Lim Chong Liang
 */
public class DemoSac implements DocumentHandler {
    boolean inMedia = false;
    boolean inStyleRule = false;
    int propertyCounter = 0;

    public void startMedia(SACMediaList media) throws CSSException {
        inMedia = true;
    }

    public void endMedia(SACMediaList media) throws CSSException {
        inMedia = false;
    }

    public void startSelector(SelectorList patterns) throws CSSException {
        if (!inMedia) {
            inStyleRule = true;
            propertyCounter = 0;
        }
    }

    public void endSelector(SelectorList patterns) throws CSSException {
        if (!inMedia) {
            System.out.println("Found " + propertyCounter + " properties.");
        }
        inStyleRule = false;

    }

    public void property(String name, LexicalUnit value, boolean important)
            throws CSSException {
        if (inStyleRule) {
            propertyCounter++;
        }
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("org.w3c.css.sac.parser", "org.w3c.flute.parser.Parser");

        InputSource source = new InputSource();
//        URL uri = new URL("file", null, -1, args[0]);
        URL uri = new URL("http://windrealm.org/tutorials/sac/parsing-css-with-sac-and-java.php#sampleInputOutput");
        InputStream stream = uri.openStream();

        source.setByteStream(stream);
        source.setURI(uri.toString());
        ParserFactory parserFactory = new ParserFactory();
        Parser parser = parserFactory.makeParser();

        parser.setDocumentHandler(new DemoSac());
        parser.parseStyleSheet(source);
        stream.close();
    }

    public void startFontFace() {
    }

    public void endFontFace() {
    }

    public void startPage(String name, String pseudoPage) {
    }

    public void endPage(String name, String pseudoPage) {
    }

    public void importStyle(String uri, SACMediaList media,
                            String defaultNamespaceURI) {
    }

    public void namespaceDeclaration(String prefix, String uri) {
    }

    public void ignorableAtRule(String atRule) {
    }

    public void comment(String text) {
    }

    public void startDocument(InputSource source) {
    }

    public void endDocument(InputSource source) {
    }
}