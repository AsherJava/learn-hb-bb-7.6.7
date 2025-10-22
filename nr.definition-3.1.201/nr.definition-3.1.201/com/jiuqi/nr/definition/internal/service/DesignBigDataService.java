/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignBigDataService {
    @Autowired
    private DesignBigDataTableDao bigDataDao;
    @Autowired
    private DefaultLanguageService defaultLanguageService;

    public void updateBigDataDefine(String key, String code, byte[] data) throws Exception {
        this.updateBigDataDefine(key, code, this.defaultLanguageService.getDefaultLanguage(), data);
    }

    public void updateBigDataDefine(String key, String code, int lang, byte[] data) throws Exception {
        DesignBigDataTable bigData = this.bigDataDao.queryigDataDefine(key, code, lang);
        if (bigData == null) {
            bigData = new DesignBigDataTable();
            bigData.setKey(key);
            bigData.setLang(lang);
            bigData.setCode(code);
            bigData.setData(data);
            bigData.setVesion("1.0");
            this.bigDataDao.insert(bigData);
        } else {
            bigData.setData(data);
            this.bigDataDao.updateData(bigData);
        }
    }

    public void updateBigDataDefine(String key, String code, byte[] data, boolean isUpdateTime) throws Exception {
        DesignBigDataTable bigData = this.bigDataDao.queryigDataDefine(key, code, this.defaultLanguageService.getDefaultLanguage());
        if (bigData == null) {
            return;
        }
        bigData.setData(data);
        this.bigDataDao.updateData(bigData, isUpdateTime);
    }

    public void deleteBigDataDefine(String key, String code) throws Exception {
        this.bigDataDao.deleteBigData(key, code, this.defaultLanguageService.getDefaultLanguage());
    }

    public void deleteBigDataDefine(String key, String code, int lang) throws Exception {
        this.bigDataDao.deleteBigData(key, code, lang);
    }

    public byte[] getBigData(String key, String code) throws Exception {
        return this.queryBigDataDefine(key, code, this.defaultLanguageService.getDefaultLanguage());
    }

    public List<DesignBigDataTable> getBigDatas(String key, String code) throws Exception {
        return this.bigDataDao.queryigDataDefines(key, code);
    }

    public void insertBigDatas(List<DesignBigDataTable> dataTables) throws Exception {
        this.insertBigDatas(dataTables, true);
    }

    public void insertBigDatas(List<DesignBigDataTable> dataTables, boolean autoTime) throws Exception {
        if (dataTables == null) {
            return;
        }
        for (DesignBigDataTable dataTable : dataTables) {
            if (dataTable.getKey() != null) continue;
            dataTable.setKey(UUID.randomUUID().toString());
        }
        this.bigDataDao.insertData(dataTables, autoTime);
    }

    public byte[] queryBigDataDefine(String key, String code, int lang) throws Exception {
        DesignBigDataTable bigDataDefine = this.bigDataDao.queryigDataDefine(key, code, lang);
        if (null != bigDataDefine) {
            return bigDataDefine.getData();
        }
        return null;
    }
}

