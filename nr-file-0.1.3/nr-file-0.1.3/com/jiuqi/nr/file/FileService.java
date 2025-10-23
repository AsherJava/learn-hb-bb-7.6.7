/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.file;

import com.jiuqi.nr.file.FileAreaConfig;
import com.jiuqi.nr.file.FileAreaService;

public interface FileService
extends FileAreaService {
    public FileAreaService area(String var1);

    public FileAreaService area(FileAreaConfig var1);

    public FileAreaService tempArea();
}

