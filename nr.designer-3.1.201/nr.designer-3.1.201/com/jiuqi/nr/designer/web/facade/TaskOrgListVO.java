/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.designer.web.facade.TaskOrgVO;
import java.util.List;

public class TaskOrgListVO {
    private List<TaskOrgVO> orgList;
    private boolean needUpdate = false;

    public TaskOrgListVO() {
    }

    public TaskOrgListVO(List<TaskOrgVO> orgList) {
        this.orgList = orgList;
    }

    public List<TaskOrgVO> getOrgList() {
        return this.orgList;
    }

    public void setOrgList(List<TaskOrgVO> orgList) {
        this.orgList = orgList;
    }

    public boolean isNeedUpdate() {
        return this.needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }
}

