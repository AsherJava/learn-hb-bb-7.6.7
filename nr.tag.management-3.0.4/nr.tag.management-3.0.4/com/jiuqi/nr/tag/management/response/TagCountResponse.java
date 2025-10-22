/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.response;

import com.jiuqi.nr.tag.management.bean.TagFacadeImpl;
import java.util.List;

public class TagCountResponse {
    private int total;
    private List<TagFacadeImpl> countTagInfos;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<TagFacadeImpl> getCountTagInfos() {
        return this.countTagInfos;
    }

    public void setCountTagInfos(List<TagFacadeImpl> countTagInfos) {
        this.countTagInfos = countTagInfos;
    }
}

