/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formulaeditor.internal.param;

import com.jiuqi.nr.formulaeditor.internal.param.core.DefaultFormulaEditor;
import org.springframework.stereotype.Component;

@Component
public class DesignFormulaEditor
extends DefaultFormulaEditor {
    @Override
    public String getId() {
        return "DEFAULT_EDITOR_ID";
    }
}

