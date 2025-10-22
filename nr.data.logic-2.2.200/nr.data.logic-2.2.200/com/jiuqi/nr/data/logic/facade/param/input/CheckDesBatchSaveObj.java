/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import java.util.ArrayList;
import java.util.List;

public class CheckDesBatchSaveObj {
    private CheckDesQueryParam checkDesQueryParam;
    private List<CheckDesObj> checkDesObjs = new ArrayList<CheckDesObj>();
    private boolean updateCurUsrTime;

    public CheckDesQueryParam getCheckDesQueryParam() {
        return this.checkDesQueryParam;
    }

    public void setCheckDesQueryParam(CheckDesQueryParam checkDesQueryParam) {
        this.checkDesQueryParam = checkDesQueryParam;
    }

    public List<CheckDesObj> getCheckDesObjs() {
        return this.checkDesObjs;
    }

    public void setCheckDesObjs(List<CheckDesObj> checkDesObjs) {
        this.checkDesObjs = checkDesObjs;
    }

    public boolean isUpdateCurUsrTime() {
        return this.updateCurUsrTime;
    }

    public void setUpdateCurUsrTime(boolean updateCurUsrTime) {
        this.updateCurUsrTime = updateCurUsrTime;
    }
}

