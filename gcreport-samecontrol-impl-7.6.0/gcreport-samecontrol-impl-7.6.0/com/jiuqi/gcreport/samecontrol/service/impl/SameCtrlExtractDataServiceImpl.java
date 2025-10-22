/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.GCAdjTypeEnum
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlChgOrgVO
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractTableZbSetting
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.GCAdjTypeEnum;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlChgEnvContextImpl;
import com.jiuqi.gcreport.samecontrol.extract.ReportExtractProcessor;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractDataService;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlChgOrgVO;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractTableZbSetting;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SameCtrlExtractDataServiceImpl
implements SameCtrlExtractDataService {
    @Override
    public void initReportSameCtrlChgEnvContextImpl(SameCtrlExtractDataVO condition, SameCtrlChgEnvContextImpl sameCtrlChgEnvContext) {
        SameCtrlChgOrgVO sameCtrlChgOrg = condition.getSameCtrlChgOrg();
        SameCtrlExtractReportCond sameCtrlExtractReportData = new SameCtrlExtractReportCond();
        sameCtrlExtractReportData.setTaskId(condition.getTaskId());
        sameCtrlExtractReportData.setSchemeId(condition.getSchemeId());
        sameCtrlExtractReportData.setSystemId(condition.getSystemId());
        sameCtrlExtractReportData.setOrgType(condition.getOrgType());
        sameCtrlExtractReportData.setChangedCode(sameCtrlChgOrg.getChangedCode());
        sameCtrlExtractReportData.setPeriodStr(condition.getPeriodStr());
        sameCtrlExtractReportData.setChangeDate(sameCtrlChgOrg.getChangeDate());
        sameCtrlExtractReportData.setCurrencyId(condition.getCurrencyId());
        sameCtrlExtractReportData.setAdjTypeId(condition.getAdjTypeId());
        sameCtrlExtractReportData.setVirtualCode(sameCtrlChgOrg.getVirtualCode());
        sameCtrlExtractReportData.setSelectAdjustCode(condition.getSelectAdjustCode());
        sameCtrlChgEnvContext.setSameCtrlExtractReportCond(sameCtrlExtractReportData);
    }

    @Override
    public void initOffsetSameCtrlChgEnvContextImpl(SameCtrlExtractDataVO condition, SameCtrlChgEnvContextImpl sameCtrlChgEnvContext) {
        SameCtrlChgOrgVO sameCtrlChgOrg = condition.getSameCtrlChgOrg();
        SameCtrlOffsetCond sameCtrlOffsetCond = new SameCtrlOffsetCond();
        sameCtrlOffsetCond.setTaskId(condition.getTaskId());
        sameCtrlOffsetCond.setSchemeId(condition.getSchemeId());
        ConsolidatedTaskService consolidatedTaskService = (ConsolidatedTaskService)SpringBeanUtils.getBean(ConsolidatedTaskService.class);
        ConsolidatedTaskVO consolidatedTaskVO = consolidatedTaskService.getTaskByTaskKeyAndPeriodStr(condition.getTaskId(), condition.getPeriodStr());
        if (consolidatedTaskVO == null) {
            throw new BusinessRuntimeException("\u4efb\u52a1\u3010" + condition.getTaskId() + "\u3011\u65f6\u671f\u3010" + condition.getPeriodStr() + "\u3011\u7684\u4f53\u7cfb\u4e0d\u5b58\u5728");
        }
        sameCtrlOffsetCond.setSystemId(consolidatedTaskVO.getSystemId());
        sameCtrlOffsetCond.setPeriodStr(condition.getPeriodStr());
        sameCtrlOffsetCond.setOrgType(condition.getOrgType());
        sameCtrlOffsetCond.setSameCtrlChgId(sameCtrlChgOrg.getId());
        sameCtrlOffsetCond.setChangedUnitCode(sameCtrlChgOrg.getChangedCode());
        sameCtrlOffsetCond.setMergeUnitCode(sameCtrlChgOrg.getVirtualParentCode());
        sameCtrlOffsetCond.setSameParentCode(sameCtrlChgOrg.getSameParentCode());
        sameCtrlOffsetCond.setCurrencyCode(condition.getCurrencyId());
        sameCtrlOffsetCond.setExtractAllParentsUnitFlag(true);
        sameCtrlChgEnvContext.setSameCtrlOffsetCond(sameCtrlOffsetCond);
        sameCtrlChgEnvContext.setResult(CollectionUtils.newArrayList());
    }

    @Override
    public void extractReportData(SameCtrlChgEnvContextImpl sameCtrlChgEnvContext) {
        ReportExtractProcessor reportExtractProcessor = ReportExtractProcessor.newInstance(sameCtrlChgEnvContext);
        reportExtractProcessor.extractReportData();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveExtractReportData(SameCtrlExtractTableZbSetting sameCtrlExtractTableZbSetting, List<List<Map<String, Object>>> extractDataList, SameCtrlExtractReportCond sameCtrlExtractReportCond) {
        if (sameCtrlExtractTableZbSetting.getUsingInFloatRegion()) {
            this.saveFloatReportData(sameCtrlExtractTableZbSetting, extractDataList, sameCtrlExtractReportCond);
        } else {
            this.saveFixReportData(sameCtrlExtractTableZbSetting, extractDataList, sameCtrlExtractReportCond);
        }
    }

    private void saveFloatReportData(SameCtrlExtractTableZbSetting sameCtrlExtractTableZbSetting, List<List<Map<String, Object>>> extractDataList, SameCtrlExtractReportCond sameCtrlExtractReportCond) {
        List<Map<String, Object>> floatZbDataList = this.listFloatZbData(extractDataList);
        this.deleteVirtualCodeSql(sameCtrlExtractReportCond, sameCtrlExtractTableZbSetting.getTableName());
        for (Map<String, Object> mapData : floatZbDataList) {
            List<Object> zbDatas = this.getZbDataList(mapData, sameCtrlExtractTableZbSetting.getFieldNames(), sameCtrlExtractReportCond);
            this.insertVirtualCodeSql(zbDatas, sameCtrlExtractTableZbSetting.getTableName(), sameCtrlExtractTableZbSetting.getFieldNames(), sameCtrlExtractReportCond);
        }
    }

    private void saveFixReportData(SameCtrlExtractTableZbSetting sameCtrlExtractTableZbSetting, List<List<Map<String, Object>>> extractDataList, SameCtrlExtractReportCond sameCtrlExtractReportCond) {
        int count = this.getVirtualCodeCount(sameCtrlExtractReportCond, sameCtrlExtractTableZbSetting.getTableName());
        if (count == 0) {
            Map<String, Object> extractDataMap = this.getFixZbDataMap(extractDataList);
            if (extractDataMap.isEmpty()) {
                return;
            }
            List<Object> zbDatas = this.getZbDataList(extractDataMap, sameCtrlExtractTableZbSetting.getFieldNames(), sameCtrlExtractReportCond);
            this.insertVirtualCodeSql(zbDatas, sameCtrlExtractTableZbSetting.getTableName(), sameCtrlExtractTableZbSetting.getFieldNames(), sameCtrlExtractReportCond);
        } else {
            this.updateVirtualCodeSql(extractDataList, sameCtrlExtractTableZbSetting.getFieldNames(), sameCtrlExtractReportCond, sameCtrlExtractTableZbSetting.getTableName());
        }
    }

    private List<Map<String, Object>> listFloatZbData(List<List<Map<String, Object>>> extractDataList) {
        ArrayList<Map<String, Object>> floatZbDataList = new ArrayList<Map<String, Object>>(16);
        for (List<Map<String, Object>> dataList : extractDataList) {
            if (org.springframework.util.CollectionUtils.isEmpty(dataList)) continue;
            floatZbDataList.addAll(dataList);
        }
        return floatZbDataList;
    }

    private Map<String, Object> getFixZbDataMap(List<List<Map<String, Object>>> extractDataList) {
        HashMap<String, Object> extractDataMap = new HashMap<String, Object>(16);
        for (List<Map<String, Object>> dataList : extractDataList) {
            if (org.springframework.util.CollectionUtils.isEmpty(dataList)) continue;
            for (Map<String, Object> dataMap : dataList) {
                extractDataMap.putAll(dataMap);
            }
        }
        return extractDataMap;
    }

    private List<Object> getZbDataList(Map<String, Object> extractDataMap, List<String> zbCodes, SameCtrlExtractReportCond sameCtrlExtractReportCond) {
        ArrayList<Object> zbDatas = new ArrayList<Object>();
        GcOrgCacheVO gcOrgCacheVO = sameCtrlExtractReportCond.getGcOrgCenterService().getOrgByCode(sameCtrlExtractReportCond.getChangedCode());
        String changedOrgType = gcOrgCacheVO != null && !StringUtils.isEmpty((String)gcOrgCacheVO.getOrgTypeId()) ? gcOrgCacheVO.getOrgTypeId() : sameCtrlExtractReportCond.getOrgType();
        for (String code : zbCodes) {
            if ("MDCODE".equals(code)) {
                zbDatas.add(sameCtrlExtractReportCond.getVirtualCode());
                continue;
            }
            if ("MD_GCORGTYPE".equals(code)) {
                zbDatas.add(changedOrgType);
                continue;
            }
            if ("DATATIME".equals(code)) {
                zbDatas.add(sameCtrlExtractReportCond.getPeriodStr());
                continue;
            }
            if ("BIZKEYORDER".equals(code)) {
                zbDatas.add(UUIDUtils.newUUIDStr());
                continue;
            }
            if ("MD_GCADJTYPE".equals(code)) {
                zbDatas.add(GCAdjTypeEnum.BEFOREADJ.getCode());
                continue;
            }
            if ("ADJUST".equals(code)) {
                zbDatas.add(sameCtrlExtractReportCond.getSelectAdjustCode());
                continue;
            }
            zbDatas.add(extractDataMap.get(code));
        }
        return zbDatas;
    }

    private void deleteVirtualCodeSql(SameCtrlExtractReportCond sameCtrlExtractReportCond, String tableName) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" DELETE  FROM ").append(tableName).append("  WHERE ");
        StringBuilder whereSql = this.getDimensionWhereSql(sameCtrlExtractReportCond);
        if (whereSql != null && whereSql.length() <= 0) {
            return;
        }
        EntNativeSqlDefaultDao.getInstance().execute(sqlBuilder.append((CharSequence)whereSql).toString(), Arrays.asList(sameCtrlExtractReportCond.getVirtualCode()));
    }

    private void insertVirtualCodeSql(List<Object> zbDataList, String tableName, List<String> zbFieldNames, SameCtrlExtractReportCond sameCtrlExtractReportCond) {
        if (org.springframework.util.CollectionUtils.isEmpty(zbDataList) || org.springframework.util.CollectionUtils.isEmpty(zbFieldNames)) {
            return;
        }
        String selectFields = this.getSelectFields(zbFieldNames);
        String zbValues = this.getZbValueStr(zbDataList, sameCtrlExtractReportCond);
        String sql = " INSERT INTO " + tableName + " (" + selectFields + ")  VALUES (" + zbValues + ")";
        EntNativeSqlDefaultDao.getInstance().execute(sql);
    }

    private void updateVirtualCodeSql(List<List<Map<String, Object>>> extractDatas, List<String> zbFieldNames, SameCtrlExtractReportCond sameCtrlExtractReportCond, String tableName) {
        Map<String, Object> zbDataMap = this.getFixZbDataMap(extractDatas);
        List<String> dimensionNames = this.listDimensionName(sameCtrlExtractReportCond.getTaskId());
        StringBuilder sqlBuilder = new StringBuilder(10);
        sqlBuilder.append(" UPDATE ").append(tableName).append("  set ");
        for (String fieldName : zbFieldNames) {
            if (dimensionNames.contains(fieldName)) continue;
            Object zbValue = zbDataMap.get(fieldName);
            if (zbValue instanceof Number) {
                sqlBuilder.append(fieldName).append("=");
                if (sameCtrlExtractReportCond.isGoBack()) {
                    if (zbValue instanceof Integer) {
                        int intValue = (Integer)zbValue;
                        intValue = -intValue;
                        sqlBuilder.append(intValue);
                    } else if (zbValue instanceof Double) {
                        double doubleValue = (Double)zbValue;
                        doubleValue = -doubleValue;
                        sqlBuilder.append(doubleValue);
                    } else {
                        sqlBuilder.append(zbValue);
                    }
                } else {
                    sqlBuilder.append(zbValue);
                }
                sqlBuilder.append(",");
                continue;
            }
            if (zbValue != null) {
                sqlBuilder.append(fieldName).append("='").append(zbValue).append("',");
                continue;
            }
            sqlBuilder.append(fieldName).append("=").append((Object)null).append(",");
        }
        sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
        sqlBuilder.append(" WHERE ");
        StringBuilder whereSql = this.getDimensionWhereSql(sameCtrlExtractReportCond);
        if (whereSql != null && whereSql.length() <= 0) {
            return;
        }
        EntNativeSqlDefaultDao.getInstance().execute(sqlBuilder.append((CharSequence)whereSql).toString(), new Object[]{sameCtrlExtractReportCond.getVirtualCode()});
    }

    private int getVirtualCodeCount(SameCtrlExtractReportCond sameCtrlExtractReportCond, String tableName) {
        StringBuilder sqlBuilder = new StringBuilder(10);
        sqlBuilder.append(" SELECT count(1)").append(" FROM ").append(tableName).append("  WHERE ");
        StringBuilder whereSql = this.getDimensionWhereSql(sameCtrlExtractReportCond);
        if (whereSql != null && whereSql.length() <= 0) {
            return 0;
        }
        return EntNativeSqlDefaultDao.getInstance().count(sqlBuilder.append((CharSequence)whereSql).toString(), Collections.singletonList(sameCtrlExtractReportCond.getVirtualCode()));
    }

    private StringBuilder getDimensionWhereSql(SameCtrlExtractReportCond sameCtrlExtractReportCond) {
        List<String> dimensionNames = this.listDimensionName(sameCtrlExtractReportCond.getTaskId());
        if (dimensionNames.isEmpty()) {
            return null;
        }
        StringBuilder sqlBuilder = new StringBuilder(10);
        GcOrgCacheVO gcOrgCacheVO = sameCtrlExtractReportCond.getGcOrgCenterService().getOrgByCode(sameCtrlExtractReportCond.getChangedCode());
        String changedOrgType = gcOrgCacheVO != null && !StringUtils.isEmpty((String)gcOrgCacheVO.getOrgTypeId()) ? gcOrgCacheVO.getOrgTypeId() : sameCtrlExtractReportCond.getOrgType();
        for (String dimensionName : dimensionNames) {
            if ("MDCODE".equals(dimensionName)) {
                sqlBuilder.append(" ").append(dimensionName).append("=?");
                continue;
            }
            if ("MD_CURRENCY".equals(dimensionName)) {
                sqlBuilder.append(" AND ").append(dimensionName).append("='").append(sameCtrlExtractReportCond.getCurrencyId()).append("' ");
                continue;
            }
            if ("MD_GCORGTYPE".equals(dimensionName)) {
                sqlBuilder.append(" AND ").append(dimensionName).append(" in ('").append(changedOrgType).append("','").append(GCOrgTypeEnum.NONE.getCode()).append("')");
                continue;
            }
            if ("DATATIME".equals(dimensionName)) {
                sqlBuilder.append(" AND ").append(dimensionName).append("='").append(sameCtrlExtractReportCond.getPeriodStr()).append("'");
                continue;
            }
            if (!"ADJUST".equals(dimensionName)) continue;
            sqlBuilder.append(" AND ").append("ADJUST").append("='").append(sameCtrlExtractReportCond.getSelectAdjustCode()).append("'");
        }
        return sqlBuilder;
    }

    private String getSelectFields(List<String> zbFieldNames) {
        StringBuffer zbFieldNameStr = new StringBuffer();
        zbFieldNames.forEach(fieldName -> zbFieldNameStr.append((String)fieldName).append(","));
        return zbFieldNameStr.deleteCharAt(zbFieldNameStr.length() - 1).toString();
    }

    private String getZbValueStr(List<Object> zbDataList, SameCtrlExtractReportCond sameCtrlExtractReportCond) {
        StringBuilder zbFieldNameStr = new StringBuilder();
        for (Object value : zbDataList) {
            if (value instanceof Number) {
                if (sameCtrlExtractReportCond.isGoBack()) {
                    if (value instanceof Integer) {
                        int intValue = (Integer)value;
                        intValue = -intValue;
                        zbFieldNameStr.append(intValue).append(",");
                        continue;
                    }
                    if (value instanceof Double) {
                        double doubleValue = (Double)value;
                        doubleValue = -doubleValue;
                        zbFieldNameStr.append(doubleValue).append(",");
                        continue;
                    }
                    System.out.println("Unsupported number type");
                    zbFieldNameStr.append(value).append(",");
                    continue;
                }
                zbFieldNameStr.append(value).append(",");
                continue;
            }
            if (value == null) {
                zbFieldNameStr.append((Object)null).append(",");
                continue;
            }
            zbFieldNameStr.append("'").append(value).append("',");
        }
        return zbFieldNameStr.deleteCharAt(zbFieldNameStr.length() - 1).toString();
    }

    private List<String> listDimensionName(String taskId) {
        ArrayList<String> dimensionNames = new ArrayList<String>();
        dimensionNames.add("MDCODE");
        dimensionNames.add("MD_GCORGTYPE");
        dimensionNames.add("DATATIME");
        dimensionNames.add("MD_CURRENCY");
        if (DimensionUtils.isExisAdjType((String)taskId)) {
            dimensionNames.add("MD_GCADJTYPE");
        }
        if (DimensionUtils.isExistAdjust((String)taskId)) {
            dimensionNames.add("ADJUST");
        }
        return dimensionNames;
    }
}

