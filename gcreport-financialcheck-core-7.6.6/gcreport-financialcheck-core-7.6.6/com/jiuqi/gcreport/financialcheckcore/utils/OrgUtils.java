/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool
 */
package com.jiuqi.gcreport.financialcheckcore.utils;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class OrgUtils {
    private static final String GROUP_ORG_ID = null;

    public static Set<String> getAllLevelsChildrenAndSelf(List<String> orgCodes, GcOrgCenterService tool) {
        HashSet<String> containChildrenOrgIds = new HashSet<String>();
        for (String orgCode : orgCodes) {
            List list = tool.listAllOrgByParentIdContainsSelf(orgCode);
            for (GcOrgCacheVO org : list) {
                containChildrenOrgIds.add(org.getCode());
            }
        }
        return new HashSet<String>(containChildrenOrgIds);
    }

    public static Set<String> getAllLevelsChildrenAndSelf(List<String> orgCodes, String dateStr, GcAuthorityType authorityType, String orgType) {
        HashSet<String> containChildrenOrgIds = new HashSet<String>();
        for (String orgCode : orgCodes) {
            containChildrenOrgIds.addAll(OrgUtils.getAllLevelsChildrenAndSelf(orgCode, dateStr, authorityType, orgType));
        }
        return new HashSet<String>(containChildrenOrgIds);
    }

    public static List<String> getAllLevelsChildrenAndSelf(String orgId, String dateStr, GcAuthorityType authorityType, String orgType) {
        if (orgId == null) {
            return Collections.emptyList();
        }
        YearPeriodObject yp = new YearPeriodObject(null, dateStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)authorityType, (YearPeriodObject)yp);
        List list = tool.listAllOrgByParentIdContainsSelf(orgId);
        ArrayList<String> orgIds = new ArrayList<String>();
        for (GcOrgCacheVO org : list) {
            orgIds.add(org.getId());
        }
        return orgIds;
    }

    public static String getOrgNameByOrgId(String orgId, String dateStr, String orgType) {
        YearPeriodObject yp = new YearPeriodObject(null, dateStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO org = tool.getOrgByCode(orgId);
        return org == null ? "" : org.getTitle();
    }

    public static boolean isGruop(String orgId, String dateStr, String orgType) {
        if (StringUtils.isNull((String)orgId)) {
            return true;
        }
        YearPeriodObject yp = new YearPeriodObject(null, dateStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        List oneLevelOrgs = tool.getOrgChildrenTree(GROUP_ORG_ID);
        if (CollectionUtils.isEmpty(oneLevelOrgs)) {
            return true;
        }
        return oneLevelOrgs.size() == 1 && ((GcOrgCacheVO)oneLevelOrgs.get(0)).getId().equals(orgId);
    }

    public static List<String> getAllUnitId(String dateStr, GcAuthorityType authorityType, String orgType) {
        ArrayList<String> ids = new ArrayList<String>();
        YearPeriodObject yp = new YearPeriodObject(null, dateStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)authorityType, (YearPeriodObject)yp);
        tool.listAllOrgByParentIdContainsSelf(null).stream().forEach(org -> ids.add(org.getId()));
        return ids;
    }

    public static List<String> getUnitCodeOnlyParent(List<String> orgCodes, GcOrgCenterService centerService) {
        ArrayList removedCodes = new ArrayList();
        ArrayList<String> finalOrgCodes = new ArrayList<String>(orgCodes);
        orgCodes.forEach(orgCode -> {
            if (removedCodes.contains(orgCode)) {
                return;
            }
            List childrenAndSelf = centerService.listAllOrgByParentIdContainsSelf(orgCode);
            ArrayList thisTimeNeedRemovedCodes = new ArrayList();
            childrenAndSelf.forEach(org -> {
                if (!org.getCode().equals(orgCode) && orgCodes.contains(org.getCode())) {
                    thisTimeNeedRemovedCodes.add(org.getCode());
                }
            });
            removedCodes.addAll(thisTimeNeedRemovedCodes);
            finalOrgCodes.removeAll(thisTimeNeedRemovedCodes);
        });
        return finalOrgCodes;
    }

    public static List<String> getBaseUnitCodeOnlyParent(List<String> orgCodes) {
        ArrayList removedCodes = new ArrayList();
        ArrayList<String> finalOrgCodes = new ArrayList<String>(orgCodes);
        orgCodes.forEach(orgCode -> {
            if (removedCodes.contains(orgCode)) {
                return;
            }
            List orgToJsonVOS = GcOrgBaseTool.getInstance().listAllSubordinates(orgCode);
            ArrayList thisTimeNeedRemovedCodes = new ArrayList();
            orgToJsonVOS.forEach(org -> {
                if (!org.getCode().equals(orgCode) && orgCodes.contains(org.getCode())) {
                    thisTimeNeedRemovedCodes.add(org.getCode());
                }
            });
            removedCodes.addAll(thisTimeNeedRemovedCodes);
            finalOrgCodes.removeAll(thisTimeNeedRemovedCodes);
        });
        return finalOrgCodes;
    }
}

