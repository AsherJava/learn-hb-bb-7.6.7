/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.node.CheckExpression
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.Measure
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 *  com.jiuqi.nr.dataentity.entity.IDataEntity
 *  com.jiuqi.nr.dataentity.param.DataEntityContext
 *  com.jiuqi.nr.dataentity.service.DataEntityService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.excel.param.DataExcelContext;
import com.jiuqi.nr.data.excel.param.DataExportParam;
import com.jiuqi.nr.data.excel.param.FormulaData;
import com.jiuqi.nr.data.excel.param.FormulaQueryInfo;
import com.jiuqi.nr.data.excel.utils.FormulaUtil;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.Measure;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.dataentity.entity.IDataEntity;
import com.jiuqi.nr.dataentity.param.DataEntityContext;
import com.jiuqi.nr.dataentity.service.DataEntityService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataExcelService {
    private static final Logger logger = LoggerFactory.getLogger(DataExcelService.class);
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IDataQueryService dataQueryService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private DataEntityService dataEntityService;

    public Grid2Data getGridData(String formKey) {
        BigDataDefine formDefine = this.runtimeView.getReportDataFromForm(formKey);
        if (null != formDefine) {
            if (formDefine.getData() != null) {
                Grid2Data bytesToGrid = Grid2Data.bytesToGrid((byte[])formDefine.getData());
                String frontScriptFromForm = this.runtimeView.getFrontScriptFromForm(formDefine.getKey());
                if (StringUtils.isNotEmpty((String)frontScriptFromForm)) {
                    bytesToGrid.setScript(frontScriptFromForm);
                }
                return bytesToGrid;
            }
            Grid2Data griddata = new Grid2Data();
            griddata.setRowCount(10);
            griddata.setColumnCount(10);
            return griddata;
        }
        return null;
    }

    public EntityViewDefine getDwEntity(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = null;
        try {
            formSchemeDefine = this.runtimeView.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error("\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230:" + formSchemeKey, e);
            throw new IllegalArgumentException(formSchemeKey);
        }
        if (formSchemeDefine == null) {
            return null;
        }
        EntityViewDefine buildEntityView = this.entityViewRunTimeController.buildEntityView(formSchemeDefine.getDw());
        return buildEntityView;
    }

    public String getDwDimensionName(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = null;
        try {
            formSchemeDefine = this.runtimeView.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error("\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230:" + formSchemeKey, e);
            throw new IllegalArgumentException(formSchemeKey);
        }
        if (formSchemeDefine == null) {
            return null;
        }
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(formSchemeDefine.getDw());
        return entityDefine.getDimensionName();
    }

    public List<String> getCalcDataLinks(DataExcelContext context) {
        Collection calcCellInfosByForm;
        if (context.getFormulaSchemeKey() != null && null != context.getFormKey() && null != (calcCellInfosByForm = this.formulaRunTimeController.getCalcCellDataLinks(context.getFormulaSchemeKey(), context.getFormKey()))) {
            ArrayList<String> calcDataLinks = new ArrayList<String>(calcCellInfosByForm.size());
            for (String dataLinkKey : calcCellInfosByForm) {
                DataLinkDefine dataLinkDefine = this.runtimeView.queryDataLinkDefine(dataLinkKey);
                if (dataLinkDefine == null || dataLinkDefine.getPosX() == 0 || dataLinkDefine.getPosY() == 0 || dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FORMULA) continue;
                calcDataLinks.add(dataLinkKey);
            }
            return calcDataLinks;
        }
        return Collections.emptyList();
    }

    public IRegionDataSet getRegionQuery(DataExportParam info, DataRegionDefine region) {
        Map<String, DimensionValue> dimensionSet = info.getContext().getDimensionSet();
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder(this.getDimensionValueSet(dimensionSet));
        QueryInfoBuilder queryInfoBuilder = QueryInfoBuilder.create((String)region.getKey(), (DimensionCombination)builder.getCombination());
        Measure measure = new Measure();
        measure.setKey("9493b4eb-6516-48a8-a878-25a63a23e63a;".substring(0, "9493b4eb-6516-48a8-a878-25a63a23e63a;".length() - 1));
        measure.setCode("WANYUAN");
        IRegionDataSet iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
        return iRegionDataSet;
    }

    public IRegionDataSet getRegionQuery(DataExportParam info, DataRegionDefine region, QueryInfoBuilder queryInfoBuilder, PageInfo pageInfo) {
        Map<String, DimensionValue> dimensionSet = info.getContext().getDimensionSet();
        if (queryInfoBuilder == null) {
            DimensionCombinationBuilder builder = new DimensionCombinationBuilder(this.getDimensionValueSet(dimensionSet));
            queryInfoBuilder = QueryInfoBuilder.create((String)region.getKey(), (DimensionCombination)builder.getCombination());
        }
        Measure measure = new Measure();
        measure.setKey("9493b4eb-6516-48a8-a878-25a63a23e63a;".substring(0, "9493b4eb-6516-48a8-a878-25a63a23e63a;".length() - 1));
        measure.setCode("WANYUAN");
        queryInfoBuilder.setMeasure(measure);
        if (pageInfo != null) {
            queryInfoBuilder.setPage(pageInfo);
        }
        IRegionDataSet iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
        return iRegionDataSet;
    }

    private DimensionValueSet getDimensionValueSet(Map<String, DimensionValue> dimensionSet) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (dimensionSet == null) {
            return dimensionValueSet;
        }
        for (DimensionValue value : dimensionSet.values()) {
            String[] values = value.getValue().split(";");
            if (values.length == 1 || values.length == 0) {
                dimensionValueSet.setValue(value.getName(), (Object)value.getValue());
                continue;
            }
            List<String> valueList = Arrays.asList(values);
            dimensionValueSet.setValue(value.getName(), valueList);
        }
        return dimensionValueSet;
    }

    public List<FormulaData> getFormulaList(FormulaQueryInfo formulaQueryInfo) {
        ArrayList<FormulaData> formulas = new ArrayList<FormulaData>();
        if (formulaQueryInfo.getFormulaSchemeKey() == null) {
            return formulas;
        }
        FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaQueryInfo.getFormulaSchemeKey());
        if (formulaSchemeDefine == null) {
            return formulas;
        }
        DataLinkDefine dataLinkDefine = null;
        if (formulaQueryInfo.getDataLinkKey() != null) {
            dataLinkDefine = this.runtimeView.queryDataLinkDefine(formulaQueryInfo.getDataLinkKey());
        }
        String balance = "balance";
        String check = "check";
        String calculate = "calculate";
        String EFDC = "EFDC";
        ArrayList<DataEngineConsts.FormulaType> formulaTypes = new ArrayList<DataEngineConsts.FormulaType>();
        String useType = formulaQueryInfo.getUseType();
        if (StringUtils.isNotEmpty((String)useType)) {
            if (useType.contains(balance) || useType.toLowerCase().contains(balance)) {
                formulaTypes.add(DataEngineConsts.FormulaType.BALANCE);
            }
            if (useType.contains(check) || useType.toLowerCase().contains(check)) {
                formulaTypes.add(DataEngineConsts.FormulaType.CHECK);
            }
            if (useType.contains(calculate) || useType.toLowerCase().contains(calculate)) {
                formulaTypes.add(DataEngineConsts.FormulaType.CALCULATE);
            }
        }
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formulaQueryInfo.getFormSchemeKey());
        for (DataEngineConsts.FormulaType formulaType : formulaTypes) {
            boolean isAnalysis = false;
            if (dataLinkDefine == null) {
                ArrayList<String> formKeys = new ArrayList<String>();
                if (formulaQueryInfo.getFormKey() != null) {
                    formKeys.add(formulaQueryInfo.getFormKey());
                }
                if (StringUtils.isNotEmpty((String)formulaQueryInfo.getFormKey())) {
                    String[] idArray = formulaQueryInfo.getFormKey().split(";");
                    for (int i = 0; i < idArray.length; ++i) {
                        String formKey = idArray[i];
                        formKeys.add(formKey);
                    }
                }
                if (formKeys.isEmpty()) {
                    List<FormulaData> formulaDatas = FormulaUtil.getFormulaDatas(formulaSchemeDefine.getKey(), null, formulaType);
                    if (formulaDatas == null) continue;
                    formulas.addAll(formulaDatas);
                    continue;
                }
                for (String formKey : formKeys) {
                    List<FormulaData> formulaDatas;
                    FormDefine queryFormById = this.runtimeView.queryFormById(formKey);
                    if (queryFormById == null || (formulaDatas = FormulaUtil.getFormulaDatas(formulaSchemeDefine.getKey(), queryFormById.getKey(), formulaType)) == null) continue;
                    formulas.addAll(formulaDatas);
                }
                continue;
            }
            isAnalysis = true;
            ArrayList parsedExpressions = new ArrayList();
            parsedExpressions.addAll(this.formulaRunTimeController.getParsedExpressionByDataLink(dataLinkDefine.getUniqueCode(), formulaSchemeDefine.getKey(), null, formulaType));
            for (IParsedExpression parsedExpression : parsedExpressions) {
                FormulaData formulaInfo;
                block25: {
                    if (parsedExpression instanceof CheckExpression && DataEngineConsts.FormulaShowType.EXCEL.getValue() == formulaQueryInfo.getShowType()) {
                        try {
                            if (!((CheckExpression)parsedExpression).support(Language.EXCEL)) {
                            }
                            break block25;
                        }
                        catch (Exception e) {
                            logger.debug(e.getMessage(), e);
                        }
                        continue;
                    }
                }
                if (StringUtils.isNotEmpty((String)(formulaInfo = new FormulaData(parsedExpression, this.runtimeView, isAnalysis, formulaQueryInfo.getShowType(), formulaSchemeDefine.getFormSchemeKey(), formulaQueryInfo.getAdjustorList(), this.dataDefinitionRuntimeController, environment)).getFormKey())) {
                    FormDefine formDefine = this.runtimeView.queryFormById(formulaInfo.getFormKey());
                    if (formDefine != null) {
                        formulaInfo.setFormTitle(formDefine.getTitle());
                    }
                } else {
                    formulaInfo.setFormTitle("\u8868\u95f4");
                }
                formulas.add(formulaInfo);
            }
        }
        String dataLinkKey = "";
        if (dataLinkDefine != null) {
            dataLinkKey = dataLinkDefine.getKey();
        }
        final String orderDataLinkKey = dataLinkKey;
        try {
            Collections.sort(formulas, new Comparator<FormulaData>(){

                @Override
                public int compare(FormulaData formula0, FormulaData formula1) {
                    if (formula0 == null && formula1 == null) {
                        return 0;
                    }
                    if (formula0 == null) {
                        return -1;
                    }
                    if (formula1 == null) {
                        return 1;
                    }
                    if (StringUtils.isNotEmpty((String)orderDataLinkKey)) {
                        String assignDataLinkKey0 = formula0.getAssignDataLinkKey();
                        String assignDataLinkKey1 = formula1.getAssignDataLinkKey();
                        if (StringUtils.isNotEmpty((String)assignDataLinkKey0) && orderDataLinkKey.contains(assignDataLinkKey0)) {
                            return -1;
                        }
                        if (StringUtils.isNotEmpty((String)assignDataLinkKey1) && orderDataLinkKey.contains(assignDataLinkKey1)) {
                            return 1;
                        }
                    }
                    if (!formula0.getCode().equals(formula1.getCode())) {
                        return formula0.getCode().compareTo(formula1.getCode());
                    }
                    return 0;
                }
            });
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return formulas;
    }

    public DataLinkDefine getLink(String linkDataKey) {
        DataLinkDefine dataLinkDefine = this.runtimeView.queryDataLinkDefine(linkDataKey);
        if (null != dataLinkDefine) {
            return dataLinkDefine;
        }
        return null;
    }

    public List<String> getAllDimEntityKey(String entityKey, Map<String, DimensionValue> dimensionSet, String formSchemeKey) {
        return this.getEntityKey(entityKey, dimensionSet, formSchemeKey, true, false);
    }

    public List<String> getAllEntityKey(String entityKey, DataExcelContext context, String filterExpression, boolean ignorePermissions) {
        ArrayList<String> valueIDList = new ArrayList<String>();
        EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(entityKey);
        HashSet<String> fields = new HashSet<String>();
        fields.add("ALL");
        ArrayList referRelations = new ArrayList();
        DataEntityContext context1 = new DataEntityContext();
        context1.setFormSchemeKey(context.getFormSchemeKey());
        context1.setSorted(true);
        context1.setFormKey(context.getFormKey());
        context1.setTaskKey(context.getTaskKey());
        IDataEntity queryEntity = this.dataEntityService.queryEntity(entityView, context1, true, null);
        if (queryEntity == null) {
            return valueIDList;
        }
        List allRows = queryEntity.getAllRow().getRowList();
        for (IEntityRow row : allRows) {
            valueIDList.add(row.getEntityKeyData());
        }
        return valueIDList;
    }

    public List<IEntityRow> getAllEntityData(String entityKey, DataExcelContext context, String filterExpression, boolean ignorePermissions) {
        ArrayList<IEntityRow> valueIDList = new ArrayList<IEntityRow>();
        EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(entityKey);
        HashSet<String> fields = new HashSet<String>();
        fields.add("ALL");
        ArrayList referRelations = new ArrayList();
        DataEntityContext context1 = new DataEntityContext();
        context1.setFormSchemeKey(context.getFormSchemeKey());
        context1.setSorted(true);
        context1.setFormKey(context.getFormKey());
        context1.setTaskKey(context.getTaskKey());
        IDataEntity queryEntity = this.dataEntityService.queryEntity(entityView, context1, true, null);
        if (queryEntity == null) {
            return valueIDList;
        }
        List allRows = queryEntity.getAllRow().getRowList();
        return allRows;
    }

    private List<String> getEntityKey(String entityKey, Map<String, DimensionValue> dimensionSet, String formSchemeKey, boolean queryDim, boolean sorted) {
        ArrayList<String> valueIDList = new ArrayList<String>();
        EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(entityKey);
        HashSet<String> fields = new HashSet<String>();
        fields.add("ALL");
        DataEntityContext context = new DataEntityContext();
        context.setFormSchemeKey(formSchemeKey);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (dimensionSet != null && dimensionSet.containsKey("DATATIME")) {
            dimensionValueSet.setValue("DATATIME", (Object)dimensionSet.get("DATATIME"));
        }
        ArrayList referRelations = new ArrayList();
        context.setSorted(true);
        IDataEntity queryEntity = queryDim ? this.dataEntityService.queryEntityWithDimVal(entityView, context, true, dimensionValueSet, null) : this.dataEntityService.queryEntity(entityView, context, true, null);
        if (queryEntity == null) {
            return valueIDList;
        }
        List allRows = queryEntity.getAllRow().getRowList();
        for (IEntityRow row : allRows) {
            valueIDList.add(row.getEntityKeyData());
        }
        return valueIDList;
    }
}

