/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 */
package com.jiuqi.bi.quickreport.engine;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import java.util.List;

public interface IReportListener {
    default public void openReport(QuickReport report, int version) throws ReportEngineException {
    }

    default public DSModel openDSModel(String dsName) throws ReportEngineException {
        return null;
    }

    default public BIDataSet openDataSet(String dsName) throws ReportEngineException {
        return null;
    }

    default public List<ParameterModel> openParamModels(List<String> parameterNames, String storageType) throws ReportEngineException {
        return null;
    }

    default public List<ParameterModel> openParamModels(List<String> parameterNames, String ownerName, String ownerType, String storageType) throws ReportEngineException {
        return null;
    }
}

