/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.event.GcCalcCheckBeforeExecuteInitOffsetItemEvent
 *  com.jiuqi.gcreport.carryover.dao.CarryOverLogDao
 *  com.jiuqi.gcreport.carryover.entity.CarryOverLogEO
 *  com.jiuqi.gcreport.carryover.enums.CarryOverTypeEnum
 *  com.jiuqi.gcreport.offsetitem.init.entity.GcOffsetInitLogInfoEO
 *  com.jiuqi.gcreport.offsetitem.init.service.impl.GcCalcOffsetInitLogServiceImpl
 */
package com.jiuqi.gcreport.calculate.listener;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.common.GcCalcLogOperateEnum;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.entity.GcCalcLogEO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.event.GcCalcCheckBeforeExecuteInitOffsetItemEvent;
import com.jiuqi.gcreport.calculate.service.GcCalcLogService;
import com.jiuqi.gcreport.carryover.dao.CarryOverLogDao;
import com.jiuqi.gcreport.carryover.entity.CarryOverLogEO;
import com.jiuqi.gcreport.carryover.enums.CarryOverTypeEnum;
import com.jiuqi.gcreport.offsetitem.init.entity.GcOffsetInitLogInfoEO;
import com.jiuqi.gcreport.offsetitem.init.service.impl.GcCalcOffsetInitLogServiceImpl;
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=-2147483648)
public class GcCalcBeforeExecuteInitOffsetItemEventListener
implements ApplicationListener<GcCalcCheckBeforeExecuteInitOffsetItemEvent> {
    private Logger logger = LoggerFactory.getLogger(GcCalcBeforeExecuteInitOffsetItemEventListener.class);
    @Autowired
    private CarryOverLogDao carryOverLogDao;
    @Autowired
    private GcCalcLogService calcLogService;
    @Autowired
    private GcCalcOffsetInitLogServiceImpl gcCalcOffsetInitLogService;

    @Override
    public void onApplicationEvent(GcCalcCheckBeforeExecuteInitOffsetItemEvent event) {
        GcCalcEnvContext env = event.getEnv();
        GcCalcArgmentsDTO argmentsDTO = env.getCalcArgments();
        String taskId = argmentsDTO.getTaskId();
        String orgId = argmentsDTO.getOrgId();
        String schemeId = argmentsDTO.getSchemeId();
        String periodStr = argmentsDTO.getPeriodStr();
        String orgType = argmentsDTO.getOrgType();
        String currency = argmentsDTO.getCurrency();
        String selectAdjustCode = StringUtils.isEmpty((String)argmentsDTO.getSelectAdjustCode()) ? "0" : argmentsDTO.getSelectAdjustCode();
        GcCalcLogEO queryCalcLog = this.calcLogService.queryLatestCalcLogEO(GcCalcLogOperateEnum.STATR_CALC, taskId, currency, periodStr, orgType, orgId, selectAdjustCode);
        GcOffsetInitLogInfoEO initLogInfoEO = this.gcCalcOffsetInitLogService.queryCalcLogEO(orgId, argmentsDTO.getAcctYear().toString());
        if (queryCalcLog == null || initLogInfoEO == null || queryCalcLog.getEndtime() == null || initLogInfoEO.getLastModifyTime() == null) {
            return;
        }
        Timestamp calcFinishTime = new Timestamp(queryCalcLog.getEndtime());
        Timestamp initOffsetFinishTime = initLogInfoEO.getLastModifyTime();
        boolean isInitOffsetChanged = initOffsetFinishTime.compareTo(calcFinishTime) == 1;
        CarryOverLogEO gcCarryOverInfoEO = this.carryOverLogDao.queryGcCarryOverInfoEO(taskId, schemeId, argmentsDTO.getAcctYear(), CarryOverTypeEnum.OFFSET.getCode());
        if (!(gcCarryOverInfoEO != null && gcCarryOverInfoEO.getEndTime() != null || isInitOffsetChanged)) {
            throw new BusinessRuntimeException("\u5355\u4f4dId[" + orgId + "],\u65f6\u671f[" + argmentsDTO.getAcctYear() + "],\u4e0e\u4e0a\u6b21\u5408\u5e76\u8ba1\u7b97\u671f\u95f4\u672a\u8fdb\u884c\u62b5\u9500\u521d\u59cb\u7684\u4fee\u6539\uff0c\u8df3\u8fc7\u521d\u59cb\u5316\u89c4\u5219\u5408\u5e76\u8ba1\u7b97\u3002");
        }
        if (!isInitOffsetChanged && gcCarryOverInfoEO.getEndTime().compareTo(calcFinishTime) == -1) {
            throw new BusinessRuntimeException("\u5355\u4f4dId[" + orgId + "],\u65f6\u671f[" + argmentsDTO.getAcctYear() + "],\u4e0e\u4e0a\u6b21\u5408\u5e76\u8ba1\u7b97\u671f\u95f4\u672a\u8fdb\u884c\u62b5\u9500\u521d\u59cb\u7684\u4fee\u6539\uff0c\u8df3\u8fc7\u521d\u59cb\u5316\u89c4\u5219\u5408\u5e76\u8ba1\u7b97\u3002");
        }
    }
}

