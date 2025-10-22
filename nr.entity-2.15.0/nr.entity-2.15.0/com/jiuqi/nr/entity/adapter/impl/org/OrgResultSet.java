/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.entity.adapter.impl.org;

import com.jiuqi.nr.entity.adapter.impl.org.data.OrgDataSource;
import com.jiuqi.nr.entity.adapter.impl.org.util.OrgConvertUtil;
import com.jiuqi.nr.entity.engine.filter.IEntityDataFilter;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class OrgResultSet
extends EntityResultSet {
    private final List<OrgDO> list;
    private final IEntityQueryParam query;
    private final OrgDataSource orgDataSource;
    private List<String> keys;
    private Map<String, String[]> pathCache = new HashMap<String, String[]>(16);
    private Set<String> authKeys = new HashSet<String>();
    private Set<String> hasNotAuthKeys = new HashSet<String>();
    private Map<String, Object> childrenMap;
    private Map<String, Object> leafMap;
    private R r;

    public OrgResultSet(List<OrgDO> list, IEntityQueryParam query, OrgDataSource orgDataSource, R r) {
        super(list.size());
        this.list = list;
        this.query = query;
        this.orgDataSource = orgDataSource;
        this.childrenMap = r.get((Object)"hasChildren") == null ? new ConcurrentHashMap() : (Map)r.get((Object)"hasChildren");
        this.leafMap = r.get((Object)"isLeaf") == null ? new ConcurrentHashMap() : (Map)r.get((Object)"isLeaf");
        this.r = r;
    }

    public OrgResultSet(List<OrgDO> list, IEntityQueryParam query, OrgDataSource orgDataSource) {
        super(list.size());
        this.list = list;
        this.query = query;
        this.orgDataSource = orgDataSource;
        this.childrenMap = new ConcurrentHashMap<String, Object>();
        this.leafMap = new ConcurrentHashMap<String, Object>();
    }

    @Override
    public List<String> getAllKeys() {
        if (this.keys != null) {
            return this.keys;
        }
        return this.list.stream().filter(Objects::nonNull).map(OrgDO::getCode).collect(Collectors.toList());
    }

    @Override
    protected Object getColumnObject(int index, String columnCode) {
        OrgDO orgDO = this.list.get(index);
        if (orgDO == null) {
            return null;
        }
        if ("localizedName".equals(columnCode)) {
            return this.getLocalName(orgDO);
        }
        if ("CODE".equals(columnCode)) {
            return orgDO.getCode();
        }
        if ("NAME".equals(columnCode)) {
            return this.getLocalName(orgDO);
        }
        if ("orgcode".equalsIgnoreCase(columnCode)) {
            return orgDO.getOrgcode();
        }
        return orgDO.get((Object)columnCode.toLowerCase(Locale.ROOT));
    }

    @Override
    protected String getKey(int index) {
        OrgDO orgDO = this.list.get(index);
        if (orgDO == null) {
            return null;
        }
        String code = orgDO.getCode();
        this.authKeys.add(code);
        return code;
    }

    @Override
    protected String getCode(int index) {
        OrgDO orgDO = this.list.get(index);
        if (orgDO == null) {
            return null;
        }
        return orgDO.getOrgcode();
    }

    @Override
    protected String getTitle(int index) {
        OrgDO orgDO = this.list.get(index);
        if (orgDO == null) {
            return null;
        }
        return this.getLocalName(orgDO);
    }

    @Override
    protected String getParent(int index) {
        OrgDO orgDO = this.list.get(index);
        if (orgDO == null) {
            return null;
        }
        return orgDO.getParentcode();
    }

    @Override
    protected Object getOrder(int index) {
        OrgDO orgDO = this.list.get(index);
        if (orgDO == null) {
            return null;
        }
        return orgDO.getOrdinal();
    }

    @Override
    protected String[] getParents(int index) {
        OrgDO orgDO = this.list.get(index);
        if (orgDO == null) {
            return new String[0];
        }
        String parents = orgDO.getParents();
        if (StringUtils.hasText(parents)) {
            String[] splitPath = parents.split("/");
            String realPath = Arrays.stream(splitPath).filter(e -> !e.equals(orgDO.getCode())).collect(Collectors.joining("/"));
            if (!StringUtils.hasText(realPath)) {
                return new String[0];
            }
            String[] path = this.pathCache.get(realPath);
            if (path != null && path.length > 0) {
                return path;
            }
            this.authKeys.add(orgDO.getCode());
            path = this.resolvePath(parents, orgDO);
            this.pathCache.put(realPath, path);
            return path;
        }
        return new String[0];
    }

    @Override
    public boolean isLeaf(int index) {
        OrgDO orgDO = this.list.get(index);
        if (orgDO == null) {
            return false;
        }
        Object leaf = orgDO.get((Object)"isLeaf");
        if (leaf == null && this.leafMap != null) {
            leaf = this.leafMap.get(this.getKey(index));
        }
        return leaf != null && Boolean.parseBoolean(leaf.toString());
    }

    @Override
    public boolean hasChildren(int index) {
        OrgDO orgDO = this.list.get(index);
        if (orgDO == null) {
            return false;
        }
        Object hasChildren = orgDO.get((Object)"hasChildren");
        if (hasChildren == null && this.childrenMap != null) {
            hasChildren = this.childrenMap.get(this.getKey(index));
        }
        return hasChildren != null && Boolean.parseBoolean(hasChildren.toString());
    }

    @NotNull
    private String[] resolvePath(String parents, OrgDO orgDO) {
        String[] path = this.pathCache.get(parents);
        if (path != null) {
            return path;
        }
        String[] splitParents = parents.split("/");
        path = Arrays.copyOf(splitParents, splitParents.length - 1);
        if (path.length == 0 || this.query.isIgnoreAuth()) {
            return path;
        }
        ArrayList<String> pathKeys = new ArrayList<String>(Arrays.asList(path));
        ArrayList<String> queryKeys = new ArrayList<String>(path.length);
        Iterator iterator = pathKeys.iterator();
        while (iterator.hasNext()) {
            String entityKey = (String)iterator.next();
            if (this.hasNotAuthKeys.contains(entityKey)) {
                iterator.remove();
                continue;
            }
            if (this.authKeys.contains(entityKey)) continue;
            queryKeys.add(entityKey);
        }
        if (CollectionUtils.isEmpty(pathKeys)) {
            return new String[0];
        }
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setOrgCodes(queryKeys);
        orgDTO.setCategoryname(orgDO.getCategoryname());
        orgDTO.setAuthType(OrgConvertUtil.getAuthType(this.query));
        orgDTO.setVersionDate(this.query.getVersionDate());
        if (this.query.getExt() != null) {
            orgDTO.putAll(this.query.getExt());
        }
        if (this.query.getFilter() != null) {
            IEntityDataFilter filter = this.query.getFilter();
            orgDTO.put(filter.getDataFilter(), (Object)filter);
            orgDTO.setExpression(filter.getExpression());
        }
        orgDTO.setOrderBy(Collections.emptyList());
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.BASIC);
        Map<String, Object> ext = this.query.getExt();
        if (ext != null) {
            orgDTO.putAll(ext);
        }
        PageVO<OrgDO> queryList = this.orgDataSource.list(orgDTO);
        ArrayList<String> filterPath = new ArrayList<String>(path.length);
        Set codeSet = queryList.getRows().stream().map(OrgDO::getCode).collect(Collectors.toSet());
        for (String key : pathKeys) {
            if (!queryKeys.contains(key)) {
                filterPath.add(key);
                continue;
            }
            if (codeSet.contains(key)) {
                this.authKeys.add(key);
                filterPath.add(key);
                continue;
            }
            this.hasNotAuthKeys.add(key);
        }
        return filterPath.toArray(new String[0]);
    }

    public List<OrgDO> getList() {
        return this.list;
    }

    public Set<String> getAuthKeys() {
        return this.authKeys;
    }

    public Map<String, Object> getChildrenMap() {
        return this.childrenMap;
    }

    public Map<String, Object> getLeafMap() {
        return this.leafMap;
    }

    @Override
    public int append(EntityResultSet rs) {
        if (rs instanceof OrgResultSet) {
            OrgResultSet orgResultSet = (OrgResultSet)rs;
            List<OrgDO> orgList = orgResultSet.getList();
            R mergeRs = orgResultSet.getR();
            if (orgList != null) {
                this.list.addAll(orgList);
                this.authKeys.addAll(orgResultSet.getAuthKeys());
                if (this.keys != null) {
                    this.getAllKeys().addAll(orgResultSet.getAllKeys());
                } else {
                    this.keys = orgResultSet.getAllKeys();
                }
            }
            Map<String, Object> mergeChildrenMap = orgResultSet.getChildrenMap();
            Map<String, Object> mergeLeafMap = orgResultSet.getLeafMap();
            if (!CollectionUtils.isEmpty(mergeChildrenMap)) {
                this.childrenMap.putAll(mergeChildrenMap);
            }
            if (!CollectionUtils.isEmpty(mergeLeafMap)) {
                this.leafMap.putAll(mergeLeafMap);
            }
            if (this.getR() != null) {
                if (mergeRs != null) {
                    this.mergeRs(mergeRs);
                } else {
                    this.setRs(mergeRs);
                }
            }
            return this.list == null ? 0 : this.list.size();
        }
        return -1;
    }

    private String getLocalName(OrgDO orgDO) {
        Object localizedNameValue;
        Map localizedNameMap;
        String localizedName = orgDO.getLocalizedName();
        if (!StringUtils.hasText(localizedName) && this.r.containsKey((Object)"localizedName") && (localizedNameMap = (Map)this.r.get((Object)"localizedName")) != null && (localizedNameValue = localizedNameMap.get(orgDO.getCode())) != null) {
            localizedName = localizedNameValue.toString();
        }
        if (!StringUtils.hasText(localizedName)) {
            localizedName = orgDO.getName();
        }
        return localizedName;
    }

    public R getR() {
        return this.r;
    }

    public void setRs(R rs) {
        this.r = rs;
    }

    private void mergeRs(R mergeRs) {
        Map mergeLocalizedNameMap = (Map)mergeRs.get((Object)"localizedName");
        Map localizedNameMap = (Map)this.getR().get((Object)"localizedName");
        if (localizedNameMap != null) {
            if (mergeLocalizedNameMap != null) {
                localizedNameMap.putAll(mergeLocalizedNameMap);
            }
        } else if (mergeLocalizedNameMap != null) {
            this.getR().put("localizedName", (Object)mergeLocalizedNameMap);
        }
    }
}

