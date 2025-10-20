/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.dto.fetch.query;

public class FetchTaskInfoDTO {
    private String runnerId;
    private String instanceId;
    private String itemId;
    private boolean standaloneServer;
    private String appName;

    public String getRunnerId() {
        return this.runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    public String getInstanceId() {
        return this.instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public boolean isStandaloneServer() {
        return this.standaloneServer;
    }

    public void setStandaloneServer(boolean standaloneServer) {
        this.standaloneServer = standaloneServer;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String toString() {
        return "FetchTaskInfoDTO{runnerId='" + this.runnerId + '\'' + ", instanceId='" + this.instanceId + '\'' + ", itemId='" + this.itemId + '\'' + ", standaloneServer=" + this.standaloneServer + ", appName='" + this.appName + '\'' + '}';
    }
}

