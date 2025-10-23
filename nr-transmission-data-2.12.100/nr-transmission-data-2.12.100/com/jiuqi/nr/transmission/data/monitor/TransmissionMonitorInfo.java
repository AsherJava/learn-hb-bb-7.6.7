/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.monitor;

import com.jiuqi.nr.transmission.data.dto.SyncHistoryDTO;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.monitor.TransmissionState;
import java.io.Serializable;
import java.util.Date;

public class TransmissionMonitorInfo
implements Serializable {
    private String executeKey;
    private String schemeKey;
    private String id;
    private TransmissionState state;
    private int states;
    private Double process;
    private DataImportResult result;
    private String detail;
    private Date endTime;
    private SyncHistoryDTO thisHistory;

    public String getExecuteKey() {
        return this.executeKey;
    }

    public void setExecuteKey(String executeKey) {
        this.executeKey = executeKey;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TransmissionState getState() {
        return this.state;
    }

    public void setState(TransmissionState state) {
        this.state = state;
        this.states = state.getValue();
    }

    public int getStates() {
        return this.states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public Double getProcess() {
        return this.process;
    }

    public void setProcess(Double process) {
        this.process = process;
    }

    public DataImportResult getResult() {
        return this.result;
    }

    public void setResult(DataImportResult result) {
        this.result = result;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public SyncHistoryDTO getThisHistory() {
        return this.thisHistory;
    }

    public void setThisHistory(SyncHistoryDTO thisHistory) {
        this.thisHistory = thisHistory;
    }
}

