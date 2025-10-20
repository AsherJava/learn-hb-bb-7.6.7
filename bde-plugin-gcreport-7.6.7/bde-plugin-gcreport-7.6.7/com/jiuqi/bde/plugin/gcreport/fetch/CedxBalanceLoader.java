/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.datamodel.AbstractFinBizDataModelLoader
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.common.constant.BizDataModelEnum
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.impl.define.gather.IFieldMappingSqlBuilderGather
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.gcreport.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.datamodel.AbstractFinBizDataModelLoader;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.common.constant.BizDataModelEnum;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.gcreport.BdeGcreportPluginType;
import com.jiuqi.bde.plugin.gcreport.fetch.CedxBalanceCondition;
import com.jiuqi.bde.plugin.gcreport.intf.UnitLengthPojo;
import com.jiuqi.bde.plugin.gcreport.util.GcreportPluginUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IFieldMappingSqlBuilderGather;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class CedxBalanceLoader
extends AbstractFinBizDataModelLoader<CedxBalanceCondition, FetchData> {
    @Autowired
    private BdeGcreportPluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    protected IFieldMappingSqlBuilderGather gather;
    private static final String GET_SUBJECT_ORIENT_SQL = "SELECT km.code, km.orient \n                                            FROM MD_GCSUBJECT km\n                                           WHERE km.systemid IN (SELECT t.systemid\n                                                                   FROM gc_constask t\n                                                                  WHERE (t.taskkey = '%1$s' OR \n                                                                         MANAGETASKKEYS LIKE '%%%1$s%%')\n                                                                    AND T.FROMPERIOD <= '%2$s'\n                                                                    AND T.TOPERIOD >= '%2$s')";

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public String getBizDataModelCode() {
        return BizDataModelEnum.CEDXMODEL.getCode();
    }

    public FetchData loadData(CedxBalanceCondition condi) {
        if (StringUtils.isEmpty((String)condi.getOrgMapping().getDataSchemeCode())) {
            throw new BusinessRuntimeException("\u6240\u9009\u5355\u4f4d\u672a\u914d\u7f6e\u6570\u636e\u6620\u5c04\u65b9\u6848");
        }
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider((BalanceCondition)condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        OrgMappingDTO orgMapping = condi.getOrgMapping();
        IDbSqlHandler SQL_HANDLER = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(condi.getOrgMapping().getDataSourceCode()));
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.SUBJECTCODE  AS SUBJECTCODE,  \n");
        sql.append("       A.HBCode       AS CURRENCYCODE, \n");
        sql.append("       A.ORIENT AS ORIENT  \n");
        sql.append("       ${ASSIST_FIELD_SQL}");
        sql.append("       ,SUM(A.DEBIT) - SUM(A.CREDIT)  * A.ORIENT         AS DXNC,  \n");
        sql.append("       (SUM(A.DEBIT) - SUM(A.CREDIT)) * A.ORIENT         AS DXJNC,  \n");
        sql.append("       (SUM(A.DEBIT) - SUM(A.CREDIT)) * A.ORIENT         AS DXDNC,  \n");
        sql.append("       (SUM(A.DEBIT) - SUM(A.CREDIT))                    AS DXYE,  \n");
        sql.append("       CASE WHEN A.ORIENT = 1 THEN (SUM(A.DEBIT) - SUM(A.CREDIT)) * A.ORIENT ELSE 0 END  AS DXJYH,  \n");
        sql.append("       CASE WHEN A.ORIENT = -1 THEN (SUM(A.DEBIT) - SUM(A.CREDIT)) * A.ORIENT ELSE 0 END AS DXDYH  \n");
        sql.append("  FROM ( ");
        sql.append("SELECT KM.CODE AS SUBJECTCODE,  \n");
        sql.append("       A.OFFSETCURR   AS HBCode, \n");
        sql.append("       ${EXTERNAL_SELECT_SQL}  \n");
        sql.append("       SUM(${A.DEBIT_FIELD}) AS DEBIT, \n");
        sql.append("       SUM(${A.CREDIT_FIELD}) AS CREDIT, \n");
        sql.append("       KM.ORIENT AS ORIENT \n");
        sql.append("  FROM GC_OFFSETVCHRITEM A,  \n");
        sql.append("       ${ORG_TABLE} CO,  \n");
        sql.append("       ${ORG_TABLE} OO,  \n");
        sql.append("       MD_GCSUBJECT KM,  \n");
        sql.append("       (SELECT CO.CODE AS CODE, ${ORG_CODE_BY_PARENTS} GCCODE,CO.GCPARENTS TREE\n");
        sql.append("          FROM ${ORG_TABLE} CO  \n");
        sql.append("         INNER JOIN ${ORG_TABLE} OO  \n");
        sql.append("            ON CO.DIFFUNITID = OO.CODE  \n");
        sql.append("         WHERE OO.CODE = ?  \n");
        sql.append("           ${VALID_ORG_CONDI} ) ORG \n");
        sql.append(" WHERE 1 = 1  \n");
        sql.append("   AND A.UNITID = CO.CODE  \n");
        sql.append("   AND A.OPPUNITID = OO.CODE  \n");
        sql.append("   ${VALID_ORG_CONDI} \n");
        sql.append("   AND A.SUBJECTCODE = KM.CODE AND A.SYSTEMID = KM.SYSTEMID \n");
        sql.append("   AND (CO.GCPARENTS LIKE ").append(SQL_HANDLER.concat(new String[]{"LTRIM(ORG.TREE)", "'%'"})).append(" \n");
        sql.append("       AND OO.GCPARENTS LIKE ").append(SQL_HANDLER.concat(new String[]{"LTRIM(ORG.TREE)", "'%'"})).append(" \n");
        sql.append("       AND ((CO.PARENTCODE = ORG.CODE AND OO.PARENTCODE = ORG.CODE) OR  \n");
        sql.append("       CO.GCPARENTS = ORG.TREE OR OO.GCPARENTS = ORG.TREE OR  \n");
        sql.append("       (CO.PARENTCODE <> OO.PARENTCODE AND  \n");
        sql.append("       SUBSTR(CO.GCPARENTS, 1, LENGTH(ORG.TREE) + LENGTH(ORG.GCCODE) + 1) <>  \n");
        sql.append("       SUBSTR(OO.GCPARENTS, 1, LENGTH(ORG.TREE) + LENGTH(ORG.GCCODE) + 1))))  \n");
        sql.append("   AND A.DATATIME = '${PERIODSCHEME}'  \n");
        sql.append("   AND A.OFFSETCURR = '${CURRENCY_VAL}'  ").append("\n");
        sql.append("   ${TASKID_CONDI}  \n");
        sql.append("   ${OTHER_ENTITY_CONDI}  \n");
        sql.append("   AND A.DISABLEFLAG <> 1  \n");
        sql.append(" GROUP BY KM.CODE, A.OFFSETCURR, \n");
        sql.append("   ${EXTERNAL_GROUP_SQL} KM.ORIENT  \n");
        sql.append("     ) A ");
        sql.append(" GROUP BY A.SUBJECTCODE, A.HBCode, A.ORIENT${ASSIST_GROUP_SQL} \n");
        Variable variable = new Variable();
        StringBuilder externalSelectSql = new StringBuilder();
        StringBuilder externalGroupSql = new StringBuilder();
        FieldMappingDefineDTO newDefineItem = new FieldMappingDefineDTO();
        for (AssistMappingBO assistMapping : assistMappingList) {
            externalSelectSql.append(String.format("A.%1$s AS %2$s,", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            externalGroupSql.append(String.format("A.%1$s,", assistMapping.getAccountAssistCode()));
        }
        variable.put("EXTERNAL_SELECT_SQL", externalSelectSql.toString());
        variable.put("EXTERNAL_GROUP_SQL", externalGroupSql.toString());
        variable.put("PERIODSCHEME", condi.getPeriodScheme());
        StringBuilder assistFieldSql = new StringBuilder();
        StringBuilder assistGroupSql = new StringBuilder();
        for (Dimension assType : condi.getAssTypeList()) {
            assistFieldSql.append(String.format(",A.%1$s AS %1$s \n", assType.getDimCode()));
            assistGroupSql.append(String.format(", A.%1$s", assType.getDimCode()));
        }
        variable.put("ASSIST_FIELD_SQL", assistFieldSql.toString());
        variable.put("ASSIST_GROUP_SQL", assistGroupSql.toString().toString());
        this.buildTaskAndOtherCondition(SQL_HANDLER, condi, variable);
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u5dee\u989d\u62b5\u9500\u4f59\u989d", (Object)new Object[]{condi}, (String)querySql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), querySql, new Object[]{orgMapping.getAcctOrgCode()}, (ResultSetExtractor)new FetchDataExtractor());
    }

    private List<String> querySystemId(CedxBalanceCondition condi) {
        if (StringUtils.isEmpty((String)condi.getTaskId())) {
            return new ArrayList<String>();
        }
        ArrayList<String> systemIdList = new ArrayList<String>();
        String taskId = condi.getTaskId();
        String periodScheme = condi.getPeriodScheme();
        String querySystemIdSql = String.format("SELECT systemid\n                     FROM GC_CONSTASK T\n                    WHERE (t.taskkey = '%1$s'\n                       OR MANAGETASKKEYS like '%%%1$s%%' )\n                      AND T.FROMPERIOD <= '%2$s'\n                      AND T.TOPERIOD >= '%2$s'", taskId, periodScheme);
        FetchData systemIdQueryDatas = (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), querySystemIdSql, null, (ResultSetExtractor)new FetchDataExtractor());
        if (systemIdQueryDatas.getRowDatas().size() <= 0) {
            return new ArrayList<String>();
        }
        systemIdQueryDatas.getRowDatas().stream().map(rowData -> rowData[0].toString()).forEach(systemIdList::add);
        return systemIdList;
    }

    private void buildTaskAndOtherCondition(IDbSqlHandler sqlHandler, CedxBalanceCondition condi, Variable variable) {
        String taskIdCondi;
        List<String> systemIdList = this.querySystemId(condi);
        int systemIdSize = systemIdList.size();
        if (systemIdSize <= 0) {
            taskIdCondi = "";
        } else if (systemIdSize == 1) {
            taskIdCondi = String.format(" AND A.systemid = '%1$s'", systemIdList.get(0));
        } else {
            List quotedSystemIdList = systemIdList.stream().map(id -> "'" + id + "'").collect(Collectors.toList());
            String systemIdsStr = String.join((CharSequence)",", quotedSystemIdList);
            taskIdCondi = String.format("AND A.systemid in (%1$s)", systemIdsStr);
        }
        String currencyVal = "CNY";
        String creditField = sqlHandler.nullToValue("A.OFFSET_CREDIT", "0");
        String debitField = sqlHandler.nullToValue("A.OFFSET_DEBIT", "0");
        String orgTable = "MD_ORG_CORPORATE";
        String otherEntityCondition = "";
        Map<String, String> otherEntity = condi.getOtherEntity();
        if (otherEntity.size() > 0) {
            HashSet<String> otherKeySet = new HashSet<String>();
            for (String string : otherEntity.keySet()) {
                otherKeySet.add(string.toUpperCase());
            }
            for (Map.Entry entry : otherEntity.entrySet()) {
                if ("taskid".equalsIgnoreCase((String)entry.getKey()) || "periodScheme".equalsIgnoreCase((String)entry.getKey())) continue;
                if ("MD_CURRENCY".equalsIgnoreCase((String)entry.getKey())) {
                    currencyVal = (String)entry.getValue();
                    continue;
                }
                if ("ORGTYPE".equalsIgnoreCase((String)entry.getKey())) {
                    otherEntityCondition = otherEntityCondition + String.format(" and A.MD_GCORGTYPE in ('%1$s',%2$s) ", entry.getValue(), "'NONE'");
                    if ("NONE".equalsIgnoreCase((String)entry.getValue())) continue;
                    orgTable = (String)entry.getValue();
                    continue;
                }
                if ("MD_GCORGTYPE".equalsIgnoreCase((String)entry.getKey())) {
                    if (otherKeySet.contains("ORGTYPE")) continue;
                    otherEntityCondition = otherEntityCondition + String.format(" and A.MD_GCORGTYPE in ('%1$s',%2$s) ", entry.getValue(), "'NONE'");
                    if ("NONE".equalsIgnoreCase((String)entry.getValue())) continue;
                    orgTable = (String)entry.getValue();
                    continue;
                }
                otherEntityCondition = otherEntityCondition + String.format(" and A.%1$s = '%2$s' ", entry.getKey(), entry.getValue());
            }
        }
        variable.put("TASKID_CONDI", taskIdCondi);
        variable.put("OTHER_ENTITY_CONDI", otherEntityCondition);
        variable.put("ORG_TABLE", orgTable);
        variable.put("VALID_ORG_CONDI", String.format(" AND '%1$s' BETWEEN %2$s AND %3$s \n AND '%1$s' BETWEEN %4$s AND %5$s ", condi.getFetchDate(), sqlHandler.formatDate("oo.VALIDTIME", sqlHandler.hyphenDateFmt()), sqlHandler.formatDate("oo.INVALIDTIME", sqlHandler.hyphenDateFmt()), sqlHandler.formatDate("co.VALIDTIME", sqlHandler.hyphenDateFmt()), sqlHandler.formatDate("co.INVALIDTIME", sqlHandler.hyphenDateFmt())));
        variable.put("A.DEBIT_FIELD", debitField);
        variable.put("A.CREDIT_FIELD", creditField);
        variable.put("CURRENCY_VAL", currencyVal);
        UnitLengthPojo unitLengthPojo = GcreportPluginUtil.getUnitLengthPojo(condi.getOrgMapping().getDataSourceCode());
        if (unitLengthPojo.isVariableLength()) {
            variable.put("ORG_CODE_BY_PARENTS", sqlHandler.SubStr("co.GCPARENTS", sqlHandler.length("co.GCPARENTS") + "-" + (unitLengthPojo.getLength() - 1), sqlHandler.length("co.GCPARENTS")));
        } else {
            variable.put("ORG_CODE_BY_PARENTS", "co.CODE");
        }
    }

    Map<String, Integer> loadSubject(CedxBalanceCondition condi) {
        if (StringUtils.isEmpty((String)condi.getTaskId())) {
            return null;
        }
        String sql = String.format(GET_SUBJECT_ORIENT_SQL, condi.getTaskId(), condi.getPeriodScheme());
        return (Map)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), sql, null, (ResultSetExtractor)new ResultSetExtractor<Map<String, Integer>>(){

            public Map<String, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                HashMap<String, Integer> subjectOrientMap1 = new HashMap<String, Integer>(512);
                while (rs.next()) {
                    subjectOrientMap1.put(rs.getString("code"), rs.getInt("orient"));
                }
                return subjectOrientMap1;
            }
        });
    }
}

