/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.financialcheckapi.checkconfig.vo.FinancialCheckConfigVO
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils
 */
package com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.service.impl.FinancialCheckConfigServiceImpl;
import com.jiuqi.gcreport.financialcheckapi.checkconfig.vo.FinancialCheckConfigVO;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class FinancialCheckConfigUtils {
    public static FinancialCheckConfigVO getCheckConfig() {
        return ((FinancialCheckConfigServiceImpl)SpringContextUtils.getBean(FinancialCheckConfigServiceImpl.class)).query();
    }

    public static ReconciliationModeEnum getCheckWay() {
        FinancialCheckConfigVO checkConfig = FinancialCheckConfigUtils.getCheckConfig();
        if (Objects.isNull(checkConfig)) {
            throw new BusinessRuntimeException("\u5bf9\u8d26\u914d\u7f6e\u4e2d\u5fc3\u672a\u914d\u7f6e\u5bf9\u8d26\u65b9\u5f0f\uff0c\u8bf7\u5148\u914d\u7f6e\u540e\u4f7f\u7528\u3002");
        }
        String checkWay = checkConfig.getCheckWay();
        if (StringUtils.isEmpty((String)checkWay)) {
            throw new BusinessRuntimeException("\u5bf9\u8d26\u914d\u7f6e\u4e2d\u5fc3\u672a\u914d\u7f6e\u5bf9\u8d26\u65b9\u5f0f\uff0c\u8bf7\u5148\u914d\u7f6e\u540e\u4f7f\u7528\u3002");
        }
        return ReconciliationModeEnum.getEnumByCode((String)checkConfig.getCheckWay());
    }

    public static String getCheckOrgType() {
        FinancialCheckConfigVO checkConfig = FinancialCheckConfigUtils.getCheckConfig();
        if (Objects.isNull(checkConfig)) {
            return null;
        }
        String orgType = checkConfig.getOrgType();
        if (StringUtils.isEmpty((String)orgType)) {
            throw new BusinessRuntimeException("\u5bf9\u8d26\u914d\u7f6e\u4e2d\u5fc3\u672a\u914d\u7f6e\u5173\u8054\u673a\u6784\u7c7b\u578b\uff0c\u8bf7\u5148\u914d\u7f6e\u540e\u4f7f\u7528\u3002");
        }
        return orgType;
    }

    public static Set<String> getUnOffsetSubjects() {
        FinancialCheckConfigVO checkConfig = FinancialCheckConfigUtils.getCheckConfig();
        if (Objects.isNull(checkConfig) || Objects.isNull(checkConfig.getOptions()) || !checkConfig.getOptions().containsKey("unOffsetSubjects")) {
            return Collections.emptySet();
        }
        List unOffsetSubjects = (List)checkConfig.getOptions().get("unOffsetSubjects");
        return BaseDataUtils.getAllChildrenContainSelfByCodes((String)"MD_ACCTSUBJECT", (Collection)unOffsetSubjects);
    }
}

