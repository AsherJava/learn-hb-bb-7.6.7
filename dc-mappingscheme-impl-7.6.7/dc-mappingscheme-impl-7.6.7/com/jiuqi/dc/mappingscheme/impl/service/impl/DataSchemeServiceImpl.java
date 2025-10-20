/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.authz.licence.FuncPointInfo
 *  com.jiuqi.bi.authz.licence.LicenceManager
 *  com.jiuqi.bi.authz.licence.ModuleInfo
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  com.jiuqi.bi.database.sql.command.SQLCommand
 *  com.jiuqi.bi.database.sql.command.StatementCommand
 *  com.jiuqi.bi.database.sql.parser.SQLCommandParser
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.database.statement.CreateTableStatement
 *  com.jiuqi.bi.database.statement.interpret.ISQLInterpretor
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.annotation.ParamCheck
 *  com.jiuqi.dc.base.common.enums.BooleanValEnum
 *  com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum
 *  com.jiuqi.dc.base.common.enums.LicenceSymbolEnum
 *  com.jiuqi.dc.base.common.enums.NotSuptTempDataBaseEnum
 *  com.jiuqi.dc.base.common.enums.OptionType
 *  com.jiuqi.dc.base.common.enums.StopFlagEnum
 *  com.jiuqi.dc.base.common.exception.CheckRuntimeException
 *  com.jiuqi.dc.base.common.intf.impl.TreeDTO
 *  com.jiuqi.dc.base.common.utils.AsyncCallBackUtil
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.base.common.utils.CommonUtil
 *  com.jiuqi.dc.base.common.utils.Pair
 *  com.jiuqi.dc.base.common.vo.DataSchemeVO
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.mappingscheme.client.common.DataRefUtil
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.common.SaveMappingSchemeEvent
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeListDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.OrgMappingTypeDTO
 *  com.jiuqi.dc.mappingscheme.client.vo.AdvancedMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.AssistMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.DataMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.DataSchemeOptionVO
 *  com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.OrgMappingVO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nvwa.sf.Framework
 *  com.jiuqi.va.basedata.service.BaseDataService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.query.common.BusinessResponseEntity
 *  com.jiuqi.va.query.datasource.web.QueryDataSourceClient
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.NotNull
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.dc.mappingscheme.impl.service.impl;

