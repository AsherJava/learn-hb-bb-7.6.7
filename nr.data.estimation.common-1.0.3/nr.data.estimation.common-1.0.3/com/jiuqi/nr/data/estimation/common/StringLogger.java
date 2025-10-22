/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.common;

import java.io.OutputStream;
import java.text.NumberFormat;

public class StringLogger {
    protected String activeMessage = "";
    protected double process;
    protected final StringBuilder logInfo = new StringBuilder();
    protected final StringBuilder logWarn = new StringBuilder();
    protected final StringBuilder logError = new StringBuilder();
    protected final StringBuilder logMessage = new StringBuilder();

    public void logInfo(String msg) {
        this.logInfo.append(this.newActiveMessage(Type.info, msg));
    }

    public void logInfo(String msg, double inc) {
        this.logInfo(msg);
        this.addProcess(inc);
    }

    public void logWarn(String msg) {
        this.logWarn.append(this.newActiveMessage(Type.warn, msg));
    }

    public void logWarn(String msg, double inc) {
        this.logWarn(msg);
        this.addProcess(inc);
    }

    public void logError(String msg) {
        this.logError.append(this.newActiveMessage(Type.error, msg));
    }

    public void logError(String msg, double inc) {
        this.logError(msg);
        this.addProcess(inc);
    }

    public void addProcess(double inc) {
        this.process += this.process + inc;
        if (this.process > 1.0) {
            this.process = 1.0;
        }
    }

    public double getProcess() {
        return this.process;
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
        this.process = 0.0;
        this.activeMessage = "";
        this.logInfo.setLength(0);
        this.logWarn.setLength(0);
        this.logError.setLength(0);
        this.logMessage.setLength(0);
    }

    public void outputLog(OutputStream outputStream) {
    }

    public String toString() {
        return this.logMessage.toString();
    }

    protected String newActiveMessage(Type type, String message) {
        this.activeMessage = "[" + this.getProcessPercent() + "]" + type.showText + message;
        this.logMessage.append(this.activeMessage).append('\n');
        return this.activeMessage;
    }

    protected static enum Type {
        info("[\u63d0\u793a]:"),
        warn("[\u8b66\u544a]:"),
        error("[\u9519\u8bef]:");

        public final String showText;

        private Type(String title) {
            this.showText = title;
        }
    }
}

