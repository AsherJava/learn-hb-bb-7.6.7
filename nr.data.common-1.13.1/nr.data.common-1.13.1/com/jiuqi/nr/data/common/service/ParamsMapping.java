/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common.service;

import com.jiuqi.nr.data.common.service.dto.DataFieldMp;
import com.jiuqi.nr.data.common.service.dto.FormulaMp;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface ParamsMapping {
    public boolean tryPeriodMap();

    public Map<String, String> getOriginPeriod(List<String> var1);

    default public String getOriginPeriod(String dataTime) {
        return this.getOriginPeriod(Collections.singletonList(dataTime)).get(dataTime);
    }

    public boolean tryOrgCodeMap();

    public Map<String, String> getOriginOrgCode(List<String> var1);

    default public String getOriginOrgCode(String dwCode) {
        return this.getOriginOrgCode(Collections.singletonList(dwCode)).get(dwCode);
    }

    public boolean tryFormCodeMap();

    public Map<String, String> getOriginFormCode(List<String> var1);

    default public boolean tryFormKeyMap() {
        return false;
    }

    default public Map<String, String> getOriginFormKey(List<String> formKeys) {
        return null;
    }

    public boolean tryFormSchemeCodeMap();

    public String getOriginFormSchemeCode(String var1);

    default public boolean tryFormSchemeKeyMap() {
        return false;
    }

    default public String getOriginFormSchemeKey(String formSchemeKey) {
        return formSchemeKey;
    }

    default public boolean tryTaskKeyMap() {
        return false;
    }

    default public String getOriginTaskKey(String taskKey) {
        return taskKey;
    }

    public boolean tryTaskCodeMap();

    public String getOriginTaskCode(String var1);

    public boolean tryFormulaSchemeCodeMap();

    public Map<String, String> getOriginFormulaScheme(List<String> var1);

    public boolean tryBaseDataMap(String var1);

    public Map<String, String> getOriginBaseData(String var1, List<String> var2);

    public boolean tryFormulaMap(String var1);

    public Map<String, String> getOriginFormula(String var1, List<String> var2);

    public boolean tryDataFieldMap(String var1);

    public Map<String, DataFieldMp> getOriginDataFieldCode(String var1, List<String> var2);

    default public FormulaMp getFormula(FormulaMp formula) {
        return null;
    }
}

