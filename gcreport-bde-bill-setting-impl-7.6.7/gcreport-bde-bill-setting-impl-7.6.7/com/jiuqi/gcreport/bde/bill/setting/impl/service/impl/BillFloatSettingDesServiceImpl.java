/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.BizModelClient
 *  com.jiuqi.bde.common.constant.FetchFixedFieldEnum
 *  com.jiuqi.bde.common.constant.OptionItemEnum
 *  com.jiuqi.bde.common.dto.BaseDataSimpleBizModelPluginDataV0
 *  com.jiuqi.bde.common.dto.FloatQueryFieldVO
 *  com.jiuqi.bde.common.dto.FloatZbMappingVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.dto.SimpleCustomComposePluginDataVO
 *  com.jiuqi.bde.common.intf.BaseDataDimension
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO
 *  com.jiuqi.gcreport.bde.common.constant.FloatResultQueryTypeEnum
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingDesEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesBaseDataService
 *  com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  tk.mybatis.mapper.util.StringUtil
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.service.impl;

import com.jiuqi.bde.bizmodel.client.BizModelClient;
import com.jiuqi.bde.common.constant.FetchFixedFieldEnum;
import com.jiuqi.bde.common.constant.OptionItemEnum;
import com.jiuqi.bde.common.dto.BaseDataSimpleBizModelPluginDataV0;
import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.FloatZbMappingVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.dto.SimpleCustomComposePluginDataVO;
import com.jiuqi.bde.common.intf.BaseDataDimension;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO;
import com.jiuqi.gcreport.bde.bill.setting.impl.intf.FloatSettingCvtResult;
import com.jiuqi.gcreport.bde.bill.setting.impl.intf.IBillExtractSchemeUnifiedHandler;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillExtractSchemeService;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFloatSettingDesService;
import com.jiuqi.gcreport.bde.common.constant.FloatResultQueryTypeEnum;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesBaseDataService;
import com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

