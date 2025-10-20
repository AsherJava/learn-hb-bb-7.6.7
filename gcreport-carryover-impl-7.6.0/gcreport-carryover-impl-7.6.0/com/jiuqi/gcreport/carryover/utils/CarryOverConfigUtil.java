/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.carryover.enums.CarryOverTypeEnum
 *  com.jiuqi.gcreport.carryover.vo.CarryOverConfigOptionBaseVO
 *  com.jiuqi.gcreport.carryover.vo.CarryOverConfigVO
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.carryover.utils;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.carryover.entity.CarryOverConfigEO;
import com.jiuqi.gcreport.carryover.enums.CarryOverTypeEnum;
import com.jiuqi.gcreport.carryover.vo.CarryOverConfigOptionBaseVO;
import com.jiuqi.gcreport.carryover.vo.CarryOverConfigVO;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Date;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

public class CarryOverConfigUtil {
    public static void initConfigVO(CarryOverConfigVO vo) {
        vo.setCreator(NpContextHolder.getContext().getUserName());
        vo.setCreateTime(new Date());
        vo.setOrdinal(new Double(OrderGenerator.newOrderID()));
        vo.setParentId(UUIDUtils.emptyUUIDStr());
    }

    public static CarryOverConfigEO convertConfigV02EO(CarryOverConfigVO vo) {
        CarryOverConfigEO eo = new CarryOverConfigEO();
        BeanUtils.copyProperties(vo, (Object)eo);
        eo.setLeafFlag(vo.getLeafFlag() != false ? 1 : 0);
        eo.setId(UUIDUtils.newUUIDStr());
        return eo;
    }

    public static CarryOverConfigOptionBaseVO convertConfigEO2OptionVO(CarryOverConfigEO eo) {
        CarryOverConfigOptionBaseVO vo = (CarryOverConfigOptionBaseVO)JsonUtils.readValue((String)eo.getOptionData(), CarryOverConfigOptionBaseVO.class);
        return vo;
    }

    public static CarryOverConfigVO convertConfigEO2VO(CarryOverConfigEO eo) {
        CarryOverConfigVO vo = new CarryOverConfigVO();
        BeanUtils.copyProperties((Object)eo, vo);
        vo.setLeafFlag(Boolean.valueOf(eo.getLeafFlag() == 1));
        CarryOverTypeEnum carryOverType = CarryOverTypeEnum.getEnumByCode((String)eo.getTypeCode());
        if (ObjectUtils.isEmpty(carryOverType)) {
            vo.setTypeTitle(carryOverType.getTitle());
            vo.setPluginName(carryOverType.getPluginName());
        }
        if (CarryOverTypeEnum.OFFSET.getCode().equals(eo.getTypeCode())) {
            CarryOverConfigOptionBaseVO optionVO = CarryOverConfigUtil.convertConfigEO2OptionVO(eo);
            vo.setBoundSystemId(optionVO.getBoundSystemId());
        }
        vo.setTypeTitle(carryOverType.getTitle());
        vo.setPluginName(carryOverType.getPluginName());
        return vo;
    }
}

