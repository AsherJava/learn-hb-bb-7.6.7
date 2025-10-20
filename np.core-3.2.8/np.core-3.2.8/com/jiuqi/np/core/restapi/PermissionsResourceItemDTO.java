/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.restapi;

public class PermissionsResourceItemDTO {
    private String code;
    private String title;

    public PermissionsResourceItemDTO() {
    }

    public PermissionsResourceItemDTO(String code, String title) {
        this.code = code;
        this.title = title;
    }

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
}

