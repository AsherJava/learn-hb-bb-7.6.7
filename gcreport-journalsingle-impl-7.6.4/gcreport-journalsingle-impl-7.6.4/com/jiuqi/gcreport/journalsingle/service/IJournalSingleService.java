/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.dimension.internal.entity.DimensionEO
 *  com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition
 *  com.jiuqi.gcreport.journalsingle.vo.JournalEnvContextVO
 *  com.jiuqi.gcreport.journalsingle.vo.JournalSingleVO
 */
package com.jiuqi.gcreport.journalsingle.service;

import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition;
import com.jiuqi.gcreport.journalsingle.entity.JournalSingleEO;
import com.jiuqi.gcreport.journalsingle.vo.JournalEnvContextVO;
import com.jiuqi.gcreport.journalsingle.vo.JournalSingleVO;
import java.util.List;

public interface IJournalSingleService {
    public Integer batchUpdatePostFlag(String var1, String var2, String var3, String var4, String var5);

    public Integer batchUpdatePostFlag(String var1, String var2, String var3, String var4, String var5, String var6);

    public void addJournalDetail(List<List<JournalSingleVO>> var1, boolean var2);

    public int queryPageCountByCondition(JournalDetailCondition var1);

    public List<JournalSingleEO> queryByPageCondition(JournalDetailCondition var1);

    public void SingleDeleteByMrecid(List<String> var1, int var2, int var3, int var4);

    public List<JournalSingleEO> queryDetailByID(JournalDetailCondition var1);

    public List<JournalSingleEO> queryDetailByMrecid(String var1);

    public void updatePostFlag(List<String> var1);

    public JournalSingleVO convertEO2VO(JournalSingleEO var1, String var2, List<DimensionEO> var3);

    public List<JournalEnvContextVO> queryJournalByDims(String var1, String var2, String var3, String var4, String var5, String var6);

    public List<JournalSingleEO> listJournalSingleByDims(String var1, String var2, String var3, String var4, String var5, String var6);

    public List<JournalSingleEO> queryByCondition(JournalDetailCondition var1);
}

