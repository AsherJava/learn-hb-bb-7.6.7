/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeImpl
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.batch.summary.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.batch.summary.service.targetform.BSFormClearTableInfo;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeImpl;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.List;

public interface BSSFormClearService {
    public void clearUselessData(SummaryScheme var1, SummarySchemeImpl var2) throws Exception;

    public void doClearFixForm(BSFormClearTableInfo var1, DimensionValueSet var2) throws Exception;

    public void doClearFloatForm(BSFormClearTableInfo var1, DimensionValueSet var2) throws Exception;

    public List<BSFormClearTableInfo> getTableModelDefines(List<FormDefine> var1, String var2, String var3, String var4);
}

