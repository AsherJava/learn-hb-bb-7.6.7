/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nvwa.cellbook.json.CellBookDeserialize
 *  com.jiuqi.nvwa.cellbook.json.CellBookSerialize
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 */
package com.jiuqi.nr.summary.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nvwa.cellbook.json.CellBookDeserialize;
import com.jiuqi.nvwa.cellbook.json.CellBookSerialize;
import com.jiuqi.nvwa.cellbook.model.CellBook;

public class SummaryReportModelVO {
    private SummaryReportModel reportModel;
    private String solutionKey;
    @JsonSerialize(using=CellBookSerialize.class)
    @JsonDeserialize(using=CellBookDeserialize.class)
    private CellBook cellBook;
    private boolean deploy;

    public SummaryReportModel getReportModel() {
        return this.reportModel;
    }

    public void setReportModel(SummaryReportModel reportModel) {
        this.reportModel = reportModel;
    }

    public String getSolutionKey() {
        return this.solutionKey;
    }

    public void setSolutionKey(String solutionKey) {
        this.solutionKey = solutionKey;
    }

    public CellBook getCellBook() {
        return this.cellBook;
    }

    public void setCellBook(CellBook cellBook) {
        this.cellBook = cellBook;
    }

    public boolean isDeploy() {
        return this.deploy;
    }

    public void setDeploy(boolean deploy) {
        this.deploy = deploy;
    }
}

