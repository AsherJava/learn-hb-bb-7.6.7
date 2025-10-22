/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore2.design.vo;

import nr.midstore2.design.domain.CommonParamDTO;

public class MistoreTaskVO
extends CommonParamDTO {
    private String dataScheme;
    private String dw;
    private String dims;
    private String dateTime;

    public String getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
    }

    public String getDw() {
        return this.dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getDims() {
        return this.dims;
    }

    public void setDims(String dims) {
        this.dims = dims;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}

