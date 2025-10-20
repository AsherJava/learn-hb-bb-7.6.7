/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  javax.persistence.Transient
 */
package com.jiuqi.va.domain.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelIndex;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.springframework.util.StringUtils;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataModelDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    @Id
    @JsonProperty(index=1)
    private UUID id;
    @JsonProperty(index=2)
    private String name;
    @JsonProperty(index=3)
    private String title;
    @JsonProperty(index=4)
    private DataModelType.BizType biztype;
    @Transient
    @JsonProperty(index=5)
    private Integer subBiztype;
    @JsonProperty(index=6)
    private String groupcode;
    @JsonProperty(index=7)
    private String remark;
    @Transient
    @JsonProperty(index=8)
    private List<DataModelColumn> columns;
    @Transient
    @JsonProperty(index=9)
    private List<DataModelIndex> indexConsts;
    @Transient
    @JsonIgnore
    private boolean distributeByReplication = false;
    @Transient
    @JsonIgnore
    private boolean locked = false;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setLocked(boolean lock) {
        if (!this.locked && lock) {
            try {
                Map extInfo;
                if (this.indexConsts != null) {
                    this.indexConsts = Collections.unmodifiableList(this.indexConsts);
                }
                if (this.columns != null) {
                    this.columns = Collections.unmodifiableList(this.columns);
                    for (DataModelColumn dataModelColumn : this.columns) {
                        dataModelColumn.setLocked(true);
                    }
                }
                if ((extInfo = this.getExtInfo()) != null) {
                    this.setExtInfo(Collections.unmodifiableMap(extInfo));
                }
            }
            catch (Throwable throwable) {
            }
            finally {
                this.locked = true;
            }
        }
    }

    private void validation() {
        if (this.locked) {
            throw new RuntimeException("\u975e\u6cd5\u7684\u64cd\u4f5c\uff0c\u7981\u6b62\u4fee\u6539\u7f13\u5b58\u6570\u636e\uff01");
        }
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.validation();
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.validation();
        if (name != null) {
            name = name.toUpperCase();
        }
        if (StringUtils.hasText(name) && !name.matches("^[A-Z0-9_]{1,64}$")) {
            throw new RuntimeException("\u975e\u6cd5\u7684\u53c2\u6570");
        }
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.validation();
        this.title = title;
    }

    public DataModelType.BizType getBiztype() {
        return this.biztype;
    }

    public void setBiztype(DataModelType.BizType biztype) {
        this.validation();
        this.biztype = biztype;
    }

    public Integer getSubBiztype() {
        return this.subBiztype;
    }

    public void setSubBiztype(Integer subBiztype) {
        this.validation();
        this.subBiztype = subBiztype;
    }

    public String getGroupcode() {
        return this.groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.validation();
        this.groupcode = groupcode;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.validation();
        this.remark = remark;
    }

    public List<DataModelColumn> getColumns() {
        return this.columns;
    }

    public void setColumns(List<DataModelColumn> columns) {
        this.validation();
        this.columns = columns;
    }

    public DataModelColumn addColumn(String columnName) {
        this.validation();
        if (this.columns == null) {
            this.columns = new ArrayList<DataModelColumn>();
        }
        DataModelColumn col = new DataModelColumn();
        col.setColumnName(columnName);
        this.columns.add(col);
        return col;
    }

    public List<DataModelIndex> getIndexConsts() {
        return this.indexConsts;
    }

    public void setIndexConsts(List<DataModelIndex> indexConsts) {
        this.validation();
        this.indexConsts = indexConsts;
    }

    public DataModelIndex addIndex(String indexName) {
        this.validation();
        if (this.indexConsts == null) {
            this.indexConsts = new ArrayList<DataModelIndex>();
        }
        DataModelIndex indx = new DataModelIndex();
        indx.setIndexName(indexName);
        this.indexConsts.add(indx);
        return indx;
    }

    public boolean isDistributeByReplication() {
        return this.distributeByReplication;
    }

    public void setDistributeByReplication(boolean distributeByReplication) {
        this.distributeByReplication = distributeByReplication;
    }

    public void setExtInfo(Map<String, Object> extInfo) {
        this.validation();
        super.setExtInfo(extInfo);
    }

    public void addExtInfo(String key, Object value) {
        this.validation();
        super.addExtInfo(key, value);
    }

    public void putAll(Map<String, Object> extInfo) {
        this.validation();
        super.putAll(extInfo);
    }
}

