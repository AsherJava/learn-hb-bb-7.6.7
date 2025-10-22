/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.steam.xlsx.impl;

import com.jiuqi.np.office.excel2.steam.xlsx.StreamingReader;
import com.jiuqi.np.office.excel2.steam.xlsx.XmlUtils;
import com.jiuqi.np.office.excel2.steam.xlsx.exceptions.OpenException;
import com.jiuqi.np.office.excel2.steam.xlsx.exceptions.ReadException;
import com.jiuqi.np.office.excel2.steam.xlsx.impl.StreamingSheet;
import com.jiuqi.np.office.excel2.steam.xlsx.impl.StreamingSheetReader;
import com.jiuqi.np.office.excel2.steam.xlsx.impl.TempFileUtil;
import com.jiuqi.np.office.excel2.steam.xlsx.sst.BufferedStringsTable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStrings;
import org.apache.poi.xssf.model.StylesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class StreamingWorkbookReader
implements Iterable<Sheet>,
AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(StreamingWorkbookReader.class);
    private final List<StreamingSheet> sheets;
    private final List<Map<String, String>> sheetProperties = new ArrayList<Map<String, String>>();
    private final StreamingReader.Builder builder;
    private File tmp;
    private File sstCache;
    private OPCPackage pkg;
    private SharedStrings sst;
    private boolean use1904Dates = false;

    @Deprecated
    public StreamingWorkbookReader(SharedStrings sst, File sstCache, OPCPackage pkg, StreamingSheetReader reader, StreamingReader.Builder builder) {
        this.sst = sst;
        this.sstCache = sstCache;
        this.pkg = pkg;
        this.sheets = Arrays.asList(new StreamingSheet(null, reader));
        this.builder = builder;
    }

    public StreamingWorkbookReader(StreamingReader.Builder builder) {
        this.sheets = new ArrayList<StreamingSheet>();
        this.builder = builder;
    }

    public StreamingSheetReader first() {
        return this.sheets.get(0).getReader();
    }

    public void init(InputStream is) {
        File f = null;
        try {
            f = TempFileUtil.writeInputStreamToFile(is, this.builder.getBufferSize());
            log.debug("Created temp file [" + f.getAbsolutePath() + "]");
            this.init(f);
            this.tmp = f;
        }
        catch (IOException e) {
            throw new ReadException("Unable to read input stream", e);
        }
        catch (RuntimeException e) {
            if (f != null) {
                f.delete();
            }
            throw e;
        }
    }

    public void init(File f) {
        try (FileInputStream stream = new FileInputStream(f);){
            Node date1904;
            if (this.builder.getPassword() != null) {
                try (POIFSFileSystem poifs = new POIFSFileSystem(stream);){
                    EncryptionInfo info = new EncryptionInfo(poifs);
                    Decryptor d = Decryptor.getInstance(info);
                    d.verifyPassword(this.builder.getPassword());
                    this.pkg = OPCPackage.open(d.getDataStream(poifs));
                }
            } else {
                this.pkg = OPCPackage.open(f);
            }
            XSSFReader reader = new XSSFReader(this.pkg);
            if (this.builder.getSstCacheSize() > 0) {
                this.sstCache = Files.createTempFile("", "", new FileAttribute[0]).toFile();
                log.debug("Created sst cache file [" + this.sstCache.getAbsolutePath() + "]");
                this.sst = BufferedStringsTable.getSharedStringsTable(this.sstCache, this.builder.getSstCacheSize(), this.pkg);
            } else {
                this.sst = reader.getSharedStringsTable();
            }
            StylesTable styles = reader.getStylesTable();
            NodeList workbookPr = XmlUtils.searchForNodeList(XmlUtils.document(reader.getWorkbookData()), "/workbook/workbookPr");
            if (workbookPr.getLength() == 1 && (date1904 = workbookPr.item(0).getAttributes().getNamedItem("date1904")) != null) {
                this.use1904Dates = "1".equals(date1904.getTextContent());
            }
            this.loadSheets(reader, this.sst, styles, this.builder.getRowCacheSize());
        }
        catch (IOException e) {
            throw new OpenException("Failed to open file", e);
        }
        catch (XMLStreamException | OpenXML4JException e) {
            throw new ReadException("Unable to read workbook", e);
        }
        catch (GeneralSecurityException e) {
            throw new ReadException("Unable to read workbook - Decryption failed", e);
        }
    }

    void loadSheets(XSSFReader reader, SharedStrings sst, StylesTable stylesTable, int rowCacheSize) throws IOException, InvalidFormatException, XMLStreamException {
        this.lookupSheetNames(reader);
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator)reader.getSheetsData();
        LinkedHashMap<URI, InputStream> sheetStreams = new LinkedHashMap<URI, InputStream>();
        while (iter.hasNext()) {
            InputStream is = iter.next();
            sheetStreams.put(iter.getSheetPart().getPartName().getURI(), is);
        }
        int i = 0;
        for (URI uri : sheetStreams.keySet()) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            factory.setProperty("javax.xml.stream.isSupportingExternalEntities", false);
            factory.setProperty("javax.xml.stream.supportDTD", false);
            XMLEventReader parser = factory.createXMLEventReader((InputStream)sheetStreams.get(uri));
            this.sheets.add(new StreamingSheet(this.sheetProperties.get(i++).get("name"), new StreamingSheetReader(sst, stylesTable, parser, this.use1904Dates, rowCacheSize)));
        }
    }

    void lookupSheetNames(XSSFReader reader) throws IOException, InvalidFormatException {
        this.sheetProperties.clear();
        NodeList nl = XmlUtils.searchForNodeList(XmlUtils.document(reader.getWorkbookData()), "/workbook/sheets/sheet");
        for (int i = 0; i < nl.getLength(); ++i) {
            HashMap<String, String> props = new HashMap<String, String>();
            props.put("name", nl.item(i).getAttributes().getNamedItem("name").getTextContent());
            Node state = nl.item(i).getAttributes().getNamedItem("state");
            props.put("state", state == null ? "visible" : state.getTextContent());
            this.sheetProperties.add(props);
        }
    }

    List<? extends Sheet> getSheets() {
        return this.sheets;
    }

    public List<Map<String, String>> getSheetProperties() {
        return this.sheetProperties;
    }

    @Override
    public Iterator<Sheet> iterator() {
        return new StreamingSheetIterator(this.sheets.iterator());
    }

    @Override
    public void close() throws IOException {
        try {
            for (StreamingSheet sheet : this.sheets) {
                sheet.getReader().close();
            }
            this.pkg.revert();
            this.pkg.close();
        }
        finally {
            if (this.tmp != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Deleting tmp file [" + this.tmp.getAbsolutePath() + "]");
                }
                this.tmp.delete();
            }
            if (this.sst instanceof BufferedStringsTable) {
                if (log.isDebugEnabled()) {
                    log.debug("Deleting sst cache file [" + this.sstCache.getAbsolutePath() + "]");
                }
                ((BufferedStringsTable)this.sst).close();
                this.sstCache.delete();
            }
        }
    }

    static class StreamingSheetIterator
    implements Iterator<Sheet> {
        private final Iterator<StreamingSheet> iterator;

        public StreamingSheetIterator(Iterator<StreamingSheet> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public Sheet next() {
            return this.iterator.next();
        }

        @Override
        public void remove() {
            throw new RuntimeException("NotSupported");
        }
    }
}

