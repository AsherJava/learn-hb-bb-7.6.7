/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.controller.DesignTimeViewController
 *  com.jiuqi.nr.definition.paramcheck.EntityViewUpgradeService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  io.swagger.annotations.ApiParam
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.examine.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.controller.DesignTimeViewController;
import com.jiuqi.nr.definition.paramcheck.EntityViewUpgradeService;
import com.jiuqi.nr.examine.bean.ParamExamineDetailInfo;
import com.jiuqi.nr.examine.bean.ParamExamineInfo;
import com.jiuqi.nr.examine.common.ErrorType;
import com.jiuqi.nr.examine.common.ExamineStatus;
import com.jiuqi.nr.examine.common.ExamineType;
import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.common.RestoreStatus;
import com.jiuqi.nr.examine.service.ParamExamineDetailInfoService;
import com.jiuqi.nr.examine.service.ParamExamineInfoService;
import com.jiuqi.nr.examine.service.SpecialDeployService;
import com.jiuqi.nr.examine.task.ExamineTask;
import com.jiuqi.nr.examine.web.bean.ErrorInfo;
import com.jiuqi.nr.examine.web.bean.ErrorQueryVo;
import com.jiuqi.nr.examine.web.bean.ExamineData;
import com.jiuqi.nr.examine.web.bean.ExamineDetailInfoVO;
import com.jiuqi.nr.examine.web.bean.ExamineInfoVo;
import com.jiuqi.nr.examine.web.bean.ExamineNode;
import com.jiuqi.nr.examine.web.bean.ExamineNodeType;
import com.jiuqi.nr.examine.web.bean.ExamineTaskVo;
import com.jiuqi.nr.examine.web.bean.ParaTreeNode;
import com.jiuqi.nr.examine.web.bean.TaskInfoVO;
import com.jiuqi.nr.examine.web.service.ParamExamineAsyncTaskExecutor;
import com.jiuqi.nr.examine.web.service.ParamExamineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/paramcheck/"})
@Api(tags={"\u53c2\u6570\u68c0\u67e5"})
public class ParamExamineController {
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private ParamExamineDetailInfoService detailService;
    @Autowired
    private ParamExamineInfoService infoService;
    @Autowired
    private ParamExamineService service;
    @Autowired
    private DesignTimeViewController designTimeViewController;
    @Autowired
    private SpecialDeployService deployService;
    @Autowired
    private EntityViewUpgradeService entityViewUpgradeService;

