/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.common.OrgContext
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.domain.org.OrgCacheDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$OrderType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryParentType
 *  com.jiuqi.va.domain.org.OrgDataSortDTO
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.extend.OrgDataCacheExtend
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.EnumDataClient
 */
package com.jiuqi.va.organization.service.impl.help;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.OrgContext;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.domain.org.OrgCacheDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.OrgDataSortDTO;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.extend.OrgDataCacheExtend;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.EnumDataClient;
import com.jiuqi.va.organization.common.OrgCoreI18nUtil;
import com.jiuqi.va.organization.common.OrgDataCacheUtil;
import com.jiuqi.va.organization.dao.VaOrgDataDao;
import com.jiuqi.va.organization.service.impl.help.OrgDataCacheService;
import com.jiuqi.va.organization.service.impl.help.OrgDataFilterService;
import com.jiuqi.va.organization.service.impl.help.OrgDataParamService;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class OrgDataQueryService {
    private static Logger logger = LoggerFactory.getLogger(OrgDataQueryService.class);
    @Autowired
    public VaOrgDataDao orgDataDao;
    @Autowired
    private OrgDataParamService orgDataParamService;
    @Autowired
    private OrgDataCacheService orgDataCacheService;
    @Autowired
    private OrgDataFilterService orgDataFilterService;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private EnumDataClient enumDataClient;
    @Autowired
    private AuthUserClient userClient;

    public OrgDO get(OrgDTO orgDTO) {
        List<OrgDO> list = this.listExtend(orgDTO);
        if (list == null) {
            list = this.orgDataCacheService.listBasicCacheData(orgDTO);
        }
        if (list != null && !list.isEmpty()) {
            OrgDO org = list.get(0);
            Set orgAuthCodes = (Set)orgDTO.get((Object)"orgAuthCodes");
            if (orgAuthCodes == null || orgAuthCodes.contains(org.getCode())) {
                return org;
            }
        }
        return null;
    }

    public int count(OrgDTO param) {
        List<OrgDO> orgDatas = this.listNoOrder(param);
        if (orgDatas == null) {
            return 0;
        }
        return orgDatas.size();
    }

    /*
     * Enabled aggressive block sorting
     */
    public PageVO<OrgDO> list(OrgDTO param) {
        String language = param.getLanguage();
        if (StringUtils.hasText(language)) {
            language = language.trim().toLowerCase().replace("-", "_");
        } else {
            Locale locale = LocaleContextHolder.getLocale();
            language = locale.toLanguageTag().toLowerCase().replace("-", "_");
        }
        language = language.trim().toLowerCase();
        param.setLanguage(language);
        List orgDatas = this.listNoOrder(param);
        if (orgDatas == null || orgDatas.isEmpty()) {
            return new PageVO(true);
        }
        this.orderList(orgDatas, param);
        orgDatas = new ArrayList<OrgDO>(orgDatas);
        int tatal = orgDatas.size();
        PageVO page = new PageVO();
        page.setRs(R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0])));
        page.setTotal(tatal);
        if (param.isPagination() && param.getLimit() > 0) {
            if (param.getOffset() >= tatal) {
                page.setRows(new ArrayList());
                return page;
            }
            int endNum = param.getOffset() + param.getLimit();
            endNum = endNum > tatal ? tatal : endNum;
            page.setRows(orgDatas.subList(param.getOffset(), endNum));
        } else {
            page.setRows(orgDatas);
        }
        orgDatas = page.getRows();
        OrgDataOption.QueryParentType queryParentType = param.getQueryParentType();
        if (queryParentType != null) {
            OrgDO currData = (OrgDO)orgDatas.get(0);
            PageVO parentPage = new PageVO(true);
            List parentList = parentPage.getRows();
            if (queryParentType == OrgDataOption.QueryParentType.DIRECT_PARENT_WITH_SELF || queryParentType == OrgDataOption.QueryParentType.ALL_PARENT_WITH_SELF) {
                parentList.add(currData);
            }
            OrgDTO parentParam = new OrgDTO();
            parentParam.setCategoryname(param.getCategoryname());
            parentParam.setVersionDate(param.getVersionDate());
            parentParam.setAuthType(param.getAuthType());
            this.listParentObj(parentParam, currData, parentList, queryParentType);
            page.setRows(parentList);
            page.setTotal(parentList.size());
            if (page.getTotal() == 0) {
                return page;
            }
            orgDatas = page.getRows();
        }
        this.leafFlag(orgDatas, param);
        OrgDataOption.QueryDataStructure qdStructure = param.getQueryDataStructure();
        OrgVersionDO versionData = this.orgDataParamService.getOrgVersion(param);
        if (versionData != null && !versionData.isActive()) {
            this.orgDataCacheService.loadMultiDetailData((OrgDO)param, orgDatas);
        }
        if (qdStructure != null && qdStructure == OrgDataOption.QueryDataStructure.ALL_WITH_REF) {
            this.loadRelateData(orgDatas, param);
        }
        if (qdStructure == null || qdStructure != OrgDataOption.QueryDataStructure.BASIC) {
            String localName = "name_" + language;
            boolean canIndex = false;
            Integer localizedIndex = null;
            OrgDO tempData = (OrgDO)orgDatas.get(0);
            if (tempData instanceof OrgCacheDO) {
                canIndex = true;
                Map colIndexMap = OrgContext.getColIndexMap((String)param.getTenantName(), (String)param.getCategoryname());
                localizedIndex = (Integer)colIndexMap.get(localName);
            }
            String showType = param.getShowType() != null ? param.getShowType() : "CODE&NAME";
            String name = null;
            for (OrgDO orgData : orgDatas) {
                name = canIndex ? (localizedIndex != null && localizedIndex > -1 ? (String)((OrgCacheDO)orgData).getFieldValue((Object)localName, localizedIndex.intValue()) : orgData.getName()) : (String)orgData.get((Object)localName);
                if (name == null) {
                    name = orgData.getName();
                }
                orgData.setLocalizedName(name);
                if ("CODE".equals(showType)) {
                    orgData.setShowTitle(orgData.getOrgcode());
                    continue;
                }
                if ("CODE&NAME".equals(showType)) {
                    orgData.setShowTitle(orgData.getOrgcode() + " " + name);
                    continue;
                }
                if ("REFCODE&NAME".equals(showType)) {
                    orgData.setShowTitle(orgData.getCode() + " " + name);
                    continue;
                }
                orgData.setShowTitle(name);
            }
        }
        return page;
    }

    private void listParentObj(OrgDTO parentParam, OrgDO data, List<OrgDO> parentList, OrgDataOption.QueryParentType queryParentType) {
        String parentcode = data.getParentcode();
        if (parentcode.equals("-")) {
            return;
        }
        parentParam.setCode(parentcode);
        OrgDO org = this.get(parentParam);
        if (org == null) {
            return;
        }
        parentList.add(org);
        if (queryParentType == OrgDataOption.QueryParentType.ALL_PARENT || queryParentType == OrgDataOption.QueryParentType.ALL_PARENT_WITH_SELF) {
            this.listParentObj(parentParam, org, parentList, queryParentType);
        }
    }

    private void orderList(List<OrgDO> orgDatas, OrgDTO param) {
        if (orgDatas == null || orgDatas.size() < 2) {
            return;
        }
        List orderBy = param.getOrderBy();
        if (orderBy != null) {
            String categoryName;
            String tenantName;
            Map indexMap;
            if (orderBy.isEmpty()) {
                return;
            }
            HashMap colIndexMap = new HashMap();
            if (orgDatas.get(0) instanceof OrgCacheDO && (indexMap = OrgContext.getColIndexMap((String)(tenantName = param.getTenantName()), (String)(categoryName = param.getCategoryname()))) != null) {
                colIndexMap.putAll(indexMap);
            }
            boolean canIndex = !colIndexMap.isEmpty();
            Collator comparator = Collator.getInstance(Locale.CHINA);
            Collections.sort(orgDatas, (o1, o2) -> {
                int compare = 0;
                int colIndex = -1;
                for (OrgDataSortDTO sort : orderBy) {
                    Object t2;
                    Object t1;
                    if (canIndex) {
                        colIndex = (Integer)colIndexMap.get(sort.getColumn());
                        t1 = ((OrgCacheDO)o1).getFieldValue((Object)sort.getColumn(), colIndex);
                        t2 = ((OrgCacheDO)o2).getFieldValue((Object)sort.getColumn(), colIndex);
                    } else {
                        t1 = o1.get((Object)sort.getColumn());
                        t2 = o2.get((Object)sort.getColumn());
                    }
                    compare = t1 == null && t2 == null ? 0 : (t1 == null && t2 != null ? -1 : (t1 != null && t2 == null ? 1 : (t1 instanceof String ? comparator.compare(t1.toString(), t2.toString()) : (t1 instanceof Comparable ? ((Comparable)t1).compareTo((Comparable)t2) : comparator.compare(t1.toString(), t2.toString())))));
                    if (compare != 0 && sort.getOrder() == OrgDataOption.OrderType.DESC) {
                        compare *= -1;
                    }
                    if (compare == 0) continue;
                    break;
                }
                return compare;
            });
            return;
        }
        if (StringUtils.hasText(param.getParentcode()) || StringUtils.hasText(param.getCode()) && OrgDataOption.QueryChildrenType.DIRECT_CHILDREN == param.getQueryChildrenType()) {
            Collections.sort(orgDatas, (o1, o2) -> o1.getOrdinal().compareTo(o2.getOrdinal()));
            return;
        }
        HashMap<String, String> codeOrder = new HashMap<String, String>();
        BigDecimal order = null;
        for (OrgDO data : orgDatas) {
            order = data.getOrdinal();
            String orderVal = order == null ? "-" : String.format("%020f", order);
            codeOrder.put(data.getCode(), orderVal);
        }
        HashMap<UUID, String> endOrder = new HashMap<UUID, String>();
        String[] parents = null;
        StringBuilder sb = new StringBuilder();
        for (OrgDO data : orgDatas) {
            parents = data.getParents().split("\\/");
            if (sb.length() > 0) {
                sb.setLength(0);
            }
            for (String parentCode : parents) {
                if (codeOrder.containsKey(parentCode)) {
                    sb.append((String)codeOrder.get(parentCode)).append("#");
                    continue;
                }
                sb.append("-#");
            }
            endOrder.put(data.getId(), sb.toString());
        }
        Collections.sort(orgDatas, (o1, o2) -> ((String)endOrder.get(o1.getId())).compareTo((String)endOrder.get(o2.getId())));
    }

    private void loadRelateData(List<OrgDO> orgDatas, OrgDTO param) {
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setTenantName(param.getTenantName());
        dataModelDTO.setName(param.getCategoryname());
        dataModelDTO.setDeepClone(Boolean.valueOf(false));
        DataModelDO dataModel = this.dataModelClient.get(dataModelDTO);
        List cols = dataModel.getColumns();
        HashMap<String, String> colMapping = new HashMap<String, String>();
        HashMap<String, Integer> colType = new HashMap<String, Integer>();
        HashMap<String, Map<String, String>> tempTableData = new HashMap<String, Map<String, String>>();
        HashMap<String, String> relateDefineShowType = new HashMap<String, String>();
        if (cols == null) {
            return;
        }
        BaseDataDefineDTO defineParam = new BaseDataDefineDTO();
        defineParam.setTenantName(param.getTenantName());
        defineParam.setDeepClone(Boolean.valueOf(false));
        String colName = null;
        String mapping = null;
        String colRefTablename = null;
        Integer mappingType = null;
        BaseDataDefineDO relateDefine = null;
        for (DataModelColumn column : cols) {
            colName = column.getColumnName().toLowerCase();
            mapping = column.getMapping();
            if (!"parentcode".equals(colName) && !StringUtils.hasText(mapping)) continue;
            if ("parentcode".equals(colName)) {
                colRefTablename = param.getCategoryname();
                mappingType = 4;
            } else {
                colRefTablename = mapping.split("\\.")[0];
                mappingType = column.getMappingType();
            }
            if (param.getExtInfo("RefShowCode") != null) {
                if (mappingType == 4) {
                    relateDefineShowType.put(colRefTablename, "REFCODE&NAME");
                } else {
                    relateDefineShowType.put(colRefTablename, "CODE&NAME");
                }
            } else if (!relateDefineShowType.containsKey(colRefTablename)) {
                if (mappingType == 1) {
                    defineParam.setName(colRefTablename);
                    relateDefine = this.baseDataDefineClient.get(defineParam);
                    relateDefineShowType.put(colRefTablename, relateDefine != null ? relateDefine.getShowtype() : null);
                } else {
                    relateDefineShowType.put(colRefTablename, "NAME");
                }
            }
            colMapping.put(colName, colRefTablename);
            colType.put(colName, mappingType);
            if (tempTableData.containsKey(colRefTablename)) continue;
            tempTableData.put(colRefTablename, new HashMap());
        }
        if (!colMapping.isEmpty()) {
            this.loadRelateData(orgDatas, param, colMapping, colType, relateDefineShowType, tempTableData);
        }
    }

    private void loadRelateData(List<OrgDO> orgDatas, OrgDTO param, Map<String, String> colMapping, Map<String, Integer> colType, Map<String, String> relateDefineShowType, Map<String, Map<String, String>> tempTableData) {
        HashMap<String, String> showTitleMap = null;
        String colName = null;
        Object value = null;
        String relateName = null;
        String refTbName = null;
        Integer refColType = null;
        String refShowType = null;
        for (OrgDO data : orgDatas) {
            showTitleMap = new HashMap<String, String>();
            for (Map.Entry<String, String> entry : colMapping.entrySet()) {
                colName = entry.getKey();
                refTbName = entry.getValue();
                value = data.get((Object)colName);
                if (value == null) continue;
                refColType = colType.get(colName);
                refShowType = relateDefineShowType.get(refTbName);
                if (value instanceof List || value.toString().contains(",")) {
                    List<String> codeList = null;
                    codeList = value instanceof List ? (List<String>)value : Arrays.asList(value.toString().split(","));
                    if (codeList == null || codeList.isEmpty()) continue;
                    StringBuilder strBuilder = new StringBuilder();
                    for (String code : codeList) {
                        if (!StringUtils.hasText(code) || (relateName = this.getRelateName(code, param, refTbName, refColType, refShowType, tempTableData)) == null) continue;
                        strBuilder.append(",").append(relateName);
                    }
                    showTitleMap.put(colName, strBuilder.length() > 0 ? strBuilder.substring(1) : "");
                    continue;
                }
                if (!StringUtils.hasText(value.toString())) continue;
                relateName = this.getRelateName(value.toString(), param, refTbName, refColType, refShowType, tempTableData);
                showTitleMap.put(colName, relateName);
            }
            data.put("showTitleMap", showTitleMap);
        }
    }

    private String getRelateName(String code, OrgDTO param, String refTableName, Integer colType, String relateDefineShowType, Map<String, Map<String, String>> tempTableData) {
        if (tempTableData.get(refTableName).containsKey(code)) {
            return tempTableData.get(refTableName).get(code);
        }
        String tenantName = param.getTenantName();
        Date versionDate = param.getVersionDate();
        String rsName = code;
        if ("-".equals(code) && (colType == 1 || colType == 4)) {
            rsName = "";
            tempTableData.get(refTableName).put(code, rsName);
            return rsName;
        }
        if (colType == 1) {
            BaseDataDTO basedataDTO = new BaseDataDTO();
            basedataDTO.setTenantName(tenantName);
            basedataDTO.setTableName(refTableName);
            basedataDTO.setStopflag(Integer.valueOf(-1));
            basedataDTO.setAuthType(BaseDataOption.AuthType.NONE);
            basedataDTO.setVersionDate(versionDate);
            basedataDTO.setObjectcode(code);
            basedataDTO.setShowType(relateDefineShowType);
            PageVO dataList = this.baseDataClient.list(basedataDTO);
            if (dataList != null && dataList.getTotal() > 0) {
                rsName = ((BaseDataDO)dataList.getRows().get(0)).getShowTitle();
            }
        } else if (colType == 4) {
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setTenantName(tenantName);
            orgDTO.setCategoryname(refTableName);
            orgDTO.setStopflag(Integer.valueOf(-1));
            orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
            orgDTO.setVersionDate(versionDate);
            orgDTO.setCode(code);
            orgDTO.setShowType(relateDefineShowType);
            PageVO<OrgDO> orgs = this.list(orgDTO);
            if (orgs != null && orgs.getTotal() > 0) {
                rsName = ((OrgDO)orgs.getRows().get(0)).getShowTitle();
            }
        } else if (colType == 2) {
            EnumDataDTO enumDataDTO = new EnumDataDTO();
            enumDataDTO.setTenantName(tenantName);
            enumDataDTO.setBiztype(refTableName);
            enumDataDTO.setVal(code);
            List enumDatas = this.enumDataClient.list(enumDataDTO);
            if (enumDatas != null && !enumDatas.isEmpty()) {
                rsName = "CODE&NAME".equals(relateDefineShowType) ? code + " " + ((EnumDataDO)enumDatas.get(0)).getTitle() : ((EnumDataDO)enumDatas.get(0)).getTitle();
            }
        } else if (colType == 3) {
            UserDTO userDTO = new UserDTO();
            userDTO.setTenantName(tenantName);
            userDTO.setId(code);
            userDTO.addExtInfo("onlyNeedBasicInfo", (Object)true);
            UserDO user = this.userClient.get(userDTO);
            if (user != null) {
                rsName = user.getName();
            }
        }
        tempTableData.get(refTableName).put(code, rsName);
        return rsName;
    }

    public List<OrgDO> listNoOrder(OrgDTO param) {
        List<OrgDO> extendDatas = this.listExtend(param);
        if (extendDatas != null) {
            return extendDatas;
        }
        List<OrgDO> cacheList = this.orgDataCacheService.listBasicCacheData(param);
        this.orgDataFilterService.filterList(cacheList, param);
        return cacheList;
    }

    private List<OrgDO> listExtend(OrgDTO param) {
        Map<String, OrgDataCacheExtend> cacheExtends = OrgDataCacheUtil.getCacheExtends();
        if (cacheExtends != null && !cacheExtends.isEmpty()) {
            for (OrgDataCacheExtend cacheExtend : cacheExtends.values()) {
                List list = cacheExtend.tryListCacheData(param);
                if (list == null) continue;
                return list;
            }
        }
        return null;
    }

    private void leafFlag(List<OrgDO> dataList, OrgDTO param) {
        if (!param.isLeafFlag() || dataList == null || dataList.isEmpty()) {
            return;
        }
        OrgDTO lfParam = new OrgDTO();
        lfParam.setTenantName(param.getTenantName());
        lfParam.setCategoryname(param.getCategoryname());
        lfParam.setVersionDate(param.getVersionDate());
        lfParam.setAuthType(OrgDataOption.AuthType.NONE);
        lfParam.setQueryChildrenType(OrgDataOption.QueryChildrenType.DIRECT_CHILDREN);
        ArrayList<String> codes = new ArrayList<String>();
        for (OrgDO data : dataList) {
            codes.add(data.getCode());
        }
        lfParam.setOrgCodes(codes);
        HashSet<String> childRefParent = new HashSet<String>();
        List<OrgDO> allDataList = this.orgDataCacheService.listBasicCacheData(lfParam);
        if (!CollectionUtils.isEmpty(allDataList)) {
            for (OrgDO orgDO : allDataList) {
                childRefParent.add(orgDO.getParentcode());
            }
        }
        for (OrgDO obj : dataList) {
            obj.put("isLeaf", (Object)(!childRefParent.contains(obj.getCode()) ? 1 : 0));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<OrgDO> verDiffList(OrgDTO orgDTO) {
        ArrayList<OrgDO> dataList = new ArrayList<OrgDO>();
        OrgContext.bindColIndex((String)orgDTO.getTenantName(), (String)orgDTO.getCategoryname());
        try {
            LinkedList<OrgCacheDO> list = this.orgDataDao.selectGreaterVer(orgDTO);
            if (!list.isEmpty()) {
                this.orgDataCacheService.loadMultiDetailData((OrgDO)orgDTO, list);
                dataList.addAll(list);
            }
        }
        catch (Throwable e) {
            logger.error("\u7ec4\u7ec7\u673a\u6784\u67e5\u8be2\u5931\u8d25", e);
        }
        finally {
            OrgContext.unbindColIndex();
        }
        return dataList;
    }
}

