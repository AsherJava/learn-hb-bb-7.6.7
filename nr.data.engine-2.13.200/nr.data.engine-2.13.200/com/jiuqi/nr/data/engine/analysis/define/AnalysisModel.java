/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 */
package com.jiuqi.nr.data.engine.analysis.define;

import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.nr.data.engine.analysis.define.AnalysisCaliber;
import com.jiuqi.nr.data.engine.analysis.define.FloatRegionConfig;
import com.jiuqi.nr.data.engine.util.Consts;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysisModel {
    private String reportName;
    private List<String> formulas = new ArrayList<String>();
    private List<AnalysisCaliber> rowCalibers = new ArrayList<AnalysisCaliber>();
    private List<AnalysisCaliber> colCalibers = new ArrayList<AnalysisCaliber>();
    private Map<String, FloatRegionConfig> regionConfigMap = new HashMap<String, FloatRegionConfig>();
    private Map<Position, Consts.CellCaliberType> cellCaliberTypes = new HashMap<Position, Consts.CellCaliberType>();
    private String globalFilter;

    public List<String> getFormulas() {
        return this.formulas;
    }

    public List<AnalysisCaliber> getRowCalibers() {
        return this.rowCalibers;
    }

    public List<AnalysisCaliber> getColCalibers() {
        return this.colCalibers;
    }

    public Map<String, FloatRegionConfig> getRegionConfigMap() {
        return this.regionConfigMap;
    }

    public String getGlobalFilter() {
        return this.globalFilter;
    }

    public void setGlobalFilter(String globalFilter) {
        this.globalFilter = globalFilter;
    }

    public String getReportName() {
        return this.reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Map<Position, Consts.CellCaliberType> getCellCaliberTypes() {
        return this.cellCaliberTypes;
    }

    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("AnalysisModel_").append(this.reportName).append(":\n");
        buff.append("formulas[\n");
        for (String formula : this.formulas) {
            buff.append(formula).append("\n");
        }
        buff.append("]\n");
        if (this.rowCalibers.size() > 0) {
            buff.append("rowCalibers[\n");
            for (AnalysisCaliber caliber : this.rowCalibers) {
                buff.append(caliber).append("\n");
            }
            buff.append("]\n");
        }
        if (this.colCalibers.size() > 0) {
            buff.append("colCalibers[\n");
            for (AnalysisCaliber caliber : this.colCalibers) {
                buff.append(caliber).append("\n");
            }
            buff.append("]\n");
        }
        if (this.regionConfigMap.size() > 0) {
            buff.append("RegionConfig[\n");
            for (FloatRegionConfig config : this.regionConfigMap.values()) {
                buff.append("region_").append(config.getRegionCode()).append(":").append(config).append("\n");
            }
            buff.append("]\n");
        }
        buff.append("globalFilter=").append(this.globalFilter).append("\n");
        return buff.toString();
    }
}

