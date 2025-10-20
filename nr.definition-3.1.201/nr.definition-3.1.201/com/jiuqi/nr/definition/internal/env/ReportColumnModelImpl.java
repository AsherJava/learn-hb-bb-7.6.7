/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.common.ApplyType
 *  com.jiuqi.nvwa.definition.common.ColumnModelKind
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.common.ApplyType;
import com.jiuqi.nvwa.definition.common.ColumnModelKind;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import org.springframework.util.StringUtils;

public class ReportColumnModelImpl
implements ColumnModelDefine {
    private final DataField dataField;
    private String referTableID;
    private String referColumnID;

    public ReportColumnModelImpl(DataField dataField) {
        this.dataField = dataField;
    }

    public String getTableID() {
        return this.dataField.getDataTableKey();
    }

    public String getName() {
        return this.dataField.getCode();
    }

    public String getTitle() {
        return this.dataField.getTitle();
    }

    public String getDesc() {
        return this.dataField.getDesc();
    }

    public String getCatagory() {
        return null;
    }

    public ColumnModelType getColumnType() {
        return this.dataField.getDataFieldType().toColumnModelType();
    }

    public int getPrecision() {
        return this.dataField.getPrecision() == null ? 0 : this.dataField.getPrecision();
    }

    public int getDecimal() {
        return this.dataField.getDecimal() == null ? 0 : this.dataField.getDecimal();
    }

    public boolean isNullAble() {
        return this.dataField.isNullable();
    }

    public String getDefaultValue() {
        return null;
    }

    public String getReferTableID() {
        if (StringUtils.hasText(this.dataField.getRefDataEntityKey())) {
            if (StringUtils.hasText(this.referTableID)) {
                return this.referTableID;
            }
            this.initReferParam();
            return this.referTableID;
        }
        return null;
    }

    private void initReferParam() {
        IEntityMetaService entityMetaService = BeanUtil.getBean(IEntityMetaService.class);
        TableModelDefine tableModel = entityMetaService.getTableModel(this.dataField.getRefDataEntityKey());
        if (tableModel != null) {
            this.referTableID = tableModel.getID();
            this.referColumnID = tableModel.getBizKeys();
        }
    }

    public String getReferColumnID() {
        if (StringUtils.hasText(this.dataField.getRefDataEntityKey())) {
            if (StringUtils.hasText(this.referColumnID)) {
                return this.referColumnID;
            }
            this.initReferParam();
            return this.referColumnID;
        }
        return null;
    }

    public String getFilter() {
        return null;
    }

    public boolean isMultival() {
        return false;
    }

    public AggrType getAggrType() {
        return null;
    }

    public ApplyType getApplyType() {
        return null;
    }

    public String getShowFormat() {
        return null;
    }

    public String getMeasureUnit() {
        return null;
    }

    public ColumnModelKind getKind() {
        return null;
    }

    public double getOrder() {
        return 0.0;
    }

    public String getLocaleTitle() {
        return this.getTitle();
    }

    public String getID() {
        return this.dataField.getKey();
    }

    public String getCode() {
        return this.dataField.getCode();
    }

    public String getSceneId() {
        return null;
    }

    public void setID(String id) {
    }

    public void setTableID(String tableID) {
    }

    public void setCode(String code) {
    }

    public void setName(String name) {
    }

    public void setTitle(String title) {
    }

    public void setDesc(String desc) {
    }

    public void setCatagory(String catagory) {
    }

    public void setColumnType(ColumnModelType type) {
    }

    public void setPrecision(int precision) {
    }

    public void setDecimal(int decimal) {
    }

    public void setNullAble(boolean nullAble) {
    }

    public void setDefaultValue(String defaultValue) {
    }

    public void setReferTableID(String referTableID) {
    }

    public void setReferColumnID(String referColumnID) {
    }

    public void setFilter(String filter) {
    }

    public void setMultival(boolean multival) {
    }

    public void setAggrType(AggrType aggrType) {
    }

    public void setApplyType(ApplyType applyType) {
    }

    public void setShowFormat(String showFormat) {
    }

    public void setMeasureUnit(String measureUnit) {
    }

    public void setKind(ColumnModelKind kind) {
    }

    public void setOrder(double order) {
    }

    public void setSceneId(String sceneId) {
    }
}

