/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.service.RoleService
 */
package com.jiuqi.nr.definition.option.internal;

import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.nr.definition.option.core.AffectedMode;
import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.core.RelationTaskOptionItem;
import com.jiuqi.nr.definition.option.impl.group.DataEntryGroup;
import com.jiuqi.nr.definition.option.internal.FormLock;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ForceFormUnLockRole
implements TaskOptionDefine {
    @Autowired
    private RoleService roleService;

    @Override
    public String getKey() {
        return "FORCE_FORM_UNLOCK_ROLE";
    }

    @Override
    public String getTitle() {
        return "\u5f3a\u5236\u89e3\u9501\u89d2\u8272";
    }

    @Override
    public String getDefaultValue() {
        return null;
    }

    @Override
    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_DROP_DOWN;
    }

    @Override
    public List<OptionItem> getOptionItems() {
        List allRoles = this.roleService.getAllRoles();
        ArrayList<OptionItem> result = new ArrayList<OptionItem>();
        for (final Role role : allRoles) {
            OptionItem item = new OptionItem(){

                @Override
                public String getTitle() {
                    return role.getTitle();
                }

                @Override
                public String getValue() {
                    return role.getId();
                }
            };
            result.add(item);
        }
        return result;
    }

    @Override
    public String getRegex() {
        return null;
    }

    @Override
    public String getPageTitle() {
        return new DataEntryGroup().getPageTitle();
    }

    @Override
    public String getGroupTitle() {
        return new DataEntryGroup().getTitle();
    }

    @Override
    public RelationTaskOptionItem getRelationTaskOptionItem() {
        RelationTaskOptionItem item = new RelationTaskOptionItem();
        item.setFromKey(new FormLock().getKey());
        item.setAffectedMode(AffectedMode.VISIBLE);
        ArrayList<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        item.setFromValues(list);
        return item;
    }

    @Override
    public double getOrder() {
        return 62.0;
    }
}

