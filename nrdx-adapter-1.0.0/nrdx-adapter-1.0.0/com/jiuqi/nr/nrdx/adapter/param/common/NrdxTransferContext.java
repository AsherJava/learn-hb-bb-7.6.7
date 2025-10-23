/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ResItem
 */
package com.jiuqi.nr.nrdx.adapter.param.common;

import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.nr.nrdx.adapter.param.common.TransferMode;
import com.jiuqi.nr.nrdx.adapter.param.log.ILogHelper;
import java.io.InputStream;
import java.util.List;

public class NrdxTransferContext {
    private String path;
    private TransferMode mode;
    private ILogHelper log;
    private List<ResItem> resItems;
    private InputStream file;

    public NrdxTransferContext() {
    }

    public NrdxTransferContext(String path, TransferMode mode, ILogHelper log) {
        this.path = path;
        this.mode = mode;
        this.log = log;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public TransferMode getMode() {
        return this.mode;
    }

    public void setMode(TransferMode mode) {
        this.mode = mode;
    }

    public ILogHelper getLog() {
        return this.log;
    }

    public void setLog(ILogHelper log) {
        this.log = log;
    }

    public List<ResItem> getResItems() {
        return this.resItems;
    }

    public void setResItems(List<ResItem> resItems) {
        this.resItems = resItems;
    }

    public InputStream getFile() {
        return this.file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }
}

