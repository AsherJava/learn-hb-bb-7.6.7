/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.form.selector.tree.impl;

import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.form.selector.tree.impl.ReportTreeNode;

public class ReportFormNode
extends ReportTreeNode {
    public static final String TOTAL_FORM_KEY = "L8ZMDT3I";
    public static final String TOTAL_FORM_TITLE = "\u6240\u6709\u516c\u5f0f";
    public static final String BETWEEN_FORM_KEY = "00000000-0000-0000-0000-000000000000";
    public static final String BETWEEN_FORM_CODE = "L8ZMFAGT";
    public static final String BETWEEN_FORM_TITLE = "\u8868\u95f4\u516c\u5f0f";
    private String serialNumber;

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public static ReportFormNode assignFormNode(FormDefine form) {
        ReportFormNode formData = new ReportFormNode();
        formData.setKey(form.getKey());
        formData.setCode(form.getFormCode());
        formData.setTitle(form.getTitle());
        formData.setSerialNumber(form.getSerialNumber());
        formData.setType("form");
        return formData;
    }

    private static void setFormType(ReportFormNode formData) {
        if (!TOTAL_FORM_KEY.equals(formData.getKey()) && !BETWEEN_FORM_KEY.equals(formData.getKey())) {
            formData.setType("form");
        }
    }
}

