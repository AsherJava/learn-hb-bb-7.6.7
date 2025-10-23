/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataRow
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.quickreport.engine.ReportEngineException
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.zbquery.engine.report;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.zbquery.engine.dataset.QueryDSModelBuilder;
import com.jiuqi.nr.zbquery.engine.report.ReportListener;
import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nr.zbquery.model.PageInfo;
import com.jiuqi.nr.zbquery.model.ZBField;
import java.util.ArrayList;
import java.util.List;

public class ExportReportListener
extends ReportListener {
    private BIDataSet dataSet;

    public ExportReportListener(String cacheId, QueryDSModelBuilder dsModelBuilder, ConditionValues conditionValues, PageInfo pageInfo) {
        super(cacheId, dsModelBuilder, conditionValues, pageInfo);
    }

    @Override
    public BIDataSet openDataSet(String dsName) throws ReportEngineException {
        if (this.dataSet == null) {
            this.dataSet = super.openDataSet(dsName);
            this.update();
        }
        return this.dataSet;
    }

    private void update() {
        ArrayList<Integer> fileZBIndexs = new ArrayList<Integer>();
        List<ZBField> zbFields = this.dsModelBuilder.getQueryModelBuilder().getModelFinder().getLayoutZBFields(null);
        for (ZBField zbField : zbFields) {
            int zbIndex;
            DataFieldType dataFieldType = DataFieldType.valueOf((int)zbField.getDataType());
            if (dataFieldType != DataFieldType.FILE && dataFieldType != DataFieldType.PICTURE || (zbIndex = this.dataSet.getMetadata().indexOf(this.dsModelBuilder.getQueryModelBuilder().getFullNameAliasMapper().get(zbField.getFullName()))) == -1) continue;
            fileZBIndexs.add(zbIndex);
        }
        if (fileZBIndexs.size() > 0) {
            int i;
            int[] zbIndexs = new int[fileZBIndexs.size()];
            for (i = 0; i < zbIndexs.length; ++i) {
                zbIndexs[i] = (Integer)fileZBIndexs.get(i);
            }
            for (i = 0; i < this.dataSet.getRecordCount(); ++i) {
                BIDataRow dataRow = this.dataSet.get(i);
                for (int j = 0; j < zbIndexs.length; ++j) {
                    dataRow.getBuffer()[zbIndexs[j]] = null;
                }
            }
        }
    }
}

