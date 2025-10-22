/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  io.netty.util.internal.StringUtil
 */
package com.jiuqi.nr.analysisreport.internal.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.analysisreport.dao.AnalysisReportDefineDao;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import io.netty.util.internal.StringUtil;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalysisReportService {
    @Autowired
    AnalysisReportDefineDao analysisReportDefineDao;

    public List<AnalysisReportDefine> getList() throws Exception {
        return this.analysisReportDefineDao.getList();
    }

    public List<AnalysisReportDefine> getListByGroupKey(String key) throws Exception {
        return this.analysisReportDefineDao.getListByGroupKey(key);
    }

    public AnalysisReportDefine getListByKey(String key) throws Exception {
        return this.analysisReportDefineDao.getListByKey(key);
    }

    public String insertModel(AnalysisReportDefine define) throws Exception {
        if (StringUtil.isNullOrEmpty((String)define.getKey())) {
            define.setKey(UUIDUtils.getKey());
            String userId = NpContextHolder.getContext().getUserId();
            define.setCreateuser(userId);
            define.setOrder(OrderGenerator.newOrder());
        } else {
            if (StringUtil.isNullOrEmpty((String)define.getOrder())) {
                define.setOrder(OrderGenerator.newOrder());
            }
            if (StringUtil.isNullOrEmpty((String)define.getCreateuser())) {
                String userId = NpContextHolder.getContext().getUserId();
                define.setCreateuser(userId);
            }
        }
        this.analysisReportDefineDao.insertModel(define);
        return define.getKey();
    }

    public String updateModel(AnalysisReportDefine define) throws Exception {
        this.analysisReportDefineDao.updateModel(define);
        return define.getKey();
    }

    public String deleteModel(String key) throws Exception {
        this.analysisReportDefineDao.deleteModel(key);
        return key;
    }

    public List<AnalysisReportDefine> fuzzyQuery(String keyWords) {
        return this.analysisReportDefineDao.fuzzyQuery(keyWords);
    }

    public void updateTemplateLastModifiedTime(String key, Date data) {
        this.analysisReportDefineDao.updateTemplateLastModifiedTime(key, data);
    }

    public Date getCatalogUpdateTime(String key) throws Exception {
        return this.analysisReportDefineDao.getCatalogUpdateTime(key);
    }
}

