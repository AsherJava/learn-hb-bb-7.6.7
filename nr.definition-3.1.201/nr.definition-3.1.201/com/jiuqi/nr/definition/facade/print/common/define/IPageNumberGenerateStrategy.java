/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.print.common.define;

import com.jiuqi.nr.definition.facade.print.common.parse.ParseContext;

public interface IPageNumberGenerateStrategy {
    public int getCurrentPageNumberOffset();

    public int getTotalPageNumber();

    public int getTemplatePageOrderNumber();

    public String parse(ParseContext var1, String var2);
}

