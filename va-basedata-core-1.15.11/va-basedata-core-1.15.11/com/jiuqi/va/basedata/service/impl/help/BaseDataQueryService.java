/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.basedata.common.BaseDataContext
 *  com.jiuqi.va.domain.basedata.BaseDataCacheDO
 *  com.jiuqi.va.domain.basedata.BaseDataCacheExtend
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryParentType
 *  com.jiuqi.va.domain.basedata.handle.BaseDataColumnValueDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.va.basedata.service.impl.help;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.basedata.common.BaseDataCacheUtil;
import com.jiuqi.va.basedata.common.BaseDataContext;
import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.dao.VaBaseDataDao;
import com.jiuqi.va.basedata.service.BaseDataDefineService;
import com.jiuqi.va.basedata.service.BaseDataDetailService;
import com.jiuqi.va.basedata.service.EnumDataService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheCoordinationService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataDummyService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataFilterService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataOrderService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataParamService;
import com.jiuqi.va.domain.basedata.BaseDataCacheDO;
import com.jiuqi.va.domain.basedata.BaseDataCacheExtend;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataColumnValueDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="vaBaseDataQueryService")
public class BaseDataQueryService {
    private static Logger logger = LoggerFactory.getLogger(BaseDataQueryService.class);
    @Autowired
    private VaBaseDataDao baseDataDao;
    @Autowired
    private BaseDataParamService baseDataParamService;
    @Autowired
    private BaseDataCacheService baseDataCacheService;
    @Autowired
    private EnumDataService enumDataService;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private BaseDataDefineService baseDataDefineService;
    @Autowired
    private BaseDataDetailService baseDataDetailService;
    @Autowired
    private BaseDataCacheCoordinationService coordinationService;
    @Autowired
    private BaseDataDummyService baseDataDummyService;
    @Autowired
    private BaseDataFilterService baseDataFilterService;
    @Autowired
    private BaseDataOrderService baseDataOrderService;
    private static String isExistQuery = "isExistQuery";
    private static String dataExist = "exist";

    public R exist(BaseDataDTO param) {
        String tableName = param.getTableName();
        if (!this.coordinationService.isCanLoadByCurrentNode(tableName)) {
            BaseDataClient client = this.coordinationService.getClient(tableName);
            if (client != null) {
                return client.exist(param);
            }
            throw new RuntimeException("Unsupported Operation");
        }
        if (param.getCode() == null && param.getId() == null && param.getObjectcode() == null) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        BaseDataDTO param2 = new BaseDataDTO();
        param2.setTenantName(param.getTenantName());
        param2.setTableName(tableName);
        param2.setAuthType(BaseDataOption.AuthType.NONE);
        param2.setStopflag(param.getStopflag());
        param2.setRecoveryflag(param.getRecoveryflag());
        if (param.getId() == null && param.getObjectcode() == null && param.getCode() != null) {
            this.baseDataParamService.initParam(param);
            param2.setCode(param.getCode().toUpperCase());
            param2.put("baseDataDefineDO", param.get((Object)"baseDataDefineDO"));
            Object shareFields = param.get((Object)"sharefields");
            if (shareFields != null) {
                List sharefields = (List)shareFields;
                for (String field : sharefields) {
                    param2.put(field, param.get((Object)field));
                }
                param2.put("sharefields", shareFields);
            }
            if (param.get((Object)"shareUnitcodes") != null) {
                param2.remove((Object)"unitcode");
                param2.put("shareUnitcodes", param.get((Object)"shareUnitcodes"));
            }
        } else if (param.getId() == null && param.getObjectcode() != null) {
            param2.setObjectcode(param.getObjectcode());
        } else {
            param2.setId(param.getId());
        }
        param2.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        param2.setVersionDate(param.getVersionDate());
        param2.put(isExistQuery, (Object)true);
        List maps = this.list(param2).getRows();
        R r = R.ok();
        if (maps != null && !maps.isEmpty()) {
            BaseDataDO old = (BaseDataDO)maps.get(0);
            r.put(dataExist, (Object)true);
            r.put("data", (Object)old);
        } else {
            r.put(dataExist, (Object)false);
        }
        return r;
    }

