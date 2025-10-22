/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.print;

import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import java.io.Serializable;
import java.util.List;

public interface PrintTemplateAttributeDefine
extends Serializable {
    public List<WordLabelDefine> getWordLabels();
}

