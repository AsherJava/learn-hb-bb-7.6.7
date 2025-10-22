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
import com.jiuqi.nr.common.resource.bean.PrivilegeWebImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PrivilegeJsonDeserializer
extends JsonDeserializer<PrivilegeWebImpl> {
    public PrivilegeWebImpl deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String resourceId;
        PrivilegeWebImpl impl = new PrivilegeWebImpl();
        JsonNode node = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = node.get("ownerId");
        String ownerId = target != null && !target.isNull() ? target.asText() : null;
        target = node.get("isRole");
        Boolean isRole = target != null && !target.isNull() ? Boolean.valueOf(target.asBoolean()) : null;
        target = node.get("resCategoryId");
        String resCategoryId = target != null && !target.isNull() ? target.asText() : null;
        target = node.get("resourceIdMapPrivilegeIds");
        HashMap<String, List<String>> resourceIds = null;
        if (target != null && !target.isNull() && target.isObject()) {
            resourceIds = new HashMap<String, List<String>>(5);
            Iterator resource = target.fieldNames();
            while (resource.hasNext()) {
                String resourceId2 = (String)resource.next();
                JsonNode jsonNode = target.get(resourceId2);
                ArrayList<String> privilegeIds = new ArrayList<String>();
                for (JsonNode privilegeId : jsonNode) {
                    privilegeIds.add(privilegeId.asText());
                }
                resourceIds.put(resourceId2, privilegeIds);
            }
        }
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
        boolean rigorousEntity = (target = node.get("rigorousEntity")) != null && !target.isNull() && target.asBoolean();
        impl.setOwnerId(ownerId);
        impl.setIsRole(isRole);
        impl.setResCategoryId(resCategoryId);
        impl.setResourceIdMapPrivilegeIds(resourceIds);
        impl.setAuthority(authority);
        impl.setResourceMapPrivilegeType(resourceMapPrivilegeType);
        impl.setRigorousEntity(rigorousEntity);
        return impl;
    }
}

