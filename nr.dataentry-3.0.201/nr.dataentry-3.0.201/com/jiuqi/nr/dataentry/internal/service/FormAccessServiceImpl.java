/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.jtable.exception.SaveDataException
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IFormAccessService
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.exception.SaveDataException;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IFormAccessService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormAccessServiceImpl
implements IFormAccessService {
    @Autowired
    private ReadWriteAccessProvider accessProvider;

    public boolean formAccess(JtableContext jtableContext, boolean writeAuth) {
        ArrayList<String> formkeyList = new ArrayList<String>();
        formkeyList.add(jtableContext.getFormKey());
        ReadWriteAccessCacheParams readWriteAccessCacheParams = new ReadWriteAccessCacheParams(jtableContext, formkeyList, writeAuth ? Consts.FormAccessLevel.FORM_DATA_WRITE : Consts.FormAccessLevel.FORM_READ);
        ReadWriteAccessCacheManager readWriteAccessCacheManager = (ReadWriteAccessCacheManager)BeanUtil.getBean(ReadWriteAccessCacheManager.class);
        readWriteAccessCacheManager.initCache(readWriteAccessCacheParams);
        FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData(jtableContext, writeAuth ? Consts.FormAccessLevel.FORM_DATA_WRITE : Consts.FormAccessLevel.FORM_READ);
        FormReadWriteAccessData accessForms = this.accessProvider.getAccessForms(formReadWriteAccessData, readWriteAccessCacheManager);
        List<String> formKeys = accessForms.getFormKeys();
        if (formKeys.contains(jtableContext.getFormKey())) {
            return true;
        }
        String oneFormKeyReason = accessForms.getOneFormKeyReason(jtableContext.getFormKey());
        String[] errors = new String[]{oneFormKeyReason};
        throw new SaveDataException(errors);
    }
}

