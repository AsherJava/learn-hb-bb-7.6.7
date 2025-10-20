/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.gcreport.rate.impl.dao.impl;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.rate.impl.dao.CommonRateSchemeDao;
import com.jiuqi.gcreport.rate.impl.entity.CommonRateSchemeEO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommonRateSchemeDaoImpl
implements CommonRateSchemeDao {
    @Autowired
    private BaseDataClient baseDataClient;

    @Override
    public List<CommonRateSchemeEO> listAllRateScheme() {
        BaseDataDTO param = this.getBaseParam();
        return this.getRateScheme(param);
    }

    @Override
    public CommonRateSchemeEO getRateSchemeById(String rateSchemeId) {
        BaseDataDTO param = this.getBaseParam();
        param.setId(UUIDUtils.fromString36((String)rateSchemeId));
        if (!this.getRateScheme(param).isEmpty()) {
            return this.getRateScheme(param).get(0);
        }
        return null;
    }

    @Override
    public CommonRateSchemeEO getRateSchemeByCode(String code) {
        BaseDataDTO param = this.getBaseParam();
        param.setCode(code);
        if (!this.getRateScheme(param).isEmpty()) {
            return this.getRateScheme(param).get(0);
        }
        return null;
    }

    @Override
    public CommonRateSchemeEO getRateSchemeByTitle(String title) {
        BaseDataDTO param = this.getBaseParam();
        param.setName(title);
        if (!this.getRateScheme(param).isEmpty()) {
            return this.getRateScheme(param).get(0);
        }
        return null;
    }

    @Override
    public Boolean saveRateScheme(CommonRateSchemeEO rateSchemeEO) {
        CommonRateSchemeEO eo;
        BaseDataDTO dto = this.convertEOToBaseDataDTO(rateSchemeEO);
        R res = rateSchemeEO.getId() == null ? this.baseDataClient.add(dto) : ((eo = this.getRateSchemeById(rateSchemeEO.getId())) == null ? this.baseDataClient.add(dto) : this.baseDataClient.update(dto));
        if (res.getCode() == 1) {
            throw new RuntimeException(res.getMsg());
        }
        return true;
    }

    @Override
    public Boolean deleteRateScheme(String rateSchemeCode) {
        BaseDataDTO dto = this.getBaseParam();
        dto.setCode(rateSchemeCode);
        PageVO pageVO = this.baseDataClient.list(dto);
        if (pageVO.getTotal() == 0) {
            return true;
        }
        List baseDataDOS = pageVO.getRows();
        BaseDataBatchOptDTO removeDTO = new BaseDataBatchOptDTO();
        removeDTO.setDataList(baseDataDOS);
        removeDTO.addExtInfo("forceDelete", (Object)true);
        R res = this.baseDataClient.batchRemove(removeDTO);
        if (res.getCode() == 1) {
            throw new RuntimeException(res.getMsg());
        }
        return true;
    }

    public List<CommonRateSchemeEO> getRateScheme(BaseDataDTO param) {
        PageVO pageVO = this.baseDataClient.list(param);
        if (pageVO.getTotal() == 0) {
            return Collections.emptyList();
        }
        List baseDataDOS = pageVO.getRows();
        List<CommonRateSchemeEO> rateSchemeList = baseDataDOS.stream().map(v -> this.convertBaseDataToEO((BaseDataDO)v)).collect(Collectors.toList());
        return rateSchemeList;
    }

    public CommonRateSchemeEO convertBaseDataToEO(BaseDataDO baseDataDO) {
        CommonRateSchemeEO commonRateSchemeEO = new CommonRateSchemeEO();
        commonRateSchemeEO.setId(UUIDUtils.toString36((UUID)baseDataDO.getId()));
        commonRateSchemeEO.setCode(baseDataDO.getCode());
        commonRateSchemeEO.setName(baseDataDO.getName());
        commonRateSchemeEO.setPeriodType((String)baseDataDO.get((Object)"periodtype"));
        commonRateSchemeEO.setDescription((String)baseDataDO.get((Object)"description"));
        commonRateSchemeEO.setCreatetime(baseDataDO.getCreatetime());
        commonRateSchemeEO.setCreateuser(baseDataDO.getCreateuser());
        return commonRateSchemeEO;
    }

    public BaseDataDTO getBaseParam() {
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName("MD_RATESCHEME");
        param.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        return param;
    }

    public BaseDataDTO convertEOToBaseDataDTO(CommonRateSchemeEO commonRateSchemeEO) {
        BaseDataDTO dto = new BaseDataDTO();
        dto.setCode(commonRateSchemeEO.getCode() == null ? this.getRandomChar(7) : commonRateSchemeEO.getCode());
        UUID id = UUIDUtils.fromString36((String)commonRateSchemeEO.getId());
        dto.setId(id);
        dto.setTableName("MD_RATESCHEME");
        dto.put("description", (Object)commonRateSchemeEO.getDescription());
        dto.put("periodtype", (Object)commonRateSchemeEO.getPeriodType());
        dto.setName(commonRateSchemeEO.getName());
        return dto;
    }

    public String getRandomChar(int length) {
        String str = "";
        for (int i = 0; i < length; ++i) {
            str = str + (char)(Math.random() * 26.0 + 65.0);
        }
        return str;
    }
}

