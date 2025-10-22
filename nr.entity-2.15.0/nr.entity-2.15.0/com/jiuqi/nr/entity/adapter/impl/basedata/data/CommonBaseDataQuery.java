/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$OrderType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.handle.BaseDataSortDTO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.data;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.entity.adapter.impl.AbstractDataQueryHelper;
import com.jiuqi.nr.entity.adapter.impl.basedata.BaseDataTreeStruct;
import com.jiuqi.nr.entity.engine.filter.IEntityDataFilter;
import com.jiuqi.nr.entity.engine.setting.OrderAttribute;
import com.jiuqi.nr.entity.engine.setting.OrderType;
import com.jiuqi.nr.entity.internal.model.impl.EntityDefineImpl;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataSortDTO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class CommonBaseDataQuery
extends AbstractDataQueryHelper<BaseDataDO> {
    protected final BaseDataDefineClient baseDataDefineClient;

    public CommonBaseDataQuery(BaseDataDefineClient baseDataDefineClient) {
        this.baseDataDefineClient = baseDataDefineClient;
    }

    protected BaseDataDTO getBaseDataFilter(IEntityQueryParam queryParam) {
        Map<String, Object> ext;
        List<OrderAttribute> orderField;
        BaseDataOption.AuthType authType;
        BaseDataDTO baseDataFilter = new BaseDataDTO();
        baseDataFilter.setTableName(queryParam.getEntityId());
        baseDataFilter.setVersionDate(queryParam.getVersionDate());
        if (queryParam.getFilter() != null) {
            IEntityDataFilter filter = queryParam.getFilter();
            baseDataFilter.put(filter.getDataFilter(), (Object)filter);
            baseDataFilter.setExpression(filter.getExpression());
        }
        Locale locale = NpContextHolder.getContext().getLocale();
        boolean simpleStructModel = false;
        if (locale != null) {
            simpleStructModel = Locale.CHINA.getLanguage().equals(locale.getLanguage());
        }
        if (simpleStructModel) {
            baseDataFilter.setQueryDataStructure(BaseDataOption.QueryDataStructure.BASIC);
        } else {
            baseDataFilter.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        }
        if (!CollectionUtils.isEmpty(queryParam.getMasterKey())) {
            baseDataFilter.setBaseDataObjectcodes(queryParam.getMasterKey());
        }
        if (!CollectionUtils.isEmpty(queryParam.getCodes())) {
            baseDataFilter.setBaseDataCodes(queryParam.getCodes());
        }
        if ((authType = CommonBaseDataQuery.getAuthType(queryParam)) != null) {
            baseDataFilter.setAuthType(authType);
        }
        this.setIsolation(queryParam, baseDataFilter);
        Integer limit = queryParam.getLimit();
        Integer offSet = queryParam.getOffSet();
        if (limit != null && offSet != null) {
            baseDataFilter.setPagination(Boolean.valueOf(true));
            baseDataFilter.setLimit(limit);
            baseDataFilter.setOffset(offSet);
        }
        if (!CollectionUtils.isEmpty(orderField = queryParam.getOrderField())) {
            ArrayList<BaseDataSortDTO> sortList = new ArrayList<BaseDataSortDTO>(orderField.size());
            for (OrderAttribute attribute : orderField) {
                BaseDataSortDTO dataSortDTO = new BaseDataSortDTO(attribute.getAttributeCode(), attribute.getType().equals((Object)OrderType.ASC) ? BaseDataOption.OrderType.ASC : BaseDataOption.OrderType.DESC);
                sortList.add(dataSortDTO);
            }
            baseDataFilter.setOrderBy(sortList);
        } else if (orderField == null) {
            baseDataFilter.setOrderBy(Collections.emptyList());
        } else if (orderField.isEmpty()) {
            baseDataFilter.setOrderBy(null);
        }
        baseDataFilter.setLazyLoad(Boolean.valueOf(queryParam.isLazy()));
        baseDataFilter.setLeafFlag(Boolean.valueOf(queryParam.isMarkLeaf()));
        if (queryParam.getQueryStop() != null) {
            baseDataFilter.setStopflag(queryParam.getQueryStop());
        }
        if ((ext = queryParam.getExt()) != null) {
            baseDataFilter.putAll(ext);
        }
        return baseDataFilter;
    }

    protected void setIsolation(IEntityQueryParam queryParam, BaseDataDTO baseDataFilter) {
        String isolationCondition = queryParam.getIsolationCondition();
        if (StringUtils.hasText(isolationCondition)) {
            BaseDataDefineDTO filterCond = new BaseDataDefineDTO();
            filterCond.setName(queryParam.getEntityId());
            filterCond.setDeepClone(Boolean.valueOf(false));
            BaseDataDefineDO baseDataDefine = this.baseDataDefineClient.get(filterCond);
            String sharefieldname = null;
            if (baseDataDefine != null) {
                sharefieldname = baseDataDefine.getSharefieldname();
            }
            if (StringUtils.hasText(sharefieldname)) {
                baseDataFilter.put(sharefieldname.toLowerCase(Locale.ROOT), (Object)isolationCondition);
            } else {
                baseDataFilter.setUnitcode(isolationCondition);
            }
            baseDataFilter.put("shareForceCheck", (Object)true);
        }
    }

    public static BaseDataOption.AuthType getAuthType(IEntityQueryParam queryParam) {
        BaseDataOption.AuthType authType = null;
        if (queryParam.isIgnoreAuth()) {
            authType = BaseDataOption.AuthType.NONE;
        } else if (queryParam.isHasReadAuth()) {
            authType = BaseDataOption.AuthType.ACCESS;
        } else if (queryParam.isHasWriteAuth()) {
            authType = BaseDataOption.AuthType.WRITE;
        }
        return authType;
    }

    protected BaseDataDefineDO getBasedataDefineByCode(String tableName) {
        if (ObjectUtils.isEmpty(tableName)) {
            return null;
        }
        BaseDataDefineDTO filterCond = new BaseDataDefineDTO();
        filterCond.setName(tableName);
        filterCond.setDeepClone(Boolean.valueOf(false));
        return this.baseDataDefineClient.get(filterCond);
    }

    public static boolean isDummy(Integer dummyflag) {
        return dummyflag != null && dummyflag == 1;
    }

    public static List<BaseDataDefineDO> filterUnDummy(List<BaseDataDefineDO> rows) {
        return rows.stream().filter(e -> !CommonBaseDataQuery.isDummy(e.getDummyflag())).collect(Collectors.toList());
    }

    public static IEntityDefine convertDefine2EntityDefine(BaseDataDefineDO baseDataDefine) {
        if (baseDataDefine == null) {
            return null;
        }
        EntityDefineImpl entityDefine = new EntityDefineImpl();
        entityDefine.setId(baseDataDefine.getName());
        entityDefine.setCode(baseDataDefine.getName());
        entityDefine.setTitle(baseDataDefine.getTitle());
        entityDefine.setDesc(baseDataDefine.getRemark());
        entityDefine.setDimensionFlag(1);
        entityDefine.setDimensionName(baseDataDefine.getName());
        BaseDataTreeStruct treeStruct = new BaseDataTreeStruct();
        Integer structType = baseDataDefine.getStructtype();
        treeStruct.setStructType(structType);
        if (structType != null && structType == 3) {
            String levelcode = baseDataDefine.getLevelcode();
            int fiexedIndex = levelcode.indexOf("#");
            if (fiexedIndex > -1) {
                String codeSize = levelcode.substring(fiexedIndex + 1);
                levelcode = levelcode.substring(0, fiexedIndex);
                treeStruct.setFixedSize(true);
                treeStruct.setCodeSize(Integer.valueOf(codeSize));
            }
            treeStruct.setLevelCode(levelcode);
        }
        entityDefine.setIsolation(baseDataDefine.getSharetype());
        entityDefine.setTreeStruct(treeStruct);
        entityDefine.setVersion(baseDataDefine.getVersionflag() == null ? 0 : baseDataDefine.getVersionflag());
        entityDefine.setTree(structType != null && structType > 1);
        entityDefine.setGroup(baseDataDefine.getGroupname());
        entityDefine.setAuthFlag(Integer.valueOf(1).equals(baseDataDefine.getAuthflag()));
        entityDefine.setCategory("BASE");
        return entityDefine;
    }

    @Override
    protected String getKey(BaseDataDO baseDataDO) {
        return baseDataDO.getObjectcode();
    }

    @Override
    protected String getDimensionName(String entityId) {
        return entityId;
    }
}

