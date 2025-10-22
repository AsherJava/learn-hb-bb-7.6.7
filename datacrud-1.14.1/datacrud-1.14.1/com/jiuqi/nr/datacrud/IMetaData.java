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
package com.jiuqi.nr.datacrud;

import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.datacrud.impl.EnumLinkDTO;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import java.util.List;

public interface IMetaData {
    public String getFieldKey();

    public String getCode();

    public DataField getDataField();

    public IFMDMAttribute getFmAttribute();

    public DataLinkDefine getDataLinkDefine();

    public String getLinkKey();

    public boolean isFieldLink();

    public boolean isFormulaLink();

    public boolean isFMDMLink();

    public int getDataType();

    public int getIndex();

    public void setIndex(int var1);

    public IAccessResult canRead();

    public IAccessResult canWrite();

    public EnumLinkDTO getEnumLink();

    public boolean isEnumType();

    public String getEntityId();

    public DataFieldGatherType getGatherType();

    public DataFieldType getDataFieldType();

    public boolean isNullAble();

    public String getBalanceExpression();

    public List<DataFieldDeployInfo> getDeployInfos();

    public boolean notProcessValue();

    public boolean isEnumShow();

    public String getEnumShowLink();

    public boolean isSensitive();
}

