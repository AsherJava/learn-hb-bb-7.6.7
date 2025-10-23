/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.ValidationRule;

public interface VerificationParser {
    public ValidationRule parse(String var1, DataField var2) throws SyntaxException;
}

