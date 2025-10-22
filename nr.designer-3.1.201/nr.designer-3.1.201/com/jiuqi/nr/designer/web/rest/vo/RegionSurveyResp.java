/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.nr.designer.web.rest.vo.SurveyCheckItem;
import java.io.Serializable;
import java.util.List;

public class RegionSurveyResp
implements Serializable {
    private String id;
    private List<SurveyCheckItem> items;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SurveyCheckItem> getItems() {
        return this.items;
    }

    public void setItems(List<SurveyCheckItem> items) {
        this.items = items;
    }
}

