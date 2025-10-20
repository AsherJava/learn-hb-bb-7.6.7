/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.item.service;

import com.jiuqi.gcreport.financialcheckcore.item.RelatedItemSaveParam;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import java.util.List;

public interface GcRelatedItemCommandService {
    public void batchDelete(List<String> var1);

    public void updateCheckSchemeInfo(List<GcRelatedItemEO> var1);

    public void cancelCheck(List<GcRelatedItemEO> var1, boolean var2);

    public void doCheck(List<GcRelatedItemEO> var1, boolean var2);

    public void batchSave(RelatedItemSaveParam var1);
}

