/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.nr.bpm.de.dataflow.systemoptions;

import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkflowOptions
implements ISystemOptionDeclare {
    private static final String WORKFLOWNAME = "\u5de5\u4f5c\u6d41";
    private static final String WORKFLOWID = "nr-flow-id";
    private static final String WORKFLOW_END_NODE = "flow-end-node";
    private static final String WORKFLOW_ASYNC_OPT = "flow-async-opt";
    private static final String WORKFLOW_ASYNC_OPT_NUM = "flow-async-opt-num";
    private static final String WORKFLOW_ASYNC_TODO = "flow-async-todo";
    private static final String WORKFLOW_ASYNC_TODO_NUM = "flow-async-todo_num";
    private static final String WORKFLOW_FORCE_NODE = "workflow-force-node";
    @Autowired
    private SystemOptionOperator systemOptionOperator;

    public String getId() {
        return WORKFLOWID;
    }

    public String getTitle() {
        return WORKFLOWNAME;
    }

    public String getNameSpace() {
        return "\u62a5\u8868";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        ISystemOptionItem node = new ISystemOptionItem(){

            public String getId() {
                return WorkflowOptions.WORKFLOW_END_NODE;
            }

            public String getTitle() {
                return "\u53d1\u5e03\u65f6\u4e0d\u6821\u9a8c\u7ed3\u675f\u4e8b\u4ef6";
            }

            public String getDefaultValue() {
                return "";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.TRUE_FALSE;
            }
        };
        optionItems.add(node);
        ISystemOptionItem nodeOptNum = new ISystemOptionItem(){

            public String getId() {
                return WorkflowOptions.WORKFLOW_ASYNC_OPT_NUM;
            }

            public String getTitle() {
                return "\u6279\u91cf\u64cd\u4f5c\u5f02\u6b65\u5904\u7406-\u5f02\u6b65\u4efb\u52a1\u6570";
            }

            public String getDefaultValue() {
                return "5";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.DEFAULT;
            }
        };
        optionItems.add(nodeOptNum);
        ISystemOptionItem nodeTodoNumOpt = new ISystemOptionItem(){

            public String getId() {
                return WorkflowOptions.WORKFLOW_ASYNC_TODO_NUM;
            }

            public String getTitle() {
                return "\u5f85\u529e\u5904\u7406\u5668\u5bb9\u91cf\u5927\u5c0f(\u503c\u4fee\u6539\u540e\u9700\u91cd\u542f\u670d\u52a1\u751f\u6548)";
            }

            public String getDefaultValue() {
                return "3000";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.DEFAULT;
            }
        };
        optionItems.add(nodeTodoNumOpt);
        ISystemOptionItem forceNode = new ISystemOptionItem(){

            public String getId() {
                return WorkflowOptions.WORKFLOW_FORCE_NODE;
            }

            public String getTitle() {
                return "\u5f3a\u5236\u4e0a\u62a5\u65f6\u4e0d\u80fd\u4e0a\u62a5\u5f53\u524d\u7528\u6237\u7684\u6240\u5c5e\u5355\u4f4d\u548c\u76d1\u7ba1\u5355\u4f4d";
            }

            public String getDefaultValue() {
                return "0";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.TRUE_FALSE;
            }
        };
        optionItems.add(forceNode);
        return optionItems;
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }

    public int getOrdinal() {
        return 11;
    }
}

