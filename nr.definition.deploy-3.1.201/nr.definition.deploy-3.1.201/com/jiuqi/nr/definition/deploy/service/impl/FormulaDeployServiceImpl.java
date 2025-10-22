/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.common.ParamResourceType
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.dao.DesignFormulaSchemeDefineDao
 *  com.jiuqi.nr.definition.internal.dao.RunTimeFormulaSchemeDefineDao
 */
package com.jiuqi.nr.definition.deploy.service.impl;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.deploy.common.ParamDeployContext;
import com.jiuqi.nr.definition.deploy.common.ParamDeployException;
import com.jiuqi.nr.definition.deploy.common.ParamDeployItem;
import com.jiuqi.nr.definition.deploy.service.impl.AbstractResourceDeployService;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.dao.DesignFormulaSchemeDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormulaSchemeDefineDao;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormulaDeployServiceImpl
extends AbstractResourceDeployService {
    @Autowired
    private DesignFormulaSchemeDefineDao designFormulaSchemeDao;
    @Autowired
    private RunTimeFormulaSchemeDefineDao runTimeFormulaSchemeDao;

    @Override
    public ParamResourceType getType() {
        return ParamResourceType.FORMULA;
    }

    private List<FormulaSchemeDefine> getSchemes(String formSchemeKey) {
        HashMap<String, Object> defines = new HashMap<String, Object>();
        try {
            List formulaSchemeDefines = this.runTimeFormulaSchemeDao.listByFormScheme(formSchemeKey);
            for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
                defines.put(formulaSchemeDefine.getKey(), formulaSchemeDefine);
            }
            List designFormulaSchemeDefines = this.designFormulaSchemeDao.listByFormScheme(formSchemeKey);
            for (DesignFormulaSchemeDefine designFormulaSchemeDefine : designFormulaSchemeDefines) {
                defines.put(designFormulaSchemeDefine.getKey(), designFormulaSchemeDefine);
            }
        }
        catch (Exception e) {
            LOGGER.error("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u67e5\u8be2\u516c\u5f0f\u65b9\u6848\u5f02\u5e38", e);
            throw new ParamDeployException("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u67e5\u8be2\u516c\u5f0f\u65b9\u6848\u5f02\u5e38");
        }
        return defines.values().stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
    }

    @Override
    public void deploy(ParamDeployContext context, String formSchemeKey) {
        List<FormulaSchemeDefine> schemes = this.getSchemes(formSchemeKey);
        for (FormulaSchemeDefine scheme : schemes) {
            LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u53d1\u5e03\u516c\u5f0f\u65b9\u6848[{}]", (Object)scheme.getTitle());
            context.getProgressConsumer().accept(this.getType(), "\u53d1\u5e03\u516c\u5f0f\u65b9\u6848[" + scheme.getTitle() + "]");
            super.deploy(scheme.getKey());
            context.getDeployItems().add(new ParamDeployItem(this.getType(), scheme.getKey()));
        }
    }

    @Override
    public void rollback(ParamDeployContext context, String formSchemeKey) {
        List<FormulaSchemeDefine> schemes = this.getSchemes(formSchemeKey);
        for (FormulaSchemeDefine scheme : schemes) {
            LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u56de\u6eda\u516c\u5f0f\u65b9\u6848[{}]", (Object)scheme.getTitle());
            context.getProgressConsumer().accept(this.getType(), "\u56de\u6eda\u516c\u5f0f\u65b9\u6848[" + scheme.getTitle() + "]");
            super.rollback(scheme.getKey());
            context.getDeployItems().add(new ParamDeployItem(this.getType(), scheme.getKey()));
        }
    }
}

