/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package nr.midstore.core.asyn;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import io.swagger.annotations.Api;
import java.util.HashMap;
import java.util.Map;
import nr.midstore.core.asyn.MidstoreBatchAsyncTaskExecutor;
import nr.midstore.core.definition.bean.MidstoreParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/midstore/asyn"})
@Api(tags={"\u4e2d\u95f4\u5e93\u6279\u91cf\u64cd\u4f5c"})
public class MidstoreBatchController {
    @Autowired
    private AsyncThreadExecutor asyncTaskManager2;

    private String publishAndExecuteTask(Map<String, Object> args) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString(args));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new MidstoreBatchAsyncTaskExecutor());
        return this.asyncTaskManager2.executeTask(npRealTimeTaskInfo);
    }

    public String publishTasK(String midstoreSchemeKey) throws Exception {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("optType", "10");
        args.put("midstoreSchemeKey", midstoreSchemeKey);
        return this.publishAndExecuteTask(args);
    }

    public String doGetDataFromMidstore(String midstoreSchemeKey) throws Exception {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("optType", "11");
        args.put("midstoreSchemeKey", midstoreSchemeKey);
        return this.publishAndExecuteTask(args);
    }

    public String doGetDataFromMidstore(MidstoreParam param) throws Exception {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("optType", "111");
        args.put("MidstoreParam", param);
        return this.publishAndExecuteTask(args);
    }

    public String doPostDataToMidstore(String midstoreSchemeKey) throws Exception {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("optType", "12");
        args.put("midstoreSchemeKey", midstoreSchemeKey);
        return this.publishAndExecuteTask(args);
    }

    public String doPostDataToMidstore(MidstoreParam param) throws Exception {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("optType", "121");
        args.put("MidstoreParam", param);
        return this.publishAndExecuteTask(args);
    }

    public String doLinkBaseDataFromFields(String midstoreSchemeKey) throws Exception {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("optType", "13");
        args.put("midstoreSchemeKey", midstoreSchemeKey);
        return this.publishAndExecuteTask(args);
    }

    public String doCheckParams(String midstoreSchemeKey) throws Exception {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("optType", "14");
        args.put("midstoreSchemeKey", midstoreSchemeKey);
        return this.publishAndExecuteTask(args);
    }

    public String doExportDocument(String midstoreSchemeKey) throws Exception {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("optType", "15");
        args.put("midstoreSchemeKey", midstoreSchemeKey);
        return this.publishAndExecuteTask(args);
    }
}

