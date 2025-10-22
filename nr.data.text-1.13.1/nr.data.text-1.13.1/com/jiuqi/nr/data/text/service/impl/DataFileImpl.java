/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.DataFile
 *  com.jiuqi.nr.io.util.FileUtil
 */
package com.jiuqi.nr.data.text.service.impl;

import com.jiuqi.nr.data.common.DataFile;
import com.jiuqi.nr.io.util.FileUtil;
import java.io.File;
import java.io.IOException;

public class DataFileImpl
implements DataFile {
    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public boolean isZip() {
        return this.file.isFile();
    }

    public void close() throws IOException {
        if (null != this.file && this.file.exists()) {
            if (this.file.isFile()) {
                boolean delete = this.file.delete();
                if (!delete) {
                    throw new IOException("\u5220\u9664\u5bfc\u51fa\u6587\u4ef6\u5931\u8d25\uff1a" + this.file.getPath());
                }
                boolean parentDelete = this.file.getParentFile().delete();
                if (!parentDelete) {
                    throw new IOException("\u5220\u9664\u5bfc\u51fa\u6587\u4ef6\u5931\u8d25\uff1a" + this.file.getParentFile().getPath());
                }
            } else {
                FileUtil.deleteFiles((File)this.file);
            }
        }
    }
}

