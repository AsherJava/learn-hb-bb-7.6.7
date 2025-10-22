/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.billcore.fetchdata.dto;

import java.util.List;
import java.util.Map;

public class FetchDataResultDTO {
    private List<FloatColumn> floatColumns;
    private List<List<Object>> subDatas;
    private List<Map<String, Object>> subItems;
    private List<FloatColumn> masterColumns;
    private Map<String, Object> masterData;

    public List<FloatColumn> getFloatColumns() {
        return this.floatColumns;
    }

    public void setFloatColumns(List<FloatColumn> floatColumns) {
        this.floatColumns = floatColumns;
    }

    public List<List<Object>> getSubDatas() {
        return this.subDatas;
    }

    public void setSubDatas(List<List<Object>> subDatas) {
        this.subDatas = subDatas;
    }

    public List<FloatColumn> getMasterColumns() {
        return this.masterColumns;
    }

    public void setMasterColumns(List<FloatColumn> masterColumns) {
        this.masterColumns = masterColumns;
    }

    public Map<String, Object> getMasterData() {
        return this.masterData;
    }

    public void setMasterData(Map<String, Object> masterData) {
        this.masterData = masterData;
    }

    class FloatColumn {
        private String filedCode;
        private int index;

        FloatColumn() {
        }

        public String getFiledCode() {
            return this.filedCode;
        }

        public void setFiledCode(String filedCode) {
            this.filedCode = filedCode;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}

