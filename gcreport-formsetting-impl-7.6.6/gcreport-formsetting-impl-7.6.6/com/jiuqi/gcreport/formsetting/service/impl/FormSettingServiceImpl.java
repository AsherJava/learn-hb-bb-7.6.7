/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.formsetting.vo.FormSettingVO
 *  com.jiuqi.gcreport.formsetting.vo.SettingVO
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.formsetting.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.formsetting.service.FormSettingAdvanceService;
import com.jiuqi.gcreport.formsetting.service.FormSettingService;
import com.jiuqi.gcreport.formsetting.util.FormSettingProcessor;
import com.jiuqi.gcreport.formsetting.vo.FormSettingVO;
import com.jiuqi.gcreport.formsetting.vo.SettingVO;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormSettingServiceImpl
implements FormSettingService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private FormSettingAdvanceService advanceService;
    @Autowired
    private IDataDefinitionDesignTimeController dataDefinitionDesignTimeController;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String createForm(FormSettingVO formSetting) {
        DesignFormDefine formDefine = null;
        try {
            DesignFormDefine formDefineOld = this.designTimeViewController.queryFormDefineByCodeInFormSchemeWithoutBinaryData(formSetting.getFormSchemeKey(), formSetting.getCode().toUpperCase());
            if (formDefineOld != null) {
                throw new BusinessRuntimeException("\u5f53\u524d\u62a5\u8868\u65b9\u6848\u4e0b\u62a5\u8868\u6807\u8bc6\u91cd\u590d\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165\uff01");
            }
            formDefine = this.designTimeViewController.createFormDefine();
            formDefine.setKey(UUIDUtils.newUUIDStr());
            formDefine.setFormCode(formSetting.getCode().toUpperCase());
            formDefine.setTitle(formSetting.getTitle());
            formDefine.setFormType(FormType.FORM_TYPE_FLOAT);
            formDefine.setIsGather(false);
            formDefine.setDescription("\u5408\u5e76\u62b5\u9500\u8868");
            formDefine.setOrder(OrderGenerator.newOrder());
            formDefine.setUpdateUser(NpContextHolder.getContext().getUser().getName());
            formDefine.setUpdateTime(new Date());
            formDefine.setFormScheme(formSetting.getFormSchemeKey());
            this.designTimeViewController.addNewFormDefine(formDefine, formSetting.getFormGroupId());
        }
        catch (JQException e) {
            throw new BusinessRuntimeException("\u521b\u5efa\u8868\u5355\u5931\u8d25", (Throwable)e);
        }
        DesignFormSchemeDefine designFormSchemeDefine = this.designTimeViewController.queryFormSchemeDefine(formSetting.getFormSchemeKey());
        DesignTaskDefine designTaskDefine = this.designTimeViewController.queryTaskDefine(designFormSchemeDefine.getTaskKey());
        String logContent = String.format("\u65b0\u5efa-\u4efb\u52a1%1$s-\u62a5\u8868%2$s", designTaskDefine.getTitle(), formDefine.getTitle());
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u62b5\u9500\u8868\u8bbe\u7f6e", (String)logContent, (String)logContent);
        return formDefine.getKey();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void setForm(SettingVO setting) {
        DesignFormSchemeDefine designFormSchemeDefine = this.designTimeViewController.queryFormSchemeDefine(setting.getFormSetting().getFormSchemeKey());
        DesignTaskDefine designTaskDefine = this.designTimeViewController.queryTaskDefine(designFormSchemeDefine.getTaskKey());
        String logContent = String.format("\u8bbe\u7f6e-\u4efb\u52a1%1$s-\u62a5\u8868%2$s", designTaskDefine.getTitle(), setting.getFormSetting().getTitle());
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u62b5\u9500\u8868\u8bbe\u7f6e", (String)logContent, (String)logContent);
        FormSettingProcessor processor = new FormSettingProcessor(setting);
        this.advanceService.updateFormSetting(processor);
        this.advanceService.updateFormula(processor);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public TableDefine queryOwnTable(String fieldId) {
        DesignTableDefine table;
        DesignFieldDefine referField;
        try {
            referField = this.dataDefinitionDesignTimeController.queryFieldDefine(fieldId);
        }
        catch (JQException e) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u5b57\u6bb5\u5b9a\u4e49\u5931\u8d25\u3002", (Throwable)e);
        }
        if (referField == null) {
            return null;
        }
        try {
            table = this.dataDefinitionDesignTimeController.queryTableDefine(referField.getOwnerTableKey());
        }
        catch (JQException e) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u8868\u5b9a\u4e49\u5931\u8d25\u3002", (Throwable)e);
        }
        return table;
    }
}

