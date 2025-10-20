/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.trans.domain.Node
 *  com.jiuqi.va.trans.domain.TransDefine
 *  com.jiuqi.va.trans.domain.VaTransInfoDO
 *  com.jiuqi.va.trans.domain.VaTransInfoRegisterTask
 */
package com.jiuqi.va.workflow.trans;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.trans.domain.Node;
import com.jiuqi.va.trans.domain.TransDefine;
import com.jiuqi.va.trans.domain.VaTransInfoDO;
import com.jiuqi.va.trans.domain.VaTransInfoRegisterTask;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class WorkflowTransInfoRegister
implements VaTransInfoRegisterTask {
    public List<VaTransInfoDO> getTransInfos() {
        ArrayList<VaTransInfoDO> tranInfos = new ArrayList<VaTransInfoDO>();
        tranInfos.add(this.registerWorkflowCommit());
        tranInfos.add(this.registerWorkflowComplete());
        tranInfos.add(this.registerWorkflowRetract());
        tranInfos.add(this.registerWorkflowRejectEndProcess());
        return tranInfos;
    }

    public String getVersion() {
        return "20240527-2000";
    }

    private VaTransInfoDO registerWorkflowCommit() {
        VaTransInfoDO vaTransInfoDO = new VaTransInfoDO();
        vaTransInfoDO.setName("workflow-commit");
        vaTransInfoDO.setTrantype("SIMPLE");
        TransDefine transDefine = new TransDefine();
        transDefine.setName("workflow-commit");
        Node todoAddNode = new Node();
        todoAddNode.setName("todo-batch-add");
        todoAddNode.setDesc("\u6dfb\u52a0\u5f85\u529e");
        todoAddNode.setMqname("VA_TODO_TASK_ADDBATCH_QUEUE");
        todoAddNode.setType("NODE");
        ArrayList<Node> nodeList = new ArrayList<Node>();
        nodeList.add(todoAddNode);
        transDefine.setNodeList(nodeList);
        transDefine.setDesc("\u5de5\u4f5c\u6d41\u63d0\u4ea4");
        vaTransInfoDO.setDefine(JSONUtil.toJSONString((Object)transDefine));
        vaTransInfoDO.setVer(Long.valueOf(System.currentTimeMillis()));
        return vaTransInfoDO;
    }

    private VaTransInfoDO registerWorkflowComplete() {
        VaTransInfoDO vaTransInfoDO = new VaTransInfoDO();
        vaTransInfoDO.setName("workflow-complete");
        vaTransInfoDO.setTrantype("SIMPLE");
        TransDefine transDefine = new TransDefine();
        transDefine.setName("workflow-complete");
        Node todoCompleteNode = new Node();
        todoCompleteNode.setName("todo-batch-complete");
        todoCompleteNode.setDesc("\u5f85\u529e\u5ba1\u6279");
        todoCompleteNode.setMqname("VA_TODO_TASK_COMPLETEBATCH_QUEUE");
        todoCompleteNode.setType("NODE");
        ArrayList<Node> nodeList = new ArrayList<Node>();
        nodeList.add(todoCompleteNode);
        transDefine.setDesc("\u5de5\u4f5c\u6d41\u5ba1\u6279");
        transDefine.setNodeList(nodeList);
        vaTransInfoDO.setDefine(JSONUtil.toJSONString((Object)transDefine));
        vaTransInfoDO.setVer(Long.valueOf(System.currentTimeMillis()));
        return vaTransInfoDO;
    }

    private VaTransInfoDO registerWorkflowRetract() {
        VaTransInfoDO vaTransInfoDO = new VaTransInfoDO();
        vaTransInfoDO.setName("workflow-retract");
        vaTransInfoDO.setTrantype("SIMPLE");
        TransDefine transDefine = new TransDefine();
        transDefine.setName("workflow-retract");
        Node todoRetractNode = new Node();
        todoRetractNode.setName("todo-retract");
        todoRetractNode.setDesc("\u5f85\u529e\u53d6\u56de");
        todoRetractNode.setMqname("VA_TODO_TASK_RETRACT_QUEUE");
        todoRetractNode.setType("NODE");
        ArrayList<Node> nodeList = new ArrayList<Node>();
        nodeList.add(todoRetractNode);
        ArrayList<String> outkeyList = new ArrayList<String>();
        outkeyList.add("taskId");
        transDefine.setOutKeyList(outkeyList);
        transDefine.setDesc("\u5de5\u4f5c\u6d41\u53d6\u56de");
        transDefine.setNodeList(nodeList);
        vaTransInfoDO.setDefine(JSONUtil.toJSONString((Object)transDefine));
        vaTransInfoDO.setVer(Long.valueOf(System.currentTimeMillis()));
        return vaTransInfoDO;
    }

    private VaTransInfoDO registerWorkflowRejectEndProcess() {
        VaTransInfoDO vaTransInfoDO = new VaTransInfoDO();
        vaTransInfoDO.setName("workflow-reopen-lastnode");
        vaTransInfoDO.setTrantype("SIMPLE");
        TransDefine transDefine = new TransDefine();
        transDefine.setName("workflow-reopen-lastnode");
        Node todoAddNode = new Node();
        todoAddNode.setName("todo-batch-add");
        todoAddNode.setDesc("\u6dfb\u52a0\u5f85\u529e");
        todoAddNode.setMqname("VA_TODO_TASK_ADDBATCH_QUEUE");
        todoAddNode.setType("NODE");
        ArrayList<Node> nodeList = new ArrayList<Node>();
        nodeList.add(todoAddNode);
        transDefine.setNodeList(nodeList);
        transDefine.setDesc("\u5de5\u4f5c\u6d41\u6d41\u7a0b\u91cd\u65b0\u6253\u5f00\u6700\u540e\u4e00\u4e2a\u8282\u70b9");
        vaTransInfoDO.setDefine(JSONUtil.toJSONString((Object)transDefine));
        vaTransInfoDO.setVer(Long.valueOf(System.currentTimeMillis()));
        return vaTransInfoDO;
    }
}

