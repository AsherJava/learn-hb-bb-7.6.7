/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.extend.OrgDataAuthTypeExtend
 */
package com.jiuqi.nr.entity.adapter.impl.org.auth;

import com.jiuqi.va.extend.OrgDataAuthTypeExtend;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;

public class OrgAuthTypeExtendImpl
implements OrgDataAuthTypeExtend {
    public Map<String, String> getAuthType() {
        return EnumSet.allOf(AuthTypeExtend.class).stream().collect(Collectors.toMap(Enum::name, AuthTypeExtend::getTitle));
    }

    public static enum AuthTypeExtend {
        PUBLISH("\u6570\u636e\u53d1\u5e03"),
        READ_UN_PUBLISH("\u67e5\u770b\u672a\u53d1\u5e03\u6570\u636e");

        private String title;

        private AuthTypeExtend(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }
    }
}

