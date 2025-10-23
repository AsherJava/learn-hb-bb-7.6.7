/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormulaVariDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFormulaVariableDefineImpl
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.formula.utils.convert;

import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaVariableDefineImpl;
import com.jiuqi.nr.formula.dto.VariableDTO;
import com.jiuqi.nr.formula.utils.BeanCopyUtils;
import com.jiuqi.nr.formula.utils.HashConvertMap;
import com.jiuqi.nr.formula.web.vo.VariableVO;
import com.jiuqi.util.OrderGenerator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;

public class VariableConvert {
    public static List<VariableVO> dtoToVoList(List<VariableDTO> list) {
        ArrayList<VariableVO> res = new ArrayList<VariableVO>(list.size());
        for (VariableDTO variableDTO : list) {
            res.add(VariableConvert.dtoToVo(variableDTO));
        }
        return res;
    }

    private static VariableVO dtoToVo(VariableDTO variableDTO) {
        VariableVO variableVO = new VariableVO();
        BeanCopyUtils.copyProperties(variableVO, variableDTO);
        return variableVO;
    }

    public static FormulaVariDefine dtoToDefine(VariableDTO value) {
        DesignFormulaVariableDefineImpl define = new DesignFormulaVariableDefineImpl();
        BeanCopyUtils.copyProperties((Object)define, (Object)value, new HashConvertMap(){
            {
                this.put("ownerLevelAndId", "level");
            }
        });
        if (!StringUtils.hasText(define.getOrder())) {
            define.setOrder(OrderGenerator.newOrder());
        }
        return define;
    }

    public static List<VariableDTO> defineToDtoList(List<FormulaVariDefine> defines) {
        ArrayList<VariableDTO> res = new ArrayList<VariableDTO>(defines.size());
        for (FormulaVariDefine define : defines) {
            res.add(VariableConvert.defineToDto(define));
        }
        return res;
    }

    private static VariableDTO defineToDto(FormulaVariDefine define) {
        VariableDTO variableDTO = new VariableDTO();
        BeanCopyUtils.copyProperties((Object)variableDTO, (Object)define, new HashConvertMap(){
            {
                this.put("level", "ownerLevelAndId");
                this.put("initValue", "initValue");
            }
        });
        return variableDTO;
    }
}

