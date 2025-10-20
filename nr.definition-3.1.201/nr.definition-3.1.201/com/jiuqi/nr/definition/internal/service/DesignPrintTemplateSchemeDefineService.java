/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.print.DataTransformUtil;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.dao.DesignPrintTemplateSchemeDefineDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.print.PrintSchemeAttributeDefineImpl;
import com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignPrintTemplateSchemeDefineService {
    @Autowired
    private DesignPrintTemplateSchemeDefineDao printSchemeDao;
    @Autowired
    private DesignBigDataTableDao bigDataDao;
    @Autowired
    private DefaultLanguageService defaultLanguageService;
    public static String BIG_PRINT_GATHER_DATA = "PRINTS_GATTHER_DATA";
    public static String BIG_PRINT_ATTR_DATA = "PRINTS_ATTR_DATA";

    public String insertPrintTemplateSchemeDefine(DesignPrintTemplateSchemeDefine define) throws Exception {
        if (define.getKey() == null) {
            define.setKey(UUIDUtils.getKey());
        }
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        this.printSchemeDao.insert(define);
        this.updatePrintData(define);
        return define.getKey();
    }

    public void updatePrintTemplateSchemeDefine(DesignPrintTemplateSchemeDefine define) throws Exception {
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        this.printSchemeDao.update(define);
        this.updatePrintData(define);
    }

    public DesignPrintTemplateSchemeDefine queryPrintTemplateSchemeDefine(String id) throws Exception {
        return this.queryPrintTemplateSchemeDefine(id, true);
    }

    public DesignPrintTemplateSchemeDefine queryPrintTemplateSchemeDefine(String id, boolean withBinaryData) {
        DesignPrintTemplateSchemeDefine define = this.printSchemeDao.getDefineByKey(id);
        if (withBinaryData && define != null) {
            this.setPrintData(define);
        }
        return define;
    }

    public List<DesignPrintTemplateSchemeDefine> getAllPrintSchemeByTask(String taskKey) throws Exception {
        List<DesignPrintTemplateSchemeDefine> defines = this.printSchemeDao.queryPrintSchemeDefineByTask(taskKey);
        this.setPrintData(defines);
        return defines;
    }

    public List<DesignPrintTemplateSchemeDefine> getAllPrintSchemeByFormScheme(String formSchemeKey) throws Exception {
        List<DesignPrintTemplateSchemeDefine> defines = this.printSchemeDao.queryPrintSchemeDefineByScheme(formSchemeKey);
        this.setPrintData(defines);
        return defines;
    }

    public List<DesignPrintTemplateSchemeDefine> queryAllPrintTemplateSchemeDefine() throws Exception {
        return this.queryAllPrintTemplateSchemeDefine(true);
    }

    public List<DesignPrintTemplateSchemeDefine> queryAllPrintTemplateSchemeDefine(boolean withBinaryData) throws Exception {
        List<DesignPrintTemplateSchemeDefine> defines = this.printSchemeDao.list();
        if (withBinaryData) {
            this.setPrintData(defines);
        }
        return defines;
    }

    public void delete(String[] keys) throws Exception {
        this.printSchemeDao.delete(keys);
        for (String id : keys) {
            this.deletePrintData(id);
        }
    }

    public void delete(String key) throws Exception {
        this.printSchemeDao.delete(key);
        this.deletePrintData(key);
    }

    public void exchangePrintTemplateSchemeOrder(String orinPrintTemplateSchemeKey, String targetPrintTemplateSchemeKey) throws Exception {
        DesignPrintTemplateSchemeDefine orinScheme = this.printSchemeDao.getDefineByKey(orinPrintTemplateSchemeKey);
        DesignPrintTemplateSchemeDefine targetcheme = this.printSchemeDao.getDefineByKey(targetPrintTemplateSchemeKey);
        if (null != orinScheme && null != targetcheme) {
            String oldOrder = orinScheme.getOrder();
            orinScheme.setOrder(targetcheme.getOrder());
            targetcheme.setOrder(oldOrder);
            this.printSchemeDao.update(orinScheme);
            this.printSchemeDao.update(targetcheme);
        }
    }

    public void updatePrintData(DesignPrintTemplateSchemeDefine define) throws Exception {
        this.updatePrintGatherData(define.getKey(), define.getGatherCoverData());
        this.updatePrintAttrData(define.getKey(), define.getCommonAttribute());
    }

    public void updatePrintGatherData(String printKey, byte[] data) throws Exception {
        this.updateBigDataDefine(printKey, BIG_PRINT_GATHER_DATA, this.defaultLanguageService.getDefaultLanguage(), data);
    }

    public void updatePrintAttrData(String printKey, byte[] data) throws Exception {
        this.updateBigDataDefine(printKey, BIG_PRINT_ATTR_DATA, this.defaultLanguageService.getDefaultLanguage(), data);
    }

    public byte[] getPrintGatherData(String printKey) {
        DesignBigDataTable bigData = this.bigDataDao.queryigDataDefine(printKey, BIG_PRINT_GATHER_DATA);
        if (null != bigData) {
            return bigData.getData();
        }
        return null;
    }

    public byte[] getPrintAttrData(String printKey) {
        DesignBigDataTable bigData = this.bigDataDao.queryigDataDefine(printKey, BIG_PRINT_ATTR_DATA);
        if (null != bigData) {
            return bigData.getData();
        }
        return null;
    }

    public void setPrintData(DesignPrintTemplateSchemeDefine define) {
        define.setGatherCoverData(this.getPrintGatherData(define.getKey()));
        define.setCommonAttribute(this.getPrintAttrData(define.getKey()));
    }

    public void setPrintData(List<DesignPrintTemplateSchemeDefine> defines) throws Exception {
        for (DesignPrintTemplateSchemeDefine define : defines) {
            this.setPrintData(define);
        }
    }

    public void deletePrintData(String printKey) throws Exception {
        this.deleteBigDataDefine(printKey, BIG_PRINT_GATHER_DATA, this.defaultLanguageService.getDefaultLanguage());
        this.deleteBigDataDefine(printKey, BIG_PRINT_ATTR_DATA, this.defaultLanguageService.getDefaultLanguage());
    }

    public void updateBigDataDefine(DesignBigDataTable bigData, byte[] data) throws Exception {
        bigData.setData(data);
        DesignBigDataTable oldData = this.bigDataDao.queryigDataDefine(bigData.getKey(), bigData.getCode(), bigData.getLang());
        if (oldData == null) {
            this.bigDataDao.insert(bigData);
        } else {
            oldData.setData(data);
            this.bigDataDao.updateData(oldData);
        }
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

    public void updateBigDataDefine(String key, String code, byte[] data) throws Exception {
        DesignBigDataTable bigData = this.bigDataDao.queryigDataDefine(key, code);
        if (bigData == null) {
            bigData = new DesignBigDataTable();
            bigData.setKey(key);
            bigData.setCode(code);
            bigData.setData(data);
            bigData.setLang(this.defaultLanguageService.getDefaultLanguage());
            bigData.setVesion("1.0");
            this.bigDataDao.insert(bigData);
        } else {
            bigData.setData(data);
            this.bigDataDao.updateData(bigData);
        }
    }

    public void deleteBigDataDefine(String key, String code, int lang) throws Exception {
        this.bigDataDao.deleteBigData(key, code, lang);
    }

    public PrintSchemeAttributeDefine getPrintSchemeAttribute(DesignPrintTemplateSchemeDefine printScheme) {
        PrintSchemeAttributeDefine define = null;
        define = null != printScheme && null != printScheme.getCommonAttribute() && new byte[1] != printScheme.getCommonAttribute() ? DataTransformUtil.deserialize(printScheme.getCommonAttribute(), PrintSchemeAttributeDefine.class) : new PrintSchemeAttributeDefineImpl();
        return define;
    }

    public void setPrintSchemeAttribute(DesignPrintTemplateSchemeDefine printScheme, PrintSchemeAttributeDefine define) {
        if (null != define) {
            printScheme.setCommonAttribute(DataTransformUtil.serializeToByteArray(define));
        } else {
            printScheme.setCommonAttribute(null);
        }
    }

    public List<DesignPrintTemplateSchemeDefine> getAllPrintSchemeByFormScheme(String formSchemeKey, boolean withBinaryData) throws Exception {
        List<DesignPrintTemplateSchemeDefine> defines = this.printSchemeDao.queryPrintSchemeDefineByScheme(formSchemeKey);
        if (withBinaryData) {
            this.setPrintData(defines);
        }
        return defines;
    }

    public List<DesignPrintTemplateSchemeDefine> listAllPrintTemplateScheme() throws Exception {
        return this.printSchemeDao.list();
    }

    public DesignPrintTemplateSchemeDefine getPrintTemplateScheme(String id) throws Exception {
        return this.printSchemeDao.getDefineByKey(id);
    }
}

