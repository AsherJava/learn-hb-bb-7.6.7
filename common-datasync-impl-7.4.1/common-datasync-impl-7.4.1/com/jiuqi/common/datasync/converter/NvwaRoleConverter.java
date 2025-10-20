/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.nvwa.glue.data.impl.AttributeDTO
 *  com.jiuqi.nvwa.glue.data.impl.GlueRoleDTO
 *  com.jiuqi.nvwa.glue.datacenter.impl.common.DataStatus
 */
package com.jiuqi.common.datasync.converter;

import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaAttributeDTO;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaRoleDTO;
import com.jiuqi.np.authz2.Role;
import com.jiuqi.nvwa.glue.data.impl.AttributeDTO;
import com.jiuqi.nvwa.glue.data.impl.GlueRoleDTO;
import com.jiuqi.nvwa.glue.datacenter.impl.common.DataStatus;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NvwaRoleConverter {
    public static DataSyncNvwaRoleDTO convertToDataSyncDTO(Role role) {
        DataSyncNvwaRoleDTO dataSyncNvwaRoleDTO = new DataSyncNvwaRoleDTO();
        dataSyncNvwaRoleDTO.setDataStatus(DataStatus.UPDATE.getValue());
        dataSyncNvwaRoleDTO.setDescription(role.getDescription());
        dataSyncNvwaRoleDTO.setId(role.getId());
        dataSyncNvwaRoleDTO.setLastModifyTime(role.getLastModifyTime() == null ? null : DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneId.systemDefault()).format(role.getLastModifyTime()));
        dataSyncNvwaRoleDTO.setName(role.getName());
        dataSyncNvwaRoleDTO.setTitle(role.getTitle());
        dataSyncNvwaRoleDTO.setAttributes(Collections.emptyList());
        return dataSyncNvwaRoleDTO;
    }

    public static GlueRoleDTO convertGlueDTO(DataSyncNvwaRoleDTO dataSyncNvwaRoleDTO) {
        GlueRoleDTO glueRoleDTO = new GlueRoleDTO();
        List<DataSyncNvwaAttributeDTO> attributes = dataSyncNvwaRoleDTO.getAttributes();
        if (attributes != null) {
            List nvwaAttributeDTOs = attributes.stream().map(attribute -> new AttributeDTO(attribute.getName(), attribute.getValue())).collect(Collectors.toList());
            glueRoleDTO.setAttribute(nvwaAttributeDTOs);
        }
        glueRoleDTO.setDataStatus(dataSyncNvwaRoleDTO.getDataStatus());
        glueRoleDTO.setDescription(dataSyncNvwaRoleDTO.getDescription());
        glueRoleDTO.setId(dataSyncNvwaRoleDTO.getId());
        glueRoleDTO.setLastModifyTime(dataSyncNvwaRoleDTO.getLastModifyTime());
        glueRoleDTO.setName(dataSyncNvwaRoleDTO.getName());
        glueRoleDTO.setTitle(dataSyncNvwaRoleDTO.getTitle());
        return glueRoleDTO;
    }
}

