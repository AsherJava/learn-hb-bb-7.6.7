/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException
 */
package com.jiuqi.nr.workflow2.actors.settings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jiuqi.nr.workflow2.actors.settings.SettingParser;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import java.util.Set;

public class GivenRoleSettings {
    private Set<String> roleIds;

    public Set<String> getRoleIds() {
        return this.roleIds;
    }

    public void setRoleIds(Set<String> roleIds) {
        this.roleIds = roleIds;
    }

    private GivenRoleSettings() {
    }

    public static GivenRoleSettings parseFromString(String settingsText) {
        try {
            return (GivenRoleSettings)SettingParser.MAPPER.readValue(settingsText, GivenRoleSettings.class);
        }
        catch (JsonMappingException e) {
            throw new ProcessRuntimeException(null, "\u53c2\u4e0e\u8005\u7b56\u7565\u8bbe\u7f6e\u89e3\u6790\u9519\u8bef\u3002", (Throwable)e);
        }
        catch (JsonProcessingException e) {
            throw new ProcessRuntimeException(null, "\u53c2\u4e0e\u8005\u7b56\u7565\u8bbe\u7f6e\u89e3\u6790\u9519\u8bef\u3002", (Throwable)e);
        }
    }
}

