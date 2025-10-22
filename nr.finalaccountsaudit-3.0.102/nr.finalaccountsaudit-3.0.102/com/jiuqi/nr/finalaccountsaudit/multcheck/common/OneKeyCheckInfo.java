/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.common;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.MultCheckItem;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OneKeyCheckInfo
extends JtableLog
implements Serializable {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private Map<String, DimensionValue> selectedDimensionSet = new HashMap<String, DimensionValue>();
    private List<MultCheckItem> checkItems;
    private boolean singleApp;
    private String checkSchemeKey;
    private Boolean beforeUpload;

    public Boolean getBeforeUpload() {
        return this.beforeUpload;
    }

    public void setBeforeUpload(Boolean beforeUpload) {
        this.beforeUpload = beforeUpload;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public Map<String, DimensionValue> getSelectedDimensionSet() {
        return this.selectedDimensionSet;
    }

    public void setSelectedDimensionSet(Map<String, DimensionValue> selectedDimensionSet) {
        this.selectedDimensionSet = selectedDimensionSet;
    }

    public List<MultCheckItem> getCheckItems() {
        return this.checkItems;
    }

    public void setCheckItems(List<MultCheckItem> checkItems) {
        this.checkItems = checkItems;
    }

    public boolean getSingleApp() {
        return this.singleApp;
    }

    public void setSingleApp(boolean singleApp) {
        this.singleApp = singleApp;
    }

    public String getCheckSchemeKey() {
        return this.checkSchemeKey;
    }

    public void setCheckSchemeKey(String checkSchemeKey) {
        this.checkSchemeKey = checkSchemeKey;
    }
}

