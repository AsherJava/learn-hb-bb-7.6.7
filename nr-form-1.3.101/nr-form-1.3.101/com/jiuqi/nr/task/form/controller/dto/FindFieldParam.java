/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.controller.dto;

import java.util.List;

public class FindFieldParam {
    List<String> tableCodes;
    List<String> fieldCodes;

    public List<String> getTableCodes() {
        return this.tableCodes;
    }

    public void setTableCodes(List<String> tableCodes) {
        this.tableCodes = tableCodes;
    }

    public List<String> getFieldCodes() {
        return this.fieldCodes;
    }

    public void setFieldCodes(List<String> fieldCodes) {
        this.fieldCodes = fieldCodes;
    }
}

