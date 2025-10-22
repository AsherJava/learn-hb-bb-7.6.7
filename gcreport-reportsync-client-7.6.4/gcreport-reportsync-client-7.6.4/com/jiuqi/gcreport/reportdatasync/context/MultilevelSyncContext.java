/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.context;

import com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;

public class MultilevelSyncContext {
    protected String sn;
    protected ReportSyncFileDTO fileDTO;
    protected SyncTypeEnums type;
    protected ReportDataSyncServerInfoVO serverInfoVO;
    protected String errorLogs;

    public ReportSyncFileDTO getFileDTO() {
        return this.fileDTO;
    }

    public void setFileDTO(ReportSyncFileDTO fileDTO) {
        this.fileDTO = fileDTO;
    }

    public SyncTypeEnums getType() {
        return this.type;
    }

    public void setType(SyncTypeEnums type) {
        this.type = type;
    }

    public ReportDataSyncServerInfoVO getServerInfoVO() {
        return this.serverInfoVO;
    }

    public void setServerInfoVO(ReportDataSyncServerInfoVO serverInfoVO) {
        this.serverInfoVO = serverInfoVO;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getErrorLogs() {
        return this.errorLogs;
    }

    public void setErrorLogs(String errorLogs) {
        this.errorLogs = errorLogs;
    }
}

