/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.database.statement.AlterColumnStatement
 *  com.jiuqi.bi.database.statement.AlterType
 *  com.jiuqi.bi.database.statement.interpret.ISQLInterpretor
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.nr.zb.scheme.internal.dao.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.nr.zb.scheme.common.PropDataType;
import com.jiuqi.nr.zb.scheme.exception.ZbSchemeDBException;
import com.jiuqi.nr.zb.scheme.internal.dao.IPropInfoDao;
import com.jiuqi.nr.zb.scheme.internal.dao.impl.ZbSchemeBaseDao;
import com.jiuqi.nr.zb.scheme.internal.entity.PropInfoDO;
import com.jiuqi.nr.zb.scheme.internal.entity.PropLinkDO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class PropInfoDAO
extends ZbSchemeBaseDao<PropInfoDO>
implements IPropInfoDao {
    private static final Logger log = LoggerFactory.getLogger(PropInfoDAO.class);

    @Override
    public Class<PropInfoDO> getClz() {
        return PropInfoDO.class;
    }

    @Override
    public void insert(PropInfoDO prop) {
        try {
            super.insert(prop);
            this.alterPropField(Collections.singletonList(prop), AlterType.ADD);
        }
        catch (Exception e) {
            throw new ZbSchemeDBException(e);
        }
    }

    @Override
    public void update(PropInfoDO prop, boolean needUpdateColumn) {
        try {
            super.update(prop);
            if (needUpdateColumn) {
                this.alterPropField(Collections.singletonList(prop), AlterType.MODIFY);
            }
        }
        catch (Exception e) {
            throw new ZbSchemeDBException(e);
        }
    }

    @Override
    public void update(PropInfoDO prop) {
        this.update(prop, true);
    }

    @Override
    public void delete(PropInfoDO prop) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insert(List<PropInfoDO> data) {
        try {
            super.insert(data.toArray(new Object[0]));
            this.alterPropField(data, AlterType.ADD);
        }
        catch (Exception e) {
            throw new ZbSchemeDBException(e);
        }
    }

    @Override
    public void update(List<PropInfoDO> props) {
        this.update(props, true);
    }

    @Override
    public void update(List<PropInfoDO> props, boolean needUpdateColumn) {
        try {
            super.update(props.toArray(new Object[0]));
            if (needUpdateColumn) {
                this.alterPropField(props, AlterType.MODIFY);
            }
        }
        catch (Exception e) {
            throw new ZbSchemeDBException(e);
        }
    }

    @Override
    public void delete(List<PropInfoDO> props) {
        try {
            super.delete(props.stream().map(PropInfoDO::getKey).toArray(String[]::new));
            this.alterPropField(props, AlterType.DROP);
        }
        catch (Exception e) {
            throw new ZbSchemeDBException(e);
        }
    }

    private void alterPropField(List<PropInfoDO> props, AlterType type) throws SQLException, SQLInterpretException {
        DataSource dataSource = this.jdbcTemplate.getDataSource();
        Assert.notNull((Object)dataSource, "dataSource is null");
        try (Connection connection = dataSource.getConnection();){
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            ISQLInterpretor interpretor = database.createSQLInterpretor(connection);
            ArrayList sqls = new ArrayList();
            for (PropInfoDO prop : props) {
                log.debug("Generating alter column statement for prop: {}", (Object)prop.getKey());
                AlterColumnStatement statement = PropInfoDAO.getAlterColumnStatement(prop, type);
                sqls.addAll(interpretor.alterColumn(statement));
            }
            log.debug("Generated alter column sql: {}", (Object)sqls);
            this.jdbcTemplate.batchUpdate(sqls.toArray(new String[0]));
            log.debug("Batch update completed successfully");
        }
        catch (Exception e) {
            log.error("{} prop column error: {}", type.name(), e.getMessage(), e);
            throw e;
        }
    }

    @NotNull
    private static AlterColumnStatement getAlterColumnStatement(PropInfoDO prop, AlterType type) {
        AlterColumnStatement statement = new AlterColumnStatement("NR_ZB_INFO", type);
        LogicField field = new LogicField();
        statement.setColumnName(prop.getFieldName());
        statement.setNewColumn(field);
        field.setFieldName(prop.getFieldName());
        field.setFieldTitle(prop.getTitle());
        field.setDataType(prop.getDataType().getValue());
        field.setNullable(true);
        switch (prop.getDataType()) {
            case STRING: {
                field.setSize(prop.getPrecision().intValue());
                break;
            }
            case UUID: {
                field.setDataType(PropDataType.STRING.getValue());
                field.setSize(36);
                break;
            }
            case INTEGER: {
                field.setPrecision(prop.getPrecision().intValue());
                break;
            }
            case DOUBLE: {
                field.setRawType(8);
                field.setPrecision(prop.getPrecision().intValue());
                field.setScale(prop.getDecimal().intValue());
                break;
            }
            case BIG_DECIMAL: {
                field.setDataType(PropDataType.DOUBLE.getValue());
                field.setPrecision(prop.getPrecision().intValue());
                field.setScale(prop.getDecimal().intValue());
                break;
            }
            case DATETIME: {
                field.setDataTypeName(prop.getDataType().name());
                break;
            }
            case ATTACHMENT: 
            case BLOB: {
                field.setDataType(PropDataType.STRING.getValue());
                field.setSize(50);
                break;
            }
            case BOOLEAN: {
                field.setDataType(PropDataType.INTEGER.getValue());
                field.setPrecision(1);
                break;
            }
            case CLOB: {
                break;
            }
        }
        return statement;
    }

    @Override
    public List<PropInfoDO> listPropInfoInScheme(String schemeKey) {
        PropLinkDO schemePropDO = new PropLinkDO();
        schemePropDO.setSchemeKey(schemeKey);
        return super.listByForeign(schemePropDO, new String[]{"schemeKey"}, PropInfoDO.class);
    }

    @Override
    public PropInfoDO getByKeyFromPropLink(String prop) {
        PropLinkDO schemePropDO = new PropLinkDO();
        schemePropDO.setPropKey(prop);
        List<PropInfoDO> propInfoDOS = super.listByForeign(schemePropDO, new String[]{"propKey"}, PropInfoDO.class);
        if (propInfoDOS.isEmpty()) {
            return null;
        }
        return propInfoDOS.get(0);
    }
}

