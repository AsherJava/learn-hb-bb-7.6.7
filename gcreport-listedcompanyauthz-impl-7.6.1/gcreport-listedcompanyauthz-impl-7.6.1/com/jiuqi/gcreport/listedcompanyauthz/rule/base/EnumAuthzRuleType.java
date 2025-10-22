/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgDO
 */
package com.jiuqi.gcreport.listedcompanyauthz.rule.base;

import com.jiuqi.va.domain.org.OrgDO;
import java.util.List;
import org.springframework.util.StringUtils;

public enum EnumAuthzRuleType {
    RULE_THIS(4, "\u672c\u7ea7\u5355\u4f4d"){

        @Override
        public boolean isAuthzOrg(OrgDO org, List<String> accept, List<String> exclude) {
            if (accept == null || accept.isEmpty()) {
                return false;
            }
            if (accept.contains(org.getCode())) {
                return 1.isAuthzByExcludeOrg(exclude, org.getParents());
            }
            return false;
        }
    }
    ,
    RULE_THIS_ALL(6, "\u672c\u7ea7+\u6240\u6709\u4e0b\u7ea7"){

        @Override
        public boolean isAuthzOrg(OrgDO org, List<String> accept, List<String> exclude) {
            if (accept == null || accept.isEmpty()) {
                return false;
            }
            String checkOrg = "/" + org.getParents() + "/";
            if (accept.stream().anyMatch(v -> "-".equals(v) || checkOrg.contains("/" + v + "/"))) {
                return 2.isAuthzByExcludeOrg(exclude, org.getParents());
            }
            return false;
        }
    }
    ,
    RULE_THIS_CHILD(5, "\u672c\u7ea7+\u76f4\u63a5\u4e0b\u7ea7"){

        @Override
        public boolean isAuthzOrg(OrgDO org, List<String> accept, List<String> exclude) {
            if (accept == null || accept.isEmpty()) {
                return false;
            }
            if (accept.contains(org.getParentcode()) || accept.contains(org.getCode())) {
                return 3.isAuthzByExcludeOrg(exclude, org.getParents());
            }
            return false;
        }
    }
    ,
    RULE_ALL(2, "\u6240\u6709\u4e0b\u7ea7"){

        @Override
        public boolean isAuthzOrg(OrgDO org, List<String> accept, List<String> exclude) {
            if (accept == null || accept.isEmpty()) {
                return false;
            }
            String checkOrg = "/" + org.getParents();
            if (accept.stream().anyMatch(v -> "-".equals(v) || checkOrg.contains("/" + v + "/"))) {
                return 4.isAuthzByExcludeOrg(exclude, org.getParents());
            }
            return false;
        }
    }
    ,
    RULE_CHILD(1, "\u76f4\u63a5\u4e0b\u7ea7"){

        @Override
        public boolean isAuthzOrg(OrgDO org, List<String> accept, List<String> exclude) {
            if (accept == null || accept.isEmpty()) {
                return false;
            }
            if (accept.contains(org.getParentcode())) {
                return 5.isAuthzByExcludeOrg(exclude, org.getParents());
            }
            return false;
        }
    }
    ,
    RULE_NONE(0, "\u65e0\u6743\u9650"){

        @Override
        public boolean isAuthzOrg(OrgDO org, List<String> accept, List<String> exclude) {
            return false;
        }
    };

    private final int id;
    private final String title;

    private EnumAuthzRuleType(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public abstract boolean isAuthzOrg(OrgDO var1, List<String> var2, List<String> var3);

    public static boolean isAuthzByExcludeOrg(List<String> exclude, String parents) {
        if (StringUtils.isEmpty(parents)) {
            return false;
        }
        if (exclude == null || exclude.isEmpty()) {
            return true;
        }
        String checkOrg = "/" + parents + "/";
        return !exclude.stream().anyMatch(v -> checkOrg.contains((CharSequence)v));
    }

    public static EnumAuthzRuleType findOf(boolean zj, boolean all, boolean child) {
        int i = (zj ? 4 : 0) + (all ? 2 : (child ? 1 : 0));
        for (EnumAuthzRuleType type : EnumAuthzRuleType.values()) {
            if (type.getId() != i) continue;
            return type;
        }
        return RULE_NONE;
    }
}