    public int count(BaseDataDTO param) {
        String tableName = param.getTableName();
        if (!this.coordinationService.isCanLoadByCurrentNode(tableName)) {
            BaseDataClient client = this.coordinationService.getClient(tableName);
            if (client != null) {
                return client.count(param);
            }
            throw new RuntimeException("Unsupported Operation");
        }
        param.setOrderBy(Collections.emptyList());
        PageVO<BaseDataDO> dataPage = this.listAnyWay(param);
        if (dataPage != null) {
            return dataPage.getTotal();
        }
        return 0;
    }

    public PageVO<BaseDataDO> list(BaseDataDTO param) {
        String tableName = param.getTableName();
        if (!this.coordinationService.isCanLoadByCurrentNode(tableName)) {
            BaseDataClient client = this.coordinationService.getClient(tableName);
            if (client != null) {
                return client.list(param);
            }
            throw new RuntimeException("Unsupported Operation");
        }
        String language = param.getLanguage();
        if (StringUtils.hasText(language)) {
            language = language.trim().toLowerCase().replace("-", "_");
        } else {
            Locale locale = LocaleContextHolder.getLocale();
            language = locale.toLanguageTag().toLowerCase().replace("-", "_");
        }
        language = language.trim().toLowerCase();
        param.setLanguage(language);
        PageVO<BaseDataDO> page = this.listAnyWay(param);
        if (page == null || page.getTotal() == 0) {
            return page;
        }
        List datas = page.getRows();
        BaseDataOption.QueryParentType queryParentType = param.getQueryParentType();
        if (queryParentType != null) {
            BaseDataDefineDO defineDO;
            int structType;
            BaseDataDO currData = (BaseDataDO)datas.get(0);
            PageVO parentPage = new PageVO(true);
            List parentList = parentPage.getRows();
            if (queryParentType == BaseDataOption.QueryParentType.DIRECT_PARENT_WITH_SELF || queryParentType == BaseDataOption.QueryParentType.ALL_PARENT_WITH_SELF) {
                parentList.add(currData);
            }
            int n = structType = (defineDO = this.baseDataParamService.getBaseDataDefineDO((BaseDataDO)param)).getStructtype() != null ? defineDO.getStructtype() : 0;
            if (structType == 2 || structType == 3) {
                BaseDataDTO parentParam = new BaseDataDTO();
                parentParam.setTableName(tableName);
                parentParam.setAuthType(param.getAuthType());
                if (param.get((Object)"sharefields") != null) {
                    List sharefields = (List)param.get((Object)"sharefields");
                    for (String field : sharefields) {
                        parentParam.put(field, param.get((Object)field));
                    }
                }
                parentParam.put("sharefieldValueInited", (Object)true);
                this.listParentObj(parentParam, currData, parentList, queryParentType);
            }
            page.setRows(parentList);
            page.setTotal(parentList.size());
            if (page.getTotal() == 0) {
                return page;
            }
            datas = page.getRows();
        }
        if (param.containsKey((Object)isExistQuery)) {
            return page;
        }
        this.markSameUnit(datas, param);
        this.leafFlag(datas, param);
        this.parentStopFlag(datas, param);
        BaseDataOption.QueryDataStructure qdStructure = param.getQueryDataStructure();
        if (qdStructure != null && qdStructure == BaseDataOption.QueryDataStructure.ALL_WITH_REF) {
            this.loadRelateData(datas, param);
        }
        if (qdStructure == null || qdStructure != BaseDataOption.QueryDataStructure.BASIC) {
            boolean isShowFullPath = false;
            if (param.isShowFullPath() && param.getQueryChildrenType() == null && (param.getId() != null || StringUtils.hasText(param.getObjectcode()) || StringUtils.hasText(param.getCode()))) {
                isShowFullPath = true;
            }
            String localName = "name_" + language;
            boolean canIndex = false;
            Integer localizedIndex = null;
            BaseDataDO tempData = (BaseDataDO)datas.get(0);
            if (tempData instanceof BaseDataCacheDO) {
                canIndex = true;
                Map colIndexMap = BaseDataContext.getColIndexMap((String)tempData.getTenantName(), (String)tempData.getTableName());
                localizedIndex = (Integer)colIndexMap.get(localName);
            }
            String showTypeStr = param.getShowType();
            String name = null;
            for (BaseDataDO baseDataDO : datas) {
                name = canIndex ? (localizedIndex != null && localizedIndex > -1 ? (String)((BaseDataCacheDO)baseDataDO).getFieldValue((Object)localName, localizedIndex.intValue(), true) : baseDataDO.getName()) : (String)baseDataDO.get((Object)localName);
                if (name == null) {
                    name = baseDataDO.getName();
                }
                baseDataDO.setLocalizedName(name);
                if (isShowFullPath) {
                    baseDataDO.setShowTitle(this.getFullPathByShowType(param, baseDataDO, showTypeStr, language));
                    continue;
                }
                if ("CODE".equals(showTypeStr)) {
                    baseDataDO.setShowTitle(baseDataDO.getCode());
                    continue;
                }
                if ("CODE&NAME".equals(showTypeStr)) {
                    baseDataDO.setShowTitle(baseDataDO.getCode() + " " + name);
                    continue;
                }
                baseDataDO.setShowTitle(name);
            }
        }
        return page;
    }

