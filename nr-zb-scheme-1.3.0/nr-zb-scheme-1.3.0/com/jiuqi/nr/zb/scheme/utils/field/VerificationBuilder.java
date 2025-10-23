/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.utils.field;

import com.jiuqi.nr.zb.scheme.common.ZbDataType;

public interface VerificationBuilder {
    public VerificationBuilder equal(String var1);

    public VerificationBuilder notEqual(String var1);

    public VerificationBuilder moreThan(String var1);

    public VerificationBuilder moreThanOrEqual(String var1);

    public VerificationBuilder lessThan(String var1);

    public VerificationBuilder lessThanOrEqual(String var1);

    public VerificationBuilder contains(String var1);

    public VerificationBuilder notContains(String var1);

    public VerificationBuilder in(String[] var1);

    public VerificationBuilder isMobilePhone();

    public VerificationBuilder notNull();

    public VerificationBuilder maxLen(int var1);

    public VerificationBuilder between(String var1, String var2);

    public VerificationBuilder notInBetween(String var1, String var2);

    public VerificationBuilder clean();

    public VerificationBuilder setDomain(String var1, String var2, ZbDataType var3);

    public String build();
}

