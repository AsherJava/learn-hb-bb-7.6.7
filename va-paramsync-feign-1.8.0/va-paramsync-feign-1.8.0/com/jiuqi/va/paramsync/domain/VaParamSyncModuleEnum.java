/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.va.paramsync.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.va.paramsync.common.VaParamSyncModulesProvider;
import com.jiuqi.va.paramsync.domain.ModuleEnumDeserializer;
import com.jiuqi.va.paramsync.domain.ModuleEnumSerializer;
import com.jiuqi.va.paramsync.domain.VaParamSyncModuleServer;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.util.StringUtils;

@JsonSerialize(using=ModuleEnumSerializer.class)
@JsonDeserialize(using=ModuleEnumDeserializer.class)
public class VaParamSyncModuleEnum
implements Serializable {
    private static final long serialVersionUID = 1L;
    private static ConcurrentHashMap<String, VaParamSyncModuleEnum> cache = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, String> titleRef = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Integer> orderRef = new ConcurrentHashMap();
    @Deprecated
    public static final VaParamSyncModuleEnum ORGCATEGORY = VaParamSyncModuleEnum.valueOf("orgcategory");
    @Deprecated
    public static final VaParamSyncModuleEnum BASEDATA = VaParamSyncModuleEnum.valueOf("basedata");
    @Deprecated
    public static final VaParamSyncModuleEnum DATAMODEL = VaParamSyncModuleEnum.valueOf("datamodel");
    @Deprecated
    public static final VaParamSyncModuleEnum METADATA = VaParamSyncModuleEnum.valueOf("metadata");
    @Deprecated
    public static final VaParamSyncModuleEnum BILLREF = VaParamSyncModuleEnum.valueOf("billref");
    private String name;

    @JsonCreator
    public static VaParamSyncModuleEnum getInstance(@JsonProperty(value="name") String name) {
        return VaParamSyncModuleEnum.valueOf(name);
    }

    private VaParamSyncModuleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static VaParamSyncModuleEnum valueOf(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        if (cache.containsKey(name = name.toLowerCase())) {
            return cache.get(name);
        }
        if (name.matches("^[A-Za-z0-9_]{1,50}$")) {
            VaParamSyncModuleEnum biz2 = new VaParamSyncModuleEnum(name);
            cache.put(name, biz2);
            return biz2;
        }
        throw new RuntimeException("\u975e\u6cd5\u7684VaParamSyncModuleEnum\u5b57\u7b26");
    }

    public static VaParamSyncModuleEnum[] values() {
        return cache.values().toArray(new VaParamSyncModuleEnum[cache.size()]);
    }

    public String getTitle() {
        if (titleRef.containsKey(this.name)) {
            return titleRef.get(this.name);
        }
        VaParamSyncModuleServer server = VaParamSyncModulesProvider.getModuleServer(this.name);
        if (server != null && StringUtils.hasText(server.getTitle())) {
            titleRef.put(this.name, server.getTitle());
            return server.getTitle();
        }
        titleRef.put(this.name, this.name);
        return this.name.toUpperCase();
    }

    public int getOrder() {
        if (orderRef.containsKey(this.name)) {
            return orderRef.get(this.name);
        }
        VaParamSyncModuleServer server = VaParamSyncModulesProvider.getModuleServer(this.name);
        if (server != null) {
            orderRef.put(this.name, server.getOrder());
            return server.getOrder();
        }
        orderRef.put(this.name, 0);
        return 0;
    }

    public String toString() {
        return this.name.toUpperCase();
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        VaParamSyncModuleEnum other = (VaParamSyncModuleEnum)obj;
        return this.name.equals(other.name);
    }
}

