/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.web.transUtil;

import com.jiuqi.nr.datascheme.fix.core.DeployFailFixHelper;
import com.jiuqi.nr.datascheme.fix.core.DeployFixParamDTO;
import com.jiuqi.nr.datascheme.fix.core.DeployFixResultDTO;
import com.jiuqi.nr.datascheme.fix.web.facade.FixParamVO;
import com.jiuqi.nr.datascheme.fix.web.facade.FixResultVO;
import java.util.ArrayList;
import java.util.List;

public class TransUtil {
    public static List<FixResultVO> resultDTO2VO(List<DeployFixResultDTO> fixResults) {
        ArrayList<FixResultVO> fixResultVOS = new ArrayList<FixResultVO>();
        for (DeployFixResultDTO fixResult : fixResults) {
            FixResultVO fixResultvo = new FixResultVO(fixResult);
            fixResultVOS.add(fixResultvo);
        }
        return fixResultVOS;
    }

    public static List<DeployFixParamDTO> paramVO2DTO(List<FixParamVO> fixParamVOS) {
        ArrayList<DeployFixParamDTO> fixParamDTOS = new ArrayList<DeployFixParamDTO>();
        for (FixParamVO fixParamVO : fixParamVOS) {
            DeployFixParamDTO FixParamDTO = new DeployFixParamDTO(fixParamVO, DeployFailFixHelper.getFixType(fixParamVO.getFixParamValue()));
            fixParamDTOS.add(FixParamDTO);
        }
        return fixParamDTOS;
    }
}

