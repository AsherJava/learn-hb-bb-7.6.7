/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.vo;

import com.jiuqi.nr.calibre2.vo.ReferenceCalibreVO;
import java.util.ArrayList;
import java.util.List;

public class ReferenceCheckVO {
    private boolean enableDelete;
    private List<ReferenceCalibreVO> items;

    public boolean isEnableDelete() {
        return this.enableDelete;
    }

    public void setEnableDelete(boolean enableDelete) {
        this.enableDelete = enableDelete;
    }

    public List<ReferenceCalibreVO> getItems() {
        return this.items;
    }

    public void setItems(List<ReferenceCalibreVO> items) {
        this.items = items;
    }

    public void addItem(ReferenceCalibreVO referenceCalibreVO) {
        if (this.items == null) {
            this.items = new ArrayList<ReferenceCalibreVO>();
        }
        this.items.add(referenceCalibreVO);
    }
}

