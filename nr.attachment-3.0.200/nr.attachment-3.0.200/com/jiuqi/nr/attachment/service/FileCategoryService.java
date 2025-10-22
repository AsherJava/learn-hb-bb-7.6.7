/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.service;

import com.jiuqi.nr.attachment.message.FileCategoryInfo;
import java.util.List;

public interface FileCategoryService {
    public String getDefaultFileCategoryCode();

    public List<FileCategoryInfo> getFileCategoryMap();

    public List<FileCategoryInfo> getFileCategoryMapForSystem();

    public String getFileCategoryTitle(String var1);

    public boolean updateFileCategory(List<FileCategoryInfo> var1);
}

