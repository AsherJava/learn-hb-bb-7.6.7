/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.cache.BaseDataCache
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo
 *  com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv
 *  com.jiuqi.gcreport.conversion.common.GcConversionWorkPaperCurrencyEnv
 *  com.jiuqi.gcreport.conversion.common.GcConversionWorkPaperEnv
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum
 *  com.jiuqi.gcreport.rate.impl.enums.ConversionTypeEnum
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.conversion.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.cache.BaseDataCache;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.conversion.action.param.GcConversionActionParam;
import com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo;
import com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv;
import com.jiuqi.gcreport.conversion.common.GcConversionWorkPaperCurrencyEnv;
import com.jiuqi.gcreport.conversion.common.GcConversionWorkPaperEnv;
import com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateService;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemItemDao;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemTaskDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO;
import com.jiuqi.gcreport.conversion.executor.impl.ConversionSimpleFormExecutorImpl;
import com.jiuqi.gcreport.conversion.service.GCConversionWorkPaperService;
import com.jiuqi.gcreport.conversion.utils.GcConversionUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum;
import com.jiuqi.gcreport.rate.impl.enums.ConversionTypeEnum;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GCConversionWorkPaperServiceImpl
implements GCConversionWorkPaperService {
    @Autowired
    private ConversionSystemTaskDao taskSchemeDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private ConversionRateService conversionRateService;
    @Autowired
    private ConversionSystemItemDao itemDao;
    private int differenceCount;
    private double stepProgressMap = 0.0;
    private ExportContext exportContext;
    private List<FormDefine> formDefines;
    private ConversionSystemTaskEO taskSchemeEO;
    private Map<String, Map<DataRegionDefine, Set<TableModelDefine>>> formIdAndTableDefineMap = new HashMap<String, Map<DataRegionDefine, Set<TableModelDefine>>>();
    private static Map<String, DimensionValue> dimensionSet;
    private static final Logger LOGGER;

    private void initDatas(GcConversionWorkPaperEnv conversionContextEnv) {
        try {
            this.formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(conversionContextEnv.getSchemeId());
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        this.taskSchemeEO = GcConversionUtils.getConversionSystemTaskEO(conversionContextEnv.getTaskId(), conversionContextEnv.getSchemeId());
    }

    @Override
    public Map<String, List<GcConversionWorkPaperEnv>> getGcBatchConversionWorkPaperEnv(String actionParamJson, ExportContext exportContext) throws Exception {
        this.stepProgressMap = 0.0;
        this.exportContext = exportContext;
        GcConversionActionParam actionParam = (GcConversionActionParam)JsonUtils.readValue((String)actionParamJson, GcConversionActionParam.class);
        JSONObject actionParams = new JSONObject(actionParamJson);
        DataEntryContext envContext = actionParam.getEnvContext();
        dimensionSet = envContext.getDimensionSet();
        DimensionValue defaultPeriodValue = dimensionSet.get("DATATIME");
        HashMap<String, List<GcConversionWorkPaperEnv>> orgEnvMap = new HashMap<String, List<GcConversionWorkPaperEnv>>();
        boolean isAllorgChoose = actionParams.optBoolean("isAllorgChoose");
        if (isAllorgChoose) {
            String periodStr = defaultPeriodValue == null ? null : defaultPeriodValue.getValue();
            String orgVersionType = actionParam.getOrgVersionType();
            List<GcOrgCacheVO> gcOrgCacheVOList = this.getAllOrg(periodStr, orgVersionType);
            for (GcOrgCacheVO gcOrgCacheVO : gcOrgCacheVOList) {
                this.formatGcBatchConversionEnv(actionParam, gcOrgCacheVO.getId(), actionParams, envContext, defaultPeriodValue, dimensionSet, orgEnvMap);
            }
        } else {
            for (String orgId : actionParam.getOrgIds()) {
                this.formatGcBatchConversionEnv(actionParam, orgId, actionParams, envContext, defaultPeriodValue, dimensionSet, orgEnvMap);
            }
        }
        this.stepProgressMap = this.getStepProgress(orgEnvMap);
        return orgEnvMap;
    }

    private void formatGcBatchConversionEnv(GcConversionActionParam actionParam, String orgId, JSONObject actionParams, DataEntryContext envContext, DimensionValue defaultPeriodValue, Map<String, DimensionValue> dimensionSet, Map<String, List<GcConversionWorkPaperEnv>> orgEnvMap) throws Exception {
        ArrayList<GcConversionWorkPaperEnv> batchEnvs = new ArrayList<GcConversionWorkPaperEnv>();
        DimensionValue adjustValue = dimensionSet.get("ADJUST");
        String selectAdjustCode = adjustValue == null ? "0" : adjustValue.getValue();
        for (String fromId : actionParam.getFormIds()) {
            GcConversionWorkPaperEnv gcConversionWorkPaperEnv = new GcConversionWorkPaperEnv();
            gcConversionWorkPaperEnv.setOrgId(orgId);
            gcConversionWorkPaperEnv.setIsSegment(Boolean.parseBoolean(actionParams.optString("isSegment")));
            gcConversionWorkPaperEnv.setTaskId(envContext.getTaskKey());
            gcConversionWorkPaperEnv.setSchemeId(envContext.getFormSchemeKey());
            gcConversionWorkPaperEnv.setOrgVersionType(actionParam.getOrgVersionType());
            gcConversionWorkPaperEnv.setPeriodStr(defaultPeriodValue == null ? null : defaultPeriodValue.getValue());
            DimensionValue gcorgtypeValue = dimensionSet.get("MD_GCORGTYPE");
            gcConversionWorkPaperEnv.setOrgTypeId(gcorgtypeValue == null ? null : gcorgtypeValue.getValue());
            gcConversionWorkPaperEnv.setFormId(fromId);
            gcConversionWorkPaperEnv.setSelectAdjustCode(selectAdjustCode);
            JSONObject reportZbDataJson = actionParams.getJSONObject("reportZbDataMap");
            JSONArray zbCodes = reportZbDataJson.optJSONArray(fromId);
            ArrayList<String> lists = new ArrayList<String>();
            gcConversionWorkPaperEnv.setFieldKeys(lists);
            if (zbCodes != null) {
                for (int count = 0; count < zbCodes.length(); ++count) {
                    DataField fieldDefine;
                    String zbCode = zbCodes.getString(count);
                    int leftBracket = zbCode.indexOf(91);
                    int rightBracket = zbCode.indexOf(93);
                    String tableCode = zbCode.substring(0, leftBracket);
                    String fieldCode = zbCode.substring(leftBracket + 1, rightBracket);
                    DataTable tableDefine = this.runtimeDataSchemeService.getDataTableByCode(tableCode);
                    if (tableDefine == null || (fieldDefine = this.runtimeDataSchemeService.getDataFieldByTableKeyAndCode(tableCode, fieldCode)) == null) continue;
                    lists.add(fieldDefine.getKey());
                }
                gcConversionWorkPaperEnv.setFieldKeys(lists);
            }
            batchEnvs.add(gcConversionWorkPaperEnv);
        }
        GcConversionWorkPaperEnv gcConversionWorkPaperEnv = new GcConversionWorkPaperEnv();
        gcConversionWorkPaperEnv.setSchemeId(envContext.getFormSchemeKey());
        gcConversionWorkPaperEnv.setTaskId(envContext.getTaskKey());
        gcConversionWorkPaperEnv.setSelectAdjustCode(selectAdjustCode);
        this.initDatas(gcConversionWorkPaperEnv);
        orgEnvMap.put(orgId, batchEnvs);
    }

    @Override
    public GcConversionWorkPaperEnv getGcConversionWorkPaperEnv(String actionParamJson, ExportContext exportContext) {
        this.stepProgressMap = 0.0;
        this.exportContext = exportContext;
        GcConversionActionParam actionParam = (GcConversionActionParam)JsonUtils.readValue((String)actionParamJson, GcConversionActionParam.class);
        JSONObject actionParams = new JSONObject(actionParamJson);
        DataEntryContext envContext = actionParam.getEnvContext();
        dimensionSet = envContext.getDimensionSet();
        DimensionValue defaultPeriodValue = dimensionSet.get("DATATIME");
        GcConversionWorkPaperEnv gcConversionWorkPaperEnv = new GcConversionWorkPaperEnv();
        gcConversionWorkPaperEnv.setIsSegment(Boolean.parseBoolean(actionParams.optString("isSegment")));
        gcConversionWorkPaperEnv.setTaskId(envContext.getTaskKey());
        gcConversionWorkPaperEnv.setSchemeId(envContext.getFormSchemeKey());
        gcConversionWorkPaperEnv.setOrgVersionType(actionParam.getOrgVersionType());
        gcConversionWorkPaperEnv.setFormId(actionParam.getFormIds().get(0));
        gcConversionWorkPaperEnv.setOrgId(actionParam.getOrgIds().get(0));
        gcConversionWorkPaperEnv.setPeriodStr(defaultPeriodValue == null ? null : defaultPeriodValue.getValue());
        DimensionValue gcorgtypeValue = dimensionSet.get("MD_GCORGTYPE");
        gcConversionWorkPaperEnv.setOrgTypeId(gcorgtypeValue == null ? null : gcorgtypeValue.getValue());
        DimensionValue adjustValue = dimensionSet.get("ADJUST");
        gcConversionWorkPaperEnv.setSelectAdjustCode(adjustValue == null ? "0" : adjustValue.getValue());
        JSONArray zbJsonIds = actionParams.getJSONArray("zbIds");
        ArrayList<String> lists = new ArrayList<String>();
        for (Object zbId : zbJsonIds) {
            lists.add((String)zbId);
        }
        gcConversionWorkPaperEnv.setFieldKeys(lists);
        this.initDatas(gcConversionWorkPaperEnv);
        return gcConversionWorkPaperEnv;
    }

    @Override
    public JSONObject getConversionWorkPaperDatas(GcConversionWorkPaperEnv conversionWorkPaperEnv, Map<String, List<ConversionSystemItemEO>> tableKeyAndItemMap) {
        this.differenceCount = 0;
        JSONObject returnJson = new JSONObject();
        conversionWorkPaperEnv.setRateSchemeCode(this.taskSchemeEO.getRateSchemeCode());
        GcOrgCacheVO orgToJsonVO = this.getOrgByCode(conversionWorkPaperEnv);
        returnJson.put("conversionTable", (Object)this.getTableData(orgToJsonVO));
        List<String> afterCurrencyCodes = this.getAfterCurrencyCodes(orgToJsonVO);
        List<GcConversionOrgAndFormContextEnv> orgAndFormContextEnvs = this.getOrgAndFormAndTableContextEnvs(orgToJsonVO, conversionWorkPaperEnv);
        if (!CollectionUtils.isEmpty(orgAndFormContextEnvs)) {
            try {
                returnJson.put("conversionDatas", (Object)this.conversion(this.taskSchemeEO, conversionWorkPaperEnv, orgAndFormContextEnvs, afterCurrencyCodes, tableKeyAndItemMap));
            }
            catch (Exception e) {
                LOGGER.error("\u5916\u5e01\u6298\u7b97\u6267\u884c\u5f02\u5e38,\u5f02\u5e38\u65b9\u6cd5conversionWorkPaper", e);
                throw new BusinessRuntimeException(e.getMessage());
            }
        }
        returnJson.put("differenceCount", this.differenceCount);
        this.sortConversionData(returnJson, conversionWorkPaperEnv);
        return returnJson;
    }

    private void sortConversionData(JSONObject returnJson, GcConversionWorkPaperEnv conversionWorkPaperEnv) {
        if (CollectionUtils.isEmpty((Collection)conversionWorkPaperEnv.getFieldKeys()) || returnJson.optJSONArray("conversionDatas").isEmpty()) {
            return;
        }
        JSONArray returnJsonArrayNew = new JSONArray();
        for (String fieldKey : conversionWorkPaperEnv.getFieldKeys()) {
            JSONArray returnJsonArray = returnJson.getJSONArray("conversionDatas");
            for (int index = 0; index < returnJsonArray.length(); ++index) {
                if (!fieldKey.equals(returnJsonArray.getJSONObject(index).optString("zbId"))) continue;
                returnJsonArrayNew.put((Object)returnJsonArray.getJSONObject(index));
            }
        }
        returnJson.put("conversionDatas", (Object)returnJsonArrayNew);
    }

    @Override
    public JSONObject conversionWorkPaper(String actionParamJson) throws Exception {
        GcConversionWorkPaperEnv conversionWorkPaperEnv = this.getGcConversionWorkPaperEnv(actionParamJson, null);
        return this.getConversionWorkPaperDatas(conversionWorkPaperEnv, null);
    }

    private boolean isFloatReport(GcConversionWorkPaperEnv conversionWorkPaperEnv, List<GcConversionOrgAndFormContextEnv> orgContextEnvs, Map<String, List<String>> fieldDefinesMap) throws Exception {
        AtomicInteger floatReportZBCount = new AtomicInteger(0);
        for (GcConversionOrgAndFormContextEnv orgAndFormEnv : orgContextEnvs) {
            List<Object> fieldNames;
            if (!DataRegionKind.DATA_REGION_SIMPLE.equals((Object)orgAndFormEnv.getDataRegionDefine().getRegionKind())) continue;
            TableModelRunInfo tableInfo = GcConversionUtils.getDefinitionsCache().getDataModelDefinitionsCache().getTableInfo(orgAndFormEnv.getTableDefine().getName());
            if (conversionWorkPaperEnv.getFieldKeys().size() > 0) {
                fieldNames = new ArrayList();
                tableInfo.getColumnFieldMap().forEach((columnModelDefine, fieldDefine) -> {
                    boolean flag;
                    Optional<String> matchItem = conversionWorkPaperEnv.getFieldKeys().stream().filter(fieldKey -> fieldDefine.getKey().equals(fieldKey)).findFirst();
                    String fieldKeyNow = matchItem.orElse(null);
                    boolean bl = flag = fieldKeyNow != null && !"".equals(fieldKeyNow);
                    if (flag) {
                        fieldNames.add(columnModelDefine.getName());
                    }
                });
                fieldDefinesMap.put(orgAndFormEnv.getTableDefine().getID(), fieldNames);
                floatReportZBCount.addAndGet(fieldNames.size());
                continue;
            }
            fieldNames = tableInfo.getColumnFieldMap().keySet().stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
            fieldDefinesMap.put(orgAndFormEnv.getTableDefine().getID(), fieldNames);
            floatReportZBCount.addAndGet(fieldNames.size());
        }
        return floatReportZBCount.get() == 0;
    }

    private double getStepProgress(List<GcConversionOrgAndFormContextEnv> orgAndFormContextEnvs) {
        double stepProgress = orgAndFormContextEnvs.size() > 0 ? new BigDecimal(0.95f / (float)orgAndFormContextEnvs.size()).setScale(6, 1).doubleValue() : 0.95;
        return stepProgress;
    }

    private double getStepProgress(List<GcConversionOrgAndFormContextEnv> orgAndFormContextEnvs, double stepProgressOfMap) {
        double stepProgress = orgAndFormContextEnvs.size() > 0 ? new BigDecimal(stepProgressOfMap / (double)orgAndFormContextEnvs.size()).setScale(6, 1).doubleValue() : 0.95;
        return stepProgress;
    }

    private double getStepProgress(Map<String, List<GcConversionWorkPaperEnv>> gcConversionWorkPaperEnvList) {
        int count = 0;
        Iterator<Map.Entry<String, List<GcConversionWorkPaperEnv>>> iterator = gcConversionWorkPaperEnvList.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, List<GcConversionWorkPaperEnv>> entry = iterator.next();
            count = gcConversionWorkPaperEnvList.entrySet().size() * entry.getValue().size();
        }
        double stepProgress = count > 0 ? new BigDecimal(0.95f / (float)count).setScale(6, 1).doubleValue() : 0.95;
        return stepProgress;
    }

    private JSONArray conversion(ConversionSystemTaskEO taskSchemeEO, GcConversionWorkPaperEnv conversionWorkPaperEnv, List<GcConversionOrgAndFormContextEnv> orgContextEnvs, List<String> afterCurrencyCodes, Map<String, List<ConversionSystemItemEO>> tableKeyAndItemMap) throws Exception {
        if (orgContextEnvs.isEmpty()) {
            return null;
        }
        HashMap<String, List<String>> fieldDefinesMap = new HashMap<String, List<String>>();
        double progress = 0.0;
        double stepProgress = 0.0;
        if (this.exportContext != null) {
            if (this.stepProgressMap != 0.0) {
                progress = this.exportContext.getProgressData().getProgressValue();
                stepProgress = this.getStepProgress(orgContextEnvs, this.stepProgressMap);
            } else {
                progress = 0.03;
                stepProgress = this.getStepProgress(orgContextEnvs);
            }
        }
        if (this.isFloatReport(conversionWorkPaperEnv, orgContextEnvs, fieldDefinesMap)) {
            if (this.exportContext != null) {
                this.exportContext.getProgressData().setProgressValueAndRefresh(progress += this.stepProgressMap);
            }
            LOGGER.error("\u6d6e\u52a8\u884c\u6570\u636e\u4e0d\u652f\u6301\u67e5\u770b");
            throw new Exception(GcI18nUtil.getMessage((String)"gc.conversion.float.error"));
        }
        JSONArray conversionDatas = new JSONArray();
        for (GcConversionOrgAndFormContextEnv orgAndFormEnv : orgContextEnvs) {
            HashSet<String> tableFieldKeys;
            Set<String> tableFieldNames;
            if (!DataRegionKind.DATA_REGION_SIMPLE.equals((Object)orgAndFormEnv.getDataRegionDefine().getRegionKind())) continue;
            TableModelRunInfo tableInfo = GcConversionUtils.getDefinitionsCache().getDataModelDefinitionsCache().getTableInfo(orgAndFormEnv.getTableDefine().getName());
            if (conversionWorkPaperEnv.getFieldKeys().size() > 0) {
                HashSet fieldNames = new HashSet();
                HashSet fieldKeys = new HashSet();
                tableInfo.getColumnFieldMap().forEach((columnModelDefine, fieldDefine) -> {
                    boolean flag;
                    Optional<String> matchItem = conversionWorkPaperEnv.getFieldKeys().stream().filter(fieldKey -> fieldDefine.getKey().equals(fieldKey)).findFirst();
                    String fieldKeyNow = matchItem.orElse(null);
                    boolean bl = flag = fieldKeyNow != null && !"".equals(fieldKeyNow);
                    if (flag) {
                        fieldNames.add(columnModelDefine.getName());
                        fieldKeys.add(fieldDefine.getKey());
                    }
                });
                tableFieldNames = fieldNames;
                tableFieldKeys = fieldKeys;
            } else {
                tableFieldNames = tableInfo.getColumnFieldMap().keySet().stream().map(ColumnModelDefine::getName).collect(Collectors.toSet());
                tableFieldKeys = tableInfo.getColumnFieldMap().values().stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
            }
            List<String> dimFieldNames = tableInfo.getDimFields().stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
            List<Object> systemItems = new ArrayList();
            if (tableKeyAndItemMap == null) {
                systemItems = this.itemDao.getSystemItemsByIndexIds(tableFieldKeys);
            } else {
                systemItems = tableKeyAndItemMap.get(orgAndFormEnv.getTableDefine().getID());
                if (systemItems == null) {
                    systemItems = this.itemDao.getSystemItemsByIndexIds(tableFieldKeys);
                    tableKeyAndItemMap.put(orgAndFormEnv.getTableDefine().getID(), systemItems);
                }
            }
            if (systemItems != null && systemItems.size() > 0) {
                GcConversionUtils conversionParams = new GcConversionUtils();
                conversionParams.setOrgAndFormEnv(orgAndFormEnv);
                conversionParams.setTableInfo(tableInfo);
                conversionParams.setSystemItems(systemItems);
                List<GcConversionWorkPaperCurrencyEnv> gcConversionWorkPaperCurrencyEnvList = this.getCurrencyEnvList(afterCurrencyCodes, conversionParams, taskSchemeEO, tableFieldNames, conversionWorkPaperEnv);
                conversionParams.setGcConversionWorkPaperCurrencyEnvList(gcConversionWorkPaperCurrencyEnvList);
                conversionParams.setDimFieldNames(dimFieldNames);
                for (String tableFieldName : tableFieldNames) {
                    FieldDefine matchFieldDefine = this.getFieldDefineByFieldName(tableInfo, tableFieldName);
                    if (!FieldType.FIELD_TYPE_DECIMAL.equals((Object)matchFieldDefine.getType())) continue;
                    conversionParams.setMatchFieldDefine(matchFieldDefine);
                    this.getConversionJson(conversionParams, conversionDatas, conversionWorkPaperEnv);
                }
            } else {
                LOGGER.error("\u5916\u5e01\u5de5\u4f5c\u5e95\u7a3f\u6307\u6807\u7684\u6c47\u7387\u7c7b\u578b\u4e3a\u7a7a");
            }
            if (this.exportContext == null) continue;
            this.exportContext.getProgressData().setProgressValueAndRefresh(progress += stepProgress);
        }
        return conversionDatas;
    }

    private FieldDefine getFieldDefineByFieldName(TableModelRunInfo tableInfo, String fieldName) {
        ColumnModelDefine columnModelDefine = tableInfo.getFieldByName(fieldName);
        FieldDefine fieldDefine = (FieldDefine)tableInfo.getColumnFieldMap().get(columnModelDefine);
        return fieldDefine;
    }

    private void getConversionJson(GcConversionUtils conversionParams, JSONArray conversionDatas, GcConversionWorkPaperEnv conversionWorkPaperEnv) throws Exception {
        JSONObject conversionData = new JSONObject();
        GcConversionWorkPaperEnv gcConversionWorkPaperEnv = new GcConversionWorkPaperEnv();
        gcConversionWorkPaperEnv.setIsSegment(conversionWorkPaperEnv.getIsSegment());
        gcConversionWorkPaperEnv.setPeriodStr(conversionWorkPaperEnv.getPeriodStr());
        gcConversionWorkPaperEnv.setSelectAdjustCode(conversionWorkPaperEnv.getSelectAdjustCode());
        boolean isSameYear = GcConversionUtils.isSameYear(GcConversionUtils.getPriorPeriod(conversionWorkPaperEnv.getPeriodStr()), conversionWorkPaperEnv.getPeriodStr());
        for (GcConversionWorkPaperCurrencyEnv gcConversionWorkPaperCurrencyEnv : conversionParams.getGcConversionWorkPaperCurrencyEnvList()) {
            BigDecimal priorBeforeFeildValue = new ConversionSimpleFormExecutorImpl().getFeildValueAsBigDecimal(gcConversionWorkPaperCurrencyEnv.getPriorBeforeQueryDataTable(), conversionParams.getMatchFieldDefine());
            if (gcConversionWorkPaperEnv.getIsSegment() && !gcConversionWorkPaperEnv.getPeriodStr().equals(gcConversionWorkPaperCurrencyEnv.getPeriodStr())) continue;
            conversionParams.getOrgAndFormEnv().setPeriodStr(gcConversionWorkPaperEnv.getPeriodStr());
            GcConversionIndexRateInfo indexRateInfo = GcConversionUtils.getRateByFieldKey(gcConversionWorkPaperCurrencyEnv.getIndexRateInfos(), conversionParams.getMatchFieldDefine().getKey());
            conversionParams.getOrgAndFormEnv().setAfterCurrencyCode(gcConversionWorkPaperCurrencyEnv.getAfterCurrencyCode());
            BigDecimal afterValue = BigDecimal.ZERO;
            if (gcConversionWorkPaperCurrencyEnv.getBeforeDataTable().getTotalCount() == 0) {
                if (conversionWorkPaperEnv.getIsSegment()) {
                    afterValue = new ConversionSimpleFormExecutorImpl().getFieldAfterValue(conversionParams.getOrgAndFormEnv(), gcConversionWorkPaperCurrencyEnv.getBeforeDataTable(), isSameYear, gcConversionWorkPaperCurrencyEnv.getPriorBeforeQueryDataTable(), gcConversionWorkPaperCurrencyEnv.getPriorAfterQueryDataTable(), gcConversionWorkPaperCurrencyEnv.getRateInfos(), conversionParams.getMatchFieldDefine(), indexRateInfo);
                }
                this.setConversionDataJson(conversionData, BigDecimal.ZERO, afterValue, new ConversionSimpleFormExecutorImpl().getFeildValueAsBigDecimal(gcConversionWorkPaperCurrencyEnv.getAfterDataTable(), conversionParams.getMatchFieldDefine()), gcConversionWorkPaperCurrencyEnv, conversionParams.getOrgAndFormEnv(), indexRateInfo, priorBeforeFeildValue, conversionWorkPaperEnv, conversionParams.getMatchFieldDefine(), isSameYear);
                continue;
            }
            BigDecimal beforeValue = new ConversionSimpleFormExecutorImpl().getFeildValueAsBigDecimal(gcConversionWorkPaperCurrencyEnv.getBeforeDataTable(), conversionParams.getMatchFieldDefine());
            afterValue = conversionParams.getDimFieldNames().contains(conversionParams.getMatchFieldDefine().getCode()) ? new BigDecimal(String.valueOf(this.getAfterDimensionFieldValue(conversionParams.getTableInfo(), gcConversionWorkPaperCurrencyEnv.getBeforeDataTable().getItem(0), gcConversionWorkPaperCurrencyEnv.getAfterDataTable().getItem(0), conversionParams.getMatchFieldDefine().getCode(), conversionParams.getMatchFieldDefine()))) : (indexRateInfo != null ? (gcConversionWorkPaperCurrencyEnv.getRateInfos() == null || gcConversionWorkPaperCurrencyEnv.getRateInfos().size() == 0 ? beforeValue : new ConversionSimpleFormExecutorImpl().getFieldAfterValue(conversionParams.getOrgAndFormEnv(), gcConversionWorkPaperCurrencyEnv.getBeforeDataTable(), isSameYear, gcConversionWorkPaperCurrencyEnv.getPriorBeforeQueryDataTable(), gcConversionWorkPaperCurrencyEnv.getPriorAfterQueryDataTable(), gcConversionWorkPaperCurrencyEnv.getRateInfos(), conversionParams.getMatchFieldDefine(), indexRateInfo)) : beforeValue);
            this.setConversionDataJson(conversionData, beforeValue, afterValue, new ConversionSimpleFormExecutorImpl().getFeildValueAsBigDecimal(gcConversionWorkPaperCurrencyEnv.getAfterDataTable(), conversionParams.getMatchFieldDefine()), gcConversionWorkPaperCurrencyEnv, conversionParams.getOrgAndFormEnv(), indexRateInfo, priorBeforeFeildValue, conversionWorkPaperEnv, conversionParams.getMatchFieldDefine(), isSameYear);
        }
        if (!conversionData.isEmpty()) {
            if (Boolean.parseBoolean(conversionData.optString("isDifference"))) {
                ++this.differenceCount;
            }
            conversionDatas.put((Object)conversionData);
        }
        if (gcConversionWorkPaperEnv.getIsSegment()) {
            if (new PeriodWrapper(gcConversionWorkPaperEnv.getPeriodStr()).getPeriod() == 1) {
                if (this.getRateLJType(conversionParams.getSystemItems().get(0).getRateTypeCode())) {
                    gcConversionWorkPaperEnv.setIsSegment(false);
                } else {
                    return;
                }
            }
            gcConversionWorkPaperEnv.setPeriodStr(GcConversionUtils.getPriorPeriod(gcConversionWorkPaperEnv.getPeriodStr()));
            this.getConversionJson(conversionParams, conversionDatas, gcConversionWorkPaperEnv);
        }
    }

    private List<GcConversionWorkPaperCurrencyEnv> getCurrencyEnvList(List<String> afterCurrencyCodes, GcConversionUtils conversionParams, ConversionSystemTaskEO taskSchemeEO, Set<String> tableFieldNames, GcConversionWorkPaperEnv conversionWorkPaperEnv) throws Exception {
        ArrayList<GcConversionWorkPaperCurrencyEnv> gcConversionWorkPaperCurrencyEnvList = new ArrayList<GcConversionWorkPaperCurrencyEnv>();
        GcConversionWorkPaperEnv conversionWorkPaperEnvNew = new GcConversionWorkPaperEnv();
        conversionWorkPaperEnvNew.setIsSegment(conversionWorkPaperEnv.getIsSegment());
        Collection<FieldDefine> converisonFieldDefines = this.getConverisonFieldDefines(conversionParams.getTableInfo(), tableFieldNames);
        for (String afterCurrencyCode : afterCurrencyCodes) {
            conversionParams.getOrgAndFormEnv().setAfterCurrencyCode(afterCurrencyCode);
            GcConversionWorkPaperCurrencyEnv gcConversionWorkPaperCurrencyEnv = new GcConversionWorkPaperCurrencyEnv();
            gcConversionWorkPaperCurrencyEnv.setAfterCurrencyCode(afterCurrencyCode);
            gcConversionWorkPaperCurrencyEnv.setPeriodStr(conversionParams.getOrgAndFormEnv().getPeriodStr());
            Map<String, BigDecimal> rateInfos = this.getConversionRateInfos(conversionParams.getOrgAndFormEnv(), taskSchemeEO);
            List<GcConversionIndexRateInfo> indexRateInfos = GcConversionUtils.getConversionIndexRateInfos(conversionParams.getOrgAndFormEnv(), taskSchemeEO, conversionParams.getSystemItems(), rateInfos);
            gcConversionWorkPaperCurrencyEnv.setIndexRateInfos(indexRateInfos);
            IDataTable priorBeforeQueryDataTable = GcConversionUtils.getPriorQueryDataTable(conversionParams.getOrgAndFormEnv(), converisonFieldDefines, true);
            gcConversionWorkPaperCurrencyEnv.setPriorBeforeQueryDataTable(priorBeforeQueryDataTable);
            IDataTable priorAfterQueryDataTable = GcConversionUtils.getPriorQueryDataTable(conversionParams.getOrgAndFormEnv(), converisonFieldDefines, false);
            gcConversionWorkPaperCurrencyEnv.setPriorAfterQueryDataTable(priorAfterQueryDataTable);
            IDataTable beforeDataTable = GcConversionUtils.getQueryDataTable(conversionParams.getOrgAndFormEnv(), conversionParams.getTableInfo(), converisonFieldDefines, true);
            gcConversionWorkPaperCurrencyEnv.setBeforeDataTable(beforeDataTable);
            int beforeTotalCount = beforeDataTable.getTotalCount();
            IDataTable afterDataTable = GcConversionUtils.getQueryDataTable(conversionParams.getOrgAndFormEnv(), conversionParams.getTableInfo(), converisonFieldDefines, false);
            gcConversionWorkPaperCurrencyEnv.setAfterDataTable(afterDataTable);
            int afterTotalCount = afterDataTable.getTotalCount();
            if (beforeTotalCount > 1 || afterTotalCount > 1) {
                LOGGER.error("\u5f53\u524d\u6298\u7b97\u7ef4\u5ea6\u4e0b\u56fa\u5b9a\u8868\u5b58\u5728\u591a\u6761\u8bb0\u5f55\uff0c\u53ef\u80fd\u662f\u5783\u573e\u6570\u636e\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.conversion.totalCount.error"));
            }
            gcConversionWorkPaperCurrencyEnv.setRateInfos(rateInfos);
            gcConversionWorkPaperCurrencyEnvList.add(gcConversionWorkPaperCurrencyEnv);
        }
        if (conversionWorkPaperEnvNew.getIsSegment()) {
            if (new PeriodWrapper(conversionParams.getOrgAndFormEnv().getPeriodStr()).getPeriod() == 1) {
                if (this.getRateLJType(conversionParams.getSystemItems().get(0).getRateTypeCode())) {
                    conversionWorkPaperEnvNew.setIsSegment(false);
                } else {
                    return gcConversionWorkPaperCurrencyEnvList;
                }
            }
            conversionParams.getOrgAndFormEnv().setPeriodStr(GcConversionUtils.getPriorPeriod(conversionParams.getOrgAndFormEnv().getPeriodStr()));
            gcConversionWorkPaperCurrencyEnvList.addAll(this.getCurrencyEnvList(afterCurrencyCodes, conversionParams, taskSchemeEO, tableFieldNames, conversionWorkPaperEnvNew));
        }
        return gcConversionWorkPaperCurrencyEnvList;
    }

    private Collection<FieldDefine> getConverisonFieldDefines(TableModelRunInfo tableInfo, Set<String> converisonFieldNames) {
        List<FieldDefine> converisonFieldDefines = converisonFieldNames.stream().map(converisonFieldName -> {
            ColumnModelDefine column = tableInfo.getFieldByName(converisonFieldName);
            FieldDefine fieldDefine = (FieldDefine)tableInfo.getColumnFieldMap().get(column);
            return fieldDefine;
        }).collect(Collectors.toList());
        return converisonFieldDefines;
    }

    private boolean getRateType(GcConversionIndexRateInfo indexRateInfo) {
        if (indexRateInfo == null) {
            return false;
        }
        String rateTypeCode = indexRateInfo.getRateTypeCode();
        return RateTypeEnum.SEGMENT_QC_LJ.getCode().equals(rateTypeCode) || RateTypeEnum.SEGMENT_QC_BN.getCode().equals(rateTypeCode) || RateTypeEnum.SEGMENT_PJ_LJ.getCode().equals(rateTypeCode) || RateTypeEnum.SEGMENT_PJ_BN.getCode().equals(rateTypeCode) || RateTypeEnum.SEGMENT_FORMULA_LJ.getCode().equals(rateTypeCode) || RateTypeEnum.SEGMENT_FORMULA_BN.getCode().equals(rateTypeCode);
    }

    private boolean getRateLJType(String rateTypeCode) {
        return RateTypeEnum.SEGMENT_QC_LJ.getCode().equals(rateTypeCode) || RateTypeEnum.SEGMENT_PJ_LJ.getCode().equals(rateTypeCode) || RateTypeEnum.SEGMENT_FORMULA_LJ.getCode().equals(rateTypeCode);
    }

    protected Object getAfterDimensionFieldValue(TableModelRunInfo tableInfo, IDataRow beforeItem, IDataRow afterItem, String fieldName, FieldDefine dimFeildDefine) throws DataTypeException {
        String dimensionName;
        DimensionValueSet masterKeys = afterItem.getRowKeys();
        Object masterKeyValue = masterKeys.getValue(dimensionName = tableInfo.getDimensionName(fieldName));
        if (masterKeyValue != null) {
            return masterKeyValue;
        }
        AbstractData value = beforeItem.getValue(dimFeildDefine);
        if (value.isNull) {
            return null;
        }
        return value.getAsObject();
    }

    private BigDecimal getConversion(BigDecimal conversion) {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        return conversion.compareTo(bigDecimal) == 0 ? bigDecimal : conversion;
    }

    private BigDecimal getSegmentRateData(GcConversionIndexRateInfo indexRateInfo, Map<String, BigDecimal> rateInfos, IDataTable beforeDataTable, GcConversionOrgAndFormContextEnv env) {
        String rateTypeCode = indexRateInfo.getRateTypeCode();
        BigDecimal qcRateValue = RateTypeEnum.SEGMENT_QC_LJ.getCode().equals(rateTypeCode) ? rateInfos.get(RateTypeEnum.QC.getCode()) : (RateTypeEnum.SEGMENT_QC_BN.getCode().equals(rateTypeCode) ? rateInfos.get(RateTypeEnum.QC.getCode()) : (RateTypeEnum.SEGMENT_PJ_LJ.getCode().equals(rateTypeCode) ? rateInfos.get(RateTypeEnum.PJ.getCode()) : (RateTypeEnum.SEGMENT_PJ_BN.getCode().equals(rateTypeCode) ? rateInfos.get(RateTypeEnum.PJ.getCode()) : (RateTypeEnum.SEGMENT_FORMULA_LJ.getCode().equals(rateTypeCode) ? (beforeDataTable.getTotalCount() == 0 ? GcConversionUtils.getConversionZbRateValue(beforeDataTable.newEmptyRow(false), env, indexRateInfo) : GcConversionUtils.getConversionZbRateValue(beforeDataTable.getItem(0), env, indexRateInfo)) : (RateTypeEnum.SEGMENT_FORMULA_BN.getCode().equals(rateTypeCode) ? (beforeDataTable.getTotalCount() == 0 ? GcConversionUtils.getConversionZbRateValue(beforeDataTable.newEmptyRow(false), env, indexRateInfo) : GcConversionUtils.getConversionZbRateValue(beforeDataTable.getItem(0), env, indexRateInfo)) : rateInfos.get(indexRateInfo.getRateTypeCode()))))));
        if (qcRateValue == null || qcRateValue.compareTo(BigDecimal.ZERO) == 0) {
            qcRateValue = BigDecimal.ZERO;
        }
        return qcRateValue;
    }

    private void setConversionDataJson(JSONObject conversionData, BigDecimal conversionCurrency, BigDecimal conversionAfter, BigDecimal conversion, GcConversionWorkPaperCurrencyEnv gcConversionWorkPaperCurrencyEnv, GcConversionOrgAndFormContextEnv orgAndFormEnv, GcConversionIndexRateInfo indexRateInfo, BigDecimal priorBeforeFeildValue, GcConversionWorkPaperEnv conversionWorkPaperEnv, FieldDefine matchFieldDefine, Boolean isSameYear) {
        try {
            BigDecimal priorAfterFeildValue = new ConversionSimpleFormExecutorImpl().getFeildValueAsBigDecimal(gcConversionWorkPaperCurrencyEnv.getPriorAfterQueryDataTable(), matchFieldDefine);
            BigDecimal priorBeforeValue = new ConversionSimpleFormExecutorImpl().getFeildValueAsBigDecimal(gcConversionWorkPaperCurrencyEnv.getPriorBeforeQueryDataTable(), matchFieldDefine);
            this.setConversionJson(conversionData, conversionCurrency, conversionAfter, conversion, gcConversionWorkPaperCurrencyEnv, orgAndFormEnv, indexRateInfo, priorBeforeFeildValue, conversionWorkPaperEnv, matchFieldDefine, isSameYear, priorAfterFeildValue, priorBeforeValue);
        }
        catch (Exception e) {
            LOGGER.error("\u5f02\u5e38\uff1a" + e.getMessage() + "\uff0c\u65f6\u671f\uff1a" + gcConversionWorkPaperCurrencyEnv.getPeriodStr(), e);
            throw new BusinessRuntimeException(e.getMessage());
        }
    }

    private void setConversionJson(JSONObject conversionData, BigDecimal conversionCurrency, BigDecimal conversionAfter, BigDecimal conversion, GcConversionWorkPaperCurrencyEnv gcConversionWorkPaperCurrencyEnv, GcConversionOrgAndFormContextEnv orgAndFormEnv, GcConversionIndexRateInfo indexRateInfo, BigDecimal priorBeforeFeildValue, GcConversionWorkPaperEnv conversionWorkPaperEnv, FieldDefine matchFieldDefine, Boolean isSameYear, BigDecimal priorAfterFeildValue, BigDecimal priorBeforeValue) {
        if (conversionWorkPaperEnv.getIsSegment()) {
            if (priorBeforeFeildValue.compareTo(conversionCurrency) == 0) {
                return;
            }
            conversionData.put("month", (Object)(PeriodUtils.getDateStrFromPeriod((String)gcConversionWorkPaperCurrencyEnv.getPeriodStr()) + "\u53d8\u52a8"));
            conversionData.put("conversionChange", (Object)NumberUtils.format((String)this.getConversion(conversionCurrency).subtract(priorBeforeValue).toString()));
            conversionData.put("conversionChangeSegment", (Object)this.getConversion(conversionCurrency).subtract(priorBeforeValue).setScale(2, 4));
            if (gcConversionWorkPaperCurrencyEnv.getRateInfos() == null || gcConversionWorkPaperCurrencyEnv.getRateInfos().size() == 0) {
                conversionData.put("conversionAfter" + orgAndFormEnv.getAfterCurrencyCode(), (Object)NumberUtils.format((Number)conversionAfter.subtract(priorBeforeValue)));
                conversionData.put("conversionAfter" + orgAndFormEnv.getAfterCurrencyCode() + "Segment", (Object)conversionAfter.subtract(priorBeforeValue).setScale(2, 4));
            } else {
                conversionData.put("conversionAfter" + orgAndFormEnv.getAfterCurrencyCode(), (Object)NumberUtils.format((Number)conversionAfter.subtract(priorAfterFeildValue)));
                conversionData.put("conversionAfter" + orgAndFormEnv.getAfterCurrencyCode() + "Segment", (Object)conversionAfter.subtract(priorAfterFeildValue).setScale(2, 4));
            }
            if (!isSameYear.booleanValue() && !this.getRateLJType(indexRateInfo.getRateTypeCode())) {
                conversionData.put("conversionChange", (Object)NumberUtils.format((Number)this.getConversion(conversionCurrency)));
                conversionData.put("conversionChangeSegment", (Object)this.getConversion(conversionCurrency));
                conversionData.put("conversionAfter" + orgAndFormEnv.getAfterCurrencyCode(), (Object)NumberUtils.format((Number)this.getConversion(conversionAfter)));
                conversionData.put("conversionAfter" + orgAndFormEnv.getAfterCurrencyCode() + "Segment", (Object)this.getConversion(conversionAfter));
            }
        } else {
            conversionData.put("month", (Object)"\u671f\u521d\u6570");
            conversionData.put("conversionChange", (Object)NumberUtils.format((String)this.getConversion(conversionCurrency).toString()));
            conversionData.put("conversionChangeSegment", (Object)this.getConversion(conversionCurrency).setScale(2, 4));
            conversionData.put("conversionAfter" + orgAndFormEnv.getAfterCurrencyCode(), (Object)NumberUtils.format((String)this.getConversion(conversionAfter).toString()));
            conversionData.put("conversionAfter" + orgAndFormEnv.getAfterCurrencyCode() + "Segment", (Object)this.getConversion(conversionAfter).setScale(2, 4));
        }
        conversionData.put("zbId", (Object)matchFieldDefine.getKey());
        conversionData.put("zbName", (Object)matchFieldDefine.getTitle());
        conversionData.put("rateTypeSegment", this.getRateType(indexRateInfo));
        conversionData.put("conversion" + orgAndFormEnv.getBeforeCurrencyCode(), (Object)NumberUtils.format((String)this.getConversion(conversionCurrency).toString()));
        conversionData.put("conversion" + orgAndFormEnv.getAfterCurrencyCode(), (Object)NumberUtils.format((String)this.getConversion(conversion).toString()));
        if (StringUtils.isEmpty((String)conversionData.optString("afterCurrency"))) {
            conversionData.put("afterCurrency", (Object)orgAndFormEnv.getAfterCurrencyCode());
        } else {
            conversionData.put("afterCurrency", (Object)(conversionData.optString("afterCurrency") + "," + orgAndFormEnv.getAfterCurrencyCode()));
        }
        conversionData.put("rate" + orgAndFormEnv.getAfterCurrencyCode(), 1.0);
        boolean isNotconv = false;
        if (indexRateInfo != null) {
            String typeCode;
            ConversionTypeEnum conversionTypeEnum = null;
            if (indexRateInfo.getRateTypeCode().endsWith("_01")) {
                conversionTypeEnum = ConversionTypeEnum.DIRECT;
                typeCode = indexRateInfo.getRateTypeCode().substring(0, indexRateInfo.getRateTypeCode().lastIndexOf("_01"));
            } else if (indexRateInfo.getRateTypeCode().endsWith("_02")) {
                conversionTypeEnum = ConversionTypeEnum.TYPE_LJ;
                typeCode = indexRateInfo.getRateTypeCode().substring(0, indexRateInfo.getRateTypeCode().lastIndexOf("_02"));
            } else if (indexRateInfo.getRateTypeCode().endsWith("_03")) {
                conversionTypeEnum = ConversionTypeEnum.TYPE_BN;
                typeCode = indexRateInfo.getRateTypeCode().substring(0, indexRateInfo.getRateTypeCode().lastIndexOf("_03"));
            } else {
                typeCode = indexRateInfo.getRateTypeCode();
            }
            GcBaseData iBaseData = ((BaseDataCache)SpringContextUtils.getBean(BaseDataCache.class)).queryBaseDataByCode("MD_RATETYPE", typeCode);
            RateTypeEnum rateTypeEnum = RateTypeEnum.getEnumByCode((String)typeCode);
            if (iBaseData != null) {
                if (rateTypeEnum != null) {
                    if (conversionWorkPaperEnv.getIsSegment()) {
                        conversionData.put("rate" + orgAndFormEnv.getAfterCurrencyCode(), (Object)new BigDecimal(this.getSegmentRateData(indexRateInfo, gcConversionWorkPaperCurrencyEnv.getRateInfos(), gcConversionWorkPaperCurrencyEnv.getBeforeDataTable(), orgAndFormEnv).toString()));
                    } else if (RateTypeEnum.NOTCONV.getCode().equals(typeCode)) {
                        isNotconv = true;
                        conversionData.put("conversionAfter" + orgAndFormEnv.getAfterCurrencyCode(), (Object)"-");
                        conversionData.put("rate" + orgAndFormEnv.getAfterCurrencyCode(), (Object)"-");
                    } else if (RateTypeEnum.COPY.getCode().equals(typeCode)) {
                        conversionData.put("conversionAfter" + orgAndFormEnv.getAfterCurrencyCode(), (Object)NumberUtils.format((String)this.getConversion(conversionCurrency).toString()));
                        conversionData.put("rate" + orgAndFormEnv.getAfterCurrencyCode(), (Object)BigDecimal.ONE);
                    } else if (ConversionTypeEnum.TYPE_LJ.equals((Object)conversionTypeEnum) || ConversionTypeEnum.TYPE_BN.equals((Object)conversionTypeEnum)) {
                        conversionData.put("rate" + orgAndFormEnv.getAfterCurrencyCode(), (Object)"-");
                    } else if (RateTypeEnum.FORMULA.getCode().equals(typeCode)) {
                        this.getRateForEmptyRow(gcConversionWorkPaperCurrencyEnv, conversionData, orgAndFormEnv, indexRateInfo);
                    } else if (gcConversionWorkPaperCurrencyEnv.getRateInfos() == null || gcConversionWorkPaperCurrencyEnv.getRateInfos().size() == 0) {
                        conversionData.put("rate" + orgAndFormEnv.getAfterCurrencyCode(), (Object)"0");
                    } else {
                        conversionData.put("rate" + orgAndFormEnv.getAfterCurrencyCode(), (Object)new BigDecimal(((BigDecimal)gcConversionWorkPaperCurrencyEnv.getRateInfos().get(typeCode)).toString()));
                    }
                    if (ConversionTypeEnum.DIRECT.equals((Object)conversionTypeEnum)) {
                        conversionData.put("rateType", (Object)(rateTypeEnum.getTitle() + "|\u76f4\u63a5\u6298\u7b97"));
                    } else if (ConversionTypeEnum.TYPE_LJ.equals((Object)conversionTypeEnum)) {
                        conversionData.put("rateType", (Object)(rateTypeEnum.getTitle() + "|\u7d2f\u8ba1\u5206\u6bb5"));
                    } else if (ConversionTypeEnum.TYPE_BN.equals((Object)conversionTypeEnum)) {
                        conversionData.put("rateType", (Object)(rateTypeEnum.getTitle() + "|\u672c\u5e74\u5206\u6bb5"));
                    } else {
                        conversionData.put("rateType", (Object)rateTypeEnum.getTitle());
                    }
                } else {
                    conversionData.put("rateType", (Object)iBaseData.getTitle());
                    this.getRateForEmptyRow(gcConversionWorkPaperCurrencyEnv, conversionData, orgAndFormEnv, indexRateInfo);
                }
            } else if (gcConversionWorkPaperCurrencyEnv.getRateInfos() == null || gcConversionWorkPaperCurrencyEnv.getRateInfos().size() == 0) {
                conversionData.put("rate" + orgAndFormEnv.getAfterCurrencyCode(), (Object)"0");
            } else {
                conversionData.put("rateType", (Object)"-");
            }
        } else {
            conversionData.put("rateType", (Object)"-");
        }
        boolean isDifferenceBool = conversionData.get("conversionAfter" + orgAndFormEnv.getAfterCurrencyCode()).equals(conversionData.get("conversion" + orgAndFormEnv.getAfterCurrencyCode()));
        if (isNotconv) {
            conversionData.put("isDifference", false);
            conversionData.put("isDifference" + orgAndFormEnv.getAfterCurrencyCode(), false);
        } else if (isDifferenceBool) {
            conversionData.put("isDifference" + orgAndFormEnv.getAfterCurrencyCode(), false);
            conversionData.put("isDifference", Boolean.parseBoolean(conversionData.optString("isDifference")));
        } else {
            conversionData.put("isDifference", true);
            conversionData.put("isDifference" + orgAndFormEnv.getAfterCurrencyCode(), true);
        }
    }

    private void getRateForEmptyRow(GcConversionWorkPaperCurrencyEnv gcConversionWorkPaperCurrencyEnv, JSONObject conversionData, GcConversionOrgAndFormContextEnv orgAndFormEnv, GcConversionIndexRateInfo indexRateInfo) {
        if (gcConversionWorkPaperCurrencyEnv.getBeforeDataTable().getTotalCount() == 0) {
            conversionData.put("rate" + orgAndFormEnv.getAfterCurrencyCode(), (Object)new BigDecimal(GcConversionUtils.getConversionZbRateValue(gcConversionWorkPaperCurrencyEnv.getBeforeDataTable().newEmptyRow(false), orgAndFormEnv, indexRateInfo).toString()));
        } else {
            conversionData.put("rate" + orgAndFormEnv.getAfterCurrencyCode(), (Object)new BigDecimal(GcConversionUtils.getConversionZbRateValue(gcConversionWorkPaperCurrencyEnv.getBeforeDataTable().getItem(0), orgAndFormEnv, indexRateInfo).toString()));
        }
    }

    protected final Map<String, BigDecimal> getConversionRateInfos(GcConversionOrgAndFormContextEnv formContextEnv, ConversionSystemTaskEO taskSchemeEO) {
        String beforeCurrencyCode = formContextEnv.getBeforeCurrencyCode();
        String afterCurrencyCode = formContextEnv.getAfterCurrencyCode();
        String periodStr = formContextEnv.getPeriodStr();
        return this.conversionRateService.getRateInfos(taskSchemeEO.getRateSchemeCode(), formContextEnv.getSchemeId(), beforeCurrencyCode, afterCurrencyCode, periodStr);
    }

    @Override
    public JSONObject getExcelTableData(GcOrgCacheVO orgToJsonVO) {
        JSONObject conversionTable = new JSONObject();
        JSONObject currencyBeforeTable = new JSONObject();
        JSONArray currencyAfterTable = new JSONArray();
        String orgBeforeCurrencyCode = String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYID"));
        GcBaseData beforeCurrency = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", orgBeforeCurrencyCode);
        currencyBeforeTable.put("zbName", (Object)"\u6307\u6807");
        currencyBeforeTable.put("currencyTitle", (Object)beforeCurrency.getTitle());
        currencyBeforeTable.put("rateType", (Object)"\u6c47\u7387\u7c7b\u578b");
        currencyBeforeTable.put("currencyCode", (Object)orgBeforeCurrencyCode);
        conversionTable.put("currencyBeforeTable", (Object)currencyBeforeTable);
        String currencyIds = String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYIDS"));
        for (String orgAfterCurrencyCode : currencyIds.split(";")) {
            if (orgBeforeCurrencyCode.equals(orgAfterCurrencyCode)) continue;
            GcBaseData afterCurrency = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", orgAfterCurrencyCode);
            JSONObject currencyTable = new JSONObject();
            currencyTable.put("currencyTitle", (Object)afterCurrency.getTitle());
            currencyTable.put("currencyCode", (Object)orgAfterCurrencyCode);
            currencyAfterTable.put((Object)currencyTable);
        }
        conversionTable.put("currencyAfterTable", (Object)currencyAfterTable);
        return conversionTable;
    }

    private JSONObject getTableData(GcOrgCacheVO orgToJsonVO) {
        JSONObject conversionTable = new JSONObject();
        JSONObject currencyBeforeTable = new JSONObject();
        JSONArray currencyAfterTable = new JSONArray();
        String orgBeforeCurrencyCode = String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYID"));
        GcBaseData beforeCurrency = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", orgBeforeCurrencyCode);
        if (beforeCurrency == null) {
            String msg = GcI18nUtil.getMessage((String)"gc.coversion.service.beforeCurrency.null.error", (Object[])new Object[]{orgToJsonVO.getTitle(), orgBeforeCurrencyCode});
            LOGGER.error(msg);
            throw new BusinessRuntimeException(msg);
        }
        currencyBeforeTable.put("currencyId", (Object)orgBeforeCurrencyCode);
        currencyBeforeTable.put("currencyTitle", (Object)beforeCurrency.getTitle());
        currencyBeforeTable.put("currencyProp", (Object)("conversion" + orgBeforeCurrencyCode));
        conversionTable.put("currencyBeforeTable", (Object)currencyBeforeTable);
        int exportExcelCount = 3;
        String currencyIds = String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYIDS"));
        for (String orgAfterCurrencyCode : currencyIds.split(";")) {
            if (orgBeforeCurrencyCode.equals(orgAfterCurrencyCode)) continue;
            GcBaseData afterCurrency = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", orgAfterCurrencyCode);
            JSONObject currencyTable = new JSONObject();
            currencyTable.put("currencyId", (Object)orgAfterCurrencyCode);
            currencyTable.put("currencyTitle", (Object)afterCurrency.getTitle());
            currencyTable.put("rateType", (Object)("rateType" + orgAfterCurrencyCode));
            currencyTable.put("rate", (Object)("rate" + orgAfterCurrencyCode));
            currencyTable.put("conversionAfter", (Object)("conversionAfter" + orgAfterCurrencyCode));
            currencyTable.put("conversion", (Object)("conversion" + orgAfterCurrencyCode));
            exportExcelCount += 3;
            currencyAfterTable.put((Object)currencyTable);
        }
        conversionTable.put("currencyAfterTable", (Object)currencyAfterTable);
        conversionTable.put("exportExcelCount", exportExcelCount);
        return conversionTable;
    }

    private GcOrgCacheVO getOrgByCode(GcConversionWorkPaperEnv env) {
        YearPeriodObject yp = new YearPeriodObject(null, env.getPeriodStr());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)env.getOrgVersionType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        return instance.getOrgByCode(env.getOrgId());
    }

    private List<GcOrgCacheVO> getAllOrg(String periodStr, String orgVersionType) {
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgVersionType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        return instance.listAllOrgByParentIdContainsSelf("");
    }

    private List<String> getAfterCurrencyCodes(GcOrgCacheVO orgToJsonVO) {
        String orgTitle = orgToJsonVO.getTitle();
        String orgBeforeCurrencyCode = String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYID"));
        String currencyIds = String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYIDS"));
        GcBaseData beforeCurrency = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", orgBeforeCurrencyCode);
        ArrayList<String> orgAfterCurrencyCodes = new ArrayList<String>();
        for (String orgAfterCurrencyCode : currencyIds.split(";")) {
            if (orgAfterCurrencyCode.equals(orgBeforeCurrencyCode)) continue;
            orgAfterCurrencyCodes.add(orgAfterCurrencyCode);
            if (GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", orgAfterCurrencyCode) != null) continue;
            String msg = "\u5355\u4f4d\uff1a[".concat(orgTitle).concat("]\u8df3\u8fc7\u6298\u7b97\uff0c\u539f\u56e0\uff1a\u672a\u627e\u5230\u6298\u7b97\u540e\u5e01\u79cd\u57fa\u7840\u6570\u636e\u9879[CODE:").concat(orgAfterCurrencyCode).concat("]\u3002");
            LOGGER.info(msg);
            return null;
        }
        if (beforeCurrency == null) {
            String msg = "\u5355\u4f4d\uff1a[".concat(orgTitle).concat("]\u8df3\u8fc7\u6298\u7b97\uff0c\u539f\u56e0\uff1a\u672a\u627e\u5230\u6298\u7b97\u524d\u5e01\u79cd\u57fa\u7840\u6570\u636e\u9879[CODE:").concat(orgBeforeCurrencyCode).concat("]\u3002");
            LOGGER.info(msg);
            return null;
        }
        return orgAfterCurrencyCodes;
    }

    private List<GcConversionOrgAndFormContextEnv> getOrgAndFormAndTableContextEnvs(GcOrgCacheVO orgToJsonVO, GcConversionWorkPaperEnv env) {
        ArrayList<String> formIds = new ArrayList<String>();
        formIds.add(env.getFormId());
        Map<String, FormDefine> formDefineMap = GcConversionUtils.getFormDefinesByEnv(env.getFormId(), formIds, this.formDefines);
        if (formDefineMap == null || formDefineMap.size() == 0) {
            return null;
        }
        FormDefine formDefine = formDefineMap.get(env.getFormId());
        if (formDefine == null) {
            return null;
        }
        Map<DataRegionDefine, Set<TableModelDefine>> formTableDefines = this.formIdAndTableDefineMap.get(formDefine.getKey());
        if (formTableDefines == null) {
            formTableDefines = GcConversionUtils.getFormTableDefines(formDefine.getKey());
            this.formIdAndTableDefineMap.put(formDefine.getKey(), formTableDefines);
        }
        if (formTableDefines == null) {
            return null;
        }
        ArrayList<GcConversionOrgAndFormContextEnv> orgAndFormContextEnvs = new ArrayList<GcConversionOrgAndFormContextEnv>();
        formTableDefines.forEach((dataRegionDefine, tableDefines) -> tableDefines.stream().filter(Objects::nonNull).forEach(tableDefine -> {
            GcConversionOrgAndFormContextEnv singleOrgEnv = new GcConversionOrgAndFormContextEnv();
            singleOrgEnv.setBeforeCurrencyCode(String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYID")));
            singleOrgEnv.setOrgId(env.getOrgId());
            singleOrgEnv.setOrgTitle(orgToJsonVO.getTitle());
            singleOrgEnv.setOrgTypeId(env.getOrgTypeId());
            singleOrgEnv.setOrgVersionType(env.getOrgVersionType());
            singleOrgEnv.setPeriodStr(env.getPeriodStr());
            singleOrgEnv.setSchemeId(env.getSchemeId());
            singleOrgEnv.setTaskId(env.getTaskId());
            singleOrgEnv.setFormDefine(formDefine);
            singleOrgEnv.setTableDefine(tableDefine);
            singleOrgEnv.setOrg(orgToJsonVO);
            singleOrgEnv.setDataRegionDefine(dataRegionDefine);
            singleOrgEnv.setDimensionSet(dimensionSet);
            singleOrgEnv.setRateSchemeCode(env.getRateSchemeCode());
            singleOrgEnv.setSelectAdjustCode(env.getSelectAdjustCode());
            orgAndFormContextEnvs.add(singleOrgEnv);
        }));
        return orgAndFormContextEnvs;
    }

    static {
        LOGGER = LoggerFactory.getLogger(GCConversionWorkPaperServiceImpl.class);
    }
}

