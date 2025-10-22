/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.entity.engine.exception;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.engine.exception.IncorrectQueryException;
import java.util.List;

public class DuplicateRowKeysException
extends IncorrectQueryException {
    private static final long serialVersionUID = -6357834248137645138L;
    private List<DimensionValueSet> duplicateKeys;

    public DuplicateRowKeysException(List<DimensionValueSet> duplicateKeys) {
        super("\u4e1a\u52a1\u4e3b\u952e\u91cd\u590d");
        this.duplicateKeys = duplicateKeys;
    }

    public List<DimensionValueSet> getDuplicateKeys() {
        return this.duplicateKeys;
    }

    public void setDuplicateKeys(List<DimensionValueSet> duplicateKeys) {
        this.duplicateKeys = duplicateKeys;
    }
}

