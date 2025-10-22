/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.query.ResourceNode
 */
package com.jiuqi.nr.batch.gather.gzw.web.app.func.para;

import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextDataImpl;
import com.jiuqi.nr.batch.gather.gzw.web.app.func.para.OpenFuncParam;
import com.jiuqi.nvwa.resourceview.query.ResourceNode;
import java.util.List;

public class OpenFuncParamImpl
implements OpenFuncParam {
    private String actionId;
    private ResourceNode currentTableRow;
    private List<ResourceNode> checkTableRow;
    private BatchGatherGZWContextDataImpl contextData;

    @Override
    public String getActionId() {
        return this.actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    @Override
    public ResourceNode getCurrentTableRow() {
        return this.currentTableRow;
    }

    public void setCurrentTableRow(ResourceNode currentTableRow) {
        this.currentTableRow = currentTableRow;
    }

    @Override
    public List<ResourceNode> getCheckTableRow() {
        return this.checkTableRow;
    }

    public void setCheckTableRow(List<ResourceNode> checkTableRow) {
        this.checkTableRow = checkTableRow;
    }

    @Override
    public BatchGatherGZWContextDataImpl getContextData() {
        return this.contextData;
    }

    public void setContextData(BatchGatherGZWContextDataImpl contextData) {
        this.contextData = contextData;
    }
}

