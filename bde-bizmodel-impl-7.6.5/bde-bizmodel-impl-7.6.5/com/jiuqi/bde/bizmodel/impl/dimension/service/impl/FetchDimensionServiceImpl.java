/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.intf.IBizDataModel
 *  com.jiuqi.bde.bizmodel.define.gather.IBizModelGather
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 */
package com.jiuqi.bde.bizmodel.impl.dimension.service.impl;

import com.jiuqi.bde.bizmodel.client.intf.IBizDataModel;
import com.jiuqi.bde.bizmodel.define.gather.IBizModelGather;
import com.jiuqi.bde.bizmodel.impl.dimension.service.AssistExtendDimService;
import com.jiuqi.bde.bizmodel.impl.dimension.service.FetchDimensionService;
import com.jiuqi.bde.bizmodel.impl.dimension.util.BdeAssistDimUtils;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FetchDimensionServiceImpl
implements FetchDimensionService {
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    protected IBizModelGather modelGather;
    @Autowired
    private AssistExtendDimService assistExtendDimService;

    @Override
    public List<DimensionVO> listDimension() {
        return this.dimensionService.loadAllDimensions();
    }

    @Override
    public List<DimensionVO> listDimensionByDataModel(String dataModelStr) {
        IBizDataModel dataModel = this.modelGather.getBizDataModel(dataModelStr);
        List dimensionList = this.dimensionService.findAllDimFieldsVOByTableName(dataModel.getEffectScope());
        return dimensionList;
    }

    @Override
    public List<DimensionVO> listAllDimensionByDataModel(String dataModelStr) {
        IBizDataModel dataModel = this.modelGather.getBizDataModel(dataModelStr);
        List dimensionList = this.dimensionService.findAllDimFieldsVOByTableName(dataModel.getEffectScope());
        if (CollectionUtils.isEmpty((Collection)dimensionList)) {
            return CollectionUtils.newArrayList();
        }
        Map<String, DimensionVO> assistDimDTOMap = this.getAssistDimDTOMap(dimensionList);
        ArrayList<DimensionVO> assistDimDTOS = new ArrayList<DimensionVO>(assistDimDTOMap.values());
        assistDimDTOS.addAll(this.getAssociatedAssistExtendDimEOToAssistDimDTO(assistDimDTOMap.keySet()));
        return assistDimDTOS;
    }

    @Override
    public List<DimensionVO> listAllDimension() {
        return BdeAssistDimUtils.listAssistDim();
    }

    @Override
    public List<SelectOptionVO> listAllSelectOptionVO() {
        return this.listAllDimension().stream().sorted(Comparator.comparing(DimensionVO::getCode)).map(item -> new SelectOptionVO(item.getCode(), item.getTitle())).collect(Collectors.toList());
    }

    private List<DimensionVO> getAssociatedAssistExtendDimEOToAssistDimDTO(Set<String> assistDimDTOS) {
        return this.assistExtendDimService.getAllStartAssistExtendDim().stream().filter(item -> assistDimDTOS.contains(item.getAssistDimCode()) || "MD_ACCTSUBJECT".equals(item.getAssistDimCode()) || "MD_ORG".equals(item.getAssistDimCode())).map(item -> {
            DimensionVO assistDimDTO = new DimensionVO();
            assistDimDTO.setCode(item.getCode());
            assistDimDTO.setTitle(item.getName());
            return assistDimDTO;
        }).collect(Collectors.toList());
    }

    private Map<String, DimensionVO> getAssistDimDTOMap(List<DimensionVO> list) {
        HashMap<String, DimensionVO> assistDimDTOMap = new HashMap<String, DimensionVO>();
        if (CollectionUtils.isEmpty(list)) {
            return assistDimDTOMap;
        }
        for (DimensionVO assistDimDTO : list) {
            if (assistDimDTOMap.containsKey(assistDimDTO.getCode())) continue;
            assistDimDTOMap.put(assistDimDTO.getCode(), assistDimDTO);
        }
        return assistDimDTOMap;
    }
}

