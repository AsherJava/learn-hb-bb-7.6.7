/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskQueryInfo
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.exception.NotFoundAsyncTaskException
 *  com.jiuqi.nr.etl.common.NrdlTask
 *  com.jiuqi.nr.etl.service.INrDataIntegrationService
 *  javax.annotation.Resource
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.webserviceclient.web;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.webserviceclient.service.DataIntegrationService;
import com.jiuqi.gcreport.webserviceclient.vo.DataIntegrationTaskTreeVo;
import com.jiuqi.gcreport.webserviceclient.vo.DataIntegrationtParam;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskQueryInfo;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NotFoundAsyncTaskException;
import com.jiuqi.nr.etl.common.NrdlTask;
import com.jiuqi.nr.etl.service.INrDataIntegrationService;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataIntegrationController {
    static final String API_PATH = "/api/gcreport/v1/dataIntegration";
    @Autowired
    private DataIntegrationService dataIntegrationService;
    @Resource
    private AsyncTaskManager asyncTaskManager;
    @Lazy
    @Autowired
    private INrDataIntegrationService iNrDataIntegrationService;

    @ResponseBody
    @PostMapping(value={"/api/gcreport/v1/dataIntegration/executeDataIntegration"})
    BusinessResponseEntity<Object> executeDataIntegration(@RequestBody DataIntegrationtParam dataIntegrationtParam) {
        return BusinessResponseEntity.ok((Object)this.dataIntegrationService.publishDataIntegrationtTask(dataIntegrationtParam));
    }

    @ResponseBody
    @PostMapping(value={"/api/gcreport/v1/dataIntegration/listDataIntegrationTaskTree"})
    BusinessResponseEntity<List<DataIntegrationTaskTreeVo>> listDataIntegrationTaskTree(@RequestBody Set<String> integrationTaskTitles) {
        return BusinessResponseEntity.ok(this.dataIntegrationService.listDataIntegrationTaskTree(integrationTaskTitles));
    }

    @ResponseBody
    @PostMapping(value={"/api/gcreport/v1/dataIntegration/listDataIntegrationTask"})
    BusinessResponseEntity<List<NrdlTask>> listDataIntegrationTask() {
        List nrdlTasks = this.iNrDataIntegrationService.getAllTask();
        return BusinessResponseEntity.ok((Object)nrdlTasks);
    }

    @GetMapping(value={"/api/gcreport/v1/dataIntegration/asynctask/query"})
    public AsyncTaskInfo queryAsyncTask(AsyncTaskQueryInfo asyncTaskQueryInfo) {
        if (StringUtils.isNotEmpty((String)asyncTaskQueryInfo.getAsynTaskID())) {
            AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
            asyncTaskInfo.setId(asyncTaskQueryInfo.getAsynTaskID());
            AsyncTask queryTask = this.asyncTaskManager.queryTask(asyncTaskQueryInfo.getAsynTaskID());
            if (null == queryTask) {
                throw new NotFoundAsyncTaskException(ExceptionCodeCost.NOTFOUND_ASYNCTASK_IDISNULL, null);
            }
            asyncTaskInfo.setProcess(queryTask.getProcess());
            asyncTaskInfo.setResult(queryTask.getResult());
            asyncTaskInfo.setDetail(queryTask.getDetail());
            TaskState state = queryTask.getState();
            asyncTaskInfo.setState(state);
            if (state == TaskState.WAITING) {
                asyncTaskInfo.setLocation(this.asyncTaskManager.queryLocation(asyncTaskQueryInfo.getAsynTaskID()));
            } else {
                Object queryDetail = this.asyncTaskManager.queryDetail(asyncTaskQueryInfo.getAsynTaskID());
                asyncTaskInfo.setDetail(queryDetail);
            }
            return asyncTaskInfo;
        }
        throw new NotFoundAsyncTaskException(ExceptionCodeCost.NOTFOUND_ASYNCTASK_IDISNULL, null);
    }
}

