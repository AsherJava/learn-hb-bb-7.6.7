/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.facade.print.DataTransformUtil;
import com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeBigDataTableDao;
import com.jiuqi.nr.definition.internal.dao.RunTimePrintTemplateDefineDao;
import com.jiuqi.nr.definition.internal.impl.RunTimeBigDataTable;
import com.jiuqi.nr.definition.internal.impl.RunTimePrintTemplateDefineImpl;
import com.jiuqi.nr.definition.internal.impl.print.PrintTemplateAttributeDefineImpl;
import com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunTimePrintTemplateDefineService {
    @Autowired
    private RunTimePrintTemplateDefineDao printDao;
    @Autowired
    private RunTimeBigDataTableDao bigDataDao;
    @Autowired
    private DefaultLanguageService defaultLanguageService;
    public static String BIG_PRINT_TEMPLATE_DATA = "PRINT_TEMPLATE_DATA";
    public static String BIG_PRINT_LABLE_DATA = "PRINT_LABLE_DATA";

    public PrintTemplateDefine queryPrintTemplateDefine(String id) throws Exception {
        return this.queryPrintTemplateDefine(id, true);
    }

    public PrintTemplateDefine queryPrintTemplateDefine(String id, boolean withBinaryData) throws Exception {
        PrintTemplateDefine define = this.printDao.getDefineByKey(id);
        if (withBinaryData && define != null) {
            this.setPrintData(define);
        }
        return define;
    }

    public List<PrintTemplateDefine> queryAllPrintTemplateDefine() throws Exception {
        return this.queryAllPrintTemplateDefine(true);
    }

    public List<PrintTemplateDefine> queryAllPrintTemplateDefineByScheme(String printSchemeKey) throws Exception {
        List<PrintTemplateDefine> defines = this.printDao.queryPrintDefineByScheme(printSchemeKey);
        this.setPrintData(defines);
        return defines;
    }

    public PrintTemplateDefine queryPrintTemplateDefineBySchemeAndForm(String printSchemeKey, String formKey) throws Exception {
        PrintTemplateDefine define = null;
        List<PrintTemplateDefine> defines = this.printDao.queryPrintDefineBySchemeAndForm(printSchemeKey, formKey);
        if (null != defines && defines.size() > 0) {
            define = defines.get(0);
            this.setPrintData(define);
        }
        return define;
    }

    public List<PrintTemplateDefine> queryAllPrintTemplateDefine(boolean withBinaryData) throws Exception {
        List<PrintTemplateDefine> defines = this.printDao.list();
        if (withBinaryData) {
            this.setPrintData(defines);
        }
        return defines;
    }

    public void delete(String[] keys) throws Exception {
        this.printDao.delete(keys);
        for (String id : keys) {
            this.deletePrintData(id);
        }
    }

    public void delete(String key) throws Exception {
        this.printDao.delete(key);
        this.deletePrintData(key);
    }

    public void insertPrintTemplateDefine(PrintTemplateDefine define) throws Exception {
        this.printDao.insert(define);
        this.updatePrintData(define);
    }

    public void insertPrintTemplateDefines(PrintTemplateDefine[] defines) throws Exception {
        this.printDao.insert(defines);
        this.updatePrintData(defines);
    }

    public void updatePrintTemplateDefine(PrintTemplateDefine define) throws Exception {
        this.printDao.update(define);
        this.updatePrintData(define);
    }

    public void updatePrintTemplateDefines(PrintTemplateDefine[] defines) throws Exception {
        this.printDao.update(defines);
        this.updatePrintData(defines);
    }

    public List<PrintTemplateDefine> queryPrintTemplateDefines(String[] keys) throws Exception {
        ArrayList<PrintTemplateDefine> list = new ArrayList<PrintTemplateDefine>();
        for (String key : keys) {
            PrintTemplateDefine define = this.printDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        this.setPrintData(list);
        return list;
    }

    public void deleteAllFromDefines() throws Exception {
        this.printDao.deleteAll();
    }

    public void updatePrintData(PrintTemplateDefine define) throws Exception {
        this.updatePrintTemplateData(define.getKey(), define.getTemplateData());
        this.updatePrintLableData(define.getKey(), define.getLabelData());
    }

    public void updatePrintData(List<PrintTemplateDefine> defines) throws Exception {
        for (PrintTemplateDefine print : defines) {
            this.updatePrintData(print);
        }
    }

    public void updatePrintData(PrintTemplateDefine[] defines) throws Exception {
        for (PrintTemplateDefine print : defines) {
            this.updatePrintData(print);
        }
    }

    public void updatePrintAttrData(String printKey, String code, byte[] data) throws Exception {
        this.updateBigDataDefine(printKey, code, this.defaultLanguageService.getDefaultLanguage(), data);
    }

    public void updatePrintTemplateData(String printKey, byte[] data) throws Exception {
        this.updateBigDataDefine(printKey, BIG_PRINT_TEMPLATE_DATA, this.defaultLanguageService.getDefaultLanguage(), data);
    }

    public void updatePrintTemplateData(String printKey, byte[] data, int lang) throws Exception {
        this.updateBigDataDefine(printKey, BIG_PRINT_TEMPLATE_DATA, lang, data);
    }

    public void updatePrintLableData(String printKey, byte[] data) throws Exception {
        this.updateBigDataDefine(printKey, BIG_PRINT_LABLE_DATA, this.defaultLanguageService.getDefaultLanguage(), data);
    }

    public byte[] getPrintTemplateData(String printKey) {
        RunTimeBigDataTable bigData = this.queryBigDataDefine(printKey, BIG_PRINT_TEMPLATE_DATA, this.defaultLanguageService.getDefaultLanguage());
        if (null != bigData) {
            return bigData.getData();
        }
        return null;
    }

    public byte[] getPrintLableData(String printKey) {
        RunTimeBigDataTable bigData = this.queryBigDataDefine(printKey, BIG_PRINT_LABLE_DATA, this.defaultLanguageService.getDefaultLanguage());
        if (null != bigData) {
            return bigData.getData();
        }
        return null;
    }

    public void setPrintData(PrintTemplateDefine define) throws Exception {
        RunTimePrintTemplateDefineImpl printTemplateDefineImpl = (RunTimePrintTemplateDefineImpl)define;
        printTemplateDefineImpl.setTemplateData(this.getPrintTemplateData(define.getKey()));
        printTemplateDefineImpl.setLabelData(this.getPrintLableData(define.getKey()));
    }

    public void setPrintData(List<PrintTemplateDefine> defines) throws Exception {
        for (PrintTemplateDefine define : defines) {
            this.setPrintData(define);
        }
    }

    public void deletePrintData(String printKey) throws Exception {
        this.deleteBigDataDefine(printKey, BIG_PRINT_TEMPLATE_DATA, this.defaultLanguageService.getDefaultLanguage());
        this.deleteBigDataDefine(printKey, BIG_PRINT_LABLE_DATA, this.defaultLanguageService.getDefaultLanguage());
    }

    public void deletePrintData(String printKey, int lang) throws Exception {
        this.deleteBigDataDefine(printKey, BIG_PRINT_TEMPLATE_DATA, lang);
        this.deleteBigDataDefine(printKey, BIG_PRINT_LABLE_DATA, lang);
    }

    public RunTimeBigDataTable queryBigDataDefine(String Key2, String code, int Lang) {
        return this.bigDataDao.queryigDataDefine(Key2, code, Lang);
    }

    public RunTimeBigDataTable queryBigDataDefine(String Key2, String code) throws Exception {
        return this.bigDataDao.queryigDataDefine(Key2, code);
    }

    public void updateBigDataDefine(RunTimeBigDataTable bigData, byte[] data) throws Exception {
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

    public PrintTemplateAttributeDefine getPrintTemplateAttribute(DesignPrintTemplateDefine printTemplate) throws Exception {
        PrintTemplateAttributeDefine define = null;
        define = null != printTemplate && null != printTemplate.getLabelData() && new byte[1] != printTemplate.getLabelData() ? DataTransformUtil.deserialize(printTemplate.getLabelData(), PrintTemplateAttributeDefine.class) : new PrintTemplateAttributeDefineImpl();
        return define;
    }

    public void setPrintTemplateAttribute(DesignPrintTemplateDefine printTemplate, PrintTemplateAttributeDefine define) throws Exception {
        if (null != define) {
            printTemplate.setLabelData(DataTransformUtil.serializeToByteArray(define));
        } else {
            printTemplate.setLabelData(null);
        }
    }
}

