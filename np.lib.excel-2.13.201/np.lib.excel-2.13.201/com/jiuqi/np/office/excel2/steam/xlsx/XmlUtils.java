/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.steam.xlsx;

import com.jiuqi.np.office.excel2.steam.xlsx.exceptions.ParseException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlUtils {
    public static Document document(InputStream is) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            factory.setExpandEntityReferences(false);
            factory.setXIncludeAware(false);
            return factory.newDocumentBuilder().parse(is);
        }
        catch (IOException | ParserConfigurationException | SAXException e) {
            throw new ParseException(e);
        }
    }

    public static NodeList searchForNodeList(Document document, String xpath) {
        try {
            return (NodeList)XPathFactory.newInstance().newXPath().compile(xpath).evaluate(document, XPathConstants.NODESET);
        }
        catch (XPathExpressionException e) {
            throw new ParseException(e);
        }
    }
}

