/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$OrderType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.org.OrgDataSortDTO
 *  com.jiuqi.va.organization.domain.OrgAuthDO
 */
package com.jiuqi.nr.entity.adapter.impl.org.util;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.entity.engine.filter.IEntityDataFilter;
import com.jiuqi.nr.entity.engine.setting.OrderAttribute;
import com.jiuqi.nr.entity.engine.setting.OrderType;
import com.jiuqi.nr.entity.internal.model.impl.EntityAttributeImpl;
import com.jiuqi.nr.entity.internal.model.impl.EntityDefineImpl;
import com.jiuqi.nr.entity.internal.model.impl.EntityReferImpl;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.OrgDataSortDTO;
import com.jiuqi.va.organization.domain.OrgAuthDO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class OrgConvertUtil {
    public static final String ORG_BASE = "MD_ORG";
    public static final String ORG_DIM_SUF = "_CODE";
    public static final String I18N_CODE = "localizedName";
    public static final String ORG_DIM = "MD_ORG";
    private static final Set<String> disableField = new HashSet<String>(10);

    public static IEntityDefine entityConvert(DataModelDO org) {
        EntityDefineImpl impl = new EntityDefineImpl();
        impl.setCode(org.getName());
        impl.setDesc(org.getRemark());
        impl.setDimensionFlag(1);
        impl.setDimensionName("MD_ORG");
        impl.setId(org.getName());
        impl.setIncludeSubTreeEntity("MD_ORG".equals(org.getName()) ? 1 : 0);
        impl.setTitle(org.getTitle());
        return impl;
    }

    public static IEntityDefine entityConvert(OrgCategoryDO org) {
        EntityDefineImpl impl = new EntityDefineImpl();
        impl.setCode(org.getName());
        impl.setDesc(org.getRemark());
        impl.setDimensionFlag(1);
        impl.setDimensionName("MD_ORG");
        impl.setId(org.getName());
        impl.setIncludeSubTreeEntity("MD_ORG".equals(org.getName()) ? 1 : 0);
        impl.setTitle(org.getTitle());
        impl.setIsolation(0);
        impl.setVersion(org.getVersionflag() == null ? 0 : org.getVersionflag());
        impl.setTree(true);
        impl.setAuthFlag(true);
        impl.setCategory("ORG");
        return impl;
    }

    public static IEntityAttribute fieldConvert(IEntityAttribute column, String bizKey, Map<String, String> i18nMap) {
        String title;
        EntityAttributeImpl impl = (EntityAttributeImpl)column;
        if (!CollectionUtils.isEmpty(i18nMap) && StringUtils.hasText(title = i18nMap.get(impl.getCode()))) {
            impl.setTitle(title);
        }
        if ("PARENTCODE".equalsIgnoreCase(column.getCode())) {
            impl.setReferTableID(column.getTableID());
            impl.setReferColumnID(bizKey);
        }
        if ("NAME".equalsIgnoreCase(column.getCode())) {
            impl.setSupportI18n(true);
        }
        if ("CODE".equalsIgnoreCase(column.getCode())) {
            impl.setReferTableID(null);
            impl.setReferColumnID(null);
        }
        return impl;
    }

    public static boolean disabledField(String code) {
        return disableField.contains(code);
    }

    public static IEntityRefer referConvert(String tableName, DataModelColumn column) {
        if (OrgConvertUtil.referEntity(column.getMappingType()) && StringUtils.hasText(column.getMapping())) {
            EntityReferImpl impl = new EntityReferImpl();
            impl.setOwnEntityId(tableName);
            impl.setOwnField(column.getColumnName());
            String[] ref = column.getMapping().split("\\.");
            impl.setReferEntityId(ref[0]);
            impl.setReferEntityField(ref[1]);
            return impl;
        }
        return null;
    }

    private static boolean referEntity(Integer mappingType) {
        return mappingType != null && (mappingType == 1 || mappingType == 4);
    }

    public static OrgDTO paramConvert(IEntityQueryParam query) {
        Map<String, Object> ext;
        List<OrderAttribute> orderField;
        OrgDataOption.AuthType type;
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname(query.getEntityId());
        orgDTO.setVersionDate(query.getVersionDate());
        if (query.getFilter() != null) {
            IEntityDataFilter filter = query.getFilter();
            orgDTO.put(filter.getDataFilter(), (Object)filter);
            orgDTO.setExpression(filter.getExpression());
        }
        Locale locale = NpContextHolder.getContext().getLocale();
        boolean simpleStructModel = false;
        if (locale != null) {
            simpleStructModel = Locale.CHINA.getLanguage().equals(locale.getLanguage());
        }
        if (simpleStructModel) {
            orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.BASIC);
        } else {
            orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        }
        if (!CollectionUtils.isEmpty(query.getMasterKey())) {
            orgDTO.setOrgCodes(query.getMasterKey());
        }
        if ((type = OrgConvertUtil.getAuthType(query)) != null) {
            orgDTO.setAuthType(type);
        }
        Integer offSet = query.getOffSet();
        Integer limit = query.getLimit();
        if (offSet != null && limit != null) {
            orgDTO.setPagination(Boolean.valueOf(true));
            orgDTO.setOffset(offSet);
            orgDTO.setLimit(limit);
        }
        if (!CollectionUtils.isEmpty(orderField = query.getOrderField())) {
            ArrayList<OrgDataSortDTO> sortList = new ArrayList<OrgDataSortDTO>(orderField.size());
            for (OrderAttribute attribute : orderField) {
                OrgDataSortDTO dataSortDTO = new OrgDataSortDTO(attribute.getAttributeCode(), attribute.getType().equals((Object)OrderType.ASC) ? OrgDataOption.OrderType.ASC : OrgDataOption.OrderType.DESC);
                sortList.add(dataSortDTO);
            }
            orgDTO.setOrderBy(sortList);
        } else if (orderField == null) {
            orgDTO.setOrderBy(Collections.emptyList());
        } else if (orderField.isEmpty()) {
            orgDTO.setOrderBy(null);
        }
        orgDTO.setLazyLoad(Boolean.valueOf(query.isLazy()));
        orgDTO.setLeafFlag(Boolean.valueOf(query.isMarkLeaf()));
        if (query.getQueryStop() != null) {
            orgDTO.setStopflag(query.getQueryStop());
        }
        if ((ext = query.getExt()) != null) {
            orgDTO.putAll(ext);
        }
        return orgDTO;
    }

    public static OrgDataOption.AuthType getAuthType(IEntityQueryParam query) {
        OrgDataOption.AuthType type = null;
        if (query.isIgnoreAuth()) {
            type = OrgDataOption.AuthType.NONE;
        } else if (query.isHasReadAuth()) {
            type = OrgDataOption.AuthType.ACCESS;
        } else if (query.isHasWriteAuth()) {
            type = OrgDataOption.AuthType.WRITE;
        }
        return type;
    }

    public static OrgAuthDO createOrgAuthDO(String type, String orgCode) {
        OrgAuthDO org = new OrgAuthDO();
        org.setAtaccess(Integer.valueOf(1));
        org.setAtapproval(Integer.valueOf(1));
        org.setAtdelegate(Integer.valueOf(1));
        org.setAtedit(Integer.valueOf(1));
        org.setAtmanage(Integer.valueOf(1));
        org.setAtreport(Integer.valueOf(1));
        org.setAtsubmit(Integer.valueOf(1));
        org.setAtwrite(Integer.valueOf(1));
        org.setBiztype(Integer.valueOf(1));
        org.setBizname(NpContextHolder.getContext().getUserName());
        org.setAuthtype(Integer.valueOf(1));
        org.setOrgcategory(type);
        org.setOrgname(orgCode);
        return org;
    }

    static {
        disableField.add("CREATEUSER");
        disableField.add("CREATETIME");
        disableField.add("PARENTS");
        disableField.add("ID");
        disableField.add("VER");
        disableField.add("CODE");
        disableField.add("VALIDTIME");
        disableField.add("INVALIDTIME");
        disableField.add("STOPFLAG");
        disableField.add("RECOVERYFLAG");
        disableField.add("ORDINAL");
        disableField.add("UNITCODE");
    }
}

