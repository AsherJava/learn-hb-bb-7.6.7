/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.provider;

import com.jiuqi.nr.attachment.provider.param.FileBucketNameParam;

public interface IFileBucketNameProvider {
    default public String getBucketName(FileBucketNameParam param) {
        return "JTABLEAREA";
    }
}

