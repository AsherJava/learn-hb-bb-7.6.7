/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.DTO.ManagementDim
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.event.HyperDimensionPublishedEvent
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.np.cache.NedisCacheManager
 */
package com.jiuqi.gcreport.consolidatedsystem.service.impl;

import com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.consolidatedsystem.DTO.ManagementDim;
import com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskCacheService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.definition.impl.basic.init.table.event.HyperDimensionPublishedEvent;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.np.cache.NedisCacheManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ManagementDimensionCacheServiceImpl
implements ManagementDimensionCacheService,
ApplicationListener<HyperDimensionPublishedEvent> {
    private NedisCacheManager cacheManger;
    private ConsolidatedOptionService conOptionService;
    private ConsolidatedTaskCacheService conTaskCacheService;
    private DimensionService dimService;
    private static final String OPTIONMANAGEMENT_KEY = "";

    public ManagementDimensionCacheServiceImpl(NedisCacheManager cacheManger, ConsolidatedOptionService conOptionService, ConsolidatedTaskCacheService conTaskCacheService, DimensionService dimService) {
        this.cacheManger = cacheManger;
        this.conOptionService = conOptionService;
        this.conTaskCacheService = conTaskCacheService;
        this.dimService = dimService;
    }

    @Override
    public void onApplicationEvent(HyperDimensionPublishedEvent event) {
        ShowModelDTO showModelDTO = event.getModel();
        if (showModelDTO != null && "GC_OFFSETVCHRITEM".equals(showModelDTO.getCode())) {
            this.cacheManger.getCache("gcreport:dimManagement").clear();
        }
    }

    @Override
    public List<ManagementDim> getManagementDimsBySystemId(String systemId) {
        if (StringUtils.isEmpty(systemId)) {
            return Collections.emptyList();
        }
        ConsolidatedOptionVO consolidatedOption = this.conOptionService.getOptionData(systemId);
        if (consolidatedOption == null || CollectionUtils.isEmpty((Collection)consolidatedOption.getManagementDimension())) {
            return Collections.emptyList();
        }
        HashMap systemDimCode2NullAble = new HashMap(16);
        consolidatedOption.getManagementDimension().forEach(dimId2isRequired -> {
            String[] dimId2isRequiredArr = dimId2isRequired.split(":");
            String dimId = dimId2isRequiredArr[0];
            boolean nullAble = dimId2isRequiredArr.length < 2 || "0".equals(dimId2isRequiredArr[1]);
            systemDimCode2NullAble.put(dimId, nullAble);
        });
        List cacheDims = (List)this.cacheManger.getCache("gcreport:dimManagement").get(systemId, this::valueLoader);
        ArrayList<ManagementDim> resultDims = new ArrayList<ManagementDim>(16);
        for (ManagementDim dim : cacheDims) {
            if (!systemDimCode2NullAble.containsKey(dim.getId())) continue;
            resultDims.add(new ManagementDim(dim.getId(), dim.getCode(), dim.getTitle(), (Boolean)systemDimCode2NullAble.get(dim.getId()), dim.getReferTable()));
        }
        return resultDims;
    }

    @Override
    public List<ManagementDim> getOptionalManagementDims() {
        return (List)this.cacheManger.getCache("gcreport:dimManagement").get(OPTIONMANAGEMENT_KEY, this::valueLoader);
    }

    private List<ManagementDim> valueLoader() {
        return this.dimService.findAllDimFieldsByTableName("GC_OFFSETVCHRITEM").stream().map(dim -> new ManagementDim(dim.getId(), dim.getCode(), dim.getTitle(), Boolean.valueOf(Integer.valueOf(1).equals(dim.getNullAble())), dim.getReferTable())).collect(Collectors.toList());
    }
}

