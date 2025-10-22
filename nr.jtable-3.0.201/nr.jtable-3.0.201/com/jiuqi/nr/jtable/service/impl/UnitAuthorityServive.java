/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.jtable.common.BatchSummaryConst;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IUnitAuthorityServive;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.util.StringUtils;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitAuthorityServive
implements IUnitAuthorityServive {
    private static final Logger logger = LoggerFactory.getLogger(UnitAuthorityServive.class);
    private static final String accessCode = "dataentry";
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthorityProvider;

    @Override
    public String getAccessCode() {
        return accessCode;
    }

    @Deprecated
    public boolean canWrite(JtableContext context) {
        String formSchemeKey = context.getFormSchemeKey();
        Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
        if (formSchemeKey != null && dimensionSet != null) {
            Date time;
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            String mainDimName = this.getMainDimName(formSchemeKey);
            DimensionValue dimensionValue = dimensionSet.get(mainDimName);
            DimensionValue periodDimensionValue = dimensionSet.get("DATATIME");
            String entityKeyData = dimensionValue.getValue();
            if (StringUtils.isEmpty((String)entityKeyData) || "00000000-0000-0000-0000-000000000000".equals(entityKeyData)) {
                return true;
            }
            boolean contains = entityKeyData.contains(";");
            if (contains) {
                return true;
            }
            try {
                time = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime()).getPeriodDateRegion(periodDimensionValue.getValue())[1];
            }
            catch (Exception e) {
                GregorianCalendar period2Calendar = PeriodUtil.period2Calendar((String)periodDimensionValue.getValue());
                time = period2Calendar.getTime();
            }
            try {
                String entityId = StringUtils.isNotEmpty((String)DsContextHolder.getDsContext().getContextEntityId()) ? DsContextHolder.getDsContext().getContextEntityId() : formScheme.getDw();
                boolean canWriteEntity = this.entityAuthorityService.canWriteEntity(entityId, entityKeyData, null, time);
                return canWriteEntity;
            }
            catch (UnauthorizedEntityException e) {
                logger.error("\u6743\u9650\u4e0d\u7b26\u5408" + (Object)((Object)e));
            }
        }
        return true;
    }

    private String getMainDimName(String formSchemeKey) {
        EntityViewDefine entityViewDefine = this.runTimeViewController.getViewByFormSchemeKey(formSchemeKey);
        com.jiuqi.nr.entity.engine.executors.ExecutorContext context = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(this.iDataDefinitionRuntimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist((ExecutorContext)context);
        return dataAssist.getDimensionName(entityViewDefine);
    }

    @Override
    public boolean canRead(JtableContext context) {
        boolean canReadTask;
        if (context.getTaskKey() != null && !(canReadTask = this.definitionAuthorityProvider.canReadTask(context.getTaskKey()))) {
            return false;
        }
        if (BatchSummaryConst.isBatchSummaryEntry(context.getVariableMap())) {
            return true;
        }
        String formSchemeKey = context.getFormSchemeKey();
        Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
        if (context.getFormSchemeKey() != null && dimensionSet != null) {
            Date time;
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            String mainDimName = this.getMainDimName(formSchemeKey);
            DimensionValue dimensionValue = dimensionSet.get(mainDimName);
            DimensionValue periodDimensionValue = dimensionSet.get("DATATIME");
            String entityKeyData = dimensionValue.getValue();
            if (StringUtils.isEmpty((String)entityKeyData) || "00000000-0000-0000-0000-000000000000".equals(entityKeyData)) {
                return true;
            }
            boolean contains = entityKeyData.contains(";");
            if (contains) {
                return true;
            }
            try {
                time = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime()).getPeriodDateRegion(periodDimensionValue.getValue())[1];
            }
            catch (Exception e) {
                GregorianCalendar period2Calendar = PeriodUtil.period2Calendar((String)periodDimensionValue.getValue());
                time = period2Calendar.getTime();
            }
            try {
                String entityId = StringUtils.isNotEmpty((String)DsContextHolder.getDsContext().getContextEntityId()) ? DsContextHolder.getDsContext().getContextEntityId() : formScheme.getDw();
                boolean canReadEntity = this.entityAuthorityService.canReadEntity(entityId, entityKeyData, null, time);
                return canReadEntity;
            }
            catch (UnauthorizedEntityException e) {
                logger.error("\u6743\u9650\u4e0d\u7b26\u5408" + (Object)((Object)e));
            }
        }
        return true;
    }
}

