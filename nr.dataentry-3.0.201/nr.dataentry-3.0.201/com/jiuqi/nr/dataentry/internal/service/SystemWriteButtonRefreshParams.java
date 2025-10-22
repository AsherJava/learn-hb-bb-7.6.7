/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataentry.gather.IRegisterDataentryRefreshParams;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemWriteButtonRefreshParams
implements IRegisterDataentryRefreshParams {
    private static final Logger logger = LoggerFactory.getLogger(SystemWriteButtonRefreshParams.class);
    @Autowired
    IDataAccessServiceProvider dataAccessServiceProvider;

    @Override
    public IRegisterDataentryRefreshParams.RefreshType getRefreshType() {
        return IRegisterDataentryRefreshParams.RefreshType.FORM_REFRESH;
    }

    @Override
    public String getPramaKey(JtableContext context) {
        return "systemWriteableFormKeys";
    }

    @Override
    public Object getPramaValue(JtableContext context) {
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(context.getTaskKey(), context.getFormSchemeKey());
        DimensionCombination dimensionValueSets = DimensionValueSetUtil.buildDimensionCombination((DimensionValueSet)DimensionValueSetUtil.getDimensionValueSet((Map)context.getDimensionSet()), (String)context.getFormSchemeKey());
        IAccessResult accessResult = dataAccessService.sysWriteable(dimensionValueSets, context.getFormKey());
        try {
            return accessResult.haveAccess();
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}

