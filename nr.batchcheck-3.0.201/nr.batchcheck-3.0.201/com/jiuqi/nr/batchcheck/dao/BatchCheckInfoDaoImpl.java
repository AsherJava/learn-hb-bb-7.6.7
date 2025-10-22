/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.batchcheck.dao;

import com.jiuqi.nr.batchcheck.bean.CheckParamImpl;
import com.jiuqi.nr.batchcheck.dao.IBatchCheckInfoDao;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BatchCheckInfoDaoImpl
implements IBatchCheckInfoDao {
    private static final Logger logger = LoggerFactory.getLogger(BatchCheckInfoDaoImpl.class);
    @Resource
    private JdbcTemplate template;
    private static final String TABLE_NAME = "batch_check_info";
    private static final String USER_ID = "user_id";
    private static final String ASYNC_TASK_ID = "async_task_id";
    private static final String CHECK_PARAM = "check_param";
    private static final String ALL_FIELD = "user_id,async_task_id,check_param";

    @Override
    public CheckParamImpl queryBatchCheckInfoByUserId(String userId) {
        String sql = String.format("select %s from %s where %s=?", ALL_FIELD, TABLE_NAME, USER_ID);
        Object[] args = new Object[]{userId};
        return (CheckParamImpl)this.template.query(sql, args, rs -> {
            if (rs.next()) {
                return this.buildCheckParamImpl(rs);
            }
            return null;
        });
    }

    private CheckParamImpl buildCheckParamImpl(ResultSet rs) {
        int index = 1;
        CheckParamImpl impl = null;
        byte[] bytes = null;
        try {
            String asyncTaskId = rs.getString(++index);
            bytes = rs.getBytes(++index);
            impl = (CheckParamImpl)this.getObject(bytes);
            impl.setAsyncTaskId(asyncTaskId);
        }
        catch (Exception e) {
            logger.warn("\u67e5\u627e\u4e0a\u6b21\u6279\u91cf\u5ba1\u6838\u4fe1\u606f\u914d\u7f6e\uff0c\u7c7b\u578b\u8f6c\u5316\u5931\u8d25");
        }
        return impl;
    }

    @Override
    public boolean addBatchCheckInfo(CheckParamImpl impl, String userId, String asyncTaskId) {
        byte[] dataBytes = null;
        try {
            dataBytes = this.getBytes(impl);
        }
        catch (IOException e) {
            logger.warn("\u63d2\u5165\u5ba1\u6838\u53c2\u6570\u914d\u7f6e\u4ea7\u751fIO\u9519\u8bef\uff01");
        }
        String sql = String.format("insert into %s (%s) values(?,?,?)", TABLE_NAME, ALL_FIELD);
        Object[] args = new Object[]{userId, asyncTaskId, dataBytes};
        int result = this.template.update(sql, args);
        return result > 0;
    }

    @Override
    public boolean updataBatchCheckInfo(CheckParamImpl impl, String userId, String asyncTaskId) {
        byte[] dataBytes = null;
        try {
            dataBytes = this.getBytes(impl);
        }
        catch (IOException e) {
            logger.warn("\u4fee\u6539\u5ba1\u6838\u53c2\u6570\u914d\u7f6e\u4ea7\u751fIO\u9519\u8bef\uff01");
        }
        String sql = String.format("update %s set %s=?,%s=? where %s=?", TABLE_NAME, ASYNC_TASK_ID, CHECK_PARAM, USER_ID);
        Object[] args = new Object[]{asyncTaskId, dataBytes, userId};
        int result = this.template.update(sql, args);
        return result > 0;
    }

    private byte[] getBytes(Object object) throws IOException {
        ByteArrayOutputStream byt = new ByteArrayOutputStream();
        ObjectOutputStream obj = new ObjectOutputStream(byt);
        obj.writeObject(object);
        return byt.toByteArray();
    }

    private Object getObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = null;
        objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object obj = objectInputStream.readObject();
        return obj;
    }
}

