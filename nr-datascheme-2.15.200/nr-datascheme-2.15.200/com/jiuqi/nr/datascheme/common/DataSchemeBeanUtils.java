/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.datascheme.common;

import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;

public class DataSchemeBeanUtils {
    private DataSchemeBeanUtils() {
    }

    public static IDataSchemeAuthService getDataSchemeAuthService() {
        return (IDataSchemeAuthService)BeanUtil.getBean(IDataSchemeAuthService.class);
    }

    public static IRuntimeDataSchemeService getRuntimeDataSchemeService() {
        return (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
    }

    public static IDesignDataSchemeService getDesignDataSchemeService() {
        return (IDesignDataSchemeService)BeanUtil.getBean(IDesignDataSchemeService.class);
    }

    public static IEntityMetaService getEntityMetaService() {
        return (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
    }

    public static PeriodEngineService getPeriodEngineService() {
        return (PeriodEngineService)BeanUtil.getBean(PeriodEngineService.class);
    }
}

