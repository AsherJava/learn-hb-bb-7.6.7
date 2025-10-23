/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.enumeration;

public enum IProcessFormRejectAttrKeys {
    is_form_reject_button("isFormRejectButton", "\u662f\u5426\u662f\u5355\u8868\u9000\u56de\u6309\u94ae"),
    use_form_reject_query("useFormRejectQuery", "\u662f\u5426\u662f\u5355\u8868\u9000\u56de\u7684\u573a\u666f"),
    process_form_reject("nrFormRejectParam", "\u5355\u8868\u9000\u56de\u7684\u6267\u884c\u53c2\u6570"),
    process_form_reject_forms("rejectFormIds", "\u9000\u56de\u8868\u5355ID");

    public final String attrKey;
    public final String description;

    private IProcessFormRejectAttrKeys(String attrKey, String description) {
        this.attrKey = attrKey;
        this.description = description;
    }
}

