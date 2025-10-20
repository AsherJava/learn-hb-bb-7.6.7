/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.dto.OrgMappingItem
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.vo.DataSchemeOptionVO
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeOptionService
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.bde.plugin.sap_s4.util;

import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.OrgMappingItem;
import com.jiuqi.bde.plugin.sap_s4.util.SapS4OrgMappingType;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.vo.DataSchemeOptionVO;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeOptionService;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class SapS4FetchUtil {
    private static SapS4FetchUtil fetchUtil;
    @Autowired
    private DataSchemeOptionService schemeOptionService;

    @PostConstruct
    public void init() {
        fetchUtil = this;
        SapS4FetchUtil.fetchUtil.schemeOptionService = this.schemeOptionService;
    }

    public static String getDbSchemeCode(DataSchemeDTO dataScheme) {
        if (dataScheme == null || CollectionUtils.isEmpty((Collection)dataScheme.getOptions())) {
            return null;
        }
        return dataScheme.getOptions().stream().filter(e -> e.getCode().equals("sapS4DbScheme")).findFirst().map(DataSchemeOptionVO::getOptionValue).orElse(null);
    }

    public static String buildUnitSql(String bukrsTableField, SapS4OrgMappingType orgMappingType, OrgMappingDTO orgMapping) {
        if (SapS4OrgMappingType.DEFAULT != orgMappingType) {
            return "";
        }
        if (CollectionUtils.isEmpty((Collection)orgMapping.getOrgMappingItems())) {
            return String.format("  AND %1$s = '${UNITCODE}'  \n", bukrsTableField);
        }
        ArrayList<String> orgCodeList = new ArrayList<String>(orgMapping.getOrgMappingItems().size());
        for (OrgMappingItem orgMappingItem : orgMapping.getOrgMappingItems()) {
            if (StringUtils.isEmpty((String)orgMappingItem.getAcctOrgCode())) continue;
            orgCodeList.add(orgMappingItem.getAcctOrgCode());
        }
        if (CollectionUtils.isEmpty(orgCodeList)) {
            return String.format("  AND %1$s = '${UNITCODE}'  \n", bukrsTableField);
        }
        return " AND " + SqlBuildUtil.getStrInCondi((String)bukrsTableField, orgCodeList);
    }

    public static String buildAssistSql(String bukrsTableField, String kostlTableField, SapS4OrgMappingType orgMappingType, OrgMappingDTO orgMapping) {
        if (SapS4OrgMappingType.KOSTL != orgMappingType) {
            return "";
        }
        if (StringUtils.isEmpty((String)orgMapping.getAssistCode()) && CollectionUtils.isEmpty((Collection)orgMapping.getOrgMappingItems())) {
            throw new BusinessRuntimeException(String.format("\u6570\u636e\u6620\u5c04\u65b9\u6848\u3010%1$s\u3011\u542f\u7528\u6309\u6210\u672c\u4e2d\u5fc3\u6620\u5c04\uff0c\u4f46\u662f\u62a5\u8868\u5355\u4f4d\u3010%2$s\u3011\u672a\u914d\u7f6e\u6210\u672c\u4e2d\u5fc3\u4ee3\u7801\uff0c\u8bf7\u68c0\u67e5\u5355\u4f4d\u6620\u5c04\u914d\u7f6e", orgMapping.getDataSchemeCode(), orgMapping.getReportOrgCode()));
        }
        if (CollectionUtils.isEmpty((Collection)orgMapping.getOrgMappingItems())) {
            return String.format("  AND %1$s = '${UNITCODE}' \n  AND %2$s = '%3$s' \n ", bukrsTableField, kostlTableField, orgMapping.getAssistCode());
        }
        ArrayList<String> assistCodeList = new ArrayList<String>(orgMapping.getOrgMappingItems().size());
        for (OrgMappingItem orgMappingItem : orgMapping.getOrgMappingItems()) {
            if (StringUtils.isEmpty((String)orgMappingItem.getAssistCode())) continue;
            assistCodeList.add(orgMappingItem.getAssistCode());
        }
        if (CollectionUtils.isEmpty(assistCodeList)) {
            return String.format("  AND %1$s = '${UNITCODE}' \n    AND %2$s = '%3$s' \n ", bukrsTableField, kostlTableField, orgMapping.getAssistCode());
        }
        return "  AND " + bukrsTableField + " = '${UNITCODE}' \n  AND " + SqlBuildUtil.getStrInCondi((String)kostlTableField, assistCodeList);
    }
}

