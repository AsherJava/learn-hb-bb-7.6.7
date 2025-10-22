/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.StatUnit
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 */
package com.jiuqi.nr.datacrud.impl;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.StatUnit;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.impl.DataValue;
import com.jiuqi.nr.datacrud.impl.GroupMetaData;
import com.jiuqi.nr.datacrud.impl.gather.StatUnitGetter;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroupDataValue
extends DataValue {
    private static final Logger logger = LoggerFactory.getLogger(GroupDataValue.class);
    private StatUnit statUnit;
    private IMetaData metaData;

    @Override
    public IMetaData getMetaData() {
        if (this.metaData == null) {
            return super.getMetaData();
        }
        return this.metaData;
    }

    @Override
    public void setMetaData(IMetaData metaData) {
        this.metaData = metaData;
    }

    public GroupDataValue(IMetaData metaDatum) {
        super(metaDatum, null);
        DataFieldGatherType gatherType = metaDatum.getGatherType();
        this.statUnit = StatUnitGetter.createStatUnit(metaDatum.getDataType(), gatherType.getValue());
        this.metaData = new GroupMetaData(metaDatum);
    }

    public GroupDataValue(IMetaData metaDatum, int statKind) {
        super(metaDatum, null);
        this.statUnit = StatUnitGetter.createStatUnitByStat(metaDatum.getDataType(), statKind);
        this.metaData = new GroupMetaData(metaDatum);
    }

    public void writeValue(AbstractData value) {
        try {
            this.statUnit.statistic(value);
        }
        catch (DataTypeException e) {
            logger.warn("\u6c47\u603b\u6570\u636e\u53d1\u751f\u9519\u8bef\uff0c\u6b64\u6c47\u603b\u6570\u636e\u53ef\u80fd\u4e0d\u6b63\u786e", e);
        }
    }

    public GroupDataValue gatherValue() {
        this.data = this.statUnit.getResult();
        this.statUnit = null;
        return this;
    }
}

