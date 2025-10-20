/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.web.facade.DataFieldVO
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.nr.datascheme.web.facade.DataFieldVO;
import com.jiuqi.nr.designer.common.ReverseItemState;
import com.jiuqi.nr.designer.util.IReverseState;

public class ReverseDataFieldVO
extends DataFieldVO
implements IReverseState {
    private ReverseItemState state;
    private String gatherTypeTitle;

    @Override
    public ReverseItemState getState() {
        return this.state;
    }

    @Override
    public void setState(ReverseItemState state) {
        this.state = state;
    }

    public String getGatherTypeTitle() {
        return this.gatherTypeTitle;
    }

    public void setGatherTypeTitle(String gatherTypeTitle) {
        this.gatherTypeTitle = gatherTypeTitle;
    }
}

