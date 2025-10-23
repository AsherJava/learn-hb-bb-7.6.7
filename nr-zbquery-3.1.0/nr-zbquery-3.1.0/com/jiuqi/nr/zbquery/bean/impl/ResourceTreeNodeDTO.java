/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonSubTypes
 *  com.fasterxml.jackson.annotation.JsonSubTypes$Type
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.jiuqi.nr.dataresource.DataResource
 *  com.jiuqi.nr.dataresource.DataResourceDefine
 *  com.jiuqi.nr.dataresource.DataResourceNode
 *  com.jiuqi.nr.dataresource.DimAttribute
 *  com.jiuqi.nr.dataresource.NodeType
 *  com.jiuqi.nr.dataresource.dto.DataResourceNodeDTO
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.period.common.utils.TimeDimField
 */
package com.jiuqi.nr.zbquery.bean.impl;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceNode;
import com.jiuqi.nr.dataresource.DimAttribute;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.dto.DataResourceNodeDTO;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.period.common.utils.TimeDimField;
import com.jiuqi.nr.zbquery.bean.facade.IResourceTreeNode;
import com.jiuqi.nr.zbquery.bean.impl.ZBFieldEx;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.util.ResTreeUtils;
import com.jiuqi.nr.zbquery.util.ZBQueryI18nUtils;
import org.springframework.util.ObjectUtils;

