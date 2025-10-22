/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.common.DataSchemeUtils
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.util.StringHelper
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dafafill.web;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.dafafill.model.ConditionField;
import com.jiuqi.nr.dafafill.model.DataFillModel;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.DefaultValueType;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.ModelType;
import com.jiuqi.nr.dafafill.model.enums.SelectType;
import com.jiuqi.nr.dafafill.model.enums.TableType;
import com.jiuqi.nr.dafafill.tree.DataFillSchemeTree;
import com.jiuqi.nr.dafafill.tree.DataFillTaskTree;
import com.jiuqi.nr.dafafill.web.vo.DataEntryVO;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.DataSchemeUtils;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.util.StringHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/datafill/dataentry"})
@Api(value="\u6570\u636e\u5f55\u5165\u7a7f\u900f\u5230\u81ea\u5b9a\u4e49\u5f55\u5165")
public class DataFillDataEntryController {
    @Autowired
    private IRunTimeViewController runTime;
    @Autowired
    private IRuntimeDataSchemeService schemeService;
    @Autowired
    private DataFillSchemeTree schemeTree;
    @Autowired
    private DataFillTaskTree taskTree;
    @Autowired
    private IRuntimeTaskService runtimeTaskService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService entityMetaService;

    @ApiOperation(value="\u83b7\u53d6dataFillModel\u5bf9\u8c61")
    @PostMapping(value={"/get-model"})
    public DataFillModel getQueryModelObject(@Valid @RequestBody DataEntryVO dataEntryVO) throws Exception {
        DataLinkDefine firstLink = this.getFirstZB(dataEntryVO.getLinkList());
        if (firstLink == null) {
            return null;
        }
        TaskDefine taskDefine = this.runtimeTaskService.queryTaskDefine(dataEntryVO.getTaskKey());
        DataScheme scheme = this.schemeService.getDataScheme(taskDefine.getDataScheme());
        List dims = this.schemeService.getDataSchemeDimension(scheme.getKey());
        Map<String, String> dimensionMap = dataEntryVO.getDimensionMap();
        ArrayList<QueryField> queryFields = new ArrayList<QueryField>();
        ArrayList<QueryField> assistFields = new ArrayList<QueryField>();
        ArrayList<ConditionField> conditionFields = new ArrayList<ConditionField>();
        ArrayList<String> valueList = new ArrayList<String>();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        String masterEntityId = "";
        String masterFullCode = "";
        for (Object dim : dims) {
            QueryField dimQueryField = this.schemeTree.convertSchemeDimToQueryField((DataDimension)dim, scheme);
            if (dimQueryField.getFieldType() == FieldType.SCENE) {
                if (this.filterDim(scheme, (DataDimension)dim)) continue;
                assistFields.add(dimQueryField);
            } else if (dimQueryField.getFieldType() == FieldType.PERIOD) {
                this.buildPeriodRange(dimQueryField, dataEntryVO, dim.getDimKey(), dimensionMap.get(dim.getDimKey()));
                queryFields.add(0, dimQueryField);
            } else if (dimQueryField.getFieldType() == FieldType.MASTER) {
                if (!dimensionMap.containsKey(dim.getDimKey())) continue;
                masterEntityId = dim.getDimKey();
                String formSchemeKey = this.getFormSchemeKey(dataEntryVO);
                if (formSchemeKey != null) {
                    dimQueryField.setFilterExpression(formSchemeKey);
                }
                queryFields.add(dimQueryField);
            }
            ConditionField conditionField = this.getConditionField(dimQueryField);
            ArrayList<String> values = new ArrayList<String>();
            values.add(dimensionMap.get(dim.getDimKey()));
            conditionField.setDefaultValues(values);
            if (dimQueryField.getFieldType() == FieldType.PERIOD) {
                valueList.add(0, conditionField.getDefaultValues().get(0));
                dimensionValueSet.setValue("DATATIME", (Object)conditionField.getDefaultValues().get(0));
                if (this.hasAdjust(dataEntryVO)) {
                    conditionField.setDefaultBinding(dataEntryVO.getParamMap().get("ADJUST"));
                }
                conditionFields.add(0, conditionField);
                continue;
            }
            if (dimQueryField.getFieldType() == FieldType.MASTER) {
                valueList.add(conditionField.getDefaultValues().get(0));
                masterFullCode = conditionField.getFullCode();
                conditionFields.add(conditionField);
                continue;
            }
            conditionFields.add(conditionField);
        }
        this.buildMasterValue(conditionFields, valueList, dataEntryVO, dimensionValueSet, masterFullCode, masterEntityId);
        DataField firstField = this.schemeService.getDataField(firstLink.getLinkExpression());
        if (!this.isFMDM(dataEntryVO)) {
            if (this.isFixed(firstField)) {
                for (String id : dataEntryVO.getLinkList()) {
                    DataLinkDefine dl = this.runTime.queryDataLinkDefine(id);
                    DataField df = this.schemeService.getDataField(dl.getLinkExpression());
                    if (df == null || !this.isFixed(df) || this.notSupport(df) || DataLinkType.DATA_LINK_TYPE_FORMULA == dl.getType()) continue;
                    DataTable table = this.schemeService.getDataTable(df.getDataTableKey());
                    queryFields.add(this.schemeTree.convertZbToQueryField(df, scheme, table, dl));
                }
            } else {
                List allLinksInRegion = this.runTime.getAllLinksInRegion(firstLink.getRegionKey());
                allLinksInRegion.sort(Comparator.comparing(DataLinkDefine::getPosX));
                DataTable table = this.schemeService.getDataTable(firstField.getDataTableKey());
                List allLinks = this.runTime.getAllLinksInRegion(firstLink.getRegionKey());
                Map linkMap = allLinks.stream().collect(Collectors.toMap(DataLinkDefine::getLinkExpression, Function.identity(), (key1, key2) -> key2));
                for (DataLinkDefine dataLink : allLinksInRegion) {
                    DataField zb = this.schemeService.getDataField(dataLink.getLinkExpression());
                    if (this.notSupport(zb) || DataLinkType.DATA_LINK_TYPE_FORMULA == dataLink.getType()) continue;
                    QueryField queryField = this.schemeTree.convertZbToQueryField(zb, scheme, table, (DataLinkDefine)linkMap.get(zb.getKey()));
                    queryFields.add(queryField);
                    if (queryField.getFieldType() != FieldType.TABLEDIMENSION) continue;
                    ConditionField conditionField = this.getConditionField(queryField);
                    conditionFields.add(conditionField);
                }
            }
        } else {
            IEntityModel entityModel = this.entityMetaService.getEntityModel(masterEntityId);
            for (String id : dataEntryVO.getLinkList()) {
                DataLinkDefine dl = this.runTime.queryDataLinkDefine(id);
                if (DataLinkType.DATA_LINK_TYPE_FMDM == dl.getType()) {
                    IEntityAttribute attribute = entityModel.getAttribute(dl.getLinkExpression());
                    queryFields.add(this.taskTree.convertZbToQueryField(entityModel, attribute, taskDefine.getTaskCode()));
                    continue;
                }
                DataField df = this.schemeService.getDataField(dl.getLinkExpression());
                if (df == null || !this.isFixed(df) || this.notSupport(df) || DataLinkType.DATA_LINK_TYPE_FORMULA == dl.getType()) continue;
                DataTable table = this.schemeService.getDataTable(df.getDataTableKey());
                queryFields.add(this.schemeTree.convertZbToQueryField(df, scheme, table, dl));
            }
        }
        DataFillModel model = new DataFillModel();
        model.setModelType(ModelType.SCHEME);
        model.setQueryFields(queryFields);
        model.setAssistFields(assistFields);
        model.setConditionFields(conditionFields);
        model.setTableType(this.getDataFillTableType(firstField));
        this.dealExtendedData(dataEntryVO, model);
        this.dealFMDMType(dataEntryVO, model);
        return model;
    }

