/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.steam.xlsx.sst;

import com.jiuqi.np.office.excel2.steam.xlsx.sst.FileBackedList;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

public class BufferedStringsTable
extends SharedStringsTable
implements AutoCloseable {
    private final FileBackedList list;

    public static BufferedStringsTable getSharedStringsTable(File tmp, int cacheSize, OPCPackage pkg) throws IOException {
        ArrayList<PackagePart> parts = pkg.getPartsByContentType(XSSFRelation.SHARED_STRINGS.getContentType());
        return parts.size() == 0 ? null : new BufferedStringsTable((PackagePart)parts.get(0), tmp, cacheSize);
    }

    private BufferedStringsTable(PackagePart part, File file, int cacheSize) throws IOException {
        this.list = new FileBackedList(file, cacheSize);
        try (InputStream inputStream = part.getInputStream();){
            this.readFrom(inputStream);
        }
    }

    @Override
    public void readFrom(InputStream is) throws IOException {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            factory.setProperty("javax.xml.stream.isSupportingExternalEntities", false);
            factory.setProperty("javax.xml.stream.supportDTD", false);
            XMLEventReader xmlEventReader = factory.createXMLEventReader(is);
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (!xmlEvent.isStartElement() || !xmlEvent.asStartElement().getName().getLocalPart().equals("si")) continue;
                this.list.add(this.parseCT_Rst(xmlEventReader));
            }
        }
        catch (XMLStreamException e) {
            throw new IOException(e);
        }
    }

    private String parseCT_Rst(XMLEventReader xmlEventReader) throws XMLStreamException {
        XMLEvent xmlEvent;
        StringBuilder buf = new StringBuilder();
        block11: while ((xmlEvent = xmlEventReader.nextTag()).isStartElement()) {
            switch (xmlEvent.asStartElement().getName().getLocalPart()) {
                case "t": {
                    buf.append(xmlEventReader.getElementText());
                    continue block11;
                }
                case "r": {
                    this.parseCT_RElt(xmlEventReader, buf);
                    continue block11;
                }
                case "rPh": 
                case "phoneticPr": {
                    this.skipElement(xmlEventReader);
                    continue block11;
                }
            }
            throw new IllegalArgumentException(xmlEvent.asStartElement().getName().getLocalPart());
        }
        return buf.length() > 0 ? buf.toString() : null;
    }

    private void parseCT_RElt(XMLEventReader xmlEventReader, StringBuilder buf) throws XMLStreamException {
        XMLEvent xmlEvent;
        block8: while ((xmlEvent = xmlEventReader.nextTag()).isStartElement()) {
            switch (xmlEvent.asStartElement().getName().getLocalPart()) {
                case "t": {
                    buf.append(xmlEventReader.getElementText());
                    continue block8;
                }
                case "rPr": {
                    this.skipElement(xmlEventReader);
                    continue block8;
                }
            }
            throw new IllegalArgumentException(xmlEvent.asStartElement().getName().getLocalPart());
        }
    }

    private void skipElement(XMLEventReader xmlEventReader) throws XMLStreamException {
        while (xmlEventReader.nextTag().isStartElement()) {
            this.skipElement(xmlEventReader);
        }
    }

    @Override
    public RichTextString getItemAt(int idx) {
        return new XSSFRichTextString(this.list.getAt(idx));
    }

    @Override
    public void close() throws IOException {
        this.list.close();
    }
}

