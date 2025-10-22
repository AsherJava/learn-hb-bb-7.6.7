/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.api.service.IDataFieldViewService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldApplyType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 *  com.jiuqi.nr.period.common.utils.TimeDimField
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.zb.scheme.core.ZbInfo
 *  com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion
 *  com.jiuqi.nr.zb.scheme.service.IZbSchemeService
 *  javax.validation.ConstraintViolation
 *  javax.validation.Validator
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataresource.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.dataresource.DataResourceLink;
import com.jiuqi.nr.dataresource.DimAttribute;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.authority.DataResourceAuthorityService;
import com.jiuqi.nr.dataresource.common.DataResourceConvert;
import com.jiuqi.nr.dataresource.common.DataResourceException;
import com.jiuqi.nr.dataresource.dao.IDataResourceDao;
import com.jiuqi.nr.dataresource.dao.IDimAttributeDao;
import com.jiuqi.nr.dataresource.dao.IResourceLinkDao;
import com.jiuqi.nr.dataresource.dto.DataResourceNodeDTO;
import com.jiuqi.nr.dataresource.dto.DimAttributeDTO;
import com.jiuqi.nr.dataresource.dto.SearchDataFieldDTO;
import com.jiuqi.nr.dataresource.entity.DataResourceDO;
import com.jiuqi.nr.dataresource.entity.DataResourceLinkDO;
import com.jiuqi.nr.dataresource.entity.DimAttributeDO;
import com.jiuqi.nr.dataresource.entity.SearchDataFieldDO;
import com.jiuqi.nr.dataresource.service.IDataLinkService;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineService;
import com.jiuqi.nr.dataresource.service.IDataResourceService;
import com.jiuqi.nr.dataresource.web.param.DataResourceQuery;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.service.IDataFieldViewService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.period.common.utils.TimeDimField;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Transactional(rollbackFor={Exception.class})
public class DataLinkServiceImpl
implements IDataLinkService {
    @Autowired
    private IDataResourceDao resourceDao;
    @Autowired
    private IResourceLinkDao linkDao;
    @Autowired
    private IDimAttributeDao attributeDao;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private Validator validator;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataResourceAuthorityService auth;
    @Autowired
    private IZbSchemeService zbSchemeService;
    @Autowired
    private IDataResourceService dataResourceService;
    @Autowired
    private IDataResourceDefineService dataResourceDefineService;
    @Autowired
    private IDataFieldViewService dataFieldViewService;
    private final Function<DataResourceLink, DataResourceLinkDO> copy = r -> {
        if (r == null) {
            return null;
        }
        return DataResourceConvert.iDl2do(r);
    };
    private final Function<List<DataResourceLink>, List<DataResourceLinkDO>> listCopy = r -> r.stream().map(this.copy).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    @Transactional(readOnly=true)
    public DataResourceLink init() {
        return new DataResourceLinkDO();
    }

    @Override
    public void insert(List<DataResourceLink> entity) {
        Assert.notNull(entity, "entity must not be null.");
        List<DataResourceLinkDO> apply = this.listCopy.apply(entity);
        for (DataResourceLink dataResourceLink : apply) {
            this.validate(dataResourceLink);
        }
        this.linkDao.insert(apply);
    }

    @Override
    public void delete(String groupKey, List<String> keys) {
        Assert.notNull((Object)groupKey, "groupKey must not be null.");
        Assert.notNull(keys, "keys must not be null.");
        this.linkDao.delete(groupKey, keys);
    }

    @Override
    public void delete(String groupKey) {
        Assert.notNull((Object)groupKey, "groupKey must not be null.");
        this.linkDao.delete(groupKey);
    }

    @Override
    public void update(List<DataResourceLink> entity) {
        Assert.notNull(entity, "entity must not be null.");
        List<DataResourceLinkDO> apply = this.listCopy.apply(entity);
        for (DataResourceLink dataResourceLink : apply) {
            Assert.notNull((Object)dataResourceLink.getOrder(), "order must not be null.");
            Assert.notNull((Object)dataResourceLink.getGroupKey(), "group must not be null.");
            Assert.notNull((Object)dataResourceLink.getDataFieldKey(), "dataFieldKey must not be null.");
        }
        this.linkDao.update(apply);
    }

    @Override
    @Transactional(readOnly=true)
    public List<DataField> getByGroupNoPeriod(String groupKey) {
        Assert.notNull((Object)groupKey, "groupKey must not be null.");
        return this.linkDao.getByGroup(groupKey);
    }

    private Map<String, Map<String, ZbInfo>> getSchemeAndZbInfoMap(List<DataField> dataFields, String period) {
        LinkedHashMap<String, Map<String, ZbInfo>> zbMap = new LinkedHashMap<String, Map<String, ZbInfo>>();
        if (!StringUtils.hasText(period)) {
            return zbMap;
        }
        HashSet<String> schemes = new HashSet<String>();
        for (DataField dataField : dataFields) {
            schemes.add(dataField.getDataSchemeKey());
        }
        List dataSchemes = this.runtimeDataSchemeService.getDataSchemes(new ArrayList(schemes));
        for (DataScheme dataScheme : dataSchemes) {
            ZbSchemeVersion version = this.zbSchemeService.getZbSchemeVersion(dataScheme.getZbSchemeKey(), period);
            if (version != null) {
                List zbInfos = this.zbSchemeService.listZbInfoByVersion(version.getKey());
                zbMap.put(dataScheme.getKey(), zbInfos.stream().filter(Objects::nonNull).collect(Collectors.toMap(ZbInfo::getCode, f -> f, (o1, o2) -> o1)));
                continue;
            }
            zbMap.put(dataScheme.getKey(), Collections.emptyMap());
        }
        return zbMap;
    }

    private DataFieldDTO getFieldViewRow(DataField dataField, ZbInfo zbInfo) {
        DataFieldDTO dataFieldDTO = new DataFieldDTO();
        if (dataField == null) {
            return dataFieldDTO;
        }
        if (zbInfo == null) {
            return (DataFieldDTO)dataField;
        }
        dataFieldDTO.setKey(dataField.getKey());
        dataFieldDTO.setDataSchemeKey(dataField.getDataSchemeKey());
        dataFieldDTO.setCode(zbInfo.getCode());
        dataFieldDTO.setTitle(zbInfo.getTitle());
        dataFieldDTO.setDesc(zbInfo.getDesc());
        dataFieldDTO.setDefaultValue(zbInfo.getDefaultValue());
        dataFieldDTO.setPrecision(zbInfo.getPrecision());
        dataFieldDTO.setFormula(zbInfo.getFormula());
        dataFieldDTO.setFormulaDesc(zbInfo.getFormulaDesc());
        dataFieldDTO.setRefDataEntityKey(zbInfo.getRefEntityId());
        dataFieldDTO.setZbType(zbInfo.getType());
        dataFieldDTO.setDecimal(zbInfo.getDecimal());
        dataFieldDTO.setNullable(zbInfo.getNullable());
        dataFieldDTO.setMeasureUnit(zbInfo.getMeasureUnit());
        dataFieldDTO.setDataFieldKind(dataField.getDataFieldKind());
        dataFieldDTO.setOrder(dataField.getOrder());
        if (null != zbInfo.getDataType()) {
            dataFieldDTO.setDataFieldType(DataFieldType.forValues((Integer)zbInfo.getDataType().getValue(), (String)zbInfo.getDataType().getTitle()));
        }
        if (null != zbInfo.getApplyType()) {
            dataFieldDTO.setDataFieldApplyType(DataFieldApplyType.forValues((Integer)zbInfo.getApplyType().getValue(), (String)zbInfo.getApplyType().getTitle()));
        }
        if (null != zbInfo.getGatherType()) {
            dataFieldDTO.setDataFieldGatherType(DataFieldGatherType.forValues((Integer)zbInfo.getGatherType().getValue(), (String)zbInfo.getGatherType().getTitle()));
        }
        return dataFieldDTO;
    }

    private DataFieldDTO getFieldViewRow(DataField dataField) {
        DataFieldDTO dataFieldDTO = new DataFieldDTO();
        dataFieldDTO.setKey(dataField.getKey());
        dataFieldDTO.setDataSchemeKey(dataField.getDataSchemeKey());
        dataFieldDTO.setCode(dataField.getCode());
        dataFieldDTO.setTitle(dataField.getTitle());
        dataFieldDTO.setDesc(dataField.getDesc());
        dataFieldDTO.setOrder(dataField.getOrder());
        dataFieldDTO.setDefaultValue(dataField.getDefaultValue());
        dataFieldDTO.setPrecision(dataField.getPrecision());
        dataFieldDTO.setFormula(dataField.getFormula());
        dataFieldDTO.setFormulaDesc(dataField.getFormulaDesc());
        dataFieldDTO.setRefDataEntityKey(dataField.getRefDataEntityKey());
        dataFieldDTO.setZbType(dataField.getZbType());
        dataFieldDTO.setDecimal(dataField.getDecimal());
        dataFieldDTO.setNullable(dataField.getNullable());
        dataFieldDTO.setMeasureUnit(dataField.getMeasureUnit());
        dataFieldDTO.setDataFieldKind(dataField.getDataFieldKind());
        if (null != dataField.getDataFieldType()) {
            dataFieldDTO.setDataFieldType(dataField.getDataFieldType());
        }
        if (null != dataField.getDataFieldApplyType()) {
            dataFieldDTO.setDataFieldApplyType(dataField.getDataFieldApplyType());
        }
        if (null != dataField.getDataFieldGatherType()) {
            dataFieldDTO.setDataFieldGatherType(dataField.getDataFieldGatherType());
        }
        return dataFieldDTO;
    }

    @Override
    public List<DataField> getByGroup(String groupKey) {
        Assert.notNull((Object)groupKey, "groupKey must not be null.");
        DataResource resource = this.dataResourceService.get(groupKey);
        DataResourceDefine resourceTree = this.dataResourceDefineService.get(resource.getResourceDefineKey());
        List<DataField> byGroup = this.linkDao.getByGroup(groupKey);
        ArrayList<DataField> res = new ArrayList<DataField>();
        String[] keys = (String[])byGroup.stream().map(Basic::getKey).toArray(String[]::new);
        List deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(keys);
        Map<String, DataFieldDeployInfo> deployInfoMap = deployInfos.stream().collect(Collectors.toMap(DataFieldDeployInfo::getDataFieldKey, f -> f, (o1, o2) -> o1));
        Map<String, Map<String, ZbInfo>> zbMap = this.getSchemeAndZbInfoMap(byGroup, resourceTree.getPeriod());
        for (DataField dataField : byGroup) {
            Map<String, ZbInfo> infoMap;
            ZbInfo zbInfo = null;
            if (zbMap.containsKey(dataField.getDataSchemeKey()) && (infoMap = zbMap.get(dataField.getDataSchemeKey())).containsKey(dataField.getCode())) {
                zbInfo = infoMap.get(dataField.getCode());
            }
            DataFieldDTO dataFieldDTO = this.getFieldViewRow(dataField, zbInfo);
            if (deployInfoMap.containsKey(dataField.getKey())) {
                dataFieldDTO.setDataTableKey(deployInfoMap.get(dataField.getKey()).getDataTableKey());
            }
            res.add((DataField)dataFieldDTO);
        }
        return res;
    }

    @Override
    public List<DataResourceLink> getByDataFieldKey(String fieldKey) {
        Assert.notNull((Object)fieldKey, "fieldKey must not be null.");
        List<DataResourceLinkDO> list = this.linkDao.getByDataFieldKey(fieldKey);
        if (list == null) {
            return Collections.emptyList();
        }
        return new ArrayList<DataResourceLink>(list);
    }

    @Override
    public List<SearchDataFieldDTO> searchByPeriod(DataResourceQuery param) {
        List<DataResourceLinkDO> links = this.linkDao.getByDefineKey(param.getDefineKey());
        if (CollectionUtils.isEmpty(links)) {
            return Collections.emptyList();
        }
        String keyword = param.getKeyword().toUpperCase();
        ArrayList<SearchDataFieldDO> searchDataFields = new ArrayList<SearchDataFieldDO>();
        HashMap<String, LinkedHashSet<String>> dataFieldGroupMap = new HashMap<String, LinkedHashSet<String>>();
        for (DataResourceLinkDO link : links) {
            LinkedHashSet<String> groups = (LinkedHashSet<String>)dataFieldGroupMap.get(link.getDataFieldKey());
            if (groups == null) {
                groups = new LinkedHashSet<String>();
                dataFieldGroupMap.put(link.getDataFieldKey(), groups);
            }
            groups.add(link.getGroupKey());
        }
        List dataFields = this.runtimeDataSchemeService.getDataFields(new ArrayList(dataFieldGroupMap.keySet()));
        HashMap zbInfoMap = new HashMap();
        for (DataField field : dataFields) {
            HashMap<String, ZbInfo> zbMap = (HashMap<String, ZbInfo>)zbInfoMap.get(field.getDataSchemeKey());
            if (zbMap == null) {
                zbMap = new HashMap<String, ZbInfo>();
                zbInfoMap.put(field.getDataSchemeKey(), zbMap);
                DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(field.getDataSchemeKey());
                ZbSchemeVersion version = this.zbSchemeService.getZbSchemeVersion(dataScheme.getZbSchemeKey(), param.getPeriod());
                if (version != null) {
                    List zbInfos = this.zbSchemeService.listZbInfoByVersion(version.getKey());
                    zbMap.putAll(zbInfos.stream().filter(Objects::nonNull).collect(Collectors.toMap(ZbInfo::getCode, f -> f, (o1, o2) -> o1)));
                }
            }
            String code = field.getCode();
            String title = field.getTitle();
            ZbInfo zbInfo = (ZbInfo)zbMap.get(code);
            if (zbInfo != null) {
                title = zbInfo.getTitle();
            }
            if (!code.toUpperCase().contains(keyword) && !title.toUpperCase().contains(keyword)) continue;
            Set groups = (Set)dataFieldGroupMap.get(field.getKey());
            for (String group : groups) {
                SearchDataFieldDO s = new SearchDataFieldDO();
                s.setKey(field.getKey());
                s.setCode(code);
                s.setTitle(title);
                s.setGroupKey(group);
                searchDataFields.add(s);
            }
        }
        return this.getSearchDataFieldDTOS(param.getDefineKey(), searchDataFields);
    }

    @Override
    @Transactional(readOnly=true)
    public List<SearchDataFieldDTO> searchByDefineKey(String defineKey, String keyword) {
        Assert.notNull((Object)defineKey, "defineKey must not be null.");
        Assert.notNull((Object)keyword, "keyword must not be null.");
        DataResourceDefine define = this.dataResourceDefineService.get(defineKey);
        if (StringUtils.hasText(define.getPeriod())) {
            DataResourceQuery param = new DataResourceQuery();
            param.setDefineKey(defineKey);
            param.setKeyword(keyword);
            param.setPeriod(define.getPeriod());
            return this.searchByPeriod(param);
        }
        List<SearchDataFieldDO> searchDataFields = this.linkDao.searchByDefineKey(defineKey, keyword);
        return this.getSearchDataFieldDTOS(defineKey, searchDataFields);
    }

    private List<SearchDataFieldDTO> getSearchDataFieldDTOS(String defineKey, List<SearchDataFieldDO> searchDataFields) {
        HashSet<String> groupKeys = new HashSet<String>();
        for (SearchDataFieldDO searchDataField : searchDataFields) {
            groupKeys.add(searchDataField.getGroupKey());
        }
        boolean needFilter = this.auth.canWrite(defineKey, NodeType.TREE.getValue());
        HashMap<String, String> readGroupMap = new HashMap<String, String>();
        List list = this.resourceDao.get(new ArrayList<String>(groupKeys));
        HashMap<String, DataResourceDO> groupMap = new HashMap<String, DataResourceDO>(list.size());
        for (DataResourceDO resourceDO : list) {
            if (needFilter) {
                readGroupMap.put(resourceDO.getKey(), this.auth.canRead(resourceDO.getKey(), NodeType.RESOURCE_GROUP.getValue()) ? "1" : "0");
            }
            groupMap.put(resourceDO.getKey(), resourceDO);
        }
        ArrayList<SearchDataFieldDTO> res = new ArrayList<SearchDataFieldDTO>(searchDataFields.size());
        for (SearchDataFieldDO searchDataField : searchDataFields) {
            String groupKey = searchDataField.getGroupKey();
            DataResourceDO resourceDO = (DataResourceDO)groupMap.get(groupKey);
            if (resourceDO == null || needFilter && !"1".equals(readGroupMap.get(resourceDO.getKey()))) continue;
            res.add(new SearchDataFieldDTO(searchDataField, new DataResourceNodeDTO(resourceDO)));
        }
        return res;
    }

    @Override
    @Transactional(readOnly=true)
    public List<DimAttribute> getDimAttributeByGroup(String groupKey) {
        IEntityModel entityModel;
        Assert.notNull((Object)groupKey, "groupKey must not be null.");
        DataResourceDO resourceDO = (DataResourceDO)this.resourceDao.get(groupKey);
        if (resourceDO == null) {
            return null;
        }
        DataResourceKind resourceKind = resourceDO.getResourceKind();
        int dimGroup = DataResourceKind.DIM_GROUP.getValue() | DataResourceKind.TABLE_DIM_GROUP.getValue();
        if ((resourceKind.getValue() & dimGroup) == 0) {
            return null;
        }
        String dimKey = resourceDO.getDimKey();
        if (dimKey == null) {
            return null;
        }
        String defineKey = resourceDO.getResourceDefineKey();
        List<DimAttributeDO> attrs = this.attributeDao.get(defineKey, dimKey);
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        boolean periodEntity = periodAdapter.isPeriodEntity(dimKey);
        if (periodEntity) {
            try {
                List showFields = periodAdapter.getPeriodEntityShowColumnModel(dimKey);
                List<DimAttribute> dimAttributes = DataResourceConvert.build(showFields, attrs, null, dimKey, defineKey);
                List timeDimFields = BqlTimeDimUtils.getTimeDimFields((String)dimKey);
                for (DimAttribute dimAttribute : dimAttributes) {
                    Optional<TimeDimField> first = timeDimFields.stream().filter(e -> e.getName().equals(dimAttribute.getCode())).findFirst();
                    first.ifPresent(timeDimField -> dimAttribute.setTitle(timeDimField.getTitle()));
                }
                return dimAttributes;
            }
            catch (Exception e2) {
                throw new DataResourceException(e2);
            }
        }
        try {
            entityModel = this.iEntityMetaService.getEntityModel(dimKey);
        }
        catch (Exception e3) {
            throw new DataResourceException(e3);
        }
        return DataResourceConvert.build(entityModel.getShowFields(), attrs, null, dimKey, defineKey);
    }

    @Override
    @Transactional(readOnly=true)
    public List<DimAttribute> getFmDimAttribute(String defineKey, String tableKey, String dimKey) {
        List fields = this.runtimeDataSchemeService.getDataFieldByTable(tableKey);
        List<DimAttributeDO> attrs = this.attributeDao.get(defineKey, dimKey);
        if (!CollectionUtils.isEmpty(fields)) {
            return DataResourceConvert.build(Collections.emptyList(), attrs, fields, dimKey, defineKey);
        }
        return null;
    }

    @Override
    @Transactional(readOnly=true)
    public DimAttribute initDimAttribute() {
        return new DimAttributeDTO();
    }

    @Override
    public void setAttribute(List<DimAttribute> entity) {
        Assert.notNull(entity, "entity must not be null.");
        if (entity.isEmpty()) {
            return;
        }
        ArrayList<DimAttributeDO> list = new ArrayList<DimAttributeDO>(entity.size());
        ArrayList<DimAttributeDO> update = new ArrayList<DimAttributeDO>(entity.size());
        DimAttribute first = entity.get(0);
        String dim = first.getDimKey();
        String tree = first.getResourceDefineKey();
        Assert.notNull((Object)dim, "dim must not be null.");
        Assert.notNull((Object)tree, "tree must not be null.");
        List<DimAttributeDO> oldList = this.attributeDao.get(tree, dim);
        HashSet<DimAttributeDO> oldSet = new HashSet<DimAttributeDO>(oldList);
        for (DimAttribute dimAttribute : entity) {
            if (dimAttribute.getOrder() == null) {
                dimAttribute.setOrder(OrderGenerator.newOrder());
            }
            this.validate(dimAttribute);
            DimAttributeDO dimAttributeDO = DataResourceConvert.iDm2Do(dimAttribute);
            if (oldSet.contains(dimAttributeDO)) {
                update.add(dimAttributeDO);
                continue;
            }
            list.add(dimAttributeDO);
        }
        this.attributeDao.insert(list);
        this.attributeDao.update(update);
    }

    private void validate(Ordered dataResource) throws DataResourceException {
        Set validate = this.validator.validate((Object)dataResource, new Class[0]);
        if (validate != null && !validate.isEmpty()) {
            String message = ((ConstraintViolation)validate.stream().findFirst().get()).getMessage();
            throw new DataResourceException(message);
        }
    }
}

