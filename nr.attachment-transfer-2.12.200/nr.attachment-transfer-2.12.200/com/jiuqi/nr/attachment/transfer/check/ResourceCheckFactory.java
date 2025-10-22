/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.check;

import com.jiuqi.nr.attachment.transfer.check.IResourceCheck;

public class ResourceCheckFactory {
    IResourceCheck memoryChecker;
    IResourceCheck diskChecker;

    public ResourceCheckFactory buildMemoryChecker(IResourceCheck check) {
        this.memoryChecker = check;
        return this;
    }

    public ResourceCheckFactory buildDiskChecker(IResourceCheck check) {
        this.diskChecker = check;
        return this;
    }

    public IResourceCheck getMemoryCheck() {
        return this.memoryChecker;
    }

    public IResourceCheck getDiskCheck() {
        return this.diskChecker;
    }
}

