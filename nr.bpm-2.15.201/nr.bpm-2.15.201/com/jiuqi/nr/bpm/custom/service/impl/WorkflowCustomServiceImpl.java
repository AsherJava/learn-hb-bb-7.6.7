/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.JqLib
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.definition.common.ReportAuditType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller2.RunTimeViewController
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.util.StringUtils
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.bpm.custom.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.JqLib;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.Actor.ActorStrategyProvider;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.custom.bean.FormulaSchemeData;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefineSaveEntity;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowGroup;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowTreeNode;
import com.jiuqi.nr.bpm.custom.bean.WorkflowTreeParam;
import com.jiuqi.nr.bpm.custom.common.WorkFlowExportor;
import com.jiuqi.nr.bpm.custom.common.WorkFlowInfoObj;
import com.jiuqi.nr.bpm.custom.dao.CustomWorkFolwDao;
import com.jiuqi.nr.bpm.custom.exception.WorkflowException;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.custom.service.CustomWorkflowConfigService;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.impl.Actor.ActorStrategyParameterType;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.definition.common.ReportAuditType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller2.RunTimeViewController;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.util.StringUtils;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class WorkflowCustomServiceImpl
implements CustomWorkFolwService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowCustomServiceImpl.class);
    private static final String WORKFLOWID = "nr-flow-id";
    private static final String WORKFLOW_END_NODE = "flow-end-node";
    @Autowired
    @Qualifier(value="designDao")
    CustomWorkFolwDao designCustomWorkflowDao;
    @Autowired
    @Qualifier(value="runtimeDao")
    CustomWorkFolwDao runtimeCustomWorkflowDao;
    @Autowired
    private WorkFlowExportor exportor;
    @Autowired
    private ActorStrategyProvider actorStrategyProvider;
    @Autowired
    private WorkflowSettingService workflowSettingService;
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;
    @Autowired
    private ProcessEngineProvider processEngineProvider;
    @Autowired
    private CustomWorkflowConfigService customWorkflowConfigService;
    @Autowired
    private RunTimeViewController runTimeViewController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;

    @Override
    public WorkFlowTreeNode creatWorkFlowGroup(WorkFlowGroup group) throws JQException {
        try {
            if (null != group) {
                WorkFlowGroup fg = this.getWorkFlowGroupByTitle(group.getTitle());
                if (fg != null) {
                    throw new JQException((ErrorEnum)WorkflowException.ERROR_REPEATE);
                }
                this.runtimeCustomWorkflowDao.insertWorkflowGroup(group);
                return this.getWorkFlowTreeNode(group);
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)WorkflowException.ERROR_EMPTY);
        }
        return null;
    }

    @Override
    public List<WorkFlowTreeNode> getFirstLevelTNode() {
        List<WorkFlowGroup> groups = this.getAllWorkFlowGroup();
        ArrayList<WorkFlowTreeNode> groupTNodes = new ArrayList<WorkFlowTreeNode>();
        for (WorkFlowGroup g : groups) {
            WorkFlowTreeNode tnode = this.getWorkFlowTreeNode(g);
            groupTNodes.add(tnode);
        }
        List<WorkFlowDefine> defines = this.runtimeCustomWorkflowDao.getAllWorkFlowDefineNoParentid();
        HashMap<String, WorkFlowDefine> map = new HashMap<String, WorkFlowDefine>();
        for (WorkFlowDefine d : defines) {
            if (map.get(d.getId()) != null) continue;
            map.put(d.getId(), d);
        }
        List<WorkFlowDefine> designDefines = this.designCustomWorkflowDao.getAllWorkFlowDefineNoParentid();
        for (WorkFlowDefine workFlowDefine : designDefines) {
            if (map.get(workFlowDefine.getId()) != null || !StringUtils.isEmpty((String)workFlowDefine.getParentID())) continue;
            map.put(workFlowDefine.getId(), workFlowDefine);
        }
        for (Map.Entry entry : map.entrySet()) {
            WorkFlowDefine value = (WorkFlowDefine)entry.getValue();
            WorkFlowTreeNode tnode = this.getWorkFlowTreeNode(value);
            groupTNodes.add(tnode);
        }
        return groupTNodes;
    }

    @Override
    public WorkFlowDefine getWorkFlowDefineByID(String defineID, int state) {
        if (null != defineID && !defineID.isEmpty()) {
            if (0 == state) {
                return this.designCustomWorkflowDao.getWorkFlowDefineByID(defineID, state);
            }
            if (1 == state) {
                return this.runtimeCustomWorkflowDao.getWorkFlowDefineByID(defineID, state);
            }
        }
        return new WorkFlowDefine();
    }

    @Override
    public WorkFlowNodeSet getDesignWorkFlowNodeSetByID(String nodeid, String linkId) {
        if (null != nodeid && !nodeid.isEmpty()) {
            return this.designCustomWorkflowDao.getWorkFlowNodeSetByID(nodeid, linkId);
        }
        return null;
    }

    @Override
    public WorkFlowLine getWorkFlowLineByID(String lineid, String linkId) {
        if (null != lineid && !lineid.isEmpty()) {
            return this.designCustomWorkflowDao.getWorkFlowLineByID(lineid, linkId);
        }
        return null;
    }

    @Override
    public List<WorkFlowAction> getWorkFlowNodeAction(String nodeid, String linkId) {
        ArrayList<WorkFlowAction> list = new ArrayList<WorkFlowAction>();
        if (null != nodeid && !nodeid.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            List<WorkFlowAction> workFlowActionByNodeID = this.designCustomWorkflowDao.getWorkFlowActionByNodeID(nodeid, linkId);
            if (workFlowActionByNodeID != null && workFlowActionByNodeID.size() > 0) {
                for (WorkFlowAction action : workFlowActionByNodeID) {
                    String exset = action.getExset();
                    try {
                        Map readValue = (Map)mapper.readValue(exset, (TypeReference)new TypeReference<Map<String, Object>>(){});
                        if (readValue != null && readValue.size() > 0) {
                            Object needAutoCheckFormSchemeKey;
                            List<FormulaSchemeData> formulaSchemeData;
                            Object needAutoCalculateFormSchemeKey;
                            if (readValue.containsKey("needAutoCalculateFormSchemeKey") && (needAutoCalculateFormSchemeKey = readValue.get("needAutoCalculateFormSchemeKey")) != null && !"".equals(needAutoCalculateFormSchemeKey)) {
                                formulaSchemeData = this.customWorkflowConfigService.queryFormulaSchemeDefines(needAutoCalculateFormSchemeKey.toString());
                                action.setCalFormulaSchemeDataList(formulaSchemeData);
                            }
                            if (readValue.containsKey("needAutoCheckFormSchemeKey") && (needAutoCheckFormSchemeKey = readValue.get("needAutoCheckFormSchemeKey")) != null && !"".equals(needAutoCheckFormSchemeKey)) {
                                formulaSchemeData = this.customWorkflowConfigService.queryFormulaSchemeDefines(needAutoCheckFormSchemeKey.toString());
                                action.setCheckFormulaSchemeDataList(formulaSchemeData);
                            }
                        }
                    }
                    catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    list.add(action);
                }
            }
            return list;
        }
        return null;
    }

    @Override
    public List<WorkFlowAction> getRunWorkFlowNodeAction(String nodeid, String linkId) {
        if (null != nodeid && !nodeid.isEmpty()) {
            return this.runtimeCustomWorkflowDao.getWorkFlowActionByNodeID(nodeid, linkId);
        }
        return null;
    }

    @Override
    public List<WorkFlowParticipant> getWorkFlowParticipant(String nodeid, String linkId) {
        if (null != nodeid && !nodeid.isEmpty()) {
            return this.designCustomWorkflowDao.getWorkFlowParticipantByNodeID(nodeid, linkId);
        }
        return null;
    }

    @Override
    public String saveWrokflowdDfine(WorkFlowDefineSaveEntity saveOjb) throws JQException {
        if (null != saveOjb) {
            String id = this.designCustomWorkflowDao.saveWorkflow(saveOjb);
            return id;
        }
        return null;
    }

    @Override
    public List<WorkFlowTreeNode> getFirstLevelTNodeByDefine(WorkFlowDefine param) {
        List<WorkFlowGroup> groups = this.getAllWorkFlowGroup();
        ArrayList<WorkFlowTreeNode> groupTNodes = new ArrayList<WorkFlowTreeNode>();
        for (WorkFlowGroup g : groups) {
            WorkFlowTreeNode tnode = this.getWorkFlowTreeNode(g);
            if (g.getId().equals(param.getParentID())) {
                tnode.setExpand(true);
                List<WorkFlowDefine> childs = this.getWorkFlowDefineByGroupID(g.getId());
                ArrayList<WorkFlowTreeNode> children = new ArrayList<WorkFlowTreeNode>();
                for (WorkFlowDefine c : childs) {
                    WorkFlowTreeNode tc = this.getWorkFlowTreeNode(c);
                    if (c.getId().equals(param.getId()) && c.getState() == param.getState()) {
                        tc.setSelected(true);
                    }
                    children.add(tc);
                }
                tnode.setChildren(children);
            }
            groupTNodes.add(tnode);
        }
        List<WorkFlowDefine> defines = this.designCustomWorkflowDao.getAllWorkFlowDefineNoParentid();
        for (WorkFlowDefine d : defines) {
            WorkFlowTreeNode tnode = this.getWorkFlowTreeNode(d);
            if (d.getId().endsWith(param.getId()) && d.getState() == param.getState()) {
                tnode.setSelected(true);
            }
            groupTNodes.add(tnode);
        }
        return groupTNodes;
    }

    @Override
    public List<WorkFlowDefine> getWorkFlowDefineByGroupID(String groupID) {
        if (null != groupID && !groupID.isEmpty()) {
            return this.designCustomWorkflowDao.getWorkFlowDefineByGroupID(groupID);
        }
        return new ArrayList<WorkFlowDefine>();
    }

    @Override
    public String getWorkFlowDefineXmlByID(String defineID, int state) {
        if (null != defineID && !defineID.isEmpty()) {
            if (0 == state) {
                return this.designCustomWorkflowDao.getWorkFlowDefineXmlByID(defineID, state);
            }
            if (1 == state) {
                return this.runtimeCustomWorkflowDao.getWorkFlowDefineXmlByID(defineID, state);
            }
        }
        return "";
    }

    @Override
    public List<WorkFlowNodeSet> getWorkFlowNodeSets(String defineID, int state) {
        if (null != defineID && !defineID.isEmpty()) {
            return this.designCustomWorkflowDao.getWorkFlowNodeSetsByDefineID(defineID, 0);
        }
        return null;
    }

    @Override
    public String getWorkFlowDefineDefaultTitle() {
        List<String> titles = this.designCustomWorkflowDao.getAllDefineTitle();
        int newIndex = 1;
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        for (String title : titles) {
            if (!title.matches("^\u65b0\u5efa\u6d41\u7a0b_\\d+$")) continue;
            String[] array = title.split("_");
            int i = Integer.valueOf(array[1]);
            indexList.add(i);
        }
        ComparatorList cl = new ComparatorList();
        Collections.sort(indexList, cl);
        for (Integer i : indexList) {
            if (i == newIndex) {
                ++newIndex;
                continue;
            }
            if (i <= newIndex) continue;
            break;
        }
        return "\u65b0\u5efa\u6d41\u7a0b_" + newIndex;
    }

    @Override
    public WorkFlowDefine updateWorkFlowDefineTitle(String id, int state, String title) throws JQException {
        try {
            if (null != id) {
                WorkFlowDefine fd = this.getWorkFlowDefineByID(id, state);
                if (fd == null) {
                    fd = this.getRunWorkFlowDefineByID(id, state);
                }
                if (fd == null) {
                    throw new JQException((ErrorEnum)WorkflowException.ERROR_SEARCH_FAIL);
                }
                this.designCustomWorkflowDao.updateWorkFlowDefineTitle(id, 0, title);
                WorkFlowDefine workFlowDefine = this.runtimeCustomWorkflowDao.getWorkFlowDefineByID(id, 1);
                if (workFlowDefine != null) {
                    this.runtimeCustomWorkflowDao.updateWorkFlowDefineTitle(id, state, title);
                    return this.runtimeCustomWorkflowDao.getWorkFlowDefineByID(id, state);
                }
                return this.designCustomWorkflowDao.getWorkFlowDefineByID(id, state);
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)WorkflowException.ERROR_EMPTY);
        }
        return null;
    }

    @Override
    public void delWorkFlowDefineByID(String defineID, int state) throws JQException {
        if (null != defineID && !defineID.isEmpty()) {
            int formSchemeSize = this.workflowSettingService.getFormSchemeSizeByWorkflow(defineID);
            if (formSchemeSize > 0) {
                LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u5220\u9664\u6d41\u7a0b\u5b9a\u4e49", (String)"\u5220\u9664\u6d41\u7a0b\u5b9a\u4e49\u5931\u8d25,\u5de5\u4f5c\u5df2\u7ecf\u88ab\u4f7f\u7528,\u4e0d\u80fd\u5220\u9664");
                throw new JQException((ErrorEnum)WorkflowException.ERROR_FAIL, "\u5de5\u4f5c\u5df2\u7ecf\u88ab\u4f7f\u7528,\u4e0d\u80fd\u5220\u9664");
            }
            this.delDesignWorkflowInfo(defineID);
            this.delRuntimeWorkflowInfo(defineID);
        }
    }

    @Override
    public void delWorkFlowGroupByID(String groupID) throws JQException {
        if (null != groupID && !groupID.isEmpty()) {
            List<WorkFlowDefine> defines = this.getWorkFlowDefineByGroupID(groupID);
            if (null != defines && !defines.isEmpty()) {
                LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u5220\u9664\u5b9a\u4e49\u5206\u7ec4", (String)"\u5220\u9664\u5b9a\u4e49\u5206\u7ec4\u5931\u8d25,\u5206\u7ec4\u4e0b\u6709\u6d41\u7a0b\u5b9a\u4e49\u4e0d\u80fd\u5220\u9664\uff0c\u8bf7\u5148\u5220\u9664\u6d41\u7a0b\u5b9a\u4e49");
                throw new JQException((ErrorEnum)WorkflowException.ERROR_DATA, "\u5206\u7ec4\u4e0b\u6709\u6d41\u7a0b\u5b9a\u4e49\u4e0d\u80fd\u5220\u9664\uff0c\u8bf7\u5148\u5220\u9664\u6d41\u7a0b\u5b9a\u4e49");
            }
            WorkFlowGroup g = this.runtimeCustomWorkflowDao.getWorkFlowGroupByID(groupID);
            if (null == g) {
                LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u5220\u9664\u5b9a\u4e49\u5206\u7ec4", (String)"\u5220\u9664\u5b9a\u4e49\u5206\u7ec4\u5931\u8d25,\u8be5\u5206\u7ec4\u4e0d\u5b58\u5728");
                throw new JQException((ErrorEnum)WorkflowException.ERROR_DATA, "\u8be5\u5206\u7ec4\u4e0d\u5b58\u5728");
            }
            this.runtimeCustomWorkflowDao.delWorkFlowGroupByID(groupID);
            this.designCustomWorkflowDao.delWorkFlowGroupByID(groupID);
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u5220\u9664\u5b9a\u4e49\u5206\u7ec4", (String)("\u5220\u9664\u5b9a\u4e49\u5206\u7ec4\u540d\u79f0:" + g.getTitle()));
        }
    }

    @Override
    public void delWorkFlowNodeSetByID(String nodeid, String linkId) throws JQException {
        if (null != nodeid && !nodeid.isEmpty()) {
            WorkFlowNodeSet nodeSet = this.runtimeCustomWorkflowDao.getWorkFlowNodeSetByID(nodeid, linkId);
            if (null == nodeSet) {
                throw new JQException((ErrorEnum)WorkflowException.ERROR_DATA, "\u8be5\u6d41\u7a0b\u8282\u70b9\u4e0d\u5b58\u5728");
            }
            this.runtimeCustomWorkflowDao.delWorkFlowNodeSetByID(nodeid, linkId);
            this.designCustomWorkflowDao.delWorkFlowNodeSetByID(nodeid, linkId);
        }
    }

    @Override
    public WorkFlowNodeSet getWorkFlowNodeSetByID(String nodeid, String linkId) {
        if (null != nodeid && !nodeid.isEmpty()) {
            return this.runtimeCustomWorkflowDao.getWorkFlowNodeSetByID(nodeid, linkId);
        }
        return null;
    }

    @Override
    public List<WorkFlowGroup> getAllWorkFlowGroup() {
        return this.runtimeCustomWorkflowDao.getAllWorkFlowGroup();
    }

    @Override
    public WorkFlowGroup getWorkFlowGroupByID(String groupID) {
        return this.runtimeCustomWorkflowDao.getWorkFlowGroupByID(groupID);
    }

    @Override
    public WorkFlowGroup getWorkFlowGroupByTitle(String title) {
        return this.runtimeCustomWorkflowDao.getWorkFlowGroupByTitle(title);
    }

    @Override
    public WorkFlowTreeNode getWorkFlowGroupTNodeByID(String groupID) {
        WorkFlowGroup group = this.getWorkFlowGroupByID(groupID);
        WorkFlowTreeNode tnode = new WorkFlowTreeNode();
        tnode.setData(group);
        tnode.setTitle(group.getTitle());
        tnode.setGroup(true);
        tnode.setHasChildern(this.groupHasChildern(group.getId()));
        return tnode;
    }

    @Override
    public List<WorkFlowTreeNode> getWorkFlowDefineTNodeByGroupID(String groupID) {
        List<WorkFlowDefine> defines = this.getWorkFlowDefineByGroupID(groupID);
        ArrayList<WorkFlowTreeNode> defineTNodes = new ArrayList<WorkFlowTreeNode>();
        for (WorkFlowDefine d : defines) {
            WorkFlowTreeNode tnode = this.getWorkFlowTreeNode(d);
            defineTNodes.add(tnode);
        }
        return defineTNodes;
    }

    @Override
    public WorkFlowTreeNode getWorkFlowDefineTNodeByID(String defineID, int state) {
        WorkFlowDefine d = this.getWorkFlowDefineByID(defineID, state);
        if (d != null) {
            WorkFlowTreeNode tnode = new WorkFlowTreeNode();
            tnode.setData(d);
            tnode.setTitle(d.getTitle());
            tnode.setGroup(false);
            tnode.setHasChildern(false);
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u6253\u5f00\u6d41\u7a0b", (String)("\u6d41\u7a0b\u540d\u79f0:" + d.getTitle()));
            return tnode;
        }
        return null;
    }

    @Override
    public WorkFlowDefine getRunWorkFlowDefineByID(String defineID, int state) {
        if (null != defineID && !defineID.isEmpty()) {
            return this.runtimeCustomWorkflowDao.getWorkFlowDefineByID(defineID, state);
        }
        return new WorkFlowDefine();
    }

    @Override
    public ActorStrategyParameterType getActorStrategyParameterType(String strategyType) {
        ActorStrategy<?> strategy = this.actorStrategyProvider.getActorStrategyByType(strategyType);
        if (null != strategy) {
            return this.actorStrategyProvider.getActorStrategyParameterType(strategy);
        }
        return null;
    }

    @Override
    @Deprecated
    public void creatWorkFlowDefine(WorkFlowDefine define) throws JQException {
        if (null != define) {
            String id = define.getId();
            WorkFlowDefine fd = this.getWorkFlowDefineByID(id, define.getState());
            if (fd != null) {
                throw new JQException((ErrorEnum)WorkflowException.ERROR_REPEATE);
            }
            this.designCustomWorkflowDao.insertWorkflowDefine(define);
        }
    }

    @Override
    @Deprecated
    public Object updateWorkFlowDefine(WorkFlowDefine param) {
        return null;
    }

    @Override
    @Deprecated
    public String creatWorkFlowNodeSet(WorkFlowNodeSet nodeSet) {
        return "\u53c2\u6570\u4e3a\u7a7a";
    }

    @Override
    public WorkFlowTreeNode getWorkFlowTreeNode(WorkFlowGroup group) {
        WorkFlowTreeNode tnode = new WorkFlowTreeNode();
        tnode.setData(group);
        tnode.setTitle(group.getTitle());
        tnode.setGroup(true);
        tnode.setHasChildern(this.groupHasChildern(group.getId()));
        return tnode;
    }

    @Override
    public WorkFlowTreeNode getWorkFlowTreeNode(WorkFlowDefine define) {
        WorkFlowTreeNode tnode = new WorkFlowTreeNode();
        tnode.setData(define);
        tnode.setTitle(define.getTitle());
        tnode.setGroup(false);
        tnode.setHasChildern(false);
        return tnode;
    }

    @Override
    public boolean groupHasChildern(String groupID) {
        if (null != groupID && !groupID.isEmpty()) {
            return this.runtimeCustomWorkflowDao.groupHasChildern(groupID);
        }
        return false;
    }

    @Override
    public List<ActorStrategy<?>> getSysParticipantSchemes() {
        List<ActorStrategy<?>> strategies = this.actorStrategyProvider.getAllActorStrategies(false);
        return strategies;
    }

    @Override
    public ActorStrategy<?> getSysParticipantSchemeByID(String id) {
        return this.actorStrategyProvider.getActorStrategyByType(id);
    }

    @Override
    @Deprecated
    public String creatWorkFlowNParticipant(WorkFlowParticipant participant) {
        return null;
    }

    @Override
    @Deprecated
    public WorkFlowDefine cloneMaintenanceWorkFlowDefine(String defineID, int state) {
        WorkFlowDefine define = this.getWorkFlowDefineByID(defineID, state);
        if (null != define) {
            // empty if block
        }
        return null;
    }

    @Override
    public WorkFlowInfoObj getWorkFlowInfoObj(WorkFlowDefine define) {
        WorkFlowInfoObj infoObj = new WorkFlowInfoObj();
        try {
            if (0 == define.getState()) {
                List<WorkFlowNodeSet> nodesets = this.getWorkFlowNodeSets(define.getId(), define.getState());
                List<WorkFlowLine> lines = this.getWorkflowLinesByLinkid(define.getLinkid());
                List<WorkFlowAction> actions = this.getWorkflowActionsByLinkid(define.getLinkid());
                List<WorkFlowParticipant> particis = this.getWorkflowParticipantsByLinkid(define.getLinkid());
                infoObj = new WorkFlowInfoObj(define, nodesets, lines, actions, particis);
            } else if (1 == define.getState()) {
                List<WorkFlowNodeSet> nodesets = this.runtimeCustomWorkflowDao.getWorkFlowNodeSetsByDefineID(define.getId(), define.getState());
                List<WorkFlowLine> lines = this.runtimeCustomWorkflowDao.getWorkFlowLinesByLinkID(define.getLinkid());
                List<WorkFlowAction> actions = this.runtimeCustomWorkflowDao.getWorkFlowActionsByLinkID(define.getLinkid());
                List<WorkFlowParticipant> particis = this.runtimeCustomWorkflowDao.getWorkFlowParticipantsByLinkID(define.getLinkid());
                infoObj = new WorkFlowInfoObj(define, nodesets, lines, actions, particis);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return infoObj;
    }

    public List<WorkFlowParticipant> getWorkflowParticipantsByLinkid(String linkid) {
        if (null != linkid && !linkid.isEmpty()) {
            return this.designCustomWorkflowDao.getWorkFlowParticipantsByLinkID(linkid);
        }
        return new ArrayList<WorkFlowParticipant>();
    }

    @Override
    public List<WorkFlowLine> getWorkflowLinesByLinkid(String linkid) {
        if (null != linkid && !linkid.isEmpty()) {
            return this.designCustomWorkflowDao.getWorkFlowLinesByLinkID(linkid);
        }
        return new ArrayList<WorkFlowLine>();
    }

    @Override
    public List<WorkFlowLine> getWorkflowLinesByRunLinkid(String linkid) {
        if (null != linkid && !linkid.isEmpty()) {
            return this.runtimeCustomWorkflowDao.getWorkFlowLinesByLinkID(linkid);
        }
        return new ArrayList<WorkFlowLine>();
    }

    @Override
    public List<WorkFlowAction> getWorkflowActionsByLinkid(String linkid) {
        if (null != linkid && !linkid.isEmpty()) {
            return this.designCustomWorkflowDao.getWorkFlowActionsByLinkID(linkid);
        }
        return new ArrayList<WorkFlowAction>();
    }

    @Override
    public WorkFlowDefine releaseWorkFlowDefine(String defineID, int state) throws Exception {
        WorkFlowDefine define_maintenance = this.getWorkFlowDefineByID(defineID, 0);
        WorkFlowInfoObj infoObj = this.getWorkFlowInfoObj(define_maintenance);
        Optional<ProcessEngine> engine = this.processEngineProvider.getProcessEngine();
        if (!engine.isPresent()) {
            throw new BpmException();
        }
        engine.get().getDeployService().deployToRunTime(infoObj);
        WorkFlowDefine define = this.getWorkFlowDefineByID(defineID, state);
        this.deleteWorkflowInfo(defineID);
        WorkFlowDefineSaveEntity workFlowInfoEntity = this.getWorkFlowInfoEntity(define);
        this.runtimeCustomWorkflowDao.saveWorkflow(workFlowInfoEntity);
        return define;
    }

    public WorkFlowDefineSaveEntity getWorkFlowInfoEntity(WorkFlowDefine define) throws JQException {
        WorkFlowDefineSaveEntity WorkFlowDefineEntity = new WorkFlowDefineSaveEntity();
        List<WorkFlowNodeSet> nodesets = this.getWorkFlowNodeSets(define.getId(), define.getState());
        WorkFlowDefineEntity.setCreat_nodes(nodesets);
        List<WorkFlowLine> lines = this.getWorkflowLinesByLinkid(define.getLinkid());
        WorkFlowDefineEntity.setCreat_lines(lines);
        List<WorkFlowAction> actions = this.getWorkflowActionsByLinkid(define.getLinkid());
        WorkFlowDefineEntity.setCreat_actions(actions);
        List<WorkFlowParticipant> particis = this.getWorkflowParticipantsByLinkid(define.getLinkid());
        WorkFlowDefineEntity.setCreat_participants(particis);
        WorkFlowGroup workFlowGroup = this.designCustomWorkflowDao.getWorkFlowGroupByID(define.getParentID());
        if (workFlowGroup != null) {
            this.designCustomWorkflowDao.insertWorkflowGroup(workFlowGroup);
        }
        define.setState(1);
        WorkFlowDefineEntity.setDefine(define);
        return WorkFlowDefineEntity;
    }

    public void deleteWorkflowInfo(String defineID) throws JQException {
        WorkFlowDefine workFlowDefine = this.designCustomWorkflowDao.getWorkFlowDefineByID(defineID, 0);
        workFlowDefine.setState(1);
        if (workFlowDefine != null) {
            WorkFlowDefine workFlowDefineById;
            WorkFlowGroup workFlowGroup;
            String linkid = workFlowDefine.getLinkid();
            ArrayList<String> lineIds = new ArrayList<String>();
            List<WorkFlowLine> workFlowLines = this.runtimeCustomWorkflowDao.getWorkFlowLinesByLinkID(linkid);
            if (workFlowLines.size() > 0) {
                for (WorkFlowLine workFlowLine : workFlowLines) {
                    lineIds.add(workFlowLine.getId());
                }
                this.runtimeCustomWorkflowDao.deleteWorkflowLine(lineIds, workFlowDefine.getLinkid());
            }
            ArrayList<String> nodeIds = new ArrayList<String>();
            List<WorkFlowNodeSet> workFlowNodeSets = this.runtimeCustomWorkflowDao.getWorkFlowNodeSetsByLinkID(linkid);
            if (workFlowNodeSets.size() > 0) {
                for (WorkFlowNodeSet workFlowNodeSet : workFlowNodeSets) {
                    nodeIds.add(workFlowNodeSet.getId());
                }
                this.runtimeCustomWorkflowDao.deleteWorkflowNode(nodeIds, workFlowDefine.getLinkid());
            }
            ArrayList<String> actionIds = new ArrayList<String>();
            List<WorkFlowAction> workFlowActions = this.runtimeCustomWorkflowDao.getWorkFlowActionsByLinkID(linkid);
            if (workFlowActions.size() > 0) {
                for (WorkFlowAction workFlowAction : workFlowActions) {
                    actionIds.add(workFlowAction.getId());
                }
                this.runtimeCustomWorkflowDao.deleteWorkflowAction(actionIds, workFlowDefine.getLinkid());
            }
            ArrayList<String> partiIds = new ArrayList<String>();
            List<WorkFlowParticipant> workFlowParticipants = this.runtimeCustomWorkflowDao.getWorkFlowParticipantsByLinkID(linkid);
            if (workFlowParticipants.size() > 0) {
                for (WorkFlowParticipant workFlowParticipant : workFlowParticipants) {
                    partiIds.add(workFlowParticipant.getId());
                }
                this.runtimeCustomWorkflowDao.deleteWorkflowParticipant(partiIds, workFlowDefine.getLinkid());
            }
            if ((workFlowGroup = this.runtimeCustomWorkflowDao.getWorkFlowGroupByID(workFlowDefine.getParentID())) != null) {
                this.runtimeCustomWorkflowDao.insertWorkflowGroup(workFlowGroup);
                this.runtimeCustomWorkflowDao.delWorkFlowGroupByID(workFlowGroup.getId());
            }
            if ((workFlowDefineById = this.runtimeCustomWorkflowDao.getWorkFlowDefineByID(defineID, 1)) != null) {
                this.runtimeCustomWorkflowDao.deleteWorkflowById(defineID, 1);
            }
        }
    }

    @Override
    public Object updateWorkFlowGroup(WorkFlowGroup group) {
        if (null != group) {
            String id = group.getId();
            WorkFlowGroup g = this.getWorkFlowGroupByID(id);
            if (g == null) {
                return "\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u5b9a\u4e49\u5206\u7ec4";
            }
            return this.runtimeCustomWorkflowDao.updateWorkFlowGroup(group);
        }
        return "\u53c2\u6570\u4e3a\u7a7a";
    }

    @Override
    public WorkFlowDefine getWorkFlowDefineByLinkID(String linkid) {
        if (null != linkid && !linkid.isEmpty()) {
            return this.runtimeCustomWorkflowDao.getWorkFlowDefineByLinkID(linkid);
        }
        return null;
    }

    @Override
    @Deprecated
    public String creatWorkFlowLine(WorkFlowLine line) {
        return null;
    }

    @Override
    public List<WorkFlowDefine> searchDefineByinput(String input) {
        if (null != input && !input.isEmpty()) {
            List<WorkFlowDefine> searchDefineByinput = this.runtimeCustomWorkflowDao.searchDefineByinput(input);
            if (searchDefineByinput != null && searchDefineByinput.size() > 0) {
                return searchDefineByinput;
            }
            return this.designCustomWorkflowDao.searchDefineByinput(input);
        }
        return new ArrayList<WorkFlowDefine>();
    }

    @Override
    public String exportWorkFlowDefine(String defineid, Integer state, HttpServletResponse response) {
        WorkFlowDefine define = this.getRunWorkFlowDefineByID(defineid, state);
        if (define != null) {
            String title = define.getTitle();
            String linkid = define.getLinkid();
            List<WorkFlowNodeSet> nodesets = this.runtimeCustomWorkflowDao.getWorkFlowNodeSetsByLinkID(linkid);
            List<WorkFlowLine> lines = this.runtimeCustomWorkflowDao.getWorkFlowLinesByLinkID(linkid);
            List<WorkFlowAction> actions = this.runtimeCustomWorkflowDao.getWorkFlowActionsByLinkID(linkid);
            List<WorkFlowParticipant> particis = this.runtimeCustomWorkflowDao.getWorkFlowParticipantsByLinkID(linkid);
            String field_xml = this.exportor.getWorkFlowDefineFieldStr(define, nodesets, lines, actions, particis);
            this.exportTxt(response, field_xml, title);
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u5bfc\u51fa\u6d41\u7a0b", (String)("\u6d41\u7a0b\u540d\u79f0:" + define.getTitle()));
            return "";
        }
        LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u5bfc\u51fa\u6d41\u7a0b", (String)"\u5bfc\u51fa\u6d41\u7a0b\u5931\u8d25,\u627e\u4e0d\u5230\u6d41\u7a0b\u5b9a\u4e49");
        return "\u627e\u4e0d\u5230\u6d41\u7a0b\u5b9a\u4e49";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void exportTxt(HttpServletResponse response, String text, String fileName) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".txt");
        try (ServletOutputStream outStr = response.getOutputStream();
             BufferedOutputStream buff = new BufferedOutputStream((OutputStream)outStr);){
            buff.write(text.getBytes("UTF-8"));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public List<WorkFlowDefine> getDefineByTitle(String title) {
        return this.designCustomWorkflowDao.getWorkFlowDefineByTitle(title);
    }

    @Override
    public List<WorkFlowDefine> getDefineByTitleAndTaskKey(String title, String taskKey) {
        return this.designCustomWorkflowDao.getWorkFlowDefineByTitleAndTaskkey(title, taskKey);
    }

    @Override
    public List<WorkFlowDefine> getDefineByCode(String code) {
        return this.runtimeCustomWorkflowDao.getWorkFlowDefineByCode(code);
    }

    @Override
    public WorkFlowGroup getGroupByTitle(String title) {
        return this.runtimeCustomWorkflowDao.getWorkFlowGroupByTitle(title);
    }

    @Override
    public List<WorkFlowParticipant> getWorkFlowParticipantsByID(String[] idarray, String linkId) {
        return this.runtimeCustomWorkflowDao.getWorkFlowParticipantsByID(idarray, linkId);
    }

    @Override
    public String checkBeforeRelease(WorkFlowDefine define) {
        WorkFlowNodeSet node;
        List<WorkFlowNodeSet> nodes = this.getWorkFlowNodeSets(define.getId(), define.getState());
        HashMap nodeActionMap = new HashMap();
        if (nodes == null || nodes.isEmpty()) {
            return "\u627e\u4e0d\u5230\u6d41\u7a0b\u5b9a\u4e49\u7684\u8282\u70b9";
        }
        int startNodeCount = 0;
        int endNodeCount = 0;
        int taskNodeCount = 0;
        String startNodeId = "";
        String endNodeId = "";
        HashMap<String, WorkFlowNodeSet> nodeMap = new HashMap<String, WorkFlowNodeSet>();
        for (WorkFlowNodeSet node2 : nodes) {
            String nodeid = node2.getId();
            if (nodeid.startsWith("Start")) {
                ++startNodeCount;
                startNodeId = nodeid;
            } else if (nodeid.startsWith("End")) {
                ++endNodeCount;
                endNodeId = nodeid;
            } else if (nodeid.startsWith("Task")) {
                ++taskNodeCount;
            }
            if (startNodeCount > 1) {
                return "\u6d41\u7a0b\u5b9a\u4e49\u4e2d\u5f00\u59cb\u8282\u70b9\u591a\u4f59\u4e00\u4e2a";
            }
            if (endNodeCount > 1) {
                return "\u6d41\u7a0b\u5b9a\u4e49\u4e2d\u7ed3\u675f\u8282\u70b9\u591a\u4f59\u4e00\u4e2a";
            }
            nodeMap.put(nodeid, node2);
            Iterator actions = this.getWorkFlowNodeAction(nodeid, define.getLinkid());
            if (nodeid.startsWith("Start") || nodeid.startsWith("End")) {
                if (actions != null && !actions.isEmpty()) {
                    return "\u8282\u70b9" + node2.getTitle() + "(" + node2.getCode() + ")\u4e0d\u9700\u8981\u914d\u7f6e\u52a8\u4f5c";
                }
            } else {
                if (actions == null || actions.isEmpty()) {
                    return "\u8282\u70b9" + node2.getTitle() + "(" + node2.getCode() + ")\u627e\u4e0d\u5230\u52a8\u4f5c";
                }
                nodeActionMap.put(nodeid, actions);
                List<WorkFlowParticipant> participants = this.getWorkFlowParticipant(nodeid, define.getLinkid());
                if (participants == null || participants.isEmpty()) {
                    return "\u8282\u70b9" + node2.getTitle() + "(" + node2.getCode() + ")\u627e\u4e0d\u5230\u53c2\u4e0e\u8005";
                }
            }
            ArrayList<String> actionIDs = new ArrayList<String>();
            Iterator iterator = actions.iterator();
            while (iterator.hasNext()) {
                WorkFlowAction a = (WorkFlowAction)iterator.next();
                String actionid = a.getActionid();
                if (actionIDs.contains(actionid)) {
                    return "\u8282\u70b9" + node2.getTitle() + "(" + node2.getCode() + ")\u4e0a\u914d\u7f6e\u7684\u52a8\u4f5c\u91cd\u590d";
                }
                actionIDs.add(actionid);
            }
        }
        if (startNodeCount == 0) {
            return "\u6d41\u7a0b\u5b9a\u4e49\u6ca1\u6709\u5f00\u59cb\u8282\u70b9";
        }
        if (endNodeCount == 0 && !this.ignoreCheckEndEvent()) {
            return "\u6d41\u7a0b\u5b9a\u4e49\u6ca1\u6709\u7ed3\u675f\u8282\u70b9";
        }
        if (taskNodeCount == 0) {
            return "\u672a\u627e\u5230\u6d41\u7a0b\u5b9a\u4e49\u7684\u4eba\u5de5\u8282\u70b9";
        }
        List<WorkFlowLine> lines = this.getWorkflowLinesByLinkid(define.getLinkid());
        if (lines == null || lines.isEmpty()) {
            return "\u627e\u4e0d\u5230\u6d41\u7a0b\u5b9a\u4e49\u4e2d\u7684\u8f6c\u79fb\u7ebf";
        }
        HashMap<String, String[]> nodelineMap = new HashMap<String, String[]>();
        HashMap<String, ArrayList<WorkFlowLine>> nodeAndBeforeLine = new HashMap<String, ArrayList<WorkFlowLine>>();
        for (WorkFlowLine line : lines) {
            String conditionExecute = line.getConditionExecute();
            if (conditionExecute != null) {
                // empty if block
            }
            String bnode = line.getBeforeNodeID();
            String anode = line.getAfterNodeID();
            if (JqLib.isEmpty((String)anode) || JqLib.isEmpty((String)bnode) || nodeMap.get(anode) == null || nodeMap.get(bnode) == null) {
                return "\u8f6c\u79fb\u7ebf" + line.getTitle() + "(" + line.getCode() + ")\u4e24\u7aef\u9700\u8981\u6709\u6d41\u7a0b\u8282\u70b9";
            }
            ArrayList<WorkFlowLine> ls = (ArrayList<WorkFlowLine>)nodeAndBeforeLine.get(bnode);
            if (ls == null) {
                ls = new ArrayList<WorkFlowLine>();
            }
            ls.add(line);
            nodeAndBeforeLine.put(bnode, ls);
            String actionid = line.getActionid();
            boolean actionCheck = false;
            if (line.getBeforeNodeID().startsWith("Start")) {
                actionCheck = true;
            } else {
                List actions = (List)nodeActionMap.get(bnode);
                for (WorkFlowAction a : actions) {
                    if (!a.getId().equals(actionid)) continue;
                    actionCheck = true;
                }
            }
            if (!actionCheck) {
                return "\u8f6c\u79fb\u7ebf" + line.getTitle() + "(" + line.getCode() + ")\u4e0a\u7684\u6309\u94ae\u4e0e\u8282\u70b9\u6309\u94ae\u4e0d\u5339\u914d";
            }
            String[] convert1 = (String[])nodelineMap.get(bnode);
            if (convert1 == null) {
                convert1 = new String[2];
            }
            convert1[1] = line.getId();
            nodelineMap.put(bnode, convert1);
            String[] convert2 = (String[])nodelineMap.get(anode);
            if (convert2 == null) {
                convert2 = new String[2];
            }
            convert2[0] = line.getId();
            nodelineMap.put(anode, convert2);
        }
        for (String nodeid : nodeMap.keySet()) {
            String[] convert = (String[])nodelineMap.get(nodeid);
            if (convert == null) {
                node = (WorkFlowNodeSet)nodeMap.get(nodeid);
                return "\u8282\u70b9" + node.getTitle() + "(" + node.getCode() + ")\u627e\u4e0d\u5230\u524d\u9a71\u3001\u540e\u7ee7\u8f6c\u79fb";
            }
            if (JqLib.isEmpty((String)convert[0]) && !nodeid.equals(startNodeId)) {
                node = (WorkFlowNodeSet)nodeMap.get(nodeid);
                return "\u8282\u70b9" + node.getTitle() + "(" + node.getCode() + ")\u627e\u4e0d\u5230\u524d\u9a71\u8f6c\u79fb";
            }
            if (JqLib.isEmpty((String)convert[1]) && !nodeid.equals(endNodeId)) {
                node = (WorkFlowNodeSet)nodeMap.get(nodeid);
                return "\u8282\u70b9" + node.getTitle() + "(" + node.getCode() + ")\u627e\u4e0d\u5230\u540e\u7ee7\u8f6c\u79fb";
            }
            if (startNodeId.equals(nodeid) && !JqLib.isEmpty((String)convert[0])) {
                return "\u542f\u52a8\u8282\u70b9\u4e0d\u9700\u8981\u524d\u9a71\u8f6c\u79fb";
            }
            if (!endNodeId.equals(nodeid) || JqLib.isEmpty((String)convert[1])) continue;
            return "\u7ed3\u675f\u8282\u70b9\u4e0d\u9700\u8981\u540e\u7ee7\u8f6c\u79fb";
        }
        for (int i = 0; i < lines.size(); ++i) {
            String tempUnit = null;
            String mdim = lines.get(i).getMdim();
            if (mdim == null) continue;
            JSONObject parseObject = new JSONObject(mdim);
            Object entityId = parseObject.get("entityId");
            if (i != lines.size()) {
                if (entityId.equals(tempUnit)) continue;
                tempUnit = entityId.toString();
                continue;
            }
            if (entityId.equals(tempUnit)) continue;
            return "\u8f6c\u79fb\u7ebf" + lines.get(i).getTitle() + "\u4e0a\u914d\u7f6e\u7684\u4e3b\u4f53\u548c\u5176\u4ed6\u8f6c\u79fb\u7ebf\u4e0a\u7684\u4e3b\u4f53\u4e0d\u4e00\u81f4,\u8bf7\u6838\u5b9e\uff01";
        }
        for (String nodeid : nodeAndBeforeLine.keySet()) {
            boolean isStart;
            boolean isRepeat;
            List nodelines = (List)nodeAndBeforeLine.get(nodeid);
            if (nodelines.size() <= 1 || !(isRepeat = this.isNodeLineReportOrUnitRepeat(nodelines, isStart = (node = (WorkFlowNodeSet)nodeMap.get(nodeid)).getId().startsWith("Start")))) continue;
            return "\u8282\u70b9" + node.getTitle() + "(" + node.getCode() + ")\u8f6c\u79fb\u7ebf\u914d\u7f6e\u7684\u5355\u4f4d\u6216\u62a5\u8868\u91cd\u590d";
        }
        return "";
    }

    private boolean isNodeLineReportOrUnitRepeat(List<WorkFlowLine> nodelines, boolean isStart) {
        if (isStart) {
            return this._isRepeat(nodelines);
        }
        HashMap<String, ArrayList<WorkFlowLine>> actionLineMap = new HashMap<String, ArrayList<WorkFlowLine>>();
        for (WorkFlowLine line : nodelines) {
            String action = line.getActionid();
            ArrayList<WorkFlowLine> lines = (ArrayList<WorkFlowLine>)actionLineMap.get(action);
            if (lines == null) {
                lines = new ArrayList<WorkFlowLine>();
            }
            lines.add(line);
            actionLineMap.put(action, lines);
        }
        for (String action : actionLineMap.keySet()) {
            List ls = (List)actionLineMap.get(action);
            boolean isr = this._isRepeat(ls);
            if (!isr) continue;
            return true;
        }
        return false;
    }

    private boolean _isRepeat(List<WorkFlowLine> lines) {
        if (lines.size() < 2) {
            return false;
        }
        HashMap<String, List> mdimReportMap = new HashMap<String, List>();
        for (WorkFlowLine line : lines) {
            int i;
            JSONArray array;
            JSONObject json;
            boolean isAllmdim = line.isAllmdim();
            boolean isAllReport = line.isAllreport();
            String report = line.getReport();
            String mdim = line.getMdim();
            ArrayList<String> reports = new ArrayList<String>();
            if (!isAllReport && !JqLib.isEmpty((String)report)) {
                json = new JSONObject(report);
                array = json.getJSONArray("reportIds");
                for (i = 0; i < array.length(); ++i) {
                    reports.add(array.getString(i));
                }
            }
            if (isAllmdim) {
                for (String mdimid : mdimReportMap.keySet()) {
                    List rs = (List)mdimReportMap.get(mdimid);
                    for (String r : rs) {
                        if (r.equals("ALLREPORT")) {
                            return true;
                        }
                        if (!reports.contains(r)) continue;
                        return true;
                    }
                    if (!"ALLMDIM".equals(mdimid)) continue;
                    rs.addAll(reports);
                    mdimReportMap.put("ALLMDIM", rs);
                }
                if (mdimReportMap.keySet().contains("ALLMDIM")) continue;
                mdimReportMap.put("ALLMDIM", reports);
                continue;
            }
            if (JqLib.isEmpty((String)mdim)) continue;
            json = new JSONObject(mdim);
            array = json.getJSONArray("unitIds");
            for (i = 0; i < array.length(); ++i) {
                String mdimID = array.getString(i);
                List rs1 = (List)mdimReportMap.get(mdimID);
                List rs2 = (List)mdimReportMap.get("ALLMDIM");
                if (rs1 != null && !rs1.isEmpty()) {
                    for (String r : rs1) {
                        if (!reports.contains(r)) continue;
                        return true;
                    }
                }
                if (rs2 != null && !rs2.isEmpty()) {
                    for (String r : rs2) {
                        if (!reports.contains(r)) continue;
                        return true;
                    }
                }
                if (rs1 == null || rs1.isEmpty()) {
                    mdimReportMap.put(mdimID, reports);
                    continue;
                }
                HashSet<String> rset = new HashSet<String>();
                rset.addAll(rs1);
                rset.addAll(reports);
                mdimReportMap.put(mdimID, new ArrayList(rset));
            }
        }
        return false;
    }

    @Override
    public List<WorkFlowDefine> getWorkflowByState() {
        return this.runtimeCustomWorkflowDao.getAllWorkFlowDefineByState(1);
    }

    @Override
    public WorkFlowAction getWorkflowActionByCode(String actionCode, String linkId) {
        return this.runtimeCustomWorkflowDao.getWorkflowActionByCode(actionCode, linkId);
    }

    @Override
    public WorkFlowAction getWorkflowActionByCode(String nodeId, String actionCode, String linkId) {
        return this.runtimeCustomWorkflowDao.getWorkflowActionByCodeAndActionId(nodeId, actionCode, linkId);
    }

    @Override
    public List<WorkFlowLine> getWorkflowLinesByPreTask(String preTaskCode, String linkId) {
        if (null != preTaskCode && !preTaskCode.isEmpty()) {
            return this.runtimeCustomWorkflowDao.getWorkFlowLinesByPreTask(preTaskCode, linkId);
        }
        return new ArrayList<WorkFlowLine>();
    }

    @Override
    public List<WorkFlowLine> getWorkflowLinesByPreTask(String preTaskCode, String actionId, String linkId) {
        if (null != preTaskCode && !preTaskCode.isEmpty()) {
            return this.runtimeCustomWorkflowDao.getWorkFlowLinesByPreTaskAndAction(preTaskCode, actionId, linkId);
        }
        return new ArrayList<WorkFlowLine>();
    }

    @Override
    public WorkFlowLine getWorkflowLine(String beforeNodeId, String afterNodeId, String actionId, String linkId) {
        return this.runtimeCustomWorkflowDao.getWorkFlowLines(beforeNodeId, afterNodeId, actionId, linkId);
    }

    @Override
    public WorkFlowAction getWorkflowActionById(String id, String linkId) {
        return this.runtimeCustomWorkflowDao.getWorkflowActionById(id, linkId);
    }

    @Override
    public List<WorkFlowLine> getWorkflowLineByEndNode(String endNode, String linkId) {
        return this.runtimeCustomWorkflowDao.getWorkFlowLineByEndNode(endNode, linkId);
    }

    @Override
    public List<WorkFlowLine> getWorkflowLineByBNodeAndEndNode(List<String> bNode, String endNode, String linkId) {
        return this.runtimeCustomWorkflowDao.getWorkFlowLineByBNodeAndEndNode(bNode, endNode, linkId);
    }

    @Override
    public WorkFlowAction getWorlflowActionByCodeAndLinkId(String code, String linkId) {
        return this.runtimeCustomWorkflowDao.getWorkflowActionByCodeandLinkId(code, linkId);
    }

    @Override
    public List<WorkFlowAction> getRunWorkflowActionsByLinkid(String linkid) {
        if (null != linkid && !linkid.isEmpty()) {
            return this.runtimeCustomWorkflowDao.getWorkFlowActionsByLinkID(linkid);
        }
        return new ArrayList<WorkFlowAction>();
    }

    private void delDesignWorkflowInfo(String defineID) throws JQException {
        WorkFlowDefine workFlowDefine = this.designCustomWorkflowDao.getWorkFlowDefineByID(defineID, 0);
        if (workFlowDefine != null) {
            WorkFlowDefine workFlowDefineById;
            WorkFlowGroup workFlowGroup;
            workFlowDefine.setState(1);
            String linkid = workFlowDefine.getLinkid();
            ArrayList<String> lineIds = new ArrayList<String>();
            List<WorkFlowLine> workFlowLines = this.designCustomWorkflowDao.getWorkFlowLinesByLinkID(linkid);
            if (workFlowLines.size() > 0) {
                for (WorkFlowLine workFlowLine : workFlowLines) {
                    lineIds.add(workFlowLine.getId());
                }
                this.designCustomWorkflowDao.deleteWorkflowLine(lineIds, workFlowDefine.getLinkid());
            }
            ArrayList<String> nodeIds = new ArrayList<String>();
            List<WorkFlowNodeSet> workFlowNodeSets = this.designCustomWorkflowDao.getWorkFlowNodeSetsByLinkID(linkid);
            if (workFlowNodeSets.size() > 0) {
                for (WorkFlowNodeSet workFlowNodeSet : workFlowNodeSets) {
                    nodeIds.add(workFlowNodeSet.getId());
                }
                this.designCustomWorkflowDao.deleteWorkflowNode(nodeIds, workFlowDefine.getLinkid());
            }
            ArrayList<String> actionIds = new ArrayList<String>();
            List<WorkFlowAction> workFlowActions = this.designCustomWorkflowDao.getWorkFlowActionsByLinkID(linkid);
            if (workFlowActions.size() > 0) {
                for (WorkFlowAction workFlowAction : workFlowActions) {
                    actionIds.add(workFlowAction.getId());
                }
                this.designCustomWorkflowDao.deleteWorkflowAction(actionIds, workFlowDefine.getLinkid());
            }
            ArrayList<String> partiIds = new ArrayList<String>();
            List<WorkFlowParticipant> workFlowParticipants = this.designCustomWorkflowDao.getWorkFlowParticipantsByLinkID(linkid);
            if (workFlowParticipants.size() > 0) {
                for (WorkFlowParticipant workFlowParticipant : workFlowParticipants) {
                    partiIds.add(workFlowParticipant.getId());
                }
                this.designCustomWorkflowDao.deleteWorkflowParticipant(partiIds, workFlowDefine.getLinkid());
            }
            if ((workFlowGroup = this.designCustomWorkflowDao.getWorkFlowGroupByID(workFlowDefine.getParentID())) != null) {
                this.designCustomWorkflowDao.insertWorkflowGroup(workFlowGroup);
                this.designCustomWorkflowDao.delWorkFlowGroupByID(workFlowGroup.getId());
            }
            if ((workFlowDefineById = this.designCustomWorkflowDao.getWorkFlowDefineByID(defineID, 0)) != null) {
                this.designCustomWorkflowDao.deleteWorkflowById(defineID, 0);
            }
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u5220\u9664\u6d41\u7a0b\u5b9a\u4e49", (String)("\u5220\u9664\u6d41\u7a0b\u5b9a\u4e49\u540d\u79f0:" + workFlowDefine.getTitle()));
        }
    }

    private void delRuntimeWorkflowInfo(String defineID) throws JQException {
        WorkFlowDefine workFlowDefine = this.runtimeCustomWorkflowDao.getWorkFlowDefineByID(defineID, 1);
        if (workFlowDefine != null) {
            WorkFlowDefine workFlowDefineById;
            WorkFlowGroup workFlowGroup;
            String linkid = workFlowDefine.getLinkid();
            ArrayList<String> lineIds = new ArrayList<String>();
            List<WorkFlowLine> workFlowLines = this.runtimeCustomWorkflowDao.getWorkFlowLinesByLinkID(linkid);
            if (workFlowLines.size() > 0) {
                for (WorkFlowLine workFlowLine : workFlowLines) {
                    lineIds.add(workFlowLine.getId());
                }
                this.runtimeCustomWorkflowDao.deleteWorkflowLine(lineIds, workFlowDefine.getLinkid());
            }
            ArrayList<String> nodeIds = new ArrayList<String>();
            List<WorkFlowNodeSet> workFlowNodeSets = this.runtimeCustomWorkflowDao.getWorkFlowNodeSetsByLinkID(linkid);
            if (workFlowNodeSets.size() > 0) {
                for (WorkFlowNodeSet workFlowNodeSet : workFlowNodeSets) {
                    nodeIds.add(workFlowNodeSet.getId());
                }
                this.runtimeCustomWorkflowDao.deleteWorkflowNode(nodeIds, workFlowDefine.getLinkid());
            }
            ArrayList<String> actionIds = new ArrayList<String>();
            List<WorkFlowAction> workFlowActions = this.runtimeCustomWorkflowDao.getWorkFlowActionsByLinkID(linkid);
            if (workFlowActions.size() > 0) {
                for (WorkFlowAction workFlowAction : workFlowActions) {
                    actionIds.add(workFlowAction.getId());
                }
                this.runtimeCustomWorkflowDao.deleteWorkflowAction(actionIds, workFlowDefine.getLinkid());
            }
            ArrayList<String> partiIds = new ArrayList<String>();
            List<WorkFlowParticipant> workFlowParticipants = this.runtimeCustomWorkflowDao.getWorkFlowParticipantsByLinkID(linkid);
            if (workFlowParticipants.size() > 0) {
                for (WorkFlowParticipant workFlowParticipant : workFlowParticipants) {
                    partiIds.add(workFlowParticipant.getId());
                }
                this.runtimeCustomWorkflowDao.deleteWorkflowParticipant(partiIds, workFlowDefine.getLinkid());
            }
            if ((workFlowGroup = this.runtimeCustomWorkflowDao.getWorkFlowGroupByID(workFlowDefine.getParentID())) != null) {
                this.runtimeCustomWorkflowDao.insertWorkflowGroup(workFlowGroup);
                this.runtimeCustomWorkflowDao.delWorkFlowGroupByID(workFlowGroup.getId());
            }
            if ((workFlowDefineById = this.runtimeCustomWorkflowDao.getWorkFlowDefineByID(defineID, 1)) != null) {
                this.runtimeCustomWorkflowDao.deleteWorkflowById(defineID, 1);
            }
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u5220\u9664\u6d41\u7a0b\u5b9a\u4e49", (String)("\u5220\u9664\u6d41\u7a0b\u5b9a\u4e49\u540d\u79f0:" + workFlowDefine.getTitle()));
        }
    }

    @Override
    public List<WorkFlowAction> getAllWorkflowAction() {
        List<WorkFlowAction> allWorkflowAction = this.runtimeCustomWorkflowDao.getAllWorkflowAction();
        List unique = allWorkflowAction.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<WorkFlowAction>(Comparator.comparing(WorkFlowAction::getActionCode))), ArrayList::new));
        return unique;
    }

    @Override
    public List<WorkFlowLine> getWorkflowLinesByPreTask(Set<String> preTaskCode, String linkId) {
        return this.runtimeCustomWorkflowDao.getWorkFlowLinesByPreTask(preTaskCode, linkId);
    }

    @Override
    public List<WorkFlowNodeSet> getWorkFlowNodeSets(String linkId) {
        return this.runtimeCustomWorkflowDao.getWorkFlowNodeSetsByLinkID(linkId);
    }

    private boolean ignoreCheckEndEvent() {
        String systemStr = this.nvwaSystemOptionService.get(WORKFLOWID, WORKFLOW_END_NODE);
        return !"0".equals(systemStr);
    }

    public String copyWorkFlowDefine(String defineId) {
        WorkFlowDefineSaveEntity copyObj = new WorkFlowDefineSaveEntity();
        WorkFlowDefine workFlowDefine = this.designCustomWorkflowDao.getWorkFlowDefineByID(defineId, 0);
        String oldLinkId = workFlowDefine.getLinkid();
        workFlowDefine.setId(null);
        workFlowDefine.setLinkid(UUID.randomUUID().toString());
        copyObj.setDefine(workFlowDefine);
        List<WorkFlowNodeSet> workFlowNodeSets = this.designCustomWorkflowDao.getWorkFlowNodeSetsByLinkID(oldLinkId);
        workFlowNodeSets.forEach(nodeSet -> nodeSet.setLinkid(workFlowDefine.getLinkid()));
        copyObj.setCreat_nodes(workFlowNodeSets);
        List<WorkFlowParticipant> workFlowParticipants = this.designCustomWorkflowDao.getWorkFlowParticipantsByLinkID(oldLinkId);
        workFlowParticipants.forEach(participant -> participant.setLinkid(workFlowDefine.getLinkid()));
        copyObj.setCreat_participants(workFlowParticipants);
        List<WorkFlowAction> actions = this.designCustomWorkflowDao.getWorkFlowActionsByLinkID(oldLinkId);
        actions.forEach(action -> action.setLinkid(workFlowDefine.getLinkid()));
        copyObj.setCreat_actions(actions);
        List<WorkFlowLine> workFlowLines = this.designCustomWorkflowDao.getWorkFlowLinesByLinkID(oldLinkId);
        workFlowLines.forEach(line -> line.setLinkid(workFlowDefine.getLinkid()));
        copyObj.setCreat_lines(workFlowLines);
        try {
            return this.designCustomWorkflowDao.saveWorkflow(copyObj);
        }
        catch (JQException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WorkFlowTreeNode> getTaskAndWorkflows(WorkflowTreeParam workflowTreeParam) {
        ArrayList<WorkFlowTreeNode> taskNodes = new ArrayList<WorkFlowTreeNode>();
        List taskDefines = this.runTimeViewController.listAllTask();
        for (TaskDefine taskDefine : taskDefines) {
            WorkFlowTreeNode workFlowTreeNode = new WorkFlowTreeNode();
            workFlowTreeNode.setDisabled(true);
            workFlowTreeNode.setKey(taskDefine.getKey());
            workFlowTreeNode.setTitle(taskDefine.getTitle());
            workFlowTreeNode.setGroup(true);
            List<WorkFlowDefine> workFlowDefines = this.designCustomWorkflowDao.getWorkFlowDefineByTaskKey(taskDefine.getKey());
            if (workFlowDefines == null || workFlowDefines.size() <= 0) continue;
            ArrayList<WorkFlowTreeNode> childNodes = new ArrayList<WorkFlowTreeNode>();
            for (WorkFlowDefine workFlowDefine : workFlowDefines) {
                WorkFlowTreeNode childNode = this.getWorkFlowTreeNode(workflowTreeParam, workFlowDefine, workflowTreeParam.getTaskKey());
                childNodes.add(childNode);
            }
            workFlowTreeNode.setChildren(childNodes);
            taskNodes.add(workFlowTreeNode);
        }
        return taskNodes;
    }

    private WorkFlowTreeNode getWorkFlowTreeNode(WorkflowTreeParam workflowTreeParam, WorkFlowDefine workFlowDefine, String taskKey) {
        WorkFlowTreeNode childNode = new WorkFlowTreeNode();
        childNode.setKey(workFlowDefine.getId());
        childNode.setTitle(workFlowDefine.getTitle());
        childNode.setParentKey(taskKey);
        if (workFlowDefine.getTaskId().equals(workflowTreeParam.getTaskKey())) {
            childNode.setDisabled(true);
        }
        return childNode;
    }

    @Override
    public void saveTaskAndWorkflows(WorkflowTreeParam workflowTreeParam) {
        List<String> selectedKeys = workflowTreeParam.getSelectedKeys();
        for (String workflowKey : selectedKeys) {
            try {
                WorkFlowDefine workFlowDefineOld = this.getWorkFlowDefineByID(workflowKey, 0);
                String newWorkflowId = UUID.randomUUID().toString();
                String newLinkId = UUID.randomUUID().toString();
                this.copyCustomWorkflowNodes(workFlowDefineOld.getLinkid(), newLinkId);
                this.copyCustomWorkflowActions(workFlowDefineOld.getLinkid(), newLinkId, workflowTreeParam.getTaskKey());
                this.copyCustomWorkflowLines(workFlowDefineOld.getLinkid(), newLinkId);
                this.copyCustomWorkflowParticipants(workFlowDefineOld.getLinkid(), newLinkId);
                this.copyCustomWorkflowDefine(workFlowDefineOld, newWorkflowId, workflowTreeParam.getTaskKey(), newLinkId);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void copyCustomWorkflowDefine(WorkFlowDefine workFlowDefineOld, String newWorkflowId, String newTaskKey, String newLinkId) throws JQException {
        workFlowDefineOld.setId(newWorkflowId);
        workFlowDefineOld.setLinkid(newLinkId);
        workFlowDefineOld.setTaskId(newTaskKey);
        this.designCustomWorkflowDao.insertWorkflowDefine(workFlowDefineOld);
    }

    private void copyCustomWorkflowNodes(String oldLinkId, String newLinkId) throws JQException {
        List<WorkFlowNodeSet> workFlowNodeSetsByLinkID = this.designCustomWorkflowDao.getWorkFlowNodeSetsByLinkID(oldLinkId);
        for (WorkFlowNodeSet workFlowNodeSet : workFlowNodeSetsByLinkID) {
            workFlowNodeSet.setLinkid(newLinkId);
            this.designCustomWorkflowDao.insertWorkflowNode(workFlowNodeSet);
        }
    }

    private void copyCustomWorkflowActions(String oldLinkId, String newLinkId, String newTaskKey) throws JQException {
        List<WorkFlowAction> workFlowActionsByLinkID = this.designCustomWorkflowDao.getWorkFlowActionsByLinkID(oldLinkId);
        for (WorkFlowAction workFlowAction : workFlowActionsByLinkID) {
            workFlowAction.setLinkid(newLinkId);
            this.replaceActionExset(workFlowAction, newTaskKey);
            this.designCustomWorkflowDao.insertWorkflowAction(workFlowAction);
        }
    }

    private void copyCustomWorkflowLines(String oldLinkId, String newLinkId) throws JQException {
        List<WorkFlowLine> workFlowLinesByLinkID = this.designCustomWorkflowDao.getWorkFlowLinesByLinkID(oldLinkId);
        for (WorkFlowLine workFlowLine : workFlowLinesByLinkID) {
            workFlowLine.setLinkid(newLinkId);
            this.designCustomWorkflowDao.insertWorkflowLine(workFlowLine);
        }
    }

    private void copyCustomWorkflowParticipants(String oldLinkId, String newLinkId) throws JQException {
        List<WorkFlowParticipant> workFlowParticipantsByLinkID = this.designCustomWorkflowDao.getWorkFlowParticipantsByLinkID(oldLinkId);
        for (WorkFlowParticipant workFlowParticipant : workFlowParticipantsByLinkID) {
            workFlowParticipant.setLinkid(newLinkId);
            this.designCustomWorkflowDao.insertWorkflowParticipant(workFlowParticipant);
        }
    }

    private void replaceActionExset(WorkFlowAction workFlowAction, String newTaskKey) throws JQException {
        try {
            List formSchemeDefines = this.runTimeViewController.listFormSchemeByTask(newTaskKey);
            FormSchemeDefine formSchemeDefine = (FormSchemeDefine)formSchemeDefines.get(0);
            FormulaSchemeDefine defaultFormulaSchemeInFormScheme = this.formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(formSchemeDefine.getKey());
            String exset = workFlowAction.getExset();
            ObjectMapper objMapper = new ObjectMapper();
            if (exset != null) {
                HashMap<String, Object> newExset = new HashMap<String, Object>();
                Map object = (Map)objMapper.readValue(exset, (TypeReference)new TypeReference<Map<String, Object>>(){});
                if (object != null) {
                    Set oldExsetKeys = object.keySet();
                    for (String key : oldExsetKeys) {
                        ArrayList<String> formulaSchemes;
                        if (object.containsKey("needAutoCalculate") && key.equals("needAutoCalculate")) {
                            Boolean needAutoCalculate = WorkflowCustomServiceImpl.convertBoolean(object.get("needAutoCalculate"));
                            newExset.put("needAutoCalculate", needAutoCalculate);
                            if (needAutoCalculate == null || !object.containsKey("needAutoCalculateConf")) continue;
                            HashMap newCalcutateConf = new HashMap();
                            HashMap newFormulaSchemes = new HashMap();
                            ArrayList<String> formulaSchemes2 = new ArrayList<String>();
                            formulaSchemes2.add(defaultFormulaSchemeInFormScheme.getKey());
                            newFormulaSchemes.put(formSchemeDefine.getKey(), formulaSchemes2);
                            newCalcutateConf.put("formulaSchemes", newFormulaSchemes);
                            Object newCalCutateConfObj = objMapper.convertValue(newCalcutateConf, Object.class);
                            newExset.put("needAutoCalculateConf", newCalCutateConfObj);
                            continue;
                        }
                        if (object.containsKey("needAutoCheck") && key.equals("needAutoCheck")) {
                            Boolean needAutoCheck = WorkflowCustomServiceImpl.convertBoolean(object.get("needAutoCheck"));
                            newExset.put("needAutoCheck", needAutoCheck);
                            if (needAutoCheck == null || !object.containsKey("needAutoCheckConf")) continue;
                            HashMap needAutoCheckConfMap = new HashMap();
                            HashMap formulaSchemes3 = new HashMap();
                            ArrayList<String> formulaSchemesList = new ArrayList<String>();
                            formulaSchemesList.add(defaultFormulaSchemeInFormScheme.getKey());
                            formulaSchemes3.put(formSchemeDefine.getKey(), formulaSchemesList);
                            HashMap currencyConf = new HashMap();
                            Object needAutoCheckConf = object.get("needAutoCheckConf");
                            if (needAutoCheckConf != null && needAutoCheckConf instanceof HashMap) {
                                Map needAutoCheckConfMapOld = (Map)needAutoCheckConf;
                                Map currencyConfOld = (Map)needAutoCheckConfMapOld.get("currencyConf");
                                Object cusValue = currencyConfOld.get("cusValue");
                                Object currencyType = currencyConfOld.get("type");
                                currencyConf.put("cusValue", cusValue);
                                currencyConf.put("type", currencyType);
                                Object conditionFilter = needAutoCheckConfMapOld.get("dimFilterCondition");
                                if (conditionFilter != null) {
                                    needAutoCheckConfMap.put("dimFilterCondition", conditionFilter);
                                }
                            }
                            needAutoCheckConfMap.put("formulaSchemes", formulaSchemes3);
                            needAutoCheckConfMap.put("currencyConf", currencyConf);
                            Object newNeedAutoCheckConfObj = objMapper.convertValue(needAutoCheckConfMap, Object.class);
                            newExset.put("needAutoCheckConf", newNeedAutoCheckConfObj);
                            continue;
                        }
                        if (object.containsKey("needAutoCalculateFormSchemeKey") && key.equals("needAutoCalculateFormSchemeKey")) {
                            newExset.put("needAutoCalculateFormSchemeKey", formSchemeDefine.getKey());
                            continue;
                        }
                        if (object.containsKey("needAutoCalculateFormulaSchemes") && key.equals("needAutoCalculateFormulaSchemes")) {
                            formulaSchemes = new ArrayList<String>();
                            formulaSchemes.add(defaultFormulaSchemeInFormScheme.getKey());
                            newExset.put("needAutoCalculateFormulaSchemes", formulaSchemes);
                            continue;
                        }
                        if (object.containsKey("needAutoCheckFormSchemeKey") && key.equals("needAutoCheckFormSchemeKey")) {
                            newExset.put("needAutoCheckFormSchemeKey", formSchemeDefine.getKey());
                            continue;
                        }
                        if (object.containsKey("needAutoCheckFormulaSchemes") && key.equals("needAutoCheckFormulaSchemes")) {
                            formulaSchemes = new ArrayList();
                            formulaSchemes.add(defaultFormulaSchemeInFormScheme.getKey());
                            newExset.put("needAutoCheckFormulaSchemes", formulaSchemes);
                            continue;
                        }
                        if (object.containsKey("nodeCheck") && key.equals("nodeCheck")) {
                            Boolean nodeCheck = WorkflowCustomServiceImpl.convertBoolean(object.get("nodeCheck"));
                            newExset.put("nodeCheck", nodeCheck);
                            if (nodeCheck == null || !object.containsKey("nodeCheckConf")) continue;
                            HashMap nodeCheckConfMap = new HashMap();
                            HashMap currencyConf = new HashMap();
                            Object nodeCheckConf = object.get("nodeCheckConf");
                            if (nodeCheckConf != null && nodeCheckConf instanceof Map) {
                                Map nodeCheckConfMapOld = (Map)nodeCheckConf;
                                Map currencyConfOld = (Map)nodeCheckConfMapOld.get("currencyConf");
                                Object cusValue = currencyConfOld.get("cusValue");
                                Object currencyType = currencyConfOld.get("type");
                                currencyConf.put("cusValue", cusValue);
                                currencyConf.put("type", currencyType);
                            }
                            nodeCheckConfMap.put("currencyConf", currencyConf);
                            newExset.put("nodeCheckConf", nodeCheckConfMap);
                            continue;
                        }
                        newExset.put(key, object.get(key));
                    }
                }
                String newExsetStr = objMapper.writeValueAsString(newExset);
                workFlowAction.setExset(newExsetStr);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static Boolean convertBoolean(Object obj) {
        String str;
        Boolean valueOf = false;
        if (obj != null && null != (str = obj.toString()) && !"".equals(str)) {
            valueOf = Boolean.valueOf(str);
            return valueOf;
        }
        return valueOf;
    }

    private String getCurrencyValue(Object typeObj, Object cusValue) {
        int type = Integer.valueOf(typeObj.toString());
        if (ReportAuditType.CUSTOM.getValue() == type) {
            if (cusValue != null) {
                if (cusValue instanceof ArrayList) {
                    List cusArray = (List)cusValue;
                    return String.join((CharSequence)";", cusArray);
                }
                return "";
            }
            return "";
        }
        if (ReportAuditType.ESCALATION.getValue() == type) {
            return "PROVIDER_BASECURRENCY";
        }
        if (ReportAuditType.CONVERSION.getValue() == type) {
            return "PROVIDER_PBASECURRENCY";
        }
        return "";
    }

    class ComparatorList
    implements Comparator<Integer> {
        ComparatorList() {
        }

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 > o2 ? 1 : -1;
        }
    }
}

