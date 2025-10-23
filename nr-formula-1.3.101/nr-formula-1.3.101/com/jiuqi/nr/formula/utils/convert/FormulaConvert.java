/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition
 *  com.jiuqi.nr.definition.internal.impl.DesignFormulaDefineImpl
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 *  com.jiuqi.nr.task.api.common.DateUtil
 *  com.jiuqi.nr.task.api.resource.state.ResourceState
 */
package com.jiuqi.nr.formula.utils.convert;

import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaDefineImpl;
import com.jiuqi.nr.formula.dto.FormulaConditionDTO;
import com.jiuqi.nr.formula.dto.FormulaDTO;
import com.jiuqi.nr.formula.utils.BeanCopyUtils;
import com.jiuqi.nr.formula.utils.HashConvertMap;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.api.common.DateUtil;
import com.jiuqi.nr.task.api.resource.state.ResourceState;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.util.StringUtils;

public class FormulaConvert {
    public static FormulaDTO defineToDTO(DesignFormulaDefine define) {
        FormulaDTO formulaDTO = new FormulaDTO();
        BeanCopyUtils.copyProperties((Object)formulaDTO, (Object)define, new HashConvertMap(){
            {
                this.put("level", "ownerLevelAndId");
                this.put("formKey", "formKey", (KK x) -> {
                    if (StringUtils.hasLength((String)x)) {
                        return x;
                    }
                    return "BJ";
                });
            }
        });
        formulaDTO.setPrivate(define.getIsPrivate());
        return formulaDTO;
    }

    public static List<FormulaDTO> defineToDTOList(List<DesignFormulaDefine> defines) {
        ArrayList<FormulaDTO> formulaDTOlist = new ArrayList<FormulaDTO>();
        for (DesignFormulaDefine designFormulaDefine : defines) {
            formulaDTOlist.add(FormulaConvert.defineToDTO(designFormulaDefine));
        }
        return formulaDTOlist;
    }

    public static DesignFormulaDefine dtoToDefine(FormulaDTO formula, IDesignTimeFormulaController formulaDesignTimeController) {
        return FormulaConvert.dtoToDefine(formula, ResourceState.NEW, formulaDesignTimeController);
    }

    public static DesignFormulaDefine dtoToDefine(FormulaDTO formula, ResourceState state, IDesignTimeFormulaController formulaDesignTimeController) {
        Object formulaDefine = formulaDesignTimeController == null ? new DesignFormulaDefineImpl() : formulaDesignTimeController.initFormula();
        if (state == ResourceState.NEW) {
            BeanCopyUtils.copyProperties(formulaDefine, formula);
        } else if (state == ResourceState.DIRTY) {
            BeanCopyUtils.copyProperties(formulaDefine, (Object)formula, new HashConvertMap(){
                {
                    this.put("ownerLevelAndId", "level");
                }
            }, "private", "updateTime");
        }
        formulaDefine.setUpdateTime(new Date());
        return formulaDefine;
    }

    public static DesignFormulaDefine[] dtoToDefineList(List<FormulaDTO> list, ResourceState state, IDesignTimeFormulaController formulaDesignTimeController) {
        DesignFormulaDefine[] res = new DesignFormulaDefine[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            res[i] = FormulaConvert.dtoToDefine(list.get(i), state, formulaDesignTimeController);
        }
        return res;
    }

    public static DesignFormulaDefine[] dtoToDefineList(List<FormulaDTO> formulas, IDesignTimeFormulaController formulaDesignTimeController) {
        return FormulaConvert.dtoToDefineList(formulas, ResourceState.NEW, formulaDesignTimeController);
    }

    public static FormulaConditionDTO convertCondition(DesignFormulaCondition condition) {
        if (condition == null) {
            return null;
        }
        FormulaConditionDTO formulaConditionDTO = new FormulaConditionDTO();
        formulaConditionDTO.setKey(condition.getKey());
        formulaConditionDTO.setCode(condition.getCode());
        formulaConditionDTO.setTitle(condition.getTitle());
        formulaConditionDTO.setCondition(condition.getFormulaCondition());
        formulaConditionDTO.setTaskKey(condition.getTaskKey());
        formulaConditionDTO.setStatus(Constants.DataStatus.NONE);
        formulaConditionDTO.setUpdateTime(((SimpleDateFormat)DateUtil.sdf_time.get()).format(condition.getUpdateTime()));
        return formulaConditionDTO;
    }
}

