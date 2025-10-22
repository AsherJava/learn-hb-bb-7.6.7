/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.encryption.desensitization.common.DesensitizedEncryptor
 */
package com.jiuqi.nr.data.logic.internal.util;

import com.jiuqi.nr.data.logic.internal.helper.CalculateHelper;
import com.jiuqi.nr.data.logic.internal.service.impl.AllCheckResultSaver;
import com.jiuqi.nr.data.logic.internal.service.impl.BatchCheckResultSaver;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.encryption.desensitization.common.DesensitizedEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanHelper {
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IFMDMAttributeService fMDMAttributeService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DesensitizedEncryptor encryptor;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private AllCheckResultSaver allCheckResultSaver;
    @Autowired
    private BatchCheckResultSaver batchCheckResultSaver;
    @Autowired
    private CalculateHelper calculateHelper;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;

    public EntityUtil getEntityUtil() {
        return this.entityUtil;
    }

    public IRuntimeDataSchemeService getDataSchemeService() {
        return this.dataSchemeService;
    }

    public IFMDMAttributeService getfMDMAttributeService() {
        return this.fMDMAttributeService;
    }

    public IRunTimeViewController getRuntimeViewController() {
        return this.runtimeViewController;
    }

    public DataModelService getDataModelService() {
        return this.dataModelService;
    }

    public DesensitizedEncryptor getEncryptor() {
        return this.encryptor;
    }

    public IEntityMetaService getEntityMetaService() {
        return this.entityMetaService;
    }

    public AllCheckResultSaver getAllCheckResultSaver() {
        return this.allCheckResultSaver;
    }

    public BatchCheckResultSaver getBatchCheckResultSaver() {
        return this.batchCheckResultSaver;
    }

    public CalculateHelper getCalculateHelper() {
        return this.calculateHelper;
    }

    public IPeriodEntityAdapter getPeriodEntityAdapter() {
        return this.periodEntityAdapter;
    }
}

