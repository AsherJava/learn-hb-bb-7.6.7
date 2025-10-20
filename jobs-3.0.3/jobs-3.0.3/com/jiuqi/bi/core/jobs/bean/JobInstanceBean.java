/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.bean;

import com.jiuqi.bi.core.jobs.BaseFactory;
import com.jiuqi.bi.core.jobs.CompositeJobFactoryManager;
import com.jiuqi.bi.core.jobs.bean.JobExecResultBean;
import com.jiuqi.bi.core.jobs.monitor.JobResult;
import com.jiuqi.bi.core.jobs.monitor.JobType;
import com.jiuqi.bi.core.jobs.monitor.State;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JobInstanceBean
implements Serializable {
    private static final long serialVersionUID = 2357786011765930968L;
    private String instanceId;
    private String parentInstanceId;
    private String rootInstanceId;
    private int level;
    private int progress;
    private String prompt;
    private int state;
    private int jobType;
    private String userguid;
    private String username;
    private long starttime;
    private long endtime;
    private List<String> linkSource = new ArrayList<String>();
    private String jobId;
    private int result;
    private String resultMessage;
    private String node;
    private String instanceName;
    private String quartzInstance;
    private String categoryId;
    private String categoryTitle;
    private String groupId;
    private String queryField1;
    private String queryField2;
    private long execStartTime;
    private String publishNode;
    private boolean backstage = true;
    private int stage = 0;
    private List<JobExecResultBean> execResults = new ArrayList<JobExecResultBean>();
    private List<JobInstanceBean> childrenInstance = new ArrayList<JobInstanceBean>();

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return this.categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getQuartzInstance() {
        return this.quartzInstance;
    }

    public void setQuartzInstance(String quartzInstance) {
        this.quartzInstance = quartzInstance;
    }

    public String getInstanceId() {
        return this.instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getParentInstanceId() {
        return this.parentInstanceId;
    }

    public void setParentInstanceId(String parentInstanceId) {
        this.parentInstanceId = parentInstanceId;
    }

    public String getRootInstanceId() {
        return this.rootInstanceId;
    }

    public void setRootInstanceId(String rootInstanceId) {
        this.rootInstanceId = rootInstanceId;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getJobType() {
        return this.jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

    public String getUserguid() {
        return this.userguid;
    }

    public void setUserguid(String userguid) {
        this.userguid = userguid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getStarttime() {
        return this.starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public long getEndtime() {
        return this.endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public String getJobId() {
        return this.jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public int getResult() {
        return this.result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getResultMessage() {
        return this.resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getNode() {
        return this.node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getInstanceName() {
        return this.instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public List<JobExecResultBean> getExecResults() {
        return this.execResults;
    }

    public void setExecResults(List<JobExecResultBean> execResults) {
        this.execResults = execResults;
    }

    public List<JobInstanceBean> getChildrenInstance() {
        return this.childrenInstance;
    }

    public void setChildrenInstance(List<JobInstanceBean> childrenInstance) {
        this.childrenInstance = childrenInstance;
    }

    public boolean isBackstage() {
        return this.backstage;
    }

    public void setBackstage(boolean backstage) {
        this.backstage = backstage;
    }

    public int getStage() {
        return this.stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public List<String> getLinkSource() {
        return this.linkSource;
    }

    public String getQueryField1() {
        return this.queryField1;
    }

    public void setQueryField1(String queryField1) {
        this.queryField1 = queryField1;
    }

    public String getQueryField2() {
        return this.queryField2;
    }

    public void setQueryField2(String queryField2) {
        this.queryField2 = queryField2;
    }

    public long getExecStartTime() {
        return this.execStartTime;
    }

    public void setExecStartTime(long execStartTime) {
        this.execStartTime = execStartTime;
    }

    public String getPublishNode() {
        return this.publishNode;
    }

    public void setPublishNode(String publishNode) {
        this.publishNode = publishNode;
    }

    public void toJson(JSONObject json) throws JSONException {
        if (json == null) {
            throw new JSONException("JSON\u5bf9\u8c61\u4e0d\u80fd\u4e3a\u7a7a");
        }
        json.put("instanceId", (Object)this.instanceId);
        json.put("parentInstanceId", (Object)this.parentInstanceId);
        json.put("rootInstanceId", (Object)this.rootInstanceId);
        json.put("level", this.level);
        json.put("progress", this.progress);
        json.put("prompt", (Object)this.prompt);
        json.put("state", this.state);
        json.put("jobType", this.jobType);
        json.put("userguid", (Object)this.userguid);
        json.put("username", (Object)this.username);
        json.put("starttime", this.starttime);
        json.put("endtime", this.endtime);
        json.put("jobId", (Object)this.jobId);
        json.put("result", this.result);
        json.put("resultMessage", (Object)this.resultMessage);
        json.put("node", (Object)this.node);
        json.put("instanceName", (Object)this.instanceName);
        json.put("backstage", this.backstage);
        json.put("queryField1", (Object)this.queryField1);
        json.put("queryField2", (Object)this.queryField2);
        json.put("publishNode", (Object)this.publishNode);
        json.put("execStartTime", this.execStartTime);
        if (this.execResults != null && !this.execResults.isEmpty()) {
            json.put("execResults", (Object)JobExecResultBean.list2Json(this.execResults));
        }
        if (this.childrenInstance != null && !this.childrenInstance.isEmpty()) {
            JSONArray childrenJson = new JSONArray();
            for (JobInstanceBean child : this.childrenInstance) {
                JSONObject childJson = new JSONObject();
                child.toJson(childJson);
                childrenJson.put((Object)childJson);
            }
            json.put("childrenInstance", (Object)childrenJson);
        }
        json.put("categoryId", (Object)this.categoryId);
        if (StringUtils.isEmpty((String)this.categoryTitle)) {
            BaseFactory jobFactory = CompositeJobFactoryManager.getJobFactory(this.categoryId);
            if (jobFactory != null) {
                this.categoryTitle = jobFactory.getJobCategoryTitle();
            } else if ("com.jiuqi.bi.jobs.realtime".equals(this.categoryId)) {
                this.categoryTitle = "\u5373\u65f6\u4efb\u52a1";
            }
        }
        json.put("categoryTitle", (Object)this.categoryTitle);
        json.put("groupId", (Object)this.groupId);
        json.put("logGuid", (Object)this.getInstanceId());
        JobType jobTypeEnum = JobType.valueOf(this.getJobType());
        json.put("jobTypeTitle", (Object)(jobTypeEnum == null ? "\u672a\u77e5\u7c7b\u578b" : jobTypeEnum.getTitle()));
        long totalTime = (this.getEndtime() > 0L ? this.getEndtime() : System.currentTimeMillis()) - this.getStarttime();
        json.put("totalTime", totalTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        json.put("startTimeStr", (Object)sdf.format(new Date(this.getStarttime())));
        json.put("endTimeStr", (Object)(this.getEndtime() > 0L ? sdf.format(new Date(this.getEndtime())) : ""));
        String execUserName = this.getUsername();
        if ("_system_".equals(execUserName)) {
            json.put("username", (Object)"\u7cfb\u7edf\u8c03\u5ea6");
        }
        State jobState = State.valueOf(this.getState());
        String jobStateTitle = jobState.getTitle();
        String stateTitle = jobState.getTitle();
        int jobResult = this.getResult();
        if (jobState == State.FINISHED) {
            stateTitle = JobResult.getResultTitle(jobResult);
            jobStateTitle = "\u672a\u8fd0\u884c";
        }
        json.put("stateTitle", (Object)stateTitle);
        json.put("jobStateTitle", (Object)jobStateTitle);
        if (!this.linkSource.isEmpty()) {
            JSONArray linkSourceArr = new JSONArray();
            for (String sourceGuid : this.linkSource) {
                linkSourceArr.put((Object)sourceGuid);
            }
            json.put("linkSource", (Object)linkSourceArr);
        }
    }
}

