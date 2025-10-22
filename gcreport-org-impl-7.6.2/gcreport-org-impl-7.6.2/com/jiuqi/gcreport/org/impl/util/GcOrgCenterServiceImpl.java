/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgTypeVerCacheTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  org.apache.commons.lang3.ArrayUtils
 */
package com.jiuqi.gcreport.org.impl.util;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgTypeVerCacheTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.cache.base.GcOrgQueryParam;
import com.jiuqi.gcreport.org.impl.util.base.OrgParamParse;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgModelProvider;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgQueryModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class GcOrgCenterServiceImpl
implements GcOrgCenterService {
    private static final Logger logger = LoggerFactory.getLogger(GcOrgCenterServiceImpl.class);
    private GcOrgQueryModel queryService;
    private GcOrgQueryParam param;

    private GcOrgCenterServiceImpl() {
    }

    protected static GcOrgCenterServiceImpl getInstance(String orgType, YearPeriodDO yp, GcAuthorityType authType) {
        GcOrgCenterServiceImpl tool = new GcOrgCenterServiceImpl();
        try {
            tool.queryService = GcOrgModelProvider.getGcOrgQueryModel();
            tool.param = new GcOrgQueryParam(tool.queryService, orgType, yp.getEndDate(), authType);
            if ("MD_ORG".equals(tool.param.getOrgtypeName())) {
                throw new RuntimeException("\u5f53\u524d\u63a5\u53e3\u65e0\u6cd5\u83b7\u53d6\u57fa\u7840\u7ec4\u7ec7\u7ed3\u6784\u6570\u636e\uff1b\u8bf7\u8c03\u7528\u57fa\u7840\u7ec4\u7ec7\u7ed3\u6784\u76f8\u5173\u63a5\u53e3(/api/gcreport/v1/gcOrganizations/baseOrgTree)\u83b7\u53d6MD_ORG\u6570\u636e\u3002");
            }
        }
        catch (Exception e) {
            logger.error("\u52a0\u8f7d\u7ec4\u7ec7\u673a\u6784\u67e5\u8be2\u5668\u5931\u8d25,\u8bf7\u8054\u7cfb\u7cfb\u7edf\u7ba1\u7406\u5458", e);
            throw new RuntimeException("\u52a0\u8f7d\u7ec4\u7ec7\u673a\u6784\u67e5\u8be2\u5668\u5931\u8d25,\u8bf7\u8054\u7cfb\u7cfb\u7edf\u7ba1\u7406\u5458: caused by " + e.getMessage(), e);
        }
        return tool;
    }

    public GcOrgTypeVerCacheTool getTypeVerInstance() {
        return new GcOrgTypeVerCacheService(GcOrgModelProvider.getGcOrgQueryModel());
    }

    public int getOrgCodeLength() {
        return this.queryService.getQueryService().getOrgCodeLength();
    }

    public List<GcOrgCacheVO> getOrgTree() {
        return this.getOrgTree(null);
    }

    public List<GcOrgCacheVO> getOrgTree(String parentid) {
        try {
            List<GcOrgCacheVO> orgTree = this.queryService.getQueryService().getOrgTree(this.param, parentid);
            if (orgTree != null && orgTree.size() > 0) {
                return orgTree;
            }
            logger.debug(this.param.getLog("getOrgTree(" + parentid + ")\u5b8c\u6574\u7ec4\u7ec7\u673a\u6784\u6811\u4e3a\u7a7a\uff0c\u8fd4\u56de\u7a7a\u96c6\u5408\u3002"));
            return CollectionUtils.newArrayList();
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(this.param.getLog("getOrgTree(" + parentid + ")\u67e5\u8be2\u7ec4\u7ec7\u673a\u6784\u6811\u5931\u8d25,\u8bf7\u8054\u7cfb\u7cfb\u7edf\u7ba1\u7406\u5458"), e);
            throw new RuntimeException(e);
        }
    }

    public List<GcOrgCacheVO> getOrgChildrenTree(String parentid) {
        return this.queryService.getQueryService().listDirectSubordinate(this.param, parentid);
    }

    public List<GcOrgCacheVO> getParentOrg(String code) {
        return this.queryService.getQueryService().listSuperior(this.param, code);
    }

    public Map<String, List<GcOrgCacheVO>> getOrgCode2ParentOrgMap() {
        HashMap<String, List<GcOrgCacheVO>> orgCode2ParentOrgMap = new HashMap<String, List<GcOrgCacheVO>>();
        List<GcOrgCacheVO> orgTree = this.getOrgTree();
        this.getAllParentOrgByOrgCode(orgTree, orgCode2ParentOrgMap, new ArrayList<GcOrgCacheVO>());
        return orgCode2ParentOrgMap;
    }

    public void getAllParentOrgByOrgCode(List<GcOrgCacheVO> orgTree, Map<String, List<GcOrgCacheVO>> orgCode2ParentOrgMap, List<GcOrgCacheVO> parentOrgList) {
        for (GcOrgCacheVO orgCacheVO : orgTree) {
            ArrayList<GcOrgCacheVO> curParentOrgList = new ArrayList<GcOrgCacheVO>(parentOrgList);
            curParentOrgList.add(orgCacheVO);
            orgCode2ParentOrgMap.put(orgCacheVO.getCode(), curParentOrgList);
            this.getAllParentOrgByOrgCode(orgCacheVO.getChildren(), orgCode2ParentOrgMap, curParentOrgList);
        }
    }

    public Map<String, List<String>> getOrgId2DirectChildOrgCodesMap() {
        List<GcOrgCacheVO> orgTree = this.getOrgTree();
        HashMap<String, List<String>> orgId2DirectChildOrgCodesMap = new HashMap<String, List<String>>();
        this.getOrgId2DirectChildOrgCodesMap(orgTree, orgId2DirectChildOrgCodesMap);
        return orgId2DirectChildOrgCodesMap;
    }

    private void getOrgId2DirectChildOrgCodesMap(List<GcOrgCacheVO> orgTree, Map<String, List<String>> orgId2DirectChildOrgCodesMap) {
        if (CollectionUtils.isEmpty(orgTree)) {
            return;
        }
        for (GcOrgCacheVO orgCacheVO : orgTree) {
            orgId2DirectChildOrgCodesMap.put(orgCacheVO.getId(), orgCacheVO.getChildren().stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()));
            this.getOrgId2DirectChildOrgCodesMap(orgCacheVO.getChildren(), orgId2DirectChildOrgCodesMap);
        }
    }

    public GcOrgCacheVO getOrgByPrimaryID(String realId) {
        if (StringUtils.isEmpty((String)realId)) {
            logger.debug(this.param.getLog("getOrgByPrimaryID(" + realId + ")\u53c2\u6570\u4e3a\u7a7a\uff0c\u83b7\u53d6\u7a7a\u7ec4\u7ec7\u673a\u6784\u3002"));
            return null;
        }
        try {
            GcOrgCacheVO org = this.queryService.getQueryService().getById(this.param, realId);
            if (org != null) {
                return org;
            }
            logger.debug(this.param.getLog("getOrgByPrimaryID(" + realId + ")\u7ec4\u7ec7\u673a\u6784\u4e3a\u7a7a\uff0c\u83b7\u53d6\u7a7a\u7ec4\u7ec7\u673a\u6784\u3002"));
            return null;
        }
        catch (Exception e) {
            logger.error(this.param.getLog("getOrgByPrimaryID(" + realId + ")\u67e5\u8be2\u7ec4\u7ec7\u673a\u6784\u5931\u8d25,\u8bf7\u8054\u7cfb\u7cfb\u7edf\u7ba1\u7406\u5458"), e);
            throw new RuntimeException(e);
        }
    }

    public GcOrgCacheVO getOrgByID(String orgId) {
        return this.getOrgByCode(orgId);
    }

    public GcOrgCacheVO getOrgByCode(String orgCode) {
        if (StringUtils.isEmpty((String)orgCode)) {
            logger.debug(this.param.getLog("getOrgByCode(" + orgCode + ")\u53c2\u6570\u4e3a\u7a7a\uff0c\u83b7\u53d6\u7a7a\u7ec4\u7ec7\u673a\u6784\u3002"));
            return null;
        }
        try {
            GcOrgCacheVO org = this.queryService.getQueryService().getByCode(this.param, orgCode);
            if (org != null) {
                return org;
            }
            logger.debug(this.param.getLog("getOrgByCode(" + orgCode + ")\u7ec4\u7ec7\u673a\u6784\u4e3a\u7a7a\uff0c\u83b7\u53d6\u7a7a\u7ec4\u7ec7\u673a\u6784\u3002"));
            return null;
        }
        catch (Exception e) {
            logger.error(this.param.getLog("getOrgByCode(" + orgCode + ")\u67e5\u8be2\u7ec4\u7ec7\u673a\u6784\u5931\u8d25,\u8bf7\u8054\u7cfb\u7cfb\u7edf\u7ba1\u7406\u5458"), e);
            throw new RuntimeException(e);
        }
    }

    public List<GcOrgCacheVO> listOrgs() {
        return this.listAllOrgByParentId(null);
    }

    public List<GcOrgCacheVO> listAllOrgByParentId(String parentId) {
        try {
            List<GcOrgCacheVO> list = this.queryService.getQueryService().listSubordinate(this.param, parentId);
            if (list.size() < 1) {
                logger.debug(this.param.getLog("listAllOrgByParentId(" + parentId + ")\u83b7\u53d6\u4e0d\u5230\u5339\u914d\u7684\u6709\u6743\u9650\u7684\u7ec4\u7ec7\u673a\u6784\u3002"));
            } else {
                logger.debug(this.param.getLog("listAllOrgByParentId(" + parentId + ")\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784" + parentId + "\u6240\u6709\u4e0b\u7ea7\u7ec4\u7ec7\u3002"));
            }
            return list.stream().filter(v -> !v.getCode().equalsIgnoreCase(parentId)).collect(Collectors.toList());
        }
        catch (Exception e) {
            logger.error(this.param.getLog("listAllOrgByParentId(" + parentId + ")\u67e5\u8be2\u7ec4\u7ec7\u673a\u6784\u5931\u8d25,\u8bf7\u8054\u7cfb\u7cfb\u7edf\u7ba1\u7406\u5458"), e);
            throw new RuntimeException(e);
        }
    }

    public List<GcOrgCacheVO> listAllOrgByParentIdContainsSelf(String parentId) {
        try {
            List<GcOrgCacheVO> list = this.queryService.getQueryService().listSubordinate(this.param, parentId);
            if (list.size() < 1) {
                logger.debug(this.param.getLog("listAllOrgByParentIdContainsSelf(" + parentId + ")\u83b7\u53d6\u4e0d\u5230\u5339\u914d\u7684\u6709\u6743\u9650\u7684\u7ec4\u7ec7\u673a\u6784\u3002"));
            } else {
                logger.debug(this.param.getLog("listAllOrgByParentIdContainsSelf(" + parentId + ")\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784" + parentId + "\u6240\u6709\u4e0b\u7ea7\u7ec4\u7ec7\u3002"));
            }
            return list;
        }
        catch (Exception e) {
            logger.error(this.param.getLog("listAllOrgByParentIdContainsSelf(" + parentId + ")\u67e5\u8be2\u7ec4\u7ec7\u673a\u6784\u5931\u8d25,\u8bf7\u8054\u7cfb\u7cfb\u7edf\u7ba1\u7406\u5458"), e);
            throw new RuntimeException(e);
        }
    }

    public List<GcOrgCacheVO> listOrgBySearch(String searchText) {
        return this.queryService.getQueryService().list(this.param, searchText);
    }

    public List<GcOrgCacheVO> listOrgBySearch(String searchText, String parentCode) {
        return this.queryService.getQueryService().list(this.param, searchText, parentCode);
    }

    public GcOrgCacheVO getMergeUnitByDifference(String orgId) {
        GcOrgCacheVO bbOrg = this.getOrgByID(orgId);
        if (null == bbOrg) {
            return null;
        }
        if (bbOrg.getOrgKind() != GcOrgKindEnum.DIFFERENCE) {
            logger.debug(this.param.getLog("getMergeUnitByDifference(" + orgId + ")\u67e5\u8be2\u7ec4\u7ec7\u4e0d\u662f\u5dee\u989d\u7ec4\u7ec7\u3002"));
            return bbOrg;
        }
        logger.debug(this.param.getLog("getMergeUnitByDifference(" + orgId + ")\u67e5\u8be2\u7ec4\u7ec7\u662f\u5dee\u989d\u7ec4\u7ec7,\u8fd4\u56de\u5408\u5e76\u7ec4\u7ec7\u673a\u6784\u3002"));
        return this.getOrgByID(bbOrg.getMergeUnitId());
    }

    public GcOrgCacheVO getUnionUnitByGrade(String orgId) {
        GcOrgCacheVO bbOrg = this.getOrgByID(orgId);
        if (null == bbOrg) {
            return null;
        }
        if (bbOrg.getOrgKind() == GcOrgKindEnum.UNIONORG && bbOrg.getMergeUnitId() != null) {
            logger.debug(this.param.getLog("getUnionUnitByGrade(" + orgId + ")\u67e5\u8be2\u7ec4\u7ec7\u662f\u5408\u5e76(\u672c\u90e8)\u7ec4\u7ec7,\u8fd4\u56de\u5408\u5e76\u7ec4\u7ec7\u673a\u6784\u3002"));
            return this.getUnionUnitByGrade(bbOrg.getMergeUnitId());
        }
        if (bbOrg.getOrgKind() != GcOrgKindEnum.BASE) {
            logger.debug(this.param.getLog("getUnionUnitByGrade(" + orgId + ")\u67e5\u8be2\u7ec4\u7ec7\u4e0d\u662f\u672c\u7ea7\u7ec4\u7ec7\u3002"));
            return bbOrg;
        }
        logger.debug(this.param.getLog("getUnionUnitByGrade(" + orgId + ")\u67e5\u8be2\u7ec4\u7ec7\u662f\u672c\u7ea7\u7ec4\u7ec7,\u8fd4\u56de\u5408\u5e76\u7ec4\u7ec7\u673a\u6784"));
        return this.getUnionUnitByGrade(bbOrg.getMergeUnitId());
    }

    public GcOrgCacheVO getCommonUnit(GcOrgCacheVO org1, GcOrgCacheVO org2) {
        if (org1 == null || org2 == null) {
            logger.debug(this.param.getLog("getCommonUnit(" + org1 + ", " + org2 + ")\u53c2\u6570\u5b58\u5728\u7a7a\u7ec4\u7ec7\u3002"));
            return null;
        }
        Object[] unitPath1 = org1.getParents();
        Object[] unitPath2 = org2.getParents();
        if (CollectionUtils.isEmpty((Object[])unitPath1) || CollectionUtils.isEmpty((Object[])unitPath2)) {
            logger.debug(this.param.getLog("getCommonUnit(" + org1.getTitle() + ", " + org2.getTitle() + ")parents\u503c\u5b58\u5728\u7a7a\u3002"));
            return null;
        }
        int minLength = Math.min(unitPath1.length, unitPath2.length);
        Object firstSameParentId = null;
        for (int i = 0; i < minLength && ((String)unitPath1[i]).equals(unitPath2[i]); ++i) {
            firstSameParentId = unitPath1[i];
        }
        if (StringUtils.isEmpty(firstSameParentId)) {
            return null;
        }
        return this.getOrgByID((String)firstSameParentId);
    }

    public boolean checkCommonUnit(String parentId, String orgId1, String orgId2) {
        Assert.isNotNull((Object)parentId, (String)"parentId can not be null", (Object[])new Object[0]);
        Assert.isNotNull((Object)orgId1, (String)"orgId1 can not be null", (Object[])new Object[0]);
        Assert.isNotNull((Object)orgId2, (String)"orgId2 can not be null", (Object[])new Object[0]);
        GcOrgCacheVO org1 = this.getOrgByID(orgId1);
        GcOrgCacheVO org2 = this.getOrgByID(orgId2);
        if (Objects.isNull(org1) || Objects.isNull(org2)) {
            logger.debug(this.param.getLog("checkCommonUnit(" + parentId + ", " + orgId1 + ", " + orgId2 + ")\u5224\u65ad\u53c2\u65701\u4e0d\u662f\u53c2\u65702\u548c\u53c2\u65703\u7684\u5171\u540c\u4e0a\u7ea7\u3002"));
            return false;
        }
        Object[] p1 = org1.getParents();
        Object[] p2 = org2.getParents();
        String parentIdStr = parentId;
        if (CollectionUtils.isEmpty((Object[])p1) || CollectionUtils.isEmpty((Object[])p2)) {
            logger.debug(this.param.getLog("checkCommonUnit(" + parentId + ", " + orgId1 + ", " + orgId2 + ")\u5224\u65ad\u53c2\u65701\u4e0d\u662f\u53c2\u65702\u548c\u53c2\u65703\u7684\u5171\u540c\u4e0a\u7ea7\u3002"));
            return false;
        }
        if (ArrayUtils.contains((Object[])p1, (Object)parentIdStr) && ArrayUtils.contains((Object[])p2, (Object)parentIdStr)) {
            logger.debug(this.param.getLog("checkCommonUnit(" + parentId + ", " + orgId1 + ", " + orgId2 + ")\u5224\u65ad\u53c2\u65701\u662f\u53c2\u65702\u548c\u53c2\u65703\u7684\u5171\u540c\u4e0a\u7ea7\u3002"));
            return true;
        }
        logger.debug(this.param.getLog("checkCommonUnit(" + parentId + ", " + orgId1 + ", " + orgId2 + ")\u5224\u65ad\u53c2\u65701\u4e0d\u662f\u53c2\u65702\u548c\u53c2\u65703\u7684\u5171\u540c\u4e0a\u7ea7\u3002"));
        return false;
    }

    public Map<String, Object> getTableDetail(String tableName, String id) {
        try {
            return this.queryService.getQueryService().getTableDetail(tableName, id);
        }
        catch (Exception e) {
            logger.error(this.param.getLog("getTableDetail(" + id + ")\u67e5\u8be2\u7ec4\u7ec7\u673a\u6784\u5931\u8d25,\u8bf7\u8054\u7cfb\u7cfb\u7edf\u7ba1\u7406\u5458"), e);
            throw new RuntimeException(e);
        }
    }

    public String getDeepestBaseUnitId(String currUnitid) {
        GcOrgCacheVO currUnitCacheVO = this.getOrgByID(currUnitid);
        if (null == currUnitCacheVO || currUnitCacheVO.isLeaf()) {
            return null;
        }
        String baseUnitid = currUnitCacheVO.getBaseUnitId();
        if (null == baseUnitid) {
            return null;
        }
        String childBaseUnitId = this.getDeepestBaseUnitId(baseUnitid);
        return null == childBaseUnitId ? baseUnitid : childBaseUnitId;
    }

    public OrgTypeVO getCurrOrgType() {
        return this.param.getType();
    }

    public OrgVersionVO getCurrOrgVersion() {
        return this.param.getVersion();
    }

    public GcOrgCacheVO getBaseOrgById(String id) {
        return this.getBaseOrgByCode(id);
    }

    public GcOrgCacheVO getBaseOrgByCode(String code) {
        return this.queryService.getQueryService().getBaseUnit(OrgParamParse.createBaseOrgParam(vo -> vo.setCode(code)));
    }

    public List<GcOrgCacheVO> collectionToTree(Collection<GcOrgCacheVO> list) {
        return this.queryService.getQueryService().collectionToTree(list);
    }

    public class GcOrgTypeVerCacheService
    implements GcOrgTypeVerCacheTool {
        private GcOrgQueryModel queryService;

        private GcOrgTypeVerCacheService(GcOrgQueryModel queryService) {
            this.queryService = queryService;
        }

        public List<OrgTypeVO> listOrgType() {
            List<OrgTypeVO> orgTypes = this.queryService.getOrgTypeService().listOrgType();
            return orgTypes;
        }

        public OrgTypeVO getOrgTypeByName(String typeName) {
            return this.queryService.getQueryService().getOrgType(typeName);
        }

        public OrgVersionVO getOrgVersionByType(String typeName, Date time) {
            return this.queryService.getQueryService().getOrgVersion(this.getOrgTypeByName(typeName), time);
        }

        public List<OrgVersionVO> getOrgVersionByType(String typeName) {
            return this.queryService.getQueryService().listOrgVersion(this.getOrgTypeByName(typeName));
        }
    }
}

