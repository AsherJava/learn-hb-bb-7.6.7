/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.common.DataLinkEditMode
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataLinkMappingDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.datacrud.impl;

import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.common.DataTypeConvert;
import com.jiuqi.nr.datacrud.impl.EnumLinkDTO;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataLinkMappingDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class MetaData
implements IMetaData {
    protected static final Logger logger = LoggerFactory.getLogger(MetaData.class);
    protected String linkKey;
    protected DataField dataField;
    protected IFMDMAttribute fmAttribute;
    protected DataLinkDefine linkDefine;
    protected List<DataLinkMappingDefine> linkMappings;
    protected EnumLinkDTO linkDTO;
    public static final int UN_SAVE_INDEX = -1;
    protected int index = -1;
    protected IAccessResult accessResult;
    protected IAccessResult writeAccessResult;
    protected String balanceExpression;
    protected List<DataFieldDeployInfo> deployInfos;
    protected boolean enumShow = false;
    protected String enumShowLink;

    @Override
    public String getLinkKey() {
        if (this.linkDefine != null) {
            return this.linkDefine.getKey();
        }
        return this.linkKey;
    }

    @Override
    public boolean isFieldLink() {
        if (this.getDataLinkDefine() == null) {
            return this.getDataField() != null;
        }
        if ("FLOATORDER".equals(this.getLinkKey())) {
            return true;
        }
        return this.getDataLinkDefine().getType() == DataLinkType.DATA_LINK_TYPE_FIELD || this.getDataLinkDefine().getType() == DataLinkType.DATA_LINK_TYPE_INFO;
    }

    @Override
    public boolean isFormulaLink() {
        if (this.getDataLinkDefine() == null) {
            return false;
        }
        return this.getDataLinkDefine().getType() == DataLinkType.DATA_LINK_TYPE_FORMULA;
    }

    @Override
    public boolean isFMDMLink() {
        if (this.getDataLinkDefine() == null) {
            return false;
        }
        return this.getDataLinkDefine().getType() == DataLinkType.DATA_LINK_TYPE_FMDM;
    }

    @Override
    public int getDataType() {
        DataField field = this.getDataField();
        IFMDMAttribute attribute = this.getFmAttribute();
        if (field != null) {
            return DataTypeConvert.dataFieldType2DataType(field.getDataFieldType().getValue());
        }
        if (attribute != null) {
            return DataTypeConvert.columnModelType2DataType(attribute.getColumnType().getValue());
        }
        return -2;
    }

    @Override
    public IFMDMAttribute getFmAttribute() {
        return this.fmAttribute;
    }

    public void setFmAttribute(IFMDMAttribute fmAttribute) {
        this.fmAttribute = fmAttribute;
    }

    @Override
    public String getFieldKey() {
        if (this.dataField != null) {
            return this.dataField.getKey();
        }
        if (this.fmAttribute != null) {
            return this.fmAttribute.getID();
        }
        return this.getLinkKey();
    }

    @Override
    public String getCode() {
        if (this.dataField != null) {
            return this.dataField.getCode();
        }
        if (this.fmAttribute != null) {
            return this.fmAttribute.getCode();
        }
        return null;
    }

    @Override
    public DataField getDataField() {
        return this.dataField;
    }

    @Override
    public DataLinkDefine getDataLinkDefine() {
        return this.linkDefine;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    public void setLinkMappings(List<DataLinkMappingDefine> linkMappings) {
        this.linkMappings = linkMappings;
    }

    public MetaData() {
    }

    public MetaData(DataLinkDefine linkDefine) {
        this.linkDefine = linkDefine;
    }

    public MetaData(DataField dataField, DataLinkDefine linkDefine) {
        this.dataField = dataField;
        this.linkDefine = linkDefine;
    }

    public MetaData(String linkKey, DataField dataField) {
        this.linkKey = linkKey;
        this.dataField = dataField;
    }

    @Override
    public IAccessResult canRead() {
        return this.accessResult;
    }

    public void setAccessResult(IAccessResult accessResult) {
        this.accessResult = accessResult;
    }

    @Override
    public IAccessResult canWrite() {
        return this.writeAccessResult;
    }

    public void setWriteAccessResult(IAccessResult writeAccessResult) {
        this.writeAccessResult = writeAccessResult;
    }

    public void addDataLinkMappingDefine(DataLinkMappingDefine linkMapDefine) {
        if (this.linkMappings == null) {
            this.linkMappings = new ArrayList<DataLinkMappingDefine>();
        }
        this.linkMappings.add(linkMapDefine);
    }

    @Override
    public EnumLinkDTO getEnumLink() {
        Map enumLinkageData;
        if (this.dataField == null) {
            return null;
        }
        if (this.linkDTO != null) {
            return this.linkDTO;
        }
        EnumLinkDTO enumLink = null;
        if (!CollectionUtils.isEmpty(this.linkMappings)) {
            enumLink = new EnumLinkDTO();
            for (DataLinkMappingDefine linkMapping : this.linkMappings) {
                String leftDataLinkKey = linkMapping.getLeftDataLinkKey();
                String rightDataLinkKey = linkMapping.getRightDataLinkKey();
                enumLink.setType("1");
                enumLink.setLink(this.getLinkKey());
                if (this.getLinkKey().equals(leftDataLinkKey) && !enumLink.getNextLinks().contains(rightDataLinkKey)) {
                    enumLink.getNextLinks().add(rightDataLinkKey);
                    continue;
                }
                if (this.getLinkKey().equals(rightDataLinkKey) && !enumLink.getPreLinks().contains(rightDataLinkKey)) {
                    enumLink.getPreLinks().add(leftDataLinkKey);
                    continue;
                }
                return null;
            }
        }
        if (!CollectionUtils.isEmpty(enumLinkageData = this.linkDefine.getEnumLinkageData()) && "2".equals(this.linkDefine.getEnumLinkageMethod())) {
            if (enumLink == null) {
                enumLink = new EnumLinkDTO();
            }
            List<String> nextLinks = enumLink.getNextLinks();
            List<String> preLinks = enumLink.getPreLinks();
            for (String index : enumLinkageData.keySet()) {
                if (!this.getLinkKey().equals(enumLinkageData.get(index))) continue;
                enumLink.setType("2");
                enumLink.setLevel(index);
                enumLink.setLink(this.getLinkKey());
                try {
                    String pre;
                    String next = (String)enumLinkageData.get(Integer.parseInt(index) + 1 + "");
                    if (next != null) {
                        nextLinks.add(next);
                    }
                    if ((pre = (String)enumLinkageData.get(Integer.parseInt(index) - 1 + "")) != null) {
                        preLinks.add(pre);
                    }
                }
                catch (Exception e) {
                    logger.warn("\u679a\u4e3e\u7ea7\u6b21\u7684\u8054\u52a8\u67e5\u627e\u53d1\u751f\u9519\u8bef", e);
                    return null;
                }
                if (!nextLinks.isEmpty() || !preLinks.isEmpty()) continue;
                logger.warn("\u679a\u4e3e\u8054\u52a8\u6570\u636e\u4e0d\u5408\u6cd5 {}", (Object)enumLinkageData);
                return null;
            }
        }
        this.linkDTO = enumLink;
        return this.linkDTO;
    }

    @Override
    public boolean isEnumType() {
        String entityKey;
        if (this.getDataField() != null ? StringUtils.hasLength(entityKey = this.getDataField().getRefDataEntityKey()) : this.getFmAttribute() != null && StringUtils.hasLength(entityKey = this.getFmAttribute().getReferEntityId())) {
            return this.getDataLinkDefine().getEditMode() != DataLinkEditMode.DATA_LINK_INPUT;
        }
        return false;
    }

    @Override
    public String getEntityId() {
        if (this.isEnumType()) {
            if (this.getDataField() != null) {
                return this.getDataField().getRefDataEntityKey();
            }
            if (this.getFmAttribute() != null) {
                return this.getFmAttribute().getReferEntityId();
            }
        }
        return null;
    }

    @Override
    public DataFieldGatherType getGatherType() {
        DataFieldGatherType gatherType;
        if (this.getDataField() != null && (gatherType = this.getDataField().getDataFieldGatherType()) != null) {
            return gatherType;
        }
        return DataFieldGatherType.NONE;
    }

    @Override
    public DataFieldType getDataFieldType() {
        if (this.getDataField() != null) {
            return this.getDataField().getDataFieldType();
        }
        if (this.getFmAttribute() != null) {
            ColumnModelType columnType = this.getFmAttribute().getColumnType();
            return DataTypeConvert.convertDataType(columnType);
        }
        return null;
    }

    @Override
    public boolean isNullAble() {
        if (this.getFmAttribute() != null) {
            return this.getFmAttribute().isNullAble();
        }
        if (this.getDataLinkDefine() != null) {
            return Boolean.TRUE.equals(this.getDataLinkDefine().getAllowNullAble());
        }
        if (this.getDataField() != null) {
            return Boolean.TRUE.equals(this.getDataField().getNullable());
        }
        return true;
    }

    @Override
    public String getBalanceExpression() {
        return this.balanceExpression;
    }

    public void setBalanceExpression(String balanceExpression) {
        this.balanceExpression = balanceExpression;
    }

    @Override
    public List<DataFieldDeployInfo> getDeployInfos() {
        if (this.deployInfos == null) {
            return Collections.emptyList();
        }
        return this.deployInfos;
    }

    @Override
    public boolean notProcessValue() {
        String measureUnit;
        DataLinkDefine dataLinkDefine = this.getDataLinkDefine();
        if (dataLinkDefine != null && StringUtils.hasLength(measureUnit = dataLinkDefine.getMeasureUnit()) && measureUnit.endsWith("NotDimession")) {
            return true;
        }
        DataField dif = this.getDataField();
        if (dif != null) {
            String measureUnit2 = dif.getMeasureUnit();
            return StringUtils.hasLength(measureUnit2) && measureUnit2.endsWith("NotDimession");
        }
        return false;
    }

    @Override
    public boolean isEnumShow() {
        return this.enumShow;
    }

    @Override
    public String getEnumShowLink() {
        return this.enumShowLink;
    }

    @Override
    public boolean isSensitive() {
        if (this.dataField != null) {
            return StringUtils.hasLength(this.dataField.getDataMaskCode());
        }
        return false;
    }

    public void setEnumShowLink(String enumShowLink) {
        this.enumShowLink = enumShowLink;
    }

    public void setEnumShow(boolean enumShow) {
        this.enumShow = enumShow;
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
        MetaData metaData = (MetaData)o;
        return Objects.equals(this.getLinkKey(), metaData.getLinkKey());
    }

    public int hashCode() {
        return this.getLinkKey() != null ? this.getLinkKey().hashCode() : 0;
    }

    public String toString() {
        return "MetaData{linkKey='" + this.linkKey + '\'' + ", dataField=" + this.dataField + ", fmAttribute=" + this.fmAttribute + '}';
    }
}

