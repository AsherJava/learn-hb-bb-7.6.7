/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.examine.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.examine.bean.MdInfoUpgradeRecordDO;
import com.jiuqi.nr.examine.facade.MdInfoDataUpgradeRecordDTO;
import com.jiuqi.nr.examine.facade.MdInfoUpgradeParamsDTO;
import java.util.Date;
import java.util.List;

public class MdInfoUpgradeRecordDTO {
    private String key = UUIDUtils.getKey();
    private String dataSchemeKey;
    private boolean upgradeSucceed;
    private String upgradeMessage;
    private Date upgradeTime = new Date();
    private MdInfoUpgradeParamsDTO upgradeParams;
    private List<MdInfoDataUpgradeRecordDTO> dataUpgradeRecords;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public boolean isUpgradeSucceed() {
        return this.upgradeSucceed;
    }

    public void setUpgradeSucceed(boolean upgradeSucceed) {
        this.upgradeSucceed = upgradeSucceed;
    }

    public String getUpgradeMessage() {
        return this.upgradeMessage;
    }

    public void setUpgradeMessage(String upgradeMessage) {
        this.upgradeMessage = upgradeMessage;
    }

    public Date getUpgradeTime() {
        return this.upgradeTime;
    }

    public void setUpgradeTime(Date upgradeTime) {
        this.upgradeTime = upgradeTime;
    }

    public MdInfoUpgradeParamsDTO getUpgradeParams() {
        return this.upgradeParams;
    }

    public void setUpgradeParams(MdInfoUpgradeParamsDTO upgradeParams) {
        this.upgradeParams = upgradeParams;
    }

    public List<MdInfoDataUpgradeRecordDTO> getDataUpgradeRecords() {
        return this.dataUpgradeRecords;
    }

    public void setDataUpgradeRecords(List<MdInfoDataUpgradeRecordDTO> dataUpgradeRecords) {
        this.dataUpgradeRecords = dataUpgradeRecords;
    }

    private static <T> String serialize(T obj) {
        if (null == obj) {
            return null;
        }
        ObjectMapper objectMapper = MdInfoUpgradeRecordDTO.getObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T deserialize(String data, Class<T> valueType) {
        if (null == data || data.isEmpty()) {
            return null;
        }
        ObjectMapper objectMapper = MdInfoUpgradeRecordDTO.getObjectMapper();
        try {
            return (T)objectMapper.readValue(data, valueType);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> List<T> deserializeForList(String data, Class<T> t) {
        if (null == data || data.isEmpty()) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaType valueType = objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{t});
        try {
            return (List)objectMapper.readValue(data, valueType);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    public MdInfoUpgradeRecordDO toDO() {
        MdInfoUpgradeRecordDO record = new MdInfoUpgradeRecordDO();
        record.setKey(this.getKey());
        record.setDataSchemeKey(this.getDataSchemeKey());
        record.setUpgradeSucceed(this.isUpgradeSucceed());
        record.setUpgradeTime(this.getUpgradeTime());
        record.setUpgradeParams(MdInfoUpgradeRecordDTO.serialize(this.getUpgradeParams()));
        record.setDataUpgradeRecord(MdInfoUpgradeRecordDTO.serialize(this.getDataUpgradeRecords()));
        return record;
    }

    public static MdInfoUpgradeRecordDTO fromDO(MdInfoUpgradeRecordDO record) {
        MdInfoUpgradeRecordDTO dto = new MdInfoUpgradeRecordDTO();
        dto.setKey(record.getKey());
        dto.setDataSchemeKey(record.getDataSchemeKey());
        dto.setUpgradeSucceed(record.isUpgradeSucceed());
        dto.setUpgradeMessage(record.getUpgradeMessage());
        dto.setUpgradeTime(record.getUpgradeTime());
        dto.setUpgradeParams(MdInfoUpgradeRecordDTO.deserialize(record.getUpgradeParams(), MdInfoUpgradeParamsDTO.class));
        dto.setDataUpgradeRecords(MdInfoUpgradeRecordDTO.deserializeForList(record.getDataUpgradeRecord(), MdInfoDataUpgradeRecordDTO.class));
        return dto;
    }
}

