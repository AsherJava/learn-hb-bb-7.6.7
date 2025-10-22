/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 */
package com.jiuqi.nr.entity.adapter.impl.org.data.query;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.entity.adapter.impl.AbstractDataQueryHelper;
import com.jiuqi.nr.entity.adapter.impl.org.OrgResultSet;
import com.jiuqi.nr.entity.adapter.impl.org.data.OrgDataSource;
import com.jiuqi.nr.entity.adapter.impl.org.util.OrgConvertUtil;
import com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider;
import com.jiuqi.nr.entity.adapter.provider.QueryOptions;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class OrgDataQuery
extends AbstractDataQueryHelper<OrgDO>
implements IDataQueryProvider {
    protected final OrgDataSource orgDataSource;
    protected IEntityQueryParam query;

    public OrgDataQuery(OrgDataSource orgDataSource, IEntityQueryParam query) {
        this.orgDataSource = orgDataSource;
        this.query = query;
    }

    protected OrgDTO getParam() {
        return OrgConvertUtil.paramConvert(this.query);
    }

    @Override
    public EntityResultSet getAllData() {
        PageVO<OrgDO> list = this.orgDataSource.list(this.getParam());
        List rows = list.getRows();
        this.query.getQueryContext().getLogger().accept(e -> {
            if (e.isTraceEnabled()) {
                String codes = rows.stream().map(OrgDO::getCode).collect(Collectors.joining(","));
                e.trace("getAllRows-count\uff1a{}\uff0ccode\u5217\u8868\uff1a{}", rows.size(), codes);
            }
        });
        this.afterQuery(rows, this.query);
        return this.convert(rows, list.getRs());
    }

    @Override
    public EntityResultSet simpleQueryByKeys() {
        ArrayList<OrgDO> rows = new ArrayList<OrgDO>();
        List<String> masterKey = this.query.getMasterKey();
        if (CollectionUtils.isEmpty(masterKey)) {
            return this.convert(rows, null);
        }
        for (String key : masterKey) {
            OrgDO orgDO;
            Map<String, Object> ext;
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setCategoryname(this.query.getEntityId());
            orgDTO.setCode(key);
            orgDTO.setCacheSyncDisable(Boolean.valueOf(true));
            orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
            orgDTO.setVersionDate(this.query.getVersionDate());
            orgDTO.setLazyLoad(Boolean.valueOf(this.query.isLazy()));
            orgDTO.setLeafFlag(Boolean.valueOf(this.query.isMarkLeaf()));
            if (this.query.getQueryStop() != null) {
                orgDTO.setStopflag(this.query.getQueryStop());
            }
            if ((ext = this.query.getExt()) != null) {
                orgDTO.putAll(ext);
            }
            if ((orgDO = this.orgDataSource.get(orgDTO)) == null) continue;
            String localName = NpContextHolder.getContext().getLocale().toLanguageTag().toLowerCase().replace("-", "_");
            Object localValue = orgDO.get((Object)("name_" + localName));
            if (localValue != null) {
                orgDO.setLocalizedName(localValue.toString());
            } else {
                orgDO.setLocalizedName(orgDO.getName());
            }
            rows.add(orgDO);
        }
        return this.convert(rows, null);
    }

    @Override
    public EntityResultSet getRootData() {
        OrgDTO param = this.getParam();
        param.setParentcode("-");
        param.setLazyLoad(Boolean.valueOf(true));
        PageVO<OrgDO> list = this.orgDataSource.list(param);
        List rows = list.getRows();
        this.afterQuery(rows, this.query);
        return this.convert(rows, list.getRs());
    }

    @Override
    public EntityResultSet getChildData(QueryOptions.TreeType treeType, String ... queryKeys) {
        if (queryKeys == null || queryKeys.length == 0) {
            return null;
        }
        if (QueryOptions.TreeType.DIRECT_CHILDREN.equals((Object)treeType)) {
            PageVO<OrgDO> list = this.internalGetChildRows(queryKeys);
            List rows = list.getRows();
            this.afterQuery(rows, this.query);
            return this.convert(rows, list.getRs());
        }
        if (QueryOptions.TreeType.ALL_CHILDREN.equals((Object)treeType)) {
            OrgDTO param = this.getParam();
            param.setLazyLoad(Boolean.valueOf(false));
            param.setCode(queryKeys[0]);
            param.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN);
            PageVO<OrgDO> list = this.orgDataSource.list(param);
            List rows = list.getRows();
            this.afterQuery(rows, this.query);
            return this.convert(rows, list.getRs());
        }
        return null;
    }

    public PageVO<OrgDO> internalGetChildRows(String ... entityKeyData) {
        OrgDTO param = this.getParam();
        if (entityKeyData.length > 1) {
            param.setOrgCodes(Arrays.asList(entityKeyData));
            param.setQueryChildrenType(OrgDataOption.QueryChildrenType.DIRECT_CHILDREN);
        } else {
            param.setParentcode(entityKeyData[0]);
        }
        param.setLazyLoad(Boolean.valueOf(true));
        return this.orgDataSource.list(param);
    }

    @Override
    public EntityResultSet findByEntityKeys() {
        OrgDTO param = this.getParam();
        param.setOrgCodes(this.query.getMasterKey());
        PageVO<OrgDO> list = this.orgDataSource.list(param);
        List rows = list.getRows();
        this.afterQuery(rows, this.query);
        return this.convert(rows, list.getRs());
    }

    @Override
    public String getParent(String queryCode) {
        OrgDTO param = this.getParam();
        param.setCode(queryCode);
        PageVO<OrgDO> list = this.orgDataSource.list(param);
        if (list.getTotal() > 0) {
            return ((OrgDO)list.getRows().get(0)).getParentcode();
        }
        return null;
    }

    @Override
    public String getParent(Map<String, Object> rowData) {
        Object parentValue = rowData.get("PARENTCODE".toLowerCase(Locale.ROOT));
        if (parentValue == null) {
            return null;
        }
        return parentValue.toString();
    }

    @Override
    public int getMaxDepth() {
        OrgDTO orgDTO = this.getParam();
        orgDTO.setOrderBy(Collections.emptyList());
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.BASIC);
        PageVO<OrgDO> list = this.orgDataSource.list(orgDTO);
        int len = 0;
        List rows = list.getRows();
        Set codeSet = rows.stream().map(OrgDO::getCode).collect(Collectors.toSet());
        if (list.getTotal() > 0) {
            for (OrgDO org : rows) {
                String[] parents = org.getParents().split("/");
                List<String> path = Arrays.asList(parents);
                List filterPath = path.stream().filter(codeSet::contains).collect(Collectors.toList());
                if (len >= filterPath.size()) continue;
                len = filterPath.size();
            }
        }
        return len;
    }

    @Override
    public int getMaxDepthByEntityKey(String entityKeyData) {
        OrgDTO param = this.getParam();
        param.setQueryDataStructure(OrgDataOption.QueryDataStructure.BASIC);
        param.setCode(entityKeyData);
        param.setOrderBy(Collections.emptyList());
        PageVO<OrgDO> list = this.orgDataSource.listSubordinate(param);
        int len = 0;
        int orgLen = 1;
        if (list.getTotal() > 0) {
            List rows = list.getRows();
            Set codeSet = rows.stream().map(OrgDO::getCode).collect(Collectors.toSet());
            for (OrgDO org : rows) {
                String[] parents = org.getParents().split("/");
                List<String> path = Arrays.asList(parents);
                List filterPath = path.stream().filter(codeSet::contains).collect(Collectors.toList());
                if (len < filterPath.size()) {
                    len = filterPath.size();
                }
                if (!org.getCode().equals(entityKeyData)) continue;
                orgLen = filterPath.size() - 1;
            }
        }
        return len - orgLen;
    }

    @Override
    public EntityResultSet findByCodes() {
        OrgDTO param = this.getParam();
        param.setOrgOrgcodes(this.query.getCodes());
        PageVO<OrgDO> list = this.orgDataSource.list(param);
        List rows = list.getRows();
        this.afterQuery(rows, this.query);
        return this.convert(rows, list.getRs());
    }

    @Override
    public int getChildCount(String entityKeyData, QueryOptions.TreeType treeType) {
        OrgDTO param = this.getParam();
        param.setOrderBy(Collections.emptyList());
        if (QueryOptions.TreeType.DIRECT_CHILDREN.equals((Object)treeType)) {
            param.setParentcode(entityKeyData);
        } else {
            param.setLazyLoad(Boolean.valueOf(false));
            param.setCode(entityKeyData);
            param.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN);
        }
        return this.orgDataSource.count(param);
    }

    @Override
    public Map<String, Integer> getChildCountByParent(String parentKey, QueryOptions.TreeType treeType) {
        PageVO<OrgDO> pageVO = this.internalGetChildRows(parentKey);
        List childRows = pageVO.getRows();
        HashMap<String, Integer> data = new HashMap<String, Integer>(childRows.size());
        if (childRows.size() > 0) {
            childRows.forEach(org -> {
                int count = 0;
                Object hasChildren = org.get((Object)"hasChildren");
                if (hasChildren != null && Boolean.parseBoolean(hasChildren.toString())) {
                    count = this.getChildCount(org.getCode(), treeType);
                }
                data.put(org.getCode(), count);
            });
        }
        return data;
    }

    @Override
    public String[] getParentsEntityKeyDataPath(String entityKeyData) {
        OrgDTO param = this.getParam();
        param.setQueryDataStructure(OrgDataOption.QueryDataStructure.BASIC);
        param.setCode(entityKeyData);
        PageVO<OrgDO> list = this.orgDataSource.list(param);
        if (list.getTotal() > 0) {
            String[] parents = ((OrgDO)list.getRows().get(0)).getParents().split("\\/");
            return Arrays.copyOf(parents, parents.length - 1);
        }
        return new String[0];
    }

    @Override
    public String[] getParentsEntityKeyDataPath(Map<String, Object> rowData) {
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

    @Override
    public int getTotalCount() {
        OrgDTO orgDTO = this.getParam();
        return this.orgDataSource.count(orgDTO);
    }

    @Override
    protected String getKey(OrgDO orgDO) {
        return orgDO.getCode();
    }

    @Override
    protected String getDimensionName(String entityId) {
        return "MD_ORG";
    }

    protected EntityResultSet convert(List<OrgDO> list, R r) {
        if (r == null) {
            r = new R();
        }
        return new OrgResultSet(list, this.query, this.orgDataSource, r);
    }
}

