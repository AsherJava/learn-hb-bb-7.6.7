/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.check;

import com.jiuqi.nr.attachment.transfer.check.IResourceCheck;
import com.jiuqi.nr.attachment.transfer.log.AttachmentLogHelper;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class MemorySpaceCheck
implements IResourceCheck {
    private final long threshold;

    public MemorySpaceCheck(long threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean check() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage usage = memoryMXBean.getHeapMemoryUsage();
        long max = usage.getMax();
        long used = usage.getUsed();
        AttachmentLogHelper.debug("\u5185\u5b58\u68c0\u6d4b\uff0c\u603b\u5bb9\u91cf\u4e3a\uff1a{},\u5df2\u7528\uff1a{}", max, used);
        return (double)used < (double)max * (1.0 - (double)this.threshold / 100.0);
    }
}

