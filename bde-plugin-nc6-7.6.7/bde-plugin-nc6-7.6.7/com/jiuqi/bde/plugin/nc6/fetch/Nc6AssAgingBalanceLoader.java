/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.datamodel.aging.AbstractAgingDataLoader
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue
 *  com.jiuqi.bde.bizmodel.execute.model.aging.AgingBalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil
 *  com.jiuqi.bde.common.constant.AgingFetchTypeEnum
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.data.util.Pair
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nc6.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.datamodel.aging.AbstractAgingDataLoader;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue;
import com.jiuqi.bde.bizmodel.execute.model.aging.AgingBalanceCondition;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil;
import com.jiuqi.bde.common.constant.AgingFetchTypeEnum;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.nc6.BdeNc6PluginType;
import com.jiuqi.bde.plugin.nc6.assist.Nc6AssistPojo;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class Nc6AssAgingBalanceLoader
extends AbstractAgingDataLoader {
    @Autowired
    private BdeNc6PluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;
    private static final String FULL_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public FetchData loadData(AgingBalanceCondition condi) {
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider((BalanceCondition)condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "DEFAULT" : schemeMappingProvider.getOrgMappingType();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT MD_ACCTSUBJECT.ORIENT,  \n");
        sql.append("       ${SUBJECTFIELD} AS SUBJECTCODE,  \n");
        sql.append("       HB.CODE CURRENCYCODE  \n");
        sql.append("               ${EXTERNAL_SELECT_SQL}  \n");
        if (condi.getAgingFetchType() == AgingFetchTypeEnum.NC) {
            sql.append("       ,SUM((BALANCELOCALDEBITAMOUNT + NVL(LOCL_J, 0) -  BALANCELOCALCREDITAMOUNT - NVL(LOCL_D, 0)) * MD_ACCTSUBJECT.ORIENT) HXNC,  \n");
            sql.append("       SUM((BALANCELOCALDEBITAMOUNT + NVL(FRAC_J, 0) -  BALANCELOCALCREDITAMOUNT - NVL(FRAC_D, 0)) * MD_ACCTSUBJECT.ORIENT) WHXNC  \n");
        } else {
            sql.append("       ,SUM((BALANCELOCALDEBITAMOUNT + NVL(LOCL_J, 0) -  BALANCELOCALCREDITAMOUNT - NVL(LOCL_D, 0)) * MD_ACCTSUBJECT.ORIENT) HXYE,  \n");
            sql.append("       SUM((BALANCELOCALDEBITAMOUNT + NVL(FRAC_J, 0) -  BALANCELOCALCREDITAMOUNT - NVL(FRAC_D, 0)) * MD_ACCTSUBJECT.ORIENT) WHXYE  \n");
        }
        sql.append("  FROM GL_VERIFYDETAIL GL_VERIFYDETAIL \n");
        sql.append("  INNER JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.ID = GL_VERIFYDETAIL.PK_ACCASOA ");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("         ${BOOKCODE} \n");
        }
        sql.append("  LEFT JOIN (${EXTERNAL_ASSIT_SQL}) ASSI ON GL_VERIFYDETAIL.ASSID = ASSI.ASSID \n");
        sql.append("  INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON GL_VERIFYDETAIL.PK_CURRTYPE = HB.ID \n");
        sql.append("  ${DETAILORGJOINSQL} \n");
        sql.append("  LEFT OUTER JOIN (SELECT L.PK_VERIFYDETAIL,  \n");
        sql.append("                          SUM(VERIFYDEBITAMOUNT) FORMERLY_J,  \n");
        sql.append("                          SUM(VERIFYFRACDEBITAMOUNT) FRAC_J,  \n");
        sql.append("                          SUM(VERIFYLOCALDEBITAMOUNT) LOCL_J,  \n");
        sql.append("                          SUM(VERIFYGROUPDEBITAMOUNT) GROUPAMOUNT_J,  \n");
        sql.append("                          SUM(VERIFYGLOBALDEBITAMOUNT) GLOBALAMOUNT_J,  \n");
        sql.append("                          SUM(VERIFYCREDITAMOUNT) FORMERLY_D,  \n");
        sql.append("                          SUM(VERIFYFRACCREDITAMOUNT) FRAC_D,  \n");
        sql.append("                          SUM(VERIFYLOCALCREDITAMOUNT) LOCL_D,  \n");
        sql.append("                          SUM(VERIFYGROUPCREDITAMOUNT) GROUPAMOUNT_D,  \n");
        sql.append("                          SUM(VERIFYGLOBALCREDITAMOUNT) GLOBALAMOUNT_D  \n");
        sql.append("                     FROM GL_VERIFY_LOG L  \n");
        sql.append("                    WHERE OPDATE > '${AGING_FETCHDATE}'  \n");
        sql.append("                      AND DR = 0  \n");
        sql.append("                    GROUP BY L.PK_VERIFYDETAIL) A  \n");
        sql.append("    ON A.PK_VERIFYDETAIL = GL_VERIFYDETAIL.PK_VERIFYDETAIL  \n");
        sql.append(" WHERE 1 = 1  \n");
        sql.append("   ${ORGCONDITION} \n");
        sql.append("   ${DETAILBOOKCONDITION}");
        sql.append("   AND GL_VERIFYDETAIL.PREPAREDDATE >= '${BEGIN_BIZDATE}'  \n");
        sql.append("   AND GL_VERIFYDETAIL.PREPAREDDATE <= '${END_BIZDATE}'  \n");
        sql.append("   AND ((BALANCELOCALDEBITAMOUNT + NVL(A.LOCL_J, 0)) <> 0 OR  \n");
        sql.append("       (BALANCEDEBITAMOUNT + NVL(A.FORMERLY_J, 0)) <> 0 OR  \n");
        sql.append("       (BALANCELOCALCREDITAMOUNT + NVL(A.LOCL_D, 0)) <> 0 OR  \n");
        sql.append("       (BALANCECREDITAMOUNT + NVL(A.FORMERLY_D, 0)) <> 0)  \n");
        sql.append("   AND GL_VERIFYDETAIL.DR = 0  \n");
        sql.append(" GROUP BY ${SUBJECTFIELD},MD_ACCTSUBJECT.ORIENT  \n");
        sql.append("          ${EXTERNAL_GROUP_SQL}  \n");
        sql.append("          ,HB.CODE  \n");
        Date effectFetchDate = this.getEffectFetchDate(condi);
        String agingfetchDate = DateUtils.format((Date)effectFetchDate, (String)FULL_TIME_FORMAT);
        Pair<String, String> bizDate = this.caclBizDate(condi, effectFetchDate);
        String beginBizDate = (String)bizDate.getFirst();
        String endBizDate = (String)bizDate.getSecond();
        StringBuilder externalSelectSql = new StringBuilder();
        StringBuilder assistSelectSql = new StringBuilder();
        StringBuilder externalGroupSql = new StringBuilder();
        StringBuilder externalAssistSql = new StringBuilder();
        StringBuilder docFreeSql = new StringBuilder();
        StringBuilder assistJoinSql = new StringBuilder();
        String mainDocFreeTable = null;
        HashSet<String> docFreeSet = new HashSet<String>(3);
        for (AssistMappingBO assistMapping : assistMappingList) {
            assistSelectSql.append(String.format(",%1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            externalSelectSql.append(String.format(",ASSI.%1$s AS %1$s", assistMapping.getAssistCode()));
            externalGroupSql.append(String.format(",ASSI.%1$s", assistMapping.getAssistCode()));
            assistJoinSql.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID = %3$s.F%4$d ", schemeMappingProvider.buildAssistSql(assistMapping), assistMapping.getAssistCode(), ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName(), ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableNum()));
            if (mainDocFreeTable == null) {
                mainDocFreeTable = ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName();
                docFreeSql.append(((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName());
                docFreeSet.add(((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName());
                continue;
            }
            if (docFreeSet.contains(((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName())) continue;
            docFreeSql.append(String.format(" JOIN %1$s %1$s ON %2$s.ASSID = %1$s.ASSID \r\n", ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName(), mainDocFreeTable));
            docFreeSet.add(((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName());
        }
        externalAssistSql.append(String.format("         SELECT %1$s.ASSID${ASSIST_DIM_SQL}  \n", mainDocFreeTable));
        externalAssistSql.append("                      FROM ${DOCFREE_SQL}  \n");
        externalAssistSql.append("                      ${ASSIST_DIM_JOIN_SQL}  \n");
        UnitAndBookValue unitAndBookValue = OrgSqlUtil.getUnitAndBookValue((OrgMappingDTO)condi.getOrgMapping());
        Variable variable = new Variable();
        variable.put("ASSIST_DIM_SQL", assistSelectSql.toString());
        variable.put("DOCFREE_SQL", docFreeSql.toString());
        variable.put("ASSIST_DIM_JOIN_SQL", assistJoinSql.toString());
        variable.put("EXTERNAL_SELECT_SQL", externalSelectSql.toString());
        variable.put("EXTERNAL_GROUP_SQL", externalGroupSql.toString());
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        variable.put("EXTERNAL_CURRENCY_SQL", schemeMappingProvider.getCurrencySql());
        variable.put("EXTERNAL_ASSIT_SQL", externalAssistSql.toString());
        variable.put("AGING_FETCHDATE", agingfetchDate);
        variable.put("BEGIN_BIZDATE", beginBizDate);
        variable.put("END_BIZDATE", endBizDate);
        variable.put("SUBJECTFIELD", "MD_ACCTSUBJECT.CODE");
        variable.put("BOOKCODE", OrgSqlUtil.getOrgConditionSql((String)"MD_ACCTSUBJECT.BOOKCODE", (List)unitAndBookValue.getBookCodes()));
        variable.put("ORGCONDITION", !orgMappingType.equals("DEFAULT") ? OrgSqlUtil.getOrgConditionSql((String)"OORG.CODE", (List)unitAndBookValue.getUnitCodes()) : "");
        variable.put("DETAILORGJOINSQL", !orgMappingType.equals("DEFAULT") ? "JOIN ORG_ORGS OORG ON GL_VERIFYDETAIL.${ORGRELATEFIELD} = OORG.PK_ORG" : "");
        variable.put("ORGRELATEFIELD", orgMappingType.equals("BUSINESSUNIT") ? "PK_UNIT" : "PK_ORG");
        variable.put("DETAILBOOKCONDITION", !orgMappingType.equals("UNIT") ? "AND GL_VERIFYDETAIL.PK_ACCOUNTINGBOOK IN (SELECT PK_ACCOUNTINGBOOK FROM ORG_ACCOUNTINGBOOK WHERE 1=1 ${BOOKCONDITON})" : "");
        variable.put("BOOKCONDITON", OrgSqlUtil.getOrgConditionSql((String)"CODE", (List)unitAndBookValue.getBookCodes()));
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u8d26\u9f84\u4f59\u989d", (Object)condi, (String)querySql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), ModelExecuteUtil.replaceContext((String)querySql, (BalanceCondition)condi), null, (ResultSetExtractor)new FetchDataExtractor());
    }

    private Date getEffectFetchDate(AgingBalanceCondition condi) {
        if (condi.getAgingFetchType() == AgingFetchTypeEnum.NC) {
            Calendar oc = Calendar.getInstance();
            oc.setTime(DateUtils.parse((String)condi.getAgingFetchDate()));
            oc.set(6, 1);
            oc.add(6, -1);
            return oc.getTime();
        }
        return DateUtils.nextDateOf((Date)DateUtils.parse((String)condi.getAgingFetchDate()));
    }

    private Pair<String, String> caclBizDate(AgingBalanceCondition condi, Date effectFetchDate) {
        Date beginDate = null;
        Date endDate = null;
        switch (condi.getAgingPeriodType()) {
            case Y: {
                beginDate = ModelExecuteUtil.decreaseYear2Date((Date)effectFetchDate, (int)condi.getAgingEndPeriod());
                endDate = ModelExecuteUtil.decreaseYear2Date((Date)effectFetchDate, (int)condi.getAgingStartPeriod());
                break;
            }
            case M: {
                beginDate = ModelExecuteUtil.decreaseMonth2Date((Date)effectFetchDate, (int)condi.getAgingEndPeriod(), (boolean)false);
                endDate = ModelExecuteUtil.decreaseMonth2Date((Date)effectFetchDate, (int)condi.getAgingStartPeriod(), (condi.getAgingFetchType() == AgingFetchTypeEnum.NC ? 1 : 0) != 0);
                break;
            }
            default: {
                beginDate = ModelExecuteUtil.decreaseDay2Date((Date)effectFetchDate, (int)condi.getAgingEndPeriod());
                endDate = ModelExecuteUtil.decreaseDay2Date((Date)effectFetchDate, (int)condi.getAgingStartPeriod());
            }
        }
        return Pair.of((Object)DateUtils.format((Date)ModelExecuteUtil.addDays((Date)beginDate, (int)1), (String)FULL_TIME_FORMAT), (Object)ModelExecuteUtil.getLastSecondInDate((Date)endDate, (SimpleDateFormat)new SimpleDateFormat(FULL_TIME_FORMAT)));
    }
}

