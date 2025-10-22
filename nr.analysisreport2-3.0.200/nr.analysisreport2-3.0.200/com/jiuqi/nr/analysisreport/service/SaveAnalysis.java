/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.designer.web.rest.vo.ReturnObject
 */
package com.jiuqi.nr.analysisreport.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.analysisreport.biservice.chart.ChartTreeNode;
import com.jiuqi.nr.analysisreport.chapter.bean.CatalogVo;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportCatalogItem;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportChapterDefine;
import com.jiuqi.nr.analysisreport.facade.AnalyVersionDefine;
import com.jiuqi.nr.analysisreport.internal.AnalysisTemp;
import com.jiuqi.nr.analysisreport.vo.ReportBaseVO;
import com.jiuqi.nr.analysisreport.vo.ReportGeneratorVO;
import com.jiuqi.nr.analysisreport.vo.ReportVersionGeneratorVo;
import com.jiuqi.nr.analysisreport.vo.ReportVersionVo;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.designer.web.rest.vo.ReturnObject;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SaveAnalysis {
    public ReturnObject queryAllTemplates() throws Exception;

    public String getList() throws Exception;

    public ReturnObject getListByGroupKey(String var1) throws Exception;

    public ReturnObject getListByKey(String var1) throws Exception;

    public ReturnObject getAnalysisModelFullPathByKey(String var1) throws Exception;

    public ReturnObject insertModel(JsonNode var1) throws Exception;

    public ReturnObject updateModel(JsonNode var1) throws Exception;

    public ReturnObject deleteModel(String var1) throws Exception;

    public ReturnObject getGroupList() throws Exception;

    public ReturnObject getGroupListByRoot() throws Exception;

    public ReturnObject getGroupByKey(String var1) throws Exception;

    public ReturnObject insertGroup(JsonNode var1) throws Exception;

    public ReturnObject updateGroup(JsonNode var1) throws Exception;

    public ReturnObject deleteGroup(String var1) throws Exception;

    public ReturnObject getAnalysisTree(String var1) throws Exception;

    public ReturnObject analysisGroupTransposition(String var1, String var2) throws Exception;

    public ReturnObject analysisModelTransposition(String var1, String var2) throws Exception;

    public ReturnObject analysisReportGenerator(ReportGeneratorVO var1) throws Exception;

    public ReturnObject chooseReportTree(String var1) throws Exception;

    public ReturnObject zbListToCode(String var1) throws Exception;

    public ReturnObject modelSelectMasterView(String var1) throws Exception;

    public ReturnObject reportToCode(String var1) throws Exception;

    public ReturnObject setAnalysisExtendData(JsonNode var1) throws Exception;

    public ReturnObject queryFormById(String var1) throws Exception;

    public ReturnObject queryEntitysByElements(String var1) throws Exception;

    public ReturnObject queryTableByElement(String var1) throws UnsupportedEncodingException;

    public ReturnObject queryFormulaById(String var1) throws Exception;

    public ReturnObject queryPasteformVarTitles(String var1) throws Exception;

    public List<AnalysisTemp> getListByKeys(List<String> var1) throws Exception;

    public List<AnalysisTemp> getTempListByGroupKey(String var1) throws Exception;

    public List<ITree<ChartTreeNode>> getRootGroup(String var1);

    public ReturnObject getVersionDetailById(String var1, String var2) throws Exception;

    public ReturnObject checkVersion(ReportVersionVo var1) throws Exception;

    public ReturnObject checkVersionExceptSelf(ReportVersionVo var1) throws Exception;

    public List<AnalyVersionDefine> getVersionList(ReportBaseVO var1) throws Exception;

    public ReturnObject insertVersion(ReportVersionGeneratorVo var1) throws Exception;

    public ReturnObject updateVersion(ReportVersionVo var1) throws Exception;

    public ReturnObject renameVersionName(String var1, String var2) throws Exception;

    public ReturnObject deleteVersion(String var1) throws Exception;

    public ReturnObject getSecurityFormularTasks(String var1) throws Exception;

    public ReturnObject getSecurityFormularRuntimeTasks(String var1);

    public ReturnObject getAllTemplatesByGroupKey(String var1) throws Exception;

    public String getModelPathByKey(String var1, String var2) throws Exception;

    public List<AnalysisTemp> fuzzyQuery(String var1) throws Exception;

    public List<Map<String, Object>> getGroupAndReportTree() throws Exception;

    public boolean checkGenCatalogCompleted(String var1, String var2) throws Exception;

    public List<ReportChapterDefine> getVersionChapterList(String var1) throws Exception;

    public List<ReportCatalogItem> loadCatalogTree(CatalogVo var1) throws Exception;

    public List<ReportCatalogItem> getLocateCatalogTree(String var1, String var2, String var3) throws Exception;

    public void updateTempModifiedTime(String var1, Date var2);

    public ReturnObject queryVarDim(String var1);

    public Map<String, List<ReportCatalogItem>> buidlCatalogTree(String var1, String var2) throws Exception;
}

