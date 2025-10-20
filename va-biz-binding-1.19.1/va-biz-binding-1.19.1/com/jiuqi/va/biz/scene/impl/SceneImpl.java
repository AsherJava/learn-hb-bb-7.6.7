/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.scene.impl;

import com.jiuqi.va.biz.impl.model.PluginImpl;
import com.jiuqi.va.biz.scene.impl.SceneDefineImpl;
import com.jiuqi.va.biz.scene.intf.Scene;

public class SceneImpl
extends PluginImpl
implements Scene {
    @Override
    public SceneDefineImpl getDefine() {
        return (SceneDefineImpl)super.getDefine();
    }
}

