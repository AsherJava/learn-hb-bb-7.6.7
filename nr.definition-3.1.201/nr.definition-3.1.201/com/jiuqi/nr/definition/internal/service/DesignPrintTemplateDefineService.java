/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.print.DataTransformUtil;
import com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.dao.DesignPrintTemplateDefineDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.print.PrintTemplateAttributeDefineImpl;
import com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DesignPrintTemplateDefineService {
    @Autowired
    private DesignPrintTemplateDefineDao printDao;
    @Autowired
    private DesignBigDataTableDao bigDataDao;
    @Autowired
    private DefaultLanguageService defaultLanguageService;
    public static String BIG_PRINT_TEMPLATE_DATA = "PRINT_TEMPLATE_DATA";
    public static String BIG_PRINT_LABLE_DATA = "PRINT_LABLE_DATA";

    public String insertPrintTemplateDefine(DesignPrintTemplateDefine define) throws Exception {
        if (define.getKey() == null) {
            define.setKey(UUIDUtils.getKey());
        }
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        if (!define.isAutoRefreshForm() && null == define.getFormUpdateTime()) {
            define.setFormUpdateTime(new Date());
        }
        this.printDao.insert(define);
        this.updatePrintData(define);
        return define.getKey();
    }

    public void updatePrintTemplateDefine(DesignPrintTemplateDefine define) throws Exception {
        List<DesignPrintTemplateDefine> printTemplateDefines = this.printDao.queryPrintDefineBySchemeAndForm(define.getPrintSchemeKey(), define.getFormKey());
        List needDeleteDefines = printTemplateDefines.stream().filter(printTemplateDefine -> !printTemplateDefine.getKey().equals(define.getKey())).collect(Collectors.toList());
        List<String> needDeleteDefineKeys = needDeleteDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(needDeleteDefineKeys)) {
            this.printDao.delete(needDeleteDefineKeys.toArray(new String[needDeleteDefineKeys.size()]));
        }
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        if (!define.isAutoRefreshForm() && null == define.getFormUpdateTime()) {
            define.setFormUpdateTime(new Date());
        }
        this.printDao.update(define);
        this.updatePrintData(define);
    }

    public DesignPrintTemplateDefine queryPrintTemplateDefine(String id) throws Exception {
        return this.queryPrintTemplateDefine(id, true);
    }

    public DesignPrintTemplateDefine queryPrintTemplateDefine(String id, boolean withBinaryData) throws Exception {
        DesignPrintTemplateDefine define = this.printDao.getDefineByKey(id);
        if (withBinaryData && define != null) {
            this.setPrintData(define);
        }
        return define;
    }

    public List<DesignPrintTemplateDefine> queryAllPrintTemplateDefine() throws Exception {
        return this.queryAllPrintTemplateDefine(true);
    }

    public List<DesignPrintTemplateDefine> queryAllPrintTemplateDefineByScheme(String printSchemeKey) throws Exception {
        List<DesignPrintTemplateDefine> defines = this.printDao.queryPrintDefineByScheme(printSchemeKey);
        this.setPrintData(defines);
        return defines;
    }

    public DesignPrintTemplateDefine queryPrintTemplateDefineBySchemeAndForm(String printSchemeKey, String formKey) throws Exception {
        DesignPrintTemplateDefine define = null;
        List<DesignPrintTemplateDefine> defines = this.printDao.queryPrintDefineBySchemeAndForm(printSchemeKey, formKey);
        if (null != defines && defines.size() > 0) {
            define = defines.get(0);
            this.setPrintData(define);
        }
        return define;
    }

    public DesignPrintTemplateDefine queryPrintTemplateDefineBySchemeAndForm(String printSchemeKey, String formKey, boolean withBinaryData) throws Exception {
        if (withBinaryData) {
            return this.queryPrintTemplateDefineBySchemeAndForm(printSchemeKey, formKey);
        }
        DesignPrintTemplateDefine define = null;
        List<DesignPrintTemplateDefine> defines = this.printDao.queryPrintDefineBySchemeAndForm(printSchemeKey, formKey);
        if (null != defines && defines.size() > 0) {
            define = defines.get(0);
        }
        return define;
    }

    public void deleteByForm(String ... formKeys) throws Exception {
        if (null == formKeys || 0 == formKeys.length) {
            return;
        }
        ArrayList templateKeys = new ArrayList();
        for (String formKey : formKeys) {
            List<DesignPrintTemplateDefine> printDefines = this.printDao.queryPrintDefineByForm(formKey);
            if (null == printDefines || printDefines.isEmpty()) continue;
            printDefines.forEach(p -> templateKeys.add(p.getKey()));
        }
        this.delete(templateKeys.toArray(new String[0]));
    }

    public List<DesignPrintTemplateDefine> queryAllPrintTemplateDefine(boolean withBinaryData) throws Exception {
        List<DesignPrintTemplateDefine> defines = this.printDao.list();
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

    public void deleteBySchemeAndFormKeys(String printSchemeKey, String ... formKeys) throws Exception {
        if (null == formKeys || 0 == formKeys.length) {
            List<DesignPrintTemplateDefine> defines = this.queryAllPrintTemplateDefineByScheme(printSchemeKey, false);
            for (DesignPrintTemplateDefine define : defines) {
                this.delete(define.getKey());
            }
        } else {
            for (String formKey : formKeys) {
                this.printDao.deleteBySchemeAndForm(printSchemeKey, formKey);
            }
        }
    }

    public List<String> insertPrintTemplateDefines(DesignPrintTemplateDefine[] defines) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        for (DesignPrintTemplateDefine define : defines) {
            String key = UUIDUtils.getKey();
            define.setKey(key);
            if (null == define.getUpdateTime()) {
                define.setUpdateTime(new Date());
            }
            if (!define.isAutoRefreshForm() && null == define.getFormUpdateTime()) {
                define.setFormUpdateTime(new Date());
            }
            result.add(key);
        }
        this.printDao.insert(defines);
        this.updatePrintData(defines);
        return result;
    }

    public void updatePrintTemplateDefines(DesignPrintTemplateDefine[] defines) throws Exception {
        for (DesignPrintTemplateDefine define : defines) {
            if (null == define.getUpdateTime()) {
                define.setUpdateTime(new Date());
            }
            if (define.isAutoRefreshForm() || null != define.getFormUpdateTime()) continue;
            define.setFormUpdateTime(new Date());
        }
        this.printDao.update(defines);
        this.updatePrintData(defines);
    }

    public List<DesignPrintTemplateDefine> queryPrintTemplateDefines(String[] keys) throws Exception {
        ArrayList<DesignPrintTemplateDefine> list = new ArrayList<DesignPrintTemplateDefine>();
        for (String key : keys) {
            DesignPrintTemplateDefine define = this.printDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        this.setPrintData(list);
        return list;
    }

    private void updatePrintData(DesignPrintTemplateDefine define) throws Exception {
        this.updatePrintTemplateData(define.getKey(), define.getTemplateData());
        this.updatePrintLableData(define.getKey(), define.getLabelData());
    }

    private void updatePrintData(List<DesignPrintTemplateDefine> defines) throws Exception {
        for (DesignPrintTemplateDefine print : defines) {
            this.updatePrintData(print);
        }
    }

    private void updatePrintData(DesignPrintTemplateDefine[] defines) throws Exception {
        for (DesignPrintTemplateDefine print : defines) {
            this.updatePrintData(print);
        }
    }

    private void updatePrintTemplateData(String printKey, byte[] data) throws Exception {
        this.updateBigDataDefine(printKey, BIG_PRINT_TEMPLATE_DATA, data);
    }

    private void updatePrintLableData(String printKey, byte[] data) throws Exception {
        this.updateBigDataDefine(printKey, BIG_PRINT_LABLE_DATA, data);
    }

    public byte[] getPrintTemplateData(String printKey) {
        DesignBigDataTable bigData = this.bigDataDao.queryigDataDefine(printKey, BIG_PRINT_TEMPLATE_DATA);
        if (null != bigData) {
            return bigData.getData();
        }
        return null;
    }

    public byte[] getPrintLableData(String printKey) {
        DesignBigDataTable bigData = this.bigDataDao.queryigDataDefine(printKey, BIG_PRINT_LABLE_DATA);
        if (null != bigData) {
            return bigData.getData();
        }
        return null;
    }

    private void setPrintData(DesignPrintTemplateDefine define) {
        define.setTemplateData(this.getPrintTemplateData(define.getKey()));
        define.setLabelData(this.getPrintLableData(define.getKey()));
    }

    private void setPrintData(List<DesignPrintTemplateDefine> defines) {
        for (DesignPrintTemplateDefine define : defines) {
            this.setPrintData(define);
        }
    }

    private void deletePrintData(String printKey) throws Exception {
        this.bigDataDao.deleteBigData(printKey, BIG_PRINT_TEMPLATE_DATA);
        this.bigDataDao.deleteBigData(printKey, BIG_PRINT_LABLE_DATA);
    }

    private void updateBigDataDefine(DesignBigDataTable bigData, byte[] data) throws Exception {
        bigData.setData(data);
        DesignBigDataTable oldData = this.bigDataDao.queryigDataDefine(bigData.getKey(), bigData.getCode(), bigData.getLang());
        if (oldData == null) {
            this.bigDataDao.insert(bigData);
        } else {
            oldData.setData(data);
            this.bigDataDao.updateData(oldData);
        }
    }

    private void updateBigDataDefine(String key, String code, int lang, byte[] data) throws Exception {
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

    private void updateBigDataDefine(String key, String code, byte[] data) throws Exception {
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

    public PrintTemplateAttributeDefine getPrintTemplateAttribute(DesignPrintTemplateDefine printTemplate) {
        PrintTemplateAttributeDefine define = null;
        define = null != printTemplate && null != printTemplate.getLabelData() && new byte[1] != printTemplate.getLabelData() ? DataTransformUtil.deserialize(printTemplate.getLabelData(), PrintTemplateAttributeDefine.class) : new PrintTemplateAttributeDefineImpl();
        return define;
    }

    public void setPrintTemplateAttribute(DesignPrintTemplateDefine printTemplate, PrintTemplateAttributeDefine define) {
        if (null != define) {
            printTemplate.setLabelData(DataTransformUtil.serializeToByteArray(define));
        } else {
            printTemplate.setLabelData(null);
        }
    }

    public List<DesignPrintTemplateDefine> queryAllPrintTemplate(String printSchemeKey) {
        List<DesignPrintTemplateDefine> listByScheme = this.printDao.listByScheme(printSchemeKey);
        if (null != listByScheme) {
            this.setPrintData(listByScheme);
        }
        return listByScheme;
    }

    public int insertTemplate(DesignPrintTemplateDefine template) throws Exception {
        if (template.getUpdateTime() == null) {
            template.setUpdateTime(new Date());
        }
        if (!template.isAutoRefreshForm() && null == template.getFormUpdateTime()) {
            template.setFormUpdateTime(new Date());
        }
        int insert = this.printDao.insert(template);
        this.updatePrintData(template);
        return insert;
    }

    public int[] insertTemplates(DesignPrintTemplateDefine[] templates) throws Exception {
        for (DesignPrintTemplateDefine template : templates) {
            if (template.getUpdateTime() == null) {
                template.setUpdateTime(new Date());
            }
            if (template.isAutoRefreshForm() || null != template.getFormUpdateTime()) continue;
            template.setFormUpdateTime(new Date());
        }
        int[] insert = this.printDao.insert(templates);
        this.updatePrintData(templates);
        return insert;
    }

    public int updateTemplate(DesignPrintTemplateDefine template) throws Exception {
        if (template.getUpdateTime() == null) {
            template.setUpdateTime(new Date());
        }
        if (!template.isAutoRefreshForm() && null == template.getFormUpdateTime()) {
            template.setFormUpdateTime(new Date());
        }
        int insert = this.printDao.update(template);
        this.updatePrintData(template);
        return insert;
    }

    public int[] updateTemplates(DesignPrintTemplateDefine[] templates) throws Exception {
        for (DesignPrintTemplateDefine template : templates) {
            if (template.getUpdateTime() == null) {
                template.setUpdateTime(new Date());
            }
            if (template.isAutoRefreshForm() || null != template.getFormUpdateTime()) continue;
            template.setFormUpdateTime(new Date());
        }
        int[] insert = this.printDao.update(templates);
        this.updatePrintData(templates);
        return insert;
    }

    public List<DesignPrintTemplateDefine> queryAllPrintTemplateDefineByScheme(String printSchemeKey, boolean withBinaryData) throws Exception {
        List<DesignPrintTemplateDefine> defines = this.printDao.queryPrintDefineByScheme(printSchemeKey);
        if (withBinaryData) {
            this.setPrintData(defines);
        }
        return defines;
    }

    public DesignPrintTemplateDefine getPrintTemplateBySchemeAndForm(String printSchemeKey, String formKey) {
        DesignPrintTemplateDefine define = null;
        List<DesignPrintTemplateDefine> defines = this.printDao.queryPrintDefineBySchemeAndForm(printSchemeKey, formKey);
        if (null != defines && !defines.isEmpty()) {
            define = defines.get(0);
        }
        return define;
    }

    public List<DesignPrintTemplateDefine> listPrintTemplateByScheme(String printSchemeKey) {
        return this.printDao.queryPrintDefineByScheme(printSchemeKey);
    }

    public List<DesignPrintTemplateDefine> listPrintTemplateByForm(String formKey) {
        return this.printDao.queryPrintDefineByForm(formKey);
    }
}

