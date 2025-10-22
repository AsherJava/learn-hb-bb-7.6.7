/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore2.design.domain;

import nr.midstore2.design.domain.BaseMidstoreDTO;

public class SchemeBaseDataDTO
extends BaseMidstoreDTO {
    protected String code;
    protected String title;
    protected String srcBaseDataKey;
    protected String remark;
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

    public String getSrcBaseDataKey() {
        return this.srcBaseDataKey;
    }

    public void setSrcBaseDataKey(String srcBaseDataKey) {
        this.srcBaseDataKey = srcBaseDataKey;
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
}

