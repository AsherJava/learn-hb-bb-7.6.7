/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.crypto.Crypto
 *  com.jiuqi.nr.common.exception.NrCommonException
 *  com.jiuqi.nr.dataentry.service.IBatchCalculateService
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.intergration.sdk.ticket.bean.Ticket
 *  com.jiuqi.nvwa.intergration.sdk.ticket.bean.TicketToken
 *  com.jiuqi.nvwa.ticket.service.TicketService
 *  javax.annotation.Resource
 *  org.json.JSONObject
 */
package com.jiuqi.nr.etl.service.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.crypto.Crypto;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.etl.common.ETLTask;
import com.jiuqi.nr.etl.common.EtlExecuteInfo;
import com.jiuqi.nr.etl.common.EtlExecuteParam;
import com.jiuqi.nr.etl.common.EtlInfo;
import com.jiuqi.nr.etl.common.EtlReturnInfo;
import com.jiuqi.nr.etl.common.EtlServeEntity;
import com.jiuqi.nr.etl.common.NodeEntites;
import com.jiuqi.nr.etl.common.TreeNodeImpl;
import com.jiuqi.nr.etl.service.IEtlService;
import com.jiuqi.nr.etl.service.internal.ETLFilterParam;
import com.jiuqi.nr.etl.service.internal.ETLTaskExecutor;
import com.jiuqi.nr.etl.service.internal.EtlAsyncTaskErrorException;
import com.jiuqi.nr.etl.service.internal.EtlServeEntityDao;
import com.jiuqi.nr.etl.service.internal.ParamSplicing;
import com.jiuqi.nr.etl.service.internal.QueryEntity;
import com.jiuqi.nr.etl.service.internal.QueryParam;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.intergration.sdk.ticket.bean.Ticket;
import com.jiuqi.nvwa.intergration.sdk.ticket.bean.TicketToken;
import com.jiuqi.nvwa.ticket.service.TicketService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EtlServiceImpl
implements IEtlService {
    private static final Logger logger = LoggerFactory.getLogger(EtlServiceImpl.class);
    @Resource
    private RunTimeAuthViewController runTimeAuthViewController;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private QueryEntity queryEntity;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private ParamSplicing paramSplicing;
    @Autowired
    private EtlServeEntityDao etlServeEntityDao;
    @Autowired
    private ITaskOptionController iTaskOptionController;
    @Autowired
    private IBatchCalculateService batchCalculateService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private TicketService ticketService;

    @Override
    public boolean testLink(String url, String userName, String passWord) {
        ETLTaskExecutor executor = new ETLTaskExecutor();
        return executor.testLink(url, userName, passWord);
    }

    @Override
    public List<ETLTask> getAllTask() {
        Optional<EtlServeEntity> etlServeEntity = this.etlServeEntityDao.getServerInfo();
        String url = etlServeEntity.get().getAddress();
        String userName = etlServeEntity.get().getUserName();
        String passWord = etlServeEntity.get().getPwd();
        try {
            passWord = Crypto.desEncrypt((String)passWord);
        }
        catch (Exception e) {
            throw new RuntimeException("\u5bc6\u7801\u65e0\u6cd5\u89e3\u5bc6\uff0c\u8bf7\u91cd\u65b0\u914d\u7f6e\u5bc6\u7801", e);
        }
        return this.getAllTask(url, userName, passWord);
    }

    private List<ETLTask> getAllTask(String url, String userName, String passWord) {
        boolean valided = this.validity(url, userName);
        if (!valided) {
            return new ArrayList<ETLTask>();
        }
        ETLTaskExecutor executor = new ETLTaskExecutor();
        return executor.getAllTask(url, userName, passWord);
    }

    private boolean validity(String url, String userName) {
        if (StringUtils.isEmpty((String)url)) {
            logger.warn("ETL\u670d\u52a1\u5730\u5740\u4e3a\u7a7a\uff01");
            return false;
        }
        if (StringUtils.isEmpty((String)userName)) {
            logger.warn("ETL\u670d\u52a1\u7528\u6237\u540d\u4e3a\u7a7a\uff01");
            return false;
        }
        return true;
    }

    @Override
    public ETLTask findTaskByName(String taskName) {
        Optional<EtlServeEntity> etlServeEntity = this.etlServeEntityDao.getServerInfo();
        String url = etlServeEntity.get().getAddress();
        String userName = etlServeEntity.get().getUserName();
        String passWord = etlServeEntity.get().getPwd();
        try {
            passWord = Crypto.desEncrypt((String)passWord);
        }
        catch (Exception e) {
            throw new RuntimeException("\u5bc6\u7801\u65e0\u6cd5\u89e3\u5bc6\uff0c\u8bf7\u91cd\u65b0\u914d\u7f6e\u5bc6\u7801", e);
        }
        return this.findTaskByName(taskName, url, userName, passWord);
    }

    private ETLTask findTaskByName(String taskName, String url, String userName, String passWord) {
        boolean valided = this.validity(url, userName);
        if (!valided) {
            return null;
        }
        ETLTaskExecutor executor = new ETLTaskExecutor();
        return executor.findTaskByName(taskName, url, userName, passWord);
    }

    @Override
    public EtlExecuteInfo execute(EtlExecuteParam paramObj) {
        String taskid = paramObj.getTaskid();
        String param = paramObj.getParam();
        Optional<EtlServeEntity> etlServeEntity = this.etlServeEntityDao.getServerInfo();
        String url = etlServeEntity.get().getAddress();
        String userName = etlServeEntity.get().getUserName();
        String passWord = etlServeEntity.get().getPwd();
        try {
            passWord = Crypto.desEncrypt((String)passWord);
        }
        catch (Exception e) {
            throw new RuntimeException("\u5bc6\u7801\u65e0\u6cd5\u89e3\u5bc6\uff0c\u8bf7\u91cd\u65b0\u914d\u7f6e\u5bc6\u7801", e);
        }
        return this.execute(taskid, param, url, userName, passWord);
    }

    private EtlExecuteInfo execute(String taskid, String param, String url, String userName, String passWord) {
        boolean valided = this.validity(url, userName);
        if (!valided) {
            return null;
        }
        ETLTaskExecutor executor = new ETLTaskExecutor();
        return executor.execute(taskid, param, url, userName, passWord);
    }

    @Override
    public List<TreeNodeImpl> getReportTask() throws Exception {
        ArrayList<TreeNodeImpl> nodeList = new ArrayList<TreeNodeImpl>();
        List taskDefines = this.runTimeAuthViewController.getAllTaskDefinesByType(TaskType.TASK_TYPE_DEFAULT);
        if (taskDefines != null) {
            for (TaskDefine taskDefine : taskDefines) {
                TreeNodeImpl treeNode = new TreeNodeImpl();
                treeNode.setKey(taskDefine.getKey());
                treeNode.setTitle(taskDefine.getTitle());
                treeNode.setExpand(false);
                List<TreeNodeImpl> formSchemeList = this.getFormSchemeList(taskDefine);
                if (formSchemeList.size() <= 0) {
                    treeNode.setChildren(null);
                    continue;
                }
                treeNode.setChildren(formSchemeList);
                nodeList.add(treeNode);
            }
        }
        return nodeList;
    }

    private List<TreeNodeImpl> getFormSchemeList(TaskDefine taskDefine) throws Exception {
        ArrayList<TreeNodeImpl> nodeList = new ArrayList<TreeNodeImpl>();
        if (taskDefine.getKey() == null) {
            return null;
        }
        List<FormSchemeDefine> formSchemes = this.getFormSchemes(taskDefine.getKey());
        if (formSchemes.size() > 0) {
            for (FormSchemeDefine formSchemeDefine : formSchemes) {
                TreeNodeImpl treeNode = new TreeNodeImpl();
                treeNode.setKey(formSchemeDefine.getKey());
                treeNode.setTitle(formSchemeDefine.getTitle());
                treeNode.setChildren(null);
                treeNode.setExpand(false);
                treeNode.setTaskId(taskDefine.getKey());
                ArrayList<NodeEntites> entitiesList = new ArrayList<NodeEntites>();
                String dw = formSchemeDefine.getDw();
                TableModelDefine dwTable = this.entityMetaService.getTableModel(dw);
                NodeEntites dwEntity = new NodeEntites();
                treeNode.setEntityKey(dw);
                dwEntity.setKey(dw);
                dwEntity.setChecked(true);
                dwEntity.setTitle(dwTable.getTitle());
                entitiesList.add(dwEntity);
                String dateTime = formSchemeDefine.getDateTime();
                TableModelDefine dataTimeTable = this.periodEngineService.getPeriodAdapter().getPeriodEntityTableModel(dateTime);
                NodeEntites dataTimeEntity = new NodeEntites();
                dataTimeEntity.setKey(dateTime);
                dataTimeEntity.setChecked(true);
                dataTimeEntity.setTitle(dataTimeTable.getTitle());
                entitiesList.add(dataTimeEntity);
                String dims = formSchemeDefine.getDims();
                if (StringUtils.isNotEmpty((String)dims)) {
                    String[] dimArrays;
                    for (String dimArray : dimArrays = dims.split(";")) {
                        TableModelDefine dimTable = this.entityMetaService.getTableModel(dimArray);
                        NodeEntites dimEntity = new NodeEntites();
                        dimEntity.setKey(dimArray);
                        dimEntity.setChecked(false);
                        dimEntity.setTitle(dimTable.getTitle());
                        entitiesList.add(dimEntity);
                    }
                }
                treeNode.setEntityList(entitiesList);
                PeriodWrapper currPeriod = this.getCurrPeriod(formSchemeDefine);
                treeNode.setPeriod(currPeriod.toString());
                nodeList.add(treeNode);
            }
        }
        return nodeList;
    }

    private List<FormSchemeDefine> getFormSchemes(String taskKey) throws Exception {
        ArrayList<FormSchemeDefine> schemeList = new ArrayList<FormSchemeDefine>();
        if (taskKey == null) {
            return null;
        }
        List queryFormSchemeByTask = this.runTimeAuthViewController.queryFormSchemeByTask(taskKey);
        if (queryFormSchemeByTask != null && !queryFormSchemeByTask.isEmpty()) {
            for (FormSchemeDefine formSchemeDefine : queryFormSchemeByTask) {
                schemeList.add(formSchemeDefine);
            }
        }
        return schemeList;
    }

    private PeriodWrapper getCurrPeriod(FormSchemeDefine formSchemeDefine) {
        PeriodType periodType = formSchemeDefine.getPeriodType();
        int periodOffset = formSchemeDefine.getPeriodOffset();
        String fromPeriod = formSchemeDefine.getFromPeriod();
        String toPeriod = formSchemeDefine.getToPeriod();
        if (null == fromPeriod || null == toPeriod) {
            char typeToCode = (char)PeriodConsts.typeToCode((int)periodType.type());
            fromPeriod = "1970" + typeToCode + "0001";
            toPeriod = "9999" + typeToCode + "0001";
        }
        return EtlServiceImpl.getCurrPeriod(periodType.type(), periodOffset, fromPeriod, toPeriod);
    }

    private static PeriodWrapper getCurrPeriod(int periodType, int periodOffset, String fromPeriod, String toPeriod) {
        PeriodWrapper fromPeriodWrapper = null;
        PeriodWrapper toPeriodWrapper = null;
        try {
            fromPeriodWrapper = new PeriodWrapper(fromPeriod);
            toPeriodWrapper = new PeriodWrapper(toPeriod);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        int fromYear = fromPeriodWrapper.getYear();
        int toYear = toPeriodWrapper.getYear();
        PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((int)fromYear, (int)toYear, (int)periodType, (int)periodOffset);
        return currentPeriod;
    }

    @Override
    public Map<String, EtlReturnInfo> ETLExecute(EtlInfo etlInfo, AsyncTaskMonitor monitor) throws NrCommonException {
        ETLTaskExecutor executor;
        EtlExecuteInfo etlExecuteInfo;
        Optional<EtlServeEntity> etlServeEntity;
        HashMap<String, EtlReturnInfo> etlReturnMap = new HashMap<String, EtlReturnInfo>();
        EtlReturnInfo etlReturnInfo = new EtlReturnInfo();
        String detail = "";
        String url = "";
        String userName = "";
        String pw = "";
        Ticket tck = this.ticketService.apply();
        String nrUserName = tck.getUser().getName();
        TicketToken userToken = this.ticketService.verifyTicket(tck.getId());
        String userTokenId = userToken.getId();
        try {
            etlServeEntity = this.etlServeEntityDao.getServerInfo();
            url = etlServeEntity.get().getAddress();
            userName = etlServeEntity.get().getUserName();
            pw = etlServeEntity.get().getPwd();
            try {
                pw = Crypto.desEncrypt((String)pw);
            }
            catch (Exception e) {
                throw new RuntimeException("\u5bc6\u7801\u65e0\u6cd5\u89e3\u5bc6\uff0c\u8bf7\u91cd\u65b0\u914d\u7f6e\u5bc6\u7801", e);
            }
        }
        catch (Exception e) {
            etlReturnInfo.setMessage("\u6ca1\u6709\u627e\u5230\u914d\u7f6e\u7684ETL\u670d\u52a1");
            etlReturnInfo.setStatus(2);
            etlReturnMap.put("2", etlReturnInfo);
            return etlReturnMap;
        }
        this.process(0.05, monitor, detail);
        String etlTaskKey = etlInfo.getEtlTaskKey();
        QueryParam queryParam = new QueryParam(etlInfo, this.queryEntity, this.runTimeViewController, this.jtableParamService);
        Set<String> unitid = queryParam.getUnitid();
        Set<String> unidCode = queryParam.getUnidCode();
        Set<String> formCode = queryParam.getFormCode();
        Set<String> formKeySet = queryParam.getFormKeySet();
        this.process(0.15, monitor, detail);
        String period = etlInfo.getPeriod();
        ETLFilterParam etlFilterParam = new ETLFilterParam(period, unitid, unidCode, formCode, formKeySet);
        JSONObject param = this.paramSplicing.paramSplicing(etlFilterParam, String.join((CharSequence)",", nrUserName), String.join((CharSequence)",", userTokenId), etlServeEntity.get().getType());
        boolean valided = this.validity(url, userName);
        if (!valided) {
            return null;
        }
        double[] progress = new double[]{0.15, 0.0};
        String calAfterFetchSettings = this.iTaskOptionController.getValue(etlInfo.getTaskKey(), "CAL_AFTER_FETCH");
        boolean calAfterFetch = false;
        if (!StringUtils.isEmpty((String)calAfterFetchSettings)) {
            calAfterFetch = calAfterFetchSettings.equals("1");
        }
        if ((etlExecuteInfo = (executor = new ETLTaskExecutor(calAfterFetch, etlInfo, this.batchCalculateService)).execute(etlTaskKey, param.toString(), url, userName, pw, status -> {
            logger.info("ETL \u6b63\u5728\u6267\u884c\uff0c\u72b6\u6001{}", (Object)status);
            if (status == 0) {
                double step = 0.05;
                if (progress[0] >= 0.98) {
                    progress[0] = 0.99;
                    step = 0.0;
                } else if (progress[0] >= 0.8) {
                    progress[1] = progress[1] + 1.0;
                    step = 0.0;
                    if (progress[1] % 3.0 == 0.0) {
                        step = 0.01;
                    }
                } else if (progress[0] >= 0.5) {
                    step = 0.01;
                } else if (progress[0] >= 0.3) {
                    step = 0.02;
                }
                progress[0] = progress[0] + step;
                this.process(progress[0], monitor, detail);
            }
        })).getStatusCode() != 200) {
            if (etlExecuteInfo.getStatusCode() == 500) {
                String[] errorMsg = new String[]{"\u4efb\u52a1\u53c2\u6570\u9519\u8bef\u6216\u670d\u52a1\u5668\u5f02\u5e38"};
                throw new EtlAsyncTaskErrorException("500", errorMsg);
            }
            if (etlExecuteInfo.getStatusCode() == 403) {
                String[] errorMsg = new String[]{"\u64cd\u4f5c\u88ab\u62d2\u7edd\uff0c\u7528\u6237\u6ca1\u6709\u6743\u9650"};
                throw new EtlAsyncTaskErrorException("403", errorMsg);
            }
            if (etlExecuteInfo.getStatusCode() == 404) {
                String[] errorMsg = new String[]{"\u6267\u884c\u7684\u8ba1\u5212\u4efb\u52a1\u4e0d\u5b58\u5728"};
                throw new EtlAsyncTaskErrorException("404", errorMsg);
            }
            String[] errorMsg = new String[]{etlExecuteInfo.getErrorMessage()};
            throw new EtlAsyncTaskErrorException("Error", errorMsg);
        }
        if (monitor.isCancel()) {
            etlReturnInfo.setMessage("\u4efb\u52a1\u53d6\u6d88");
            etlReturnInfo.setStatus(-1);
            etlReturnMap.put("-1", etlReturnInfo);
            return etlReturnMap;
        }
        this.process(1.0, monitor, detail);
        return etlReturnMap;
    }

    public void process(double progress, AsyncTaskMonitor monitor, String detail) {
        String retStr = "";
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setMessage(detail);
        ObjectMapper mapper = new ObjectMapper();
        try {
            retStr = mapper.writeValueAsString((Object)returnInfo);
            monitor.progressAndMessage(progress, retStr);
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
    }
}

