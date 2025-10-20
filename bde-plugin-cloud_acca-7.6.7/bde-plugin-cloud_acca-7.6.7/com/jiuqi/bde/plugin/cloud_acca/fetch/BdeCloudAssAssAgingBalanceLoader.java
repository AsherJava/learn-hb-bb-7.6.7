/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.datamodel.aging.AbstractAgingDataLoader
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.bizmodel.execute.model.aging.AgingBalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.common.constant.AgingFetchTypeEnum
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.dto.OrgMappingItem
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateCommonFormatEnum
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.cloud_acca.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.datamodel.aging.AbstractAgingDataLoader;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.model.aging.AgingBalanceCondition;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.common.constant.AgingFetchTypeEnum;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.OrgMappingItem;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.cloud_acca.BdeCloudAccaPluginType;
import com.jiuqi.bde.plugin.cloud_acca.util.CloudAccaFetchUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateCommonFormatEnum;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class BdeCloudAssAssAgingBalanceLoader
extends AbstractAgingDataLoader {
    @Autowired
    private BdeCloudAccaPluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;
    private static final String AGING_BALANCE_PERIOD_TYPE_DAY = "D";
    private static final String AGING_BALANCE_PERIOD_TYPE_MONTH = "M";
    private static final String AGING_BALANCE_PERIOD_TYPE_YEAR = "Y";
    private static final String DEFAULT_DATE_FORMAT = DateCommonFormatEnum.FULL_DIGIT_BY_DASH.getFormat();

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public FetchData loadData(AgingBalanceCondition condi) {
        this.buildTempTable((BalanceCondition)condi);
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(condi.getOrgMapping().getDataSourceCode()));
        String sql = this.getSql(condi);
        Variable variable = this.initParam(condi, sqlHandler);
        String lastSql = VariableParseUtil.parse((String)sql, (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u8d26\u9f84\u4f59\u989d", (Object)condi, (String)lastSql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), ModelExecuteUtil.replaceContext((String)lastSql, (BalanceCondition)condi), null, (ResultSetExtractor)new FetchDataExtractor());
    }

    private String getSql(AgingBalanceCondition condi) {
        String masterSql = this.getMasterSql(condi);
        String masterQuerySql = this.getMasterQuerySql();
        String joinSubjectTableSql = this.getJoinSubjectTableSql();
        String joinWriteOffProcessSql = this.getJoinWriteOffProcessSql();
        String balanceSql = this.getBalanceSql(condi);
        String unChargedBalanceSql = this.getUnChargedBalanceSql();
        if (Boolean.TRUE.equals(condi.isIncludeUncharged())) {
            balanceSql = balanceSql + unChargedBalanceSql;
        }
        masterQuerySql = masterQuerySql.replace("${BALANCE_SQL}", balanceSql).replace("${JOIN_SUBJECT_TABLE}", joinSubjectTableSql).replace("${JOIN_WRITE_OFF_PROCESS_TABLE}", joinWriteOffProcessSql);
        return masterSql.replace("${MASTER_QUERY_SQL}", masterQuerySql);
    }

    private String getMasterSql(AgingBalanceCondition condi) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.SUBJECTCODE  as SUBJECTCODE,\n");
        sql.append("       A.CURRENCYCODE as CURRENCYCODE,\n");
        sql.append("       ${RESULT_EXTERNAL_ORIGINFIELD}  \n");
        if (condi.getAgingFetchType() == AgingFetchTypeEnum.YE) {
            sql.append("       SUM(A.AMNT)    AS HXYE, \n");
            sql.append("       0    AS WHXYE \n");
        } else {
            sql.append("       SUM(A.AMNT)    AS HXNC, \n");
            sql.append("       0    AS WHXNC \n");
        }
        sql.append("FROM (${MASTER_QUERY_SQL}) A \n");
        sql.append("GROUP BY A.SUBJECTCODE, ${EXTERNAL_FIELDSQL} A.CURRENCYCODE\n");
        return sql.toString();
    }

    private String getMasterQuerySql() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.CURRENCYCODE                                                                           as CURRENCYCODE,\n");
        sql.append("       A.SUBJECTCODE                                                                            AS SUBJECTCODE,\n");
        sql.append("       ${EXTERNAL_ORIGINFIELD}  \n");
        sql.append("       A.AMNT* (SUBJECT.ORIENT) + SIGN((A.DEBIT - A.CREDIT) * (SUBJECT.ORIENT)) * COALESCE(CWOPROCESS.SUMAMNT, 0) AS AMNT\n");
        sql.append("FROM (${BALANCE_SQL}) A\n");
        sql.append("${JOIN_SUBJECT_TABLE}\n");
        sql.append("${JOIN_WRITE_OFF_PROCESS_TABLE}\n");
        sql.append("WHERE ${AGING_BALANCE_TYPE} > ${FIRST_DATE} \n");
        sql.append("  AND ${AGING_BALANCE_TYPE} <= ${SECOND_DATE} \n");
        return sql.toString();
    }

    private String getJoinSubjectTableSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("INNER JOIN (SELECT DISTINCT B.CODE,\n");
        sql.append("                            B.NAME,\n");
        sql.append("                            B.ORIENT\n");
        sql.append("              FROM MD_ACCTSUBJECT B\n");
        sql.append("              LEFT JOIN MD_ORG_FIN DW On B.UNITCODE = DW.code\n");
        sql.append("             WHERE (\n");
        sql.append("                   ${SUBJECT_ORG_CONDITION}\n");
        sql.append("                OR DW.Code = (SELECT DISTINCT ${PARENT_ORGS} \n");
        sql.append("                                FROM MD_ORG_FIN c \n");
        sql.append("                               WHERE ${PARENT_ORG_CONDITION}) \n");
        sql.append("                              ) \n");
        sql.append("                    ${VALID_TIME} \n");
        sql.append("                    ${INVALID_TIME} \n");
        sql.append("                    ${SUBJECT_BOOK_CODE} ) SUBJECT ON A.SUBJECTCODE = SUBJECT.CODE \n ");
        return sql.toString();
    }

    private String getJoinWriteOffProcessSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("LEFT JOIN (SELECT P.UNITCODE       AS UNITCODE, \n");
        sql.append("                  P.BILLID         AS BILLID, \n");
        sql.append("                  SUM(ABS(P.AMNT)) AS SUMAMNT \n");
        sql.append("             FROM (SELECT A.UNITCODE       AS UNITCODE, \n");
        sql.append("                          A.FROMID         AS BILLID, \n");
        sql.append("                          CASE \n");
        sql.append("                              WHEN (A.CREDIT) <> 0 THEN -1 \n");
        sql.append("                              ELSE 1 \n");
        sql.append("                              END * A.HXJE AS AMNT, \n");
        sql.append("                          A.HXFLAG         AS HXFLAG, \n");
        sql.append("                          A.HXDATE         AS HXDATE, \n");
        sql.append("                          A.BIZDATE        AS BIZDATE \n");
        sql.append("                     FROM GL_ACCT_WO_PROCESS A  \n");
        sql.append("                    UNION ALL \n");
        sql.append("                   SELECT B.UNITCODE       AS UNITCODE, \n");
        sql.append("                          B.TOID           AS BILLID, \n");
        sql.append("                          CASE \n");
        sql.append("                              WHEN (B.CREDIT) <> 0 THEN -1 \n");
        sql.append("                              ELSE 1 \n");
        sql.append("                              END * B.HXJE AS AMNT, \n");
        sql.append("                          B.HXFLAG         AS HXFLAG, \n");
        sql.append("                          B.HXDATE         AS HXDATE, \n");
        sql.append("                          B.BIZDATE        AS BIZDATE \n");
        sql.append("                     FROM GL_ACCT_WO_PROCESS B) P \n");
        sql.append("          WHERE P.BIZDATE > ${DEADLINE_DATE} \n");
        sql.append("          GROUP BY P.UNITCODE, P.BILLID) CWOPROCESS \n");
        sql.append("   ON A.UNITCODE = CWOPROCESS.UNITCODE \n");
        sql.append("   AND A.ID = CWOPROCESS.BILLID \n");
        return sql.toString();
    }

    private String getBalanceSql(AgingBalanceCondition condi) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.ID, \n");
        sql.append("       A.CURRENCYCODE   as CURRENCYCODE, \n");
        sql.append("       A.SUBJECTCODE    AS SUBJECTCODE, \n");
        sql.append("       ${ORIGINFIELD}  \n");
        sql.append("       A.BIZDATE, \n");
        sql.append("       A.UNITCODE, \n");
        sql.append("       A.DEBIT, \n");
        sql.append("       A.CREDIT, \n");
        sql.append("       CASE \n");
        sql.append("           WHEN (A.CREDIT) <> 0 THEN -1 \n");
        sql.append("           ELSE 1 \n");
        sql.append("           END * A.HXYE AS AMNT \n");
        sql.append("FROM GL_ACCT_WO_DATA A  \n");
        if (Boolean.FALSE.equals(condi.isIncludeUncharged())) {
            sql.append("     LEFT JOIN GL_VOUCHER_${YEAR} VCHR ON VCHR.ID = A.VCHRID AND (VCHR.POSTFLAG = 1  OR VCHR.POSTFLAG IS NULL)  \n");
        }
        sql.append("WHERE A.ACCTYEAR = ${YEAR} \n");
        sql.append("  AND ${ORG_CONDI} \n");
        sql.append(this.getWoInitCondi());
        return sql.toString();
    }

    private String getWoInitCondi() {
        StringBuilder sql = new StringBuilder();
        sql.append("AND A.SUBJECTCODE not in (SELECT subjectcode\n");
        sql.append("                            FROM GL_ACCT_WO_INIT GAWI\n");
        sql.append("                           WHERE GAWI.acctyear = A.acctyear\n");
        sql.append("                             AND GAWI.unitcode = A.unitcode\n");
        sql.append("                             AND GAWI.bookcode = A.bookcode\n");
        sql.append("                             AND A.acctperiod = '0'\n");
        sql.append("                             AND GAWI.useflag = 0)\n");
        return sql.toString();
    }

    private String getUnChargedBalanceSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("UNION ALL \n");
        sql.append("SELECT A.ID, \n");
        sql.append("       A.CURRENCYCODE                        AS CURRENCYCODE, \n");
        sql.append("       A.SUBJECTCODE                         AS SUBJECTCODE, \n");
        sql.append("       ${ORIGINFIELD}  \n");
        sql.append("       A.BIZDATE, \n");
        sql.append("       PZ.UNITCODE, \n");
        sql.append("       A.DEBIT, \n");
        sql.append("       A.CREDIT, \n");
        sql.append("       (A.DEBIT - A.CREDIT) AS AMNT \n");
        sql.append("FROM GL_VOUCHERITEMASS_${YEAR} A  \n");
        sql.append("         JOIN GL_VOUCHER_${YEAR} PZ ON A.VCHRID = PZ.ID \n");
        sql.append("WHERE 1 = 1 \n");
        sql.append("  AND ${ORG_CONDI} \n");
        sql.append("  AND PZ.TEMPFLAG = 0 \n");
        sql.append("  AND PZ.AUDITFLAG = 0 \n");
        return sql.toString();
    }

    private Variable initParam(AgingBalanceCondition condi, IDbSqlHandler sqlHandler) {
        Variable variable = new Variable();
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider((BalanceCondition)condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder dimFieldSql = new StringBuilder();
        StringBuilder externalDimFieldSql = new StringBuilder();
        StringBuilder resultExternalDimFieldSql = new StringBuilder();
        StringBuilder externalDimGroupSql = new StringBuilder();
        StringBuilder allDimFieldSql = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            dimFieldSql.append(String.format("A .%1$s AS %2$s,", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            externalDimFieldSql.append(String.format("A .%1$s AS %1$s,", assistMapping.getAssistCode()));
            resultExternalDimFieldSql.append(String.format("CASE WHEN A .%1$s IS NULL THEN '%2$S'  ELSE A .%1$s END AS %1$s,", assistMapping.getAssistCode(), "#"));
            allDimFieldSql.append(String.format("A.%1$s,", assistMapping.getAccountAssistCode()));
            externalDimGroupSql.append(String.format("A.%1$s,", assistMapping.getAssistCode()));
        }
        variable.put("ORIGINFIELD", dimFieldSql.toString());
        variable.put("FIELDSQL", allDimFieldSql.toString());
        variable.put("RESULT_EXTERNAL_ORIGINFIELD", resultExternalDimFieldSql.toString());
        variable.put("EXTERNAL_ORIGINFIELD", externalDimFieldSql.toString());
        variable.put("EXTERNAL_FIELDSQL", externalDimGroupSql.toString());
        String bizDate = "A.BIZDATE";
        Date now = condi.getEndDate();
        Calendar oc = Calendar.getInstance();
        oc.setTime(now);
        oc.set(6, 1);
        oc.add(6, -1);
        Date firstDayOfYear = oc.getTime();
        String dateFmt = "'" + sqlHandler.hyphenDateFmt().trim() + "'";
        String agingperiodType = condi.getAgingPeriodType().toString();
        Integer agingEndPeriod = condi.getAgingEndPeriod();
        Integer agingStartPeriod = condi.getAgingStartPeriod();
        if (condi.getAgingFetchType() == AgingFetchTypeEnum.YE) {
            variable.put("AGING_BALANCE_TYPE", bizDate);
            variable.put("FIRST_DATE", sqlHandler.toDate(this.getDate(now, agingperiodType, agingEndPeriod), dateFmt));
            variable.put("SECOND_DATE", sqlHandler.toDate(this.getDate(now, agingperiodType, agingStartPeriod), dateFmt));
            variable.put("DEADLINE_DATE", sqlHandler.toDate("'" + this.currentDay(now) + "'", dateFmt));
        } else {
            variable.put("AGING_BALANCE_TYPE", bizDate);
            variable.put("FIRST_DATE", sqlHandler.toDate(this.getDate(firstDayOfYear, agingperiodType, agingEndPeriod), dateFmt));
            variable.put("SECOND_DATE", sqlHandler.toDate(this.getDate(firstDayOfYear, agingperiodType, agingStartPeriod), dateFmt));
            variable.put("DEADLINE_DATE", sqlHandler.toDate("'" + this.currentDay(firstDayOfYear) + "'", dateFmt));
        }
        variable.put("SUBJECT_ORG_CONDITION", CloudAccaFetchUtil.getInCondi("B.UnitCode", this.getDistinctOrgAndChildreCode(condi.getOrgMapping())));
        variable.put("PARENT_ORG_CONDITION", CloudAccaFetchUtil.getInCondi("C.code", this.getDistinctOrgAndChildreCode(condi.getOrgMapping())));
        variable.put("PARENT_ORGS", sqlHandler.SubStr("c.PARENTS", sqlHandler.indexStrSql("c.PARENTS", "'/'") + "-1"));
        Date endDate = DateUtils.lastDateOf((int)condi.getAcctYear(), (int)condi.getEndPeriod());
        variable.put("VALID_TIME", " AND " + sqlHandler.formatDate("B.validtime", sqlHandler.hyphenDateFmt()) + " <= '" + DateUtils.format((Date)endDate) + "'");
        variable.put("INVALID_TIME", " AND " + sqlHandler.formatDate("B.Invalidtime", sqlHandler.hyphenDateFmt()) + " > '" + DateUtils.format((Date)endDate) + "'");
        variable.put("SUBJECT_BOOK_CODE", "AND B.BOOKCODE = '" + condi.getOrgMapping().getAcctBookCode() + "'");
        variable.put("YEAR", condi.getAcctYear() + "");
        variable.put("ORG_CONDI", CloudAccaFetchUtil.getInCondi("A.UnitCode", this.getDistinctOrgAndChildreCode(condi.getOrgMapping())));
        return variable;
    }

    private List<String> getDistinctOrgAndChildreCode(OrgMappingDTO orgMapping) {
        HashSet<String> assistCodeList = new HashSet<String>(orgMapping.getOrgMappingItems().size());
        if (CollectionUtils.isEmpty((Collection)orgMapping.getOrgMappingItems())) {
            assistCodeList.add(orgMapping.getAcctOrgCode());
            return new ArrayList<String>(assistCodeList);
        }
        for (OrgMappingItem orgMappingItem : orgMapping.getOrgMappingItems()) {
            if (StringUtils.isEmpty((String)orgMappingItem.getAcctOrgCode())) continue;
            assistCodeList.add(orgMappingItem.getAcctOrgCode());
        }
        return new ArrayList<String>(assistCodeList);
    }

    private String getDate(Date now, String zlqjType, int qj) throws BusinessRuntimeException {
        String date;
        if (zlqjType.endsWith(AGING_BALANCE_PERIOD_TYPE_DAY)) {
            date = this.decreaseDay(now, qj);
        } else if (zlqjType.endsWith(AGING_BALANCE_PERIOD_TYPE_MONTH)) {
            date = this.decreaseMonth(now, qj);
        } else if (zlqjType.endsWith(AGING_BALANCE_PERIOD_TYPE_YEAR)) {
            date = this.decreaseYear(now, qj);
        } else {
            throw new BusinessRuntimeException("\u9ad8\u7ea7\u8d26\u9f84\u516c\u5f0f\u7684\u671f\u95f4\u7c7b\u578b\u9519\u8bef");
        }
        return "'" + date + "'";
    }

    private String decreaseDay(Date now, int days) {
        return ModelExecuteUtil.decreaseDay((Date)now, (int)days, (String)DEFAULT_DATE_FORMAT);
    }

    private String decreaseMonth(Date now, int months) {
        return ModelExecuteUtil.decreaseMonth((Date)now, (int)months, (boolean)true, (String)DEFAULT_DATE_FORMAT);
    }

    private String decreaseYear(Date now, int years) {
        return ModelExecuteUtil.decreaseYear((Date)now, (int)years, (String)DEFAULT_DATE_FORMAT);
    }

    private String currentDay(Date now) {
        if (now != null) {
            return DateUtils.format((Date)now);
        }
        return DateUtils.nowDateStr();
    }
}

