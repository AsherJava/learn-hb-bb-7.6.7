/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.dto;

public class RecordsQueryDTO {
    private boolean currenPage;
    private int status;
    private int downLoad;
    private int page;
    private int pageSize;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isCurrenPage() {
        return this.currenPage;
    }

    public void setCurrenPage(boolean currenPage) {
        this.currenPage = currenPage;
    }

    public int getDownLoad() {
        return this.downLoad;
    }

    public void setDownLoad(int downLoad) {
        this.downLoad = downLoad;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

