/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputConditionVO
 *  com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputVO
 *  com.jiuqi.gcreport.financialcheckapi.dataentry.vo.GcRelatedItemVO
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.service;

import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputConditionVO;
import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputVO;
import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.GcRelatedItemVO;
import java.util.List;

public interface GcFinancialCheckDataEntryService {
    public DataInputVO query(DataInputConditionVO var1);

    public void saveRelatedItems(List<GcRelatedItemVO> var1);

    public List<GcRelatedItemVO> listByIds(List<String> var1);
}

