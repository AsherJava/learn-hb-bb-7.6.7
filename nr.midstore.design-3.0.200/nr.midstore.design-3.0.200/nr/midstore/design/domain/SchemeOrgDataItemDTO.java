/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.design.domain;

import nr.midstore.design.domain.BaseMidstoreDTO;

public class SchemeOrgDataItemDTO
extends BaseMidstoreDTO {
    protected String code;
    protected String title;
    protected String parentCode;
    protected String orgCode;
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

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }
}

