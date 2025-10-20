/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.autoconfigure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BudAuthComponent {
    @Value(value="${jiuqi.gmc3.auth.viewreport:false}")
    private boolean viewReport;
    @Value(value="${jiuqi.gmc3.auth.taskreport:false}")
    private boolean taskReport;
    @Value(value="${jiuqi.gmc3.auth.taskapprove:false}")
    private boolean taskApprove;
    @Value(value="${jiuqi.gmc3.auth.org:false}")
    private boolean org;

    public boolean isViewReport() {
        return this.viewReport;
    }

    public void setViewReport(boolean viewReport) {
        this.viewReport = viewReport;
    }

    public boolean isTaskReport() {
        return this.taskReport;
    }

    public void setTaskReport(boolean taskReport) {
        this.taskReport = taskReport;
    }

    public boolean isTaskApprove() {
        return this.taskApprove;
    }

    public void setTaskApprove(boolean taskApprove) {
        this.taskApprove = taskApprove;
    }

    public boolean isOrg() {
        return this.org;
    }

    public void setOrg(boolean org) {
        this.org = org;
    }
}

