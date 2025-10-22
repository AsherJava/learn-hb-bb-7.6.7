/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.bpm.custom.service.impl;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.service.impl.WorkflowCustomServiceImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class WorkflowCustomServiceCache
extends WorkflowCustomServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowCustomServiceCache.class);

    @Override
    public WorkFlowNodeSet getWorkFlowNodeSetByID(String nodeid, String linkId) {
        String cacheId = nodeid + "@" + linkId;
        NpContext context = NpContextHolder.getContext();
        ContextExtension defaultExtension = context.getDefaultExtension();
        if (defaultExtension.get(cacheId) != null) {
            return (WorkFlowNodeSet)context.getDefaultExtension().get(cacheId);
        }
        WorkFlowNodeSet workFlowNodeSet = super.getWorkFlowNodeSetByID(nodeid, linkId);
        if (workFlowNodeSet != null) {
            defaultExtension.put(cacheId, (Serializable)workFlowNodeSet);
        }
        return workFlowNodeSet;
    }

    @Override
    public WorkFlowAction getWorkflowActionById(String id, String linkId) {
        String cacheId = id + "@" + linkId;
        NpContext context = NpContextHolder.getContext();
        ContextExtension defaultExtension = context.getDefaultExtension();
        if (defaultExtension.get(cacheId) != null) {
            return (WorkFlowAction)context.getDefaultExtension().get(cacheId);
        }
        WorkFlowAction workflowAction = super.getWorkflowActionById(id, linkId);
        if (workflowAction != null) {
            defaultExtension.put(cacheId, (Serializable)workflowAction);
        }
        return workflowAction;
    }

    @Override
    public List<WorkFlowLine> getWorkflowLinesByLinkid(String linkid) {
        NpContext context = NpContextHolder.getContext();
        ContextExtension defaultExtension = context.getDefaultExtension();
        if (defaultExtension.get(linkid) != null) {
            return (List)context.getDefaultExtension().get(linkid);
        }
        ArrayList lineSerializable = null;
        List<WorkFlowLine> workflowLines = super.getWorkflowLinesByLinkid(linkid);
        if (workflowLines instanceof ArrayList) {
            lineSerializable = (ArrayList)workflowLines;
        }
        defaultExtension.put(linkid, (Serializable)lineSerializable);
        return workflowLines;
    }

    @Override
    public WorkFlowDefine getWorkFlowDefineByID(String defineID, int state) {
        NpContext context = NpContextHolder.getContext();
        ContextExtension defaultExtension = context.getDefaultExtension();
        if (defaultExtension.get(defineID) != null) {
            return (WorkFlowDefine)context.getDefaultExtension().get(defineID);
        }
        WorkFlowDefine workFlowDefine = super.getWorkFlowDefineByID(defineID, state);
        defaultExtension.put(defineID, (Serializable)workFlowDefine);
        return workFlowDefine;
    }

    @Override
    public List<WorkFlowLine> getWorkflowLinesByRunLinkid(String linkid) {
        NpContext context = NpContextHolder.getContext();
        ContextExtension defaultExtension = context.getDefaultExtension();
        if (defaultExtension.get(linkid) != null) {
            return (List)context.getDefaultExtension().get(linkid);
        }
        ArrayList lineSerializable = null;
        List<WorkFlowLine> workflowLines = super.getWorkflowLinesByRunLinkid(linkid);
        if (workflowLines instanceof ArrayList) {
            lineSerializable = (ArrayList)workflowLines;
        }
        defaultExtension.put(linkid, (Serializable)lineSerializable);
        return workflowLines;
    }

    @Override
    public WorkFlowLine getWorkFlowLineByID(String lineid, String linkId) {
        String cacheId = lineid + "@" + linkId;
        NpContext context = NpContextHolder.getContext();
        ContextExtension defaultExtension = context.getDefaultExtension();
        if (defaultExtension.get(cacheId) != null) {
            return (WorkFlowLine)context.getDefaultExtension().get(cacheId);
        }
        WorkFlowLine workFlowLine = super.getWorkFlowLineByID(lineid, linkId);
        defaultExtension.put(cacheId, (Serializable)workFlowLine);
        return workFlowLine;
    }
}

