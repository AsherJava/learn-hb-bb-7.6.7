/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.extend.BaseDataAuthTypeExtend
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.auth;

import com.jiuqi.va.extend.BaseDataAuthTypeExtend;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BaseDataAuthTypeExtendImpl
implements BaseDataAuthTypeExtend {
    public Map<String, String> getAuthType() {
        return EnumSet.allOf(AuthTypeExtend.class).stream().collect(Collectors.toMap(Enum::name, AuthTypeExtend::getTitle));
    }

    public static enum AuthTypeExtend {
        SUBMIT("\u9001\u5ba1"),
        UPLOAD("\u4e0a\u62a5"),
        APPROVAL("\u5ba1\u6279"),
        PUBLISH("\u53d1\u5e03"),
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

