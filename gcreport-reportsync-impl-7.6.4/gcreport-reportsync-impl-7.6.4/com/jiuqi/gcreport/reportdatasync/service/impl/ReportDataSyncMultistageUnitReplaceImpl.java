/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool
 *  com.jiuqi.nr.io.service.MultistageUnitReplace
 */
package com.jiuqi.gcreport.reportdatasync.service.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool;
import com.jiuqi.nr.io.service.MultistageUnitReplace;
import org.springframework.stereotype.Service;

@Service
public class ReportDataSyncMultistageUnitReplaceImpl
implements MultistageUnitReplace {
    public String getSuperiorCode(String subordinateCode) {
        OrgToJsonVO baseOrg = GcOrgBaseTool.getInstance().getOrgByCode(subordinateCode);
        if (baseOrg == null) {
            return subordinateCode;
        }
        String gzUnitCode = (String)baseOrg.getFieldValue("GZCODE");
        return String.valueOf(StringUtils.isEmpty((String)gzUnitCode) ? subordinateCode : gzUnitCode);
    }

    public String getSuperiorOrgCode(String subOrgCode) {
        return null;
    }
}

