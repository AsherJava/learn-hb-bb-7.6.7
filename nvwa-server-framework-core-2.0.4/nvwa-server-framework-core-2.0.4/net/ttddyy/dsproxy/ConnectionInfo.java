/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy;

public class ConnectionInfo {
    private String dataSourceName;
    private String connectionId;
    private int isolationLevel;
    private boolean isClosed;
    private int commitCount;
    private int rollbackCount;

    public String getDataSourceName() {
        return this.dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getConnectionId() {
        return this.connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public int getIsolationLevel() {
        return this.isolationLevel;
    }

    public void setIsolationLevel(int isolationLevel) {
        this.isolationLevel = isolationLevel;
    }

    public void incrementCommitCount() {
        ++this.commitCount;
    }

    public void incrementRollbackCount() {
        ++this.rollbackCount;
    }

    public int getCommitCount() {
        return this.commitCount;
    }

    public void setCommitCount(int commitCount) {
        this.commitCount = commitCount;
    }

    public int getRollbackCount() {
        return this.rollbackCount;
    }

    public void setRollbackCount(int rollbackCount) {
        this.rollbackCount = rollbackCount;
    }

    public boolean isClosed() {
        return this.isClosed;
    }

    public void setClosed(boolean closed) {
        this.isClosed = closed;
    }
}

