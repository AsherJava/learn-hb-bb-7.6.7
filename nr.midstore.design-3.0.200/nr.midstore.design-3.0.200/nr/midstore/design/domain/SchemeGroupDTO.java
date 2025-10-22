/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.design.domain;

import nr.midstore.design.domain.BaseMidstoreDTO;

public class SchemeGroupDTO
extends BaseMidstoreDTO {
    private String title;
    private String desc;
    private String parent;

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

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}

