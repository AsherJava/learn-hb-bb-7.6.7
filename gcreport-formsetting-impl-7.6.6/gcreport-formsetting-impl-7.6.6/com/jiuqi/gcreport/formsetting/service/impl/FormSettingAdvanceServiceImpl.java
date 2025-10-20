/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.formsetting.service.impl;

import com.jiuqi.gcreport.formsetting.service.FormSettingAdvanceService;
import com.jiuqi.gcreport.formsetting.util.FormSettingProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormSettingAdvanceServiceImpl
implements FormSettingAdvanceService {
    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void updateFormSetting(FormSettingProcessor processor) {
        processor.updateFormSetting();
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void updateFormula(FormSettingProcessor processor) {
        processor.updateFormulas();
    }
}

