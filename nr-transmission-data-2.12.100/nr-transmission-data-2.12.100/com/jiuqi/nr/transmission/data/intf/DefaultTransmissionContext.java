/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.transmission.data.intf;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.transmission.data.api.IExecuteParam;
import com.jiuqi.nr.transmission.data.auth.FormAuthJudge;
import com.jiuqi.nr.transmission.data.auth.IFormAuthJudge;
import com.jiuqi.nr.transmission.data.common.TransmissionExportType;
import com.jiuqi.nr.transmission.data.intf.ContextExpendParam;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.intf.MappingImportParam;
import com.jiuqi.nr.transmission.data.intf.MappingParam;
import com.jiuqi.nr.transmission.data.log.ILogHelper;
import com.jiuqi.nr.transmission.data.log.LogHelper;
import com.jiuqi.nr.transmission.data.var.ITransmissionContext;

public class DefaultTransmissionContext
implements ITransmissionContext {
    private String serveKey;
    private IExecuteParam executeParam;
    private final LogHelper logHelper;
    private final IFormAuthJudge formAuthJudge;
    private AsyncTaskMonitor transmissionMonitor;
    private DataImportResult dataImportResult = new DataImportResult();
    private String fmdmForm;
    private ContextExpendParam contextExpendParam = new ContextExpendParam();
    private MappingParam mappingParam;
    private MappingImportParam mappingImportParam;
    private TransmissionExportType transmissionExportType;

    public DefaultTransmissionContext() {
        this.logHelper = new LogHelper();
        this.formAuthJudge = new FormAuthJudge(null);
    }

    public DefaultTransmissionContext(String serveKey, IExecuteParam executeParam) {
        this.serveKey = serveKey;
        this.executeParam = executeParam;
        this.logHelper = new LogHelper();
        this.formAuthJudge = new FormAuthJudge(null);
    }

    @Override
    public String getServeKey() {
        return this.serveKey;
    }

    public void setServeKey(String serveKey) {
        this.serveKey = serveKey;
    }

    @Override
    public IExecuteParam getExecuteParam() {
        return this.executeParam;
    }

    public void setExecuteParam(IExecuteParam executeParam) {
        this.executeParam = executeParam;
    }

    @Override
    public ILogHelper getLogHelper() {
        return this.logHelper;
    }

    @Override
    public IFormAuthJudge getFormAuthJudge() {
        return this.formAuthJudge;
    }

    @Override
    public AsyncTaskMonitor getTransmissionMonitor() {
        return this.transmissionMonitor;
    }

    public void setTransmissionMonitor(AsyncTaskMonitor transmissionMonitor) {
        this.transmissionMonitor = transmissionMonitor;
    }

    @Override
    public DataImportResult getDataImportResult() {
        return this.dataImportResult;
    }

    public void setDataImportResult(DataImportResult dataImportResult) {
        this.dataImportResult = dataImportResult;
    }

    @Override
    public String getFmdmForm() {
        return this.fmdmForm;
    }

    public void setFmdmForm(String fmdmForm) {
        this.fmdmForm = fmdmForm;
    }

    @Override
    public ContextExpendParam getContextExpendParam() {
        return this.contextExpendParam;
    }

    public void setContextExpendParam(ContextExpendParam contextExpendParam) {
        this.contextExpendParam = contextExpendParam;
    }

    @Override
    public MappingParam getMappingParam() {
        return this.mappingParam;
    }

    public void setMappingParam(MappingParam mappingParam) {
        this.mappingParam = mappingParam;
    }

    @Override
    public MappingImportParam getMappingImportParam() {
        return this.mappingImportParam;
    }

    public void setMappingImportParam(MappingImportParam mappingImportParam) {
        this.mappingImportParam = mappingImportParam;
    }

    @Override
    public TransmissionExportType getTransmissionExportType() {
        return this.transmissionExportType;
    }

    public void setTransmissionExportType(TransmissionExportType transmissionExportType) {
        this.transmissionExportType = transmissionExportType;
    }
}

