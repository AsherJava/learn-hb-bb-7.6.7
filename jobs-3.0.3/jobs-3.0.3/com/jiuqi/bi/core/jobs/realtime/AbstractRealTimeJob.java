/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.realtime;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.base.IJobExecuteAble;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractRealTimeJob
implements Serializable,
IJobExecuteAble<JobContext> {
    private String title;
    private List<String> linkSource = new ArrayList<String>();
    private Map<String, String> params = new HashMap<String, String>();
    private String userGuid;
    private String userName;
    private String queryField1;
    private String queryField2;

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params == null ? new HashMap<String, String>() : params;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getUserGuid() {
        return this.userGuid;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
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
}

