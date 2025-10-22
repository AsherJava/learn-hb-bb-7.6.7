/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.service;

import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import java.util.Arrays;
import java.util.List;

public interface IDataAccessItemBaseService {
    public String name();

    default public String group() {
        return this.name();
    }

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }

    default public boolean isEnable(String taskKey, String formSchemeKey) {
        return true;
    }

    default public AccessLevel getLevel() {
        return AccessLevel.FORM;
    }

    default public List<String> getCodeList() {
        return Arrays.asList("2");
    }

    public IAccessMessage getAccessMessage();
}

