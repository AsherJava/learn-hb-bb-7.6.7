/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.query.ResourceNode
 */
package com.jiuqi.nr.batch.summary.web.app.func.para;

import com.jiuqi.nvwa.resourceview.query.ResourceNode;
import java.util.List;

public class OpenGroupTreePagePara {
    private String taskId;
    private List<ResourceNode> checkTableRow;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<ResourceNode> getCheckTableRow() {
        return this.checkTableRow;
    }

    public void setCheckTableRow(List<ResourceNode> checkTableRow) {
        this.checkTableRow = checkTableRow;
    }
}

