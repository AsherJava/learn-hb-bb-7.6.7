/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.filterTemplate.util;

import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDO;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO;
import com.jiuqi.nr.filterTemplate.web.vo.FilterTemplateVO;
import java.util.ArrayList;
import java.util.List;

public class ObjConvert {
    public List<FilterTemplateDTO> FilterTemplateDO2DTO(List<FilterTemplateDO> ftDOs) {
        ArrayList<FilterTemplateDTO> ftDTOs = new ArrayList<FilterTemplateDTO>();
        for (FilterTemplateDO ftDO : ftDOs) {
            FilterTemplateDTO ftVO = new FilterTemplateDTO(ftDO);
            ftDTOs.add(ftVO);
        }
        return ftDTOs;
    }

    public List<FilterTemplateDO> FilterTemplateDTO2DO(List<FilterTemplateDTO> ftDTOs) {
        ArrayList<FilterTemplateDO> ftVOs = new ArrayList<FilterTemplateDO>();
        for (FilterTemplateDTO ftDTO : ftDTOs) {
            FilterTemplateDO ftVO = new FilterTemplateDO(ftDTO);
            ftVOs.add(ftVO);
        }
        return ftVOs;
    }

    public List<FilterTemplateVO> FilterTemplateDTO2VO(List<FilterTemplateDTO> ftDTOs) {
        ArrayList<FilterTemplateVO> ftVOs = new ArrayList<FilterTemplateVO>();
        for (FilterTemplateDTO ftDTO : ftDTOs) {
            FilterTemplateVO ftVO = new FilterTemplateVO(ftDTO);
            ftVOs.add(ftVO);
        }
        return ftVOs;
    }
}

