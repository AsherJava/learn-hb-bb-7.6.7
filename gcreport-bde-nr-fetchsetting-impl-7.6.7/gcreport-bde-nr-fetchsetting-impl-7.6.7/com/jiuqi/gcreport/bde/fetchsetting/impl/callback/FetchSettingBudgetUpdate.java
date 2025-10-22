/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.callback;

import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class FetchSettingBudgetUpdate
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(FetchSettingBudgetUpdate.class);
    private static final String FILED_STRING = " id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType,fetchSourceCode,bizModelCode,optimizeRuleGroup,sign,fetchType,subjectCode,excludeSubjectCode,dimType,sumType,reclassSubjCode,reclassSrcSubjCode,agingRangeType,agingRangeStart,agingRangeEnd,cashCode,dimensionSetting,formula,currencyCode,acctYear,acctPeriod,orgCode,memo,sortOrder,dataSourceCode ";
    private static final String DELETE_FILED_STRING = "fetchSourceCode,bizModelCode,optimizeRuleGroup,sign,fetchType,subjectCode,excludeSubjectCode,dimType,sumType,reclassSubjCode,reclassSrcSubjCode,agingRangeType,agingRangeStart,agingRangeEnd,cashCode,dimensionSetting,formula,currencyCode,acctYear,acctPeriod,orgCode,memo,dataSourceCode";
    private static final String BACKUP_TABLE_SUFFIX = "_backups";

    @Transactional(rollbackFor={Exception.class})
    public void execute(DataSource dataSource) throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        logger.info("\u5f00\u59cb\u5907\u4efdBDE\u53d6\u6570\u8bbe\u7f6e\u8bbe\u8ba1\u671f\u6570\u636e");
        this.backupTable("BDE_FETCHSETTING_DES", jdbcTemplate);
        logger.info("\u8bbe\u8ba1\u671f\u6570\u636e\u5907\u4efd\u5b8c\u6210\uff0c\u5907\u4efd\u8868\u4e3aBDE_FETCHSETTING_DES_backups");
        logger.info("\u5f00\u59cb\u4fee\u590dBDE\u53d6\u6570\u8bbe\u7f6e\u8bbe\u8ba1\u671f\u6570\u636e");
        this.updateByTableName("BDE_FETCHSETTING_DES", jdbcTemplate);
        logger.info("\u8bbe\u8ba1\u671f\u6570\u636e\u4fee\u590d\u5b8c\u6210");
        logger.info("\u5f00\u59cb\u5220\u9664BDE\u53d6\u6570\u8bbe\u7f6e\u8bbe\u8ba1\u671f\u5e9f\u5f03\u5b57\u6bb5");
        this.deleteTableField("BDE_FETCHSETTING_DES", jdbcTemplate);
        logger.info("\u8bbe\u8ba1\u671f\u5e9f\u5f03\u5b57\u6bb5\u5220\u9664\u5b8c\u6210");
        logger.info("\u5f00\u59cb\u5220\u9664\u8bbe\u8ba1\u671f\u5197\u4f59\u6570\u636e");
        this.deleteRedundantRecords("BDE_FETCHSETTING_DES", jdbcTemplate);
        logger.info("\u8bbe\u8ba1\u671f\u5197\u4f59\u6570\u636e\u5220\u9664\u5b8c\u6210");
        logger.info("\u5f00\u59cb\u5907\u4efdBDE\u53d6\u6570\u8bbe\u7f6e\u8fd0\u884c\u671f\u6570\u636e");
        this.backupTable("BDE_FETCHSETTING", jdbcTemplate);
        logger.info("\u5f00\u59cb\u4fee\u590d\u8fd0\u884c\u671f\u6570\u636e");
        this.updateByTableName("BDE_FETCHSETTING", jdbcTemplate);
        logger.info("\u8fd0\u884c\u671f\u6570\u636e\u4fee\u590d\u5b8c\u6210");
        logger.info("\u5f00\u59cb\u5220\u9664BDE\u53d6\u6570\u8bbe\u7f6e\u8fd0\u884c\u671f\u5e9f\u5f03\u5b57\u6bb5");
        this.deleteTableField("BDE_FETCHSETTING", jdbcTemplate);
        logger.info("\u8fd0\u884c\u671f\u5e9f\u5f03\u5b57\u6bb5\u5220\u9664\u5b8c\u6210");
        logger.info("\u5f00\u59cb\u5220\u9664\u8fd0\u884c\u671f\u5197\u4f59\u6570\u636e");
        this.deleteRedundantRecords("BDE_FETCHSETTING", jdbcTemplate);
        logger.info("\u8fd0\u884c\u671f\u5197\u4f59\u6570\u636e\u5220\u9664\u5b8c\u6210");
    }

    private void updateByTableName(String tableName, JdbcTemplate jdbcTemplate) {
        String querSql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType,fetchSourceCode,bizModelCode,optimizeRuleGroup,sign,fetchType,subjectCode,excludeSubjectCode,dimType,sumType,reclassSubjCode,reclassSrcSubjCode,agingRangeType,agingRangeStart,agingRangeEnd,cashCode,dimensionSetting,formula,currencyCode,acctYear,acctPeriod,orgCode,memo,sortOrder,dataSourceCode  \n FROM " + tableName + "  fs  WHERE FIXEDSETTINGDATA IS NULL ORDER BY FIELDDEFINEID,SORTORDER\n";
        List oldSettingList = jdbcTemplate.query(querSql, (rs, row) -> this.getOldFetchSettingEO(rs));
        Map fetchSettingGroupByFieldDefineId = oldSettingList.stream().collect(Collectors.groupingBy(OldFetchSettingDesEO::getFieldDefineId, LinkedHashMap::new, Collectors.toList()));
        HashMap fixedSettingDataMap = new HashMap();
        for (List oldSettingGroup : fetchSettingGroupByFieldDefineId.values()) {
            ArrayList<FixedAdaptSettingVO> fixedAdaptSettingVOList = new ArrayList<FixedAdaptSettingVO>();
            FixedAdaptSettingVO adaptSettingVO = new FixedAdaptSettingVO();
            String id = ((OldFetchSettingDesEO)oldSettingGroup.get(0)).getId();
            StringBuilder memo = new StringBuilder();
            LinkedHashMap fetchSourceSettingGroup = oldSettingGroup.stream().collect(Collectors.groupingBy(OldFetchSettingDesEO::getFetchSourceCode, LinkedHashMap::new, Collectors.toList()));
            HashMap bizModelFormula = new HashMap();
            if (fetchSourceSettingGroup.containsKey("FORMULA")) {
                OldFetchSettingDesEO logicFormulaOldSetting = (OldFetchSettingDesEO)((List)fetchSourceSettingGroup.get("FORMULA")).get(0);
                adaptSettingVO.setLogicFormula(logicFormulaOldSetting.getFormula());
                adaptSettingVO.setMemo(logicFormulaOldSetting.getMemo());
            }
            for (Map.Entry fetchSourceSettingEntry : fetchSourceSettingGroup.entrySet()) {
                String fetchSourceCode = (String)fetchSourceSettingEntry.getKey();
                if ("FORMULA".equals(fetchSourceCode)) continue;
                List oldFetchSourceSettingList = (List)fetchSourceSettingEntry.getValue();
                ArrayList<FixedFetchSourceRowSettingVO> fetchSourceRowSettingVOList = new ArrayList<FixedFetchSourceRowSettingVO>();
                for (OldFetchSettingDesEO oldFetchSettingDesEO : oldFetchSourceSettingList) {
                    fetchSourceRowSettingVOList.add(this.copyProperties(oldFetchSettingDesEO));
                    memo.append(oldFetchSettingDesEO.getMemo());
                }
                bizModelFormula.put(fetchSourceCode, fetchSourceRowSettingVOList);
            }
            if (StringUtils.isEmpty((String)adaptSettingVO.getMemo())) {
                adaptSettingVO.setMemo(memo.toString());
            }
            adaptSettingVO.setBizModelFormula(bizModelFormula);
            fixedAdaptSettingVOList.add(adaptSettingVO);
            fixedSettingDataMap.put(id, fixedAdaptSettingVOList);
        }
        Map<String, String> fixedSettingDataStrMap = fixedSettingDataMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, fixedAdaptSettings -> JsonUtils.writeValueAsString(fixedAdaptSettings.getValue())));
        String updateSql = " UPDATE " + tableName + " SET FIXEDSETTINGDATA = ? WHERE ID = ?";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (String id : fixedSettingDataStrMap.keySet()) {
            argsList.add(new Object[]{fixedSettingDataStrMap.get(id), id});
        }
        jdbcTemplate.batchUpdate(updateSql, argsList);
    }

    private void backupTable(String tableName, JdbcTemplate jdbcTemplate) {
        String backupSql = String.format("create table %1$s as select * from %2$s", tableName + BACKUP_TABLE_SUFFIX, tableName);
        jdbcTemplate.update(backupSql);
    }

    private void deleteTableField(String tableName, JdbcTemplate jdbcTemplate) {
        String[] dropFieldArray;
        for (String dropField : dropFieldArray = DELETE_FILED_STRING.split(",")) {
            String deleteFieldSql = String.format("ALTER TABLE %1$s  DROP  COLUMN %2$S", tableName, dropField);
            jdbcTemplate.update(deleteFieldSql);
        }
    }

    private void deleteRedundantRecords(String tableName, JdbcTemplate jdbcTemplate) {
        String deleteRedundantRecordsSql = String.format("delete from %1$s where FIXEDSETTINGDATA is null", tableName);
        jdbcTemplate.update(deleteRedundantRecordsSql);
    }

    private FixedFetchSourceRowSettingVO copyProperties(OldFetchSettingDesEO oldFetchSettingDesEO) {
        FixedFetchSourceRowSettingVO fetchSourceRowSettingVO = new FixedFetchSourceRowSettingVO();
        fetchSourceRowSettingVO.setId(oldFetchSettingDesEO.getId());
        fetchSourceRowSettingVO.setBizModelCode(oldFetchSettingDesEO.getBizModelCode());
        fetchSourceRowSettingVO.setOptimizeRuleGroup(oldFetchSettingDesEO.getOptimizeRuleGroup());
        fetchSourceRowSettingVO.setSign(oldFetchSettingDesEO.getSign());
        fetchSourceRowSettingVO.setFetchType(oldFetchSettingDesEO.getFetchType());
        fetchSourceRowSettingVO.setSubjectCode(oldFetchSettingDesEO.getSubjectCode());
        fetchSourceRowSettingVO.setExcludeSubjectCode(oldFetchSettingDesEO.getExcludeSubjectCode());
        fetchSourceRowSettingVO.setDimType(oldFetchSettingDesEO.getDimType());
        fetchSourceRowSettingVO.setSumType(oldFetchSettingDesEO.getSumType());
        fetchSourceRowSettingVO.setReclassSubjCode(oldFetchSettingDesEO.getReclassSubjCode());
        fetchSourceRowSettingVO.setReclassSrcSubjCode(oldFetchSettingDesEO.getReclassSrcSubjCode());
        fetchSourceRowSettingVO.setAgingRangeType(oldFetchSettingDesEO.getAgingRangeType());
        fetchSourceRowSettingVO.setAgingRangeStart(oldFetchSettingDesEO.getAgingRangeStart());
        fetchSourceRowSettingVO.setAgingRangeEnd(oldFetchSettingDesEO.getAgingRangeEnd());
        fetchSourceRowSettingVO.setCashCode(oldFetchSettingDesEO.getCashCode());
        fetchSourceRowSettingVO.setDimensionSetting(oldFetchSettingDesEO.getDimensionSetting());
        fetchSourceRowSettingVO.setCurrencyCode(oldFetchSettingDesEO.getCurrencyCode());
        fetchSourceRowSettingVO.setAcctYear(oldFetchSettingDesEO.getAcctYear());
        fetchSourceRowSettingVO.setAcctPeriod(oldFetchSettingDesEO.getAcctPeriod());
        fetchSourceRowSettingVO.setOrgCode(oldFetchSettingDesEO.getOrgCode());
        return fetchSourceRowSettingVO;
    }

    private OldFetchSettingDesEO getOldFetchSettingEO(ResultSet rs) throws SQLException {
        OldFetchSettingDesEO eo = new OldFetchSettingDesEO();
        eo.setId(rs.getString(1));
        eo.setFetchSchemeId(rs.getString(2));
        eo.setFormSchemeId(rs.getString(3));
        eo.setFormId(rs.getString(4));
        eo.setRegionId(rs.getString(5));
        eo.setDataLinkId(rs.getString(6));
        eo.setFieldDefineId(rs.getString(7));
        eo.setRegionType(rs.getString(8));
        eo.setFetchSourceCode(rs.getString(9));
        eo.setBizModelCode(rs.getString(10));
        eo.setOptimizeRuleGroup(rs.getString(11));
        eo.setSign(rs.getString(12));
        eo.setFetchType(rs.getString(13));
        eo.setSubjectCode(rs.getString(14));
        eo.setExcludeSubjectCode(rs.getString(15));
        eo.setDimType(rs.getString(16));
        eo.setSumType(rs.getString(17));
        eo.setReclassSubjCode(rs.getString(18));
        eo.setReclassSrcSubjCode(rs.getString(19));
        eo.setAgingRangeType(rs.getString(20));
        eo.setAgingRangeStart(rs.getObject(21) == null ? null : Integer.valueOf(rs.getInt(21)));
        eo.setAgingRangeEnd(rs.getObject(22) == null ? null : Integer.valueOf(rs.getInt(22)));
        eo.setCashCode(rs.getString(23));
        eo.setDimensionSetting(rs.getString(24));
        eo.setFormula(rs.getString(25));
        eo.setCurrencyCode(rs.getString(26));
        eo.setAcctYear(rs.getString(27));
        eo.setAcctPeriod(rs.getString(28));
        eo.setOrgCode(rs.getString(29));
        eo.setMemo(rs.getString(30));
        eo.setSortOrder(rs.getInt(31));
        eo.setDataSourceCode(rs.getString(32));
        return eo;
    }

    class OldFetchSettingDesEO {
        private String id;
        private String fetchSchemeId;
        private String formSchemeId;
        private String formId;
        private String regionId;
        private String dataLinkId;
        private String fieldDefineId;
        private String regionType;
        private String fetchSourceCode;
        private String bizModelCode;
        private String optimizeRuleGroup;
        private String sign;
        private String fetchType;
        private String subjectCode;
        private String excludeSubjectCode;
        private String dimType;
        private String sumType;
        private String reclassSubjCode;
        private String reclassSrcSubjCode;
        private String agingRangeType;
        private Integer agingRangeStart;
        private Integer agingRangeEnd;
        private String cashCode;
        private String dimensionSetting;
        private String formula;
        private String currencyCode;
        private String acctYear;
        private String acctPeriod;
        private String orgCode;
        private String memo;
        private List<String> dimComb;
        private Integer sortOrder;
        private String dataSourceCode;

        OldFetchSettingDesEO() {
        }

        String getDataSourceCode() {
            return this.dataSourceCode;
        }

        void setDataSourceCode(String dataSourceCode) {
            this.dataSourceCode = dataSourceCode;
        }

        String getFetchSchemeId() {
            return this.fetchSchemeId;
        }

        void setFetchSchemeId(String fetchSchemeId) {
            this.fetchSchemeId = fetchSchemeId;
        }

        String getFormSchemeId() {
            return this.formSchemeId;
        }

        void setFormSchemeId(String formSchemeId) {
            this.formSchemeId = formSchemeId;
        }

        String getFormId() {
            return this.formId;
        }

        void setFormId(String formId) {
            this.formId = formId;
        }

        String getRegionId() {
            return this.regionId;
        }

        void setRegionId(String regionId) {
            this.regionId = regionId;
        }

        String getDataLinkId() {
            return this.dataLinkId;
        }

        void setDataLinkId(String dataLinkId) {
            this.dataLinkId = dataLinkId;
        }

        String getFieldDefineId() {
            return this.fieldDefineId;
        }

        void setFieldDefineId(String fieldDefineId) {
            this.fieldDefineId = fieldDefineId;
        }

        String getRegionType() {
            return this.regionType;
        }

        void setRegionType(String regionType) {
            this.regionType = regionType;
        }

        String getFetchSourceCode() {
            return this.fetchSourceCode;
        }

        void setFetchSourceCode(String fetchSourceCode) {
            this.fetchSourceCode = fetchSourceCode;
        }

        String getBizModelCode() {
            return this.bizModelCode;
        }

        void setBizModelCode(String bizModelCode) {
            this.bizModelCode = bizModelCode;
        }

        String getOptimizeRuleGroup() {
            return this.optimizeRuleGroup;
        }

        void setOptimizeRuleGroup(String optimizeRuleGroup) {
            this.optimizeRuleGroup = optimizeRuleGroup;
        }

        String getSign() {
            return this.sign;
        }

        void setSign(String sign) {
            this.sign = sign;
        }

        String getFetchType() {
            return this.fetchType;
        }

        void setFetchType(String fetchType) {
            this.fetchType = fetchType;
        }

        String getSubjectCode() {
            return this.subjectCode;
        }

        void setSubjectCode(String subjectCode) {
            this.subjectCode = subjectCode;
        }

        String getExcludeSubjectCode() {
            return this.excludeSubjectCode;
        }

        void setExcludeSubjectCode(String excludeSubjectCode) {
            this.excludeSubjectCode = excludeSubjectCode;
        }

        void setDimType(String dimType) {
            this.dimType = dimType;
        }

        String getDimType() {
            return this.dimType;
        }

        String getSumType() {
            return this.sumType;
        }

        void setSumType(String sumType) {
            this.sumType = sumType;
        }

        String getReclassSubjCode() {
            return this.reclassSubjCode;
        }

        void setReclassSubjCode(String reclassSubjCode) {
            this.reclassSubjCode = reclassSubjCode;
        }

        String getReclassSrcSubjCode() {
            return this.reclassSrcSubjCode;
        }

        void setReclassSrcSubjCode(String reclassSrcSubjCode) {
            this.reclassSrcSubjCode = reclassSrcSubjCode;
        }

        String getAgingRangeType() {
            return this.agingRangeType;
        }

        void setAgingRangeType(String agingRangeType) {
            this.agingRangeType = agingRangeType;
        }

        Integer getAgingRangeStart() {
            return this.agingRangeStart;
        }

        void setAgingRangeStart(Integer agingRangeStart) {
            this.agingRangeStart = agingRangeStart;
        }

        Integer getAgingRangeEnd() {
            return this.agingRangeEnd;
        }

        void setAgingRangeEnd(Integer agingRangeEnd) {
            this.agingRangeEnd = agingRangeEnd;
        }

        String getCashCode() {
            return this.cashCode;
        }

        void setCashCode(String cashCode) {
            this.cashCode = cashCode;
        }

        String getDimensionSetting() {
            return this.dimensionSetting;
        }

        void setDimensionSetting(String dimensionSetting) {
            this.dimensionSetting = dimensionSetting;
        }

        String getFormula() {
            return this.formula;
        }

        void setFormula(String formula) {
            this.formula = formula;
        }

        String getCurrencyCode() {
            return this.currencyCode;
        }

        void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        String getAcctYear() {
            return this.acctYear;
        }

        void setAcctYear(String acctYear) {
            this.acctYear = acctYear;
        }

        String getAcctPeriod() {
            return this.acctPeriod;
        }

        void setAcctPeriod(String acctPeriod) {
            this.acctPeriod = acctPeriod;
        }

        String getOrgCode() {
            return this.orgCode;
        }

        void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        String getMemo() {
            return this.memo;
        }

        void setMemo(String memo) {
            this.memo = memo;
        }

        String getId() {
            return this.id;
        }

        void setId(String id) {
            this.id = id;
        }

        Integer getSortOrder() {
            return this.sortOrder;
        }

        void setSortOrder(Integer sortOrder) {
            this.sortOrder = sortOrder;
        }

        List<String> getDimComb() {
            return this.dimComb;
        }

        void setDimComb(List<String> dimComb) {
            this.dimComb = dimComb;
        }
    }
}

