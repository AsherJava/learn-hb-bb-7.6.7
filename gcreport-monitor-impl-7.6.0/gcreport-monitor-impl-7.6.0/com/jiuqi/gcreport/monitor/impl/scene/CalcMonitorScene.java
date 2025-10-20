/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.common.GcCalcLogOperateEnum
 *  com.jiuqi.gcreport.calculate.entity.GcCalcLogEO
 *  com.jiuqi.gcreport.calculate.service.impl.GcCalcLogServiceImpl
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.common.util.LamdbaUtils
 *  com.jiuqi.gcreport.monitor.api.common.MonitorSceneEnum
 *  com.jiuqi.gcreport.monitor.api.common.MonitorStateEnum
 *  com.jiuqi.gcreport.monitor.api.inf.MonitorArgument
 *  com.jiuqi.gcreport.monitor.api.inf.MonitorScene
 *  com.jiuqi.gcreport.monitor.api.inf.RouterRedirect
 */
package com.jiuqi.gcreport.monitor.impl.scene;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.common.GcCalcLogOperateEnum;
import com.jiuqi.gcreport.calculate.entity.GcCalcLogEO;
import com.jiuqi.gcreport.calculate.service.impl.GcCalcLogServiceImpl;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.common.util.LamdbaUtils;
import com.jiuqi.gcreport.monitor.api.common.MonitorSceneEnum;
import com.jiuqi.gcreport.monitor.api.common.MonitorStateEnum;
import com.jiuqi.gcreport.monitor.api.inf.MonitorArgument;
import com.jiuqi.gcreport.monitor.api.inf.MonitorScene;
import com.jiuqi.gcreport.monitor.api.inf.RouterRedirect;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CalcMonitorScene
implements MonitorScene {
    public MonitorSceneEnum getMonitorScene() {
        return MonitorSceneEnum.NODE_CALC;
    }

    public MonitorStateEnum getState(MonitorArgument argument) {
        Random random = new Random();
        if (random.nextBoolean()) {
            return MonitorStateEnum.FINISH_NOT_IS;
        }
        return MonitorStateEnum.FINISH_IS;
    }

    public Map<String, MonitorStateEnum> getStates(MonitorArgument argument) {
        if (argument.getOrgIds().isEmpty()) {
            return new HashMap<String, MonitorStateEnum>(16);
        }
        GcCalcLogServiceImpl gcCalcLogService = (GcCalcLogServiceImpl)SpringContextUtils.getBean(GcCalcLogServiceImpl.class);
        List gcCalcLogInfoEOS = gcCalcLogService.queryLatestCalcLogEOs(GcCalcLogOperateEnum.STATR_CALC, argument.getTaskId(), "CNY", argument.getPeriodStr(), argument.getAdjustCode());
        Map<String, GcCalcLogEO> calcLogInfoEOMap = gcCalcLogInfoEOS.stream().filter(LamdbaUtils.distinctByKey(GcCalcLogEO::getOrgId)).filter(eo -> argument.getOrgType().equals(eo.getOrgType())).collect(Collectors.toMap(GcCalcLogEO::getOrgId, eo -> eo));
        try {
            Map<String, MonitorStateEnum> stateEnumMap = argument.getOrgIds().stream().collect(Collectors.toMap(org1 -> org1, o -> {
                GcCalcLogEO gcCalcLogInfoEO = (GcCalcLogEO)calcLogInfoEOMap.get(o);
                if (null == gcCalcLogInfoEO) {
                    return MonitorStateEnum.FINISH_NOT_IS;
                }
                boolean calcState = TaskStateEnum.SUCCESS.getCode().equals(gcCalcLogInfoEO.getTaskState());
                return calcState ? MonitorStateEnum.FINISH_IS : MonitorStateEnum.FINISH_NOT_IS;
            }));
            return stateEnumMap;
        }
        catch (IllegalStateException e) {
            throw new BusinessRuntimeException("\u5355\u4f4d\u91cd\u590d");
        }
    }

    public RouterRedirect getURL(MonitorArgument argument) {
        return null;
    }

    public int getOrder() {
        return 4;
    }
}

