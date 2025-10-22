/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bi.publicparam.datasource.period;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/query-publicParam"})
@Api(value="\u516c\u5171\u53c2\u6570\uff1a\u65f6\u671f")
public class NrPeriodController {
    @Autowired
    IPeriodEntityAdapter period;

    @GetMapping(value={"/custom-period-entity"})
    @ApiOperation(value="\u83b7\u53d6\u81ea\u5b9a\u4e49\u65f6\u671f\u7684\u65f6\u671f\u4e3b\u4f53")
    public List<Map<String, String>> getCustomPeriodEntity() {
        ArrayList<Map<String, String>> res = new ArrayList<Map<String, String>>();
        List periodEntities = this.period.getPeriodEntityByPeriodType(PeriodType.CUSTOM);
        for (IPeriodEntity pd : periodEntities) {
            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put("value", pd.getKey());
            temp.put("label", pd.getTitle());
            res.add(temp);
        }
        return res;
    }

    @GetMapping(value={"/month-period-entity"})
    @ApiOperation(value="\u83b7\u53d6\u6708\u62a5\u65f6\u671f\u7684\u65f6\u671f\u4e3b\u4f53")
    public List<Map<String, String>> getMonthPeriodEntity() {
        ArrayList<Map<String, String>> res = new ArrayList<Map<String, String>>();
        List periodEntities = this.period.getPeriodEntityByPeriodType(PeriodType.MONTH);
        for (IPeriodEntity pd : periodEntities) {
            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put("value", pd.getKey());
            temp.put("label", pd.getTitle());
            res.add(temp);
        }
        return res;
    }
}

