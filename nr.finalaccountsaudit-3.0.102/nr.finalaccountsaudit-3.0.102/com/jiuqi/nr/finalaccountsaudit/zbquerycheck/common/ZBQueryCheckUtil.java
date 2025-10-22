/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.publicparam.datasource.entity.NrEntityDataSourceModel
 *  com.jiuqi.bi.publicparam.datasource.period.NrPeriodDataSourceModel
 *  com.jiuqi.bi.quickreport.model.QuickReport
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.zbquery.model.ConditionField
 *  com.jiuqi.nr.zbquery.model.ConditionValues
 *  com.jiuqi.nr.zbquery.model.QueryObjectType
 *  com.jiuqi.nvwa.bap.parameter.extend.NvwaBaseDataSourceModel
 *  com.jiuqi.nvwa.bap.parameter.extend.NvwaOrgDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.quickreport.api.query.option.Option
 *  com.jiuqi.nvwa.quickreport.common.NvwaQuickReportException
 *  com.jiuqi.nvwa.quickreport.service.QuickReportModelService
 *  com.jiuqi.util.StringUtils
 *  io.netty.util.internal.StringUtil
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.finalaccountsaudit.zbquerycheck.common;

import com.jiuqi.bi.publicparam.datasource.entity.NrEntityDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.period.NrPeriodDataSourceModel;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.zbquery.model.ConditionField;
import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nvwa.bap.parameter.extend.NvwaBaseDataSourceModel;
import com.jiuqi.nvwa.bap.parameter.extend.NvwaOrgDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.quickreport.api.query.option.Option;
import com.jiuqi.nvwa.quickreport.common.NvwaQuickReportException;
import com.jiuqi.nvwa.quickreport.service.QuickReportModelService;
import com.jiuqi.util.StringUtils;
import io.netty.util.internal.StringUtil;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZBQueryCheckUtil {
    private static final Logger logger = LoggerFactory.getLogger(ZBQueryCheckUtil.class);
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    EntityQueryHelper entityQueryHelper;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private QuickReportModelService quickReportModelService;
    @Autowired
    private PeriodEngineService periodEngineService;

    public Map<String, DimensionValue> getDimensionValueSet(JtableContext context) {
        Map<String, DimensionValue> dimValueSet = new HashMap<String, DimensionValue>();
        FormSchemeDefine formDefine = null;
        try {
            formDefine = this.runTimeViewController.getFormScheme(context.getFormSchemeKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return dimValueSet;
        }
        String period = ((DimensionValue)context.getDimensionSet().get("DATATIME")).getValue();
        EntityViewDefine entityView = this.entityQueryHelper.getDwEntityView(formDefine.getKey());
        String entityKeys = "";
        try {
            IEntityTable entityTable = this.entityQueryHelper.buildEntityTable(entityView, period, formDefine.getKey(), false);
            for (IEntityRow entityRow : entityTable.getAllRows()) {
                entityKeys = String.format("%S%S%S", entityKeys, ";", entityRow.getEntityKeyData());
            }
            if (StringUtils.isNotEmpty((String)entityKeys)) {
                entityKeys = entityKeys.substring(1, entityKeys.length());
            }
            dimValueSet = this.getDimensionValueSetEx(context.getFormSchemeKey(), entityKeys, period);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return dimValueSet;
        }
        return dimValueSet;
    }

    public ConditionValues dimensionToConditionValues(Map<String, DimensionValue> dimension, List<ConditionField> conditions) {
        ConditionValues conditionValues = new ConditionValues();
        block2: for (ConditionField condition : conditions) {
            if (condition.getObjectType() != QueryObjectType.DIMENSION) continue;
            String fullName = condition.getFullName();
            try {
                IEntityDefine entityDefine = this.getEntityDefine(fullName);
                if (entityDefine != null) {
                    String dimName = this.getMainDimName(entityDefine.getId());
                    if (dimension.get(dimName) == null) continue;
                    conditionValues.putValue(fullName, new String[]{dimension.get(dimName).getValue()});
                    continue;
                }
                throw new NullPointerException();
            }
            catch (Exception e) {
                IPeriodEntity period;
                String timeDimTable;
                List perids = this.periodEntityAdapter.getPeriodEntity();
                Iterator iterator = perids.iterator();
                if (!iterator.hasNext() || !(timeDimTable = BqlTimeDimUtils.getBqlTimeDimTable((String)(period = (IPeriodEntity)iterator.next()).getCode())).equals(fullName)) continue;
                for (String name : dimension.keySet()) {
                    DimensionValue dValue = dimension.get(name);
                    if (PeriodType.fromCode((int)period.getCode().toCharArray()[0]).type() != dValue.getType()) continue;
                    conditionValues.putValue(fullName, new String[]{dValue.getValue()});
                    continue block2;
                }
            }
        }
        return conditionValues;
    }

    public Option dimensionToQuickReportOption(String guid, Map<String, DimensionValue> dimension) {
        Option option;
        block15: {
            option = new Option();
            try {
                QuickReport quickReport = this.quickReportModelService.getQuickReportByGuidOrId(guid);
                List pList = quickReport.getParamModels();
                if (pList == null || pList.size() <= 0) break block15;
                HashMap<String, String[]> paramsValues = new HashMap<String, String[]>();
                block8: for (ParameterModel param : pList) {
                    try {
                        String dimName;
                        IEntityDefine entityDefine;
                        String entityId;
                        NvwaOrgDataSourceModel model;
                        AbstractParameterDataSourceModel dataSourceModel = param.getDatasource();
                        if (dataSourceModel == null) continue;
                        if (dataSourceModel instanceof NvwaOrgDataSourceModel) {
                            model = (NvwaOrgDataSourceModel)dataSourceModel;
                            entityId = model.getOrgType();
                            entityDefine = this.getEntityDefine(entityId);
                            if (entityDefine != null) {
                                dimName = this.getMainDimName(entityDefine.getId());
                                if (dimension.get(dimName) == null) continue;
                                paramsValues.put(param.getName(), new String[]{dimension.get(dimName).getValue()});
                                continue;
                            }
                            throw new NullPointerException();
                        }
                        if (dataSourceModel instanceof NvwaBaseDataSourceModel) {
                            model = (NvwaBaseDataSourceModel)dataSourceModel;
                            String entityViewId = model.getEntityViewId();
                            String dimName2 = this.getMainDimName(entityViewId);
                            if (dimension.get(dimName2) == null) continue;
                            paramsValues.put(param.getName(), new String[]{dimension.get(dimName2).getValue()});
                            continue;
                        }
                        if (dataSourceModel instanceof NrPeriodDataSourceModel) {
                            model = (NrPeriodDataSourceModel)dataSourceModel;
                            for (String name : dimension.keySet()) {
                                DimensionValue dValue = dimension.get(name);
                                if (model.getPeriodType() != dValue.getType()) continue;
                                paramsValues.put(param.getName(), new String[]{dValue.getValue()});
                                continue block8;
                            }
                            continue;
                        }
                        if (!(dataSourceModel instanceof NrEntityDataSourceModel)) continue;
                        model = (NrEntityDataSourceModel)dataSourceModel;
                        entityId = model.getEntityViewId();
                        entityDefine = this.getEntityDefine(entityId);
                        if (entityDefine != null) {
                            dimName = this.getMainDimName(entityDefine.getId());
                            if (dimension.get(dimName) == null) continue;
                            paramsValues.put(param.getName(), new String[]{dimension.get(dimName).getValue()});
                            continue;
                        }
                        throw new NullPointerException();
                    }
                    catch (Exception exception) {
                    }
                }
                Class<?> clazz = option.getClass();
                Field field = clazz.getDeclaredField("paramsValues");
                field.setAccessible(true);
                field.set(option, paramsValues);
            }
            catch (NvwaQuickReportException e) {
                e.printStackTrace();
            }
            catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            catch (SecurityException e) {
                e.printStackTrace();
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return option;
    }

    private Map<String, DimensionValue> getDimensionValueSetEx(String formSchemeKey, String entity, String period) throws Exception {
        HashMap<String, DimensionValue> dimValueSet = new HashMap<String, DimensionValue>();
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        String entitiesKey = taskDefine.getMasterEntitiesKey();
        String[] entitiesKeyArr = entitiesKey.split(";");
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist((com.jiuqi.np.dataengine.executors.ExecutorContext)context);
        String mainDimName = this.getMainDimName(formSchemeKey);
        for (String entityKey : entitiesKeyArr) {
            EntityViewDefine entityView = this.entityQueryHelper.getEntityView(formSchemeKey, entityKey);
            if (this.isPeriodView(entityKey)) {
                String periodDimName = dataAssist.getDimensionName(entityView);
                DimensionValue periodDim = new DimensionValue();
                periodDim.setName(periodDimName);
                periodDim.setType(taskDefine.getPeriodType().type());
                periodDim.setValue(StringUtil.isNullOrEmpty((String)period) ? PeriodUtil.currentPeriod((int)taskDefine.getPeriodType().type(), (int)0).toString() : period);
                dimValueSet.put(periodDimName, periodDim);
                continue;
            }
            String entityDimName = dataAssist.getDimensionName(entityView);
            DimensionValue entityDim = new DimensionValue();
            entityDim.setName(entityDimName);
            entityDim.setType(0);
            if (!StringUtil.isNullOrEmpty((String)entity) && mainDimName.equals(entityDimName)) {
                entityDim.setValue(entity);
            } else {
                entityDim.setValue("");
            }
            dimValueSet.put(entityDimName, entityDim);
        }
        return dimValueSet;
    }

    private boolean isPeriodView(String viewKey) {
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        return periodAdapter.isPeriodEntity(viewKey);
    }

    private String getMainDimName(String formSchemeKey) {
        return this.entityQueryHelper.getMainDimName(formSchemeKey);
    }

    private IEntityDefine getEntityDefine(String entityId) {
        try {
            IEntityDefine viewDefine = this.entityMetaService.queryEntity(entityId);
            if (viewDefine == null) {
                viewDefine = this.entityMetaService.queryEntityByCode(entityId);
            }
            return viewDefine;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}

