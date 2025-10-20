/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.utils;

public final class ProcessInfo {
    private final int exitCode;
    private final String processStdOutput;
    private final String processErrOutput;

    public ProcessInfo(int exitCode, String processStdOutput, String processErrOutput) {
        this.exitCode = exitCode;
        this.processStdOutput = processStdOutput;
        this.processErrOutput = processErrOutput;
    }

    public int getExitCode() {
        return this.exitCode;
    }

    public String getProcessStdOutput() {
        return this.processStdOutput;
    }

    public String getProcessErrOutput() {
        return this.processErrOutput;
    }
}

