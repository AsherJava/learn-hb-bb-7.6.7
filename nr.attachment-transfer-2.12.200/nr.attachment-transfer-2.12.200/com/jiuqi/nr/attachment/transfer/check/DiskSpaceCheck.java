/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.check;

import com.jiuqi.nr.attachment.transfer.check.IResourceCheck;
import com.jiuqi.nr.attachment.transfer.log.AttachmentLogHelper;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class DiskSpaceCheck
implements IResourceCheck {
    private final File dir;
    private final int maxSize;

    public DiskSpaceCheck(String path, int maxSize) {
        this.dir = new File(FilenameUtils.normalize(path));
        this.maxSize = maxSize;
        AttachmentLogHelper.debug("\u521d\u59cb\u5316\u78c1\u76d8\u68c0\u6d4b\u5668\uff0c\u8def\u5f84\u4e3a\uff1a" + path + ",\u6700\u5927\u5185\u5b58\u4e3a\uff1a" + maxSize);
    }

    @Override
    public boolean check() {
        if (!this.dir.exists()) {
            this.dir.mkdir();
        }
        long length = 0L;
        try {
            length = FileUtils.sizeOfDirectory(this.dir);
        }
        catch (Exception e) {
            AttachmentLogHelper.debug(e.getMessage(), e);
        }
        AttachmentLogHelper.debug("\u78c1\u76d8\u68c0\u6d4b\uff0c\u5927\u5c0f\u4e3a\uff1a" + length);
        double lengthGB = (double)length / 1024.0 / 1024.0 / 1024.0;
        return lengthGB <= (double)this.maxSize * 0.9;
    }
}

