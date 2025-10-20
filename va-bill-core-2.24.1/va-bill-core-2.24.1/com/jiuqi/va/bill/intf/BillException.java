/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 */
package com.jiuqi.va.bill.intf;

import com.jiuqi.va.biz.ruler.intf.CheckResult;
import java.util.List;
import java.util.Map;

public class BillException
extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private List<CheckResult> checkMessages;
    private Boolean closeTab;
    private int level;
    private transient Map<String, List<Map<String, Object>>> tablesData;
    private transient Map<String, List<List<Object>>> frontTablesData;
    private boolean invisible;

    public BillException(String message) {
        super(message);
    }

    public BillException(String message, int level) {
        super(message);
        this.level = level;
    }

    public BillException(Throwable cause) {
        super(cause);
    }

    public BillException(String message, Throwable cause) {
        super(message, cause);
    }

    public BillException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Boolean getCloseTab() {
        return this.closeTab;
    }

    public void setCloseTab(Boolean closeTab) {
        this.closeTab = closeTab;
    }

    public List<CheckResult> getCheckMessages() {
        return this.checkMessages;
    }

    public void setCheckMessages(List<CheckResult> checkMessages) {
        this.checkMessages = checkMessages;
    }

    public Map<String, List<Map<String, Object>>> getTablesData() {
        return this.tablesData;
    }

    public boolean isInvisible() {
        return this.invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public void setTablesData(Map<String, List<Map<String, Object>>> tablesData) {
        this.tablesData = tablesData;
    }

    public BillException(String message, List<CheckResult> checkMessages) {
        super(message);
        this.checkMessages = checkMessages;
    }

    public BillException(String message, Boolean closeTab) {
        super(message);
        this.closeTab = closeTab;
    }

    public Map<String, List<List<Object>>> getFrontTablesData() {
        return this.frontTablesData;
    }

    public void setFrontTablesData(Map<String, List<List<Object>>> frontTablesData) {
        this.frontTablesData = frontTablesData;
    }
}

