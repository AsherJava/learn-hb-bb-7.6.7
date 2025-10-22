/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.obj;

import com.jiuqi.nr.data.logic.internal.obj.FormulaNodeSaveObj;
import java.util.List;

public class FormulaNodeSaveInfo {
    private final List<FormulaNodeSaveObj> fmlNodeSaveObjs;
    private final boolean containMaskData;

    public FormulaNodeSaveInfo(List<FormulaNodeSaveObj> fmlNodeSaveObjs, boolean containMaskData) {
        this.fmlNodeSaveObjs = fmlNodeSaveObjs;
        this.containMaskData = containMaskData;
    }

    public boolean isContainMaskData() {
        return this.containMaskData;
    }

    public List<FormulaNodeSaveObj> getFmlNodeSaveObjs() {
        return this.fmlNodeSaveObjs;
    }
}

