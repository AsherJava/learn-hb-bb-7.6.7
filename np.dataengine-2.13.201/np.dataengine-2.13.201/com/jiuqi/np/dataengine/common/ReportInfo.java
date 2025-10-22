/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.PeriodModifier
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.period.PeriodModifier;
import java.io.Serializable;

public class ReportInfo
implements Serializable {
    private static final long serialVersionUID = 3392475554185729103L;
    private String reportKey;
    private String reportSolution;
    private String reportName;
    private String reportTitle;
    @Deprecated
    private int maxRow;
    @Deprecated
    private int maxCol;
    @Deprecated
    private int minRow = 1;
    @Deprecated
    private int minCol = 1;
    private int[] validCols;
    private int[] validRows;
    private PeriodModifier periodModifier;
    private String periodDim;
    private String linkAlias;

    public ReportInfo(String reportKey, String reportName, String reportTitle, int minRow, int maxRow, int minCol, int maxCol) {
        this(reportKey, reportName, reportTitle, maxRow, maxCol);
        this.minRow = minRow;
        this.minCol = minCol;
    }

    public ReportInfo(String reportKey, String reportName, String reportTitle, int maxRow, int maxCol) {
        this.reportKey = reportKey;
        this.reportName = reportName;
        this.reportTitle = reportTitle;
        this.maxRow = maxRow;
        this.maxCol = maxCol;
    }

    public ReportInfo(String reportKey, String reportName, String reportTitle, int[] validCols, int[] validRows) {
        this.reportKey = reportKey;
        this.reportName = reportName;
        this.reportTitle = reportTitle;
        this.validCols = validCols;
        this.validRows = validRows;
    }

    public String getReportName() {
        return this.reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    @Deprecated
    public int getMaxRow() {
        return this.maxRow;
    }

    @Deprecated
    public void setMaxRow(int maxRow) {
        this.maxRow = maxRow;
    }

    @Deprecated
    public int getMaxCol() {
        return this.maxCol;
    }

    @Deprecated
    public void setMaxCol(int maxCol) {
        this.maxCol = maxCol;
    }

    public int getMinRow() {
        return this.minRow;
    }

    public void setMinRow(int minRow) {
        this.minRow = minRow;
    }

    public int getMinCol() {
        return this.minCol;
    }

    public void setMinCol(int minCol) {
        this.minCol = minCol;
    }

    public String getReportTitle() {
        return this.reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getReportSolution() {
        return this.reportSolution;
    }

    public void setReportSolution(String reportSolution) {
        this.reportSolution = reportSolution;
    }

    public String getReportKey() {
        return this.reportKey;
    }

    public void setReportKey(String reportKey) {
        this.reportKey = reportKey;
    }

    public void setPeriodModifierStr(String periodModifierStr) {
        this.periodModifier = PeriodModifier.parse((String)periodModifierStr);
    }

    public String getPeriodModifierStr() {
        return this.periodModifier == null ? null : this.periodModifier.toString();
    }

    public String getPeriodDim() {
        return this.periodDim;
    }

    public void setPeriodDim(String periodDim) {
        this.periodDim = periodDim;
    }

    public int[] getValidCols() {
        if (this.validCols == null) {
            this.validCols = new int[this.maxCol - this.minCol + 1];
            int index = 0;
            int i = this.minCol;
            while (i <= this.maxCol) {
                this.validCols[index] = i++;
                ++index;
            }
        }
        return this.validCols;
    }

    public void setValidCols(int[] validCols) {
        this.validCols = validCols;
    }

    public int[] getValidRows() {
        if (this.validRows == null) {
            this.validRows = new int[this.maxRow - this.minRow + 1];
            int index = 0;
            int i = this.minRow;
            while (i <= this.maxRow) {
                this.validRows[index] = i++;
                ++index;
            }
        }
        return this.validRows;
    }

    public void setValidRows(int[] validRows) {
        this.validRows = validRows;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.reportKey == null ? 0 : this.reportKey.hashCode());
        result = 31 * result + (this.reportSolution == null ? 0 : this.reportSolution.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        ReportInfo other = (ReportInfo)obj;
        if (this.reportKey == null ? other.reportKey != null : !this.reportKey.equals(other.reportKey)) {
            return false;
        }
        return !(this.reportSolution == null ? other.reportSolution != null : !this.reportSolution.equals(other.reportSolution));
    }

    public String toString() {
        if (StringUtils.isNotEmpty((String)this.reportSolution)) {
            return this.reportSolution + ":" + this.reportName;
        }
        return this.reportName;
    }

    public String getLinkAlias() {
        return this.linkAlias;
    }

    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    public PeriodModifier getPeriodModifier() {
        return this.periodModifier;
    }

    public void setPeriodModifier(PeriodModifier periodModifier) {
        this.periodModifier = periodModifier;
    }
}

