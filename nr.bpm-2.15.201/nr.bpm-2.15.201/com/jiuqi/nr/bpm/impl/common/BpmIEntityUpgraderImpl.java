/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.impl.common;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.service.BpmIEntityUpgrader;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.util.StringUtils;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BpmIEntityUpgraderImpl
implements BpmIEntityUpgrader {
    private static final Logger logger = LoggerFactory.getLogger(BpmIEntityUpgraderImpl.class);
    @Autowired
    private IDataDefinitionRuntimeController dataRunTimeController;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private IEntityDataService entityDataService;
    @Resource
    private IRunTimeViewController controller;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IFormSchemeService formSchemeService;

    @Override
    public String queryParentEntityKey(String entityKey, EntityViewDefine entityView, BusinessKeyInfo businessKey) {
        DimensionValueSet masterKeys = new DimensionValueSet();
        masterKeys.setValue("DATATIME", (Object)businessKey.getPeriod());
        IEntityQuery entityQuery = this.buildQuery(entityView, masterKeys, businessKey.getFormSchemeKey());
        IEntityTable entityTable = null;
        ExecutorContext context = new ExecutorContext(this.dataRunTimeController);
        context.setVarDimensionValueSet(masterKeys);
        try {
            context.setVarDimensionValueSet(masterKeys);
            entityTable = entityQuery.executeReader((IContext)context);
        }
        catch (Exception e) {
            throw new BpmException(e);
        }
        IEntityRow targetRow = entityTable.findByEntityKey(entityKey);
        if (targetRow == null) {
            return null;
        }
        String parentEntityKey = targetRow.getParentEntityKey();
        return parentEntityKey;
    }

    @Override
    public String[] queryParentEntityKeys(String entityKey, EntityViewDefine entityView, BusinessKeyInfo businessKey) {
        DimensionValueSet masterKeys = new DimensionValueSet();
        masterKeys.setValue("DATATIME", (Object)businessKey.getPeriod());
        IEntityQuery entityQuery = this.buildQuery(entityView, masterKeys, businessKey.getFormSchemeKey());
        entityQuery.setAuthorityOperations(AuthorityType.None);
        IEntityTable entityTable = null;
        ExecutorContext context = new ExecutorContext(this.dataRunTimeController);
        context.setVarDimensionValueSet(masterKeys);
        try {
            context.setVarDimensionValueSet(masterKeys);
            entityTable = entityQuery.executeReader((IContext)context);
        }
        catch (Exception e) {
            throw new BpmException(e);
        }
        IEntityRow targetRow = entityTable.findByEntityKey(entityKey);
        if (targetRow == null) {
            return null;
        }
        String[] parentsEntityKey = targetRow.getParentsEntityKeyDataPath();
        return parentsEntityKey;
    }

    public IEntityQuery buildQuery(EntityViewDefine view, DimensionValueSet valueSet, String formSchemeKey) {
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setMasterKeys(valueSet);
        query.setAuthorityOperations(AuthorityType.Read);
        EntityViewDefine entityView = this.controller.getViewByFormSchemeKey(formSchemeKey);
        query.setEntityView(entityView);
        return query;
    }

    @Override
    public String queryEntityView(BusinessKeyInfo businessKey) {
        String formSchemeKey = businessKey.getFormSchemeKey();
        FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
        return this.getContextMainDimId(formScheme.getDw());
    }

    @Override
    public String queryEntityView(BusinessKey businessKey) {
        String formSchemeKey = businessKey.getFormSchemeKey();
        FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
        return this.getContextMainDimId(formScheme.getDw());
    }

    public String getContextMainDimId(String dw) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        return StringUtils.isEmpty((String)entityId) ? dw : entityId;
    }
}

