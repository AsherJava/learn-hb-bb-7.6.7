/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.datav.dashboard.engine;

import java.io.Serializable;

public class WidgetRenderContext
implements Serializable {
    private boolean drill;
    private boolean renderParam;
    private boolean forceUpdateDataset;

    public boolean isDrill() {
        return this.drill;
    }

    public void setDrill(boolean drill) {
        this.drill = drill;
    }

    public boolean isRenderParam() {
        return this.renderParam;
    }

    public void setRenderParam(boolean renderParam) {
        this.renderParam = renderParam;
    }

    public boolean isForceUpdateDataset() {
        return this.forceUpdateDataset;
    }

    public void setForceUpdateDataset(boolean forceUpdateDataset) {
        this.forceUpdateDataset = forceUpdateDataset;
    }
}

