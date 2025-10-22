/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.bpm.custom.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import com.jiuqi.nr.bpm.custom.common.WorkFlowInfoObj;
import com.jiuqi.nr.bpm.custom.dao.CustomWorkFolwDao;
import com.jiuqi.nr.bpm.custom.exception.WorkflowException;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class WorkFlowImportor {
    private static final Logger logger = LoggerFactory.getLogger(WorkFlowImportor.class);
    @Autowired
    @Qualifier(value="designDao")
    CustomWorkFolwDao designCustomWorkflowDao;
    @Autowired
    @Qualifier(value="runtimeDao")
    CustomWorkFolwDao runtimeCustomWorkflowDao;
    @Autowired
    IRunTimeViewController viewController;
    @Autowired
    UserService<User> userService;
    @Autowired
    RoleService roleService;

    public void importDataByExcel(WorkFlowDefine olddefine, MultipartFile file) throws JQException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            WorkFlowDefine define;
            WorkFlowInfoObj obj = (WorkFlowInfoObj)mapper.readValue(file.getBytes(), WorkFlowInfoObj.class);
            if (obj != null) {
                define = obj.getDefine();
                if (define == null) {
                    throw new JQException((ErrorEnum)WorkflowException.ERROR_NOTFOUND);
                }
                define.setParentID(olddefine.getParentID());
                define.setState(0);
                define.setOrder(olddefine.getOrder());
                define.setTitle(olddefine.getTitle());
                define.setCode(olddefine.getCode());
                define.setId(olddefine.getId());
                String linkid = olddefine.getLinkid();
                define.setLinkid(linkid);
                define.setTaskId(olddefine.getTaskId());
                List<WorkFlowAction> actions = obj.getActions();
                List<WorkFlowLine> lines = obj.getLines();
                List<WorkFlowNodeSet> nodesets = obj.getNodesets();
                List<WorkFlowParticipant> participant = obj.getParticis();
                try {
                    this.designCustomWorkflowDao.deleteWorkflowActionByLinkId(olddefine.getLinkid());
                    this.designCustomWorkflowDao.deleteWorkflowLineByLinkId(olddefine.getLinkid());
                    this.designCustomWorkflowDao.deleteWorkflowNodeByLinkId(olddefine.getLinkid());
                    this.designCustomWorkflowDao.deleteWorkflowParticipantByLinkId(olddefine.getLinkid());
                    this.designCustomWorkflowDao.deleteWorkflowById(olddefine.getId(), 0);
                    this.runtimeCustomWorkflowDao.deleteWorkflowActionByLinkId(olddefine.getLinkid());
                    this.runtimeCustomWorkflowDao.deleteWorkflowLineByLinkId(olddefine.getLinkid());
                    this.runtimeCustomWorkflowDao.deleteWorkflowNodeByLinkId(olddefine.getLinkid());
                    this.runtimeCustomWorkflowDao.deleteWorkflowParticipantByLinkId(olddefine.getLinkid());
                    this.runtimeCustomWorkflowDao.deleteWorkflowById(olddefine.getId(), 1);
                }
                catch (Exception e) {
                    throw new JQException((ErrorEnum)WorkflowException.ERROR_FAIL);
                }
                try {
                    this.designCustomWorkflowDao.insertWorkflowDefine(define);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new JQException((ErrorEnum)WorkflowException.ERROR_INSERT);
                }
                Map<String, List<String>> actionsMap = this.getNodeActionsMap(actions);
                Map<String, List<String>> participantsMap = this.getNodeParticipantsMap(participant);
                HashMap<String, String> nodeIDMap = new HashMap<String, String>();
                HashMap<String, String> nodeIDTitleMap = new HashMap<String, String>();
                if (nodesets != null && !nodesets.isEmpty()) {
                    for (WorkFlowNodeSet node : nodesets) {
                        String oldID = node.getId();
                        nodeIDMap.put(oldID, node.getId());
                        nodeIDTitleMap.put(node.getId(), node.getTitle());
                        node.setId(node.getId());
                        node.setLinkid(linkid);
                        List<String> plist = participantsMap.get(oldID);
                        if (!oldID.startsWith("Start") && !oldID.startsWith("End")) {
                            String[] parray = new String[plist.size()];
                            plist.toArray(parray);
                            node.setPartis(parray);
                            List<String> aList = actionsMap.get(oldID);
                            if (aList != null) {
                                String[] aArray = new String[aList.size()];
                                aList.toArray(aArray);
                                node.setActions(aArray);
                            }
                        }
                        try {
                            this.designCustomWorkflowDao.insertWorkflowNode(node);
                        }
                        catch (Exception e) {
                            throw new JQException((ErrorEnum)WorkflowException.ERROR_INSERT);
                        }
                    }
                }
                if (lines != null && !lines.isEmpty()) {
                    for (WorkFlowLine l : lines) {
                        l.setId(l.getId());
                        l.setLinkid(linkid);
                        try {
                            this.designCustomWorkflowDao.insertWorkflowLine(l);
                        }
                        catch (Exception e) {
                            throw new JQException((ErrorEnum)WorkflowException.ERROR_INSERT);
                        }
                    }
                }
                if (participant != null && !participant.isEmpty()) {
                    for (WorkFlowParticipant p : participant) {
                        String newID = (String)nodeIDMap.get(p.getNodeid());
                        String nodeTitle = (String)nodeIDTitleMap.get(newID);
                        String warnning = this.checkNodeActor(p, nodeTitle);
                        p.setLinkid(linkid);
                        p.setNodeid(newID);
                        try {
                            this.designCustomWorkflowDao.insertWorkflowParticipant(p);
                        }
                        catch (Exception e) {
                            throw new JQException((ErrorEnum)WorkflowException.ERROR_INSERT);
                        }
                    }
                }
                if (actions != null && !actions.isEmpty()) {
                    for (WorkFlowAction action : actions) {
                        action.setLinkid(linkid);
                        action.setNodeid((String)nodeIDMap.get(action.getNodeid()));
                        try {
                            this.designCustomWorkflowDao.insertWorkflowAction(action);
                        }
                        catch (Exception e) {
                            throw new JQException((ErrorEnum)WorkflowException.ERROR_INSERT);
                        }
                    }
                }
            } else {
                LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u5bfc\u5165\u6d41\u7a0b", (String)"\u5bfc\u5165\u6d41\u7a0b\u5931\u8d25");
                throw new JQException((ErrorEnum)WorkflowException.ERROR_NOTFOUND);
            }
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u5bfc\u5165\u6d41\u7a0b", (String)("\u6d41\u7a0b\u540d\u79f0:" + define.getTitle()));
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRWORKFLOW.getTitle(), (String)"\u5bfc\u5165\u6d41\u7a0b", (String)"\u5bfc\u5165\u6d41\u7a0b\u5931\u8d25");
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private Map<String, List<String>> getNodeActionsMap(List<WorkFlowAction> actions) {
        HashMap<String, List<String>> actionsMap = new HashMap<String, List<String>>();
        for (WorkFlowAction a : actions) {
            ArrayList<String> ids = (ArrayList<String>)actionsMap.get(a.getNodeid());
            if (ids == null) {
                ids = new ArrayList<String>();
            }
            ids.add(a.getId());
            actionsMap.put(a.getNodeid(), ids);
        }
        return actionsMap;
    }

    private Map<String, List<String>> getNodeParticipantsMap(List<WorkFlowParticipant> participant) {
        HashMap<String, List<String>> participantsMap = new HashMap<String, List<String>>();
        for (WorkFlowParticipant p : participant) {
            ArrayList<String> ids = (ArrayList<String>)participantsMap.get(p.getNodeid());
            if (ids == null) {
                ids = new ArrayList<String>();
            }
            ids.add(p.getId());
            participantsMap.put(p.getNodeid(), ids);
        }
        return participantsMap;
    }

    private String checkLineSet(WorkFlowLine line) {
        String warnning = "";
        warnning = warnning + this.checkLineReportSet(line);
        warnning = warnning + this.checkLineMBody(line);
        warnning = warnning + this.checkLineFormular(line);
        warnning = warnning + this.checkLineMessageUser(line);
        return warnning;
    }

    private String checkLineReportSet(WorkFlowLine line) {
        if (line.isAllreport()) {
            return "";
        }
        String report = line.getReport();
        StringBuffer sb = new StringBuffer();
        if (report != null && !report.isEmpty()) {
            JSONObject js = new JSONObject(report);
            JSONArray array = js.getJSONArray("reportIds");
            for (String id : array) {
                FormDefine form = this.viewController.queryFormById(id);
                if (form != null) continue;
                sb.append("\u672a\u627e\u5230\"" + line.getTitle() + "\"\u4e0a\u9009\u62e9\u7684\u62a5\u8868\uff0c\u8bf7\u91cd\u65b0\u8bbe\u7f6e\u3002");
                break;
            }
        }
        return sb.toString();
    }

    private String checkLineMBody(WorkFlowLine line) {
        if (line.isAllmdim()) {
            return "";
        }
        String mdim = line.getMdim();
        StringBuffer sb = new StringBuffer();
        if (mdim != null && !mdim.isEmpty()) {
            JSONObject js = new JSONObject(mdim);
            String string = js.getString("entityId");
        }
        return sb.toString();
    }

    private String checkLineFormular(WorkFlowLine line) {
        String formular = line.getFormula();
        StringBuffer sb = new StringBuffer();
        if (formular != null && !formular.isEmpty()) {
            ReportFormulaParser parse = ReportFormulaParser.getInstance();
            try {
                parse.parseCond(formular, (IContext)new ParserContext());
            }
            catch (ParseException e) {
                logger.error(e.getMessage(), e);
                return "\"" + line.getTitle() + "\"\u4e0a\u8bbe\u7f6e\u7684\u516c\u5f0f\u89e3\u6790\u5931\u8d25\uff0c\u8bf7\u91cd\u65b0\u8bbe\u7f6e\u3002";
            }
        }
        return sb.toString();
    }

    private String checkLineMessageUser(WorkFlowLine line) {
        StringBuffer sb = new StringBuffer();
        Map<String, Object> map = line.getMsguser();
        if (map == null) {
            return "";
        }
        for (String key : map.keySet()) {
            String[] userIds;
            Object value = map.get(key);
            JSONObject jsonObject = new JSONObject(value.toString());
            String[] roleIds = (String[])jsonObject.get("roleIds");
            for (String one : userIds = (String[])jsonObject.get("userIds")) {
                ArrayList<String> roleids;
                List roles;
                User user = null;
                try {
                    user = this.userService.get(one);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if (user != null || (roles = this.roleService.getByIds(roleids = new ArrayList<String>(Arrays.asList(roleIds)))) != null && roles.size() > 0) continue;
                sb.append("\u672a\u627e\u5230\"" + line.getTitle() + "\"\u4e0a\u8bbe\u7f6e\u7684\u6d88\u606f\u63a5\u6536\u4eba\uff0c\u8bf7\u91cd\u65b0\u8bbe\u7f6e\u3002");
                return sb.toString();
            }
        }
        return "";
    }

    private String checkNodeActor(WorkFlowParticipant p, String title) {
        StringBuffer sb = new StringBuffer();
        String[] roleIds = p.getRoleIds();
        String[] userIds = p.getUserIds();
        if (roleIds != null && roleIds.length > 0) {
            for (String id : roleIds) {
                Optional role = this.roleService.get(id);
                if (role != null) continue;
                sb.append("\u672a\u627e\u5230\"" + title + "\"\u8282\u70b9\u4e0a\u8bbe\u7f6e\u7684\u53c2\u4e0e\u8005-\u89d2\u8272\uff0c\u8bf7\u91cd\u65b0\u8bbe\u7f6e\u3002");
                break;
            }
        }
        if (userIds != null && userIds.length > 0) {
            for (String id : userIds) {
                User user = this.userService.get(id);
                if (user != null) continue;
                sb.append("\u672a\u627e\u5230\"" + title + "\"\u8282\u70b9\u4e0a\u8bbe\u7f6e\u7684\u53c2\u4e0e\u8005-\u7528\u6237\uff0c\u8bf7\u91cd\u65b0\u8bbe\u7f6e\u3002");
                break;
            }
        }
        return sb.toString();
    }

    public class ParserContext
    implements IContext {
    }
}

