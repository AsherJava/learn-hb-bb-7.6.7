/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.authority;

import com.jiuqi.bi.quickreport.authority.IReportAuthority;

public class ReportAuthorityValidator {
    private static IReportAuthority authority = new DefaultReportAuthority();

    private ReportAuthorityValidator() {
    }

    public static boolean setAuthority(IReportAuthority authority) {
        if (authority != null) {
            ReportAuthorityValidator.authority = authority;
            return true;
        }
        return false;
    }

    public static boolean canAccess(String reportGuid, String userGuid) {
        return authority.canAccess(reportGuid, userGuid);
    }

    public static boolean canExport(String reportGuid, String userGuid) {
        return authority.canExport(reportGuid, userGuid);
    }

    public static boolean canPrint(String reportGuid, String userGuid) {
        return authority.canPrint(reportGuid, userGuid);
    }

    static class DefaultReportAuthority
    implements IReportAuthority {
        DefaultReportAuthority() {
        }

        @Override
        public boolean canAccess(String reportGuid, String userGuid) {
            return true;
        }

        @Override
        public boolean canExport(String reportGuid, String userGuid) {
            return true;
        }

        @Override
        public boolean canPrint(String reportGuid, String userGuid) {
            return true;
        }
    }
}

