/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.callback;

import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO;
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

public class FetchSettingFixedsettingdataUpdate
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(FetchSettingFixedsettingdataUpdate.class);
    private static final String FILED_STRING = " id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType,fetchSourceCode,bizModelCode,optimizeRuleGroup,sign,fetchType,subjectCode,excludeSubjectCode,dimType,sumType,reclassSubjCode,reclassSrcSubjCode,agingRangeType,agingRangeStart,agingRangeEnd,cashCode,dimensionSetting,formula,currencyCode,acctYear,acctPeriod,orgCode,memo,sortOrder,dataSourceCode ";
    private static final String NEW_FILED_STRING = " id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData, sortOrder";
    private static final String BACKUP_TABLE_PREFIX = "BK230607_";

    @Transactional(rollbackFor={Exception.class})
    public void execute(DataSource dataSource) throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        logger.info("\u5f00\u59cb\u5411\u65b0\u7684BDE\u53d6\u6570\u8bbe\u7f6e\u8bbe\u8ba1\u671f\u8868\u4e2d\u586b\u5145\u5347\u7ea7\u540e\u7684\u5386\u53f2\u6570\u636e");
        this.insertHistoryDataByTableName("BDE_FETCHSETTING_DES", jdbcTemplate);
        logger.info("\u5411\u65b0\u7684BDE\u53d6\u6570\u8bbe\u7f6e\u8bbe\u8ba1\u671f\u8868\u4e2d\u586b\u5145\u5347\u7ea7\u540e\u7684\u5386\u53f2\u6570\u636e\u5b8c\u6210");
        logger.info("\u5f00\u59cb\u5411\u65b0\u7684BDE\u53d6\u6570\u8bbe\u7f6e\u8fd0\u884c\u671f\u8868\u4e2d\u586b\u5145\u5347\u7ea7\u540e\u7684\u5386\u53f2\u6570\u636e");
        this.insertHistoryDataByTableName("BDE_FETCHSETTING", jdbcTemplate);
        logger.info("\u5411\u65b0\u7684BDE\u53d6\u6570\u8bbe\u7f6e\u8fd0\u884c\u671f\u8868\u4e2d\u586b\u5145\u5347\u7ea7\u540e\u7684\u5386\u53f2\u6570\u636e\u5b8c\u6210");
    }

    private void insertHistoryDataByTableName(String tableName, JdbcTemplate jdbcTemplate) {
        String querSql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType,fetchSourceCode,bizModelCode,optimizeRuleGroup,sign,fetchType,subjectCode,excludeSubjectCode,dimType,sumType,reclassSubjCode,reclassSrcSubjCode,agingRangeType,agingRangeStart,agingRangeEnd,cashCode,dimensionSetting,formula,currencyCode,acctYear,acctPeriod,orgCode,memo,sortOrder,dataSourceCode  \n FROM BK230607_" + tableName + "  fs  ORDER BY SORTORDER\n";
        List oldSettingList = jdbcTemplate.query(querSql, (rs, row) -> this.getOldFetchSettingEO(rs));
        LinkedHashMap oldSettingGroupMap = oldSettingList.stream().collect(Collectors.groupingBy(OldFetchSettingDesEO::getGroupIdentification, LinkedHashMap::new, Collectors.toList()));
        ArrayList<FetchSettingDesEO> settingDesEOList = new ArrayList<FetchSettingDesEO>();
        for (List oldSettingGroupByCondi : oldSettingGroupMap.values()) {
            OldFetchSettingDesEO firstOldFetchSettingDesEO = (OldFetchSettingDesEO)oldSettingGroupByCondi.get(0);
            FetchSettingDesEO fetchSettingDesEO = new FetchSettingDesEO();
            fetchSettingDesEO.setId(UUIDUtils.newHalfGUIDStr());
            fetchSettingDesEO.setFetchSchemeId(firstOldFetchSettingDesEO.getFetchSchemeId());
            fetchSettingDesEO.setFormSchemeId(firstOldFetchSettingDesEO.getFormSchemeId());
            fetchSettingDesEO.setFormId(firstOldFetchSettingDesEO.getFormId());
            fetchSettingDesEO.setRegionId(firstOldFetchSettingDesEO.getRegionId());
            fetchSettingDesEO.setDataLinkId(firstOldFetchSettingDesEO.getDataLinkId());
            fetchSettingDesEO.setFieldDefineId(firstOldFetchSettingDesEO.getFieldDefineId());
            fetchSettingDesEO.setRegionType(firstOldFetchSettingDesEO.getRegionType());
            fetchSettingDesEO.setSortOrder(firstOldFetchSettingDesEO.getSortOrder());
            ArrayList<FixedAdaptSettingVO> fixedAdaptSettingVOList = new ArrayList<FixedAdaptSettingVO>();
            FixedAdaptSettingVO adaptSettingVO = new FixedAdaptSettingVO();
            StringBuilder memo = new StringBuilder();
            LinkedHashMap fetchSourceSettingGroup = oldSettingGroupByCondi.stream().collect(Collectors.groupingBy(OldFetchSettingDesEO::getFetchSourceCode, LinkedHashMap::new, Collectors.toList()));
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
            fetchSettingDesEO.setFixedSettingData(JsonUtils.writeValueAsString(fixedAdaptSettingVOList));
            settingDesEOList.add(fetchSettingDesEO);
        }
        this.addBatchFetchSettingDesEO(settingDesEOList, tableName, jdbcTemplate);
    }

    public void addBatchFetchSettingDesEO(List<FetchSettingDesEO> fetchSettingDesEOS, String tableName, JdbcTemplate jdbcTemplate) {
        String sql = " insert into  " + tableName + " \n ( " + NEW_FILED_STRING + ")\n values( ?,?,?,?,?, ?,?,?,?,?)";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (FetchSettingDesEO eo : fetchSettingDesEOS) {
            if (StringUtils.isEmpty((String)eo.getId())) {
                eo.setId(UUIDUtils.newHalfGUIDStr());
            }
            Object[] args = new Object[]{eo.getId(), eo.getFetchSchemeId(), eo.getFormSchemeId(), eo.getFormId(), eo.getRegionId(), eo.getDataLinkId(), eo.getFieldDefineId(), eo.getRegionType(), eo.getFixedSettingData(), eo.getSortOrder()};
            argsList.add(args);
        }
        jdbcTemplate.batchUpdate(sql, argsList);
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
        fetchSourceRowSettingVO.setFormula(oldFetchSettingDesEO.getFormula());
        fetchSourceRowSettingVO.setDataSourceCode(oldFetchSettingDesEO.getDataSourceCode());
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

        public String getGroupIdentification() {
            StringBuilder groupIdentification = new StringBuilder();
            groupIdentification.append(this.fetchSchemeId).append("-");
            groupIdentification.append(this.formSchemeId).append("-");
            groupIdentification.append(this.formId).append("-");
            groupIdentification.append(this.regionId).append("-");
            groupIdentification.append(this.dataLinkId).append("-");
            groupIdentification.append(this.fieldDefineId).append("-");
            groupIdentification.append(this.regionType).append("-");
            return groupIdentification.toString();
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

