/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool
 *  com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.nr.bpm.upload.UploadState
 */
package com.jiuqi.gcreport.onekeymerge.util;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeService;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.nr.bpm.upload.UploadState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OrgUtils {
    public static List<GcOrgCacheVO> getAllHbUnitSortByParentsLengthAsc(GcActionParamsVO gcActionParamsVO) {
        ArrayList<GcOrgCacheVO> result = new ArrayList<GcOrgCacheVO>();
        if (gcActionParamsVO.getMergeType().equals((Object)MergeTypeEnum.ALL_LEVEL)) {
            List<GcOrgCacheVO> tree = OrgUtils.getChildrenTreeByParentId(gcActionParamsVO);
            OrgUtils.getHb(tree, result);
        } else {
            GcOrgCacheVO currentUnit = OrgUtils.getCurrentUnit(gcActionParamsVO);
            result.add(currentUnit);
        }
        List<GcOrgCacheVO> sortedOrgs = result.stream().sorted((o1, o2) -> o2.getParents().length - o1.getParents().length).collect(Collectors.toList());
        return sortedOrgs;
    }

    public static List<GcOrgCacheVO> getAllHbUnitParent(GcActionParamsVO gcActionParamsVO) {
        ArrayList<GcOrgCacheVO> result = new ArrayList<GcOrgCacheVO>();
        OrgUtils.getHb(OrgUtils.getChildrenTreeByParentId(gcActionParamsVO), result);
        return result;
    }

    public static List<GcOrgCacheVO> getUnUploadUnit(GcActionParamsVO gcActionParamsVO) {
        List<GcOrgCacheVO> tree = OrgUtils.getChildrenTreeByParentId(gcActionParamsVO);
        GcOnekeyMergeService tool = (GcOnekeyMergeService)SpringContextUtils.getBean(GcOnekeyMergeService.class);
        ArrayList<GcOrgCacheVO> unUploadUnits = new ArrayList<GcOrgCacheVO>();
        OrgUtils.filterUnit(tree, unUploadUnits, tool, gcActionParamsVO);
        return unUploadUnits;
    }

    public static Map<String, UploadState> getAllOrgUploadStates(GcActionParamsVO gcActionParamsTemp, List<GcOrgCacheVO> orgs, Set<String> allOrgIds) {
        for (GcOrgCacheVO org : orgs) {
            gcActionParamsTemp.setOrgId(org.getId());
            List<GcOrgCacheVO> gcOrgCacheVOS = OrgUtils.listAllOrgByParentIdContainsSelf(gcActionParamsTemp);
            allOrgIds.addAll(gcOrgCacheVOS.stream().map(GcOrgCacheVO::getId).collect(Collectors.toSet()));
        }
        return UploadStateTool.getInstance().getUploadSates((Object)gcActionParamsTemp, new ArrayList<String>(allOrgIds));
    }

    private static void filterUnit(List<GcOrgCacheVO> tree, List<GcOrgCacheVO> uploadUnits, GcOnekeyMergeService tool, GcActionParamsVO paramsVO) {
        for (GcOrgCacheVO gcOrgCacheVO : tree) {
            ReturnObject ret;
            if (CollectionUtils.isEmpty((Collection)gcOrgCacheVO.getChildren()) || (ret = tool.checkUploadState(paramsVO, gcOrgCacheVO.getId())).isSuccess()) continue;
            uploadUnits.add(gcOrgCacheVO);
            OrgUtils.filterUnit(gcOrgCacheVO.getChildren(), uploadUnits, tool, paramsVO);
        }
    }

    public static List<GcOrgCacheVO> getChildrenTreeByParentId(GcActionParamsVO gcActionParamsVO) {
        return OrgUtils.getChildrenTree(gcActionParamsVO.getOrgType(), gcActionParamsVO.getPeriodStr(), gcActionParamsVO.getOrgId());
    }

    public static GcOrgCacheVO getCurrentUnit(GcActionParamsVO gcActionParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, gcActionParamsVO.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)gcActionParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        return tool.getOrgByCode(gcActionParamsVO.getOrgId());
    }

    public static List<GcOrgCacheVO> getOrgsByCodes(GcActionParamsVO gcActionParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, gcActionParamsVO.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)gcActionParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        ArrayList<GcOrgCacheVO> mergeOrgs = new ArrayList<GcOrgCacheVO>();
        for (String id : gcActionParamsVO.getOrgIds()) {
            GcOrgCacheVO org = tool.getOrgByCode(id);
            mergeOrgs.add(org);
        }
        return mergeOrgs;
    }

    public static GcOrgCacheVO getOrgByCode(String periodStr, String orgType, String orgId) {
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        return tool.getOrgByCode(orgId);
    }

    public static GcOrgCacheVO getParentOrg(GcActionParamsVO gcActionParamsVO, String orgId) {
        YearPeriodObject yp = new YearPeriodObject(null, gcActionParamsVO.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)gcActionParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        List parentsOrg = tool.getParentOrg(orgId);
        if (parentsOrg.size() <= 1) {
            return null;
        }
        return CollectionUtils.isEmpty((Collection)parentsOrg) ? null : (GcOrgCacheVO)parentsOrg.get(parentsOrg.size() - 2);
    }

    public static List<GcOrgCacheVO> listAllOrgByParentIdContainsSelf(GcActionParamsVO gcActionParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, gcActionParamsVO.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)gcActionParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        return tool.listAllOrgByParentIdContainsSelf(gcActionParamsVO.getOrgId());
    }

    public static GcOrgCacheVO getCurrentUnit(String orgType, String periodStr, String orgId) {
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        return tool.getOrgByCode(orgId);
    }

    public static List<GcOrgCacheVO> getAllLeafUnitByParent(List<GcOrgCacheVO> tree) {
        ArrayList<GcOrgCacheVO> result = new ArrayList<GcOrgCacheVO>();
        OrgUtils.getLeaf(tree, result);
        return result;
    }

    public static List<GcOrgCacheVO> getDirectLeafUnitByParent(List<GcOrgCacheVO> tree) {
        ArrayList<GcOrgCacheVO> result = new ArrayList<GcOrgCacheVO>();
        if (CollectionUtils.isEmpty(tree)) {
            return new ArrayList<GcOrgCacheVO>();
        }
        tree.get(0).getChildren().forEach(orgToJsonVO -> {
            if (orgToJsonVO.getChildren().size() == 0) {
                result.add((GcOrgCacheVO)orgToJsonVO);
            }
        });
        return result;
    }

    public static List<GcOrgCacheVO> getAllLeafUnitByParent(GcActionParamsVO gcActionParamsVO) {
        List<GcOrgCacheVO> tree = OrgUtils.getChildrenTree(gcActionParamsVO.getOrgType(), gcActionParamsVO.getPeriodStr(), gcActionParamsVO.getOrgId());
        ArrayList<GcOrgCacheVO> result = new ArrayList<GcOrgCacheVO>();
        OrgUtils.getLeaf(tree, result);
        return result;
    }

    private static List<GcOrgCacheVO> getChildrenTree(String orgType, String periodStr, String parentId) {
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        return tool.getOrgTree(parentId);
    }

    private static void getLeaf(List<GcOrgCacheVO> children, List<GcOrgCacheVO> result) {
        children.forEach(orgToJsonVO -> {
            if (orgToJsonVO.getChildren().size() > 0) {
                OrgUtils.getLeaf(orgToJsonVO.getChildren(), result);
            } else {
                result.add((GcOrgCacheVO)orgToJsonVO);
            }
        });
    }

    private static void getHb(List<GcOrgCacheVO> children, List<GcOrgCacheVO> result) {
        children.forEach(orgToJsonVO -> {
            if (orgToJsonVO.getChildren().size() > 0) {
                result.add((GcOrgCacheVO)orgToJsonVO);
                OrgUtils.getHb(orgToJsonVO.getChildren(), result);
            }
        });
    }
}

