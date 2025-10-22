/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.gcreport.aidocaudit.util;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.List;
import java.util.stream.Collectors;

public class AidocauditOrgUtil {
    private AidocauditOrgUtil() {
        throw new UnsupportedOperationException("\u8be5\u7c7b\u4e0d\u5141\u8bb8\u5b9e\u4f8b\u5316");
    }

    public static List<String> getOrgIds(Boolean isAllOrg, List<String> orgIds, String taskId, String period) {
        if (Boolean.TRUE.equals(isAllOrg)) {
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            TaskDefine taskDefine = runTimeViewController.queryTaskDefine(taskId);
            IFuncExecuteService iFuncExecuteService = (IFuncExecuteService)SpringContextUtils.getBean(IFuncExecuteService.class);
            FormSchemeDefine formSchemeDefine = iFuncExecuteService.queryFormScheme(taskId, period);
            String orgType = taskDefine.getDw().replace("@ORG", "");
            GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)new YearPeriodObject(formSchemeDefine.getKey(), period));
            List orgTree = orgCenterService.listAllOrgByParentId(null);
            orgIds = orgTree.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList());
        }
        return orgIds;
    }
}

