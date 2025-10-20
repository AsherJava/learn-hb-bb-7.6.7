/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.nvwa.glue.data.impl.AttributeDTO
 *  com.jiuqi.nvwa.glue.data.impl.GlueBaseDataDTO
 *  com.jiuqi.nvwa.glue.datacenter.impl.common.DataStatus
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 */
package com.jiuqi.common.datasync.converter;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaAttributeDTO;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaBaseDataDTO;
import com.jiuqi.nvwa.glue.data.impl.AttributeDTO;
import com.jiuqi.nvwa.glue.data.impl.GlueBaseDataDTO;
import com.jiuqi.nvwa.glue.datacenter.impl.common.DataStatus;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.ObjectUtils;

public class NvwaBaseDataConverter {
    public static DataSyncNvwaBaseDataDTO convertToDataSyncDTO(BaseDataDO baseData, String baseDataType) {
        String lastModifyTime;
        DataSyncNvwaBaseDataDTO dataSyncNvwaBaseDataDTO = new DataSyncNvwaBaseDataDTO();
        dataSyncNvwaBaseDataDTO.setId(baseData.getId() == null ? "" : baseData.getId().toString());
        dataSyncNvwaBaseDataDTO.setCode(baseData.getCode());
        dataSyncNvwaBaseDataDTO.setCreateTime(ObjectUtils.isEmpty(baseData.getCreatetime()) ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(baseData.getCreatetime()));
        dataSyncNvwaBaseDataDTO.setCreateUser(baseData.getCreateuser() == null ? "" : baseData.getCreateuser().toString());
        int dataStatus = baseData.getRecoveryflag() == null ? DataStatus.UPDATE.getValue() : (baseData.getRecoveryflag() == 1 ? DataStatus.DELETE.getValue() : DataStatus.UPDATE.getValue());
        dataSyncNvwaBaseDataDTO.setDataStatus(dataStatus);
        dataSyncNvwaBaseDataDTO.setId(baseData.getId() == null ? "" : baseData.getId().toString());
        dataSyncNvwaBaseDataDTO.setInvalidTime(baseData.getInvalidtime());
        BigDecimal bd = baseData.getVer();
        if (bd == null) {
            lastModifyTime = null;
        } else {
            long l = bd.setScale(0, 1).longValue();
            lastModifyTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(l));
        }
        dataSyncNvwaBaseDataDTO.setLastModifyTime(lastModifyTime);
        dataSyncNvwaBaseDataDTO.setName(baseData.getName());
        dataSyncNvwaBaseDataDTO.setOrdinal(baseData.getOrdinal());
        dataSyncNvwaBaseDataDTO.setOrgnizationCode(baseData.getUnitcode());
        dataSyncNvwaBaseDataDTO.setParentCode(baseData.getParentcode());
        dataSyncNvwaBaseDataDTO.setRecovery(baseData.getRecoveryflag() == null ? false : baseData.getRecoveryflag() != 0);
        dataSyncNvwaBaseDataDTO.setShortName(baseData.getShortname());
        dataSyncNvwaBaseDataDTO.setStop(baseData.getStopflag() == null ? false : baseData.getStopflag() != 0);
        dataSyncNvwaBaseDataDTO.setType(baseDataType);
        dataSyncNvwaBaseDataDTO.setValidTime(baseData.getValidtime());
        LinkedList<DataSyncNvwaAttributeDTO> attributes = new LinkedList<DataSyncNvwaAttributeDTO>();
        for (Map.Entry entry : baseData.entrySet()) {
            String key = (String)entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Date) {
                value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(value);
            }
            attributes.add(new DataSyncNvwaAttributeDTO(key, ConverterUtils.getAsString(value)));
        }
        dataSyncNvwaBaseDataDTO.setAttributes(attributes);
        return dataSyncNvwaBaseDataDTO;
    }

    public static GlueBaseDataDTO convertGlueDTO(DataSyncNvwaBaseDataDTO dataSyncNvwaBaseDataDTO) {
        GlueBaseDataDTO glueBaseDataDTO = new GlueBaseDataDTO();
        List<DataSyncNvwaAttributeDTO> attributes = dataSyncNvwaBaseDataDTO.getAttributes();
        if (attributes != null) {
            List nvwaAttributeDTOs = attributes.stream().map(nvwaAttributeDTO -> new AttributeDTO(nvwaAttributeDTO.getName(), nvwaAttributeDTO.getValue())).collect(Collectors.toList());
            glueBaseDataDTO.setAttribute(nvwaAttributeDTOs);
        }
        glueBaseDataDTO.setCode(dataSyncNvwaBaseDataDTO.getCode());
        glueBaseDataDTO.setCreateTime(dataSyncNvwaBaseDataDTO.getCreateTime());
        glueBaseDataDTO.setCreateUser(dataSyncNvwaBaseDataDTO.getCreateUser());
        glueBaseDataDTO.setDataStatus(dataSyncNvwaBaseDataDTO.getDataStatus());
        glueBaseDataDTO.setId(dataSyncNvwaBaseDataDTO.getId());
        glueBaseDataDTO.setInvalidTime(dataSyncNvwaBaseDataDTO.getInvalidTime());
        glueBaseDataDTO.setLastModifyTime(dataSyncNvwaBaseDataDTO.getLastModifyTime());
        glueBaseDataDTO.setName(dataSyncNvwaBaseDataDTO.getName());
        glueBaseDataDTO.setOrdinal(dataSyncNvwaBaseDataDTO.getOrdinal());
        glueBaseDataDTO.setOrgnizationCode(dataSyncNvwaBaseDataDTO.getOrgnizationCode());
        glueBaseDataDTO.setParentCode(dataSyncNvwaBaseDataDTO.getParentCode());
        glueBaseDataDTO.setRecovery(dataSyncNvwaBaseDataDTO.isRecovery());
        glueBaseDataDTO.setShortName(dataSyncNvwaBaseDataDTO.getShortName());
        glueBaseDataDTO.setStop(dataSyncNvwaBaseDataDTO.isStop());
        glueBaseDataDTO.setType(dataSyncNvwaBaseDataDTO.getType());
        glueBaseDataDTO.setValidTime(dataSyncNvwaBaseDataDTO.getValidTime());
        return glueBaseDataDTO;
    }
}

