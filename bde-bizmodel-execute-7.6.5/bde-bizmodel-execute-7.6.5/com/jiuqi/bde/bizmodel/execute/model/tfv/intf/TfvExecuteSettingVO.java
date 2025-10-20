/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 */
package com.jiuqi.bde.bizmodel.execute.model.tfv.intf;

import com.jiuqi.bde.bizmodel.execute.model.tfv.intf.TfvOptimKey;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;

public class TfvExecuteSettingVO {
    private ExecuteSettingVO executeSettingVO;
    private TfvOptimKey optimKey;

    public TfvExecuteSettingVO() {
    }

    public TfvExecuteSettingVO(ExecuteSettingVO executeSettingVO, TfvOptimKey optimKey) {
        this.executeSettingVO = executeSettingVO;
        this.optimKey = optimKey;
    }

    public ExecuteSettingVO getExecuteSettingVO() {
        return this.executeSettingVO;
    }

    public void setExecuteSettingVO(ExecuteSettingVO executeSettingVO) {
        this.executeSettingVO = executeSettingVO;
    }

    public TfvOptimKey getOptimKey() {
        return this.optimKey;
    }

    public void setOptimKey(TfvOptimKey optimKey) {
        this.optimKey = optimKey;
    }

    public String toString() {
        return "TfvExecuteSettingVO [executeSettingVO=" + this.executeSettingVO + ", optimKey=" + this.optimKey + "]";
    }
}

