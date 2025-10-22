/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentity_ext.internal;

import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO;
import com.jiuqi.nr.dataentity_ext.internal.db.EntityDataDO;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.springframework.util.StringUtils;

public class EntityDataDTO
implements IEntityDataDTO {
    private EntityDataType entityDataType;
    private String[] path;
    private boolean leaf;
    private boolean root;
    private String key;
    private String code;
    private String title;
    private String parent;
    private BigDecimal order;
    private int childCount;
    private int allChildCount;

    public EntityDataDTO(EntityDataDO entityDataDO) {
        this.key = entityDataDO.getKey();
        this.code = entityDataDO.getCode();
        this.title = entityDataDO.getTitle();
        String doParent = entityDataDO.getParent();
        this.root = "-".equals(doParent);
        this.parent = this.root ? null : doParent;
        this.order = entityDataDO.getOrder();
        this.leaf = entityDataDO.getLeaf() == 1;
        this.childCount = entityDataDO.getChildCount();
        this.allChildCount = entityDataDO.getAllChildCount();
        this.entityDataType = EntityDataType.getByCode(entityDataDO.getType());
        String[] split = entityDataDO.getPath().split("/");
        ArrayList<String> list = new ArrayList<String>();
        for (String s : split) {
            if (!StringUtils.hasText(s)) continue;
            list.add(s);
        }
        if (!list.isEmpty()) {
            this.path = list.toArray(new String[0]);
        }
    }

    @Override
    public String[] getPath() {
        return this.path;
    }

    @Override
    public EntityDataType getType() {
        return this.entityDataType;
    }

    @Override
    public boolean isLeaf() {
        return this.leaf;
    }

    @Override
    public boolean isRoot() {
        return this.root;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getParent() {
        return this.parent;
    }

    @Override
    public BigDecimal getOrder() {
        return this.order;
    }

    public int getChildCount() {
        return this.childCount;
    }

    public int getAllChildCount() {
        return this.allChildCount;
    }
}

