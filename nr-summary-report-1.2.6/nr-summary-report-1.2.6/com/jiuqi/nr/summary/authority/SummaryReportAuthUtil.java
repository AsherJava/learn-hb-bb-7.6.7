/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.authority.resource.ResourceGroupItem
 *  com.jiuqi.nvwa.authority.resource.ResourceItem
 */
package com.jiuqi.nr.summary.authority;

import com.jiuqi.nr.summary.api.SummaryReport;
import com.jiuqi.nr.summary.api.SummarySolution;
import com.jiuqi.nr.summary.api.SummarySolutionGroup;
import com.jiuqi.nvwa.authority.resource.ResourceGroupItem;
import com.jiuqi.nvwa.authority.resource.ResourceItem;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class SummaryReportAuthUtil {
    public static String toResourceId(SummarySolutionGroup solutionGroup) {
        return SummaryReportAuthUtil.toResourceIdForGroup(solutionGroup.getKey());
    }

    public static String toResourceId(SummarySolution solution) {
        return SummaryReportAuthUtil.toResourceIdForSolution(solution.getKey());
    }

    public static String toResourceId(SummaryReport report) {
        return SummaryReportAuthUtil.toResourceIdForReport(report.getKey());
    }

    public static String toResourceIdForGroup(String key) {
        if (!StringUtils.hasLength(key)) {
            return null;
        }
        return "SR_G_" + key;
    }

    public static String toResourceIdForSolution(String key) {
        if (!StringUtils.hasLength(key)) {
            return null;
        }
        return "SR_S_" + key;
    }

    public static String toResourceIdForReport(String key) {
        if (!StringUtils.hasLength(key)) {
            return null;
        }
        return "SR_R_" + key;
    }

    public static String toObjectId(String resourceId) {
        if (!StringUtils.hasLength(resourceId)) {
            return null;
        }
        if (SummaryReportAuthUtil.isGroup(resourceId)) {
            return resourceId.substring("SR_G_".length());
        }
        if (SummaryReportAuthUtil.isSolution(resourceId)) {
            return resourceId.substring("SR_S_".length());
        }
        if (SummaryReportAuthUtil.isReport(resourceId)) {
            return resourceId.substring("SR_R_".length());
        }
        return resourceId;
    }

    public static boolean isGroup(String resourceId) {
        if (!StringUtils.hasLength(resourceId)) {
            return false;
        }
        return resourceId.startsWith("SR_G_");
    }

    public static boolean isSolution(String resourceId) {
        if (!StringUtils.hasLength(resourceId)) {
            return false;
        }
        return resourceId.startsWith("SR_S_");
    }

    public static boolean isReport(String resourceId) {
        if (!StringUtils.hasLength(resourceId)) {
            return false;
        }
        return resourceId.startsWith("SR_R_");
    }

    public static ResourceGroupItem createResource(SummarySolutionGroup solutionGroup) {
        if (ObjectUtils.isEmpty(solutionGroup)) {
            return null;
        }
        String resourceId = SummaryReportAuthUtil.toResourceId(solutionGroup);
        ResourceGroupItem item = ResourceGroupItem.createResourceGroupItem((String)resourceId, (String)solutionGroup.getTitle(), (boolean)true);
        return item;
    }

    public static ResourceItem createResource(SummarySolution solution) {
        if (ObjectUtils.isEmpty(solution)) {
            return null;
        }
        String resourceId = SummaryReportAuthUtil.toResourceId(solution);
        ResourceGroupItem item = ResourceGroupItem.createResourceGroupItem((String)resourceId, (String)solution.getTitle(), (boolean)true);
        return item;
    }

    public static ResourceItem createResource(SummaryReport report) {
        if (ObjectUtils.isEmpty(report)) {
            return null;
        }
        String resourceId = SummaryReportAuthUtil.toResourceId(report);
        ResourceItem item = ResourceItem.createResourceItem((String)resourceId, (String)report.getTitle());
        return item;
    }
}

