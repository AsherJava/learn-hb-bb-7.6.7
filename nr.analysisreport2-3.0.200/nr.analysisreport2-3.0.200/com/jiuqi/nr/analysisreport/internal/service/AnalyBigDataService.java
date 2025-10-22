/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.analysisreport.internal.service;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.analysisreport.dao.AnalyBigdataDao;
import com.jiuqi.nr.analysisreport.facade.AnalyBigdataDefine;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyBigDataService {
    @Autowired
    private AnalyBigdataDao analyBigdataDao;

    public String inseartBigData(AnalyBigdataDefine define) throws Exception {
        if (null == define.getKey()) {
            define.setKey(UUIDUtils.getKey());
        }
        if (StringUtils.isEmpty((CharSequence)define.getArcKey())) {
            define.setArcKey("default");
        }
        this.analyBigdataDao.inseartBigdata(define);
        return define.getKey();
    }

    public List<String> inseartBigDatas(AnalyBigdataDefine[] defines) throws Exception {
        this.analyBigdataDao.inseartBigdatas(defines);
        List<String> bigDataKeys = Arrays.stream(defines).map(e -> e.getKey()).collect(Collectors.toList());
        return bigDataKeys;
    }

    public String updateBigData(AnalyBigdataDefine define) throws Exception {
        this.analyBigdataDao.updateBigdata(define);
        return define.getKey();
    }

    public int deleteBigDataByKey(String key) throws Exception {
        return this.analyBigdataDao.deleteBigDataByKey(key);
    }

    public AnalyBigdataDefine getBykey(String key) {
        return this.analyBigdataDao.getBykey(key);
    }

    public AnalyBigdataDefine getArcBigData(String bigDataKey, String arcKey) {
        return this.analyBigdataDao.getArcBigData(bigDataKey, arcKey);
    }

    public int[] batchDeleteBigDataByKeys(String[] keys) throws Exception {
        return this.analyBigdataDao.batchDeleteByKeys(keys);
    }

    public List<AnalyBigdataDefine> list(String key) throws Exception {
        return this.analyBigdataDao.list(key);
    }

    public boolean checkGenCatalogCompleted(String key) {
        return this.analyBigdataDao.checkGenCatalogCompleted(key);
    }

    public Map<String, String> getCatalogMap(String key) {
        return this.analyBigdataDao.getCatalogMap(key);
    }
}

