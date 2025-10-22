/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.query.ResourceNode
 */
package com.jiuqi.nr.batch.gather.gzw.web.app.func.para;

import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextData;
import com.jiuqi.nvwa.resourceview.query.ResourceNode;
import java.util.List;

public interface OpenFuncParam {
    public String getActionId();

    public ResourceNode getCurrentTableRow();

    public List<ResourceNode> getCheckTableRow();

    public BatchGatherGZWContextData getContextData();
}

