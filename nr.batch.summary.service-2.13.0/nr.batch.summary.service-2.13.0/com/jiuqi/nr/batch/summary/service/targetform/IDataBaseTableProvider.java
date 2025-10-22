/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.batch.summary.service.targetform;

import com.jiuqi.nr.batch.summary.service.targetform.OriTableModelInfo;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public interface IDataBaseTableProvider {
    public TableModelDefine getOriginTableModelDefine(String var1, String var2, String var3);

    public TableModelDefine getSumTableModelDefine(OriTableModelInfo var1, SummaryScheme var2, String var3);
}

