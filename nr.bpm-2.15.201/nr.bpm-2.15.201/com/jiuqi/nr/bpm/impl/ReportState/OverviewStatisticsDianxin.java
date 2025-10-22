/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.GatherException
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.bpm.impl.ReportState;

import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.GatherException;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.common.UploadNumForDianxin;
import com.jiuqi.nr.bpm.common.UploadSumNew;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.tree.TreeWorkflow;
import com.jiuqi.nr.bpm.impl.ReportState.UploadUtil;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.bpm.upload.utils.ActionStateEnum;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class OverviewStatisticsDianxin {
    private static final Logger logger = LoggerFactory.getLogger(OverviewStatisticsDianxin.class);
    private static final String MD_ORG = "MD_ORG";
    private static final String PERIOD_FIELD = "PERIOD";
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Resource
    private WorkflowSettingService settingService;
    @Resource
    private CustomWorkFolwService customWorkFolwService;
    @Autowired
    private TreeWorkflow treeWorkFlow;
    @Autowired
    private IWorkflow workflow;
    private static final RowMapper<UploadNumForDianxin> rowMapper = (rs, rowNum) -> {
        UploadNumForDianxin uploadNumForDianxin = new UploadNumForDianxin();
        String actionCode = rs.getString("PREVEVENT");
        String curnode = rs.getString("CURNODE");
        int num = rs.getInt("RECORD_COUNT");
        uploadNumForDianxin.setActionCode(actionCode);
        uploadNumForDianxin.setPreNode(curnode);
        uploadNumForDianxin.setCount(num);
        return uploadNumForDianxin;
    };
    private static final RowMapper<ActionStateBean> stateRowMapper = (rs, rowNum) -> {
        ActionStateBean actionStateBean = new ActionStateBean();
        String actionCode = rs.getString("PREVEVENT");
        String curNode = rs.getString("CURNODE");
        String preNode = rs.getString("preNode");
        actionStateBean.setCode(actionCode);
        actionStateBean.setTaskCode(curNode);
        actionStateBean.setPreNode(preNode);
        return actionStateBean;
    };

    /*
     * WARNING - void declaration
     */
    public void queryStateNew(UploadSumNew uploadSum, FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, String period, TaskDefine taskDefine, String entitySelf, String formKey, boolean corporate, String mainDim, EntityViewDefine unitView, IEntityTable iEntityTable, boolean leafEntity, boolean filterDiffUnit, boolean onlyDirectChild, boolean flowsType, Map<String, List<String>> statisticalStates) throws Exception {
        IEntityModel entityModel;
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        String hisTableCode = "SYS_UP_HI_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allFields = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        Map<String, DimensionValue> dimensionSetMap = UploadUtil.getDimensionSet(dimensionValueSet);
        String unitKey = dimensionSetMap.get(mainDim).getValue();
        try {
            entityModel = this.iEntityMetaService.getEntityModel(unitView.getEntityId());
        }
        catch (Exception e) {
            throw new GatherException("\u672a\u627e\u5230\u5355\u4f4d\u5b9e\u4f53\u7684\u62a5\u8868\u7c7b\u578b\u6307\u6807\uff0c\u65e0\u6cd5\u8fdb\u884c\u5dee\u989d\u6c47\u603b\u3002", (Throwable)e);
        }
        IEntityAttribute bblxField = entityModel.getBblxField();
        ArrayList<String> codeList = new ArrayList<String>(Arrays.asList(unitKey.split(";")));
        HashSet<String> minusSumSet = new HashSet<String>();
        HashSet<String> isLeafSet = new HashSet<String>();
        HashSet notDirectChildSet = new HashSet();
        if (codeList.size() > 1) {
            for (String string : codeList) {
                IEntityRow iEntityRow = iEntityTable.findByEntityKey(string);
                if (iEntityRow == null) continue;
                if (iEntityTable.getChildRows(iEntityRow.getEntityKeyData()).size() > 0) {
                    isLeafSet.add(string);
                }
                if (bblxField == null || !"1".equals(iEntityRow.getAsString(bblxField.getCode()))) continue;
                minusSumSet.add(string);
            }
        }
        if (filterDiffUnit && minusSumSet.size() > 0) {
            codeList.removeAll(minusSumSet);
        }
        if (leafEntity && isLeafSet.size() > 0) {
            codeList.removeAll(isLeafSet);
        }
        uploadSum.setMasterSum(codeList.size());
        dimensionValueSet.setValue(mainDim, codeList);
        HashMap<String, Integer> countMap = new HashMap<String, Integer>();
        for (Map.Entry<String, List<String>> entry : statisticalStates.entrySet()) {
            countMap.put("custom@" + entry.getKey(), 0);
        }
        List<UploadNumForDianxin> list = this.batchReadOnlyQuery(formScheme, dimensionValueSet, tableCode, hisTableCode);
        boolean bl = false;
        if (list != null && list.size() > 0) {
            void var31_38;
            for (UploadNumForDianxin uploadNumForDianxin : list) {
                String string = uploadNumForDianxin.getActionCode();
                String preNode = uploadNumForDianxin.getPreNode();
                Integer count = uploadNumForDianxin.getCount();
                String stateCode = this.convertStateCode(string);
                String customCode = preNode + "@" + (String)stateCode;
                for (Map.Entry<String, List<String>> stateMap : statisticalStates.entrySet()) {
                    String key = stateMap.getKey();
                    List<String> value = stateMap.getValue();
                    if (!value.contains(customCode)) continue;
                    Integer num = (Integer)countMap.get("custom@" + key);
                    num = num + count;
                    countMap.put("custom@" + key, num);
                }
                if (string.equals("act_upload") || string.equals("cus_upload") || string.equals("act_cancel_confirm")) {
                    uploadSum.addUploadNum();
                }
                if ("act_confirm".equals(string) || "cus_confirm".equals(string)) {
                    uploadSum.addConfirmNum();
                }
                var31_38 += count.intValue();
            }
            int nooperateNum = uploadSum.getMasterSum() - var31_38;
            block6: for (Map.Entry<String, List<String>> entry : statisticalStates.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                for (String va : value) {
                    if (!va.contains("start")) continue;
                    countMap.put("custom@" + key, nooperateNum);
                    continue block6;
                }
            }
        } else {
            block8: for (Map.Entry<String, List<String>> entry : statisticalStates.entrySet()) {
                String string = entry.getKey();
                List<String> value = entry.getValue();
                for (String va : value) {
                    if (!va.contains("start")) continue;
                    countMap.put("custom@" + string, uploadSum.getMasterSum());
                    continue block8;
                }
            }
        }
        uploadSum.setCustomStateMap(countMap);
        Map<String, String> actionInfo = this.treeWorkFlow.getActionCodeAndStateName(formScheme.getKey());
        if (flowsType) {
            if (actionInfo.containsKey("act_submit") || actionInfo.containsKey("cus_submit")) {
                Integer n = uploadSum.getMasterSum() - uploadSum.getSubmitedNum() - uploadSum.getRejectedNum() - uploadSum.getReturnedNum() - uploadSum.getUploadedNum() - uploadSum.getConfirmedNum();
                uploadSum.setUnSubmitedNum(n > 0 ? n : 0);
            } else {
                Integer n = uploadSum.getMasterSum() - uploadSum.getSubmitedNum() - uploadSum.getRejectedNum() - uploadSum.getReturnedNum() - uploadSum.getUploadedNum() - uploadSum.getConfirmedNum();
                uploadSum.setOriginalNum(n > 0 ? n : 0);
            }
        } else {
            Integer n = uploadSum.getMasterSum() - uploadSum.getRejectedNum() - uploadSum.getUploadedNum() - uploadSum.getConfirmedNum();
            uploadSum.setOriginalNum(n > 0 ? n : 0);
        }
    }

    private List<UploadNumForDianxin> batchReadOnlyQuery(FormSchemeDefine formSchemeDefine, DimensionValueSet masterKey, String stateTableName, String hisStateTableName) {
        if (Objects.isNull(stateTableName) || Objects.isNull(hisStateTableName)) {
            return Collections.emptyList();
        }
        try {
            JdbcTemplate jdbcTemplate = (JdbcTemplate)BeanUtil.getBean(JdbcTemplate.class);
            DimensionSet dimesions = masterKey.getDimensionSet();
            StringBuffer selectSql = new StringBuffer();
            StringBuffer selectHisSql = new StringBuffer();
            StringBuffer joinSql = new StringBuffer();
            StringBuffer sqlWhere = new StringBuffer();
            StringBuffer sqlGroupBy = new StringBuffer();
            StringBuffer orderBy = new StringBuffer();
            selectSql.append("select ").append("t1.prevevent , t2.curnode, count(*) as record_count ");
            selectSql.append(" from ").append(stateTableName).append(" t1 ");
            selectHisSql.append("(").append(" select t2.* ");
            selectHisSql.append(" from ").append("(").append("select t2.*, ROW_NUMBER() OVER (PARTITION BY MDCODE,PERIOD ORDER BY TIME_ DESC) as rn ");
            selectHisSql.append(" from ").append(hisStateTableName).append(" t2 ").append(" ) ").append(" t2 ");
            selectHisSql.append(" where rn = 1 ").append(" ) ").append(" t2 ");
            joinSql.append("join ").append(selectHisSql).append(" on ").append("t1.MDCODE=T2.MDCODE AND t1.PERIOD=t2.PERIOD ");
            sqlGroupBy.append(" group by ").append(" t1.PREVEVENT, t2.CURNODE ");
            orderBy.append(" order by ").append(" record_count DESC");
            Object[] args = new Object[dimesions.size() + 1];
            int in = 0;
            sqlWhere.append(" where ").append("t1.").append("PROCESSKEY").append(" =? ");
            args[in] = this.nrParameterUtils.getProcessKey(formSchemeDefine.getKey());
            ++in;
            for (int index = 0; index < dimesions.size(); ++index) {
                String dimesion = dimesions.get(index);
                Object dimValue = masterKey.getValue(dimesion);
                boolean needIn = dimValue instanceof List;
                String dimesionField = this.parseDimField(dimesion);
                sqlWhere.append(" and ");
                sqlWhere.append("t1.").append(dimesionField);
                if (needIn) {
                    List dimValueList = (List)dimValue;
                    args = Arrays.copyOf(args, args.length + dimValueList.size() - 1);
                    this.appendArgValue(sqlWhere, args, dimValue, in);
                } else {
                    sqlWhere.append("=?");
                }
                if (!needIn) {
                    args[in] = dimValue;
                }
                ++in;
            }
            selectSql.append(joinSql).append(sqlWhere).append(sqlGroupBy).append(orderBy);
            List nums = jdbcTemplate.query(selectSql.toString(), rowMapper, args);
            return nums;
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5931\u8d25", e);
            throw new RuntimeException(e);
        }
    }

    private String parseDimField(String dimension) {
        if (StringUtils.isEmpty((String)dimension)) {
            return null;
        }
        if (dimension.startsWith(MD_ORG)) {
            return "MDCODE";
        }
        if (dimension.equals("DATATIME")) {
            return PERIOD_FIELD;
        }
        return dimension;
    }

    private void appendArgValue(StringBuffer sql, Object[] args, Object value, int index) {
        List values = (List)value;
        boolean addFlag = false;
        sql.append(" in (");
        for (Object o : values) {
            if (addFlag) {
                sql.append(",");
            }
            sql.append("?");
            args[index] = o;
            addFlag = true;
            ++index;
        }
        sql.append(") ");
    }

    private String convertStateCode(String actionCode) {
        String stateCode = null;
        if ("start".equals(actionCode)) {
            return "start";
        }
        if ("act_submit".equals(actionCode) || "cus_submit".equals(actionCode)) {
            return UploadState.SUBMITED.toString();
        }
        if ("act_return".equals(actionCode) || "cus_return".equals(actionCode)) {
            return UploadState.RETURNED.toString();
        }
        if ("act_upload".equals(actionCode) || "cus_upload".equals(actionCode) || "act_cancel_confirm".equals(actionCode)) {
            return UploadState.UPLOADED.toString();
        }
        if ("act_confirm".equals(actionCode) || "cus_confirm".equals(actionCode)) {
            return UploadState.CONFIRMED.toString();
        }
        if ("act_reject".equals(actionCode) || "cus_reject".equals(actionCode)) {
            return UploadState.REJECTED.toString();
        }
        return stateCode;
    }

    public List<ActionStateBean> queryState(FormSchemeDefine formSchemeDefine, DimensionValueSet masterKey) {
        String stateTableName = "SYS_UP_ST_" + formSchemeDefine.getFormSchemeCode();
        String hisStateTableName = "SYS_UP_HI_" + formSchemeDefine.getFormSchemeCode();
        try {
            WorkFlowType workFlowType = this.workflow.queryStartType(formSchemeDefine.getKey());
            boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeDefine.getKey());
            JdbcTemplate jdbcTemplate = (JdbcTemplate)BeanUtil.getBean(JdbcTemplate.class);
            DimensionSet dimesions = masterKey.getDimensionSet();
            StringBuffer selectSql = new StringBuffer();
            StringBuffer selectHisSql = new StringBuffer();
            StringBuffer joinSql = new StringBuffer();
            StringBuffer sqlWhere = new StringBuffer();
            selectSql.append("select ").append("t1.prevevent , t1.curnode, t2.curnode as preNode ");
            selectSql.append(" from ").append(stateTableName).append(" t1 ");
            selectHisSql.append("(").append(" select * ");
            selectHisSql.append(" from ").append("(").append("select t2.*, ROW_NUMBER() OVER (PARTITION BY MDCODE,PERIOD ORDER BY TIME_ DESC) as rn ");
            selectHisSql.append(" from ").append(hisStateTableName).append(" t2 ").append(" ) ").append(" as foo ");
            selectHisSql.append(" where rn = 1 ").append(" ) ").append(" t2 ");
            joinSql.append("join ").append(selectHisSql).append(" on ").append("t1.MDCODE=t2.MDCODE AND t1.PERIOD=t2.PERIOD ");
            Object[] args = new Object[dimesions.size() + 1];
            int in = 0;
            sqlWhere.append(" where ").append("t1.").append("PROCESSKEY").append(" =? ");
            args[in] = this.nrParameterUtils.getProcessKey(formSchemeDefine.getKey());
            ++in;
            for (int index = 0; index < dimesions.size(); ++index) {
                String dimesion = dimesions.get(index);
                Object dimValue = masterKey.getValue(dimesion);
                boolean needIn = dimValue instanceof List;
                String dimesionField = this.parseDimField(dimesion);
                sqlWhere.append(" and ");
                sqlWhere.append("t1.").append(dimesionField);
                if (needIn) {
                    List dimValueList = (List)dimValue;
                    args = Arrays.copyOf(args, args.length + dimValueList.size() - 1);
                    this.appendArgValue(sqlWhere, args, dimValue, in);
                } else {
                    sqlWhere.append("=?");
                }
                if (!needIn) {
                    args[in] = dimValue;
                }
                ++in;
            }
            selectSql.append(joinSql).append(sqlWhere);
            List actionStateBeanList = jdbcTemplate.query(selectSql.toString(), stateRowMapper, args);
            ArrayList<ActionStateBean> actionStateBeans = new ArrayList<ActionStateBean>();
            for (ActionStateBean actionStateBean : actionStateBeanList) {
                ActionStateBean state = this.getState(actionStateBean.getCode(), actionStateBean.getTaskCode(), "0", formSchemeDefine, defaultWorkflow, workFlowType);
                state.setPreNode(actionStateBean.getPreNode());
                actionStateBeans.add(state);
            }
            return actionStateBeans;
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5931\u8d25", e);
            throw new RuntimeException(e);
        }
    }

    private ActionStateBean getState(String action, String node, String forceUpload, FormSchemeDefine formScheme, boolean defaultWorkflow, WorkFlowType workflowType) {
        ActionStateBean actionState = new ActionStateBean();
        actionState.setTaskCode(node);
        actionState.setTaskKey(node);
        switch (action) {
            case "act_other_start": {
                actionState.setCode(UploadState.PART_START.toString());
                actionState.setTitile(formScheme.getKey(), ActionStateEnum.PART_START.getStateName(workflowType));
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
            case "act_other_submit": {
                actionState.setCode(UploadState.PART_SUBMITED.toString());
                actionState.setTitile(formScheme.getKey(), ActionStateEnum.PART_SUBMITED.getStateName(workflowType));
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
            case "act_other_upload": {
                actionState.setCode(UploadState.PART_UPLOADED.toString());
                actionState.setTitile(formScheme.getKey(), ActionStateEnum.PART_UPLOADED.getStateName(workflowType));
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
            case "act_other_confirm": {
                actionState.setCode(UploadState.PART_CONFIRMED.toString());
                actionState.setTitile(formScheme.getKey(), ActionStateEnum.PART_CONFIRMED.getStateName(workflowType));
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
        }
        if (defaultWorkflow) {
            return this.defaultActionState(action, node, formScheme, forceUpload);
        }
        return this.customActionState(action, node, formScheme, forceUpload);
    }

    private ActionStateBean defaultActionState(String action, String node, FormSchemeDefine formScheme, String forceUpload) {
        ActionStateBean actionState = new ActionStateBean();
        actionState.setTaskKey(node);
        actionState.setTaskCode(node);
        if ("start".equals(action)) {
            TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
            if (node.equals("tsk_upload")) {
                if (flowsSetting.isUnitSubmitForCensorship()) {
                    actionState.setCode(UploadState.SUBMITED.toString());
                    actionState.setTitile(formScheme.getKey(), "\u5df2\u9001\u5ba1");
                    actionState.setForceUpload(forceUpload.equals("1"));
                    return actionState;
                }
                actionState.setCode(UploadState.ORIGINAL_UPLOAD.toString());
                actionState.setTitile(formScheme.getKey(), "\u672a\u4e0a\u62a5");
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
            if (node.equals("tsk_submit")) {
                actionState.setCode(UploadState.ORIGINAL_SUBMIT.toString());
                actionState.setTitile(formScheme.getKey(), "\u672a\u9001\u5ba1");
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
        } else {
            if ("act_submit".equals(action) || "cus_submit".equals(action)) {
                actionState.setCode(UploadState.SUBMITED.toString());
                actionState.setTitile(formScheme.getKey(), "\u5df2\u9001\u5ba1");
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
            if ("act_return".equals(action) || "cus_return".equals(action)) {
                actionState.setCode(UploadState.RETURNED.toString());
                actionState.setTitile(formScheme.getKey(), "\u5df2\u9000\u5ba1");
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
            if ("act_upload".equals(action) || "act_cancel_confirm".equals(action) || "cus_upload".equals(action)) {
                actionState.setCode(UploadState.UPLOADED.toString());
                actionState.setTitile(formScheme.getKey(), "\u5df2\u4e0a\u62a5");
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
            if ("act_confirm".equals(action) || "cus_confirm".equals(action)) {
                actionState.setCode(UploadState.CONFIRMED.toString());
                actionState.setTitile(formScheme.getKey(), "\u5df2\u786e\u8ba4");
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
            if ("act_reject".equals(action) || "cus_reject".equals(action)) {
                actionState.setCode(UploadState.REJECTED.toString());
                actionState.setTitile(formScheme.getKey(), "\u5df2\u9000\u56de");
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
        }
        actionState.setCode(UploadState.ORIGINAL.toString());
        actionState.setTitile(formScheme.getKey(), ActionStateEnum.ORIGINAL.getStateName());
        actionState.setForceUpload(forceUpload.equals("1"));
        return actionState;
    }

    private ActionStateBean customActionState(String action, String node, FormSchemeDefine formScheme, String forceUpload) {
        ActionStateBean actionState = new ActionStateBean();
        actionState.setTaskKey(node);
        actionState.setTaskCode(node);
        WorkflowSettingDefine refSetting = this.settingService.getWorkflowDefineByFormSchemeKey(formScheme.getKey());
        WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
        if ("start".equals(action)) {
            Iterator<WorkFlowAction> iterator;
            List<WorkFlowAction> workFlowNodeAction = this.customWorkFolwService.getRunWorkFlowNodeAction(node, workFlowDefine.getLinkid());
            if (workFlowNodeAction != null && workFlowNodeAction.size() > 0 && (iterator = workFlowNodeAction.iterator()).hasNext()) {
                WorkFlowAction workFlowAction = iterator.next();
                if ("cus_submit".equals(workFlowAction.getActionCode())) {
                    actionState.setCode(UploadState.ORIGINAL_SUBMIT.toString());
                } else if ("cus_upload".equals(workFlowAction.getActionCode())) {
                    actionState.setCode(UploadState.ORIGINAL_UPLOAD.toString());
                }
                actionState.setTitile("\u672a" + workFlowAction.getActionTitle());
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
        } else {
            List<WorkFlowLine> workflowLineByEndNode = this.customWorkFolwService.getWorkflowLineByEndNode(node, workFlowDefine.getLinkid());
            for (WorkFlowLine wkl : workflowLineByEndNode) {
                String actionId = wkl.getActionid();
                WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionById(actionId, workFlowDefine.getLinkid());
                if (workflowAction == null || !workflowAction.getActionCode().equals(action)) continue;
                actionState.setCode(workflowAction.getStateCode());
                actionState.setTitile(workflowAction.getStateName());
                actionState.setForceUpload(forceUpload.equals("1"));
            }
            if (actionState == null || actionState.getCode() == null) {
                actionState = this.defaultActionState(action, node, formScheme, forceUpload);
            }
        }
        return actionState;
    }
}