    private void listParentObj(BaseDataDTO parentParam, BaseDataDO data, List<BaseDataDO> parentList, BaseDataOption.QueryParentType queryParentType) {
        String parentcode = data.getParentcode();
        if (!StringUtils.hasText(parentcode) || parentcode.equals("-")) {
            return;
        }
        parentParam.setCode(parentcode);
        PageVO<BaseDataDO> page = this.listAnyWay(parentParam);
        if (page == null || page.getTotal() == 0) {
            return;
        }
        BaseDataDO basedata = (BaseDataDO)page.getRows().get(0);
        parentList.add(basedata);
        if (queryParentType == BaseDataOption.QueryParentType.ALL_PARENT || queryParentType == BaseDataOption.QueryParentType.ALL_PARENT_WITH_SELF) {
            this.listParentObj(parentParam, basedata, parentList, queryParentType);
        }
    }

    private String getFullPathByShowType(BaseDataDTO param, BaseDataDO currData, String defineShowType, String language) {
        StringBuilder fullPath = new StringBuilder();
        if (!StringUtils.hasText(currData.getParents())) {
            return fullPath.toString();
        }
        String[] parents = currData.getParents().split("\\/");
        if (parents == null || parents.length <= 0) {
            return fullPath.toString();
        }
        String name = null;
        for (String parent : parents) {
            BaseDataDTO parentparam = new BaseDataDTO();
            parentparam.setTableName(param.getTableName());
            parentparam.setTenantName(param.getTenantName());
            parentparam.setStopflag(Integer.valueOf(-1));
            parentparam.setRecoveryflag(Integer.valueOf(-1));
            if (param.get((Object)"sharefields") != null) {
                List sharefields = (List)param.get((Object)"sharefields");
                for (String field : sharefields) {
                    parentparam.put(field, currData.get((Object)field));
                }
            }
            parentparam.setCode(parent);
            R rs = this.exist(parentparam);
            if (((Boolean)rs.get((Object)dataExist)).booleanValue()) {
                BaseDataDO obj = (BaseDataDO)rs.get((Object)"data");
                String showType = param.getShowType();
                if (!StringUtils.hasText(showType)) {
                    showType = defineShowType;
                }
                if ("CODE".equals(showType)) {
                    fullPath.append(obj.getCode());
                } else {
                    name = null;
                    if (language != null) {
                        name = (String)obj.get((Object)("name_" + language));
                    }
                    if (name == null) {
                        name = obj.getName();
                    }
                    if ("CODE&NAME".equals(showType)) {
                        fullPath.append(obj.getCode() + " " + name);
                    } else {
                        fullPath.append(name);
                    }
                }
            }
            fullPath.append("/");
        }
        if (fullPath.length() <= 0) {
            return fullPath.toString();
        }
        return fullPath.toString().substring(0, fullPath.length() - 1);
    }

