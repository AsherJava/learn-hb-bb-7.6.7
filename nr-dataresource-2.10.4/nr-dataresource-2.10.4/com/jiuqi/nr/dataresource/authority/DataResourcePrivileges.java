/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.InheritablePrivilege
 *  com.jiuqi.np.authz2.privilege.PrivilegeType
 */
package com.jiuqi.nr.dataresource.authority;

import com.jiuqi.np.authz2.privilege.InheritablePrivilege;
import com.jiuqi.np.authz2.privilege.PrivilegeType;
import org.springframework.stereotype.Component;

public class DataResourcePrivileges {

    @Component
    public static class DataResourceWritePrivilege
    extends BaseDataSetPrivilege {
        private static final long serialVersionUID = 6854436588635256512L;

        public String getId() {
            return "DataResource_write";
        }

        public String getName() {
            return "input";
        }

        public String getTitle() {
            return "\u7f16\u8f91";
        }
    }

    @Component
    public static class DataResourceReadPrivilege
    extends BaseDataSetPrivilege {
        private static final long serialVersionUID = 6754436588635256512L;

        public String getId() {
            return "DataResource_read";
        }

        public String getName() {
            return "read";
        }

        public String getTitle() {
            return "\u8bbf\u95ee";
        }
    }

    static abstract class BaseDataSetPrivilege
    extends InheritablePrivilege {
        private static final long serialVersionUID = -6959301011161244446L;

        BaseDataSetPrivilege() {
        }

        public String getInheritPathProviderId() {
            return "dataresource_privilege_inhrite_path_provider";
        }

        public int getType() {
            return PrivilegeType.OBJECT_INHERIT.getValue();
        }
    }
}

