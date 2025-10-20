/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.anno;

import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface DBIndexs {
    public DBIndex[] value();
}

