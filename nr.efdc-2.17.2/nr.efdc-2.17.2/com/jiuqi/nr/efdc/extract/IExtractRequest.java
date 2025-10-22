/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.nr.efdc.extract;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.efdc.extract.ExtractDataRegion;
import com.jiuqi.nr.efdc.extract.IExtractDataUpdator;
import com.jiuqi.nr.efdc.extract.exception.ExtractException;
import java.util.List;
import java.util.Map;

public interface IExtractRequest {
    public FormulaSchemeDefine getFormulaSchemeDefine();

    public FormDefine getCurrentReport();

    public List<ExtractDataRegion> doPrepare(ExecutorContext var1, Map<String, Object> var2, IFormulaRunTimeController var3) throws ExtractException;

    public void doExtract(ExecutorContext var1, IDataAccessProvider var2, DimensionValueSet var3, Map<String, Object> var4, IExtractDataUpdator var5) throws ExtractException;
}

