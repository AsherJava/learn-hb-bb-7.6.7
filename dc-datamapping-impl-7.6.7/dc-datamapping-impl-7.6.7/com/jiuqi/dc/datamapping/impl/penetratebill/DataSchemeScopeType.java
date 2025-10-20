/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.base.common.penetratebill.IScopeType
 *  com.jiuqi.dc.base.common.vo.BaseDataShowVO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  com.jiuqi.dc.datamapping.client.enums.DataRefFilterType
 *  com.jiuqi.dc.datamapping.client.vo.DataRefListVO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 */
package com.jiuqi.dc.datamapping.impl.penetratebill;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.common.penetratebill.IScopeType;
import com.jiuqi.dc.base.common.vo.BaseDataShowVO;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.client.enums.DataRefFilterType;
import com.jiuqi.dc.datamapping.client.vo.DataRefListVO;
import com.jiuqi.dc.datamapping.impl.gather.impl.DataRefConfigureServiceGather;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSchemeScopeType
implements IScopeType {
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private DataRefConfigureServiceGather dataRefConfigureServiceGather;

    public String getCode() {
        return "DATASCHEME";
    }

    public String getName() {
        return "\u6570\u636e\u6620\u5c04\u65b9\u6848";
    }

    public String getOrdinal() {
        return "10";
    }

    public boolean match(String unitCode, String scopeValue) {
        DataSchemeDTO dataSchemeDTO;
        BaseDataShowVO data = (BaseDataShowVO)JsonUtils.readValue((String)scopeValue, (TypeReference)new TypeReference<BaseDataShowVO>(){});
        try {
            dataSchemeDTO = this.dataSchemeService.getByCode(data.getCode());
        }
        catch (BusinessRuntimeException e) {
            throw new BusinessRuntimeException(String.format("\u8054\u67e5\u5355\u636e\u65b9\u6848\u4e0b\u9002\u7528\u8303\u56f4\u3010\u6570\u636e\u6620\u5c04\u65b9\u6848-%1$s %2$s\u3011\u5df2\u7ecf\u88ab\u5220\u9664\uff0c\u8bf7\u8fdb\u884c\u66f4\u6539", data.getCode(), data.getTitle()));
        }
        DataRefListDTO dataRefListDTO = new DataRefListDTO();
        dataRefListDTO.setDataSchemeCode(dataSchemeDTO.getCode());
        dataRefListDTO.setTableName("MD_ORG");
        dataRefListDTO.setFilterType(DataRefFilterType.HASREF.getCode());
        DataRefListVO refList = this.dataRefConfigureServiceGather.getDataRefConfigureServiceBySourceDataType(dataSchemeDTO.getSourceDataType()).list(dataRefListDTO);
        List unitCodes = refList.getPageVo().getRows().stream().map(DataRefDTO::getCode).collect(Collectors.toList());
        return unitCodes.contains(unitCode);
    }
}