import com.jiuqi.bi.authz.licence.FuncPointInfo;
import com.jiuqi.bi.authz.licence.LicenceManager;
import com.jiuqi.bi.authz.licence.ModuleInfo;
import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.sql.command.SQLCommand;
import com.jiuqi.bi.database.sql.command.StatementCommand;
import com.jiuqi.bi.database.sql.parser.SQLCommandParser;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.annotation.ParamCheck;
import com.jiuqi.dc.base.common.enums.BooleanValEnum;
import com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum;
import com.jiuqi.dc.base.common.enums.LicenceSymbolEnum;
import com.jiuqi.dc.base.common.enums.NotSuptTempDataBaseEnum;
import com.jiuqi.dc.base.common.enums.OptionType;
import com.jiuqi.dc.base.common.enums.StopFlagEnum;
import com.jiuqi.dc.base.common.exception.CheckRuntimeException;
import com.jiuqi.dc.base.common.intf.impl.TreeDTO;
import com.jiuqi.dc.base.common.utils.AsyncCallBackUtil;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.base.common.utils.CommonUtil;
import com.jiuqi.dc.base.common.utils.Pair;
import com.jiuqi.dc.base.common.vo.DataSchemeVO;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.mappingscheme.client.common.DataRefUtil;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.common.SaveMappingSchemeEvent;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeListDTO;
import com.jiuqi.dc.mappingscheme.client.dto.OrgMappingTypeDTO;
import com.jiuqi.dc.mappingscheme.client.vo.AdvancedMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.AssistMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.DataMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.DataSchemeOptionVO;
import com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.OrgMappingVO;
import com.jiuqi.dc.mappingscheme.impl.common.DataTableInfo;
import com.jiuqi.dc.mappingscheme.impl.common.DateTableEnum;
import com.jiuqi.dc.mappingscheme.impl.dao.DataSchemeDao;
import com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeInitializer;
import com.jiuqi.dc.mappingscheme.impl.define.IFieldMappingProvider;
import com.jiuqi.dc.mappingscheme.impl.define.IOrgMappingTypeProvider;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IDataSchemeInitializerGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IFieldMappingProviderGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IOrgMappingTypeProviderGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather;
import com.jiuqi.dc.mappingscheme.impl.domain.DataSchemeDO;
import com.jiuqi.dc.mappingscheme.impl.enums.SchemeBaseDataRefType;
import com.jiuqi.dc.mappingscheme.impl.event.BaseDataRefDefineDelEvent;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataRefCheckService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeOptionService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.dc.mappingscheme.impl.service.impl.DataSchemeCacheProvider;
import com.jiuqi.dc.mappingscheme.impl.util.RefTableSyncUtil;
import com.jiuqi.dc.mappingscheme.impl.util.SchemeInitUtil;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.datasource.web.QueryDataSourceClient;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataSchemeServiceImpl
implements DataSchemeService {
    public static final int MAX_SCHEME_NUM = 100;
    @Autowired
    private DataSchemeDao dataSchemeDao;
    @Autowired
    private DataSchemeCacheProvider cacheProvider;
    @Autowired
    private IPluginTypeGather pluginTypeGather;
    @Autowired
    private QueryDataSourceClient dataSourceClient;
    @Autowired
    private IDataSchemeInitializerGather initializerGather;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private DataSchemeOptionService optionService;
    @Autowired
    private IOrgMappingTypeProviderGather orgMappingTypeGather;
    @Autowired
    private IFieldMappingProviderGather iFieldMappingProviderGather;
    @Autowired
    private DimensionService assistDimService;
    @Autowired
    private BaseDataService baseDataService;
    @Autowired
    private DataRefCheckService dataRefCheckService;
    @Autowired
    private BaseDataRefDefineService baseDataRefDefineService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String DEFAULT_CODE = "01";
    private static final String PREFIX = "0";

    @Override
    public List<DataSchemeVO> listPluginType() {
        Framework framework = Framework.getInstance();
        LicenceManager licenceManager = framework.getLicenceManager();
        HashMap<String, Boolean> softVersionsMap = new HashMap<String, Boolean>();
        try {
            for (ModuleInfo moduleInfo : licenceManager.getProductLicence(framework.getProductId()).getModules()) {
                if (!"com.jiuqi.datacenter".equals(moduleInfo.getId()) && !"com.jiuqi.bde".equals(moduleInfo.getId())) continue;
                for (FuncPointInfo funcPointInfo : moduleInfo.getFuncPoints()) {
                    String pluginType;
                    if (funcPointInfo.getId().startsWith("com.jiuqi.datacenter.plugin.") && (softVersionsMap.get(pluginType = funcPointInfo.getId().substring(28).toUpperCase()) == null || Boolean.FALSE.equals(softVersionsMap.get(pluginType)))) {
                        softVersionsMap.put(pluginType, Boolean.TRUE.equals(funcPointInfo.getValue()));
                    }
                    if (!funcPointInfo.getId().startsWith("com.jiuqi.bde.plugin.") || softVersionsMap.get(pluginType = funcPointInfo.getId().substring(21).toUpperCase()) != null && !Boolean.FALSE.equals(softVersionsMap.get(pluginType))) continue;
                    softVersionsMap.put(pluginType, Boolean.TRUE.equals(funcPointInfo.getValue()));
                }
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u63d2\u4ef6\u6388\u6743\u8bfb\u53d6\u6709\u8bef\uff0c\u8bf7\u786e\u8ba4\u3002");
        }
        ArrayList<DataSchemeVO> options = new ArrayList<DataSchemeVO>(20);
        for (IPluginType pluginType : this.pluginTypeGather.list()) {
            if (StringUtils.isEmpty((String)pluginType.getLicenceSymbol()) || "UNKNOWN".equals(pluginType.getSymbol())) continue;
            String licenceSymbol = pluginType.getLicenceSymbol().toUpperCase();
            if (!LicenceSymbolEnum.PROJECT.getSymbol().equals(licenceSymbol) && (softVersionsMap.get(licenceSymbol) == null || !((Boolean)softVersionsMap.get(licenceSymbol)).booleanValue())) continue;
            options.add(new DataSchemeVO(pluginType.getSymbol(), pluginType.getTitle(), pluginType.sourceDataType(), pluginType.needEtlJob(), null));
        }
        return options.stream().sorted(Comparator.comparing(item -> {
            IPluginType type = this.pluginTypeGather.getPluginType(item.getCode());
            return type.getOrder();
        })).collect(Collectors.toList());
    }

    @Override
    public List<SelectOptionVO> listDataSource() {
        BusinessResponseEntity response = this.dataSourceClient.getAllDataSource();
        ArrayList<SelectOptionVO> options = new ArrayList<SelectOptionVO>(10);
        SelectOptionVO current = new SelectOptionVO();
        current.setName("\u5f53\u524d\u6570\u636e\u6e90");
        current.setCode("current");
        options.add(current);
        ((List)response.getData()).stream().forEach(item -> {
            SelectOptionVO optionVo = new SelectOptionVO();
            optionVo.setCode(item.getCode());
            optionVo.setName(item.getName());
            options.add(optionVo);
        });
        return options;
    }

    @Override
    public List<TreeDTO> tree(DataSchemeDTO dto) {
        ArrayList<TreeDTO> tree = new ArrayList<TreeDTO>(1);
        TreeDTO root = new TreeDTO();
        root.setId("root");
        root.setCode("root");
        root.setParentId("-");
        root.setTitle("\u6570\u636e\u6620\u5c04\u65b9\u6848");
        List treeNodes = this.listAll().stream().map(item -> this.convert2TreeNode((DataSchemeDTO)item)).collect(Collectors.toList());
        root.setChildren(treeNodes);
        tree.add(root);
        return tree;
    }

    @Override
    public List<DataSchemeDTO> listAll() {
        return this.cacheProvider.list();
    }

    @Override
    public List<DataSchemeDTO> listAllStart() {
        return this.cacheProvider.list().stream().filter(item -> StopFlagEnum.START.getCode().compareTo(item.getStopFlag()) == 0).collect(Collectors.toList());
    }

    @Override
    @ParamCheck
    public List<DataSchemeDTO> list(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataSchemeListDTO dto) {
        List<Object> list = StringUtils.isEmpty((String)dto.getSearchKey()) && StringUtils.isEmpty((String)dto.getPluginType()) ? this.listAll() : this.listAll().stream().filter(item -> {
            if (!StringUtils.isEmpty((String)dto.getSearchKey()) && item.getCode().contains(dto.getSearchKey())) {
                return true;
            }
            if (!StringUtils.isEmpty((String)dto.getSearchKey()) && item.getName().contains(dto.getSearchKey())) {
                return true;
            }
            return !StringUtils.isEmpty((String)dto.getPluginType()) && item.getPluginType().equals(dto.getPluginType());
        }).collect(Collectors.toList());
        return list.stream().map(item -> {
            DataSchemeDTO dataSchemeDTO = new DataSchemeDTO();
            BeanUtils.copyProperties(item, dataSchemeDTO);
            dataSchemeDTO.setDataMapping(null);
            dataSchemeDTO.setOptions(null);
            return dataSchemeDTO;
        }).collect(Collectors.toList());
    }

    @Override
    @ParamCheck
    public DataSchemeDTO findById(@NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") String id) {
        Object fieldDTO;
        DataSchemeDTO dataScheme = this.findDataById(id, null);
        dataScheme.setOptions(this.addOptionName(dataScheme.getOptions()));
        DataMappingVO dataMapping = dataScheme.getDataMapping();
        List assistMapping = dataMapping.getAssistMapping();
        List assistCodeList = assistMapping.stream().map(DimMappingVO::getCode).collect(Collectors.toList());
        List advancedMapping = dataMapping.getAdvancedMapping();
        List advancedCodeList = advancedMapping.stream().map(AdvancedMappingVO::getCode).collect(Collectors.toList());
        IPluginType pluginType = this.pluginTypeGather.getPluginType(dataScheme.getPluginType());
        if (Objects.isNull(dataMapping.getSubjectMapping()) && Objects.nonNull(fieldDTO = pluginType.subjectField(dataScheme))) {
            dataMapping.setSubjectMapping(SchemeInitUtil.createDimMapping((FieldDTO)fieldDTO, SchemeBaseDataRefType.ACCTSUBJECT.getCode(), SchemeBaseDataRefType.ACCTSUBJECT.getName()));
        }
        if (Objects.isNull(dataMapping.getCurrencyMapping()) && Objects.nonNull(fieldDTO = pluginType.currencyField(dataScheme))) {
            dataMapping.setCurrencyMapping(SchemeInitUtil.createDimMapping((FieldDTO)fieldDTO, SchemeBaseDataRefType.CURRENCY.getCode(), SchemeBaseDataRefType.CURRENCY.getName()));
        }
        if (Objects.isNull(dataMapping.getCfitemMapping()) && Objects.nonNull(fieldDTO = pluginType.cfItemField(dataScheme))) {
            dataMapping.setCfitemMapping(SchemeInitUtil.createDimMapping((FieldDTO)fieldDTO, SchemeBaseDataRefType.CFITEM.getCode(), SchemeBaseDataRefType.CFITEM.getName()));
        }
        for (DataSchemeOptionVO option : dataScheme.getOptions()) {
            if (!"DC_OPPOSITE_DIMENSIONS".equals(option.getCode())) continue;
            pluginType.getOptionList().stream().filter(o -> "DC_OPPOSITE_DIMENSIONS".equals(o.getCode())).findFirst().ifPresent(dataSchemeOption -> option.setSource(dataSchemeOption.source()));
            break;
        }
        HashSet effectTableSet = CollectionUtils.newHashSet();
        effectTableSet.add("DC_VOUCHERITEMASS");
        effectTableSet.add("DC_PREASSBALANCE");
        effectTableSet.add("DC_UNCLEAREDITEM");
        List<IFieldMappingProvider> fieldMappingProviderList = this.iFieldMappingProviderGather.listByPluginType(pluginType);
        for (IFieldMappingProvider iFieldMappingProvider : fieldMappingProviderList) {
            effectTableSet.add(iFieldMappingProvider.getEffectTable());
        }
        HashSet<String> codeSet = new HashSet<String>();
        for (String tableName : effectTableSet) {
            for (DimensionVO dimensionVO : this.assistDimService.findDimFieldsVOByTableName(tableName)) {
                if (codeSet.contains(dimensionVO.getCode())) continue;
                codeSet.add(dimensionVO.getCode());
                if ("dims".equals(dimensionVO.getDimensionType()) && !assistCodeList.contains(dimensionVO.getCode())) {
                    assistMapping.add(new AssistMappingVO(dimensionVO.getCode(), dimensionVO.getTitle()));
                    continue;
                }
                if (!"meas".equals(dimensionVO.getDimensionType()) || advancedCodeList.contains(dimensionVO.getCode())) continue;
                advancedMapping.add(new AdvancedMappingVO(dimensionVO.getCode(), dimensionVO.getTitle()));
            }
        }
        assistMapping = assistMapping.stream().filter(item -> codeSet.contains(item.getCode())).collect(Collectors.toList());
        advancedMapping = advancedMapping.stream().filter(item -> codeSet.contains(item.getCode())).collect(Collectors.toList());
        dataMapping.setAssistMapping(assistMapping);
        dataMapping.setAdvancedMapping(advancedMapping);
        return dataScheme;
    }

    @Override
    @ParamCheck
    public DataSchemeDTO findByCode(@NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") String code) {
        return this.findDataByCode(code, null);
    }

    @Override
    @ParamCheck
    public DataSchemeDTO getById(@NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") String id) {
        DataSchemeDTO data = this.findById(id);
        if (data == null) {
            throw new BusinessRuntimeException(String.format("\u6807\u8bc6\u3010%1$s\u3011\u7684\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u5b58\u5728", id));
        }
        return data;
    }

    @Override
    @ParamCheck
    public DataSchemeDTO getByCode(@NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") String code) {
        DataSchemeDTO data = this.findByCode(code);
        if (data == null) {
            throw new BusinessRuntimeException(String.format("\u4ee3\u7801\u3010%1$s\u3011\u7684\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u5b58\u5728", code));
        }
        return data;
    }

    @Override
    public String calNextCode() {
        List<DataSchemeDTO> listAll = this.listAll();
        if (CollectionUtils.isEmpty(listAll)) {
            return DEFAULT_CODE;
        }
        DataSchemeDTO maxScheme = listAll.get(listAll.size() - 1);
        Integer nextCode = -1;
        try {
            nextCode = Integer.valueOf(maxScheme.getCode()) + 1;
        }
        catch (NumberFormatException e) {
            return "";
        }
        if (nextCode.compareTo(100) >= 0) {
            Set dataSchemeSet = listAll.stream().map(DataSchemeDTO::getCode).collect(Collectors.toSet());
            for (int i = 1; i < 100; ++i) {
                String code = CommonUtil.lpad((String)String.valueOf(i), (String)PREFIX, (int)2);
                if (dataSchemeSet.contains(code)) continue;
                return code;
            }
            return "";
        }
        return CommonUtil.lpad((String)String.valueOf(nextCode), (String)PREFIX, (int)2);
    }

    @Override
    @ParamCheck
    @Transactional(rollbackFor={Exception.class})
    public Boolean create(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataSchemeDTO dto) {
        dto.setStopFlag(StopFlagEnum.START.getCode());
        this.handleCustomDimMapping(dto);
        DataSchemeDO schemeDO = (DataSchemeDO)BeanConvertUtil.convert((Object)dto, DataSchemeDO.class, (String[])new String[0]);
        schemeDO.setDataMapping(JsonUtils.writeValueAsString((Object)dto.getDataMapping()));
        schemeDO.setId(UUIDUtils.newUUIDStr());
        schemeDO.setVer(0L);
        this.dataSchemeDao.insert(schemeDO);
        dto.setId(schemeDO.getId());
        if (dto.getOptions() != null && !dto.getOptions().isEmpty()) {
            this.optionService.save(dto.getCode(), dto.getOptions());
            this.refTableSync(dto.getOptions());
        }
        this.cacheProvider.syncCache();
        ApplicationContextRegister.getApplicationContext().publishEvent((ApplicationEvent)new SaveMappingSchemeEvent((Object)this, dto));
        String title = StringUtils.join((Object[])new String[]{"\u65b0\u589e", dto.getCode(), dto.getPluginType()}, (String)"-");
        LogHelper.info((String)DcFunctionModuleEnum.DATAMAPPINGSCHEME.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)dto));
        return true;
    }

    @Override
    public void check(DataSchemeDTO dto) {
        Assert.isNotEmpty((String)dto.getCode(), (String)"\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dto.getName(), (String)"\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dto.getPluginType(), (String)"\u63d2\u4ef6\u7c7b\u578b\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)dto.getDataMapping(), (String)"\u6620\u5c04\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        if (dto.getCode().length() != 2) {
            throw new CheckRuntimeException("\u4ee3\u7801\u957f\u5ea6\u53ea\u80fd\u4e3a2\u4f4d");
        }
        try {
            Integer.valueOf(dto.getCode());
        }
        catch (NumberFormatException e) {
            throw new BusinessRuntimeException("\u4ee3\u7801\u53ea\u80fd\u4e3a\u6570\u5b57");
        }
        if (dto.getName().length() > 100) {
            throw new CheckRuntimeException("\u540d\u79f0\u957f\u5ea6\u4e0d\u80fd\u5927\u4e8e100");
        }
        IPluginType pluginType = this.pluginTypeGather.getPluginType(dto.getPluginType());
        Assert.isNotNull((Object)pluginType, (String)String.format("\u6807\u8bc6\u4e3a\u3010%1$s\u3011\u7684\u63d2\u4ef6\u7c7b\u578b\u4e0d\u5b58\u5728", dto.getPluginType()), (Object[])new Object[0]);
        DataSchemeDTO savedSchemeDO = this.findByCode(dto.getCode());
        Assert.isNull((Object)savedSchemeDO, (String)String.format("\u4ee3\u7801\u3010%1$s\u3011\u7684\u6570\u636e\u6620\u5c04\u65b9\u6848\u5df2\u5b58\u5728", dto.getCode()), (Object[])new Object[0]);
        this.advancedSqlCheck(dto);
    }

    @Override
    @ParamCheck
    public Boolean modify(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataSchemeDTO dto) {
        this.handleCustomDimMapping(dto);
        DataSchemeDO schemeDO = new DataSchemeDO();
        schemeDO.setId(dto.getId());
        schemeDO.setVer(dto.getVer());
        schemeDO.setName(dto.getName());
        schemeDO.setPluginType(dto.getPluginType());
        schemeDO.setDataSourceCode(dto.getDataSourceCode());
        schemeDO.setCustomConfig(dto.getCustomConfig());
        schemeDO.setSourceDataType(dto.getSourceDataType());
        schemeDO.setEtlJobId(dto.getEtlJobId());
        schemeDO.setDataMapping(JsonUtils.writeValueAsString((Object)dto.getDataMapping()));
        int i = this.dataSchemeDao.update(schemeDO);
        if (i != 1) {
            throw new CheckRuntimeException("\u6570\u636e\u5df2\u88ab\u4fee\u6539\u6216\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        if (dto.getOptions() != null && !dto.getOptions().isEmpty()) {
            this.optionService.save(dto.getCode(), dto.getOptions());
            this.refTableSync(dto.getOptions());
        }
        this.cacheProvider.syncCache();
        ApplicationContextRegister.getApplicationContext().publishEvent((ApplicationEvent)new SaveMappingSchemeEvent((Object)this, dto));
        String title = StringUtils.join((Object[])new String[]{"\u4fee\u6539", dto.getCode(), dto.getPluginType()}, (String)"-");
        LogHelper.info((String)DcFunctionModuleEnum.DATAMAPPINGSCHEME.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)dto));
        return true;
    }

    @Override
    public void modifyCheck(DataSchemeDTO dto) {
        Assert.isNotEmpty((String)dto.getId(), (String)"\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)dto.getVer(), (String)"\u7248\u672c\u53f7\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dto.getName(), (String)"\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dto.getPluginType(), (String)"\u63d2\u4ef6\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)dto.getDataMapping(), (String)"\u6620\u5c04\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        IPluginType pluginType = this.pluginTypeGather.getPluginType(dto.getPluginType());
        Assert.isNotNull((Object)pluginType, (String)String.format("\u6807\u8bc6\u4e3a\u3010%1$s\u3011\u7684\u63d2\u4ef6\u7c7b\u578b\u4e0d\u5b58\u5728", dto.getPluginType()), (Object[])new Object[0]);
        if (dto.getName().length() > 100) {
            throw new CheckRuntimeException("\u540d\u79f0\u957f\u5ea6\u4e0d\u80fd\u5927\u4e8e100");
        }
        this.advancedSqlCheck(dto);
    }

    private void handleCustomDimMapping(DataSchemeDTO dto) {
        Map<String, AssistMappingVO> assistMappingMap = dto.getDataMapping().getAssistMapping().stream().collect(Collectors.toMap(DimMappingVO::getCode, obj -> obj, (obj1, obj2) -> obj2));
        List advancedMapping = dto.getDataMapping().getAdvancedMapping();
        for (AdvancedMappingVO advancedMappingVO : advancedMapping) {
            if (!assistMappingMap.containsKey(advancedMappingVO.getCode())) continue;
            AssistMappingVO assistMappingVO = assistMappingMap.get(advancedMappingVO.getCode());
            advancedMappingVO.setRuleType(assistMappingVO.getRuleType());
            advancedMappingVO.setIsolationStrategy(assistMappingVO.getIsolationStrategy());
            advancedMappingVO.setAdvancedSql(assistMappingVO.getAdvancedSql());
        }
        List keyOrderList = dto.getDataMapping().getAssistMapping().stream().map(DimMappingVO::getCode).collect(Collectors.toList());
        advancedMapping.sort(Comparator.comparingInt(item -> {
            if (keyOrderList.contains(item.getCode())) {
                return keyOrderList.indexOf(item.getCode());
            }
            return keyOrderList.size();
        }));
    }

    private void refTableSync(List<DataSchemeOptionVO> list) {
        for (DataSchemeOptionVO option : list) {
            if (!"DC_OPPOSITE_DIMENSIONS".equals(option.getCode())) continue;
            String optionValue = option.getOptionValue();
            if (StringUtils.isEmpty((String)optionValue)) break;
            AsyncCallBackUtil.asyncCall(RefTableSyncUtil::syncSrcField);
            break;
        }
    }

    private void advancedSqlCheck(DataSchemeDTO dto) {
        DataMappingVO dataMapping = dto.getDataMapping();
        ArrayList checkList = CollectionUtils.newArrayList();
        checkList.add(new Pair((Object)dataMapping.getOrgMapping().getName(), (Object)dataMapping.getOrgMapping().getAdvancedSql()));
        if (Objects.nonNull(dataMapping.getSubjectMapping())) {
            checkList.add(new Pair((Object)dataMapping.getSubjectMapping().getName(), (Object)dataMapping.getSubjectMapping().getAdvancedSql()));
        }
        if (Objects.nonNull(dataMapping.getCurrencyMapping())) {
            checkList.add(new Pair((Object)dataMapping.getCurrencyMapping().getName(), (Object)dataMapping.getCurrencyMapping().getAdvancedSql()));
        }
        if (Objects.nonNull(dataMapping.getCfitemMapping())) {
            checkList.add(new Pair((Object)dataMapping.getCfitemMapping().getName(), (Object)dataMapping.getCfitemMapping().getAdvancedSql()));
        }
        checkList.addAll(dataMapping.getAssistMapping().stream().map(item -> new Pair((Object)item.getName(), (Object)item.getAdvancedSql())).collect(Collectors.toList()));
        checkList.addAll(dataMapping.getAdvancedMapping().stream().map(item -> new Pair((Object)item.getName(), (Object)item.getAdvancedSql())).collect(Collectors.toList()));
        for (Pair pair : checkList) {
            if (StringUtils.isEmpty((String)((String)pair.getSecond()))) continue;
            List<SelectOptionVO> list = this.baseDataRefDefineService.parseSqlByDataSource(dto.getDataSourceCode(), (String)pair.getSecond());
            Set columns = list.stream().map(SelectOptionVO::getCode).collect(Collectors.toSet());
            if (!columns.contains("ID")) {
                throw new CheckRuntimeException(String.format("\u3010%1$s\u3011\u6620\u5c04\u5b9a\u4e49\u9700\u8981\u5305\u542bID\u5217", pair.getFirst()));
            }
            if (!columns.contains("CODE")) {
                throw new CheckRuntimeException(String.format("\u3010%1$s\u3011\u6620\u5c04\u5b9a\u4e49\u9700\u8981\u5305\u542bCODE\u5217", pair.getFirst()));
            }
            if (columns.contains("NAME")) continue;
            throw new CheckRuntimeException(String.format("\u3010%1$s\u3011\u6620\u5c04\u5b9a\u4e49\u9700\u8981\u5305\u542bNAME\u5217", pair.getFirst()));
        }
    }

    @Override
    @ParamCheck
    public Boolean quoted(@NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") String code) {
        return BooleanValEnum.YES.getCode().compareTo(this.dataSchemeDao.existQuote(code)) == 0;
    }

    @Override
    @ParamCheck
    @Transactional(rollbackFor={Exception.class})
    public Boolean init(@NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") String code) {
        DataSchemeDTO scheme = this.getByCode(code);
        this.initializerGather.getByPluginType(scheme.getPluginType()).doInit(scheme);
        return true;
    }

    @Override
    @ParamCheck
    @Transactional(rollbackFor={Exception.class})
    public Boolean delete(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataSchemeDTO dto) {
        Assert.isNotEmpty((String)dto.getId(), (String)"\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)dto.getVer(), (String)"\u7248\u672c\u53f7\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        DataSchemeDTO savedSchemeDO = this.findDataById(dto.getId(), dto.getVer());
        Assert.isNotNull((Object)savedSchemeDO, (String)"\u6570\u636e\u5df2\u88ab\u4fee\u6539\u6216\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5", (Object[])new Object[0]);
        this.dataRefCheckService.orgRefCheck(savedSchemeDO.getCode());
        List<BaseDataMappingDefineDTO> baseDataRefDefineList = this.baseDataRefDefineService.listBySchemeCode(savedSchemeDO.getCode());
        for (BaseDataMappingDefineDTO defineDTO : baseDataRefDefineList) {
            if ("MD_CURRENCY".equals(defineDTO.getCode())) continue;
            ApplicationContextRegister.getApplicationContext().publishEvent(new BaseDataRefDefineDelEvent((Object)this, (DataMappingDefineDTO)defineDTO));
        }
        DataSchemeDO schemeDO = new DataSchemeDO();
        schemeDO.setId(dto.getId());
        schemeDO.setVer(dto.getVer());
        int i = this.dataSchemeDao.delete(schemeDO);
        if (i != 1) {
            throw new CheckRuntimeException("\u6570\u636e\u5df2\u88ab\u4fee\u6539\u6216\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        this.optionService.delete(dto.getCode());
        this.cacheProvider.syncCache();
        String title = StringUtils.join((Object[])new String[]{"\u5220\u9664", savedSchemeDO.getCode(), savedSchemeDO.getPluginType()}, (String)"-");
        LogHelper.info((String)DcFunctionModuleEnum.DATAMAPPINGSCHEME.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)savedSchemeDO));
        return true;
    }

    @Override
    @ParamCheck
    @Transactional(rollbackFor={Exception.class})
    public Boolean start(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataSchemeDTO dto) {
        Assert.isNotEmpty((String)dto.getId(), (String)"\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)dto.getVer(), (String)"\u7248\u672c\u53f7\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        DataSchemeDTO savedSchemeDO = this.findDataById(dto.getId(), dto.getVer());
        Assert.isNotNull((Object)savedSchemeDO, (String)"\u6570\u636e\u5df2\u88ab\u4fee\u6539\u6216\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5", (Object[])new Object[0]);
        if (savedSchemeDO.getStopFlag().compareTo(StopFlagEnum.STOP.getCode()) != 0) {
            return true;
        }
        DataSchemeDO schemeDO = new DataSchemeDO();
        schemeDO.setId(dto.getId());
        schemeDO.setVer(dto.getVer());
        schemeDO.setStopFlag(StopFlagEnum.START.getCode());
        int i = this.dataSchemeDao.stop(schemeDO);
        if (i != 1) {
            throw new CheckRuntimeException("\u6570\u636e\u5df2\u88ab\u4fee\u6539\u6216\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        this.cacheProvider.syncCache();
        return true;
    }

    @Override
    @ParamCheck
    @Transactional(rollbackFor={Exception.class})
    public Boolean stop(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataSchemeDTO dto) {
        Assert.isNotEmpty((String)dto.getId(), (String)"\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)dto.getVer(), (String)"\u7248\u672c\u53f7\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        DataSchemeDTO savedSchemeDO = this.findDataById(dto.getId(), dto.getVer());
        Assert.isNotNull((Object)savedSchemeDO, (String)"\u6570\u636e\u5df2\u88ab\u4fee\u6539\u6216\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5", (Object[])new Object[0]);
        if (savedSchemeDO.getStopFlag().compareTo(StopFlagEnum.START.getCode()) != 0) {
            return true;
        }
        DataSchemeDO schemeDO = new DataSchemeDO();
        schemeDO.setId(dto.getId());
        schemeDO.setVer(dto.getVer());
        schemeDO.setStopFlag(StopFlagEnum.STOP.getCode());
        int i = this.dataSchemeDao.stop(schemeDO);
        if (i != 1) {
            throw new CheckRuntimeException("\u6570\u636e\u5df2\u88ab\u4fee\u6539\u6216\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        this.cacheProvider.syncCache();
        return true;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String initTemporaryTable(String id) {
        StringBuilder errorLog = new StringBuilder();
        DataSchemeDTO dto = this.findById(id);
        IDataSchemeInitializer initializer = this.initializerGather.getByPluginType(dto.getPluginType());
        List<DataTableInfo> dataTableInfos = initializer.getTableInfoList(dto);
        if (CollectionUtils.isEmpty(dataTableInfos)) {
            return "\u521d\u59cb\u5316\u4e34\u65f6\u8868\u6210\u529f";
        }
        Connection connection = null;
        IDatabase database = null;
        try {
            connection = this.dataSourceService.getConnection(dto.getDataSourceCode());
            database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            if (database == null) {
                throw new BusinessRuntimeException("\u83b7\u53d6IDatabase\u5931\u8d25");
            }
            ISQLMetadata sqlMetadata = database.createMetadata(connection);
            for (DataTableInfo dataTableInfo : dataTableInfos) {
                if (sqlMetadata.getTableByName(dataTableInfo.getTableName()) != null || NotSuptTempDataBaseEnum.isNotSuptTempDataBase((String)database.getName()).booleanValue() && !DateTableEnum.PHYSICAL.equals((Object)dataTableInfo.getDateTableEnum())) continue;
                this.createTemporaryTable(dataTableInfo, errorLog, connection, database);
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u521d\u59cb\u5316\u4e34\u65f6\u8868\u5931\u8d25\uff0c\u8bf7\u5c1d\u8bd5\u624b\u52a8\u521b\u5efa", (Throwable)e);
        }
        finally {
            this.dataSourceService.closeConnection(dto.getDataSourceCode(), connection);
        }
        if (errorLog.length() == 0) {
            return "\u521d\u59cb\u5316\u4e34\u65f6\u8868\u6210\u529f";
        }
        throw new BusinessRuntimeException(errorLog.toString());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void createTemporaryTable(DataTableInfo dataTableInfo, StringBuilder errorLog, Connection connection, IDatabase database) {
        Statement pst = null;
        try {
            List sqlCommands = new SQLCommandParser().parse(database, dataTableInfo.getSql());
            String sql = this.getCreateSql((SQLCommand)sqlCommands.get(0), connection, database);
            switch (dataTableInfo.getDateTableEnum()) {
                case TRANSACTION_TEMPORARY: {
                    sql = sql.replaceFirst("(?i)create", "CREATE GLOBAL TEMPORARY ");
                    sql = sql + " ON COMMIT DELETE ROWS";
                    break;
                }
                case SESSION_TEMPORARY: {
                    sql = sql.replaceFirst("(?i)create", "CREATE GLOBAL TEMPORARY ");
                    sql = sql + " ON COMMIT PRESERVE ROWS";
                    break;
                }
                case PHYSICAL: {
                    break;
                }
            }
            pst = connection.prepareStatement(sql);
            pst.execute();
            if (!StringUtils.isEmpty((String)dataTableInfo.getPrimaryFields())) {
                String primarySql = String.format("ALTER TABLE %1$s ADD CONSTRAINT PK_%1$s PRIMARY KEY (%2$s)", dataTableInfo.getTableName(), dataTableInfo.getPrimaryFields());
                List primarySqlList = new SQLCommandParser().parse(database, primarySql);
                ((SQLCommand)primarySqlList.get(0)).execute(connection);
            }
            if (CollectionUtils.isEmpty(dataTableInfo.getIndexSqlList())) {
                return;
            }
            for (String indexSql : dataTableInfo.getIndexSqlList()) {
                List sqlCommandList = new SQLCommandParser().parse(database, indexSql);
                ((SQLCommand)sqlCommandList.get(0)).execute(connection);
            }
        }
        catch (Exception e) {
            this.logger.error("\u521b\u5efa\u3010{}\u3011\u8868\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f\uff1a{}", (Object)dataTableInfo.getTableName(), (Object)e);
            errorLog.append(String.format("\u521b\u5efa%1$s\u8868\u5931\u8d25\n", dataTableInfo.getTableName()));
        }
        finally {
            try {
                if (!Objects.isNull(pst)) {
                    pst.close();
                }
            }
            catch (SQLException e) {
                pst = null;
            }
        }
    }

    private String getCreateSql(SQLCommand sqlCommand, Connection connection, IDatabase database) throws SQLInterpretException {
        StatementCommand statementCommand = (StatementCommand)sqlCommand;
        ISQLInterpretor sqlInterpretor = database.createSQLInterpretor(connection);
        List createSqls = sqlInterpretor.createTable((CreateTableStatement)statementCommand.getStatement());
        return (String)createSqls.get(0);
    }

    private DataSchemeDTO findDataByCode(String code, Long ver) {
        if (ver == null) {
            return (DataSchemeDTO)this.cacheProvider.get(code);
        }
        return this.cacheProvider.list().stream().filter(item -> code.equals(item.getCode()) && ver.compareTo(item.getVer()) == 0).findFirst().orElse(null);
    }

    private DataSchemeDTO findDataById(String id, Long ver) {
        return this.cacheProvider.list().stream().filter(item -> id.equals(item.getId()) && (ver == null || ver != null && ver.compareTo(item.getVer()) == 0)).findFirst().orElse(null);
    }

    private TreeDTO convert2TreeNode(DataSchemeDTO dataScheme) {
        TreeDTO node = new TreeDTO();
        node.setId(dataScheme.getId());
        node.setCode(dataScheme.getCode());
        node.setTitle(DataRefUtil.getNodeLabel((String)dataScheme.getCode(), (String)dataScheme.getName()));
        node.setParentCode("-");
        node.setLeaf(Boolean.valueOf(true));
        HashMap<String, Object> attributes = new HashMap<String, Object>(3);
        attributes.put("ID", dataScheme.getId());
        attributes.put("PLUGINTYPE", dataScheme.getPluginType());
        attributes.put("STOPFLAG", dataScheme.getStopFlag());
        node.setAttributes(attributes);
        return node;
    }

    @Override
    public DataSchemeDTO initPluginInfo(DataSchemeDTO dto) {
        FieldDTO cfitemDto;
        FieldDTO currencyDto;
        Assert.isNotNull((Object)dto, (String)"\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dto.getDataSourceCode(), (String)"\u6570\u636e\u6e90\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dto.getPluginType(), (String)"\u6838\u7b97\u8f6f\u4ef6\u7248\u672c\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        IPluginType pluginType = this.pluginTypeGather.getPluginType(dto.getPluginType());
        if (pluginType == null) {
            throw new BusinessRuntimeException(String.format("\u63d2\u4ef6\u7c7b\u578b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", dto.getPluginType()));
        }
        DataSchemeDTO dataMappingScheme = new DataSchemeDTO();
        List<DataSchemeOptionVO> optionList = this.optionService.getListByPluginType(dto.getPluginType());
        dataMappingScheme.setOptions(optionList);
        DataMappingVO dataMapping = new DataMappingVO();
        IOrgMappingTypeProvider orgMappingTypeProvider = this.orgMappingTypeGather.getProvider(pluginType);
        if (orgMappingTypeProvider == null) {
            throw new BusinessRuntimeException(String.format("\u63d2\u4ef6\u7c7b\u578b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u7ec4\u7ec7\u673a\u6784\u5bf9\u5e94\u5173\u7cfb\u6570\u636e\u9879", dto.getPluginType()));
        }
        OrgMappingTypeDTO orgMappingType = orgMappingTypeProvider.listMappingType(dto).get(0);
        BaseDataMappingDefineDTO baseDataMappingDefine = orgMappingType.getBaseDataMappingDefine();
        DimMappingVO orgDimMapping = SchemeInitUtil.createDimMapping(SchemeBaseDataRefType.ORG.getCode(), "\u7ec4\u7ec7\u673a\u6784", baseDataMappingDefine.getRuleType(), baseDataMappingDefine.getAutoMatchDim(), orgMappingType.getCode(), null, baseDataMappingDefine.getAdvancedSql(), true);
        OrgMappingVO orgMapping = new OrgMappingVO();
        BeanUtils.copyProperties(orgDimMapping, orgMapping);
        orgMapping.setOrgMappingType(orgMappingType.getCode());
        dataMapping.setOrgMapping(orgMapping);
        FieldDTO subjectDto = pluginType.subjectField(dto);
        if (Objects.nonNull(subjectDto)) {
            DimMappingVO subjectMapping = SchemeInitUtil.createDimMapping(subjectDto, SchemeBaseDataRefType.ACCTSUBJECT.getCode(), "\u79d1\u76ee");
            dataMapping.setSubjectMapping(subjectMapping);
        }
        if (Objects.nonNull(currencyDto = pluginType.currencyField(dto))) {
            DimMappingVO currencyMapping = SchemeInitUtil.createDimMapping(currencyDto, SchemeBaseDataRefType.CURRENCY.getCode(), "\u5e01\u522b");
            dataMapping.setCurrencyMapping(currencyMapping);
        }
        if (Objects.nonNull(cfitemDto = pluginType.cfItemField(dto))) {
            DimMappingVO cfitemMapping = SchemeInitUtil.createDimMapping(cfitemDto, SchemeBaseDataRefType.CFITEM.getCode(), "\u73b0\u6d41\u9879\u76ee");
            dataMapping.setCfitemMapping(cfitemMapping);
        }
        ArrayList assistMappingList = CollectionUtils.newArrayList();
        ArrayList advancedMappingList = CollectionUtils.newArrayList();
        HashSet effectTableSet = CollectionUtils.newHashSet();
        effectTableSet.add("DC_VOUCHERITEMASS");
        effectTableSet.add("DC_PREASSBALANCE");
        effectTableSet.add("DC_UNCLEAREDITEM");
        List<IFieldMappingProvider> fieldMappingProviderList = this.iFieldMappingProviderGather.listByPluginType(pluginType);
        for (IFieldMappingProvider iFieldMappingProvider : fieldMappingProviderList) {
            effectTableSet.add(iFieldMappingProvider.getEffectTable());
        }
        HashSet<String> codeSet = new HashSet<String>();
        for (String tableName : effectTableSet) {
            for (DimensionVO dimensionVO : this.assistDimService.findDimFieldsVOByTableName(tableName)) {
                if (codeSet.contains(dimensionVO.getCode())) continue;
                codeSet.add(dimensionVO.getCode());
                if ("dims".equals(dimensionVO.getDimensionType())) {
                    assistMappingList.add(new AssistMappingVO(dimensionVO.getCode(), dimensionVO.getTitle()));
                    continue;
                }
                if (!"meas".equals(dimensionVO.getDimensionType())) continue;
                advancedMappingList.add(new AdvancedMappingVO(dimensionVO.getCode(), dimensionVO.getTitle()));
            }
        }
        dataMapping.setAssistMapping((List)assistMappingList);
        dataMapping.setAdvancedMapping((List)advancedMappingList);
        dataMappingScheme.setDataMapping(dataMapping);
        return dataMappingScheme;
    }

    private List<DataSchemeOptionVO> addOptionName(List<DataSchemeOptionVO> options) {
        for (DataSchemeOptionVO option : options) {
            StringBuilder optionName = new StringBuilder();
            String optionValueStr = option.getOptionValue();
            if (StringUtils.isEmpty((String)optionValueStr)) {
                option.setOptionName("");
                continue;
            }
            String paramType = option.getParamType();
            if (OptionType.BASEDATA.getType().equals(paramType)) {
                String[] optionValueList;
                BaseDataDTO param = new BaseDataDTO();
                param.setTableName(option.getBaseDataTable());
                Map<String, String> baseDataMap = this.baseDataService.list(param).getRows().stream().collect(Collectors.toMap(BaseDataDO::getCode, BaseDataDO::getName, (o1, o2) -> o2));
                for (String optionValue : optionValueList = optionValueStr.split(",")) {
                    if (!baseDataMap.containsKey(optionValue)) continue;
                    optionName.append(baseDataMap.get(optionValue)).append("\uff0c");
                }
            } else if (OptionType.SELECT.getType().equals(paramType)) {
                String[] optionList;
                List<String> optionValueList = Arrays.asList(optionValueStr.split(","));
                String sourceStr = option.getSource();
                for (String optionStr : optionList = sourceStr.split(",")) {
                    if (!optionValueList.contains(optionStr.split(":")[1])) continue;
                    optionName.append(optionStr.split(":")[0]).append("\uff0c");
                }
            } else if (OptionType.BOOLEAN.getType().equals(paramType)) {
                if ("true".equals(optionValueStr)) {
                    optionName.append("\u662f");
                } else if ("false".equals(optionValueStr)) {
                    optionName.append("\u5426");
                }
            } else {
                optionName.append(optionValueStr);
            }
            String optionNameStr = optionName.toString();
            if (optionName.toString().endsWith("\uff0c")) {
                optionNameStr = optionNameStr.substring(0, optionNameStr.length() - 1);
            }
            option.setOptionName(optionNameStr);
        }
        return options;
    }
}

