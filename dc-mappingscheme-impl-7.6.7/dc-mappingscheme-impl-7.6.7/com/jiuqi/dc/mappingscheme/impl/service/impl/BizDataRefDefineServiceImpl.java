/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.annotation.ParamCheck
 *  com.jiuqi.dc.base.common.enums.BooleanValEnum
 *  com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum
 *  com.jiuqi.dc.base.common.event.SyncVchrTableEvent
 *  com.jiuqi.dc.base.common.exception.CheckRuntimeException
 *  com.jiuqi.dc.base.common.intf.impl.TreeDTO
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.mappingscheme.client.common.DataRefUtil
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.NotNull
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.dc.mappingscheme.impl.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.annotation.ParamCheck;
import com.jiuqi.dc.base.common.enums.BooleanValEnum;
import com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum;
import com.jiuqi.dc.base.common.event.SyncVchrTableEvent;
import com.jiuqi.dc.base.common.exception.CheckRuntimeException;
import com.jiuqi.dc.base.common.intf.impl.TreeDTO;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.mappingscheme.client.common.DataRefUtil;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.common.BaseDataDefineTreeNodeType;
import com.jiuqi.dc.mappingscheme.impl.common.ModelTypeEnum;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.dao.DataMappingDefineDao;
import com.jiuqi.dc.mappingscheme.impl.dao.FieldMappingDefineDao;
import com.jiuqi.dc.mappingscheme.impl.define.IFieldMappingProvider;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import com.jiuqi.dc.mappingscheme.impl.define.IRuleType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IDataSchemeInitializerGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IFieldMappingProviderGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IOrgMappingTypeProviderGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IRuleTypeGather;
import com.jiuqi.dc.mappingscheme.impl.domain.DataMappingDefineDO;
import com.jiuqi.dc.mappingscheme.impl.domain.FieldMappingDefineDO;
import com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.dc.mappingscheme.impl.service.impl.BizDataRefDefineCacheProvider;
import com.jiuqi.dc.mappingscheme.impl.service.impl.DataSchemeCacheProvider;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BizDataRefDefineServiceImpl
implements BizDataRefDefineService {
    @Autowired
    private DataSchemeCacheProvider dataSchemeCacheProvider;
    @Autowired
    private DataMappingDefineDao dataMappingDao;
    @Autowired
    private FieldMappingDefineDao fieldMappingDao;
    @Autowired
    private BizDataRefDefineCacheProvider cacheProvider;
    @Autowired
    private DataSchemeService schemeService;
    @Autowired
    private IFieldMappingProviderGather fieldMappingProviderGather;
    @Autowired
    private IOrgMappingTypeProviderGather orgMappingTypeGather;
    @Autowired
    private IPluginTypeGather pluginTypeGather;
    @Autowired
    private DimensionService assistDimService;
    @Autowired
    private IDataSchemeInitializerGather iDataSchemeInitializerGather;
    @Autowired
    private BizDataRefDefineService bizDataRefDefineService;
    @Autowired
    private IRuleTypeGather ruleTypeGather;
    private static final String NODE_TYPE = "nodetype";

    @Override
    @ParamCheck
    public List<TreeDTO> tree(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataRefDefineListDTO dto) {
        List<DataSchemeDTO> schemeList = this.schemeService.listAllStart();
        if (CollectionUtils.isEmpty(schemeList)) {
            return CollectionUtils.newArrayList();
        }
        List<TreeDTO> treeList = schemeList.stream().filter(item -> {
            if (StringUtils.isEmpty((String)dto.getDataSchemeCode())) {
                return true;
            }
            return item.getCode().equals(dto.getDataSchemeCode());
        }).map(item -> this.convert2TreeNode((DataSchemeDTO)item)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(treeList)) {
            return CollectionUtils.newArrayList();
        }
        Map<String, TreeDTO> treeNodeMap = treeList.stream().collect(Collectors.toMap(TreeDTO::getCode, item -> item, (k1, k2) -> k2));
        List dataMappingDefineDTOList = this.list(dto).stream().sorted(Comparator.comparingInt(o -> this.fieldMappingProviderGather.getProvider(this.pluginTypeGather.getPluginType(o.getPluginType()), o.getCode()).showOrder())).collect(Collectors.toList());
        for (DataMappingDefineDTO define : dataMappingDefineDTOList) {
            TreeDTO node = treeNodeMap.get(define.getDataSchemeCode());
            if (node != null) {
                node.addChild(this.convert2TreeNode(define));
                continue;
            }
            treeList.add(this.convert2TreeNode(define));
        }
        return treeList;
    }

    private TreeDTO convert2TreeNode(DataSchemeDTO item) {
        TreeDTO node = new TreeDTO();
        node.setId(item.getId());
        node.setCode(item.getCode());
        node.setTitle(DataRefUtil.getNodeLabel((String)item.getCode(), (String)item.getName()));
        node.setParentCode("-");
        node.setLeaf(Boolean.valueOf(true));
        node.setNodeType(BaseDataDefineTreeNodeType.SCHEME.getCode());
        HashMap<String, Object> attributes = new HashMap<String, Object>(2);
        attributes.put(NODE_TYPE, (Object)BaseDataDefineTreeNodeType.SCHEME);
        attributes.put("PLUGINTYPE", item.getPluginType());
        node.setAttributes(attributes);
        return node;
    }

    private TreeDTO convert2TreeNode(DataMappingDefineDTO define) {
        TreeDTO node = new TreeDTO();
        node.setId(define.getId());
        node.setCode(define.getCode());
        node.setTitle(DataRefUtil.getNodeLabel((String)define.getCode(), (String)define.getName()));
        node.setParentCode(define.getDataSchemeCode());
        node.setLeaf(Boolean.valueOf(true));
        node.setNodeType(BaseDataDefineTreeNodeType.ITEM.getCode());
        HashMap<String, Object> attributes = new HashMap<String, Object>(1);
        attributes.put(NODE_TYPE, (Object)BaseDataDefineTreeNodeType.ITEM);
        attributes.put("PLUGINTYPE", define.getPluginType());
        node.setAttributes(attributes);
        return node;
    }

    @Override
    @ParamCheck
    public List<DataMappingDefineDTO> list(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataRefDefineListDTO dto) {
        ArrayList<DataMappingDefineDTO> list = new ArrayList<DataMappingDefineDTO>();
        DataSchemeDTO dataSchemeDTO = this.schemeService.getByCode(dto.getDataSchemeCode());
        IPluginType pluginType = this.pluginTypeGather.getPluginType(dataSchemeDTO.getPluginType());
        List<SelectOptionVO> bizDataConvertList = pluginType.getBizDataConvertList();
        for (SelectOptionVO bizDataType : bizDataConvertList) {
            DataMappingDefineDTO dataMappingDefineDTO = new DataMappingDefineDTO();
            dataMappingDefineDTO.setCode(bizDataType.getCode());
            dataMappingDefineDTO.setName(bizDataType.getName());
            dataMappingDefineDTO.setDataSchemeCode(dataSchemeDTO.getCode());
            dataMappingDefineDTO.setPluginType(dataSchemeDTO.getPluginType());
            list.add(dataMappingDefineDTO);
        }
        return list;
    }

    private boolean filterByCondi(DataRefDefineListDTO dto, DataMappingDefineDTO item) {
        if (!StringUtils.isEmpty((String)dto.getDataSchemeCode()) && !item.getDataSchemeCode().startsWith(dto.getDataSchemeCode())) {
            return false;
        }
        if (!StringUtils.isEmpty((String)dto.getCode()) && !item.getCode().startsWith(dto.getCode())) {
            return false;
        }
        return StringUtils.isEmpty((String)dto.getSearchKey()) || item.getCode().contains(dto.getSearchKey()) || item.getName().contains(dto.getSearchKey());
    }

    @Override
    @ParamCheck
    public List<DataMappingDefineDTO> listBySchemeCode(@NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") String schemeCode) {
        return this.list(new DataRefDefineListDTO(schemeCode));
    }

    @Override
    @ParamCheck
    public DataMappingDefineDTO findById(@NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") String id) {
        return this.findDataById(id, null);
    }

    @Override
    @ParamCheck
    public DataMappingDefineDTO findByCode(@NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") String schemeCode, @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") String code) {
        return this.findDataByCode(schemeCode, code, null);
    }

    @Override
    @ParamCheck
    public DataMappingDefineDTO getById(@NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") String id) {
        DataMappingDefineDTO data = this.findById(id);
        if (data == null) {
            throw new BusinessRuntimeException(String.format("\u6807\u8bc6\u3010%1$s\u3011\u7684\u6570\u636e\u4e0d\u5b58\u5728", id));
        }
        return data;
    }

    @Override
    @ParamCheck
    public DataMappingDefineDTO getByCode(@NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") String schemeCode, @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") String code) {
        DataMappingDefineDTO data = this.findByCode(schemeCode, code);
        if (data == null) {
            throw new BusinessRuntimeException(String.format("\u6570\u636e\u6e90\u3010%1$s\u3011\u4ee3\u7801\u3010%2$s\u3011\u7684\u6570\u636e\u4e0d\u5b58\u5728", schemeCode, code));
        }
        return data;
    }

    private DataMappingDefineDTO findDataByCode(String schemeCode, String code, Long ver) {
        if (ver == null) {
            DataSchemeDTO dataSchemeDTO = (DataSchemeDTO)this.dataSchemeCacheProvider.get(schemeCode);
            return new DataMappingDefineDTO(dataSchemeDTO, code);
        }
        return this.cacheProvider.list().stream().filter(item -> schemeCode.equals(item.getDataSchemeCode()) && code.equals(item.getCode()) && ver.compareTo(item.getVer()) == 0).findFirst().orElse(null);
    }

    private DataMappingDefineDTO findDataById(String id, Long ver) {
        return this.cacheProvider.list().stream().filter(item -> id.equals(item.getId()) && (ver == null || ver != null && ver.compareTo(item.getVer()) == 0)).findFirst().orElse(null);
    }

    @Override
    @ParamCheck
    public List<SelectOptionVO> listMappingTable(@NotBlank(message="\u63d2\u4ef6\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u63d2\u4ef6\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a") String pluginTypeStr) {
        IPluginType pluginType = this.pluginTypeGather.getPluginType(pluginTypeStr);
        if (pluginType == null) {
            throw new BusinessRuntimeException(String.format("\u63d2\u4ef6\u7c7b\u578b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", pluginTypeStr));
        }
        List<IFieldMappingProvider> providerList = this.fieldMappingProviderGather.listByPluginType(pluginType);
        return providerList.stream().map(item -> {
            SelectOptionVO optionVo = new SelectOptionVO();
            optionVo.setCode(item.getCode());
            optionVo.setName(item.getName());
            return optionVo;
        }).collect(Collectors.toList());
    }

    @Override
    @ParamCheck
    public List<FieldDTO> listOdsFields(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataMappingDefineDTO dto) {
        Assert.isNotEmpty((String)dto.getDataSchemeCode());
        DataSchemeDTO scheme = this.schemeService.getByCode(dto.getDataSchemeCode());
        IPluginType pluginType = this.pluginTypeGather.getPluginType(scheme.getPluginType());
        if (pluginType == null) {
            throw new BusinessRuntimeException(String.format("\u6ca1\u6709\u83b7\u53d6\u5230%1$s\u7684\u63d2\u4ef6\u7c7b\u578b", scheme.getPluginType()));
        }
        IFieldMappingProvider provider = this.fieldMappingProviderGather.getProvider(pluginType, dto.getCode());
        if (provider == null) {
            throw new BusinessRuntimeException(String.format("\u6ca1\u6709\u83b7\u53d6\u5230%1$s\u7684\u6620\u5c04\u5b57\u6bb5", pluginType.getSymbol()));
        }
        return provider.listOdsField(scheme);
    }

    @Override
    @ParamCheck
    @Transactional(rollbackFor={Exception.class})
    public Boolean create(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataMappingDefineDTO dto) {
        this.bizDataRefDefineService.schemeInitCreate(dto);
        String title = StringUtils.join((Object[])new String[]{"\u65b0\u589e", dto.getDataSchemeCode(), dto.getName()}, (String)"-");
        LogHelper.info((String)DcFunctionModuleEnum.BIZDATAMAPPINGDEFINE.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)dto));
        return true;
    }

    @Override
    public Boolean schemeInitCreate(DataMappingDefineDTO dto) {
        Assert.isNotEmpty((String)dto.getDataSchemeCode(), (String)"\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dto.getName(), (String)"\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        this.check(dto);
        DataMappingDefineDTO savedDo = this.findByCode(dto.getDataSchemeCode(), dto.getCode());
        if (savedDo != null) {
            throw new BusinessRuntimeException(String.format("\u4ee3\u7801\u3010%1$s\u3011\u7684\u6570\u636e\u5df2\u5b58\u5728", dto.getCode()));
        }
        ArrayList itemByItemAssistFlags = CollectionUtils.newArrayList();
        DataMappingDefineDO domain = (DataMappingDefineDO)BeanConvertUtil.convert((Object)dto, DataMappingDefineDO.class, (String[])new String[0]);
        domain.setId(UUIDUtils.newUUIDStr());
        domain.setVer(0L);
        domain.setModelType(ModelTypeEnum.BIZDATA.getCode());
        ArrayList<FieldMappingDefineDO> items = new ArrayList<FieldMappingDefineDO>(dto.getItems().size());
        Long ordinal = 1L;
        for (FieldMappingDefineDTO item : dto.getItems()) {
            FieldMappingDefineDO itemDo = (FieldMappingDefineDO)BeanConvertUtil.convert((Object)item, FieldMappingDefineDO.class, (String[])new String[0]);
            itemDo.setId(UUIDUtils.newUUIDStr());
            itemDo.setDataMappingId(domain.getId());
            itemDo.setDataSchemeCode(domain.getDataSchemeCode());
            itemDo.setTableName(domain.getCode());
            Long l = ordinal;
            Long l2 = ordinal = Long.valueOf(ordinal + 1L);
            itemDo.setOrdinal(l);
            itemDo.setFieldMappingType(item.getFieldMappingType());
            items.add(itemDo);
            if (!RuleType.isItemByItem(item.getRuleType()).booleanValue()) continue;
            itemByItemAssistFlags.add(item.getFieldName());
        }
        this.dataMappingDao.insert(domain);
        this.fieldMappingDao.batchInsert(items);
        if (!itemByItemAssistFlags.isEmpty()) {
            ApplicationContextRegister.getApplicationContext().publishEvent((ApplicationEvent)new SyncVchrTableEvent((Object)this, ShiroUtil.getTenantName(), (List)itemByItemAssistFlags));
        }
        this.cacheProvider.syncCache();
        return true;
    }

    private void check(DataMappingDefineDTO dto) {
        if (dto.getCode().length() > 60) {
            throw new CheckRuntimeException("\u4ee3\u7801\u957f\u5ea6\u4e0d\u80fd\u5927\u4e8e60");
        }
        if (dto.getName().length() > 100) {
            throw new CheckRuntimeException("\u540d\u79f0\u957f\u5ea6\u4e0d\u80fd\u5927\u4e8e100");
        }
        DataSchemeDTO dataScheme = this.schemeService.getByCode(dto.getDataSchemeCode());
        IFieldMappingProvider provider = this.fieldMappingProviderGather.getProvider(this.pluginTypeGather.getPluginType(dataScheme.getPluginType()), dto.getCode());
        if (provider == null) {
            throw new BusinessRuntimeException(String.format("\u63d2\u4ef6\u7c7b\u578b\u3010%1$s\u3011\u4ee3\u7801\u3010%2$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6620\u5c04\u5b57\u6bb5\u63d0\u4f9b\u5668", dataScheme.getPluginType(), dto.getCode()));
        }
        StringBuffer msg = new StringBuffer();
        int rowNum = 0;
        for (FieldMappingDefineDTO item : dto.getItems()) {
            ++rowNum;
            if (StringUtils.isEmpty((String)item.getFieldName())) {
                msg.append("\u3010").append(item.getFieldTitle()).append("\u3011\u7ef4\u5ea6\u4e00\u672c\u8d26\u5b57\u6bb5\u6807\u8bc6\u4e3a\u7a7a").append("/");
                continue;
            }
            if (StringUtils.isEmpty((String)item.getFieldTitle())) {
                msg.append("\u3010").append(item.getFieldTitle()).append("\u3011\u7ef4\u5ea6\u4e00\u672c\u8d26\u5b57\u6bb5\u540d\u4e3a\u7a7a").append("/");
                continue;
            }
            if (StringUtils.isEmpty((String)item.getOdsFieldName())) {
                msg.append("\u3010").append(item.getFieldTitle()).append("\u3011\u7ef4\u5ea6\u6620\u5c04\u5b57\u6bb5\u4e3a\u7a7a\uff0c\u8bf7\u586b\u5199").append("/");
                continue;
            }
            if (StringUtils.isEmpty((String)item.getRuleType())) {
                msg.append("\u3010").append(item.getFieldTitle()).append("\u3011\u7ef4\u5ea6\u6620\u5c04\u8f6c\u6362\u7c7b\u578b\u4e3a\u7a7a\uff0c\u8bf7\u586b\u5199").append("/");
                continue;
            }
            IRuleType itemRuleType = this.ruleTypeGather.getRuleTypeByCode(item.getRuleType());
            if (itemRuleType == null) {
                msg.append("\u3010").append(item.getFieldTitle()).append("\u3011\u7ef4\u5ea6").append(String.format("\u6620\u5c04\u7c7b\u578b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", item.getRuleType())).append("/");
                continue;
            }
            if (item.getFixedFlag() == null) {
                msg.append("\u3010").append(item.getFieldTitle()).append("\u3011\u5185\u7f6e\u6807\u8bc6\u4e3a\u7a7a").append("/");
                continue;
            }
            item.setFixedFlag(item.getFixedFlag().compareTo(BooleanValEnum.YES.getCode()) == 0 ? BooleanValEnum.YES.getCode() : BooleanValEnum.NO.getCode());
        }
        if (msg.length() != 0) {
            throw new CheckRuntimeException(msg.toString());
        }
    }

    @Override
    @ParamCheck
    @Transactional(rollbackFor={Exception.class})
    public Boolean modify(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataMappingDefineDTO dto) {
        Assert.isNotEmpty((String)dto.getDataSchemeCode(), (String)"\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dto.getName(), (String)"\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((Collection)dto.getItems(), (String)"\u5b9a\u4e49\u660e\u7ec6\u8bb0\u5f55\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        this.check(dto);
        DataMappingDefineDTO savedDo = this.getById(dto.getId());
        ArrayList itemByItemAssistFlags = CollectionUtils.newArrayList();
        ArrayList<FieldMappingDefineDO> items = new ArrayList<FieldMappingDefineDO>(dto.getItems().size());
        Long ordinal = 1L;
        for (FieldMappingDefineDTO item : dto.getItems()) {
            FieldMappingDefineDO itemDo = (FieldMappingDefineDO)BeanConvertUtil.convert((Object)item, FieldMappingDefineDO.class, (String[])new String[0]);
            itemDo.setId(UUIDUtils.newUUIDStr());
            itemDo.setDataMappingId(dto.getId());
            itemDo.setDataSchemeCode(savedDo.getDataSchemeCode());
            itemDo.setTableName(savedDo.getCode());
            Long l = ordinal;
            Long l2 = ordinal = Long.valueOf(ordinal + 1L);
            itemDo.setOrdinal(l);
            items.add(itemDo);
            if (!RuleType.isItemByItem(item.getRuleType()).booleanValue()) continue;
            itemByItemAssistFlags.add(item.getFieldName());
        }
        this.fieldMappingDao.deleteByDataMappingId(dto.getId());
        this.fieldMappingDao.batchInsert(items);
        if (!itemByItemAssistFlags.isEmpty()) {
            ApplicationContextRegister.getApplicationContext().publishEvent((ApplicationEvent)new SyncVchrTableEvent((Object)this, ShiroUtil.getTenantName(), (List)itemByItemAssistFlags));
        }
        this.cacheProvider.syncCache();
        String title = StringUtils.join((Object[])new String[]{"\u4fee\u6539", dto.getDataSchemeCode(), dto.getName()}, (String)"-");
        LogHelper.info((String)DcFunctionModuleEnum.BIZDATAMAPPINGDEFINE.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)dto));
        return true;
    }

    @Override
    @ParamCheck
    @Transactional(rollbackFor={Exception.class})
    public Boolean delete(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataMappingDefineDTO dto) {
        DataMappingDefineDTO savedData = this.bizDataRefDefineService.schemeInitDelete(dto);
        String title = StringUtils.join((Object[])new String[]{"\u5220\u9664", savedData.getDataSchemeCode(), savedData.getName()}, (String)"-");
        LogHelper.info((String)DcFunctionModuleEnum.BIZDATAMAPPINGDEFINE.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)savedData));
        return true;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public DataMappingDefineDTO schemeInitDelete(DataMappingDefineDTO dto) {
        Assert.isNotEmpty((String)dto.getId(), (String)"\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)dto.getVer(), (String)"\u7248\u672c\u53f7\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        DataMappingDefineDTO savedData = this.findDataById(dto.getId(), dto.getVer());
        if (savedData == null) {
            throw new CheckRuntimeException("\u6570\u636e\u5df2\u88ab\u4fee\u6539\u6216\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        DataMappingDefineDO domain = new DataMappingDefineDO();
        domain.setId(dto.getId());
        domain.setVer(dto.getVer());
        int i = this.dataMappingDao.delete(domain);
        if (i != 1) {
            throw new CheckRuntimeException("\u6570\u636e\u5df2\u88ab\u4fee\u6539\u6216\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        this.fieldMappingDao.deleteByDataMappingId(dto.getId());
        this.cacheProvider.syncCache();
        return savedData;
    }

    @Override
    @ParamCheck
    public String preview(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataMappingDefineDTO dto) {
        return null;
    }
}

