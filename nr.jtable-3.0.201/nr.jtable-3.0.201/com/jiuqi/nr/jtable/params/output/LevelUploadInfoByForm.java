/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.output.EntityData;
import java.util.List;

public class LevelUploadInfoByForm {
    private List<EntityData> entitys;
    private String name;
    private String key;
    private String code;

    public List<EntityData> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(List<EntityData> entitys) {
        this.entitys = entitys;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

