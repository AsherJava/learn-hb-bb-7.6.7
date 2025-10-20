/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeBigDataTableDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormGroupDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormGroupLinkDao;
import com.jiuqi.nr.definition.internal.impl.RunTimeBigDataTable;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormGroupLink;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunTimeFormDefineService {
    @Autowired
    private RunTimeFormDefineDao formDao;
    @Autowired
    private RunTimeFormGroupDefineDao groupDao;
    @Autowired
    private RunTimeFormGroupLinkDao groupLinkDao;
    @Autowired
    private RunTimeBigDataTableDao bigDataDao;
    public static String BIG_FORM_DATA = "FORM_DATA";

    public void setFormDao(RunTimeFormDefineDao formDao) {
        this.formDao = formDao;
    }

    public void setGroupDao(RunTimeFormGroupDefineDao groupDao) {
        this.groupDao = groupDao;
    }

    public void setGroupLinkDao(RunTimeFormGroupLinkDao groupLinkDao) {
        this.groupLinkDao = groupLinkDao;
    }

    public void setBigDataDao(RunTimeBigDataTableDao bigDataDao) {
        this.bigDataDao = bigDataDao;
    }

    public List<FormDefine> queryFormDefineByGroupId(String id) throws Exception {
        return this.queryFormDefineByGroupId(id, true);
    }

    public List<FormDefine> queryFormDefineByGroupId(String id, boolean withBinaryData) throws Exception {
        List<FormDefine> defines = this.formDao.listByGroup(id);
        if (withBinaryData) {
            this.setReportData(defines);
        }
        return defines;
    }

    public FormDefine queryFormDefineByGroupId(String id, String formDefineCode, boolean withBinaryData) throws Exception {
        List<FormDefine> defines = this.formDao.listByGroup(id, formDefineCode);
        if (withBinaryData) {
            this.setReportData(defines);
        }
        if (null != defines && defines.size() > 0) {
            return defines.get(0);
        }
        return null;
    }

    public List<FormDefine> queryFormDefinesByGroupId(String id, String formDefineCode, boolean withBinaryData) throws Exception {
        List<FormDefine> defines = this.formDao.listByGroup(id, formDefineCode);
        if (withBinaryData) {
            this.setReportData(defines);
        }
        return defines;
    }

    public FormDefine queryFormDefine(String id) throws Exception {
        return this.queryFormDefine(id, true);
    }

    public FormDefine queryFormDefine(String id, boolean withBinaryData) throws Exception {
        FormDefine define = this.formDao.getDefineByKey(id);
        if (withBinaryData && define != null) {
            this.setReportData(define);
        }
        return define;
    }

    public FormDefine queryFormDefineByCode(String code) throws Exception {
        return this.queryFormDefine(code, true);
    }

    public FormDefine queryFormDefineByCode(String code, boolean withBinaryData) throws Exception {
        FormDefine define = this.formDao.queryDefinesByCode(code);
        if (withBinaryData) {
            this.setReportData(define);
        }
        return define;
    }

    public FormDefine queryFormDefineByTask(String taskKey, String formDefineCode) throws Exception {
        return this.queryFormDefineByTask(taskKey, formDefineCode, true);
    }

    public FormDefine queryFormDefineByTask(String taskKey, String formDefineCode, boolean withBinaryData) throws Exception {
        FormDefine define = this.formDao.queryDefineBytask(taskKey, formDefineCode);
        if (withBinaryData) {
            this.setReportData(define);
        }
        return define;
    }

    public List<FormDefine> queryFormDefinesByTask(String taskKey, String formDefineCode, boolean withBinaryData) throws Exception {
        List<FormDefine> defines = this.formDao.queryDefinesBytask(taskKey, formDefineCode);
        if (withBinaryData) {
            this.setReportData(defines);
        }
        return defines;
    }

    public List<FormDefine> queryFormDefinesByTask(String taskKey, boolean withBinaryData) throws Exception {
        List<FormDefine> defines = this.formDao.queryDefinesBytask(taskKey);
        if (withBinaryData) {
            this.setReportData(defines);
        }
        return defines;
    }

    public FormDefine queryFormDefineByScheme(String formSchemeKey, String formDefineCode) throws Exception {
        return this.queryFormDefineByScheme(formSchemeKey, formDefineCode, true);
    }

    public FormDefine queryFormDefineByScheme(String formSchemeKey, String formDefineCode, boolean withBinaryData) throws Exception {
        FormDefine define = this.formDao.queryFormDefineByScheme(formSchemeKey, formDefineCode);
        if (withBinaryData) {
            this.setReportData(define);
        }
        return define;
    }

    public List<FormDefine> queryAllFormDefine() throws Exception {
        return this.queryAllFormDefine(true);
    }

    public List<FormDefine> queryAllFormDefine(boolean withBinaryData) throws Exception {
        List<FormDefine> defines = this.formDao.list();
        if (withBinaryData) {
            this.setReportData(defines);
        }
        return defines;
    }

    public void delete(String[] keys) throws Exception {
        this.formDao.delete(keys);
        for (String id : keys) {
            this.DeleteReportData(id);
        }
    }

    public void delete(String key) throws Exception {
        this.formDao.delete(key);
        this.DeleteReportData(key);
    }

    public FormDefine queryFormDefinesByCode(String code) throws Exception {
        FormDefine define = this.formDao.queryDefinesByCode(code);
        this.setReportData(define);
        return define;
    }

    public List<FormDefine> queryFormDefinesListByCode(String code) throws Exception {
        List<FormDefine> defines = this.formDao.queryDefinesListByCode(code);
        this.setReportData(defines);
        return defines;
    }

    public List<FormDefine> queryFormDefines(String[] keys) {
        ArrayList<FormDefine> list = new ArrayList<FormDefine>();
        for (String key : keys) {
            FormDefine define = this.formDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        this.setReportData(list);
        return list;
    }

    public List<FormDefine> getAllFormsInGroup(String formGroupKey) throws Exception {
        List<FormDefine> list = this.formDao.getAllFormsInGroup(formGroupKey);
        this.setReportData(list);
        return list;
    }

    public List<FormDefine> getAllFormsInGroups(String[] formGroupKeys, boolean withBinaryData) throws Exception {
        ArrayList<FormDefine> list = new ArrayList<FormDefine>();
        for (String id : formGroupKeys) {
            List<FormDefine> list2 = this.formDao.getAllFormsInGroup(id);
            if (withBinaryData) {
                this.setReportData(list2);
            }
            list.addAll(list2);
        }
        return list;
    }

    public List<RunTimeFormGroupLink> getFormGroupLinksByFormId(String formKey) throws Exception {
        return this.groupLinkDao.getFormGroupLinksByFormId(formKey);
    }

    public void deleteAllFromDefines() throws Exception {
        this.formDao.deleteAllFromDefines();
    }

    public void addFormToGroup(String formKey, String formGroupKey) throws Exception {
        FormDefine define = this.formDao.getDefineByKey(formKey);
        if (define == null) {
            throw new Exception("\u4e0d\u5b58\u5728ID\u4e3a\u3010" + formKey + "\u3011\u7684\u8868\u5355");
        }
        FormGroupDefine group = this.groupDao.getDefineByKey(formGroupKey);
        if (group == null) {
            throw new Exception("\u4e0d\u5b58\u5728ID\u4e3a\u3010" + formGroupKey + "\u3011\u7684\u8868\u5355\u5206\u7ec4");
        }
        RunTimeFormGroupLink link = new RunTimeFormGroupLink();
        link.setFormKey(formKey);
        link.setGroupKey(formGroupKey);
        this.groupLinkDao.insert(link);
    }

    public void removeFormFromGroup(String formKey, String formGroupKey) throws Exception {
        FormDefine define = this.formDao.getDefineByKey(formKey);
        if (define == null) {
            throw new Exception("\u4e0d\u5b58\u5728ID\u4e3a\u3010" + formKey + "\u3011\u7684\u8868\u5355");
        }
        FormGroupDefine group = this.groupDao.getDefineByKey(formGroupKey);
        if (group == null) {
            throw new Exception("\u4e0d\u5b58\u5728ID\u4e3a\u3010" + formGroupKey + "\u3011\u7684\u8868\u5355\u5206\u7ec4");
        }
        RunTimeFormGroupLink link = new RunTimeFormGroupLink();
        link.setFormKey(formKey);
        link.setGroupKey(formGroupKey);
        this.groupLinkDao.deleteLink(link);
    }

    public void updateFormAttrData(String formKey, String code, byte[] data) throws Exception {
        this.UpdateBigDataDefine(formKey, code, RunTimeBigDataTableDao.DEFAULT_lAND, data);
    }

    public void updateReportData(String formKey, byte[] data) throws Exception {
        this.UpdateBigDataDefine(formKey, BIG_FORM_DATA, RunTimeBigDataTableDao.DEFAULT_lAND, data);
    }

    public void updateReportData(String formKey, byte[] data, int lang) throws Exception {
        this.UpdateBigDataDefine(formKey, BIG_FORM_DATA, lang, data);
    }

    public void updateReportData(FormDefine form) throws Exception {
        this.updateReportData(form.getKey(), form.getBinaryData());
    }

    public byte[] getReportData(String formKey) {
        RunTimeBigDataTable bigData = this.queryBigDataDefine(formKey, BIG_FORM_DATA, RunTimeBigDataTableDao.DEFAULT_lAND);
        if (null != bigData) {
            return bigData.getData();
        }
        return null;
    }

    public void setReportData(FormDefine define) throws Exception {
        RunTimeFormDefineImpl aa = (RunTimeFormDefineImpl)define;
        aa.setBinaryData(this.getReportData(define.getKey()));
    }

    public void setReportData(List<FormDefine> defines) {
        for (FormDefine define : defines) {
            RunTimeFormDefineImpl aa = (RunTimeFormDefineImpl)define;
            aa.setBinaryData(this.getReportData(define.getKey()));
        }
    }

    public void DeleteReportData(String formKey) throws Exception {
        this.deleteBigDataDefine(formKey, BIG_FORM_DATA, RunTimeBigDataTableDao.DEFAULT_lAND);
    }

    public void DeleteReportData(String formKey, int lang) throws Exception {
        this.deleteBigDataDefine(formKey, BIG_FORM_DATA, lang);
    }

    public RunTimeBigDataTable queryBigDataDefine(String Key2, String code, int Lang) {
        return this.bigDataDao.queryigDataDefine(Key2, code, Lang);
    }

    public RunTimeBigDataTable queryBigDataDefine(String Key2, String code) throws Exception {
        return this.bigDataDao.queryigDataDefine(Key2, code);
    }

    public void UpdateBigDataDefine(RunTimeBigDataTable bigData, byte[] data) throws Exception {
        bigData.setData(data);
        RunTimeBigDataTable oldData = this.bigDataDao.queryigDataDefine(bigData.getKey(), bigData.getCode(), bigData.getLang());
        if (oldData == null) {
            this.bigDataDao.insert(bigData);
        } else {
            oldData.setData(data);
            this.bigDataDao.updateData(oldData);
        }
    }

    public void UpdateBigDataDefine(String key, String code, int lang, byte[] data) throws Exception {
        RunTimeBigDataTable bigData = this.bigDataDao.queryigDataDefine(key, code, lang);
        if (bigData == null) {
            bigData = new RunTimeBigDataTable();
            bigData.setKey(key);
            bigData.setLang(lang);
            bigData.setCode(code);
            bigData.setData(data);
            bigData.setVersion(RunTimeBigDataTableDao.DEFAULT_VERSION);
            this.bigDataDao.insert(bigData);
        } else {
            bigData.setData(data);
            this.bigDataDao.update(bigData);
        }
    }

    public void updateBigDataDefine(String key, String code, byte[] data) throws Exception {
        RunTimeBigDataTable bigData = this.bigDataDao.queryigDataDefine(key, code);
        if (bigData == null) {
            bigData = new RunTimeBigDataTable();
            bigData.setKey(key);
            bigData.setCode(code);
            bigData.setData(data);
            bigData.setLang(RunTimeBigDataTableDao.DEFAULT_lAND);
            bigData.setVersion(RunTimeBigDataTableDao.DEFAULT_VERSION);
            this.bigDataDao.insert(bigData);
        } else {
            bigData.setData(data);
            this.bigDataDao.update(bigData);
        }
    }

    public void deleteBigDataDefine(String key, String code, int lang) throws Exception {
        this.bigDataDao.deleteBigData(key, code, lang);
    }

    public void DeleteBigDataDefine(String key, String code) throws Exception {
        this.bigDataDao.deleteBigData(key, code, RunTimeBigDataTableDao.DEFAULT_lAND);
    }

    public void insertFormDefine(FormDefine formDefine) throws Exception {
        this.formDao.insert(formDefine);
    }

    public void insertFormDefines(FormDefine[] formDefines) throws Exception {
        this.formDao.insert(formDefines);
    }

    public List<FormDefine> listGhostForm() {
        return this.formDao.listGhostForm();
    }
}

