/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.util.Guid
 */
package com.jiuqi.gcreport.invest.relation.service.impl;

import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.invest.investbill.enums.InvestInfoEnum;
import com.jiuqi.gcreport.invest.relation.dao.InvestRelationDao;
import com.jiuqi.gcreport.invest.relation.service.InvestRelationService;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.util.Guid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvestRelationServiceImpl
implements InvestRelationService {
    @Autowired
    private InvestRelationDao investRelationDao;

    @Override
    public List<Map<String, Object>> getDirectInvest(Map<String, Object> params) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getIndirectInvest(Map<String, Object> params) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getInvestOfTree(Map<String, Object> params) {
        ArrayList<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        String mergeUnit = (String)params.get("mergeUnit");
        String orgType = (String)params.get("orgType");
        String periodStr = (String)params.get("periodStr");
        Boolean showInDirect = (Boolean)params.get("showInDirect");
        if (!showInDirect.booleanValue()) {
            params.put("mergeType", InvestInfoEnum.DIRECT.getCode());
        }
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        params.put("acctPeriod", yp.getPeriod());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = orgTool.getOrgByCode(mergeUnit);
        String baseUnitCode = orgCacheVO.getBaseUnitId();
        if (baseUnitCode != null) {
            params.put("mergeUnit", baseUnitCode);
        }
        List<Map<String, Object>> records = this.investRelationDao.listInvestBill(params);
        InvestBillTool.formatBillContent(records, params, (String)"GC_INVESTBILL");
        List<Map<String, Object>> recordsAll = this.investRelationDao.listBill(params);
        if (GcOrgKindEnum.UNIONORG.getCode() != orgCacheVO.getOrgKind().getCode()) {
            List temp3 = records.stream().map(record -> {
                record.put("ID", Guid.newGuid());
                record.put("ISLEAF", true);
                return record;
            }).collect(Collectors.toList());
            results.addAll(temp3);
            return results;
        }
        List nodes = orgTool.getOrgChildrenTree(mergeUnit);
        block4: for (GcOrgCacheVO gcOrgCacheVO : nodes) {
            switch (gcOrgCacheVO.getOrgKind()) {
                case SINGLE: {
                    Boolean isLeaf = recordsAll.stream().anyMatch(record -> record.get("UNITCODE").equals(gcOrgCacheVO.getCode()));
                    Optional<Map> singleDirectInvest = records.stream().filter(record -> record.get("INVESTEDUNIT_CODE").equals(gcOrgCacheVO.getCode())).findFirst();
                    if (singleDirectInvest.isPresent()) {
                        singleDirectInvest.get().put("ISLEAF", isLeaf == false);
                        results.add(singleDirectInvest.get());
                        continue block4;
                    }
                    HashedMap<String, Object> directChildren = new HashedMap<String, Object>();
                    directChildren.put("ID", Guid.newGuid());
                    directChildren.put("INVESTEDUNIT_ID", gcOrgCacheVO.getCode());
                    directChildren.put("INVESTEDUNIT_CODE", gcOrgCacheVO.getCode());
                    directChildren.put("INVESTEDUNIT", gcOrgCacheVO.getTitle());
                    directChildren.put("ENDEQUITYRATIO", 0);
                    directChildren.put("MERGETYPE", "");
                    directChildren.put("MERGETYPE_CODE", "NONE");
                    directChildren.put("ORGKIND", GcOrgKindEnum.SINGLE.getCode());
                    directChildren.put("ISLEAF", isLeaf == false);
                    results.add(directChildren);
                    continue block4;
                }
                case UNIONORG: {
                    Optional<Map> unionDirectInvest = records.stream().filter(record -> record.get("INVESTEDUNIT_CODE").equals(gcOrgCacheVO.getBaseUnitId())).findFirst();
                    if (unionDirectInvest.isPresent()) {
                        unionDirectInvest.get().put("INVESTEDUNIT_ID", gcOrgCacheVO.getCode());
                        unionDirectInvest.get().put("INVESTEDUNIT_CODE", gcOrgCacheVO.getCode());
                        unionDirectInvest.get().put("INVESTEDUNIT", gcOrgCacheVO.getTitle());
                        unionDirectInvest.get().put("ORGKIND", GcOrgKindEnum.UNIONORG.getCode());
                        unionDirectInvest.get().put("ISLEAF", false);
                        results.add(unionDirectInvest.get());
                        continue block4;
                    }
                    HashedMap<String, Object> directChildren = new HashedMap<String, Object>();
                    directChildren.put("ID", Guid.newGuid());
                    directChildren.put("INVESTEDUNIT_ID", gcOrgCacheVO.getCode());
                    directChildren.put("INVESTEDUNIT_CODE", gcOrgCacheVO.getCode());
                    directChildren.put("INVESTEDUNIT", gcOrgCacheVO.getTitle());
                    directChildren.put("ENDEQUITYRATIO", 0);
                    directChildren.put("MERGETYPE", "");
                    directChildren.put("MERGETYPE_CODE", "NONE");
                    directChildren.put("ORGKIND", GcOrgKindEnum.UNIONORG.getCode());
                    directChildren.put("ISLEAF", false);
                    results.add(directChildren);
                    continue block4;
                }
            }
            HashedMap<String, Object> notInvest = new HashedMap<String, Object>();
            notInvest.put("ID", Guid.newGuid());
            notInvest.put("INVESTEDUNIT_ID", gcOrgCacheVO.getCode());
            notInvest.put("INVESTEDUNIT_CODE", gcOrgCacheVO.getCode());
            notInvest.put("INVESTEDUNIT", gcOrgCacheVO.getTitle());
            notInvest.put("ENDEQUITYRATIO", 0);
            notInvest.put("MERGETYPE", "");
            notInvest.put("MERGETYPE_CODE", "NONE");
            notInvest.put("ORGKIND", GcOrgKindEnum.BASE.getCode());
            notInvest.put("ISLEAF", true);
            results.add(notInvest);
        }
        if (showInDirect.booleanValue()) {
            List temp3 = records.stream().filter(record -> record.get("MERGETYPE_CODE").equals(InvestInfoEnum.INDIRECT.getCode())).map(record -> {
                record.put("ID", Guid.newGuid());
                record.put("ISLEAF", true);
                return record;
            }).collect(Collectors.toList());
            results.addAll(temp3);
        }
        return results;
    }

    @Override
    public Map<String, Object> getInvest(Map<String, Object> params) {
        HashedMap<String, Object> results = new HashedMap<String, Object>();
        String mergeUnit = (String)params.get("mergeUnit");
        String orgType = (String)params.get("orgType");
        String periodStr = (String)params.get("periodStr");
        Boolean showInDirect = (Boolean)params.get("showInDirect");
        if (!showInDirect.booleanValue()) {
            params.put("mergeType", InvestInfoEnum.DIRECT.getCode());
        }
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        params.put("acctPeriod", yp.getPeriod());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = orgTool.getOrgByCode(mergeUnit);
        String baseUnitCode = orgCacheVO.getBaseUnitId();
        if (baseUnitCode != null) {
            params.put("mergeUnit", baseUnitCode);
        }
        List<Map<String, Object>> records = this.investRelationDao.listInvestBill(params);
        InvestBillTool.formatBillContent(records, params, (String)"GC_INVESTBILL");
        List<Map<String, Object>> recordsUp = this.investRelationDao.listInvestedBill(params);
        InvestBillTool.formatBillContent(recordsUp, params, (String)"GC_INVESTBILL");
        results.put("ID", Guid.newGuid());
        results.put("UNITCODE_CODE", mergeUnit);
        results.put("UNITCODE", orgCacheVO.getTitle());
        results.put("children", records);
        results.put("parents", recordsUp);
        return results;
    }
}

