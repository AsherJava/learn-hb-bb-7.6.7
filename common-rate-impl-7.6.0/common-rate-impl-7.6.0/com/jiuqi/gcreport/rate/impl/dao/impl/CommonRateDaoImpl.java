/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.gcreport.rate.impl.dao.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.rate.impl.dao.CommonRateDao;
import com.jiuqi.gcreport.rate.impl.entity.CommonRateInfoEO;
import com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommonRateDaoImpl
implements CommonRateDao {
    @Autowired
    private BaseDataClient baseDataClient;

    @Override
    public PageInfo<CommonRateInfoEO> queryRateList(String dataTimeStart, String dataTimeEnd, String sourceCurrencyCode, String targetCurrencyCode, String rateSchemeCode, Integer pageSize, Integer pageNum) {
        StringBuilder sql = new StringBuilder();
        ArrayList<String> params = new ArrayList<String>();
        StringBuilder whereSql = new StringBuilder("  where  1 = 1 \n");
        whereSql.append(" and  v.rateSchemeCode = ? \n");
        params.add(rateSchemeCode);
        if (dataTimeStart != null && dataTimeStart.trim().length() == 9) {
            whereSql.append(" and v.dataTime>= ?   \n");
            params.add(dataTimeStart);
        }
        if (dataTimeEnd != null && dataTimeEnd.trim().length() == 9) {
            whereSql.append(" and v.dataTime<= ?  \n");
            params.add(dataTimeEnd);
        }
        if (!StringUtils.isEmpty((String)sourceCurrencyCode)) {
            whereSql.append(" and  v.sourceCurrencyCode = ? \n");
            params.add(sourceCurrencyCode);
        }
        if (!StringUtils.isEmpty((String)targetCurrencyCode)) {
            whereSql.append(" and  v.targetCurrencyCode = ? \n");
            params.add(targetCurrencyCode);
        }
        sql.append("select *  from MD_ENT_RATE v \n");
        sql.append((CharSequence)whereSql);
        EntNativeSqlDefaultDao instance = EntNativeSqlDefaultDao.getInstance();
        int number = instance.count(sql.toString(), params);
        List mapList = instance.selectMap(sql.toString(), params);
        if (CollectionUtils.isEmpty((Collection)mapList)) {
            return PageInfo.empty();
        }
        List<CommonRateInfoEO> allEO = this.convertMapList2Eos(mapList);
        return PageInfo.of(allEO, (int)number);
    }

    @Override
    public CommonRateInfoEO queryRateInfo(String rateSchemeCode, String dataTime, String sourceCurrencyCode, String targetCurrencyCode) {
        String rateInfo = rateSchemeCode + "_" + dataTime + "_" + sourceCurrencyCode + "_" + targetCurrencyCode;
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName("MD_ENT_RATE");
        param.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        param.setCode(rateInfo);
        PageVO pageVO = this.baseDataClient.list(param);
        if (pageVO.getTotal() == 0) {
            return null;
        }
        List baseDataDOS = pageVO.getRows();
        BaseDataDO baseDataDO = (BaseDataDO)baseDataDOS.get(0);
        return this.covertBaseDataToEO(baseDataDO);
    }

    @Override
    public Boolean saveRates(String rateSchemeCode, List<CommonRateInfoEO> rateItemEoList) {
        BaseDataBatchOptDTO batchOptDTO = new BaseDataBatchOptDTO();
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName("MD_ENT_RATE");
        batchOptDTO.setQueryParam(param);
        for (CommonRateInfoEO eo : rateItemEoList) {
            String code = rateSchemeCode + "_" + eo.getDataTime() + "_" + eo.getSourceCurrencyCode() + "_" + eo.getTargetCurrencyCode();
            eo.setRateSchemeCode(rateSchemeCode);
            eo.setCode(code);
            eo.setName(code);
        }
        List dataList = rateItemEoList.stream().map(this::covertEOToBaseData).collect(Collectors.toList());
        batchOptDTO.setDataList(dataList);
        this.baseDataClient.batchUpdate(batchOptDTO);
        return true;
    }

    @Override
    public void deleteRateInfoBySchemeCode(String rateSchemeCode) {
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName("MD_ENT_RATE");
        param.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        param.put("rateschemecode", (Object)rateSchemeCode);
        PageVO pageVO = this.baseDataClient.list(param);
        if (pageVO.getTotal() == 0) {
            return;
        }
        List baseDataDOS = pageVO.getRows();
        BaseDataBatchOptDTO removeDTO = new BaseDataBatchOptDTO();
        removeDTO.setQueryParam(param);
        removeDTO.setDataList(baseDataDOS);
        removeDTO.addExtInfo("forceDelete", (Object)true);
        R res = this.baseDataClient.batchRemove(removeDTO);
        if (res.getCode() == 1) {
            throw new RuntimeException(res.getMsg());
        }
    }

    @Override
    public Boolean deleteRateInfos(List<String> rateItemIds) {
        if (CollectionUtils.isEmpty(rateItemIds)) {
            return true;
        }
        BaseDataBatchOptDTO removeDTO = new BaseDataBatchOptDTO();
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName("MD_ENT_RATE");
        removeDTO.setQueryParam(param);
        ArrayList<BaseDataDTO> removeBaseDataDOs = new ArrayList<BaseDataDTO>();
        for (String id : rateItemIds) {
            BaseDataDTO removeBaseDataDO = new BaseDataDTO();
            removeBaseDataDO.setTableName("MD_ENT_RATE");
            removeBaseDataDO.setId(UUIDUtils.fromString36((String)id));
            removeBaseDataDOs.add(removeBaseDataDO);
        }
        removeDTO.setDataList(removeBaseDataDOs);
        removeDTO.addExtInfo("forceDelete", (Object)true);
        R res = this.baseDataClient.batchRemove(removeDTO);
        if (res.getCode() == 1) {
            throw new RuntimeException(res.getMsg());
        }
        return true;
    }

    public CommonRateInfoEO covertBaseDataToEO(BaseDataDO baseDataDO) {
        CommonRateInfoEO commonRateInfoEO = new CommonRateInfoEO();
        commonRateInfoEO.setId(UUIDUtils.toString36((UUID)baseDataDO.getId()));
        commonRateInfoEO.setCode(baseDataDO.getCode());
        commonRateInfoEO.setName(baseDataDO.getName());
        commonRateInfoEO.setId(ConverterUtils.getAsString((Object)baseDataDO.get((Object)"id")));
        commonRateInfoEO.setDataTime(ConverterUtils.getAsString((Object)baseDataDO.get((Object)"dataTime")));
        commonRateInfoEO.setSourceCurrencyCode(ConverterUtils.getAsString((Object)baseDataDO.get((Object)"sourcecurrencycode")));
        commonRateInfoEO.setTargetCurrencyCode(ConverterUtils.getAsString((Object)baseDataDO.get((Object)"targetcurrencycode")));
        HashMap<String, BigDecimal> rateInfo = new HashMap<String, BigDecimal>();
        for (String key : baseDataDO.keySet()) {
            if (!key.startsWith("ratetype_")) continue;
            rateInfo.put(key.substring(key.indexOf(95) + 1), ConverterUtils.getAsBigDecimal((Object)baseDataDO.get((Object)key)));
        }
        commonRateInfoEO.setRateInfo(rateInfo);
        return commonRateInfoEO;
    }

    public BaseDataDTO covertEOToBaseData(CommonRateInfoEO commonRateInfoEO) {
        BaseDataDTO baseDataDTO = this.getBaseParam();
        baseDataDTO.setCode(commonRateInfoEO.getCode());
        baseDataDTO.put("rateschemecode", (Object)commonRateInfoEO.getRateSchemeCode());
        baseDataDTO.setName(commonRateInfoEO.getName());
        UUID uuid = StringUtils.isEmpty((String)commonRateInfoEO.getId()) ? UUID.randomUUID() : UUIDUtils.fromString36((String)commonRateInfoEO.getId());
        baseDataDTO.setId(uuid);
        baseDataDTO.put("datatime", (Object)commonRateInfoEO.getDataTime());
        baseDataDTO.put("sourcecurrencycode", (Object)commonRateInfoEO.getSourceCurrencyCode());
        baseDataDTO.put("targetcurrencycode", (Object)commonRateInfoEO.getTargetCurrencyCode());
        for (String key : commonRateInfoEO.getRateInfo().keySet()) {
            BigDecimal rate = commonRateInfoEO.getRateInfo().get(key);
            int decimal = CommonRateUtils.getRateValueFieldFractionDigits(key);
            BigDecimal result = rate.setScale(decimal, RoundingMode.HALF_UP);
            baseDataDTO.put("ratetype_" + key, (Object)result);
        }
        return baseDataDTO;
    }

    public BaseDataDTO getBaseParam() {
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName("MD_ENT_RATE");
        param.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        return param;
    }

    public List<CommonRateInfoEO> convertMapList2Eos(List<Map<String, Object>> mapList) {
        List<CommonRateInfoEO> sortEos = mapList.stream().map(rowData -> {
            CommonRateInfoEO eo = new CommonRateInfoEO();
            eo.setId(ConverterUtils.getAsString(rowData.get("ID")));
            eo.setCode(ConverterUtils.getAsString(rowData.get("CODE")));
            eo.setName(ConverterUtils.getAsString(rowData.get("NAME")));
            eo.setDataTime(ConverterUtils.getAsString(rowData.get("DATATIME")));
            eo.setSourceCurrencyCode(ConverterUtils.getAsString(rowData.get("SOURCECURRENCYCODE")));
            eo.setTargetCurrencyCode(ConverterUtils.getAsString(rowData.get("TARGETCURRENCYCODE")));
            HashMap<String, BigDecimal> rateInfo = new HashMap<String, BigDecimal>();
            for (String key : rowData.keySet()) {
                if (!key.startsWith("RATETYPE_")) continue;
                rateInfo.put(key.substring(key.indexOf(95) + 1), ConverterUtils.getAsBigDecimal(rowData.get(key)));
            }
            eo.setRateInfo(rateInfo);
            return eo;
        }).sorted((o1, o2) -> {
            int sourceCurrencyCodeAscValue = String.valueOf(o1.getSourceCurrencyCode()).compareTo(String.valueOf(o2.getSourceCurrencyCode()));
            if (sourceCurrencyCodeAscValue != 0) {
                return sourceCurrencyCodeAscValue;
            }
            int targetCurrencyCodeAscValue = String.valueOf(o1.getTargetCurrencyCode()).compareTo(String.valueOf(o2.getTargetCurrencyCode()));
            if (targetCurrencyCodeAscValue != 0) {
                return targetCurrencyCodeAscValue;
            }
            return o1.getDataTime().compareTo(o2.getDataTime());
        }).collect(Collectors.toList());
        return sortEos;
    }
}

