/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.dataentry.internal.service.util;

import com.jiuqi.nr.dataentry.bean.PeriodRegion;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchemePeriodHelper {
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;

    public List<PeriodRegion> unSplitPeriod(List<SchemePeriodLinkDefine> list, String entityKey) {
        ArrayList<PeriodRegion> periodRegionList = new ArrayList<PeriodRegion>();
        if (null == list || list.size() == 0) {
            return periodRegionList;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        for (SchemePeriodLinkDefine def : list) {
            if (StringUtils.isEmpty((String)def.getPeriodKey())) {
                PeriodRegion periodRegion = new PeriodRegion();
                periodRegion.setStartPeriod(null);
                periodRegion.setEndPeriod(null);
                periodRegion.setScheme(def.getSchemeKey());
                periodRegionList.add(periodRegion);
                continue;
            }
            map.put(def.getPeriodKey(), def.getPeriodKey());
        }
        List collect = this.periodEngineService.getPeriodAdapter().getPeriodProvider(entityKey).getPeriodItems();
        String scheme = list.get(0).getSchemeKey();
        boolean islx = false;
        PeriodRegion periodRegion = new PeriodRegion();
        for (int i = 0; i < collect.size(); ++i) {
            IPeriodRow row = (IPeriodRow)collect.get(i);
            if (row.getCode().equals(map.get(row.getCode()))) {
                if (islx) {
                    periodRegion.setEndPeriod(row.getCode());
                    continue;
                }
                if (i == collect.size() - 1) {
                    periodRegion = new PeriodRegion();
                    periodRegionList.add(periodRegion);
                    periodRegion.setStartPeriod(row.getCode());
                    periodRegion.setEndPeriod(row.getCode());
                    periodRegion.setScheme(scheme);
                    continue;
                }
                islx = true;
                periodRegion = new PeriodRegion();
                periodRegion.setStartPeriod(row.getCode());
                periodRegion.setScheme(scheme);
                periodRegionList.add(periodRegion);
                continue;
            }
            islx = false;
            if (!StringUtils.isNotEmpty((String)periodRegion.getScheme()) || i == 0) continue;
            periodRegion.setEndPeriod(((IPeriodRow)collect.get(i - 1)).getCode());
            periodRegion = new PeriodRegion();
        }
        return periodRegionList;
    }

    public IPeriodEntity schemeSearchEntity(FormSchemeDefine formscheme) {
        String dateTime = this.iRunTimeViewController.queryTaskDefine(formscheme.getTaskKey()).getDateTime();
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        IPeriodEntity iPeriod = periodAdapter.getPeriodEntity(dateTime);
        return iPeriod;
    }
}

