/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.period.PeriodType
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.period.web;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.common.rest.EnableRange;
import com.jiuqi.nr.period.common.rest.Period13Object;
import com.jiuqi.nr.period.common.rest.PeriodDataSelectObject;
import com.jiuqi.nr.period.common.utils.PeriodException;
import com.jiuqi.nr.period.common.utils.PeriodPropertyGroup;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.service.PeriodDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/nr/period"})
@Api(tags={"\u65f6\u671f\u7ba1\u7406"})
public class IrregularPeriodController {
    @Autowired
    private PeriodDataService periodDateService;
    @Autowired
    private PeriodEngineService periodEngineService;

    @GetMapping(value={"/queryPeriodData/{id}"})
    @ApiOperation(value="\u6839\u636e\u65f6\u671fID\u67e5\u8be2\u65f6\u671f\u6570\u636e")
    public List<PeriodDataSelectObject> queryPeriodDataByPeriodKey(@PathVariable String id) throws JQException {
        if (StringUtils.isEmpty(id)) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_101);
        }
        try {
            IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
            IPeriodProvider periodProvider = periodAdapter.getPeriodProvider(id);
            IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(id);
            PeriodPropertyGroup periodPropertyGroup = periodEntity.getPeriodPropertyGroup();
            List<IPeriodRow> periodItems = periodProvider.getPeriodItems();
            if (periodEntity.getPeriodType().equals((Object)PeriodType.CUSTOM)) {
                return this.periodRowToPeriodDataSelectObject(periodPropertyGroup, periodItems);
            }
            ArrayList<IPeriodRow> titleModifyList = new ArrayList<IPeriodRow>();
            for (IPeriodRow periodItem : periodItems) {
                if (periodItem.getTitle().equals(PeriodUtils.getDateStrFromPeriod(periodItem.getCode()))) continue;
                titleModifyList.add(periodItem);
            }
            return titleModifyList.stream().map(PeriodDataSelectObject::defineToObject).collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_106, (Throwable)e);
        }
    }

    @GetMapping(value={"/queryPeriodDataTitle/{id}"})
    @ApiOperation(value="\u6839\u636e\u65f6\u671fID\u67e5\u8be2\u65f6\u671f\u6570\u636e\u6807\u9898\u96c6\u5408")
    @RequiresPermissions(value={"nr:period_data:query"})
    public Map<String, String> queryPeriodDataTitleByPeriodKey(@PathVariable String id) throws JQException {
        if (StringUtils.isEmpty(id)) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_101);
        }
        LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
        try {
            IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
            IPeriodProvider periodProvider = periodAdapter.getPeriodProvider(id);
            List<IPeriodRow> periodItems = periodProvider.getPeriodItems();
            periodItems.sort((o1, o2) -> o1.getCode().compareTo(o2.getCode()));
            for (IPeriodRow periodItem : periodItems) {
                titleMap.put(periodItem.getCode(), periodItem.getTitle());
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_106, (Throwable)e);
        }
        return titleMap;
    }

    private List<PeriodDataSelectObject> periodRowToPeriodDataSelectObject(PeriodPropertyGroup periodPropertyGroup, List<IPeriodRow> periodItems) {
        List<Object> periodDataSelectObjects = new ArrayList();
        if (periodPropertyGroup == null) {
            periodDataSelectObjects = periodItems.stream().map(PeriodDataSelectObject::defineToObject).collect(Collectors.toList());
        } else {
            switch (periodPropertyGroup) {
                case PERIOD_GROUP_BY_YEAR: {
                    periodDataSelectObjects = periodItems.stream().map(a -> {
                        PeriodDataSelectObject periodDataSelectObject = PeriodDataSelectObject.defineToObject(a);
                        periodDataSelectObject.setGroup(a.getYear() + PeriodPropertyGroup.PERIOD_GROUP_BY_YEAR.getGroupName());
                        return periodDataSelectObject;
                    }).collect(Collectors.toList());
                    break;
                }
                default: {
                    periodDataSelectObjects = periodItems.stream().map(PeriodDataSelectObject::defineToObject).collect(Collectors.toList());
                }
            }
        }
        return periodDataSelectObjects;
    }

    @GetMapping(value={"/queryPeriod13Data/{id}"})
    @ApiOperation(value="\u6839\u636e\u65f6\u671fID\u67e5\u8be213\u671f\u6570\u636e")
    public Period13Object queryPeriod13Data(@PathVariable String id) throws JQException {
        if (StringUtils.isEmpty(id)) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_101);
        }
        try {
            Period13Object object = new Period13Object();
            IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
            IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(id);
            if (periodEntity.getPeriodType().equals((Object)PeriodType.MONTH) && PeriodUtils.isPeriod13(periodEntity.getCode(), periodEntity.getPeriodType())) {
                if (periodEntity.getMinFiscalMonth() == 0) {
                    object.getShowPeriod13().add(0);
                } else {
                    object.getShowPeriod13().add(-1);
                }
                if (periodEntity.getMaxFiscalMonth() > 12) {
                    object.getShowPeriod13().add(periodEntity.getMaxFiscalMonth());
                } else {
                    object.getShowPeriod13().add(-1);
                }
                List<IPeriodRow> periodItems = periodAdapter.getPeriodProvider(periodEntity.getCode()).getPeriodItems();
                if (null != periodItems && !periodItems.isEmpty()) {
                    EnableRange enableRange = new EnableRange();
                    enableRange.setBegin(periodItems.get(0).getCode());
                    enableRange.setEnd(periodItems.get(periodItems.size() - 1).getCode());
                    object.getEnableRange().add(enableRange);
                }
                return object;
            }
            return null;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_106, (Throwable)e);
        }
    }
}

