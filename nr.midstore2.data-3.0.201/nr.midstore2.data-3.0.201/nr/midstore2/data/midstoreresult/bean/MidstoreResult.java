/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package nr.midstore2.data.midstoreresult.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class MidstoreResult {
    private String key;
    private String user;
    private String exchangeMode;
    private String task;
    private String midstore;
    private String period;
    private int unit;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date executeTime;
    private String source;
    private String detail;
    private String status;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getExecuteTime() {
        return this.executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public String getExchangeMode() {
        return this.exchangeMode;
    }

    public void setExchangeMode(String exchangeMode) {
        this.exchangeMode = exchangeMode;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getMidstore() {
        return this.midstore;
    }

    public void setMidstore(String midstore) {
        this.midstore = midstore;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getUnit() {
        return this.unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