    @ApiOperation(value="\u53c2\u6570\u68c0\u67e5", httpMethod="POST")
    @RequestMapping(value={"check_all"}, method={RequestMethod.POST})
    public String check(@RequestBody ExamineTaskVo checkTaskVo) {
        ExamineTask task = new ExamineTask();
        task.setParaType(ParaType.forValue(checkTaskVo.paraType));
        task.setKey(checkTaskVo.paraKey);
        task.setTaskType(checkTaskVo.examineType);
        task.setCheckInfoKey(UUIDUtils.getKey());
        NpRealTimeTaskInfo info = new NpRealTimeTaskInfo();
        info.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)task));
        this.infoService.insert(this.initExamineInfo(task));
        info.setAbstractRealTimeJob((AbstractRealTimeJob)new ParamExamineAsyncTaskExecutor());
        this.asyncTaskManager.publishTask(info, AsynctaskPoolType.ASYNCTASK_PARAMEXAMIE.getName());
        return task.getCheckInfoKey();
    }

    private ParamExamineInfo initExamineInfo(ExamineTask task) {
        ParamExamineInfo info = new ParamExamineInfo();
        info.setCheckDate(Calendar.getInstance().getTime());
        info.setKey(task.getCheckInfoKey());
        info.setStatus(ExamineStatus.CHECK);
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        info.setUserId(user.getId());
        return info;
    }

    @ApiOperation(value="\u53c2\u6570\u68c0\u67e5\u7ed3\u679c", httpMethod="GET")
    @RequestMapping(value={"list/{type}"}, method={RequestMethod.GET})
    public ExamineInfoVo list(@ApiParam(name="\u5c55\u793a\u6a21\u5f0f", value="1-\u9519\u8bef\u7c7b\u578b\uff0c2-\u53c2\u6570\u7c7b\u578b\uff0c3-\u4fee\u590d\u7c7b\u578b", required=true) @PathVariable int type) {
        ExamineInfoVo info = new ExamineInfoVo();
        info.datas = new ExamineData();
        info.datas.viewType = type;
        info.datas.nodes = new ArrayList<ExamineNode>();
        switch (type) {
            case 1: {
                this.showByErrorType(info);
                break;
            }
            case 2: {
                this.showByParaType(info);
                break;
            }
            case 3: {
                this.showByRestoreStatus(info);
            }
        }
        return info;
    }

    @ApiOperation(value="\u53c2\u6570\u68c0\u67e5\u9519\u8bef\u4fe1\u606f", httpMethod="POST")
    @RequestMapping(value={"error_type"}, method={RequestMethod.POST})
    public List<ErrorInfo> getErrorInfo(@RequestBody ErrorQueryVo errorQueryVo) {
        ArrayList<ErrorInfo> ret = new ArrayList<ErrorInfo>();
        ErrorType errorType = ErrorType.forValue(errorQueryVo.errorType);
        if (errorType == null) {
            ret.addAll(ErrorType.TASK_REFUSE_RUNTIME.getAll().stream().map(e -> new ErrorInfo((ErrorType)((Object)e))).collect(Collectors.toList()));
            return ret;
        }
        ErrorInfo errorInfo = new ErrorInfo(errorType);
        errorInfo.pathNode = this.service.getParaNode(errorQueryVo);
        ret.add(errorInfo);
        return ret;
    }

    @ApiOperation(value="\u83b7\u53d6\u53c2\u6570\u5c5e\u6027", httpMethod="GET")
    @RequestMapping(value={"para_tree"}, method={RequestMethod.GET})
    public List<ParaTreeNode> getParaTree() {
        ArrayList<ParaTreeNode> ret = new ArrayList<ParaTreeNode>();
        List<DesignTaskDefine> task = this.service.getTask();
        task.stream().forEach(t -> {
            ParaTreeNode node = new ParaTreeNode();
            node.setTitle(t.getTitle());
            node.setKey(t.getKey());
            node.setType(ParaType.TASK.getValue());
            ArrayList<ParaTreeNode> children = new ArrayList<ParaTreeNode>();
            node.setChildren(children);
            List<DesignFormSchemeDefine> schemeInTask = this.service.getSchemeInTask(t.getKey());
            node.setExpand(schemeInTask.size() > 0);
            schemeInTask.stream().forEach(s -> {
                ParaTreeNode cnode = new ParaTreeNode();
                cnode.setTitle(s.getTitle());
                cnode.setKey(s.getKey());
                cnode.setType(ParaType.FORMSCHEME.getValue());
                cnode.setChildren(Collections.emptyList());
                children.add(cnode);
            });
            ret.add(node);
        });
        return ret;
    }

    private void showByRestoreStatus(ExamineInfoVo info) {
        Map<ExamineNodeType, ExamineNode> nodeMap = this.initNodesByRestoreStaus();
        ParamExamineInfo examineInfo = this.infoService.getLastExamineInfo();
        if (examineInfo == null) {
            info = new ExamineInfoVo();
            return;
        }
        String infoKey = examineInfo.getKey();
        List<ParamExamineDetailInfo> details = this.detailService.listAllRefuseByExamineInfoKey(infoKey);
        details.stream().forEach(detail -> {
            ExamineNode node = (ExamineNode)nodeMap.get((Object)this.getNodeType_restoreStatus((ParamExamineDetailInfo)detail));
            ExamineNode examineNode = node.getNode(this.getNodeType_errorType((ParamExamineDetailInfo)detail));
            examineNode.infos.add(this.convertInfoVo((ParamExamineDetailInfo)detail));
        });
        info.datas = this.convertToInfo(nodeMap);
        info.datas.viewType = 1;
        info.examineStatus = examineInfo.getStatus().getValue();
    }

    private void showByParaType(ExamineInfoVo info) {
        Map<ExamineNodeType, ExamineNode> nodeMap = this.initNodesByParaType();
        ParamExamineInfo examineInfo = this.infoService.getLastExamineInfo();
        if (examineInfo == null) {
            info = new ExamineInfoVo();
            return;
        }
        String infoKey = examineInfo.getKey();
        List<ParamExamineDetailInfo> details = this.detailService.listByExamineInfoKey(infoKey);
        details.stream().forEach(detail -> {
            ExamineNode node = (ExamineNode)nodeMap.get((Object)this.getNodeType_paraType((ParamExamineDetailInfo)detail));
            ExamineNode examineNode = node.getNode(this.getNodeType_errorType((ParamExamineDetailInfo)detail));
            examineNode.infos.add(this.convertInfoVo((ParamExamineDetailInfo)detail));
        });
        info.datas = this.convertToInfo(nodeMap);
        info.datas.viewType = 1;
        info.examineStatus = examineInfo.getStatus().getValue();
    }

    private void showByErrorType(ExamineInfoVo info) {
        Map<ExamineNodeType, ExamineNode> nodeMap = this.initNodesByErrorType();
        ParamExamineInfo examineInfo = this.infoService.getLastExamineInfo();
        if (examineInfo == null) {
            info = new ExamineInfoVo();
            return;
        }
        info.examineStatus = examineInfo.getStatus().getValue();
        String infoKey = examineInfo.getKey();
        List<ParamExamineDetailInfo> details = this.detailService.listByExamineInfoKey(infoKey);
        details.stream().forEach(detail -> {
            ExamineNode node = (ExamineNode)nodeMap.get((Object)this.getNodeType_errorType((ParamExamineDetailInfo)detail));
            ExamineNode examineNode = node.getNode(this.getNodeType_paraType((ParamExamineDetailInfo)detail));
            examineNode.infos.add(this.convertInfoVo((ParamExamineDetailInfo)detail));
        });
        info.datas = this.convertToInfo(nodeMap);
        info.datas.viewType = 1;
    }

    private ExamineData convertToInfo(Map<ExamineNodeType, ExamineNode> nodeMap) {
        ExamineData data = new ExamineData();
        data.nodes = new ArrayList<ExamineNode>();
        nodeMap.entrySet().stream().forEach(e -> {
            ExamineNode node = (ExamineNode)e.getValue();
            node.fixChildren();
            data.nodes.add(node);
        });
        return data;
    }

    private Map<ExamineNodeType, ExamineNode> initNodesByErrorType() {
        TreeMap<ExamineNodeType, ExamineNode> map = new TreeMap<ExamineNodeType, ExamineNode>();
        map.put(ExamineNodeType.REFUSE, this.initExamineNode(ExamineNodeType.REFUSE));
        map.put(ExamineNodeType.QUOTE, this.initExamineNode(ExamineNodeType.QUOTE));
        map.put(ExamineNodeType.ERROR, this.initExamineNode(ExamineNodeType.ERROR));
        return map;
    }

    private Map<ExamineNodeType, ExamineNode> initNodesByParaType() {
        TreeMap<ExamineNodeType, ExamineNode> map = new TreeMap<ExamineNodeType, ExamineNode>();
        map.put(ExamineNodeType.TASK, this.initExamineNode(ExamineNodeType.TASK));
        map.put(ExamineNodeType.FORMSCHEME, this.initExamineNode(ExamineNodeType.FORMSCHEME));
        map.put(ExamineNodeType.FORMGROUP, this.initExamineNode(ExamineNodeType.FORMGROUP));
        map.put(ExamineNodeType.FORM, this.initExamineNode(ExamineNodeType.FORM));
        map.put(ExamineNodeType.REGION, this.initExamineNode(ExamineNodeType.REGION));
        map.put(ExamineNodeType.LINK, this.initExamineNode(ExamineNodeType.LINK));
        map.put(ExamineNodeType.FORMULASCHEME, this.initExamineNode(ExamineNodeType.FORMULASCHEME));
        map.put(ExamineNodeType.FORMULA, this.initExamineNode(ExamineNodeType.FORMULA));
        map.put(ExamineNodeType.PRINTSCHEME, this.initExamineNode(ExamineNodeType.PRINTSCHEME));
        return map;
    }

    private Map<ExamineNodeType, ExamineNode> initNodesByRestoreStaus() {
        TreeMap<ExamineNodeType, ExamineNode> map = new TreeMap<ExamineNodeType, ExamineNode>();
        map.put(ExamineNodeType.UNRESTORE, this.initExamineNode(ExamineNodeType.UNRESTORE));
        map.put(ExamineNodeType.SUCCESS, this.initExamineNode(ExamineNodeType.SUCCESS));
        map.put(ExamineNodeType.FAILED, this.initExamineNode(ExamineNodeType.FAILED));
        return map;
    }

    private ExamineNode initExamineNode(ExamineNodeType nodeType) {
        ExamineNode node = new ExamineNode();
        node.children = new ArrayList<ExamineNode>();
        node.title = nodeType.getMsg();
        node.type = nodeType.getValue();
        return node;
    }

    private ExamineNodeType getNodeType_errorType(ParamExamineDetailInfo detail) {
        ExamineType examineType = detail.getExamineType();
        switch (examineType) {
            case REFUSE: {
                return ExamineNodeType.REFUSE;
            }
            case QUOTE: {
                return ExamineNodeType.QUOTE;
            }
        }
        return ExamineNodeType.ERROR;
    }

    private ExamineNodeType getNodeType_paraType(ParamExamineDetailInfo detail) {
        ParaType paraType = detail.getParaType();
        switch (paraType) {
            case FORMSCHEME: {
                return ExamineNodeType.FORMSCHEME;
            }
            case FORMULASCHEME: {
                return ExamineNodeType.FORMULASCHEME;
            }
            case FORMULA: {
                return ExamineNodeType.FORMULA;
            }
            case PRINTSCHEME: {
                return ExamineNodeType.PRINTSCHEME;
            }
            case FORMGROUP: {
                return ExamineNodeType.FORMGROUP;
            }
            case FORM: {
                return ExamineNodeType.FORM;
            }
            case REGION: {
                return ExamineNodeType.REGION;
            }
            case LINK: {
                return ExamineNodeType.LINK;
            }
        }
        return ExamineNodeType.TASK;
    }

    private ExamineNodeType getNodeType_restoreStatus(ParamExamineDetailInfo detail) {
        RestoreStatus status = detail.getRestoreStatus();
        switch (status) {
            case FAILED: {
                return ExamineNodeType.FAILED;
            }
            case IGNORE: {
                return ExamineNodeType.IGNORE;
            }
            case MARKSUCCESS: {
                return ExamineNodeType.MARKSUCCESS;
            }
            case SUCCESS: {
                return ExamineNodeType.SUCCESS;
            }
        }
        return ExamineNodeType.UNRESTORE;
    }

    private ExamineDetailInfoVO convertInfoVo(ParamExamineDetailInfo detail) {
        ExamineDetailInfoVO dInfo = new ExamineDetailInfoVO();
        dInfo.paraKey = detail.getParaKey();
        dInfo.errorTitle = detail.getErrorType().getDescription();
        dInfo.errorType = detail.getErrorType().getValue();
        dInfo.examineType = detail.getExamineType().getValue();
        dInfo.description = detail.getDescription();
        dInfo.paraTitle = "";
        dInfo.paraType = detail.getParaType().getValue();
        dInfo.restoreStatus = detail.getRestoreStatus() == null ? RestoreStatus.UNRESTORE.getValue() : detail.getRestoreStatus().getValue();
        return dInfo;
    }

    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u5217\u8868", httpMethod="GET")
    @RequestMapping(value={"getAllTask"}, method={RequestMethod.GET})
    public List<TaskInfoVO> getAllTask() {
        ArrayList<TaskInfoVO> result = new ArrayList<TaskInfoVO>();
        List allTaskDefines = this.designTimeViewController.getAllTaskDefines();
        allTaskDefines.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).forEach(task -> {
            TaskInfoVO taskInfo = new TaskInfoVO();
            taskInfo.setKey(task.getKey());
            taskInfo.setCode(task.getTaskCode());
            taskInfo.setTitle(task.getTitle());
            result.add(taskInfo);
        });
        return result;
    }

    @ApiOperation(value="\u6267\u884c\u9884\u53d1\u5e03\uff0c\u5e76\u83b7\u53d6DDL\u6587\u4ef6KEY")
    @RequestMapping(value={"preDeploy/{taskKey}"}, method={RequestMethod.GET})
    public List<String> preDeploy(@PathVariable(value="taskKey") String taskKey) {
        return this.deployService.preDeploy(taskKey);
    }

    @ApiOperation(value="\u53d1\u5e03")
    @RequestMapping(value={"deploy/{taskKey}"}, method={RequestMethod.GET})
    public void deploy(@PathVariable(value="taskKey") String taskKey) {
        this.deployService.doDeploy(taskKey);
    }

    @ApiOperation(value="\u8fc7\u6ee4\u6a21\u677f\u5347\u7ea7")
    @RequestMapping(value={"entityViewUpgrade"}, method={RequestMethod.GET})
    public void entityViewUpgrade() throws JQException {
        try {
            this.entityViewUpgradeService.upgrade();
        }
        catch (Exception e) {
            throw new JQException(new ErrorEnum(){

                public String getCode() {
                    return "8001";
                }

                public String getMessage() {
                    return "\u8fc7\u6ee4\u6a21\u677f\u5347\u7ea7\u5931\u8d25";
                }
            }, e.getMessage());
        }
    }
}

