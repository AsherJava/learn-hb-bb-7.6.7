/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.sensitive.service;

import com.jiuqi.nr.sensitive.bean.daoObject.SensitiveWordDaoObject;
import java.util.List;

public interface CheckSensitiveWordService {
    public List<SensitiveWordDaoObject> thisWordIsSensitiveWord(String var1);

    public void cacheSensitiveWordMap(List<SensitiveWordDaoObject> var1, List<SensitiveWordDaoObject> var2);
}

