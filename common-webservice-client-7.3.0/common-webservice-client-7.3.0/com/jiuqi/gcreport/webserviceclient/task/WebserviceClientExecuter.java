/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataParam
 *  com.jiuqi.gcreport.organization.impl.bean.OrgDataDO
 *  com.jiuqi.gcreport.organization.impl.service.GcOrgDataService
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor
 */
package com.jiuqi.gcreport.webserviceclient.task;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.organization.api.vo.OrgDataParam;
import com.jiuqi.gcreport.organization.impl.bean.OrgDataDO;
import com.jiuqi.gcreport.organization.impl.service.GcOrgDataService;
import com.jiuqi.gcreport.webserviceclient.utils.WebserviceClientUtil;
import com.jiuqi.gcreport.webserviceclient.vo.WebserviceClientParam;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

public class WebserviceClientExecuter {
    private static Logger logger = LoggerFactory.getLogger(WebserviceClientExecuter.class);
    public static Map<String, String> taskId2WsClientCode = new HashMap<String, String>();

    public boolean executeWebservicClient(WebserviceClientParam wsParam, List<String> logs) {
        CacheObjectResourceRemote cacheObjectResourceRemote = (CacheObjectResourceRemote)SpringContextUtils.getBean(CacheObjectResourceRemote.class);
        WebserviceClientUtil.initWSClientParam(wsParam);
        return this.executeWebservicClient(wsParam, logs, (AsyncTaskMonitor)new SimpleAsyncProgressMonitor(UUIDUtils.newUUIDStr(), cacheObjectResourceRemote));
    }

    public boolean executeWebservicClient(WebserviceClientParam wsParam, List<String> logs, AsyncTaskMonitor monitor) {
        WebserviceClientUtil.createAsyncTask(monitor, "\u5f00\u59cbWS\u4efb\u52a1\u6267\u884c;", logs);
        WebserviceClientUtil.modifyAsyncTaskState(monitor, 0.1, "\u521d\u59cb\u5316\u53c2\u6570\u4fe1\u606f;", logs);
        String paramsXmlText = this.initParamsXmlText(wsParam);
        List<String> orgCodeList = wsParam.getOrgCodeList();
        for (int i = 0; i < orgCodeList.size(); ++i) {
            String orgCode = orgCodeList.get(i);
            logs.add("\u5355\u4f4d\u3010" + orgCode + "\u3011,\u5f00\u59cb\u8c03\u7528WS\u670d\u52a1;");
            String currOrgCodeXmlText = paramsXmlText.replaceAll("@orgCode@", orgCode);
            String taskId = UUIDUtils.newUUIDStr().toUpperCase();
            currOrgCodeXmlText = currOrgCodeXmlText.replaceAll("@newId@", taskId);
            taskId2WsClientCode.put(taskId, wsParam.getWsClientBaseDataCode());
            currOrgCodeXmlText = this.getBaseOrgFieldValue(currOrgCodeXmlText, orgCode, logs);
            if (currOrgCodeXmlText == null) {
                return false;
            }
            boolean success = WebserviceClientUtil.sendRequestToWS(currOrgCodeXmlText, wsParam, logs);
            if (!success) {
                return false;
            }
            double currentProgress = new BigDecimal((double)((i + 1) / orgCodeList.size()) * 0.9).setScale(2, 4).doubleValue();
            WebserviceClientUtil.modifyAsyncTaskState(monitor, currentProgress, "\u5355\u4f4d\u3010" + orgCode + "\u3011,\u8c03\u7528WS\u670d\u52a1\u5b8c\u6210;", logs);
        }
        WebserviceClientUtil.modifyAsyncTaskState(monitor, 1.0, "\u4efb\u52a1\u6267\u884c\u7ed3\u675f\u3002", logs);
        return true;
    }

