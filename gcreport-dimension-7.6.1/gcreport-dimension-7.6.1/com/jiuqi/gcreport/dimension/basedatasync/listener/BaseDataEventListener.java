/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$EventType
 *  com.jiuqi.va.event.BaseDataEvent
 */
package com.jiuqi.gcreport.dimension.basedatasync.listener;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.dimension.basedatasync.dao.BaseDataChangeInfoDao;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.event.BaseDataEvent;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component(value="dcBaseDataEventListener")
public class BaseDataEventListener
implements ApplicationListener<BaseDataEvent> {
    @Autowired
    private BaseDataChangeInfoDao baseDataChangeInfoDao;
    @Autowired
    private DimensionService dimensionService;

    @Override
    public void onApplicationEvent(BaseDataEvent baseDataEvent) {
        String code;
        BaseDataDTO baseDataDTO = baseDataEvent.getBaseDataDTO();
        List<DimensionVO> dimensionVOS = this.dimensionService.loadAllDimensions();
        Set baseCodeSet = dimensionVOS.stream().map(DimensionVO::getReferField).filter(referField -> !StringUtils.isEmpty((String)referField)).collect(Collectors.toSet());
        baseCodeSet.add("MD_ACCTSUBJECT");
        baseCodeSet.add("MD_CURRENCY");
        baseCodeSet.add("MD_CFITEM");
        if (baseCodeSet.contains(baseDataDTO.getTableName()) && !StringUtils.isEmpty((String)(code = BaseDataOption.EventType.DELETE.equals((Object)baseDataEvent.getEventType()) ? baseDataDTO.getObjectcode() : (BaseDataOption.EventType.UPDATE.equals((Object)baseDataEvent.getEventType()) ? Optional.ofNullable(baseDataDTO.getCode()).orElse(baseDataEvent.getBaseDataOldDTO().getCode()) : baseDataDTO.getCode())))) {
            this.baseDataChangeInfoDao.insertBaseDataChangeInfo(code, baseDataDTO.getTableName(), baseDataEvent.getEventType().name(), DateUtils.now());
        }
    }
}

