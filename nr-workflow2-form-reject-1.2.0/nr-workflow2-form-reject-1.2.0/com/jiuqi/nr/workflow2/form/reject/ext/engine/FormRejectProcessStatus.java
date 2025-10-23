/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus$DataAccessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus$DataReportStatus
 */
package com.jiuqi.nr.workflow2.form.reject.ext.engine;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.form.reject.entity.IRejectFormRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.enumeration.FormRejectStatus;

public class FormRejectProcessStatus
implements IProcessStatus {
    private final IRejectFormRecordEntity entity;

    public FormRejectProcessStatus(IRejectFormRecordEntity entity) {
        this.entity = entity;
    }

    public String getCode() {
        return this.entity.getStatus().value;
    }

    public String getTitle() {
        return this.entity.getStatus().title;
    }

    public String getAlias() {
        return this.entity.getStatus().title;
    }

    public IProcessStatus.DataAccessStatus getDataAccessStatus() {
        if (FormRejectStatus.rejected == this.entity.getStatus()) {
            return IProcessStatus.DataAccessStatus.WRITEABLE;
        }
        return IProcessStatus.DataAccessStatus.READONLY;
    }

    public IProcessStatus.DataReportStatus getDataReportStatus() {
        return IProcessStatus.DataReportStatus.UNREPORTED;
    }

    public String getIcon() {
        return "";
    }

    public String getColor() {
        return "";
    }
}

