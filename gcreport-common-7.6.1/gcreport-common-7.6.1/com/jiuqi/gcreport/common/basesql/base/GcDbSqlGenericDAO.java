/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.gcreport.common.basesql.base;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import java.io.Serializable;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.util.StringUtils;

public abstract class GcDbSqlGenericDAO<Entity extends DefaultTableEntity, ID extends Serializable>
extends AbstractEntDbSqlGenericDAO<Entity>
implements IDbSqlGenericDAO<Entity, ID> {
    public GcDbSqlGenericDAO(Class<Entity> entity) {
        super(entity);
        this.initTableName();
    }

    private void initTableName() {
        DBTable de = this.getEntityType().getAnnotation(DBTable.class);
        if (de != null && StringUtils.hasText(de.name())) {
            this.setTableName(de.name().toUpperCase());
        }
    }

    @Override
    public Serializable save(Entity entity) throws DataAccessException {
        if (entity.getId() == null) {
            entity.setId(UUIDUtils.newUUIDStr());
        }
        super.add(entity);
        return entity.getId();
    }

    @Override
    public List<Entity> saveAll(List<Entity> entities) throws DataAccessException {
        entities.forEach(f -> this.save(f));
        return entities;
    }

    public int update(Entity object) {
        return super.update(object);
    }

    @Override
    public void updateAll(List<Entity> entities) throws DataAccessException {
        entities.forEach(f -> this.update(f));
    }

    @Override
    public Entity get(ID id) {
        try {
            DefaultTableEntity newInstance = (DefaultTableEntity)this.getTableDeclarator().getEntityClass().newInstance();
            newInstance.setId((String)id);
            return (Entity)((DefaultTableEntity)super.selectByEntity((BaseEntity)newInstance));
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.common.basesql.orm.error"), e);
        }
    }

    @Override
    public List<Entity> loadAll() throws DataAccessException {
        try {
            DefaultTableEntity newInstance = (DefaultTableEntity)this.getTableDeclarator().getEntityClass().newInstance();
            return super.selectList((BaseEntity)newInstance);
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.common.basesql.orm.error"), e);
        }
    }
}

