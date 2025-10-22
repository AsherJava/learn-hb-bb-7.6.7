/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.facade;

import com.jiuqi.nr.analysisreport.vo.print.PrintStyle;
import java.util.HashMap;
import java.util.Map;

public class ExportSetting {
    private int indexParagraph = 0;
    private Map<String, Integer> pageNums = new HashMap<String, Integer>();
    private Integer initPageNum = -1;
    private PrintStyle printStyle;
    private String originNumber;

    public int getIndexParagraph() {
        return this.indexParagraph;
    }

    public void setIndexParagraph(int indexParagraph) {
        this.indexParagraph = indexParagraph;
    }

    public Integer getInitPageNum() {
        return this.initPageNum;
    }

    public void setInitPageNum(Integer initPageNum) {
        this.initPageNum = initPageNum;
    }

    public String getOriginNumber() {
        return this.originNumber;
    }

    public void setOriginNumber(String originNumber) {
        this.originNumber = originNumber;
    }

    public Map<String, Integer> getPageNums() {
        return this.pageNums;
    }

    public void setPageNums(Map<String, Integer> pageNums) {
        this.pageNums = pageNums;
    }

    public PrintStyle getPrintStyle() {
        return this.printStyle;
    }

    public void setPrintStyle(PrintStyle printStyle) {
        this.printStyle = printStyle;
    }
}

