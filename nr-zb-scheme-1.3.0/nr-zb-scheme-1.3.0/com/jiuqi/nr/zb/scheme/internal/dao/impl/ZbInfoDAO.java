/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.shiro.util.Assert
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.zb.scheme.internal.dao.impl;

import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.exception.BeanParaException;
import com.jiuqi.nr.zb.scheme.internal.anno.DBAnno;
import com.jiuqi.nr.zb.scheme.internal.dao.IPropInfoDao;
import com.jiuqi.nr.zb.scheme.internal.dao.IZbInfoDao;
import com.jiuqi.nr.zb.scheme.internal.db.BaseDao;
import com.jiuqi.nr.zb.scheme.internal.db.ExtDBField;
import com.jiuqi.nr.zb.scheme.internal.db.ZbSchemeTransUtil;
import com.jiuqi.nr.zb.scheme.internal.entity.PropInfoDO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbInfoDO;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Repository
public class ZbInfoDAO
extends BaseDao
implements IZbInfoDao {
    private static final Logger log = LoggerFactory.getLogger(ZbInfoDAO.class);
    @Autowired
    private IPropInfoDao propInfoDao;

    public Class<ZbInfoDO> getClz() {
        return ZbInfoDO.class;
    }

    @Override
    public Class<?> getExternalTransCls() {
        return ZbSchemeTransUtil.class;
    }

    private List<PropInfo> getPropInfos() {
        return new ArrayList<PropInfo>();
    }

    @Override
    public ZbInfoDO getByKey(String key) {
        List<PropInfo> propInfos = this.getPropInfos();
        return this.getByKey(key, propInfos);
    }

    @Override
    public ZbInfoDO getByKey(String key, List<PropInfo> propInfos) {
        List<ZbInfoDO> list;
        if (CollectionUtils.isEmpty(propInfos)) {
            super.getByKey(key, ZbInfoDO.class);
        }
        if (CollectionUtils.isEmpty(list = this.list("ZB_KEY=? ", new Object[]{key}, propInfos))) {
            return null;
        }
        return list.stream().findFirst().orElse(null);
    }

    @Override
    public ZbInfoDO getByVersionAndCode(String version, String code) {
        return this.getByVersionAndCode(version, code, this.getPropInfos());
    }

    @Override
    public ZbInfoDO getByVersionAndCode(String version, String code, List<PropInfo> propInfos) {
        if (CollectionUtils.isEmpty(propInfos)) {
            return this.getBy("ZB_VERSION=? AND ZB_CODE=? ", new Object[]{version, code}, ZbInfoDO.class);
        }
        List<ZbInfoDO> list = this.list("ZB_VERSION=? AND ZB_CODE=? ", new Object[]{version, code}, propInfos);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.stream().findFirst().orElse(null);
    }

    @Override
    public List<ZbInfoDO> listByParent(String parentKey) {
        return this.listByParent(parentKey, this.getPropInfos());
    }

    @Override
    public List<ZbInfoDO> listByParent(String parentKey, List<PropInfo> propInfos) {
        return this.list("ZB_PARENT=? ", new Object[]{parentKey}, propInfos);
    }

    @Override
    public List<ZbInfoDO> listByKeys(List<String> keys) {
        return this.listByKeys(keys, this.getPropInfos());
    }

    @Override
    public List<ZbInfoDO> listByKeys(List<String> keys, List<PropInfo> propInfos) {
        StringBuilder sbr = new StringBuilder();
        sbr.setLength(0);
        sbr.append("ZB_KEY").append(" IN (").append(keys.stream().map(s -> "?").collect(Collectors.joining(","))).append(")");
        return this.list(sbr.toString(), keys.toArray(), propInfos);
    }

    @Override
    public List<ZbInfoDO> listBySchemeAndVersion(String schemeKey, String versionKey) {
        return this.listBySchemeAndVersion(schemeKey, versionKey, this.getPropInfos());
    }

    @Override
    public List<ZbInfoDO> listBySchemeAndVersion(String schemeKey, String versionKey, List<PropInfo> propInfos) {
        return this.list(this.getWhereSql("ZB_SCHEME_KEY", "ZB_VERSION"), new Object[]{schemeKey, versionKey}, propInfos);
    }

    private String getWhereSql(String ... fields) {
        StringBuilder builder = new StringBuilder();
        if (fields != null) {
            for (int i = 0; i < fields.length; ++i) {
                builder.append(fields[i]).append("=? ");
                if (i >= fields.length - 1) continue;
                builder.append("AND ");
            }
        }
        return builder.toString();
    }

    @Override
    public List<ZbInfoDO> listByVersion(String versionKey) {
        return this.listByVersion(versionKey, this.getPropInfos());
    }

    @Override
    public List<ZbInfoDO> listByVersion(String versionKey, List<PropInfo> propInfos) {
        return this.list(this.getWhereSql("ZB_VERSION"), new Object[]{versionKey}, propInfos);
    }

    @Override
    public List<ZbInfoDO> listBySchemeAndCode(String schemeKey, String code) {
        return this.listBySchemeAndCode(schemeKey, code, this.getPropInfos());
    }

    @Override
    public List<ZbInfoDO> listBySchemeAndCode(String schemeKey, String code, List<PropInfo> propInfos) {
        return this.list(this.getWhereSql("ZB_SCHEME_KEY", "ZB_CODE"), new Object[]{schemeKey, code}, propInfos);
    }

    @Override
    public List<ZbInfoDO> listByVersionAndCode(String versionKey, List<String> codes) {
        return this.listByVersionAndCode(versionKey, codes, this.getPropInfos());
    }

    @Override
    public List<ZbInfoDO> listByVersionAndCode(String versionKey, List<String> codes, List<PropInfo> propInfos) {
        if (versionKey == null || codes == null || codes.isEmpty()) {
            log.debug("\u53c2\u6570\u5f02\u5e38, versionKey={}, codes={}", (Object)versionKey, (Object)codes);
            throw new BeanParaException("\u53c2\u6570\u5f02\u5e38");
        }
        this.buildMethod();
        String sbr = this.selectSQL + " WHERE " + "ZB_VERSION" + " =? AND " + "ZB_CODE" + " IN (" + codes.stream().map(s -> "?").collect(Collectors.joining(",")) + ")";
        ArrayList<String> args = new ArrayList<String>(codes.size() + 1);
        args.add(versionKey);
        args.addAll(codes);
        return this.list(sbr, args.toArray(), propInfos);
    }

    public List<ZbInfoDO> list(String sqlWhere, Object[] args, List<PropInfo> propInfos) {
        if (CollectionUtils.isEmpty(propInfos)) {
            return super.list(sqlWhere, args, ZbInfoDO.class);
        }
        Map propInfoMap = propInfos.stream().collect(Collectors.toMap(PropInfo::getFieldName, f -> f, (v1, v2) -> v1, LinkedHashMap::new));
        List<ZbInfoDO> ret = new ArrayList<ZbInfoDO>();
        try {
            String sql;
            this.buildMethod();
            StringBuilder selectSQL = new StringBuilder(this.selectSQL.substring(0, this.selectSQL.lastIndexOf("from")));
            propInfoMap.keySet().forEach(k -> selectSQL.append(",").append((String)k));
            selectSQL.append(" from ").append(this.tablename).append(" ");
            String order = "";
            if (!StringUtils.isEmpty(this.orderFieldName)) {
                Field orderField = (Field)this.fieldMap.get(this.orderFieldName);
                DBAnno.DBField dbField = orderField.getAnnotation(DBAnno.DBField.class);
                order = " order by " + dbField.dbField().toUpperCase();
            }
            if (sqlWhere == null || sqlWhere.equals("")) {
                sql = selectSQL + order;
                ret = this.jdbcTemplate.query(con -> con.prepareStatement(sql), this.getRowMapper(propInfoMap));
            } else {
                sql = selectSQL.append(" where ").append(sqlWhere).append(" ").append(order).toString();
                ret = this.jdbcTemplate.query(con -> {
                    PreparedStatement statement = con.prepareStatement(sql);
                    if (args != null) {
                        for (int i = 0; i < args.length; ++i) {
                            statement.setObject(i + 1, args[i]);
                        }
                    }
                    return statement;
                }, this.getRowMapper(propInfoMap));
            }
        }
        catch (Exception e) {
            super.throwBeanParaException(e);
        }
        return ret;
    }

    protected <T> RowMapper<T> getRowMapper(Map<String, PropInfo> propInfoMap) {
        return (rs, rowNum) -> {
            ZbInfoDO infoDO = new ZbInfoDO();
            ResultSetMetaData metaData = rs.getMetaData();
            ArrayList<PropInfo> ext = new ArrayList<PropInfo>();
            for (int i = 0; i < metaData.getColumnCount(); ++i) {
                Field valueField;
                String name = metaData.getColumnName(i + 1);
                if (this.dbFieldMap.containsKey(name)) {
                    super.readRecord(name, rs, infoDO);
                    continue;
                }
                if (!propInfoMap.containsKey(name)) continue;
                PropInfo propInfo = (PropInfo)propInfoMap.get(name);
                PropInfoDO value = PropInfoDO.valueOf(propInfo);
                ext.add(value);
                try {
                    valueField = value.getClass().getDeclaredField("value");
                }
                catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
                super.readRecord(valueField, ExtDBField.valueOf(value), rs, value);
            }
            infoDO.setExtProp(ext);
            return infoDO;
        };
    }

    @Override
    public void insert(ZbInfoDO zbInfoDO) throws Exception {
        this.insert(Collections.singletonList(zbInfoDO));
    }

    @Override
    public void insert(List<ZbInfoDO> zbInfos) throws Exception {
        if (CollectionUtils.isEmpty(zbInfos)) {
            return;
        }
        try {
            this.buildMethod();
            Map<String, Integer> extFields = this.classification(zbInfos);
            int sub1 = this.insertSQL.indexOf(")");
            int sub2 = this.insertSQL.lastIndexOf(")");
            StringBuilder builder = new StringBuilder(this.insertSQL.substring(0, sub1));
            extFields.keySet().forEach(k -> builder.append(",").append((String)k));
            builder.append(this.insertSQL.substring(sub1, sub2));
            extFields.keySet().forEach(k -> builder.append(",").append("?"));
            String sql = builder.append(")").toString();
            Object[] ov = new Object[]{};
            ArrayList<Object[]> argList = new ArrayList<Object[]>();
            for (ZbInfoDO obj : zbInfos) {
                ArrayList<Object> lst = new ArrayList<Object>();
                for (String dbFieldName : this.dbFieldList) {
                    Field field = (Field)this.fieldMap.get(this.dbFieldMap.get(dbFieldName));
                    DBAnno.DBField fieldAnno = field.getAnnotation(DBAnno.DBField.class);
                    lst.add(super.transValue(field, fieldAnno, (Object)obj));
                }
                this.addPropValue(extFields, obj, lst);
                argList.add(lst.toArray(ov));
            }
            int[] ints = this.jdbcTemplate.batchUpdate(sql, argList);
            log.debug("\u63d2\u5165\u591a\u6761\u6307\u6807: {}", (Object)ints.length);
        }
        catch (Exception e) {
            this.throwBeanParaException(e);
        }
    }

    private Map<String, Integer> classification(List<ZbInfoDO> zbInfos) {
        LinkedHashMap<String, Integer> extFields = new LinkedHashMap<String, Integer>(zbInfos.size());
        int index = 1;
        for (ZbInfoDO zbInfo : zbInfos) {
            List<PropInfo> extProp = zbInfo.getExtProp();
            for (PropInfo propInfo : extProp) {
                if (extFields.containsKey(propInfo.getFieldName())) continue;
                extFields.put(propInfo.getFieldName(), index++);
            }
        }
        return extFields;
    }

    @Override
    public void update(ZbInfoDO zbInfoDO) throws Exception {
        this.update(Collections.singletonList(zbInfoDO));
    }

    @Override
    public void update(List<ZbInfoDO> zbInfos) throws Exception {
        if (CollectionUtils.isEmpty(zbInfos)) {
            return;
        }
        try {
            this.buildMethod();
            Map<String, Integer> extFields = this.classification(zbInfos);
            if (StringUtils.isEmpty(this.pk)) {
                this.throwBeanParaException("pk\u672a\u5b9a\u4e49");
            }
            Field pkfield = (Field)this.fieldMap.get(this.pk);
            DBAnno.DBField pkDbField = pkfield.getAnnotation(DBAnno.DBField.class);
            StringBuffer sql = new StringBuffer(this.updateSQL.toString());
            extFields.keySet().forEach(field -> sql.append(",").append((String)field).append("=?"));
            sql.append(" where ").append(pkDbField.dbField().toUpperCase()).append("=?");
            Object[] ov = new Object[]{};
            ArrayList<Object[]> argList = new ArrayList<Object[]>();
            for (ZbInfoDO obj : zbInfos) {
                ArrayList<Object> lst = new ArrayList<Object>();
                for (String dbFieldName : this.dbFieldList) {
                    Field field2 = (Field)this.fieldMap.get(this.dbFieldMap.get(dbFieldName));
                    DBAnno.DBField fieldAnno = field2.getDeclaredAnnotation(DBAnno.DBField.class);
                    if (fieldAnno.notUpdate() || fieldAnno.isPk()) continue;
                    lst.add(super.transValue(field2, fieldAnno, (Object)obj));
                }
                this.addPropValue(extFields, obj, lst);
                Object pk = this.transValue(pkfield, pkDbField, (Object)obj);
                lst.add(pk);
                argList.add(lst.toArray(ov));
            }
            this.jdbcTemplate.batchUpdate(sql.toString(), argList);
        }
        catch (Exception e) {
            this.throwBeanParaException(e);
        }
    }

    private void addPropValue(Map<String, Integer> extFields, ZbInfoDO obj, List<Object> lst) throws Exception {
        int size = lst.size() - 1;
        for (int i = 0; i < extFields.size(); ++i) {
            lst.add(null);
        }
        List<PropInfo> extProp = obj.getExtProp();
        if (extProp != null) {
            for (PropInfo propInfo : extProp) {
                Field valueField;
                try {
                    valueField = propInfo.getClass().getDeclaredField("value");
                }
                catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
                Object o = this.transValue(valueField, ExtDBField.valueOf(propInfo), (Object)propInfo);
                lst.set(size + extFields.get(propInfo.getFieldName()), o);
            }
        }
    }

    @Override
    public void deleteByKeys(List<String> keys) {
        super.delete(keys.toArray(new Object[0]));
    }

    @Override
    public void deleteByScheme(String scheme) {
        super.deleteBy(new String[]{"schemeKey"}, new Object[]{scheme});
    }

    @Override
    public void deleteByVersion(String versionKey) {
        super.deleteBy(new String[]{"versionKey"}, new Object[]{versionKey});
    }

    public void delete(ZbInfoDO zbInfoDO) throws Exception {
        super.delete(zbInfoDO);
    }

    public void delete(List<ZbInfoDO> zbInfos) throws Exception {
        super.delete(zbInfos.toArray(new Object[0]));
    }

    @Override
    public int getNonEmptyFieldCount(String fieldName) {
        Assert.notNull((Object)fieldName, (String)"fieldName can not be null");
        return super.count(fieldName.toUpperCase() + " IS NOT NULL");
    }

    @Override
    public int countZbByVersion(String version) {
        return super.count("ZB_VERSION_KEY = '" + version + "'");
    }

    @Override
    public int countZbByScheme(String schemeKey) {
        return super.count("ZB_SCHEME_KEY = '" + schemeKey + "'");
    }
}

