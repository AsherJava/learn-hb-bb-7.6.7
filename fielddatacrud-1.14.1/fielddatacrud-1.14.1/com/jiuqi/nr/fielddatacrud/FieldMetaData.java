/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.common.DataTypeConvert
 *  com.jiuqi.nr.datacrud.impl.EnumLinkDTO
 *  com.jiuqi.nr.datacrud.impl.MetaData
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.fielddatacrud;

import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.common.DataTypeConvert;
import com.jiuqi.nr.datacrud.impl.EnumLinkDTO;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class FieldMetaData
implements IMetaData {
    protected static final Logger logger = LoggerFactory.getLogger(MetaData.class);
    protected DataField dataField;
    protected int index = -1;
    protected List<DataFieldDeployInfo> deployInfos;

    public String getLinkKey() {
        return null;
    }

    public boolean isFieldLink() {
        DataField dataField = this.getDataField();
        return dataField != null;
    }

    public boolean isFormulaLink() {
        return false;
    }

    public boolean isFMDMLink() {
        return false;
    }

    public int getDataType() {
        DataField field = this.getDataField();
        if (field != null) {
            return DataTypeConvert.dataFieldType2DataType((int)field.getDataFieldType().getValue());
        }
        return -2;
    }

    public IFMDMAttribute getFmAttribute() {
        throw new UnsupportedOperationException();
    }

    public String getFieldKey() {
        if (this.dataField != null) {
            return this.dataField.getKey();
        }
        return null;
    }

    public String getCode() {
        if (this.dataField != null) {
            return this.dataField.getCode();
        }
        return null;
    }

    public DataField getDataField() {
        return this.dataField;
    }

    public DataLinkDefine getDataLinkDefine() {
        return null;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public FieldMetaData() {
    }

    public FieldMetaData(DataField dataField) {
        this.dataField = dataField;
    }

    public IAccessResult canRead() {
        return null;
    }

    public IAccessResult canWrite() {
        return null;
    }

    public EnumLinkDTO getEnumLink() {
        throw new UnsupportedOperationException();
    }

    public boolean isEnumType() {
        if (this.getDataField() != null) {
            String entityKey = this.getDataField().getRefDataEntityKey();
            return StringUtils.hasLength(entityKey);
        }
        return false;
    }

    public String getEntityId() {
        if (this.isEnumType() && this.getDataField() != null) {
            return this.getDataField().getRefDataEntityKey();
        }
        return null;
    }

    public DataFieldGatherType getGatherType() {
        DataFieldGatherType gatherType;
        if (this.getDataField() != null && (gatherType = this.getDataField().getDataFieldGatherType()) != null) {
            return gatherType;
        }
        return DataFieldGatherType.NONE;
    }

    public DataFieldType getDataFieldType() {
        if (this.getDataField() != null) {
            return this.getDataField().getDataFieldType();
        }
        return null;
    }

    public boolean isNullAble() {
        if (this.getDataField() != null) {
            return Boolean.TRUE.equals(this.getDataField().getNullable());
        }
        return true;
    }

    public String getBalanceExpression() {
        throw new UnsupportedOperationException();
    }

    public List<DataFieldDeployInfo> getDeployInfos() {
        if (this.deployInfos == null) {
            return Collections.emptyList();
        }
        return this.deployInfos;
    }

    public boolean notProcessValue() {
        DataField dataField = this.getDataField();
        if (dataField != null) {
            String measureUnit = dataField.getMeasureUnit();
            return StringUtils.hasLength(measureUnit) && measureUnit.endsWith("NotDimession");
        }
        return false;
    }

    public boolean isEnumShow() {
        throw new UnsupportedOperationException();
    }

    public String getEnumShowLink() {
        throw new UnsupportedOperationException();
    }

    public boolean isSensitive() {
        return false;
    }

    public void setDeployInfos(List<DataFieldDeployInfo> deployInfos) {
        this.deployInfos = deployInfos;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        FieldMetaData metaData = (FieldMetaData)o;
        return Objects.equals(this.getCode(), metaData.getCode());
    }

    public int hashCode() {
        return this.getCode() != null ? this.getCode().hashCode() : 0;
    }

    public String toString() {
        return "FieldMetaData{dataField=" + this.dataField + ", index=" + this.index + ", deployInfos=" + this.deployInfos + '}';
    }
}

