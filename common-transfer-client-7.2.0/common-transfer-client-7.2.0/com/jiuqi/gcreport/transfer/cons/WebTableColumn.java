/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.transfer.cons;

import com.jiuqi.gcreport.transfer.vo.TableFormatEnum;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

@Target(value={ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface WebTableColumn {
    @AliasFor(value="value")
    public String title() default "";

    public boolean show() default true;

    @AliasFor(value="title")
    public String value() default "";

    public int width() default -1;

    public String align() default "";

    public TableFormatEnum formatter() default TableFormatEnum.STRING;
}

