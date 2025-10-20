/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatArgsMappingVO
 */
package com.jiuqi.bde.floatmodel.client.vo;

import com.jiuqi.bde.common.dto.FloatArgsMappingVO;
import java.util.List;

public class VaQueryPluginDataVO {
    private String queryDefineCode;
    List<FloatArgsMappingVO> argsMapping;

    public String getQueryDefineCode() {
        return this.queryDefineCode;
    }

    public void setQueryDefineCode(String queryDefineCode) {
        this.queryDefineCode = queryDefineCode;
    }

    public List<FloatArgsMappingVO> getArgsMapping() {
        return this.argsMapping;
    }

    public void setArgsMapping(List<FloatArgsMappingVO> argsMapping) {
        this.argsMapping = argsMapping;
    }
}

