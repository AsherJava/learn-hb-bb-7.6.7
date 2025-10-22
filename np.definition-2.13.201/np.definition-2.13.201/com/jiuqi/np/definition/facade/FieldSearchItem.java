/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.facade;

import com.jiuqi.np.definition.facade.FieldSearchNode;
import java.util.List;

public class FieldSearchItem {
    private String key;
    private String code;
    private String title;
    private List<FieldSearchNode> tablePath;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<FieldSearchNode> getGroupPath() {
        return this.tablePath;
    }

    public void setGroupPath(List<FieldSearchNode> groupPath) {
        this.tablePath = groupPath;
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

