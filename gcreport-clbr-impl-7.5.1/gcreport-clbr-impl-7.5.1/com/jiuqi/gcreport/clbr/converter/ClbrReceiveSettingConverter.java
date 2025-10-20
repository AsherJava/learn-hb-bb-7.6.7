/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.clbr.dto.ClbrReceiveSettingDTO
 */
package com.jiuqi.gcreport.clbr.converter;

import com.jiuqi.gcreport.clbr.dto.ClbrReceiveSettingDTO;
import com.jiuqi.gcreport.clbr.entity.ClbrReceiveSettingEO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class ClbrReceiveSettingConverter {
    public static List<ClbrReceiveSettingEO> convertDTO2EO(List<ClbrReceiveSettingDTO> dtos) {
        List<ClbrReceiveSettingEO> eos = dtos.stream().map(dto -> {
            ClbrReceiveSettingEO eo = new ClbrReceiveSettingEO();
            BeanUtils.copyProperties(dto, (Object)eo);
            return eo;
        }).collect(Collectors.toList());
        return eos;
    }

    public static List<ClbrReceiveSettingDTO> convertEO2DTO(List<ClbrReceiveSettingEO> eos) {
        List<ClbrReceiveSettingDTO> dtos = eos.stream().map(eo -> {
            ClbrReceiveSettingDTO dto = new ClbrReceiveSettingDTO();
            BeanUtils.copyProperties(eo, dto);
            return dto;
        }).collect(Collectors.toList());
        return dtos;
    }
}

