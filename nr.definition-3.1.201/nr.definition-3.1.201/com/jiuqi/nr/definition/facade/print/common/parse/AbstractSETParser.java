/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.print.common.parse;

import com.jiuqi.nr.definition.facade.print.common.parse.IPatternParser;
import com.jiuqi.nr.definition.facade.print.common.parse.ParseContext;

public abstract class AbstractSETParser
implements IPatternParser {
    protected String startTag;
    protected String endTag;

    public AbstractSETParser(String startTag, String endTag) {
        this.startTag = startTag;
        this.endTag = endTag;
    }

    @Override
    public boolean canParse(String str) {
        return str.startsWith(this.startTag) && str.endsWith(this.endTag);
    }

    @Override
    public String parse(ParseContext context, String patternStr) {
        String result = patternStr.replace(this.startTag, "");
        result = result.replace(this.endTag, "");
        return this.doParse(context, result);
    }

    protected abstract String doParse(ParseContext var1, String var2);
}

