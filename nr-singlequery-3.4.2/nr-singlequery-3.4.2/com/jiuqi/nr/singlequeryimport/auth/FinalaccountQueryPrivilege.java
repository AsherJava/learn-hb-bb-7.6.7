/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.InheritablePrivilege
 *  com.jiuqi.np.authz2.privilege.PrivilegeType
 */
package com.jiuqi.nr.singlequeryimport.auth;

import com.jiuqi.np.authz2.privilege.InheritablePrivilege;
import com.jiuqi.np.authz2.privilege.PrivilegeType;
import org.springframework.stereotype.Component;

public class FinalaccountQueryPrivilege {

    @Component
    public static class FinalaccountQueryACCREDITPrivilege
    extends InheritablePrivilege {
        private static final long serialVersionUID = -6077110037489018048L;

        public String getId() {
            return "finalaccountquery_auth_resource_accredit";
        }

        public String getName() {
            return "accredit";
        }

        public String getTitle() {
            return "\u6388\u6743";
        }

        public int getType() {
            return PrivilegeType.OBJECT_INHERIT.getValue();
        }

        public String getInheritPathProviderId() {
            return "finalaccountquery_inherit_path_provider";
        }
    }

    @Component
    public static class FinalaccountQueryWritePrivilege
    extends InheritablePrivilege {
        private static final long serialVersionUID = -6077110037489018048L;

        public String getId() {
            return "finalaccountquery_auth_resource_write";
        }

        public String getName() {
            return "write";
        }

        public String getTitle() {
            return "\u7f16\u8f91";
        }

        public int getType() {
            return PrivilegeType.OBJECT_INHERIT.getValue();
        }

        public String getInheritPathProviderId() {
            return "finalaccountquery_inherit_path_provider";
        }
    }

    @Component
    public static class FinalaccountQueryReadPrivilege
    extends InheritablePrivilege {
        private static final long serialVersionUID = 4386367473662906749L;

        public String getId() {
            return "finalaccountquery_auth_resource_read";
        }

        public String getName() {
            return "read";
        }

        public String getTitle() {
            return "\u8bbf\u95ee";
        }

        public int getType() {
            return PrivilegeType.OBJECT_INHERIT.getValue();
        }

        public String getInheritPathProviderId() {
            return "finalaccountquery_inherit_path_provider";
        }
    }
}

