/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 */
package com.jiuqi.nr.entity.adapter.impl.enumdata;

import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class EnumDataResultSet
extends EntityResultSet {
    private final List<EnumDataDO> list;
    private final List<String> keys;

    public EnumDataResultSet(List<EnumDataDO> list) {
        super(list.size());
        this.list = list;
        this.keys = list.stream().map(EnumDataDO::getVal).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllKeys() {
        return this.keys;
    }

    @Override
    protected Object getColumnObject(int index, String columnCode) {
        EnumDataDO enumDataDO = this.list.get(index);
        switch (columnCode.toUpperCase(Locale.ROOT)) {
            case "ID": {
                return enumDataDO.getId();
            }
            case "CODE": {
                return enumDataDO.getVal();
            }
            case "TITLE": {
                return enumDataDO.getTitle();
            }
            case "SHORTNAME": {
                return enumDataDO.getTitle();
            }
        }
        return null;
    }

    @Override
    protected String getKey(int index) {
        EnumDataDO enumDataDO = this.list.get(index);
        return enumDataDO.getVal();
    }

    @Override
    protected String getCode(int index) {
        EnumDataDO enumDataDO = this.list.get(index);
        return enumDataDO.getVal();
    }

    @Override
    protected String getTitle(int index) {
        EnumDataDO enumDataDO = this.list.get(index);
        return enumDataDO.getTitle();
    }

    @Override
    protected String getParent(int index) {
        return null;
    }

    @Override
    protected Object getOrder(int index) {
        EnumDataDO enumDataDO = this.list.get(index);
        return enumDataDO.getOrdernum();
    }

    @Override
    protected String[] getParents(int index) {
        return new String[0];
    }

    @Override
    public int append(EntityResultSet rs) {
        return 0;
    }
}

