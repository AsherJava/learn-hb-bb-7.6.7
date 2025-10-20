/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.feign.client.WorkflowServerClient
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.intf.ModelFunction
 */
package com.jiuqi.va.bill.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.feign.client.WorkflowServerClient;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CheckWorkflowCurrNodeExistFunction
extends ModelFunction {
    @Autowired
    private WorkflowServerClient workflowServerClient;
    private static final String NODE_CODE = "nodeCode";

    public CheckWorkflowCurrNodeExistFunction() {
        this.parameters().add(new Parameter(NODE_CODE, 6, "\u5de5\u4f5c\u6d41\u8282\u70b9\u6807\u8bc6", false));
    }

    public String name() {
        return "CheckWorkflowCurrNodeExist";
    }

    public String title() {
        return "\u5224\u65ad\u5de5\u4f5c\u6d41\u5f53\u524d\u8282\u70b9\u662f\u5426\u5728\u76ee\u6807\u8282\u70b9\u4e2d\u5b58\u5728";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        ModelDataContext mdc = (ModelDataContext)iContext;
        BillContextImpl billContext = (BillContextImpl)mdc.model.getContext();
        String billCode = (String)billContext.getContextValue("billCode");
        String nodeId = (String)billContext.getContextValue("nodeId");
        if (!StringUtils.hasText(billCode)) {
            billCode = (String)billContext.getContextValue("X--billCode");
        }
        if (!StringUtils.hasText(nodeId)) {
            nodeId = (String)billContext.getContextValue("X--nodeId");
        }
        if (!StringUtils.hasText(billCode) || !StringUtils.hasText(nodeId)) {
            return false;
        }
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setBizcode(billCode);
        processNodeDTO.setNodeid(nodeId);
        processNodeDTO.setCompleteuserid(ShiroUtil.getUser().getId());
        ProcessNodeDO processNode = this.workflowServerClient.getProcessNode(processNodeDTO);
        if (processNode == null || !StringUtils.hasText(processNode.getNodecode())) {
            return false;
        }
        String nodeCode = processNode.getNodecode();
        for (IASTNode iastNode : list) {
            Object value = iastNode.evaluate(iContext);
            if (!(value instanceof String) || !nodeCode.equals(value.toString())) continue;
            return true;
        }
        return false;
    }

    public String addDescribe() {
        return "\u5224\u65ad\u5de5\u4f5c\u6d41\u5f53\u524d\u8282\u70b9\u662f\u5426\u5728\u76ee\u6807\u8282\u70b9\u4e2d\u5b58\u5728";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public String toDescription() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u63cf\u8ff0\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(NODE_CODE).append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u5de5\u4f5c\u6d41\u8282\u70b9\u6807\u8bc6\uff0c\u53ef\u4ee5\u4f20\u5165\u4e00\u4e2a\u6216\u591a\u4e2a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)1)).append("\uff1a").append(DataType.toString((int)1)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u5224\u65ad\u4f20\u5165\u53c2\u6570\u662f\u5426\u5305\u542b\u5de5\u4f5c\u6d41\u5f53\u524d\u8282\u70b9\u7684\u6807\u8bc6sid-CB230D69-6B0A-4DE5-AAB2-AFEB07F1B879").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f1\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("CheckWorkflowCurrNodeExist('sid-C983AAAF-BF71-4FCA-BB8E-2CA63BE06789')").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("false").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f2\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("CheckWorkflowCurrNodeExist('sid-C983AAAF-BF71-4FCA-BB8E-2CA63BE06789', 'sid-CB230D69-6B0A-4DE5-AAB2-AFEB07F1B879')").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("true");
        return buffer.toString();
    }
}

