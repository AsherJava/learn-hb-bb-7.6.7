/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.blob.BlobContainer
 *  com.jiuqi.np.blob.BlobContainerProvider
 */
package com.jiuqi.gcreport.common.mongodb;

import com.jiuqi.np.blob.BlobContainer;
import com.jiuqi.np.blob.BlobContainerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GCBlobContainerManager {
    @Autowired
    private BlobContainerProvider blobProvider;

    public BlobContainer getDefaultBlobContainer() {
        return this.getBlobContainer("GCREPORT_FILE");
    }

    public BlobContainer getBlobContainer(String blobName) {
        return this.blobProvider.getContainer(blobName);
    }
}

