/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.LamdbaUtils
 *  com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionLogInfoDao
 *  com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionLogInfoEo
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.monitor.api.common.MonitorSceneEnum
 *  com.jiuqi.gcreport.monitor.api.common.MonitorStateEnum
 *  com.jiuqi.gcreport.monitor.api.inf.MonitorArgument
 *  com.jiuqi.gcreport.monitor.api.inf.MonitorScene
 *  com.jiuqi.gcreport.monitor.api.inf.RouterRedirect
 */
package com.jiuqi.gcreport.monitor.impl.scene;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.LamdbaUtils;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionLogInfoDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionLogInfoEo;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
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
public class ConversionScene
implements MonitorScene {
    public MonitorSceneEnum getMonitorScene() {
        return MonitorSceneEnum.NODE_CONVERSION;
    }

    public MonitorStateEnum getState(MonitorArgument argument) {
        Random random = new Random();
        if (random.nextBoolean()) {
            return MonitorStateEnum.UPLOAD_IS;
        }
        return MonitorStateEnum.UPLOAD_NOT;
    }

    public Map<String, MonitorStateEnum> getStates(MonitorArgument argument) {
        if (argument.getOrgIds().isEmpty()) {
            return new HashMap<String, MonitorStateEnum>(16);
        }
        String sql = " select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_CONV_LOG", (String)"scheme") + " from " + "GC_CONV_LOG" + "  scheme \n  where 1=1 \n  and scheme.schemeId = ? \n  and scheme.taskId = ? \n  and scheme.periodStr = ? \n  and scheme.adjust = ? \n";
        String adjustCode = StringUtils.isEmpty((String)argument.getAdjustCode()) ? "0" : argument.getAdjustCode();
        ConversionLogInfoDao conversionLogInfoDao = (ConversionLogInfoDao)SpringContextUtils.getBean(ConversionLogInfoDao.class);
        List conversionLogInfoEos = conversionLogInfoDao.selectEntity(sql, new Object[]{argument.getSchemeId(), argument.getTaskId(), argument.getPeriodStr(), adjustCode});
        Map<String, ConversionLogInfoEo> conversionLogInfoEoMap = conversionLogInfoEos.stream().filter(LamdbaUtils.distinctByKey(ConversionLogInfoEo::getUnitId)).collect(Collectors.toMap(ConversionLogInfoEo::getUnitId, eo -> eo));
        try {
            Map<String, MonitorStateEnum> stateEnumMap = argument.getOrgIds().stream().collect(Collectors.toMap(org1 -> org1, o -> {
                ConversionLogInfoEo conversionLogInfoEo = (ConversionLogInfoEo)conversionLogInfoEoMap.get(o);
                return null == conversionLogInfoEo ? MonitorStateEnum.CONVERSION_NOT_IS : MonitorStateEnum.CONVERSION_IS;
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
        return 1;
    }
}

