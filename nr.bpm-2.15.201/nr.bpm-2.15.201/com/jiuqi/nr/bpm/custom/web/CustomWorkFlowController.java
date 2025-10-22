/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.bpm.custom.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.action.ActionBase;
import com.jiuqi.nr.bpm.action.ActionProviderImpl;
import com.jiuqi.nr.bpm.condition.IConditionalExecute;
import com.jiuqi.nr.bpm.condition.IConditionalExecuteProvider;
import com.jiuqi.nr.bpm.custom.bean.ConditionExecute;
import com.jiuqi.nr.bpm.custom.bean.CurrencyDefine;
import com.jiuqi.nr.bpm.custom.bean.FormschemeData;
import com.jiuqi.nr.bpm.custom.bean.FormulaSchemeData;
import com.jiuqi.nr.bpm.custom.bean.QueryParticipantParam;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefineSaveEntity;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowGroup;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeActionSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowTreeNode;
import com.jiuqi.nr.bpm.custom.bean.WorkflowTreeParam;
import com.jiuqi.nr.bpm.custom.common.WorkFlowConstant;
import com.jiuqi.nr.bpm.custom.common.WorkFlowImportor;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.custom.service.CustomWorkflowConfigService;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.impl.Actor.ActorStrategyParameterType;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(value={"/api/custom/workflow"})
@Api(tags={"\u81ea\u5b9a\u4e49\u5de5\u4f5c\u6d41"})
@JQRestController
public class CustomWorkFlowController {
    private static final Logger logger = LoggerFactory.getLogger(CustomWorkFlowController.class);
    @Autowired
    private CustomWorkFolwService workFlowService;
    @Autowired
    private WorkFlowImportor importor;
    @Autowired
    private ActionProviderImpl actionProvider;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProcessEngineProvider processEngineProvider;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Autowired
    IConditionalExecuteProvider conditionalExecuteProvider;
    @Autowired
    private CustomWorkflowConfigService customWorkflowConfigService;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;

