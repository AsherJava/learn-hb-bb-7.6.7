/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.Node
 */
package com.jiuqi.nvwa.sf.adapter.spring.cloud;

import com.jiuqi.bi.core.nodekeeper.Node;
import java.io.Serializable;

public class ServiceNode
extends Node
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String machineCode;

    public ServiceNode() {
    }

    public ServiceNode(Node node) {
        this.setGroup(node.getGroup());
        this.setAlive(node.isAlive());
        this.setServiceAddress(node.getServiceAddress());
        this.setIp(node.getIp());
        this.setHttps(node.isHttps());
        this.setApplicationName(node.getApplicationName());
        this.setMachineName(node.getMachineName());
        this.setMaster(node.isMaster());
        this.setName(node.getName());
        this.setPort(node.getPort());
        this.setSnowflakeId(node.getSnowflakeId());
        this.setContextPath(node.getContextPath());
        this.setLastTimeStamp(node.getLastTimeStamp());
        this.setApplicationState(node.getApplicationState());
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getMachineCode() {
        return this.machineCode;
    }
}

