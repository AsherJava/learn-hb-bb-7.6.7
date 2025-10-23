/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 */
package com.jiuqi.nr.summary.executor.deploy.comparator;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.summary.executor.deploy.DeployContext;
import com.jiuqi.nr.summary.executor.deploy.comparator.TemporaryCell;
import com.jiuqi.nr.summary.model.report.SummaryFloatRegion;
import com.jiuqi.nr.summary.model.report.SummaryReport;
import com.jiuqi.nr.summary.utils.ParamComparator;
import com.jiuqi.nr.summary.utils.SummaryReportModelHelper;

public class ComparatorHolder {
    private static ParamComparator<SummaryReport, SummaryReport> summaryReportComparator = null;
    private static ParamComparator<SummaryFloatRegion, DesignDataTable> floatRegionComparator = null;
    private static ParamComparator<TemporaryCell, DesignDataField> cellComparator = null;

    public static ParamComparator<SummaryReport, SummaryReport> getSummaryReportComparator() {
        if (summaryReportComparator == null) {
            summaryReportComparator = new SummaryReportComparator();
        }
        return summaryReportComparator;
    }

    public static ParamComparator<SummaryFloatRegion, DesignDataTable> getFloatRegionComparator(DeployContext deployContext) {
        return new FloatRegionComparator(deployContext);
    }

    public static ParamComparator<TemporaryCell, DesignDataField> getCellComparator() {
        if (cellComparator == null) {
            cellComparator = new CellComparator();
        }
        return cellComparator;
    }

    static class CellComparator
    extends ParamComparator<TemporaryCell, DesignDataField> {
        CellComparator() {
        }

        @Override
        protected String getFirstValueKey(TemporaryCell cell) {
            return cell.code;
        }

        @Override
        protected String getSecondValueKey(DesignDataField dataField) {
            return dataField.getCode();
        }

        @Override
        protected long getFirstValueTime(TemporaryCell cell) {
            return cell.modityTime;
        }

        @Override
        protected long getSecondValueTime(DesignDataField dataField) {
            return dataField.getUpdateTime().toEpochMilli();
        }
    }

    static class FloatRegionComparator
    extends ParamComparator<SummaryFloatRegion, DesignDataTable> {
        private final SummaryReportModelHelper reportModelHelper;
        private final DeployContext deployContext;

        FloatRegionComparator(DeployContext deployContext) {
            this.reportModelHelper = new SummaryReportModelHelper(deployContext.getDesignReportModel());
            this.deployContext = deployContext;
        }

        @Override
        protected String getFirstValueKey(SummaryFloatRegion floatRegion) {
            return this.reportModelHelper.generateFloatTableCode(floatRegion);
        }

        @Override
        protected String getSecondValueKey(DesignDataTable dataTable) {
            return dataTable.getCode();
        }

        @Override
        protected long getFirstValueTime(SummaryFloatRegion data) {
            return this.deployContext.getCurrReport().getModifyTime().getTime();
        }

        @Override
        protected long getSecondValueTime(DesignDataTable data) {
            return data.getUpdateTime().toEpochMilli();
        }
    }

    static class SummaryReportComparator
    extends ParamComparator<SummaryReport, SummaryReport> {
        SummaryReportComparator() {
        }

        @Override
        protected String getFirstValueKey(SummaryReport data) {
            return data.getKey();
        }

        @Override
        protected String getSecondValueKey(SummaryReport data) {
            return data.getKey();
        }

        @Override
        protected long getFirstValueTime(SummaryReport data) {
            return data.getModifyTime().getTime();
        }

        @Override
        protected long getSecondValueTime(SummaryReport data) {
            return data.getModifyTime().getTime();
        }
    }
}

