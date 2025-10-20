/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum
 *  com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum
 *  com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum
 *  com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel
 *  com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO
 *  com.jiuqi.bde.bizmodel.client.vo.CustomCondition
 *  com.jiuqi.bde.bizmodel.client.vo.SelectField
 *  com.jiuqi.bde.bizmodel.define.gather.IBizModelGather
 *  com.jiuqi.bde.common.constant.ApplyScopeEnum
 *  com.jiuqi.bde.common.constant.BdeFunctionModuleEnum
 *  com.jiuqi.bde.common.constant.FetchFixedFieldEnum
 *  com.jiuqi.bde.common.dto.ColumnDefineVO
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.utils.CollectionUtil
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.query.common.BusinessResponseEntity
 *  com.jiuqi.va.query.datasource.vo.DataSourceInfoVO
 *  com.jiuqi.va.query.datasource.web.QueryDataSourceClient
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 *  com.jiuqi.va.query.tree.service.MenuTreeService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.bizmodel.impl.model.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum;
import com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum;
import com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum;
import com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel;
import com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO;
import com.jiuqi.bde.bizmodel.client.vo.CustomCondition;
import com.jiuqi.bde.bizmodel.client.vo.SelectField;
import com.jiuqi.bde.bizmodel.define.gather.IBizModelGather;
import com.jiuqi.bde.bizmodel.impl.model.dao.BizModelDao;
import com.jiuqi.bde.bizmodel.impl.model.dao.CustomBizModelDao;
import com.jiuqi.bde.bizmodel.impl.model.entity.CustomBizModelEO;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelService;
import com.jiuqi.bde.bizmodel.impl.model.service.impl.BizModelManageServiceImpl;
import com.jiuqi.bde.common.constant.ApplyScopeEnum;
import com.jiuqi.bde.common.constant.BdeFunctionModuleEnum;
import com.jiuqi.bde.common.constant.FetchFixedFieldEnum;
import com.jiuqi.bde.common.dto.ColumnDefineVO;
import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.utils.CollectionUtil;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.datasource.vo.DataSourceInfoVO;
import com.jiuqi.va.query.datasource.web.QueryDataSourceClient;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.tree.service.MenuTreeService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomBizModelManageServiceImpl
extends BizModelManageServiceImpl {
    @Autowired
    private CustomBizModelDao customBizModelDao;
    @Autowired
    private BizModelService bizModelService;
    private final NedisCache bdeBizModelCache;
    @Autowired
    private BizModelDao bizModelDao;
    @Autowired
    protected IBizModelGather bizModelGather;
    @Autowired
    private QueryDataSourceClient dataSourceClient;
    @Autowired
    private MenuTreeService menuTreeService;

    public CustomBizModelManageServiceImpl(@Autowired NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("BDE_BIZMODEL_MANAGE");
        this.bdeBizModelCache = cacheManager.getCache("BDE_BIZMODEL");
    }

    @Override
    public String getCategoryCode() {
        return BizModelCategoryEnum.BIZMODEL_CUSTOM.getCode();
    }

    @Override
    public String list() {
        List<CustomBizModelDTO> customBizModelDTOList = this.listModel();
        return JsonUtils.writeValueAsString(customBizModelDTOList);
    }

    public List<CustomBizModelDTO> listModel() {
        return (List)this.bdeBizModelCache.get("BDE_CUSTOM_BIZMODEL_CACHE_ID", () -> {
            List<CustomBizModelEO> customBizModelDatas = this.customBizModelDao.loadAll();
            if (CollectionUtils.isEmpty(customBizModelDatas)) {
                return Collections.emptyList();
            }
            return customBizModelDatas.stream().map(this::convertCustomBizModelDTO).collect(Collectors.toList());
        });
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(String bizModelDtoStr) {
        CustomBizModelDTO customBizModelDTO = (CustomBizModelDTO)JsonUtils.readValue((String)bizModelDtoStr, (TypeReference)new TypeReference<CustomBizModelDTO>(){});
        this.checkCustomBizModelDTO(customBizModelDTO, true);
        CustomBizModelEO customBizModelData = new CustomBizModelEO();
        BeanUtils.copyProperties(customBizModelDTO, customBizModelData);
        customBizModelData.setId(UUIDUtils.newHalfGUIDStr());
        customBizModelData.setCustomCondition(JsonUtils.writeValueAsString((Object)customBizModelDTO.getCustomConditions()));
        customBizModelData.setFetchFields(JsonUtils.writeValueAsString((Object)customBizModelDTO.getSelectFieldList()));
        this.customBizModelDao.save(customBizModelData);
        this.bizmodelCacheClear();
        LogHelper.info((String)BdeFunctionModuleEnum.BIZMODEL.getFullModuleName(), (String)String.format("\u65b0\u589e-%1$s", customBizModelDTO.getName()), (String)JsonUtils.writeValueAsString((Object)customBizModelDTO));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void update(String bizModelDtoStr) {
        CustomBizModelDTO customBizModelDTO = (CustomBizModelDTO)JsonUtils.readValue((String)bizModelDtoStr, (TypeReference)new TypeReference<CustomBizModelDTO>(){});
        this.checkCustomBizModelDTO(customBizModelDTO, false);
        CustomBizModelEO customBizModelData = new CustomBizModelEO();
        BeanUtils.copyProperties(customBizModelDTO, customBizModelData);
        customBizModelData.setCustomCondition(JsonUtils.writeValueAsString((Object)customBizModelDTO.getCustomConditions()));
        customBizModelData.setFetchFields(JsonUtils.writeValueAsString((Object)customBizModelDTO.getSelectFieldList()));
        this.customBizModelDao.update(customBizModelData);
        this.bizmodelCacheClear();
        LogHelper.info((String)BdeFunctionModuleEnum.BIZMODEL.getFullModuleName(), (String)String.format("\u4fee\u6539-%1$s", customBizModelDTO.getName()), (String)JsonUtils.writeValueAsString((Object)customBizModelDTO));
    }

    public CustomBizModelDTO convertCustomBizModelDTO(CustomBizModelEO customBizModelData) {
        CustomBizModelDTO customBizModelDTO = new CustomBizModelDTO();
        customBizModelDTO.setFetchTable(customBizModelData.getFetchTable());
        customBizModelDTO.setFixedCondition(customBizModelData.getFixedCondition());
        customBizModelDTO.setApplyScope(customBizModelData.getApplyScope());
        customBizModelDTO.setDataSourceCode(customBizModelData.getDataSourceCode());
        customBizModelDTO.setQueryTemplateId(customBizModelData.getQueryTemplateId());
        customBizModelDTO.setComputationModelCode(customBizModelData.getComputationModelCode());
        customBizModelDTO.setId(customBizModelData.getId());
        customBizModelDTO.setCode(customBizModelData.getCode());
        customBizModelDTO.setName(customBizModelData.getName());
        customBizModelDTO.setStopFlag(customBizModelData.getStopFlag());
        customBizModelDTO.setOrdinal(customBizModelData.getOrdinal());
        List customConditions = (List)JsonUtils.readValue((String)customBizModelData.getCustomCondition(), (TypeReference)new TypeReference<List<CustomCondition>>(){});
        customBizModelDTO.setCustomConditions(customConditions);
        List selectFieldList = (List)JsonUtils.readValue((String)customBizModelData.getFetchFields(), (TypeReference)new TypeReference<List<SelectField>>(){});
        customBizModelDTO.setSelectFieldList(selectFieldList);
        ArrayList fetchFieldShow = CollectionUtils.newArrayList();
        for (int i = 0; i < selectFieldList.size(); ++i) {
            String aggregateFuncCode = ((SelectField)selectFieldList.get(i)).getAggregateFuncCode();
            String aggregateFuncName = AggregateFuncEnum.getEnumByCode((String)aggregateFuncCode).getFuncName();
            StringBuilder selectField = new StringBuilder();
            selectField.append(aggregateFuncName).append(" | ");
            selectField.append(((SelectField)selectFieldList.get(i)).getFieldCode()).append(" ");
            selectField.append(((SelectField)selectFieldList.get(i)).getFieldName());
            fetchFieldShow.add(selectField.toString());
        }
        customBizModelDTO.setFetchFieldShow((List)fetchFieldShow);
        ArrayList customConditionShow = CollectionUtils.newArrayList();
        for (int i = 0; i < customConditions.size(); ++i) {
            String ruleCode = ((CustomCondition)customConditions.get(i)).getRuleCode();
            String ruleName = MatchingRuleEnum.getEnumByCode((String)ruleCode).getRuleName();
            StringBuilder customCondition = new StringBuilder();
            customCondition.append(ruleName).append(" | ");
            customCondition.append(" ").append(((CustomCondition)customConditions.get(i)).getParamsCode()).append(" ");
            customCondition.append(((CustomCondition)customConditions.get(i)).getParamsName());
            customConditionShow.add(customCondition.toString());
        }
        customBizModelDTO.setCustomConditionShow((List)customConditionShow);
        customBizModelDTO.setFetchFieldNames(CollectionUtil.join(selectFieldList.stream().map(SelectField::getFieldName).collect(Collectors.toList()), (String)","));
        IBizComputationModel computationModel = this.bizModelGather.getComputationModelByCode(customBizModelDTO.getComputationModelCode());
        customBizModelDTO.setComputationModelName(computationModel.getName());
        customBizModelDTO.setApplyScopeShow(ApplyScopeEnum.getApplyScopeEnumByCode((String)customBizModelDTO.getApplyScope()).getTitle());
        if (!StringUtils.isEmpty((String)customBizModelData.getQueryTemplateId())) {
            Map<String, TemplateInfoVO> queryTemplateMap = this.menuTreeService.listInit().stream().collect(Collectors.toMap(TemplateInfoVO::getId, item -> item));
            if (queryTemplateMap.get(customBizModelData.getQueryTemplateId()) != null) {
                customBizModelDTO.setQueryTemplateShow(queryTemplateMap.get(customBizModelData.getQueryTemplateId()).getTitle());
            } else {
                customBizModelDTO.setQueryTemplateShow(customBizModelData.getQueryTemplateId() + "\u3010\u5df2\u5220\u9664\u3011");
            }
        }
        if (!StringUtils.isEmpty((String)customBizModelData.getDataSourceCode())) {
            if (DataSourceService.CURRENT.equals(customBizModelData.getDataSourceCode())) {
                customBizModelDTO.setDataSourceCodeShow("\u5f53\u524d\u6570\u636e\u6e90");
                return customBizModelDTO;
            }
            BusinessResponseEntity dataSourceListEntity = this.dataSourceClient.getAllDataSource();
            if (!dataSourceListEntity.isSuccess()) {
                throw new BusinessRuntimeException("\u6570\u636e\u6e90\u67e5\u8be2\u51fa\u73b0\u9519\u8bef" + dataSourceListEntity.getErrorMessage());
            }
            Map<String, DataSourceInfoVO> dsMap = ((List)dataSourceListEntity.getData()).stream().collect(Collectors.toMap(DataSourceInfoVO::getCode, item -> item));
            if (dsMap.get(customBizModelData.getDataSourceCode()) != null) {
                customBizModelDTO.setDataSourceCodeShow(dsMap.get(customBizModelData.getDataSourceCode()).getName());
            } else {
                customBizModelDTO.setDataSourceCodeShow(customBizModelData.getDataSourceCode() + "\u3010\u5df2\u5220\u9664\u3011");
            }
        }
        return customBizModelDTO;
    }

    private void checkCustomBizModelDTO(CustomBizModelDTO customBizModelDTO, boolean isSaveFlag) {
        Set queryTemplateIdSet;
        if (null == customBizModelDTO) {
            throw new BusinessRuntimeException("\u81ea\u5b9a\u4e49\u6a21\u578b\u914d\u7f6e\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)customBizModelDTO.getCode()) || StringUtils.isEmpty((String)customBizModelDTO.getName())) {
            throw new BdeRuntimeException("\u81ea\u5b9a\u4e49\u6a21\u578b\u540d\u79f0\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        IBizComputationModel computationModel = this.bizModelGather.getComputationModelByCode(customBizModelDTO.getComputationModelCode());
        customBizModelDTO.setComputationModelName(computationModel.getName());
        if (!CollectionUtils.isEmpty((Collection)computationModel.getFetchTypes()) && CollectionUtils.isEmpty((Collection)customBizModelDTO.getFetchFields())) {
            throw new BusinessRuntimeException("\u53d6\u6570\u5b57\u6bb5\u4e0d\u5141\u8bb8\u4e3a\u7a7a.");
        }
        List<BizModelDTO> list = this.bizModelService.listAll();
        Map<String, String> allCodeAndNames = list.stream().collect(Collectors.toMap(BizModelDTO::getName, BizModelDTO::getCode));
        if (isSaveFlag && !allCodeAndNames.isEmpty() && allCodeAndNames.values().contains(customBizModelDTO.getCode())) {
            customBizModelDTO.setCode((customBizModelDTO.getCode().length() > 10 ? customBizModelDTO.getCode().substring(0, 10) : customBizModelDTO.getCode()) + UUIDUtils.newHalfGUIDStr().toUpperCase());
        }
        if (!(allCodeAndNames.isEmpty() || StringUtils.isEmpty((String)allCodeAndNames.get(customBizModelDTO.getName())) || allCodeAndNames.get(customBizModelDTO.getName()).equals(customBizModelDTO.getCode()))) {
            throw new BusinessRuntimeException("\u4e1a\u52a1\u6a21\u578b\u540d\u79f0\u4e0d\u5141\u8bb8\u91cd\u590d,\u540d\u79f0:" + customBizModelDTO.getName());
        }
        if (!StringUtils.isEmpty((String)customBizModelDTO.getQueryTemplateId()) && !(queryTemplateIdSet = this.menuTreeService.listInit().stream().map(TemplateInfoVO::getId).collect(Collectors.toSet())).contains(customBizModelDTO.getQueryTemplateId())) {
            throw new BusinessRuntimeException(String.format("\u4e1a\u52a1\u6a21\u578b\u6307\u5b9a\u7684\u7a7f\u900f\u754c\u9762\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u67e5\u8be2\u5b9a\u4e49\uff0c\u8bf7\u5728\u67e5\u8be2\u5b9a\u4e49\u4e2d\u68c0\u67e5\u662f\u5426\u88ab\u4fee\u6539\u6216\u5220\u9664", customBizModelDTO.getQueryTemplateId()));
        }
        if (!StringUtils.isEmpty((String)customBizModelDTO.getDataSourceCode())) {
            if (DataSourceService.CURRENT.equals(customBizModelDTO.getDataSourceCode())) {
                return;
            }
            BusinessResponseEntity dataSourceListEntity = this.dataSourceClient.getAllDataSource();
            if (!dataSourceListEntity.isSuccess()) {
                throw new BusinessRuntimeException("\u6570\u636e\u6e90\u67e5\u8be2\u51fa\u73b0\u9519\u8bef" + dataSourceListEntity.getErrorMessage());
            }
            Set dsCodeSet = ((List)dataSourceListEntity.getData()).stream().map(DataSourceInfoVO::getCode).collect(Collectors.toSet());
            if (!dsCodeSet.contains(customBizModelDTO.getDataSourceCode())) {
                throw new BusinessRuntimeException(String.format("\u4e1a\u52a1\u6a21\u578b\u6307\u5b9a\u7684\u6570\u636e\u6e90\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u6e90\uff0c\u8bf7\u5728\u6570\u636e\u6e90\u7ba1\u7406\u4e2d\u68c0\u67e5\u662f\u5426\u88ab\u4fee\u6539\u6216\u5220\u9664", customBizModelDTO.getDataSourceCode()));
            }
        }
    }

    public CustomBizModelDTO getById(String id) {
        Assert.isNotEmpty((String)id);
        return this.listModel().stream().filter(item -> id.equals(item.getId())).findFirst().orElseThrow(() -> new BusinessRuntimeException(String.format("\u6839\u636e\u4e1a\u52a1\u6a21\u578b\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879\uff0c\u8bf7\u68c0\u67e5\u4e1a\u52a1\u6a21\u578b\u662f\u5426\u88ab\u505c\u7528\u6216\u5220\u9664", id)));
    }

    public CustomBizModelDTO getByCode(String code) {
        Assert.isNotEmpty((String)code);
        return this.listModel().stream().filter(item -> code.equals(item.getCode())).findFirst().orElseThrow(() -> new BusinessRuntimeException(String.format("\u6839\u636e\u4e1a\u52a1\u6a21\u578b\u4ee3\u7801\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879\uff0c\u8bf7\u68c0\u67e5\u4e1a\u52a1\u6a21\u578b\u662f\u5426\u88ab\u505c\u7528\u6216\u5220\u9664", code)));
    }

    @Override
    public BizModelColumnDefineVO getColumnDefines(String bizModelCode) {
        Assert.isNotEmpty((String)bizModelCode);
        CustomBizModelDTO bizModelDTO = (CustomBizModelDTO)this.bizModelService.get(bizModelCode);
        BizModelColumnDefineVO modelColumnDefine = new BizModelColumnDefineVO();
        IBizComputationModel bizModel = this.bizModelGather.getComputationModelByCode(bizModelDTO.getComputationModelCode());
        ArrayList<ColumnDefineVO> columnDefines = new ArrayList<ColumnDefineVO>();
        if (!CollectionUtils.isEmpty((Collection)bizModel.getFixedFields())) {
            for (ColumnDefineVO columnDefine : bizModel.getFixedFields()) {
                if (FetchFixedFieldEnum.FETCHTYPE.getCode().equalsIgnoreCase(columnDefine.getCode())) {
                    ArrayList fetchTypeSelectOptions = new ArrayList();
                    bizModelDTO.getSelectFieldList().forEach(selectField -> fetchTypeSelectOptions.add(new SelectOptionVO(selectField.getFieldCode(), selectField.getFieldName())));
                    columnDefine.setData(fetchTypeSelectOptions);
                }
                columnDefines.add(columnDefine);
            }
        }
        if (!CollectionUtils.isEmpty((Collection)bizModelDTO.getCustomConditions())) {
            for (CustomCondition customCondition : bizModelDTO.getCustomConditions()) {
                MatchingRuleEnum matchingRuleEnum = MatchingRuleEnum.getEnumByCode((String)customCondition.getRuleCode());
                ColumnDefineVO columnDefineVO = new ColumnDefineVO(customCondition.getParamsCode(), customCondition.getParamsName(), customCondition.getRequired().booleanValue(), matchingRuleEnum.getDataRanges(), "INPUT", null, null);
                columnDefines.add(columnDefineVO);
            }
        }
        modelColumnDefine.setColumnDefines(columnDefines);
        modelColumnDefine.setOptionItems(bizModel.getOptionItems());
        modelColumnDefine.setFetchSourceCode(bizModelCode);
        modelColumnDefine.setComputationModelIcon(bizModel.getIcon());
        return modelColumnDefine;
    }

    @Override
    public void bizmodelCacheClear() {
        this.bdeBizModelCache.clear();
    }

    @Override
    public String getTableName() {
        return "BDE_BIZMODEL_CUSTOM";
    }

    @Override
    public BizModelDao getBizModelDao() {
        return this.bizModelDao;
    }

    @Override
    public IBizModelGather getBizModelGather() {
        return this.bizModelGather;
    }
}

