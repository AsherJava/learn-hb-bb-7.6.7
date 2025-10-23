/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.nr.period.util.JacksonUtils
 */
package com.jiuqi.nr.task.form.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.nr.period.util.JacksonUtils;
import java.util.Arrays;
import java.util.List;

public class ReverseModeUtils {
    public static String createGroupPath(String formScheme, String group, String form) {
        if (formScheme != null && group != null) {
            return JacksonUtils.objectToJson(Arrays.asList(formScheme, group, form));
        }
        return null;
    }

    public static GroupBuilder createGroupBuilder(String groupKey) {
        return new GroupBuilder(groupKey);
    }

    public static class GroupBuilder {
        private List<String> split;

        public GroupBuilder(String groupKey) {
            if (groupKey != null) {
                this.split = (List)JacksonUtils.jsonToObject((String)groupKey, (TypeReference)new TypeReference<List<String>>(){});
            }
        }

        public String getTitle(int i) {
            if (i < 3 && i >= 0) {
                return this.split == null ? null : this.split.get(i);
            }
            return null;
        }

        public boolean needCreateGroup() {
            return this.split != null && this.split.size() == 3;
        }
    }
}

