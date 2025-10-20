/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.InheritablePrivilege
 *  com.jiuqi.np.authz2.privilege.PrivilegeType
 */
package com.jiuqi.nr.definition.auth.authz2;

import com.jiuqi.np.authz2.privilege.InheritablePrivilege;
import com.jiuqi.np.authz2.privilege.PrivilegeType;
import org.springframework.stereotype.Component;

public class TaskPrivileges {

    @Component
    public static class TaskReadUnPublishPrivilege
    extends BaseTaskPrivilege {
        private static final long serialVersionUID = -5525169116254143157L;

        public String getId() {
            return "task_privilege_read_unpublish";
        }

        public String getName() {
            return "readUnPublish";
        }

        public String getTitle() {
            return "\u8bbf\u95ee\u672a\u53d1\u5e03\u6570\u636e";
        }
    }

    @Component
    public static class TaskDataPublishPrivilege
    extends BaseTaskPrivilege {
        private static final long serialVersionUID = 2418772810989209730L;

        public String getId() {
            return "task_privilege_data_publish";
        }

        public String getName() {
            return "dataPublish";
        }

        public String getTitle() {
            return "\u6570\u636e\u53d1\u5e03";
        }
    }

    @Component
    public static class TaskModelingPrivilege
    extends BaseTaskPrivilege {
        private static final long serialVersionUID = 951401017124303237L;

        public String getId() {
            return "task_privilege_modeling";
        }

        public String getName() {
            return "modeling";
        }

        public String getTitle() {
            return "\u5efa\u6a21";
        }
    }

    @Component
    public static class TaskAuditPrivilege
    extends BaseTaskPrivilege {
        private static final long serialVersionUID = -8267372997241544816L;

        public String getId() {
            return "task_privilege_audit";
        }

        public String getName() {
            return "audit";
        }

        public String getTitle() {
            return "\u5ba1\u6279";
        }
    }

    @Component
    public static class TaskUploadPrivilege
    extends BaseTaskPrivilege {
        private static final long serialVersionUID = 5090538745035586175L;

        public String getId() {
            return "task_privilege_upload";
        }

        public String getName() {
            return "upload";
        }

        public String getTitle() {
            return "\u4e0a\u62a5";
        }
    }

    @Component
    public static class TaskSubmitPrivilege
    extends BaseTaskPrivilege {
        private static final long serialVersionUID = -1653153843401470036L;

        public String getId() {
            return "task_privilege_submit";
        }

        public String getName() {
            return "submit";
        }

        public String getTitle() {
            return "\u9001\u5ba1";
        }
    }

    @Component
    public static class TaskWritePrivilege
    extends BaseTaskPrivilege {
        private static final long serialVersionUID = -8187013482960114147L;

        public String getId() {
            return "task_privilege_write";
        }

        public String getName() {
            return "input";
        }

        public String getTitle() {
            return "\u6570\u636e\u5199";
        }
    }

    @Component
    public static class TaskReadPrivilege
    extends BaseTaskPrivilege {
        private static final long serialVersionUID = 1654436588635256596L;

        public String getId() {
            return "task_privilege_read";
        }

        public String getName() {
            return "read";
        }

        public String getTitle() {
            return "\u8bbf\u95ee";
        }
    }

    static abstract class BaseTaskPrivilege
    extends InheritablePrivilege {
        private static final long serialVersionUID = -2959301011161144444L;

        BaseTaskPrivilege() {
        }

        public String getInheritPathProviderId() {
            return "task_privilege_inhrite_path_provider";
        }

        public int getType() {
            return PrivilegeType.OBJECT_INHERIT.getValue();
        }
    }
}

