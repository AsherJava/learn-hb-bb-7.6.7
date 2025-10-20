/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.print.common.parse;

import com.jiuqi.nr.definition.facade.print.common.parse.ParseContext;

public interface IPatternParser {
    public boolean canParse(String var1);

    public String parse(ParseContext var1, String var2);

    public boolean canReuser(String var1);
}

