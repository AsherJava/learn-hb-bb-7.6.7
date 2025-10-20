/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.datascheme.i18n.language.LanguageType
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.config.ParamMaxNumberConfig;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.dao.DesignFormDefineDao;
import com.jiuqi.nr.definition.internal.dao.DesignFormGroupDefineDao;
import com.jiuqi.nr.definition.internal.dao.DesignFormGroupLinkDao;
import com.jiuqi.nr.definition.internal.dao.DesignFormSchemeDefineDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignFormDefineService {
    private static final Logger logger = LoggerFactory.getLogger(DesignFormDefineService.class);
    @Autowired
    private DesignFormDefineDao formDao;
    @Autowired
    private DesignFormSchemeDefineDao schemeDao;
    @Autowired
    private DesignFormGroupDefineDao groupDao;
    @Autowired
    private DesignFormGroupLinkDao groupLinkDao;
    @Autowired
    private DesignBigDataTableDao bigDataDao;
    @Autowired
    private ParamMaxNumberConfig paramMaxNumberConfig;
    public static final String BIG_FORM_DATA = "FORM_DATA";
    public static final String BIG_FILLING_GUIDE = "FILLING_GUIDE";
    public static final String BIG_SURVEY_DATA = "BIG_SURVEY_DATA";
    public static final String BIG_SCRIPT_EDITOR = "BIG_SCRIPT_EDITOR";
    public static final String BIG_ATTACHMENT_DATA = "ATTACHMENT";
    public static final String BIG_EXTENT_STYLE = "EXTENTSTYLE";
    public static final String BIG_ANALYSIS_FORM_PARAM = "ANALYSIS_FORM_PARAM";
    public static final String BIG_REGION_SURVEY = "BIG_REGION_SURVEY";

    public String insertFormDefine(DesignFormDefine define) throws Exception {
        if (define.getKey() == null) {
            define.setKey(UUIDUtils.getKey());
        }
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        this.maxCheck(1);
        this.formDao.insert(define);
        this.updateBigDataDefine(define.getKey(), BIG_FORM_DATA, LanguageType.CHINESE.getValue(), define.getBinaryData());
        this.updateBigDataDefine(define.getKey(), BIG_SURVEY_DATA, LanguageType.CHINESE.getValue(), DesignFormDefineBigDataUtil.StringToBytes(define.getSurveyData()));
        this.updateBigDataDefine(define.getKey(), BIG_FILLING_GUIDE, LanguageType.CHINESE.getValue(), DesignFormDefineBigDataUtil.StringToBytes(define.getFillingGuide()));
        this.updateBigDataDefine(define.getKey(), BIG_SCRIPT_EDITOR, LanguageType.CHINESE.getValue(), DesignFormDefineBigDataUtil.StringToBytes(define.getScriptEditor()));
        return define.getKey();
    }

    private void maxCheck(int insertNumber) {
        int count;
        int formMaxNumber = this.paramMaxNumberConfig.getFormMaxNumber();
        if (formMaxNumber != 0 && (count = this.count()) + insertNumber > formMaxNumber) {
            throw new RuntimeException("\u8d85\u8fc7\u8868\u5355\u4e0a\u9650\uff0c\u6700\u5927:" + formMaxNumber);
        }
    }

    public int count() {
        return this.formDao.count(null);
    }

    public String insertFormDefine(DesignFormDefine define, int type) throws Exception {
        if (define.getKey() == null) {
            define.setKey(UUIDUtils.getKey());
        }
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        this.maxCheck(1);
        this.formDao.insert(define);
        this.updateBigDataDefine(define.getKey(), BIG_FORM_DATA, type, define.getBinaryData());
        this.updateBigDataDefine(define.getKey(), BIG_SURVEY_DATA, type, DesignFormDefineBigDataUtil.StringToBytes(define.getSurveyData()));
        this.updateBigDataDefine(define.getKey(), BIG_FILLING_GUIDE, LanguageType.CHINESE.getValue(), DesignFormDefineBigDataUtil.StringToBytes(define.getFillingGuide()));
        this.updateBigDataDefine(define.getKey(), BIG_SCRIPT_EDITOR, type, DesignFormDefineBigDataUtil.StringToBytes(define.getScriptEditor()));
        return define.getKey();
    }

    public void updateFormDefine(DesignFormDefine define) throws Exception {
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        this.formDao.update(define);
        this.updateBigDataDefine(define.getKey(), BIG_FORM_DATA, LanguageType.CHINESE.getValue(), define.getBinaryData());
        this.updateBigDataDefine(define.getKey(), BIG_SURVEY_DATA, LanguageType.CHINESE.getValue(), DesignFormDefineBigDataUtil.StringToBytes(define.getSurveyData()));
        this.updateBigDataDefine(define.getKey(), BIG_FILLING_GUIDE, LanguageType.CHINESE.getValue(), DesignFormDefineBigDataUtil.StringToBytes(define.getFillingGuide()));
        this.updateBigDataDefine(define.getKey(), BIG_SCRIPT_EDITOR, LanguageType.CHINESE.getValue(), DesignFormDefineBigDataUtil.StringToBytes(define.getScriptEditor()));
    }

    public void updateFormDefine(DesignFormDefine define, int type) throws Exception {
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        this.formDao.update(define);
        Grid2Data.bytesToGrid((byte[])define.getBinaryData());
        this.updateBigDataDefine(define.getKey(), BIG_FORM_DATA, type, define.getBinaryData());
        this.updateBigDataDefine(define.getKey(), BIG_SURVEY_DATA, type, DesignFormDefineBigDataUtil.StringToBytes(define.getSurveyData()));
        this.updateBigDataDefine(define.getKey(), BIG_FILLING_GUIDE, LanguageType.CHINESE.getValue(), DesignFormDefineBigDataUtil.StringToBytes(define.getFillingGuide()));
        this.updateBigDataDefine(define.getKey(), BIG_SCRIPT_EDITOR, type, DesignFormDefineBigDataUtil.StringToBytes(define.getScriptEditor()));
    }

    public void updateFormTime(String formKey) {
        this.formDao.updateFormTime(formKey);
    }

    public void batchUpdateFormTime(String ... formKeys) {
        this.formDao.batchUpdateFormTime(formKeys);
    }

    public List<DesignFormDefine> queryFormDefineByGroupId(String id) throws Exception {
        return this.queryFormDefineByGroupId(id, true);
    }

    public List<DesignFormDefine> queryFormDefineByGroupId(String id, boolean withBinaryData) throws Exception {
        List<DesignFormDefine> defines = this.querySoftFormDefineByGroupId(id);
        if (withBinaryData) {
            this.setFillingGuide(defines);
            this.setReportData(defines);
            this.setSurveyData(defines);
        }
        return defines;
    }

    public List<DesignFormDefine> querySoftFormDefineByGroupId(String id) throws Exception {
        return this.formDao.getAllFormsInGroup(id);
    }

    public DesignFormDefine queryFormDefineByGroupId(String id, String formDefineCode, boolean withBinaryData) throws JQException {
        List<DesignFormDefine> defines = null;
        try {
            defines = this.formDao.getFormsInGroupByCode(id, formDefineCode);
            this.setFillingGuide(defines);
            if (withBinaryData) {
                this.setReportData(defines);
                this.setSurveyData(defines);
            }
        }
        catch (JQException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_004, (Throwable)e);
        }
        if (null != defines && defines.size() > 0) {
            return defines.get(0);
        }
        return null;
    }

    public DesignFormDefine queryFormDefineByTitleInGroup(String id, String formDefineTitle, boolean withBinaryData) throws JQException {
        List<DesignFormDefine> defines = null;
        try {
            defines = this.formDao.getFormsInGroupByTitle(id, formDefineTitle);
            this.setFillingGuide(defines);
            if (withBinaryData) {
                this.setReportData(defines);
                this.setSurveyData(defines);
            }
        }
        catch (JQException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_004, (Throwable)e);
        }
        if (null != defines && defines.size() > 0) {
            return defines.get(0);
        }
        return null;
    }

    public DesignFormDefine queryFormDefineByCode(String code, boolean withBinaryData) throws Exception {
        DesignFormDefine define = this.formDao.queryDefinesByCode(code);
        this.setFillingGuide(define);
        if (withBinaryData) {
            this.setReportData(define);
            this.setSurveyData(define);
        }
        return define;
    }

    public DesignFormDefine queryFormDefineByTask(String taskKey, String formDefineCode) throws Exception {
        return this.queryFormDefineByTask(taskKey, formDefineCode, true);
    }

    public DesignFormDefine queryFormDefineByTask(String taskKey, String formDefineCode, boolean withBinaryData) throws Exception {
        DesignFormDefine define = null;
        List<DesignFormDefine> forms = this.queryFormDefinesByTask(taskKey, formDefineCode, withBinaryData);
        if (null != forms && forms.size() > 0) {
            define = forms.get(0);
            this.setFillingGuide(define);
            if (withBinaryData) {
                this.setReportData(define);
                this.setSurveyData(define);
            }
        }
        return define;
    }

    public List<DesignFormDefine> queryFormDefinesByTask(String taskKey, String formDefineCode, boolean withBinaryData) throws Exception {
        ArrayList<DesignFormDefine> defines = new ArrayList<DesignFormDefine>();
        List<DesignFormSchemeDefine> schemes = this.schemeDao.listByTask(taskKey);
        for (DesignFormSchemeDefine scheme : schemes) {
            DesignFormDefine form = this.queryFormDefineByCodeInScheme(scheme.getKey(), formDefineCode, withBinaryData);
            if (null == form) continue;
            defines.add(form);
        }
        return defines;
    }

    public List<DesignFormDefine> queryFormDefinesByTask(String taskKey, boolean withBinaryData) throws Exception {
        ArrayList<DesignFormDefine> defines = new ArrayList<DesignFormDefine>();
        List<DesignFormSchemeDefine> schemes = this.schemeDao.listByTask(taskKey);
        for (DesignFormSchemeDefine scheme : schemes) {
            List<DesignFormDefine> forms = this.queryFormDefineByScheme(scheme.getKey(), withBinaryData);
            if (null == forms || forms.size() <= 0) continue;
            defines.addAll(forms);
        }
        this.setFillingGuide(defines);
        if (withBinaryData) {
            this.setReportData(defines);
            this.setSurveyData(defines);
        }
        return defines;
    }

    public DesignFormDefine queryFormDefineByScheme(String formSchemeKey, String formDefineCode) throws Exception {
        return this.queryFormDefineByCodeInScheme(formSchemeKey, formDefineCode, true);
    }

    public DesignFormDefine querySoftFormDefineBySchenme(String formSchemeKey, String formDefineCode) {
        List<DesignFormDefine> defines = this.formDao.getFormsInSchemeByCode(formSchemeKey, formDefineCode);
        if (null != defines && defines.size() > 0) {
            return defines.get(0);
        }
        return null;
    }

    public DesignFormDefine queryFormDefineByCodeInScheme(String formSchemeKey, String formDefineCode, boolean withBinaryData) throws JQException {
        ArrayList<DesignFormDefine> defines = new ArrayList();
        try {
            defines = this.formDao.getFormsInSchemeByCode(formSchemeKey, formDefineCode);
            this.setFillingGuide(defines);
            if (withBinaryData) {
                this.setReportData(defines);
                this.setSurveyData(defines);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (null != defines && defines.size() > 0) {
            return (DesignFormDefine)defines.get(0);
        }
        return null;
    }

    public DesignFormDefine queryFormDefineBTitleInScheme(String formSchemeKey, String formDefineTitle, boolean withBinaryData) throws JQException {
        DesignFormGroupDefine group;
        List<DesignFormGroupDefine> groups = this.groupDao.queryDefinesByFormScheme(formSchemeKey);
        DesignFormDefine form = null;
        Iterator<DesignFormGroupDefine> iterator = groups.iterator();
        while (iterator.hasNext() && (form = this.queryFormDefineByTitleInGroup((group = iterator.next()).getKey(), formDefineTitle, withBinaryData)) == null) {
        }
        return form;
    }

    public List<DesignFormDefine> queryFormDefineByScheme(String formSchemeKey, boolean withBinaryData) throws Exception {
        List<DesignFormGroupDefine> groups = this.groupDao.queryDefinesByFormScheme(formSchemeKey);
        ArrayList<DesignFormDefine> forms = new ArrayList<DesignFormDefine>();
        if (null != groups) {
            for (DesignFormGroupDefine group : groups) {
                List<DesignFormDefine> forms2 = this.formDao.getAllFormsInGroup(group.getKey());
                if (null == forms2) continue;
                forms.addAll(forms2);
            }
            this.setFillingGuide(forms);
            if (withBinaryData) {
                this.setReportData(forms);
                this.setSurveyData(forms);
            }
        }
        return forms;
    }

    public List<DesignFormDefine> queryAllFormDefine() throws Exception {
        return this.queryAllFormDefine(true);
    }

    public List<DesignFormDefine> queryAllFormDefine(boolean withBinaryData) throws Exception {
        List<DesignFormDefine> defines = this.formDao.list();
        this.setFillingGuide(defines);
        if (withBinaryData) {
            this.setReportData(defines);
            this.setSurveyData(defines);
        }
        return defines;
    }

    public void delete(String[] keys) throws Exception {
        this.formDao.delete(keys);
        for (String id : keys) {
            this.deleteReportData(id);
        }
    }

    public void delete(String key) throws Exception {
        this.formDao.delete(key);
        this.deleteReportData(key);
    }

    public DesignFormDefine queryFormDefinesByCode(String code) throws Exception {
        DesignFormDefine define = this.formDao.queryDefinesByCode(code);
        this.setFillingGuide(define);
        this.setReportData(define);
        this.setSurveyData(define);
        return define;
    }

    public List<DesignFormDefine> queryFormDefinesListByCode(String code, boolean withBinaryData) throws Exception {
        List<DesignFormDefine> defines = this.formDao.queryDefinesListByCode(code);
        this.setFillingGuide(defines);
        if (withBinaryData) {
            this.setReportData(defines);
            this.setSurveyData(defines);
        }
        return defines;
    }

    public List<DesignFormDefine> queryFormDefinesListByCode(String code) throws Exception {
        List<DesignFormDefine> defines = this.formDao.queryDefinesListByCode(code);
        this.setReportData(defines);
        this.setSurveyData(defines);
        return defines;
    }

    public List<String> insertFormDefines(DesignFormDefine[] defines) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < defines.length; ++i) {
            if (defines[i].getKey() == null) {
                defines[i].setKey(UUIDUtils.getKey());
            }
            if (defines[i].getUpdateTime() == null) {
                defines[i].setUpdateTime(new Date());
            }
            result.add(defines[i].getKey());
        }
        this.maxCheck(defines.length);
        this.formDao.insert(defines);
        this.updateReportData(defines);
        return result;
    }

    public void updateFormDefines(DesignFormDefine[] defines) throws Exception {
        for (DesignFormDefine define : defines) {
            if (define.getUpdateTime() != null) continue;
            define.setUpdateTime(new Date());
        }
        this.formDao.update(defines);
        this.updateReportData(defines);
    }

    public List<DesignFormDefine> queryFormDefines(String[] keys) throws Exception {
        ArrayList<DesignFormDefine> list = new ArrayList<DesignFormDefine>();
        for (String key : keys) {
            DesignFormDefine define = this.formDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        this.setFillingGuide(list);
        this.setReportData(list);
        this.setSurveyData(list);
        return list;
    }

    public List<DesignFormDefine> getAllFormsInGroup(String formGroupKey) throws Exception {
        List<DesignFormDefine> list = this.formDao.getAllFormsInGroup(formGroupKey);
        this.setFillingGuide(list);
        this.setReportData(list);
        this.setSurveyData(list);
        return list;
    }

    public void deleteAllFromDefines() throws Exception {
        this.formDao.deleteAllFromDefines();
    }

    public void addFormToGroup(String formKey, String formGroupKey) throws Exception {
        if (this.groupLinkDao.queryDesignFormGroupLink(formKey, formGroupKey) != null) {
            return;
        }
        DesignFormDefine define = this.formDao.getDefineByKey(formKey);
        if (define == null) {
            throw new Exception("\u4e0d\u5b58\u5728ID\u4e3a\u3010" + formKey + "\u3011\u7684\u8868\u5355");
        }
        DesignFormGroupDefine group = this.groupDao.getDefineByKey(formGroupKey);
        if (group == null) {
            throw new Exception("\u4e0d\u5b58\u5728ID\u4e3a\u3010" + formGroupKey + "\u3011\u7684\u8868\u5355\u5206\u7ec4");
        }
        DesignFormGroupLink link = new DesignFormGroupLink();
        link.setFormKey(formKey);
        link.setGroupKey(formGroupKey);
        List<DesignFormGroupLink> designFormGroupLinkList = this.groupLinkDao.getFormGroupLinksByFormId(formKey);
        if (designFormGroupLinkList != null && designFormGroupLinkList.size() > 0) {
            link.setFormOrder(OrderGenerator.newOrder());
        } else {
            link.setFormOrder(define.getOrder());
        }
        this.groupLinkDao.insert(link);
    }

    public void removeFormFromGroup(String formKey, String formGroupKey) throws Exception {
        DesignFormGroupLink link = new DesignFormGroupLink();
        link.setFormKey(formKey);
        link.setGroupKey(formGroupKey);
        this.groupLinkDao.deleteLink(link);
    }

    public void updateFormAttrData(String formKey, String code, byte[] data) throws Exception {
        this.updateBigDataDefine(formKey, code, LanguageType.CHINESE.getValue(), data);
    }

    public void updateReportData(String formKey, byte[] data) throws Exception {
        this.updateBigDataDefine(formKey, BIG_FORM_DATA, LanguageType.CHINESE.getValue(), data);
    }

    public void updateReportData(String formKey, byte[] data, int lang) throws Exception {
        this.updateBigDataDefine(formKey, BIG_FORM_DATA, lang, data);
    }

    public void updateReportData(DesignFormDefine form) throws Exception {
        if (form.getBinaryData() != null) {
            this.updateReportData(form.getKey(), form.getBinaryData());
        }
    }

    public void updateReportData(List<DesignFormDefine> forms) throws Exception {
        for (DesignFormDefine form : forms) {
            this.updateReportData(form);
        }
    }

    public void updateReportData(DesignFormDefine[] forms) throws Exception {
        for (DesignFormDefine form : forms) {
            this.updateReportData(form);
        }
    }

    public Map<Integer, byte[]> getReportDatas(String formKey) throws Exception {
        List<DesignBigDataTable> datas = this.bigDataDao.queryigDataDefines(formKey, BIG_FORM_DATA);
        if (null != datas && !datas.isEmpty()) {
            return datas.stream().collect(Collectors.toMap(DesignBigDataTable::getLang, DesignBigDataTable::getData));
        }
        return null;
    }

    public byte[] getReportData(String formKey) throws Exception {
        DesignBigDataTable bigData = this.queryBigDataDefine(formKey, BIG_FORM_DATA, LanguageType.CHINESE.getValue());
        if (null != bigData) {
            return bigData.getData();
        }
        return null;
    }

    public byte[] getReportData(String formKey, int type) throws Exception {
        DesignBigDataTable bigData = this.queryBigDataDefine(formKey, BIG_FORM_DATA, type);
        if (null != bigData) {
            return bigData.getData();
        }
        return null;
    }

    private void setReportData(DesignFormDefine define) throws Exception {
        define.setBinaryData(this.getReportData(define.getKey()));
    }

    private void setReportData(DesignFormDefine define, int type) throws Exception {
        define.setBinaryData(this.getReportData(define.getKey(), type));
    }

    private void setSurveyData(DesignFormDefine define) throws Exception {
        define.setSurveyData(DesignFormDefineBigDataUtil.bytesToString(this.getSurveyData(define.getKey())));
    }

    private void setReportData(List<DesignFormDefine> defines) throws Exception {
        for (DesignFormDefine define : defines) {
            define.setBinaryData(this.getReportData(define.getKey()));
        }
    }

    private void setSurveyData(List<DesignFormDefine> defines) throws Exception {
        for (DesignFormDefine define : defines) {
            define.setSurveyData(DesignFormDefineBigDataUtil.bytesToString(this.getSurveyData(define.getKey())));
        }
    }

    private void setFillingGuide(DesignFormDefine define) throws Exception {
        define.setFillingGuide(DesignFormDefineBigDataUtil.bytesToString(this.getBigData(define.getKey(), BIG_FILLING_GUIDE)));
    }

    private void setFillingGuide(List<DesignFormDefine> defines) throws Exception {
        for (DesignFormDefine define : defines) {
            if (null == define) continue;
            define.setFillingGuide(DesignFormDefineBigDataUtil.bytesToString(this.getBigData(define.getKey(), BIG_FILLING_GUIDE)));
        }
    }

    private void setSriptEditor(DesignFormDefine define) throws Exception {
        if (null != define) {
            define.setScriptEditor(DesignFormDefineBigDataUtil.bytesToString(this.getFrontScript(define.getKey())));
        }
    }

    private void deleteReportData(String formKey) throws Exception {
        this.deleteBigDataDefine(formKey, BIG_FORM_DATA, LanguageType.CHINESE.getValue());
        this.deleteBigDataDefine(formKey, BIG_ANALYSIS_FORM_PARAM, LanguageType.CHINESE.getValue());
    }

    private DesignBigDataTable queryBigDataDefine(String Key2, String code, int Lang) throws Exception {
        return this.bigDataDao.queryigDataDefine(Key2, code, Lang);
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
        this.updateBigDataDefine(key, code, LanguageType.CHINESE.getValue(), data);
    }

    public void deleteBigDataDefine(String key, String code, int lang) throws Exception {
        this.bigDataDao.deleteBigData(key, code, lang);
    }

    public void DeleteBigDataDefine(String key, String code) throws Exception {
        this.bigDataDao.deleteBigData(key, code, LanguageType.CHINESE.getValue());
    }

    public byte[] getBigData(String formKey, String code) throws Exception {
        DesignBigDataTable bigData = this.queryBigDataDefine(formKey, code, LanguageType.CHINESE.getValue());
        if (null != bigData) {
            return bigData.getData();
        }
        return null;
    }

    public byte[] getBigDataByLanguageType(String formKey, String code, int lang) throws Exception {
        DesignBigDataTable bigData = this.queryBigDataDefine(formKey, code, lang);
        if (null != bigData) {
            return bigData.getData();
        }
        return null;
    }

    public void deleteOrderBigData(String formKey, String code) throws Exception {
        this.deleteBigDataDefine(formKey, code, LanguageType.CHINESE.getValue());
    }

    public List<DesignFormGroupLink> getFormGroupLinksByFormId(String formKey) throws Exception {
        List<DesignFormGroupLink> list = this.groupLinkDao.getFormGroupLinksByFormId(formKey);
        return list;
    }

    public List<DesignFormGroupLink> getFormGroupLinksByGroupId(String groupKey) throws Exception {
        List<DesignFormGroupLink> list = this.groupLinkDao.getFormGroupLinksByGroupId(groupKey);
        return list;
    }

    public void updateDesignFormGroupLink(DesignFormGroupLink designFormGroupLink) throws Exception {
        if (this.groupLinkDao.queryDesignFormGroupLink(designFormGroupLink.getFormKey(), designFormGroupLink.getGroupKey()) == null) {
            throw new Exception("\u4e0d\u5b58\u5728formKey\u4e3a\u3010" + designFormGroupLink.getFormKey() + "\u3011, groupKey\u4e3a \u3010" + designFormGroupLink.getGroupKey() + "\u3011\u7684\u8868\u5355");
        }
        this.groupLinkDao.updateLink(designFormGroupLink);
    }

    public DesignFormGroupLink queryDesignFormGroupLink(String formKey, String groupKey) throws Exception {
        return this.groupLinkDao.queryDesignFormGroupLink(formKey, groupKey);
    }

    public List<DesignFormDefine> queryFormsByTypeInScheme(String formSchemeKey, FormType type) throws JQException {
        return this.formDao.queryByFormSchemeAndType(formSchemeKey, type);
    }

    public List<DesignFormDefine> listGhostForm() {
        return this.formDao.listGhostForm();
    }

    public DesignFormDefine queryFormDefineContainsFormData(String formKey, int containType) throws Exception {
        DesignFormDefine define = this.formDao.getDefineByKey(formKey);
        if (define != null) {
            if ((containType & 4) > 0) {
                this.setFillingGuide(define);
            }
            if ((containType & 1) > 0) {
                this.setReportData(define);
            }
            if ((containType & 8) > 0) {
                this.setSriptEditor(define);
            }
            if ((containType & 2) > 0) {
                this.setSurveyData(define);
            }
        }
        return define;
    }

    public void updateFillGuide(String formKey, byte[] fillGuide) throws Exception {
        this.updateBigDataDefine(formKey, BIG_FILLING_GUIDE, LanguageType.CHINESE.getValue(), fillGuide);
    }

    public void updateFrontScript(String formKey, byte[] frontScript) throws Exception {
        this.updateBigDataDefine(formKey, BIG_SCRIPT_EDITOR, LanguageType.CHINESE.getValue(), frontScript);
    }

    public void updateSurveyData(String formKey, byte[] surveyData) throws Exception {
        this.updateBigDataDefine(formKey, BIG_SURVEY_DATA, LanguageType.CHINESE.getValue(), surveyData);
    }

    public byte[] getFillGuide(String formKey) throws Exception {
        DesignBigDataTable bigData = this.queryBigDataDefine(formKey, BIG_FILLING_GUIDE, LanguageType.CHINESE.getValue());
        if (null != bigData) {
            return bigData.getData();
        }
        return null;
    }

    public byte[] getFrontScript(String formKey) throws Exception {
        DesignBigDataTable bigData = this.queryBigDataDefine(formKey, BIG_SCRIPT_EDITOR, LanguageType.CHINESE.getValue());
        if (null != bigData) {
            return bigData.getData();
        }
        return null;
    }

    public byte[] getSurveyData(String formKey) throws Exception {
        DesignBigDataTable bigData = this.queryBigDataDefine(formKey, BIG_SURVEY_DATA, LanguageType.CHINESE.getValue());
        if (null != bigData) {
            return bigData.getData();
        }
        return null;
    }

    public List<DesignFormDefine> queryFormByScheme(String scheme, boolean withBinaryData) throws Exception {
        List<DesignFormDefine> defines = this.formDao.queryByFormScheme(scheme);
        if (withBinaryData) {
            this.setFillingGuide(defines);
            this.setReportData(defines);
            this.setSurveyData(defines);
        }
        return defines;
    }

    public List<DesignFormDefine> queryAllSoftFormDefinesByFormScheme(String scheme) {
        return this.formDao.queryByFormScheme(scheme);
    }

    public List<DesignFormDefine> getSimpleFormDefines(List<String> formKeys) {
        return this.formDao.querySimpleFormDefinesByFormKeys(formKeys);
    }

    public List<DesignFormDefine> getFormDefines(List<String> formKeys) {
        return this.formDao.getDefineByKeys(formKeys);
    }

    public List<DesignFormGroupLink> getFormGroupLink(List<String> groups) {
        return this.groupLinkDao.getFormGroupLinksByGroups(groups);
    }

    public DesignFormDefine getFormDefineByCodeInScheme(String formSchemeKey, String formDefineCode) throws Exception {
        List<Object> defines = new ArrayList();
        defines = this.formDao.getFormsInSchemeByCode(formSchemeKey, formDefineCode);
        if (null != defines && defines.size() > 0) {
            return (DesignFormDefine)defines.get(0);
        }
        return null;
    }

    public List<DesignFormDefine> listFormByFormScheme(String formSchemeKey) throws Exception {
        return this.formDao.queryByFormScheme(formSchemeKey);
    }

    public void syncFormStyleUpdateTime(String srcKey, String desKey) {
        this.bigDataDao.syncUpdateTime(srcKey, desKey, BIG_FORM_DATA, LanguageType.CHINESE.getValue());
    }

    public DesignFormDefine getFormWithoutBinaryData(String formKey) {
        return this.formDao.getDefineByKey(formKey);
    }

    public void updateFormWithoutBinaryData(DesignFormDefine define) throws Exception {
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        this.formDao.update(define);
    }
}

