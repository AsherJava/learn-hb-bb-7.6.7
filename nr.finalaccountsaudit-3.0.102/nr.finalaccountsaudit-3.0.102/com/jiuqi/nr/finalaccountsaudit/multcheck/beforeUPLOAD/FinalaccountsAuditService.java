/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.google.gson.internal.LinkedTreeMap
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam
 *  com.jiuqi.nr.dataentry.bean.ExecuteTaskParam
 *  com.jiuqi.nr.dataentry.monitor.DataEntryAsyncProgressMonitor
 *  com.jiuqi.nr.dataentry.service.IFinalaccountsAuditService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.unit.treestore.enumeration.NamedParameterSqlBuilder
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.beforeUPLOAD;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.internal.LinkedTreeMap;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.ExecuteTaskParam;
import com.jiuqi.nr.dataentry.monitor.DataEntryAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.service.IFinalaccountsAuditService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.finalaccountsaudit.multcheck.beforeUPLOAD.FinalaccountsAuditCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.beforeUPLOAD.FinalaccountsAuditConfig;
import com.jiuqi.nr.finalaccountsaudit.multcheck.beforeUPLOAD.FinalaccountsAuditExtraInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.beforeUPLOAD.FinalaccountsAuditExtraInfoParam;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.MultCheckItem;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.MultCheckResultItem;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.OneKeyCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.service.IMultCheckTableService;
import com.jiuqi.nr.finalaccountsaudit.multcheck.controller.IBeforUploadMultCheckController;
import com.jiuqi.nr.finalaccountsaudit.multcheck.controller.IMultCheckController;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.unit.treestore.enumeration.NamedParameterSqlBuilder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

