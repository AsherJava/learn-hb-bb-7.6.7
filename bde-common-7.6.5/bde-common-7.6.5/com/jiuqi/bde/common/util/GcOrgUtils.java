/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataParam
 *  com.jiuqi.gcreport.organization.impl.bean.OrgDataDO
 *  com.jiuqi.gcreport.organization.impl.service.GcOrgDataService
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.bde.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.common.dto.DimensionValue;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.organization.api.vo.OrgDataParam;
import com.jiuqi.gcreport.organization.impl.bean.OrgDataDO;
import com.jiuqi.gcreport.organization.impl.service.GcOrgDataService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class GcOrgUtils {
    @Autowired
    private GcOrgDataService gcOrgDataServiceAutoWired;
    private static GcOrgDataService gcOrgDataService;

    @PostConstruct
    public void init() {
        gcOrgDataService = this.gcOrgDataServiceAutoWired;
    }

    public static List<String> getChildAndSelfUnitCodes(OrgDataParam orgDataParam) {
        List list = gcOrgDataService.listAllChildrenWithSelf(orgDataParam);
        if (CollectionUtils.isEmpty((Collection)list)) {
            return new ArrayList<String>(Collections.singleton(orgDataParam.getOrgParentCode()));
        }
        ArrayList<String> units = new ArrayList<String>();
        for (OrgDataDO orgEntry : list) {
            units.add(orgEntry.getCode());
        }
        return units;
    }

    public static String unitToStringCommaSeparated(List<String> orgs) {
        return "'" + String.join((CharSequence)"','", orgs) + "'";
    }

    public static String unitToStringCommaSeparated(FetchTaskContext fetchTaskContext) {
        OrgDataParam orgDataParam = new OrgDataParam();
        orgDataParam.setOrgParentCode(fetchTaskContext.getOrgMapping().getReportOrgCode());
        orgDataParam.setAuthType("NONE");
        orgDataParam.setOrgType(fetchTaskContext.getRpUnitType());
        Date date = DateUtils.parse((String)fetchTaskContext.getEndDateStr());
        HashMap dimensionValueMap = StringUtils.isEmpty((String)fetchTaskContext.getDimensionSetStr()) ? new HashMap() : (Map)JsonUtils.readValue((String)fetchTaskContext.getDimensionSetStr(), (TypeReference)new TypeReference<Map<String, DimensionValue>>(){});
        orgDataParam.setOrgVerCode(dimensionValueMap.get("DATATIME") != null ? ((DimensionValue)dimensionValueMap.get("DATATIME")).getValue() : DateUtils.getYearOfDate((Date)date) + "Y" + String.format("00%02d", DateUtils.getDateFieldValue((Date)date, (int)2)));
        return "'" + String.join((CharSequence)"','", GcOrgUtils.getChildAndSelfUnitCodes(orgDataParam)) + "'";
    }
}

