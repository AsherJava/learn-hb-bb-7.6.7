/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.print;

import com.jiuqi.nr.single.core.print.ITemplatePageStyleConfig;

public class DefaultTemplatePageStyleConfig
implements ITemplatePageStyleConfig {
    @Override
    public double getVerticalSpacing() {
        return 5.0;
    }

    @Override
    public double getWordLabelHeight() {
        return 20.0;
    }

    @Override
    public double getWordLabelWidth() {
        return 80.0;
    }

    @Override
    public String getName() {
        return "_defaultPageStyleConfig";
    }
}

