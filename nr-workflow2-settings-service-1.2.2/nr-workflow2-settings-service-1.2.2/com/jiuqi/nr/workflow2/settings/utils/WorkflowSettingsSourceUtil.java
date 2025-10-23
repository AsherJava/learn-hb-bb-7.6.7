/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityDefineAssist
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.workflow2.settings.utils;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityDefineAssist;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.workflow2.settings.vo.source.FormulaSchemeSource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class WorkflowSettingsSourceUtil {
    private static final String KEY_OF_CODE = "code";
    private static final String KEY_OF_TITLE = "title";
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDefineAssist entityDefineAssist;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;

    public List<FormulaSchemeSource> buildFormulaSchemeSource(String taskKey) {
        ArrayList<FormulaSchemeSource> formulaSchemeSources = new ArrayList<FormulaSchemeSource>();
        try {
            List formSchemeDefines = this.designTimeViewController.listFormSchemeByTask(taskKey);
            for (DesignFormSchemeDefine formSchemeDefine : formSchemeDefines) {
                FormulaSchemeSource source = new FormulaSchemeSource();
                source.setFormSchemeKey(formSchemeDefine.getKey());
                source.setFormSchemeTitle(formSchemeDefine.getTitle());
                ArrayList<Map<String, Object>> formulaSchemes = new ArrayList<Map<String, Object>>();
                List formulaSchemeDefines = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeDefine.getKey());
                for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put(KEY_OF_CODE, formulaSchemeDefine.getKey());
                    data.put(KEY_OF_TITLE, formulaSchemeDefine.getTitle());
                    formulaSchemes.add(data);
                }
                source.setFormulaSchemeValues(formulaSchemes);
                formulaSchemeSources.add(source);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return formulaSchemeSources;
    }

    public boolean isContainCurrency(String taskKey) {
        DesignTaskDefine designTaskDefine = this.designTimeViewController.getTask(taskKey);
        boolean openCurrency = this.entityDefineAssist.existCurrencyAttributes(designTaskDefine.getDw());
        if (openCurrency) {
            boolean isDimContainCurrency = false;
            String dims = designTaskDefine.getDims();
            if (StringUtils.hasLength(dims)) {
                isDimContainCurrency = dims.contains("MD_CURRENCY@BASE");
            }
            if (isDimContainCurrency) {
                List dimensions = this.designDataSchemeService.getDataSchemeDimension(designTaskDefine.getDataScheme());
                boolean reportDimension = dimensions.stream().anyMatch(x -> this.isReportDimension((DesignDataDimension)x) && "MD_CURRENCY@BASE".equals(x.getDimKey()));
                return !reportDimension;
            }
        }
        return false;
    }

    private boolean isReportDimension(DesignDataDimension x) {
        return DimensionType.DIMENSION.equals((Object)x.getDimensionType()) && x.getReportDim() != false;
    }

    public List<Map<String, Object>> buildCurrencySource(String taskKey) {
        ArrayList<Map<String, Object>> currencySource = new ArrayList<Map<String, Object>>();
        DesignTaskDefine taskDefine = this.designTimeViewController.getTask(taskKey);
        List entityRefers = this.entityMetaService.getEntityRefer(this.getEntityCaliber((TaskDefine)taskDefine));
        String currencyEntityId = null;
        for (IEntityRefer entityRefer : entityRefers) {
            if (!entityRefer.getOwnField().equals("CURRENCYIDS")) continue;
            currencyEntityId = entityRefer.getReferEntityId();
        }
        if (currencyEntityId != null && !currencyEntityId.isEmpty()) {
            String curPeriod;
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
            IPeriodRow curPeriodRow = periodProvider.getCurPeriod();
            if (curPeriodRow != null) {
                curPeriod = curPeriodRow.getCode();
            } else {
                Optional<IPeriodRow> maxPeriodRow = periodProvider.getPeriodItems().stream().max(Comparator.comparing(IPeriodRow::getCode));
                if (maxPeriodRow.isPresent()) {
                    curPeriod = maxPeriodRow.get().getCode();
                } else {
                    NullPointerException e = new NullPointerException("\u5f53\u524d\u4efb\u52a1\u4f7f\u7528\u7684\u65f6\u671f\u5b9e\u4f53\u6ca1\u6709\u5b9a\u4e49\u4efb\u4f55\u65f6\u671f\u503c\uff01\u8bf7\u68c0\u67e5\u53c2\u6570\u8bbe\u7f6e\uff01");
                    LoggerFactory.getLogger(this.getClass()).error("\u5f53\u524d\u4efb\u52a1\u4f7f\u7528\u7684\u65f6\u671f\u5b9e\u4f53\u6ca1\u6709\u5b9a\u4e49\u4efb\u4f55\u65f6\u671f\u503c\uff01\u8bf7\u68c0\u67e5\u53c2\u6570\u8bbe\u7f6e\uff01", e);
                    throw e;
                }
            }
            IEntityTable entityTable = this.getEntityTable(currencyEntityId, curPeriod, taskDefine.getDateTime());
            List allRows = entityTable.getAllRows();
            for (IEntityRow row : allRows) {
                HashMap<String, String> currencyData = new HashMap<String, String>();
                currencyData.put(KEY_OF_CODE, row.getCode());
                currencyData.put(KEY_OF_TITLE, row.getTitle());
                currencySource.add(currencyData);
            }
        }
        return currencySource;
    }

    public List<Map<String, Object>> buildErrorHandleSource() {
        try {
            List auditTypes = this.auditTypeDefineService.queryAllAuditType();
            ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            for (AuditType auditType : auditTypes) {
                String auditTypeCode = String.valueOf(auditType.getCode());
                HashMap<String, String> auditItem = new HashMap<String, String>();
                auditItem.put(KEY_OF_CODE, auditTypeCode);
                auditItem.put(KEY_OF_TITLE, auditType.getTitle());
                auditItem.put("value", "2");
                result.add(auditItem);
            }
            return result;
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public IEntityTable getEntityTable(String entityId, String period, String periodView) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setPeriodView(periodView);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(entityId));
        entityQuery.setMasterKeys(dimensionValueSet);
        try {
            return entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getEntityCaliber(TaskDefine taskDefine) {
        String contextEntityId = DsContextHolder.getDsContext().getContextEntityId();
        if (StringUtils.hasLength(contextEntityId)) {
            return contextEntityId;
        }
        return taskDefine.getDw();
    }

    public boolean isMultiEntityCaliber(String taskKey) {
        TaskOrgLinkListStream taskOrgLinkListStream = this.runTimeViewController.listTaskOrgLinkStreamByTask(taskKey);
        List taskOrgLinkDefines = taskOrgLinkListStream.auth().getList();
        return taskOrgLinkDefines != null && taskOrgLinkDefines.size() > 1;
    }
}

