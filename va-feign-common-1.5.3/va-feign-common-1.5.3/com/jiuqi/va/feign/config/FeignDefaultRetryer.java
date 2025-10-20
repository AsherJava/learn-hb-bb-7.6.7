/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  feign.RetryableException
 *  feign.Retryer
 *  feign.Retryer$Default
 */
package com.jiuqi.va.feign.config;

import com.jiuqi.va.feign.util.FeignUtil;
import feign.RetryableException;
import feign.Retryer;

public class FeignDefaultRetryer
extends Retryer.Default
implements Retryer {
    private final int maxAttempts;
    private final long period;
    private final long maxPeriod;

    public FeignDefaultRetryer() {
        this(100L, 1000L, 5);
    }

    public FeignDefaultRetryer(long period, long maxPeriod, int maxAttempts) {
        super(period, maxPeriod, maxAttempts);
        this.period = period;
        this.maxPeriod = maxPeriod;
        this.maxAttempts = maxAttempts;
    }

    public Retryer clone() {
        return new FeignDefaultRetryer(this.period, this.maxPeriod, this.maxAttempts);
    }

    public void continueOrPropagate(RetryableException e) {
        try {
            super.continueOrPropagate(e);
        }
        catch (Throwable e2) {
            FeignUtil.retryableException(true, e);
            throw e2;
        }
    }
}

