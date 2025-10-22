/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.parser.eoums;

import com.jiuqi.nr.single.core.para.parser.eoums.DataInfo;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EnumsItemModel {
    private Map<String, DataInfo> datas = new LinkedHashMap<String, DataInfo>();
    private List<DataInfo> dataList = new ArrayList<DataInfo>();
    private String code;
    private String jioCode;
    private boolean multiSelect;
    private String title;
    private boolean valueEmpty;
    private String codeStruct;
    private int codeLen;
    private boolean fix;
    private int treeTyep;
    private boolean leafOnly;
    private boolean forceInList;

    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    public String getJioCode() {
        return this.jioCode;
    }

    public void setJioCode(String value) {
        this.jioCode = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getCodeStruct() {
        return this.codeStruct;
    }

    public void setCodeStruct(String value) {
        this.codeStruct = value;
    }

    public int getCodeLen() {
        return this.codeLen;
    }

    public void setCodeLen(int value) {
        this.codeLen = value;
    }

    public boolean getFix() {
        return this.fix;
    }

    public void setFix(boolean value) {
        this.fix = value;
    }

    public int getTreeTyep() {
        return this.treeTyep;
    }

    public void setTreeTyep(int value) {
        this.treeTyep = value;
    }

    public boolean getLeafOnly() {
        return this.leafOnly;
    }

    public void setLeafOnly(boolean value) {
        this.leafOnly = value;
    }

    public boolean getForceInList() {
        return this.forceInList;
    }

    public void setForceInList(boolean value) {
        this.forceInList = value;
    }

    public boolean getMultiSelect() {
        return this.multiSelect;
    }

    public void setMultiSelect(boolean value) {
        this.multiSelect = value;
    }

    public Map<String, DataInfo> getEnumItemList() {
        return this.datas;
    }

    public List<DataInfo> getItemDataList() {
        return this.dataList;
    }

    public boolean getValueEmpty() {
        return this.valueEmpty;
    }

    public void setValueEmpty(boolean valueEmpty) {
        this.valueEmpty = valueEmpty;
    }
}

