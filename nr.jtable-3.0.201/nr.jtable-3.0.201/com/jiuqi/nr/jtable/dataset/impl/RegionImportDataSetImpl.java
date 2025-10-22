/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DuplicateRowKeysException
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.exception.IncorrectRowKeysException
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ImportErrorDataInfo
 *  com.jiuqi.nr.common.importdata.ImportResultRegionObject
 *  com.jiuqi.nr.common.importdata.SaveErrorDataInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.sensitive.bean.daoObject.SensitiveWordDaoObject
 *  com.jiuqi.nr.sensitive.service.CheckSensitiveWordService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DuplicateRowKeysException;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.exception.IncorrectRowKeysException;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ImportErrorDataInfo;
import com.jiuqi.nr.common.importdata.ImportResultRegionObject;
import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.dataset.AbstractRegionQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.IRegionImportDataSet;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionQueryTabeStrategy;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.impl.SimpleRegionQueryTabeStrategy;
import com.jiuqi.nr.jtable.dataset.impl.SimpleRegionRelationEvn;
import com.jiuqi.nr.jtable.exception.DataEngineQueryException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import com.jiuqi.nr.jtable.params.base.TableData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.RegionDataCommitSet;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.input.ReportDataCommitSet;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.FloatOrderGenerator;
import com.jiuqi.nr.jtable.util.RegionSettingUtil;
import com.jiuqi.nr.sensitive.bean.daoObject.SensitiveWordDaoObject;
import com.jiuqi.nr.sensitive.service.CheckSensitiveWordService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegionImportDataSetImpl
implements IRegionImportDataSet {
    private static final Logger logger = LoggerFactory.getLogger(RegionImportDataSetImpl.class);
    private IJtableDataEngineService jtableDataEngineService;
    private INvwaSystemOptionService iNvwaSystemOptionService;
    private CheckSensitiveWordService checkSensitiveWordService;
    private IJtableParamService jtableParamService;
    private AbstractRegionRelationEvn regionRelationEvn;
    private IDataTable dataTable;
    private RegionQueryInfo regionQueryInfo;
    private JtableContext jtableContext;
    private RegionData regionData;
    private DataFormaterCache dataFormaterCache;
    private List<String> calcDataLinks;
    private Map<String, String> dimCache = new HashMap<String, String>();
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private IJtableResourceService jtableResourceService;
    private IRunTimeViewController runTimeViewController;
    private LinkedHashMap<String, Object> fmdmDataMap = new LinkedHashMap();
    private IEntityMetaService entityMetaService;
    private IEntityDataService entityDataService;
    private IEntityViewRunTimeController runTimeController;
    private DataEntityFullService dataEntityFullService;
    private boolean saveFileGroupKey = false;
    public static final String FMDM_REPEAT_FIELD = "_FMDM_REPEAT_FIELD";

    public RegionImportDataSetImpl(JtableContext jtableContext, RegionData regionData) {
        this.regionData = regionData;
        this.jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
        this.iNvwaSystemOptionService = (INvwaSystemOptionService)BeanUtil.getBean(INvwaSystemOptionService.class);
        this.jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        this.checkSensitiveWordService = (CheckSensitiveWordService)BeanUtil.getBean(CheckSensitiveWordService.class);
        this.jtableResourceService = (IJtableResourceService)BeanUtil.getBean(IJtableResourceService.class);
        this.runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        this.entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        this.entityDataService = (IEntityDataService)BeanUtil.getBean(IEntityDataService.class);
        this.runTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        this.dataEntityFullService = (DataEntityFullService)BeanUtil.getBean(DataEntityFullService.class);
        this.jtableContext = jtableContext;
        this.calcDataLinks = this.jtableParamService.getCalcDataLinks(jtableContext);
        this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        this.regionRelationEvn = regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue() ? new SimpleRegionRelationEvn(regionData, jtableContext) : new FloatRegionRelationEvn(regionData, jtableContext);
        this.dataFormaterCache = this.regionRelationEvn.getDataFormaterCache();
        String entity_matchAll = this.iNvwaSystemOptionService.get("nr-data-entry-group", "JTABLE_ENTITY_MATCH_ALL");
        this.dataFormaterCache.setEntityMatchAll("1".equals(entity_matchAll));
        this.regionQueryInfo = new RegionQueryInfo();
        this.regionRelationEvn.setPaginate(false);
        this.regionRelationEvn.setCommitData(true);
        this.createDataQuery();
    }

    private void createDataQuery() {
        AbstractRegionQueryTableStrategy queryTabeStrategy = null;
        List<FieldData> fieldDefineList = this.getFieldDataList();
        if (fieldDefineList.isEmpty()) {
            return;
        }
        IDataQuery dataQuery = this.jtableDataEngineService.getDataQuery(this.jtableContext, this.regionData.getKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(this.jtableContext);
        RegionSettingUtil.rebuildMasterKeyByRegion(this.regionData, dimensionValueSet, this.regionRelationEvn);
        dataQuery.setMasterKeys(this.getCollectionDim(dimensionValueSet));
        this.regionQueryInfo.setContext(this.jtableContext);
        queryTabeStrategy = this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue() ? new FloatRegionQueryTabeStrategy(dataQuery, this.regionRelationEvn, this.regionQueryInfo) : new SimpleRegionQueryTabeStrategy(dataQuery, this.regionRelationEvn, this.regionQueryInfo);
        FormData formData = this.jtableParamService.getReport(this.jtableContext.getFormKey(), this.jtableContext.getFormSchemeKey());
        if (!FormType.FORM_TYPE_NEWFMDM.name().equals(formData.getFormType())) {
            this.dataTable = queryTabeStrategy.getRegionModifyTable();
            boolean isAppending = false;
            if (this.jtableContext.getVariableMap().get("isAppending") != null) {
                isAppending = (Boolean)this.jtableContext.getVariableMap().get("isAppending");
            }
            if (this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue() && !isAppending) {
                this.dataTable.deleteAll();
            }
        }
    }

    @Override
    public void commitRangeData() {
        List<FieldData> fieldDefineList = this.getFieldDataList();
        if (fieldDefineList.isEmpty()) {
            return;
        }
        FormData formData = this.jtableParamService.getReport(this.jtableContext.getFormKey(), this.jtableContext.getFormSchemeKey());
        if (FormType.FORM_TYPE_NEWFMDM.name().equals(formData.getFormType())) {
            try {
                Map<String, DimensionValue> dimensionSet = this.jtableContext.getDimensionSet();
                DimensionValue dimensionValue = dimensionSet.get("DATATIME");
                String period = dimensionValue.getValue();
                this.commitFmdmData(this.jtableContext.getFormSchemeKey(), period);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (!this.regionRelationEvn.getAllowDuplicate()) {
                this.dataTable.needCheckDuplicateKeys(true);
            }
            try {
                this.dataTable.commitChanges(true);
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{e.getLocalizedMessage()});
            }
        }
    }

    @Override
    public RegionData getRegionData() {
        return this.regionData;
    }

    @Override
    public List<LinkData> getLinkDataList() {
        return this.jtableParamService.getLinks(this.regionData.getKey());
    }

    @Override
    public boolean isFloatRegion() {
        return this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue();
    }

    @Override
    public FieldData getFieldDataByDataLink(LinkData linkData) {
        return this.regionRelationEvn.getFieldByDataLink(linkData.getKey());
    }

    @Override
    public List<FieldData> getFieldDataList() {
        ArrayList<FieldData> fieldDefines = new ArrayList<FieldData>();
        FieldData fieldData = null;
        FormData formData = this.jtableParamService.getReport(this.jtableContext.getFormKey(), this.jtableContext.getFormSchemeKey());
        if (FormType.FORM_TYPE_NEWFMDM.name().equals(formData.getFormType())) {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(this.jtableContext.getTaskKey());
            Map<String, IFMDMAttribute> fmdmAttribute = this.jtableResourceService.getFMDMAttribute(taskDefine.getDw(), this.jtableContext.getFormSchemeKey());
            List<LinkData> allLinks = this.jtableParamService.getLinks(this.regionData.getKey());
            HashedMap<String, LinkData> collect = new HashedMap<String, LinkData>();
            for (LinkData linkData : allLinks) {
                collect.put(linkData.getZbcode(), linkData);
            }
            for (Map.Entry entry : fmdmAttribute.entrySet()) {
                IFMDMAttribute value = (IFMDMAttribute)entry.getValue();
                fieldData = new FieldData();
                fieldData.setFieldCode(value.getCode());
                fieldData.setFieldName(value.getName());
                fieldData.setFieldTitle(value.getTitle());
                if (!collect.containsKey(value.getCode())) continue;
                LinkData linkData = (LinkData)collect.get(value.getCode());
                fieldData.setDataLinkKey(linkData.getKey());
                fieldDefines.add(fieldData);
            }
        } else {
            fieldDefines.addAll(this.regionRelationEvn.getDataLinkFieldMap().values());
        }
        return fieldDefines;
    }

    @Override
    public ImportResultRegionObject importDataRowSet(MemoryDataSet<Object> dataSet) {
        ImportResultRegionObject importResultRegionObject = new ImportResultRegionObject();
        String matchRoundOfImport = this.iNvwaSystemOptionService.get("nr-data-entry-group", "MATCH_ROUND_OF_IMPORT");
        boolean matchRound = false;
        if (matchRoundOfImport.equals("1")) {
            matchRound = true;
        }
        if (this.regionData.isReadOnly()) {
            importResultRegionObject.getRegionError().setErrorCode(ErrorCode.REGIONERROR);
            importResultRegionObject.setRegionKey(this.regionData.getKey());
            importResultRegionObject.getRegionError().setErrorInfo("\u533a\u57df\u53ea\u8bfb\uff0c\u4e0d\u53ef\u5bfc\u5165\u3002");
            return importResultRegionObject;
        }
        List<FieldData> fieldDefineList = this.getFieldDataList();
        if (fieldDefineList.isEmpty()) {
            return importResultRegionObject;
        }
        ArrayList<ImportErrorDataInfo> importErrorDataInfoList = new ArrayList<ImportErrorDataInfo>();
        FormData formData = this.jtableParamService.getReport(this.jtableContext.getFormKey(), this.jtableContext.getFormSchemeKey());
        if (FormType.FORM_TYPE_NEWFMDM.name().equals(formData.getFormType())) {
            Map<String, LinkData> dataLinkMap = this.regionRelationEvn.getDataLinkMap();
            RegionDataSet fmdmData = this.jtableResourceService.getFmdmData(this.regionData, this.regionQueryInfo);
            Map<String, List<String>> cells = fmdmData.getCells();
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(this.jtableContext.getTaskKey());
            IFMDMAttribute fmdmParentField = this.jtableParamService.getFmdmParentField(taskDefine.getDw(), this.jtableContext.getFormSchemeKey());
            for (int i = 0; i < dataSet.size(); ++i) {
                Object[] dataRow = dataSet.getBuffer(i);
                for (Map.Entry<String, List<String>> cell : cells.entrySet()) {
                    List<String> cellList = cell.getValue();
                    for (int j = 0; j < cellList.size(); ++j) {
                        Object value = dataRow[j];
                        LinkData linkData = dataLinkMap.get(cellList.get(j));
                        String style = linkData.getStyle();
                        if (style != null && style.equals("yyyy-MM") && value != null) {
                            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                            SimpleDateFormat dfD = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = null;
                            try {
                                date = sdf.parse(value.toString());
                                dataRow[j] = value = dfD.format(date);
                            }
                            catch (Exception e) {
                                logger.warn(e.getMessage());
                            }
                        }
                        ImportErrorDataInfo importFieldErrorDataInfo = new ImportErrorDataInfo();
                        if (fmdmParentField != null && fmdmParentField.getCode().equals(linkData.getZbcode()) && linkData.getType() == LinkType.LINK_TYPE\uff3fENUM.getValue() && (value.toString().equals("-") || value.toString().equals("null"))) continue;
                        Object data = value;
                        if (style == null || !style.equals("yyyy-MM")) {
                            data = linkData.getData(value, this.dataFormaterCache, (SaveErrorDataInfo)importFieldErrorDataInfo, matchRound);
                        }
                        if (!this.fmdmDataMap.containsKey(linkData.getZbcode())) {
                            this.fmdmDataMap.put(linkData.getZbcode(), data);
                        } else {
                            this.fmdmDataMap.put(linkData.getZbcode() + FMDM_REPEAT_FIELD, data);
                        }
                        if (linkData.getType() == LinkType.LINK_TYPE_STRING.getValue() || linkData.getType() == LinkType.LINK_TYPE_TEXT.getValue() || linkData.getType() == LinkType.LINK_TYPE_PICTURE.getValue() || linkData.getType() == LinkType.LINK_TYPE_FILE.getValue()) {
                            String valueAfterFormat;
                            List sensitiveList;
                            boolean allIsNoDesc = true;
                            if (value != null && (sensitiveList = this.checkSensitiveWordService.thisWordIsSensitiveWord(valueAfterFormat = value.toString().trim().replaceAll("\n", ""))) != null && sensitiveList.size() > 0) {
                                StringBuilder msg;
                                if (importFieldErrorDataInfo.getDataError().getErrorCode() == null) {
                                    msg = new StringBuilder();
                                    msg.append("\u6307\u6807:");
                                    msg.append(linkData.getZbtitle() + ";");
                                    for (SensitiveWordDaoObject sensitiveWordDaoObject : sensitiveList) {
                                        if (sensitiveWordDaoObject.getSensitiveDescription() == null || sensitiveWordDaoObject.getSensitiveDescription().length() <= 0) continue;
                                        allIsNoDesc = false;
                                        msg.append(sensitiveWordDaoObject.getSensitiveDescription());
                                        msg.append(";");
                                    }
                                    if (allIsNoDesc) {
                                        msg.append(";\u5bfc\u5165\u7684\u6570\u636e\u4e2d\u5305\u542b\u654f\u611f\u4fe1\u606f\uff0c\u4e0d\u5141\u8bb8\u5bfc\u5165\uff0c\u8bf7\u4fee\u6539\u6570\u636e\u540e\u91cd\u65b0\u5bfc\u5165\uff01");
                                    }
                                    importFieldErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                                    importFieldErrorDataInfo.getDataError().setErrorInfo(msg.toString());
                                } else {
                                    msg = new StringBuilder();
                                    msg.append(importFieldErrorDataInfo.getDataError().getErrorInfo());
                                    for (SensitiveWordDaoObject sensitiveWordDaoObject : sensitiveList) {
                                        if (sensitiveWordDaoObject.getSensitiveDescription() == null || sensitiveWordDaoObject.getSensitiveDescription().length() <= 0) continue;
                                        allIsNoDesc = false;
                                        msg.append(sensitiveWordDaoObject.getSensitiveDescription());
                                        msg.append(";");
                                    }
                                    if (allIsNoDesc) {
                                        msg.append(";\u5bfc\u5165\u7684\u6570\u636e\u4e2d\u5305\u542b\u654f\u611f\u4fe1\u606f\uff0c\u4e0d\u5141\u8bb8\u5bfc\u5165\uff0c\u8bf7\u4fee\u6539\u6570\u636e\u540e\u91cd\u65b0\u5bfc\u5165\uff01");
                                    }
                                    importFieldErrorDataInfo.getDataError().setErrorInfo(msg.toString());
                                }
                            }
                        }
                        if (linkData.getExpression() != null && linkData.getExpression().size() > 0) {
                            for (String expression : linkData.getExpression()) {
                                if (!expression.contains("NOT ISNULL") || linkData != null && !StringUtils.isEmpty((String)linkData.toString())) continue;
                                StringBuilder msg = new StringBuilder();
                                msg.append("\u6307\u6807:");
                                msg.append(linkData.getZbtitle() + "\u4e0d\u80fd\u4e3a\u7a7a");
                                importFieldErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                                importFieldErrorDataInfo.getDataError().setErrorInfo(msg.toString());
                            }
                        }
                        if (null == importFieldErrorDataInfo.getDataError().getErrorCode() || linkData == null) continue;
                        importFieldErrorDataInfo.setDataLinkKey(linkData.getKey());
                        importFieldErrorDataInfo.setFieldKey(linkData.getZbid());
                        importFieldErrorDataInfo.setDataIndex(Integer.valueOf(i));
                        importErrorDataInfoList.add(importFieldErrorDataInfo);
                    }
                }
            }
            importResultRegionObject.setImportErrorDataInfoList(importErrorDataInfoList);
            return importResultRegionObject;
        }
        ArrayList<Map<String, DimensionValue>> importDataDimensionValues = new ArrayList<Map<String, DimensionValue>>(dataSet.size());
        importResultRegionObject.setImportDataDimensionValues(importDataDimensionValues);
        FloatOrderGenerator floatOrderGenerator = this.appends();
        HashMap<FieldData, String> fieldDataDefaultMap = new HashMap<FieldData, String>();
        if (this.regionRelationEvn.getBizKeyOrderFields().size() > 0) {
            List<FieldData> fieldDataList = this.regionRelationEvn.getBizKeyOrderFields().get(0);
            DimensionValueSet masterKeys = this.dataTable.getMasterKeys();
            DimensionValueSet dimensionValueSet = new DimensionValueSet(masterKeys);
            if (this.regionRelationEvn.getAllowDuplicate()) {
                dimensionValueSet.setValue("RECORDKEY", (Object)UUID.randomUUID().toString());
            }
            for (FieldData fieldData : fieldDataList) {
                LinkData dataLink;
                if (fieldData.getFieldValueType() == FieldValueType.FIELD_VALUE_BIZKEY_ORDER.getValue() || !StringUtils.isNotEmpty((String)(dataLink = this.regionRelationEvn.getDataLinkByFiled(fieldData.getFieldKey())).getDefaultValue())) continue;
                AbstractData expressionEvaluat = this.jtableDataEngineService.expressionEvaluat(dataLink.getDefaultValue(), this.jtableContext, dimensionValueSet);
                if (expressionEvaluat != null) {
                    Object fieldValue = dataLink.getFormatData(expressionEvaluat, this.dataFormaterCache, this.jtableContext);
                    fieldDataDefaultMap.put(fieldData, fieldValue.toString());
                    continue;
                }
                fieldDataDefaultMap.put(fieldData, dataLink.getDefaultValue());
            }
        }
        for (int i = 0; i < dataSet.size(); ++i) {
            Object[] dataRow = dataSet.getBuffer(i);
            ArrayList<ImportErrorDataInfo> rowDimensionErrors = new ArrayList<ImportErrorDataInfo>();
            DimensionValueSet valueSet = this.getRowDimensionValueSet(dataRow, rowDimensionErrors, i, fieldDataDefaultMap);
            importDataDimensionValues.add(DimensionValueSetUtil.getDimensionSet(valueSet));
            if (!this.isValidDataRow(dataRow) && this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) continue;
            if (!rowDimensionErrors.isEmpty()) {
                importErrorDataInfoList.addAll(rowDimensionErrors);
                continue;
            }
            IDataRow regionDataRow = null;
            try {
                if (this.regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                    if (this.dataTable.getCount() > 0) {
                        regionDataRow = this.dataTable.getItem(0);
                    }
                    if (regionDataRow == null) {
                        regionDataRow = this.dataTable.appendRow(valueSet);
                    }
                } else {
                    regionDataRow = this.dataTable.appendRow(valueSet);
                }
            }
            catch (IncorrectQueryException e) {
                List<ImportErrorDataInfo> incorrectQueryExceptionList = this.exceptionErrors((Exception)((Object)e), i);
                importErrorDataInfoList.addAll(incorrectQueryExceptionList);
                continue;
            }
            if (this.regionRelationEvn.getFloatOrderFields().size() > 0) {
                Integer floatOederIndex = this.regionRelationEvn.getCellIndex("FLOATORDER");
                regionDataRow.setValue(floatOederIndex.intValue(), (Object)floatOrderGenerator.next());
            }
            for (int j = 0; j < fieldDefineList.size(); ++j) {
                Object value;
                ImportErrorDataInfo importFieldErrorDataInfo = new ImportErrorDataInfo();
                FieldData fieldDefine = fieldDefineList.get(j);
                if (fieldDefine == null || null != (value = dataRow[j]) && "IMPORT_INVALID_DATA".equals(value.toString())) continue;
                LinkData dataLink = this.regionRelationEvn.getDataLinkByFiled(fieldDefine.getFieldKey());
                String style = dataLink.getStyle();
                if (style != null && style.equals("yyyy-MM") && value != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                    SimpleDateFormat dfD = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = sdf.parse(value.toString());
                        dataRow[j] = value = dfD.format(date);
                    }
                    catch (Exception e) {
                        logger.warn(e.getMessage());
                    }
                }
                Object data = null;
                try {
                    data = dataLink.getData(value, this.dataFormaterCache, (SaveErrorDataInfo)importFieldErrorDataInfo, matchRound);
                    if (dataLink.getType() == LinkType.LINK_TYPE_STRING.getValue() || dataLink.getType() == LinkType.LINK_TYPE_TEXT.getValue() || dataLink.getType() == LinkType.LINK_TYPE_PICTURE.getValue() || dataLink.getType() == LinkType.LINK_TYPE_FILE.getValue()) {
                        String valueAfterFormat;
                        List sensitiveList;
                        boolean allIsNoDesc = true;
                        if (value != null && (sensitiveList = this.checkSensitiveWordService.thisWordIsSensitiveWord(valueAfterFormat = value.toString().trim().replaceAll("\n", ""))) != null && sensitiveList.size() > 0) {
                            StringBuilder msg;
                            if (importFieldErrorDataInfo.getDataError().getErrorCode() == null) {
                                msg = new StringBuilder();
                                msg.append("\u6307\u6807:");
                                msg.append(dataLink.getZbtitle() + ";");
                                for (SensitiveWordDaoObject sensitiveWordDaoObject : sensitiveList) {
                                    if (sensitiveWordDaoObject.getSensitiveDescription() == null || sensitiveWordDaoObject.getSensitiveDescription().length() <= 0) continue;
                                    allIsNoDesc = false;
                                    msg.append(sensitiveWordDaoObject.getSensitiveDescription());
                                    msg.append(";");
                                }
                                if (allIsNoDesc) {
                                    msg.append(";\u5bfc\u5165\u7684\u6570\u636e\u4e2d\u5305\u542b\u654f\u611f\u4fe1\u606f\uff0c\u4e0d\u5141\u8bb8\u5bfc\u5165\uff0c\u8bf7\u4fee\u6539\u6570\u636e\u540e\u91cd\u65b0\u5bfc\u5165\uff01");
                                }
                                importFieldErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                                importFieldErrorDataInfo.getDataError().setErrorInfo(msg.toString());
                            } else {
                                msg = new StringBuilder();
                                msg.append(importFieldErrorDataInfo.getDataError().getErrorInfo());
                                for (SensitiveWordDaoObject sensitiveWordDaoObject : sensitiveList) {
                                    if (sensitiveWordDaoObject.getSensitiveDescription() == null || sensitiveWordDaoObject.getSensitiveDescription().length() <= 0) continue;
                                    allIsNoDesc = false;
                                    msg.append(sensitiveWordDaoObject.getSensitiveDescription());
                                    msg.append(";");
                                }
                                if (allIsNoDesc) {
                                    msg.append(";\u5bfc\u5165\u7684\u6570\u636e\u4e2d\u5305\u542b\u654f\u611f\u4fe1\u606f\uff0c\u4e0d\u5141\u8bb8\u5bfc\u5165\uff0c\u8bf7\u4fee\u6539\u6570\u636e\u540e\u91cd\u65b0\u5bfc\u5165\uff01");
                                }
                                importFieldErrorDataInfo.getDataError().setErrorInfo(msg.toString());
                            }
                        }
                    }
                    if (dataLink.getExpression() != null && dataLink.getExpression().size() > 0) {
                        for (String expression : dataLink.getExpression()) {
                            if (!expression.contains("NOT ISNULL") || value != null && !StringUtils.isEmpty((String)value.toString())) continue;
                            StringBuilder msg = new StringBuilder();
                            msg.append("\u6307\u6807:");
                            msg.append(dataLink.getZbtitle() + "\u4e0d\u80fd\u4e3a\u7a7a");
                            importFieldErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                            importFieldErrorDataInfo.getDataError().setErrorInfo(msg.toString());
                        }
                    }
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                if (null != importFieldErrorDataInfo.getDataError().getErrorCode()) {
                    importFieldErrorDataInfo.setDataLinkKey(dataLink.getKey());
                    importFieldErrorDataInfo.setFieldKey(fieldDefine.getFieldKey());
                    importFieldErrorDataInfo.setDataIndex(Integer.valueOf(i));
                    importErrorDataInfoList.add(importFieldErrorDataInfo);
                    continue;
                }
                regionDataRow.setValue(this.regionRelationEvn.getCellIndex(dataLink.getKey()), data);
            }
        }
        importResultRegionObject.setImportErrorDataInfoList(importErrorDataInfoList);
        return importResultRegionObject;
    }

    private FloatOrderGenerator appends() {
        if (DataRegionKind.DATA_REGION_SIMPLE.getValue() == this.regionData.getType()) {
            return null;
        }
        if (DataRegionKind.DATA_REGION_ROW_LIST.getValue() == this.regionData.getType() && this.regionData.getRegionTop() == 1) {
            return null;
        }
        if (DataRegionKind.DATA_REGION_COLUMN_LIST.getValue() == this.regionData.getType() && this.regionData.getRegionLeft() == 1) {
            return null;
        }
        IGroupingQuery dataQuery = this.jtableDataEngineService.getGroupingQuery(this.jtableContext, this.regionData.getKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(this.jtableContext);
        dataQuery.setMasterKeys(this.getCollectionDim(dimensionValueSet));
        Double currMaxVal = null;
        try {
            IDataRow item;
            String floatOrder;
            FieldData fieldData = this.jtableParamService.getFieldByCodeInTable("FLOATORDER", this.dataTable.getFieldsInfo().getFieldDefine(0).getOwnerTableKey());
            FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldData.getFieldKey());
            dataQuery.addColumn(fieldDefine);
            dataQuery.setGatherType(0, FieldGatherType.FIELD_GATHER_MAX);
            IGroupingTable executeQuery = dataQuery.executeReader(this.jtableDataEngineService.getExecutorContext(this.jtableContext));
            if (executeQuery.getCount() > 0 && (floatOrder = (item = executeQuery.getItem(0)).getAsString(0)) != null && !floatOrder.equals("")) {
                currMaxVal = Double.parseDouble(floatOrder);
            }
        }
        catch (Exception e1) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e1.getMessage(), e1);
        }
        FloatOrderGenerator floatOrderGenerator = null;
        floatOrderGenerator = currMaxVal == null ? new FloatOrderGenerator() : new FloatOrderGenerator(currMaxVal);
        return floatOrderGenerator;
    }

    private boolean isValidDataRow(Object[] dataRow) {
        boolean isEmpty = true;
        List<FieldData> fieldDefineList = this.getFieldDataList();
        for (int i = 0; i < dataRow.length; ++i) {
            FieldData fieldData;
            LinkData linkData;
            Object value = dataRow[i];
            if (value == null || StringUtils.isEmpty((String)value.toString()) || value.toString().equals("IMPORT_INVALID_DATA") || (linkData = this.regionRelationEvn.getDataLinkByFiled((fieldData = fieldDefineList.get(i)).getFieldKey())) != null && (this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue() && this.calcDataLinks.contains(linkData.getKey()) || StringUtils.isNotEmpty((String)linkData.getDefaultValue()) && linkData.getDefaultValue().equals(value.toString()))) continue;
            isEmpty = false;
        }
        return !isEmpty;
    }

    public DimensionValueSet getRowDimensionValueSet(Object[] dataRow, List<ImportErrorDataInfo> importErrorDataInfoList, int i, Map<FieldData, String> fieldDataDefaultMap) {
        DimensionValueSet masterKeys = this.dataTable.getMasterKeys();
        if (this.regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            return masterKeys;
        }
        List<FieldData> fieldDefineList = this.getFieldDataList();
        HashMap<String, Object> fieldValueMap = new HashMap<String, Object>(100);
        for (int j = 0; j < fieldDefineList.size(); ++j) {
            fieldValueMap.put(fieldDefineList.get(j).getFieldKey().toString(), dataRow[j]);
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet(masterKeys);
        if (this.regionRelationEvn.getAllowDuplicate()) {
            dimensionValueSet.setValue("RECORDKEY", (Object)UUID.randomUUID().toString());
        }
        StringBuffer emptyBizKeyFiledName = new StringBuffer();
        List<FieldData> fieldDataList = this.regionRelationEvn.getBizKeyOrderFields().get(0);
        ArrayList<String> dataLinkKeyList = new ArrayList<String>();
        HashMap<FieldData, String> emptyValueFiledDataLinkMap = new HashMap<FieldData, String>();
        for (FieldData fieldData : fieldDataList) {
            Object value;
            ImportErrorDataInfo importErrorDataInfo;
            String dimensionName;
            if (this.dimCache.containsKey(fieldData.getFieldKey())) {
                dimensionName = this.dimCache.get(fieldData.getFieldKey());
                if (this.dimCache.size() > 100) {
                    this.dimCache.clear();
                }
            } else {
                dimensionName = this.jtableDataEngineService.getDimensionName(fieldData);
                this.dimCache.put(fieldData.getFieldKey(), dimensionName);
            }
            if ("RECORDKEY".equals(dimensionName) || "FLOATORDER".equals(dimensionName)) continue;
            LinkData dataLink = this.regionRelationEvn.getDataLinkByFiled(fieldData.getFieldKey());
            dataLinkKeyList.add(dataLink.getKey());
            if (dimensionValueSet.hasValue(dimensionName)) {
                String dimensionValue = dimensionValueSet.getValue(dimensionName).toString();
                fieldValueMap.put(fieldData.getFieldKey().toString(), dimensionValue);
                block2: for (int j = 0; j < fieldDefineList.size(); ++j) {
                    if (!fieldData.getFieldKey().equals(fieldDefineList.get(j).getFieldKey())) continue;
                    Object defaultValue = RegionSettingUtil.checkRegionDefaultValueGetIfAbsent(this.regionData, dataLink);
                    if (defaultValue != null) {
                        String[] splitVal;
                        importErrorDataInfo = new ImportErrorDataInfo();
                        String cellValue = dataRow[j].toString();
                        for (String cv : splitVal = cellValue.split("\\|")) {
                            Object value2 = dataLink.getData(cv, this.dataFormaterCache, (SaveErrorDataInfo)importErrorDataInfo, true);
                            if (!Objects.nonNull(value2) || defaultValue.equals(value2)) continue;
                            importErrorDataInfo.setFieldKey(fieldData.getFieldKey());
                            importErrorDataInfo.setDataLinkKey(dataLink.getKey());
                            importErrorDataInfo.setDataIndex(Integer.valueOf(i));
                            importErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                            importErrorDataInfo.setFieldTitle(fieldData.getFieldTitle());
                            importErrorDataInfo.getDataError().setErrorInfo("\u6570\u636e\u4e0d\u5c5e\u4e8e\u8be5\u533a\u57df\uff0c\u4e0e\u533a\u57df\u8bbe\u7f6e\u7684\u7ef4\u5ea6\u9ed8\u8ba4\u503c\u4e0d\u5339\u914d\uff01");
                            importErrorDataInfoList.add(importErrorDataInfo);
                            break block2;
                        }
                        break;
                    }
                    dataRow[j] = dimensionValue;
                    break;
                }
            }
            if ((value = fieldValueMap.get(fieldData.getFieldKey().toString())) == null || StringUtils.isEmpty((String)value.toString())) {
                if (emptyBizKeyFiledName.length() > 0) {
                    emptyBizKeyFiledName.append("\u3001");
                }
                emptyBizKeyFiledName.append(fieldData.getFieldTitle());
                emptyValueFiledDataLinkMap.put(fieldData, dataLink.getKey());
                continue;
            }
            SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
            if (value.toString().equals("IMPORT_INVALID_DATA")) continue;
            Object rowData = dataLink.getData(value, this.dataFormaterCache, saveErrorDataInfo, false);
            if (saveErrorDataInfo.getDataError().getErrorCode() != null && saveErrorDataInfo.getDataError().getErrorCode().equals((Object)ErrorCode.DATAERROR)) {
                importErrorDataInfo = new ImportErrorDataInfo();
                importErrorDataInfo.setFieldKey(fieldData.getFieldKey());
                importErrorDataInfo.setDataLinkKey(dataLink.getKey());
                importErrorDataInfo.setDataIndex(Integer.valueOf(i));
                importErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                importErrorDataInfo.setFieldTitle(emptyBizKeyFiledName.toString());
                importErrorDataInfo.getDataError().setErrorInfo(saveErrorDataInfo.getDataError().getErrorInfo());
                importErrorDataInfoList.add(importErrorDataInfo);
            }
            if (rowData == null || StringUtils.isEmpty((String)rowData.toString())) {
                if (emptyBizKeyFiledName.length() > 0) {
                    emptyBizKeyFiledName.append("\u3001");
                }
                emptyBizKeyFiledName.append(fieldData.getFieldTitle());
                continue;
            }
            if (rowData.toString().equals("IMPORT_INVALID_DATA") && fieldDataDefaultMap.get(fieldData) != null) {
                rowData = fieldDataDefaultMap.get(fieldData);
            }
            dimensionValueSet.setValue(dimensionName, rowData);
        }
        List<String> tableKeyList = this.regionRelationEvn.getTableKeyList();
        for (String tableKey : tableKeyList) {
            TableData tableData = this.jtableParamService.getTable(tableKey);
            List<String> bizKeyFields = tableData.getBizKeyFields();
            for (String fieldKey : bizKeyFields) {
                String dimensionName;
                FieldData fieldData = this.jtableParamService.getField(fieldKey);
                if (!tableData.getBizKeyFields().contains(fieldData.getFieldKey())) continue;
                if (null != this.dimCache.get(fieldData.getFieldKey())) {
                    dimensionName = this.dimCache.get(fieldData.getFieldKey());
                    if (this.dimCache.size() > 100) {
                        this.dimCache.clear();
                    }
                } else {
                    dimensionName = this.jtableDataEngineService.getDimensionName(fieldData);
                    this.dimCache.put(fieldData.getFieldKey(), dimensionName);
                }
                if (dimensionValueSet.hasValue(dimensionName)) continue;
                AbstractData expressionEvaluat = this.jtableDataEngineService.expressionEvaluat(fieldData.getDefaultValue(), this.jtableContext, dimensionValueSet);
                if (expressionEvaluat != null) {
                    dimensionValueSet.setValue(dimensionName, (Object)expressionEvaluat.getAsString());
                    continue;
                }
                dimensionValueSet.setValue(dimensionName, (Object)fieldData.getDefaultValue());
            }
        }
        if (emptyBizKeyFiledName.length() > 0) {
            for (FieldData fieldData : emptyValueFiledDataLinkMap.keySet()) {
                ImportErrorDataInfo importErrorDataInfo = new ImportErrorDataInfo();
                importErrorDataInfo.setFieldKey(fieldData.getFieldKey());
                importErrorDataInfo.setDataLinkKey((String)emptyValueFiledDataLinkMap.get(fieldData));
                importErrorDataInfo.setDataIndex(Integer.valueOf(i));
                importErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                importErrorDataInfo.setFieldTitle(emptyBizKeyFiledName.toString());
                importErrorDataInfo.getDataError().setErrorInfo("\u6d6e\u52a8\u884c\u4e1a\u52a1\u4e3b\u952e\u6307\u6807\u201c" + emptyBizKeyFiledName.toString() + "\u201d\u4e0d\u80fd\u4e3a\u7a7a\u3002");
                importErrorDataInfoList.add(importErrorDataInfo);
            }
        }
        return dimensionValueSet;
    }

    @Override
    public List<RegionTab> getRegionTabs() {
        return this.regionData.getTabs();
    }

    @Override
    public void setRegionTab(RegionTab regionTabSettingDefine) {
        ArrayList<String> filterFormulaList = new ArrayList<String>();
        if (regionTabSettingDefine == null) {
            this.regionQueryInfo.getFilterInfo().setFilterFormula(filterFormulaList);
        } else {
            if (StringUtils.isNotEmpty((String)regionTabSettingDefine.getFilter())) {
                filterFormulaList.add(regionTabSettingDefine.getFilter());
            }
            this.regionQueryInfo.getFilterInfo().setFilterFormula(filterFormulaList);
        }
    }

    private List<ImportErrorDataInfo> exceptionErrors(Exception e, int i) {
        IJtableDataEngineService jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        ArrayList<ImportErrorDataInfo> incorrectQueryExceptionList = new ArrayList<ImportErrorDataInfo>();
        if (e instanceof DuplicateRowKeysException) {
            DuplicateRowKeysException duplicateRowKeysException = (DuplicateRowKeysException)e;
            List duplicateKeys = duplicateRowKeysException.getDuplicateKeys();
            for (DimensionValueSet key : duplicateKeys) {
                StringBuffer duplicateStr = new StringBuffer("\u4e1a\u52a1\u4e3b\u952e\u91cd\u590d(");
                List<FieldData> fieldList = this.regionRelationEvn.getBizKeyOrderFields().get(0);
                ArrayList dataLinkList = new ArrayList();
                for (FieldData field : fieldList) {
                    String dimensionName = jtableDataEngineService.getDimensionName(field);
                    String dimensionValue = key.getValue(dimensionName).toString();
                    LinkData dataLink = this.regionRelationEvn.getDataLinkByFiled(field.getFieldKey());
                    if (dataLink instanceof EnumLinkData) {
                        EnumLinkData enumLink = (EnumLinkData)dataLink;
                        IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
                        EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                        entityQueryByKeyInfo.setEntityViewKey(enumLink.getEntityKey());
                        entityQueryByKeyInfo.setEntityKey(dimensionValue);
                        entityQueryByKeyInfo.setDataLinkKey(enumLink.getKey());
                        EntityByKeyReturnInfo entityDataByKey = jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                        dimensionValue = entityDataByKey.getEntity() == null ? "" : entityDataByKey.getEntity().getRowCaption();
                    }
                    duplicateStr.append(field.getFieldTitle()).append(":").append(dimensionValue).append(";");
                }
                duplicateStr.append(")");
                for (FieldData field : fieldList) {
                    for (String dataLinkKey : dataLinkList) {
                        ImportErrorDataInfo importRowErrorDataInfo = new ImportErrorDataInfo();
                        importRowErrorDataInfo.setDataLinkKey(dataLinkKey);
                        importRowErrorDataInfo.setFieldKey(field.getFieldKey());
                        importRowErrorDataInfo.setDataIndex(Integer.valueOf(i));
                        importRowErrorDataInfo.getDataError().setErrorInfo(duplicateStr.toString());
                        importRowErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                        incorrectQueryExceptionList.add(importRowErrorDataInfo);
                    }
                }
            }
        } else if (e instanceof IncorrectRowKeysException) {
            IncorrectRowKeysException incorrectRowKeysException = (IncorrectRowKeysException)e;
            DimensionValueSet rowKey = incorrectRowKeysException.getRowKeys();
            StringBuffer emptyDimStr = new StringBuffer("\u4e1a\u52a1\u4e3b\u952e\u4e3a\u7a7a(");
            List<FieldData> fieldList = this.regionRelationEvn.getBizKeyOrderFields().get(0);
            ArrayList<String> dataLinkList = new ArrayList<String>();
            for (FieldData field : fieldList) {
                String dataLinkKey = this.regionRelationEvn.getDataLinkKeyByFiled(field.getFieldKey());
                dataLinkList.add(dataLinkKey);
                String dimensionName = jtableDataEngineService.getDimensionName(field);
                emptyDimStr.append(field.getFieldTitle()).append(":").append(rowKey.getValue(dimensionName)).append(";");
            }
            emptyDimStr.append(")");
            for (FieldData field : fieldList) {
                for (String dataLinkKey : dataLinkList) {
                    ImportErrorDataInfo importRowErrorDataInfo = new ImportErrorDataInfo();
                    importRowErrorDataInfo.setDataLinkKey(dataLinkKey);
                    importRowErrorDataInfo.setFieldKey(field.getFieldKey());
                    importRowErrorDataInfo.setDataIndex(Integer.valueOf(i));
                    importRowErrorDataInfo.getDataError().setErrorInfo(emptyDimStr.toString());
                    importRowErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                    incorrectQueryExceptionList.add(importRowErrorDataInfo);
                }
            }
        }
        if (incorrectQueryExceptionList.isEmpty()) {
            ImportErrorDataInfo importRowErrorDataInfo = new ImportErrorDataInfo();
            importRowErrorDataInfo.setDataLinkKey(null);
            importRowErrorDataInfo.setFieldKey(null);
            importRowErrorDataInfo.setDataIndex(Integer.valueOf(i));
            importRowErrorDataInfo.getDataError().setErrorInfo(e.getMessage());
            importRowErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
            incorrectQueryExceptionList.add(importRowErrorDataInfo);
        }
        return incorrectQueryExceptionList;
    }

    private void commitFmdmData(String formSchemeKey, String period) throws Exception {
        RegionDataSet fmdmData = this.jtableResourceService.getFmdmData(this.regionData, this.regionQueryInfo);
        Map<String, List<String>> cells = fmdmData.getCells();
        List<List<Object>> oldData = fmdmData.getData();
        ArrayList data = new ArrayList();
        List collectData = this.fmdmDataMap.values().stream().collect(Collectors.toList());
        ArrayList<Object> newData = null;
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        DimensionValueSet dim = new DimensionValueSet();
        dim.setValue("DATATIME", (Object)period);
        Map<String, IFMDMAttribute> fmdmAttributeMap = this.jtableResourceService.getFMDMAttribute(taskDefine.getDw(), formSchemeKey);
        String entityId = taskDefine.getDw();
        if (entityId == null) {
            entityId = formScheme.getDw();
        }
        List entityRefer = this.entityMetaService.getEntityRefer(entityId);
        Map<String, String> ownFieldToReferEntityId = entityRefer.stream().collect(Collectors.toMap(IEntityRefer::getOwnField, IEntityRefer::getReferEntityId));
        for (Map.Entry<String, List<String>> cell : cells.entrySet()) {
            List<String> cellList = cell.getValue();
            for (int i = 0; i < cellList.size(); ++i) {
                String entityKeyData;
                String[] str;
                DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(cellList.get(i));
                Object cellData = collectData.get(i);
                String zbCode = dataLinkDefine.getLinkExpression();
                String referEntityId = ownFieldToReferEntityId.get(zbCode);
                if (referEntityId != null) {
                    if (cellData != null && cellData.toString().contains("|")) {
                        str = cellData.toString().split("\\|");
                        entityKeyData = this.queryEntityKeyData(str[0], dim, referEntityId, formSchemeKey);
                        newData = new ArrayList();
                        if (entityKeyData != null) {
                            newData.add(entityKeyData);
                            newData.add(entityKeyData);
                        } else {
                            newData.add(str[0]);
                            newData.add(str[0]);
                        }
                        data.add(newData);
                        continue;
                    }
                    newData = new ArrayList();
                    newData.add(cellData);
                    newData.add(cellData);
                    data.add(newData);
                    continue;
                }
                newData = new ArrayList<Object>();
                if (cellData != null && cellData.toString().contains("|")) {
                    str = cellData.toString().split("\\|");
                    entityKeyData = this.queryEntityKeyData(str[0], dim, entityId, formSchemeKey);
                    if (entityKeyData != null) {
                        newData.add(entityKeyData);
                        newData.add(entityKeyData);
                    } else {
                        newData.add(str[0]);
                        newData.add(str[0]);
                    }
                    data.add(newData);
                    continue;
                }
                if ("\u221a".equals(cellData)) {
                    newData.add(true);
                    newData.add(true);
                    data.add(newData);
                    continue;
                }
                if ("\u00d7".equals(cellData)) {
                    newData.add(false);
                    newData.add(false);
                    data.add(newData);
                    continue;
                }
                newData.add(cellData);
                newData.add(cellData);
                data.add(newData);
            }
        }
        ReportDataCommitSet reportDataCommitSet = new ReportDataCommitSet();
        HashMap<String, RegionDataCommitSet> commitData = new HashMap<String, RegionDataCommitSet>();
        RegionDataCommitSet regionDataCommitSet = new RegionDataCommitSet();
        regionDataCommitSet.setContext(this.jtableContext);
        regionDataCommitSet.getCells().putAll(cells);
        regionDataCommitSet.getData().add(data);
        regionDataCommitSet.setRegionKey(this.regionData.getKey());
        commitData.put(this.regionData.getKey(), regionDataCommitSet);
        reportDataCommitSet.setCommitData(commitData);
        reportDataCommitSet.setContext(this.jtableContext);
        reportDataCommitSet.setUnitAdd(false);
        this.jtableResourceService.saveFmdmData(reportDataCommitSet, this.regionData, false);
    }

    private String queryEntityKeyData(String cellData, DimensionValueSet dim, String entityId, String formSchemeKey) throws Exception {
        String entityKeyData = null;
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setMasterKeys(this.getCollectionDim(dim));
        EntityViewDefine referEntityView = this.runTimeController.buildEntityView(entityId);
        iEntityQuery.setEntityView(referEntityView);
        IEntityTable iEntityTable = this.dataEntityFullService.executeEntityReader(iEntityQuery, null, referEntityView, formSchemeKey).getEntityTable();
        IEntityRow byCode = iEntityTable.findByCode(cellData);
        entityKeyData = byCode.getEntityKeyData();
        return entityKeyData;
    }

    public boolean isSaveFileGroupKey() {
        return this.saveFileGroupKey;
    }

    @Override
    public void setSaveFileGroupKey(boolean saveFileGroupKey) {
        this.saveFileGroupKey = saveFileGroupKey;
    }

    private DimensionValueSet getCollectionDim(DimensionValueSet dimensionSet) {
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        for (int j = 0; j < dimensionSet.size(); ++j) {
            builder.setValue(dimensionSet.getName(j), new Object[]{dimensionSet.getValue(j)});
        }
        DimensionValueSet varDim = builder.getCollection().combineWithoutVarDim();
        return varDim;
    }
}

