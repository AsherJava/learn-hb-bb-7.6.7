/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.event;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.event.BucketEventType;

public interface IBucketEventPublisher {
    public void publish(Bucket var1, BucketEventType var2);
}

