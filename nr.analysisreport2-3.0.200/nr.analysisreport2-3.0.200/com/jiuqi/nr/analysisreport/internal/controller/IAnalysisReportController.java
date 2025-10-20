/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.internal.controller;

import com.jiuqi.nr.analysisreport.facade.AnalyVersionDefine;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportGroupDefine;
import java.util.Date;
import java.util.List;

public interface IAnalysisReportController {
    public List<AnalysisReportDefine> getList() throws Exception;

    public List<AnalysisReportDefine> getListByGroupKey(String var1) throws Exception;

    public AnalysisReportDefine getListByKey(String var1) throws Exception;

    public String insertModel(AnalysisReportDefine var1) throws Exception;

    public String updateModel(AnalysisReportDefine var1) throws Exception;

    public String deleteModel(String var1) throws Exception;

    public List<AnalysisReportGroupDefine> getGroupList() throws Exception;

    public List<AnalysisReportGroupDefine> getGroupByParent(String var1) throws Exception;

    public AnalysisReportGroupDefine getGroupByKey(String var1) throws Exception;

    public String insertGroup(AnalysisReportGroupDefine var1) throws Exception;

    public String updateGroup(AnalysisReportGroupDefine var1) throws Exception;

    public String deleteGroup(String var1) throws Exception;

    public List<AnalyVersionDefine> getVersionListByname(String var1, String var2, String var3, String var4) throws Exception;

    public List<AnalyVersionDefine> getVersionList(String var1, String var2, String var3) throws Exception;

    public List<AnalyVersionDefine> getVersionList(String var1) throws Exception;

    public String insertVersion(AnalyVersionDefine var1) throws Exception;

    public String updateVersion(AnalyVersionDefine var1) throws Exception;

    public AnalyVersionDefine getVersionBykey(String var1) throws Exception;

    public Integer deleteVersionBykey(String var1) throws Exception;

    public Integer deleteVersionByModelKey(String var1) throws Exception;

    public Date getCatalogUpdateTime(String var1) throws Exception;
}

