/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.crypto.Crypto
 *  com.jiuqi.nr.dataentry.service.IBatchCalculateService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableParamService
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
import com.jiuqi.nr.common.crypto.Crypto;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.etl.common.EtlExecuteInfo;
import com.jiuqi.nr.etl.common.EtlExecuteParam;
import com.jiuqi.nr.etl.common.EtlInfo;
import com.jiuqi.nr.etl.common.EtlReturnInfo;
import com.jiuqi.nr.etl.common.EtlServeEntity;
import com.jiuqi.nr.etl.common.NrdlTask;
import com.jiuqi.nr.etl.service.INrDataIntegrationService;
import com.jiuqi.nr.etl.service.internal.ETLFilterParam;
import com.jiuqi.nr.etl.service.internal.EtlAsyncTaskErrorException;
import com.jiuqi.nr.etl.service.internal.EtlServeEntityDao;
import com.jiuqi.nr.etl.service.internal.NrdlTaskExecutor;
import com.jiuqi.nr.etl.service.internal.ParamSplicing;
import com.jiuqi.nr.etl.service.internal.QueryEntity;
import com.jiuqi.nr.etl.service.internal.QueryParam;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableParamService;
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
public class NrdlServiceImpl
implements INrDataIntegrationService {
    private static final Logger logger = LoggerFactory.getLogger(NrdlServiceImpl.class);
    @Autowired
    private EtlServeEntityDao etlServeEntityDao;
    @Autowired
    private ParamSplicing paramSplicing;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private QueryEntity queryEntity;
    @Autowired
    private ITaskOptionController iTaskOptionController;
    @Autowired
    private IBatchCalculateService batchCalculateService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private TicketService ticketService;

    private boolean validity(String url, String userName) {
        if (StringUtils.isEmpty((String)url)) {
            logger.warn("\u6570\u636e\u96c6\u6210\u670d\u52a1\u5730\u5740\u4e3a\u7a7a\uff01");
            return false;
        }
        if (StringUtils.isEmpty((String)userName)) {
            logger.warn("\u6570\u636e\u96c6\u6210\u670d\u52a1\u7528\u6237\u540d\u4e3a\u7a7a\uff01");
            return false;
        }
        return true;
    }

    @Override
    public boolean testLink(String url, String userName, String passWord) {
        NrdlTaskExecutor executor = new NrdlTaskExecutor();
        return executor.testLink(url, userName, passWord);
    }

    @Override
    public List<NrdlTask> getAllTask() {
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

    private List<NrdlTask> getAllTask(String url, String userName, String passWord) {
        boolean valided = this.validity(url, userName);
        if (!valided) {
            return new ArrayList<NrdlTask>();
        }
        NrdlTaskExecutor executor = new NrdlTaskExecutor();
        return executor.getAllTask(url, userName, passWord);
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
        NrdlTaskExecutor executor = new NrdlTaskExecutor();
        return executor.execute(taskid, param, url, userName, passWord);
    }

    @Override
    public Map<String, EtlReturnInfo> ETLExecute(EtlInfo etlInfo, AsyncTaskMonitor monitor) {
        NrdlTaskExecutor executor;
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
            etlReturnInfo.setMessage("\u6ca1\u6709\u627e\u5230\u914d\u7f6e\u7684\u6570\u636e\u96c6\u6210\u670d\u52a1");
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
        if ((etlExecuteInfo = (executor = new NrdlTaskExecutor(calAfterFetch, etlInfo, this.batchCalculateService)).execute(etlTaskKey, param.toString(), url, userName, pw, status -> {
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
        })).getStatusCode() != 100) {
            String[] errorMsg = new String[1];
            switch (etlExecuteInfo.getStatusCode()) {
                case 500: {
                    errorMsg[0] = "\u4efb\u52a1\u53c2\u6570\u9519\u8bef\u6216\u670d\u52a1\u5668\u5f02\u5e38";
                    throw new EtlAsyncTaskErrorException("500", errorMsg);
                }
                case -9999: {
                    errorMsg[0] = "\u672a\u8ba4\u8bc1\u901a\u8fc7";
                    throw new EtlAsyncTaskErrorException("-9999", errorMsg);
                }
                case -100: {
                    errorMsg[0] = "\u4efb\u52a1\u8fd0\u884c\u5931\u8d25";
                    throw new EtlAsyncTaskErrorException("-100", errorMsg);
                }
                case 1: {
                    errorMsg[0] = "\u4efb\u52a1\u672a\u5b8c\u6210";
                    throw new EtlAsyncTaskErrorException("1", errorMsg);
                }
                case 2: {
                    errorMsg[0] = "\u4efb\u52a1\u5df2\u53d6\u6d88";
                    throw new EtlAsyncTaskErrorException("2", errorMsg);
                }
                case 3: {
                    errorMsg[0] = "\u4efb\u52a1\u5df2\u7ec8\u6b62";
                    throw new EtlAsyncTaskErrorException("3", errorMsg);
                }
                case 4: {
                    errorMsg[0] = "\u4efb\u52a1\u5f02\u5e38";
                    throw new EtlAsyncTaskErrorException("4", errorMsg);
                }
                case -2: {
                    errorMsg[0] = "\u4efb\u52a1\u6b63\u5728\u53d6\u6d88";
                    throw new EtlAsyncTaskErrorException("-2", errorMsg);
                }
            }
            errorMsg[0] = etlExecuteInfo.getErrorMessage();
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

