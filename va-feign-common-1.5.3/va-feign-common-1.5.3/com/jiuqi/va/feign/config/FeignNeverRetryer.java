/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  feign.RetryableException
 *  feign.Retryer
 */
package com.jiuqi.va.feign.config;

import com.jiuqi.va.feign.util.FeignUtil;
import feign.RetryableException;
import feign.Retryer;

public class FeignNeverRetryer
implements Retryer {
    public Retryer clone() {
        return this;
    }

    public void continueOrPropagate(RetryableException e) {
        FeignUtil.retryableException(false, e);
        throw e;
    }
}

