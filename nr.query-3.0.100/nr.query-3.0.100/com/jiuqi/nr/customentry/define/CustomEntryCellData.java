/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.customentry.define;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomEntryCellData {
    private static final Logger log = LoggerFactory.getLogger(CustomEntryCellData.class);
    private String clientData;
    private String editText;
    private int colIndex;
    private int rowIndex;
    private String period;
    private String hasBizKey;

    public void setClientData(String clientData) {
        this.clientData = clientData;
    }

    public String getClientData() {
        return this.clientData;
    }

    public Map<String, String> getClientDataObj() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return (Map)mapper.readValue(this.clientData, Map.class);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public void setEditText(String editText) {
        this.editText = editText;
    }

    public String getEditText() {
        return this.editText;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public int getColIndex() {
        return this.colIndex;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getHasBizKey() {
        return this.hasBizKey;
    }

    public void setHasBizKey(String hasBizKey) {
        this.hasBizKey = hasBizKey;
    }
}