@Service
public class BillFloatSettingDesServiceImpl
implements BillFloatSettingDesService,
IBillExtractSchemeUnifiedHandler {
    @Autowired
    private BillExtractSchemeService schemeService;
    private static final Pattern FLOAT_ARGS_PATTERN = Pattern.compile("(?<=\\$\\{).*?(?=})");
    private static final Pattern FLOAT_LOGIC_ARGS_PATTERN = Pattern.compile("(?<=FLOAT\\[).*?(?=])");
    @Autowired
    private FetchFloatSettingDesDao fetchFloatSettingDesDao;
    @Autowired
    private FetchSettingDesDao fetchSettingDesDao;
    @Autowired
    private BizModelClient bizModelClient;
    @Autowired
    private FetchSettingDesBaseDataService fetchSettingDesBaseDataService;
    static final String SIMPLE_FETCHSOURCE = "SIMPLE_FETCHSOURCE";

    @Override
    public BillFloatRegionConfigDTO getBillFloatSetting(BillSettingCondiDTO billSettingCondi) {
        Assert.isNotNull((Object)billSettingCondi);
        Assert.isNotEmpty((String)billSettingCondi.getBillType());
        Assert.isNotEmpty((String)billSettingCondi.getSchemeId());
        Assert.isNotEmpty((String)billSettingCondi.getBillTable());
        FetchSettingCond condi = new FetchSettingCond();
        condi.setFormSchemeId(billSettingCondi.getBillType());
        condi.setFetchSchemeId(billSettingCondi.getSchemeId());
        condi.setFormId(billSettingCondi.getBillTable());
        condi.setRegionId(billSettingCondi.getBillTable());
        FetchFloatSettingDesEO floatSetting = this.fetchFloatSettingDesDao.getFetchFloatSettingDes(condi);
        return this.convert2Dto(floatSetting);
    }

    @Override
    public Map<String, BillFloatRegionConfigDTO> getBillFloatSettingByBillCodeAndScheme(BillSettingCondiDTO billSettingCondi) {
        Assert.isNotNull((Object)billSettingCondi);
        Assert.isNotEmpty((String)billSettingCondi.getBillType());
        Assert.isNotEmpty((String)billSettingCondi.getSchemeId());
        FetchSettingCond condi = new FetchSettingCond();
        condi.setFormSchemeId(billSettingCondi.getBillType());
        condi.setFetchSchemeId(billSettingCondi.getSchemeId());
        List fetchFloatSettingDes = this.fetchFloatSettingDesDao.listFetchFloatSettingDesByBillUniqueCodeAndFetchSchemeId(condi);
        HashMap<String, BillFloatRegionConfigDTO> regionConfigDTOMap = new HashMap<String, BillFloatRegionConfigDTO>();
        for (FetchFloatSettingDesEO settingDes : fetchFloatSettingDes) {
            String tableName = settingDes.getRegionId();
            regionConfigDTOMap.put(tableName, this.convert2Dto(settingDes));
        }
        return regionConfigDTOMap;
    }

    @Override
    public void save(String schemeId, BillExtractSettingDTO setting) {
        BillFetchSchemeDTO schemeDto = this.schemeService.getById(schemeId);
        FloatSettingCvtResult result = this.convert2Eo(schemeDto, setting);
        if (!CollectionUtils.isEmpty(result.getDelFixSettingValues())) {
            this.fetchFloatSettingDesDao.deleteBatchFetchFloatSettingDesData(result.getDelFixSettingValues());
            this.fetchSettingDesDao.deleteBatchFetchSettingDesDataByRegionId(result.getDelFixSettingValues());
        }
        if (!CollectionUtils.isEmpty(result.getFloatSettings())) {
            ArrayList deleteFloatSettingWhereValues = new ArrayList(result.getFloatSettings().stream().map(fetchFloatSettingDes -> Arrays.asList(fetchFloatSettingDes.getFormSchemeId(), fetchFloatSettingDes.getFetchSchemeId(), fetchFloatSettingDes.getFormId(), fetchFloatSettingDes.getRegionId())).collect(Collectors.toList()));
            this.fetchFloatSettingDesDao.deleteBatchFetchFloatSettingDesData(deleteFloatSettingWhereValues);
            this.fetchFloatSettingDesDao.addBatch(result.getFloatSettings());
        }
    }

    @Override
    public int delete(BillFetchSchemeDTO schemeDto) {
        Assert.isNotNull((Object)schemeDto);
        Assert.isNotEmpty((String)schemeDto.getId());
        this.fetchFloatSettingDesDao.deleteByFetchSchemeId(schemeDto.getId());
        return 0;
    }

    @Override
    public int syncCache(BillFetchSchemeDTO schemeDto) {
        return 0;
    }

    @Override
    public void copy(BillFetchSchemeDTO srcScheme, String targetId) {
        Assert.isNotNull((Object)srcScheme);
        Assert.isNotEmpty((String)srcScheme.getBillType());
        Assert.isNotEmpty((String)srcScheme.getId());
        Assert.isNotEmpty((String)targetId);
        FetchSettingCond condi = new FetchSettingCond();
        condi.setFormSchemeId(srcScheme.getBillType());
        condi.setFetchSchemeId(srcScheme.getId());
        List fetchFloatSettingDesEOS = this.fetchFloatSettingDesDao.listFloatSettingDesByCondi(condi);
        if (CollectionUtils.isEmpty((Collection)fetchFloatSettingDesEOS)) {
            return;
        }
        ArrayList<FetchFloatSettingDesEO> floatSettingData = new ArrayList<FetchFloatSettingDesEO>();
        for (FetchFloatSettingDesEO srcFloatSettingDesEO : fetchFloatSettingDesEOS) {
            FetchFloatSettingDesEO floatSettingDesEO = new FetchFloatSettingDesEO();
            BeanUtils.copyProperties(srcFloatSettingDesEO, floatSettingDesEO);
            floatSettingDesEO.setFetchSchemeId(targetId);
            floatSettingDesEO.setId(UUIDUtils.newHalfGUIDStr());
            floatSettingData.add(floatSettingDesEO);
        }
        this.fetchFloatSettingDesDao.addBatch(floatSettingData);
        BillFetchSchemeDTO targetScheme = (BillFetchSchemeDTO)BeanConvertUtil.convert((Object)srcScheme, BillFetchSchemeDTO.class, (String[])new String[0]);
        targetScheme.setId(targetId);
        this.syncCache(targetScheme);
    }

    private FloatSettingCvtResult convert2Eo(BillFetchSchemeDTO schemeDto, BillExtractSettingDTO setting) {
        FloatSettingCvtResult result = new FloatSettingCvtResult();
        BillFloatRegionConfigDTO floatSetting = setting.getFloatSetting();
        if (floatSetting == null || StringUtils.isEmpty((String)floatSetting.getQueryType())) {
            if (setting.getItemTableSetting() == null || setting.getItemTableSetting().isEmpty()) {
                return result;
            }
            for (Map.Entry itemSettingEntry : setting.getItemTableSetting().entrySet()) {
                result.addResult(this.convert2Eo(schemeDto, (BillExtractSettingDTO)itemSettingEntry.getValue()));
            }
            return result;
        }
        QueryConfigInfo queryConfigInfo = floatSetting.getQueryConfigInfo();
        this.standardizedSimpleDataComposePluginData(floatSetting.getQueryType(), queryConfigInfo);
        if (null == queryConfigInfo || CollectionUtils.isEmpty((Collection)queryConfigInfo.getZbMapping()) || CollectionUtils.isEmpty((Collection)queryConfigInfo.getQueryFields())) {
            List<Object> deleteFixedSettingObjs = Arrays.asList(schemeDto.getBillType(), schemeDto.getId(), setting.getBillTable(), setting.getBillTable());
            result.addDelFixSettingValues(deleteFixedSettingObjs);
            result.addFloatSettings(this.getFetchFloatSettingDesEO(schemeDto, floatSetting));
            return result;
        }
        List allLinkIds = queryConfigInfo.getZbMapping().stream().map(FloatZbMappingVO::getDataLinkId).collect(Collectors.toList());
        ArrayList<String> deleteLinkIds = new ArrayList<String>();
        Set distinctLinkId = allLinkIds.stream().filter(i -> allLinkIds.stream().filter(i::equals).count() > 1L).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(distinctLinkId)) {
            queryConfigInfo.setZbMapping(this.zbMappingInitDataInfo(queryConfigInfo.getZbMapping(), deleteLinkIds));
            queryConfigInfo.setUsedFields(this.initUsedFields(schemeDto, floatSetting));
            result.addFloatSettings(this.getFetchFloatSettingDesEO(schemeDto, floatSetting));
        }
        if (setting.getItemTableSetting() == null || setting.getItemTableSetting().isEmpty()) {
            return result;
        }
        for (Map.Entry itemSettingEntry : setting.getItemTableSetting().entrySet()) {
            result.addResult(this.convert2Eo(schemeDto, (BillExtractSettingDTO)itemSettingEntry.getValue()));
        }
        return result;
    }

    private List<String> initUsedFields(BillFetchSchemeDTO schemeDto, BillFloatRegionConfigDTO floatSetting) {
        FetchSettingCond fetchSettingCond = new FetchSettingCond();
        fetchSettingCond.setFetchSchemeId(schemeDto.getId());
        fetchSettingCond.setFormSchemeId(schemeDto.getBillType());
        fetchSettingCond.setFormId(floatSetting.getBillTable());
        fetchSettingCond.setRegionId(floatSetting.getBillTable());
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
        String zbMappingJsonStr = JSONUtil.toJSONString((Object)floatSetting.getQueryConfigInfo().getZbMapping());
        matcher = FLOAT_ARGS_PATTERN.matcher(zbMappingJsonStr);
        while (matcher.find()) {
            fieldSet.add(matcher.group());
        }
        LinkedHashSet<String> result = new LinkedHashSet<String>();
        for (FloatQueryFieldVO queryFieldVO : floatSetting.getQueryConfigInfo().getQueryFields()) {
            String fieldName = queryFieldVO.getName().trim();
            if (!fieldSet.contains(fieldName)) continue;
            result.add(fieldName);
        }
        return new ArrayList<String>(result);
    }

    private FetchFloatSettingDesEO getFetchFloatSettingDesEO(BillFetchSchemeDTO schemeDto, BillFloatRegionConfigDTO floatSetting) {
        FetchFloatSettingDesEO fetchFloatSetting = new FetchFloatSettingDesEO();
        fetchFloatSetting.setFormSchemeId(schemeDto.getBillType());
        fetchFloatSetting.setFetchSchemeId(schemeDto.getId());
        fetchFloatSetting.setFormId(floatSetting.getBillTable());
        fetchFloatSetting.setRegionId(floatSetting.getBillTable());
        fetchFloatSetting.setQueryType(floatSetting.getQueryType());
        if (StringUtils.isEmpty((String)fetchFloatSetting.getId())) {
            fetchFloatSetting.setId(UUIDUtils.newHalfGUIDStr());
        }
        if (floatSetting.getQueryConfigInfo() != null) {
            fetchFloatSetting.setQueryConfigInfo(JSONUtil.toJSONString((Object)floatSetting.getQueryConfigInfo()));
        }
        return fetchFloatSetting;
    }

    private void standardizedSimpleDataComposePluginData(String queryType, QueryConfigInfo queryConfigInfo) {
        if (!SIMPLE_FETCHSOURCE.equals(queryType)) {
            return;
        }
        SimpleCustomComposePluginDataVO sluginDataVO = (SimpleCustomComposePluginDataVO)JsonUtils.readValue((String)queryConfigInfo.getPluginData(), SimpleCustomComposePluginDataVO.class);
        queryConfigInfo.setPluginData(JsonUtils.writeValueAsString((Object)sluginDataVO));
    }

    private List<FloatZbMappingVO> zbMappingInitDataInfo(List<FloatZbMappingVO> zbMapping, List<String> deleteLinkId) {
        return zbMapping.stream().filter(floatZbMappingVO -> !deleteLinkId.contains(floatZbMappingVO.getDataLinkId())).map(item -> this.setFieldDefineInfo((FloatZbMappingVO)item)).collect(Collectors.toList());
    }

    private FloatZbMappingVO setFieldDefineInfo(FloatZbMappingVO zbMappingVO) {
        return zbMappingVO;
    }

    private BillFloatRegionConfigDTO convert2Dto(FetchFloatSettingDesEO floatSetting) {
        if (floatSetting == null) {
            return null;
        }
        BillFloatRegionConfigDTO fetchFloatSetting = (BillFloatRegionConfigDTO)BeanConvertUtil.convert((Object)floatSetting, BillFloatRegionConfigDTO.class, (String[])new String[0]);
        if (!StringUtils.isEmpty((String)floatSetting.getQueryConfigInfo())) {
            fetchFloatSetting.setQueryConfigInfo((QueryConfigInfo)JsonUtils.readValue((String)floatSetting.getQueryConfigInfo(), QueryConfigInfo.class));
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
}

