/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao
 *  com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish
 */
package com.jiuqi.nr.formula.service.impl;

import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao;
import com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish;
import com.jiuqi.nr.formula.exception.FormulaResourceException;
import com.jiuqi.nr.formula.service.IPublishCheckService;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublishCheckServiceImpl
implements IPublishCheckService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignTimeFormulaController formulaDesignTimeController;
    @Autowired
    private TaskPlanPublishDao taskPlanPublishDao;

    private Predicate<String> getFormulaSchemePredicate() {
        return key -> {
            DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.getFormulaScheme(key);
            if (formulaScheme == null) {
                return true;
            }
            return this.getFormSchemePredicate().test(formulaScheme.getFormSchemeKey());
        };
    }

    private Predicate<String> getFormSchemePredicate() {
        return key -> {
            DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(key);
            if (formScheme == null) {
                return true;
            }
            return this.getTaskPredicate().test(formScheme.getTaskKey());
        };
    }

    private Predicate<String> getTaskPredicate() {
        return key -> {
            TaskPlanPublish taskPlanPublish;
            try {
                taskPlanPublish = this.taskPlanPublishDao.queryWorkPlan(key);
            }
            catch (Exception e) {
                throw new FormulaResourceException(e);
            }
            if (taskPlanPublish != null) {
                return !"publishing".equals(taskPlanPublish.getPublishStatus());
            }
            return true;
        };
    }

    @Override
    public boolean canFormulaSchemeEdit(String key) {
        return this.getFormulaSchemePredicate().test(key);
    }

    @Override
    public boolean canFormSchemeEdit(String key) {
        return this.getFormSchemePredicate().test(key);
    }

    @Override
    public boolean canTaskEdit(String key) {
        return this.getTaskPredicate().test(key);
    }
}

