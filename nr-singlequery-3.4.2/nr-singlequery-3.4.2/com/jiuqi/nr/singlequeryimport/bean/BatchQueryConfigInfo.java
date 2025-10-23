/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.zbquery.rest.vo.QueryConfigVO
 */
package com.jiuqi.nr.singlequeryimport.bean;

import com.jiuqi.nr.zbquery.rest.vo.QueryConfigVO;
import java.util.ArrayList;
import java.util.List;

public class BatchQueryConfigInfo
extends QueryConfigVO {
    private List<String> modelIds = new ArrayList<String>();

    public List<String> getModelIds() {
        return this.modelIds;
    }

    public void setModelIds(List<String> modelIds) {
        this.modelIds = modelIds;
    }
}

