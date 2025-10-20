/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.etl.common.EtlServeEntity
 *  com.jiuqi.nr.etl.service.internal.NrdlTaskExecutor
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.webserviceclient.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.webserviceclient.service.DataIntegrationService;
import com.jiuqi.gcreport.webserviceclient.utils.DataIntegrationUtil;
import com.jiuqi.gcreport.webserviceclient.vo.DataIntegrationTaskTreeVo;
import com.jiuqi.gcreport.webserviceclient.vo.DataIntegrationtParam;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.etl.common.EtlServeEntity;
import com.jiuqi.nr.etl.service.internal.NrdlTaskExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataIntegrationServiceImpl
implements DataIntegrationService {
    private static final String API_JOBS_REMOTE_LIST = "/api/jobs/remote/list/com.jiuqi.bi.dataintegration";
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @Override
    public AsyncTaskInfo publishDataIntegrationtTask(DataIntegrationtParam dataIntegrationtParam) {
        String asynTaskID = this.asyncTaskManager.publishTask((Object)dataIntegrationtParam, "GC_ASYNC_TASK_DATA_INTEGRATIONT");
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @Override
    public List<DataIntegrationTaskTreeVo> listDataIntegrationTaskTree(Set<String> integrationTaskTitles) {
        EtlServeEntity serveEntity = DataIntegrationUtil.getEtlServeEntity();
        String pw = DataIntegrationUtil.getPassword(serveEntity);
        NrdlTaskExecutor.login((String)serveEntity.getAddress(), (String)serveEntity.getUserName(), (String)pw);
        return this.listDataIntegrationTaskTree("", integrationTaskTitles);
    }

    private List<DataIntegrationTaskTreeVo> listDataIntegrationTaskTree(String folderGuid, Set<String> integrationTaskTitles) {
        String taskString = NrdlTaskExecutor.doGet((String)(API_JOBS_REMOTE_LIST + (StringUtils.isEmpty((String)folderGuid) ? "" : "?folderGuid=" + folderGuid)), null);
        JSONArray taskListJsonArr = new JSONArray(taskString);
        ArrayList<DataIntegrationTaskTreeVo> taskTreeVoList = new ArrayList<DataIntegrationTaskTreeVo>();
        for (int i = 0; i < taskListJsonArr.length(); ++i) {
            JSONObject taskJsonObj = taskListJsonArr.getJSONObject(i);
            DataIntegrationTaskTreeVo taskTreeVo = new DataIntegrationTaskTreeVo();
            String taskGuid = (String)taskJsonObj.get("guid");
            taskTreeVo.setId(taskGuid);
            taskTreeVo.setLabel((String)taskJsonObj.get("title"));
            taskTreeVo.setLeafFlag((Boolean)taskJsonObj.get("isFolder") == false);
            if (!StringUtils.isEmpty((String)folderGuid)) {
                taskTreeVo.setParentId(folderGuid);
            }
            if (!taskTreeVo.isLeafFlag()) {
                List<DataIntegrationTaskTreeVo> childTasks = this.listDataIntegrationTaskTree(taskGuid, integrationTaskTitles);
                if (CollectionUtils.isEmpty(childTasks)) continue;
                taskTreeVo.setChildren(childTasks);
                taskTreeVoList.add(taskTreeVo);
                continue;
            }
            if (!integrationTaskTitles.contains(taskTreeVo.getLabel())) continue;
            taskTreeVoList.add(taskTreeVo);
        }
        return taskTreeVoList;
    }
}

