/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.dto;

import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.SimpleCustomComposePluginDataVO;
import java.util.List;

public class SimpleComposeDateDTO {
    private SimpleCustomComposePluginDataVO simpleCustomComposePluginDataVO;
    private List<FloatQueryFieldVO> queryFields;
    private List<String> usedFields;

    public SimpleCustomComposePluginDataVO getSimpleCustomComposePluginDataVO() {
        return this.simpleCustomComposePluginDataVO;
    }

    public void setSimpleCustomComposePluginDataVO(SimpleCustomComposePluginDataVO simpleCustomComposePluginDataVO) {
        this.simpleCustomComposePluginDataVO = simpleCustomComposePluginDataVO;
    }

    public List<FloatQueryFieldVO> getQueryFields() {
        return this.queryFields;
    }

    public void setQueryFields(List<FloatQueryFieldVO> queryFields) {
        this.queryFields = queryFields;
    }

    public List<String> getUsedFields() {
        return this.usedFields;
    }

    public void setUsedFields(List<String> usedFields) {
        this.usedFields = usedFields;
    }

    public SimpleComposeDateDTO(SimpleCustomComposePluginDataVO simpleCustomComposePluginDataVO, List<FloatQueryFieldVO> queryFields, List<String> usedFields) {
        this.simpleCustomComposePluginDataVO = simpleCustomComposePluginDataVO;
        this.queryFields = queryFields;
        this.usedFields = usedFields;
    }
}

