/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.InheritablePrivilege
 *  com.jiuqi.np.authz2.privilege.PrivilegeType
 */
package com.jiuqi.nr.query.dataset.Authority;

import com.jiuqi.np.authz2.privilege.InheritablePrivilege;
import com.jiuqi.np.authz2.privilege.PrivilegeType;
import org.springframework.stereotype.Component;

public class DataSetPrivileges {

    @Component
    public static class DataSetDeletePrivilege
    extends BaseDataSetPrivilege {
        private static final long serialVersionUID = 1654436588635256536L;

        public String getId() {
            return "dataset_model_resource_delete";
        }

        public String getName() {
            return "delete";
        }

        public String getTitle() {
            return "\u5220\u9664";
        }
    }

    @Component
    public static class DataSetWritePrivilege
    extends BaseDataSetPrivilege {
        private static final long serialVersionUID = 1654436588635256522L;

        public String getId() {
            return "dataset_model_resource_write";
        }

        public String getName() {
            return "write";
        }

        public String getTitle() {
            return "\u7f16\u8f91";
        }
    }

    @Component
    public static class DataSetReadPrivilege
    extends BaseDataSetPrivilege {
        private static final long serialVersionUID = 1654436588635256512L;

        public String getId() {
            return "dataset_model_resource_read";
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
        private static final long serialVersionUID = -2959301011161244446L;

        BaseDataSetPrivilege() {
        }

        public String getInheritPathProviderId() {
            return "dataset_privilege_inhrite_path_provider";
        }

        public int getType() {
            return PrivilegeType.OBJECT_INHERIT.getValue();
        }
    }
}

