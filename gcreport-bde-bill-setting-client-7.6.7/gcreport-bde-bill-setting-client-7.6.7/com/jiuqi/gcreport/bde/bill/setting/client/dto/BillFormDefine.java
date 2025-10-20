/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.setting.client.dto;

import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillChildTableData;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractParagraph;
import java.util.List;

public class BillFormDefine {
    private String mainTitle;
    private String mainTableName;
    private String mainTableTitle;
    private List<BillExtractParagraph> mainData;
    private List<BillChildTableData> childrenTable;

    public BillFormDefine() {
    }

    public BillFormDefine(String mainTitle, String mainTableName, String mainTableTitle, List<BillExtractParagraph> mainData, List<BillChildTableData> childrenTable) {
        this.mainTitle = mainTitle;
        this.mainTableName = mainTableName;
        this.mainTableTitle = mainTableTitle;
        this.mainData = mainData;
        this.childrenTable = childrenTable;
    }

    public String getMainTitle() {
        return this.mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getMainTableName() {
        return this.mainTableName;
    }

    public void setMainTableName(String mainTableName) {
        this.mainTableName = mainTableName;
    }

    public String getMainTableTitle() {
        return this.mainTableTitle;
    }

    public void setMainTableTitle(String mainTableTitle) {
        this.mainTableTitle = mainTableTitle;
    }

    public List<BillExtractParagraph> getMainData() {
        return this.mainData;
    }

    public void setMainData(List<BillExtractParagraph> mainData) {
        this.mainData = mainData;
    }

    public List<BillChildTableData> getChildrenTable() {
        return this.childrenTable;
    }

    public void setChildrenTable(List<BillChildTableData> childrenTable) {
        this.childrenTable = childrenTable;
    }
}

