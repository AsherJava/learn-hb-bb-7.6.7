/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.cache.message.MessagePublisher
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.NodeAdapter
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.common.EnumTransUtils
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.cache.message.MessagePublisher;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.NodeAdapter;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.common.EnumTransUtils;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormulaData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.output.FormCheckStatusInfo;
import com.jiuqi.nr.jtable.params.output.FormCheckStatusMessage;
import com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckResultInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.params.output.FormulaNodeInfo;
import com.jiuqi.nr.jtable.service.IFormulaCheckDesService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.jtable.util.CheckResultUtil;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.EntityDataLoader;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class CheckMonitor
extends AbstractMonitor {
    private final IRuntimeDataSchemeService dataSchemeService;
    protected IRunTimeViewController runtimeView;
    protected IDataDefinitionRuntimeController runtimeController;
    protected IJtableResourceService jtableResourceService;
    protected FormulaCheckReturnInfo checkReturnInfo = new FormulaCheckReturnInfo();
    protected FormSchemeDefine formScheme;
    protected FormulaSchemeDefine formulaSchemeDefine;
    protected Map<String, FormCheckStatusInfo> checkStatusMap = new HashMap<String, FormCheckStatusInfo>();
    protected JtableContext jtableContext;
    protected List<EntityViewData> entityList;
    private Map<String, List<String>> regionBizKeysMap = new HashMap<String, List<String>>();
    private Map<String, FormulaCheckDesInfo> formulaCheckDesMap;
    private final Logger logger = LoggerFactory.getLogger(CheckMonitor.class);
    private Map<String, Integer> dimensionListIndexMap = new HashMap<String, Integer>();
    private Map<String, Map<String, String>> entityDataMap = new HashMap<String, Map<String, String>>();
    private Map<String, List<String>> regionDataIds = new HashMap<String, List<String>>();
    private Map<String, EntityDataLoader> entityDataLoaders = new HashMap<String, EntityDataLoader>();
    private DecimalFormat decimalFormat;
    private DataModelService dataModelService;

    public CheckMonitor(FormSchemeDefine formScheme, FormulaSchemeDefine formulaSchemeDefine) {
        this.runtimeView = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        this.runtimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        this.jtableResourceService = (IJtableResourceService)BeanUtil.getBean(IJtableResourceService.class);
        this.dataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        this.dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
        this.formScheme = formScheme;
        this.formulaSchemeDefine = formulaSchemeDefine;
        this.decimalFormat = new DecimalFormat();
        this.decimalFormat.setGroupingSize(3);
        this.decimalFormat.setGroupingUsed(true);
    }

    public void setCheckStatusMap(Map<String, FormCheckStatusInfo> checkStatusMap, JtableContext jtableContext) {
        this.checkStatusMap = checkStatusMap;
        this.jtableContext = jtableContext;
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        this.entityList = jtableParamService.getEntityList(jtableContext.getFormSchemeKey());
    }

    public void error(FormulaCheckEventImpl event) {
        if (this.formulaCheckDesMap == null) {
            this.formulaCheckDesMap = new HashMap<String, FormulaCheckDesInfo>();
            IFormulaCheckDesService formulaCheckDesService = (IFormulaCheckDesService)BeanUtil.getBean(IFormulaCheckDesService.class);
            FormulaCheckDesQueryInfo formulaCheckDesQueryInfo = new FormulaCheckDesQueryInfo();
            formulaCheckDesQueryInfo.setFormSchemeKey(this.jtableContext.getFormSchemeKey());
            formulaCheckDesQueryInfo.setTaskKey(this.jtableContext.getTaskKey());
            formulaCheckDesQueryInfo.setFormKey(this.jtableContext.getFormKey());
            formulaCheckDesQueryInfo.setDimensionSet(this.jtableContext.getDimensionSet());
            formulaCheckDesQueryInfo.setFormulaSchemeKey(this.jtableContext.getFormulaSchemeKey());
            List<FormulaCheckDesInfo> formulaCheckDesList = formulaCheckDesService.queryFormulaCheckDes(formulaCheckDesQueryInfo);
            for (FormulaCheckDesInfo formulaCheckDesInfo : formulaCheckDesList) {
                this.formulaCheckDesMap.put(formulaCheckDesInfo.getDesKey(), formulaCheckDesInfo);
            }
        }
        FormulaData formula = new FormulaData(event.getFormulaObj());
        formula.setKey(event.getParsedExpresionKey());
        DimensionValueSet rowkey = event.getRowkey();
        List<DimensionValueSet> rowKeys = this.fillLostDim(rowkey);
        for (DimensionValueSet rowKey : rowKeys) {
            FormulaCheckResultInfo creatCheckResultInfo = this.creatCheckResultInfo(event, rowKey);
            if (creatCheckResultInfo == null) continue;
            this.checkReturnInfo.getResults().add(creatCheckResultInfo);
        }
    }

    private List<DimensionValueSet> fillLostDim(DimensionValueSet rowkey) {
        Map<String, DimensionValue> onlyDimCartesian = DimensionValueSetUtil.getOnlyDimCartesian(this.jtableContext.getDimensionSet(), this.jtableContext.getFormSchemeKey());
        List<DimensionValueSet> onlyDims = DimensionValueSetUtil.getDimensionValueSetList(new HashMap<String, DimensionValue>(onlyDimCartesian));
        ArrayList<DimensionValueSet> result = new ArrayList<DimensionValueSet>();
        for (DimensionValueSet onlyDim : onlyDims) {
            if (rowkey.hasValue(onlyDim.getName(0))) continue;
            DimensionValueSet newDimension = new DimensionValueSet(rowkey);
            for (int i = 0; i < onlyDim.size(); ++i) {
                newDimension.setValue(onlyDim.getName(i), onlyDim.getValue(i));
            }
            result.add(newDimension);
        }
        if (result.isEmpty()) {
            result.add(rowkey);
        }
        return result;
    }

    public FormulaCheckReturnInfo getCheckResult() {
        return this.checkReturnInfo;
    }

    protected FormulaCheckResultInfo creatCheckResultInfo(FormulaCheckEventImpl event, DimensionValueSet rowKey) {
        FormulaCheckResultInfo checkResult = new FormulaCheckResultInfo();
        FormulaData formula = new FormulaData(event.getFormulaObj());
        formula.setKey(event.getParsedExpresionKey());
        formula.setFormulaSchemeKey(this.jtableContext.getFormulaSchemeKey());
        String formulaStr = event.getCompliedFormulaExpression();
        if (StringUtils.isEmpty((String)formulaStr)) {
            formulaStr = formula.getFormula();
        }
        formula.setFormula(formulaStr);
        String formKey = CheckResultUtil.getFormKey(formula);
        formula.setGlobRow(event.getWildcardRow());
        formula.setGlobCol(event.getWildcardCol());
        checkResult.setFormula(formula);
        if (event.getLeftValue() != null && !event.getLeftValue().isNull) {
            checkResult.setLeft(CheckResultUtil.valueFormate(this.decimalFormat, event.getLeftValue()));
        }
        if (event.getRightValue() != null && !event.getRightValue().isNull) {
            checkResult.setRight(CheckResultUtil.valueFormate(this.decimalFormat, event.getRightValue()));
        }
        if (event.getDifferenceValue() != null && !event.getDifferenceValue().isNull) {
            checkResult.setDifference(CheckResultUtil.valueFormate(this.decimalFormat, event.getDifferenceValue()));
        }
        Map<String, DimensionValue> dimensionSet = this.jtableContext.getDimensionSet();
        DimensionValueSet dataDimensionValueSet = new DimensionValueSet();
        for (String dimensionName : dimensionSet.keySet()) {
            if (rowKey.hasValue(dimensionName)) {
                dataDimensionValueSet.setValue(dimensionName, rowKey.getValue(dimensionName));
                continue;
            }
            dataDimensionValueSet.setValue(dimensionName, (Object)dimensionSet.get(dimensionName).getValue());
        }
        String statusStr = formKey + ";" + dataDimensionValueSet.toString();
        if (this.checkStatusMap.containsKey(statusStr)) {
            FormCheckStatusInfo formCheckStatusInfo = this.checkStatusMap.get(statusStr);
            formCheckStatusInfo.setStatus(2);
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet(rowKey);
        for (String dimensionName : dimensionSet.keySet()) {
            if (dimensionValueSet.hasValue(dimensionName)) continue;
            dimensionValueSet.setValue(dimensionName, (Object)dimensionSet.get(dimensionName).getValue());
        }
        String rowDimensionValueKey = dimensionValueSet.toString();
        if (this.dimensionListIndexMap.containsKey(rowDimensionValueKey)) {
            checkResult.setDimensionIndex(this.dimensionListIndexMap.get(rowDimensionValueKey));
        } else {
            dimensionSet = DimensionValueSetUtil.getDimensionSet(dimensionValueSet);
            this.checkReturnInfo.getDimensionList().add(dimensionSet);
            int dimensionValueSetIndex = this.checkReturnInfo.getDimensionList().size() - 1;
            this.dimensionListIndexMap.put(rowDimensionValueKey, dimensionValueSetIndex);
            checkResult.setDimensionIndex(dimensionValueSetIndex);
        }
        EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
        entityQueryByKeyInfo.setContext(this.jtableContext);
        IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
        IPeriodEntityAdapter periodEntityAdapter = (IPeriodEntityAdapter)BeanUtil.getBean(IPeriodEntityAdapter.class);
        for (EntityViewData entity : this.entityList) {
            if (periodEntityAdapter.isPeriodEntity(entity.getKey()) || this.entityDataLoaders.containsKey(entity.getDimensionName())) continue;
            entityQueryByKeyInfo.setEntityViewKey(entity.getKey());
            EntityDataLoader entityDataLoader = jtableEntityService.getEntityDataLoader(entityQueryByKeyInfo, true);
            this.entityDataLoaders.put(entity.getDimensionName(), entityDataLoader);
        }
        for (NodeAdapter nodeAdapter : event.getNodes()) {
            FormulaNodeInfo nodeInfo = new FormulaNodeInfo();
            DynamicDataNode dynamicDataNode = nodeAdapter.getNodeInfo();
            QueryField queryField = dynamicDataNode.getQueryField();
            nodeInfo.setFieldKey(queryField.getUID());
            DataField dataField = null;
            boolean hasField = false;
            int fractionDigits = 0;
            try {
                dataField = this.dataSchemeService.getDataFieldByColumnKey(queryField.getUID());
                if (dataField != null) {
                    nodeInfo.setFieldTitle(dataField.getTitle());
                    nodeInfo.setFieldType(dataField.getType().name());
                    hasField = true;
                    fractionDigits = dataField.getFractionDigits() == null ? 0 : dataField.getFractionDigits();
                } else {
                    ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByID(queryField.getUID());
                    if (columnModelDefine != null) {
                        nodeInfo.setFieldTitle(columnModelDefine.getTitle());
                        nodeInfo.setFieldType(EnumTransUtils.valueOf((ColumnModelType)columnModelDefine.getColumnType()).name());
                        hasField = true;
                        fractionDigits = columnModelDefine.getPrecision();
                    }
                }
            }
            catch (Exception e) {
                this.logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            DataLinkColumn dataLinkInfo = nodeAdapter.getDataLinkInfo();
            if (dataLinkInfo != null) {
                FormDefine nodeForm;
                if (StringUtils.isEmpty((String)dataLinkInfo.getDataLinkCode()) || dataLinkInfo.getReportInfo() == null) {
                    nodeInfo.setNodeShow(dynamicDataNode.toString());
                } else {
                    nodeInfo.setNodeShow(dataLinkInfo.toString());
                }
                ReportInfo reportInfo = dataLinkInfo.getReportInfo();
                if (reportInfo != null && (nodeForm = this.runtimeView.queryFormById(reportInfo.getReportKey())) != null) {
                    nodeInfo.setFormKey(nodeForm.getKey());
                    nodeInfo.setFormTitle(nodeForm.getTitle());
                    DataLinkDefine dataLinkDefine = this.runtimeView.queryDataLinkDefineByUniquecode(nodeForm.getKey(), dataLinkInfo.getDataLinkCode());
                    if (dataLinkDefine != null) {
                        nodeInfo.setDataLinkKey(dataLinkDefine.getKey());
                        nodeInfo.setRegionKey(dataLinkDefine.getRegionKey());
                        String dataId = this.getDataId(dataLinkDefine, dimensionValueSet, queryField);
                        if (StringUtils.isNotEmpty((String)dataId) && this.isInvalidData(dataLinkDefine.getRegionKey(), dataDimensionValueSet, dataId)) {
                            return null;
                        }
                        nodeInfo.setId(dataId);
                    }
                }
            } else {
                nodeInfo.setNodeShow(dynamicDataNode.toString());
            }
            if (hasField) {
                if (FieldType.FIELD_TYPE_DATE.name().equals(nodeInfo.getFieldType())) {
                    nodeInfo.setValue(CheckResultUtil.valueFormateDate(nodeAdapter.getValue()));
                } else if (FieldType.FIELD_TYPE_DATE_TIME.name().equals(nodeInfo.getFieldType())) {
                    nodeInfo.setValue(CheckResultUtil.valueFormateDateTime(nodeAdapter.getValue()));
                } else {
                    nodeInfo.setValue(CheckResultUtil.valueFormate(this.decimalFormat, nodeAdapter.getValue(), fractionDigits));
                }
            } else {
                nodeInfo.setValue(CheckResultUtil.valueFormate(this.decimalFormat, nodeAdapter.getValue()));
            }
            checkResult.getNodes().add(nodeInfo);
        }
        String desKey = CheckResultUtil.buildDesKey(this.jtableContext.getFormSchemeKey(), this.jtableContext.getFormulaSchemeKey(), formula.getFormKey(), formula.getKey(), formula.getGlobRow(), formula.getGlobCol(), this.checkReturnInfo.getDimensionList().get(checkResult.getDimensionIndex()));
        checkResult.setDimensionTitle(DimensionValueSetUtil.getDimensionTitle(dimensionValueSet, this.jtableContext.getFormSchemeKey(), this.entityDataMap, this.entityDataLoaders));
        checkResult.setKey(desKey);
        if (this.formulaCheckDesMap.containsKey(desKey)) {
            checkResult.setDescriptionInfo(this.formulaCheckDesMap.get(desKey).getDescriptionInfo());
        }
        return checkResult;
    }

    private boolean isInvalidData(String regionKey, DimensionValueSet dimensionValueSet, String dataId) {
        DataRegionDefine regionDefine = this.runtimeView.queryDataRegionDefine(regionKey);
        if (regionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE && StringUtils.isNotEmpty((String)regionDefine.getFilterCondition())) {
            String regionDataStr = regionKey + ";" + dimensionValueSet.toString();
            List<Object> dataIds = new ArrayList();
            if (this.regionDataIds.containsKey(regionDataStr)) {
                dataIds = this.regionDataIds.get(regionDataStr);
            } else {
                this.regionDataIds.put(regionDataStr, dataIds);
                JtableContext regionContext = new JtableContext(this.jtableContext);
                regionContext.setFormKey(regionDefine.getFormKey());
                regionContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet(dimensionValueSet));
                RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
                regionQueryInfo.setContext(regionContext);
                regionQueryInfo.setRegionKey(regionKey);
                dataIds.addAll(this.jtableResourceService.queryRegionIds(regionQueryInfo));
            }
            if (!dataIds.contains(dataId)) {
                return true;
            }
        }
        return false;
    }

    private String getDataId(DataLinkDefine dataLinkDefine, DimensionValueSet dimensionValueSet, QueryField queryField) {
        DataRegionDefine region = this.runtimeView.queryDataRegionDefine(dataLinkDefine.getRegionKey());
        if (region.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
            return null;
        }
        List<String> regionBizKeyList = this.getRegionBizKeyList(region);
        StringBuffer bizKeyStrBuf = new StringBuffer();
        for (String bizKey : regionBizKeyList) {
            DimensionValueSet dimensionRestriction;
            String bizKeyValue = null;
            if (dimensionValueSet.hasValue(bizKey)) {
                bizKeyValue = dimensionValueSet.getValue(bizKey).toString();
            } else if (queryField.getTable().getDimensionRestriction() != null && (dimensionRestriction = queryField.getTable().getDimensionRestriction()).hasValue(bizKey)) {
                bizKeyValue = dimensionRestriction.getValue(bizKey).toString();
            }
            if (StringUtils.isEmpty((String)bizKeyValue)) continue;
            if (bizKeyStrBuf.length() > 0) {
                bizKeyStrBuf.append("#^$");
            }
            bizKeyStrBuf.append(bizKeyValue);
        }
        return bizKeyStrBuf.toString();
    }

    private List<String> getRegionBizKeyList(DataRegionDefine region) {
        if (this.regionBizKeysMap.containsKey(region.getKey())) {
            return this.regionBizKeysMap.get(region.getKey());
        }
        ArrayList<String> bizKeyList = new ArrayList<String>();
        this.regionBizKeysMap.put(region.getKey(), bizKeyList);
        try {
            List allLinkDefines = this.runtimeView.getAllLinksInRegion(region.getKey());
            List fieldKeys = this.runtimeView.getFieldKeysInRegion(region.getKey());
            List tableDefines = this.runtimeController.queryTableDefinesByFields((Collection)fieldKeys);
            ExecutorContext context = new ExecutorContext(this.runtimeController);
            IDataAccessProvider dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
            IDataAssist dataAssist = dataAccessProvider.newDataAssist(context);
            if (tableDefines == null || tableDefines.isEmpty()) {
                return bizKeyList;
            }
            HashSet<String> tableNameSet = new HashSet<String>();
            for (TableDefine table : tableDefines) {
                if (tableNameSet.contains(table.getKey())) continue;
                tableNameSet.add(table.getKey());
                String[] bizKeyFieldsID = table.getBizKeyFieldsID();
                FieldDefine bizKeyOrderFieldDefine = null;
                block3: for (String fieldKey : bizKeyFieldsID) {
                    FieldDefine fieldDefine = this.runtimeController.queryFieldDefine(fieldKey);
                    if (region.getAllowDuplicateKey() && fieldDefine.getValueType() == FieldValueType.FIELD_VALUE_BIZKEY_ORDER) {
                        bizKeyOrderFieldDefine = fieldDefine;
                    }
                    for (DataLinkDefine link : allLinkDefines) {
                        if (0 == link.getPosX() || 0 == link.getPosY() || link.getPosX() < region.getRegionLeft() || link.getPosY() < region.getRegionTop() || link.getPosX() > region.getRegionRight() || link.getPosY() > region.getRegionBottom() || !link.getLinkExpression().equals(fieldKey)) continue;
                        bizKeyList.add(dataAssist.getDimensionName(fieldDefine));
                        continue block3;
                    }
                }
                if (bizKeyOrderFieldDefine == null) continue;
                bizKeyList.add("RECORDKEY");
            }
        }
        catch (Exception e) {
            this.logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return bizKeyList;
    }

    public void finish() {
        super.finish();
        ITaskOptionController systemOptionManager = (ITaskOptionController)BeanUtil.getBean(ITaskOptionController.class);
        String checkStatusMessage = systemOptionManager.getValue(this.formScheme.getTaskKey(), "CHECK_STATUS_ON_OFF");
        if (checkStatusMessage.equals("1")) {
            MessagePublisher messageService = (MessagePublisher)BeanUtil.getBean(MessagePublisher.class);
            FormCheckStatusMessage message = new FormCheckStatusMessage();
            message.setContext(this.jtableContext);
            message.setStatusList(new ArrayList<FormCheckStatusInfo>(this.checkStatusMap.values()));
            NpContext context = NpContextHolder.getContext();
            String userTenant = context.getTenant();
            message.setTenant(userTenant);
            String identityId = context.getIdentityId();
            if (identityId != null) {
                message.setIdentityId(identityId);
            }
            message.setUserName(context.getUser().getName());
            try {
                messageService.publishMessage("MESSAGE_CHANNE_FORM_CHECK_STATUS", (Object)message);
            }
            catch (Exception e) {
                this.logger.error("\u53d1\u9001\u5ba1\u6838\u72b6\u6001\u6d88\u606f", e);
            }
        }
    }
}

