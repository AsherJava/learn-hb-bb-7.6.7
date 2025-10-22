/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.rate.client.dto.RateTypeVO
 *  com.jiuqi.common.rate.client.enums.ApplyRangeEnum
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowCallbackHandler
 */
package com.jiuqi.gcreport.conversion.conversionsystem.executor.common;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.rate.client.dto.RateTypeVO;
import com.jiuqi.common.rate.client.enums.ApplyRangeEnum;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemItemDao;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemTaskDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO;
import com.jiuqi.gcreport.conversion.conversionsystem.executor.ConversionSystemExportExecutorImpl;
import com.jiuqi.gcreport.conversion.conversionsystem.executor.common.ConversionSystemItemExcelBO;
import com.jiuqi.gcreport.conversion.conversionsystem.executor.common.ConversionSystemItemExcelModel;
import com.jiuqi.gcreport.conversion.conversionsystem.service.ConversionSystemService;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ConversionSystemImportExportUtils {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ConversionSystemService systemService;
    @Autowired
    private ConversionSystemItemDao itemDao;
    @Autowired
    private ConversionSystemTaskDao taskSchemeDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    public List<ConversionSystemItemExcelModel> exportDatas(ConversionSystemExportExecutorImpl.ConversionSystemExportParam exportParam) {
        String exportType = exportParam.getExportType();
        String taskId = exportParam.getTaskId();
        String schemeId = exportParam.getSchemeId();
        String formGroupId = exportParam.getFormGroupId();
        String formId = exportParam.getFormId();
        HashMap<String, FormSchemeDefine> schemeId2Define = new HashMap<String, FormSchemeDefine>();
        HashMap taskSchemeId2TaskEOMap = new HashMap();
        ArrayList<FormDefine> formDefines = new ArrayList<FormDefine>();
        ArrayList<ConversionSystemItemExcelModel> excelModels = new ArrayList<ConversionSystemItemExcelModel>();
        try {
            Map<String, IndexInfo> indexInfosMapByIndexKeys;
            switch (exportType) {
                case "EXPORT_TASK": {
                    List allFormsInTask = this.runTimeViewController.queryAllFormDefinesByTask(taskId);
                    if (!CollectionUtils.isEmpty(allFormsInTask)) {
                        formDefines.addAll(allFormsInTask);
                    }
                    List formSchemeDefineList = this.runTimeViewController.queryFormSchemeByTask(taskId);
                    formSchemeDefineList.stream().forEach(v -> schemeId2Define.put(v.getKey(), (FormSchemeDefine)v));
                    break;
                }
                case "EXPORT_SCHEME": {
                    List allFormsInScheme = this.runTimeViewController.queryAllFormDefinesByFormScheme(schemeId);
                    if (!CollectionUtils.isEmpty(allFormsInScheme)) {
                        formDefines.addAll(allFormsInScheme);
                    }
                    schemeId2Define.put(schemeId, this.runTimeViewController.getFormScheme(schemeId));
                    break;
                }
                case "EXPORT_FORMGROUP": {
                    List allFormsInGroup = null;
                    try {
                        allFormsInGroup = this.runTimeViewController.getAllFormsInGroup(formGroupId);
                    }
                    catch (Exception e) {
                        throw new BusinessRuntimeException((Throwable)e);
                    }
                    if (!CollectionUtils.isEmpty(allFormsInGroup)) {
                        formDefines.addAll(allFormsInGroup);
                    }
                    schemeId2Define.put(schemeId, this.runTimeViewController.getFormScheme(schemeId));
                    break;
                }
                case "EXPORT_FORM": {
                    FormDefine formDefine2 = this.runTimeViewController.queryFormById(formId);
                    if (formDefine2 != null) {
                        formDefines.add(formDefine2);
                    }
                    schemeId2Define.put(schemeId, this.runTimeViewController.getFormScheme(schemeId));
                    break;
                }
                default: {
                    throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.system.import.exportType.notfound.error", (Object[])new Object[]{exportType}));
                }
            }
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskId);
            HashMap formId2TitleMap = new HashMap();
            HashMap formId2CodeMap = new HashMap();
            HashMap formId2SchemeId = new HashMap();
            formDefines.stream().forEach(formDefine -> {
                formId2TitleMap.put(formDefine.getKey(), formDefine.getTitle());
                formId2CodeMap.put(formDefine.getKey(), formDefine.getFormCode());
                formId2SchemeId.put(formDefine.getKey(), formDefine.getFormScheme());
            });
            HashMap regionId2Code = new HashMap();
            Map<String, RateTypeVO> rateTypeVOMap = CommonRateUtils.getShowRateType((ApplyRangeEnum)ApplyRangeEnum.REPORT).stream().collect(Collectors.toMap(RateTypeVO::getCode, v -> v));
            List<ConversionSystemItemEO> items = this.itemDao.getSystemItemBySchemeTaskIdsAndFormIds(formId2TitleMap.keySet());
            if (CollectionUtils.isEmpty(items)) {
                return excelModels;
            }
            Set<String> indexIds = items.stream().map(ConversionSystemItemEO::getIndexId).filter(Objects::nonNull).collect(Collectors.toSet());
            try {
                indexInfosMapByIndexKeys = this.getIndexInfosMapByIndexKeys(indexIds);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
            }
            items.forEach(item -> {
                IndexInfo indexInfo = (IndexInfo)indexInfosMapByIndexKeys.get(item.getIndexId());
                String currSchemeId = (String)formId2SchemeId.get(item.getFormId());
                FormSchemeDefine currFormScheme = (FormSchemeDefine)schemeId2Define.get(currSchemeId);
                if (indexInfo == null) {
                    return;
                }
                ConversionSystemItemExcelModel excelModel = new ConversionSystemItemExcelModel();
                excelModel.setTaskTitle(taskDefine.getTitle());
                excelModel.setTaskCode(taskDefine.getTaskCode());
                excelModel.setSchemeTitle(currFormScheme.getTitle());
                excelModel.setSchemeCode(currFormScheme.getFormSchemeCode());
                excelModel.setFormTitle((String)formId2TitleMap.get(item.getFormId()));
                excelModel.setFormCode((String)formId2CodeMap.get(item.getFormId()));
                excelModel.setIndexCode(indexInfo.indexCode);
                excelModel.setIndexTitle(indexInfo.indexTitle);
                excelModel.setIndexTableName(indexInfo.tableName);
                RateTypeVO rateTypeVO = (RateTypeVO)rateTypeVOMap.get(item.getRateTypeCode());
                if (rateTypeVO != null) {
                    excelModel.setRateTypeTitle(rateTypeVO.getName());
                } else {
                    excelModel.setRateTypeTitle(item.getRateTypeCode());
                }
                String reginId = item.getRegionId();
                if (regionId2Code.get(reginId) == null) {
                    DataRegionDefine regionDefine = this.runTimeViewController.queryDataRegionDefine(reginId);
                    regionId2Code.put(reginId, regionDefine.getCode());
                }
                excelModel.setRegionCode((String)regionId2Code.get(reginId));
                excelModel.setRateFormula(item.getRateFormula());
                excelModels.add(excelModel);
            });
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u6c47\u7387\u65b9\u6848\u8bbe\u7f6e\u5bfc\u51fa\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
        }
        return excelModels;
    }

    public String importDatas(List<ConversionSystemItemExcelModel> excelModels) {
        if (CollectionUtils.isEmpty(excelModels)) {
            return GcI18nUtil.getMessage((String)"gc.coversion.system.import.data.isnull.info");
        }
        List<ConversionSystemItemExcelBO> conversionSystemItemExcelBOS = this.convertExcelModelsToBOs(excelModels);
        Set<String> formIds = conversionSystemItemExcelBOS.parallelStream().map(ConversionSystemItemExcelBO::getFormId).collect(Collectors.toSet());
        Set<String> indexIds = conversionSystemItemExcelBOS.parallelStream().map(ConversionSystemItemExcelBO::getIndexId).collect(Collectors.toSet());
        List<SystemItemInfo> existsSystemIndexInfos = this.getAllExistsSystemIndexInfo(formIds, indexIds);
        ConcurrentHashMap existsSystemIndexInfosMap = new ConcurrentHashMap();
        existsSystemIndexInfos.stream().forEach(existsSystemIndexInfo -> {
            String comKey = this.getComKey(existsSystemIndexInfo.formKey, existsSystemIndexInfo.indexId);
            existsSystemIndexInfosMap.put(comKey, existsSystemIndexInfo);
        });
        String userName = NpContextHolder.getContext().getUserName();
        CopyOnWriteArrayList addItemEos = new CopyOnWriteArrayList();
        CopyOnWriteArrayList updateItemEos = new CopyOnWriteArrayList();
        Map<String, RateTypeVO> rateTypeVOMap = CommonRateUtils.getShowRateType((ApplyRangeEnum)ApplyRangeEnum.REPORT).stream().collect(Collectors.toMap(RateTypeVO::getName, v -> v, (v1, v2) -> v1));
        conversionSystemItemExcelBOS.parallelStream().forEach(conversionSystemItemExcelBO -> {
            String formId = conversionSystemItemExcelBO.getFormId();
            String indexId = conversionSystemItemExcelBO.getIndexId();
            String rateTypeTitle = conversionSystemItemExcelBO.getRateTypeTitle();
            String regionId = conversionSystemItemExcelBO.getRegionId();
            String rateFormula = conversionSystemItemExcelBO.getRateFormula();
            String systemItemComkey = this.getComKey(formId, indexId);
            SystemItemInfo systemItemInfo = (SystemItemInfo)existsSystemIndexInfosMap.get(systemItemComkey);
            RateTypeVO rateType = (RateTypeVO)rateTypeVOMap.get(rateTypeTitle);
            if (systemItemInfo != null) {
                ConversionSystemItemEO conversionSystemItemEO = new ConversionSystemItemEO();
                conversionSystemItemEO.setId(systemItemInfo.id);
                conversionSystemItemEO.setFormId(formId);
                conversionSystemItemEO.setIndexId(indexId);
                conversionSystemItemEO.setRegionId(regionId);
                if (rateType != null) {
                    conversionSystemItemEO.setRateTypeCode(rateType.getCode());
                } else {
                    conversionSystemItemEO.setRateTypeCode(rateTypeTitle);
                }
                conversionSystemItemEO.setRateFormula(rateFormula);
                conversionSystemItemEO.setModifiedtime(new Date());
                conversionSystemItemEO.setModifieduser(userName);
                updateItemEos.add(conversionSystemItemEO);
            } else {
                ConversionSystemItemEO conversionSystemItemEO = new ConversionSystemItemEO();
                conversionSystemItemEO.setId(UUIDUtils.newUUIDStr());
                conversionSystemItemEO.setFormId(formId);
                conversionSystemItemEO.setIndexId(indexId);
                conversionSystemItemEO.setRegionId(regionId);
                if (rateType != null) {
                    conversionSystemItemEO.setRateTypeCode(rateType.getCode());
                    conversionSystemItemEO.setRateFormula(rateFormula);
                }
                conversionSystemItemEO.setCreatetime(new Date());
                conversionSystemItemEO.setCreateuser(userName);
                addItemEos.add(conversionSystemItemEO);
            }
        });
        if (!CollectionUtils.isEmpty(addItemEos)) {
            this.itemDao.addBatch(addItemEos);
        }
        if (!CollectionUtils.isEmpty(updateItemEos)) {
            this.itemDao.updateBatch(updateItemEos);
        }
        return GcI18nUtil.getMessage((String)"gc.coversion.system.import.success", (Object[])new Object[]{addItemEos.size(), updateItemEos.size()});
    }

    private String getComKey(String ... keys) {
        String key = Arrays.stream(keys).collect(Collectors.joining("|"));
        return key;
    }

    private List<ConversionSystemItemExcelBO> convertExcelModelsToBOs(List<ConversionSystemItemExcelModel> excelModels) {
        ConcurrentHashMap<String, List<String>> taskCode2IdMap = new ConcurrentHashMap<String, List<String>>();
        ConcurrentHashMap<String, String> schemeCode2IdMap = new ConcurrentHashMap<String, String>();
        ConcurrentHashMap<String, String> formCode2IdMap = new ConcurrentHashMap<String, String>();
        ConcurrentHashMap<String, String> regionCode2IdMap = new ConcurrentHashMap<String, String>();
        ConcurrentHashMap<String, List<String>> tableName2IdMap = new ConcurrentHashMap<String, List<String>>();
        ConcurrentHashMap<String, String> tableNameAndIndexCode2IdMap = new ConcurrentHashMap<String, String>();
        ArrayList<ConversionSystemItemExcelBO> excelBos = new ArrayList<ConversionSystemItemExcelBO>();
        for (int rowIndex = 0; rowIndex < excelModels.size(); ++rowIndex) {
            try {
                ConversionSystemItemExcelBO excelBO = this.convertExcelModelToBO(taskCode2IdMap, schemeCode2IdMap, formCode2IdMap, regionCode2IdMap, tableName2IdMap, tableNameAndIndexCode2IdMap, excelModels.get(rowIndex));
                excelBos.add(excelBO);
                continue;
            }
            catch (Exception ex) {
                String message = GcI18nUtil.getMessage((String)"gc.coversion.system.import.error", (Object[])new Object[]{rowIndex + 2, ex.getMessage()});
                throw new BusinessRuntimeException(message, (Throwable)ex);
            }
        }
        return excelBos;
    }

    private ConversionSystemItemExcelBO convertExcelModelToBO(Map<String, List<String>> taskCode2IdMap, Map<String, String> schemeCode2IdMap, Map<String, String> formCode2IdMap, Map<String, String> regionCode2IdMap, Map<String, List<String>> tableName2IdMap, Map<String, String> tableNameAndIndexCode2IdMap, ConversionSystemItemExcelModel excelModel) {
        String tableDataScheme;
        String tableKey;
        String regionCode;
        String regionKey;
        String taskDataScheme;
        String taskKey;
        String taskCode = excelModel.getTaskCode();
        Objects.requireNonNull(taskCode, GcI18nUtil.getMessage((String)"gc.coversion.system.taskCode.notnull.error"));
        List<String> taskCodeAndDataScheme = taskCode2IdMap.get(taskCode);
        if (taskCodeAndDataScheme == null) {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefineByCode(taskCode);
            Objects.requireNonNull(taskDefine, GcI18nUtil.getMessage((String)"gc.coversion.system.taskCode.notfound.error", (Object[])new Object[]{taskCode}));
            taskKey = taskDefine.getKey();
            taskDataScheme = taskDefine.getDataScheme();
            taskCodeAndDataScheme = new ArrayList<String>();
            taskCodeAndDataScheme.add(taskKey);
            taskCodeAndDataScheme.add(taskDataScheme);
            taskCode2IdMap.put(taskCode, taskCodeAndDataScheme);
        } else {
            taskKey = taskCodeAndDataScheme.get(0);
            taskDataScheme = taskCodeAndDataScheme.get(1);
        }
        String schemeCode = excelModel.getSchemeCode();
        Objects.requireNonNull(schemeCode, GcI18nUtil.getMessage((String)"gc.coversion.system.schemeCode.notnull.error"));
        String schemeKey = schemeCode2IdMap.get(schemeCode);
        if (schemeKey == null) {
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormschemeByCode(schemeCode);
            Objects.requireNonNull(formSchemeDefine, GcI18nUtil.getMessage((String)"gc.coversion.system.schemeCode.notfound.error", (Object[])new Object[]{schemeCode}));
            schemeKey = formSchemeDefine.getKey();
            schemeCode2IdMap.put(schemeCode, schemeKey);
        }
        String formCode = excelModel.getFormCode();
        Objects.requireNonNull(formCode, GcI18nUtil.getMessage((String)"gc.coversion.system.formCode.notnull.error"));
        String formKey = formCode2IdMap.get(formCode);
        if (formKey == null) {
            try {
                FormDefine formDefine = this.runTimeViewController.queryFormByCodeInScheme(schemeKey, formCode);
                Objects.requireNonNull(formDefine, GcI18nUtil.getMessage((String)"gc.coversion.system.formCode.notfound.error", (Object[])new Object[]{schemeCode, formCode}));
                formKey = formDefine.getKey();
                formCode2IdMap.put(formCode, formKey);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
            }
        }
        if ((regionKey = regionCode2IdMap.get(regionCode = excelModel.getRegionCode())) == null) {
            DataRegionDefine regionDefine = this.runTimeViewController.getDataRegion(regionCode, formKey, schemeKey);
            regionKey = regionDefine == null ? null : regionDefine.getKey();
            regionCode2IdMap.put(regionCode, regionKey);
        }
        String indexTableName = excelModel.getIndexTableName();
        Objects.requireNonNull(indexTableName, GcI18nUtil.getMessage((String)"gc.coversion.system.indexTableName.notnull.error"));
        List<String> tableCodeAndDataScheme = tableName2IdMap.get(indexTableName);
        if (tableCodeAndDataScheme == null) {
            try {
                DataTable dataTable = this.runtimeDataSchemeService.getDataTableByCode(indexTableName);
                Objects.requireNonNull(dataTable, GcI18nUtil.getMessage((String)"gc.coversion.system.indexTableName.notfound.error", (Object[])new Object[]{indexTableName}));
                tableKey = dataTable.getKey();
                tableDataScheme = dataTable.getDataSchemeKey();
                tableCodeAndDataScheme = new ArrayList<String>();
                tableCodeAndDataScheme.add(tableKey);
                tableCodeAndDataScheme.add(tableDataScheme);
                tableName2IdMap.put(indexTableName, tableCodeAndDataScheme);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
            }
        } else {
            tableKey = tableCodeAndDataScheme.get(0);
            tableDataScheme = tableCodeAndDataScheme.get(1);
        }
        String indexCode = excelModel.getIndexCode();
        Objects.requireNonNull(indexCode, GcI18nUtil.getMessage((String)"gc.coversion.system.indexId.notnull.error"));
        String indexId = tableNameAndIndexCode2IdMap.get(indexTableName + "|" + indexCode);
        if (indexId == null) {
            try {
                DataField dataField = this.runtimeDataSchemeService.getDataFieldByTableKeyAndCode(tableKey, indexCode);
                Objects.requireNonNull(dataField, GcI18nUtil.getMessage((String)"gc.coversion.system.indexId.notfound.error", (Object[])new Object[]{indexTableName, indexCode}));
                indexId = dataField.getKey();
                tableNameAndIndexCode2IdMap.put(indexTableName + "|" + indexCode, indexId);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
            }
        }
        if (!tableDataScheme.equals(taskDataScheme)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.system.dataScheme.notSame.error"));
        }
        return new ConversionSystemItemExcelBO(taskKey, schemeKey, formKey, regionKey, indexId, excelModel.getRateTypeTitle(), excelModel.getRateFormula());
    }

    public static List<String> getTempGroupIds(TempTableCondition ... tempTableConditions) {
        ArrayList<String> tempGroupIds = new ArrayList<String>();
        for (int i = 0; i < tempTableConditions.length; ++i) {
            TempTableCondition tempTableCondition = tempTableConditions[i];
            String tempGroupId = tempTableCondition.getTempGroupId();
            if (tempGroupId == null) continue;
            tempGroupIds.add(tempGroupId);
        }
        return tempGroupIds;
    }

    public Map<String, IndexInfo> getIndexInfosMapByIndexKeys(Set<String> indexKeys) {
        if (CollectionUtils.isEmpty(indexKeys)) {
            return Collections.emptyMap();
        }
        List dataFields = this.runtimeDataSchemeService.getDataFields(new ArrayList<String>(indexKeys));
        HashMap<String, String> nrTableKey2Code = new HashMap<String, String>();
        ConcurrentHashMap<String, IndexInfo> map = new ConcurrentHashMap<String, IndexInfo>();
        for (DataField dataField : dataFields) {
            String tableCode;
            String tableKey = dataField.getDataTableKey();
            if (!nrTableKey2Code.containsKey(tableKey)) {
                DataTable dataTable = this.runtimeDataSchemeService.getDataTable(tableKey);
                if (dataTable == null) continue;
                tableCode = dataTable.getCode();
                nrTableKey2Code.put(tableKey, tableCode);
            } else {
                tableCode = (String)nrTableKey2Code.get(tableKey);
            }
            IndexInfo uploadInfo = new IndexInfo(dataField.getKey(), dataField.getCode(), dataField.getTitle(), tableCode);
            map.put(dataField.getKey(), uploadInfo);
        }
        return map;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<SystemItemInfo> getAllExistsSystemIndexInfo(Set<String> formKeys, Set<String> indexIds) {
        String sql = " select item.id, item.schemetaskid, item.formid as formid, item.indexid \n from  GC_CONV_SYSTEM_ITEM item  where 1=1 and %s and %s \n";
        final ArrayList<SystemItemInfo> existsSystemIndexInfos = new ArrayList<SystemItemInfo>();
        List<String> tempGroupIds = null;
        try {
            TempTableCondition whereTitleSql = SqlUtils.getConditionOfMulStr(formKeys, (String)"item.formid", (boolean)false);
            TempTableCondition whereTitleSql2 = SqlUtils.getConditionOfMulStr(indexIds, (String)"item.indexid", (boolean)false);
            String formatSql = String.format(sql, whereTitleSql.getCondition(), whereTitleSql2.getCondition());
            tempGroupIds = ConversionSystemImportExportUtils.getTempGroupIds(whereTitleSql, whereTitleSql2);
            this.jdbcTemplate.query(formatSql, new RowCallbackHandler(){

                public void processRow(ResultSet rs) throws SQLException {
                    int index = 1;
                    String id = rs.getString(index++);
                    String taskSchemeKey = rs.getString(index++);
                    String formKey = rs.getString(index++);
                    String indexId = rs.getString(index++);
                    SystemItemInfo systemIndexInfo = new SystemItemInfo(id, taskSchemeKey, formKey, indexId);
                    existsSystemIndexInfos.add(systemIndexInfo);
                }
            });
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupIds(tempGroupIds);
        }
        return existsSystemIndexInfos;
    }

    public static class SystemItemInfo {
        public String id;
        public String taskSchemeKey;
        public String formKey;
        public String indexId;

        public SystemItemInfo(String id, String taskSchemeKey, String formKey, String indexId) {
            this.id = id;
            this.taskSchemeKey = taskSchemeKey;
            this.formKey = formKey;
            this.indexId = indexId;
        }
    }

    public static class IndexInfo {
        public String indexKey;
        public String indexTitle;
        public String indexCode;
        public String tableName;

        public IndexInfo(String indexKey, String indexCode, String indexTitle, String tableName) {
            this.indexKey = indexKey;
            this.indexCode = indexCode;
            this.indexTitle = indexTitle;
            this.tableName = tableName;
        }
    }
}

