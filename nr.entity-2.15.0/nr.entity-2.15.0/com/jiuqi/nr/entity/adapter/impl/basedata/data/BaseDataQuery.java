/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryParentType
 *  com.jiuqi.va.domain.basedata.handle.BaseDataColumnValueDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  org.apache.commons.lang3.ArrayUtils
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.data;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.entity.adapter.impl.basedata.BaseDataResultSet;
import com.jiuqi.nr.entity.adapter.impl.basedata.data.CommonBaseDataQuery;
import com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider;
import com.jiuqi.nr.entity.adapter.provider.QueryOptions;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataColumnValueDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class BaseDataQuery
extends CommonBaseDataQuery
implements IDataQueryProvider {
    private static final Logger logger = LoggerFactory.getLogger("com.jiuqi.nr.entity.logger");
    private final IEntityQueryParam queryParam;
    private final BaseDataClient baseDataClient;
    private Boolean isTree;

    public BaseDataQuery(BaseDataDefineClient baseDataDefineClient, IEntityQueryParam queryParam, BaseDataClient baseDataClient) {
        super(baseDataDefineClient);
        this.queryParam = queryParam;
        this.baseDataClient = baseDataClient;
    }

    @Override
    public EntityResultSet getAllData() {
        BaseDataDTO baseDataFilter = this.getBaseDataFilter(this.queryParam);
        return this.getEntityResultSetByFilter(baseDataFilter);
    }

    @Override
    public EntityResultSet simpleQueryByKeys() {
        Map<String, Object> ext;
        BaseDataDTO baseDataFilter = new BaseDataDTO();
        baseDataFilter.setTableName(this.queryParam.getEntityId());
        baseDataFilter.setAuthType(BaseDataOption.AuthType.NONE);
        baseDataFilter.setIgnoreShareFields(Boolean.valueOf(true));
        baseDataFilter.setVersionDate(this.queryParam.getVersionDate());
        baseDataFilter.setLeafFlag(Boolean.valueOf(this.queryParam.isMarkLeaf()));
        baseDataFilter.setLazyLoad(Boolean.valueOf(this.queryParam.isLazy()));
        this.setIsolation(this.queryParam, baseDataFilter);
        List<String> masterKey = this.queryParam.getMasterKey();
        if (masterKey != null) {
            if (masterKey.size() == 1) {
                baseDataFilter.setObjectcode(masterKey.get(0));
            } else {
                baseDataFilter.setBaseDataObjectcodes(masterKey);
            }
        }
        if ((ext = this.queryParam.getExt()) != null) {
            baseDataFilter.putAll(ext);
        }
        return this.getEntityResultSetByFilter(baseDataFilter);
    }

    @Override
    public EntityResultSet getRootData() {
        BaseDataDTO baseDataFilter = this.getBaseDataFilter(this.queryParam);
        baseDataFilter.setParentcode("-");
        baseDataFilter.setLazyLoad(Boolean.valueOf(true));
        PageVO<BaseDataDO> page = this.executeQuery(baseDataFilter);
        return new BaseDataResultSet(page, this.baseDataClient, this.baseDataDefineClient, this.queryParam);
    }

    @Override
    public EntityResultSet getChildData(QueryOptions.TreeType treeType, String ... queryKeys) {
        BaseDataOption.QueryChildrenType type = this.getTreeType(treeType);
        if (ArrayUtils.isEmpty((Object[])queryKeys)) {
            return null;
        }
        if (!this.isTree()) {
            return new BaseDataResultSet((PageVO<BaseDataDO>)new PageVO(true), this.baseDataClient, this.baseDataDefineClient, this.queryParam);
        }
        EntityResultSet rs = null;
        for (String queryKey : queryKeys) {
            EntityResultSet children = this.getChildren(type, queryKey);
            if (rs == null) {
                rs = children;
                continue;
            }
            rs.merge(children);
        }
        return rs;
    }

    private BaseDataOption.QueryChildrenType getTreeType(QueryOptions.TreeType treeType) {
        BaseDataOption.QueryChildrenType type = BaseDataOption.QueryChildrenType.DIRECT_CHILDREN;
        if (QueryOptions.TreeType.ALL_CHILDREN.equals((Object)treeType)) {
            type = BaseDataOption.QueryChildrenType.ALL_CHILDREN;
        }
        return type;
    }

    @Override
    public EntityResultSet findByEntityKeys() {
        return this.getAllData();
    }

    @Override
    public String getParent(String queryCode) {
        if (!this.isTree()) {
            return null;
        }
        BaseDataDTO baseDataFilter = this.getBaseDataFilter(this.queryParam);
        baseDataFilter.setObjectcode(queryCode);
        baseDataFilter.setQueryParentType(BaseDataOption.QueryParentType.DIRECT_PARENT);
        PageVO page = this.baseDataClient.list(baseDataFilter);
        if (page.getTotal() > 0) {
            return ((BaseDataDO)page.getRows().get(0)).getObjectcode();
        }
        return null;
    }

    @Override
    public String getParent(Map<String, Object> rowData) {
        if (rowData == null) {
            throw new IllegalArgumentException("'rowData' must not be null.");
        }
        if (!this.isTree()) {
            return null;
        }
        String cacheNameBaseDataDefine = "NR_ENTITY_BASEDATA_DEFINE";
        ContextExtension baseDataDefinecache = NpContextHolder.getContext().getExtension("NR_ENTITY_BASEDATA_DEFINE");
        BaseDataDefineDO baseDataDefine = (BaseDataDefineDO)baseDataDefinecache.get(this.queryParam.getEntityId());
        if (baseDataDefine == null) {
            baseDataDefine = this.getBasedataDefineByCode(this.queryParam.getEntityId());
            if (baseDataDefine == null) {
                throw new RuntimeException("basedata '" + this.queryParam.getEntityId() + "' not found.");
            }
            baseDataDefinecache.put(this.queryParam.getEntityId(), (Serializable)baseDataDefine);
        }
        if (baseDataDefine.getSharetype() == 0) {
            Object parent = rowData.get("PARENTCODE".toLowerCase(Locale.ROOT));
            if (parent == null) {
                return null;
            }
            return parent.toString();
        }
        return this.getParent(rowData.get("OBJECTCODE".toLowerCase(Locale.ROOT)).toString());
    }

    @Override
    public int getMaxDepth() {
        return this.getMaxDepth("-");
    }

    @Override
    public int getMaxDepthByEntityKey(String entityKeyData) {
        return this.getMaxDepth(entityKeyData);
    }

    @Override
    public EntityResultSet findByCodes() {
        return this.getAllData();
    }

    @Override
    public int getChildCount(String entityKeyData, QueryOptions.TreeType treeType) {
        if (!this.isTree()) {
            return 0;
        }
        return this.getChildrenCount(entityKeyData, this.getTreeType(treeType));
    }

    @Override
    public Map<String, Integer> getChildCountByParent(String parentKey, QueryOptions.TreeType treeType) {
        if (!this.isTree()) {
            return new HashMap<String, Integer>();
        }
        return this.getChildrenCountOfChildren(parentKey, this.getTreeType(treeType));
    }

    @Override
    public String[] getParentsEntityKeyDataPath(String entityKeyData) {
        if (!this.isTree()) {
            return new String[0];
        }
        ArrayList<String> path = new ArrayList<String>();
        this.getParentPath(path, entityKeyData);
        Collections.reverse(path);
        return path.toArray(new String[0]);
    }

    private void getParentPath(List<String> parent, String objectCode) {
        String queryParent = this.getParent(objectCode);
        if (StringUtils.hasText(queryParent)) {
            parent.add(queryParent);
            this.getParentPath(parent, queryParent);
        }
    }

    @Override
    public String[] getParentsEntityKeyDataPath(Map<String, Object> rowData) {
        if (rowData == null) {
            throw new IllegalArgumentException("'rowData' must not be null.");
        }
        if (!this.isTree()) {
            return new String[0];
        }
        String cacheNameBaseDataDefine = "NR_ENTITY_BASEDATA_DEFINE";
        ContextExtension baseDataDefinecache = NpContextHolder.getContext().getExtension("NR_ENTITY_BASEDATA_DEFINE");
        BaseDataDefineDO baseDataDefine = (BaseDataDefineDO)baseDataDefinecache.get(this.queryParam.getEntityId());
        if (baseDataDefine == null) {
            baseDataDefine = this.getBasedataDefineByCode(this.queryParam.getEntityId());
            if (baseDataDefine == null) {
                throw new RuntimeException("basedata '" + this.queryParam.getEntityId() + "' not found.");
            }
            baseDataDefinecache.put(this.queryParam.getEntityId(), (Serializable)baseDataDefine);
        }
        if (baseDataDefine.getSharetype() == 0) {
            Object parents = rowData.get("PARENTS".toLowerCase(Locale.ROOT));
            if (parents == null) {
                return null;
            }
            String parentsVal = parents.toString();
            if (StringUtils.hasText(parentsVal)) {
                return parentsVal.split("/");
            }
            return null;
        }
        return this.getParentsEntityKeyDataPath(rowData.get("OBJECTCODE".toLowerCase(Locale.ROOT)).toString());
    }

    @Override
    public int getTotalCount() {
        BaseDataDTO baseDataFilter = this.getBaseDataFilter(this.queryParam);
        return this.baseDataClient.count(baseDataFilter);
    }

    public int getMaxDepth(String objectCode) {
        int zeroDepth = 0;
        if (ObjectUtils.isEmpty(this.queryParam.getEntityId())) {
            return zeroDepth;
        }
        if (!this.isTree()) {
            return zeroDepth + 1;
        }
        BaseDataDTO baseDataFilter = this.getBaseDataFilter(this.queryParam);
        if (!"-".equals(objectCode)) {
            baseDataFilter.setQueryChildrenType(BaseDataOption.QueryChildrenType.ALL_CHILDREN);
            baseDataFilter.setLazyLoad(Boolean.valueOf(false));
            baseDataFilter.setObjectcode(objectCode);
        }
        baseDataFilter.setOrderBy(Collections.emptyList());
        PageVO page = this.baseDataClient.list(baseDataFilter);
        if (page.getTotal() == 0) {
            return zeroDepth;
        }
        Optional<Integer> maxDepth = page.getRows().stream().max((d1, d2) -> {
            int length1 = StringUtils.hasLength(d1.getParents()) ? d1.getParents().split("/").length : 0;
            int length2 = StringUtils.hasLength(d2.getParents()) ? d2.getParents().split("/").length : 0;
            return length1 - length2;
        }).map(baseData -> baseData.getParents().split("/").length);
        return maxDepth.orElse(0) - this.getDepthByObjectCode(objectCode) + zeroDepth;
    }

    private BaseDataDefineDO getDataDefine() {
        BaseDataDefineDTO filterCond = new BaseDataDefineDTO();
        filterCond.setName(this.queryParam.getEntityId());
        return this.baseDataDefineClient.get(filterCond);
    }

    private int getDepthByObjectCode(String objectCode) {
        BaseDataDO baseData = this.getByObjectCode(objectCode);
        return baseData == null ? 0 : baseData.getParents().split("/").length;
    }

    private BaseDataDO getByObjectCode(String objectCode) {
        if ("-".equals(objectCode)) {
            return null;
        }
        BaseDataDTO baseDataFilter = this.getBaseDataFilter(this.queryParam);
        baseDataFilter.setExpression(null);
        baseDataFilter.setObjectcode(objectCode);
        PageVO page = this.baseDataClient.list(baseDataFilter);
        if (page.getTotal() == 0) {
            return null;
        }
        if (page.getTotal() > 1) {
            throw new RuntimeException("\u67e5\u8be2\u5230\u591a\u4e2aOBJECTCODEE\u4e3a\u201c" + objectCode + "\u201d\u7684\u57fa\u7840\u6570\u636e\u3002");
        }
        return (BaseDataDO)page.getRows().get(0);
    }

    public EntityResultSet getChildren(BaseDataOption.QueryChildrenType queryChildrenType, String objectCode) {
        BaseDataDTO baseDataFilter = this.getBaseDataFilter(this.queryParam);
        if (queryChildrenType == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN) {
            if (baseDataFilter.isLazyLoad()) {
                baseDataFilter.setParentcode(objectCode);
            } else {
                baseDataFilter.setObjectcode(objectCode);
            }
        } else {
            baseDataFilter.setLazyLoad(Boolean.valueOf(false));
            baseDataFilter.setObjectcode(objectCode);
        }
        baseDataFilter.setQueryChildrenType(queryChildrenType);
        return this.getEntityResultSetByFilter(baseDataFilter);
    }

    private EntityResultSet getEntityResultSetByFilter(BaseDataDTO baseDataFilter) {
        return new BaseDataResultSet(this.executeQuery(baseDataFilter), this.baseDataClient, this.baseDataDefineClient, this.queryParam);
    }

    private PageVO<BaseDataDO> executeQuery(BaseDataDTO baseDataFilter) {
        PageVO<BaseDataDO> page = this.query(baseDataFilter);
        List rows = page.getRows();
        this.afterQuery(rows, this.queryParam);
        page.setRows(rows);
        return page;
    }

    private PageVO<BaseDataDO> query(BaseDataDTO baseDataFilter) {
        PageVO list = this.baseDataClient.list(baseDataFilter);
        if (!this.queryParam.isMaskingData()) {
            return list;
        }
        this.queryMaskedData(baseDataFilter, (PageVO<BaseDataDO>)list);
        return list;
    }

    private void queryMaskedData(BaseDataDTO baseDataFilter, PageVO<BaseDataDO> list) {
        Map fields = (Map)list.getRs().get((Object)"sensitiveFields");
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }
        BaseDataColumnValueDTO param = new BaseDataColumnValueDTO();
        BaseDataDTO unMaskedParam = new BaseDataDTO();
        unMaskedParam.setTableName(baseDataFilter.getTableName());
        unMaskedParam.setVersionDate(baseDataFilter.getVersionDate());
        unMaskedParam.setBaseDataObjectcodes(list.getRows().stream().map(BaseDataDO::getObjectcode).collect(Collectors.toList()));
        param.setQueryParam(unMaskedParam);
        String[] columns = new ArrayList(fields.keySet()).toArray(new String[0]);
        HashMap<String, Integer> columnMap = new HashMap<String, Integer>();
        for (int i = 0; i < columns.length; ++i) {
            columnMap.put(columns[i], i);
        }
        param.setColumns(columns);
        param.setSensitive(true);
        Map sourceValue = this.baseDataClient.columnValueList(param);
        list.getRs().put("CACHE_UNMASKED", (Object)sourceValue);
        list.getRs().put("CACHE_UNMASKED_COLUMN", columnMap);
    }

    public int getChildrenCount(String objectCode, BaseDataOption.QueryChildrenType queryChildrenType) {
        BaseDataDTO baseDataFilter = this.getBaseDataFilter(this.queryParam);
        baseDataFilter.setQueryChildrenType(queryChildrenType);
        if (queryChildrenType == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN) {
            if (baseDataFilter.isLazyLoad()) {
                baseDataFilter.setParentcode(objectCode);
            } else {
                baseDataFilter.setObjectcode(objectCode);
            }
        } else {
            baseDataFilter.setLazyLoad(Boolean.valueOf(false));
            baseDataFilter.setObjectcode(objectCode);
        }
        baseDataFilter.setOrderBy(Collections.emptyList());
        return this.baseDataClient.count(baseDataFilter);
    }

    public Map<String, Integer> getChildrenCountOfChildren(String objectCode, BaseDataOption.QueryChildrenType queryChildrenType) {
        BaseDataDTO baseDataFilter = this.getBaseDataFilter(this.queryParam);
        baseDataFilter.setQueryChildrenType(BaseDataOption.QueryChildrenType.DIRECT_CHILDREN);
        if (baseDataFilter.isLazyLoad()) {
            baseDataFilter.setParentcode(objectCode);
        } else {
            baseDataFilter.setObjectcode(objectCode);
        }
        PageVO page = this.baseDataClient.list(baseDataFilter);
        return page.getRows().stream().collect(Collectors.toMap(BaseDataDO::getObjectcode, baseData -> this.getChildrenCount(baseData.getObjectcode(), queryChildrenType)));
    }

    private boolean isTree() {
        if (this.isTree != null) {
            return this.isTree;
        }
        BaseDataDefineDO dataDefine = this.getDataDefine();
        this.isTree = dataDefine.getStructtype() != 0;
        return this.isTree;
    }
}

