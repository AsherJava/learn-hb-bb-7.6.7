/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.print.DataTransformUtil;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeBigDataTableDao;
import com.jiuqi.nr.definition.internal.dao.RunTimePrintTemplateSchemeDefineDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.RunTimeBigDataTable;
import com.jiuqi.nr.definition.internal.impl.RunTimePrintTemplateSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.print.PrintSchemeAttributeDefineImpl;
import com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunTimePrintTemplateSchemeDefineService {
    @Autowired
    private RunTimePrintTemplateSchemeDefineDao printSchemeDao;
    @Autowired
    private RunTimeBigDataTableDao bigDataDao;
    @Autowired
    private DefaultLanguageService defaultLanguageService;
    public static String BIG_PRINT_GATHER_DATA = "PRINTS_GATTHER_DATA";
    public static String BIG_PRINT_ATTR_DATA = "PRINTS_ATTR_DATA";

    public void setPrintSchemeDao(RunTimePrintTemplateSchemeDefineDao printSchemeDao) {
        this.printSchemeDao = printSchemeDao;
    }

    public void setBigDataDao(RunTimeBigDataTableDao bigDataDao) {
        this.bigDataDao = bigDataDao;
    }

    public PrintTemplateSchemeDefine queryPrintTemplateSchemeDefine(String id) throws Exception {
        return this.queryPrintTemplateSchemeDefine(id, true);
    }

    public PrintTemplateSchemeDefine queryPrintTemplateSchemeDefine(String id, boolean withBinaryData) throws Exception {
        PrintTemplateSchemeDefine define = this.printSchemeDao.getDefineByKey(id);
        if (withBinaryData && define != null) {
            this.setPrintData(define);
        }
        return define;
    }

    public List<PrintTemplateSchemeDefine> getAllPrintSchemeByTask(String taskKey) throws Exception {
        List<PrintTemplateSchemeDefine> defines = this.printSchemeDao.queryPrintSchemeDefineByTask(taskKey);
        this.setPrintData(defines);
        return defines;
    }

    public List<PrintTemplateSchemeDefine> getAllPrintSchemeByFormScheme(String formSchemeKey, boolean withBinaryData) {
        List<PrintTemplateSchemeDefine> defines = this.printSchemeDao.queryPrintSchemeDefineByScheme(formSchemeKey);
        if (withBinaryData) {
            this.setPrintData(defines);
        }
        return defines;
    }

    public List<PrintTemplateSchemeDefine> getAllPrintSchemeByFormScheme(String formSchemeKey) {
        return this.getAllPrintSchemeByFormScheme(formSchemeKey, true);
    }

    public List<PrintTemplateSchemeDefine> queryAllPrintTemplateSchemeDefine() throws Exception {
        return this.queryAllPrintTemplateSchemeDefine(true);
    }

    public List<PrintTemplateSchemeDefine> queryAllPrintTemplateSchemeDefine(boolean withBinaryData) throws Exception {
        List<PrintTemplateSchemeDefine> defines = this.printSchemeDao.list();
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

    public void insertPrintTemplateSchemeDefine(DesignPrintTemplateSchemeDefine define) throws Exception {
        this.printSchemeDao.insert(define);
        this.updatePrintData(define);
    }

    public void insertPrintTemplateSchemeDefines(DesignPrintTemplateSchemeDefine[] defines) throws Exception {
        this.printSchemeDao.insert(defines);
        this.updatePrintData(defines);
    }

    public void updatePrintTemplateSchemeDefine(DesignPrintTemplateSchemeDefine define) throws Exception {
        this.printSchemeDao.update(define);
        this.updatePrintData(define);
    }

    public void updatePrintTemplateSchemeDefines(DesignPrintTemplateSchemeDefine[] defines) throws Exception {
        this.printSchemeDao.update(defines);
        this.updatePrintData(defines);
    }

    public List<PrintTemplateSchemeDefine> queryPrintTemplateSchemeDefines(String[] keys) throws Exception {
        ArrayList<PrintTemplateSchemeDefine> list = new ArrayList<PrintTemplateSchemeDefine>();
        for (String key : keys) {
            PrintTemplateSchemeDefine define = this.printSchemeDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        this.setPrintData(list);
        return list;
    }

    public void deleteAllFromDefines() throws Exception {
        this.printSchemeDao.deleteAll();
    }

    public void updatePrintData(DesignPrintTemplateSchemeDefine define) throws Exception {
        this.updatePrintGatherData(define.getKey(), define.getGatherCoverData());
        this.updatePrintAttrData(define.getKey(), define.getCommonAttribute());
    }

    public void updatePrintData(List<DesignPrintTemplateSchemeDefine> defines) throws Exception {
        for (DesignPrintTemplateSchemeDefine print : defines) {
            this.updatePrintData(print);
        }
    }

    public void updatePrintData(DesignPrintTemplateSchemeDefine[] defines) throws Exception {
        for (DesignPrintTemplateSchemeDefine print : defines) {
            this.updatePrintData(print);
        }
    }

    public void updatePrintAttrData(String printKey, String code, byte[] data) throws Exception {
        this.updateBigDataDefine(printKey, code, this.defaultLanguageService.getDefaultLanguage(), data);
    }

    public void updatePrintGatherData(String printKey, byte[] data) throws Exception {
        this.updateBigDataDefine(printKey, BIG_PRINT_GATHER_DATA, this.defaultLanguageService.getDefaultLanguage(), data);
    }

    public void updatePrintGatherData(String printKey, byte[] data, int lang) throws Exception {
        this.updateBigDataDefine(printKey, BIG_PRINT_GATHER_DATA, lang, data);
    }

    public void updatePrintAttrData(String printKey, byte[] data) throws Exception {
        this.updateBigDataDefine(printKey, BIG_PRINT_ATTR_DATA, this.defaultLanguageService.getDefaultLanguage(), data);
    }

    public byte[] getPrintGatherData(String printKey) {
        RunTimeBigDataTable bigData = this.queryBigDataDefine(printKey, BIG_PRINT_GATHER_DATA, this.defaultLanguageService.getDefaultLanguage());
        if (null != bigData) {
            return bigData.getData();
        }
        return null;
    }

    public byte[] getPrintAttrData(String printKey) {
        RunTimeBigDataTable bigData = this.queryBigDataDefine(printKey, BIG_PRINT_ATTR_DATA, this.defaultLanguageService.getDefaultLanguage());
        if (null != bigData) {
            return bigData.getData();
        }
        return null;
    }

    public void setPrintData(PrintTemplateSchemeDefine define) {
        RunTimePrintTemplateSchemeDefineImpl printTemplateSchemeDefineImpl = (RunTimePrintTemplateSchemeDefineImpl)define;
        printTemplateSchemeDefineImpl.setGatherCoverData(this.getPrintGatherData(define.getKey()));
        printTemplateSchemeDefineImpl.setCommonAttribute(this.getPrintAttrData(define.getKey()));
    }

    public void setPrintData(List<PrintTemplateSchemeDefine> defines) {
        for (PrintTemplateSchemeDefine define : defines) {
            this.setPrintData(define);
        }
    }

    public void deletePrintData(String printKey) throws Exception {
        this.deleteBigDataDefine(printKey, BIG_PRINT_GATHER_DATA, this.defaultLanguageService.getDefaultLanguage());
        this.deleteBigDataDefine(printKey, BIG_PRINT_ATTR_DATA, this.defaultLanguageService.getDefaultLanguage());
    }

    public void deletePrintData(String printKey, int lang) throws Exception {
        this.deleteBigDataDefine(printKey, BIG_PRINT_GATHER_DATA, lang);
        this.deleteBigDataDefine(printKey, BIG_PRINT_ATTR_DATA, lang);
    }

    public RunTimeBigDataTable queryBigDataDefine(String Key2, String code, int Lang) {
        return this.bigDataDao.queryigDataDefine(Key2, code, Lang);
    }

    public RunTimeBigDataTable queryBigDataDefine(String Key2, String code) throws Exception {
        return this.bigDataDao.queryigDataDefine(Key2, code);
    }

    public void updateBigDataDefine(DesignBigDataTable bigData, byte[] data) throws Exception {
        bigData.setData(data);
        RunTimeBigDataTable oldData = this.bigDataDao.queryigDataDefine(bigData.getKey(), bigData.getCode(), bigData.getLang());
        if (oldData == null) {
            this.bigDataDao.insert(bigData);
        } else {
            oldData.setData(data);
            this.bigDataDao.updateData(oldData);
        }
    }

    public void updateBigDataDefine(String key, String code, int lang, byte[] data) throws Exception {
        RunTimeBigDataTable bigData = this.bigDataDao.queryigDataDefine(key, code, lang);
        if (bigData == null) {
            bigData = new RunTimeBigDataTable();
            bigData.setKey(key);
            bigData.setLang(lang);
            bigData.setCode(code);
            bigData.setData(data);
            bigData.setVersion("1.0");
            this.bigDataDao.insert(bigData);
        } else {
            bigData.setData(data);
            this.bigDataDao.updateData(bigData);
        }
    }

    public void updateBigDataDefine(String key, String code, byte[] data) throws Exception {
        RunTimeBigDataTable bigData = this.bigDataDao.queryigDataDefine(key, code);
        if (bigData == null) {
            bigData = new RunTimeBigDataTable();
            bigData.setKey(key);
            bigData.setCode(code);
            bigData.setData(data);
            bigData.setLang(this.defaultLanguageService.getDefaultLanguage());
            bigData.setVersion("1.0");
            this.bigDataDao.insert(bigData);
        } else {
            bigData.setData(data);
            this.bigDataDao.updateData(bigData);
        }
    }

    public void deleteBigDataDefine(String key, String code, int lang) throws Exception {
        this.bigDataDao.deleteBigData(key, code, lang);
    }

    public void DeleteBigDataDefine(String key, String code) throws Exception {
        this.bigDataDao.deleteBigData(key, code, this.defaultLanguageService.getDefaultLanguage());
    }

    public PrintSchemeAttributeDefine getPrintSchemeAttribute(DesignPrintTemplateSchemeDefine printScheme) throws Exception {
        PrintSchemeAttributeDefine define = null;
        define = null != printScheme && null != printScheme.getCommonAttribute() && new byte[1] != printScheme.getCommonAttribute() ? DataTransformUtil.deserialize(printScheme.getCommonAttribute(), PrintSchemeAttributeDefine.class) : new PrintSchemeAttributeDefineImpl();
        return define;
    }

    public void setPrintSchemeAttribute(DesignPrintTemplateSchemeDefine printScheme, PrintSchemeAttributeDefine define) throws Exception {
        if (null != define) {
            printScheme.setCommonAttribute(DataTransformUtil.serializeToByteArray(define));
        } else {
            printScheme.setCommonAttribute(null);
        }
    }
}

