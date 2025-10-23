/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.web.vo;

import com.jiuqi.nr.dafafill.model.enums.ModelType;
import com.jiuqi.nr.dafafill.web.vo.ZBSelectorZBInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class ZBSelectorParam {
    private String dataSchemeCode;
    private String taskCode;
    private String taskKey;
    private String fmdmFormKey;
    private String hasZB;
    private ModelType modelType;
    private List<ZBSelectorZBInfo> zbList;

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFmdmFormKey() {
        return this.fmdmFormKey;
    }

    public void setFmdmFormKey(String fmdmFormKey) {
        this.fmdmFormKey = fmdmFormKey;
    }

    public String getHasZB() {
        return this.hasZB;
    }

    public void setHasZB(String hasZB) {
        this.hasZB = hasZB;
    }

    public ModelType getModelType() {
        return this.modelType;
    }

    public void setModelType(ModelType modelType) {
        this.modelType = modelType;
    }

    public List<ZBSelectorZBInfo> getZbList() {
        return this.zbList;
    }

    public void setZbList(List<ZBSelectorZBInfo> zbList) {
        this.zbList = zbList;
    }

    public List<String> getZbKeyList() {
        if (CollectionUtils.isEmpty(this.zbList)) {
            return new ArrayList<String>();
        }
        return this.zbList.stream().map(ZBSelectorZBInfo::getZb).collect(Collectors.toList());
    }
}

