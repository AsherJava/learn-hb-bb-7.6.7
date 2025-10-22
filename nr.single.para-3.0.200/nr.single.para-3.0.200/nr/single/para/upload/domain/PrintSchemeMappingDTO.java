/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.domain;

import nr.single.para.upload.domain.BaseCompareDTO;

public class PrintSchemeMappingDTO
extends BaseCompareDTO {
    private String schemeKey;
    private String singleFormCode;
    private String singleFormTitle;
    private String netFormCode;
    private String netFormTitle;

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getSingleFormCode() {
        return this.singleFormCode;
    }

    public void setSingleFormCode(String singleFormCode) {
        this.singleFormCode = singleFormCode;
    }

    public String getSingleFormTitle() {
        return this.singleFormTitle;
    }

    public void setSingleFormTitle(String singleFormTitle) {
        this.singleFormTitle = singleFormTitle;
    }

    public String getNetFormCode() {
        return this.netFormCode;
    }

    public void setNetFormCode(String netFormCode) {
        this.netFormCode = netFormCode;
    }

    public String getNetFormTitle() {
        return this.netFormTitle;
    }

    public void setNetFormTitle(String netFormTitle) {
        this.netFormTitle = netFormTitle;
    }
}

