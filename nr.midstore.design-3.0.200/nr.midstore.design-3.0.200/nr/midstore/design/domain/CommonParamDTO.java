/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.design.domain;

import nr.midstore.design.domain.BaseMidstoreDTO;

public class CommonParamDTO
extends BaseMidstoreDTO {
    private String code;
    private String title;
    private String desc;

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

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

