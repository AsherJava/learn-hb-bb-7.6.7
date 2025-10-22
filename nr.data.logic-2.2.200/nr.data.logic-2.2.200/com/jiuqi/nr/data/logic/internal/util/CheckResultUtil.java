/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.INumberData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.node.NodeAdapter
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.common.EnumTransUtils
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.encryption.desensitization.common.DesensitizedEncryptor
 */
package com.jiuqi.nr.data.logic.internal.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.INumberData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.node.NodeAdapter;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.extend.param.CheckDesContext;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultObj;
import com.jiuqi.nr.data.logic.facade.param.input.DefaultQueryFilter;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCol;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCondition;
import com.jiuqi.nr.data.logic.facade.param.input.QueryConditionBuilder;
import com.jiuqi.nr.data.logic.facade.param.input.QueryFilterOperator;
import com.jiuqi.nr.data.logic.facade.param.output.CheckDescription;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultData;
import com.jiuqi.nr.data.logic.facade.param.output.DesCheckState;
import com.jiuqi.nr.data.logic.facade.param.output.FormulaData;
import com.jiuqi.nr.data.logic.facade.param.output.FormulaNode;
import com.jiuqi.nr.data.logic.facade.param.output.RelatedTaskInfo;
import com.jiuqi.nr.data.logic.internal.entity.FmlCheckResultEntity;
import com.jiuqi.nr.data.logic.internal.helper.CKDValCollectorCache;
import com.jiuqi.nr.data.logic.internal.helper.CKDValidateCollector;
import com.jiuqi.nr.data.logic.internal.obj.CKDValidInfo;
import com.jiuqi.nr.data.logic.internal.obj.CKRPackageInfo;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.obj.FormulaNodeSaveInfo;
import com.jiuqi.nr.data.logic.internal.obj.FormulaNodeSaveObj;
import com.jiuqi.nr.data.logic.internal.obj.LinkEnv;
import com.jiuqi.nr.data.logic.internal.obj.NodeInfoQueryCache;
import com.jiuqi.nr.data.logic.internal.provider.LinkEnvProvider;
import com.jiuqi.nr.data.logic.internal.provider.impl.LinkEnvProviderImpl;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.IDimDataLoader;
import com.jiuqi.nr.datascheme.api.common.EnumTransUtils;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.encryption.desensitization.common.DesensitizedEncryptor;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

