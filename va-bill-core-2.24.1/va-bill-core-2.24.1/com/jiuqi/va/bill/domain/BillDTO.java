/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.PageDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.domain;

import com.jiuqi.va.mapper.domain.PageDTO;
import com.jiuqi.va.mapper.domain.TenantDO;

public class BillDTO
extends TenantDO
implements PageDTO {
    private static final long serialVersionUID = -7600034376799864858L;
    private String defineCode;
    private String tableName;
    private String billcode;
    private String userCode;
    private String viewName;
    private long defineVer;
    private String triggerOrigin;
    private Object define;
    private boolean pagination;
    private int offset;
    private int limit;
    private String sort;
    private String order;
    private boolean useInc;

    public boolean isPagination() {
        return this.pagination;
    }

    public void setPagination(boolean pagination) {
        this.pagination = pagination;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getUserCode() {
        return this.userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getBillcode() {
        return this.billcode;
    }

    public void setBillcode(String billcode) {
        this.billcode = billcode;
    }

    public String getDefineCode() {
        return this.defineCode;
    }

    public void setDefineCode(String defineCode) {
        this.defineCode = defineCode;
    }

    public String getViewName() {
        return this.viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public long getDefineVer() {
        return this.defineVer;
    }

    public void setDefineVer(long defineVer) {
        this.defineVer = defineVer;
    }

    public String getTriggerOrigin() {
        return this.triggerOrigin;
    }

    public void setTriggerOrigin(String triggerOrigin) {
        this.triggerOrigin = triggerOrigin;
    }

    public Object getDefine() {
        return this.define;
    }

    public void setDefine(Object define) {
        this.define = define;
    }

    public boolean isUseInc() {
        return this.useInc;
    }

    public void setUseInc(boolean useInc) {
        this.useInc = useInc;
    }
}

