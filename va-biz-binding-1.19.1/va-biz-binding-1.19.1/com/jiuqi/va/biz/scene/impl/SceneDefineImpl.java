/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.scene.impl;

import com.jiuqi.va.biz.impl.model.PluginDefineImpl;
import com.jiuqi.va.biz.scene.impl.SceneItemImpl;
import com.jiuqi.va.biz.scene.intf.SceneDefine;
import com.jiuqi.va.biz.scene.intf.SceneItem;
import java.util.ArrayList;
import java.util.List;

public class SceneDefineImpl
extends PluginDefineImpl
implements SceneDefine {
    private List<SceneItemImpl> scenes = new ArrayList<SceneItemImpl>();

    @Override
    public List<? extends SceneItem> getScenes() {
        return this.scenes;
    }
}

