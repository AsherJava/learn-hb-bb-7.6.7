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
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryParentType
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 */
package com.jiuqi.nr.entity.adapter.impl.basedata;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.entity.adapter.impl.basedata.data.CommonBaseDataQuery;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class BaseDataResultSet
extends EntityResultSet {
    private final PageVO<BaseDataDO> pageVO;
    private final BaseDataClient baseDataClient;
    private final IEntityQueryParam entityQueryParam;
    private final BaseDataDefineClient baseDataDefineClient;
    private List<String> keys;
    private Map<String, String[]> pathCache = new HashMap<String, String[]>(16);
    private Map<String, String> parentCache = new HashMap<String, String>(16);
    private BaseDataDefineDO baseDataDefineDO;
    private Map<String, Object> childrenMap;
    private Map<String, Object> leafMap;
    private Map<String, Object[]> maskedValue;
    private Map<String, Integer> maskedFieldsIdx;
    private Boolean listStruct = null;

    public BaseDataResultSet(PageVO<BaseDataDO> pageVO, BaseDataClient baseDataClient, BaseDataDefineClient baseDataDefineClient, IEntityQueryParam entityQueryParam) {
        super(pageVO.getRows().size());
        this.pageVO = pageVO;
        this.baseDataClient = baseDataClient;
        this.entityQueryParam = entityQueryParam;
        this.baseDataDefineClient = baseDataDefineClient;
        this.childrenMap = pageVO.getRs().get((Object)"hasChildren") == null ? new ConcurrentHashMap<String, Object>() : (Map)pageVO.getRs().get((Object)"hasChildren");
        this.leafMap = pageVO.getRs().get((Object)"isLeaf") == null ? new ConcurrentHashMap<String, Object>() : (Map)pageVO.getRs().get((Object)"isLeaf");
        this.maskedValue = pageVO.getRs().get((Object)"CACHE_UNMASKED") == null ? new HashMap<String, Object[]>() : (Map)pageVO.getRs().get((Object)"CACHE_UNMASKED");
        this.maskedFieldsIdx = pageVO.getRs().get((Object)"CACHE_UNMASKED_COLUMN") == null ? new HashMap<String, Integer>() : (Map)pageVO.getRs().get((Object)"CACHE_UNMASKED_COLUMN");
    }

    @Override
    public List<String> getAllKeys() {
        if (this.keys != null) {
            return this.keys;
        }
        return this.pageVO.getRows().stream().filter(Objects::nonNull).map(BaseDataDO::getObjectcode).collect(Collectors.toList());
    }

    @Override
    protected Object getColumnObject(int index, String columnCode) {
        BaseDataDO baseDataDO = this.getList().get(index);
        if (baseDataDO == null) {
            return null;
        }
        if ("localizedName".equals(columnCode)) {
            return this.getLocalName(baseDataDO);
        }
        if ("CODE".equals(columnCode)) {
            return baseDataDO.getCode();
        }
        if ("NAME".equals(columnCode)) {
            return this.getName(baseDataDO);
        }
        if ("OBJECTCODE".equals(columnCode)) {
            return baseDataDO.getObjectcode();
        }
        if (this.entityQueryParam.isMaskingData() && !CollectionUtils.isEmpty(this.maskedFieldsIdx)) {
            return this.getMaskedValue(baseDataDO.getObjectcode(), this.maskedFieldsIdx.get(columnCode.toLowerCase(Locale.ROOT)));
        }
        return baseDataDO.get((Object)columnCode.toLowerCase(Locale.ROOT));
    }

    private String getLocalName(BaseDataDO baseDataDO) {
        Object localizedNameValue;
        Map localizedNameMap;
        String localizedName = baseDataDO.getLocalizedName();
        if (!StringUtils.hasText(localizedName) && this.pageVO.getRs().containsKey((Object)"localizedName") && (localizedNameMap = (Map)this.pageVO.getRs().get((Object)"localizedName")) != null && (localizedNameValue = localizedNameMap.get(baseDataDO.getObjectcode())) != null) {
            localizedName = localizedNameValue.toString();
        }
        if (!StringUtils.hasText(localizedName)) {
            return this.getName(baseDataDO);
        }
        if (!this.entityQueryParam.isMaskingData() && !CollectionUtils.isEmpty(this.maskedFieldsIdx)) {
            return this.getName(baseDataDO);
        }
        return localizedName;
    }

    private String getName(BaseDataDO baseDataDO) {
        if (!this.entityQueryParam.isMaskingData() || CollectionUtils.isEmpty(this.maskedFieldsIdx)) {
            return baseDataDO.getName();
        }
        Object nameValue = this.getMaskedValue(baseDataDO.getObjectcode(), this.maskedFieldsIdx.get("name"));
        if (nameValue != null) {
            return nameValue.toString();
        }
        return baseDataDO.getName();
    }

    @Override
    protected String getKey(int index) {
        BaseDataDO baseDataDO = this.getList().get(index);
        if (baseDataDO == null) {
            return null;
        }
        return baseDataDO.getObjectcode();
    }

    @Override
    protected String getCode(int index) {
        BaseDataDO baseDataDO = this.getList().get(index);
        if (baseDataDO == null) {
            return null;
        }
        return baseDataDO.getCode();
    }

    @Override
    protected String getTitle(int index) {
        BaseDataDO baseDataDO = this.getList().get(index);
        if (baseDataDO == null) {
            return null;
        }
        return this.getLocalName(baseDataDO);
    }

    @Override
    protected String getParent(int index) {
        BaseDataDO baseDataDO = this.getList().get(index);
        if (baseDataDO == null) {
            return null;
        }
        String parentCode = baseDataDO.getParentcode();
        String cacheParent = this.parentCache.get(parentCode);
        if (cacheParent != null) {
            return cacheParent;
        }
        BaseDataDefineDO baseDataDefine = this.getBaseDataDefine();
        if (baseDataDefine.getSharetype() == 0) {
            return parentCode;
        }
        String parent = this.getParent(baseDataDO.getObjectcode());
        this.parentCache.put(parentCode, parent);
        return parent;
    }

    private BaseDataDefineDO getBaseDataDefine() {
        if (this.baseDataDefineDO != null) {
            return this.baseDataDefineDO;
        }
        String cacheNameBaseDataDefine = "NR_ENTITY_BASEDATA_DEFINE";
        ContextExtension baseDataDefinecache = NpContextHolder.getContext().getExtension("NR_ENTITY_BASEDATA_DEFINE");
        BaseDataDefineDO baseDataDefine = (BaseDataDefineDO)baseDataDefinecache.get(this.entityQueryParam.getEntityId());
        if (baseDataDefine == null) {
            baseDataDefine = this.getBaseDataDefineByCode(this.entityQueryParam.getEntityId());
            if (baseDataDefine == null) {
                throw new RuntimeException("basedata '" + this.entityQueryParam.getEntityId() + "' not found.");
            }
            baseDataDefinecache.put(this.entityQueryParam.getEntityId(), (Serializable)baseDataDefine);
        }
        this.baseDataDefineDO = baseDataDefine;
        return baseDataDefine;
    }

    @Override
    protected Object getOrder(int index) {
        BaseDataDO baseDataDO = this.getList().get(index);
        if (baseDataDO == null) {
            return null;
        }
        return baseDataDO.getOrdinal();
    }

    @Override
    protected String[] getParents(int index) {
        BaseDataDO baseDataDO = this.getList().get(index);
        if (baseDataDO == null) {
            return null;
        }
        String parents = baseDataDO.getParents();
        if (StringUtils.hasText(parents)) {
            String[] path = this.pathCache.get(parents);
            if (path != null) {
                return path;
            }
            path = this.resolvePath(parents);
            if (path != null) {
                this.pathCache.put(parents, path);
            }
            return path;
        }
        return new String[0];
    }

    @Override
    public boolean isLeaf(int index) {
        BaseDataDO baseDataDO = this.getList().get(index);
        if (baseDataDO == null) {
            return false;
        }
        if (this.isListStruct()) {
            return true;
        }
        Object leaf = baseDataDO.get((Object)"isLeaf");
        if (leaf == null) {
            leaf = this.leafMap.get(this.getKey(index));
        }
        return leaf != null && Boolean.parseBoolean(leaf.toString());
    }

    @Override
    public boolean hasChildren(int index) {
        BaseDataDO baseDataDO = this.getList().get(index);
        if (baseDataDO == null) {
            return false;
        }
        if (this.isListStruct()) {
            return false;
        }
        Object hasChildren = baseDataDO.get((Object)"hasChildren");
        if (hasChildren == null) {
            hasChildren = this.childrenMap.get(this.getKey(index));
        }
        return hasChildren != null && Boolean.parseBoolean(hasChildren.toString());
    }

    private boolean isListStruct() {
        if (this.listStruct == null) {
            BaseDataDefineDO baseDataDefine = this.getBaseDataDefine();
            Integer structtype = baseDataDefine.getStructtype();
            this.listStruct = structtype != null && (structtype == 0 || structtype == 1);
        }
        return this.listStruct;
    }

    private String[] resolvePath(String parentsVal) {
        String[] path = new String[]{};
        if (StringUtils.hasText(parentsVal)) {
            String[] splitParents = parentsVal.split("/");
            path = Arrays.copyOf(splitParents, splitParents.length - 1);
        }
        if (path.length == 0) {
            return path;
        }
        BaseDataDefineDO baseDataDefine = this.getBaseDataDefine();
        if (baseDataDefine.getSharetype() == 0 && this.entityQueryParam.isIgnoreAuth()) {
            return path;
        }
        return this.getParentPath(path);
    }

    public List<BaseDataDO> getList() {
        return this.pageVO.getRows();
    }

    public R getR() {
        return this.pageVO.getRs();
    }

    public void setRs(R rs) {
        this.pageVO.setRs(rs);
    }

    @Override
    public int append(EntityResultSet rs) {
        if (rs instanceof BaseDataResultSet) {
            BaseDataResultSet baseDataResultSet = (BaseDataResultSet)rs;
            List<BaseDataDO> mergeRows = baseDataResultSet.getList();
            R mergeRs = baseDataResultSet.getR();
            if (mergeRows != null) {
                this.getList().addAll(mergeRows);
                if (this.keys != null) {
                    this.getAllKeys().addAll(baseDataResultSet.getAllKeys());
                } else {
                    this.keys = baseDataResultSet.getAllKeys();
                }
            }
            if (this.getR() != null) {
                if (mergeRs != null) {
                    this.mergeRs(mergeRs);
                }
            } else {
                this.setRs(mergeRs);
            }
            return this.getList() == null ? 0 : this.getList().size();
        }
        return -1;
    }

    private void mergeRs(R mergeRs) {
        Map mergeLocalizedNameMap = (Map)mergeRs.get((Object)"localizedName");
        Map localizedNameMap = (Map)this.getR().get((Object)"localizedName");
        Map mergerLeafMap = (Map)this.getR().get((Object)"isLeaf");
        Map mergerChildrenMap = (Map)this.getR().get((Object)"hasChildren");
        if (localizedNameMap != null) {
            if (mergeLocalizedNameMap != null) {
                localizedNameMap.putAll(mergeLocalizedNameMap);
            }
        } else if (mergeLocalizedNameMap != null) {
            this.getR().put("localizedName", (Object)mergeLocalizedNameMap);
        }
        if (!CollectionUtils.isEmpty(mergerLeafMap)) {
            this.leafMap.putAll(mergerLeafMap);
        }
        if (!CollectionUtils.isEmpty(mergerChildrenMap)) {
            this.childrenMap.putAll(mergerChildrenMap);
        }
        Map mergerUnMaskedData = (Map)mergeRs.get((Object)"CACHE_UNMASKED");
        Map localUnMaskedData = (Map)this.getR().get((Object)"CACHE_UNMASKED");
        if (localUnMaskedData != null) {
            if (mergerUnMaskedData != null) {
                localUnMaskedData.putAll(mergerUnMaskedData);
            }
        } else if (mergerUnMaskedData != null) {
            this.getR().put("CACHE_UNMASKED", (Object)mergerUnMaskedData);
            this.getR().put("CACHE_UNMASKED_COLUMN", mergeRs.get((Object)"CACHE_UNMASKED_COLUMN"));
        }
    }

    protected String getParent(String objectCode) {
        PageVO page;
        Map<String, Object> ext;
        BaseDataDTO baseDataFilter = new BaseDataDTO();
        baseDataFilter.setTableName(this.entityQueryParam.getEntityId());
        baseDataFilter.setObjectcode(objectCode);
        baseDataFilter.setAuthType(CommonBaseDataQuery.getAuthType(this.entityQueryParam));
        baseDataFilter.setQueryParentType(BaseDataOption.QueryParentType.DIRECT_PARENT);
        BaseDataDefineDO baseDataDefine = this.getBaseDataDefine();
        if (baseDataDefine.getSharetype() != 0) {
            String sharefieldname = baseDataDefine.getSharefieldname();
            if (StringUtils.hasText(sharefieldname)) {
                baseDataFilter.put(sharefieldname.toLowerCase(Locale.ROOT), (Object)this.entityQueryParam.getIsolationCondition());
            } else {
                baseDataFilter.setUnitcode(this.entityQueryParam.getIsolationCondition());
            }
            baseDataFilter.put("shareForceCheck", (Object)true);
        }
        if ((ext = this.entityQueryParam.getExt()) != null) {
            baseDataFilter.putAll(ext);
        }
        if ((page = this.baseDataClient.list(baseDataFilter)).getTotal() > 0) {
            return ((BaseDataDO)page.getRows().get(0)).getObjectcode();
        }
        return null;
    }

    private String[] getParentPath(String[] path) {
        PageVO page;
        Map<String, Object> ext;
        ArrayList<String> objectCodePath = new ArrayList<String>(path.length);
        BaseDataDTO baseDataFilter = new BaseDataDTO();
        baseDataFilter.setTableName(this.entityQueryParam.getEntityId());
        baseDataFilter.setCodeScope(Arrays.asList(path));
        baseDataFilter.setAuthType(CommonBaseDataQuery.getAuthType(this.entityQueryParam));
        baseDataFilter.setQueryDataStructure(BaseDataOption.QueryDataStructure.BASIC);
        BaseDataDefineDO baseDataDefine = this.getBaseDataDefine();
        if (baseDataDefine.getSharetype() != 0) {
            String sharefieldname = baseDataDefine.getSharefieldname();
            if (StringUtils.hasText(sharefieldname)) {
                baseDataFilter.put(sharefieldname.toLowerCase(Locale.ROOT), (Object)this.entityQueryParam.getIsolationCondition());
            } else {
                baseDataFilter.setUnitcode(this.entityQueryParam.getIsolationCondition());
            }
            baseDataFilter.put("shareForceCheck", (Object)true);
        }
        if ((ext = this.entityQueryParam.getExt()) != null) {
            baseDataFilter.putAll(ext);
        }
        if ((page = this.baseDataClient.list(baseDataFilter)) != null && page.getTotal() > 0) {
            Map<String, String> codeMap = page.getRows().stream().collect(Collectors.toMap(e -> e.getCode(), f -> f.getObjectcode(), (f1, f2) -> f2));
            for (String code : path) {
                String objectCode = codeMap.get(code);
                if (objectCode == null) continue;
                objectCodePath.add(objectCode);
            }
        }
        return objectCodePath.toArray(new String[0]);
    }

    protected BaseDataDefineDO getBaseDataDefineByCode(String tableName) {
        if (ObjectUtils.isEmpty(tableName)) {
            return null;
        }
        BaseDataDefineDTO filterCond = new BaseDataDefineDTO();
        filterCond.setName(tableName);
        filterCond.setDeepClone(Boolean.valueOf(false));
        return this.baseDataDefineClient.get(filterCond);
    }

    public PageVO<BaseDataDO> getPageVO() {
        return this.pageVO;
    }

    public BaseDataClient getBaseDataClient() {
        return this.baseDataClient;
    }

    public IEntityQueryParam getEntityQueryParam() {
        return this.entityQueryParam;
    }

    public BaseDataDefineClient getBaseDataDefineClient() {
        return this.baseDataDefineClient;
    }

    private Object getMaskedValue(String objectCode, Integer columnIdx) {
        Object[] values = this.maskedValue.get(objectCode);
        if (values != null && columnIdx != null) {
            return values[columnIdx];
        }
        return null;
    }
}

