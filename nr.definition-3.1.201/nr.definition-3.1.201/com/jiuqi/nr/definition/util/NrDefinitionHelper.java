/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.dao.DesignFormSchemeDefineDao;
import com.jiuqi.nr.definition.internal.dao.DesignFormulaSchemeDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormSchemeDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormulaSchemeDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeTaskDefineDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrDefinitionHelper {
    @Autowired
    private RunTimeTaskDefineDao runTimeTaskDefineDao;
    @Autowired
    private DesignFormSchemeDefineDao designFormSchemeDefineDao;
    @Autowired
    private RunTimeFormSchemeDefineDao runTimeFormSchemeDefineDao;
    @Autowired
    private DesignFormulaSchemeDefineDao designFormulaSchemeDefineDao;
    @Autowired
    private RunTimeFormulaSchemeDefineDao runTimeFormulaSchemeDefineDao;
    private static final String LOCK_PREFIX = "nr_param_cache_service_";

    private FormSchemeDefine getFormSchemeDefine(String schemeKey) {
        FormSchemeDefine formSchemeDefine = this.designFormSchemeDefineDao.getDefineByKey(schemeKey);
        if (null == formSchemeDefine) {
            formSchemeDefine = this.runTimeFormSchemeDefineDao.getDefineByKey(schemeKey);
        }
        return formSchemeDefine;
    }

    private FormulaSchemeDefine getFormulaSchemeDefine(String schemeKey) {
        FormulaSchemeDefine formulaSchemeDefine = this.designFormulaSchemeDefineDao.getDefineByKey(schemeKey);
        if (null == formulaSchemeDefine) {
            formulaSchemeDefine = this.runTimeFormulaSchemeDefineDao.getDefineByKey(schemeKey);
        }
        return formulaSchemeDefine;
    }

    public String getLockName(ParamResourceType resourceType, String schemeKey) {
        FormSchemeDefine formSchemeDefine = null;
        switch (resourceType) {
            case FORM: {
                formSchemeDefine = this.getFormSchemeDefine(schemeKey);
                break;
            }
            case FORMULA: {
                FormulaSchemeDefine formulaSchemeDefine = this.getFormulaSchemeDefine(schemeKey);
                if (null == formulaSchemeDefine) break;
                formSchemeDefine = this.getFormSchemeDefine(formulaSchemeDefine.getFormSchemeKey());
                break;
            }
        }
        if (null == formSchemeDefine) {
            return LOCK_PREFIX.concat(schemeKey);
        }
        TaskDefine taskDefine = this.runTimeTaskDefineDao.getDefineByKey(formSchemeDefine.getTaskKey());
        if (null != taskDefine && "1.0".equals(taskDefine.getVersion())) {
            return LOCK_PREFIX.concat(taskDefine.getKey());
        }
        return LOCK_PREFIX.concat(formSchemeDefine.getKey());
    }
}

