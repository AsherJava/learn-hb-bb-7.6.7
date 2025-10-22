/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.np.definition.observer;

import com.jiuqi.np.definition.observer.MessageType;
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
public @interface NpDefinitionObserver {
    public MessageType[] type() default {MessageType.UNKNOWN};

    public String[] name() default {""};
}

