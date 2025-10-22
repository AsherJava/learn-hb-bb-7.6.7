/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.auth.entity.authz2;

import com.jiuqi.np.dataengine.auth.entity.EntityOperation;
import com.jiuqi.np.dataengine.intf.IEntityRow;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

public class EntityAuthorityUtils {
    protected static final String PRIVILEGE_ID_SUFFIX_TAG = "_privilege";
    private static final String PRIVILEGE_ID_SUFFIX_READ = "_privilege_read";
    private static final String PRIVILEGE_ID_SUFFIX_WRITE = "_privilege_write";
    private static final String PRIVILEGE_ID_SUFFIX_DATAINPUT = "_privilege_input";
    private static final String PRIVILEGE_ID_SUFFIX_FLOW_SUBMIT = "_privilege_submit";
    private static final String PRIVILEGE_ID_SUFFIX_FLOW_UPLOAD = "_privilege_upload";
    private static final String PRIVILEGE_ID_SUFFIX_FLOW_AUDIT = "_privilege_audit";
    private static final String PRIVILEGE_ID_SUFFIX_PUBLISH_DATA = "_privilege_publish";
    private static final String PRIVILEGE_ID_SUFFIX_READ_UNPUBLISH = "_privilege_read_unpublish";
    public static final Integer PRIVILEGE_RULE_ORDER_ENTITYLINK = 99;
    protected static final String RULE_RES_ID_SUFFIX_TAG = "_rule_res";
    private static final String PRIVILEGE_RULE_RES_ID_SUFFIX_OWN = "_rule_res_own";
    private static final String PRIVILEGE_RULE_RES_ID_SUFFIX_CHILDREN = "_rule_res_children";
    private static final String PRIVILEGE_RULE_RES_ID_SUFFIX_ALLCHILDREN = "_rule_res_allchildren";
    private static final String TABLE_RES_ID_SUFFIX = "_res_table";

    public static String getReadPrivilegeId(String entityTableKey) {
        return EntityAuthorityUtils.generatePrivilegeId(entityTableKey, PRIVILEGE_ID_SUFFIX_READ);
    }

    public static final String getWritePrivilegeId(String entityTableKey) {
        return EntityAuthorityUtils.generatePrivilegeId(entityTableKey, PRIVILEGE_ID_SUFFIX_WRITE);
    }

    public static final String getEntityDataPrivilegeId(String entityTableKey, EntityOperation operation) {
        switch (operation) {
            case READ: {
                return EntityAuthorityUtils.generatePrivilegeId(entityTableKey, PRIVILEGE_ID_SUFFIX_READ);
            }
            case EDIT: {
                return EntityAuthorityUtils.generatePrivilegeId(entityTableKey, PRIVILEGE_ID_SUFFIX_WRITE);
            }
            case EDIT_FORM_DATA: {
                return EntityAuthorityUtils.generatePrivilegeId(entityTableKey, PRIVILEGE_ID_SUFFIX_DATAINPUT);
            }
            case SUBMIT_FORM_DATA: {
                return EntityAuthorityUtils.generatePrivilegeId(entityTableKey, PRIVILEGE_ID_SUFFIX_FLOW_SUBMIT);
            }
            case UPLOAD_FORM_DATA: {
                return EntityAuthorityUtils.generatePrivilegeId(entityTableKey, PRIVILEGE_ID_SUFFIX_FLOW_UPLOAD);
            }
            case AUDIT_FORM_DATA: {
                return EntityAuthorityUtils.generatePrivilegeId(entityTableKey, PRIVILEGE_ID_SUFFIX_FLOW_AUDIT);
            }
            case PUBLISH_FORM_DATA: {
                return EntityAuthorityUtils.generatePrivilegeId(entityTableKey, PRIVILEGE_ID_SUFFIX_PUBLISH_DATA);
            }
            case READ_UNPUBLISH_FORM_DATA: {
                return EntityAuthorityUtils.generatePrivilegeId(entityTableKey, PRIVILEGE_ID_SUFFIX_READ_UNPUBLISH);
            }
        }
        throw new UnsupportedOperationException("\u672a\u77e5\u7684\u4e3b\u4f53\u64cd\u4f5c\uff1a" + (Object)((Object)operation));
    }

    public static final String getPrivilegeIdByTemplate(String entityTableKey, String templatePrivilegeId) {
        int privilegeTagPosition = templatePrivilegeId.indexOf(PRIVILEGE_ID_SUFFIX_TAG);
        if (privilegeTagPosition < 0) {
            throw new UnsupportedOperationException("unrecognized templatePrivilegeId: " + templatePrivilegeId);
        }
        String privilegeIdSuffix = templatePrivilegeId.substring(privilegeTagPosition);
        return EntityAuthorityUtils.generatePrivilegeId(entityTableKey, privilegeIdSuffix);
    }

    protected static String generatePrivilegeId(String entityTableKey, String privilegeIdSuffix) {
        Assert.notNull((Object)entityTableKey, "'entityTableKey' must not be null.");
        return entityTableKey.concat(privilegeIdSuffix);
    }

    public static String getOwnPrivilegeRuleResourceId(String entityTableKey) {
        return EntityAuthorityUtils.generateResourceId(entityTableKey, PRIVILEGE_RULE_RES_ID_SUFFIX_OWN);
    }

    public static String getChildrenPrivilegeRuleResourceId(String entityTableKey) {
        return EntityAuthorityUtils.generateResourceId(entityTableKey, PRIVILEGE_RULE_RES_ID_SUFFIX_CHILDREN);
    }

    public static String getAllChildrenPrivilegeRuleResourceId(String entityTableKey) {
        return EntityAuthorityUtils.generateResourceId(entityTableKey, PRIVILEGE_RULE_RES_ID_SUFFIX_ALLCHILDREN);
    }

    public static boolean isRuleResource(String resourceId) {
        return resourceId != null && resourceId.contains(RULE_RES_ID_SUFFIX_TAG);
    }

    public static String getEntityTableResourceId(String entityTableKey) {
        return EntityAuthorityUtils.generateResourceId(entityTableKey, TABLE_RES_ID_SUFFIX);
    }

    public static boolean isEntityTableResourceId(@NonNull String resourceId) {
        return resourceId.contains(TABLE_RES_ID_SUFFIX);
    }

    public static String getEntityRowResourceId(IEntityRow entityRow) {
        Assert.notNull((Object)entityRow, "'entityRow' must not be null.");
        return entityRow.getEntityKeyData();
    }

    private static String generateResourceId(String entityTableKey, String resourceIdSuffix) {
        Assert.notNull((Object)entityTableKey, "'entityTableKey' must not be null.");
        return entityTableKey.concat(resourceIdSuffix);
    }
}

