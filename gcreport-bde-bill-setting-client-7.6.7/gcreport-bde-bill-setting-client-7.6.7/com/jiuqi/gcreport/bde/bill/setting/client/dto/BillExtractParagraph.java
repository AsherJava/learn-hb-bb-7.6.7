/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.setting.client.dto;

import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractField;
import java.util.List;

public class BillExtractParagraph {
    private List<List<BillExtractField>> paraRowData;
    private String paraTitle;

    public BillExtractParagraph() {
    }

    public BillExtractParagraph(List<List<BillExtractField>> paraRowData, String paraTitle) {
        this.paraRowData = paraRowData;
        this.paraTitle = paraTitle;
    }

    public List<List<BillExtractField>> getParaRowData() {
        return this.paraRowData;
    }

    public void setParaRowData(List<List<BillExtractField>> paraRowData) {
        this.paraRowData = paraRowData;
    }

    public String getParaTitle() {
        return this.paraTitle;
    }

    public void setParaTitle(String paraTitle) {
        this.paraTitle = paraTitle;
    }
}

