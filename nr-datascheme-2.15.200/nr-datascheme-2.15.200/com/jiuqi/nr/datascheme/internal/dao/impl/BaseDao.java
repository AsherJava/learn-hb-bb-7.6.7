/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.nr.datascheme.exception.BeanParaException;
import com.jiuqi.nr.datascheme.exception.DBParaException;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.datascheme.internal.dao.impl.TransUtil;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public abstract class BaseDao {
    private static final Class<?>[] typeNames = new Class[]{Integer.TYPE, Integer.class, Double.TYPE, Double.class, Float.TYPE, Float.class, Long.TYPE, Long.class, Boolean.TYPE, Boolean.class, String.class, byte[].class, Date.class, Time.class, Timestamp.class, java.util.Date.class, Blob.class, Clob.class};
    private static final int[] jdbcTypes = new int[]{4, 4, 8, 8, 6, 6, -5, -5, -7, -7, 12, 2004, 91, 92, 93, 93, 2004, 2005};
    protected StringBuffer insertSQL = new StringBuffer("insert into ");
    protected StringBuffer selectSQL = new StringBuffer("select ");
    protected StringBuffer updateSQL = new StringBuffer("update ");
    private List<String> dbFieldList = new ArrayList<String>();
    private Map<String, String> dbFieldMap = new HashMap<String, String>();
    protected Map<String, Field> fieldMap = new HashMap<String, Field>();
    private HashMap<String, Method> methodMap = new HashMap();
    private HashMap<String, HashMap<Class<?>, Method>> transMethodMap = new HashMap();
    private HashMap<String, HashMap<Class<?>, Method>> externalTransMethodMap = new HashMap();
    protected String tablename = "";
    private String orderFieldName = "";
    protected String pk = "";
    private Class<TransUtil> transCls = TransUtil.class;
    protected Class<?> externalTransCls = null;
    private boolean hasBuilded;
    private DBAnno.DBLink[] links = null;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(BaseDao.class);

    public abstract Class<?> getClz();

    public BaseDao() {
        this.buildMethod();
    }

    protected void buildMethod() {
        Method[] ms;
        if (this.hasBuilded) {
            return;
        }
        Class<?> clz = this.getClz();
        if (clz == null) {
            throw new IllegalArgumentException("\u8bf7\u5728getClz\u4e2d\u76f4\u63a5\u8fd4\u56de\u7c7b\u5b9e\u4f8b\u5bf9\u8c61");
        }
        if (clz.isAnnotationPresent(DBAnno.DBTable.class)) {
            DBAnno.DBTable dbTable = clz.getAnnotation(DBAnno.DBTable.class);
            this.tablename = dbTable.dbTable().toUpperCase();
            this.insertSQL.append(this.tablename).append(" (");
            this.updateSQL.append(this.tablename).append(" set ");
        }
        if (clz.isAnnotationPresent(DBAnno.DBLink.class)) {
            this.links = (DBAnno.DBLink[])clz.getAnnotationsByType(DBAnno.DBLink.class);
        } else if (clz.isAnnotationPresent(DBAnno.DBLinks.class)) {
            DBAnno.DBLinks[] annotationsByType = (DBAnno.DBLinks[])clz.getAnnotationsByType(DBAnno.DBLinks.class);
            this.links = annotationsByType[0].value();
        }
        HashMap<String, Field> fieldMapTemp = new HashMap<String, Field>(30);
        while (clz != null) {
            Field[] declaredFields;
            for (Field declaredField : declaredFields = clz.getDeclaredFields()) {
                String name = declaredField.getName();
                if (fieldMapTemp.containsKey(name)) continue;
                fieldMapTemp.put(name, declaredField);
            }
            clz = clz.getSuperclass();
        }
        clz = this.getClz();
        int i = 0;
        StringBuilder param = new StringBuilder();
        for (Method method : ms = clz.getMethods()) {
            if (method.getDeclaringClass().equals(Object.class)) continue;
            this.methodMap.put(method.getName(), method);
        }
        Collection fields = fieldMapTemp.values();
        ArrayList<String> upFields = new ArrayList<String>();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(DBAnno.DBField.class)) continue;
            if (i > 0) {
                this.insertSQL.append(",");
                this.selectSQL.append(",");
                param.append(",");
            }
            DBAnno.DBField fieldAnno = field.getAnnotation(DBAnno.DBField.class);
            this.fieldMap.put(field.getName(), field);
            this.dbFieldList.add(fieldAnno.dbField().toUpperCase());
            this.dbFieldMap.put(fieldAnno.dbField().toUpperCase(), field.getName());
            this.insertSQL.append(fieldAnno.dbField().toUpperCase());
            this.selectSQL.append(fieldAnno.dbField().toUpperCase());
            if (fieldAnno.isPk()) {
                this.pk = field.getName();
            } else if (!fieldAnno.notUpdate()) {
                upFields.add(fieldAnno.dbField().toUpperCase());
            }
            param.append("?");
            if (fieldAnno.isOrder()) {
                this.orderFieldName = field.getName();
            }
            ++i;
        }
        if (upFields.isEmpty()) {
            throw new BeanParaException("\u672a\u627e\u5230\u66f4\u65b0\u5b57\u6bb5\uff0c\u5fc5\u987b\u5b58\u5728\u4e00\u4e2a\u9700\u8981\u66f4\u65b0\u7684\u5b57\u6bb5");
        }
        this.updateSQL.append(String.join((CharSequence)",", upFields.stream().map(r -> r + " =? ").collect(Collectors.toList())));
        if (i > 0) {
            this.insertSQL.append(")");
        }
        this.insertSQL.append(" values (").append((CharSequence)param).append(")");
        this.selectSQL.append(" from ").append(this.tablename).append(" ");
        this.externalTransCls = this.getExternalTransCls();
        this.initTransMethodMap();
        this.hasBuilded = true;
    }

    private void initTransMethodMap() {
        Class<?>[] parameterTypes;
        HashMap<Class<Object>, Method> methodMap;
        Method[] methods;
        for (Method method : methods = this.transCls.getMethods()) {
            methodMap = this.transMethodMap.get(method.getName());
            if (methodMap == null) {
                methodMap = new HashMap();
            }
            if ((parameterTypes = method.getParameterTypes()).length != 1) continue;
            methodMap.put(parameterTypes[0], method);
            this.transMethodMap.put(method.getName(), methodMap);
        }
        if (this.externalTransCls != null) {
            for (Method method : methods = this.externalTransCls.getMethods()) {
                methodMap = this.externalTransMethodMap.get(method.getName());
                if (methodMap == null) {
                    methodMap = new HashMap();
                }
                if ((parameterTypes = method.getParameterTypes()).length != 1) continue;
                methodMap.put(parameterTypes[0], method);
                this.externalTransMethodMap.put(method.getName(), methodMap);
            }
        }
    }

    public Class<?> getExternalTransCls() {
        return null;
    }

    private int findType(Class<?> obj) {
        if (obj == null) {
            return 1111;
        }
        String typeName = obj.getName();
        if (typeName != null) {
            for (int i = 0; i < typeNames.length; ++i) {
                if (!typeName.equals(typeNames[i].getName())) continue;
                return jdbcTypes[i];
            }
        }
        return 0;
    }

    protected void readRecord(ResultSet rs, Object obj) {
        try {
            for (String dbField : this.dbFieldList) {
                Throwable throwable;
                Object v;
                Field field = this.fieldMap.get(this.dbFieldMap.get(dbField));
                DBAnno.DBField fieldAnno = field.getAnnotation(DBAnno.DBField.class);
                if (fieldAnno.dbType() == Timestamp.class) {
                    v = rs.getTimestamp(dbField);
                    this.transValueByType(obj, v, field, fieldAnno);
                    continue;
                }
                if (fieldAnno.dbType() == Blob.class) {
                    v = rs.getBinaryStream(dbField);
                    throwable = null;
                    try {
                        this.transValueByType(obj, v, field, fieldAnno);
                        continue;
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        if (v == null) continue;
                        if (throwable != null) {
                            try {
                                ((InputStream)v).close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                            continue;
                        }
                        ((InputStream)v).close();
                        continue;
                    }
                }
                if (fieldAnno.dbType() == Clob.class) {
                    if (this.clobReadByStream()) {
                        v = rs.getBinaryStream(dbField);
                        throwable = null;
                        try {
                            this.transValueByType(obj, v, field, fieldAnno);
                            continue;
                        }
                        catch (Throwable throwable4) {
                            throwable = throwable4;
                            throw throwable4;
                        }
                        finally {
                            if (v == null) continue;
                            if (throwable != null) {
                                try {
                                    ((InputStream)v).close();
                                }
                                catch (Throwable throwable5) {
                                    throwable.addSuppressed(throwable5);
                                }
                                continue;
                            }
                            ((InputStream)v).close();
                            continue;
                        }
                    }
                    v = rs.getClob(dbField);
                    this.transValueByType(obj, v, field, fieldAnno);
                    continue;
                }
                v = rs.getObject(dbField);
                this.transValueByType(obj, v, field, fieldAnno);
            }
        }
        catch (Exception e) {
            throw new BeanParaException(e);
        }
    }

    private void transValueByType(Object obj, Object v, Field field, DBAnno.DBField fieldAnno) throws SQLException, Exception, IllegalAccessException, NoSuchMethodException {
        if (v != null) {
            int t = 0;
            t = Object.class != fieldAnno.dbType() ? this.findType(fieldAnno.dbType()) : this.findType(field.getType());
            for (int s = 0; s < jdbcTypes.length; ++s) {
                if (t != jdbcTypes[s]) continue;
                if (typeNames[s] == Integer.class) {
                    v = TransUtil.parseInteger(v);
                    break;
                }
                if (typeNames[s] == Double.class) {
                    v = TransUtil.parseDouble(v);
                    break;
                }
                if (typeNames[s] == Blob.class) {
                    v = TransUtil.transBlob(v);
                    break;
                }
                if (typeNames[s] == Clob.class) {
                    v = TransUtil.transClob(v);
                    break;
                }
                if (typeNames[s] == java.util.Date.class) {
                    v = TransUtil.parseDate(v);
                    break;
                }
                if (typeNames[s] != String.class || v == null || !fieldAnno.upper()) continue;
                v = v.toString().toUpperCase();
            }
            if (!StringUtils.isEmpty(fieldAnno.tranWith())) {
                v = this.transValue(fieldAnno.tranWith(), fieldAnno.appType(), v);
            }
            if (StringUtils.isEmpty(fieldAnno.set())) {
                ReflectionUtils.makeAccessible(field);
                field.set(obj, v);
            } else {
                Method m = obj.getClass().getMethod(fieldAnno.set(), v.getClass());
                try {
                    m.invoke(obj, v);
                }
                catch (Exception e) {
                    this.logger.debug(field.getName() + "==" + m.getName() + "===" + m.getParameterTypes()[0] + "==" + v + "==" + v.getClass().getName());
                    throw e;
                }
            }
        }
    }

    private Object transValue(String methodName, Class<?> type, Object v) throws Exception {
        if (v.getClass() == type) {
            return v;
        }
        HashMap<Class<?>, Method> methodMap = this.externalTransMethodMap.get(methodName);
        if (methodMap != null) {
            Method transMethod = methodMap.get(v.getClass());
            v = transMethod.invoke(this.externalTransCls.newInstance(), v);
        } else {
            methodMap = this.transMethodMap.get(methodName);
            if (methodMap != null) {
                Method transMethod = methodMap.get(v.getClass());
                if (transMethod == null) {
                    transMethod = methodMap.get(v.getClass().getSuperclass());
                }
                if (null != transMethod) {
                    v = transMethod.invoke(this.transCls.newInstance(), v);
                }
            }
        }
        return v;
    }

    public int insert(Object obj) throws BeanParaException, DBParaException {
        if (obj == null) {
            throw new DBParaException();
        }
        int ret = 0;
        try {
            String sql = this.insertSQL.toString();
            Object[] ov = new Object[]{};
            ArrayList<Object> lst = new ArrayList<Object>();
            ArrayList<Integer> typeList = new ArrayList<Integer>();
            for (String dbFieldName : this.dbFieldList) {
                Field field = this.fieldMap.get(this.dbFieldMap.get(dbFieldName));
                DBAnno.DBField fieldAnno = field.getAnnotation(DBAnno.DBField.class);
                ReflectionUtils.makeAccessible(field);
                Object v = field.get(obj);
                if (StringUtils.hasLength(fieldAnno.get())) {
                    Method getMethod = this.methodMap.get(fieldAnno.get());
                    if (getMethod == null) {
                        this.throwBeanParaException("getMethod\u5b9a\u4e49\u6709\u8bef\uff01\uff01" + obj.getClass().getName() + "." + fieldAnno.get());
                    } else {
                        v = getMethod.invoke(obj, ov);
                    }
                }
                try {
                    if (fieldAnno.autoDate()) {
                        v = Calendar.getInstance().getTime();
                    }
                }
                catch (Exception e) {
                    this.throwBeanParaException(field.getName() + "\u81ea\u52a8\u586b\u5145\u5f53\u524d\u65f6\u95f4\u5931\u8d25\uff01");
                }
                if (v != null && StringUtils.hasLength(fieldAnno.tranWith())) {
                    v = this.transValue(fieldAnno.tranWith(), fieldAnno.dbType(), v);
                }
                int type = 0;
                type = Object.class != fieldAnno.dbType() ? this.findType(fieldAnno.dbType()) : this.findType(field.getType());
                typeList.add(type);
                lst.add(v);
            }
            this.logger.debug(sql + " \n " + obj);
            this.jdbcTemplate.update(sql, lst.toArray(ov));
        }
        catch (DataAccessException e) {
            throw e;
        }
        catch (Exception e) {
            this.throwBeanParaException(e);
        }
        return ret;
    }

    public int[] insert(Object[] objs) throws BeanParaException, DBParaException {
        if (objs == null || objs.length == 0) {
            return null;
        }
        int[] ret = new int[objs.length];
        try {
            String sql = this.insertSQL.toString();
            Object[] ov = new Object[]{};
            ArrayList<Object[]> argList = new ArrayList<Object[]>();
            for (Object obj : objs) {
                ArrayList<Object> lst = new ArrayList<Object>();
                for (String dbFieldName : this.dbFieldList) {
                    Field field = this.fieldMap.get(this.dbFieldMap.get(dbFieldName));
                    DBAnno.DBField fieldAnno = field.getAnnotation(DBAnno.DBField.class);
                    ReflectionUtils.makeAccessible(field);
                    Object v = field.get(obj);
                    if (StringUtils.hasLength(fieldAnno.get())) {
                        Method getMethod = this.methodMap.get(fieldAnno.get());
                        if (getMethod == null) {
                            this.throwBeanParaException("getMethod\u5b9a\u4e49\u6709\u8bef\uff01\uff01" + obj.getClass().getName() + "." + fieldAnno.get());
                        } else {
                            v = getMethod.invoke(obj, ov);
                        }
                    }
                    try {
                        if (fieldAnno.autoDate()) {
                            v = Calendar.getInstance().getTime();
                        }
                    }
                    catch (Exception e) {
                        this.throwBeanParaException(field.getName() + "\u81ea\u52a8\u586b\u5145\u5f53\u524d\u65f6\u95f4\u5931\u8d25\uff01");
                    }
                    if (v != null && StringUtils.hasLength(fieldAnno.tranWith())) {
                        v = this.transValue(fieldAnno.tranWith(), fieldAnno.dbType(), v);
                    }
                    lst.add(v);
                }
                argList.add(lst.toArray(ov));
            }
            this.logger.debug(sql + " \n " + objs);
            ret = this.jdbcTemplate.batchUpdate(sql, argList);
        }
        catch (DataAccessException e) {
            throw e;
        }
        catch (Exception e) {
            this.throwBeanParaException(e);
        }
        return ret;
    }

    public int delete(Object obj) throws BeanParaException, DBParaException {
        if (obj == null) {
            throw new DBParaException();
        }
        int ret = 0;
        try {
            if (!StringUtils.hasLength(this.pk)) {
                this.throwBeanParaException("pk\u672a\u5b9a\u4e49");
            }
            Field field = this.fieldMap.get(this.pk);
            DBAnno.DBField dbField = field.getAnnotation(DBAnno.DBField.class);
            String sql = "delete from " + this.tablename + " where " + dbField.dbField().toUpperCase() + "=?";
            if (obj != null && StringUtils.hasLength(dbField.tranWith())) {
                obj = this.transValue(dbField.tranWith(), dbField.dbType(), obj);
            }
            this.logger.debug(sql + " \n " + obj);
            ret = this.jdbcTemplate.update(sql, new Object[]{obj});
        }
        catch (DataAccessException e) {
            throw e;
        }
        catch (Exception e) {
            this.throwBeanParaException(e);
        }
        return ret;
    }

    public int deleteAll() throws BeanParaException {
        if (!StringUtils.hasLength(this.pk)) {
            this.throwBeanParaException("pk\u672a\u5b9a\u4e49");
        }
        String sql = "delete from " + this.tablename + " ";
        this.logger.debug(sql);
        return this.jdbcTemplate.update(sql);
    }

    public int[] delete(final Object[] objs) throws BeanParaException, DBParaException {
        if (objs == null || objs.length == 0) {
            return null;
        }
        int[] ret = new int[objs.length];
        try {
            if (!StringUtils.hasLength(this.pk)) {
                this.throwBeanParaException("pk\u672a\u5b9a\u4e49");
            }
            Field field = this.fieldMap.get(this.pk);
            final DBAnno.DBField dbField = field.getAnnotation(DBAnno.DBField.class);
            StringBuffer sql = new StringBuffer("delete from ").append(this.tablename).append(" where ").append(dbField.dbField().toUpperCase()).append("=?");
            String sqlStr = sql.toString();
            this.logger.debug(sqlStr + "\n" + objs);
            ret = this.jdbcTemplate.batchUpdate(sqlStr, new BatchPreparedStatementSetter(){

                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Object obj = objs[i];
                    try {
                        if (obj != null && StringUtils.hasLength(dbField.tranWith())) {
                            obj = BaseDao.this.transValue(dbField.tranWith(), dbField.dbType(), obj);
                        }
                    }
                    catch (DataAccessException e) {
                        throw e;
                    }
                    catch (Exception e) {
                        BaseDao.this.throwBeanParaException(e);
                    }
                    ps.setObject(1, obj);
                }

                public int getBatchSize() {
                    return objs.length;
                }
            });
        }
        catch (DataAccessException e) {
            throw e;
        }
        catch (Exception e) {
            this.throwBeanParaException(e);
        }
        return ret;
    }

    public int deleteBy(String[] fieldNames, Object[] args) throws BeanParaException, DBParaException {
        if (fieldNames == null || args == null || fieldNames.length != args.length || fieldNames.length == 0) {
            throw new DBParaException();
        }
        int ret = 0;
        try {
            StringBuffer deleteSQL = new StringBuffer("delete from ");
            deleteSQL.append(this.tablename).append(" where ");
            ArrayList<Object> list = new ArrayList<Object>();
            for (int i = 0; i < fieldNames.length; ++i) {
                Object v;
                String fieldName = fieldNames[i];
                Field field = this.fieldMap.get(fieldName = this.dbFieldMap.get(fieldName));
                if (field == null) {
                    this.throwBeanParaException(fieldName + "\u5c5e\u6027\u4e0d\u6b63\u786e\uff01");
                }
                DBAnno.DBField fieldAnno = field.getAnnotation(DBAnno.DBField.class);
                if (i > 0) {
                    deleteSQL.append(" and ");
                }
                if (null == (v = args[i])) {
                    deleteSQL.append(fieldAnno.dbField().toUpperCase()).append(" is null ");
                    continue;
                }
                deleteSQL.append(fieldAnno.dbField().toUpperCase()).append(" =? ");
                if (StringUtils.hasLength(fieldAnno.tranWith())) {
                    v = this.transValue(fieldAnno.tranWith(), fieldAnno.dbType(), v);
                }
                list.add(v);
            }
            String sql = deleteSQL.toString();
            this.logger.debug(sql + "\n" + list);
            ret = this.jdbcTemplate.update(sql, list.toArray());
        }
        catch (DataAccessException e) {
            throw e;
        }
        catch (Exception e) {
            this.throwBeanParaException(e);
        }
        return ret;
    }

    public int update(Object obj) throws BeanParaException, DBParaException {
        int ret = 0;
        if (obj == null) {
            throw new DBParaException();
        }
        try {
            ArrayList<Object> lst = new ArrayList<Object>();
            if (!StringUtils.hasLength(this.pk)) {
                this.throwBeanParaException("pk\u672a\u5b9a\u4e49");
            }
            Field pkfield = this.fieldMap.get(this.pk);
            DBAnno.DBField pkDbField = pkfield.getAnnotation(DBAnno.DBField.class);
            StringBuffer sql = new StringBuffer(this.updateSQL.toString()).append(" where ").append(pkDbField.dbField().toUpperCase()).append("=?");
            Object[] ov = new Object[]{};
            for (String dbFieldName : this.dbFieldList) {
                Object v = null;
                Field field = this.fieldMap.get(this.dbFieldMap.get(dbFieldName));
                DBAnno.DBField fieldAnno = field.getDeclaredAnnotation(DBAnno.DBField.class);
                if (fieldAnno.notUpdate() || fieldAnno.isPk()) continue;
                ReflectionUtils.makeAccessible(field);
                v = field.get(obj);
                if (StringUtils.hasLength(fieldAnno.get())) {
                    Method getMethod = this.methodMap.get(fieldAnno.get());
                    if (getMethod == null) {
                        this.throwBeanParaException("getMethod\u5b9a\u4e49\u6709\u8bef\uff01\uff01" + obj.getClass().getName() + "." + fieldAnno.get());
                    } else {
                        v = getMethod.invoke(obj, ov);
                    }
                }
                try {
                    if (fieldAnno.autoDate()) {
                        v = Calendar.getInstance().getTime();
                    }
                }
                catch (Exception e) {
                    this.throwBeanParaException(field.getName() + "\u81ea\u52a8\u586b\u5145\u5f53\u524d\u65f6\u95f4\u5931\u8d25\uff01");
                }
                if (v != null && StringUtils.hasLength(fieldAnno.tranWith())) {
                    v = this.transValue(fieldAnno.tranWith(), fieldAnno.dbType(), v);
                }
                lst.add(v);
            }
            ReflectionUtils.makeAccessible(pkfield);
            Object v = pkfield.get(obj);
            if (StringUtils.hasLength(pkDbField.get())) {
                Method getMethod = this.methodMap.get(pkDbField.get());
                if (getMethod == null) {
                    this.throwBeanParaException("getMethod\u5b9a\u4e49\u6709\u8bef\uff01\uff01" + obj.getClass().getName() + "." + pkDbField.get());
                } else {
                    v = getMethod.invoke(obj, ov);
                }
            }
            if (v != null && StringUtils.hasLength(pkDbField.tranWith())) {
                v = this.transValue(pkDbField.tranWith(), pkDbField.dbType(), v);
            }
            lst.add(v);
            String sqlStr = sql.toString();
            this.logger.debug(sqlStr + "\n" + obj);
            ret = this.jdbcTemplate.update(sqlStr, lst.toArray(ov));
        }
        catch (DataAccessException e) {
            throw e;
        }
        catch (Exception e) {
            this.throwBeanParaException(e);
        }
        return ret;
    }

    public int[] update(Object[] objs) throws BeanParaException, DBParaException {
        if (objs == null || objs.length == 0) {
            throw new DBParaException();
        }
        int[] ret = new int[objs.length];
        try {
            if (!StringUtils.hasLength(this.pk)) {
                this.throwBeanParaException("pk\u672a\u5b9a\u4e49");
            }
            Field pkfield = this.fieldMap.get(this.pk);
            DBAnno.DBField pkDbField = pkfield.getAnnotation(DBAnno.DBField.class);
            StringBuffer sql = new StringBuffer(this.updateSQL.toString()).append(" where ").append(pkDbField.dbField().toUpperCase()).append("=?");
            Object[] ov = new Object[]{};
            ArrayList<Object[]> argList = new ArrayList<Object[]>();
            for (Object obj : objs) {
                ArrayList<Object> lst = new ArrayList<Object>();
                for (String dbFieldName : this.dbFieldList) {
                    Object v = null;
                    Field field = this.fieldMap.get(this.dbFieldMap.get(dbFieldName));
                    DBAnno.DBField fieldAnno = field.getDeclaredAnnotation(DBAnno.DBField.class);
                    if (fieldAnno.notUpdate() || fieldAnno.isPk()) continue;
                    ReflectionUtils.makeAccessible(field);
                    v = field.get(obj);
                    if (StringUtils.hasLength(fieldAnno.get())) {
                        Method getMethod = this.methodMap.get(fieldAnno.get());
                        if (getMethod == null) {
                            this.throwBeanParaException("getMethod\u5b9a\u4e49\u6709\u8bef\uff01\uff01" + obj.getClass().getName() + "." + fieldAnno.get());
                        } else {
                            v = getMethod.invoke(obj, ov);
                        }
                    }
                    try {
                        if (fieldAnno.autoDate()) {
                            v = Calendar.getInstance().getTime();
                        }
                    }
                    catch (Exception e) {
                        this.throwBeanParaException(field.getName() + "\u81ea\u52a8\u586b\u5145\u5f53\u524d\u65f6\u95f4\u5931\u8d25\uff01");
                    }
                    if (v != null && StringUtils.hasLength(fieldAnno.tranWith())) {
                        v = this.transValue(fieldAnno.tranWith(), fieldAnno.dbType(), v);
                    }
                    lst.add(v);
                }
                ReflectionUtils.makeAccessible(pkfield);
                Object v = pkfield.get(obj);
                if (StringUtils.hasLength(pkDbField.get())) {
                    Method getMethod = this.methodMap.get(pkDbField.get());
                    if (getMethod == null) {
                        this.throwBeanParaException("getMethod\u5b9a\u4e49\u6709\u8bef\uff01\uff01" + obj.getClass().getName() + "." + pkDbField.get());
                    } else {
                        v = getMethod.invoke(obj, ov);
                    }
                }
                if (v != null && StringUtils.hasLength(pkDbField.tranWith())) {
                    v = this.transValue(pkDbField.tranWith(), pkDbField.dbType(), v);
                }
                lst.add(v);
                argList.add(lst.toArray(ov));
            }
            String sqlStr = sql.toString();
            this.logger.debug(sqlStr + "\n" + objs);
            ret = this.jdbcTemplate.batchUpdate(sqlStr, argList);
        }
        catch (DataAccessException e) {
            throw e;
        }
        catch (Exception e) {
            this.throwBeanParaException(e);
        }
        return ret;
    }

    public <T> List<T> list(String sqlWhere, Object[] args, Class<? extends T> t) throws BeanParaException {
        List ret = new ArrayList();
        try {
            String sql;
            String order = "";
            if (StringUtils.hasLength(this.orderFieldName)) {
                Field orderField = this.fieldMap.get(this.orderFieldName);
                DBAnno.DBField dbField = orderField.getAnnotation(DBAnno.DBField.class);
                order = " order by " + dbField.dbField().toUpperCase();
            }
            if (sqlWhere == null || sqlWhere.equals("")) {
                sql = this.selectSQL.toString() + order;
                this.logger.debug(sql);
                ret = this.jdbcTemplate.query(con -> con.prepareStatement(sql), this.getRowMapper(t));
            } else {
                sql = new StringBuffer(this.selectSQL.toString()).append(" where ").append(sqlWhere).append(" ").append(order).toString();
                this.logger.debug(sql);
                ret = this.jdbcTemplate.query(con -> {
                    PreparedStatement statement = con.prepareStatement(sql);
                    if (args != null) {
                        for (int i = 0; i < args.length; ++i) {
                            statement.setObject(i + 1, args[i]);
                        }
                    }
                    return statement;
                }, this.getRowMapper(t));
            }
        }
        catch (DataAccessException e) {
            throw e;
        }
        catch (Exception e) {
            this.throwBeanParaException(e);
        }
        return ret;
    }

    public <T> List<T> list(Class<? extends T> t) {
        List list = new ArrayList();
        try {
            String order = "";
            if (StringUtils.hasLength(this.orderFieldName)) {
                Field orderField = this.fieldMap.get(this.orderFieldName);
                DBAnno.DBField dbField = orderField.getAnnotation(DBAnno.DBField.class);
                order = " order by " + dbField.dbField().toUpperCase();
            }
            String sql = this.selectSQL.toString() + order;
            this.logger.debug(sql);
            list = this.jdbcTemplate.query(sql, this.getRowMapper(t));
        }
        catch (DataAccessException e) {
            throw e;
        }
        catch (Exception e) {
            this.throwBeanParaException(e);
        }
        return list;
    }

    public <T> List<T> list(String[] fieldNames, Object[] args, Class<? extends T> t) throws BeanParaException {
        assert (fieldNames == null && args == null || fieldNames.length == args.length);
        List list = new ArrayList();
        try {
            String order = "";
            if (StringUtils.hasLength(this.orderFieldName)) {
                Field orderField = this.fieldMap.get(this.orderFieldName);
                DBAnno.DBField dbField = orderField.getAnnotation(DBAnno.DBField.class);
                order = " order by " + dbField.dbField().toUpperCase();
            }
            if (fieldNames == null || fieldNames.length == 0) {
                String sql = this.selectSQL.toString() + order;
                this.logger.debug(sql);
                list = this.jdbcTemplate.query(sql, this.getRowMapper(t));
            } else {
                StringBuffer sqlWhere = new StringBuffer();
                Object[] objs = new Object[args.length];
                int curIdx = 0;
                int i = 0;
                while (i < fieldNames.length) {
                    String fieldName = fieldNames[i];
                    Field field = this.fieldMap.get(fieldName = this.dbFieldMap.get(fieldName));
                    if (field == null) {
                        this.throwBeanParaException(fieldName + "\u5c5e\u6027\u4e0d\u6b63\u786e\uff01");
                    }
                    DBAnno.DBField fieldAnno = field.getAnnotation(DBAnno.DBField.class);
                    if (args[i] == null) {
                        if (sqlWhere.length() > 0) {
                            sqlWhere.append(" and ");
                        }
                        sqlWhere.append(fieldAnno.dbField().toUpperCase()).append(" is null ");
                        Object[] objsTrans = new Object[objs.length - 1];
                        for (int s = 0; s < i; ++s) {
                            objsTrans[s] = objs[s];
                        }
                        objs = objsTrans;
                        --curIdx;
                    } else {
                        if (sqlWhere.length() > 0) {
                            sqlWhere.append(" and ");
                        }
                        sqlWhere.append(fieldAnno.dbField().toUpperCase()).append("=? ");
                        Object v = args[i];
                        if (v != null && StringUtils.hasLength(fieldAnno.tranWith())) {
                            v = this.transValue(fieldAnno.tranWith(), fieldAnno.dbType(), v);
                        }
                        objs[curIdx] = v;
                    }
                    ++i;
                    ++curIdx;
                }
                String sql = new StringBuffer(this.selectSQL.toString()).append(" where ").append(sqlWhere).append(order).toString();
                Object[] objs1 = objs;
                this.logger.debug(sql);
                list = this.jdbcTemplate.query(con -> {
                    PreparedStatement statement = con.prepareStatement(sql);
                    for (int i = 0; i < objs1.length; ++i) {
                        statement.setObject(i + 1, objs1[i]);
                    }
                    return statement;
                }, this.getRowMapper(t));
            }
        }
        catch (DataAccessException e) {
            throw e;
        }
        catch (Exception e) {
            this.throwBeanParaException(e);
        }
        return list;
    }

    public <T> T getByKey(Object obj, Class<T> t) throws BeanParaException {
        Object result = null;
        try {
            if (!StringUtils.hasLength(this.pk)) {
                this.throwBeanParaException(t + "\u672a\u5b9a\u4e49\u4e3b\u952e\uff01");
            }
            StringBuffer sqlWhere = new StringBuffer();
            Field field = this.fieldMap.get(this.pk);
            DBAnno.DBField dbField = field.getAnnotation(DBAnno.DBField.class);
            sqlWhere.append(dbField.dbField().toUpperCase()).append("=?");
            if (obj != null) {
                obj = this.transValue(dbField.tranWith(), dbField.dbType(), obj);
            }
            String sql = new StringBuffer(this.selectSQL.toString()).append(" where ").append(sqlWhere).toString();
            this.logger.debug(sql);
            result = this.jdbcTemplate.query(sql, new Object[]{obj}, this.resultSetExtractor(t));
        }
        catch (DataAccessException e) {
            throw e;
        }
        catch (Exception e) {
            this.throwBeanParaException(e);
        }
        return (T)result;
    }

    public <T> T getBy(String whereSQL, Object[] obj, Class<? extends T> t) throws BeanParaException {
        Object result = null;
        String sql = "";
        sql = !StringUtils.hasLength(whereSQL) ? this.selectSQL.toString() : this.selectSQL.toString() + " where " + whereSQL;
        this.logger.debug(sql);
        result = this.jdbcTemplate.query(sql, obj, this.resultSetExtractor(t));
        return (T)result;
    }

    public <T> List<T> listByForeign(Object obj, String[] filterAttrs1, String[] filterAttrs2, Object[] values, Class<? extends T> t) throws BeanParaException {
        List list = new ArrayList();
        try {
            Object v;
            DBAnno.DBField annotation;
            StringBuffer sql = new StringBuffer("select ");
            for (int i = 0; i < this.dbFieldList.size(); ++i) {
                if (i > 0) {
                    sql.append(",");
                }
                sql.append(this.tablename).append(".").append(this.dbFieldList.get(i));
            }
            sql.append(" from ").append(this.tablename).append(" where ");
            final Class<?> foreignClass = obj.getClass();
            DBAnno.DBTable tableAnno = foreignClass.getAnnotation(DBAnno.DBTable.class);
            if (tableAnno == null) {
                this.throwBeanParaException(foreignClass.getName() + "\u6ca1\u6709\u5173\u8054\u6570\u636e\u5e93\u8868\uff01\uff01");
            }
            DBAnno.DBLink link = null;
            if (this.links == null) {
                this.throwBeanParaException(t.getName() + "\u6ca1\u6709\u8bbe\u7f6e\u6570\u636e\u5e93\u5173\u8054\u5173\u7cfb\uff01\uff01");
            }
            for (DBAnno.DBLink l : this.links) {
                Class<?> c = l.linkWith();
                if (c != foreignClass) continue;
                link = l;
            }
            if (link == null) {
                DBAnno.DBLink[] foreignLinks = null;
                if (foreignClass.isAnnotationPresent(DBAnno.DBLink.class)) {
                    foreignLinks = (DBAnno.DBLink[])foreignClass.getAnnotationsByType(DBAnno.DBLink.class);
                } else if (foreignClass.isAnnotationPresent(DBAnno.DBLinks.class)) {
                    DBAnno.DBLinks[] annotationsByType = (DBAnno.DBLinks[])foreignClass.getAnnotationsByType(DBAnno.DBLinks.class);
                    foreignLinks = annotationsByType[0].value();
                }
                if (foreignLinks != null) {
                    for (final DBAnno.DBLink l : foreignLinks) {
                        Class<?> c = l.linkWith();
                        if (c != t) continue;
                        link = new DBAnno.DBLink(){

                            @Override
                            public Class<? extends Annotation> annotationType() {
                                return l.annotationType();
                            }

                            @Override
                            public Class<?> linkWith() {
                                return foreignClass;
                            }

                            @Override
                            public String linkField() {
                                return l.field();
                            }

                            @Override
                            public String field() {
                                return l.linkField();
                            }
                        };
                    }
                } else {
                    this.throwBeanParaException(foreignClass.getName() + "\u4e0e" + t + "\u6ca1\u6709\u8bbe\u7f6e\u5173\u8054\u5173\u7cfb\uff01\uff01");
                }
            }
            Field field = this.fieldMap.get(link.field());
            Field linkField = foreignClass.getDeclaredField(link.linkField());
            sql.append(this.tablename).append(".").append(field.getAnnotation(DBAnno.DBField.class).dbField().toUpperCase()).append(" in (select ").append(tableAnno.dbTable().toUpperCase()).append(".").append(linkField.getAnnotation(DBAnno.DBField.class).dbField().toUpperCase()).append(" from ").append(tableAnno.dbTable().toUpperCase());
            StringBuffer sqlWhere = new StringBuffer();
            ArrayList<Object> paraList = new ArrayList<Object>();
            if (filterAttrs2 != null) {
                for (int idx = 0; idx < filterAttrs2.length; ++idx) {
                    Field linkField1 = foreignClass.getDeclaredField(filterAttrs2[idx]);
                    annotation = linkField1.getAnnotation(DBAnno.DBField.class);
                    if (sqlWhere.length() == 0) {
                        sqlWhere.append(" where ");
                    } else {
                        sqlWhere.append(" and ");
                    }
                    sqlWhere.append(tableAnno.dbTable().toUpperCase()).append(".").append(annotation.dbField().toUpperCase()).append("=? ");
                    ReflectionUtils.makeAccessible(linkField1);
                    v = linkField1.get(obj);
                    if (v != null && StringUtils.hasLength(annotation.tranWith())) {
                        v = this.transValue(annotation.tranWith(), annotation.dbType(), v);
                    }
                    paraList.add(v);
                }
            }
            sql.append(sqlWhere).append(")");
            sqlWhere = new StringBuffer();
            if (filterAttrs1 != null) {
                for (int idx = 0; idx < filterAttrs1.length; ++idx) {
                    Field filterField = this.fieldMap.get(filterAttrs1[idx]);
                    annotation = filterField.getAnnotation(DBAnno.DBField.class);
                    sqlWhere.append(" and ");
                    sqlWhere.append(this.tablename).append(".").append(annotation.dbField().toUpperCase()).append("=? ");
                    v = values[idx];
                    if (v != null && StringUtils.hasLength(annotation.tranWith())) {
                        v = this.transValue(annotation.tranWith(), annotation.dbType(), v);
                    }
                    paraList.add(v);
                }
            }
            sql.append(sqlWhere);
            list = this.jdbcTemplate.query(con -> {
                PreparedStatement statement = con.prepareStatement(sql.toString());
                for (int i = 0; i < paraList.size(); ++i) {
                    statement.setObject(i + 1, paraList.get(i));
                }
                return statement;
            }, this.getRowMapper(t));
        }
        catch (DataAccessException e) {
            throw e;
        }
        catch (Exception e) {
            this.throwBeanParaException(e);
        }
        return list;
    }

    public <T> List<T> listByForeign(Object obj, String[] queryParams, Class<? extends T> t) throws BeanParaException {
        List list = new ArrayList();
        try {
            StringBuffer sql = new StringBuffer("select ");
            for (int i = 0; i < this.dbFieldList.size(); ++i) {
                if (i > 0) {
                    sql.append(",");
                }
                sql.append(this.tablename).append(".").append(this.dbFieldList.get(i));
            }
            sql.append(" from ").append(this.tablename).append(" where ");
            final Class<?> foreignClass = obj.getClass();
            DBAnno.DBTable tableAnno = foreignClass.getAnnotation(DBAnno.DBTable.class);
            if (tableAnno == null) {
                this.throwBeanParaException(foreignClass.getName() + "\u6ca1\u6709\u5173\u8054\u6570\u636e\u5e93\u8868\uff01\uff01");
            }
            DBAnno.DBLink link = null;
            if (this.links == null) {
                this.throwBeanParaException(t.getName() + "\u6ca1\u6709\u8bbe\u7f6e\u6570\u636e\u5e93\u5173\u8054\u5173\u7cfb\uff01\uff01");
            }
            for (DBAnno.DBLink l : this.links) {
                Class<?> c = l.linkWith();
                if (c != foreignClass) continue;
                link = l;
            }
            if (link == null) {
                DBAnno.DBLink[] foreignLinks = null;
                if (foreignClass.isAnnotationPresent(DBAnno.DBLink.class)) {
                    foreignLinks = (DBAnno.DBLink[])foreignClass.getAnnotationsByType(DBAnno.DBLink.class);
                } else if (foreignClass.isAnnotationPresent(DBAnno.DBLinks.class)) {
                    DBAnno.DBLinks[] annotationsByType = (DBAnno.DBLinks[])foreignClass.getAnnotationsByType(DBAnno.DBLinks.class);
                    foreignLinks = annotationsByType[0].value();
                }
                if (foreignLinks != null) {
                    for (final DBAnno.DBLink l : foreignLinks) {
                        Class<?> c = l.linkWith();
                        if (c != t) continue;
                        link = new DBAnno.DBLink(){

                            @Override
                            public Class<? extends Annotation> annotationType() {
                                return l.annotationType();
                            }

                            @Override
                            public Class<?> linkWith() {
                                return foreignClass;
                            }

                            @Override
                            public String linkField() {
                                return l.field();
                            }

                            @Override
                            public String field() {
                                return l.linkField();
                            }
                        };
                    }
                } else {
                    this.throwBeanParaException(foreignClass.getName() + "\u4e0e" + t + "\u6ca1\u6709\u8bbe\u7f6e\u5173\u8054\u5173\u7cfb\uff01\uff01");
                }
            }
            Field field = this.fieldMap.get(link.field());
            Field linkField = foreignClass.getDeclaredField(link.linkField());
            sql.append(this.tablename).append(".").append(field.getAnnotation(DBAnno.DBField.class).dbField().toUpperCase()).append(" in(select ").append(tableAnno.dbTable().toUpperCase()).append(".").append(linkField.getAnnotation(DBAnno.DBField.class).dbField().toUpperCase()).append(" from ").append(tableAnno.dbTable().toUpperCase());
            ArrayList<Object> listParam = new ArrayList<Object>();
            for (int i = 0; i < queryParams.length; ++i) {
                String queryParam = queryParams[i];
                Field linkField1 = foreignClass.getDeclaredField(queryParam);
                DBAnno.DBField annotation = linkField1.getAnnotation(DBAnno.DBField.class);
                if (i == 0) {
                    sql.append(" where ");
                } else {
                    sql.append(" and ");
                }
                sql.append(tableAnno.dbTable().toUpperCase()).append(".").append(annotation.dbField().toUpperCase());
                ReflectionUtils.makeAccessible(linkField1);
                Object v = linkField1.get(obj);
                if (v == null) {
                    sql.append(" is null ");
                    continue;
                }
                sql.append("=? ");
                if (StringUtils.hasLength(annotation.tranWith())) {
                    v = this.transValue(annotation.tranWith(), annotation.dbType(), v);
                }
                listParam.add(v);
            }
            sql.append(")");
            String order = "";
            if (StringUtils.hasLength(this.orderFieldName)) {
                Field orderField = this.fieldMap.get(this.orderFieldName);
                DBAnno.DBField dbField = orderField.getAnnotation(DBAnno.DBField.class);
                order = " order by " + dbField.dbField().toUpperCase();
            }
            sql.append(order);
            list = this.jdbcTemplate.query(con -> {
                PreparedStatement statement = con.prepareStatement(sql.toString());
                for (int i = 0; i < listParam.size(); ++i) {
                    statement.setObject(i + 1, listParam.get(i));
                }
                return statement;
            }, this.getRowMapper(t));
        }
        catch (DataAccessException e) {
            throw e;
        }
        catch (Exception e) {
            this.throwBeanParaException(e);
        }
        return list;
    }

    public int update(Object obj, String[] fieldNames, Object[] args) throws BeanParaException, DBParaException {
        int ret = 0;
        if (fieldNames == null || args == null || fieldNames.length == 0 || fieldNames.length != args.length) {
            throw new DBParaException();
        }
        try {
            StringBuffer sqlWhere = new StringBuffer();
            ArrayList<Object> listParas = new ArrayList<Object>();
            for (int i = 0; i < fieldNames.length; ++i) {
                String fieldName = fieldNames[i];
                Field field = this.fieldMap.get(this.dbFieldMap.get(fieldName));
                if (field == null) {
                    this.throwBeanParaException(fieldName + "\u5c5e\u6027\u4e0d\u6b63\u786e\uff01");
                }
                DBAnno.DBField fieldAnno = field.getAnnotation(DBAnno.DBField.class);
                if (sqlWhere.length() > 0) {
                    sqlWhere.append(" and ");
                }
                Object v = args[i];
                try {
                    if (fieldAnno.autoDate()) {
                        v = Calendar.getInstance().getTime();
                    }
                }
                catch (Exception e) {
                    this.throwBeanParaException(field.getName() + "\u81ea\u52a8\u586b\u5145\u5f53\u524d\u65f6\u95f4\u5931\u8d25\uff01");
                }
                if (null == v) {
                    sqlWhere.append(fieldAnno.dbField().toUpperCase()).append(" is null ");
                    continue;
                }
                sqlWhere.append(fieldAnno.dbField().toUpperCase()).append("=?");
                if (StringUtils.hasLength(fieldAnno.tranWith())) {
                    v = this.transValue(fieldAnno.tranWith(), fieldAnno.dbType(), v);
                }
                listParas.add(v);
            }
            String sql = new StringBuffer(this.updateSQL.toString()).append(" where ").append(sqlWhere).toString();
            ArrayList<Object> lst = new ArrayList<Object>();
            Object[] ov = new Object[]{};
            for (String dbFieldName : this.dbFieldList) {
                Object v = null;
                Field field = this.fieldMap.get(this.dbFieldMap.get(dbFieldName));
                DBAnno.DBField fieldAnno = field.getDeclaredAnnotation(DBAnno.DBField.class);
                if (fieldAnno.notUpdate() || fieldAnno.isPk()) continue;
                ReflectionUtils.makeAccessible(field);
                v = field.get(obj);
                if (StringUtils.hasLength(fieldAnno.get())) {
                    Method getMethod = this.methodMap.get(fieldAnno.get());
                    if (getMethod == null) {
                        this.throwBeanParaException("getMethod\u5b9a\u4e49\u6709\u8bef\uff01\uff01" + obj.getClass().getName() + "." + fieldAnno.get());
                    } else {
                        v = getMethod.invoke(obj, ov);
                    }
                }
                try {
                    if (fieldAnno.autoDate()) {
                        v = Calendar.getInstance().getTime();
                    }
                }
                catch (Exception e) {
                    this.throwBeanParaException(field.getName() + "\u81ea\u52a8\u586b\u5145\u5f53\u524d\u65f6\u95f4\u5931\u8d25\uff01");
                }
                if (v != null && StringUtils.hasLength(fieldAnno.tranWith())) {
                    v = this.transValue(fieldAnno.tranWith(), fieldAnno.dbType(), v);
                }
                lst.add(v);
            }
            for (int i = 0; i < listParas.size(); ++i) {
                lst.add(listParas.get(i));
            }
            this.logger.debug(sql);
            ret = this.jdbcTemplate.update(sql, lst.toArray(ov));
        }
        catch (DataAccessException e) {
            throw e;
        }
        catch (Exception e) {
            this.throwBeanParaException(e);
        }
        return ret;
    }

    private BeanParaException throwBeanParaException(Exception e) throws BeanParaException {
        throw new BeanParaException(e);
    }

    private BeanParaException throwBeanParaException(String message) throws BeanParaException {
        throw new BeanParaException(message);
    }

    protected <T> RowMapper<T> getRowMapper(Class<? extends T> t) {
        return (rs, rowNum) -> {
            Object obj = null;
            try {
                obj = t.newInstance();
                this.readRecord(rs, obj);
            }
            catch (BeanParaException | IllegalAccessException | InstantiationException e) {
                this.logger.error(((Throwable)e).getMessage(), (Throwable)e);
                throw new RuntimeException((Throwable)e);
            }
            return obj;
        };
    }

    private <T> ResultSetExtractor<T> resultSetExtractor(final Class<T> t) {
        return new ResultSetExtractor<T>(){

            public T extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    Object obj = null;
                    try {
                        obj = t.newInstance();
                        BaseDao.this.readRecord(rs, obj);
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return obj;
                }
                return null;
            }
        };
    }

    protected int update(String sql) {
        this.logger.debug(sql);
        return this.jdbcTemplate.update(sql);
    }

    protected int update(String sql, Object[] obj, int[] types) {
        this.logger.debug(sql);
        return this.jdbcTemplate.update(sql, obj, types);
    }

    protected <T> List<T> queryList(String sql, Object[] args, RowMapper<T> rowMapper) {
        this.logger.debug(sql);
        return this.jdbcTemplate.query(sql, args, rowMapper);
    }

    protected <T> List<T> queryForList(String sql, Object[] args, Class<T> elementType) {
        this.logger.debug(sql);
        return this.jdbcTemplate.queryForList(sql, args, elementType);
    }

    public <T> T queryForObject(String sql, Object[] args, Class<T> requiredType) {
        this.logger.debug(sql);
        return (T)this.jdbcTemplate.queryForObject(sql, args, requiredType);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean clobReadByStream() {
        DataSource dataSource = this.jdbcTemplate.getDataSource();
        Connection conn = DataSourceUtils.getConnection((DataSource)dataSource);
        IDatabase database = null;
        try {
            database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            boolean bl = database.isDatabase("polardb") || database.isDatabase("POSTGRESQL") || database.isDatabase("Informix");
            return bl;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)conn, (DataSource)dataSource);
        }
        return false;
    }
}

