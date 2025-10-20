/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.model.value;

import com.jiuqi.nvwa.framework.parameter.ParameterException;
import java.text.Format;
import java.util.Locale;

public interface IParameterValueFormat {
    public String format(Object var1) throws ParameterException;

    public Object parse(String var1) throws ParameterException;

    default public Format getDataShowFormat(Locale locale) {
        return null;
    }
}

