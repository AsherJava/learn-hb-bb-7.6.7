/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.nr.bpm.setting.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.bpm.asynctask.ClearProcessAsyncTaskExecutor;
import com.jiuqi.nr.bpm.asynctask.StartProcessAsyncTaskExecutor;
import com.jiuqi.nr.bpm.asynctask.UnBindProcessAsyncTaskExecutor;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowParam;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.constant.SettingError;
import com.jiuqi.nr.bpm.setting.pojo.BaseData;
import com.jiuqi.nr.bpm.setting.pojo.DataParam;
import com.jiuqi.nr.bpm.setting.pojo.EntitryCount;
import com.jiuqi.nr.bpm.setting.pojo.ITreeNode;
import com.jiuqi.nr.bpm.setting.pojo.ProcessExcelParam;
import com.jiuqi.nr.bpm.setting.pojo.ProcessTrackExcelInfo;
import com.jiuqi.nr.bpm.setting.pojo.ProcessTrackPrintData;
import com.jiuqi.nr.bpm.setting.pojo.ReportData;
import com.jiuqi.nr.bpm.setting.pojo.ReportParam;
import com.jiuqi.nr.bpm.setting.pojo.SearchResult;
import com.jiuqi.nr.bpm.setting.pojo.ShowNodeParam;
import com.jiuqi.nr.bpm.setting.pojo.ShowNodeResult;
import com.jiuqi.nr.bpm.setting.pojo.ShowResult;
import com.jiuqi.nr.bpm.setting.pojo.StartState;
import com.jiuqi.nr.bpm.setting.pojo.StateChangeObj;
import com.jiuqi.nr.bpm.setting.pojo.WorkflowSettingPojo;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.setting.tree.grid.pojo.GridDataResult;
import com.jiuqi.nr.bpm.setting.tree.grid.pojo.IGridParam;
import com.jiuqi.nr.bpm.setting.tree.pojo.WorkflowData;
import com.jiuqi.nr.bpm.setting.tree.pojo.WorkflowTree;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value={"/api/workflow/setting"})
@Api(tags={"\u6d41\u7a0b\u914d\u7f6e"})
@JQRestController
public class WorkflowSettingWeb {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowSettingWeb.class);
    @Autowired
    public WorkflowSettingService workflowSettingService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private IDataentryFlowService dataentryFlowService;

    @RequestMapping(value={"/getTreeNodes"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u53ca\u5176\u4e0b\u5c5e\u62a5\u8868\u65b9\u6848")
    public List<ITreeNode> getTreeNodes() throws JQException {
        List<ITreeNode> treeNodes = null;
        try {
            treeNodes = this.workflowSettingService.getTaskByCondition();
            if (treeNodes.size() <= 0) {
                throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u6ca1\u6709\u9700\u8981\u7ed1\u5b9a\u6d41\u7a0b\u7684\u4efb\u52a1\uff01");
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u6ca1\u6709\u9700\u8981\u7ed1\u5b9a\u6d41\u7a0b\u7684\u4efb\u52a1\uff01");
        }
        return treeNodes;
    }

    @RequestMapping(value={"/getWorkflows"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u5168\u90e8\u5de5\u4f5c\u6d41")
    public List<WorkflowParam> getWrokflowByState() throws JQException {
        List<WorkflowParam> workflowByState = null;
        try {
            workflowByState = this.workflowSettingService.getAllWorkflowList();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u5de5\u4f5c\u6d41\u4fe1\u606f\u4e3a\u7a7a");
        }
        return workflowByState;
    }

    @RequestMapping(value={"/queryWorkflowDefines"}, method={RequestMethod.GET})
    @ApiOperation(value="\u67e5\u8be2\u6240\u6709\u7684\u6d41\u7a0b\u914d\u7f6e\u5b9a\u4e49")
    public List<WorkflowSettingDefine> queryWorkflowDefines() throws JQException {
        ArrayList<WorkflowSettingDefine> workflowSettingDefines = new ArrayList();
        try {
            workflowSettingDefines = this.workflowSettingService.queryWorkflowSettings();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u6d41\u7a0b\u914d\u7f6e\u4fe1\u606f\u4e3a\u7a7a");
        }
        return workflowSettingDefines;
    }

    @GetMapping(value={"/updateWorkflowSettingState"})
    @ApiOperation(value="\u6d41\u7a0b\u914d\u7f6e(\u542f\u7528\u548c\u505c\u7528)")
    public void updateWorkflowSettingState(@RequestParam(value="settingID") String settingId, @RequestParam(value="state") boolean state) {
    }

    @RequestMapping(value={"/queryBaseParam"}, method={RequestMethod.POST})
    @ApiOperation(value="\u67e5\u8be2\u57fa\u7840\u53c2\u6570")
    public BaseData queryBaseParam(@RequestBody DataParam paramData) throws JQException {
        BaseData baseDataParam = new BaseData();
        try {
            baseDataParam = this.workflowSettingService.queryBaseParam(paramData);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u57fa\u7840\u53c2\u6570\u6570\u636e\u4e3a\u7a7a");
        }
        return baseDataParam;
    }

    @RequestMapping(value={"/queryGridData"}, method={RequestMethod.POST})
    @ApiOperation(value="\u67e5\u8be2\u8868\u683c\u6811\u6570\u636e")
    public List<GridDataResult> queryGridData(@RequestBody IGridParam gridParam) throws JQException {
        ArrayList<GridDataResult> queryGridData = new ArrayList();
        try {
            queryGridData = this.workflowSettingService.queryGridData(gridParam);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u8868\u683c\u6570\u636e\u4e3a\u7a7a");
        }
        return queryGridData;
    }

    @RequestMapping(value={"/entityQuerySetCount"}, method={RequestMethod.POST})
    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u4e3b\u4f53\u6570\u91cf")
    public int entityQuerySetCount(@RequestBody EntitryCount entitryCount) throws JQException {
        int count = 0;
        try {
            if (null != entitryCount) {
                count = this.workflowSettingService.entityQuerySetCount(entitryCount);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u4e3b\u4f53\u6570\u91cf\u4e3a\u7a7a");
        }
        return count;
    }

    @GetMapping(value={"/refreshStrategicPartici"})
    @ApiOperation(value="\u5237\u65b0\u53c2\u4e0e\u8005")
    public void refreshStrategicPartici(@RequestParam(value="formSchemeKey") String formSchemeKey, @RequestParam(value="period") String period) throws JQException {
        if (null != formSchemeKey) {
            try {
                this.workflowSettingService.refreshStrategicPartici(formSchemeKey, period);
                this.workflowSettingService.getStrategicParticiLog(formSchemeKey);
            }
            catch (Exception e) {
                LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u5237\u65b0\u53c2\u4e0e\u8005", (String)"\u5237\u65b0\u53c2\u4e0e\u8005\u5931\u8d25");
                logger.error(e.getMessage(), e);
                throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u5237\u65b0\u53c2\u4e0e\u8005\u5931\u8d25");
            }
        }
    }

    @RequestMapping(value={"/startWorkflowState"}, method={RequestMethod.POST})
    @ApiOperation(value="\u5f00\u542f\u6d41\u7a0b")
    public StartState startWorkflowState(@RequestBody StateChangeObj stateChange) throws JQException {
        StartState startState = new StartState();
        try {
            startState = this.workflowSettingService.startDataObjs(stateChange);
        }
        catch (Exception e) {
            if (stateChange.isSelectAll()) {
                LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u5168\u90e8\u542f\u52a8\u6d41\u7a0b", (String)"\u5168\u90e8\u542f\u52a8\u6d41\u7a0b\u5931\u8d25");
            } else {
                LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u542f\u52a8\u6d41\u7a0b", (String)"\u542f\u52a8\u6d41\u7a0b\u5931\u8d25");
            }
            throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u542f\u52a8\u6d41\u7a0b\u5931\u8d25");
        }
        return startState;
    }

    @RequestMapping(value={"/claerStartWorkflowState"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6e05\u9664\u5f00\u542f\u6d41\u7a0b")
    public boolean clearStartWorkflowState(@RequestBody StateChangeObj stateChange) throws JQException {
        boolean isClear = false;
        try {
            isClear = this.workflowSettingService.clearDataObjs(stateChange);
        }
        catch (Exception e) {
            if (stateChange.isSelectAll()) {
                LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u5168\u90e8\u6e05\u9664", (String)"\u5168\u90e8\u6e05\u9664\u5931\u8d25");
            } else {
                LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u6e05\u9664\u542f\u52a8", (String)"\u6e05\u9664\u6d41\u7a0b\u5931\u8d25");
            }
            throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u6e05\u9664\u6d41\u7a0b\u5931\u8d25");
        }
        return isClear;
    }

    @PostMapping(value={"/saveWrokflowdSetting"})
    @ApiOperation(value="\u4fdd\u5b58\u6d41\u7a0b\u8bbe\u7f6e")
    public String saveWrokflowdSetting(@RequestBody WorkflowSettingPojo saveOjb) throws JQException {
        String settingId = "";
        try {
            settingId = this.workflowSettingService.saveWorkFlowSettingData(saveOjb);
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u4fdd\u5b58", (String)"\u4fdd\u5b58\u5931\u8d25");
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u4fdd\u5b58\u5931\u8d25");
        }
        return settingId;
    }

    @GetMapping(value={"/deleteWorkflowDataById"})
    @ApiOperation(value="\u5220\u9664\u6d41\u7a0b\u8bbe\u7f6e")
    public void deleteWorkflowDataById(@RequestParam(value="defineID") String settingId, @RequestParam(value="formSchemeId") String formSchemeId, @RequestParam(value="period") String period) throws JQException {
        if (null == settingId || settingId.isEmpty()) {
            return;
        }
        try {
            String error = this.workflowSettingService.deleteWorkflowSetting(settingId, formSchemeId, period);
            if (!error.isEmpty()) {
                LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u5220\u9664\u6d41\u7a0b\u65b9\u6848", (String)"\u5220\u9664\u5931\u8d25");
                throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u5220\u9664\u5931\u8d25");
            }
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u5220\u9664\u6d41\u7a0b\u65b9\u6848", (String)"\u5220\u9664\u6d41\u7a0b\u65b9\u6848\u5931\u8d25");
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u5220\u9664\u5931\u8d25");
        }
    }

    @PostMapping(value={"/showWorkflow"})
    @ApiOperation(value="\u6d41\u7a0b\u9884\u89c8")
    public ShowResult showWorkflow(@RequestBody ShowNodeParam nodeParam) throws JQException {
        ShowResult nodes = new ShowResult();
        try {
            nodes = this.workflowSettingService.showWorkflow(nodeParam);
            WorkFlowDefine workflowDefine = this.workflowSettingService.getWorkflowDefine(nodeParam.getFormSchemeKey(), nodes.getWorkflowId());
            String title = "\u9ed8\u8ba4\u6d41\u7a0b";
            if (workflowDefine != null) {
                title = workflowDefine.getTitle();
            }
            nodes.setTitle(title);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u52a0\u8f7d\u6570\u636e\u5931\u8d25");
        }
        return nodes;
    }

    @PostMapping(value={"/searchDefine"})
    @ApiOperation(value="\u67e5\u8be2")
    public List<SearchResult> searchDefine(String inputText) throws JQException {
        ArrayList<SearchResult> searchByInput = new ArrayList();
        try {
            searchByInput = this.workflowSettingService.searchByInput(inputText);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u67e5\u8be2\u6570\u636e\u7ed3\u679c\u4e3a\u7a7a");
        }
        return searchByInput;
    }

    @RequestMapping(value={"/exportdata"}, method={RequestMethod.POST})
    public void exportExcel(@RequestBody ProcessExcelParam processExcelParam, HttpServletResponse response) {
        this.workflowSettingService.exportExcel(response, processExcelParam);
    }

    public void printProcessTrack(@RequestBody List<ProcessTrackExcelInfo> list, HttpServletResponse response, HttpServletRequest request) {
        String agent = request.getHeader("User-Agent").toLowerCase();
        try {
            ProcessTrackPrintData result = this.workflowSettingService.printProcessTrack(list);
            String suffix = ".pdf";
            String fileName = "";
            fileName = result == null ? "\u6d41\u7a0b\u8ddf\u8e2a\u56fe" + suffix : result.getFileName() + suffix;
            if (agent.indexOf("firefox") >= 0) {
                fileName.replace(" ", "_");
            }
            String resultFileName = new String(fileName.getBytes(), "iso8859-1");
            response.setContentType("application/octet-stream");
            response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
            response.setHeader("Content-disposition", "attachment;filename=" + resultFileName);
            ServletOutputStream outputStream = response.getOutputStream();
            if (result == null) {
                outputStream.write(new byte[0]);
            } else {
                outputStream.write(result.getData());
            }
            response.flushBuffer();
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @CrossOrigin(value={"*"})
    @RequestMapping(value={"/printProcessTrack"})
    public void printProcessTrack1(String workflowtitle, String currNodeName, String date, String printUid, HttpServletResponse response, HttpServletRequest request) throws JQException {
        if (StringUtils.isEmpty((String)printUid)) {
            return;
        }
        try {
            String agent = request.getHeader("User-Agent").toLowerCase();
            this.cacheObjectResourceRemote = (CacheObjectResourceRemote)BeanUtil.getBean(CacheObjectResourceRemote.class);
            Object param = this.cacheObjectResourceRemote.find((Object)printUid);
            if (param != null && param instanceof ShowNodeParam) {
                ShowNodeParam nodeParam = (ShowNodeParam)param;
                List<ProcessTrackExcelInfo> list = this.getDataTable(workflowtitle, currNodeName, date, nodeParam);
                ProcessTrackPrintData result = this.workflowSettingService.printProcessTrack(list);
                String suffix = ".pdf";
                String fileName = "";
                fileName = result == null ? "\u6d41\u7a0b\u8ddf\u8e2a\u56fe" + suffix : result.getFileName() + suffix;
                if (agent.indexOf("firefox") >= 0) {
                    fileName.replace(" ", "_");
                }
                String resultFileName = new String(fileName.getBytes(), "iso8859-1");
                response.setContentType("application/pdf");
                response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
                response.setHeader("Content-disposition", "inline;filename=\"" + resultFileName + "\"");
                ServletOutputStream outputStream = response.getOutputStream();
                if (result == null) {
                    outputStream.write(new byte[0]);
                } else {
                    outputStream.write(result.getData());
                }
                response.flushBuffer();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value={"/preparePrintProcess"})
    @ApiOperation(value="\u9884\u6253\u5370\u6d41\u7a0b\u8ddf\u8e2a\uff0c\u6253\u5370\u53c2\u6570\u653e\u5230\u7f13\u5b58\u4e2d")
    private String preparePrintProcess(@RequestBody ShowNodeParam nodeParam) {
        String printUid = UUID.randomUUID().toString();
        this.cacheObjectResourceRemote = (CacheObjectResourceRemote)BeanUtil.getBean(CacheObjectResourceRemote.class);
        this.cacheObjectResourceRemote.create((Object)printUid, (Object)nodeParam);
        return printUid;
    }

    private List<ProcessTrackExcelInfo> getDataTable(String workflowtitle, String currNodeName, String date, ShowNodeParam nodeParam) throws JQException {
        ShowResult datas = new ShowResult();
        try {
            datas = this.showWorkflow(nodeParam);
        }
        catch (JQException e) {
            throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u52a0\u8f7d\u6570\u636e\u5931\u8d25");
        }
        ArrayList<ProcessTrackExcelInfo> dataTable = new ArrayList<ProcessTrackExcelInfo>();
        List<Object> finishDatas = new ArrayList();
        List<ShowNodeResult> timeDatas = datas.getNodeList();
        List<String> lineDatas = datas.getLineList();
        ShowNodeResult unFinishData = new ShowNodeResult();
        String defineId = datas.getWorkflowId();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean confrimed = datas.isConfrimed();
        boolean defaultProcess = false;
        if (defineId == null) {
            defaultProcess = true;
        }
        finishDatas = timeDatas.subList(0, timeDatas.size() - 1);
        ShowNodeResult endNode = timeDatas.get(timeDatas.size() - 1);
        if (defaultProcess) {
            if (confrimed) {
                ArrayList<String> actions = new ArrayList<String>();
                for (ShowNodeResult item : timeDatas) {
                    actions.add(item.getActionName());
                }
                boolean action = Arrays.asList(actions).contains("\u786e\u8ba4");
                unFinishData = action ? null : endNode;
            } else {
                unFinishData = endNode.getNodeId().equals("tsk_upload") ? endNode : null;
            }
        } else {
            boolean state = false;
            for (int i = 0; i < lineDatas.size(); ++i) {
                if (lineDatas.get(i).indexOf("End") == -1) continue;
                state = true;
                break;
            }
            unFinishData = endNode != null && endNode.getNodeId() != null && state ? endNode : null;
        }
        if (finishDatas != null && finishDatas.size() > 0) {
            for (int i = 0; i < finishDatas.size(); ++i) {
                ProcessTrackExcelInfo nodeObjTemp1 = new ProcessTrackExcelInfo();
                nodeObjTemp1.setNum(String.valueOf(i + 1));
                nodeObjTemp1.setNodeName(((ShowNodeResult)finishDatas.get(i)).getNodeName());
                if (((ShowNodeResult)finishDatas.get(i)).getActionName() != null && ((ShowNodeResult)finishDatas.get(i)).getActionCode() != null) {
                    nodeObjTemp1.setActionState("\u5b8c\u6210");
                } else {
                    nodeObjTemp1.setActionState("\u5f85\u6267\u884c");
                }
                nodeObjTemp1.setUser(((ShowNodeResult)finishDatas.get(i)).getUser());
                nodeObjTemp1.setActionName(((ShowNodeResult)finishDatas.get(i)).getActionName());
                nodeObjTemp1.setDesc(((ShowNodeResult)finishDatas.get(i)).getDesc());
                if (((ShowNodeResult)finishDatas.get(i)).getTime() != null) {
                    String time = ((ShowNodeResult)finishDatas.get(i)).getTime();
                    nodeObjTemp1.setTime(time);
                }
                dataTable.add(nodeObjTemp1);
            }
            ProcessTrackExcelInfo nodeObjTemp2 = new ProcessTrackExcelInfo();
            nodeObjTemp2.setNum(String.valueOf(dataTable.size() + 1));
            nodeObjTemp2.setNodeName(endNode.getNodeName());
            nodeObjTemp2.setUser(endNode.getActors());
            nodeObjTemp2.setActionState("\u5f85\u6267\u884c");
            nodeObjTemp2.setDesc(endNode.getDesc());
            if (endNode.getTime() != null) {
                nodeObjTemp2.setTime(simpleDateFormat.format(endNode.getTime()));
            }
            dataTable.add(nodeObjTemp2);
        } else {
            ProcessTrackExcelInfo nodeObjTemp3 = new ProcessTrackExcelInfo();
            nodeObjTemp3.setNum("1");
            nodeObjTemp3.setNodeName(endNode.getNodeName());
            nodeObjTemp3.setUser(endNode.getActors());
            nodeObjTemp3.setActionState("\u5f85\u6267\u884c");
            nodeObjTemp3.setDesc(endNode.getDesc());
            if (endNode.getTime() != null) {
                nodeObjTemp3.setTime(simpleDateFormat.format(endNode.getTime()));
            }
            dataTable.add(nodeObjTemp3);
        }
        ProcessTrackExcelInfo end = (ProcessTrackExcelInfo)dataTable.get(dataTable.size() - 1);
        String workflowInfo = workflowtitle + ',' + currNodeName + ',' + date;
        end.setWorkflowInfo(workflowInfo);
        dataTable.set(dataTable.size() - 1, end);
        return dataTable;
    }

    @PostMapping(value={"/queryReportData"})
    @ApiOperation(value="\u62a5\u8868\u53ca\u62a5\u8868\u5206\u7ec4")
    public List<ReportData> queryReportData(@RequestBody ReportParam reportParam) throws JQException {
        ArrayList<ReportData> reportData = new ArrayList();
        try {
            reportData = this.workflowSettingService.queryReportGroupData(reportParam);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return reportData;
    }

    @GetMapping(value={"/queryStartType"})
    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u4ee5\u4ec0\u4e48\u65b9\u5f0f\u5f00\u542f")
    public int queryStartType(@RequestParam(value="formSchemeKey") String formSchemekey) throws JQException {
        int type = 2;
        try {
            WorkFlowType queryStartType = this.workflowSettingService.queryStartType(formSchemekey);
            type = queryStartType.getValue();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return type;
    }

    @PostMapping(value={"/unBindProcess"})
    @ApiOperation(value="\u67d0\u4e2a\u8d44\u6e90\u4e0d\u7ed1\u5b9a\u6d41\u7a0b")
    public void unBindProcess(@RequestBody StateChangeObj stateChangeObj) throws JQException {
        try {
            this.workflowSettingService.unBindProcess(stateChangeObj);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @GetMapping(value={"/getWorkflowDefine"})
    @ApiOperation(value="\u83b7\u53d6\u5de5\u4f5c\u6d41\u5b9a\u4e49")
    public WorkFlowDefine getWorkflowDefine(@RequestParam(value="formSchemeKey") String formSchemeKey, @RequestParam(value="workflowId") String workflowId) throws JQException {
        WorkFlowDefine workflowDefine = new WorkFlowDefine();
        try {
            workflowDefine = this.workflowSettingService.getWorkflowDefine(formSchemeKey, workflowId);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u83b7\u53d6\u6d41\u7a0b\u5b9a\u4e49\u6570\u636e\u4e3a\u7a7a");
        }
        return workflowDefine;
    }

    @RequestMapping(value={"start-process"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u5f00\u542f\u6d41\u7a0b")
    public AsyncTaskInfo startProcess(@RequestBody StateChangeObj stateChange) throws InterruptedException {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)stateChange));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new StartProcessAsyncTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @RequestMapping(value={"clear-process"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u6e05\u9664\u6d41\u7a0b")
    public AsyncTaskInfo clearProcess(@RequestBody StateChangeObj stateChange) throws InterruptedException {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)stateChange));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new ClearProcessAsyncTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @RequestMapping(value={"nobind-process"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u4e0d\u7ed1\u5b9a\u6d41\u7a0b")
    public AsyncTaskInfo noBindProcess(@RequestBody StateChangeObj stateChangeObj) throws JQException {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)stateChangeObj));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new UnBindProcessAsyncTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @GetMapping(value={"/getAllTask"})
    @ApiOperation(value="\u83b7\u53d6\u5168\u90e8\u4efb\u52a1")
    public List<WorkflowTree<WorkflowData>> getAllTask(@RequestParam(value="formSchemeKey") String formSchemeKey, @RequestParam(value="searchId") String searchId) throws JQException {
        ArrayList<WorkflowTree<WorkflowData>> workflowList = new ArrayList();
        try {
            workflowList = this.workflowSettingService.getAllTask(formSchemeKey, searchId);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u83b7\u53d6\u6d41\u7a0b\u5b9a\u4e49\u6570\u636e\u4e3a\u7a7a");
        }
        return workflowList;
    }

    @GetMapping(value={"/isExistData"})
    @ApiOperation(value="\u83b7\u53d6\u5168\u90e8\u4efb\u52a1")
    public boolean isExistData(@RequestParam(value="taskKey") String taskKey) {
        return this.dataentryFlowService.isExistData(taskKey);
    }
}

