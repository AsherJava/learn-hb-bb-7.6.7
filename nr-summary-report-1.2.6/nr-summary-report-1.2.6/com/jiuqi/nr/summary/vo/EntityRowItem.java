/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.calibre2.domain.CalibreDataDTO
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;

public class EntityRowItem {
    private String code;
    private String title;
    private int level;

    public EntityRowItem() {
    }

    public EntityRowItem(String code, String title, int level) {
        this.code = code;
        this.title = title;
        this.level = level;
    }

    public static EntityRowItem build(CalibreDataDTO calibreDataDTO) {
        return new EntityRowItem(calibreDataDTO.getCode(), calibreDataDTO.getName(), 0);
    }

    public static EntityRowItem build(IEntityRow entityRow) {
        int level = 0;
        String[] parentPath = entityRow.getParentsEntityKeyDataPath();
        if (parentPath != null) {
            level = parentPath.length;
        }
        return new EntityRowItem(entityRow.getEntityKeyData(), entityRow.getTitle(), level);
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

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

