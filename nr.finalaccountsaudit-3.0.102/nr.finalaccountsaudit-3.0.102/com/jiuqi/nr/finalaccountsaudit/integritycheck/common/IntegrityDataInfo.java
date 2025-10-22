/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.integritycheck.common;

import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class IntegrityDataInfo
extends JtableLog {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private List<String> dwKeyList = new ArrayList<String>();
    private List<String> fmKeyList = new ArrayList<String>();
    private List<String> dwKeyDefectList = new ArrayList<String>();
    private List<String> dwKeyDefectZeroList = new ArrayList<String>();
    private List<String> dwKeyDefectDiffList = new ArrayList<String>();
    private List<String> dwKeyDefectZeroDiffList = new ArrayList<String>();
    private List<String> dwList = new ArrayList<String>();
    private List<String> headerList = new ArrayList<String>();
    private Map<String, List<String>> rowDataZero = new LinkedHashMap<String, List<String>>();
    private Map<String, List<String>> rowData = new LinkedHashMap<String, List<String>>();
    private Map<String, List<String>> rowDataZeroDiff = new LinkedHashMap<String, List<String>>();
    private Map<String, List<String>> rowDataDiff = new LinkedHashMap<String, List<String>>();
    private PagerInfo pagerInfo;
    private Integer emptyTabCount = 0;
    private Integer emptyTabCountZero = 0;
    private Integer emptyTabCountDiff = 0;
    private Integer emptyTabCountZeroDiff = 0;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public Integer getEmptyTabCount() {
        return this.emptyTabCount;
    }

    public void setEmptyTabCount(Integer emptyTabCount) {
        this.emptyTabCount = emptyTabCount;
    }

    public Integer getEmptyTabCountZero() {
        return this.emptyTabCountZero;
    }

    public void setEemptyTabCountZero(Integer emptyTabCountZero) {
        this.emptyTabCountZero = emptyTabCountZero;
    }

    public Integer getEmptyTabCountDiff() {
        return this.emptyTabCountDiff;
    }

    public void setEmptyTabCountDiff(Integer emptyTabCountDiff) {
        this.emptyTabCountDiff = emptyTabCountDiff;
    }

    public Integer getEmptyTabCountZeroDiff() {
        return this.emptyTabCountZeroDiff;
    }

    public void setEmptyTabCountZeroDiff(Integer emptyTabCountZeroDiff) {
        this.emptyTabCountZeroDiff = emptyTabCountZeroDiff;
    }

    public List<String> getDwKeyDefectList() {
        return this.dwKeyDefectList;
    }

    public void setDwKeyDefectList(List<String> dwKeyDefectList) {
        this.dwKeyDefectList = dwKeyDefectList;
    }

    public List<String> getDwKeyDefectZeroList() {
        return this.dwKeyDefectZeroList;
    }

    public void setDwKeyDefectZeroList(List<String> dwKeyDefectZeroList) {
        this.dwKeyDefectZeroList = dwKeyDefectZeroList;
    }

    public List<String> getDwKeyDefectDiffList() {
        return this.dwKeyDefectDiffList;
    }

    public void setDwKeyDefectDiffList(List<String> dwKeyDefectDiffList) {
        this.dwKeyDefectDiffList = dwKeyDefectDiffList;
    }

    public List<String> getDwKeyDefectZeroDiffList() {
        return this.dwKeyDefectZeroDiffList;
    }

    public void setDwKeyDefectZeroDiffList(List<String> dwKeyDefectZeroDiffList) {
        this.dwKeyDefectZeroDiffList = dwKeyDefectZeroDiffList;
    }

    public List<String> getDwKeyList() {
        return this.dwKeyList;
    }

    public void setDwKeyList(List<String> dwKeyList) {
        this.dwKeyList = dwKeyList;
    }

    public List<String> getfmKeyList() {
        return this.fmKeyList;
    }

    public void setfmKeyList(List<String> fmKeyList) {
        this.fmKeyList = fmKeyList;
    }

    public List<String> getDwList() {
        return this.dwList;
    }

    public void setDwList(List<String> dwList) {
        this.dwList = dwList;
    }

    public List<String> getHeaderList() {
        return this.headerList;
    }

    public void setHeaderList(List<String> HeaderList) {
        this.headerList = HeaderList;
    }

    public Map<String, List<String>> getRowData() {
        return this.rowData;
    }

    public void setRowData(Map<String, List<String>> RowData) {
        this.rowData = RowData;
    }

    public Map<String, List<String>> getRowDataZero() {
        return this.rowDataZero;
    }

    public void setRowDataZero(Map<String, List<String>> RowDataZero) {
        this.rowDataZero = RowDataZero;
    }

    public Map<String, List<String>> getRowDataDiff() {
        return this.rowDataDiff;
    }

    public void setRowDataDiff(Map<String, List<String>> RowDataDiff) {
        this.rowDataDiff = RowDataDiff;
    }

    public Map<String, List<String>> getRowDataZeroDiff() {
        return this.rowDataZeroDiff;
    }

    public void setRowDataZeroDiff(Map<String, List<String>> RowDataZeroDiff) {
        this.rowDataZeroDiff = RowDataZeroDiff;
    }

    public PagerInfo getPagerInfo() {
        return this.pagerInfo;
    }

    public void setPagerInfo(PagerInfo pagerInfo) {
        this.pagerInfo = pagerInfo;
    }
}

