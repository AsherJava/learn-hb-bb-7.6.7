/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.nodekeeper;

import com.jiuqi.bi.core.nodekeeper.ServiceNodeState;
import java.io.Serializable;

public class Node
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String machineName;
    private String ip;
    private boolean isMaster;
    private long lastTimeStamp;
    private boolean isAlive = true;
    private String serviceAddress;
    private String port;
    private int snowflakeId = -1;
    private String contextPath;
    private boolean isHttps;
    private String applicationName;
    private String machineCode;
    private String group;
    private Long timeDiff;
    private ServiceNodeState applicationState = ServiceNodeState.STOP;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMachineName() {
        return this.machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isMaster() {
        return this.isMaster;
    }

    public void setMaster(boolean isMaster) {
        this.isMaster = isMaster;
    }

    public long getLastTimeStamp() {
        return this.lastTimeStamp;
    }

    public void setLastTimeStamp(long lastTimeStamp) {
        this.lastTimeStamp = lastTimeStamp;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public String getServiceAddress() {
        return this.serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getSnowflakeId() {
        return this.snowflakeId;
    }

    public void setSnowflakeId(int snowflakeId) {
        this.snowflakeId = snowflakeId;
    }

    public String getContextPath() {
        return this.contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public boolean isHttps() {
        return this.isHttps;
    }

    public void setHttps(boolean isHttps) {
        this.isHttps = isHttps;
    }

    public String getApplicationName() {
        return this.applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return this.group;
    }

    public ServiceNodeState getApplicationState() {
        return this.applicationState;
    }

    public void setApplicationState(ServiceNodeState applicationState) {
        this.applicationState = applicationState;
    }

    public String getMachineCode() {
        return this.machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public Long getTimeDiff() {
        return this.timeDiff;
    }

    public void setTimeDiff(Long timeDiff) {
        this.timeDiff = timeDiff;
    }
}

