/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.facade.DesignPrintSettingDefine;
import com.jiuqi.nr.definition.internal.dao.DesignPrintSettingDefineDao;
import com.jiuqi.nr.definition.internal.impl.DesignPrintSettingDefineImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DesignPrintSettingDefineService {
    @Autowired
    private DesignPrintSettingDefineDao designPrintSettingDefineDao;

    public DesignPrintSettingDefine query(String printSchemeKey, String formKey) {
        return (DesignPrintSettingDefine)this.designPrintSettingDefineDao.query(printSchemeKey, formKey);
    }

    public List<DesignPrintSettingDefine> list(String printSchemeKey) {
        List defines = this.designPrintSettingDefineDao.list(printSchemeKey);
        return new ArrayList<DesignPrintSettingDefine>(defines);
    }

    public void delete(String printSchemeKey, String formKey) {
        this.designPrintSettingDefineDao.delete(printSchemeKey, formKey);
    }

    public void delete(String printSchemeKey) {
        this.designPrintSettingDefineDao.delete(printSchemeKey);
    }

    public void insert(DesignPrintSettingDefine define) {
        if (null == define) {
            return;
        }
        if (null == define.getUpdateTime()) {
            define.setUpdateTime(new Date());
        }
        DesignPrintSettingDefineImpl impl = DesignPrintSettingDefineImpl.valueOf(define);
        this.designPrintSettingDefineDao.insert(impl);
    }

    public void insert(List<DesignPrintSettingDefine> defines) {
        if (CollectionUtils.isEmpty(defines)) {
            return;
        }
        for (DesignPrintSettingDefine define : defines) {
            if (null != define.getUpdateTime()) continue;
            define.setUpdateTime(new Date());
        }
        this.designPrintSettingDefineDao.insert(defines.stream().map(DesignPrintSettingDefineImpl::valueOf).collect(Collectors.toList()));
    }

    public void update(DesignPrintSettingDefine define) {
        if (null == define) {
            return;
        }
        if (null == define.getUpdateTime()) {
            define.setUpdateTime(new Date());
        }
        DesignPrintSettingDefineImpl impl = DesignPrintSettingDefineImpl.valueOf(define);
        this.designPrintSettingDefineDao.update(impl);
    }

    public void update(List<DesignPrintSettingDefine> defines) {
        if (CollectionUtils.isEmpty(defines)) {
            return;
        }
        for (DesignPrintSettingDefine define : defines) {
            if (null != define.getUpdateTime()) continue;
            define.setUpdateTime(new Date());
        }
        this.designPrintSettingDefineDao.update(defines.stream().map(DesignPrintSettingDefineImpl::valueOf).collect(Collectors.toList()));
    }
}

