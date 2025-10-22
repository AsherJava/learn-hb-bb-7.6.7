/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.steam.xlsx.impl;

import com.jiuqi.np.office.excel2.steam.xlsx.exceptions.CloseException;
import com.jiuqi.np.office.excel2.steam.xlsx.exceptions.ParseException;
import com.jiuqi.np.office.excel2.steam.xlsx.impl.StreamingCell;
import com.jiuqi.np.office.excel2.steam.xlsx.impl.StreamingRow;
import com.jiuqi.np.office.excel2.steam.xlsx.impl.StringSupplier;
import com.jiuqi.np.office.excel2.steam.xlsx.impl.Supplier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.model.SharedStrings;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class StreamingSheetReader
implements Iterable<Row> {
    private static final Logger log = LoggerFactory.getLogger(StreamingSheetReader.class);
    private final SharedStrings sst;
    private final StylesTable stylesTable;
    private final XMLEventReader parser;
    private final DataFormatter dataFormatter = new DataFormatter();
    private final Set<Integer> hiddenColumns = new HashSet<Integer>();
    private int lastRowNum;
    private int currentRowNum;
    private int firstColNum = 0;
    private int currentColNum;
    private int rowCacheSize;
    private List<Row> rowCache = new ArrayList<Row>();
    private Iterator<Row> rowCacheIterator;
    private String lastContents;
    private StreamingRow currentRow;
    private StreamingCell currentCell;
    private boolean use1904Dates;
    private List<CellRangeAddress> cellRanges;

    public StreamingSheetReader(SharedStrings sst, StylesTable stylesTable, XMLEventReader parser, boolean use1904Dates, int rowCacheSize) {
        this.sst = sst;
        this.stylesTable = stylesTable;
        this.parser = parser;
        this.use1904Dates = use1904Dates;
        this.rowCacheSize = rowCacheSize;
        this.cellRanges = new ArrayList<CellRangeAddress>();
    }

    private boolean getRow() {
        try {
            this.rowCache.clear();
            while (this.rowCache.size() < this.rowCacheSize && this.parser.hasNext()) {
                this.handleEvent(this.parser.nextEvent());
            }
            this.rowCacheIterator = this.rowCache.iterator();
            return this.rowCacheIterator.hasNext();
        }
        catch (XMLStreamException | SAXException e) {
            throw new ParseException("Error reading XML stream", e);
        }
    }

    private String[] splitCellRef(String ref) {
        int splitPos = -1;
        for (int i = 1; i < ref.length(); ++i) {
            char c = ref.charAt(i);
            if (c < '0' || c > '9') continue;
            splitPos = i;
            break;
        }
        return new String[]{ref.substring(0, splitPos), ref.substring(splitPos)};
    }

    private void handleEvent(XMLEvent event) throws SAXException {
        if (event.getEventType() == 4) {
            Characters c = event.asCharacters();
            this.lastContents = this.lastContents + c.getData();
        } else if (event.getEventType() == 1 && this.isSpreadsheetTag(event.asStartElement().getName())) {
            StartElement startElement = event.asStartElement();
            String tagLocalName = startElement.getName().getLocalPart();
            if ("mergeCell".equals(tagLocalName)) {
                Attribute mergeCellNumAttr = startElement.getAttributeByName(new QName("ref"));
                this.handleMergeCell(mergeCellNumAttr);
            } else if ("row".equals(tagLocalName)) {
                Attribute isHiddenAttr;
                Attribute rowNumAttr = startElement.getAttributeByName(new QName("r"));
                int rowIndex = this.currentRowNum;
                if (rowNumAttr != null) {
                    this.currentRowNum = rowIndex = Integer.parseInt(rowNumAttr.getValue()) - 1;
                }
                boolean isHidden = (isHiddenAttr = startElement.getAttributeByName(new QName("hidden"))) != null && ("1".equals(isHiddenAttr.getValue()) || "true".equals(isHiddenAttr.getValue()));
                Attribute outlineLevelAttr = startElement.getAttributeByName(new QName("outlineLevel"));
                int outlineLevel = outlineLevelAttr == null ? 0 : Integer.parseInt(outlineLevelAttr.getValue());
                this.currentRow = new StreamingRow(rowIndex, isHidden, outlineLevel);
                this.currentColNum = this.firstColNum;
            } else if ("col".equals(tagLocalName)) {
                boolean isHidden;
                Attribute isHiddenAttr = startElement.getAttributeByName(new QName("hidden"));
                boolean bl = isHidden = isHiddenAttr != null && ("1".equals(isHiddenAttr.getValue()) || "true".equals(isHiddenAttr.getValue()));
                if (isHidden) {
                    Attribute minAttr = startElement.getAttributeByName(new QName("min"));
                    Attribute maxAttr = startElement.getAttributeByName(new QName("max"));
                    int min = Integer.parseInt(minAttr.getValue()) - 1;
                    int max = Integer.parseInt(maxAttr.getValue()) - 1;
                    for (int columnIndex = min; columnIndex <= max; ++columnIndex) {
                        this.hiddenColumns.add(columnIndex);
                    }
                }
            } else if ("c".equals(tagLocalName)) {
                Attribute ref = startElement.getAttributeByName(new QName("r"));
                if (ref != null) {
                    String[] coord = this.splitCellRef(ref.getValue());
                    this.currentCell = new StreamingCell(CellReference.convertColStringToIndex(coord[0]), Integer.parseInt(coord[1]) - 1, this.use1904Dates);
                } else {
                    this.currentCell = new StreamingCell(this.currentColNum, this.currentRowNum, this.use1904Dates);
                }
                this.setFormatString(startElement, this.currentCell);
                Attribute type = startElement.getAttributeByName(new QName("t"));
                if (type != null) {
                    this.currentCell.setType(type.getValue());
                } else {
                    this.currentCell.setType("n");
                }
                Attribute style = startElement.getAttributeByName(new QName("s"));
                if (style != null) {
                    String indexStr = style.getValue();
                    try {
                        int index = Integer.parseInt(indexStr);
                        this.currentCell.setCellStyle(this.stylesTable.getStyleAt(index));
                    }
                    catch (NumberFormatException nfe) {
                        log.warn("Ignoring invalid style index {}", (Object)indexStr);
                    }
                } else {
                    this.currentCell.setCellStyle(this.stylesTable.getStyleAt(0));
                }
            } else if ("dimension".equals(tagLocalName)) {
                String ref;
                Attribute refAttr = startElement.getAttributeByName(new QName("ref"));
                String string = ref = refAttr != null ? refAttr.getValue() : null;
                if (ref != null) {
                    int i;
                    for (i = ref.length() - 1; i >= 0; --i) {
                        if (Character.isDigit(ref.charAt(i))) continue;
                        try {
                            this.lastRowNum = Integer.parseInt(ref.substring(i + 1)) - 1;
                        }
                        catch (NumberFormatException numberFormatException) {}
                        break;
                    }
                    for (i = 0; i < ref.length(); ++i) {
                        if (Character.isAlphabetic(ref.charAt(i))) continue;
                        this.firstColNum = CellReference.convertColStringToIndex(ref.substring(0, i));
                        break;
                    }
                }
            } else if ("f".equals(tagLocalName) && this.currentCell != null) {
                this.currentCell.setFormulaType(true);
            }
            this.lastContents = "";
        } else if (event.getEventType() == 2 && this.isSpreadsheetTag(event.asEndElement().getName())) {
            EndElement endElement = event.asEndElement();
            String tagLocalName = endElement.getName().getLocalPart();
            if ("v".equals(tagLocalName) || "t".equals(tagLocalName)) {
                this.currentCell.setRawContents(this.unformattedContents());
                this.currentCell.setContentSupplier(this.formattedContents());
            } else if ("row".equals(tagLocalName) && this.currentRow != null) {
                this.rowCache.add(this.currentRow);
                ++this.currentRowNum;
            } else if ("c".equals(tagLocalName)) {
                this.currentRow.getCellMap().put(this.currentCell.getColumnIndex(), this.currentCell);
                this.currentCell = null;
                ++this.currentColNum;
            } else if ("f".equals(tagLocalName) && this.currentCell != null) {
                this.currentCell.setFormula(this.lastContents);
            }
        }
    }

    private void handleMergeCell(Attribute mergeCellNumAttr) {
        String cellMerge = mergeCellNumAttr.getValue();
        String[] cells = cellMerge.split(":");
        String begin = cells[0];
        String beginEnglish = begin.replaceAll("[^a-z^A-Z]", "");
        int beginNum = Integer.valueOf(begin.replaceAll(beginEnglish, ""));
        String end = cells[1];
        String endEnglish = end.replaceAll("[^a-z^A-Z]", "");
        int endNum = Integer.valueOf(end.replaceAll(endEnglish, ""));
        CellRangeAddress cellRangeAddress = new CellRangeAddress(beginNum - 1, endNum - 1, StreamingSheetReader.excelColStrToNum(beginEnglish) - 1, StreamingSheetReader.excelColStrToNum(endEnglish) - 1);
        this.cellRanges.add(cellRangeAddress);
    }

    public static int excelColStrToNum(String column) {
        int num = 0;
        int result = 0;
        int length = column.length();
        for (int i = 0; i < length; ++i) {
            char ch = column.charAt(length - i - 1);
            num = ch - 65 + 1;
            num = (int)((double)num * Math.pow(26.0, i));
            result += num;
        }
        return result;
    }

    private boolean isSpreadsheetTag(QName name) {
        return name.getNamespaceURI() != null && name.getNamespaceURI().endsWith("/main");
    }

    boolean isColumnHidden(int columnIndex) {
        if (this.rowCacheIterator == null) {
            this.getRow();
        }
        return this.hiddenColumns.contains(columnIndex);
    }

    int getLastRowNum() {
        if (this.rowCacheIterator == null) {
            this.getRow();
        }
        return this.lastRowNum;
    }

    void setFormatString(StartElement startElement, StreamingCell cell) {
        Attribute cellStyle = startElement.getAttributeByName(new QName("s"));
        String cellStyleString = cellStyle != null ? cellStyle.getValue() : null;
        XSSFCellStyle style = null;
        if (cellStyleString != null) {
            style = this.stylesTable.getStyleAt(Integer.parseInt(cellStyleString));
        } else if (this.stylesTable.getNumCellStyles() > 0) {
            style = this.stylesTable.getStyleAt(0);
        }
        if (style != null) {
            cell.setNumericFormatIndex(style.getDataFormat());
            String formatString = style.getDataFormatString();
            if (formatString != null) {
                cell.setNumericFormat(formatString);
            } else {
                cell.setNumericFormat(BuiltinFormats.getBuiltinFormat(cell.getNumericFormatIndex().shortValue()));
            }
        } else {
            cell.setNumericFormatIndex(null);
            cell.setNumericFormat(null);
        }
    }

    Supplier formattedContents() {
        return this.getFormatterForType(this.currentCell.getType());
    }

    private Supplier getFormatterForType(String type) {
        switch (type) {
            case "s": {
                if (!this.lastContents.isEmpty()) {
                    int idx = Integer.parseInt(this.lastContents);
                    return new StringSupplier(this.sst.getItemAt(idx).toString());
                }
                return new StringSupplier(this.lastContents);
            }
            case "inlineStr": 
            case "str": {
                return new StringSupplier(new XSSFRichTextString(this.lastContents).toString());
            }
            case "e": {
                return new StringSupplier("ERROR:  " + this.lastContents);
            }
            case "n": {
                if (this.currentCell.getNumericFormat() != null && this.lastContents.length() > 0) {
                    final String currentLastContents = this.lastContents;
                    final short currentNumericFormatIndex = this.currentCell.getNumericFormatIndex();
                    final String currentNumericFormat = this.currentCell.getNumericFormat();
                    return new Supplier(){
                        String cachedContent;

                        @Override
                        public Object getContent() {
                            if (this.cachedContent == null) {
                                this.cachedContent = StreamingSheetReader.this.dataFormatter.formatRawCellContents(Double.parseDouble(currentLastContents), currentNumericFormatIndex, currentNumericFormat);
                            }
                            return this.cachedContent;
                        }
                    };
                }
                return new StringSupplier(this.lastContents);
            }
        }
        return new StringSupplier(this.lastContents);
    }

    String unformattedContents() {
        switch (this.currentCell.getType()) {
            case "s": {
                if (!this.lastContents.isEmpty()) {
                    int idx = Integer.parseInt(this.lastContents);
                    return this.sst.getItemAt(idx).toString();
                }
                return this.lastContents;
            }
            case "inlineStr": {
                return new XSSFRichTextString(this.lastContents).toString();
            }
        }
        return this.lastContents;
    }

    @Override
    public Iterator<Row> iterator() {
        return new StreamingRowIterator();
    }

    public void close() {
        try {
            this.parser.close();
        }
        catch (XMLStreamException e) {
            throw new CloseException(e);
        }
    }

    public int getNumMergedRegions() {
        return this.cellRanges.size();
    }

    public CellRangeAddress getMergedRegion(int index) {
        if (index == this.cellRanges.size() - 1) {
            CellRangeAddress last = this.cellRanges.get(index);
            this.cellRanges.clear();
            return last;
        }
        return this.cellRanges.get(index);
    }

    class StreamingRowIterator
    implements Iterator<Row> {
        public StreamingRowIterator() {
            if (StreamingSheetReader.this.rowCacheIterator == null) {
                this.hasNext();
            }
        }

        @Override
        public boolean hasNext() {
            return StreamingSheetReader.this.rowCacheIterator != null && StreamingSheetReader.this.rowCacheIterator.hasNext() || StreamingSheetReader.this.getRow();
        }

        @Override
        public Row next() {
            return (Row)StreamingSheetReader.this.rowCacheIterator.next();
        }

        @Override
        public void remove() {
            throw new RuntimeException("NotSupported");
        }
    }
}