    @RequestMapping(value={"/getFirstLevelTNode"}, method={RequestMethod.GET})
    @ApiOperation(value="\u67e5\u8be2\u6240\u6709\u4e00\u7ea7\u6811\u8282\u70b9")
    public List<WorkFlowTreeNode> getFirstLevelTNode() {
        List<WorkFlowTreeNode> groups = null;
        try {
            groups = this.workFlowService.getFirstLevelTNode();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return groups;
    }

    @PostMapping(value={"/getFirstLevelTNodeByDefine"})
    @ApiOperation(value="\u67e5\u8be2\u6240\u6709\u5206\u7ec4,\u5c55\u5f00\u6307\u5b9a\u5b9a\u4e49\u7684\u5206\u7ec4")
    public List<WorkFlowTreeNode> getFirstLevelTNodeByDefine(@RequestBody WorkFlowDefine param) {
        List<WorkFlowTreeNode> groups = null;
        try {
            groups = this.workFlowService.getFirstLevelTNodeByDefine(param);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return groups;
    }

    @RequestMapping(value={"/searchDefineByinput/{input}"}, method={RequestMethod.GET})
    @ApiOperation(value="\u6839\u636e\u8f93\u5165\u67e5\u8be2\u6d41\u7a0b\u5b9a\u4e49")
    public List<WorkFlowDefine> searchDefineByinput(@PathVariable String input) {
        List<WorkFlowDefine> defines = null;
        try {
            defines = this.workFlowService.searchDefineByinput(input);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return defines;
    }

    @RequestMapping(value={"/getWorkFlowGroupTNodeByID/{groupID}"}, method={RequestMethod.GET})
    @ApiOperation(value="\u67e5\u8be2\u6307\u5b9a\u5206\u7ec4")
    public WorkFlowTreeNode getWorkFlowGroupTNodeByID(@PathVariable String groupID) {
        WorkFlowTreeNode group = null;
        try {
            group = this.workFlowService.getWorkFlowGroupTNodeByID(groupID);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return group;
    }

    @RequestMapping(value={"/getWorkFlowDefineTNodeByGroupID"}, method={RequestMethod.GET})
    @ApiOperation(value="\u67e5\u8be2\u5206\u7ec4\u4e0b\u7684\u6d41\u7a0b\u5b9a\u4e49")
    public List<WorkFlowTreeNode> getWorkFlowDefineTNodeByGroupID(@RequestParam(value="groupId") String groupID) {
        List<WorkFlowTreeNode> defines = null;
        try {
            defines = this.workFlowService.getWorkFlowDefineTNodeByGroupID(groupID);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return defines;
    }

    @GetMapping(value={"/getWorkFlowDefineTNodeByID"})
    @ApiOperation(value="\u67e5\u8be2\u6307\u5b9a\u7684\u6d41\u7a0b\u5b9a\u4e49")
    public WorkFlowTreeNode getWorkFlowDefineTNodeByID(@RequestParam(value="defineID") String defineID, @RequestParam(value="state") Integer state) {
        if (null == defineID || defineID.isEmpty() || null == state) {
            return null;
        }
        WorkFlowTreeNode define = null;
        try {
            define = this.workFlowService.getWorkFlowDefineTNodeByID(defineID, state);
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u6253\u5f00\u6d41\u7a0b", (String)"\u6253\u5f00\u6d41\u7a0b\u5931\u8d25");
            throw new RuntimeException(e.getMessage());
        }
        return define;
    }

    @GetMapping(value={"/getWorkFlowDefineXmlByID"})
    @ApiOperation(value="\u67e5\u8be2\u6307\u5b9a\u7684\u6d41\u7a0b\u5b9a\u4e49xml")
    public String getWorkFlowDefineXmlByID(@RequestParam(value="defineID") String defineID, @RequestParam(value="state") Integer state) {
        if (null == defineID || defineID.isEmpty() || null == state) {
            return null;
        }
        String result = null;
        try {
            result = this.workFlowService.getWorkFlowDefineXmlByID(defineID, state);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    @GetMapping(value={"/getDesignWorkFlowDefineXmlByID"})
    @ApiOperation(value="\u67e5\u8be2\u6307\u5b9a\u7684\u8bbe\u8ba1\u671f\u7684\u6d41\u7a0b\u5b9a\u4e49xml")
    public String getDesignWorkFlowDefineXmlByID(@RequestParam(value="defineID") String defineID, @RequestParam(value="state") Integer state) {
        if (null == defineID || defineID.isEmpty() || null == state) {
            return null;
        }
        String result = null;
        try {
            result = this.workFlowService.getWorkFlowDefineXmlByID(defineID, 0);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    @GetMapping(value={"/getWorkFlowNodeSetByID"})
    @ApiOperation(value="\u67e5\u8be2\u6307\u5b9a\u7684\u6d41\u7a0b\u8282\u70b9")
    public WorkFlowNodeSet getWorkFlowNodeByID(@RequestParam(value="nodeID") String nodeID, @RequestParam(value="linkId") String linkId) {
        WorkFlowNodeSet node = null;
        try {
            node = this.workFlowService.getDesignWorkFlowNodeSetByID(nodeID, linkId);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        if (node == null) {
            node = new WorkFlowNodeSet();
        }
        return node;
    }

    @GetMapping(value={"/getWorkFlowLineByID"})
    @ApiOperation(value="\u67e5\u8be2\u6307\u5b9a\u7684\u8f6c\u79fb\u7ebf")
    public WorkFlowLine getWorkFlowLineByID(@RequestParam(value="lineid") String lineid, @RequestParam(value="linkId") String linkId) {
        WorkFlowLine line = null;
        try {
            line = this.workFlowService.getWorkFlowLineByID(lineid, linkId);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        if (line == null) {
            line = new WorkFlowLine();
        }
        return line;
    }

    @GetMapping(value={"/getWorkFlowActions"})
    @ApiOperation(value="\u67e5\u8be2\u6307\u5b9a\u7684\u8282\u70b9\u7684\u6309\u94ae")
    public List<WorkFlowAction> getWorkFlowNodeActions(@RequestParam(value="nodeid") String nodeid, @RequestParam(value="linkId") String linkId) {
        List<WorkFlowAction> actions = null;
        try {
            actions = this.workFlowService.getWorkFlowNodeAction(nodeid, linkId);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        if (actions == null) {
            actions = new ArrayList<WorkFlowAction>();
        }
        return actions;
    }

    @GetMapping(value={"/getWorkFlowNodeParticipants"})
    @ApiOperation(value="\u67e5\u8be2\u6307\u5b9a\u7684\u8282\u70b9\u7684\u53c2\u4e0e\u8005")
    public List<WorkFlowParticipant> getWorkFlowNodeParticipants(@RequestParam(value="nodeid") String nodeid, @RequestParam(value="linkId") String linkId) {
        List<WorkFlowParticipant> plist = null;
        try {
            plist = this.workFlowService.getWorkFlowParticipant(nodeid, linkId);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        if (null == plist) {
            plist = new ArrayList<WorkFlowParticipant>();
        }
        return plist;
    }

    @PostMapping(value={"/getWorkFlowParticipantsByID"})
    @ApiOperation(value="\u67e5\u8be2\u6307\u5b9a\u7684\u8282\u70b9\u7684\u53c2\u4e0e\u8005")
    public List<WorkFlowParticipant> getWorkFlowParticipantsByID(@RequestBody QueryParticipantParam participantParam) {
        List<WorkFlowParticipant> plist = null;
        try {
            String[] idarray = participantParam.getIdarray();
            String linkId = participantParam.getLinkId();
            plist = this.workFlowService.getWorkFlowParticipantsByID(idarray, linkId);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        if (null == plist) {
            plist = new ArrayList<WorkFlowParticipant>();
        }
        return plist;
    }

    @GetMapping(value={"/getSysParticipantSchemes"})
    @ApiOperation(value="\u83b7\u53d6\u7cfb\u7edf\u4e2d\u6240\u6709\u7684\u53c2\u4e0e\u8005\u7b56\u7565")
    public List<ActorStrategy<?>> getSysParticipantSchemes() {
        List<ActorStrategy<?>> strategys = null;
        try {
            strategys = this.workFlowService.getSysParticipantSchemes();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return strategys;
    }

    @GetMapping(value={"/getSysParticipantSchemeByID"})
    @ApiOperation(value="\u83b7\u53d6\u7cfb\u7edf\u4e2d\u6307\u5b9a\u7684\u53c2\u4e0e\u8005\u7b56\u7565")
    public ActorStrategy<?> getSysParticipantSchemeByID(@RequestParam(value="strategytype") String strategytype) {
        if (strategytype == null || strategytype.isEmpty()) {
            throw new IllegalArgumentException("actorStrategyType can not be null or empty.");
        }
        ActorStrategy<?> scheme = null;
        try {
            scheme = this.workFlowService.getSysParticipantSchemeByID(strategytype);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return scheme;
    }

    @GetMapping(value={"/getActorStrategyParameterType"})
    @ApiOperation(value="\u83b7\u53d6\u7b56\u7565\u503c\u7c7b\u578b")
    public ActorStrategyParameterType getActorStrategyParameterType(@RequestParam(value="strategytype") String strategytype) {
        ActorStrategyParameterType scheme = null;
        if (strategytype == null || strategytype.isEmpty()) {
            throw new IllegalArgumentException("actorStrategyType can not be null or empty.");
        }
        try {
            scheme = this.workFlowService.getActorStrategyParameterType(strategytype);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return scheme;
    }

    @GetMapping(value={"/getSysFlowObject"})
    @ApiOperation(value="\u83b7\u53d6\u7cfb\u7edf\u6d41\u7a0b\u5bf9\u8c61")
    public String getSysFlowObject() {
        WorkFlowConstant.FLOWOBJ[] array = WorkFlowConstant.FLOWOBJ.values();
        List<Object> list = new ArrayList();
        if (null != array && array.length > 0) {
            list = Arrays.asList(array);
        }
        return ((Object)list).toString();
    }

    @GetMapping(value={"/getSysSoluObject"})
    @ApiOperation(value="\u83b7\u53d6\u7cfb\u7edf\u4e1a\u52a1\u5bf9\u8c61")
    public String getSysSoluObject() {
        WorkFlowConstant.SOLUOBJ[] array = WorkFlowConstant.SOLUOBJ.values();
        List<Object> list = new ArrayList();
        if (null != array && array.length > 0) {
            list = Arrays.asList(array);
        }
        return ((Object)list).toString();
    }

    @GetMapping(value={"/getWorkFlowDefineDefaultTitle"})
    @ApiOperation(value="\u83b7\u53d6\u6d41\u7a0b\u5b9a\u4e49\u9ed8\u8ba4\u6807\u9898")
    public String getWorkFlowDefineDefaultTitle() {
        String title = "";
        try {
            title = this.workFlowService.getWorkFlowDefineDefaultTitle();
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u65b0\u5efa\u6d41\u7a0b\u5b9a\u4e49", (String)("\u6d41\u7a0b\u5b9a\u4e49\u540d\u79f0\uff1a" + title));
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u65b0\u5efa\u6d41\u7a0b\u5b9a\u4e49", (String)"\u65b0\u5efa\u6d41\u7a0b\u5b9a\u4e49\u5931\u8d25");
            throw new RuntimeException(e.getMessage());
        }
        return title;
    }

    @GetMapping(value={"/isDefineTitleRepeat"})
    @ApiOperation(value="\u5224\u65ad\u5b9a\u4e49\u540d\u79f0\u662f\u5426\u91cd\u590d")
    public boolean isDefineTitleRepeat(@RequestParam(value="title") String title, @RequestParam(value="defineid") String defineid, @RequestParam(value="taskKey") String taskKey) {
        List<WorkFlowDefine> defines = this.workFlowService.getDefineByTitleAndTaskKey(title, taskKey);
        if (defines == null || defines.size() == 0) {
            return false;
        }
        for (WorkFlowDefine define : defines) {
            if (define.getId().equals(defineid)) continue;
            return true;
        }
        return false;
    }

    @GetMapping(value={"/isDefineCodeRepeat"})
    @ApiOperation(value="\u5224\u65ad\u5b9a\u4e49\u6807\u8bc6\u662f\u5426\u91cd\u590d")
    public boolean isDefineCodeRepeat(@RequestParam(value="code") String code, @RequestParam(value="defineid") String defineid) {
        List<WorkFlowDefine> defines = this.workFlowService.getDefineByCode(code);
        if (defines == null || defines.size() == 0) {
            return false;
        }
        for (WorkFlowDefine define : defines) {
            if (define.getId().equals(defineid)) continue;
            return true;
        }
        return false;
    }

    @GetMapping(value={"/isGroupTitleRepeat"})
    @ApiOperation(value="\u5224\u65ad\u5206\u7ec4\u540d\u79f0\u662f\u5426\u91cd\u590d")
    public boolean isGroupTitleRepeat(@RequestParam(value="title") String title, @RequestParam(value="groupid") String groupid) {
        WorkFlowGroup group = this.workFlowService.getGroupByTitle(title);
        return group != null && !group.getId().equals(groupid);
    }

    @GetMapping(value={"/getAllSysAction"})
    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u6309\u94ae")
    public List<ActionBase> getAllSysAction() {
        List<ActionBase> actions = this.actionProvider.getAllActionBase();
        return actions;
    }

    @GetMapping(value={"/getSysActionByID"})
    @ApiOperation(value="\u6839\u636eID\u83b7\u53d6\u7cfb\u7edf\u6ce8\u518c\u6309\u94ae")
    public ActionBase getSysActionByID(@RequestParam(value="actionid") String actionid) {
        ActionBase action = this.actionProvider.getActionBaseByID(actionid);
        return action;
    }

    @GetMapping(value={"/getSysActionByCode"})
    @ApiOperation(value="\u6839\u636eCode\u83b7\u53d6\u7cfb\u7edf\u6ce8\u518c\u6309\u94ae")
    public ActionBase getSysActionByCode(@RequestParam(value="actionCode") String actionCode) {
        ActionBase action = this.actionProvider.getActionBaseByCode(actionCode);
        return action;
    }

    @PostMapping(value={"/getActionSetStr"})
    @ApiOperation(value="\u83b7\u5f97\u6309\u94ae\u6269\u5c55\u5c5e\u6027\u96c6\u5408\u5b57\u7b26\u4e32\u6807\u8bc6")
    public String getActionSetStr(@RequestBody WorkFlowNodeActionSet actionSet) {
        try {
            String str = this.objectMapper.writeValueAsString((Object)actionSet);
            return str;
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping(value={"/creatWorkFlowGroup"})
    @ApiOperation(value="\u65b0\u5efa\u6d41\u7a0b\u5206\u7ec4")
    public WorkFlowTreeNode creatWorkFlowGroup(@RequestBody WorkFlowGroup param) {
        try {
            WorkFlowTreeNode obj = this.workFlowService.creatWorkFlowGroup(param);
            if (obj instanceof String) {
                LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u65b0\u5efa\u5b9a\u4e49\u5206\u7ec4", (String)"\u65b0\u5efa\u5b9a\u4e49\u5206\u7ec4\u5931\u8d25");
                throw new RuntimeException((String)((Object)obj));
            }
            if (obj instanceof WorkFlowGroup) {
                WorkFlowGroup group = (WorkFlowGroup)((Object)obj);
                WorkFlowTreeNode tnode = this.workFlowService.getWorkFlowTreeNode(group);
                LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u65b0\u5efa\u5b9a\u4e49\u5206\u7ec4", (String)("\u65b0\u5efa\u5b9a\u4e49\u5206\u7ec4\u540d\u79f0\uff1a" + tnode.getTitle()));
                return tnode;
            }
            if (obj instanceof WorkFlowTreeNode) {
                WorkFlowTreeNode tnode = obj;
                LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u65b0\u5efa\u5b9a\u4e49\u5206\u7ec4", (String)("\u65b0\u5efa\u5b9a\u4e49\u5206\u7ec4\u540d\u79f0\uff1a" + tnode.getTitle()));
                return tnode;
            }
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u65b0\u5efa\u5b9a\u4e49\u5206\u7ec4", (String)"\u65b0\u5efa\u5b9a\u4e49\u5206\u7ec4\u5931\u8d25");
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

    @PostMapping(value={"/updateWorkFlowGroup"})
    @ApiOperation(value="\u4fee\u6539\u6d41\u7a0b\u5206\u7ec4")
    public WorkFlowTreeNode updateWorkFlowGroup(@RequestBody WorkFlowGroup param) {
        try {
            Object obj = this.workFlowService.updateWorkFlowGroup(param);
            if (obj instanceof String) {
                LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u4fee\u6539\u5b9a\u4e49\u5206\u7ec4", (String)"\u4fee\u6539\u5b9a\u4e49\u5206\u7ec4\u5931\u8d25");
                throw new RuntimeException((String)obj);
            }
            if (obj instanceof WorkFlowGroup) {
                WorkFlowGroup define = (WorkFlowGroup)obj;
                WorkFlowTreeNode tnode = this.workFlowService.getWorkFlowTreeNode(define);
                LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u4fee\u6539\u5b9a\u4e49\u5206\u7ec4", (String)("\u4fee\u6539\u5b9a\u4e49\u5206\u7ec4\u540d\u79f0\uff1a" + tnode.getTitle()));
                return tnode;
            }
            WorkFlowTreeNode tnode = (WorkFlowTreeNode)obj;
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u4fee\u6539\u5b9a\u4e49\u5206\u7ec4", (String)("\u4fee\u6539\u5b9a\u4e49\u5206\u7ec4\u540d\u79f0\uff1a" + tnode.getTitle()));
            return tnode;
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u4fee\u6539\u5b9a\u4e49\u5206\u7ec4", (String)"\u4fee\u6539\u5b9a\u4e49\u5206\u7ec4\u5931\u8d25");
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping(value={"/saveWrokflowdDfine"})
    @ApiOperation(value="\u4fdd\u5b58\u6d41\u7a0b\u5b9a\u4e49")
    public String saveWrokflowdDfine(@RequestBody @SFDecrypt WorkFlowDefineSaveEntity saveOjb) throws JQException {
        String id = null;
        try {
            id = this.workFlowService.saveWrokflowdDfine(saveOjb);
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u4fdd\u5b58", (String)"\u4fdd\u5b58\u5931\u8d25");
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        return id;
    }

    @GetMapping(value={"/updateWorkFlowDefineTitle"})
    @ApiOperation(value="\u4fee\u6539\u6d41\u7a0b\u5b9a\u4e49\u6807\u9898")
    public WorkFlowTreeNode updateWorkFlowDefineTitle(@RequestParam(value="id") String id, @RequestParam(value="state") Integer state, @RequestParam(value="title") String title) {
        if (null == id || id.isEmpty() || null == state) {
            return null;
        }
        try {
            Object obj = this.workFlowService.updateWorkFlowDefineTitle(id, state, title);
            if (obj instanceof String) {
                LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u4fee\u6539\u6d41\u7a0b\u5b9a\u4e49", (String)"\u4fee\u6539\u6d41\u7a0b\u5b9a\u4e49\u5931\u8d25");
                throw new RuntimeException((String)obj);
            }
            if (obj instanceof WorkFlowDefine) {
                WorkFlowDefine define = (WorkFlowDefine)obj;
                WorkFlowTreeNode tnode = this.workFlowService.getWorkFlowTreeNode(define);
                LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u4fee\u6539\u6d41\u7a0b\u5b9a\u4e49", (String)("\u4fee\u6539\u6d41\u7a0b\u5b9a\u4e49\u540d\u79f0\uff1a" + tnode.getTitle()));
                return tnode;
            }
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u4fee\u6539\u6d41\u7a0b\u5b9a\u4e49", (String)"\u4fee\u6539\u6d41\u7a0b\u5b9a\u4e49\u5931\u8d25");
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

    @GetMapping(value={"/designWorkFlowDefine"})
    @ApiOperation(value="\u8bbe\u8ba1\u6d41\u7a0b\u5b9a\u4e49")
    public WorkFlowTreeNode designWorkFlowDefine(@RequestParam(value="defineID") String defineID, @RequestParam(value="state") Integer state) {
        if (null == defineID || defineID.isEmpty() || null == state) {
            return null;
        }
        try {
            if (state == 0) {
                return this.workFlowService.getWorkFlowDefineTNodeByID(defineID, state);
            }
            WorkFlowDefine define = this.workFlowService.getWorkFlowDefineByID(defineID, 0);
            if (define != null) {
                return this.workFlowService.getWorkFlowTreeNode(define);
            }
            define = this.workFlowService.cloneMaintenanceWorkFlowDefine(defineID, state);
            return this.workFlowService.getWorkFlowDefineTNodeByID(defineID, state);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping(value={"/releaseWorkFlowDefine"})
    @ApiOperation(value="\u53d1\u5e03\u6d41\u7a0b\u5b9a\u4e49")
    public WorkFlowTreeNode releaseWorkFlowDefine(@RequestParam(value="defineID") String defineID, @RequestParam(value="state") Integer state) {
        try {
            state = 0;
            WorkFlowDefine releaseDefine = this.workFlowService.releaseWorkFlowDefine(defineID, state);
            if (releaseDefine != null) {
                LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u53d1\u5e03\u6d41\u7a0b", (String)(releaseDefine.getCode() + "|" + releaseDefine.getTitle()));
                return this.workFlowService.getWorkFlowTreeNode(releaseDefine);
            }
            return null;
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u53d1\u5e03\u6d41\u7a0b", (String)"\u53d1\u5e03\u6d41\u7a0b\u5931\u8d25");
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping(value={"/checkBeforeRelease"})
    @ApiOperation(value="\u6d41\u7a0b\u53d1\u5e03\u524d\u68c0\u67e5")
    public String checkBeforeRelease(@RequestParam(value="defineID") String defineID, @RequestParam(value="state") Integer state) {
        try {
            WorkFlowDefine define = this.workFlowService.getWorkFlowDefineByID(defineID, state);
            if (define != null) {
                String errorMsg = this.workFlowService.checkBeforeRelease(define);
                return errorMsg;
            }
            return "\u627e\u4e0d\u5230\u6307\u5b9a\u7684\u6d41\u7a0b\u5b9a\u4e49";
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping(value={"/delWorkFlowDefineByID"})
    @ApiOperation(value="\u5220\u9664\u6307\u5b9a\u7684\u6d41\u7a0b\u5b9a\u4e49")
    public void delWorkFlowDefineByID(@RequestParam(value="defineID") String defineID, @RequestParam(value="state") Integer state) throws JQException {
        this.workFlowService.delWorkFlowDefineByID(defineID, state);
    }

    @RequestMapping(value={"/delWorkFlowGroupByID"}, method={RequestMethod.GET})
    @ApiOperation(value="\u5220\u9664\u6307\u5b9a\u7684\u6d41\u7a0b\u5206\u7ec4")
    public void delWorkFlowGroupByID(String groupID) {
        try {
            this.workFlowService.delWorkFlowGroupByID(groupID);
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u5220\u9664\u5b9a\u4e49\u5206\u7ec4", (String)"\u5220\u9664\u5b9a\u4e49\u5206\u7ec4\u5931\u8d25");
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @RequestMapping(value={"/exportWorkFlowDefine/{defineid}/{state}"}, method={RequestMethod.GET})
    @ApiOperation(value="\u5bfc\u51fa\u6307\u5b9a\u6d41\u7a0b\u5b9a\u4e49")
    public void exportWorkFlowDefine(@PathVariable(value="defineid") String defineid, @PathVariable(value="state") Integer state, HttpServletResponse response) throws IOException {
        String error = this.workFlowService.exportWorkFlowDefine(defineid, state, response);
        if (null != error && !error.isEmpty()) {
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u5bfc\u51fa\u6d41\u7a0b", (String)"\u5bfc\u51fa\u6d41\u7a0b\u5931\u8d25");
            throw new RuntimeException(error);
        }
        response.flushBuffer();
    }

    @PostMapping(value={"/importWorkFlowDefine"})
    @ApiOperation(value="\u5bfc\u5165\u6307\u5b9a\u6d41\u7a0b\u5b9a\u4e49")
    public void importWorkFlowDefine(@RequestParam(value="file") MultipartFile file, @RequestParam(value="defineID") String defineID, @RequestParam(value="state") Integer state, HttpServletRequest request) throws IOException {
        WorkFlowDefine define = this.workFlowService.getWorkFlowDefineByID(defineID, state);
        if (define == null) {
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u5bfc\u5165\u6d41\u7a0b", (String)"\u5bfc\u5165\u6d41\u7a0b\u5931\u8d25");
            throw new RuntimeException("dfadfsadfsadf");
        }
        try {
            this.importor.importDataByExcel(define, file);
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u5bfc\u5165\u6d41\u7a0b", (String)"\u5bfc\u5165\u6d41\u7a0b\u5931\u8d25");
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping(value={"/getAction"})
    @ApiOperation(value="\u83b7\u53d6\u6307\u5b9a\u6309\u94ae\u4fe1\u606f")
    public void getAction(String actionCode) {
        String linkId = null;
        this.workFlowService.getWorkflowActionByCode(actionCode, linkId);
    }

    @PostMapping(value={"/getCheckFormula"})
    @ApiOperation(value="\u83b7\u53d6\u516c\u5f0f\u5ba1\u6838\u7c7b\u578b")
    public List<AuditType> getCheckFormula() {
        ArrayList<AuditType> auditTypeList = new ArrayList();
        try {
            auditTypeList = this.auditTypeDefineService.queryAllAuditType();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return auditTypeList;
    }

    @PostMapping(value={"/getConditionExecute"})
    @ApiOperation(value="\u83b7\u53d6\u6761\u4ef6\u6267\u884c\u5668")
    public List<ConditionExecute> getConditionExecute() {
        ArrayList<ConditionExecute> allConditionalExecute = new ArrayList<ConditionExecute>();
        ConditionExecute conditionExecute = null;
        try {
            List<IConditionalExecute> conditionalExecute = this.conditionalExecuteProvider.getAllConditionalExecute();
            for (IConditionalExecute iConditionalExecute : conditionalExecute) {
                conditionExecute = new ConditionExecute();
                String title = iConditionalExecute.getTitle();
                String className = iConditionalExecute.className();
                conditionExecute.setCode(className);
                conditionExecute.setTitle(title);
                allConditionalExecute.add(conditionExecute);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return allConditionalExecute;
    }

    @PostMapping(value={"/xmlFlie"})
    @ApiOperation(value="xml\u8bfb\u53d6")
    public void convertFlie() {
    }

    @GetMapping(value={"/queryFormSchemeDatas"})
    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u4e0b\u7684\u6240\u6709\u62a5\u8868\u65b9\u6848")
    public List<FormschemeData> queryFormSchemeDatas(@RequestParam(value="taskKey") String taskKey) {
        return this.customWorkflowConfigService.queryFormSchemeDefines(taskKey);
    }

    @GetMapping(value={"/queryFormulaSchemeDefines"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u5168\u90e8\u975e\u8d22\u52a1\u516c\u5f0f\u65b9\u6848")
    public List<FormulaSchemeData> queryFormulaSchemeDefines(@RequestParam(value="formSchemeKey") String formSchemeKey) {
        return this.customWorkflowConfigService.queryFormulaSchemeDefines(formSchemeKey);
    }

    @GetMapping(value={"/queryCurrencyDefines"})
    @ApiOperation(value="\u83b7\u53d6\u5e01\u79cd\u96c6\u5408")
    public List<CurrencyDefine> queryCurrencyDefines() {
        try {
            return this.customWorkflowConfigService.queryCurrencyData();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value={"/queryWorkflowByTask"})
    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u4e0b\u7684\u6240\u6709\u62a5\u8868\u65b9\u6848")
    public List<WorkFlowTreeNode> queryWorkflowByTask(@RequestParam(value="taskKey") String taskKey) {
        return this.customWorkflowConfigService.queryWorkflowByTaskKey(taskKey);
    }

    @GetMapping(value={"/enableCurrency"})
    @ApiOperation(value="\u5e01\u79cd\u914d\u7f6e\u662f\u5426\u663e\u793a")
    public boolean enableCurrency(@RequestParam(value="taskKey") String taskKey) {
        return this.customWorkflowConfigService.enableCurrency(taskKey);
    }

    @GetMapping(value={"/showForceControlButton"})
    @ApiOperation(value="\u5f3a\u63a7\u6309\u94ae\u662f\u5426\u663e\u793a")
    public boolean showForceControlButton(@RequestParam(value="taskKey") String taskKey) {
        return this.workFlowDimensionBuilder.enableTwoTree(taskKey);
    }

    @PostMapping(value={"/getTaskAndWorkflows"})
    @ApiOperation(value="\u4efb\u52a1\u53ca\u5173\u8054\u7684\u6d41\u7a0b\u5b9a\u4e49")
    public List<WorkFlowTreeNode> getTaskAndWorkflows(@RequestBody WorkflowTreeParam workflowTreeParam) {
        return this.workFlowService.getTaskAndWorkflows(workflowTreeParam);
    }

    @PostMapping(value={"saveTaskAndWorkflows"})
    @ApiOperation(value="\u4fdd\u5b58\u4efb\u52a1\u548c\u6240\u9009\u7684\u6d41\u7a0b\u5b9a\u4e49")
    public void saveTaskAndWorkflows(@RequestBody WorkflowTreeParam workflowTreeParam) {
        this.workFlowService.saveTaskAndWorkflows(workflowTreeParam);
    }
}

