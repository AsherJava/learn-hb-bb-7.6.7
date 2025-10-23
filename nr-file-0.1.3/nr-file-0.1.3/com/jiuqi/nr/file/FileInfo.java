/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.file;

import com.jiuqi.nr.file.FileStatus;
import java.io.Serializable;
import java.util.Date;

public interface FileInfo
extends Serializable {
    public String getKey();

    public String getArea();

    public String getName();

    public String getExtension();

    public FileStatus getStatus();

    public long getSize();

    public String getCreater();

    public Date getCreateTime();

    public String getLastModifier();

    public Date getLastModifyTime();

    public int getVersion();

    public String getFileGroupKey();

    public String getPath();

    public String getSecretlevel();
}