    private void dealExtendedData(DataEntryVO dataEntryVO, DataFillModel model) {
        if (dataEntryVO.getParamMap() != null && StringUtils.hasText(dataEntryVO.getParamMap().get("FORMKEY"))) {
            model.getExtendedData().put("FORMKEY", dataEntryVO.getParamMap().get("FORMKEY"));
        }
        if (StringUtils.hasText(dataEntryVO.getTaskKey())) {
            model.getExtendedData().put("taskKey", dataEntryVO.getTaskKey());
            model.getExtendedData().put("TASKKEY", dataEntryVO.getTaskKey());
        }
        if (this.getFormSchemeKey(dataEntryVO) != null) {
            model.getExtendedData().put("FORMSCHEMEKEY", this.getFormSchemeKey(dataEntryVO));
        }
        if (dataEntryVO.getParamMap() != null && StringUtils.hasText(dataEntryVO.getParamMap().get("NRContext"))) {
            model.getExtendedData().put("NRContext", dataEntryVO.getParamMap().get("NRContext"));
        }
    }

    private void dealFMDMType(DataEntryVO dataEntryVO, DataFillModel model) {
        if (this.isFMDM(dataEntryVO)) {
            model.setTableType(TableType.FMDM);
        }
    }

    private boolean filterDim(DataScheme scheme, DataDimension dim) {
        if (this.schemeService.enableAdjustPeriod(scheme.getKey()).booleanValue() && "ADJUST".equals(dim.getDimKey())) {
            return true;
        }
        return DataSchemeUtils.isSingleSelect((DataDimension)dim);
    }

