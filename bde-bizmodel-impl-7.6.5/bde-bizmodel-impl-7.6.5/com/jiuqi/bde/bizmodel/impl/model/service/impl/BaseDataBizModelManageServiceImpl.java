/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.client.dto.BaseDataBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum
 *  com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel
 *  com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO
 *  com.jiuqi.bde.bizmodel.define.gather.IBizModelGather
 *  com.jiuqi.bde.common.constant.BdeFunctionModuleEnum
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
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
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.basedata.service.BaseDataDefineService
 *  com.jiuqi.va.datamodel.service.VaDataModelPublishedService
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.bizmodel.impl.model.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.client.dto.BaseDataBizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum;
import com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel;
import com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO;
import com.jiuqi.bde.bizmodel.define.gather.IBizModelGather;
import com.jiuqi.bde.bizmodel.impl.model.dao.BaseDataBizModelDao;
import com.jiuqi.bde.bizmodel.impl.model.dao.BizModelDao;
import com.jiuqi.bde.bizmodel.impl.model.entity.BaseDataBizModelEO;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelService;
import com.jiuqi.bde.bizmodel.impl.model.service.impl.BizModelManageServiceImpl;
import com.jiuqi.bde.common.constant.BdeFunctionModuleEnum;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
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
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.basedata.service.BaseDataDefineService;
import com.jiuqi.va.datamodel.service.VaDataModelPublishedService;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaseDataBizModelManageServiceImpl
extends BizModelManageServiceImpl {
    private static Logger logger = LoggerFactory.getLogger(BaseDataBizModelManageServiceImpl.class);
    @Autowired
    private BaseDataBizModelDao baseDataBizModelDao;
    @Autowired
    private BaseDataDefineService baseDataDefineService;
    @Autowired
    private VaDataModelPublishedService dataModelService;
    @Autowired
    private BizModelService bizModelService;
    @Autowired
    private BizModelDao bizModelDao;
    @Autowired
    private IBizModelGather bizModelGather;
    private final NedisCache bdeBizModelCache;

    public BaseDataBizModelManageServiceImpl(@Autowired NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("BDE_BIZMODEL_MANAGE");
        this.bdeBizModelCache = cacheManager.getCache("BDE_BIZMODEL");
    }

    @Override
    public String getCategoryCode() {
        return BizModelCategoryEnum.BIZMODEL_BASEDATA.getCode();
    }

    @Override
    public String list() {
        List<BaseDataBizModelDTO> baseDataBizModelDTOList = this.listModel();
        return JsonUtils.writeValueAsString(baseDataBizModelDTOList);
    }

    public List<BaseDataBizModelDTO> listModel() {
        return (List)this.bdeBizModelCache.get("BDE_BASEDATA_BIZMODEL_CACHE_ID", () -> {
            List<BaseDataBizModelEO> baseDataBizModelDatas = this.baseDataBizModelDao.loadAll();
            if (CollectionUtils.isEmpty(baseDataBizModelDatas)) {
                return Collections.emptyList();
            }
            return baseDataBizModelDatas.stream().map(baseDataBizModelDTO -> this.convertBaseDataBizModelDTO((BaseDataBizModelEO)baseDataBizModelDTO)).collect(Collectors.toList());
        });
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(String bizModelDtoStr) {
        BaseDataBizModelDTO baseDataBizModelDTO = (BaseDataBizModelDTO)JsonUtils.readValue((String)bizModelDtoStr, (TypeReference)new TypeReference<BaseDataBizModelDTO>(){});
        this.checkBaseDataBizModelDTO(baseDataBizModelDTO, true);
        BaseDataBizModelEO baseDataBizModelData = new BaseDataBizModelEO();
        BeanUtils.copyProperties(baseDataBizModelDTO, baseDataBizModelData);
        baseDataBizModelData.setId(UUIDUtils.newHalfGUIDStr());
        baseDataBizModelData.setFetchFields(CollectionUtils.toString((List)baseDataBizModelDTO.getFetchFields()).toUpperCase());
        this.baseDataBizModelDao.save(baseDataBizModelData);
        this.bizmodelCacheClear();
        LogHelper.info((String)BdeFunctionModuleEnum.BIZMODEL.getFullModuleName(), (String)String.format("\u65b0\u589e-%1$s", baseDataBizModelDTO.getName()), (String)JsonUtils.writeValueAsString((Object)baseDataBizModelDTO));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void update(String bizModelDtoStr) {
        BaseDataBizModelDTO baseDataBizModelDTO = (BaseDataBizModelDTO)JsonUtils.readValue((String)bizModelDtoStr, (TypeReference)new TypeReference<BaseDataBizModelDTO>(){});
        this.checkBaseDataBizModelDTO(baseDataBizModelDTO, false);
        BaseDataBizModelEO baseDataBizModelData = new BaseDataBizModelEO();
        BeanUtils.copyProperties(baseDataBizModelDTO, baseDataBizModelData);
        baseDataBizModelData.setFetchFields(CollectionUtils.toString((List)baseDataBizModelDTO.getFetchFields()).toUpperCase());
        this.baseDataBizModelDao.update(baseDataBizModelData);
        this.bizmodelCacheClear();
        LogHelper.info((String)BdeFunctionModuleEnum.BIZMODEL.getFullModuleName(), (String)String.format("\u4fee\u6539-%1$s", baseDataBizModelDTO.getName()), (String)JsonUtils.writeValueAsString((Object)baseDataBizModelDTO));
    }

    public BaseDataBizModelDTO convertBaseDataBizModelDTO(BaseDataBizModelEO baseDataBizModelData) {
        BaseDataBizModelDTO baseDataBizModelDTO = new BaseDataBizModelDTO();
        BeanUtils.copyProperties(baseDataBizModelData, baseDataBizModelDTO);
        String baseDataTableName = baseDataBizModelData.getBaseDataDefine();
        if (!StringUtils.isEmpty((String)baseDataTableName)) {
            BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
            baseDataDefineDTO.setName(baseDataTableName);
            Boolean isExist = (Boolean)this.baseDataDefineService.exist(baseDataDefineDTO).get((Object)"exist");
            if (Boolean.FALSE.equals(isExist)) {
                baseDataBizModelDTO.setStopFlag(Integer.valueOf(1));
                logger.error("\u672a\u627e\u5230\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u3010{}\u3011", (Object)baseDataTableName);
                return baseDataBizModelDTO;
            }
            PageVO baseDataDefinePageVO = this.baseDataDefineService.list(baseDataDefineDTO);
            String name = ((BaseDataDefineDO)baseDataDefinePageVO.getRows().get(0)).getTitle();
            baseDataBizModelDTO.setBaseDataTableName(name);
        }
        if (!StringUtils.isEmpty((String)baseDataBizModelData.getFetchFields())) {
            baseDataBizModelDTO.setFetchFields(Arrays.asList(baseDataBizModelData.getFetchFields().split(",")));
        }
        baseDataBizModelDTO.setComputationModelName(ComputationModelEnum.BASEDATA.getName());
        baseDataBizModelDTO.setFetchFieldNames(this.getFetchTypeNames(baseDataBizModelDTO));
        return baseDataBizModelDTO;
    }

    private String getFetchTypeNames(BaseDataBizModelDTO baseDataBizModelDTO) {
        List fetchTypes = baseDataBizModelDTO.getFetchFields();
        if (CollectionUtils.isEmpty((Collection)fetchTypes)) {
            return "";
        }
        ArrayList fetchTypesNames = new ArrayList();
        String baseDataDefine = baseDataBizModelDTO.getBaseDataDefine();
        DataModelDTO param = new DataModelDTO();
        param.setName(baseDataDefine);
        DataModelDO dataModelDO = this.dataModelService.get(param);
        HashMap<String, String> baseDataMap = new HashMap<String, String>(dataModelDO.getColumns().size() * 2);
        for (DataModelColumn column : dataModelDO.getColumns()) {
            baseDataMap.put(column.getColumnName(), column.getColumnTitle());
        }
        fetchTypes.forEach(fetchType -> fetchTypesNames.add(baseDataMap.get(fetchType)));
        return CollectionUtils.toString(fetchTypesNames);
    }

    private void checkBaseDataBizModelDTO(BaseDataBizModelDTO baseDataBizModelDTO, boolean isSaveFlag) {
        if (null == baseDataBizModelDTO) {
            throw new BusinessRuntimeException("\u57fa\u7840\u6570\u636e\u6a21\u578b\u914d\u7f6e\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)baseDataBizModelDTO.getCode()) || StringUtils.isEmpty((String)baseDataBizModelDTO.getName())) {
            throw new BdeRuntimeException("\u57fa\u7840\u6570\u636e\u6a21\u578b\u540d\u79f0\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        baseDataBizModelDTO.setComputationModelName(ComputationModelEnum.BASEDATA.getName());
        if (CollectionUtils.isEmpty((Collection)baseDataBizModelDTO.getFetchFields())) {
            throw new BusinessRuntimeException("\u53d6\u6570\u5b57\u6bb5\u4e0d\u5141\u8bb8\u4e3a\u7a7a.");
        }
        List<BizModelDTO> list = this.bizModelService.listAll();
        Map<String, String> allCodeAndNames = list.stream().collect(Collectors.toMap(BizModelDTO::getName, BizModelDTO::getCode));
        if (isSaveFlag) {
            List<BaseDataBizModelEO> baseDataBizModelDatas;
            Set baseDataDefineSet;
            if (!allCodeAndNames.isEmpty() && allCodeAndNames.values().contains(baseDataBizModelDTO.getCode())) {
                baseDataBizModelDTO.setCode((baseDataBizModelDTO.getCode().length() > 10 ? baseDataBizModelDTO.getCode().substring(0, 10) : baseDataBizModelDTO.getCode()) + UUIDUtils.newHalfGUIDStr().toUpperCase());
            }
            if ((baseDataDefineSet = (baseDataBizModelDatas = this.baseDataBizModelDao.loadAll()).stream().map(BaseDataBizModelEO::getBaseDataDefine).collect(Collectors.toSet())).contains(baseDataBizModelDTO.getBaseDataDefine())) {
                throw new BusinessRuntimeException("\u57fa\u7840\u6570\u636e\u4e0d\u5141\u8bb8\u91cd\u590d");
            }
        }
        if (!(allCodeAndNames.isEmpty() || StringUtils.isEmpty((String)allCodeAndNames.get(baseDataBizModelDTO.getName())) || allCodeAndNames.get(baseDataBizModelDTO.getName()).equals(baseDataBizModelDTO.getCode()))) {
            throw new BusinessRuntimeException("\u4e1a\u52a1\u6a21\u578b\u540d\u79f0\u4e0d\u5141\u8bb8\u91cd\u590d,\u540d\u79f0:" + baseDataBizModelDTO.getName());
        }
    }

    public BaseDataBizModelDTO getById(String id) {
        Assert.isNotEmpty((String)id);
        return this.listModel().stream().filter(item -> id.equals(item.getId())).findFirst().orElseThrow(() -> new BusinessRuntimeException(String.format("\u6839\u636e\u4e1a\u52a1\u6a21\u578b\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879\uff0c\u8bf7\u68c0\u67e5\u4e1a\u52a1\u6a21\u578b\u662f\u5426\u88ab\u505c\u7528\u6216\u5220\u9664", id)));
    }

    public BaseDataBizModelDTO getByCode(String code) {
        Assert.isNotEmpty((String)code);
        return this.listModel().stream().filter(item -> code.equals(item.getCode())).findFirst().orElseThrow(() -> new BusinessRuntimeException(String.format("\u6839\u636e\u4e1a\u52a1\u6a21\u578b\u4ee3\u7801\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879\uff0c\u8bf7\u68c0\u67e5\u4e1a\u52a1\u6a21\u578b\u662f\u5426\u88ab\u505c\u7528\u6216\u5220\u9664", code)));
    }

    @Override
    public void bizmodelCacheClear() {
        this.bdeBizModelCache.clear();
    }

    @Override
    public String getTableName() {
        return "BDE_BIZMODEL_BASEDATA";
    }

    @Override
    public BizModelDao getBizModelDao() {
        return this.bizModelDao;
    }

    @Override
    public IBizModelGather getBizModelGather() {
        return this.bizModelGather;
    }

    @Override
    public BizModelColumnDefineVO getColumnDefines(String bizModelCode) {
        Assert.isNotEmpty((String)bizModelCode);
        BaseDataBizModelDTO bizModelDTO = (BaseDataBizModelDTO)this.bizModelService.get(bizModelCode);
        BizModelColumnDefineVO modelColumnDefine = new BizModelColumnDefineVO();
        ArrayList<ColumnDefineVO> columnDefines = new ArrayList<ColumnDefineVO>();
        IBizComputationModel bizModel = this.bizModelGather.getComputationModelByCode(bizModelDTO.getComputationModelCode());
        Map<String, String> fetchFieldMap = this.getFetchFieldMap(bizModelDTO);
        List fixedFields = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)bizModel.getFixedFields()), (TypeReference)new TypeReference<List<ColumnDefineVO>>(){});
        for (ColumnDefineVO columnDefineVO : fixedFields) {
            columnDefines.add(columnDefineVO);
            if (!FetchFixedFieldEnum.FETCHTYPE.getCode().equals(columnDefineVO.getCode())) continue;
            ArrayList fetchTypeSelectOptions = new ArrayList();
            bizModelDTO.getFetchFields().forEach(fetchField -> fetchTypeSelectOptions.add(new SelectOptionVO(fetchField, (String)fetchFieldMap.get(fetchField))));
            columnDefineVO.setData(fetchTypeSelectOptions);
        }
        modelColumnDefine.setColumnDefines(columnDefines);
        modelColumnDefine.setOptionItems(bizModel.getOptionItems());
        modelColumnDefine.setFetchSourceCode(bizModelCode);
        modelColumnDefine.setComputationModelIcon(bizModel.getIcon());
        return modelColumnDefine;
    }

    private Map<String, String> getFetchFieldMap(BaseDataBizModelDTO bizModelDTO) {
        DataModelDTO param = new DataModelDTO();
        param.setName(bizModelDTO.getBaseDataDefine());
        DataModelDO dataModelDO = this.dataModelService.get(param);
        HashMap<String, String> columnMap = new HashMap<String, String>(dataModelDO.getColumns().size() * 2);
        for (DataModelColumn column : dataModelDO.getColumns()) {
            columnMap.put(column.getColumnName(), column.getColumnTitle());
        }
        return columnMap;
    }
}

