/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.service;

import com.jiuqi.nr.multcheck2.bean.MCSchemeParam;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import java.util.List;

public interface IMCParamService {
    public List<MCSchemeParam> exportMCSchemeParams(String var1);

    public List<MultcheckScheme> query(String var1);

    public void batchDeleteSchemeByKeys(List<String> var1);

    public void deleteSchemeByFormScheme(String var1);

    public void batchAddMCSParams(List<MCSchemeParam> var1);

    public void batchModifyMCSParams(List<MCSchemeParam> var1);
}

