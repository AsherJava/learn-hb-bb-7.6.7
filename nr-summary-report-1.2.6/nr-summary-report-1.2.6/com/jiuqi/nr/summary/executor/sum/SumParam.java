/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.datascheme.api.DataScheme
 */
package com.jiuqi.nr.summary.executor.sum;

import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.model.soulution.DimensionData;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import java.io.Serializable;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class SumParam
implements Serializable {
    @NonNull
    private SummarySolutionModel summarySolutionModel;
    @NonNull
    private PeriodWrapper currPeriod;
    @Nullable
    private List<DimensionData> dimDataRange;
    @Nullable
    private List<SummaryReportModel> summaryReportModels;
    private DataScheme summaryDataScheme;
    private List<DataScheme> sourceDataScheme;

    public PeriodWrapper getCurrPeriod() {
        return this.currPeriod;
    }

    public void setCurrPeriod(PeriodWrapper currPeriod) {
        this.currPeriod = currPeriod;
    }

    public SummarySolutionModel getSummarySolutionModel() {
        return this.summarySolutionModel;
    }

    public List<DimensionData> getDimDataRange() {
        return this.dimDataRange;
    }

    public List<SummaryReportModel> getSummaryReportModels() {
        return this.summaryReportModels;
    }

    public DataScheme getSummaryDataScheme() {
        return this.summaryDataScheme;
    }

    public List<DataScheme> getSourceDataScheme() {
        return this.sourceDataScheme;
    }

    public void setSummarySolutionModel(SummarySolutionModel summarySolutionModel) {
        this.summarySolutionModel = summarySolutionModel;
    }

    public void setDimDataRange(List<DimensionData> dimDataRange) {
        this.dimDataRange = dimDataRange;
    }

    public void setSummaryReportModels(List<SummaryReportModel> summaryReportModels) {
        this.summaryReportModels = summaryReportModels;
    }

    public void setSummaryDataScheme(DataScheme summaryDataScheme) {
        this.summaryDataScheme = summaryDataScheme;
    }

    public void setSourceDataScheme(List<DataScheme> sourceDataScheme) {
        this.sourceDataScheme = sourceDataScheme;
    }
}

