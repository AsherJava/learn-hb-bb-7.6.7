/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.obj;

import com.jiuqi.nr.data.logic.internal.util.SystemOptionUtil;

@Deprecated
public class CKDCheckSetting {
    private final int minNum;
    private final int maxNum;
    private final boolean existChinese;

    public CKDCheckSetting(SystemOptionUtil systemOptionUtil) {
        this.minNum = systemOptionUtil.getCheckCharNum();
        this.maxNum = systemOptionUtil.getCheckCharMaxNum();
        this.existChinese = systemOptionUtil.ckdContainsChinese();
    }

    public int getMinNum() {
        return this.minNum;
    }

    public int getMaxNum() {
        return this.maxNum;
    }

    public boolean isExistChinese() {
        return this.existChinese;
    }
}

