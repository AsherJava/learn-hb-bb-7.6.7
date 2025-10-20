/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.NameProvider
 */
package com.jiuqi.bi.database.sql.model.tables;

import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.fields.CoalesceField;
import com.jiuqi.bi.database.sql.model.fields.NVLField;
import com.jiuqi.bi.database.sql.model.fields.RefField;
import com.jiuqi.bi.database.sql.model.filters.IsNullFilter;
import com.jiuqi.bi.database.sql.model.tables.FieldMap;
import com.jiuqi.bi.database.sql.model.tables.JoinMode;
import com.jiuqi.bi.database.sql.model.tables.JoinedTable;
import com.jiuqi.bi.database.sql.model.tables.SubTable;
import com.jiuqi.bi.database.sql.model.tables.UnionMode;
import com.jiuqi.bi.database.sql.model.tables.UnionedTable;
import com.jiuqi.bi.util.NameProvider;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

final class FullJoinConvertor {
    private JoinedTable joinTable;
    private Map<ISQLField, ISQLField> subFieldMaps;
    private NameProvider nameProvider;
    private static final String UNION_PREFIX = "U";

    public FullJoinConvertor(JoinedTable joinTable) {
        this.joinTable = joinTable;
        this.nameProvider = new NameProvider();
        this.subFieldMaps = new HashMap<ISQLField, ISQLField>();
        this.nameProvider.useName(joinTable.mainTable().tableName());
        for (SubTable subTable : joinTable.subTables()) {
            this.nameProvider.useName(subTable.table().tableName());
        }
    }

    public int convert() throws SQLModelException {
        int count = 0;
        SubTable subTable = this.fetchFullJoinTable();
        while (subTable != null) {
            Map<ISQLField, ISQLField> unionMaps = this.fullJoinToUnion(subTable);
            this.mergeJoinMaps(unionMaps);
            ++count;
            subTable = this.fetchFullJoinTable();
        }
        return count;
    }

    private void mergeJoinMaps(Map<ISQLField, ISQLField> unionMaps) {
        if (this.subFieldMaps.isEmpty()) {
            this.subFieldMaps.putAll(unionMaps);
            return;
        }
        HashSet<ISQLField> prevFields = new HashSet<ISQLField>();
        for (Map.Entry<ISQLField, ISQLField> e : this.subFieldMaps.entrySet()) {
            ISQLField field = e.getValue();
            ISQLField newField = unionMaps.get(field);
            if (newField == null) continue;
            e.setValue(newField);
            prevFields.add(field);
        }
        for (Map.Entry<ISQLField, ISQLField> e : unionMaps.entrySet()) {
            if (prevFields.contains(e.getKey())) continue;
            this.subFieldMaps.put(e.getKey(), e.getValue());
        }
    }

    public Map<ISQLField, ISQLField> getSubFieldMaps() {
        return this.subFieldMaps;
    }

    private SubTable fetchFullJoinTable() throws SQLModelException {
        int fullJoinSize = 0;
        Iterator<SubTable> i = this.joinTable.subTables().iterator();
        while (i.hasNext()) {
            SubTable subTable = i.next();
            if (subTable.joinMode() != JoinMode.FULL) continue;
            ++fullJoinSize;
            if (!this.isUnionable(subTable)) continue;
            i.remove();
            return subTable;
        }
        if (fullJoinSize > 0) {
            throw new SQLModelException("\u8868\u5173\u8054\u5173\u7cfb\u590d\u6742\uff0c\u65e0\u6cd5\u8f6c\u6362\u4e3aUNION\u6a21\u5f0f\u3002");
        }
        return null;
    }

    private boolean isUnionable(SubTable subTable) {
        for (FieldMap map : subTable.fieldMaps()) {
            ISQLField mapField = map.left().owner() == subTable.table() ? map.right() : map.left();
            if (mapField.owner() == this.joinTable.mainTable()) continue;
            if (mapField instanceof NVLField) {
                for (ISQLField field : ((NVLField)mapField).fields()) {
                    if (field.owner() == this.joinTable.mainTable()) continue;
                    return false;
                }
                continue;
            }
            if (mapField instanceof CoalesceField) {
                for (ISQLField field : ((CoalesceField)mapField).fields()) {
                    if (field.owner() == this.joinTable.mainTable()) continue;
                    return false;
                }
                continue;
            }
            return false;
        }
        return true;
    }

    private Map<ISQLField, ISQLField> fullJoinToUnion(SubTable subTable) throws SQLModelException {
        HashMap<ISQLField, ISQLField> rawToJoinMaps = new HashMap<ISQLField, ISQLField>();
        HashMap<ISQLField, ISQLField> joinToUnionMaps = new HashMap<ISQLField, ISQLField>();
        JoinedTable leftJoinTable = this.createLeftJoinTable(subTable, rawToJoinMaps);
        JoinedTable rightJoinTable = this.createRightJoinTable(subTable);
        UnionedTable unionTable = this.createUnionTable(leftJoinTable, rightJoinTable, joinToUnionMaps);
        Map<ISQLField, ISQLField> maps = this.mergeUnionMaps(rawToJoinMaps, joinToUnionMaps);
        this.updateReferences(maps);
        this.joinTable.setMainTable(unionTable);
        return maps;
    }

