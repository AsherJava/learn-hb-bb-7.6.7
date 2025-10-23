/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.file.attribute;

import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.attribute.FileAttributeCollection;
import com.jiuqi.nr.file.attribute.FileInfoExtension;
import java.io.InputStream;
import java.util.List;

public interface BlockAttributeService
extends FileAreaService {
    public FileInfo upload(String var1, InputStream var2, FileAttributeCollection var3);

    public FileInfoExtension getInfoExtension(String var1);

    public List<FileInfoExtension> getInfoExtension(String ... var1);
}

