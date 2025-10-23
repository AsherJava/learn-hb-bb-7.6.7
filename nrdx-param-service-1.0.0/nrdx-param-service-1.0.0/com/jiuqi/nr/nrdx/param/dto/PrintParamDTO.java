/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType
 */
package com.jiuqi.nr.nrdx.param.dto;

import com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType;
import com.jiuqi.nr.nrdx.param.dto.ParamDTO;
import java.util.List;
import java.util.Map;

public class PrintParamDTO
extends ParamDTO {
    private List<String> printSchemes;
    private String printScheme;
    boolean all;
    Map<String, List<String>> formMap;

    public PrintParamDTO() {
    }

    public PrintParamDTO(String printScheme, List<String> paramKeys, NrdxParamNodeType resourceType) {
        super(paramKeys, resourceType);
        this.printScheme = printScheme;
    }

    public List<String> getPrintSchemes() {
        return this.printSchemes;
    }

    public void setPrintSchemes(List<String> printSchemes) {
        this.printSchemes = printSchemes;
    }

    public String getPrintScheme() {
        return this.printScheme;
    }

    public void setPrintScheme(String printScheme) {
        this.printScheme = printScheme;
    }

    public boolean isAll() {
        return this.all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    public Map<String, List<String>> getFormMap() {
        return this.formMap;
    }

    public void setFormMap(Map<String, List<String>> formMap) {
        this.formMap = formMap;
    }
}