    private JoinedTable createLeftJoinTable(SubTable subTable, Map<ISQLField, ISQLField> maps) {
        RefField refField;
        JoinedTable table = new JoinedTable();
        table.setMainTable(this.joinTable.mainTable());
        SubTable newSubTable = (SubTable)subTable.clone();
        newSubTable.setJoinMode(JoinMode.LEFT);
        table.subTables().add(newSubTable);
        NameProvider localNProvider = new NameProvider();
        for (ISQLField field : this.joinTable.mainTable().fields()) {
            if (!field.isVisible()) continue;
            refField = new RefField(table, field, localNProvider.uniqueOf(field.fieldName()));
            table.addField(refField);
            maps.put(field, refField);
        }
        for (ISQLField field : subTable.table().fields()) {
            if (!field.isVisible()) continue;
            refField = new RefField(table, field, localNProvider.uniqueOf(field.fieldName()));
            table.addField(refField);
            maps.put(field, refField);
        }
        return table;
    }

    private JoinedTable createRightJoinTable(SubTable subTable) {
        RefField refField;
        JoinedTable table = new JoinedTable();
        table.setMainTable(this.joinTable.mainTable());
        SubTable newSubTable = (SubTable)subTable.clone();
        newSubTable.setJoinMode(JoinMode.RIGHT);
        table.subTables().add(newSubTable);
        NameProvider localNProvider = new NameProvider();
        for (ISQLField field : this.joinTable.mainTable().fields()) {
            if (!field.isVisible()) continue;
            refField = new RefField(table, field, localNProvider.uniqueOf(field.fieldName()));
            table.addField(refField);
        }
        for (ISQLField field : subTable.table().fields()) {
            if (!field.isVisible()) continue;
            refField = new RefField(table, field, localNProvider.uniqueOf(field.fieldName()));
            table.addField(refField);
        }
        for (FieldMap map : newSubTable.fieldMaps()) {
            ISQLField leftField = map.left().owner() == this.joinTable.mainTable() ? map.left() : map.right();
            RefField refField2 = new RefField(table, leftField);
            table.whereFilters().add(new IsNullFilter(refField2));
        }
        return table;
    }

    private UnionedTable createUnionTable(JoinedTable leftJoinTable, JoinedTable rightJoinTable, Map<ISQLField, ISQLField> maps) {
        UnionedTable table = new UnionedTable(this.nameProvider.nameOf(UNION_PREFIX));
        table.setUnionMode(UnionMode.UNIONALL);
        table.tables().add(leftJoinTable);
        table.tables().add(rightJoinTable);
        for (ISQLField field : leftJoinTable.fields()) {
            ISQLField newField = table.createField(field.name(), field.alias());
            table.fields().add(newField);
            maps.put(field, newField);
        }
        return table;
    }

    private void updateReferences(Map<ISQLField, ISQLField> maps) throws SQLModelException {
        for (ISQLField field : this.joinTable.fields()) {
            this.updateField(field, maps);
        }
        for (SubTable subTable : this.joinTable.subTables()) {
            for (FieldMap map : subTable.fieldMaps()) {
                map.setLeft(this.updateField(map.left(), maps));
                map.setRight(this.updateField(map.right(), maps));
            }
        }
    }

    private ISQLField updateField(ISQLField field, Map<ISQLField, ISQLField> maps) {
        ISQLField newField = maps.get(field);
        if (newField != null) {
            return newField;
        }
        if (field instanceof RefField) {
            this.updateRefField((RefField)field, maps);
        } else if (field instanceof NVLField) {
            this.updateNVLField((NVLField)field, maps);
        } else if (field instanceof CoalesceField) {
            this.updateCoalesceField((CoalesceField)field, maps);
        }
        return field;
    }

    private Map<ISQLField, ISQLField> mergeUnionMaps(Map<ISQLField, ISQLField> rawToMidMaps, Map<ISQLField, ISQLField> midToFinalMaps) throws SQLModelException {
        HashMap<ISQLField, ISQLField> maps = new HashMap<ISQLField, ISQLField>(rawToMidMaps.size());
        for (Map.Entry<ISQLField, ISQLField> e : rawToMidMaps.entrySet()) {
            ISQLField rawField = e.getKey();
            ISQLField midField = e.getValue();
            ISQLField finalField = midToFinalMaps.get(midField);
            if (finalField == null) {
                throw new SQLModelException("\u7a0b\u5e8f\u9519\u8bef\uff0c\u65e0\u6cd5\u83b7\u53d6\u5b57\u6bb5\u8f6c\u5316\u540e\u7684\u6620\u5c04\u5173\u7cfb\uff1a" + rawField.toString());
            }
            maps.put(rawField, finalField);
        }
        return maps;
    }

    private void updateRefField(RefField field, Map<ISQLField, ISQLField> maps) {
        ISQLField newField = maps.get(field.field());
        if (newField != null) {
            field.setField(newField);
        }
    }

    private void updateNVLField(NVLField field, Map<ISQLField, ISQLField> maps) {
        ListIterator<ISQLField> i = field.fields().listIterator();
        while (i.hasNext()) {
            ISQLField rawField = i.next();
            ISQLField newField = maps.get(rawField);
            if (newField == null) continue;
            i.set(newField);
        }
    }

    private void updateCoalesceField(CoalesceField field, Map<ISQLField, ISQLField> maps) {
        ListIterator<ISQLField> i = field.fields().listIterator();
        while (i.hasNext()) {
            ISQLField rawField = i.next();
            ISQLField newField = maps.get(rawField);
            if (newField == null) continue;
            i.set(newField);
        }
    }
}

