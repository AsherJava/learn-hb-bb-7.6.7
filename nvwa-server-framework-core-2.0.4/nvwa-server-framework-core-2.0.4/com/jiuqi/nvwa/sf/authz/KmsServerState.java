/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.authz;

import java.util.Date;
import java.util.Objects;

public class KmsServerState {
    private String serverAddress;
    private Boolean alive;
    private Date lastUpdateTime;

    public KmsServerState(String serverAddress, Boolean alive, Date lastUpdateTime) {
        this.serverAddress = serverAddress;
        this.alive = alive;
        this.lastUpdateTime = lastUpdateTime;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        KmsServerState that = (KmsServerState)o;
        return Objects.equals(this.serverAddress, that.serverAddress);
    }

    public int hashCode() {
        return Objects.hash(this.serverAddress);
    }

    public String toString() {
        return "KmsServerState{serverAddress='" + this.serverAddress + '\'' + ", alive=" + this.alive + '}';
    }

    public Date getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getServerAddress() {
        return this.serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public Boolean getAlive() {
        return this.alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }
}

