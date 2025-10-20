/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.nvwa.glue.data.impl.AttributeDTO
 *  com.jiuqi.nvwa.glue.data.impl.GlueOrganizationDTO
 *  com.jiuqi.nvwa.glue.datacenter.impl.common.DataStatus
 *  com.jiuqi.va.domain.org.OrgDO
 */
package com.jiuqi.common.datasync.converter;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaAttributeDTO;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaOrganizationDTO;
import com.jiuqi.nvwa.glue.data.impl.AttributeDTO;
import com.jiuqi.nvwa.glue.data.impl.GlueOrganizationDTO;
import com.jiuqi.nvwa.glue.datacenter.impl.common.DataStatus;
import com.jiuqi.va.domain.org.OrgDO;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.ObjectUtils;

public class NvwaOrganizationConverter {
    public static DataSyncNvwaOrganizationDTO convertToDataSyncDTO(OrgDO org, List<String> zbNames) {
        List<DataSyncNvwaAttributeDTO> attributes;
        String lastModifyTime;
        DataSyncNvwaOrganizationDTO dataSyncNvwaOrganizationDTO = new DataSyncNvwaOrganizationDTO();
        dataSyncNvwaOrganizationDTO.setCode(org.getCode());
        dataSyncNvwaOrganizationDTO.setCreateTime(ObjectUtils.isEmpty(org.getCreatetime()) ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(org.getCreatetime()));
        dataSyncNvwaOrganizationDTO.setCreateUser(org.getCreateuser() == null ? "" : org.getCreateuser());
        dataSyncNvwaOrganizationDTO.setDataStatus(DataStatus.UPDATE.getValue());
        dataSyncNvwaOrganizationDTO.setId(org.getId() == null ? "" : org.getId().toString());
        dataSyncNvwaOrganizationDTO.setInvalidTime(org.getInvalidtime());
        BigDecimal bd = org.getVer();
        if (bd == null) {
            lastModifyTime = null;
        } else {
            long l = bd.setScale(0, 1).longValue();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            lastModifyTime = simpleDateFormat.format(new Date(l));
        }
        dataSyncNvwaOrganizationDTO.setLastModifyTime(lastModifyTime);
        dataSyncNvwaOrganizationDTO.setName(org.getName());
        dataSyncNvwaOrganizationDTO.setOrdinal(org.getOrdinal());
        dataSyncNvwaOrganizationDTO.setParentCode(org.getParentcode());
        dataSyncNvwaOrganizationDTO.setRecovery(org.getRecoveryflag() == null ? false : org.getRecoveryflag() != 0);
        dataSyncNvwaOrganizationDTO.setShortName(org.getShortname());
        dataSyncNvwaOrganizationDTO.setStop(org.getStopflag() == null ? false : org.getStopflag() != 0);
        dataSyncNvwaOrganizationDTO.setType(org.getCategoryname());
        dataSyncNvwaOrganizationDTO.setValidTime(org.getValidtime());
        if (zbNames == null) {
            attributes = Collections.emptyList();
        } else {
            attributes = new LinkedList();
            for (String zbName : zbNames) {
                if (ObjectUtils.isEmpty(zbName)) continue;
                Object value = org.get((Object)zbName.toLowerCase());
                if (value instanceof Date) {
                    value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(value);
                }
                attributes.add(new DataSyncNvwaAttributeDTO(zbName, ConverterUtils.getAsString((Object)value)));
            }
        }
        dataSyncNvwaOrganizationDTO.setAttributes(attributes);
        return dataSyncNvwaOrganizationDTO;
    }

    public static GlueOrganizationDTO convertGlueDTO(DataSyncNvwaOrganizationDTO daSyncNvwaOrganizationDTO) {
        GlueOrganizationDTO glueOrganizationDTO = new GlueOrganizationDTO();
        List<DataSyncNvwaAttributeDTO> attributes = daSyncNvwaOrganizationDTO.getAttributes();
        if (attributes != null) {
            List nvwaAttributeDTOs = attributes.stream().map(attribute -> new AttributeDTO(attribute.getName(), attribute.getValue())).collect(Collectors.toList());
            glueOrganizationDTO.setAttribute(nvwaAttributeDTOs);
        }
        glueOrganizationDTO.setCode(daSyncNvwaOrganizationDTO.getCode());
        glueOrganizationDTO.setCreateTime(daSyncNvwaOrganizationDTO.getCreateTime());
        glueOrganizationDTO.setCreateUser(daSyncNvwaOrganizationDTO.getCreateUser());
        glueOrganizationDTO.setDataStatus(daSyncNvwaOrganizationDTO.getDataStatus());
        glueOrganizationDTO.setId(daSyncNvwaOrganizationDTO.getId());
        glueOrganizationDTO.setInvalidTime(daSyncNvwaOrganizationDTO.getInvalidTime());
        glueOrganizationDTO.setLastModifyTime(daSyncNvwaOrganizationDTO.getLastModifyTime());
        glueOrganizationDTO.setName(daSyncNvwaOrganizationDTO.getName());
        glueOrganizationDTO.setOrdinal(daSyncNvwaOrganizationDTO.getOrdinal());
        glueOrganizationDTO.setParentCode(daSyncNvwaOrganizationDTO.getParentCode());
        glueOrganizationDTO.setRecovery(daSyncNvwaOrganizationDTO.isRecovery());
        glueOrganizationDTO.setShortName(daSyncNvwaOrganizationDTO.getShortName());
        glueOrganizationDTO.setStop(daSyncNvwaOrganizationDTO.isStop());
        glueOrganizationDTO.setType(daSyncNvwaOrganizationDTO.getType());
        glueOrganizationDTO.setValidTime(daSyncNvwaOrganizationDTO.getValidTime());
        return glueOrganizationDTO;
    }
}

