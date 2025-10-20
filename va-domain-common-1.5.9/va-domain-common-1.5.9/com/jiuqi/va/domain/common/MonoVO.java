/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  reactor.core.publisher.Mono
 */
package com.jiuqi.va.domain.common;

import com.jiuqi.va.domain.common.EnvConfig;
import reactor.core.publisher.Mono;

public class MonoVO {
    public static Object just(Object data) {
        return EnvConfig.isWebFluxSupport() ? Mono.justOrEmpty((Object)data) : data;
    }
}

