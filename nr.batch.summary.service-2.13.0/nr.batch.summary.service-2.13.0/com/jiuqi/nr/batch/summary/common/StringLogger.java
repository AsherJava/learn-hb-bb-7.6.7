/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.utils.DateUtils
 */
package com.jiuqi.nr.batch.summary.common;

import com.jiuqi.nr.batch.summary.storage.utils.DateUtils;
import java.io.OutputStream;
import java.text.NumberFormat;

public class StringLogger {
    protected double process;
    protected String activeMessage;
    private final StringBuilder logInfo = new StringBuilder();
    private final StringBuilder logWarn = new StringBuilder();
    private final StringBuilder logError = new StringBuilder();
    protected final StringBuilder logMessage = new StringBuilder();

    public StringLogger logInfo(String msg) {
        this.logInfo.append(this.newActiveMessage(Type.INFO, msg)).append('\n');
        return this;
    }

    public String getInfoLog() {
        return this.logInfo.toString();
    }

    public StringLogger logWarn(String msg) {
        this.logWarn.append(this.newActiveMessage(Type.WARN, msg)).append('\n');
        return this;
    }

    public String getWarnLog() {
        return this.logWarn.toString();
    }

    public void logError(String msg) {
        this.logError.append(this.newActiveMessage(Type.ERROR, msg)).append('\n');
    }

    public void logError(String msg, Throwable t) {
        this.logError.append(this.newActiveMessage(Type.ERROR, msg)).append('\n');
    }

    public String getErrorLog() {
        return this.logError.toString();
    }

    public String getLogMessage() {
        return this.logMessage.toString();
    }

    public double getProcess() {
        return this.process;
    }

    public StringLogger addProcess(double inc) {
        this.process += this.process + inc;
        if (this.process > 1.0) {
            this.process = 1.0;
        }
        return this;
    }

    public String getActiveMessage() {
        return this.activeMessage;
    }

    public String getProcessPercent() {
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        percentInstance.setMaximumFractionDigits(2);
        return percentInstance.format(this.process);
    }

    public void clear() {
        this.logInfo.setLength(0);
        this.logWarn.setLength(0);
        this.logError.setLength(0);
        this.logMessage.setLength(0);
    }

    public void outputLog(OutputStream outputStream) {
    }

    protected String newActiveMessage(Type type, String message) {
        this.activeMessage = DateUtils.get_HH_mm_ss() + type.showText + message;
        this.logMessage.append(this.activeMessage).append('\n');
        return this.activeMessage;
    }

    protected static enum Type {
        INFO("[\u63d0\u793a]:"),
        WARN("[\u8b66\u544a]:"),
        ERROR("[\u9519\u8bef]:");

        public final String showText;

        private Type(String title) {
            this.showText = title;
        }
    }
}

