/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.transmission.data.vo;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.transmission.data.domain.SyncSchemeGroupDO;
import java.io.Serializable;

public class SchemeGroupNodeVO
implements INode,
Serializable {
    private String key;
    private String code;
    private String title;
    private String parent;

    public SchemeGroupNodeVO(SyncSchemeGroupDO syncSchemeGroupDO) {
        this.setKey(syncSchemeGroupDO.getKey());
        this.setTitle(syncSchemeGroupDO.getTitle());
        this.setParent(syncSchemeGroupDO.getParent());
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}

