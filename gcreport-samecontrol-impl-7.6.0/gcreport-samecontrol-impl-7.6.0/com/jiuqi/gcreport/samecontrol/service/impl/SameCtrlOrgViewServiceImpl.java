/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.jiuqi.gcreport.samecontrol.dao.SameCtrlOrgViewDao;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlOrgViewService;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SameCtrlOrgViewServiceImpl
implements SameCtrlOrgViewService {
    @Autowired
    private IDataDefinitionDesignTimeController dataDefinitionDesignTimeController;
    @Autowired
    IRunTimeViewController iRunTimeViewController;
    @Autowired
    private SameCtrlOrgViewDao sameCtrlOrgViewDao;

    @Override
    public void setOrgViewStopFlag() {
    }

    @Override
    public String getSameCtrlViewId(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        String masterEntitiesKey = formSchemeDefine.getMasterEntitiesKey();
        if (StringUtils.isNotEmpty((String)masterEntitiesKey)) {
            String originalViewId = masterEntitiesKey.split(";")[0];
            return this.sameCtrlOrgViewDao.getSameCtrlViewIdByOriginalViewId(originalViewId);
        }
        return null;
    }
}

