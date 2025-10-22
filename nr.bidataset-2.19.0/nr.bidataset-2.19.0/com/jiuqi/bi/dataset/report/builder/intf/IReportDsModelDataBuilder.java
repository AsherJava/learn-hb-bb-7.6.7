/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.report.builder.intf;

import com.jiuqi.bi.dataset.report.builder.intf.IReportDsModelFieldParam;
import com.jiuqi.bi.dataset.report.exception.ReportDsModelDataBuildException;
import com.jiuqi.bi.dataset.report.model.ReportDsModelDefine;
import com.jiuqi.bi.dataset.report.model.ReportDsParameter;
import com.jiuqi.bi.dataset.report.model.ReportExpField;
import java.util.List;

public interface IReportDsModelDataBuilder {
    public ReportDsModelDefine buildDefault(String var1) throws ReportDsModelDataBuildException;

    public List<ReportExpField> buildExpFields(List<ReportDsParameter> var1, List<IReportDsModelFieldParam> var2) throws ReportDsModelDataBuildException;
}

