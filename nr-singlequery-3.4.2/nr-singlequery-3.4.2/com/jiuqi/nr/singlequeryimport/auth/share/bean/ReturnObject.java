/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.auth.share.bean;

import com.jiuqi.nr.singlequeryimport.auth.share.bean.SingleQueryAuthShareCacheUserInfo;
import java.util.ArrayList;
import java.util.List;

public class ReturnObject {
    private List<SingleQueryAuthShareCacheUserInfo> obj = new ArrayList<SingleQueryAuthShareCacheUserInfo>();
    private int recordCount = 0;
    private String errMsg;

    public List<SingleQueryAuthShareCacheUserInfo> getObj() {
        return this.obj;
    }

    public void setObj(List<SingleQueryAuthShareCacheUserInfo> obj) {
        this.obj = obj;
    }

    public int getRecordCount() {
        return this.recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}

