/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.intf;

import com.jiuqi.nr.transmission.data.vo.MappingSchemeVO;
import java.util.List;

public class ImportReturnParam {
    private boolean isFlowType;
    private boolean hasWorkFlow;
    private String fileKey;
    private List<MappingSchemeVO> mappingSchemes;

    public boolean isFlowType() {
        return this.isFlowType;
    }

    public void setFlowType(boolean flowType) {
        this.isFlowType = flowType;
    }

    public boolean isHasWorkFlow() {
        return this.hasWorkFlow;
    }

    public boolean getHasWorkFlow() {
        return this.hasWorkFlow;
    }

    public void setHasWorkFlow(boolean hasWorkFlow) {
        this.hasWorkFlow = hasWorkFlow;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public List<MappingSchemeVO> getMappingSchemes() {
        return this.mappingSchemes;
    }

    public void setMappingSchemes(List<MappingSchemeVO> mappingSchemes) {
        this.mappingSchemes = mappingSchemes;
    }
}

