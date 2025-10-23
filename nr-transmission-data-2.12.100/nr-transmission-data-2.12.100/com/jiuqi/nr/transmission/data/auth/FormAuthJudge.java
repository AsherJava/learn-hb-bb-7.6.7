/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider
 *  com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams
 *  com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager
 *  com.jiuqi.nr.dataentry.util.Consts$FormAccessLevel
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.nr.transmission.data.auth;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.transmission.data.auth.IFormAuthJudge;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import java.util.ArrayList;
import java.util.List;

public class FormAuthJudge
implements IFormAuthJudge {
    private final SyncSchemeParamDTO syncSchemeParamDTO;
    private ReadWriteAccessCacheManager readWriteAccessCacheManager;
    private boolean hasInit;

    public FormAuthJudge(SyncSchemeParamDTO syncSchemeParamDTO) {
        this.syncSchemeParamDTO = syncSchemeParamDTO;
    }

    @Override
    public List<String> judge(DimensionValueSet dimensionValueSet) {
        this.authFilter();
        JtableContext contextQuery = new JtableContext();
        contextQuery.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet));
        contextQuery.setTaskKey(this.syncSchemeParamDTO.getTask());
        contextQuery.setFormSchemeKey(this.syncSchemeParamDTO.getFormSchemeKey());
        FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData(contextQuery, Consts.FormAccessLevel.FORM_DATA_WRITE);
        ReadWriteAccessProvider readWriteAccessProvider = (ReadWriteAccessProvider)BeanUtil.getBean(ReadWriteAccessProvider.class);
        FormReadWriteAccessData accessForms = readWriteAccessProvider.getAccessForms(formReadWriteAccessData, this.readWriteAccessCacheManager);
        return accessForms.getFormKeys();
    }

    private void authFilter() {
        if (this.hasInit) {
            return;
        }
        JtableContext jtableContext = new JtableContext();
        jtableContext.setTaskKey(this.syncSchemeParamDTO.getTask());
        jtableContext.setFormSchemeKey(this.syncSchemeParamDTO.getFormSchemeKey());
        jtableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)this.syncSchemeParamDTO.getDimensionValueSet()));
        ArrayList formKeys = new ArrayList();
        ReadWriteAccessCacheParams readWriteAccessCacheParams = new ReadWriteAccessCacheParams(jtableContext, formKeys, Consts.FormAccessLevel.FORM_DATA_WRITE);
        this.readWriteAccessCacheManager = (ReadWriteAccessCacheManager)BeanUtil.getBean(ReadWriteAccessCacheManager.class);
        this.readWriteAccessCacheManager.initCache(readWriteAccessCacheParams);
    }
}

