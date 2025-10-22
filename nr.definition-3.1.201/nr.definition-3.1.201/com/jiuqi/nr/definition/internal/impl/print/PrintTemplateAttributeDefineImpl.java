/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl.print;

import com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import java.util.ArrayList;
import java.util.List;

public class PrintTemplateAttributeDefineImpl
implements PrintTemplateAttributeDefine {
    private static final long serialVersionUID = 2371491754932463454L;
    private List<WordLabelDefine> wordLabels = new ArrayList<WordLabelDefine>();

    @Override
    public List<WordLabelDefine> getWordLabels() {
        return this.wordLabels;
    }
}