    private void buildPeriodRange(QueryField field, DataEntryVO dataEntryVO, String dimKey, String value) throws Exception {
        List defineList;
        List list;
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(dimKey);
        TaskDefine taskDefine = this.runtimeTaskService.queryTaskDefine(dataEntryVO.getTaskKey());
        String fromPeriod = taskDefine.getFromPeriod();
        String toPeriod = taskDefine.getToPeriod();
        if (this.getFormSchemeKey(dataEntryVO) != null && (list = (defineList = this.runTime.querySchemePeriodLinkBySchemeSort(this.getFormSchemeKey(dataEntryVO))).stream().map(SchemePeriodLinkDefine::getPeriodKey).collect(Collectors.toList())).contains(value)) {
            String temp;
            String priorPeriod = value;
            do {
                temp = priorPeriod;
            } while (list.contains(priorPeriod = periodProvider.priorPeriod(priorPeriod)));
            fromPeriod = temp;
            String nextPeriod = value;
            do {
                temp = nextPeriod;
            } while (list.contains(nextPeriod = periodProvider.nextPeriod(nextPeriod)));
            toPeriod = temp;
        }
        if (StringHelper.isNull((String)fromPeriod)) {
            field.setMinValue(periodProvider.getPeriodCodeRegion()[0]);
        } else {
            field.setMinValue(fromPeriod);
        }
        if (StringHelper.isNull((String)toPeriod)) {
            String[] region = periodProvider.getPeriodCodeRegion();
            field.setMaxValue(region[region.length - 1]);
        } else {
            field.setMaxValue(toPeriod);
        }
    }

    private void buildMasterValue(List<ConditionField> conditionFields, List<String> valueList, DataEntryVO dataEntryVO, DimensionValueSet dimensionValueSet, String masterFullCode, String masterEntityId) throws Exception {
        TaskDefine taskDefine = this.runtimeTaskService.queryTaskDefine(dataEntryVO.getTaskKey());
        if (!StringUtils.hasText(masterEntityId)) {
            masterEntityId = taskDefine.getDw();
        }
        EntityViewDefine entityView = !masterEntityId.equals(taskDefine.getDw()) ? this.entityViewRunTimeController.buildEntityView(masterEntityId, this.getContextFilterExpression(dataEntryVO)) : this.runTime.getViewByTaskDefineKey(taskDefine.getKey());
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.sorted(true);
        query.setEntityView(entityView);
        query.setMasterKeys(dimensionValueSet);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setVarDimensionValueSet(dimensionValueSet);
        context.setPeriodView(taskDefine.getDateTime());
        IEntityTable entityTable = query.executeReader((IContext)context);
        List list = entityTable.getChildRows(valueList.get(1));
        List<String> collect = list.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        collect.add(0, valueList.get(1));
        conditionFields.stream().filter(e -> masterFullCode.equals(e.getFullCode())).findFirst().get().setDefaultValues(collect);
    }

