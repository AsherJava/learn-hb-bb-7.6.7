/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.design.domain;

import nr.midstore.design.domain.BaseMidstoreDTO;

public class SchemeFieldDTO
extends BaseMidstoreDTO {
    protected String code;
    protected String title;
    protected String srcTableKey;
    protected String srcFieldKey;
    protected String remark;
    protected String schemeKey;
    protected boolean encrypted;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSrcTableKey() {
        return this.srcTableKey;
    }

    public void setSrcTableKey(String srcTableKey) {
        this.srcTableKey = srcTableKey;
    }

    public String getSrcFieldKey() {
        return this.srcFieldKey;
    }

    public void setSrcFieldKey(String srcFieldKey) {
        this.srcFieldKey = srcFieldKey;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public boolean isEncrypted() {
        return this.encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }
}