public class CheckResultUtil {
    private static final Logger logger = LoggerFactory.getLogger(CheckResultUtil.class);
    private static final IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)BeanUtil.getBean(IFormulaRunTimeController.class);
    private static final IRunTimeViewController runtimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
    private static final IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
    private static final IFMDMAttributeService fMDMAttributeService = (IFMDMAttributeService)BeanUtil.getBean(IFMDMAttributeService.class);
    private static final IColumnModelFinder columnModelFinder = (IColumnModelFinder)BeanUtil.getBean(IColumnModelFinder.class);
    private static final DataModelService dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
    private static final ZoneId zoneId = ZoneId.systemDefault();
    public static final String YEAR_MONTH_DAY_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<FmlCheckResultEntity> orderCheckResult(List<FmlCheckResultEntity> fmlCheckResultEntities) {
        if (!CollectionUtils.isEmpty(fmlCheckResultEntities)) {
            List orderedDimColNames = fmlCheckResultEntities.get(0).getDimMap().keySet().stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            try {
                return fmlCheckResultEntities.stream().sorted(Comparator.comparing(FmlCheckResultEntity::getFormOrder).thenComparing(FmlCheckResultEntity::getFormulaCheckType, Comparator.reverseOrder()).thenComparing(FmlCheckResultEntity::getFormulaOrder).thenComparing(FmlCheckResultEntity::getGlobRow).thenComparing(FmlCheckResultEntity::getGlobCol).thenComparing(o -> CheckResultUtil.getDimOrderKey(o, orderedDimColNames)).thenComparing(FmlCheckResultEntity::getDimStr)).collect(Collectors.toList());
            }
            catch (Exception e) {
                logger.error("\u5ba1\u6838\u7ed3\u679c\u6392\u5e8f\u5f02\u5e38:{}", (Object)e.getMessage(), (Object)e);
            }
        }
        return fmlCheckResultEntities;
    }

    private static String getDimOrderKey(FmlCheckResultEntity fmlCheckResultEntity, List<String> orderedDimColNames) {
        if (CollectionUtils.isEmpty(orderedDimColNames) || orderedDimColNames.size() <= 2) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        orderedDimColNames.forEach(colName -> sb.append(fmlCheckResultEntity.getDimMap().get(colName)));
        return sb.toString();
    }

    public static String getFormOrder(String formKey) {
        FormDefine formDefine = runtimeViewController.queryFormById(formKey);
        if (formDefine == null) {
            return "Z";
        }
        return formDefine.getOrder();
    }

    public static String getFormulaOrder(String formulaKey) {
        FormulaDefine formulaDefine = formulaRunTimeController.queryFormulaDefine(formulaKey);
        return formulaDefine == null ? null : formulaDefine.getOrder();
    }

    public static String getDimStr(DimensionValueSet dimensionValueSet, List<String> formSchemeEntityNames) {
        StringBuilder dimStrBuf = new StringBuilder();
        for (int dimIndex = 0; dimIndex < dimensionValueSet.size(); ++dimIndex) {
            String dimName = dimensionValueSet.getName(dimIndex);
            if ("CKR_RECID".equals(dimName) || "ALLCKR_ASYNCTASKID".equals(dimName) || "RECORDKEY".equals(dimName) || "VERSIONID".equals(dimName) || formSchemeEntityNames.contains(dimName)) continue;
            dimStrBuf.append(dimName).append(":").append(dimensionValueSet.getValue(dimName)).append(";");
        }
        String id = null;
        if (dimensionValueSet.hasValue("RECORDKEY")) {
            id = dimensionValueSet.getValue("RECORDKEY").toString();
        }
        if (StringUtils.isEmpty(id)) {
            dimStrBuf.append("ID:null;");
        } else {
            dimStrBuf.append("ID:").append(id).append(";");
        }
        return dimStrBuf.toString();
    }

    public static FormulaNodeSaveInfo getNodes(DecimalFormat decimalFormat, FormulaCheckEventImpl event, DimensionValueSet dimensionValueSet, NodeInfoQueryCache cache) {
        ArrayList<FormulaNodeSaveObj> nodes = new ArrayList<FormulaNodeSaveObj>();
        boolean containMaskData = false;
        DimensionValueSet innerDim = CheckResultUtil.getInnerDim(dimensionValueSet, cache);
        for (NodeAdapter nodeAdapter : event.getNodes()) {
            DataModelLinkColumn dataModelLink;
            DynamicDataNode dynamicDataNode = nodeAdapter.getNodeInfo();
            if (dynamicDataNode == null) continue;
            QueryField queryField = dynamicDataNode.getQueryField();
            FormulaNodeSaveObj nodeInfo = new FormulaNodeSaveObj();
            if (queryField != null) {
                nodeInfo.setUuid(queryField.getUID());
            }
            nodeInfo.setComplex(!dynamicDataNode.isSupportLocate());
            CheckResultUtil.findRelatedTaskInfo(dimensionValueSet, cache, dynamicDataNode, nodeInfo);
            boolean nodeShowCached = cache.getNodeShowMap().containsKey(dynamicDataNode.getNodeId());
            if (nodeShowCached) {
                nodeInfo.setShow(cache.getNodeShowMap().get(dynamicDataNode.getNodeId()));
            }
            if ((dataModelLink = dynamicDataNode.getDataModelLink()) == null || StringUtils.isEmpty((String)dataModelLink.getDataLinkCode()) || dataModelLink.getReportInfo() == null) {
                if (!nodeShowCached) {
                    nodeInfo.setShow(dynamicDataNode.toString());
                    cache.getNodeShowMap().put(dynamicDataNode.getNodeId(), nodeInfo.getShow());
                }
                if (queryField != null) {
                    nodeInfo.setValue(CheckResultUtil.valueFormat(decimalFormat, nodeAdapter.getValue(), queryField.getFractionDigits()));
                } else {
                    nodeInfo.setValue(CheckResultUtil.valueFormat(decimalFormat, nodeAdapter.getValue()));
                }
            } else {
                DataLinkDefine dataLinkDefine;
                block16: {
                    FormDefine nodeForm = cache.queryFormById(runtimeViewController, dataModelLink.getReportInfo().getReportKey());
                    if (nodeForm == null || (dataLinkDefine = cache.queryDataLinkDefineByUniqueCode(runtimeViewController, nodeForm.getKey(), dataModelLink.getDataLinkCode())) == null) continue;
                    if (!nodeShowCached) {
                        nodeInfo.setShow(dataModelLink.toString());
                        cache.getNodeShowMap().put(dynamicDataNode.getNodeId(), nodeInfo.getShow());
                    }
                    nodeInfo.setKey(dataLinkDefine.getKey());
                    if (queryField != null) {
                        try {
                            FieldDefine fieldDefine = cache.findFieldDefine(columnModelFinder, dataModelLink.getColumModel());
                            if (FieldType.FIELD_TYPE_DATE == fieldDefine.getType()) {
                                nodeInfo.setValue(CheckResultUtil.valueFormatDate(nodeAdapter.getValue()));
                                break block16;
                            }
                            if (FieldType.FIELD_TYPE_DATE_TIME == fieldDefine.getType()) {
                                nodeInfo.setValue(CheckResultUtil.valueFormatDateTime(nodeAdapter.getValue()));
                                break block16;
                            }
                            nodeInfo.setValue(CheckResultUtil.valueFormat(decimalFormat, nodeAdapter.getValue(), queryField.getFractionDigits()));
                        }
                        catch (Exception e) {
                            logger.error("\u516c\u5f0f\u8282\u70b9\u8d4b\u503c\u5f02\u5e38\uff1a" + e.getMessage(), e);
                        }
                    } else {
                        nodeInfo.setValue(CheckResultUtil.valueFormat(decimalFormat, nodeAdapter.getValue()));
                    }
                }
                nodeInfo.setId(CheckResultUtil.getDataId(dataLinkDefine, innerDim, queryField, cache));
            }
            String nodeDataMaskCode = CheckResultUtil.getNodeDataMaskCode(nodeInfo, nodeAdapter.getValue(), cache);
            if (StringUtils.isNotEmpty((String)nodeDataMaskCode)) {
                containMaskData = true;
                CheckResultUtil.maskNodeValue(nodeInfo, nodeDataMaskCode, cache.getBeanHelper().getEncryptor());
            }
            nodes.add(nodeInfo);
        }
        return new FormulaNodeSaveInfo(nodes, containMaskData);
    }

    private static boolean isNodeComplex(@NonNull QueryField queryField, @NonNull DynamicDataNode dynamicDataNode) {
        QueryTable table = queryField.getTable();
        if (table == null) {
            return false;
        }
        if (queryField.getIsLj()) {
            return true;
        }
        if (queryField.getPeriodModifier() == null && queryField.getDimensionRestriction() == null) {
            return false;
        }
        boolean relateTask = StringUtils.isNotEmpty((String)dynamicDataNode.getRelateTaskItem());
        if (!relateTask) {
            return true;
        }
        DataModelLinkColumn dataModelLink = dynamicDataNode.getDataModelLink();
        if (dataModelLink == null) {
            return true;
        }
        boolean fmlPeriodModify = queryField.getPeriodModifier() != null && !queryField.getPeriodModifier().equals((Object)dataModelLink.getPeriodModifier());
        boolean fmlDimensionModify = queryField.getDimensionRestriction() != null && !queryField.getDimensionRestriction().equals((Object)dataModelLink.getDimensionRestriction());
        return fmlPeriodModify || fmlDimensionModify;
    }

    private static void findRelatedTaskInfo(DimensionValueSet dimensionValueSet, NodeInfoQueryCache cache, DynamicDataNode dynamicDataNode, FormulaNodeSaveObj nodeInfo) {
        if (nodeInfo.isComplex()) {
            return;
        }
        String taskAlias = dynamicDataNode.getRelateTaskItem();
        if (StringUtils.isEmpty((String)taskAlias)) {
            return;
        }
        NodeInfoQueryCache.RelatedTaskInfo taskByAlias = cache.getTaskByAlias(taskAlias);
        if (taskByAlias == null) {
            return;
        }
        IDataModelLinkFinder dataModelLinkFinder = cache.getDataModelLinkFinder();
        ExecutorContext executorContext = cache.getExecutorContext();
        ReportFmlExecEnvironment fmlExecEnvironment = cache.getFmlExecEnvironment();
        nodeInfo.setRelatedTaskKey(taskByAlias.getTaskKey());
        nodeInfo.setRelatedOrgId(taskByAlias.getDwEntity().getKey());
        String dwDimName = taskByAlias.getDwEntity().getDimensionName();
        List relatedUnitKey = dataModelLinkFinder.findRelatedUnitKey(executorContext, taskAlias, dwDimName, dimensionValueSet.getValue(dwDimName));
        String relatedDwDimValue = CollectionUtils.isEmpty(relatedUnitKey) ? "" : String.valueOf(relatedUnitKey.get(0));
        String relatedDwDimName = dataModelLinkFinder.getRelatedUnitDimName(executorContext, taskAlias, dwDimName);
        String relatedPeriodDimName = taskByAlias.getPeriodEntity().getDimensionName();
        String relatedPeriodDimValue = taskByAlias.getPeriod();
        TreeMap<String, String> masterKey = new TreeMap<String, String>();
        nodeInfo.setMasterKey(masterKey);
        masterKey.put(relatedDwDimName, relatedDwDimValue);
        masterKey.put(relatedPeriodDimName, relatedPeriodDimValue);
        List<EntityData> dimEntityList = taskByAlias.getDimEntityList();
        if (CollectionUtils.isEmpty(dimEntityList)) {
            return;
        }
        Map selectDims = dataModelLinkFinder.getDimValuesByLinkAlias(executorContext, taskAlias);
        if (!CollectionUtils.isEmpty(selectDims)) {
            Set dimNames = dimEntityList.stream().map(EntityData::getDimensionName).collect(Collectors.toSet());
            selectDims.forEach((k, v) -> {
                if (dimNames.contains(k) && StringUtils.isNotEmpty((String)v)) {
                    masterKey.put((String)k, (String)v);
                }
            });
        }
        for (EntityData entityData : dimEntityList) {
            if (masterKey.containsKey(entityData.getDimensionName())) continue;
            if (fmlExecEnvironment.is1v1RelationDim(executorContext, entityData.getDimensionName(), taskAlias)) {
                List relationValuesByDim = fmlExecEnvironment.getRelationValuesByDim(executorContext, entityData.getDimensionName(), relatedDwDimValue, relatedPeriodDimValue, taskAlias);
                masterKey.put(entityData.getDimensionName(), CollectionUtils.isEmpty(relationValuesByDim) ? "null" : (String)relationValuesByDim.get(0));
                continue;
            }
            masterKey.put(entityData.getDimensionName(), String.valueOf(dimensionValueSet.getValue(entityData.getDimensionName())));
        }
    }

    private static String getNodeDataMaskCode(FormulaNodeSaveObj nodeInfo, AbstractData value, NodeInfoQueryCache cache) {
        if (value.dataType != 6) {
            return null;
        }
        String columnModelKey = nodeInfo.getUuid();
        return cache.getDataFieldMaskCodeByColId(columnModelKey);
    }

    private static void maskNodeValue(FormulaNodeSaveObj nodeInfo, String maskCode, DesensitizedEncryptor encryptor) {
        if (StringUtils.isEmpty((String)maskCode)) {
            return;
        }
        String realValue = nodeInfo.getValue();
        try {
            String maskValue = encryptor.desensitize(maskCode, realValue);
            nodeInfo.setValue(maskValue);
        }
        catch (Exception e) {
            logger.warn(" \u8131\u654f\u6267\u884c\u5931\u8d25 | \u7801\u503c:{} | \u539f\u503c:{} | \u9519\u8bef:{}", maskCode, realValue, e.getMessage());
        }
    }

    private static DimensionValueSet getInnerDim(DimensionValueSet rowDim, NodeInfoQueryCache cache) {
        DimensionValueSet innerDim = new DimensionValueSet();
        for (int i = 0; i < rowDim.size(); ++i) {
            if (cache.getFormSchemeEntityNames().contains(rowDim.getName(i))) continue;
            innerDim.setValue(rowDim.getName(i), rowDim.getValue(i));
        }
        return innerDim;
    }

    public static String getDataId(DataLinkDefine dataLinkDefine, DimensionValueSet innerDim, QueryField queryField, NodeInfoQueryCache cache) {
        DataRegionDefine region = cache.queryDataRegionDefine(runtimeViewController, dataLinkDefine.getRegionKey());
        if (region.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
            return null;
        }
        List<String> regionBizKeyList = CheckResultUtil.getRegionBizKeyList(region, cache);
        StringBuilder bizKeyStrBuf = new StringBuilder();
        for (int i = 0; i < regionBizKeyList.size(); ++i) {
            String bizKey = regionBizKeyList.get(i);
            String bizKeyValue = null;
            if (innerDim.hasValue(bizKey)) {
                bizKeyValue = innerDim.getValue(bizKey).toString();
            } else if (queryField != null && queryField.getTable().getDimensionRestriction() != null) {
                DimensionValueSet dimensionRestriction = queryField.getTable().getDimensionRestriction();
                if (dimensionRestriction.hasValue(bizKey)) {
                    bizKeyValue = dimensionRestriction.getValue(bizKey).toString();
                }
            } else if (i < innerDim.size()) {
                bizKeyValue = innerDim.getValue(i).toString();
            }
            CheckResultUtil.appendDataId(bizKeyStrBuf, bizKey, bizKeyValue);
        }
        return bizKeyStrBuf.toString();
    }

    public static void appendDataId(StringBuilder bizKeyStr, String bizKeyDimName, String bizKeyValue) {
        if (StringUtils.isEmpty((String)bizKeyValue)) {
            bizKeyValue = bizKeyDimName.equals("RECORDKEY") ? bizKeyDimName : bizKeyDimName + "|EMPTYDIM";
        }
        if (bizKeyStr.length() > 0) {
            bizKeyStr.append("#^$");
        }
        bizKeyStr.append(bizKeyValue);
    }

    public static List<String> getRegionBizKeyList(DataRegionDefine region, NodeInfoQueryCache cache) {
        Map<String, List<String>> regionBizKeysMap = cache.getRegionBizKeyMap();
        if (regionBizKeysMap.containsKey(region.getKey())) {
            return regionBizKeysMap.get(region.getKey());
        }
        ArrayList<String> bizKeyList = new ArrayList<String>();
        regionBizKeysMap.put(region.getKey(), bizKeyList);
        try {
            List allLinkDefines = runtimeViewController.getAllLinksInRegion(region.getKey());
            List fieldKeys = runtimeViewController.getFieldKeysInRegion(region.getKey());
            List tableDefines = dataDefinitionRuntimeController.queryTableDefinesByFields((Collection)fieldKeys);
            ExecutorContext context = new ExecutorContext(dataDefinitionRuntimeController);
            IDataAccessProvider dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
            IDataAssist dataAssist = dataAccessProvider.newDataAssist(context);
            if (tableDefines == null || tableDefines.isEmpty()) {
                return bizKeyList;
            }
            for (TableDefine table : tableDefines) {
                String[] bizKeyFieldsID = table.getBizKeyFieldsID();
                FieldDefine bizKeyOrderFieldDefine = null;
                block3: for (String fieldKey : bizKeyFieldsID) {
                    FieldDefine fieldDefine = cache.queryFieldDefine(runtimeViewController, fieldKey);
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
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return bizKeyList;
    }

    public static String valueFormatDateTime(AbstractData value) {
        long dateTime;
        try {
            dateTime = value.getAsDateTime();
        }
        catch (DataTypeException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
        return CheckResultUtil.dateToStringTime(new Date(dateTime));
    }

    public static String valueFormatDate(AbstractData value) {
        try {
            Date date = value.getAsDateObj();
            return CheckResultUtil.dateToString(date);
        }
        catch (DataTypeException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        LocalDate localDateTime = instant.atZone(zoneId).toLocalDate();
        return formatter.format(localDateTime);
    }

    public static String dateToStringTime(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return formatterTime.format(localDateTime);
    }

    public static String valueFormat(DecimalFormat decimalFormat, AbstractData valueData) {
        if (valueData instanceof INumberData) {
            BigDecimal bigDecimal = null;
            try {
                bigDecimal = valueData.getAsCurrency();
            }
            catch (DataTypeException e) {
                logger.error("\u683c\u5f0f\u5316\u5ba1\u6838\u9519\u8bef\u6570\u636e\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            if (bigDecimal != null) {
                decimalFormat.setMaximumFractionDigits(bigDecimal.scale());
                decimalFormat.setMinimumFractionDigits(bigDecimal.scale());
                return decimalFormat.format(bigDecimal);
            }
            return "0";
        }
        String value = valueData == null ? "" : valueData.getAsString();
        return value;
    }

    public static String valueFormat(DecimalFormat decimalFormat, AbstractData valueData, int fractionDigits) {
        if (valueData instanceof INumberData) {
            BigDecimal bigDecimal = null;
            try {
                bigDecimal = valueData.getAsCurrency();
            }
            catch (DataTypeException e) {
                logger.error("\u683c\u5f0f\u5316\u5ba1\u6838\u9519\u8bef\u6570\u636e\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            decimalFormat.setMaximumFractionDigits(fractionDigits);
            decimalFormat.setMinimumFractionDigits(fractionDigits);
            if (bigDecimal != null) {
                return decimalFormat.format(bigDecimal);
            }
            return decimalFormat.format(0L);
        }
        String value = valueData == null ? "" : valueData.getAsString();
        return value;
    }

    public static String buildCheckRecordMapKey(String formKey, DimensionValueSet dimensionValueSet) {
        return formKey + ";" + dimensionValueSet.toString();
    }

    public static String buildRECID(String formulaSchemeKey, String formKey, String formulaKey, int globRow, int globCol, Map<String, DimensionValue> dimensionSet) {
        FormulaSchemeDefine formulaSchemeDefine = formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
        assert (formulaSchemeDefine != null) : "\u516c\u5f0f\u65b9\u6848\u672a\u627e\u5230";
        String formSchemeKey = formulaSchemeDefine.getFormSchemeKey();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (DimensionValue value : dimensionSet.values()) {
            dimensionValueSet.setValue(value.getName(), (Object)value.getValue());
        }
        if (dimensionValueSet.hasValue("RECORDKEY")) {
            Object value = dimensionValueSet.getValue("RECORDKEY");
            dimensionValueSet.clearValue("RECORDKEY");
            dimensionValueSet.setValue("ID", value);
        } else if (!dimensionValueSet.hasValue("ID")) {
            dimensionValueSet.setValue("ID", (Object)"null");
        }
        if (dimensionValueSet.hasValue("VERSIONID")) {
            dimensionValueSet.clearValue("VERSIONID");
        }
        if (dimensionValueSet.hasValue("ALLCKR_ASYNCTASKID")) {
            dimensionValueSet.clearValue("ALLCKR_ASYNCTASKID");
        }
        if (dimensionValueSet.hasValue("CKR_BATCH_ID")) {
            dimensionValueSet.clearValue("CKR_BATCH_ID");
        }
        String masterStr = formSchemeKey + ";" + formulaSchemeKey + ";" + formKey + ";" + formulaKey + ";" + DataEngineConsts.FormulaType.CHECK.getValue() + ";" + globRow + ";" + globCol + ";" + dimensionValueSet;
        return CheckResultUtil.toFakeUUID(masterStr).toString();
    }

    public static UUID toFakeUUID(String string) {
        int i;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException nsae) {
            throw new InternalError("MD5 not supported", nsae);
        }
        byte[] data = md.digest(string.getBytes());
        long msb = 0L;
        long lsb = 0L;
        assert (data.length == 16) : "data must be 16 bytes in length";
        for (i = 0; i < 8; ++i) {
            msb = msb << 8 | (long)(data[i] & 0xFF);
        }
        for (i = 8; i < 16; ++i) {
            lsb = lsb << 8 | (long)(data[i] & 0xFF);
        }
        return new UUID(msb, lsb);
    }

    public static String buildAllCheckCacheKey(String formulaSchemeKey, DimensionValueSet dimensionValueSet) {
        for (int dimIndex = 0; dimIndex < dimensionValueSet.size(); ++dimIndex) {
            Object dimValue = dimensionValueSet.getValue(dimIndex);
            if (!(dimValue instanceof List)) continue;
            List dimValueList = (List)dimValue;
            Collections.sort(dimValueList);
            dimensionValueSet.setValue(dimensionValueSet.getName(dimIndex), (Object)dimValueList);
        }
        String masterStr = formulaSchemeKey + ";" + dimensionValueSet;
        String cacheKey = CheckResultUtil.toFakeUUID(masterStr).toString();
        logger.debug("{}   CheckResultUtil\u5168\u5ba1\u7ed3\u679c\u7f13\u5b58masterStr :{}; cacheKey:{}", new Date(), masterStr, cacheKey);
        return cacheKey;
    }

    public static CheckResult packageCheckResult(List<FmlCheckResultEntity> checkResultEntities, List<CheckDesObj> checkDesObjs, CKRPackageInfo ckrPackageInfo) {
        Map<Integer, Boolean> checkTypes = ckrPackageInfo.getCheckTypes();
        QueryCondition queryCondition = ckrPackageInfo.getQueryCondition();
        FormulaShowInfo formulaShowInfo = ckrPackageInfo.getFormulaShowInfo();
        QueryContext queryContext = ckrPackageInfo.getQueryContext();
        Map<String, IDimDataLoader> entityDataLoaderMap = ckrPackageInfo.getEntityDataLoaderMap();
        Map<String, String> colDimNameMap = ckrPackageInfo.getColDimNameMap();
        CKDValCollectorCache ckdValCollectorCache = ckrPackageInfo.getCkdValCollectorCache();
        String formSchemeKey = ckrPackageInfo.getFormSchemeKey();
        HashMap<String, Integer> ckdDescIndexMap = new HashMap<String, Integer>();
        CheckResult checkResult = new CheckResult();
        Map<String, CheckDescription> checkDesObjMap = checkDesObjs.stream().collect(Collectors.toMap(CheckDesObj::getRecordId, CheckDesObj::getCheckDescription, (o1, o2) -> o2));
        checkResult.setMessage("success");
        ParamUtil paramUtil = (ParamUtil)BeanUtil.getBean(ParamUtil.class);
        Map<Integer, String> codeTitleMap = paramUtil.getCheckTypeCodeTitleMap();
        if (checkResultEntities != null) {
            HashMap<String, Integer> dimensionListIndexMap = new HashMap<String, Integer>();
            HashMap<String, String> unitKeyTitleMap = new HashMap<String, String>(1000);
            HashMap<String, String> unitKeyCodeMap = new HashMap<String, String>(1000);
            IDimDataLoader dwEntityDataLoader = entityDataLoaderMap.get(colDimNameMap.get("MDCODE"));
            LinkEnvProviderImpl linkEnvProvider = new LinkEnvProviderImpl(runtimeViewController, dataDefinitionRuntimeController, fMDMAttributeService);
            for (FmlCheckResultEntity checkResultEntity : checkResultEntities) {
                String recId = checkResultEntity.getRecId();
                CheckResultObj checkResultObj = new CheckResultObj(checkResultEntity, checkDesObjMap.get(recId));
                if (!CheckResultUtil.filterCheckTypes(checkResultEntity, checkDesObjMap, checkTypes) || !CheckResultUtil.filterQueryCon(checkResultObj, queryCondition)) continue;
                DimensionValueSet rowDim = DimensionUtil.getDimFromCKREntity(checkResultEntity.getDimMap(), colDimNameMap);
                CheckResultData checkResultData = new CheckResultData();
                checkResultData.setRecordId(recId);
                checkResultData.setLeft(checkResultEntity.getLeft());
                checkResultData.setRight(checkResultEntity.getRight());
                checkResultData.setDifference(checkResultEntity.getBalance());
                FormulaData formulaData = CheckResultUtil.getFormulaData(checkResultEntity, queryContext, formulaShowInfo);
                checkResultData.setFormulaData(formulaData);
                String unitKey = checkResultEntity.getDimMap().get("MDCODE");
                checkResultData.setUnitKey(unitKey);
                if (unitKeyTitleMap.containsKey(unitKey)) {
                    checkResultData.setUnitTitle((String)unitKeyTitleMap.get(unitKey));
                    checkResultData.setUnitCode((String)unitKeyCodeMap.get(unitKey));
                } else {
                    String title = dwEntityDataLoader.getTitleByEntityDataKey(unitKey);
                    String code = dwEntityDataLoader.getCodeByEntityDataKey(unitKey);
                    checkResultData.setUnitTitle(title);
                    checkResultData.setUnitCode(code);
                    unitKeyTitleMap.put(unitKey, title);
                    unitKeyCodeMap.put(unitKey, code);
                }
                List<FormulaNode> formulaNodes = CheckResultUtil.getFormulaNodes(checkResultEntity.getErrorDesc(), linkEnvProvider);
                checkResultData.setFormulaNodeList(formulaNodes);
                try {
                    checkResultData.setDimensionTitle(DimensionUtil.getDimensionTitle(rowDim, entityDataLoaderMap));
                }
                catch (Exception e) {
                    logger.error("\u5ba1\u6838\u7ed3\u679c\u5173\u8054\u5b9e\u4f53\u67e5\u8be2\u5f02\u5e38" + e.getMessage(), e);
                    checkResultData.setDimensionTitle(new HashMap<String, String>());
                }
                CheckResultUtil.fillDimStr(rowDim, checkResultEntity.getDimStr());
                CheckResultUtil.fillCheckResultDimIndex(checkResult, dimensionListIndexMap, rowDim, checkResultData);
                if (checkDesObjMap.containsKey(recId)) {
                    checkResultData.setCheckDescription(checkDesObjMap.get(recId));
                    CheckDesContext checkDesContext = new CheckDesContext(formSchemeKey, checkResultData.getFormulaData().getFormulaSchemeKey());
                    CKDValidateCollector ckdValidateCollector = ckdValCollectorCache.getCKDValidateCollector(checkDesContext);
                    CheckResultUtil.fillCKDDescIndex(checkResult, ckdDescIndexMap, checkResultData, ckdValidateCollector);
                }
                int checkType = checkResultEntity.getFormulaCheckType();
                CheckResultUtil.fillCheckTypeCountMap(checkResult, codeTitleMap, checkType);
                checkResult.getResultData().add(checkResultData);
            }
        }
        checkResult.setTotalCount(checkResult.getResultData().size());
        return checkResult;
    }

    private static boolean filterQueryCon(CheckResultObj checkResultObj, QueryCondition queryCondition) {
        return queryCondition == null || queryCondition.filter(checkResultObj);
    }

    public static void fillCheckResultDimIndex(CheckResult checkResult, Map<String, Integer> dimensionListIndexMap, DimensionValueSet dataRowDimensionValueSet, CheckResultData checkResultData) {
        String dimensionValueSetKey = dataRowDimensionValueSet.toString();
        if (dimensionListIndexMap.containsKey(dimensionValueSetKey)) {
            checkResultData.setDimensionIndex(dimensionListIndexMap.get(dimensionValueSetKey));
        } else {
            Map<String, DimensionValue> dimensionSet = DimensionUtil.getDimensionSet(dataRowDimensionValueSet);
            checkResult.getDimensionList().add(dimensionSet);
            int dimensionValueSetIndex = checkResult.getDimensionList().size() - 1;
            dimensionListIndexMap.put(dimensionValueSetKey, dimensionValueSetIndex);
            checkResultData.setDimensionIndex(dimensionValueSetIndex);
        }
    }

    public static void fillCKDDescIndex(CheckResult checkResult, Map<String, Integer> ckdDescIndexMap, CheckResultData checkResultData, CKDValidateCollector ckdValidateCollector) {
        CheckDescription checkDescription = checkResultData.getCheckDescription();
        if (checkDescription != null && DesCheckState.FAIL == checkDescription.getState()) {
            CheckDesObj checkDesByCKR = CheckResultUtil.getCheckDesByCKR(checkResult, checkResultData);
            CKDValidInfo validInfo = ckdValidateCollector.getValidInfo(checkDesByCKR);
            List<String> errorMsgList = validInfo.getErrorMsgList();
            if (validInfo.isPass() || CollectionUtils.isEmpty(errorMsgList)) {
                return;
            }
            checkResultData.setCkdDescIndexList(new ArrayList<Integer>());
            for (String msg : errorMsgList) {
                if (ckdDescIndexMap.containsKey(msg)) {
                    checkResultData.getCkdDescIndexList().add(ckdDescIndexMap.get(msg));
                    continue;
                }
                List<String> ckdDescList = checkResult.getCkdDescList();
                ckdDescList.add(msg);
                int index = ckdDescList.size() - 1;
                ckdDescIndexMap.put(msg, index);
                checkResultData.getCkdDescIndexList().add(index);
            }
        }
    }

    public static CheckDesObj getCheckDesByCKR(CheckResult checkResult, CheckResultData checkResultData) {
        CheckDesObj checkDesObj = new CheckDesObj();
        FormulaData formulaData = checkResultData.getFormulaData();
        checkDesObj.setFormulaSchemeKey(formulaData.getFormulaSchemeKey());
        checkDesObj.setFormKey(formulaData.getFormKey());
        checkDesObj.setFormulaExpressionKey(formulaData.getParsedExpressionKey());
        checkDesObj.setFormulaCode(formulaData.getCode());
        checkDesObj.setGlobRow(formulaData.getGlobRow());
        checkDesObj.setGlobCol(formulaData.getGlobCol());
        checkDesObj.setDimensionSet(checkResult.getDimensionList().get(checkResultData.getDimensionIndex()));
        checkDesObj.setCheckDescription(checkResultData.getCheckDescription());
        return checkDesObj;
    }

    public static void fillCheckTypeCountMap(CheckResult checkResult, Map<Integer, String> codeTitleMap, int checkType) {
        String title = codeTitleMap.get(checkType);
        if (checkResult.getCheckTypeCountMap().containsKey(title)) {
            Integer integer = checkResult.getCheckTypeCountMap().get(title);
            integer = integer + 1;
            checkResult.getCheckTypeCountMap().put(title, integer);
        } else {
            checkResult.getCheckTypeCountMap().put(title, 1);
        }
    }

    private static boolean filterCheckTypes(FmlCheckResultEntity checkResultEntity, Map<String, CheckDescription> checkDesMap, Map<Integer, Boolean> checkTypes) {
        if (checkTypes.isEmpty()) {
            return true;
        }
        if (checkTypes.containsKey(checkResultEntity.getFormulaCheckType())) {
            Boolean aBoolean = checkTypes.get(checkResultEntity.getFormulaCheckType());
            if (aBoolean == null) {
                return true;
            }
            CheckDescription checkDescription = checkDesMap.get(checkResultEntity.getRecId());
            if (aBoolean.booleanValue()) {
                return checkDescription != null && StringUtils.isNotEmpty((String)checkDescription.getDescription());
            }
            return checkDescription == null || StringUtils.isEmpty((String)checkDescription.getDescription());
        }
        return false;
    }

    public static List<FormulaNode> getFormulaNodes(String nodesJson, LinkEnvProvider linkEnvProvider) {
        if (StringUtils.isNotEmpty((String)nodesJson)) {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{FormulaNodeSaveObj.class});
            List nodes = new ArrayList();
            try {
                nodes = (List)mapper.readValue(nodesJson, javaType);
            }
            catch (IOException e) {
                logger.error("\u516c\u5f0f\u8282\u70b9\u4fe1\u606f\u53cd\u5e8f\u5217\u5316\u5f02\u5e38,\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            ArrayList<FormulaNode> formulaNodes = new ArrayList<FormulaNode>();
            for (FormulaNodeSaveObj node : nodes) {
                FormulaNode formulaNode = new FormulaNode();
                if (!StringUtils.isEmpty((String)node.getKey())) {
                    formulaNode.setDataLinkKey(node.getKey());
                    LinkEnv linkEnv = linkEnvProvider.get(formulaNode.getDataLinkKey());
                    if (linkEnv != null) {
                        formulaNode.setFormKey(linkEnv.getFormKey());
                        formulaNode.setFormTitle(linkEnv.getFormTitle());
                        formulaNode.setRegionKey(linkEnv.getRegionKey());
                        formulaNode.setFieldKey(linkEnv.getFieldKey());
                        formulaNode.setFieldTitle(linkEnv.getFieldTitle());
                        formulaNode.setFieldType(linkEnv.getFieldType());
                    }
                }
                if (StringUtils.isEmpty((String)formulaNode.getFieldKey())) {
                    FieldDefine fieldDefine = columnModelFinder.findFieldDefineByColumnId(node.getUuid());
                    if (fieldDefine == null) {
                        ColumnModelDefine column = dataModelService.getColumnModelDefineByID(node.getUuid());
                        if (column != null) {
                            formulaNode.setFieldKey(column.getID());
                            formulaNode.setFieldTitle(column.getTitle());
                            formulaNode.setFieldType(EnumTransUtils.valueOf((ColumnModelType)column.getColumnType()).name());
                        }
                    } else {
                        formulaNode.setFieldKey(fieldDefine.getKey());
                        formulaNode.setFieldTitle(fieldDefine.getTitle());
                        formulaNode.setFieldType(fieldDefine.getType().name());
                    }
                }
                formulaNode.setNodeShow(node.getShow());
                formulaNode.setDataId(node.getId());
                formulaNode.setValue(node.getValue());
                formulaNode.setComplex(node.isComplex());
                if (StringUtils.isNotEmpty((String)node.getRelatedTaskKey())) {
                    RelatedTaskInfo relatedTaskInfo = new RelatedTaskInfo(node.getRelatedTaskKey(), node.getRelatedOrgId(), node.getMasterKey());
                    formulaNode.setRelatedTaskInfo(relatedTaskInfo);
                }
                formulaNodes.add(formulaNode);
            }
            return formulaNodes;
        }
        return Collections.emptyList();
    }

    private static FormulaData getFormulaData(FmlCheckResultEntity checkResultEntity, QueryContext queryContext, FormulaShowInfo formulaShowInfo) {
        FormulaData formulaData = new FormulaData();
        String formulaSchemeKey = checkResultEntity.getFormulaSchemeKey();
        formulaData.setFormulaSchemeKey(formulaSchemeKey);
        String formKey = checkResultEntity.getFormKey();
        FormDefine formDefine = runtimeViewController.queryFormById(formKey);
        String formTitle = formDefine == null ? "\u8868\u95f4" : formDefine.getTitle();
        formulaData.setFormKey(formKey);
        formulaData.setFormTitle(formTitle);
        String expressionKey = checkResultEntity.getFormulaExpressionKey();
        formulaData.setParsedExpressionKey(expressionKey);
        formulaData.setKey(checkResultEntity.getFormulaKey());
        formulaData.setCheckType(checkResultEntity.getFormulaCheckType());
        formulaData.setGlobCol(checkResultEntity.getGlobCol());
        formulaData.setGlobRow(checkResultEntity.getGlobRow());
        IParsedExpression parsedExpression = formulaRunTimeController.getParsedExpression(formulaSchemeKey, formKey, expressionKey);
        if (parsedExpression != null) {
            String formula = "";
            try {
                formula = parsedExpression.getFormula(queryContext, formulaShowInfo);
            }
            catch (InterpretException e) {
                logger.error("\u516c\u5f0f\u5185\u5bb9\u89e3\u6790\u5f02\u5e38:" + e.getMessage(), e);
            }
            formulaData.setFormula(formula);
            formulaData.setCode(parsedExpression.getSource().getCode());
            formulaData.setMeaning(parsedExpression.getSource().getMeanning());
        }
        formulaData.setOrder(checkResultEntity.getFormulaOrder());
        return formulaData;
    }

    public static void fillDimStr(DimensionValueSet dimensionValueSet, String dimStr) {
        if (StringUtils.isNotEmpty((String)dimStr)) {
            String[] dims;
            for (String dim : dims = dimStr.split(";")) {
                String[] dimValues = dim.split(":");
                if (dimValues.length != 2) continue;
                dimensionValueSet.setValue(dimValues[0], (Object)dimValues[1]);
            }
        }
    }

    public static String removeUTF0Char(String str) {
        return str == null ? null : str.replace("\u0000", "");
    }

    public static QueryCondition buildQueryConByCheckType(Map<Integer, Boolean> checkTypes) {
        QueryConditionBuilder queryConditionBuilder;
        if (CollectionUtils.isEmpty(checkTypes)) {
            return DefaultQueryFilter.NO_FILTER;
        }
        Optional<Boolean> any = checkTypes.values().stream().filter(Objects::nonNull).findAny();
        if (any.isPresent()) {
            queryConditionBuilder = null;
            for (Map.Entry<Integer, Boolean> e : checkTypes.entrySet()) {
                Integer checkType = e.getKey();
                Boolean writeDes = e.getValue();
                QueryConditionBuilder subQuery = new QueryConditionBuilder(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.EQUALS, checkType);
                if (writeDes != null) {
                    if (writeDes.booleanValue()) {
                        subQuery.andSubQuery(DefaultQueryFilter.DES_IS_NOTNULL);
                    } else {
                        subQuery.andSubQuery(DefaultQueryFilter.DES_IS_NULL);
                    }
                }
                if (queryConditionBuilder == null) {
                    queryConditionBuilder = new QueryConditionBuilder(subQuery.build());
                    continue;
                }
                queryConditionBuilder.orSubQuery(subQuery.build());
            }
        } else {
            queryConditionBuilder = new QueryConditionBuilder(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.IN, new ArrayList<Integer>(checkTypes.keySet()));
        }
        return queryConditionBuilder.build();
    }
}

