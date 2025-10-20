/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryCondition
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperRowData
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperSettingVo$SettingData
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperSettingVo$SettingData$ZbSetting
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.invest.investworkpaper.service.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.invest.investworkpaper.dao.InvestWorkPaperFormDataQueryDao;
import com.jiuqi.gcreport.invest.investworkpaper.enums.DataSourceEnum;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryCondition;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperRowData;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperSettingVo;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InvestWorkPaperFormDataQueryTask {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private InvestWorkPaperFormDataQueryDao investWorkPaperFormDataQueryDao;

    public List<InvestWorkPaperRowData> assembleFomData(List<Map<String, Object>> investBills, InvestWorkPaperSettingVo.SettingData settingData, InvestWorkPaperQueryCondition condition, List<GcOrgCacheVO> orgCacheVOs) {
        List zbSettingList = settingData.getZbSetting();
        if (zbSettingList == null) {
            return new ArrayList<InvestWorkPaperRowData>();
        }
        HashMap invested2TableColumnsMap = new HashMap();
        investBills.forEach(investBill -> {
            String investedUnit = (String)investBill.get("INVESTEDUNIT_ID");
            List tableColumns = invested2TableColumnsMap.getOrDefault(investedUnit, new ArrayList());
            tableColumns.add(investBill.get("INVESTEDUNIT_ID") + "_" + investBill.get("UNITCODE_ID"));
            invested2TableColumnsMap.put(investedUnit, tableColumns);
        });
        Map<String, GcOrgCacheVO> orgCode2DataMap = orgCacheVOs.stream().collect(Collectors.toMap(orgCacheVO -> orgCacheVO.getCode(), orgCacheVO -> orgCacheVO));
        Map<String, List<String>> zbTableName2FieldNamesMap = this.getZbTableName2FieldNamesMap(zbSettingList);
        HashMap<String, String> mergeUnit2InvestedUnitCodeMap = new HashMap<String, String>();
        Map<String, List<String>> orgTypeId2MdCodeList = this.getOrgTypeId2MdCodeList(condition, invested2TableColumnsMap.keySet(), orgCode2DataMap, mergeUnit2InvestedUnitCodeMap);
        Map<String, Map<String, String>> orgCode2ZbFieldNameAndZbValueMap = this.investWorkPaperFormDataQueryDao.getNrData(condition, orgTypeId2MdCodeList, mergeUnit2InvestedUnitCodeMap, zbTableName2FieldNamesMap);
        ArrayList<InvestWorkPaperRowData> tableRowDatas = new ArrayList<InvestWorkPaperRowData>();
        Map zbCode2ZbSetting = zbSettingList.stream().collect(Collectors.toMap(item -> item.getZbCode(), item -> item, (v1, v2) -> v1, LinkedHashMap::new));
        zbCode2ZbSetting.forEach((zbCode, zbSetting) -> {
            InvestWorkPaperRowData rowData = new InvestWorkPaperRowData();
            rowData.setDataSource(DataSourceEnum.FORMDATA.getCode());
            rowData.setDataSourceTitle(DataSourceEnum.FORMDATA.getTitle());
            rowData.setZbCode(zbCode);
            rowData.setZbTable(zbSetting.getZbTable());
            rowData.setZbTitle(zbSetting.getZbProject());
            for (Map.Entry entry : invested2TableColumnsMap.entrySet()) {
                String invested = (String)entry.getKey();
                List tableColumns = (List)entry.getValue();
                Map zbCode2ZbValueMap = (Map)orgCode2ZbFieldNameAndZbValueMap.get(invested);
                if (zbCode2ZbValueMap == null || zbCode2ZbValueMap.isEmpty()) continue;
                tableColumns.forEach(tableColumn -> rowData.addDynamicField(tableColumn, zbCode2ZbValueMap.get(zbCode)));
            }
            tableRowDatas.add(rowData);
        });
        return tableRowDatas;
    }

    private Map<String, List<String>> getOrgTypeId2MdCodeList(InvestWorkPaperQueryCondition condition, Set<String> investedUnitCodeSet, Map<String, GcOrgCacheVO> orgCode2DataMap, Map<String, String> mergeUnit2InvestedUnitCodeMap) {
        HashMap<String, List<String>> orgTypeId2MdCodeList = new HashMap<String, List<String>>();
        investedUnitCodeSet.forEach(investedUnitCode -> {
            GcOrgCacheVO investedUnitVO = (GcOrgCacheVO)orgCode2DataMap.get(investedUnitCode);
            if (investedUnitVO != null && GcOrgKindEnum.BASE.equals((Object)investedUnitVO.getOrgKind())) {
                mergeUnit2InvestedUnitCodeMap.put(investedUnitVO.getMergeUnitId(), (String)investedUnitCode);
                investedUnitCode = investedUnitVO.getMergeUnitId();
                investedUnitVO = (GcOrgCacheVO)orgCode2DataMap.get(investedUnitVO.getMergeUnitId());
            }
            String orgTypeId = investedUnitVO == null || StringUtils.isEmpty((String)investedUnitVO.getOrgTypeId()) ? condition.getOrgType() : investedUnitVO.getOrgTypeId();
            orgTypeId2MdCodeList.computeIfAbsent(orgTypeId, k -> new ArrayList()).add(investedUnitCode);
        });
        return orgTypeId2MdCodeList;
    }

    private Map<String, List<String>> getZbTableName2FieldNamesMap(List<InvestWorkPaperSettingVo.SettingData.ZbSetting> zbSettings) {
        HashMap<String, List<String>> zbTableName2FieldNames = new HashMap<String, List<String>>();
        if (zbSettings == null) {
            return null;
        }
        zbSettings.stream().forEach(zbSetting -> {
            if (StringUtils.isEmpty((String)zbSetting.getZbTable()) || StringUtils.isEmpty((String)zbSetting.getZbCode())) {
                return;
            }
            List fields = zbTableName2FieldNames.computeIfAbsent(zbSetting.getZbTable(), k -> new ArrayList());
            fields.add(zbSetting.getZbCode());
        });
        return zbTableName2FieldNames;
    }
}

