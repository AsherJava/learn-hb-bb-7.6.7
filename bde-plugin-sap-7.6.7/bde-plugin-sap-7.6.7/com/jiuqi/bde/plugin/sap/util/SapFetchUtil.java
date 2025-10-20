/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.dto.OrgMappingItem
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.DataType
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeOptionDTO
 *  com.jiuqi.dc.mappingscheme.client.option.DataSchemeOptionValue
 *  com.jiuqi.dc.mappingscheme.client.vo.DataSchemeOptionVO
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeOptionService
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.bde.plugin.sap.util;

import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.OrgMappingItem;
import com.jiuqi.bde.plugin.sap.util.SapOrgMappingType;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.enums.DataType;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeOptionDTO;
import com.jiuqi.dc.mappingscheme.client.option.DataSchemeOptionValue;
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
public class SapFetchUtil {
    private static SapFetchUtil fetchUtil;
    public static final String FN_MANDT = "MANDT";
    public static final String FN_KTOPL = "KTOPL";
    public static final String FN_BUKRS = "BUKRS";
    @Autowired
    private DataSchemeOptionService schemeOptionService;

    @PostConstruct
    public void init() {
        fetchUtil = this;
        SapFetchUtil.fetchUtil.schemeOptionService = this.schemeOptionService;
    }

    public static String buildUnitSql(String bukrsTableField, SapOrgMappingType orgMappingType, OrgMappingDTO orgMapping) {
        if (SapOrgMappingType.DEFAULT != orgMappingType) {
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

    public static String buildAssistSql(String bukrsTableField, String prctrTableField, SapOrgMappingType orgMappingType, OrgMappingDTO orgMapping) {
        if (SapOrgMappingType.PRCTR != orgMappingType) {
            return "";
        }
        if (StringUtils.isEmpty((String)orgMapping.getAssistCode()) && CollectionUtils.isEmpty((Collection)orgMapping.getOrgMappingItems())) {
            throw new BusinessRuntimeException(String.format("\u6570\u636e\u6620\u5c04\u65b9\u6848\u3010%1$s\u3011\u542f\u7528\u6309\u5229\u6da6\u4e2d\u5fc3\u6620\u5c04\uff0c\u4f46\u662f\u62a5\u8868\u5355\u4f4d\u3010%2$s\u3011\u672a\u914d\u7f6e\u5229\u6da6\u4e2d\u5fc3\u4ee3\u7801\uff0c\u8bf7\u68c0\u67e5\u5355\u4f4d\u6620\u5c04\u914d\u7f6e", orgMapping.getDataSchemeCode(), orgMapping.getReportOrgCode()));
        }
        if (CollectionUtils.isEmpty((Collection)orgMapping.getOrgMappingItems())) {
            return String.format("  AND %1$s = '${UNITCODE}' \n  AND %2$s = '%3$s' \n ", bukrsTableField, prctrTableField, orgMapping.getAssistCode());
        }
        ArrayList<String> assistCodeList = new ArrayList<String>(orgMapping.getOrgMappingItems().size());
        for (OrgMappingItem orgMappingItem : orgMapping.getOrgMappingItems()) {
            if (StringUtils.isEmpty((String)orgMappingItem.getAssistCode())) continue;
            assistCodeList.add(orgMappingItem.getAssistCode());
        }
        if (CollectionUtils.isEmpty(assistCodeList)) {
            return String.format("  AND %1$s = '${UNITCODE}' \n    AND %2$s = '%3$s' \n ", bukrsTableField, prctrTableField, orgMapping.getAssistCode());
        }
        return "  AND " + bukrsTableField + " = '${UNITCODE}' \n  AND " + SqlBuildUtil.getStrInCondi((String)prctrTableField, assistCodeList);
    }

    public static Boolean isIncludePlData(String dataSchemeCode) {
        Assert.isNotEmpty((String)dataSchemeCode);
        DataSchemeOptionDTO optionDTO = SapFetchUtil.fetchUtil.schemeOptionService.getValueByDataSchemeCode(dataSchemeCode, "sapIncludePlData");
        if (optionDTO != null) {
            return optionDTO.getOptionValue().getBooleanValue();
        }
        DataSchemeOptionValue defaultValue = new DataSchemeOptionValue(DataType.INT, (Object)"sapIncludePlData");
        return defaultValue.getBooleanValue();
    }

    public static String getDbSchemeCode(DataSchemeDTO dataScheme) {
        if (dataScheme == null || CollectionUtils.isEmpty((Collection)dataScheme.getOptions())) {
            return null;
        }
        return dataScheme.getOptions().stream().filter(e -> e.getCode().equals("sapDbScheme")).findFirst().map(DataSchemeOptionVO::getOptionValue).orElse(null);
    }
}

