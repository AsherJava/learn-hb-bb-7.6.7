/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.analysisreport.internal.service;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.analysisreport.dao.AnalyVersionDefineDao;
import com.jiuqi.nr.analysisreport.facade.AnalyVersionDefine;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyVersionService {
    @Autowired
    private AnalyVersionDefineDao analyVersionDefineDao;

    public String insertVersion(AnalyVersionDefine define) throws Exception {
        if (null == define.getKey()) {
            define.setKey(UUIDUtils.getKey());
        }
        this.analyVersionDefineDao.insertVersion(define);
        return define.getKey();
    }

    public String updateVersion(AnalyVersionDefine define) throws Exception {
        this.analyVersionDefineDao.updateVersion(define);
        return define.getKey();
    }

    public int deleteBanben(AnalyVersionDefine define) throws Exception {
        return this.analyVersionDefineDao.deleteBanben(define);
    }

    public int deleteVersionBykey(String key) throws Exception {
        return this.analyVersionDefineDao.deleteVersionByKey(key);
    }

    public AnalyVersionDefine getVersionBykey(String analyVersionDefine) throws Exception {
        return this.analyVersionDefineDao.getListByKey(analyVersionDefine);
    }

    public List<AnalyVersionDefine> getVersionList(String analytemplateKey, String entitykey, String date) {
        return this.analyVersionDefineDao.getVersionList(analytemplateKey, entitykey, date);
    }

    public List<AnalyVersionDefine> getVersionListByname(String analytemplateKey, String entitykey, String date, String name) {
        return this.analyVersionDefineDao.getVersionListByname(analytemplateKey, entitykey, date, name);
    }

    public List<AnalyVersionDefine> getListByModelKey(String modelKey) {
        return this.analyVersionDefineDao.getListByModelKey(modelKey);
    }

    public int[] batchDeleteVersion(AnalyVersionDefine[] analyVersionDefines) throws DBParaException {
        return this.analyVersionDefineDao.batchDeleteVersion(analyVersionDefines);
    }

    public int deleteBymodelKey(String modelKey) throws DBParaException {
        return this.analyVersionDefineDao.deleteBymodelKey(modelKey);
    }
}

