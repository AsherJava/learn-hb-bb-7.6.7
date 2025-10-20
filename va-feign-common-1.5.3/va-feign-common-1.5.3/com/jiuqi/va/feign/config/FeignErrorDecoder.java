/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  feign.Response
 *  feign.codec.ErrorDecoder
 *  feign.codec.ErrorDecoder$Default
 */
package com.jiuqi.va.feign.config;

import com.jiuqi.va.feign.util.FeignUtil;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder
extends ErrorDecoder.Default
implements ErrorDecoder {
    public Exception decode(String methodKey, Response response) {
        Exception e = FeignUtil.errorDecoder(response);
        if (e != null) {
            return e;
        }
        return super.decode(methodKey, response);
    }
}

