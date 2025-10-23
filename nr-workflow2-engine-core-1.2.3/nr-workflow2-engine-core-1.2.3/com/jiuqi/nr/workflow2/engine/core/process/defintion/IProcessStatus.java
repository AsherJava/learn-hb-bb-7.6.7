/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.defintion;

public interface IProcessStatus {
    public String getCode();

    public String getTitle();

    public String getAlias();

    public DataAccessStatus getDataAccessStatus();

    public DataReportStatus getDataReportStatus();

    public String getIcon();

    public String getColor();

    public static enum DataReportStatus {
        CONFIRMED,
        REPORTED,
        UNREPORTED;

    }

    public static enum DataAccessStatus {
        WRITEABLE,
        READONLY,
        UNREADABLE;

    }
}

