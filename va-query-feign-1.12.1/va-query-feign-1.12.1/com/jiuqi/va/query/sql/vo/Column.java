/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.sql.vo;

import com.jiuqi.va.query.template.enumerate.ParamTypeEnum;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import java.util.ArrayList;
import java.util.List;

public class Column {
    private String content;
    private String fieldName;
    private List<Column> listTpamscolumn = new ArrayList<Column>();
    int totalRow;
    int totalCol;
    int row;
    int col;
    int rLen;
    int cLen;
    private boolean hasChilren;
    private int treeStep;
    private String id;
    private String pid;
    private ParamTypeEnum columnType;
    private String width;
    private TemplateFieldSettingVO fieldSetting;

    public Column() {
    }

    public Column(String content, String fieldName) {
        this.content = content;
        this.fieldName = fieldName;
    }

    public Column(String fieldName, String content, int treeStep) {
        this.treeStep = treeStep;
        this.fieldName = fieldName;
        this.content = content;
    }

    public ParamTypeEnum getColumnType() {
        return this.columnType;
    }

    public void setColumnType(ParamTypeEnum columnType) {
        this.columnType = columnType;
    }

    public int getTotalRow() {
        return this.totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public int getTotalCol() {
        return this.totalCol;
    }

    public void setTotalCol(int totalCol) {
        this.totalCol = totalCol;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isHasChilren() {
        return this.hasChilren;
    }

    public void setHasChilren(boolean hasChilren) {
        this.hasChilren = hasChilren;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public List<Column> getListTpamscolumn() {
        return this.listTpamscolumn;
    }

    public void setListTpamscolumn(List<Column> listTpamscolumn) {
        this.listTpamscolumn = listTpamscolumn;
    }

    public int getTreeStep() {
        return this.treeStep;
    }

    public void setTreeStep(int treeStep) {
        this.treeStep = treeStep;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getrLen() {
        return this.rLen;
    }

    public void setrLen(int rLen) {
        this.rLen = rLen;
    }

    public int getcLen() {
        return this.cLen;
    }

    public void setcLen(int cLen) {
        this.cLen = cLen;
    }

    public String getWidth() {
        return this.width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public TemplateFieldSettingVO getFieldSetting() {
        return this.fieldSetting;
    }

    public void setFieldSetting(TemplateFieldSettingVO fieldSetting) {
        this.fieldSetting = fieldSetting;
    }
}

