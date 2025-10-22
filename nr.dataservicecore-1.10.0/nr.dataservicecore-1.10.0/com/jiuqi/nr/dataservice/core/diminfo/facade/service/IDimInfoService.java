/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.diminfo.facade.service;

import java.util.List;

public interface IDimInfoService {
    public List<String> queryPriDimByDataScheme(String var1);

    public List<String> queryPriDimByTask(String var1);

    public List<String> queryPriDimByFormScheme(String var1);
}

