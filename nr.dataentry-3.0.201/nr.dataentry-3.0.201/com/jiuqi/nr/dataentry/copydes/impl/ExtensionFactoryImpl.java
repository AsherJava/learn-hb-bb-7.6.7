/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.dataentry.copydes.impl;

import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataentry.copydes.IExtensionFactory;
import com.jiuqi.nr.dataentry.copydes.IUnsupportedDesHandler;
import com.jiuqi.nr.dataentry.copydes.impl.UnsupportedHandler;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExtensionFactoryImpl
implements IExtensionFactory {
    @Autowired
    private ICheckErrorDescriptionService checkErrorDescriptionService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;

    @Override
    public IUnsupportedDesHandler getUnsupportedDesHandler(Map<String, String> bizKeyOrderMap) {
        return new UnsupportedHandler(this, bizKeyOrderMap);
    }

    public ICheckErrorDescriptionService getCheckErrorDescriptionService() {
        return this.checkErrorDescriptionService;
    }

    public IRunTimeViewController getRunTimeViewController() {
        return this.runTimeViewController;
    }

    public IFormSchemeService getFormSchemeService() {
        return this.formSchemeService;
    }

    public IEntityMetaService getEntityMetaService() {
        return this.entityMetaService;
    }

    public PeriodEngineService getPeriodEngineService() {
        return this.periodEngineService;
    }

    public DimensionCollectionUtil getDimensionCollectionUtil() {
        return this.dimensionCollectionUtil;
    }
}

