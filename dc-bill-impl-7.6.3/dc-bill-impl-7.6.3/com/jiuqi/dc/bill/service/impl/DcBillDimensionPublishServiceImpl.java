/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.dimension.dto.DimTableRelDTO
 *  com.jiuqi.gcreport.dimension.dto.DimensionDTO
 *  com.jiuqi.gcreport.dimension.internal.service.DimensionCustomPublishService
 *  com.jiuqi.gcreport.dimension.vo.DimensionPublishInfoVO
 *  com.jiuqi.gcreport.dimension.vo.EffectTableVO
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.dc.bill.service.impl;

import com.jiuqi.dc.bill.service.impl.DcBillPublishServiceImpl;
import com.jiuqi.gcreport.dimension.dto.DimTableRelDTO;
import com.jiuqi.gcreport.dimension.dto.DimensionDTO;
import com.jiuqi.gcreport.dimension.internal.service.DimensionCustomPublishService;
import com.jiuqi.gcreport.dimension.vo.DimensionPublishInfoVO;
import com.jiuqi.gcreport.dimension.vo.EffectTableVO;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DcBillDimensionPublishServiceImpl
implements DimensionCustomPublishService {
    @Autowired
    private DcBillPublishServiceImpl billPublishService;

    public List<String> getTableNames() {
        ArrayList<EffectTableVO> effectTableVOS = new ArrayList<EffectTableVO>();
        effectTableVOS.add(new EffectTableVO("DC_BILL_VOUCHERITEM", "\u5355\u636e\u51ed\u8bc1\u5b50\u8868"));
        effectTableVOS.add(new EffectTableVO("DC_BILL_AGEUNCLEAREDITEM", "\u5355\u636e\u8d26\u9f84\u672a\u6e05\u9879\u5b50\u8868"));
        return effectTableVOS.stream().map(EffectTableVO::getTableName).collect(Collectors.toList());
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

