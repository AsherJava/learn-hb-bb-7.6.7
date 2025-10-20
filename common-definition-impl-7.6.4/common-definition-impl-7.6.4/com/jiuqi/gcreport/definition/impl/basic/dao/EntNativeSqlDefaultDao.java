/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.gcreport.definition.impl.basic.dao;

import com.jiuqi.gcreport.definition.impl.basic.base.sql.EntSqlTool;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.EntPreparedStatementSetter;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.EntResultSetExtractor;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.dao.internal.EntDaoCacheManager;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntNativeSqlDefaultDao<Entity extends BaseEntity>
extends AbstractEntDbSqlGenericDAO<Entity> {
    private EntNativeSqlDefaultDao(Class<Entity> clz) {
        super(clz, (FEntSqlTemplate)SpringBeanUtils.getBean(FEntSqlTemplate.class));
    }

    private EntNativeSqlDefaultDao(Class<Entity> clz, String tableName) {
        super(clz, (FEntSqlTemplate)SpringBeanUtils.getBean(FEntSqlTemplate.class));
        this.setTableName(tableName);
    }

    public static EntNativeSqlDefaultDao<DefaultTableEntity> getInstance() {
        return new EntNativeSqlDefaultDao<DefaultTableEntity>(DefaultTableEntity.class);
    }

    public static <T extends BaseEntity> EntNativeSqlDefaultDao<T> newInstance(String tableName, Class<T> clz) {
        EntNativeSqlDefaultDao<T> dao = new EntNativeSqlDefaultDao<T>(clz, tableName);
        EntDaoCacheManager manager = (EntDaoCacheManager)SpringBeanUtils.getBean(EntDaoCacheManager.class);
        manager.initGenericDAO(dao);
        return dao;
    }

    public void saveOrUpdate(Entity obj) {
        Entity e = this.selectByEntity(obj);
        if (e != null) {
            this.updateSelective(obj);
        } else {
            this.addSelective(obj);
        }
    }

    public <T> List<T> selectListAssignResultExtractor(String sql, EntResultSetExtractor<T> result, Object ... param) {
        ArrayList<Object> paramList = new ArrayList<Object>();
        if (param != null) {
            Collections.addAll(paramList, param);
        }
        return this.selectListAssignResultExtractor(sql, result, paramList);
    }

    private <T> List<T> selectListAssignResultExtractor(String sql, EntResultSetExtractor<T> result, List<Object> param) {
        return this.getEntSqlTemplate().queryByPaging(EntSqlTool.newDqlInstance(sql, param, result), -1, -1);
    }

    public <T> T selectEntityAssignResultExtractor(String sql, EntPreparedStatementSetter setter, EntResultSetExtractor<T> result) {
        return this.getEntSqlTemplate().query(EntSqlTool.newDqlInstance(sql, setter, result));
    }
}

