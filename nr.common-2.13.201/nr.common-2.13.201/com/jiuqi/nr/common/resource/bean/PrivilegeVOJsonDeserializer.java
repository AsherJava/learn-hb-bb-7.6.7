/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.np.authz2.privilege.Authority
 */
package com.jiuqi.nr.common.resource.bean;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.nr.common.resource.bean.PrivilegeVO;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PrivilegeVOJsonDeserializer
extends JsonDeserializer<PrivilegeVO> {
    public PrivilegeVO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String resourceId;
        PrivilegeVO impl = new PrivilegeVO();
        JsonNode node = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = node.get("ownerId");
        String ownerId = target != null && !target.isNull() ? target.asText() : null;
        target = node.get("isRole");
        Boolean isRole = target != null && !target.isNull() ? Boolean.valueOf(target.asBoolean()) : null;
        target = node.get("isDuty");
        Boolean isDuty = target != null && !target.isNull() ? Boolean.valueOf(target.asBoolean()) : null;
        target = node.get("resCategoryId");
        String resCategoryId = target != null && !target.isNull() ? target.asText() : null;
        target = node.get("authority");
        HashMap<String, Map<String, Authority>> authority = null;
        HashMap<String, Authority> privilegeMap = null;
        if (target != null && !target.isNull()) {
            authority = new HashMap<String, Map<String, Authority>>();
            Iterator resource = target.fieldNames();
            JsonNode privilegeNode = null;
            while (resource.hasNext()) {
                privilegeMap = new HashMap<String, Authority>();
                resourceId = (String)resource.next();
                privilegeNode = target.get(resourceId);
                Iterator privilege = privilegeNode.fieldNames();
                while (privilege.hasNext()) {
                    String privilegeId = (String)privilege.next();
                    JsonNode jsonNode = privilegeNode.get(privilegeId);
                    if (jsonNode == null || jsonNode.isNull()) continue;
                    privilegeMap.put(privilegeId, Authority.valueOf((String)jsonNode.asText()));
                }
                authority.put(resourceId, privilegeMap);
            }
        }
        HashMap<String, Integer> resourceMapPrivilegeType = null;
        target = node.get("resourceMapPrivilegeType");
        if (target != null && !target.isNull() && target.isObject()) {
            resourceMapPrivilegeType = new HashMap<String, Integer>(5);
            Iterator privilegeTypes = target.fieldNames();
            while (privilegeTypes.hasNext()) {
                resourceId = (String)privilegeTypes.next();
                JsonNode jsonNode = target.get(resourceId);
                resourceMapPrivilegeType.put(resourceId, jsonNode.asInt());
            }
        }
        impl.setOwnerId(ownerId);
        impl.setRole(isRole);
        impl.setDuty(isDuty);
        impl.setResCategoryId(resCategoryId);
        impl.setAuthority(authority);
        impl.setResourceMapPrivilegeType(resourceMapPrivilegeType);
        return impl;
    }
}

