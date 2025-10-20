/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.np.user.User
 *  com.jiuqi.nvwa.glue.data.impl.AttributeDTO
 *  com.jiuqi.nvwa.glue.data.impl.GlueUserDTO
 *  com.jiuqi.nvwa.glue.datacenter.impl.common.DataStatus
 */
package com.jiuqi.common.datasync.converter;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaAttributeDTO;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaUserDTO;
import com.jiuqi.np.user.User;
import com.jiuqi.nvwa.glue.data.impl.AttributeDTO;
import com.jiuqi.nvwa.glue.data.impl.GlueUserDTO;
import com.jiuqi.nvwa.glue.datacenter.impl.common.DataStatus;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class NvwaUserConverter {
    public static DataSyncNvwaUserDTO convertToDataSyncDTO(User user, List<DataSyncNvwaAttributeDTO> attributes, Collection<String> grantedOrgS) {
        DataSyncNvwaUserDTO dataSyncNvwaUserDTO = new DataSyncNvwaUserDTO();
        dataSyncNvwaUserDTO.setBirthday(user.getBirthday() == null ? "" : DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault()).format(user.getBirthday()));
        dataSyncNvwaUserDTO.setCertificateNumber(user.getCertificateNumber());
        dataSyncNvwaUserDTO.setCertificateType(user.getCertificateType() == null ? 0 : user.getCertificateType().getValue());
        dataSyncNvwaUserDTO.setDataStatus(DataStatus.UPDATE.getValue());
        dataSyncNvwaUserDTO.setEmail(user.getEmail());
        dataSyncNvwaUserDTO.setFullName(user.getFullname());
        dataSyncNvwaUserDTO.setId(user.getId());
        dataSyncNvwaUserDTO.setLastModifyTime(user.getModifyTime() == null ? null : DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneId.systemDefault()).format(user.getModifyTime()));
        dataSyncNvwaUserDTO.setNickName(user.getNickname());
        dataSyncNvwaUserDTO.setOrgCode(user.getOrgCode());
        dataSyncNvwaUserDTO.setSex(user.getSex() == null ? 0 : user.getSex().getValue());
        dataSyncNvwaUserDTO.setTelephone(user.getTelephone());
        dataSyncNvwaUserDTO.setUsername(user.getName());
        dataSyncNvwaUserDTO.setWechat(user.getWechat());
        dataSyncNvwaUserDTO.setEnabled(user.isEnabled());
        dataSyncNvwaUserDTO.setAttributes(attributes);
        if (!CollectionUtils.isEmpty(grantedOrgS)) {
            dataSyncNvwaUserDTO.setGrantedOrgS(new ArrayList<String>(grantedOrgS));
        } else {
            dataSyncNvwaUserDTO.setGrantedOrgS(new ArrayList<String>());
        }
        return dataSyncNvwaUserDTO;
    }

    public static GlueUserDTO convertGlueDTO(DataSyncNvwaUserDTO dataSyncNvwaUserDTO) {
        GlueUserDTO glueUserDTO = new GlueUserDTO();
        List<DataSyncNvwaAttributeDTO> attributes = dataSyncNvwaUserDTO.getAttributes();
        if (attributes != null) {
            List nvwaAttributeDTOs = attributes.stream().map(attribute -> new AttributeDTO(attribute.getName(), attribute.getValue())).collect(Collectors.toList());
            glueUserDTO.setAttribute(nvwaAttributeDTOs);
        }
        glueUserDTO.setBirthday(dataSyncNvwaUserDTO.getBirthday());
        glueUserDTO.setCertificateNumber(dataSyncNvwaUserDTO.getCertificateNumber());
        glueUserDTO.setCertificateType(dataSyncNvwaUserDTO.getCertificateType());
        glueUserDTO.setDataStatus(dataSyncNvwaUserDTO.getDataStatus());
        glueUserDTO.setEmail(dataSyncNvwaUserDTO.getEmail());
        glueUserDTO.setFullName(dataSyncNvwaUserDTO.getFullName());
        glueUserDTO.setId(dataSyncNvwaUserDTO.getId());
        glueUserDTO.setLastModifyTime(dataSyncNvwaUserDTO.getLastModifyTime());
        glueUserDTO.setNickName(dataSyncNvwaUserDTO.getNickName());
        glueUserDTO.setOrgCode(dataSyncNvwaUserDTO.getOrgCode());
        glueUserDTO.setSex(dataSyncNvwaUserDTO.getSex());
        glueUserDTO.setTelephone(dataSyncNvwaUserDTO.getTelephone());
        glueUserDTO.setUsername(dataSyncNvwaUserDTO.getUsername());
        glueUserDTO.setEnable(dataSyncNvwaUserDTO.isEnabled());
        glueUserDTO.setWechat(dataSyncNvwaUserDTO.getWechat());
        return glueUserDTO;
    }
}