@Service
public class FinalaccountsAuditService
implements IFinalaccountsAuditService {
    private static final Logger logger = LoggerFactory.getLogger(FinalaccountsAuditService.class);
    @Autowired
    IMultCheckController multCheckController;
    @Autowired
    IJtableParamService jtableParamService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IMultCheckTableService iMultCheckTableService;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AsyncThreadExecutor asyncThreadExecutor;
    @Autowired
    private IBeforUploadMultCheckController beforUploadMultCheckController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Resource
    IJtableEntityService jtableEntityService;
    private static final String TABLE_NAME = "NR_MULTCHECK_BEFORE_BATCH_UPLOAD";
    private static final String[] columns = new String[]{"MBBU_TASK", "MBBU_CHECKITEM", "MBBU_UNIT"};

    public String comprehensiveAudit(ExecuteTaskParam param, AsyncTaskMonitor asyncTaskMonitor) {
        JtableContext context = new JtableContext(param.getContext());
        OneKeyCheckInfo oneKeyCheckInfo = this.createParam(context);
        ArrayList<MultCheckItem> allItemList = new ArrayList();
        try {
            OneKeyCheckInfo oneKeyCheckParam = new OneKeyCheckInfo();
            oneKeyCheckParam.setContext(context);
            allItemList = this.multCheckController.queryAllItemList(oneKeyCheckParam);
            oneKeyCheckInfo.setCheckItems(allItemList);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5ba1\u6838\u9879\u62a5\u9519", e);
            throw new RuntimeException(e.getMessage());
        }
        try {
            return this.execute(oneKeyCheckInfo, context, asyncTaskMonitor);
        }
        catch (Exception e) {
            logger.error("\u6267\u884c\u7efc\u5408\u5ba1\u6838\u62a5\u9519", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public String bathComprehensiveAudit(BatchExecuteTaskParam param, AsyncTaskMonitor asyncTaskMonitor) {
        JtableContext context = new JtableContext(param.getContext());
        OneKeyCheckInfo oneKeyCheckInfo = this.createParam(context);
        ArrayList<MultCheckItem> allItemList = new ArrayList();
        try {
            OneKeyCheckInfo oneKeyCheckParam = new OneKeyCheckInfo();
            oneKeyCheckParam.setContext(context);
            oneKeyCheckParam.setBeforeUpload(true);
            List<Map<String, Object>> schemeList = this.iMultCheckTableService.getSchemeList(context.getFormSchemeKey());
            if (schemeList.size() > 0) {
                List scheme = schemeList.stream().filter(e -> e.get("s_key").toString().startsWith("defaultScheme")).collect(Collectors.toList());
                if (scheme.size() > 0) {
                    oneKeyCheckParam.setCheckSchemeKey(((Map)scheme.get(0)).get("s_key").toString());
                } else {
                    oneKeyCheckParam.setCheckSchemeKey("defaultScheme");
                }
            } else {
                oneKeyCheckParam.setCheckSchemeKey("defaultScheme");
            }
            allItemList = this.multCheckController.queryAllItemList(oneKeyCheckParam);
            oneKeyCheckInfo.setCheckItems(allItemList);
        }
        catch (Exception e2) {
            logger.error("\u67e5\u8be2\u5ba1\u6838\u9879\u62a5\u9519", e2);
            throw new RuntimeException(e2.getMessage());
        }
        try {
            HashMap dimensionSet = new HashMap(context.getDimensionSet());
            EntityViewData entityViewData = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
            String dw = ((DimensionValue)dimensionSet.get(entityViewData.getDimensionName())).getValue();
            ArrayList<String> dwList = new ArrayList();
            IEntityDataService entityDataService = (IEntityDataService)SpringBeanUtils.getBean(IEntityDataService.class);
            IEntityViewRunTimeController runTimeController = (IEntityViewRunTimeController)SpringBeanUtils.getBean(IEntityViewRunTimeController.class);
            IDataDefinitionRuntimeController definitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
            EntityViewDefine entityViewDefine = runTimeController.buildEntityView(entityViewData.getKey());
            IEntityQuery entityQuery = entityDataService.newEntityQuery();
            entityQuery.setEntityView(entityViewDefine);
            ExecutorContext executorContext = new ExecutorContext(definitionRuntimeController);
            TaskDefine taskDefine = this.viewController.queryTaskDefine(context.getTaskKey());
            executorContext.setPeriodView(taskDefine.getDateTime());
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue("DATATIME", (Object)((DimensionValue)dimensionSet.get("DATATIME")).getValue());
            if (dw.equals("")) {
                entityQuery.setMasterKeys(dimensionValueSet);
                IEntityTable iEntityTable = entityQuery.executeReader((IContext)executorContext);
                List allRows = iEntityTable.getAllRows();
                StringBuffer sb = new StringBuffer();
                for (IEntityRow iEntityRow : allRows) {
                    sb.append(iEntityRow.getCode()).append(";");
                    dwList.add(iEntityRow.getCode());
                }
                if (sb.length() > 0) {
                    dw = sb.substring(0, sb.length() - 1);
                }
                ((DimensionValue)dimensionSet.get(entityViewData.getDimensionName())).setValue(dw);
            } else {
                dwList = Arrays.asList(dw.split(";"));
            }
            this.fitlerDW(context.getTaskKey(), dwList, oneKeyCheckInfo, ((DimensionValue)dimensionSet.get(entityViewData.getDimensionName())).getValue().equals(""));
            String scop = this.findDW(dwList, (String)dwList.get(0), entityQuery, entityViewData.getDimensionName(), dimensionValueSet, executorContext);
            if (scop != null) {
                for (MultCheckItem multCheckItem : oneKeyCheckInfo.getCheckItems()) {
                    if (!multCheckItem.getCheckType().equals("entityCheck")) continue;
                    ((LinkedTreeMap)multCheckItem.getItemSetting()).put((Object)"scop", (Object)scop);
                }
            }
            return this.execute(oneKeyCheckInfo, context, asyncTaskMonitor);
        }
        catch (Exception e3) {
            logger.error("\u6267\u884c\u7efc\u5408\u5ba1\u6838\u62a5\u9519", e3);
            throw new RuntimeException(e3.getMessage());
        }
    }

    private String findDW(List<String> dwList, String dw, IEntityQuery entityQuery, String dimensionName, DimensionValueSet dimensionValueSet, ExecutorContext executorContext) throws Exception {
        dimensionValueSet.setValue(dimensionName, (Object)dw);
        entityQuery.setMasterKeys(dimensionValueSet);
        IEntityTable iEntityTable = entityQuery.executeReader((IContext)executorContext);
        List allRows = iEntityTable.getAllRows();
        String code = ((IEntityRow)allRows.get(0)).getParentEntityKey();
        if (dwList.contains(code)) {
            return this.findDW(dwList, code, entityQuery, dimensionName, dimensionValueSet, executorContext);
        }
        return dw;
    }

    private OneKeyCheckInfo createParam(JtableContext context) {
        OneKeyCheckInfo oneKeyCheckInfo = new OneKeyCheckInfo();
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>(context.getDimensionSet());
        oneKeyCheckInfo.setBeforeUpload(true);
        oneKeyCheckInfo.setContext(context);
        oneKeyCheckInfo.setSelectedDimensionSet(dimensionSet);
        return oneKeyCheckInfo;
    }

    private String execute(OneKeyCheckInfo oneKeyCheckInfo, JtableContext context, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        DataEntryAsyncProgressMonitor monitor = new DataEntryAsyncProgressMonitor(asyncTaskMonitor, 0.8, 0.0);
        this.beforUploadMultCheckController.oneKeyCheck(oneKeyCheckInfo, (AsyncTaskMonitor)monitor);
        String asyncTaskId = monitor.getTaskId();
        boolean access = true;
        List<MultCheckResultItem> checkItemResults = this.multCheckController.getCheckItemResults(asyncTaskId, context.getFormSchemeKey());
        HashMap<String, Integer> checkItemTypes = new HashMap<String, Integer>();
        checkItemTypes.put("GSSH", 1);
        checkItemTypes.put("enumCheck", 1);
        checkItemTypes.put("entityCheck", 1);
        checkItemTypes.put("entityTreeCheck", 1);
        if (checkItemResults != null && checkItemResults.size() > 0) {
            for (MultCheckResultItem multCheckResultItem : checkItemResults) {
                String checkStatus = multCheckResultItem.getCheckStatus();
                int statusInteger = Double.valueOf(checkStatus).intValue();
                if (checkItemTypes.containsKey(multCheckResultItem.getCheckType()) && ("0".equals(checkStatus) || statusInteger == 0)) {
                    access = false;
                    break;
                }
                if (!multCheckResultItem.getCheckType().equals("entityCheck") || "1".equals(checkStatus)) continue;
                access = false;
                break;
            }
        }
        if (!access) {
            HashMap dimensionSet = new HashMap(context.getDimensionSet());
            FinalaccountsAuditConfig finalaccountsAuditConfig = new FinalaccountsAuditConfig();
            finalaccountsAuditConfig.setBeforeUPLOAD(true);
            FinalaccountsAuditCheckInfo finalaccountsAuditCheckInfo = new FinalaccountsAuditCheckInfo();
            finalaccountsAuditCheckInfo.setContext(context);
            FinalaccountsAuditExtraInfo finalaccountsAuditExtraInfo = new FinalaccountsAuditExtraInfo();
            finalaccountsAuditExtraInfo.setAsyncTaskId(asyncTaskId);
            finalaccountsAuditExtraInfo.setCheckItems(oneKeyCheckInfo.getCheckItems());
            finalaccountsAuditExtraInfo.setFormSchemeKey(context.getFormSchemeKey());
            String period = ((DimensionValue)dimensionSet.get("DATATIME")).toString();
            FormSchemeDefine formScheme = this.viewController.getFormScheme(context.getFormSchemeKey());
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
            String periodTitle = periodProvider.getPeriodTitle(period);
            finalaccountsAuditExtraInfo.setDate(periodTitle);
            finalaccountsAuditExtraInfo.setPeriod(period);
            finalaccountsAuditExtraInfo.setTaskKey(context.getTaskKey());
            TaskDefine taskDefine = this.viewController.queryTaskDefine(context.getTaskKey());
            finalaccountsAuditExtraInfo.setTaskName(taskDefine.getTitle());
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
            finalaccountsAuditExtraInfo.setMasterEntity(dwEntity);
            ArrayList<String> selectedEntity = new ArrayList<String>();
            selectedEntity.add(((DimensionValue)dimensionSet.get(dwEntity.getDimensionName())).getValue());
            finalaccountsAuditExtraInfo.setSelectEntityNum(selectedEntity);
            FinalaccountsAuditExtraInfoParam finalaccountsAuditExtraInfoParam = new FinalaccountsAuditExtraInfoParam();
            finalaccountsAuditExtraInfoParam.setContext(context);
            finalaccountsAuditExtraInfo.setParam(finalaccountsAuditExtraInfoParam);
            finalaccountsAuditConfig.setCheckInfo(finalaccountsAuditCheckInfo);
            finalaccountsAuditConfig.setExtraInfo(finalaccountsAuditExtraInfo);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString((Object)finalaccountsAuditConfig);
        }
        return null;
    }

    private void fitlerDW(String task, List<String> dwList, OneKeyCheckInfo oneKeyCheckInfo, Boolean allDW) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(new String[]{columns[0]});
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)task);
        UnitItemFilterObject filterObject = this.executeQueryHalf(sqlBuilder.toString(), source);
        List<MultCheckItem> allItemList = oneKeyCheckInfo.getCheckItems();
        Iterator<MultCheckItem> it = allItemList.iterator();
        boolean havefitlerCheckItem = false;
        boolean canfitlerCheckItem = false;
        while (it.hasNext()) {
            ArrayList intersection;
            MultCheckItem item = it.next();
            if (item.getCheckType().equals("entityCheck") && item.getCheckType().equals("entityTreeCheck")) {
                if (havefitlerCheckItem) {
                    if (canfitlerCheckItem) {
                        it.remove();
                    }
                } else {
                    havefitlerCheckItem = true;
                    intersection = new ArrayList(dwList);
                    if (filterObject != null) {
                        intersection = (ArrayList)CollectionUtils.intersection(filterObject.getUnitList(), dwList);
                    }
                    int Bblx_DHB = 0;
                    EntityViewData dwEntity = this.jtableParamService.getDwEntity(oneKeyCheckInfo.getContext().getFormSchemeKey());
                    int unitNum = intersection.size();
                    String unitBblx = null;
                    IEntityAttribute bblxField = null;
                    try {
                        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(dwEntity.getKey());
                        bblxField = dwEntityModel.getBblxField();
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                    if (bblxField != null) {
                        for (String unitKey : intersection) {
                            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                            entityQueryByKeyInfo.setContext(oneKeyCheckInfo.getContext());
                            entityQueryByKeyInfo.setEntityKey(unitKey);
                            entityQueryByKeyInfo.setEntityViewKey(dwEntity.getKey());
                            entityQueryByKeyInfo.getCaptionFields().add(bblxField.getCode());
                            EntityByKeyReturnInfo queryEntityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                            int bblxIndex = queryEntityDataByKey.getCells().indexOf(bblxField.getCode());
                            EntityData entity = queryEntityDataByKey.getEntity();
                            if (entity == null || bblxIndex < 0 || (unitBblx = (String)entity.getData().get(bblxIndex)) == null || !unitBblx.equals("0")) continue;
                            ++Bblx_DHB;
                        }
                    }
                    if (Bblx_DHB == unitNum) {
                        canfitlerCheckItem = true;
                        it.remove();
                    }
                }
            }
            if (filterObject == null || !filterObject.getCheckItemList().contains(item.getName())) continue;
            if (item.getItemSetting() == null || item.getItemSetting().equals("null")) {
                if (allDW.booleanValue() || (intersection = (ArrayList)CollectionUtils.intersection(filterObject.getUnitList(), dwList)).size() != 0) continue;
                it.remove();
                continue;
            }
            if (allDW.booleanValue()) {
                ((LinkedTreeMap)item.getItemSetting()).put((Object)"unitList", filterObject.getUnitList());
                continue;
            }
            intersection = (ArrayList)CollectionUtils.intersection(filterObject.getUnitList(), dwList);
            if (intersection.size() == 0) {
                it.remove();
                continue;
            }
            ((LinkedTreeMap)item.getItemSetting()).put((Object)"unitList", (Object)intersection);
        }
    }

    private UnitItemFilterObject executeQueryHalf(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        UnitItemFilterObject queryResult = null;
        try {
            queryResult = (UnitItemFilterObject)template.query(sql, (SqlParameterSource)source, this::readObject);
        }
        catch (Exception e) {
            logger.error("\u672a\u521b\u5efa\u6279\u91cf\u4e0a\u62a5\u524d\u7efc\u5408\u5ba1\u6838\u914d\u7f6e\u8868\uff0c\u6240\u6709\u5355\u4f4d\u90fd\u5c06\u6309\u7167\u9ed8\u8ba4\u5ba1\u6838\u65b9\u6848\u8fdb\u884c\u5ba1\u6838\uff0c\u914d\u7f6e\u8868\u8be6\u60c5\u8bf7\u54a8\u8be2\u7ba1\u7406\u5458");
        }
        return queryResult;
    }

    private UnitItemFilterObject readObject(ResultSet rs) throws SQLException {
        rs.next();
        UnitItemFilterObject impl = new UnitItemFilterObject();
        String checkItemString = rs.getString(columns[1]);
        String unitString = rs.getString(columns[2]);
        String checkItemSplits = checkItemString.contains(",") ? "," : ";";
        String unitSplits = unitString.contains(",") ? "," : ";";
        String[] checkItemList = checkItemString.split(checkItemSplits);
        String[] unitList = unitString.split(unitSplits);
        impl.setCheckItemList(Arrays.asList(checkItemList));
        impl.setUnitList(new ArrayList<String>(Arrays.asList(unitList)));
        return impl;
    }

    class UnitItemFilterObject {
        private List<String> checkItemList;
        private ArrayList<String> unitList;

        UnitItemFilterObject() {
        }

        public List<String> getCheckItemList() {
            return this.checkItemList;
        }

        public void setCheckItemList(List<String> checkItemList) {
            this.checkItemList = checkItemList;
        }

        public ArrayList<String> getUnitList() {
            return this.unitList;
        }

        public void setUnitList(ArrayList<String> unitList) {
            this.unitList = unitList;
        }
    }
}

