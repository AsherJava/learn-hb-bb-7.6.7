/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.event;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.event.BucketEventType;
import com.jiuqi.bi.oss.event.IBucketEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BucketEventEngine {
    private final Logger log = LoggerFactory.getLogger(BucketEventEngine.class);
    private static final BucketEventEngine instance = new BucketEventEngine();
    private IBucketEventPublisher publisher;

    public static BucketEventEngine getInstance() {
        return instance;
    }

    public void setPublisher(IBucketEventPublisher publisher) {
        this.publisher = publisher;
    }

    public IBucketEventPublisher getPublisher() {
        return this.publisher;
    }

    public void publish(Bucket bucket, BucketEventType type) {
        if (this.publisher == null) {
            this.log.error("BucketEventEngine#publish\u5931\u8d25\uff0c\u7f3a\u5c11IBucketEventPublisher\u5b9e\u73b0\u7c7b");
            return;
        }
        this.publisher.publish(bucket, type);
    }
}