    private TableType getDataFillTableType(DataField field) {
        if (field == null) {
            return TableType.FIXED;
        }
        DataTable table = this.schemeService.getDataTable(field.getDataTableKey());
        if (table == null) {
            return null;
        }
        DataTableType tableType = table.getDataTableType();
        if (tableType == DataTableType.ACCOUNT) {
            return TableType.ACCOUNT;
        }
        return null;
    }

    private ConditionField getConditionField(QueryField dimQueryField) {
        ConditionField conditionField = new ConditionField();
        conditionField.setFullCode(dimQueryField.getFullCode());
        conditionField.setSelectType(this.convertFieldTypeToSelectType(dimQueryField.getFieldType()));
        conditionField.setDefaultValueType(this.convertFieldTypeToDefVType(dimQueryField.getFieldType()));
        conditionField.setDefaultMaxValueType(this.convertFieldTypeToDefVType(dimQueryField.getFieldType()));
        conditionField.setQuickCondition(true);
        return conditionField;
    }

    private SelectType convertFieldTypeToSelectType(FieldType type) {
        switch (type) {
            case MASTER: {
                return SelectType.NONE;
            }
        }
        return SelectType.SINGLE;
    }

    private DefaultValueType convertFieldTypeToDefVType(FieldType type) {
        switch (type) {
            case MASTER: {
                return DefaultValueType.USELECTION;
            }
            case PERIOD: 
            case SCENE: {
                return DefaultValueType.SPECIFIC;
            }
        }
        return DefaultValueType.NONE;
    }

    private DataLinkDefine getFirstZB(List<String> selectZbs) {
        for (String id : selectZbs) {
            DataLinkDefine dataLink = this.runTime.queryDataLinkDefine(id);
            if (dataLink.getType() != DataLinkType.DATA_LINK_TYPE_FIELD && dataLink.getType() != DataLinkType.DATA_LINK_TYPE_FMDM && dataLink.getType() != DataLinkType.DATA_LINK_TYPE_INFO) continue;
            return dataLink;
        }
        return null;
    }

    private boolean isFixed(DataField df) {
        return FieldType.ZB == this.schemeTree.convertDataFieldKind(df.getDataFieldKind());
    }

    private boolean isFMDM(DataEntryVO dataEntryVO) {
        return dataEntryVO.getParamMap() != null && StringUtils.hasText(dataEntryVO.getParamMap().get("TABLETYPE")) && TableType.FMDM.name().equals(dataEntryVO.getParamMap().get("TABLETYPE"));
    }

    private boolean notSupport(DataField df) {
        return df == null || df.getDataFieldType() == DataFieldType.PICTURE || df.getDataFieldType() == DataFieldType.FILE;
    }

    private boolean hasAdjust(DataEntryVO dataEntryVO) {
        return dataEntryVO.getParamMap() != null && dataEntryVO.getParamMap().containsKey("ADJUST");
    }

    private String getFormSchemeKey(DataEntryVO dataEntryVO) {
        if (dataEntryVO.getParamMap() != null && StringUtils.hasText(dataEntryVO.getParamMap().get("FORMSCHEMEKEY"))) {
            return dataEntryVO.getParamMap().get("FORMSCHEMEKEY");
        }
        return null;
    }

    private String getContextFilterExpression(DataEntryVO dataEntryVO) {
        if (dataEntryVO.getParamMap() != null && StringUtils.hasText(dataEntryVO.getParamMap().get("CONTEXTFILTEREXPRESSION"))) {
            return dataEntryVO.getParamMap().get("CONTEXTFILTEREXPRESSION");
        }
        return "";
    }
}

