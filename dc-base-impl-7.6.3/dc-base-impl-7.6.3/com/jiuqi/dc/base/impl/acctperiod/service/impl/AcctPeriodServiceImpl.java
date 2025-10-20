/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodCondiVO
 *  com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodDefineVO
 */
package com.jiuqi.dc.base.impl.acctperiod.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodCondiVO;
import com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodDefineVO;
import com.jiuqi.dc.base.impl.acctperiod.service.AcctPeriodService;
import com.jiuqi.dc.base.impl.onlinePeriod.data.OnlinePeriod;
import com.jiuqi.dc.base.impl.onlinePeriod.service.OnlinePeriodDefineService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcctPeriodServiceImpl
implements AcctPeriodService {
    @Autowired
    private OnlinePeriodDefineService onlinePeriodDefineService;

    @Override
    public List<Integer> listYear() {
        Integer minYear = this.onlinePeriodDefineService.getMinPeriodYear();
        ArrayList<Integer> startYearList = new ArrayList<Integer>();
        Calendar calendar = Calendar.getInstance();
        if (minYear != null) {
            while (minYear <= calendar.get(1)) {
                startYearList.add(minYear);
                Integer n = minYear;
                Integer n2 = minYear = Integer.valueOf(minYear + 1);
            }
        } else {
            startYearList.add(calendar.get(1));
        }
        return startYearList;
    }

    @Override
    public OnlinePeriod getOnlinePeriod(String moduleCode, String unitCode) {
        List<OnlinePeriodDefineVO> onlinePeriodDefineVOList = this.onlinePeriodDefineService.getAllTableData(new OnlinePeriodCondiVO(moduleCode, unitCode));
        if (onlinePeriodDefineVOList == null || onlinePeriodDefineVOList.size() == 0) {
            return null;
        }
        if (onlinePeriodDefineVOList.size() > 1) {
            throw new BusinessRuntimeException("\u67e5\u627e\u5230\u4e24\u4e2a\u5bf9\u5e94\u7684\u4e0a\u7ebf\u671f\u95f4\uff0c\u8bf7\u5ba1\u6838\u6570\u636e");
        }
        OnlinePeriod onlinePeriod = new OnlinePeriod();
        onlinePeriod.setStartYear(Integer.valueOf(onlinePeriodDefineVOList.get(0).getOnlinePeriod().substring(0, 4)));
        onlinePeriod.setStartPeriod(Integer.valueOf(onlinePeriodDefineVOList.get(0).getOnlinePeriod().substring(5, 7)));
        return onlinePeriod;
    }
}

