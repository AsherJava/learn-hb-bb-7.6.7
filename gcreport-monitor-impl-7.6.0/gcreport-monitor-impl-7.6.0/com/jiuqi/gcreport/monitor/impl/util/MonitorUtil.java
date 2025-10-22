/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.monitor.api.inf.MonitorArgument
 *  com.jiuqi.gcreport.monitor.api.vo.show.ConditionVO
 */
package com.jiuqi.gcreport.monitor.impl.util;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.monitor.api.inf.MonitorArgument;
import com.jiuqi.gcreport.monitor.api.vo.show.ConditionVO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;

public class MonitorUtil {
    public static String getAllFieldsSQL(Class<? extends DefaultTableEntity> entity, String bm) {
        return SqlUtils.getColumnsSqlByEntity(entity, (String)bm);
    }

    public static Double newOrder() {
        return new Long(OrderGenerator.newOrderID()).doubleValue();
    }

    public static MonitorArgument getMonitorArgsFromVO(ConditionVO vo) {
        MonitorArgument argument = new MonitorArgument();
        BeanUtils.copyProperties(vo, argument);
        return argument;
    }

    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        ArrayList<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;
        int number = source.size() / n;
        int offset = 0;
        for (int i = 0; i < n; ++i) {
            List<T> value = null;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                --remainder;
                ++offset;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }
}

