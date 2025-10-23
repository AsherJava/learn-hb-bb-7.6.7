/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplayScheme
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplaySchemeImpl
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.impl.FMDMDisplaySchemeDaoImpl
 */
package com.jiuqi.nr.uselector.module.upgrade;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplayScheme;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplaySchemeImpl;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.impl.FMDMDisplaySchemeDaoImpl;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;

public class CaptionFieldsTableUpGrade2
implements CustomClassExecutor {
    private IRunTimeViewController rtViewCtrl = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
    private FMDMDisplaySchemeDaoImpl captionFieldsDao = (FMDMDisplaySchemeDaoImpl)SpringBeanUtils.getBean(FMDMDisplaySchemeDaoImpl.class);

    public void execute(DataSource dataSource) {
        List allRows = this.captionFieldsDao.findAllRows();
        if (allRows != null && !allRows.isEmpty()) {
            List updateSchemes = allRows.stream().map(this::updateScheme).collect(Collectors.toList());
            this.captionFieldsDao.batchUpdate(updateSchemes);
        }
    }

    private FMDMDisplaySchemeImpl updateScheme(FMDMDisplayScheme scheme) {
        FormSchemeDefine formScheme = this.rtViewCtrl.getFormScheme(scheme.getFormScheme());
        FMDMDisplaySchemeImpl impl = new FMDMDisplaySchemeImpl();
        impl.setKey(scheme.getKey());
        impl.setFormScheme(scheme.getFormScheme());
        impl.setEntityId(formScheme.getDw());
        impl.setOwner(scheme.getOwner());
        impl.setFields(scheme.getFields());
        return impl;
    }
}

