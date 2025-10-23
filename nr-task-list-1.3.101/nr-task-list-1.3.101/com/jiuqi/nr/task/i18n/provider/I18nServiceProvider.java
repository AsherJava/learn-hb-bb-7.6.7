/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.internal.service.DesignBigDataService
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 *  com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.task.i18n.provider;

import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.internal.service.DesignBigDataService;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.task.i18n.service.I18nTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class I18nServiceProvider {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignTimeFormulaController designTimeFormulaController;
    @Autowired
    private DesParamLanguageDao desParamLanguageDao;
    @Autowired
    private DesignFormDefineService formDefineService;
    @Autowired
    private I18nTreeService treeService;
    @Autowired
    private DesignBigDataService bigDataService;
    @Autowired
    private IEntityMetaService entityMetaService;

    public IDesignTimeViewController getDesignTimeViewController() {
        return this.designTimeViewController;
    }

    public IDesignTimeFormulaController getDesignTimeFormulaController() {
        return this.designTimeFormulaController;
    }

    public DesParamLanguageDao getDesParamLanguageDao() {
        return this.desParamLanguageDao;
    }

    public DesignFormDefineService getFormDefineService() {
        return this.formDefineService;
    }

    public I18nTreeService getTreeService() {
        return this.treeService;
    }

    public DesignBigDataService getBigDataService() {
        return this.bigDataService;
    }

    public IEntityMetaService getEntityMetaService() {
        return this.entityMetaService;
    }
}

