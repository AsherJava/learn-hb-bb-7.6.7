/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.dto.OrgMappingItem
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.DataType
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeOptionDTO
 *  com.jiuqi.dc.mappingscheme.client.option.DataSchemeOptionValue
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeOptionService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  javax.annotation.PostConstruct
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.bip_flagship.util;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.OrgMappingItem;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.enums.DataType;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeOptionDTO;
import com.jiuqi.dc.mappingscheme.client.option.DataSchemeOptionValue;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeOptionService;
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
public class BipFlagShipFetchUtil {
    private static BipFlagShipFetchUtil fetchUtil;
    public static final String DEFAULT = "DEFAULT";
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private BaseDataRefDefineService baseDataDefineService;
    @Autowired
    private DataSchemeOptionService schemeOptionService;

    @PostConstruct
    public void init() {
        fetchUtil = this;
        BipFlagShipFetchUtil.fetchUtil.dataSourceService = this.dataSourceService;
        BipFlagShipFetchUtil.fetchUtil.baseDataDefineService = this.baseDataDefineService;
        BipFlagShipFetchUtil.fetchUtil.schemeOptionService = this.schemeOptionService;
    }

    public static Map<String, Integer> loadSubject(BalanceCondition condi) {
        StringBuilder query = new StringBuilder();
        query.append(" SELECT CODE, MAX(ORIENT) AS ORIENT FROM (%1$s) T WHERE 1 = 1 \n");
        query.append(BipFlagShipFetchUtil.buildUnitSql("BOOKCODE", condi.getOrgMapping()));
        query.append(" GROUP BY CODE");
        Variable variable = new Variable();
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        String lastSql = VariableParseUtil.parse((String)query.toString(), (Map)variable.getVariableMap());
        String subjectSql = ModelExecuteUtil.replaceContext((String)BipFlagShipFetchUtil.fetchUtil.baseDataDefineService.getByCode(condi.getOrgMapping().getDataSchemeCode(), "MD_ACCTSUBJECT").getAdvancedSql(), (BalanceCondition)condi);
        return (Map)BipFlagShipFetchUtil.fetchUtil.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), String.format(lastSql, subjectSql), null, (ResultSetExtractor)new ResultSetExtractor<Map<String, Integer>>(){

            public Map<String, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                HashMap<String, Integer> result = new HashMap<String, Integer>(512);
                while (rs.next()) {
                    result.put(rs.getString(1), rs.getInt(2));
                }
                return result;
            }
        });
    }

    public static String getTenant(DataSchemeDTO dataScheme) {
        Assert.isNotNull((Object)dataScheme);
        Assert.isNotEmpty((String)dataScheme.getCode());
        DataSchemeOptionDTO optionDTO = BipFlagShipFetchUtil.fetchUtil.schemeOptionService.getValueByDataSchemeCode(dataScheme.getCode(), "TENANT_CODE");
        if (optionDTO != null) {
            return optionDTO.getOptionValue().getStringValue();
        }
        return "";
    }

    public static String getPeriodUnion(Integer year, Integer period) {
        return year.toString() + "-" + String.format("%02d", period);
    }

    public static String getAdjustPeriodUnion(Integer year, String period) {
        return year.toString() + "-" + period;
    }

    public static String getTenantConditionSql(String tableName, String tenant) {
        return String.format(" AND %1$s.YTENANT_ID = '%2$s'", tableName, tenant);
    }

    public static Boolean getEnableTransTable(DataSchemeDTO dataScheme) {
        Assert.isNotNull((Object)dataScheme);
        Assert.isNotEmpty((String)dataScheme.getCode());
        DataSchemeOptionDTO optionDTO = BipFlagShipFetchUtil.fetchUtil.schemeOptionService.getValueByDataSchemeCode(dataScheme.getCode(), "BipEnableTemporaryTable");
        if (optionDTO != null) {
            return optionDTO.getOptionValue().getBooleanValue();
        }
        DataSchemeOptionValue defaultValue = new DataSchemeOptionValue(DataType.INT, (Object)"true");
        return defaultValue.getBooleanValue();
    }

    public static String buildUnitSql(String orgTableField, OrgMappingDTO orgMapping) {
        if (CollectionUtils.isEmpty((Collection)orgMapping.getOrgMappingItems())) {
            return String.format(" AND %1$s = '${BOOKCODE}'  \n", orgTableField);
        }
        ArrayList<String> orgCodeList = new ArrayList<String>(orgMapping.getOrgMappingItems().size());
        for (OrgMappingItem orgMappingItem : orgMapping.getOrgMappingItems()) {
            if (StringUtils.isEmpty((String)orgMappingItem.getAcctBookCode())) continue;
            orgCodeList.add(orgMappingItem.getAcctBookCode());
        }
        if (CollectionUtils.isEmpty(orgCodeList)) {
            return String.format(" AND %1$s = '${BOOKCODE}'  \n", orgTableField);
        }
        return " AND " + SqlBuildUtil.getStrInCondi((String)orgTableField, orgCodeList);
    }
}

