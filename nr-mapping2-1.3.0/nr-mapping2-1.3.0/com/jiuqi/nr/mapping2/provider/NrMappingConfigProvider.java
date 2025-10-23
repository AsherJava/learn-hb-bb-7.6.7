/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.provider.BaseDataInfo
 *  com.jiuqi.nvwa.mapping.provider.ColumnInfo
 *  com.jiuqi.nvwa.mapping.provider.ColumnValue
 *  com.jiuqi.nvwa.mapping.provider.IMappingConfigProvider
 *  com.jiuqi.nvwa.mapping.provider.MappingProperty
 *  com.jiuqi.nvwa.mapping.provider.MappingResource
 *  com.jiuqi.nvwa.mapping.provider.PluginInfo
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.mapping2.provider;

import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.mapping2.common.NrMappingUtil;
import com.jiuqi.nr.mapping2.provider.INrMappingType;
import com.jiuqi.nr.mapping2.provider.NrMappingCollector;
import com.jiuqi.nr.mapping2.provider.NrMappingParam;
import com.jiuqi.nr.mapping2.provider.NrMappingResource;
import com.jiuqi.nr.mapping2.provider.impl.MappingTypeConst;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.provider.BaseDataInfo;
import com.jiuqi.nvwa.mapping.provider.ColumnInfo;
import com.jiuqi.nvwa.mapping.provider.ColumnValue;
import com.jiuqi.nvwa.mapping.provider.IMappingConfigProvider;
import com.jiuqi.nvwa.mapping.provider.MappingProperty;
import com.jiuqi.nvwa.mapping.provider.MappingResource;
import com.jiuqi.nvwa.mapping.provider.PluginInfo;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NrMappingConfigProvider
implements IMappingConfigProvider {
    protected final Logger logger = LoggerFactory.getLogger(IMappingConfigProvider.class);
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IFMDMAttributeService ifmdmAttributeService;
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeCtrl;
    @Autowired
    private IRunTimeViewController runtimeCtrl;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private BaseDataDefineClient basedataDefineClient;
    @Autowired
    private NrMappingCollector collector;

    public MappingProperty getProperty() {
        return new MappingProperty("NrTask", new PluginInfo("@nr", "nr-mapping2-property-plugin"), true);
    }

    public List<MappingResource> getResources(MappingScheme scheme) {
        ArrayList<NrMappingResource> res = new ArrayList<NrMappingResource>();
        res.add(MappingTypeConst.ZB);
        res.add(MappingTypeConst.PERIOD);
        res.add(MappingTypeConst.FORMULA);
        NrMappingParam mappingParam = NrMappingUtil.getNrMappingParam(scheme);
        if (mappingParam != null && StringUtils.hasText(mappingParam.getType())) {
            ArrayList<NrMappingResource> resources = this.collector.getTypeMap().get(mappingParam.getType()).getResources(scheme);
            res.addAll(resources);
            return res.stream().sorted(Comparator.comparing(NrMappingResource::getOrder)).collect(Collectors.toList());
        }
        return res.stream().sorted(Comparator.comparing(NrMappingResource::getOrder)).collect(Collectors.toList());
    }

    public List<PluginInfo> getExtendButtons(MappingScheme scheme) {
        NrMappingParam mappingParam = NrMappingUtil.getNrMappingParam(scheme);
        if (mappingParam != null && StringUtils.hasText(mappingParam.getType())) {
            return this.collector.getTypeMap().get(mappingParam.getType()).getExtendButtons(scheme);
        }
        return Collections.emptyList();
    }

    public boolean supportFastGetBaseData() {
        return true;
    }

    public List<BaseDataInfo> getBaseDataList(MappingScheme scheme) {
        NrMappingParam mappingParam = NrMappingUtil.getNrMappingParam(scheme);
        if (mappingParam != null) {
            String taskKey = mappingParam.getTaskKey();
            String formSchemeKey = mappingParam.getFormSchemeKey();
            if (StringUtils.hasText(taskKey) && StringUtils.hasText(formSchemeKey)) {
                return this.queryAllBaseData(taskKey, formSchemeKey);
            }
        }
        return Collections.emptyList();
    }

    private List<BaseDataInfo> queryAllBaseData(String taskKey, String formSchemeKey) {
        ArrayList<BaseDataInfo> baseDataVOList = new ArrayList<BaseDataInfo>();
        ArrayList formList = new ArrayList();
        List allFormGroups = this.runtimeCtrl.getAllFormGroupsInFormScheme(formSchemeKey);
        for (FormGroupDefine formGroup : allFormGroups) {
            try {
                List forms = this.runtimeCtrl.getAllFormsInGroup(formGroup.getKey());
                formList.addAll(forms);
            }
            catch (Exception e) {
                this.logger.error(formGroup.getTitle() + "NR\u6620\u5c04\u7c7b\u578b\uff1a\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u57fa\u6570\u6570\u636e\u5f02\u5e38\uff1a\uff1a" + e.getMessage(), e);
            }
        }
        HashSet<String> set = new HashSet<String>();
        ArrayList<String> fieldKeys = new ArrayList<String>();
        TaskDefine taskDefine = null;
        List<IFMDMAttribute> attributes = null;
        for (FormDefine form : formList) {
            if (FormType.FORM_TYPE_NEWFMDM.equals((Object)form.getFormType())) {
                if (taskDefine == null) {
                    taskDefine = this.runtimeCtrl.queryTaskDefine(taskKey);
                }
                List allLinks = this.runTimeAuthViewController.getAllLinksInForm(form.getKey());
                for (DataLinkDefine link : allLinks) {
                    if (link.getType().getValue() == DataLinkType.DATA_LINK_TYPE_FMDM.getValue()) {
                        attributes = this.buildAttrBasedataVo(formSchemeKey, baseDataVOList, set, taskDefine, attributes, link);
                        continue;
                    }
                    fieldKeys.add(0, link.getLinkExpression());
                }
                continue;
            }
            List keys = this.runtimeCtrl.getFieldKeysInForm(form.getKey());
            fieldKeys.addAll(keys);
        }
        List fields = this.dataRuntimeCtrl.queryFieldDefinesInRange(fieldKeys);
        for (FieldDefine field : fields) {
            String entityId = field.getEntityKey();
            BaseDataInfo baseDataVO = this.buildBaseDataVo(set, entityId);
            if (baseDataVO == null) continue;
            baseDataVOList.add(baseDataVO);
        }
        return baseDataVOList;
    }

    @NotNull
    private List<IFMDMAttribute> buildAttrBasedataVo(String formSchemeKey, List<BaseDataInfo> baseDataVOList, Set<String> set, TaskDefine taskDefine, List<IFMDMAttribute> attributes, DataLinkDefine link) {
        IFMDMAttribute attribute;
        String entityId;
        BaseDataInfo baseDataVO;
        Optional<IFMDMAttribute> findAttribute;
        if (attributes == null) {
            FMDMAttributeDTO fMDMAttributeDTO = new FMDMAttributeDTO();
            fMDMAttributeDTO.setEntityId(taskDefine.getDw());
            fMDMAttributeDTO.setFormSchemeKey(formSchemeKey);
            attributes = this.ifmdmAttributeService.list(fMDMAttributeDTO);
        }
        if ((findAttribute = attributes.stream().filter(e -> e.getCode().equals(link.getLinkExpression())).findFirst()).isPresent() && (baseDataVO = this.buildBaseDataVo(set, entityId = (attribute = findAttribute.get()).getEntityId())) != null) {
            baseDataVOList.add(0, baseDataVO);
        }
        return attributes;
    }

    private BaseDataInfo buildBaseDataVo(Set<String> set, String entityId) {
        BaseDataInfo baseDataVO = null;
        if (StringUtils.hasText(entityId) && BaseDataAdapterUtil.isBaseData((String)entityId)) {
            String entityCode = this.entityMetaService.getEntityCode(entityId);
            BaseDataDefineDTO dto = new BaseDataDefineDTO();
            dto.setName(entityCode);
            BaseDataDefineDO defineDO = this.basedataDefineClient.get(dto);
            if (!set.add(defineDO.getName())) {
                return baseDataVO;
            }
            baseDataVO = new BaseDataInfo();
            baseDataVO.setCode(defineDO.getName());
            baseDataVO.setTitle(defineDO.getTitle());
            baseDataVO.setKey(defineDO.getId().toString());
        }
        return baseDataVO;
    }

    public PluginInfo getOrgSelectorPlugin() {
        return new PluginInfo("@nr", "nr-mapping2-org-select-plugin");
    }

    public String getOrgMappingTip(MappingScheme scheme) {
        NrMappingParam mappingParam = NrMappingUtil.getNrMappingParam(scheme);
        if (mappingParam != null && StringUtils.hasText(mappingParam.getType())) {
            return this.collector.getTypeMap().get(mappingParam.getType()).getOrgMappingTip(scheme);
        }
        return "";
    }

    public boolean showOrgParentMapping(MappingScheme scheme) {
        NrMappingParam mappingParam = NrMappingUtil.getNrMappingParam(scheme);
        if (mappingParam != null && StringUtils.hasText(mappingParam.getType())) {
            return this.collector.getTypeMap().get(mappingParam.getType()).showOrgParentMapping(scheme);
        }
        return false;
    }

    public List<ColumnInfo> getCustomCols() {
        ArrayList<ColumnInfo> cols = new ArrayList<ColumnInfo>();
        cols.add(new ColumnInfo("NR_SCHEME_TYPE", "\u65b9\u6848\u7c7b\u578b", 1.0));
        return cols;
    }

    public Map<String, ColumnValue> getCustomColValueMap(MappingScheme scheme) {
        HashMap<String, ColumnValue> res = new HashMap<String, ColumnValue>();
        res.put("NR_SCHEME_TYPE", new ColumnValue(this.getTypeTitle(scheme)));
        return res;
    }

    private String getTypeTitle(MappingScheme scheme) {
        NrMappingParam nrMappingParam = NrMappingUtil.getNrMappingParam(scheme);
        if (nrMappingParam != null) {
            if (!StringUtils.hasText(nrMappingParam.getType())) {
                return "\u4e2d\u95f4\u5e93\u6620\u5c04";
            }
            INrMappingType nrMappingType = this.collector.getTypeMap().get(nrMappingParam.getType());
            if (nrMappingType != null) {
                return nrMappingType.getTypeTitle();
            }
            return "\u4e2d\u95f4\u5e93\u6620\u5c04";
        }
        return "";
    }

    public boolean needOrderScheme() {
        return true;
    }

    public int orderValue(MappingScheme scheme) {
        if (!scheme.getSource().contains("NR-MAPPING-FACTORY")) {
            return 9999;
        }
        NrMappingParam nrMappingParam = NrMappingUtil.getNrMappingParam(scheme);
        if (nrMappingParam != null && StringUtils.hasText(nrMappingParam.getType())) {
            return (int)this.collector.getTypeMap().get(nrMappingParam.getType()).getOrder();
        }
        this.logger.info("\u6620\u5c04\u65b9\u6848\u7c7b\u578b\u4e3a\u7a7a\uff0c\u65b9\u6848code\uff1a{} \uff0c\u65b9\u6848\u6807\u9898\uff1a{} \uff0c\u65b9\u6848\u5386\u53f2extParam\uff1a{}", scheme.getCode(), scheme.getTitle(), scheme.getExtParam());
        return 9999;
    }
}

