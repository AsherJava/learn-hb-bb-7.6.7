/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.design.domain;

import nr.midstore.design.domain.BaseMidstoreDTO;

public class SchemeBaseDataFieldDTO
extends BaseMidstoreDTO {
    protected String code;
    protected String title;
    protected String baseDataKey;
    protected String srcBaseDataKey;
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

    public String getBaseDataKey() {
        return this.baseDataKey;
    }

    public void setBaseDataKey(String baseDataKey) {
        this.baseDataKey = baseDataKey;
    }

    public String getSrcBaseDataKey() {
        return this.srcBaseDataKey;
    }

    public void setSrcBaseDataKey(String srcBaseDataKey) {
        this.srcBaseDataKey = srcBaseDataKey;
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

