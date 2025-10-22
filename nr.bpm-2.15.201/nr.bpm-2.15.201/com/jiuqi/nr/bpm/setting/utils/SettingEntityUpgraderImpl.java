/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.bpm.setting.utils;

import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SettingEntityUpgraderImpl
implements IEntityUpgrader {
    private static final Logger logger = LoggerFactory.getLogger(SettingEntityUpgraderImpl.class);
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    IEntityMetaService entityMetaService;
    @Autowired
    IPeriodEntityAdapter periodEntityAdapter;

    public EntityViewDefine getEntityViewDefine(String formSchemeKey) {
        try {
            return this.runTimeViewController.getViewByFormSchemeKey(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean isFristEntity(String entityKey) {
        try {
            if (!this.periodEntityAdapter.isPeriodEntity(entityKey)) {
                return true;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    public boolean isPeriod(String entityKey) {
        try {
            if (this.periodEntityAdapter.isPeriodEntity(entityKey)) {
                return true;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    public String getBizDimensionName(String entityKey) {
        try {
            return this.entityMetaService.getDimensionName(entityKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public String getTableName(String entityKey) {
        try {
            TableModelDefine tableModel = this.entityMetaService.getTableModel(entityKey);
            return tableModel.getName();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

