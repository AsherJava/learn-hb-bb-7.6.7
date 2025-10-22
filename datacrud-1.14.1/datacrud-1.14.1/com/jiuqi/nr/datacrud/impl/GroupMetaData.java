/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.datacrud.impl;

import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.impl.EnumLinkDTO;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import java.util.List;

public class GroupMetaData
extends MetaData {
    protected IMetaData metaData;
    protected DataFieldType dataFieldType;

    public GroupMetaData(IMetaData metaDatum) {
        this.metaData = metaDatum;
        DataFieldGatherType gatherType = this.metaData.getGatherType();
        switch (gatherType) {
            case NONE: {
                this.setDataFieldType(DataFieldType.STRING);
                break;
            }
            case COUNT: 
            case DISTINCT_COUNT: {
                this.setDataFieldType(DataFieldType.INTEGER);
                break;
            }
        }
    }

    private GroupMetaData() {
    }

    @Override
    public String getLinkKey() {
        return this.metaData.getLinkKey();
    }

    @Override
    public IFMDMAttribute getFmAttribute() {
        return this.metaData.getFmAttribute();
    }

    @Override
    public DataField getDataField() {
        return this.metaData.getDataField();
    }

    @Override
    public DataLinkDefine getDataLinkDefine() {
        return this.metaData.getDataLinkDefine();
    }

    @Override
    public EnumLinkDTO getEnumLink() {
        return this.metaData.getEnumLink();
    }

    @Override
    public DataFieldType getDataFieldType() {
        if (this.dataFieldType == null) {
            return this.metaData.getDataFieldType();
        }
        return this.dataFieldType;
    }

    public void setDataFieldType(DataFieldType dataFieldType) {
        this.dataFieldType = dataFieldType;
    }

    @Override
    public boolean isFieldLink() {
        return this.metaData.isFieldLink();
    }

    @Override
    public boolean isFormulaLink() {
        return this.metaData.isFormulaLink();
    }

    @Override
    public boolean isFMDMLink() {
        return this.metaData.isFMDMLink();
    }

    @Override
    public int getDataType() {
        return this.metaData.getDataType();
    }

    @Override
    public String getFieldKey() {
        return this.metaData.getFieldKey();
    }

    @Override
    public String getCode() {
        return this.metaData.getCode();
    }

    @Override
    public IAccessResult canRead() {
        return this.metaData.canRead();
    }

    @Override
    public IAccessResult canWrite() {
        return this.metaData.canWrite();
    }

    @Override
    public boolean isEnumType() {
        return this.metaData.isEnumType();
    }

    @Override
    public String getEntityId() {
        return this.metaData.getEntityId();
    }

    @Override
    public DataFieldGatherType getGatherType() {
        return this.metaData.getGatherType();
    }

    @Override
    public boolean isNullAble() {
        return this.metaData.isNullAble();
    }

    @Override
    public String getBalanceExpression() {
        return this.metaData.getBalanceExpression();
    }

    @Override
    public List<DataFieldDeployInfo> getDeployInfos() {
        return this.metaData.getDeployInfos();
    }

    @Override
    public boolean notProcessValue() {
        return this.metaData.notProcessValue();
    }

    @Override
    public boolean isEnumShow() {
        return this.metaData.isEnumShow();
    }

    @Override
    public String getEnumShowLink() {
        return this.metaData.getEnumShowLink();
    }
}

