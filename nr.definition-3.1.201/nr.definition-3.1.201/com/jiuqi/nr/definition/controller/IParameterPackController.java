/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public interface IParameterPackController {
    public OutputStream packDesignTask(UUID var1) throws Exception;

    public OutputStream extractTaskPack(UUID var1) throws Exception;

    public void loadRuntimeTaskPack(InputStream var1) throws Exception;

    public void maintainParameterPack(UUID[] var1) throws Exception;

    public void maintainParameterPack() throws Exception;

    public void backupParameterPack(UUID var1) throws Exception;

    public void mergeToRuntime(UUID var1) throws Exception;

    public void deleteTask(UUID var1) throws Exception;

    public void crossCheck(UUID var1) throws Exception;
}

