/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.gather.gzw.web.vo;

import com.jiuqi.nr.batch.gather.gzw.web.vo.SingleDimVO;
import com.jiuqi.nr.batch.gather.gzw.web.vo.TaskOrgLinkVO;
import java.util.List;

public class CorporateTypeVO {
    private List<TaskOrgLinkVO> taskOrgTypes;
    private List<SingleDimVO> singleDims;

    public List<TaskOrgLinkVO> getTaskOrgTypes() {
        return this.taskOrgTypes;
    }

    public void setTaskOrgTypes(List<TaskOrgLinkVO> taskOrgTypes) {
        this.taskOrgTypes = taskOrgTypes;
    }

    public List<SingleDimVO> getSingleDims() {
        return this.singleDims;
    }

    public void setSingleDims(List<SingleDimVO> singleDims) {
        this.singleDims = singleDims;
    }
}

