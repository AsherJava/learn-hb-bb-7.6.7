/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.splittable.service;

import com.jiuqi.nr.splittable.exception.SplitTableException;
import com.jiuqi.nr.splittable.web.SplitDataPM;
import java.util.Map;

public interface SplitGridService {
    public Map<String, String> getGridAreaByKey(SplitDataPM var1) throws SplitTableException;

    public String getGridChildAreaByKey(String var1);

    public String getCellBook(SplitDataPM var1);
}

