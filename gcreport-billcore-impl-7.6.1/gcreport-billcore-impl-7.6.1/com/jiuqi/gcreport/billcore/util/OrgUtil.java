/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.billcore.util;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.Calendar;

public class OrgUtil {
    public static boolean hasReadAuth(String unitCode, String oppUnitCode, String orgType) {
        YearPeriodObject yp = new YearPeriodObject(Calendar.getInstance());
        if (StringUtils.isEmpty((String)orgType)) {
            orgType = "MD_ORG_CORPORATE";
        }
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO unit = instance.getOrgByCode(unitCode);
        GcOrgCacheVO oppUnit = instance.getOrgByCode(oppUnitCode);
        return null != unit || null != oppUnit;
    }

    public static String getOrgType(String tableName) {
        TableModelDefine tableDefine;
        ColumnModelDefine columnModelDefine;
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        TableModelDefine referTableDefine = dataModelService.getTableModelDefineById((columnModelDefine = dataModelService.getColumnModelDefineByCode((tableDefine = dataModelService.getTableModelDefineByCode(tableName)).getID(), "UNITCODE")).getReferTableID());
        if (null == referTableDefine || StringUtils.isEmpty((String)referTableDefine.getCode())) {
            return "MD_ORG_CORPORATE";
        }
        return referTableDefine.getCode();
    }

    public static GcOrgCenterService getOrgCenterService(String orgType, int acctYear, GcAuthorityType gcAuthorityType) {
        YearPeriodObject yp = new YearPeriodObject(Calendar.getInstance());
        if (acctYear != yp.getYear()) {
            yp = new YearPeriodObject(null, acctYear + "Y0012");
        }
        if (null == gcAuthorityType) {
            gcAuthorityType = GcAuthorityType.NONE;
        }
        return GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)gcAuthorityType, (YearPeriodObject)yp);
    }
}

