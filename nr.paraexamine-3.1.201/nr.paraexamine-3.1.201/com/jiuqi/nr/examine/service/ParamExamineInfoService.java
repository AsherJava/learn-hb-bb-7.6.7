/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.service;

import com.jiuqi.nr.examine.bean.ParamExamineDetailInfo;
import com.jiuqi.nr.examine.bean.ParamExamineInfo;
import com.jiuqi.nr.examine.dao.ParamExamineDetailInfoDao;
import com.jiuqi.nr.examine.dao.ParamExamineInfoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParamExamineInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ParamExamineInfoService.class);
    @Autowired
    private ParamExamineInfoDao dao;
    @Autowired
    private ParamExamineDetailInfoDao detailDao;

    public void insert(ParamExamineInfo examineInfo) {
        try {
            this.dao.insert(examineInfo);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void insertDetail(ParamExamineDetailInfo detail) {
        try {
            this.detailDao.insert(detail);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void finish(String checkInfoKey) {
        try {
            this.dao.finish(checkInfoKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public ParamExamineInfo getLastExamineInfo() {
        return this.dao.getLast();
    }
}

