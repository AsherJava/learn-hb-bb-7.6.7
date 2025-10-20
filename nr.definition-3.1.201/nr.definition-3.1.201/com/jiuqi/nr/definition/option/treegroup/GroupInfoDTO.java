/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.treegroup;

import com.jiuqi.nr.definition.option.treegroup.GroupInfo;
import org.springframework.util.StringUtils;

public class GroupInfoDTO
implements GroupInfo {
    private String dimKey;
    private String dimFieldCode;
    private String relateEntityId;
    private String relateFieldCode;

    public GroupInfoDTO() {
        throw new UnsupportedOperationException();
    }

    public GroupInfoDTO(String first, String second) {
        if (StringUtils.hasLength(first) && first.indexOf(";") > -1) {
            this.initFirstLevelGroup(first.split(";"));
        }
        if (StringUtils.hasLength(second) && second.indexOf(";") > -1) {
            this.initSecondLevelGroup(second.split(";"));
        }
    }

    @Override
    public boolean isHasSecondLevelGroup() {
        return StringUtils.hasLength(this.relateEntityId) && StringUtils.hasLength(this.relateFieldCode);
    }

    @Override
    public boolean isFromDim() {
        if (StringUtils.hasLength(this.dimKey) && StringUtils.hasLength(this.relateEntityId)) {
            return this.dimKey.equals(this.relateEntityId);
        }
        return false;
    }

    private void initFirstLevelGroup(String[] strings) {
        this.dimKey = strings[0];
        this.dimFieldCode = strings[1];
    }

    private void initSecondLevelGroup(String[] strings) {
        this.relateEntityId = strings[0];
        this.relateFieldCode = strings[1];
    }

    @Override
    public String getDimKey() {
        return this.dimKey;
    }

    @Override
    public String getDimFieldCode() {
        return this.dimFieldCode;
    }

    @Override
    public String getRelateEntityId() {
        return this.relateEntityId;
    }

    @Override
    public String getRelateFieldCode() {
        return this.relateFieldCode;
    }
}

