/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  io.netty.util.internal.StringUtil
 */
package com.jiuqi.nr.analysisreport.internal.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.analysisreport.dao.AnalysisReportGroupDefineDao;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportGroupDefine;
import io.netty.util.internal.StringUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalysisReportGroupService {
    @Autowired
    AnalysisReportGroupDefineDao analysisReportGroupDefineDao;

    public List<AnalysisReportGroupDefine> getGroupList() throws Exception {
        return this.analysisReportGroupDefineDao.getGroupList();
    }

    public List<AnalysisReportGroupDefine> getGroupByParent(String parent) throws Exception {
        return this.analysisReportGroupDefineDao.getGroupByParent(parent);
    }

    public AnalysisReportGroupDefine getGroupByKey(String key) throws Exception {
        return this.analysisReportGroupDefineDao.getGroupByKey(key);
    }

    public String insertGroup(AnalysisReportGroupDefine define) throws Exception {
        if (null == define.getKey()) {
            define.setKey(UUIDUtils.getKey());
            define.setOrder(OrderGenerator.newOrder());
        } else {
            if (StringUtil.isNullOrEmpty((String)define.getKey())) {
                define.setKey(UUIDUtils.getKey());
            }
            if (StringUtil.isNullOrEmpty((String)define.getOrder())) {
                define.setOrder(OrderGenerator.newOrder());
            }
        }
        this.analysisReportGroupDefineDao.insertGroup(define);
        return define.getKey();
    }

    public String updateGroup(AnalysisReportGroupDefine define) throws Exception {
        this.analysisReportGroupDefineDao.updateGroup(define);
        return define.getKey();
    }

    public String deleteGroup(String key) throws Exception {
        this.analysisReportGroupDefineDao.deleteGroup(key);
        return key;
    }

    public List<AnalysisReportGroupDefine> fuzzyQuery(String keyWords) {
        return this.analysisReportGroupDefineDao.fuzzyQuery(keyWords);
    }
}