public class ResourceTreeNodeDTO
implements IResourceTreeNode {
    private String key;
    private String code;
    private String title;
    private int type;
    private String parentKey;
    private String order;
    private String dimKey;
    private String defineKey;
    private boolean indeterminate;
    private String dataTableKey;
    private String source;
    private String dw;
    private String dataSchemeKey;
    private boolean displayTiered;
    private String linkZb;
    @JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="type", visible=true)
    @JsonSubTypes(value={@JsonSubTypes.Type(value=ZBFieldEx.class, name="ZB")})
    private QueryObject queryObject;

    public ResourceTreeNodeDTO() {
    }

    public ResourceTreeNodeDTO(DataResourceNode dataResourceNode, IResourceTreeNode parentTreeNode) {
        this.key = ResTreeUtils.generateKey(dataResourceNode);
        this.code = dataResourceNode.getCode();
        this.title = dataResourceNode.getTitle();
        this.type = dataResourceNode.getType();
        this.parentKey = dataResourceNode.getParentKey();
        if (!ObjectUtils.isEmpty(parentTreeNode)) {
            this.parentKey = parentTreeNode.getKey();
            this.defineKey = parentTreeNode.getDefineKey();
            this.dw = parentTreeNode.getDw();
        }
        this.order = dataResourceNode.getOrder();
        this.dimKey = dataResourceNode.getDimKey();
        this.dataTableKey = dataResourceNode.getDataTableKey();
        this.source = dataResourceNode.getSource();
        this.dataSchemeKey = dataResourceNode.getDataSchemeKey();
        this.linkZb = dataResourceNode.getLinkZb();
    }

    public ResourceTreeNodeDTO(DataResourceDefine dataResourceDefine, IResourceTreeNode parentTreeNode) {
        this.key = ResTreeUtils.generateKey(dataResourceDefine);
        this.code = dataResourceDefine.getOrder();
        this.title = dataResourceDefine.getTitle();
        this.type = NodeType.TREE.getValue();
        this.parentKey = ResTreeUtils.generateKey(dataResourceDefine.getGroupKey(), NodeType.TREE_GROUP);
        if (!ObjectUtils.isEmpty(parentTreeNode)) {
            this.parentKey = parentTreeNode.getKey();
        }
        this.order = dataResourceDefine.getOrder();
        this.dimKey = dataResourceDefine.getDimKey();
        this.defineKey = dataResourceDefine.getKey();
        this.dw = dataResourceDefine.getDimKey();
        this.dataSchemeKey = parentTreeNode.getDataSchemeKey();
    }

    public ResourceTreeNodeDTO(DataField dataField, IResourceTreeNode resGroupNode) {
        this.key = ResTreeUtils.generateKey(dataField, resGroupNode);
        this.code = dataField.getCode();
        this.title = dataField.getTitle();
        this.order = dataField.getOrder();
        this.parentKey = resGroupNode.getKey();
        this.defineKey = resGroupNode.getDefineKey();
        switch (dataField.getDataFieldKind()) {
            case FIELD: 
            case BUILT_IN_FIELD: {
                this.type = NodeType.FIELD_LINK.getValue();
                break;
            }
            case FIELD_ZB: {
                this.type = NodeType.FIELD_ZB_LINK.getValue();
                break;
            }
            case TABLE_FIELD_DIM: {
                this.type = NodeType.TABLE_DIM_GROUP.getValue();
                break;
            }
            case PUBLIC_FIELD_DIM: {
                this.type = NodeType.DIM_GROUP.getValue();
            }
        }
        this.dataTableKey = dataField.getDataTableKey();
        this.source = dataField.getKey();
        this.dw = resGroupNode.getDw();
        this.dataSchemeKey = resGroupNode.getDataSchemeKey();
    }

    public ResourceTreeNodeDTO(DimAttribute dimAttribute, IResourceTreeNode dimGroupNode) {
        this.key = ResTreeUtils.generateKey(dimAttribute, dimGroupNode);
        this.code = dimAttribute.getCode();
        String srcTitle = dimAttribute.getTitle();
        if (ZBQueryI18nUtils.getMessage("zbquery.displayCode", new Object[0]).equalsIgnoreCase(srcTitle)) {
            srcTitle = ZBQueryI18nUtils.getMessage("zbquery.code", new Object[0]);
        }
        this.title = srcTitle;
        this.order = dimAttribute.getOrder();
        this.dimKey = dimAttribute.getDimKey();
        this.defineKey = dimAttribute.getResourceDefineKey();
        this.parentKey = dimGroupNode.getKey();
        this.dw = dimGroupNode.getDw();
        this.dataSchemeKey = dimGroupNode.getDataSchemeKey();
        this.type = 99;
    }

    public ResourceTreeNodeDTO(TimeDimField timeDimField, IResourceTreeNode dimGroupNode) {
        this.key = ResTreeUtils.generateKey(timeDimField, dimGroupNode);
        this.code = timeDimField.getName();
        this.title = timeDimField.getTitle();
        this.order = timeDimField.getTimeGranularity() == null ? null : timeDimField.getTimeGranularity().name();
        this.dimKey = dimGroupNode.getDimKey();
        this.defineKey = dimGroupNode.getDefineKey();
        this.parentKey = dimGroupNode.getKey();
        this.dw = dimGroupNode.getDw();
        this.dataSchemeKey = dimGroupNode.getDataSchemeKey();
        this.type = 99;
    }

    public ResourceTreeNodeDTO(DataResource dataResource) {
        this.key = ResTreeUtils.generateKey(dataResource);
        this.code = dataResource.getOrder();
        this.title = dataResource.getTitle();
        this.order = dataResource.getOrder();
        this.dimKey = dataResource.getDimKey();
        this.defineKey = dataResource.getResourceDefineKey();
        this.parentKey = dataResource.getParentKey();
        switch (dataResource.getResourceKind()) {
            case DIM_GROUP: {
                this.type = NodeType.DIM_GROUP.getValue();
                break;
            }
            case TABLE_DIM_GROUP: {
                this.type = NodeType.TABLE_DIM_GROUP.getValue();
                break;
            }
            case RESOURCE_GROUP: {
                this.type = NodeType.RESOURCE_GROUP.getValue();
            }
        }
        this.dataTableKey = dataResource.getDataTableKey();
        this.source = dataResource.getSource();
        this.dataSchemeKey = dataResource.getDataSchemeKey();
        this.linkZb = dataResource.getLinkZb();
    }

    public DataResourceNodeDTO change2DataResourceNode() {
        DataResourceNodeDTO dataResourceNodeDTO = new DataResourceNodeDTO();
        dataResourceNodeDTO.setKey(ResTreeUtils.getRealKey(this.getKey()));
        dataResourceNodeDTO.setCode(this.getCode());
        dataResourceNodeDTO.setTitle(this.getTitle());
        dataResourceNodeDTO.setType(this.getType());
        dataResourceNodeDTO.setParentKey(ResTreeUtils.getRealKey(this.getParentKey()));
        dataResourceNodeDTO.setDimKey(this.getDimKey());
        dataResourceNodeDTO.setOrder(this.getOrder());
        dataResourceNodeDTO.setDataTableKey(this.getDataTableKey());
        dataResourceNodeDTO.setSource(this.getSource());
        dataResourceNodeDTO.setDataSchemeKey(this.getDataSchemeKey());
        dataResourceNodeDTO.setLinkZb(this.getLinkZb());
        return dataResourceNodeDTO;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public QueryObject getQueryObject() {
        return this.queryObject;
    }

    public void setQueryObject(QueryObject queryObject) {
        this.queryObject = queryObject;
    }

    @Override
    public String getDimKey() {
        return this.dimKey;
    }

    public void setDimKey(String dimKey) {
        this.dimKey = dimKey;
    }

    @Override
    public String getDefineKey() {
        return this.defineKey;
    }

    public void setDefineKey(String defineKey) {
        this.defineKey = defineKey;
    }

    @Override
    public boolean isIndeterminate() {
        return this.indeterminate;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    @Override
    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String getSource() {
        return this.source;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    @Override
    public String getDw() {
        return this.dw;
    }

    @Override
    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public void setIndeterminate(boolean indeterminate) {
        this.indeterminate = indeterminate;
    }

    @Override
    public boolean isDisplayTiered() {
        return this.displayTiered;
    }

    public void setDisplayTiered(boolean displayTiered) {
        this.displayTiered = displayTiered;
    }

    @Override
    public String getLinkZb() {
        return this.linkZb;
    }

    public void setLinkZb(String linkZb) {
        this.linkZb = linkZb;
    }

    @Override
    public int compareTo(IResourceTreeNode o) {
        if (this.type == o.getType()) {
            return this.order.compareTo(o.getOrder());
        }
        return o.getType() - this.type;
    }

    public String toString() {
        return "ResourceTreeNodeDTO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", type=" + this.type + ", parentKey='" + this.parentKey + '\'' + '}';
    }
}

