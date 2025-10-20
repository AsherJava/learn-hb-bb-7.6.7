/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.carryover.vo.CarryOverOrgInfo
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.carryover.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.carryover.entity.CarryOverLogEO;
import com.jiuqi.gcreport.carryover.vo.CarryOverOrgInfo;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;

public class CarryOverLogUtil {
    public static CarryOverLogEO initCarryOverLogEO(GcOrgCacheVO org, QueryParamsVO queryParamsVO) {
        CarryOverLogEO eo = new CarryOverLogEO();
        BeanUtils.copyProperties(queryParamsVO, (Object)eo);
        eo.setProcess(0.0);
        eo.setCreateTime(new Date());
        eo.setId(UUIDUtils.newUUIDStr());
        eo.setGroupId(queryParamsVO.getTaskLogId());
        eo.setAcctYear(queryParamsVO.getAcctYear() - 1);
        eo.setTaskState(TaskStateEnum.WAITTING.getCode());
        eo.setCreator(NpContextHolder.getContext().getUserName());
        CarryOverOrgInfo info = new CarryOverOrgInfo();
        info.setCode(org.getCode());
        info.setTitle(org.getTitle());
        eo.setUnitInfo(JsonUtils.writeValueAsString(Collections.singletonList(info)));
        if (!StringUtils.isEmpty((String)queryParamsVO.getConsSystemId())) {
            eo.setTargetSystemId(queryParamsVO.getConsSystemId());
        }
        return eo;
    }

    public static List<CarryOverOrgInfo> getOrgInfo(CarryOverLogEO eo) {
        return (List)JsonUtils.readValue((String)eo.getUnitInfo(), (TypeReference)new TypeReference<List<CarryOverOrgInfo>>(){});
    }
}

