/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.Min
 *  javax.validation.constraints.NotBlank
 */
package com.jiuqi.nr.zb.scheme.web.vo;

import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ZbCheckParam {
    private static final int EXPIRE = 259200000;
    @NotBlank(message="formSchemeKey is blank")
    private @NotBlank(message="formSchemeKey is blank") String formSchemeKey;
    @NotBlank(message="zbSchemeKey is blank")
    private @NotBlank(message="zbSchemeKey is blank") String zbSchemeKey;
    @NotBlank(message="zbSchemeVersionKey is blank")
    private @NotBlank(message="zbSchemeVersionKey is blank") String zbSchemeVersionKey;
    @Min(value=0L)
    private @Min(value=0L) long expire = 259200000L;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getZbSchemeKey() {
        return this.zbSchemeKey;
    }

    public void setZbSchemeKey(String zbSchemeKey) {
        this.zbSchemeKey = zbSchemeKey;
    }

    public String getZbSchemeVersionKey() {
        return this.zbSchemeVersionKey;
    }

    public void setZbSchemeVersionKey(String zbSchemeVersionKey) {
        this.zbSchemeVersionKey = zbSchemeVersionKey;
    }

    public long getExpire() {
        return this.expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ZbCheckParam that = (ZbCheckParam)o;
        return Objects.equals(this.formSchemeKey, that.formSchemeKey) && Objects.equals(this.zbSchemeKey, that.zbSchemeKey) && Objects.equals(this.zbSchemeVersionKey, that.zbSchemeVersionKey);
    }

    public int hashCode() {
        return Objects.hash(this.formSchemeKey, this.zbSchemeKey, this.zbSchemeVersionKey);
    }
}

