/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatArgsMappingVO
 *  com.jiuqi.bde.common.dto.FloatQueryFieldVO
 *  com.jiuqi.bde.common.dto.FloatZbMappingVO
 */
package com.jiuqi.gcreport.bde.common.vo;

import com.jiuqi.bde.common.dto.FloatArgsMappingVO;
import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.FloatZbMappingVO;
import java.util.List;

public class MappingInfoVO {
    List<FloatArgsMappingVO> argsMapping;
    List<FloatZbMappingVO> zbMapping;
    List<FloatQueryFieldVO> queryFields;
    List<String> usedFields;

    public List<FloatArgsMappingVO> getArgsMapping() {
        return this.argsMapping;
    }

    public void setArgsMapping(List<FloatArgsMappingVO> argsMapping) {
        this.argsMapping = argsMapping;
    }

    public List<FloatZbMappingVO> getZbMapping() {
        return this.zbMapping;
    }

    public void setZbMapping(List<FloatZbMappingVO> zbMapping) {
        this.zbMapping = zbMapping;
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
}

