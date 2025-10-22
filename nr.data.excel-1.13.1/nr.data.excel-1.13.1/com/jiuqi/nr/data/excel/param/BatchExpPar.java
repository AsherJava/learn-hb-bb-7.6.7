/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.nr.data.excel.export.ExpExtendBeanCollector;
import com.jiuqi.nr.data.excel.obj.BatchExportOps;
import com.jiuqi.nr.data.excel.param.BaseExpPar;
import com.jiuqi.nr.data.excel.param.BatchExpParSer;
import com.jiuqi.nr.data.excel.param.ExcelRule;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.List;

public class BatchExpPar
extends BaseExpPar {
    private List<String> forms;
    private DimensionCollection dimensionCollection;
    private ExcelRule rule;
    private BatchExportOps batchExportOps;

    public BatchExpPar() {
    }

    public BatchExpPar(BatchExpParSer batchExpParSer) {
        this.rule = ((ExpExtendBeanCollector)BeanUtil.getBean(ExpExtendBeanCollector.class)).getExcelRuleByName(batchExpParSer.getExcelRuleName());
        this.forms = batchExpParSer.getForms();
        this.dimensionCollection = batchExpParSer.getDimensionCollection();
        this.batchExportOps = batchExpParSer.getBatchExportOps();
        this.setFormSchemeKey(batchExpParSer.getFormSchemeKey());
        this.setOps(batchExpParSer.getOps());
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

    public ExcelRule getRule() {
        return this.rule;
    }

    public void setRule(ExcelRule rule) {
        this.rule = rule;
    }

    public BatchExportOps getBatchExportOps() {
        return this.batchExportOps;
    }

    public void setBatchExportOps(BatchExportOps batchExportOps) {
        this.batchExportOps = batchExportOps;
    }
}

