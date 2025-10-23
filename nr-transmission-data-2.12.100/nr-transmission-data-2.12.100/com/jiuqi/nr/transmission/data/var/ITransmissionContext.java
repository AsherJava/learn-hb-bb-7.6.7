/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.transmission.data.var;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.transmission.data.api.IExecuteParam;
import com.jiuqi.nr.transmission.data.auth.IFormAuthJudge;
import com.jiuqi.nr.transmission.data.common.TransmissionExportType;
import com.jiuqi.nr.transmission.data.intf.ContextExpendParam;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.intf.MappingImportParam;
import com.jiuqi.nr.transmission.data.intf.MappingParam;
import com.jiuqi.nr.transmission.data.log.ILogHelper;

public interface ITransmissionContext {
    public String getServeKey();

    public IExecuteParam getExecuteParam();

    public ILogHelper getLogHelper();

    public IFormAuthJudge getFormAuthJudge();

    public AsyncTaskMonitor getTransmissionMonitor();

    public DataImportResult getDataImportResult();

    public String getFmdmForm();

    public ContextExpendParam getContextExpendParam();

    public MappingParam getMappingParam();

    public MappingImportParam getMappingImportParam();

    public TransmissionExportType getTransmissionExportType();
}

