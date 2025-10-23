/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation
 */
package com.jiuqi.nr.workflow2.engine.dflt.exception;

import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation;
import java.util.Set;

public class TransferDataFileAnalyseException
extends RuntimeException {
    public TransferDataFileAnalyseException(IProcessIOInstance instance, IProcessIOOperation operation) {
        super("\u8bfb\u53d6\u6d41\u7a0b\u5b9e\u4f8b\u6570\u636e\uff1a\n" + TransferDataFileAnalyseException.printInstance(instance) + " \u4e0e\u5bf9\u5e94\u7684\u64cd\u4f5c\u5386\u53f2\u6570\u636e\uff1a\n" + TransferDataFileAnalyseException.printOperation(operation) + " \u987a\u5e8f\u4e0d\u5bf9\u5e94 \u6570\u636e\u8fc1\u79fb\u7ec8\u6b62\uff01");
    }

    public TransferDataFileAnalyseException(Set<String> rangeInstanceId, IProcessIOOperation operation) {
        super("\u672c\u6b21\u8bfb\u53d6\u7684\u6d41\u7a0b\u64cd\u4f5c\u5386\u53f2\u7684\u6d41\u7a0b\u5b9e\u4f8b\u8303\u56f4\u4e3a\uff1a" + rangeInstanceId + " \u5f53\u524d\u8bfb\u53d6\u5230\u7684\u6d41\u7a0b\u64cd\u4f5c\u5386\u53f2\u6570\u636e\u5f52\u5c5e\u7684\u6d41\u7a0b\u5b9e\u4f8bid\u5e76\u4e0d\u5728\u8fd9\u4e2a\u8303\u56f4\u5185 \u6570\u636e\u8fc1\u79fb\u7ec8\u6b62\uff01\n\u5f02\u5e38\u7684\u64cd\u4f5c\u5386\u53f2\u6570\u636e\u4e3a\uff1a\n" + TransferDataFileAnalyseException.printOperation(operation));
    }

    public static String printInstance(IProcessIOInstance instance) {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(instance.getId()).append("\n");
        sb.append("dimensionValueSet: ").append(instance.getBusinessObject().getDimensions().toDimensionValueSet().toString()).append("\n");
        sb.append("currentUserTask: ").append(instance.getCurrentUserTask()).append("\n");
        sb.append("status: ").append(instance.getStatus()).append("\n");
        sb.append("currentUserTaskCode: ").append(instance.getCurrentUserTaskCode()).append("\n");
        return sb.toString();
    }

    public static String printOperation(IProcessIOOperation operation) {
        StringBuilder sb = new StringBuilder();
        sb.append("instanceId: ").append(operation.getInstanceId()).append("\n");
        sb.append("sourceUserTask: ").append(operation.getSourceUserTask()).append("\n");
        sb.append("targetUserTask: ").append(operation.getTargetUserTask()).append("\n");
        sb.append("action: ").append(operation.getAction()).append("\n");
        sb.append("operationTime: ").append(operation.getOperateTime().toString()).append("\n");
        sb.append("operator: ").append(operation.getOperator()).append("\n");
        sb.append("comment: ").append(operation.getComment()).append("\n");
        return sb.toString();
    }
}

