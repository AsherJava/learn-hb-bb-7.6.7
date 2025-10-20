/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.definition.internal.controller2;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.api.IDesignTimeExtFormulaController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum2;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaDefineImpl;
import com.jiuqi.nr.definition.internal.service.DesignExtFormulaDefineService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.util.OrderGenerator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DesignTimeExtFormulaController
implements IDesignTimeExtFormulaController {
    private static final Logger logger = LoggerFactory.getLogger(DesignTimeExtFormulaController.class);
    @Autowired
    private DesignExtFormulaDefineService designExtFormulaDefineService;
    @Autowired
    private INvwaSystemOptionService systemOptionService;

    @Override
    public DesignFormulaDefine initExtFormula() {
        DesignFormulaDefineImpl designFormulaDefine = new DesignFormulaDefineImpl();
        designFormulaDefine.setKey(UUIDUtils.getKey());
        designFormulaDefine.setIsPrivate(true);
        designFormulaDefine.setOrder(OrderGenerator.newOrder());
        designFormulaDefine.setUpdateTime(new Date());
        return designFormulaDefine;
    }

    @Override
    public void insertExtFormula(DesignFormulaDefine[] designFormulaDefines) {
        try {
            this.designExtFormulaDefineService.insertFormulaDefines(designFormulaDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updateExtFormula(DesignFormulaDefine[] designFormulaDefines) {
        try {
            this.designExtFormulaDefineService.updateFormulaDefines(designFormulaDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_UPDATE, (Throwable)e);
        }
    }

    @Override
    public void deleteExtFormula(String[] keys) {
        try {
            this.designExtFormulaDefineService.delete(keys);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_DELETE, (Throwable)e);
        }
    }

    @Override
    public void deleteExtFormulaByFormulaScheme(String formulaScheme) {
        try {
            this.designExtFormulaDefineService.deleteByScheme(formulaScheme);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_DELETE, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormulaDefine> getFormulaByCodeAndSchemeAndForm(String formulaCode, String formKey, String formulaScheme) {
        ArrayList<DesignFormulaDefine> defines = new ArrayList<DesignFormulaDefine>();
        try {
            List<DesignFormulaDefine> formulaDefines = this.designExtFormulaDefineService.queryFormulaDefineBySchemeAndCodes(formulaCode, formulaScheme);
            if (null != formulaDefines && formulaDefines.size() > 0) {
                for (DesignFormulaDefine formulaDefine : formulaDefines) {
                    if (formulaDefine.getFormKey() == null) {
                        if (formulaDefine.getFormKey() == formKey) continue;
                        defines.add(formulaDefine);
                        continue;
                    }
                    if (formulaDefine.getFormKey().equals(formKey)) continue;
                    defines.add(formulaDefine);
                }
            }
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_QUERY, (Throwable)e);
        }
        return defines;
    }

    @Override
    public List<DesignFormulaDefine> listFormulaByScheme(String formulaScheme) {
        try {
            return this.designExtFormulaDefineService.listFormulaByFormulaScheme(formulaScheme);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormulaDefine> listAllFormulaDefine() {
        try {
            return this.designExtFormulaDefineService.queryAllFormulaDefine();
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormulaDefine> listFormulaBySchemeAndFormAndEntity(String formulaScheme, String formKey, String unit) {
        try {
            return this.designExtFormulaDefineService.getFormulaByUnit(formulaScheme, formKey, unit);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormulaDefine> listFormulaBySchemeIgnorePrivate(String formulaScheme) {
        try {
            return this.designExtFormulaDefineService.searchFormulaIgnorePrivate(formulaScheme);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormulaDefine> listFormulaBySchemeAndForm(String formulaScheme, String formKey) {
        try {
            return this.designExtFormulaDefineService.queryFormulaDefineBySchemeAndForm(formulaScheme, formKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_QUERY, (Throwable)e);
        }
    }

    @Override
    public boolean getExistPrivateFormula() {
        boolean isOpen = false;
        String enable = this.systemOptionService.get("PRIVATE_FORMULA", "PRIVATE_FORMULA_VALUE");
        if (StringUtils.isNotEmpty((String)enable) && "1".equals(enable)) {
            isOpen = true;
        }
        return isOpen;
    }
}

