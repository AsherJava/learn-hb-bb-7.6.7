/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.client.vo;

import com.jiuqi.bde.bizmodel.client.vo.FetchSettingExtInfoVo;
import java.util.Map;

public class ExtInfoResultVO {
    private String logicFormulaMemo;
    Map<String, FetchSettingExtInfoVo> bizModelSettingExtInfoMap;

    public String getLogicFormulaMemo() {
        return this.logicFormulaMemo;
    }

    public void setLogicFormulaMemo(String logicFormulaMemo) {
        this.logicFormulaMemo = logicFormulaMemo;
    }

    public Map<String, FetchSettingExtInfoVo> getBizModelSettingExtInfoMap() {
        return this.bizModelSettingExtInfoMap;
    }

    public void setBizModelSettingExtInfoMap(Map<String, FetchSettingExtInfoVo> bizModelSettingExtInfoMap) {
        this.bizModelSettingExtInfoMap = bizModelSettingExtInfoMap;
    }
}

