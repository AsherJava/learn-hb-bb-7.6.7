/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.client.assistdim.enums.AssistDimEffectTableEnum
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  org.apache.commons.collections4.MapUtils
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.penetrate.impl.service.impl;

import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.service.AdjustVchrPenetrateContentProvider;
import com.jiuqi.bde.penetrate.impl.util.AdjustVchrPenetrateUtil;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.client.assistdim.enums.AssistDimEffectTableEnum;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class AdjustVchrPenetrateContentProviderImpl
implements AdjustVchrPenetrateContentProvider {
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    protected BizDataRefDefineService bizDataDefineService;
    @Autowired
    protected BaseDataRefDefineService baseDataDefineService;
    @Autowired
    private AdjustVchrPenetrateUtil adjustVchrPenetrateUtil;

    @Override
    public List<PenetrateBalance> query(PenetrateBaseDTO condi) {
        List dimensionVOS = this.dimensionService.findDimFieldsVOByTableName(AssistDimEffectTableEnum.DC_ADJUSTVCHRITEM.name());
        Map<String, DimensionVO> dimensionMap = dimensionVOS.stream().collect(Collectors.toMap(DimensionVO::getCode, dimension -> dimension));
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT 0                                                                  AS ROWTYPE,  \n");
        sql.append("      '${ADJUSTTYPE}'                                                     AS VCHRSRCTYPE, \n");
        sql.append("       A.SUBJECTCODE                                                      AS SUBJECTCODE,     \n");
        sql.append("       SUBJECT.NAME                                                       AS SUBJECTNAME, \n");
        sql.append("       A.CURRENCYCODE                                                     AS CURRENCYCODE, \n");
        sql.append("       1 AS ORIENT,  \n");
        sql.append("       0                                                                  AS NC, \n");
        sql.append("       0                                                                  AS QC, \n");
        sql.append("       SUM(A.DEBIT)                                                       AS DEBIT, \n");
        sql.append("       SUM(A.CREDIT)                                                      AS CREDIT, \n");
        sql.append("       SUM(A.DEBIT)                                                       AS DSUM, \n");
        sql.append("       SUM(A.CREDIT)                                                      AS CSUM, \n");
        sql.append("       SUM(A.DEBIT - A.CREDIT)                                            AS YE, \n");
        sql.append("       0                                                                  AS ORGNNC, \n");
        sql.append("       0                                                                  AS ORGNQC, \n");
        sql.append("       SUM(A.ORGND)                                                       AS ORGND, \n");
        sql.append("       SUM(A.ORGNC)                                                       AS ORGNC, \n");
        sql.append("       SUM(A.ORGND)                                                       AS ORGNDSUM, \n");
        sql.append("       SUM(A.ORGNC)                                                       AS ORGNCSUM, \n");
        sql.append("       SUM(A.ORGND - A.ORGNC)                                             AS ORGNYE \n");
        sql.append("       ${ASSIST_FIELD_SQL}");
        sql.append("  FROM DC_ADJUSTVCHRITEM A \n");
        sql.append("  LEFT JOIN MD_ACCTSUBJECT SUBJECT ON A.SUBJECTCODE = SUBJECT.CODE  \n");
        sql.append("       ${ASSJOINSQL} \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append(String.format(" AND A.UNITCODE = '%s' \n", condi.getOrgMapping().getReportOrgCode()));
        sql.append(String.format(" AND A.ACCTYEAR = %s \n", condi.getAcctYear()));
        sql.append("   AND A.ACCTPERIOD >= '${STARTPERIOD}' \n");
        sql.append("   AND A.ACCTPERIOD <= '${ENDPERIOD}' \n");
        sql.append("   ${ADJUST_VCHR_PERIOD_TYPE} \n");
        sql.append(this.adjustVchrPenetrateUtil.buildSubjectCondi("A", "SUBJECTCODE", condi.getSubjectCode())).append(" \n");
        sql.append(this.adjustVchrPenetrateUtil.buildExcludeCondi("A", "SUBJECTCODE", condi.getExcludeSubjectCode())).append(" \n");
        sql.append("   ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append(" GROUP BY A.SUBJECTCODE, SUBJECT.NAME, A.CURRENCYCODE ${ASSIST_GROUP_SQL} \n");
        StringBuilder assistFieldSql = new StringBuilder();
        StringBuilder assistGroupSql = new StringBuilder();
        StringBuilder externalAssJoinSql = new StringBuilder();
        StringBuilder externalAssConfigSql = new StringBuilder();
        Variable variable = new Variable();
        if (!CollectionUtils.isEmpty((Collection)condi.getAssTypeList())) {
            for (Dimension dimension2 : condi.getAssTypeList()) {
                if (StringUtils.isEmpty((String)dimension2.getDimCode())) continue;
                if (dimensionMap.get(dimension2.getDimCode()) == null) {
                    return new ArrayList<PenetrateBalance>();
                }
                String referField = dimensionMap.get(dimension2.getDimCode()).getReferField();
                if (referField != null) {
                    String assistTable = referField.substring(referField.indexOf("MD_") + "MD_".length()).toUpperCase();
                    assistFieldSql.append(String.format(",CASE WHEN A.%1$s = '#' THEN NULL ELSE A.%1$s END AS %1$s, %2$s.NAME AS %1$s_NAME \n", dimension2.getDimCode(), assistTable));
                    externalAssJoinSql.append(String.format("LEFT JOIN %1$s %2$s ON %2$s.CODE = A.%3$s ", referField, assistTable, dimension2.getDimCode()));
                    assistGroupSql.append(String.format(", A.%1$s, %2$s.NAME", dimension2.getDimCode(), assistTable));
                } else {
                    assistFieldSql.append(String.format(",CASE WHEN A.%1$s = '#' THEN NULL ELSE A.%1$s END AS %1$s, \n", dimension2.getDimCode()));
                    assistGroupSql.append(String.format(", A.%1$s", dimension2.getDimCode()));
                }
                externalAssConfigSql.append(this.adjustVchrPenetrateUtil.matchByRule("A", dimension2.getDimCode(), dimension2.getDimValue(), dimension2.getDimRule()));
            }
        }
        String periodScheme = "";
        if (MapUtils.isNotEmpty((Map)condi.getDimensionSet()) && condi.getDimensionSet().containsKey("DATATIME")) {
            periodScheme = (String)((Map)condi.getDimensionSet().get("DATATIME")).get("value");
        }
        variable.put("ADJUST_VCHR_PERIOD_TYPE", this.adjustVchrPenetrateUtil.buildAdjustVchrPeriodTypeCondi(periodScheme));
        variable.put("ASSIST_FIELD_SQL", assistFieldSql.toString());
        variable.put("ASSIST_GROUP_SQL", assistGroupSql.toString());
        variable.put("ASSJOINSQL", externalAssJoinSql.toString());
        variable.put("STARTPERIOD", String.format("%02d", condi.getStartPeriod()));
        variable.put("ENDPERIOD", String.format("%02d", condi.getEndPeriod()));
        variable.put("EXTERNAL_ASS_CONFIG_SQL", externalAssConfigSql.toString());
        variable.put("ADJUSTTYPE", "\u624b\u5de5\u8c03\u6574");
        String lastSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        return (List)OuterDataSourceUtils.getJdbcTemplate().query(lastSql, (ResultSetExtractor)new ResultSetExtractor<List<PenetrateBalance>>(){

            public List<PenetrateBalance> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<PenetrateBalance> result = new ArrayList<PenetrateBalance>();
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (rs.next()) {
                    PenetrateBalance penetrateBalance = new PenetrateBalance();
                    for (int i = 1; i <= columnCount; ++i) {
                        String columnName = metaData.getColumnLabel(i).toUpperCase();
                        Object columnValue = rs.getObject(i);
                        penetrateBalance.put(columnName, columnValue);
                    }
                    result.add(penetrateBalance);
                }
                return result;
            }
        }, new Object[0]);
    }

    @Override
    public List<PenetrateBalance> xjllQuery(PenetrateBaseDTO condi) {
        List dimensionVOS = this.dimensionService.findDimFieldsVOByTableName(AssistDimEffectTableEnum.DC_ADJUSTVCHRITEM.name());
        Map<String, DimensionVO> dimensionMap = dimensionVOS.stream().collect(Collectors.toMap(DimensionVO::getCode, dimension -> dimension));
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT 0                                                                  AS ROWTYPE,  \n");
        sql.append("      '${ADJUSTTYPE}'                                                     AS VCHRSRCTYPE, \n");
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append("       A.SUBJECTCODE                                                      AS SUBJECTCODE,     \n");
            sql.append("       SUBJECT.NAME                                                       AS SUBJECTNAME, \n");
        }
        sql.append("       A.CFITEMCODE                                                       AS CASHCODE, \n");
        sql.append("       CFITEM.NAME                                                        AS CASHNAME, \n");
        sql.append("       1 AS ORIENT,  \n");
        sql.append("       SUM(A.DEBIT - A.CREDIT)                                            AS BQNUM,\n");
        sql.append("       SUM(A.ORGND - A.ORGNC)                                             AS WBQNUM,\n");
        sql.append("       SUM(A.DEBIT - A.CREDIT)                                            AS LJNUM,\n");
        sql.append("       SUM(A.ORGND - A.ORGNC)                                             AS WLJNUM\n");
        sql.append("       ${ASSIST_FIELD_SQL}");
        sql.append("  FROM DC_ADJUSTVCHRITEM A \n");
        sql.append("  LEFT JOIN MD_ACCTSUBJECT SUBJECT ON A.SUBJECTCODE = SUBJECT.CODE  \n");
        sql.append("  LEFT JOIN MD_CFITEM CFITEM ON A.CFITEMCODE = CFITEM.CODE  \n");
        sql.append("       ${ASSJOINSQL} \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append(String.format(" AND A.UNITCODE = '%s' \n", condi.getOrgMapping().getReportOrgCode()));
        sql.append(String.format(" AND A.ACCTYEAR = %s \n", condi.getAcctYear()));
        sql.append("   AND A.ACCTPERIOD >= '${STARTPERIOD}' \n");
        sql.append("   AND A.ACCTPERIOD <= '${ENDPERIOD}' \n");
        sql.append("   ${ADJUST_VCHR_PERIOD_TYPE} \n");
        sql.append(this.adjustVchrPenetrateUtil.buildCfItemCondi("A", "CFITEMCODE", condi.getCashCode()));
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append(this.adjustVchrPenetrateUtil.buildSubjectCondi("A", "SUBJECTCODE", condi.getSubjectCode()));
        }
        sql.append("   ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append(" GROUP BY A.CFITEMCODE, CFITEM.NAME, A.CURRENCYCODE \n");
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append(", A.SUBJECTCODE, SUBJECT.NAME \n");
        }
        sql.append("   ${ASSIST_GROUP_SQL}  \n");
        StringBuilder assistFieldSql = new StringBuilder();
        StringBuilder assistGroupSql = new StringBuilder();
        StringBuilder externalAssJoinSql = new StringBuilder();
        StringBuilder externalAssConfigSql = new StringBuilder();
        Variable variable = new Variable();
        if (!CollectionUtils.isEmpty((Collection)condi.getAssTypeList())) {
            for (Dimension dimension2 : condi.getAssTypeList()) {
                if (dimensionMap.get(dimension2.getDimCode()) == null) continue;
                String referField = dimensionMap.get(dimension2.getDimCode()).getReferField();
                if (referField != null) {
                    String assistTable = referField.substring(referField.indexOf("MD_") + "MD_".length()).toUpperCase();
                    assistFieldSql.append(String.format(",CASE WHEN A.%1$s = '#' THEN NULL ELSE A.%1$s END AS %1$s, %2$s.NAME AS %1$s_NAME \n", dimension2.getDimCode(), assistTable));
                    externalAssJoinSql.append(String.format("LEFT JOIN %1$s %2$s ON %2$s.CODE = A.%3$s ", referField, assistTable, dimension2.getDimCode()));
                    assistGroupSql.append(String.format(", A.%1$s, %2$s.NAME", dimension2.getDimCode(), assistTable));
                } else {
                    assistFieldSql.append(String.format(",CASE WHEN A.%1$s = '#' THEN NULL ELSE A.%1$s END AS %1$s, \n", dimension2.getDimCode()));
                    assistGroupSql.append(String.format(", A.%1$s", dimension2.getDimCode()));
                }
                externalAssConfigSql.append(this.adjustVchrPenetrateUtil.matchByRule("A", dimension2.getDimCode(), dimension2.getDimValue(), dimension2.getDimRule()));
            }
        }
        String periodScheme = "";
        if (MapUtils.isNotEmpty((Map)condi.getDimensionSet()) && condi.getDimensionSet().containsKey("DATATIME")) {
            periodScheme = (String)((Map)condi.getDimensionSet().get("DATATIME")).get("value");
        }
        variable.put("ADJUST_VCHR_PERIOD_TYPE", this.adjustVchrPenetrateUtil.buildAdjustVchrPeriodTypeCondi(periodScheme));
        variable.put("ASSIST_FIELD_SQL", assistFieldSql.toString());
        variable.put("ASSIST_GROUP_SQL", assistGroupSql.toString());
        variable.put("ASSJOINSQL", externalAssJoinSql.toString());
        variable.put("STARTPERIOD", String.format("%02d", condi.getStartPeriod()));
        variable.put("ENDPERIOD", String.format("%02d", condi.getEndPeriod()));
        variable.put("EXTERNAL_ASS_CONFIG_SQL", externalAssConfigSql.toString());
        variable.put("ADJUSTTYPE", "\u624b\u5de5\u8c03\u6574");
        String lastSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        return (List)OuterDataSourceUtils.getJdbcTemplate().query(lastSql, (ResultSetExtractor)new ResultSetExtractor<List<PenetrateBalance>>(){

            public List<PenetrateBalance> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<PenetrateBalance> result = new ArrayList<PenetrateBalance>();
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (rs.next()) {
                    PenetrateBalance penetrateBalance = new PenetrateBalance();
                    for (int i = 1; i <= columnCount; ++i) {
                        String columnName = metaData.getColumnLabel(i).toUpperCase();
                        Object columnValue = rs.getObject(i);
                        penetrateBalance.put(columnName, columnValue);
                    }
                    result.add(penetrateBalance);
                }
                return result;
            }
        }, new Object[0]);
    }
}

