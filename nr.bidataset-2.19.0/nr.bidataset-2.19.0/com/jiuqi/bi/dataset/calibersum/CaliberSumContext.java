/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.bi.dataset.calibersum;

import com.jiuqi.bi.dataset.calibersum.CaliberSumDSProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaliberSumContext
implements IContext {
    private static final Logger logger = LoggerFactory.getLogger(CaliberSumDSProvider.class);
    private ExecutorContext executorContext;
    private FormSchemeDefine formScheme;
    private String formulaSchemeKey;
    private String period;
    private DimensionValueSet destMasterKeys;

    public CaliberSumContext(ExecutorContext executorContext) {
        this.executorContext = executorContext;
    }

    public String getFormSchemeCode() {
        return this.formScheme.getFormSchemeCode();
    }

    public String getUnitDim() {
        return this.executorContext.getUnitDimension();
    }

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }

    public FormSchemeDefine getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(FormSchemeDefine formScheme) {
        this.formScheme = formScheme;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public DimensionValueSet getDestMasterKeys() {
        return this.destMasterKeys;
    }

    public void setDestMasterKeys(DimensionValueSet destMasterKeys) {
        this.destMasterKeys = destMasterKeys;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public Logger getLogger() {
        return logger;
    }
}

