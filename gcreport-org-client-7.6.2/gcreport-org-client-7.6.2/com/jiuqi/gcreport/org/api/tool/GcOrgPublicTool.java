/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.tool;

import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterFactory;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.vo.tool.OrgCenterParam;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.Calendar;

public class GcOrgPublicTool {
    private static GcOrgCenterFactory factory;

    public static void setFactory(GcOrgCenterFactory factory) {
        GcOrgPublicTool.factory = factory;
    }

    public static GcOrgCenterService getInstance() {
        return GcOrgPublicTool.getInstance(null);
    }

    public static GcOrgCenterService getInstance(String orgType) {
        return GcOrgPublicTool.getInstance(orgType, GcAuthorityType.ACCESS);
    }

    public static GcOrgCenterService getInstance(String orgType, GcAuthorityType authType) {
        return GcOrgPublicTool.getInstance(orgType, authType, new YearPeriodObject(Calendar.getInstance()));
    }

    public static GcOrgCenterService getInstance(String orgType, GcAuthorityType authType, YearPeriodObject yp) {
        return factory.getInstance(new OrgCenterParam(orgType, yp, authType));
    }

    public static GcOrgCenterService getInstance(String orgType, GcAuthorityType authType, YearPeriodDO yp) {
        return factory.getInstance(new OrgCenterParam(orgType, yp, authType));
    }
}

