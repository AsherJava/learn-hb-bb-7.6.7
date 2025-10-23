/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.NotNull
 *  javax.validation.constraints.Pattern
 *  javax.validation.constraints.Size
 */
package com.jiuqi.nr.zb.scheme.internal.dto;

import com.jiuqi.nr.zb.scheme.core.ZbGroup;
import com.jiuqi.nr.zb.scheme.internal.excel.validation.ExcelCheck;
import java.time.Instant;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ZbGroupDTO
implements ZbGroup {
    @Size(max=40, message="{zs.text.size}")
    @Pattern(regexp="^GROUP_(\\d{2})+$", message="{zs.code.notReg}", groups={ExcelCheck.class})
    private @Size(max=40, message="{zs.text.size}") @Pattern(regexp="^GROUP_(\\d{2})+$", message="{zs.code.notReg}", groups={ExcelCheck.class}) String key;
    @NotBlank(message="{zs.title.notBlank}")
    @Size(min=1, max=200, message="{zs.title.size}")
    private @NotBlank(message="{zs.title.notBlank}") @Size(min=1, max=200, message="{zs.title.size}") String title;
    @Size(max=40, message="{zs.text.size}")
    @NotNull(message="{zs.zbParentKey.notNull}")
    @Pattern(regexp="^GROUP_(\\d{2})+$", message="{zs.parentCode.notReg}", groups={ExcelCheck.class})
    private @Size(max=40, message="{zs.text.size}") @NotNull(message="{zs.zbParentKey.notNull}") @Pattern(regexp="^GROUP_(\\d{2})+$", message="{zs.parentCode.notReg}", groups={ExcelCheck.class}) String parentKey;
    @Size(max=40, message="{zs.text.size}")
    @NotNull(message="{zs.zbSchemeKey.notNull}")
    private @Size(max=40, message="{zs.text.size}") @NotNull(message="{zs.zbSchemeKey.notNull}") String schemeKey;
    @Size(max=40, message="{zs.text.size}")
    @NotNull(message="{zs.zbVersionKey.notNull}")
    private @Size(max=40, message="{zs.text.size}") @NotNull(message="{zs.zbVersionKey.notNull}") String versionKey;
    private Instant updateTime;
    private String level;
    private String order;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getParentKey() {
        return this.parentKey;
    }

    @Override
    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    @Override
    public String getVersionKey() {
        return this.versionKey;
    }

    @Override
    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey;
    }

    @Override
    public String getSchemeKey() {
        return this.schemeKey;
    }

    @Override
    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    @Override
    public Instant getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }
}

