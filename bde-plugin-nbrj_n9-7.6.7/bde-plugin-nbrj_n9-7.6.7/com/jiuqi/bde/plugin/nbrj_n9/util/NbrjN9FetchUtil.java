/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.dto.OrgMappingItem
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  javax.annotation.PostConstruct
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nbrj_n9.util;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.OrgMappingItem;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class NbrjN9FetchUtil {
    public static final String ASS_SQL = " LEFT JOIN (%1$s) %2$s ON %2$s.ID=%3$S.%4$s";
    public static final String FN_DEFAULT_MAPPINGTYPE = "DEFAULT";
    @Autowired
    private DataSourceService dataSourceService;
    private static NbrjN9FetchUtil fetchUtil;

    @PostConstruct
    public void init() {
        fetchUtil = this;
        NbrjN9FetchUtil.fetchUtil.dataSourceService = this.dataSourceService;
    }

    public static Map<String, Integer> loadSubject(BalanceCondition condi) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n");
        query.append("      ACODE ASSUBJECTCODE,\n");
        query.append("      DC AS ORIENT\n");
        query.append("FROM\n");
        query.append("ACODE\n");
        return (Map)NbrjN9FetchUtil.fetchUtil.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), query.toString(), null, (ResultSetExtractor)new ResultSetExtractor<Map<String, Integer>>(){

            public Map<String, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                HashMap<String, Integer> result = new HashMap<String, Integer>(512);
                while (rs.next()) {
                    result.put(rs.getString(1), rs.getInt(2));
                }
                return result;
            }
        });
    }

    public static String buildUnitSql(String orgMappingType, OrgMappingDTO orgMapping, String tableName) {
        if (!FN_DEFAULT_MAPPINGTYPE.equals(orgMappingType)) {
            return String.format("  AND %1$sKEPTBCODE = '${UNITCODE}'  \n", tableName);
        }
        if (CollectionUtils.isEmpty((Collection)orgMapping.getOrgMappingItems())) {
            return String.format("  AND %1$sKEPTBCODE = '${UNITCODE}'  \n", tableName);
        }
        ArrayList<String> orgCodeList = new ArrayList<String>(orgMapping.getOrgMappingItems().size());
        for (OrgMappingItem orgMappingItem : orgMapping.getOrgMappingItems()) {
            if (StringUtils.isEmpty((String)orgMappingItem.getAcctOrgCode())) continue;
            orgCodeList.add(orgMappingItem.getAcctOrgCode());
        }
        if (CollectionUtils.isEmpty(orgCodeList)) {
            return String.format("  AND %1$sKEPTBCODE = '${UNITCODE}'  \n", tableName);
        }
        return String.format(" AND " + SqlBuildUtil.getStrInCondi((String)"%1$sKEPTBCODE", orgCodeList), tableName);
    }
}

