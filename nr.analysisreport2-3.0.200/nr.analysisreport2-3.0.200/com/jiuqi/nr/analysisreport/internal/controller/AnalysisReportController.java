/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.internal.controller;

import com.jiuqi.nr.analysisreport.facade.AnalyVersionDefine;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportGroupDefine;
import com.jiuqi.nr.analysisreport.internal.controller.IAnalysisReportController;
import com.jiuqi.nr.analysisreport.internal.service.AnalyVersionService;
import com.jiuqi.nr.analysisreport.internal.service.AnalysisReportGroupService;
import com.jiuqi.nr.analysisreport.internal.service.AnalysisReportService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnalysisReportController
implements IAnalysisReportController {
    @Autowired
    AnalysisReportService modelService;
    @Autowired
    AnalysisReportGroupService groupService;
    @Autowired
    AnalyVersionService analyVersionService;

    @Override
    public List<AnalysisReportDefine> getList() throws Exception {
        return this.modelService.getList();
    }

    @Override
    public List<AnalysisReportDefine> getListByGroupKey(String key) throws Exception {
        return this.modelService.getListByGroupKey(key);
    }

    @Override
    public AnalysisReportDefine getListByKey(String key) throws Exception {
        return this.modelService.getListByKey(key);
    }

    @Override
    public String insertModel(AnalysisReportDefine define) throws Exception {
        return this.modelService.insertModel(define);
    }

    @Override
    public String updateModel(AnalysisReportDefine define) throws Exception {
        return this.modelService.updateModel(define);
    }

    @Override
    public String deleteModel(String key) throws Exception {
        return this.modelService.deleteModel(key);
    }

    @Override
    public List<AnalysisReportGroupDefine> getGroupList() throws Exception {
        return this.groupService.getGroupList();
    }

    @Override
    public List<AnalysisReportGroupDefine> getGroupByParent(String parent) throws Exception {
        return this.groupService.getGroupByParent(parent);
    }

    @Override
    public AnalysisReportGroupDefine getGroupByKey(String key) throws Exception {
        return this.groupService.getGroupByKey(key);
    }

    @Override
    public String insertGroup(AnalysisReportGroupDefine define) throws Exception {
        return this.groupService.insertGroup(define);
    }

    @Override
    public String updateGroup(AnalysisReportGroupDefine define) throws Exception {
        return this.groupService.updateGroup(define);
    }

    @Override
    public String deleteGroup(String key) throws Exception {
        return this.groupService.deleteGroup(key);
    }

    @Override
    public List<AnalyVersionDefine> getVersionListByname(String analytemplateKey, String entitykey, String date, String name) throws Exception {
        return this.analyVersionService.getVersionListByname(analytemplateKey, entitykey, date, name);
    }

    @Override
    public List<AnalyVersionDefine> getVersionList(String analytemplateKey, String entitykey, String date) throws Exception {
        return this.analyVersionService.getVersionList(analytemplateKey, entitykey, date);
    }

    @Override
    public List<AnalyVersionDefine> getVersionList(String analytemplateKey) throws Exception {
        return this.analyVersionService.getListByModelKey(analytemplateKey);
    }

    @Override
    public String insertVersion(AnalyVersionDefine define) throws Exception {
        return this.analyVersionService.insertVersion(define);
    }

    @Override
    public String updateVersion(AnalyVersionDefine define) throws Exception {
        return this.analyVersionService.updateVersion(define);
    }

    @Override
    public AnalyVersionDefine getVersionBykey(String analyVersionDefine) throws Exception {
        return this.analyVersionService.getVersionBykey(analyVersionDefine);
    }

    @Override
    public Integer deleteVersionBykey(String key) throws Exception {
        return this.analyVersionService.deleteVersionBykey(key);
    }

    @Override
    public Integer deleteVersionByModelKey(String key) throws Exception {
        return this.analyVersionService.deleteBymodelKey(key);
    }

    @Override
    public Date getCatalogUpdateTime(String key) throws Exception {
        return this.modelService.getCatalogUpdateTime(key);
    }
}

