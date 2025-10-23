/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplayScheme
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplaySchemeImpl
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.impl.FMDMDisplaySchemeDaoImpl
 */
package com.jiuqi.nr.uselector.module.upgrade;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplayScheme;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplaySchemeImpl;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.impl.FMDMDisplaySchemeDaoImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;

public class CaptionFieldsTableUpGrade
implements CustomClassExecutor {
    private IRunTimeViewController rtViewCtrl = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
    private FMDMDisplaySchemeDaoImpl captionFieldsDao = (FMDMDisplaySchemeDaoImpl)SpringBeanUtils.getBean(FMDMDisplaySchemeDaoImpl.class);

    public void execute(DataSource dataSource) {
        List allRows = this.captionFieldsDao.findAllRows();
        ArrayList<String> deleteRowKeys = new ArrayList<String>();
        for (FMDMDisplayScheme scheme : allRows) {
            TaskDefine taskDefine = this.rtViewCtrl.queryTaskDefine(scheme.getFormScheme());
            if (taskDefine == null) continue;
            this.batchInsertByTask(scheme, taskDefine);
            deleteRowKeys.add(scheme.getKey());
        }
        if (!deleteRowKeys.isEmpty()) {
            this.captionFieldsDao.batchDelete(deleteRowKeys);
        }
    }

    private void batchInsertByTask(FMDMDisplayScheme scheme, TaskDefine taskDefine) {
        if (taskDefine != null) {
            List formSchemeDefines;
            ArrayList<FMDMDisplaySchemeImpl> updateSchemes = new ArrayList<FMDMDisplaySchemeImpl>();
            try {
                formSchemeDefines = this.rtViewCtrl.queryFormSchemeByTask(taskDefine.getKey());
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                FMDMDisplaySchemeImpl impl = new FMDMDisplaySchemeImpl();
                impl.setKey(UUID.randomUUID().toString());
                impl.setFields(scheme.getFields());
                impl.setOwner(scheme.getOwner());
                impl.setFormScheme(formSchemeDefine.getKey());
                updateSchemes.add(impl);
            }
            this.captionFieldsDao.batchInsert(updateSchemes);
        }
    }
}

