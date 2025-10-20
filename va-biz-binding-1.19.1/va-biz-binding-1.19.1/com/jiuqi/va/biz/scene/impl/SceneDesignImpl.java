/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.scene.impl;

import com.jiuqi.va.biz.scene.intf.SceneDesign;
import com.jiuqi.va.biz.scene.intf.SceneEditProp;
import java.util.ArrayList;
import java.util.List;

public class SceneDesignImpl
implements SceneDesign {
    private List<SceneEditProp> editProps = new ArrayList<SceneEditProp>();

    @Override
    public List<? extends SceneEditProp> getEditProps() {
        return this.editProps;
    }
}

