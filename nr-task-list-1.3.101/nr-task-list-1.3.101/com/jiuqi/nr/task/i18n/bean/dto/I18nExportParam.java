/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 */
package com.jiuqi.nr.task.i18n.bean.dto;

import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class I18nExportParam {
    private ZipOutputStream zipOut;
    private DesignTaskDefine task;
    private ZipEntry parentEntry;

    public I18nExportParam() {
    }

    public I18nExportParam(ZipOutputStream zipOut, DesignTaskDefine task, ZipEntry parentEntry) {
        this.zipOut = zipOut;
        this.task = task;
        this.parentEntry = parentEntry;
    }

    public ZipOutputStream getZipOut() {
        return this.zipOut;
    }

    public void setZipOut(ZipOutputStream zipOut) {
        this.zipOut = zipOut;
    }

    public DesignTaskDefine getTask() {
        return this.task;
    }

    public void setTask(DesignTaskDefine task) {
        this.task = task;
    }

    public ZipEntry getParentEntry() {
        return this.parentEntry;
    }

    public void setParentEntry(ZipEntry parentEntry) {
        this.parentEntry = parentEntry;
    }
}

