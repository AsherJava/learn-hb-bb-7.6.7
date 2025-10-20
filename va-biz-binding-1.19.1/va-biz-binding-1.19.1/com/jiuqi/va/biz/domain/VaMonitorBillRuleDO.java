/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.va.biz.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Table(name="VA_MONITOR_BILL_RULE")
public class VaMonitorBillRuleDO
extends TenantDO {
    @Id
    private String id;
    private String hostname;
    private String definecode;
    private String ruleid;
    private String rulename;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatetime;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date mperiod;
    private int executecount;
    private long totaltime;
    private long maxtime;
    private long mintime;
    private long avgtime;
    private int period;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostname() {
        return this.hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getDefinecode() {
        return this.definecode;
    }

    public void setDefinecode(String definecode) {
        this.definecode = definecode;
    }

    public String getRulename() {
        return this.rulename;
    }

    public void setRulename(String rulename) {
        this.rulename = rulename;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getMperiod() {
        return this.mperiod;
    }

    public void setMperiod(Date mperiod) {
        this.mperiod = mperiod;
    }

    public int getExecutecount() {
        return this.executecount;
    }

    public void setExecutecount(int executecount) {
        this.executecount = executecount;
    }

    public long getTotaltime() {
        return this.totaltime;
    }

    public void setTotaltime(long totaltime) {
        this.totaltime = totaltime;
    }

    public long getMaxtime() {
        return this.maxtime;
    }

    public void setMaxtime(long maxtime) {
        this.maxtime = maxtime;
    }

    public long getMintime() {
        return this.mintime;
    }

    public void setMintime(long mintime) {
        this.mintime = mintime;
    }

    public long getAvgtime() {
        return this.avgtime;
    }

    public void setAvgtime(long avgtime) {
        this.avgtime = avgtime;
    }

    public int getPeriod() {
        return this.period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getRuleid() {
        return this.ruleid;
    }

    public void setRuleid(String ruleid) {
        this.ruleid = ruleid;
    }

    public VaMonitorBillRuleDO() {
    }

    public String toString() {
        return "VaMonitorBillRuleDO{id=" + this.id + ", hostname='" + this.hostname + '\'' + ", definecode='" + this.definecode + '\'' + ", ruleid='" + this.ruleid + '\'' + ", rulename='" + this.rulename + '\'' + ", updatetime=" + this.updatetime + ", mperiod=" + this.mperiod + ", executecount=" + this.executecount + ", totaltime=" + this.totaltime + ", maxtime=" + this.maxtime + ", mintime=" + this.mintime + ", avgtime=" + this.avgtime + ", period=" + this.period + '}';
    }

    public VaMonitorBillRuleDO(String id, String hostname, String definecode, String ruleid, String rulename, Date updatetime, Date mperiod, int executecount, long totaltime, long maxtime, long mintime, long avgtime, int period) {
        this.id = id;
        this.hostname = hostname;
        this.definecode = definecode;
        this.ruleid = ruleid;
        this.rulename = rulename;
        this.updatetime = updatetime;
        this.mperiod = mperiod;
        this.executecount = executecount;
        this.totaltime = totaltime;
        this.maxtime = maxtime;
        this.mintime = mintime;
        this.avgtime = avgtime;
        this.period = period;
    }
}

