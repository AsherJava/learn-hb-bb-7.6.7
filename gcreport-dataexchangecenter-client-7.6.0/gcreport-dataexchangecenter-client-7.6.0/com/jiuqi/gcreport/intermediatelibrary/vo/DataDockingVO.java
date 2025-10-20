/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.vo;

import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingTaskVO;
import java.util.List;

public class DataDockingVO {
    private String sysCode;
    private String sn;
    private String requestTime;
    private List<DataDockingTaskVO> data;

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getRequestTime() {
        return this.requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public List<DataDockingTaskVO> getData() {
        return this.data;
    }

    public void setData(List<DataDockingTaskVO> data) {
        this.data = data;
    }

    public String getSysCode() {
        return this.sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }
}

