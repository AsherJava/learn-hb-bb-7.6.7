/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.print.web.param;

import java.io.Serializable;

public class CommonTemplatePM
implements Serializable {
    String designerId;
    String printSchemeKey;
    String[] formKeys;

    public String getDesignerId() {
        return this.designerId;
    }

    public void setDesignerId(String designerId) {
        this.designerId = designerId;
    }

    public String getPrintSchemeKey() {
        return this.printSchemeKey;
    }

    public void setPrintSchemeKey(String printSchemeKey) {
        this.printSchemeKey = printSchemeKey;
    }

    public String[] getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(String[] formKeys) {
        this.formKeys = formKeys;
    }
}

