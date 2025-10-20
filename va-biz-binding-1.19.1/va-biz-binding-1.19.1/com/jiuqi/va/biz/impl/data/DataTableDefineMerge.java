/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.data;

import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataFieldDefineMerge;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.value.NamedContainerImpl;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class DataTableDefineMerge {
    public static void merge(DataTableDefineImpl target, DataTableDefineImpl base) {
        if (target.getId() == null) {
            target.setId(base.getId());
        }
        if (target.getName() == null) {
            target.setName(base.getName());
        }
        if (target.getTitle() == null) {
            target.setTitle(base.getTitle());
        }
        if (base.getTableType() != null) {
            target.setTableType(base.getTableType());
        }
        if (base.getTableName() != null) {
            target.setTableName(base.getTableName());
        }
        if (base.getParentId() != null) {
            target.setParentId(base.getParentId());
        }
        if (base.isReadonly()) {
            target.setReadonly(base.isReadonly());
        }
        if (base.isRequired()) {
            target.setRequired(base.isRequired());
        }
        if (base.isSingle()) {
            target.setSingle(base.isSingle());
        }
        if (base.isFixed()) {
            target.setFixed(base.isFixed());
        }
        if (base.isEnableFilter()) {
            target.setFixed(base.isEnableFilter());
        }
        if (base.isFilterCondition()) {
            target.setFixed(base.isFilterCondition());
        }
        if (target.getInitRows() == 0) {
            target.setInitRows(base.getInitRows());
        }
        ((NamedContainerImpl)base.getFields()).stream().forEach(o -> {
            DataFieldDefineImpl field = (DataFieldDefineImpl)((NamedContainerImpl)target.getFields()).find(o.getName());
            if (field == null) {
                target.addField((DataFieldDefineImpl)o);
            } else {
                DataFieldDefineMerge.merge(field, o);
            }
        });
        final HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < base.getFieldList().size(); ++i) {
            map.put(base.getFieldList().get(i).getName(), i);
        }
        Collections.sort(target.getFieldList(), new Comparator<DataFieldDefineImpl>(){

            @Override
            public int compare(DataFieldDefineImpl o1, DataFieldDefineImpl o2) {
                int i1 = map.getOrDefault(o1.getName(), Integer.MAX_VALUE);
                int i2 = map.getOrDefault(o2.getName(), Integer.MAX_VALUE);
                return Integer.compare(i1, i2);
            }
        });
        target.setFieldList(target.getFieldList());
    }
}

