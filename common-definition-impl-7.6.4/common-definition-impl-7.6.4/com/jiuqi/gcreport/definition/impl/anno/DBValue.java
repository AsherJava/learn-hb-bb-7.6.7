/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.anno;

import com.jiuqi.gcreport.definition.impl.anno.DBKeyValuePair;

public @interface DBValue {
    public String id();

    public DBKeyValuePair[] fields() default {};
}

