/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.BizModelClient
 *  com.jiuqi.bde.common.constant.FetchFixedFieldEnum
 *  com.jiuqi.bde.common.constant.OptionItemEnum
 *  com.jiuqi.bde.common.dto.BaseDataSimpleBizModelPluginDataV0
 *  com.jiuqi.bde.common.dto.FloatQueryFieldVO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.FloatZbMappingVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.bde.common.dto.SimpleCustomComposePluginDataVO
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.bde.common.intf.BaseDataDimension
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.bde.common.constant.FloatResultQueryTypeEnum
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingSaveDataVO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingDesEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesBaseDataService
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.intf.IDimensionProvider
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.transaction.annotation.Transactional
 *  tk.mybatis.mapper.util.StringUtil
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.jiuqi.bde.bizmodel.client.BizModelClient;
import com.jiuqi.bde.common.constant.FetchFixedFieldEnum;
import com.jiuqi.bde.common.constant.OptionItemEnum;
import com.jiuqi.bde.common.dto.BaseDataSimpleBizModelPluginDataV0;
import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.FloatZbMappingVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.bde.common.dto.SimpleCustomComposePluginDataVO;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.bde.common.intf.BaseDataDimension;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.common.constant.FloatResultQueryTypeEnum;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingSaveDataVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingDesService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesBaseDataService;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.util.StringUtil;

