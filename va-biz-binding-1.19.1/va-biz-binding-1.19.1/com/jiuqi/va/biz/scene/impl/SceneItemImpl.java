/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.scene.impl;

import com.jiuqi.va.biz.scene.impl.SceneDesignImpl;
import com.jiuqi.va.biz.scene.intf.SceneItem;

public class SceneItemImpl
implements SceneItem {
    private String name;
    private String title;
    private String desc;
    private SceneDesignImpl design;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public SceneDesignImpl getDesign() {
        return this.design;
    }
}