    public PageVO<BaseDataDO> listAnyWay(BaseDataDTO param) {
        PageVO page = null;
        try {
            this.baseDataParamService.initParam(param);
        }
        catch (Exception e) {
            page = new PageVO(true);
            page.setRs(R.error((String)e.getMessage()));
            return page;
        }
        if (this.baseDataParamService.isDummy((BaseDataDO)param)) {
            return this.baseDataDummyService.list(param);
        }
        Map<String, BaseDataCacheExtend> cacheExtends = BaseDataCacheUtil.getCacheExtends();
        if (cacheExtends != null && !cacheExtends.isEmpty()) {
            for (BaseDataCacheExtend cacheExtend : cacheExtends.values()) {
                page = cacheExtend.tryListCacheData(param);
                if (page == null) continue;
                return page;
            }
        }
        return this.listByCache(param);
    }

    private PageVO<BaseDataDO> listByCache(BaseDataDTO param) {
        PageVO page = new PageVO();
        List<BaseDataDO> cacheList = this.baseDataCacheService.listBasicCacheData(param);
        if (cacheList == null || cacheList.isEmpty()) {
            page.setTotal(0);
            page.setRows(new ArrayList());
            page.setRs(R.ok());
            return page;
        }
        if (param.containsKey((Object)isExistQuery) || param.getId() != null && param.getQueryChildrenType() == null) {
            page.setTotal(cacheList.size());
            page.setRows(cacheList);
            page.setRs(R.ok());
            return page;
        }
        this.baseDataFilterService.filterList(cacheList, param, true);
        this.baseDataOrderService.orderList(cacheList, param);
        cacheList = new ArrayList<BaseDataDO>(cacheList);
        int tatal = cacheList.size();
        page.setRs(R.ok());
        page.setTotal(tatal);
        if (param.isPagination() && param.getLimit() > 0) {
            if (param.getOffset() < tatal) {
                int endNum = param.getOffset() + param.getLimit();
                endNum = endNum > tatal ? tatal : endNum;
                page.setRows(cacheList.subList(param.getOffset(), endNum));
            } else {
                page.setRows(new ArrayList());
            }
        } else {
            page.setRows(cacheList);
        }
        return page;
    }

    private void markSameUnit(List<BaseDataDO> dataList, BaseDataDTO param) {
        if (dataList == null || dataList.isEmpty() || param.isIgnoreShareFields()) {
            return;
        }
        BaseDataDefineDO defineDO = this.baseDataParamService.getBaseDataDefineDO((BaseDataDO)param);
        int defineSharetype = defineDO.getSharetype();
        boolean markSameUnit = false;
        if (defineSharetype > 1) {
            markSameUnit = true;
        }
        if (markSameUnit) {
            String unitcode = param.getUnitcode();
            for (BaseDataDO data : dataList) {
                data.put("sameUnit", (Object)(data.getUnitcode().equals(unitcode) || defineSharetype == 3 && "-".equals(data.getUnitcode()) ? 1 : 0));
            }
        }
    }

