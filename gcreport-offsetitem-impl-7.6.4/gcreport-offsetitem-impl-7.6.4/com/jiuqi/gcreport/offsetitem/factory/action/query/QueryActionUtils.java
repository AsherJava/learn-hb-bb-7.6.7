/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAdjustExecutor
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.va.organization.service.join.OrgDataClientImpl
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.offsetitem.factory.action.query;

import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAdjustExecutor;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType;
import com.jiuqi.gcreport.offsetitem.service.GcUnOffsetSelectOptionService;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.va.organization.service.join.OrgDataClientImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryActionUtils {
    @Autowired
    private GcUnOffsetSelectOptionService gcUnOffsetSelectOptionService;
    @Resource
    GcOffSetItemAdjustExecutor gcOffSetItemAdjustExecutor;
    @Autowired
    OrgDataClientImpl orgDataClient;
    private final Logger logger = LoggerFactory.getLogger(QueryActionUtils.class);
    public static final String UNITID = "UNITID";
    public static final String OPPUNITID = "OPPUNITID";
    public static final String UNITTREE_SORT = "1";

    public boolean isUnitTreeSort(String showTypeCode, String dataSource, String pageCode) {
        List configList = (List)this.gcUnOffsetSelectOptionService.listSelectOptionForTab(dataSource).get(0).get("value");
        List gcOffsetItemShowTypes = this.gcOffSetItemAdjustExecutor.listShowTypesForCondition(pageCode, dataSource);
        for (GcOffsetItemShowType gcOffsetItemShowType : gcOffsetItemShowTypes) {
            if (!gcOffsetItemShowType.getCode().equals(showTypeCode)) continue;
            for (Map configMap : configList) {
                if (!gcOffsetItemShowType.getCode().equals(configMap.get("value"))) continue;
                return UNITTREE_SORT.equals(configMap.get("isUnitTreeSort"));
            }
        }
        return false;
    }

    public List<Map<String, Object>> sortListByUnitTree(List<Map<String, Object>> resultList, QueryParamsVO queryParamsVO) {
        try {
            Map<String, Integer> orgCode2IndexMap = this.getOrgCode2IndexMap(queryParamsVO);
            resultList.sort((o1, o2) -> {
                if (orgCode2IndexMap.get(o1.get(UNITID)) != null && orgCode2IndexMap.get(o1.get(OPPUNITID)) != null) {
                    int io2;
                    int io1 = Math.min((Integer)orgCode2IndexMap.get(o1.get(UNITID)), (Integer)orgCode2IndexMap.get(o1.get(OPPUNITID)));
                    if (io1 == (io2 = Math.min((Integer)orgCode2IndexMap.get(o2.get(UNITID)), (Integer)orgCode2IndexMap.get(o2.get(OPPUNITID))))) {
                        io1 = Math.max((Integer)orgCode2IndexMap.get(o1.get(UNITID)), (Integer)orgCode2IndexMap.get(o1.get(OPPUNITID)));
                        io2 = Math.max((Integer)orgCode2IndexMap.get(o2.get(UNITID)), (Integer)orgCode2IndexMap.get(o2.get(OPPUNITID)));
                    }
                    if (io1 != -1) {
                        io1 = orgCode2IndexMap.size() - io1;
                    }
                    if (io2 != -1) {
                        io2 = orgCode2IndexMap.size() - io2;
                    }
                    return io2 - io1;
                }
                return orgCode2IndexMap.get(o1.get(UNITID)) != null ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            });
        }
        catch (Exception e) {
            this.logger.error("\u6309\u5355\u4f4d\u6392\u5e8f\u5f02\u5e38\uff1a", e);
        }
        return resultList;
    }

    public Map<String, Integer> getOrgCode2IndexMap(QueryParamsVO queryParamsVO) {
        YearPeriodDO period = YearPeriodUtil.transform(null, (String)queryParamsVO.getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)period);
        List gcOrgCacheVOList = orgTool.listAllOrgByParentIdContainsSelf(queryParamsVO.getOrgId());
        List orgCodes = gcOrgCacheVOList.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList());
        HashMap<String, Integer> orgCode2IndexMap = new HashMap<String, Integer>();
        for (int i = 0; i < orgCodes.size(); ++i) {
            orgCode2IndexMap.put((String)orgCodes.get(i), i);
        }
        return orgCode2IndexMap;
    }
}

