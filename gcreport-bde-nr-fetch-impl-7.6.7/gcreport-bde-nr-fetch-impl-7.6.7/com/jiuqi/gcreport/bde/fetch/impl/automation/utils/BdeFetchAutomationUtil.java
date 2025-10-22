/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.gcreport.bde.fetch.impl.automation.utils;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetch.impl.automation.intf.FetchAutomationParameterDTO;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Collection;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BdeFetchAutomationUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdeFetchAutomationUtil.class);
    public static final String FN_AUTOMATION_FETCH_TASK_NAME = "AUTOMATION_FETCH";

    public static EfdcInfo buildEfdcInfoByParam(FetchAutomationParameterDTO parameterPojo) {
        EfdcInfo efdcInfo = new EfdcInfo();
        efdcInfo.setAccount(parameterPojo.isAccount());
        efdcInfo.setContainsUnbVou(parameterPojo.isIncludeUncharged());
        efdcInfo.setFormKey(parameterPojo.getFormKey());
        efdcInfo.setFormSchemeKey(parameterPojo.getSchemeKey());
        efdcInfo.setTaskKey(parameterPojo.getTaskKey());
        efdcInfo.setVariableMap(parameterPojo.getVariableMap());
        if (StringUtils.isEmpty((String)parameterPojo.getCurrency())) {
            OrgDataClient orgDataClient = (OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class);
            OrgDTO orgDataCondi = new OrgDTO();
            orgDataCondi.setCategoryname(parameterPojo.getOrgType());
            orgDataCondi.setCode(parameterPojo.getUnitCode());
            orgDataCondi.setAuthType(OrgDataOption.AuthType.NONE);
            PageVO pageVo = orgDataClient.list(orgDataCondi);
            Assert.isNotEmpty((Collection)pageVo.getRows(), (String)String.format("\u7ec4\u7ec7\u673a\u6784\u3010%1$s\u3011\u3010%2$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e", parameterPojo.getUnitCode(), parameterPojo.getOrgType()), (Object[])new Object[0]);
            String currencyCode = StringUtils.toViewString((Object)((OrgDO)pageVo.getRows().get(0)).getValueOf("CURRENCYID"));
            if (StringUtils.isEmpty((String)currencyCode)) {
                LOGGER.warn("\u7ec4\u7ec7\u673a\u6784\u3010{}\u3011\u3010{}\u3011\u83b7\u53d6\u5230\u7684\u5e01\u79cd\u4e3a\u7a7a,\u4f7f\u7528\u9ed8\u8ba4\u5e01\u79cdCNY", (Object)parameterPojo.getUnitCode(), (Object)parameterPojo.getOrgType());
                currencyCode = "CNY";
            }
            parameterPojo.setCurrency(currencyCode);
        }
        String adjustCode = null;
        if (DimensionUtils.isExistAdjust((String)parameterPojo.getTaskKey())) {
            adjustCode = "0";
        }
        Map dimensionSet = DimensionUtils.buildDimensionMap((String)parameterPojo.getTaskKey(), (String)parameterPojo.getCurrency(), (String)parameterPojo.getDataTime(), (String)parameterPojo.getOrgType(), (String)parameterPojo.getUnitCode(), (String)adjustCode);
        dimensionSet.putAll(parameterPojo.getDimensionSet());
        efdcInfo.setDimensionSet(dimensionSet);
        return efdcInfo;
    }
}

