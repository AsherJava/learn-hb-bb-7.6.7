/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition
 *  com.jiuqi.gcreport.journalsingle.condition.JournalSinglePostCondition
 *  com.jiuqi.gcreport.journalsingle.vo.JournalAdjustedFiguresVO
 *  com.jiuqi.gcreport.journalsingle.vo.JournalSingleVO
 *  com.jiuqi.gcreport.journalsingle.vo.JournalWorkPaperDataVO
 */
package com.jiuqi.gcreport.journalsingle.service;

import com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition;
import com.jiuqi.gcreport.journalsingle.condition.JournalSinglePostCondition;
import com.jiuqi.gcreport.journalsingle.vo.JournalAdjustedFiguresVO;
import com.jiuqi.gcreport.journalsingle.vo.JournalSingleVO;
import com.jiuqi.gcreport.journalsingle.vo.JournalWorkPaperDataVO;
import java.util.List;

public interface IJournalSinglePostService {
    public String postData(JournalSinglePostCondition var1);

    public List<JournalWorkPaperDataVO> getJournalWorkPaperData(JournalDetailCondition var1);

    public List<JournalSingleVO> getPenerationData(JournalDetailCondition var1);

    public JournalAdjustedFiguresVO getAdjustedfigure(JournalSinglePostCondition var1, String var2);
}

