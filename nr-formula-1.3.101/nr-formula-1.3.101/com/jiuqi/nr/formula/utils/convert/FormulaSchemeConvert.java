/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFormulaSchemeDefineImpl
 */
package com.jiuqi.nr.formula.utils.convert;

import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaSchemeDefineImpl;
import com.jiuqi.nr.formula.dto.FormulaSchemeDTO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FormulaSchemeConvert {
    public static DesignFormulaSchemeDefine dtoToDefine(FormulaSchemeDTO formulaSchemeDTO, IDesignTimeFormulaController formulaDesignTimeController) {
        Object schemeDefine = formulaDesignTimeController != null ? formulaDesignTimeController.initFormulaScheme() : new DesignFormulaSchemeDefineImpl();
        schemeDefine.setUpdateTime(formulaSchemeDTO.getUpdateTime());
        schemeDefine.setKey(formulaSchemeDTO.getKey());
        schemeDefine.setOrder(formulaSchemeDTO.getOrder());
        schemeDefine.setOwnerLevelAndId(formulaSchemeDTO.getLevel());
        schemeDefine.setTitle(formulaSchemeDTO.getTitle());
        schemeDefine.setFormSchemeKey(formulaSchemeDTO.getFormSchemeKey());
        schemeDefine.setFormulaSchemeType(formulaSchemeDTO.getFormulaSchemeType());
        schemeDefine.setDisplayMode(formulaSchemeDTO.getDisplayMode());
        schemeDefine.setDefault(Boolean.TRUE.equals(formulaSchemeDTO.getDefaultScheme()));
        schemeDefine.setShow(Boolean.TRUE.equals(formulaSchemeDTO.getShowScheme()));
        schemeDefine.setFormulaSchemeMenuApply(formulaSchemeDTO.getMenuApply());
        return schemeDefine;
    }

    public static DesignFormulaSchemeDefine[] dtoToDefines(List<FormulaSchemeDTO> formulaSchemeDTOList, IDesignTimeFormulaController formulaDesignTimeController) {
        DesignFormulaSchemeDefine[] defines = new DesignFormulaSchemeDefine[formulaSchemeDTOList.size()];
        for (int i = 0; i < formulaSchemeDTOList.size(); ++i) {
            FormulaSchemeDTO formulaSchemeDTO = formulaSchemeDTOList.get(i);
            formulaSchemeDTO.setUpdateTime(new Date());
            defines[i] = FormulaSchemeConvert.dtoToDefine(formulaSchemeDTO, formulaDesignTimeController);
        }
        return defines;
    }

    public static SimpleDateFormat getSimpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static FormulaSchemeDTO defineToDto(DesignFormulaSchemeDefine formulaScheme) {
        FormulaSchemeDTO formulaSchemeDTO = new FormulaSchemeDTO();
        formulaSchemeDTO.setDefaultScheme(formulaScheme.isDefault());
        formulaSchemeDTO.setFormSchemeKey(formulaScheme.getFormSchemeKey());
        formulaSchemeDTO.setLevel(formulaScheme.getOwnerLevelAndId());
        formulaSchemeDTO.setFormulaSchemeType(formulaScheme.getFormulaSchemeType());
        formulaSchemeDTO.setKey(formulaScheme.getKey());
        formulaSchemeDTO.setOrder(formulaScheme.getOrder());
        formulaSchemeDTO.setShowScheme(formulaScheme.isShow());
        formulaSchemeDTO.setTitle(formulaScheme.getTitle());
        formulaSchemeDTO.setUpdateTime(formulaScheme.getUpdateTime());
        formulaSchemeDTO.setDisplayMode(formulaScheme.getDisplayMode());
        formulaSchemeDTO.setMenuApply(formulaScheme.getFormulaSchemeMenuApply());
        return formulaSchemeDTO;
    }

    public static List<FormulaSchemeDTO> defineToDtos(List<DesignFormulaSchemeDefine> designFormulaSchemeDefines) {
        ArrayList<FormulaSchemeDTO> list = new ArrayList<FormulaSchemeDTO>(designFormulaSchemeDefines.size());
        for (DesignFormulaSchemeDefine designFormulaSchemeDefine : designFormulaSchemeDefines) {
            FormulaSchemeDTO formulaSchemeDTO = FormulaSchemeConvert.defineToDto(designFormulaSchemeDefine);
            list.add(formulaSchemeDTO);
        }
        return list;
    }
}

