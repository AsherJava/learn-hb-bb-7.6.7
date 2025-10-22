/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.dataentry.bean.ReviewInfoParam;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;

@Deprecated
public interface IReviewInfoService {
    @Deprecated
    public ReviewInfoParam getReviewInfoTable(String var1);

    @Deprecated
    public ReviewInfoParam getFormulaSchemeInfoTable(String var1, String var2);

    @Deprecated
    public ReturnInfo saveOrUpdatetRevienInfo(ReviewInfoParam var1);
}

