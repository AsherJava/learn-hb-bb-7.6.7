/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.constant.ArgumentValueEnum
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.dto.OrgMappingItem
 *  com.jiuqi.bde.common.util.ContextVariableParseUtil
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.CommonUtil
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  javax.annotation.PostConstruct
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.standard.util;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.ArgumentValueEnum;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.OrgMappingItem;
import com.jiuqi.bde.common.util.ContextVariableParseUtil;
import com.jiuqi.bde.plugin.standard.util.AssistPojo;
import com.jiuqi.bde.plugin.standard.util.ShareTypeEnum;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.CommonUtil;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class StandardFetchUtil {
    public static final String EMPTYGUID = "00000000000000000000000000000000";
    public static final String TBJC_ORGUNIT = "JC_ORGUNIT";
    public static final String TBJC_KM = "JC_KM";
    public static final String TBJC_HB = "JC_HB";
    public static final String FN_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static StandardFetchUtil fetchUtil;
    public static final String FN_DEFAULT_MAPPINGTYPE = "DEFAULT";
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private BaseDataRefDefineService baseDataDefineService;
    private static final String SUBJECT_SELECT_SQL_TMPL = "SELECT CODE, MAX(ORIENT) AS ORIENT FROM (%1$s) T WHERE 1 = 1 GROUP BY CODE ";
    private static final String TBGL_BALANCE = "ZW_KMZD";
    private static final String TBGL_ASSBALANCE = "ZW_FZHSZD";

    @PostConstruct
    public void init() {
        fetchUtil = this;
        StandardFetchUtil.fetchUtil.dataSourceService = this.dataSourceService;
        StandardFetchUtil.fetchUtil.baseDataDefineService = this.baseDataDefineService;
    }

    public static String getBaseDataSql(AssistPojo assistPojo) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.RWID AS ID,   \n");
        sql.append("       T.STDCODE AS CODE,  \n");
        sql.append("       T.STDNAME AS NAME  \n");
        sql.append("  FROM ").append(assistPojo.getTableName()).append(" T \n");
        sql.append("  LEFT JOIN JC_ORGUNIT ORG ON T.ORGID = ORG.RWID  \n");
        sql.append(" WHERE 1 = 1  \n");
        if (ShareTypeEnum.ISOLATION == assistPojo.getShareType()) {
            sql.append(" AND T.STDCODE = '#unitCode#'  \n");
        } else if (ShareTypeEnum.SHARE_ISOLATION == assistPojo.getShareType()) {
            sql.append("   AND (  \n");
            sql.append("            T.ORGID = '").append(EMPTYGUID).append("' \n");
            sql.append("         OR ORG.STDCODE = '#unitCode#' \n");
            sql.append("       )  \n");
        }
        return sql.toString();
    }

    public static Map<String, Integer> loadSubject(BalanceCondition condi) {
        String subjectSql = StandardFetchUtil.fetchUtil.baseDataDefineService.getByCode(condi.getOrgMapping().getDataSchemeCode(), "MD_ACCTSUBJECT").getAdvancedSql();
        String executeSql = StandardFetchUtil.parse(subjectSql, condi);
        return (Map)StandardFetchUtil.fetchUtil.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), String.format(SUBJECT_SELECT_SQL_TMPL, executeSql), null, (ResultSetExtractor)new ResultSetExtractor<Map<String, Integer>>(){

            public Map<String, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                HashMap<String, Integer> result = new HashMap<String, Integer>(512);
                while (rs.next()) {
                    result.put(rs.getString(1), rs.getInt(2));
                }
                return result;
            }
        });
    }

    public static String parse(String text, BalanceCondition condi) {
        return ContextVariableParseUtil.parse((String)text, StandardFetchUtil.initContextVariableMap(condi));
    }

    private static Map<String, String> initContextVariableMap(BalanceCondition condi) {
        String year = String.valueOf(condi.getAcctYear());
        String period = String.valueOf(condi.getEndPeriod());
        LinkedHashMap<String, String> precastParamMap = new LinkedHashMap<String, String>(8);
        precastParamMap.put(ContextVariableParseUtil.getVariable((String)ArgumentValueEnum.UNITCODE.getCode()), condi.getOrgMapping().getAcctOrgCode());
        precastParamMap.put(ContextVariableParseUtil.getVariable((String)ArgumentValueEnum.YEARPERIOD.getCode()), String.format("%1$s-%2$s", year, CommonUtil.lpad((String)period, (String)"0", (int)2)));
        precastParamMap.put(ContextVariableParseUtil.getVariable((String)ArgumentValueEnum.BOOKCODE.getCode()), condi.getOrgMapping().getAcctBookCode());
        precastParamMap.put(ContextVariableParseUtil.getVariable((String)ArgumentValueEnum.YEAR.getCode()), year);
        precastParamMap.put(ContextVariableParseUtil.getVariable((String)ArgumentValueEnum.PERIOD.getCode()), period);
        precastParamMap.put(ContextVariableParseUtil.getVariable((String)ArgumentValueEnum.FULLPERIOD.getCode()), CommonUtil.lpad((String)period, (String)"0", (int)2));
        precastParamMap.put(ContextVariableParseUtil.getVariable((String)ArgumentValueEnum.INCLUDEUNCHARGED.getCode()), Boolean.TRUE.equals(condi.getIncludeUncharged()) ? "1" : "0");
        return precastParamMap;
    }

    public static String getTableNameByCondi(boolean assist) {
        return assist ? TBGL_ASSBALANCE : TBGL_BALANCE;
    }

    public static String buildUnitSql(String orgMappingType, OrgMappingDTO orgMapping) {
        if (!FN_DEFAULT_MAPPINGTYPE.equals(orgMappingType)) {
            return "  AND T.ORGID = '${UNITCODE}'  \n";
        }
        if (CollectionUtils.isEmpty((Collection)orgMapping.getOrgMappingItems())) {
            return "  AND T.ORGID = '${UNITCODE}'  \n";
        }
        ArrayList<String> orgCodeList = new ArrayList<String>(orgMapping.getOrgMappingItems().size());
        for (OrgMappingItem orgMappingItem : orgMapping.getOrgMappingItems()) {
            if (StringUtils.isEmpty((String)orgMappingItem.getAcctOrgCode())) continue;
            orgCodeList.add(orgMappingItem.getAcctOrgCode());
        }
        if (CollectionUtils.isEmpty(orgCodeList)) {
            return "  AND T.ORGID = '${UNITCODE}'  \n";
        }
        return " AND " + SqlBuildUtil.getStrInCondi((String)"T.ORGID", orgCodeList);
    }

    public static String buildAssistSql(String orgMappingType, OrgMappingDTO orgMapping) {
        if (FN_DEFAULT_MAPPINGTYPE.equals(orgMappingType)) {
            return "";
        }
        if (StringUtils.isEmpty((String)orgMapping.getAssistCode())) {
            return "";
        }
        if (CollectionUtils.isEmpty((Collection)orgMapping.getOrgMappingItems())) {
            return String.format("  AND T.%1$s = '%2$s'", orgMappingType, orgMapping.getAssistCode());
        }
        ArrayList<String> assistCodeList = new ArrayList<String>(orgMapping.getOrgMappingItems().size());
        for (OrgMappingItem orgMappingItem : orgMapping.getOrgMappingItems()) {
            if (StringUtils.isEmpty((String)orgMappingItem.getAssistCode())) continue;
            assistCodeList.add(orgMappingItem.getAssistCode());
        }
        if (CollectionUtils.isEmpty(assistCodeList)) {
            return String.format("  AND T.%1$s = '%2$s'", orgMappingType, orgMapping.getAssistCode());
        }
        return " AND " + SqlBuildUtil.getStrInCondi((String)orgMappingType, assistCodeList);
    }
}