    private void leafFlag(List<BaseDataDO> dataList, BaseDataDTO param) {
        if (!param.isLeafFlag()) {
            return;
        }
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        BaseDataDefineDO defineDO = this.baseDataParamService.getBaseDataDefineDO((BaseDataDO)param);
        int defineStructtype = defineDO.getStructtype();
        if (defineStructtype == 0 || defineStructtype == 1) {
            return;
        }
        BaseDataDTO lfParam = new BaseDataDTO();
        lfParam.setTableName(param.getTableName());
        lfParam.setUnitcode(param.getUnitcode());
        lfParam.setVersionDate(param.getVersionDate());
        lfParam.setAuthType(BaseDataOption.AuthType.NONE);
        if (param.getStopflag() != null && param.getStopflag() == 1) {
            lfParam.setStopflag(Integer.valueOf(-1));
        } else {
            lfParam.setStopflag(param.getStopflag());
        }
        if (param.containsKey((Object)"sharefields")) {
            lfParam.put("sharefields", param.get((Object)"sharefields"));
            for (Object field : (List)param.get((Object)"sharefields")) {
                lfParam.put((String)field, param.get(field));
            }
        }
        if (param.containsKey((Object)"shareUnitcodes")) {
            lfParam.put("shareUnitcodes", param.get((Object)"shareUnitcodes"));
        }
        lfParam.setQueryChildrenType(BaseDataOption.QueryChildrenType.DIRECT_CHILDREN);
        ArrayList<String> codes = new ArrayList<String>();
        for (BaseDataDO data : dataList) {
            codes.add(data.getCode());
        }
        lfParam.setBaseDataCodes(codes);
        HashSet<String> childRefParent = new HashSet<String>();
        PageVO<BaseDataDO> allDataPage = this.listAnyWay(lfParam);
        if (allDataPage != null && allDataPage.getTotal() > 0) {
            for (BaseDataDO baseDataDO : allDataPage.getRows()) {
                childRefParent.add(baseDataDO.getParentcode());
            }
        }
        Iterator<Object> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            BaseDataDO data;
            data.put("isLeaf", (Object)(!childRefParent.contains((data = (BaseDataDO)iterator.next()).getCode()) ? 1 : 0));
        }
    }

    private void parentStopFlag(List<BaseDataDO> dataList, BaseDataDTO param) {
        if (param.getStopflag() == null || param.getStopflag() != 1) {
            return;
        }
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        BaseDataDefineDO defineDO = this.baseDataParamService.getBaseDataDefineDO((BaseDataDO)param);
        int defineStructtype = defineDO.getStructtype();
        if (defineStructtype == 0 || defineStructtype == 1) {
            return;
        }
        BaseDataDTO lfParam = new BaseDataDTO();
        lfParam.setTableName(param.getTableName());
        lfParam.setUnitcode(param.getUnitcode());
        lfParam.setVersionDate(param.getVersionDate());
        lfParam.setAuthType(BaseDataOption.AuthType.NONE);
        if (param.getStopflag() != null && param.getStopflag() == 1) {
            lfParam.setStopflag(Integer.valueOf(-1));
        } else {
            lfParam.setStopflag(param.getStopflag());
        }
        if (param.containsKey((Object)"sharefields")) {
            lfParam.put("sharefields", param.get((Object)"sharefields"));
            for (Object field : (List)param.get((Object)"sharefields")) {
                lfParam.put((String)field, param.get(field));
            }
        }
        if (param.containsKey((Object)"shareUnitcodes")) {
            lfParam.put("shareUnitcodes", param.get((Object)"shareUnitcodes"));
        }
        ArrayList<String> codes = new ArrayList<String>();
        for (BaseDataDO data : dataList) {
            codes.add(data.getParentcode());
        }
        lfParam.setBaseDataCodes(codes);
        HashSet<String> childRefParent = new HashSet<String>();
        PageVO<BaseDataDO> allDataPage = this.listAnyWay(lfParam);
        if (allDataPage != null && allDataPage.getTotal() > 0) {
            for (BaseDataDO baseDataDO : allDataPage.getRows()) {
                if (baseDataDO.getStopflag() != 1) continue;
                childRefParent.add(baseDataDO.getCode());
            }
        }
        for (BaseDataDO data : dataList) {
            data.put("isParentStop", (Object)childRefParent.contains(data.getParentcode()));
        }
    }

    private void loadRelateData(List<BaseDataDO> datas, BaseDataDTO param) {
        List cols = null;
        Object refShow = param.getExtInfo("RefShowCode");
        if (refShow != null && refShow.toString().startsWith("[{")) {
            cols = JSONUtil.parseArray((String)refShow.toString(), DataModelColumn.class);
        }
        HashMap<String, Map<String, Object>> fieldProps = null;
        if (cols == null) {
            JsonNode defaultShowFields;
            BaseDataDefineDO define = this.baseDataParamService.getBaseDataDefineDO((BaseDataDO)param);
            ObjectNode defineJson = JSONUtil.parseObject((String)define.getDefine());
            if (define.getDummyflag() != null && define.getDummyflag() == 1) {
                JsonNode columnsArr = defineJson.get("columns");
                cols = JSONUtil.parseArray((String)columnsArr.toString(), DataModelColumn.class);
            } else if (defineJson.has("showFields")) {
                JsonNode showFields = defineJson.get("showFields");
                cols = JSONUtil.parseArray((String)showFields.toString(), DataModelColumn.class);
            } else if (defineJson.has("defaultShowFields") && (defaultShowFields = defineJson.get("defaultShowFields")) != null) {
                cols = JSONUtil.parseArray((String)defaultShowFields.toString(), DataModelColumn.class);
            }
            if (defineJson.has("fieldProps")) {
                fieldProps = new HashMap<String, Map<String, Object>>();
                List propList = JSONUtil.parseMapArray((String)defineJson.get("fieldProps").toString());
                for (Map map : propList) {
                    fieldProps.put(map.get("columnName").toString().toLowerCase(), map);
                }
            }
        }
        if (cols == null || cols.isEmpty()) {
            return;
        }
        HashMap<String, String> colMapping = new HashMap<String, String>();
        HashMap<String, Integer> colType = new HashMap<String, Integer>();
        HashMap<String, Map<String, String>> tempTableData = new HashMap<String, Map<String, String>>();
        HashMap<String, String> relateDefineShowType = new HashMap<String, String>();
        BaseDataDefineDTO defineParam = new BaseDataDefineDTO();
        defineParam.setTenantName(param.getTenantName());
        defineParam.setDeepClone(Boolean.valueOf(false));
        String columnName = null;
        String mapping = null;
        String colRefTablename = null;
        Integer mappingType = null;
        BaseDataDefineDO relateDefine = null;
        for (DataModelColumn column : cols) {
            columnName = column.getColumnName().toLowerCase();
            mapping = column.getMapping();
            if (!"parentcode".equals(columnName) && !StringUtils.hasText(mapping)) continue;
            if ("parentcode".equals(columnName)) {
                colRefTablename = param.getTableName();
                mappingType = 1;
            } else {
                colRefTablename = mapping.split("\\.")[0];
                mappingType = column.getMappingType();
            }
            if (refShow != null) {
                if (mappingType == 4) {
                    relateDefineShowType.put(colRefTablename, "REFCODE&NAME");
                } else {
                    relateDefineShowType.put(colRefTablename, "CODE&NAME");
                }
            } else if (!relateDefineShowType.containsKey(colRefTablename)) {
                if (mappingType == 1) {
                    defineParam.setName(colRefTablename);
                    relateDefine = this.baseDataDefineService.get(defineParam);
                    relateDefineShowType.put(colRefTablename, relateDefine != null ? relateDefine.getShowtype() : null);
                } else {
                    relateDefineShowType.put(colRefTablename, "NAME");
                }
            }
            colMapping.put(columnName, colRefTablename);
            colType.put(columnName, mappingType);
            if (tempTableData.containsKey(colRefTablename)) continue;
            tempTableData.put(colRefTablename, new HashMap());
        }
        if (!colMapping.isEmpty()) {
            this.loadRelateData(datas, param, colMapping, colType, relateDefineShowType, fieldProps, tempTableData);
        }
    }

    private void loadRelateData(List<BaseDataDO> datas, BaseDataDTO param, Map<String, String> colMapping, Map<String, Integer> colType, Map<String, String> relateDefineShowType, Map<String, Map<String, Object>> fieldProps, Map<String, Map<String, String>> tempTableData) {
        boolean canIndex = false;
        Map colIndexMap = null;
        BaseDataDO tempData = datas.get(0);
        if (tempData instanceof BaseDataCacheDO) {
            canIndex = true;
            colIndexMap = BaseDataContext.getColIndexMap((String)tempData.getTenantName(), (String)tempData.getTableName());
        }
        HashMap<String, String> showTitleMap = null;
        Object value = null;
        String relateName = null;
        String refTbName = null;
        Integer refColType = null;
        String refShowType = null;
        String driveField = null;
        String colName = null;
        for (BaseDataDO data : datas) {
            showTitleMap = new HashMap<String, String>();
            for (Map.Entry<String, String> entry : colMapping.entrySet()) {
                Map<String, Object> fieldPropInfo;
                colName = entry.getKey();
                if (canIndex) {
                    if (!colIndexMap.containsKey(colName)) continue;
                    value = ((BaseDataCacheDO)data).getFieldValue((Object)colName, ((Integer)colIndexMap.get(colName)).intValue(), true);
                } else {
                    value = data.get((Object)colName);
                }
                if (value == null) continue;
                refTbName = entry.getValue();
                refColType = colType.get(colName);
                refShowType = relateDefineShowType.get(refTbName);
                driveField = null;
                if (fieldProps != null && !fieldProps.isEmpty() && (fieldPropInfo = fieldProps.get(colName)) != null && fieldPropInfo.get("driveField") != null) {
                    driveField = fieldPropInfo.get("driveField").toString();
                }
                if (value instanceof List || value.toString().contains(",")) {
                    List<String> codeList = null;
                    codeList = value instanceof List ? (List<String>)value : Arrays.asList(value.toString().split(","));
                    if (codeList == null || codeList.isEmpty()) continue;
                    StringBuilder strBuilder = new StringBuilder();
                    for (String code : codeList) {
                        if (!StringUtils.hasText(code) || (relateName = this.getRelateName(code, param, colName, refTbName, refColType, refShowType, driveField, tempTableData, data)) == null) continue;
                        strBuilder.append(",").append(relateName);
                    }
                    showTitleMap.put(colName, strBuilder.length() > 0 ? strBuilder.substring(1) : "");
                    continue;
                }
                if (!StringUtils.hasText(value.toString())) continue;
                relateName = this.getRelateName(value.toString(), param, colName, refTbName, refColType, refShowType, driveField, tempTableData, data);
                showTitleMap.put(colName, relateName);
            }
            data.put("showTitleMap", showTitleMap);
        }
    }

    private String getRelateName(String code, BaseDataDTO param, String colName, String refTableName, Integer colType, String relateDefineShowType, String driveField, Map<String, Map<String, String>> tempTableData, BaseDataDO currData) {
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
            if (StringUtils.hasText(driveField)) {
                basedataDTO.setUnitcode((String)currData.get((Object)driveField.toLowerCase()));
            } else {
                this.baseDataParamService.initDefineInfo((BaseDataDO)basedataDTO);
                if (basedataDTO.get((Object)"sharefields") != null) {
                    List sharefields = (List)basedataDTO.get((Object)"sharefields");
                    for (String field : sharefields) {
                        basedataDTO.put(field, param.get((Object)field));
                    }
                }
            }
            basedataDTO.setStopflag(Integer.valueOf(-1));
            basedataDTO.setAuthType(BaseDataOption.AuthType.NONE);
            basedataDTO.setVersionDate(versionDate);
            if ("parentcode".equals(colName)) {
                basedataDTO.setCode(code);
            } else {
                basedataDTO.setObjectcode(code);
            }
            basedataDTO.setShowType(relateDefineShowType);
            PageVO<BaseDataDO> dataList = this.list(basedataDTO);
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
            PageVO orgs = this.orgDataClient.list(orgDTO);
            if (orgs != null && orgs.getTotal() > 0) {
                rsName = ((OrgDO)orgs.getRows().get(0)).getShowTitle();
            }
        } else if (colType == 2) {
            EnumDataDTO enumDataDTO = new EnumDataDTO();
            enumDataDTO.setTenantName(tenantName);
            enumDataDTO.setBiztype(refTableName);
            enumDataDTO.setVal(code);
            enumDataDTO.addExtInfo("languageTransFlag", (Object)true);
            EnumDataDO enumData = this.enumDataService.get(enumDataDTO);
            if (enumData != null) {
                rsName = "CODE&NAME".equals(relateDefineShowType) ? enumData.getVal() + " " + enumData.getTitle() : enumData.getTitle();
            }
        } else if (colType == 3) {
            UserDTO userDTO = new UserDTO();
            userDTO.setTenantName(tenantName);
            userDTO.setId(code);
            userDTO.addExtInfo("onlyNeedBasicInfo", (Object)true);
            UserDO user = this.authUserClient.get(userDTO);
            if (user != null && user.getName() != null) {
                rsName = "CODE&NAME".equals(relateDefineShowType) ? user.getUsername() + " " + user.getName() : user.getName();
            }
        }
        tempTableData.get(refTableName).put(code, rsName);
        return rsName;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<BaseDataDO> verDiffList(BaseDataDTO basedataDTO) {
        ArrayList<BaseDataDO> dataList = new ArrayList<BaseDataDO>();
        BaseDataContext.bindColIndex((String)basedataDTO.getTenantName(), (String)basedataDTO.getTableName());
        try {
            LinkedList<BaseDataCacheDO> list = this.baseDataDao.selectGreaterVer(basedataDTO);
            if (!list.isEmpty()) {
                this.baseDataDetailService.loadDetailData((BaseDataDO)basedataDTO, list);
                dataList.addAll(list);
            }
        }
        catch (Exception e) {
            logger.error("\u57fa\u7840\u6570\u636e\u67e5\u8be2\u5f02\u5e38", e);
        }
        finally {
            BaseDataContext.unbindColIndex();
        }
        return dataList;
    }

    public Map<String, Object[]> columnValueList(BaseDataColumnValueDTO param) {
        BaseDataDTO queryParam = param.getQueryParam();
        String tableName = queryParam.getTableName();
        if (!this.coordinationService.isCanLoadByCurrentNode(tableName)) {
            BaseDataClient client = this.coordinationService.getClient(tableName);
            if (client != null) {
                return client.columnValueList(param);
            }
            throw new RuntimeException("Unsupported Operation");
        }
        HashMap<String, Object[]> map = new HashMap<String, Object[]>();
        PageVO<BaseDataDO> page = this.list(queryParam);
        if (page == null || page.getTotal() == 0) {
            return map;
        }
        Map indexMap = BaseDataContext.getColIndexMap((String)param.getTenantName(), (String)queryParam.getTableName());
        String[] cols = param.getColumns();
        Object val = null;
        for (BaseDataDO data : page.getRows()) {
            Object[] vals = new Object[cols.length];
            for (int i = 0; i < cols.length; ++i) {
                vals[i] = data instanceof BaseDataCacheDO ? (val = ((BaseDataCacheDO)data).getFieldValue((Object)cols[i], ((Integer)indexMap.get(cols[i])).intValue(), !param.isSensitive())) : data.get((Object)cols[i]);
            }
            map.put(data.getObjectcode(), vals);
        }
        return map;
    }
}

