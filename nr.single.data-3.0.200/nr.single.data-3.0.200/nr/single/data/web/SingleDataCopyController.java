/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package nr.single.data.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import io.swagger.annotations.Api;
import java.util.HashMap;
import java.util.Map;
import nr.single.data.bean.NrSingleDataErrorEnum;
import nr.single.data.bean.TaskCopyParam;
import nr.single.data.datacopy.ITaskDataCopyService;
import nr.single.data.web.DataCopyAsyncTaskExecutor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/single/DataCopy"})
@Api(tags={"\u4efb\u52a1\u6570\u636e\u590d\u5236"})
public class SingleDataCopyController {
    private static final Logger logger = LoggerFactory.getLogger(SingleDataCopyController.class);
    @Autowired
    private ITaskDataCopyService dataCopySevice;
    @Autowired
    private AsyncThreadExecutor asyncTaskManager2;

    @PostMapping(value={"/DataCopys"})
    public String taskDataCopy(@RequestBody TaskCopyParam copyParam) throws Exception {
        if (StringUtils.isNotEmpty((CharSequence)copyParam.getTaskKey())) {
            AsyncTaskMonitor monitor = null;
            return this.dataCopySevice.copyDataByParam(copyParam, monitor);
        }
        throw new JQException((ErrorEnum)NrSingleDataErrorEnum.NRSINGDATAER_EXCEPTION_002);
    }

    @PostMapping(value={"/DataCopyAsync"})
    public String taskDataCopyAnsyc(@RequestBody TaskCopyParam copyParam) throws Exception {
        if (StringUtils.isNotEmpty((CharSequence)copyParam.getTaskKey())) {
            String asyncTaskId = this.copyTaskData(copyParam);
            return asyncTaskId;
        }
        throw new JQException((ErrorEnum)NrSingleDataErrorEnum.NRSINGDATAER_EXCEPTION_002);
    }

    private String publishAndExecuteTask(Map<String, Object> args) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString(args));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new DataCopyAsyncTaskExecutor());
        return this.asyncTaskManager2.executeTask(npRealTimeTaskInfo);
    }

    private String copyTaskData(TaskCopyParam copyParam) {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("copyParam", copyParam);
        return this.publishAndExecuteTask(args);
    }
}

