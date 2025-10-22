/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.sensitive.bean.viewObject;

import com.jiuqi.nr.sensitive.bean.viewObject.SensitiveWordViewObject;
import java.util.List;

public class SensitiveWordResultInfo {
    private List<SensitiveWordViewObject> sensitiveWordViewObjectList;
    private Integer sensitiveWordCount;

    public List<SensitiveWordViewObject> getSensitiveWordViewObjectList() {
        return this.sensitiveWordViewObjectList;
    }

    public void setSensitiveWordViewObjectList(List<SensitiveWordViewObject> sensitiveWordViewObjectList) {
        this.sensitiveWordViewObjectList = sensitiveWordViewObjectList;
    }

    public Integer getSensitiveWordCount() {
        return this.sensitiveWordCount;
    }

    public void setSensitiveWordCount(Integer sensitiveWordCount) {
        this.sensitiveWordCount = sensitiveWordCount;
    }
}

