/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.var.common.vo;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nvwa.grid2.Grid2Data;

public class HtmlTableContext {
    private Grid2Data grid2Data;
    private int printPaperWidth;
    private int tableWidth;
    private String varType;
    private String modelKey;
    private JsonNode customRows = null;
    private JsonNode customCols = null;

    public Grid2Data getGrid2Data() {
        return this.grid2Data;
    }

    public HtmlTableContext(Grid2Data grid2Data, String varType, String modelKey) {
        this.grid2Data = grid2Data;
        this.varType = varType;
        this.modelKey = modelKey;
    }

    public void setGrid2Data(Grid2Data grid2Data) {
        this.grid2Data = grid2Data;
    }

    public int getPrintPaperWidth() {
        return this.printPaperWidth;
    }

    public void setPrintPaperWidth(int printPaperWidth) {
        this.printPaperWidth = printPaperWidth;
    }

    public int getTableWidth() {
        return this.tableWidth;
    }

    public void setTableWidth(int tableWidth) {
        this.tableWidth = tableWidth;
    }

    public String getVarType() {
        return this.varType;
    }

    public void setVarType(String varType) {
        this.varType = varType;
    }

    public String getModelKey() {
        return this.modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    public JsonNode getCustomRows() {
        return this.customRows;
    }

    public void setCustomRows(JsonNode customRows) {
        this.customRows = customRows;
    }

    public JsonNode getCustomCols() {
        return this.customCols;
    }

    public void setCustomCols(JsonNode customCols) {
        this.customCols = customCols;
    }
}

