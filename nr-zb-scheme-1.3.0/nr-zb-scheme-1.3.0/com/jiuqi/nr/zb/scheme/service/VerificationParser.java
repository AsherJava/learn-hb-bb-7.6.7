/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 */
package com.jiuqi.nr.zb.scheme.service;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.nr.zb.scheme.core.ValidationRule;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;

public interface VerificationParser {
    public ValidationRule parse(String var1, ZbInfo var2) throws SyntaxException;
}

