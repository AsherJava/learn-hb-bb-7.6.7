/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.steam.xlsx;

import com.jiuqi.np.office.excel2.steam.xlsx.XmlUtils;
import com.jiuqi.np.office.excel2.steam.xlsx.exceptions.MissingSheetException;
import com.jiuqi.np.office.excel2.steam.xlsx.exceptions.OpenException;
import com.jiuqi.np.office.excel2.steam.xlsx.exceptions.ReadException;
import com.jiuqi.np.office.excel2.steam.xlsx.impl.StreamingSheetReader;
import com.jiuqi.np.office.excel2.steam.xlsx.impl.StreamingWorkbook;
import com.jiuqi.np.office.excel2.steam.xlsx.impl.StreamingWorkbookReader;
import com.jiuqi.np.office.excel2.steam.xlsx.impl.TempFileUtil;
import com.jiuqi.np.office.excel2.steam.xlsx.sst.BufferedStringsTable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.Objects;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStrings;
import org.apache.poi.xssf.model.StylesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class StreamingReader
implements Iterable<Row>,
AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(StreamingReader.class);
    private File tmp;
    private final StreamingWorkbookReader workbook;

    public StreamingReader(StreamingWorkbookReader workbook) {
        this.workbook = workbook;
    }

    @Override
    public Iterator<Row> iterator() {
        return this.workbook.first().iterator();
    }

    @Override
    public void close() throws IOException {
        try {
            this.workbook.close();
        }
        finally {
            if (this.tmp != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Deleting tmp file [" + this.tmp.getAbsolutePath() + "]");
                }
                this.tmp.delete();
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int rowCacheSize = 10;
        private int bufferSize = 1024;
        private int sheetIndex = 0;
        private int sstCacheSize = -1;
        private String sheetName;
        private String password;

        public int getRowCacheSize() {
            return this.rowCacheSize;
        }

        public int getBufferSize() {
            return this.bufferSize;
        }

        public int getSheetIndex() {
            return this.sheetIndex;
        }

        public String getSheetName() {
            return this.sheetName;
        }

        public String getPassword() {
            return this.password;
        }

        public int getSstCacheSize() {
            return this.sstCacheSize;
        }

        public Builder rowCacheSize(int rowCacheSize) {
            this.rowCacheSize = rowCacheSize;
            return this;
        }

        public Builder bufferSize(int bufferSize) {
            this.bufferSize = bufferSize;
            return this;
        }

        public Builder sheetIndex(int sheetIndex) {
            this.sheetIndex = sheetIndex;
            return this;
        }

        public Builder sheetName(String sheetName) {
            this.sheetName = sheetName;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder sstCacheSize(int sstCacheSize) {
            this.sstCacheSize = sstCacheSize;
            return this;
        }

        public Workbook open(InputStream is) {
            StreamingWorkbookReader workbook = new StreamingWorkbookReader(this);
            workbook.init(is);
            return new StreamingWorkbook(workbook);
        }

        public Workbook open(File file) {
            StreamingWorkbookReader workbook = new StreamingWorkbookReader(this);
            workbook.init(file);
            return new StreamingWorkbook(workbook);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public StreamingReader read(InputStream is) {
            File f = null;
            try {
                f = TempFileUtil.writeInputStreamToFile(is, this.bufferSize);
                log.debug("Created temp file [" + f.getAbsolutePath() + "]");
                try (StreamingReader r = this.read(f);){
                    r.tmp = f;
                    StreamingReader streamingReader = r;
                    return streamingReader;
                }
            }
            catch (IOException e) {
                throw new ReadException("Unable to read input stream", e);
            }
            catch (RuntimeException e) {
                if (f == null) throw e;
                f.delete();
                throw e;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public StreamingReader read(File f) {
            try {
                Node date1904;
                SharedStrings sst;
                OPCPackage pkg;
                if (this.password != null) {
                    try (POIFSFileSystem poifs = new POIFSFileSystem(new FileInputStream(f));){
                        EncryptionInfo info = new EncryptionInfo(poifs);
                        Decryptor d = Decryptor.getInstance(info);
                        d.verifyPassword(this.password);
                        pkg = OPCPackage.open(d.getDataStream(poifs));
                    }
                } else {
                    pkg = OPCPackage.open(f);
                }
                boolean use1904Dates = false;
                XSSFReader reader = new XSSFReader(pkg);
                File sstCache = null;
                if (this.sstCacheSize > 0) {
                    sstCache = Files.createTempFile("", "", new FileAttribute[0]).toFile();
                    log.debug("Created sst cache file [" + sstCache.getAbsolutePath() + "]");
                    sst = BufferedStringsTable.getSharedStringsTable(sstCache, this.sstCacheSize, pkg);
                } else {
                    sst = reader.getSharedStringsTable();
                }
                StylesTable styles = reader.getStylesTable();
                NodeList workbookPr = XmlUtils.searchForNodeList(XmlUtils.document(reader.getWorkbookData()), "/workbook/workbookPr");
                if (workbookPr.getLength() == 1 && (date1904 = workbookPr.item(0).getAttributes().getNamedItem("date1904")) != null) {
                    use1904Dates = "1".equals(date1904.getTextContent());
                }
                try (InputStream sheet = this.findSheet(reader);){
                    if (sheet == null) {
                        throw new MissingSheetException("Unable to find sheet at index [" + this.sheetIndex + "]");
                    }
                    XMLInputFactory factory = XMLInputFactory.newInstance();
                    factory.setProperty("javax.xml.stream.supportDTD", false);
                    factory.setProperty("javax.xml.stream.isSupportingExternalEntities", false);
                    XMLEventReader parser = factory.createXMLEventReader(sheet);
                    StreamingReader streamingReader = new StreamingReader(new StreamingWorkbookReader(sst, sstCache, pkg, new StreamingSheetReader(sst, styles, parser, use1904Dates, this.rowCacheSize), this));
                    return streamingReader;
                }
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

        private InputStream findSheet(XSSFReader reader) throws IOException, InvalidFormatException {
            int index = this.sheetIndex;
            if (this.sheetName != null) {
                index = -1;
                NodeList nl = XmlUtils.searchForNodeList(XmlUtils.document(reader.getWorkbookData()), "/workbook/sheets/sheet");
                for (int i = 0; i < nl.getLength(); ++i) {
                    if (!Objects.equals(nl.item(i).getAttributes().getNamedItem("name").getTextContent(), this.sheetName)) continue;
                    index = i;
                }
                if (index < 0) {
                    return null;
                }
            }
            Iterator<InputStream> iter = reader.getSheetsData();
            InputStream sheet = null;
            int i = 0;
            while (iter.hasNext()) {
                InputStream is = iter.next();
                if (i++ != index) continue;
                sheet = is;
                log.debug("Found sheet at index [" + this.sheetIndex + "]");
                break;
            }
            return sheet;
        }
    }
}

