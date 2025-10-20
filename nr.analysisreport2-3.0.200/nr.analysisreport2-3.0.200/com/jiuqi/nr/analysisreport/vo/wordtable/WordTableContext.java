/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.xwpf.usermodel.XWPFTable
 */
package com.jiuqi.nr.analysisreport.vo.wordtable;

import com.jiuqi.nr.analysisreport.facade.ExportSetting;
import com.jiuqi.nr.analysisreport.utils.CustomXWPFDocument;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.xwpf.usermodel.XWPFTable;

public class WordTableContext {
    private int rowLength;
    private int colLength;
    private int longestRow;
    private Map<Integer, List<Integer>> hMergeCells = new HashMap<Integer, List<Integer>>();
    private XWPFTable table;
    private CustomXWPFDocument document;
    private ExportSetting exportSetting;

    public int getRowLength() {
        return this.rowLength;
    }

    public int getColLength() {
        return this.colLength;
    }

    public int getLongestRow() {
        return this.longestRow;
    }

    public WordTableContext(int rowLength, int colLength, int longestRow) {
        this.rowLength = rowLength;
        this.colLength = colLength;
        this.longestRow = longestRow;
    }

    public CustomXWPFDocument getDocument() {
        return this.document;
    }

    public void setDocument(CustomXWPFDocument document) {
        this.document = document;
    }

    public ExportSetting getExportSetting() {
        return this.exportSetting;
    }

    public void setExportSetting(ExportSetting exportSetting) {
        this.exportSetting = exportSetting;
    }

    public XWPFTable getTable() {
        return this.table;
    }

    public void setTable(XWPFTable table) {
        this.table = table;
    }

    public Map<Integer, List<Integer>> gethMergeCells() {
        return this.hMergeCells;
    }
}

