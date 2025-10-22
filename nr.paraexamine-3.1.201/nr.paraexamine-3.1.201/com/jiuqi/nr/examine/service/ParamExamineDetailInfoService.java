/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.service;

import com.jiuqi.nr.examine.bean.ParamExamineDetailInfo;
import com.jiuqi.nr.examine.dao.ParamExamineDetailInfoDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParamExamineDetailInfoService {
    @Autowired
    private ParamExamineDetailInfoDao dao;

    public void insert(ParamExamineDetailInfo info) {
        try {
            this.dao.insert(info);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ParamExamineDetailInfo> listByExamineInfoKey(String infoKey) {
        return this.dao.listByExamineInfoKey(infoKey);
    }

    public List<ParamExamineDetailInfo> listAllRefuseByExamineInfoKey(String infoKey) {
        return this.dao.listAllRefuseByExamineInfoKey(infoKey);
    }
}

