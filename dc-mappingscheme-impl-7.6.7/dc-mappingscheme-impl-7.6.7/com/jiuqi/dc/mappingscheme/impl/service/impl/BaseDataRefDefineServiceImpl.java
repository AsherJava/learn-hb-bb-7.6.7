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
 *  com.jiuqi.dc.base.common.exception.CheckRuntimeException
 *  com.jiuqi.dc.base.common.intf.impl.TreeDTO
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.mappingscheme.client.common.Columns
 *  com.jiuqi.dc.mappingscheme.client.common.DataRefUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.vo.AssistMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.DataMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.RuleTypeShowVO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.NotNull
 *  org.springframework.transaction.annotation.Propagation
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
import com.jiuqi.dc.base.common.exception.CheckRuntimeException;
import com.jiuqi.dc.base.common.intf.impl.TreeDTO;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.mappingscheme.client.common.Columns;
import com.jiuqi.dc.mappingscheme.client.common.DataRefUtil;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.vo.AssistMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.DataMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.RuleTypeShowVO;
import com.jiuqi.dc.mappingscheme.impl.common.BaseDataDefineTreeNodeType;
import com.jiuqi.dc.mappingscheme.impl.common.Column;
import com.jiuqi.dc.mappingscheme.impl.common.ModelTypeEnum;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.dao.BaseDataMappingDefineDao;
import com.jiuqi.dc.mappingscheme.impl.dao.FieldMappingDefineDao;
import com.jiuqi.dc.mappingscheme.impl.define.IRuleType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IRuleTypeGather;
import com.jiuqi.dc.mappingscheme.impl.domain.BaseDataMappingDefineDO;
import com.jiuqi.dc.mappingscheme.impl.domain.FieldMappingDefineDO;
import com.jiuqi.dc.mappingscheme.impl.enums.SchemeBaseDataRefType;
import com.jiuqi.dc.mappingscheme.impl.event.BaseDataRefDefineDelEvent;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.dc.mappingscheme.impl.service.impl.BaseDataRefDefineCacheProvider;
import com.jiuqi.dc.mappingscheme.impl.util.AdvanceSqlParser;
import com.jiuqi.dc.mappingscheme.impl.util.RefTableSyncUtil;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaseDataRefDefineServiceImpl
implements BaseDataRefDefineService {
    @Autowired
    private BaseDataMappingDefineDao dataMappingDao;
    @Autowired
    private FieldMappingDefineDao fieldMappingDao;
    @Autowired
    private BaseDataRefDefineCacheProvider cacheProvider;
    @Autowired
    private DataSchemeService schemeService;
    @Autowired
    private DimensionService assistDimService;
    @Autowired
    private AdvanceSqlParser sqlParser;
    @Autowired
    private IRuleTypeGather ruleTypeGather;
    @Autowired
    private BaseDataRefDefineService baseDataRefDefineService;
    private static final String NODE_TYPE = "nodetype";

    @Override
    public List<RuleTypeShowVO> ruleType() {
        List<IRuleType> ruleTypeList = this.ruleTypeGather.listAll();
        ArrayList<RuleTypeShowVO> options = new ArrayList<RuleTypeShowVO>(ruleTypeList.size());
        for (IRuleType ruleType : ruleTypeList) {
            options.add(new RuleTypeShowVO(ruleType.getCode(), ruleType.getName(), ruleType.getItem2Item(), ruleType.getRuleTypeClass()));
        }
        return options;
    }

    @Override
    public List<TreeDTO> tree(DataRefDefineListDTO dto) {
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
        List<String> filterDim = SchemeBaseDataRefType.getSchemeBaseDataRefList();
        for (BaseDataMappingDefineDTO define : this.list(dto)) {
            if (filterDim.contains(define.getCode())) continue;
            TreeDTO node = treeNodeMap.get(define.getDataSchemeCode());
            if (node != null) {
                node.addChild(this.convert2TreeNode((DataMappingDefineDTO)define));
                continue;
            }
            treeList.add(this.convert2TreeNode((DataMappingDefineDTO)define));
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
        HashMap<String, Object> attributes = new HashMap<String, Object>(2);
        attributes.put(NODE_TYPE, (Object)BaseDataDefineTreeNodeType.ITEM);
        attributes.put("PLUGINTYPE", define.getPluginType());
        node.setAttributes(attributes);
        return node;
    }

    @Override
    @ParamCheck
    public List<BaseDataMappingDefineDTO> list(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataRefDefineListDTO dto) {
        ArrayList<BaseDataMappingDefineDTO> list = new ArrayList<BaseDataMappingDefineDTO>();
        DataSchemeDTO dataScheme = this.schemeService.findByCode(dto.getDataSchemeCode());
        String dataSchemeCode = dataScheme.getCode();
        String pluginType = dataScheme.getPluginType();
        DataMappingVO dataMapping = dataScheme.getDataMapping();
        list.add(this.buildBaseDataMappingDefine(dataSchemeCode, pluginType, (DimMappingVO)dataMapping.getOrgMapping(), dataMapping.getOrgMapping().getName(), dataMapping.getOrgMapping().getCode()));
        if (Objects.nonNull(dataMapping.getSubjectMapping())) {
            list.add(this.buildBaseDataMappingDefine(dataSchemeCode, pluginType, dataMapping.getSubjectMapping(), dataMapping.getSubjectMapping().getName(), dataMapping.getSubjectMapping().getCode()));
        }
        if (Objects.nonNull(dataMapping.getCurrencyMapping())) {
            list.add(this.buildBaseDataMappingDefine(dataSchemeCode, pluginType, dataMapping.getCurrencyMapping(), dataMapping.getCurrencyMapping().getName(), dataMapping.getCurrencyMapping().getCode()));
        }
        if (Objects.nonNull(dataMapping.getCfitemMapping())) {
            list.add(this.buildBaseDataMappingDefine(dataSchemeCode, pluginType, dataMapping.getCfitemMapping(), dataMapping.getCfitemMapping().getName(), dataMapping.getCfitemMapping().getCode()));
        }
        Map<String, DimensionVO> dimensionVOMap = this.assistDimService.loadAllDimensions().stream().collect(Collectors.toMap(DimensionVO::getCode, item -> item, (o1, o2) -> o2));
        for (AssistMappingVO assistMappingVO : dataMapping.getAssistMapping()) {
            DimensionVO dimensionVO;
            if (assistMappingVO.getCode() == null || StringUtils.isEmpty((String)assistMappingVO.getAdvancedSql()) || Objects.isNull(dimensionVO = dimensionVOMap.get(assistMappingVO.getCode())) || StringUtils.isEmpty((String)dimensionVO.getReferField())) continue;
            list.add(this.buildBaseDataMappingDefine(dataSchemeCode, pluginType, (DimMappingVO)assistMappingVO, dimensionVO.getTitle(), dimensionVO.getReferField()));
        }
        return list;
    }

    private BaseDataMappingDefineDTO buildBaseDataMappingDefine(String dataSchemeCode, String pluginType, DimMappingVO dimMappingVO, String name, String relTableName) {
        BaseDataMappingDefineDTO dto = new BaseDataMappingDefineDTO();
        dto.setDataSchemeCode(dataSchemeCode);
        dto.setPluginType(pluginType);
        dto.setCode(dimMappingVO.getCode());
        dto.setName(name);
        dto.setRelTableName(relTableName);
        dto.setIsolationStrategy(dimMappingVO.getIsolationStrategy());
        dto.setRuleType(dimMappingVO.getRuleType());
        dto.setAdvancedSql(dimMappingVO.getAdvancedSql());
        dto.setAutoMatchDim(dimMappingVO.getAutoMatchDim());
        ArrayList<FieldMappingDefineDTO> items = new ArrayList<FieldMappingDefineDTO>();
        dto.setItems(items);
        List baseMapping = dimMappingVO.getBaseMapping();
        if (CollectionUtils.isEmpty((Collection)baseMapping)) {
            return dto;
        }
        for (Columns columns : baseMapping) {
            FieldMappingDefineDTO fieldMappingDefineDTO = new FieldMappingDefineDTO();
            fieldMappingDefineDTO.setFieldName(columns.getFieldName());
            fieldMappingDefineDTO.setFieldTitle(columns.getFieldTitle());
            fieldMappingDefineDTO.setOdsFieldName(columns.getOdsFieldName());
            items.add(fieldMappingDefineDTO);
        }
        return dto;
    }

    private boolean filterByCondi(DataRefDefineListDTO dto, BaseDataMappingDefineDTO item) {
        if (!StringUtils.isEmpty((String)dto.getDataSchemeCode()) && !item.getDataSchemeCode().startsWith(dto.getDataSchemeCode())) {
            return false;
        }
        if (!StringUtils.isEmpty((String)dto.getCode()) && !item.getCode().startsWith(dto.getCode())) {
            return false;
        }
        return StringUtils.isEmpty((String)dto.getSearchKey()) || item.getCode().contains(dto.getSearchKey()) || item.getName().contains(dto.getSearchKey());
    }

    @Override
    public List<BaseDataMappingDefineDTO> listBySchemeCode(@NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") String schemeCode) {
        return this.list(new DataRefDefineListDTO(schemeCode));
    }

    @Override
    @ParamCheck
    public BaseDataMappingDefineDTO findById(@NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") String id) {
        return this.findDataById(id, null);
    }

    @Override
    @ParamCheck
    public BaseDataMappingDefineDTO findByCode(@NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") String schemeCode, @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") String code) {
        return this.findDataByCode(schemeCode, code, null);
    }

    @Override
    @ParamCheck
    public BaseDataMappingDefineDTO getById(@NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") String id) {
        BaseDataMappingDefineDTO data = this.findById(id);
        if (data == null) {
            throw new BusinessRuntimeException(String.format("\u6807\u8bc6\u3010%1$s\u3011\u7684\u6570\u636e\u4e0d\u5b58\u5728", id));
        }
        return data;
    }

    @Override
    @ParamCheck
    public BaseDataMappingDefineDTO getByCode(@NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") String schemeCode, @NotBlank(message="\u6570\u636e\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49") @NotBlank(message="\u6570\u636e\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49") String code) {
        BaseDataMappingDefineDTO data = this.findByCode(schemeCode, code);
        if (data == null) {
            throw new BusinessRuntimeException(String.format("\u6570\u636e\u6e90\u3010%1$s\u3011\u4ee3\u7801\u3010%2$s\u3011\u7684\u6570\u636e\u4e0d\u5b58\u5728", schemeCode, code));
        }
        return data;
    }

    private BaseDataMappingDefineDTO findDataByCode(String schemeCode, String code, Long ver) {
        List<BaseDataMappingDefineDTO> list = this.list(new DataRefDefineListDTO(schemeCode));
        return list.stream().filter(item -> code.equals(item.getCode())).findFirst().orElse(null);
    }

    private BaseDataMappingDefineDTO findDataById(String id, Long ver) {
        return this.cacheProvider.list().stream().filter(item -> id.equals(item.getId()) && (ver == null || ver != null && ver.compareTo(item.getVer()) == 0)).findFirst().orElse(null);
    }

    @Override
    @ParamCheck
    public BaseDataMappingDefineDTO fixed(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataMappingDefineDTO dto) {
        return null;
    }

    @Override
    public List<DimensionVO> listAssistDim() {
        List<String> filterDim = SchemeBaseDataRefType.getSchemeBaseDataRefList();
        return this.assistDimService.loadAllDimensions().stream().filter(item -> !StringUtils.isEmpty((String)item.getReferField()) && !filterDim.contains(item.getCode())).collect(Collectors.toList());
    }

    @Override
    public List<SelectOptionVO> parseSql(@NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a") String dataSchemeCode, @NotBlank(message="SQL\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="SQL\u4e0d\u80fd\u4e3a\u7a7a") String sql) {
        DataSchemeDTO dataScheme = this.schemeService.getByCode(dataSchemeCode);
        return this.parseSqlByDataSource(dataScheme.getDataSourceCode(), sql);
    }

    @Override
    public List<SelectOptionVO> parseSqlByDataSource(String dataSourceCode, String sql) {
        this.verifySpecialSQL(sql);
        List<Column> columnList = this.sqlParser.parseSql(dataSourceCode, sql);
        return columnList.stream().map(item -> {
            SelectOptionVO selOption = new SelectOptionVO();
            selOption.setCode(item.getName());
            selOption.setName(item.getTitle());
            return selOption;
        }).collect(Collectors.toList());
    }

    public void verifySpecialSQL(String sql) {
        String lowerSql = sql.toLowerCase();
        if (Pattern.matches("[\\s\\S]*insert\\s+into\\s+[\\s\\S]*values[\\s\\S]*", lowerSql)) {
            throw new IllegalArgumentException("\u201c\u6620\u5c04\u5b9a\u4e49\u201d\u7684sql\u4e2d\u4e0d\u5141\u8bb8\u4f7f\u7528insert\u8bed\u53e5");
        }
        if (Pattern.matches("[\\s\\S]*update\\s+[\\s\\S]+set\\s+[\\s\\S]*", lowerSql)) {
            throw new IllegalArgumentException("\u201c\u6620\u5c04\u5b9a\u4e49\u201d\u7684sql\u4e2d\u4e0d\u5141\u8bb8\u4f7f\u7528update\u8bed\u53e5");
        }
        if (Pattern.matches("[\\s\\S]*merge\\s+into\\s+[\\s\\S]*", lowerSql)) {
            throw new IllegalArgumentException("\u201c\u6620\u5c04\u5b9a\u4e49\u201d\u7684sql\u4e2d\u4e0d\u5141\u8bb8\u4f7f\u7528merge\u8bed\u53e5");
        }
        if (Pattern.matches("[\\s\\S]*delete\\s+from\\s+[\\s\\S]*", lowerSql)) {
            throw new IllegalArgumentException("\u201c\u6620\u5c04\u5b9a\u4e49\u201d\u7684sql\u4e2d\u4e0d\u5141\u8bb8\u4f7f\u7528delete\u8bed\u53e5");
        }
        if (Pattern.matches("[\\s\\S]*truncate\\s+table\\s+[\\s\\S]*", lowerSql)) {
            throw new IllegalArgumentException("\u201c\u6620\u5c04\u5b9a\u4e49\u201d\u7684sql\u4e2d\u4e0d\u5141\u8bb8\u4f7f\u7528truncate\u8bed\u53e5");
        }
        if (Pattern.matches("[\\s\\S]*create\\s+((table+)|(user+)|(tablespace+)|(database+)|([\\s\\S]*index+))\\s+[\\s\\S]*", lowerSql)) {
            throw new IllegalArgumentException("\u201c\u6620\u5c04\u5b9a\u4e49\u201d\u7684sql\u4e2d\u4e0d\u5141\u8bb8\u4f7f\u7528create\u8bed\u53e5");
        }
        if (Pattern.matches("[\\s\\S]*alter\\s+table\\s+[\\s\\S]*", lowerSql)) {
            throw new IllegalArgumentException("\u201c\u6620\u5c04\u5b9a\u4e49\u201d\u7684sql\u4e2d\u4e0d\u5141\u8bb8\u4f7f\u7528alter\u8bed\u53e5");
        }
        if (Pattern.matches("[\\s\\S]*drop\\s+((table+)|(user+)|(tablespace+)|(database+)|(index+))\\s+[\\s\\S]*", lowerSql)) {
            throw new IllegalArgumentException("\u201c\u6620\u5c04\u5b9a\u4e49\u201d\u7684sql\u4e2d\u4e0d\u5141\u8bb8\u4f7f\u7528drop\u8bed\u53e5");
        }
        if (lowerSql.contains(";") || lowerSql.contains("\uff1b")) {
            throw new IllegalArgumentException("\u201c\u6620\u5c04\u5b9a\u4e49\u201d\u7684sql\u4e2d\u4e0d\u5141\u8bb8\u4f7f\u7528\u5206\u53f7");
        }
    }

    @Override
    @ParamCheck
    @Transactional(rollbackFor={Exception.class})
    public Boolean create(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") BaseDataMappingDefineDTO dto) {
        this.baseDataRefDefineService.schemeInitCreate(dto);
        String title = StringUtils.join((Object[])new String[]{"\u65b0\u589e", dto.getCode(), dto.getName()}, (String)"-");
        LogHelper.info((String)DcFunctionModuleEnum.BASEDATAMAPPINGDEFINE.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)dto));
        return true;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean schemeInitCreate(BaseDataMappingDefineDTO dto) {
        if (StringUtils.isEmpty((String)dto.getAdvancedSql())) {
            return false;
        }
        Assert.isNotEmpty((String)dto.getDataSchemeCode(), (String)"\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dto.getCode(), (String)"\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dto.getName(), (String)"\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dto.getRuleType(), (String)"\u89c4\u5219\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((Collection)dto.getItems(), (String)"\u5b9a\u4e49\u660e\u7ec6\u8bb0\u5f55\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        this.check(dto);
        BaseDataMappingDefineDTO savedDo = this.findByCode(dto.getDataSchemeCode(), dto.getCode());
        if (savedDo != null) {
            throw new BusinessRuntimeException(String.format("\u4ee3\u7801\u3010%1$s\u3011\u7684\u6570\u636e\u5df2\u5b58\u5728", dto.getCode()));
        }
        BaseDataMappingDefineDO domain = (BaseDataMappingDefineDO)BeanConvertUtil.convert((Object)dto, BaseDataMappingDefineDO.class, (String[])new String[0]);
        domain.setId(UUIDUtils.newUUIDStr());
        domain.setVer(0L);
        domain.setModelType(ModelTypeEnum.BASEDATA.getCode());
        domain.setCreateTime(new Date());
        ArrayList<FieldMappingDefineDO> items = new ArrayList<FieldMappingDefineDO>(dto.getItems().size());
        Long ordinal = 1L;
        for (FieldMappingDefineDTO item : dto.getItems()) {
            FieldMappingDefineDO itemDo = (FieldMappingDefineDO)BeanConvertUtil.convert((Object)item, FieldMappingDefineDO.class, (String[])new String[0]);
            itemDo.setId(UUIDUtils.newUUIDStr());
            itemDo.setDataMappingId(domain.getId());
            itemDo.setDataSchemeCode(domain.getDataSchemeCode());
            itemDo.setTableName(dto.getRelTableName());
            itemDo.setRuleType("#");
            itemDo.setFixedFlag(BooleanValEnum.NO.getCode());
            Long l = ordinal;
            Long l2 = ordinal = Long.valueOf(ordinal + 1L);
            itemDo.setOrdinal(l);
            items.add(itemDo);
        }
        this.dataMappingDao.insert(domain);
        this.fieldMappingDao.batchInsert(items);
        BaseDataRefDefineServiceImpl baseDataRefDefineService = (BaseDataRefDefineServiceImpl)ApplicationContextRegister.getBean(BaseDataRefDefineServiceImpl.class);
        baseDataRefDefineService.syncTable(domain.getRuleType(), domain.getCode());
        this.cacheProvider.syncCache();
        return true;
    }

    private void check(BaseDataMappingDefineDTO dto) {
        if (dto.getCode().length() > 60) {
            throw new CheckRuntimeException("\u4ee3\u7801\u957f\u5ea6\u4e0d\u80fd\u5927\u4e8e60");
        }
        if (dto.getName().length() > 100) {
            throw new CheckRuntimeException("\u540d\u79f0\u957f\u5ea6\u4e0d\u80fd\u5927\u4e8e100");
        }
        IRuleType ruleType = this.ruleTypeGather.getRuleTypeByCode(dto.getRuleType());
        if (ruleType == null) {
            throw new CheckRuntimeException(String.format("\u89c4\u5219\u7c7b\u578b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", dto.getRuleType()));
        }
        if (dto.getAdvancedSql().length() > 2000) {
            throw new CheckRuntimeException("\u6620\u5c04\u5b9a\u4e49\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc72000");
        }
        Set selOptionSet = this.parseSql(dto.getDataSchemeCode(), dto.getAdvancedSql()).stream().map(SelectOptionVO::getCode).collect(Collectors.toSet());
        if (!selOptionSet.contains("ID")) {
            throw new CheckRuntimeException("\u6620\u5c04\u5b9a\u4e49\u9700\u8981\u5305\u542bID\u5217");
        }
        if (!selOptionSet.contains("CODE")) {
            throw new CheckRuntimeException("\u6620\u5c04\u5b9a\u4e49\u9700\u8981\u5305\u542bCODE\u5217");
        }
        if (!selOptionSet.contains("NAME")) {
            throw new CheckRuntimeException("\u6620\u5c04\u5b9a\u4e49\u9700\u8981\u5305\u542bNAME\u5217");
        }
        this.schemeService.getByCode(dto.getDataSchemeCode());
        dto.setAutoMatchDim(StringUtils.isEmpty((String)dto.getAutoMatchDim()) ? "#" : dto.getAutoMatchDim());
        dto.setAutoMatchDim(dto.getAutoMatchDim().toUpperCase());
    }

    @Override
    @ParamCheck
    @Transactional(rollbackFor={Exception.class})
    public Boolean modify(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") BaseDataMappingDefineDTO dto) {
        Assert.isNotEmpty((String)dto.getDataSchemeCode(), (String)"\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dto.getCode(), (String)"\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dto.getName(), (String)"\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dto.getRuleType(), (String)"\u89c4\u5219\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((Collection)dto.getItems(), (String)"\u5b9a\u4e49\u660e\u7ec6\u8bb0\u5f55\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dto.getAdvancedSql(), (String)"\u6620\u5c04\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        this.check(dto);
        BaseDataMappingDefineDTO savedDo = this.getById(dto.getId());
        BaseDataMappingDefineDO domain = new BaseDataMappingDefineDO();
        domain.setId(dto.getId());
        domain.setVer(dto.getVer());
        domain.setName(dto.getName());
        domain.setAdvancedSql(dto.getAdvancedSql());
        domain.setRuleType(dto.getRuleType());
        domain.setAutoMatchDim(dto.getAutoMatchDim());
        int i = this.dataMappingDao.update(domain);
        if (i != 1) {
            throw new CheckRuntimeException("\u6570\u636e\u5df2\u88ab\u4fee\u6539\u6216\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        IRuleType oldRuleType = this.ruleTypeGather.getRuleTypeByCode(savedDo.getRuleType());
        IRuleType newRuleType = this.ruleTypeGather.getRuleTypeByCode(domain.getRuleType());
        if (oldRuleType.getItem2Item().booleanValue() && newRuleType.getItem2Item().booleanValue() && !Objects.equals(oldRuleType.getRuleTypeClass().getCode(), newRuleType.getRuleTypeClass().getCode())) {
            ApplicationContextRegister.getApplicationContext().publishEvent(new BaseDataRefDefineDelEvent((Object)this, (DataMappingDefineDTO)savedDo));
            String title = StringUtils.join((Object[])new String[]{"\u5220\u9664", savedDo.getCode(), savedDo.getName(), savedDo.getRuleType()}, (String)"-");
            LogHelper.info((String)DcFunctionModuleEnum.DATAMAPPING.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)savedDo));
        }
        ArrayList<FieldMappingDefineDO> items = new ArrayList<FieldMappingDefineDO>(dto.getItems().size());
        Long ordinal = 1L;
        for (FieldMappingDefineDTO item : dto.getItems()) {
            FieldMappingDefineDO itemDo = (FieldMappingDefineDO)BeanConvertUtil.convert((Object)item, FieldMappingDefineDO.class, (String[])new String[0]);
            itemDo.setId(UUIDUtils.newUUIDStr());
            itemDo.setDataMappingId(domain.getId());
            itemDo.setDataSchemeCode(savedDo.getDataSchemeCode());
            itemDo.setTableName(dto.getRelTableName());
            itemDo.setRuleType("#");
            itemDo.setFixedFlag(BooleanValEnum.NO.getCode());
            Long l = ordinal;
            Long l2 = ordinal = Long.valueOf(ordinal + 1L);
            itemDo.setOrdinal(l);
            items.add(itemDo);
        }
        this.fieldMappingDao.deleteByDataMappingId(domain.getId());
        this.fieldMappingDao.batchInsert(items);
        this.syncTable(domain.getRuleType(), savedDo.getCode());
        this.cacheProvider.syncCache();
        String title = StringUtils.join((Object[])new String[]{"\u4fee\u6539", dto.getCode(), dto.getName()}, (String)"-");
        LogHelper.info((String)DcFunctionModuleEnum.BASEDATAMAPPINGDEFINE.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)dto));
        return true;
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void syncTable(String ruleType, String tableName) {
        if (!RuleType.isItemByItem(ruleType).booleanValue()) {
            return;
        }
        RefTableSyncUtil.syncTable(tableName);
    }

    @Override
    @ParamCheck
    @Transactional(rollbackFor={Exception.class})
    public Boolean delete(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") BaseDataMappingDefineDTO dto) {
        BaseDataMappingDefineDTO savedData = this.baseDataRefDefineService.schemeInitDelete(dto);
        if (RuleType.isItemByItem(savedData.getRuleType()).booleanValue()) {
            ApplicationContextRegister.getApplicationContext().publishEvent(new BaseDataRefDefineDelEvent((Object)this, (DataMappingDefineDTO)savedData));
        }
        String title = StringUtils.join((Object[])new String[]{"\u5220\u9664", savedData.getCode(), savedData.getName()}, (String)"-");
        LogHelper.info((String)DcFunctionModuleEnum.BASEDATAMAPPINGDEFINE.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)savedData));
        return true;
    }

    @Override
    public BaseDataMappingDefineDTO schemeInitDelete(BaseDataMappingDefineDTO dto) {
        Assert.isNotEmpty((String)dto.getId(), (String)"\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)dto.getVer(), (String)"\u7248\u672c\u53f7\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        BaseDataMappingDefineDTO savedData = this.findDataById(dto.getId(), dto.getVer());
        if (savedData == null) {
            throw new CheckRuntimeException("\u6570\u636e\u5df2\u88ab\u4fee\u6539\u6216\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        BaseDataMappingDefineDO domain = new BaseDataMappingDefineDO();
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
    public List<Columns> getRefTableColumns(String tableName) {
        List<String> columnList = this.dataMappingDao.getTableColumn(tableName);
        if (CollectionUtils.isEmpty(columnList)) {
            return CollectionUtils.newArrayList();
        }
        columnList = columnList.stream().map(String::toUpperCase).collect(Collectors.toList());
        ArrayList<Columns> columnsList = new ArrayList<Columns>();
        HashMap titleMap = CollectionUtils.newHashMap();
        titleMap.put("ODS_UNITCODE", "\u7ec4\u7ec7\u673a\u6784");
        titleMap.put("ODS_BOOKCODE", "\u8d26\u7c3f");
        titleMap.put("ODS_ACCTYEAR", "\u5e74\u5ea6");
        titleMap.put("ODS_CUSTOM_CODE", "\u81ea\u5b9a\u4e49\u4ee3\u7801");
        titleMap.put("ODS_ASSISTCODE", "\u5355\u4f4d\u6269\u5c55\u7ef4\u5ea6\u4ee3\u7801");
        titleMap.put("ODS_ASSISTNAME", "\u5355\u4f4d\u6269\u5c55\u7ef4\u5ea6\u540d\u79f0");
        titleMap.put("REMARK", "\u5907\u6ce8");
        for (String columnName : columnList) {
            if (!columnName.startsWith("ODS_") && !Objects.equals(columnName, "REMARK")) continue;
            Columns column = new Columns();
            column.setFieldName(columnName.substring(columnName.indexOf("_") + 1));
            column.setFieldTitle(titleMap.getOrDefault(columnName, columnName));
            column.setOdsFieldName("");
            columnsList.add(column);
        }
        return columnsList;
    }
}

