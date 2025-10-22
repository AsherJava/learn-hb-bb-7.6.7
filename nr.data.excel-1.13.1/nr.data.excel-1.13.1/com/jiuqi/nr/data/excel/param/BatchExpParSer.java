/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.nr.data.excel.obj.BatchExportOps;
import com.jiuqi.nr.data.excel.obj.ExportOps;
import com.jiuqi.nr.data.excel.param.BatchExpPar;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.io.Serializable;
import java.util.List;

public class BatchExpParSer
implements Serializable {
    private static final long serialVersionUID = -7689517096879562891L;
    private String formSchemeKey;
    private ExportOps ops;
    private List<String> forms;
    private DimensionCollection dimensionCollection;
    private BatchExportOps batchExportOps;
    private String excelRuleName;

    public BatchExpParSer(BatchExpPar batchExpPar) {
        if (batchExpPar.getRule() == null) {
            throw new IllegalArgumentException("ExcelRule must not be null!");
        }
        this.forms = batchExpPar.getForms();
        this.dimensionCollection = batchExpPar.getDimensionCollection();
        this.formSchemeKey = batchExpPar.getFormSchemeKey();
        this.ops = batchExpPar.getOps();
        this.batchExportOps = batchExpPar.getBatchExportOps();
        this.excelRuleName = batchExpPar.getRule().name();
    }

    public String getExcelRuleName() {
        return this.excelRuleName;
    }

    public void setExcelRuleName(String excelRuleName) {
        this.excelRuleName = excelRuleName;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public ExportOps getOps() {
        return this.ops;
    }

    public void setOps(ExportOps ops) {
        this.ops = ops;
    }

    public List<String> getForms() {
        return this.forms;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }

    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    public BatchExportOps getBatchExportOps() {
        return this.batchExportOps;
    }

    public void setBatchExportOps(BatchExportOps batchExportOps) {
        this.batchExportOps = batchExportOps;
    }
}

