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
package com.jiuqi.va.bill.trans;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.trans.domain.Node;
import com.jiuqi.va.trans.domain.TransDefine;
import com.jiuqi.va.trans.domain.VaTransInfoDO;
import com.jiuqi.va.trans.domain.VaTransInfoRegisterTask;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BillTransInfoRegister
implements VaTransInfoRegisterTask {
    public List<VaTransInfoDO> getTransInfos() {
        ArrayList<VaTransInfoDO> tranInfos = new ArrayList<VaTransInfoDO>();
        tranInfos.add(this.registerBillCommit());
        tranInfos.add(this.registerBillComplete());
        tranInfos.add(this.registerBillReject());
        tranInfos.add(this.registerBillRetract());
        return tranInfos;
    }

    public String getVersion() {
        return "20231127-0001";
    }

    private VaTransInfoDO registerBillCommit() {
        VaTransInfoDO vaTransInfoDO = new VaTransInfoDO();
        vaTransInfoDO.setName("bill-commit");
        vaTransInfoDO.setTrantype("SIMPLE");
        TransDefine transDefine = new TransDefine();
        transDefine.setName("bill-commit");
        Node billCommitAfterNode = new Node();
        billCommitAfterNode.setName("bill-commit-after");
        billCommitAfterNode.setDesc("\u5355\u636e\u63d0\u4ea4\u5b8c\u6210");
        billCommitAfterNode.setAssignexchange(true);
        billCommitAfterNode.setMqname("VA_BILL_COMMIT_AFTER_");
        billCommitAfterNode.setType("NODE");
        Node todoAddNode = new Node();
        todoAddNode.setName("todo-batch-add");
        todoAddNode.setDesc("\u6dfb\u52a0\u5f85\u529e");
        todoAddNode.setMqname("VA_TODO_TASK_ADDBATCH_QUEUE");
        todoAddNode.setType("NODE");
        ArrayList<Node> nodeList = new ArrayList<Node>();
        nodeList.add(billCommitAfterNode);
        nodeList.add(todoAddNode);
        transDefine.setNodeList(nodeList);
        transDefine.setDesc("\u5355\u636e\u63d0\u4ea4");
        vaTransInfoDO.setDefine(JSONUtil.toJSONString((Object)transDefine));
        vaTransInfoDO.setVer(Long.valueOf(System.currentTimeMillis()));
        return vaTransInfoDO;
    }

    private VaTransInfoDO registerBillComplete() {
        VaTransInfoDO vaTransInfoDO = new VaTransInfoDO();
        vaTransInfoDO.setName("bill-complete");
        vaTransInfoDO.setTrantype("SIMPLE");
        TransDefine transDefine = new TransDefine();
        transDefine.setName("bill-complete");
        Node todoCompleteNode = new Node();
        todoCompleteNode.setName("todo-batch-complete");
        todoCompleteNode.setDesc("\u5f85\u529e\u5ba1\u6279");
        todoCompleteNode.setMqname("VA_TODO_TASK_COMPLETEBATCH_QUEUE");
        todoCompleteNode.setType("NODE");
        Node billCompleteAfterNode = new Node();
        billCompleteAfterNode.setName("bill-complete-after");
        billCompleteAfterNode.setDesc("\u5355\u636e\u5ba1\u6279\u5b8c\u6210");
        billCompleteAfterNode.setAssignexchange(true);
        billCompleteAfterNode.setMqname("VA_BILL_COMPLETE_AFTER_");
        billCompleteAfterNode.setType("NODE");
        ArrayList<Node> nodeList = new ArrayList<Node>();
        nodeList.add(todoCompleteNode);
        nodeList.add(billCompleteAfterNode);
        ArrayList<String> outkeyList = new ArrayList<String>();
        outkeyList.add("processStatus");
        transDefine.setOutKeyList(outkeyList);
        transDefine.setDesc("\u5355\u636e\u540c\u610f");
        transDefine.setNodeList(nodeList);
        vaTransInfoDO.setDefine(JSONUtil.toJSONString((Object)transDefine));
        vaTransInfoDO.setVer(Long.valueOf(System.currentTimeMillis()));
        return vaTransInfoDO;
    }

    private VaTransInfoDO registerBillReject() {
        VaTransInfoDO vaTransInfoDO = new VaTransInfoDO();
        vaTransInfoDO.setName("bill-reject");
        vaTransInfoDO.setTrantype("SIMPLE");
        TransDefine transDefine = new TransDefine();
        transDefine.setName("bill-reject");
        Node todoCompleteNode = new Node();
        todoCompleteNode.setName("todo-batch-complete");
        todoCompleteNode.setDesc("\u5f85\u529e\u9a73\u56de");
        todoCompleteNode.setMqname("VA_TODO_TASK_COMPLETEBATCH_QUEUE");
        todoCompleteNode.setType("NODE");
        Node billRejectAfterNode = new Node();
        billRejectAfterNode.setName("bill-reject-after");
        billRejectAfterNode.setDesc("\u5355\u636e\u9a73\u56de\u5b8c\u6210");
        billRejectAfterNode.setAssignexchange(true);
        billRejectAfterNode.setMqname("VA_BILL_REJECT_AFTER_");
        billRejectAfterNode.setType("NODE");
        ArrayList<Node> nodeList = new ArrayList<Node>();
        nodeList.add(todoCompleteNode);
        nodeList.add(billRejectAfterNode);
        ArrayList<String> outkeyList = new ArrayList<String>();
        outkeyList.add("processStatus");
        transDefine.setOutKeyList(outkeyList);
        transDefine.setDesc("\u5355\u636e\u9a73\u56de");
        transDefine.setNodeList(nodeList);
        vaTransInfoDO.setDefine(JSONUtil.toJSONString((Object)transDefine));
        vaTransInfoDO.setVer(Long.valueOf(System.currentTimeMillis()));
        return vaTransInfoDO;
    }

    private VaTransInfoDO registerBillRetract() {
        VaTransInfoDO vaTransInfoDO = new VaTransInfoDO();
        vaTransInfoDO.setName("bill-retract");
        vaTransInfoDO.setTrantype("SIMPLE");
        TransDefine transDefine = new TransDefine();
        transDefine.setName("bill-retract");
        Node todoRetractNode = new Node();
        todoRetractNode.setName("todo-retract");
        todoRetractNode.setDesc("\u5f85\u529e\u53d6\u56de");
        todoRetractNode.setMqname("VA_TODO_TASK_RETRACT_QUEUE");
        todoRetractNode.setType("NODE");
        Node billRetractAfterNode = new Node();
        billRetractAfterNode.setName("bill-retract-after");
        billRetractAfterNode.setDesc("\u5355\u636e\u53d6\u56de\u5b8c\u6210");
        billRetractAfterNode.setAssignexchange(true);
        billRetractAfterNode.setMqname("VA_BILL_RETRACT_AFTER_");
        billRetractAfterNode.setType("NODE");
        ArrayList<Node> nodeList = new ArrayList<Node>();
        nodeList.add(todoRetractNode);
        nodeList.add(billRetractAfterNode);
        ArrayList<String> outkeyList = new ArrayList<String>();
        outkeyList.add("taskId");
        transDefine.setOutKeyList(outkeyList);
        transDefine.setDesc("\u5355\u636e\u53d6\u56de");
        transDefine.setNodeList(nodeList);
        vaTransInfoDO.setDefine(JSONUtil.toJSONString((Object)transDefine));
        vaTransInfoDO.setVer(Long.valueOf(System.currentTimeMillis()));
        return vaTransInfoDO;
    }
}

