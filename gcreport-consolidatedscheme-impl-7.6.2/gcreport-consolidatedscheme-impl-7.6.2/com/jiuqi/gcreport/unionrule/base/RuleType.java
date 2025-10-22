/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.unionrule.base;

import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RuleType {
    public String name();

    public String code();

    public String formulaFetchDataSource() default "";

    public Class<? extends AbstractUnionRule>[] dto() default {};

    public double order() default 4.9E-324;
}