    private String initParamsXmlText(WebserviceClientParam wsParam) {
        this.checkParams(wsParam);
        String periodStr = this.getPeriodStr(wsParam);
        String year = periodStr.substring(0, 4);
        String period = periodStr.substring(7);
        String paramsXmlText = wsParam.getParamsXmlText();
        paramsXmlText = paramsXmlText.replaceAll("@acctYear@", year);
        paramsXmlText = paramsXmlText.replace("@acctPeriod@", period.replace("0", ""));
        paramsXmlText = paramsXmlText.replaceAll("@acctYearPeriod@", year + "-" + period);
        paramsXmlText = paramsXmlText.replaceAll("@zjkTableName@", wsParam.getZjkTableName());
        Date currDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        paramsXmlText = paramsXmlText.replaceAll("@sumitDate@", df.format(currDate));
        if (!StringUtils.isEmpty((String)wsParam.getToken())) {
            String tokenAndTimeStamp = this.getEncryptedToken(wsParam, currDate);
            paramsXmlText = paramsXmlText.replaceAll("@token@", tokenAndTimeStamp);
        }
        return paramsXmlText;
    }

    private void checkParams(WebserviceClientParam wsParam) {
        if (StringUtils.isEmpty((String)wsParam.getUrl())) {
            throw new BusinessRuntimeException("\u57fa\u7840\u6570\u636e\u3010MD_WS_CLIENT\u3011,\u6570\u636e\u9879\u3010" + wsParam.getWsClientBaseDataCode() + "\u3011\u672a\u914d\u7f6eURL");
        }
        if (StringUtils.isEmpty((String)wsParam.getParamsXmlText())) {
            throw new BusinessRuntimeException("\u57fa\u7840\u6570\u636e\u3010MD_WS_CLIENT\u3011,\u6570\u636e\u9879\u3010" + wsParam.getWsClientBaseDataCode() + "\u3011\u672a\u914d\u7f6eXML\u53c2\u6570");
        }
    }

    private String getEncryptedToken(WebserviceClientParam condition, Date currDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String timeStamp = df.format(currDate);
        String tokenAndTimeStamp = condition.getToken() + timeStamp;
        String tokenByMd5 = DigestUtils.md5DigestAsHex(tokenAndTimeStamp.getBytes());
        return tokenByMd5;
    }

    private String getBaseOrgFieldValue(String currOrgCodeXmlText, String orgCode, List<String> logs) {
        OrgDataDO orgDataDO = this.getOrgDataDO(orgCode, logs);
        if (orgDataDO == null) {
            return null;
        }
        for (String fieldCode : orgDataDO.getFieldValMap().keySet()) {
            String otherFieldCode = "@MD_ORG." + fieldCode.toUpperCase() + "@";
            String fieldValue = orgDataDO.getValAsString(fieldCode.toUpperCase());
            currOrgCodeXmlText = currOrgCodeXmlText.replaceAll(otherFieldCode, fieldValue);
        }
        return currOrgCodeXmlText;
    }

    private OrgDataDO getOrgDataDO(String orgCode, List<String> logs) {
        GcOrgDataService gcOrgDataService = (GcOrgDataService)SpringContextUtils.getBean(GcOrgDataService.class);
        OrgDataParam dataParam = new OrgDataParam();
        dataParam.setAuthType("Read");
        dataParam.setOrgCode(orgCode);
        dataParam.setOrgType("MD_ORG");
        List orgDataDOS = gcOrgDataService.list(dataParam);
        if (CollectionUtils.isEmpty((Collection)orgDataDOS)) {
            logs.add("\u672a\u67e5\u8be2\u5230\u5355\u4f4d:" + orgCode);
            return null;
        }
        OrgDataDO orgDataDO = (OrgDataDO)orgDataDOS.get(0);
        return orgDataDO;
    }

    private String getPeriodStr(WebserviceClientParam condition) {
        String periodType = condition.getPeriodType();
        int type = condition.getSchemePeriodType();
        if ("CURRENTPERIOD".equals(periodType)) {
            return WebserviceClientUtil.getCurrentPeriod(type).toString();
        }
        if ("PREVIOUSPERIOD".equals(periodType)) {
            PeriodWrapper periodWrapper = WebserviceClientUtil.getCurrentPeriod(type);
            periodWrapper.priorPeriod();
            return periodWrapper.toString();
        }
        return condition.getPeriodString();
    }
}

