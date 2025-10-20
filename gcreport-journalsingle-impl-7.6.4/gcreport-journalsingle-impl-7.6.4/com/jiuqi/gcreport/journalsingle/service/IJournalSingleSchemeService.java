/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.journalsingle.vo.JournalRelateSchemeVO
 */
package com.jiuqi.gcreport.journalsingle.service;

import com.jiuqi.gcreport.journalsingle.vo.JournalRelateSchemeVO;
import java.util.List;

public interface IJournalSingleSchemeService {
    public String insertRelateScheme(JournalRelateSchemeVO var1);

    public Integer deleteRelateScheme(JournalRelateSchemeVO var1);

    public List<JournalRelateSchemeVO> listRelateSchemes();

    public String getRelateSchemeId(String var1, String var2, String var3);
}

