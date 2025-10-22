/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.web.facade.DataTableVO
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.nr.datascheme.web.facade.DataTableVO;
import com.jiuqi.nr.designer.common.ReverseItemState;
import com.jiuqi.nr.designer.util.IReverseState;
import com.jiuqi.nr.designer.web.rest.vo.ReverseDataFieldVO;
import java.util.Map;

public class ReverseDataTableVO
extends DataTableVO
implements IReverseState {
    private ReverseItemState state;
    private Map<String, ReverseDataFieldVO> dataFields;

    @Override
    public ReverseItemState getState() {
        return this.state;
    }

    @Override
    public void setState(ReverseItemState state) {
        this.state = state;
    }

    public Map<String, ReverseDataFieldVO> getDataFields() {
        return this.dataFields;
    }

    public void setDataFields(Map<String, ReverseDataFieldVO> dataFields) {
        this.dataFields = dataFields;
    }
}

