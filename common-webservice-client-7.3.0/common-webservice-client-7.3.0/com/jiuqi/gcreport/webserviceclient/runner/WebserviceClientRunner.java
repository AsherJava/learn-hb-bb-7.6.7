/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 */
package com.jiuqi.gcreport.webserviceclient.runner;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.gcreport.webserviceclient.task.WebserviceClientExecuter;
import com.jiuqi.gcreport.webserviceclient.vo.WebserviceClientParam;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PlanTaskRunner(id="F99D8DF671FB45FD9FBB192FCB603248", settingPage="webserviceClientAdvanceConfig", name="com.jiuqi.gcreport.webserviceclient.runner.WebserviceClientRunner", title="webservice\u5ba2\u6237\u7aef")
public class WebserviceClientRunner
extends Runner {
    private Logger logger = LoggerFactory.getLogger(WebserviceClientRunner.class);

    public boolean excute(String runnerParameter) {
        if (StringUtils.isEmpty((String)runnerParameter)) {
            this.appendLog("\u672a\u8bbe\u7f6e\u9ad8\u7ea7\u53c2\u6570");
            return false;
        }
        this.logger.info("\u6570\u636e\u4f20\u8f93\u8ba1\u5212\u4efb\u52a1\u53c2\u6570\uff1a{}", (Object)runnerParameter);
        WebserviceClientParam wsParam = (WebserviceClientParam)JsonUtils.readValue((String)runnerParameter, WebserviceClientParam.class);
        ArrayList<String> taskLogs = new ArrayList<String>();
        WebserviceClientExecuter executer = new WebserviceClientExecuter();
        boolean excuteResult = executer.executeWebservicClient(wsParam, taskLogs);
        this.appendLog(StringUtils.join((Object[])taskLogs.toArray(), (String)";\n"));
        return excuteResult;
    }
}

