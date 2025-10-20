/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.JsonNodeFactory
 *  com.fasterxml.jackson.databind.node.ObjectNode
 */
package com.jiuqi.va.workflow.constants;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class VaWorkflowConstants {
    public static final int MAPSIZE = 16;
    public static final String LOG_PREFIX = "[\u5de5\u4f5c\u6d41]>>>";
    public static final String EMPTY_BLANK = " ";
    public static final String EMPTY_STR = "";
    public static final String REG_BLANK = " ";
    public static final String SPLIT_LEFT_BRACKET = "\\(";
    public static final String SPLIT_RIGHT_BRACKET = "\\)";
    public static final String LEFT_BRACKET = "(";
    public static final String RIGHT_BRACKET = ")";
    public static final String LEFT_SYMBOL_ONE = "[";
    public static final String RIGHT_SYMBOL_ONE = "]";
    public static final String SPLIT_LEFT_SYMBOL_ONE = "\\[";
    public static final String SPLIT_RIGHT_SYMBOL_ONE = "\\]";
    public static final String SYMBOL_ONE = "','";
    public static final String COMMA = ",";
    public static final String SHORT_LINE = "-";
    public static final String KEY_METATYPE = "metaType";
    public static final String KEY_BIZNAME = "bizName";
    public static final String KEY_AUTOTASKMODULENAME = "autoTaskModuleName";
    public static final String KEY_AUTOTASKNAME = "autoTaskName";
    public static final String KEY_BIZCODE = "bizCode";
    public static final String KEY_BIZTYPE = "bizType";
    public static final String KEY_AUTOTASKPARAM = "autoTaskParam";
    public static final String KEY_APPROVALRESULT = "approvalResult";
    public static final String KEY_APPROVEUSERID = "approveUserId";
    public static final String KEY_TODOPARAM = "todoParam";
    public static final String KEY_REJECTTYPE = "rejectType";
    public static final String KEY_REJECTSKIPNODE = "rejectSkipNode";
    public static final String KEY_TABLEDATA = "tableData";
    public static final String KEY_COLUMNNAME = "columnName";
    public static final String KEY_FORMULATYPE = "formulaType";
    public static final String KEY_TABLENAME = "tableName";
    public static final String KEY_PROPERTIES = "properties";
    public static final String KEY_NODEAUTOREJECTTASK = "nodeautorejecttask";
    public static final String KEY_APPROVERTIME_EXPR = "[approverTime]";
    public static final String KEY_APPROVERTIME = "approverTime";
    public static final String KEY_APPROVALOPINION_EXPR = "[approvalOpinion]";
    public static final String KEY_APPROVALOPINION = "approvalOpinion";
    public static final String KEY_CONFIGTYPE = "configType";
    public static final String KEY_COMPLETEUSERTYPE = "completeUserType";
    public static final String KEY_RESOURCEID = "resourceId";
    public static final String KEY_METADATAUNIQUECODE = "metaDataUniqueCode";
    public static final String KEY_BILLSCHEMECODE = "billSchemeCode";
    public static final String KEY_APPROVESCHEMECODE = "approveSchemeCode";
    public static final String KEY_BILLREADSCHEMECODE = "billReadSchemeCode";
    public static final String KEY_RESULTKEY = "resultKey";
    public static final String KEY_APPROVESCHEME = "approveScheme";
    public static final String KEY_BILLREADSCHEME = "billReadScheme";
    public static final String KEY_MULTISCHEMES = "multiSchemes";
    public static final String KEY_DEFINECODE = "defineCode";
    public static final String KEY_BIZDEFINE = "bizDefine";
    public static final String KEY_BIZDEFINE_TITLE = "bizDefineTitle";
    public static final String KEY_BILLWORKFLOW_VARIABLE_PREFIX = "BILL_WORKFLOW_RELATION_VARIABLES:";
    public static final String KEY_IGNORE_USER = "ignoreUser";
    public static final String KEY_TRANSFER_TO = "transferTo";
    public static final String KEY_TRANSFER_FROM = "transferFrom";
    public static final String KEY_TRANSFER_LIST = "transferList";
    public static final String L_DATA = "data";
    public static final String L_NAME = "name";
    public static final String L_BILL = "bill";
    public static final String L_ITEMS = "items";
    public static final String L_PARAM = "param";
    public static final String L_EXPRESSION = "expression";
    public static final String L_RESULT = "result";
    public static final String L_APPROVER_EXPR = "[approver]";
    public static final String L_APPROVER = "approver";
    public static final String L_PROPERTIES = "properties";
    public static final String KEY_CURRENT_NODE_CODE = "currentNodeCode";
    public static final String L_PROCESS_NODE_ID = "processNodeId";
    public static final String L_PROCESS_NODE_NAME = "processNodeName";
    public static final String L_WORKFLOW = "workflow";
    public static final String L_SCHEMES = "schemes";
    public static final String L_TITLE = "title";
    public static final String U_PARTICIPANT = "PARTICIPANT";
    public static final String U_APPROVER = "APPROVER";
    public static final String U_APPROVE_NODE = "NEXT_APPROVE_NODE";
    public static final String U_BIZCODE = "BIZCODE";
    public static final String U_TASKTYPE = "TASKTYPE";
    public static final String U_COMPLETEUSER = "COMPLETEUSER";
    public static final String U_COUNTERSIGNFLAG = "COUNTERSIGNFLAG";
    public static final String COUNTERSIGNFLAG = "1";
    public static final String WRITE_BACK_BILL_DATA_AUTO_TASK = "WriteBackBillDataAutoTask";
    public static final Pattern LEFT_BRACKET_PATTERN = Pattern.compile("\\(");
    public static final Pattern RIGHT_BRACKET_PATTERN = Pattern.compile("\\)");
    public static final Pattern LEFT_SYMBOL_ONE_PATTERN = Pattern.compile("\\[");
    public static final Pattern RIGHT_SYMBOL_ONE_PATTERN = Pattern.compile("\\]");
    public static final Pattern EMPTY_BLANK_COMPILE = Pattern.compile(" ", 16);
    public static final String PRIOCESS_NODE_FINISHED = "\u6d41\u7a0b\u7ed3\u675f";
    public static final Integer TRANSFER_FLAG = 4;
    public static final String YES_FLAG = "1";
    private static final JsonNode EMPTY_JSON_NODE;
    private static final List<String> WRITE_BILL_EXPRESSION_LIST;
    public static final String PRE_KEY_PROCESSINSTANCEID = "processInstanceId:";
    public static final String PRE_KEY_SUBMIT = ":SUBMIT:";
    public static final String WORKFLOW_LOCK_RETRACT = "workflowLockRetract";
    public static final String CURR_NODE = "currNode";
    public static final String PROCESS_DEFINE_KEY = "PROCESSDEFINEKEY";
    public static final String PROCESS_DEFINE_VERSION = "PROCESSDEFINEVERSION";
    public static final String PROCESS_OPERATION_SUBMIT = "\u63d0\u4ea4";
    public static final String APPROVAL_COMMENT = "approvalComment";

    private VaWorkflowConstants() {
    }

    public static JsonNode emptyJsonNode() {
        return EMPTY_JSON_NODE;
    }

    public static List<String> getWriteBillDataExprList() {
        return WRITE_BILL_EXPRESSION_LIST;
    }

    static {
        WRITE_BILL_EXPRESSION_LIST = new ArrayList<String>();
        EMPTY_JSON_NODE = new ObjectNode(JsonNodeFactory.instance);
    }
}

