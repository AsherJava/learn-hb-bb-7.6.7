/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.gather.gzw.web.vo;

import com.jiuqi.nr.batch.gather.gzw.web.vo.DimValueVO;
import java.util.List;

public class SingleDimVO {
    private String entityId;
    private String title;
    private List<DimValueVO> dimValues;

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DimValueVO> getDimValues() {
        return this.dimValues;
    }

    public void setDimValues(List<DimValueVO> dimValues) {
        this.dimValues = dimValues;
    }
}

