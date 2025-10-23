/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequery.multcheck.checkdes;

import com.jiuqi.nr.singlequery.multcheck.checkdes.ISingleQueryCheckDesValidator;
import com.jiuqi.nr.singlequery.multcheck.checkdes.SingleQueryContext;

public interface ISingleQueryDesValidatorProvider {
    public ISingleQueryCheckDesValidator getValidator(SingleQueryContext var1);
}

