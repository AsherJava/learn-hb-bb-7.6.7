/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 */
package com.jiuqi.nr.zb.scheme.internal.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.zb.scheme.common.PropDataType;
import com.jiuqi.nr.zb.scheme.common.VersionStatus;
import com.jiuqi.nr.zb.scheme.common.ZbApplyType;
import com.jiuqi.nr.zb.scheme.common.ZbDataType;
import com.jiuqi.nr.zb.scheme.common.ZbGatherType;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import com.jiuqi.nr.zb.scheme.utils.JsonUtils;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class ZbSchemeTransUtil {
    private static final Logger logger = LoggerFactory.getLogger(ZbSchemeTransUtil.class);

    public Instant transTimeStampByInstant(Timestamp time) {
        return time != null ? time.toInstant() : null;
    }

    public Timestamp transTimeStampByInstant(Instant date) {
        return date != null ? Timestamp.from(date) : null;
    }

    public Integer transDataType(ZbDataType type) {
        return type != null ? Integer.valueOf(type.getValue()) : null;
    }

    public ZbDataType transDataType(Integer value) {
        return value != null ? ZbDataType.valueOf(value) : null;
    }

    public Integer transPropDataType(PropDataType type) {
        return type != null ? Integer.valueOf(type.getValue()) : null;
    }

    public PropDataType transPropDataType(Integer value) {
        return value != null ? PropDataType.forValue(value) : null;
    }

    public Integer transVersionStatus(VersionStatus type) {
        return type != null ? Integer.valueOf(type.getValue()) : null;
    }

    public VersionStatus transVersionStatus(Integer value) {
        return value != null ? VersionStatus.forValue(value) : null;
    }

    public Integer transZbType(ZbType type) {
        return type != null ? Integer.valueOf(type.getValue()) : null;
    }

    public ZbType transZbType(Integer value) {
        return value != null ? ZbType.forValue(value) : null;
    }

    public Integer transZbDataType(ZbDataType type) {
        return type != null ? Integer.valueOf(type.getValue()) : null;
    }

    public ZbDataType transZbDataType(Integer value) {
        return value != null ? ZbDataType.valueOf(value) : null;
    }

    public Integer transZbApplyType(ZbApplyType type) {
        return type != null ? Integer.valueOf(type.getValue()) : null;
    }

    public ZbApplyType transZbApplyType(Integer value) {
        return value != null ? ZbApplyType.forValue(value) : null;
    }

    public Integer transZbGatherType(ZbGatherType type) {
        return type != null ? Integer.valueOf(type.getValue()) : null;
    }

    public ZbGatherType transZbGatherType(Integer value) {
        return value != null ? ZbGatherType.forValue(value) : null;
    }

    public String transFormatProperties(FormatProperties formatProperties) {
        if (formatProperties == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString((Object)formatProperties);
        }
        catch (JsonProcessingException e) {
            logger.error("\u6307\u6807\u663e\u793a\u683c\u5f0f\u8f6c\u6362\u5931\u8d25", e);
            return null;
        }
    }

    public FormatProperties transFormatProperties(String formatProperties) {
        if (StringUtils.hasLength(formatProperties)) {
            return null;
        }
        if (formatProperties.startsWith("@")) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (FormatProperties)mapper.readValue(formatProperties, FormatProperties.class);
        }
        catch (Exception e) {
            logger.error("\u6307\u6807\u663e\u793a\u683c\u5f0f\u8f6c\u6362\u5931\u8d25", e);
            return null;
        }
    }

    public static ArrayList transListJson(String value) {
        if (value == null) {
            return new ArrayList();
        }
        try {
            return JsonUtils.fromJson(value, ArrayList.class);
        }
        catch (Exception e) {
            logger.error("\u6307\u6807\u663e\u793a\u683c\u5f0f\u8f6c\u6362\u5931\u8d25", e);
            return new ArrayList();
        }
    }

    public static String transListJson(ArrayList value) {
        if (value == null) {
            return null;
        }
        try {
            return JsonUtils.toJson(value);
        }
        catch (Exception e) {
            logger.error("\u6307\u6807\u663e\u793a\u683c\u5f0f\u8f6c\u6362\u5931\u8d25", e);
            return null;
        }
    }

    public static Timestamp transTimeStamp2(String value) {
        if (value == null) {
            return null;
        }
        return Timestamp.valueOf(value);
    }

    public static Timestamp transTimeStamp2(Date date) {
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    public static Timestamp transTimeStamp2(Instant date) {
        if (date == null) {
            return null;
        }
        return Timestamp.from(date);
    }

    public static Instant transTimeStamp2(Timestamp value) {
        if (value == null) {
            return null;
        }
        return value.toInstant();
    }
}

