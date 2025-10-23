/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.io.template;

public interface ImportExecuteTemplateInterface {
    public void createTempTable();

    public void executeImport();

    public void swapTable();

    public void dropTempTable();

    public void dropOriginTable();

    public void afterRelevantOperation();
}

