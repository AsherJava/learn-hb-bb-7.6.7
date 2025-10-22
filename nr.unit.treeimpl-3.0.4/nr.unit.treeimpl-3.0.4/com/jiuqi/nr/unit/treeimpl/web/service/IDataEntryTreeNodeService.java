/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.impl.RunTimeContextData
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.unit.treeimpl.web.service;

import com.jiuiqi.nr.unit.treebase.context.impl.RunTimeContextData;
import com.jiuqi.nr.unit.treeimpl.web.request.LevelTreeExportParam;
import com.jiuqi.nr.unit.treeimpl.web.request.NodeModifyParam;
import com.jiuqi.nr.unit.treeimpl.web.response.NodeTagsCountInfo;
import com.jiuqi.nr.unit.treeimpl.web.response.NodeTagsInfo;
import javax.servlet.http.HttpServletResponse;

public interface IDataEntryTreeNodeService {
    public void exportLevelTree(LevelTreeExportParam var1, HttpServletResponse var2);

    public NodeTagsInfo inqueryNodeTags(NodeModifyParam var1);

    public Boolean saveTerminalState(NodeModifyParam var1);

    public NodeTagsCountInfo countTagNodes(RunTimeContextData var1);
}

