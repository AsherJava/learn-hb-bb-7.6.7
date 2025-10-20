/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO
 *  com.jiuqi.dc.base.impl.assistdim.util.AssistDimUtil
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.bde.bizmodel.impl.dimension.util;

import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import com.jiuqi.bde.bizmodel.impl.dimension.service.AssistExtendDimService;
import com.jiuqi.dc.base.impl.assistdim.util.AssistDimUtil;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BdeAssistDimUtils {
    public static final Set<String> SYS_SPECOL = new HashSet<String>(Arrays.asList("id", "ver", "code", "objectcode", "unitcode", "stopflag", "recoveryflag", "validtime", "invalidtime", "parents", "searchKey", "shortname", "createtime", "createuser", "ordinal", "name", "parentcode"));

    public static List<DimensionVO> listAssistDim() {
        List assistDimDTOS = AssistDimUtil.listPublished();
        AssistExtendDimService assistExtendDimService = (AssistExtendDimService)ApplicationContextRegister.getBean(AssistExtendDimService.class);
        List<AssistExtendDimVO> assistExtendDimVOList = assistExtendDimService.getAllStartAssistExtendDim();
        Map<String, Integer> dimSortMap = assistDimDTOS.stream().collect(Collectors.toMap(DimensionVO::getCode, DimensionVO::getSortOrder));
        Map<String, AssistExtendDimVO> extendDimMap = assistExtendDimService.getAllStartAssistExtendDim().stream().collect(Collectors.toMap(AssistExtendDimVO::getCode, Function.identity()));
        for (AssistExtendDimVO assistExtendDimVO : assistExtendDimVOList) {
            DimensionVO assistDimDTO = new DimensionVO();
            assistDimDTO.setCode(assistExtendDimVO.getCode());
            assistDimDTO.setTitle(assistExtendDimVO.getName());
            assistDimDTOS.add(assistDimDTO);
        }
        return assistDimDTOS.stream().sorted(new DimensionOrder(extendDimMap, dimSortMap)).collect(Collectors.toList());
    }

    private static class DimensionOrder
    implements Comparator<DimensionVO> {
        private Map<String, AssistExtendDimVO> extendDimMap;
        private Map<String, Integer> dimSortMap;

        DimensionOrder(Map<String, AssistExtendDimVO> extendDimMap, Map<String, Integer> dimSortMap) {
            this.extendDimMap = extendDimMap;
            this.dimSortMap = dimSortMap;
        }

        @Override
        public int compare(DimensionVO dimension1, DimensionVO dimension2) {
            return this.getOredr(dimension1) - this.getOredr(dimension2);
        }

        private int getOredr(DimensionVO dimension) {
            if (this.dimSortMap.get(dimension.getCode()) != null) {
                return this.dimSortMap.get(dimension.getCode()) * 100;
            }
            if (this.extendDimMap.get(dimension.getCode()) != null) {
                return this.dimSortMap.get(this.extendDimMap.get(dimension.getCode()).getAssistDimCode()) == null ? 9900 : this.dimSortMap.get(this.extendDimMap.get(dimension.getCode()).getAssistDimCode()) * 100 + 1;
            }
            return 9900;
        }
    }
}

