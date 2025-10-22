/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.bpm.custom.common;

import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.bpm.custom.common.UpdateExecutorTool;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class DesWorkflowUpdateExecutor
implements CustomClassExecutor {
    private static final String DES_WORKFLOW_DEFINE = "DES_WORKFLOW_DEFINE";
    private static final String DES_WORKFLOW_LINE = "DES_WORKFLOW_LINE";
    private static final String DES_WORKFLOW_ACTION = "DES_WORKFLOW_ACTION";
    private static final String DES_WORKFLOW_NODESET = "DES_WORKFLOW_NODESET";
    private static final String DES_WORKFLOW_PARTI = "DES_WORKFLOW_PARTI";
    private static final String F_XML = "F_XML";
    private static final String L_MDIM = "L_MDIM";
    private static final String L_REPORT = "L_REPORT";
    private static final String L_MSGCONTENT = "L_MSGCONTENT";
    private static final String L_MSGUSER = "L_MSGUSER";
    private static final String A_EXSET = "A_EXSET";
    private static final String N_PARTIS = "N_PARTIS";
    private static final String N_ACTIONS = "N_ACTIONS";
    private static final String P_SVALUE = "P_SVALUE";
    private static final String P_ROLEIDS = "P_ROLEIDS";
    private static final String P_USERIDS = "P_USERIDS";
    private static final String F_ID = "F_ID";
    private static final String L_ID = "L_ID";
    private static final String A_ID = "A_ID";
    private static final String N_ID = "N_ID";
    private static final String P_ID = "P_ID";

    private List<String> getDesWorkflowDefinePrimaryKeys() {
        ArrayList<String> primaryKeyNames = new ArrayList<String>();
        primaryKeyNames.add(F_ID);
        return primaryKeyNames;
    }

    private List<String> getDesWorkflowLinePrimaryKeys() {
        ArrayList<String> primaryKeyNames = new ArrayList<String>();
        primaryKeyNames.add(L_ID);
        return primaryKeyNames;
    }

    private List<String> getDesWorkflowNodeSetPrimaryKeys() {
        ArrayList<String> primaryKeyNames = new ArrayList<String>();
        primaryKeyNames.add(N_ID);
        return primaryKeyNames;
    }

    private List<String> getDesWorkflowPartiPrimaryKeys() {
        ArrayList<String> primaryKeyNames = new ArrayList<String>();
        primaryKeyNames.add(P_ID);
        return primaryKeyNames;
    }

    private List<String> getDesWorkflowActionPrimaryKeys() {
        ArrayList<String> primaryKeyNames = new ArrayList<String>();
        primaryKeyNames.add(A_ID);
        return primaryKeyNames;
    }

    private List<String> getUpdateLineFileds() {
        ArrayList<String> lineFileds = new ArrayList<String>();
        lineFileds.add(L_MDIM);
        lineFileds.add(L_REPORT);
        lineFileds.add(L_MSGCONTENT);
        lineFileds.add(L_MSGUSER);
        return lineFileds;
    }

    private List<String> getUpdatePartiFileds() {
        ArrayList<String> partiFileds = new ArrayList<String>();
        partiFileds.add(P_SVALUE);
        partiFileds.add(P_ROLEIDS);
        partiFileds.add(P_USERIDS);
        return partiFileds;
    }

    private List<String> getNodeSetFileds() {
        ArrayList<String> nodeSetFileds = new ArrayList<String>();
        nodeSetFileds.add(N_PARTIS);
        nodeSetFileds.add(N_ACTIONS);
        return nodeSetFileds;
    }

    public void execute(DataSource dataSource) throws Exception {
        UpdateExecutorTool updateExecutorTool = (UpdateExecutorTool)BeanUtils.getBean(UpdateExecutorTool.class);
        updateExecutorTool.update(dataSource, F_XML, this.getDesWorkflowDefinePrimaryKeys(), DES_WORKFLOW_DEFINE);
        updateExecutorTool.batchUpdate(dataSource, this.getUpdateLineFileds(), this.getDesWorkflowLinePrimaryKeys(), DES_WORKFLOW_LINE);
        updateExecutorTool.update(dataSource, A_EXSET, this.getDesWorkflowActionPrimaryKeys(), DES_WORKFLOW_ACTION);
        updateExecutorTool.batchUpdate(dataSource, this.getNodeSetFileds(), this.getDesWorkflowNodeSetPrimaryKeys(), DES_WORKFLOW_NODESET);
        updateExecutorTool.batchUpdate(dataSource, this.getUpdatePartiFileds(), this.getDesWorkflowPartiPrimaryKeys(), DES_WORKFLOW_PARTI);
    }
}

