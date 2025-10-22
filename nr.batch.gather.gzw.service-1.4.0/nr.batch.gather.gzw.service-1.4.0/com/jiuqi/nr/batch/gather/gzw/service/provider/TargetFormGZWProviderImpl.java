/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.targetform.IDataBaseTableProvider
 *  com.jiuqi.nr.batch.summary.service.targetform.OriTableModelInfo
 *  com.jiuqi.nr.batch.summary.service.targetform.SumTableModelImpl
 *  com.jiuqi.nr.batch.summary.service.targetform.SumTableModelInfo
 *  com.jiuqi.nr.batch.summary.service.targetform.TargetFromProviderFactoryImpl
 *  com.jiuqi.nr.batch.summary.service.targetform.TargetFromProviderImpl
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.batch.gather.gzw.service.provider;

import com.jiuqi.nr.batch.summary.service.targetform.IDataBaseTableProvider;
import com.jiuqi.nr.batch.summary.service.targetform.OriTableModelInfo;
import com.jiuqi.nr.batch.summary.service.targetform.SumTableModelImpl;
import com.jiuqi.nr.batch.summary.service.targetform.SumTableModelInfo;
import com.jiuqi.nr.batch.summary.service.targetform.TargetFromProviderFactoryImpl;
import com.jiuqi.nr.batch.summary.service.targetform.TargetFromProviderImpl;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public class TargetFormGZWProviderImpl
extends TargetFromProviderImpl {
    public TargetFormGZWProviderImpl(TargetFromProviderFactoryImpl wrapper, SummaryScheme summaryScheme, IDataBaseTableProvider iDataBaseTableProvider) {
        super(wrapper, summaryScheme, iDataBaseTableProvider);
    }

    public SumTableModelInfo getSumTableModel(OriTableModelInfo oriTableModel, String period) {
        String summaryDBTableName = oriTableModel.getTableName();
        TableModelDefine sumTableModel = this.wrapper.dataModelService.getTableModelDefineByCode(summaryDBTableName);
        return new SumTableModelImpl(sumTableModel, this.wrapper.dataModelService, oriTableModel);
    }
}

