/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.data.common.param.EntityValue
 *  com.jiuqi.nr.data.common.service.FileWriter
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.fielddatacrud.FieldQueryInfoBuilder
 *  com.jiuqi.nr.fielddatacrud.FieldSort
 *  com.jiuqi.nr.fielddatacrud.IFieldQueryInfo
 */
package com.jiuqi.nr.data.text;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.data.common.param.EntityValue;
import com.jiuqi.nr.data.common.service.FileWriter;
import com.jiuqi.nr.data.text.IFieldFileParam;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.fielddatacrud.FieldQueryInfoBuilder;
import com.jiuqi.nr.fielddatacrud.FieldSort;
import com.jiuqi.nr.fielddatacrud.IFieldQueryInfo;
import java.util.Iterator;

public class FieldFileParamBuilder {
    private FieldQueryInfoBuilder fieldQueryInfoBuilder;
    private String fileName = "FIELD_DATA";
    private boolean expZip = true;
    private EntityValue entityValue;
    private boolean expBizKey = false;
    private FileWriter fileWriter;
    private boolean isExportFile = true;
    private int exportCount = 0;

    public static FieldFileParamBuilder create(DimensionCollection dimension) {
        return new FieldFileParamBuilder(dimension);
    }

    public FieldFileParamBuilder select(String fieldKey) {
        this.fieldQueryInfoBuilder.select(fieldKey);
        return this;
    }

    public FieldFileParamBuilder where(RowFilter filter) {
        this.fieldQueryInfoBuilder.where(filter);
        return this;
    }

    public FieldFileParamBuilder orderBy(FieldSort fieldSort) {
        this.fieldQueryInfoBuilder.orderBy(fieldSort);
        return this;
    }

    public FieldFileParamBuilder addVariable(Variable variable) {
        this.fieldQueryInfoBuilder.addVariable(variable);
        return this;
    }

    public FieldFileParamBuilder setPage(PageInfo page) {
        this.fieldQueryInfoBuilder.setPage(page);
        return this;
    }

    public FieldFileParamBuilder setPage(int rowsPerPage, int pageIndex) {
        this.fieldQueryInfoBuilder.setPage(rowsPerPage, pageIndex);
        return this;
    }

    public void setDesensitized(boolean desensitized) {
        this.fieldQueryInfoBuilder.setDesensitized(desensitized);
    }

    public FieldFileParamBuilder setAuthType(ResouceType type) {
        this.fieldQueryInfoBuilder.setAuthType(type);
        return this;
    }

    public FieldFileParamBuilder setExpFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public FieldFileParamBuilder setExpZip(boolean expZip) {
        this.expZip = expZip;
        return this;
    }

    public FieldFileParamBuilder setEntityValue(EntityValue entityValue) {
        this.entityValue = entityValue;
        return this;
    }

    public void setExpBizKey(boolean expBizKey) {
        this.expBizKey = expBizKey;
    }

    public void setFileWriter(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    public void setExportFile(boolean exportFile) {
        this.isExportFile = exportFile;
    }

    private FieldFileParamBuilder(DimensionCollection dimensionCollection) {
        this.fieldQueryInfoBuilder = FieldQueryInfoBuilder.create((DimensionCollection)dimensionCollection);
        this.fieldQueryInfoBuilder.setDesensitized(false);
    }

    public IFieldFileParam build() {
        final IFieldQueryInfo fieldQueryInfo = this.fieldQueryInfoBuilder.build();
        return new IFieldFileParam(){

            public DimensionCollection getDimensionCollection() {
                return fieldQueryInfo.getDimensionCollection();
            }

            public Iterator<String> selectFieldItr() {
                return fieldQueryInfo.selectFieldItr();
            }

            public Iterator<RowFilter> rowFilterItr() {
                return fieldQueryInfo.rowFilterItr();
            }

            public Iterator<FieldSort> fieldSortItr() {
                return fieldQueryInfo.fieldSortItr();
            }

            public Iterator<Variable> variableItr() {
                return fieldQueryInfo.variableItr();
            }

            public PageInfo getPageInfo() {
                return fieldQueryInfo.getPageInfo();
            }

            public ResouceType getAuthMode() {
                return fieldQueryInfo.getAuthMode();
            }

            public boolean isDesensitized() {
                return fieldQueryInfo.isDesensitized();
            }

            @Override
            public String getFileName() {
                return FieldFileParamBuilder.this.fileName;
            }

            @Override
            public boolean isExpZip() {
                return FieldFileParamBuilder.this.expZip;
            }

            @Override
            public EntityValue getEntityValue() {
                return FieldFileParamBuilder.this.entityValue;
            }

            @Override
            public boolean expBizKey() {
                return FieldFileParamBuilder.this.expBizKey;
            }

            @Override
            public FileWriter getFileWriter() {
                return FieldFileParamBuilder.this.fileWriter;
            }

            @Override
            public boolean expFile() {
                return FieldFileParamBuilder.this.isExportFile;
            }

            @Override
            public int getExportCount() {
                return FieldFileParamBuilder.this.exportCount;
            }
        };
    }
}