@Service
public class FetchFloatSettingDesServiceImpl
implements FetchFloatSettingDesService {
    private static final Pattern FLOAT_ARGS_PATTERN = Pattern.compile("(?<=\\$\\{).*?(?=})");
    private static final Pattern FLOAT_LOGIC_ARGS_PATTERN = Pattern.compile("(?<=FLOAT\\[).*?(?=])");
    @Autowired
    private FetchFloatSettingDesDao fetchFloatSettingDesDao;
    @Autowired
    private FetchSettingDesDao fetchSettingDesDao;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IDimensionProvider dimensionProvider;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private BizModelClient bizModelClient;
    @Autowired
    private FetchSettingDesBaseDataService fetchSettingDesBaseDataService;

    @Override
    public FloatRegionConfigVO getFetchFloatSettingDes(FetchSettingCond fetchSettingCond) {
        FetchFloatSettingDesEO fetchFloatSettingDes = this.fetchFloatSettingDesDao.getFetchFloatSettingDes(fetchSettingCond);
        return this.convertFloatSettingEoToVo(fetchFloatSettingDes);
    }

    @Override
    public List<FloatRegionConfigVO> listFetchFloatSettingDesByFetchScheme(FetchSettingCond fetchSettingCond) {
        List fetchFloatSettingDesEOS = this.fetchFloatSettingDesDao.listFetchFloatSettingDesByFetchScheme(fetchSettingCond);
        return fetchFloatSettingDesEOS.stream().map(this::convertFloatSettingEoToVo).collect(Collectors.toList());
    }

    @Override
    public List<FloatRegionConfigVO> listFloatSettingDesByCondi(FetchSettingCond fetchSettingCond) {
        List fetchFloatSettingDesEOS = this.fetchFloatSettingDesDao.listFloatSettingDesByCondi(fetchSettingCond);
        return fetchFloatSettingDesEOS.stream().map(this::convertFloatSettingEoToVo).collect(Collectors.toList());
    }

    private FloatRegionConfigVO convertFloatSettingEoToVo(FetchFloatSettingDesEO fetchFloatSettingDes) {
        if (fetchFloatSettingDes == null) {
            return null;
        }
        FloatRegionConfigVO fetchFloatSetting = new FloatRegionConfigVO();
        BeanUtils.copyProperties(fetchFloatSettingDes, fetchFloatSetting);
        if (!StringUtils.isEmpty((String)fetchFloatSettingDes.getQueryConfigInfo())) {
            fetchFloatSetting.setQueryConfigInfo((QueryConfigInfo)JSONUtil.parseObject((String)fetchFloatSettingDes.getQueryConfigInfo(), QueryConfigInfo.class));
        }
        this.reSetSimpleDataComposePluginData(fetchFloatSetting.getQueryType(), fetchFloatSetting.getQueryConfigInfo());
        return fetchFloatSetting;
    }

    private void reSetSimpleDataComposePluginData(String queryType, QueryConfigInfo queryConfigInfo) {
        if (!FloatResultQueryTypeEnum.SIMPLE_FETCHSOURCE.getCode().equals(queryType)) {
            return;
        }
        BaseDataSimpleBizModelPluginDataV0 simplePluginDataV0 = (BaseDataSimpleBizModelPluginDataV0)JsonUtils.readValue((String)queryConfigInfo.getPluginData(), BaseDataSimpleBizModelPluginDataV0.class);
        Map dimBaseDataTableMap = (Map)BdeClientUtil.parseResponse((BusinessResponseEntity)this.bizModelClient.getBaseDataInputConfig());
        List dimensionMapping = simplePluginDataV0.getDimensionMapping();
        for (BaseDataDimension baseDataDimension : dimensionMapping) {
            switch (baseDataDimension.getDimCode()) {
                case "SUBJECTCODE": {
                    baseDataDimension.setBaseDataTable((String)dimBaseDataTableMap.get(FetchFixedFieldEnum.SUBJECTCODE.getCode()));
                    break;
                }
                case "CURRENCYCODE": {
                    baseDataDimension.setBaseDataTable((String)dimBaseDataTableMap.get(OptionItemEnum.CURRENCYCODE.getCode()));
                    break;
                }
                case "CFITEMCODE": {
                    baseDataDimension.setBaseDataTable((String)dimBaseDataTableMap.get(FetchFixedFieldEnum.CASHCODE.getCode()));
                    break;
                }
                default: {
                    baseDataDimension.setBaseDataTable((String)dimBaseDataTableMap.get(baseDataDimension.getDimCode()));
                }
            }
            if (StringUtil.isNotEmpty((String)baseDataDimension.getDimValue())) {
                baseDataDimension.setDimBaseDataValue(this.fetchSettingDesBaseDataService.getBaseDataByTableAndCodeSingle(baseDataDimension.getBaseDataTable(), baseDataDimension.getDimValue()));
            }
            if (!StringUtil.isNotEmpty((String)baseDataDimension.getExcludeValue())) continue;
            baseDataDimension.setExcludeBaseDataValue(this.fetchSettingDesBaseDataService.getBaseDataByTableAndCodeSingle(baseDataDimension.getBaseDataTable(), baseDataDimension.getExcludeValue()));
        }
        queryConfigInfo.setPluginData(JsonUtils.writeValueAsString((Object)simplePluginDataV0));
    }

    @Override
    public Map<String, FloatRegionConfigVO> getFloatRegionSettingDesByForm(FetchSettingCond fetchSettingCond) {
        List fetchFloatSettingDes = this.fetchFloatSettingDesDao.listFetchFloatSettingDesByFormId(fetchSettingCond);
        return fetchFloatSettingDes.stream().map(item -> this.convertFloatSettingEoToVo((FetchFloatSettingDesEO)item)).collect(Collectors.toMap(FloatRegionConfigVO::getRegionId, Function.identity()));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String saveFetchFloatSettingDataHandle(FetchSettingSaveDataVO fetchSettingSaveData) {
        ArrayList<List<Object>> deleteFixedSettingWhereValues = new ArrayList<List<Object>>();
        StringBuffer massageInfo = new StringBuffer();
        List<FetchFloatSettingDesEO> fetchFloatSettingDatas = this.listSaveFloatSettingData(fetchSettingSaveData, deleteFixedSettingWhereValues, massageInfo);
        if (massageInfo.length() <= 0) {
            if (!CollectionUtils.isEmpty(deleteFixedSettingWhereValues)) {
                this.fetchFloatSettingDesDao.deleteBatchFetchFloatSettingDesData(deleteFixedSettingWhereValues);
                this.fetchSettingDesDao.deleteBatchFetchSettingDesDataByRegionId(deleteFixedSettingWhereValues);
            }
            if (!CollectionUtils.isEmpty(fetchFloatSettingDatas)) {
                ArrayList deleteFloatSettingWhereValues = new ArrayList(fetchFloatSettingDatas.stream().map(fetchFloatSettingDes -> Arrays.asList(fetchFloatSettingDes.getFormSchemeId(), fetchFloatSettingDes.getFetchSchemeId(), fetchFloatSettingDes.getFormId(), fetchFloatSettingDes.getRegionId())).collect(Collectors.toList()));
                this.fetchFloatSettingDesDao.deleteBatchFetchFloatSettingDesData(deleteFloatSettingWhereValues);
                this.fetchFloatSettingDesDao.addBatch(fetchFloatSettingDatas);
            }
        }
        return massageInfo.toString();
    }

    @Override
    public List<SelectOptionVO> listOtherEntity(String formSchemeId) {
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeId);
        if (ObjectUtils.isEmpty(formSchemeDefine.getDims())) {
            return CollectionUtils.newArrayList();
        }
        return this.listOtherEntityByMasterEntitiesKey(formSchemeDefine.getDims());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void cleanFloatSetting(FetchSettingCond fetchSettingCond) {
        Assert.isNotEmpty((String)fetchSettingCond.getFormSchemeId(), (String)"\u62a5\u8868\u65b9\u6848ID\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)fetchSettingCond.getFetchSchemeId(), (String)"\u53d6\u6570\u65b9\u6848ID\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)fetchSettingCond.getFormId(), (String)"\u62a5\u8868\u8868\u5355ID\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)fetchSettingCond.getRegionId(), (String)"\u533a\u57dfID\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        DataRegionDefine dataRegionDefine = this.iRunTimeViewController.queryDataRegionDefine(fetchSettingCond.getRegionId());
        if (dataRegionDefine == null || !DataRegionKind.DATA_REGION_ROW_LIST.equals((Object)dataRegionDefine.getRegionKind())) {
            throw new BusinessRuntimeException("\u6240\u9009\u533a\u57df\u4e0d\u662f\u6d6e\u52a8\u533a\u57df,\u65e0\u6cd5\u6e05\u9664");
        }
        ArrayList<List<Object>> deleteWhereValues = new ArrayList<List<Object>>();
        deleteWhereValues.add(Arrays.asList(fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId(), fetchSettingCond.getRegionId()));
        this.fetchFloatSettingDesDao.deleteBatchFetchFloatSettingDesData(deleteWhereValues);
        this.fetchSettingDesDao.deleteFloatFetchSettingDesData(deleteWhereValues);
    }

    private List<FetchFloatSettingDesEO> listSaveFloatSettingData(FetchSettingSaveDataVO fetchSettingSaveData, List<List<Object>> deleteFixSettingWhereValues, StringBuffer massageInfo) {
        Map fetchFloatSettingDatas = fetchSettingSaveData.getFetchFloatSettingDatas();
        ArrayList<FetchFloatSettingDesEO> fetchFloatSettingDesDatas = new ArrayList<FetchFloatSettingDesEO>();
        for (String regionId : fetchFloatSettingDatas.keySet()) {
            DataRegionDefine dataRegionDefine = this.iRunTimeViewController.queryDataRegionDefine(regionId);
            if (null == dataRegionDefine) {
                throw new BdeRuntimeException("\u533a\u57df\u5bf9\u8c61\u4e0d\u5b58\u5728 regionId=" + regionId);
            }
            if (DataRegionKind.DATA_REGION_SIMPLE.equals((Object)dataRegionDefine.getRegionKind())) continue;
            FloatRegionConfigVO fetchFloatSetting = (FloatRegionConfigVO)fetchFloatSettingDatas.get(regionId);
            if (null == fetchFloatSetting) {
                List<Object> deleteFixedSettingObjs = Arrays.asList(fetchSettingSaveData.getFormSchemeId(), fetchSettingSaveData.getFetchSchemeId(), fetchSettingSaveData.getFormId(), regionId);
                deleteFixSettingWhereValues.add(deleteFixedSettingObjs);
                continue;
            }
            QueryConfigInfo queryConfigInfo = fetchFloatSetting.getQueryConfigInfo();
            this.standardizedSimpleDataComposePluginData(fetchFloatSetting.getQueryType(), queryConfigInfo);
            if (null == queryConfigInfo || CollectionUtils.isEmpty((Collection)queryConfigInfo.getZbMapping()) || CollectionUtils.isEmpty((Collection)queryConfigInfo.getQueryFields())) {
                List<Object> deleteFixedSettingObjs = Arrays.asList(fetchSettingSaveData.getFormSchemeId(), fetchSettingSaveData.getFetchSchemeId(), fetchSettingSaveData.getFormId(), regionId);
                deleteFixSettingWhereValues.add(deleteFixedSettingObjs);
                fetchFloatSettingDesDatas.add(this.getFetchFloatSettingDesEO(fetchSettingSaveData, regionId, fetchFloatSetting, queryConfigInfo));
                continue;
            }
            List queryNames = queryConfigInfo.getQueryFields().stream().map(FloatQueryFieldVO::getName).collect(Collectors.toList());
            List allLinkIds = queryConfigInfo.getZbMapping().stream().map(FloatZbMappingVO::getDataLinkId).collect(Collectors.toList());
            ArrayList<String> deleteLinkIds = new ArrayList<String>();
            Set distinctLinkId = allLinkIds.stream().filter(i -> allLinkIds.stream().filter(i::equals).count() > 1L).collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(distinctLinkId)) {
                for (String linkId : distinctLinkId) {
                    DataLinkDefine dataLinkDefine = this.iRunTimeViewController.queryDataLinkDefine(linkId);
                    if (null == dataLinkDefine) {
                        massageInfo.append("\u6570\u636e\u94fe\u63a5\u5bf9\u8c61\u4e0d\u5b58\u5728 dataLinkId=").append(linkId);
                        continue;
                    }
                    massageInfo.append("\u5750\u6807\uff1a[").append(dataLinkDefine.getPosX()).append(",").append(dataLinkDefine.getPosY()).append("]\u8bbe\u7f6e\u91cd\u590d\u3002");
                }
                continue;
            }
            Map<String, String> dataLinkAndQueryNameMapping = queryConfigInfo.getZbMapping().stream().collect(Collectors.toMap(FloatZbMappingVO::getDataLinkId, FloatZbMappingVO::getQueryName));
            for (String dataLinkId : dataLinkAndQueryNameMapping.keySet()) {
                DataLinkDefine dataLinkDefine = this.iRunTimeViewController.queryDataLinkDefine(dataLinkId);
                if (null == dataLinkDefine) {
                    deleteLinkIds.add(dataLinkId);
                    continue;
                }
                String queryName = dataLinkAndQueryNameMapping.get(dataLinkId);
                String patternStr = "(?<=\\$\\{)[^}]*(?=})";
                Pattern pattern = Pattern.compile(patternStr);
                Matcher matcher = pattern.matcher(queryName);
                if (!matcher.find() || queryNames.contains(matcher.group(0))) continue;
                massageInfo.append("\u5750\u6807\uff1a[").append(dataLinkDefine.getPosX()).append(",").append(dataLinkDefine.getPosY()).append("]\uff0c\u67e5\u8be2\u5b9a\u4e49\u5217\u3010").append(matcher.group(0)).append("\u3011\u4e0d\u5b58\u5728 \r\n");
            }
            queryConfigInfo.setZbMapping(this.zbMappingInitDataInfo(queryConfigInfo.getZbMapping(), deleteLinkIds));
            queryConfigInfo.setUsedFields(this.initUsedFields(fetchSettingSaveData, regionId, queryConfigInfo.getQueryFields(), queryConfigInfo.getZbMapping()));
            this.fetchSettingDesDao.deleteFetchSettingByFetchSettingCond(new FetchSettingCond(fetchSettingSaveData.getFetchSchemeId(), fetchSettingSaveData.getFormSchemeId(), fetchSettingSaveData.getFormId(), regionId), deleteLinkIds);
            if (CollectionUtils.isEmpty((Collection)queryConfigInfo.getUsedFields())) {
                massageInfo.append("\u6d6e\u52a8\u884c\u53d6\u6570\u914d\u7f6e\u4e2d\u6ca1\u6709\u7528\u5230\u4efb\u4f55\u67e5\u8be2\u5b9a\u4e49\u7ed3\u679c\u5b57\u6bb5,\u8bf7\u68c0\u67e5\u914d\u7f6e\u4fe1\u606f\uff01");
            }
            if (massageInfo.length() > 0) continue;
            fetchFloatSettingDesDatas.add(this.getFetchFloatSettingDesEO(fetchSettingSaveData, regionId, fetchFloatSetting, queryConfigInfo));
        }
        return fetchFloatSettingDesDatas;
    }

    private void standardizedSimpleDataComposePluginData(String queryType, QueryConfigInfo queryConfigInfo) {
        if (!FloatResultQueryTypeEnum.SIMPLE_FETCHSOURCE.getCode().equals(queryType)) {
            return;
        }
        SimpleCustomComposePluginDataVO sluginDataVO = (SimpleCustomComposePluginDataVO)JsonUtils.readValue((String)queryConfigInfo.getPluginData(), SimpleCustomComposePluginDataVO.class);
        queryConfigInfo.setPluginData(JsonUtils.writeValueAsString((Object)sluginDataVO));
    }

    private FetchFloatSettingDesEO getFetchFloatSettingDesEO(FetchSettingSaveDataVO fetchSettingSaveData, String regionId, FloatRegionConfigVO fetchFloatSetting, QueryConfigInfo queryConfigInfo) {
        fetchFloatSetting.setFormSchemeId(fetchSettingSaveData.getFormSchemeId());
        fetchFloatSetting.setFetchSchemeId(fetchSettingSaveData.getFetchSchemeId());
        fetchFloatSetting.setFormId(fetchSettingSaveData.getFormId());
        fetchFloatSetting.setRegionId(regionId);
        FetchFloatSettingDesEO fetchFloatSettingDes = new FetchFloatSettingDesEO();
        BeanUtils.copyProperties(fetchFloatSetting, fetchFloatSettingDes);
        if (StringUtils.isEmpty((String)fetchFloatSettingDes.getId())) {
            fetchFloatSettingDes.setId(UUIDUtils.newHalfGUIDStr());
        }
        if (queryConfigInfo != null) {
            fetchFloatSettingDes.setQueryConfigInfo(JSONUtil.toJSONString((Object)queryConfigInfo));
        }
        return fetchFloatSettingDes;
    }

    private List<FloatZbMappingVO> zbMappingInitDataInfo(List<FloatZbMappingVO> zbMapping, List<String> deleteLinkId) {
        return zbMapping.stream().filter(floatZbMappingVO -> !deleteLinkId.contains(floatZbMappingVO.getDataLinkId())).map(item -> this.setFieldDefineInfo((FloatZbMappingVO)item)).collect(Collectors.toList());
    }

    private FloatZbMappingVO setFieldDefineInfo(FloatZbMappingVO zbMappingVO) {
        DataLinkDefine dataLinkDefine = this.iRunTimeViewController.queryDataLinkDefine(zbMappingVO.getDataLinkId());
        if (dataLinkDefine == null) {
            return zbMappingVO;
        }
        zbMappingVO.setFieldDefineId(dataLinkDefine.getLinkExpression());
        try {
            FieldDefine fieldDefine = this.iRunTimeViewController.queryFieldDefine(zbMappingVO.getFieldDefineId());
            zbMappingVO.setDataLinkName(fieldDefine.getTitle());
            zbMappingVO.setFieldDefineType(Integer.valueOf(DataTypesConvert.fieldTypeToDataType((FieldType)fieldDefine.getType())));
            return zbMappingVO;
        }
        catch (Exception e) {
            throw new BdeRuntimeException("\u67e5\u8be2\u6d6e\u52a8\u8868\u6307\u6807\u6620\u5c04\u65f6\uff0c\u6839\u636e\u6307\u6807id\u3010" + zbMappingVO.getFieldDefineId() + "\u3011\u83b7\u53d6\u6307\u6807\u5f02\u5e38", (Throwable)e);
        }
    }

    private List<String> initUsedFields(FetchSettingSaveDataVO fetchSettingSaveData, String regionId, List<FloatQueryFieldVO> queryFields, List<FloatZbMappingVO> zbMapping) {
        FetchSettingCond fetchSettingCond = new FetchSettingCond();
        fetchSettingCond.setFetchSchemeId(fetchSettingSaveData.getFetchSchemeId());
        fetchSettingCond.setFormSchemeId(fetchSettingSaveData.getFormSchemeId());
        fetchSettingCond.setFormId(fetchSettingSaveData.getFormId());
        fetchSettingCond.setRegionId(regionId);
        List fetchSettingDesEOS = this.fetchSettingDesDao.listFetchSettingDesByRegionId(fetchSettingCond);
        String fetchSettingJsonStr = JSONUtil.toJSONString((Object)fetchSettingDesEOS);
        Matcher matcher = FLOAT_ARGS_PATTERN.matcher(fetchSettingJsonStr);
        HashSet<String> fieldSet = new HashSet<String>();
        while (matcher.find()) {
            fieldSet.add(matcher.group());
        }
        matcher = FLOAT_LOGIC_ARGS_PATTERN.matcher(fetchSettingJsonStr);
        while (matcher.find()) {
            fieldSet.add(matcher.group());
        }
        String zbMappingJsonStr = JSONUtil.toJSONString(zbMapping);
        matcher = FLOAT_ARGS_PATTERN.matcher(zbMappingJsonStr);
        while (matcher.find()) {
            fieldSet.add(matcher.group());
        }
        LinkedHashSet<String> result = new LinkedHashSet<String>();
        for (FloatQueryFieldVO queryFieldVO : queryFields) {
            String fieldName = queryFieldVO.getName().trim();
            if (!fieldSet.contains(fieldName)) continue;
            result.add(fieldName);
        }
        return new ArrayList<String>(result);
    }

    private List<SelectOptionVO> listOtherEntityByMasterEntitiesKey(String masterEntitiesKey) {
        String[] entityViewKeyArray;
        ArrayList<SelectOptionVO> selectOptions = new ArrayList<SelectOptionVO>();
        for (String entityId : entityViewKeyArray = masterEntitiesKey.split(";")) {
            TableModelDefine tableModelDefine = this.entityMetaService.getTableModel(entityId);
            String dimensionName = this.dimensionProvider.getDimensionNameByEntityId(entityId);
            if (tableModelDefine == null) continue;
            SelectOptionVO selectOption = new SelectOptionVO(dimensionName, tableModelDefine.getTitle());
            selectOptions.add(selectOption);
        }
        return selectOptions;
    }
}

