/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.enums.BooleanValEnum
 *  com.jiuqi.dc.datamapping.client.dto.DataRefAutoMatchDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.mappingscheme.client.common.DataRefUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  org.apache.commons.collections4.MapUtils
 */
package com.jiuqi.dc.datamapping.impl.service.impl.automatch;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.enums.BooleanValEnum;
import com.jiuqi.dc.datamapping.client.dto.DataRefAutoMatchDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.impl.service.impl.automatch.AbstractRefServiceImpl;
import com.jiuqi.dc.mappingscheme.client.common.DataRefUtil;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

@Service
public class CodeNameRefServiceImpl
extends AbstractRefServiceImpl {
    @Override
    public String getCode() {
        return "CODE|NAME";
    }

    @Override
    public String getName() {
        return "\u4ee3\u7801\u540d\u79f0\u5339\u914d";
    }

    @Override
    public List<DataRefDTO> autoMatch(BaseDataMappingDefineDTO define, List<DataRefDTO> unRefData, List<Map<String, Object>> baseDataList, DataRefAutoMatchDTO dto) {
        LinkedList refList = CollectionUtils.newLinkedList();
        String lowerCodeMatchDim = "code";
        String lowerNameMatchDim = "name";
        Map<String, Map> dimMap = baseDataList.stream().filter(e -> Objects.nonNull(e.get(lowerCodeMatchDim))).collect(Collectors.toMap(e -> MapUtils.getString((Map)e, (Object)lowerCodeMatchDim) + "|" + MapUtils.getString((Map)e, (Object)lowerNameMatchDim), e -> e, (k1, k2) -> k2));
        for (DataRefDTO dataDto : unRefData) {
            Map baseDataDO;
            if (Boolean.TRUE.equals(dataDto.getMatched()) || !Objects.nonNull(baseDataDO = dimMap.get(dataDto.getValueStr(DataRefUtil.getOdsPrefixName((String)"CODE")) + "|" + dataDto.getValueStr(DataRefUtil.getOdsPrefixName((String)"NAME"))))) continue;
            dataDto.setCode(MapUtils.getString((Map)baseDataDO, (Object)"code"));
            dataDto.setAutoMatchFlag(BooleanValEnum.YES.getCode());
            dataDto.setMatched(true);
            refList.add(dataDto);
        }
        return refList;
    }
}

