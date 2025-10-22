/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore2.design.domain;

import nr.midstore2.design.domain.BaseMidstoreDTO;

public class SchemeOrgDataFieldDTO
extends BaseMidstoreDTO {
    protected String code;
    protected String title;
    protected String srcOrgDataKey;
    protected String srcFieldKey;
    protected String schemeKey;

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

    public String getSrcOrgDataKey() {
        return this.srcOrgDataKey;
    }

    public void setSrcOrgDataKey(String srcOrgDataKey) {
        this.srcOrgDataKey = srcOrgDataKey;
    }

    public String getSrcFieldKey() {
        return this.srcFieldKey;
    }

    public void setSrcFieldKey(String srcFieldKey) {
        this.srcFieldKey = srcFieldKey;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }
}

