/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.analysisreport.utils;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.analysisreport.authority.AnalysisReportAuthorityProvider;
import com.jiuqi.nr.analysisreport.authority.common.AnalysisReportResourceType;
import com.jiuqi.nr.analysisreport.common.NrAnalysisErrorEnum;
import com.jiuqi.nr.definition.internal.BeanUtil;

public class CheckPermissionUtil {
    public static void checkReadPermission(String resourceKey) throws JQException {
        AnalysisReportAuthorityProvider authProvider = (AnalysisReportAuthorityProvider)BeanUtil.getBean(AnalysisReportAuthorityProvider.class);
        if (!authProvider.canReadModal(resourceKey, AnalysisReportResourceType.TEMPLATE)) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_401);
        }
    }
}

