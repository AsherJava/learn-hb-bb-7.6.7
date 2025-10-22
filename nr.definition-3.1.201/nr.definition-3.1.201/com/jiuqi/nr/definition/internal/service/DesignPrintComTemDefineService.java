/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.datascheme.i18n.language.LanguageType
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.dao.DesignPrintComTemDefineDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.DesignPrintComTemDefineImpl;
import com.jiuqi.util.OrderGenerator;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignPrintComTemDefineService {
    @Autowired
    private DesignPrintComTemDefineDao printComTemDefineDao;
    @Autowired
    private DesignBigDataTableDao bigDataTableDao;

    public DesignPrintComTemDefine init(String scheme, String title) {
        DesignPrintComTemDefineImpl define = new DesignPrintComTemDefineImpl();
        define.setKey(UUIDUtils.getKey());
        define.setCode(OrderGenerator.newOrder());
        define.setTitle(title);
        define.setPrintSchemeKey(scheme);
        define.setOrder(OrderGenerator.newOrder());
        define.setUpdateTime(new Date());
        return define;
    }

    private DesignBigDataTable getTemplateData(String key) {
        return this.bigDataTableDao.queryigDataDefine(key, "PRINTS_COMTEM_DATA");
    }

    private void deleteTemplateData(String key) throws Exception {
        this.bigDataTableDao.deleteBigData(key, "PRINTS_COMTEM_DATA");
    }

    private void saveTemplateData(DesignPrintComTemDefine define) throws Exception {
        DesignBigDataTable data = this.getTemplateData(define.getKey());
        if (null == data) {
            data = new DesignBigDataTable();
            data.setKey(define.getKey());
            data.setCode("PRINTS_COMTEM_DATA");
            data.setData(define.getTemplateData());
            data.setLang(LanguageType.CHINESE.getValue());
            this.bigDataTableDao.insert(data);
        } else {
            data.setData(define.getTemplateData());
            this.bigDataTableDao.updateData(data);
        }
    }

    private void addTemplateData(DesignPrintComTemDefine define) {
        if (null == define) {
            return;
        }
        DesignBigDataTable data = this.getTemplateData(define.getKey());
        if (null != data) {
            define.setTemplateData(data.getData());
        }
    }

    public DesignPrintComTemDefine getByKey(String key) {
        DesignPrintComTemDefine define = this.printComTemDefineDao.getByKey(key);
        this.addTemplateData(define);
        return define;
    }

    public DesignPrintComTemDefine getBySchemeAndCode(String scheme, String code) {
        DesignPrintComTemDefine define = this.printComTemDefineDao.getBySchemeAndCode(scheme, code);
        this.addTemplateData(define);
        return define;
    }

    public List<DesignPrintComTemDefine> listByScheme(String scheme) {
        List<DesignPrintComTemDefine> list = this.printComTemDefineDao.listByScheme(scheme);
        for (DesignPrintComTemDefine define : list) {
            this.addTemplateData(define);
        }
        return list;
    }

    public List<DesignPrintComTemDefine> listBySchemeWithoutBigData(String scheme) {
        return this.printComTemDefineDao.listByScheme(scheme);
    }

    public void delete(DesignPrintComTemDefine define) throws Exception {
        this.printComTemDefineDao.delete(define.getKey());
        this.deleteTemplateData(define.getKey());
    }

    public void delete(DesignPrintComTemDefine[] defines) throws Exception {
        for (DesignPrintComTemDefine define : defines) {
            this.printComTemDefineDao.delete(define.getKey());
            this.deleteTemplateData(define.getKey());
        }
    }

    public void deleteByKey(String key) throws Exception {
        this.printComTemDefineDao.deleteByKey(key);
        this.deleteTemplateData(key);
    }

    public void deleteByScheme(String scheme) throws Exception {
        List<DesignPrintComTemDefine> list = this.printComTemDefineDao.listByScheme(scheme);
        this.delete(list.toArray(new DesignPrintComTemDefine[0]));
    }

    public void deleteBySchemeAndCode(String scheme, String code) throws Exception {
        DesignPrintComTemDefine define = this.printComTemDefineDao.getBySchemeAndCode(scheme, code);
        if (null == define) {
            return;
        }
        this.delete(define);
    }

    public void insert(DesignPrintComTemDefine define) throws Exception {
        if (null == define.getUpdateTime()) {
            define.setUpdateTime(new Date());
        }
        this.printComTemDefineDao.insert(define);
        this.saveTemplateData(define);
    }

    public void insert(DesignPrintComTemDefine[] defines) throws Exception {
        for (DesignPrintComTemDefine define : defines) {
            if (null != define.getUpdateTime()) continue;
            define.setUpdateTime(new Date());
        }
        this.printComTemDefineDao.insert(defines);
        for (DesignPrintComTemDefine define : defines) {
            this.saveTemplateData(define);
        }
    }

    public void update(DesignPrintComTemDefine define) throws Exception {
        if (null == define.getUpdateTime()) {
            define.setUpdateTime(new Date());
        }
        this.printComTemDefineDao.update(define);
        this.saveTemplateData(define);
    }

    public void update(DesignPrintComTemDefine[] defines) throws Exception {
        for (DesignPrintComTemDefine define : defines) {
            if (null != define.getUpdateTime()) continue;
            define.setUpdateTime(new Date());
        }
        this.printComTemDefineDao.update(defines);
        for (DesignPrintComTemDefine define : defines) {
            this.saveTemplateData(define);
        }
    }
}

