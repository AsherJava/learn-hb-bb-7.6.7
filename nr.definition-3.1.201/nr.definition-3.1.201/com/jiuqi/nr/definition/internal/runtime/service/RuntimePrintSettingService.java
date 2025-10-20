/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.nr.definition.facade.PrintSettingDefine;
import com.jiuqi.nr.definition.internal.dao.RuntimePrintSettingDefineDao;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimePrintSettingService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuntimePrintSettingService
implements IRuntimePrintSettingService {
    @Autowired
    private RuntimePrintSettingDefineDao runtimePrintSettingDefineDao;

    @Override
    public PrintSettingDefine query(String printSchemeKey, String formKey) {
        return this.runtimePrintSettingDefineDao.query(printSchemeKey, formKey);
    }

    @Override
    public List<PrintSettingDefine> list(String printSchemeKey) {
        List defines = this.runtimePrintSettingDefineDao.list(printSchemeKey);
        return new ArrayList<PrintSettingDefine>(defines);
    }
}

