/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.nr.query.block;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.query.block.LinkOpenMode;
import com.jiuqi.nr.query.block.LinkType;
import com.jiuqi.nr.query.deserializer.SuperLinkInforDeserializer;
import com.jiuqi.nr.query.serializer.SuperLinkInforSerializer;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@JsonSerialize(using=SuperLinkInforSerializer.class)
@JsonDeserialize(using=SuperLinkInforDeserializer.class)
public class SuperLinkInfor {
    private static final Logger log = LoggerFactory.getLogger(SuperLinkInfor.class);
    public static final String SUPERLINK_LINKTYPE = "type";
    public static final String SUPERLINK_TARGET = "target";
    public static final String SUPERLINK_PARAMETERS = "param";
    public static final String SUPERLINK_OPENMODE = "openmode";
    public static final String SUPERLINK_LINKNAME = "linkname";
    private LinkType linkType;
    private String target;
    private Map<String, String> parameters;
    private LinkOpenMode openMode;
    private String linkName;

    public void setLinkType(LinkType type) {
        this.linkType = type;
    }

    public LinkType getLinkType() {
        return this.linkType;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTarget() {
        return this.target;
    }

    public void setParameters(String map) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.parameters = map != null && map != "" ? (Map<Object, Object>)objectMapper.readValue(map, Map.class) : new HashMap<String, String>();
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public String getParametersStr() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this.parameters);
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public void setOpenMode(LinkOpenMode mode) {
        this.openMode = mode;
    }

    public LinkOpenMode getOpenMode() {
        return this.openMode;
    }

    public String getLinkName() {
        return this.linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }
}

