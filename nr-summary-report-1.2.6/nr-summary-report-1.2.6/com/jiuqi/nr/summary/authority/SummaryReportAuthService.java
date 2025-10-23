/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService
 */
package com.jiuqi.nr.summary.authority;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.summary.authority.SummaryReportAuthUtil;
import com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SummaryReportAuthService {
    @Autowired
    private DefaultAuthQueryService defaultAuthQueryService;

    public boolean canReadGroup(String key) {
        if (!StringUtils.hasLength(key)) {
            return false;
        }
        if ("_summary_solution_root_group_".equals(key)) {
            return true;
        }
        return this.canRead(SummaryReportAuthUtil.toResourceIdForGroup(key));
    }

    public boolean canReadSolution(String key) {
        if (!StringUtils.hasLength(key)) {
            return false;
        }
        return this.canRead(SummaryReportAuthUtil.toResourceIdForSolution(key));
    }

    public boolean canReadReport(String key) {
        if (!StringUtils.hasLength(key)) {
            return false;
        }
        return this.canRead(SummaryReportAuthUtil.toResourceIdForReport(key));
    }

    public boolean canSumGroup(String key) {
        if (!StringUtils.hasLength(key)) {
            return false;
        }
        if ("_summary_solution_root_group_".equals(key)) {
            return true;
        }
        return this.canSum(SummaryReportAuthUtil.toResourceIdForGroup(key));
    }

    public boolean canSumSolution(String key) {
        if (!StringUtils.hasLength(key)) {
            return false;
        }
        return this.canSum(SummaryReportAuthUtil.toResourceIdForSolution(key));
    }

    public boolean canSumReport(String key) {
        if (!StringUtils.hasLength(key)) {
            return false;
        }
        return this.canSum(SummaryReportAuthUtil.toResourceIdForReport(key));
    }

    public boolean canManageGroup(String key) {
        if (!StringUtils.hasLength(key)) {
            return false;
        }
        if ("_summary_solution_root_group_".equals(key)) {
            return true;
        }
        return this.canManage(SummaryReportAuthUtil.toResourceIdForGroup(key));
    }

    public boolean canManageSolution(String key) {
        if (!StringUtils.hasLength(key)) {
            return false;
        }
        return this.canManage(SummaryReportAuthUtil.toResourceIdForSolution(key));
    }

    public boolean canManageReport(String key) {
        if (!StringUtils.hasLength(key)) {
            return false;
        }
        return this.canManage(SummaryReportAuthUtil.toResourceIdForReport(key));
    }

    private boolean canRead(String resourceId) {
        return this.hasReadAuth(resourceId) || this.hasSumAuth(resourceId) || this.hasManageAuth(resourceId);
    }

    private boolean canSum(String resourceId) {
        return this.hasSumAuth(resourceId) || this.hasManageAuth(resourceId);
    }

    private boolean canManage(String resourceId) {
        return this.hasManageAuth(resourceId);
    }

    private boolean hasReadAuth(String resourceId) {
        return this.hasAuth("summaryreport_auth_resource_read", resourceId);
    }

    private boolean hasSumAuth(String resourceId) {
        return this.hasAuth("summaryreport_auth_resource_sum", resourceId);
    }

    private boolean hasManageAuth(String resourceId) {
        return this.hasAuth("summaryreport_auth_resource_manage", resourceId);
    }

    private boolean hasAuth(String privilegeId, String resourceId) {
        return this.defaultAuthQueryService.hasAuth(privilegeId, NpContextHolder.getContext().getIdentityId(), (Object)resourceId);
    }
}

