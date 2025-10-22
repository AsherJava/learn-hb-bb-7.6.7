/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billcore.common.BillPublishServiceImpl
 *  com.jiuqi.gcreport.dimension.dto.DimTableRelDTO
 *  com.jiuqi.gcreport.dimension.dto.DimensionDTO
 *  com.jiuqi.gcreport.dimension.internal.service.DimensionCustomPublishService
 *  com.jiuqi.gcreport.dimension.vo.DimensionPublishInfoVO
 *  com.jiuqi.gcreport.dimension.vo.EffectTableVO
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.asset.assetbill.service.impl;

import com.jiuqi.gcreport.asset.assetbill.service.impl.AssetDimensionEffectScope;
import com.jiuqi.gcreport.billcore.common.BillPublishServiceImpl;
import com.jiuqi.gcreport.dimension.dto.DimTableRelDTO;
import com.jiuqi.gcreport.dimension.dto.DimensionDTO;
import com.jiuqi.gcreport.dimension.internal.service.DimensionCustomPublishService;
import com.jiuqi.gcreport.dimension.vo.DimensionPublishInfoVO;
import com.jiuqi.gcreport.dimension.vo.EffectTableVO;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetDimensionPublishServiceImpl
implements DimensionCustomPublishService {
    @Autowired
    private AssetDimensionEffectScope assetDimensionEffectScope;
    @Autowired
    private BillPublishServiceImpl billPublishService;

    public List<String> getTableNames() {
        List<EffectTableVO> effectTableVO = this.assetDimensionEffectScope.getEffectTableVO();
        return effectTableVO.stream().map(EffectTableVO::getTableName).collect(Collectors.toList());
    }

    public void publish(String tableName, DimensionDTO dimensionDTO) {
        TableModelDefine tableModelDefine = this.billPublishService.checkDesignAndRunTimeDiff(tableName, dimensionDTO.getCode());
        this.billPublishService.publish(tableModelDefine, dimensionDTO);
    }

    public DimensionPublishInfoVO publishCheck(DimTableRelDTO dimTableRelDTO, DimensionDTO dimensionDTO) {
        return this.billPublishService.publishCheck(dimTableRelDTO, dimensionDTO);
    }

    public DimensionPublishInfoVO publishCheckUnPublished(DimTableRelDTO dimTableRelDTO, DimensionDTO dimensionDTO) {
        return this.billPublishService.publishCheckUnPublished(dimTableRelDTO, dimensionDTO);
    }
}

