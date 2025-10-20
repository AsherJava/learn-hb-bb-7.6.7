/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.BIDataSetNotFoundException
 *  com.jiuqi.bi.dataset.manager.DataSetManagerFactory
 *  com.jiuqi.bi.dataset.manager.IDataSetManager
 *  com.jiuqi.bi.dataset.model.DSModel
 */
package com.jiuqi.bi.quickreport.engine.context;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetNotFoundException;
import com.jiuqi.bi.dataset.manager.DataSetManagerFactory;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.quickreport.engine.IReportListener;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.engine.parser.IDataSetModelProvider;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.model.DataSetInfo;
import com.jiuqi.bi.quickreport.model.QuickReport;
import java.util.HashMap;
import java.util.Map;

public final class ReportDataSetProvider
implements IDataSetModelProvider {
    private Map<String, DataSetInfo> refDataSets = new HashMap<String, DataSetInfo>();
    private IDataSetManager dataSetManager;
    private IReportListener listener;

    public ReportDataSetProvider(QuickReport report) {
        for (DataSetInfo dataSetInfo : report.getRefDataSets()) {
            String dsName = dataSetInfo.getId();
            this.refDataSets.put(dsName.toUpperCase(), dataSetInfo);
        }
        this.dataSetManager = DataSetManagerFactory.create();
    }

    @Override
    public DSModel findModel(String datasetName) throws ReportExpressionException {
        String dsName = datasetName.toUpperCase();
        DSModel model = null;
        if (this.listener != null) {
            try {
                model = this.listener.openDSModel(dsName);
            }
            catch (ReportEngineException e) {
                throw new ReportExpressionException(e);
            }
        }
        if (model == null) {
            DataSetInfo dsInfo = this.refDataSets.get(dsName);
            if (dsInfo == null) {
                return null;
            }
            try {
                model = this.dataSetManager.findModel(dsName, dsInfo.getType());
            }
            catch (BIDataSetNotFoundException e) {
                return null;
            }
            catch (BIDataSetException e) {
                throw new ReportExpressionException("\u52a0\u8f7d\u6570\u636e\u96c6\u6a21\u578b[" + datasetName + "]\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
        }
        return model;
    }

    public IReportListener getListener() {
        return this.listener;
    }

    public void setListener(IReportListener listener) {
        this.listener = listener;
    }
}

