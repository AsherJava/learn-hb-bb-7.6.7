/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.adapter.impl.AbstractDataQueryHelper
 *  com.jiuqi.nr.entity.adapter.impl.org.OrgResultSet
 *  com.jiuqi.nr.entity.adapter.impl.org.data.OrgDataSource
 *  com.jiuqi.nr.entity.adapter.impl.org.util.OrgConvertUtil
 *  com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider
 *  com.jiuqi.nr.entity.adapter.provider.QueryOptions$TreeType
 *  com.jiuqi.nr.entity.engine.result.EntityResultSet
 *  com.jiuqi.nr.entity.param.IEntityQueryParam
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  org.apache.commons.lang3.ArrayUtils
 */
package com.jiuqi.gcreport.samecontrol.nr.dataquery;

import com.jiuqi.nr.entity.adapter.impl.AbstractDataQueryHelper;
import com.jiuqi.nr.entity.adapter.impl.org.OrgResultSet;
import com.jiuqi.nr.entity.adapter.impl.org.data.OrgDataSource;
import com.jiuqi.nr.entity.adapter.impl.org.util.OrgConvertUtil;
import com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider;
import com.jiuqi.nr.entity.adapter.provider.QueryOptions;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.va.domain.common.PageVO;
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
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class GcSameCtrlOrgDataQuery
extends AbstractDataQueryHelper<OrgDO>
implements IDataQueryProvider {
    protected final OrgDataSource orgDataSource;
    protected IEntityQueryParam query;

    public GcSameCtrlOrgDataQuery(OrgDataSource orgDataSource, IEntityQueryParam query) {
        this.orgDataSource = orgDataSource;
        this.query = query;
    }

    public EntityResultSet getAllData() {
        OrgDTO param = this.getRecoveryFlag();
        PageVO list = this.orgDataSource.list(param);
        List rows = list.getRows();
        this.afterQuery(rows, this.query);
        return this.convert(rows);
    }

    public EntityResultSet simpleQueryByKeys() {
        ArrayList<OrgDO> rows = new ArrayList<OrgDO>();
        List masterKey = this.query.getMasterKey();
        if (CollectionUtils.isEmpty(masterKey)) {
            return this.convert(rows);
        }
        for (String key : masterKey) {
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setCategoryname(this.query.getEntityId());
            orgDTO.setCode(key);
            orgDTO.setCacheSyncDisable(Boolean.valueOf(true));
            orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
            orgDTO.setRecoveryflag(Integer.valueOf(-1));
            orgDTO.setVersionDate(this.query.getVersionDate());
            OrgDO orgDO = this.orgDataSource.get(orgDTO);
            rows.add(orgDO);
        }
        return this.convert(rows);
    }

    public EntityResultSet getRootData() {
        OrgDTO param = this.getRecoveryFlag();
        param.setParentcode("-");
        param.setLazyLoad(Boolean.valueOf(true));
        PageVO list = this.orgDataSource.list(param);
        List rows = list.getRows();
        this.afterQuery(rows, this.query);
        return this.convert(rows);
    }

    public EntityResultSet getChildData(QueryOptions.TreeType treeType, String ... queryKeys) {
        if (ArrayUtils.isEmpty((Object[])queryKeys)) {
            return null;
        }
        if (QueryOptions.TreeType.DIRECT_CHILDREN.equals((Object)treeType)) {
            return this.convert(this.internalGetChildRows(queryKeys));
        }
        if (QueryOptions.TreeType.ALL_CHILDREN.equals((Object)treeType)) {
            OrgDTO param = this.getRecoveryFlag();
            param.setCode(queryKeys[0]);
            param.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN);
            PageVO list = this.orgDataSource.list(param);
            List rows = list.getRows();
            this.afterQuery(rows, this.query);
            return this.convert(rows);
        }
        return null;
    }

    public List<OrgDO> internalGetChildRows(String ... entityKeyData) {
        OrgDTO param = this.getRecoveryFlag();
        if (entityKeyData.length > 1) {
            param.setOrgCodes(Arrays.asList(entityKeyData));
            param.setQueryChildrenType(OrgDataOption.QueryChildrenType.DIRECT_CHILDREN);
        } else {
            param.setParentcode(entityKeyData[0]);
        }
        param.setLazyLoad(Boolean.valueOf(true));
        PageVO list = this.orgDataSource.list(param);
        List rows = list.getRows();
        this.afterQuery(rows, this.query);
        return rows;
    }

    public EntityResultSet findByEntityKeys() {
        OrgDTO param = this.getRecoveryFlag();
        param.setOrgCodes(this.query.getMasterKey());
        PageVO list = this.orgDataSource.list(param);
        List rows = list.getRows();
        this.afterQuery(rows, this.query);
        return this.convert(rows);
    }

    public String getParent(String queryCode) {
        OrgDTO param = this.getRecoveryFlag();
        param.setCode(queryCode);
        PageVO list = this.orgDataSource.list(param);
        if (list.getTotal() > 0) {
            return ((OrgDO)list.getRows().get(0)).getParentcode();
        }
        return null;
    }

    public String getParent(Map<String, Object> rowData) {
        Object parentValue = rowData.get("PARENTCODE".toLowerCase(Locale.ROOT));
        if (parentValue == null) {
            return null;
        }
        return parentValue.toString();
    }

    public int getMaxDepth() {
        OrgDTO orgDTO = this.getRecoveryFlag();
        orgDTO.setOrderBy(Collections.emptyList());
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.SIMPLE);
        PageVO list = this.orgDataSource.list(orgDTO);
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

    public int getMaxDepthByEntityKey(String entityKeyData) {
        OrgDTO param = this.getRecoveryFlag();
        param.setQueryDataStructure(OrgDataOption.QueryDataStructure.SIMPLE);
        param.setCode(entityKeyData);
        param.setOrderBy(Collections.emptyList());
        PageVO list = this.orgDataSource.listSubordinate(param);
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

    public EntityResultSet findByCodes() {
        OrgDTO param = this.getRecoveryFlag();
        param.setOrgOrgcodes(this.query.getCodes());
        PageVO list = this.orgDataSource.list(param);
        List rows = list.getRows();
        this.afterQuery(rows, this.query);
        return this.convert(rows);
    }

    public int getChildCount(String entityKeyData, QueryOptions.TreeType treeType) {
        OrgDTO param = this.getRecoveryFlag();
        param.setOrderBy(Collections.emptyList());
        if (QueryOptions.TreeType.DIRECT_CHILDREN.equals((Object)treeType)) {
            param.setParentcode(entityKeyData);
        } else {
            param.setCode(entityKeyData);
            param.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN);
        }
        return this.orgDataSource.count(param);
    }

    public Map<String, Integer> getChildCountByParent(String parentKey, QueryOptions.TreeType treeType) {
        List<OrgDO> childRows = this.internalGetChildRows(parentKey);
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

    public String[] getParentsEntityKeyDataPath(String entityKeyData) {
        OrgDTO param = this.getRecoveryFlag();
        param.setQueryDataStructure(OrgDataOption.QueryDataStructure.SIMPLE);
        param.setCode(entityKeyData);
        PageVO list = this.orgDataSource.list(param);
        if (list.getTotal() > 0) {
            String[] parents = ((OrgDO)list.getRows().get(0)).getParents().split("\\/");
            return Arrays.copyOf(parents, parents.length - 1);
        }
        return new String[0];
    }

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

    public int getTotalCount() {
        OrgDTO orgDTO = this.getRecoveryFlag();
        return this.orgDataSource.count(orgDTO);
    }

    protected String getKey(OrgDO orgDO) {
        return orgDO.getCode();
    }

    protected String getDimensionName(String entityId) {
        return "MD_ORG";
    }

    protected EntityResultSet convert(List<OrgDO> list) {
        return new OrgResultSet(list, this.query, this.orgDataSource);
    }

    private OrgDTO getRecoveryFlag() {
        OrgDTO orgDTO = OrgConvertUtil.paramConvert((IEntityQueryParam)this.query);
        orgDTO.setRecoveryflag(Integer.valueOf(-1));
        return orgDTO;
    }
}

