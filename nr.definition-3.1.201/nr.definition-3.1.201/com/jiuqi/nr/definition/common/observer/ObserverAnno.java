/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.nr.definition.common.observer;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Documented
@Controller
@ResponseBody
public @interface ObserverAnno {
}

