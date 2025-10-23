/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.period.select.common;

import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.select.vo.PeriodRangeData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PeriodRangeUtil<T extends SchemePeriodLinkDefine> {
    public List<PeriodRangeData> unSplitPeriod(List<T> list, List<IPeriodRow> collect) {
        ArrayList<PeriodRangeData> objs = new ArrayList<PeriodRangeData>();
        if (null == list || list.size() == 0) {
            return objs;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        for (SchemePeriodLinkDefine def : list) {
            if (StringUtils.isEmpty((String)def.getPeriodKey())) {
                PeriodRangeData objn = new PeriodRangeData();
                objn.setFormScheme(def.getSchemeKey());
                objn.setStart(null);
                objn.setEnd(null);
                objs.add(objn);
                continue;
            }
            map.put(def.getPeriodKey(), def.getPeriodKey());
        }
        String scheme = ((SchemePeriodLinkDefine)list.get(0)).getSchemeKey();
        boolean islx = false;
        PeriodRangeData obj = new PeriodRangeData();
        for (int i = 0; i < collect.size(); ++i) {
            IPeriodRow row = collect.get(i);
            if (row.getCode().equals(map.get(row.getCode()))) {
                if (islx) {
                    obj.setEnd(row.getCode());
                    obj.setEndTime(null != row.getEndDate() ? Long.valueOf(row.getEndDate().getTime()) : null);
                    continue;
                }
                if (i == collect.size() - 1) {
                    obj = new PeriodRangeData();
                    objs.add(obj);
                    obj.setFormScheme(scheme);
                    obj.setStart(row.getCode());
                    obj.setStartTime(null != row.getStartDate() ? Long.valueOf(row.getStartDate().getTime()) : null);
                    obj.setEnd(row.getCode());
                    obj.setEndTime(null != row.getEndDate() ? Long.valueOf(row.getEndDate().getTime()) : null);
                    continue;
                }
                islx = true;
                obj = new PeriodRangeData();
                objs.add(obj);
                obj.setFormScheme(scheme);
                obj.setStart(row.getCode());
                obj.setStartTime(null != row.getStartDate() ? Long.valueOf(row.getStartDate().getTime()) : null);
                continue;
            }
            islx = false;
            if (!StringUtils.isNotEmpty((String)obj.getFormScheme()) || i == 0) continue;
            obj.setEnd(collect.get(i - 1).getCode());
            obj.setEndTime(null != row.getEndDate() ? Long.valueOf(row.getEndDate().getTime()) : null);
            obj = new PeriodRangeData();
        }
        return objs;
    }
}

