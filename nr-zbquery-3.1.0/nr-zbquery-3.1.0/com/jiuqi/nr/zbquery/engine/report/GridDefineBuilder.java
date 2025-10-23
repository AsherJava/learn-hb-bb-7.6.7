/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.quickreport.builder.define.DimensionFieldDefine
 *  com.jiuqi.bi.quickreport.builder.define.DimensionGroupDefine
 *  com.jiuqi.bi.quickreport.builder.define.FieldDefine
 *  com.jiuqi.bi.quickreport.builder.define.FieldObject
 *  com.jiuqi.bi.quickreport.builder.define.GridDefine
 *  com.jiuqi.bi.quickreport.builder.define.GridSetting
 *  com.jiuqi.bi.quickreport.builder.define.MeasureFieldDefine
 *  com.jiuqi.bi.quickreport.builder.define.MeasureGroupDefine
 *  com.jiuqi.bi.quickreport.model.HierarchyMode
 *  com.jiuqi.bi.quickreport.model.OrderMode
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.zbquery.engine.report;

import com.jiuqi.bi.quickreport.builder.define.DimensionFieldDefine;
import com.jiuqi.bi.quickreport.builder.define.DimensionGroupDefine;
import com.jiuqi.bi.quickreport.builder.define.FieldDefine;
import com.jiuqi.bi.quickreport.builder.define.FieldObject;
import com.jiuqi.bi.quickreport.builder.define.GridDefine;
import com.jiuqi.bi.quickreport.builder.define.GridSetting;
import com.jiuqi.bi.quickreport.builder.define.MeasureFieldDefine;
import com.jiuqi.bi.quickreport.builder.define.MeasureGroupDefine;
import com.jiuqi.bi.quickreport.model.HierarchyMode;
import com.jiuqi.bi.quickreport.model.OrderMode;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.zbquery.engine.condStyle.ConditionStyleBuilder;
import com.jiuqi.nr.zbquery.engine.dataset.QueryDSModelBuilder;
import com.jiuqi.nr.zbquery.model.DimensionAttributeField;
import com.jiuqi.nr.zbquery.model.FieldGroup;
import com.jiuqi.nr.zbquery.model.FieldGroupType;
import com.jiuqi.nr.zbquery.model.HeaderMode;
import com.jiuqi.nr.zbquery.model.LayoutField;
import com.jiuqi.nr.zbquery.model.LinkField;
import com.jiuqi.nr.zbquery.model.LinkResourceNode;
import com.jiuqi.nr.zbquery.model.MagnitudeValue;
import com.jiuqi.nr.zbquery.model.OpenType;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryField;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.model.SumPosition;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.util.DimensionValueUtils;
import com.jiuqi.nr.zbquery.util.EnumTransfer;
import com.jiuqi.nr.zbquery.util.FullNameWrapper;
import com.jiuqi.nr.zbquery.util.QuerySystemOptionUtils;
import java.util.List;

public class GridDefineBuilder {
    private ZBQueryModel zbQueryModel;
    private QueryDSModelBuilder dsModelBuilder;
    private GridDefine gridDefine;
    private boolean isCol = false;
    private boolean hasColTiered = false;

    public GridDefineBuilder(ZBQueryModel zbQueryModel, QueryDSModelBuilder dsModelBuilder) {
        this.zbQueryModel = zbQueryModel;
        this.dsModelBuilder = dsModelBuilder;
    }

    public void build() {
        this.gridDefine = new GridDefine(new GridSetting());
        if (this.zbQueryModel.getQueryObjects().isEmpty()) {
            return;
        }
        this.buildRows();
        this.buildCols();
        this.buildBizkeyOrderField();
        this.buildSetting();
    }

    public GridDefine getGridDefine() {
        return this.gridDefine;
    }

    private void buildRows() {
        this.buildRowNum();
        this.isCol = false;
        List<LayoutField> layoutFields = this.zbQueryModel.getLayout().getRows();
        for (LayoutField layoutField : layoutFields) {
            this.gridDefine.getRows().add(this.buildFiled(layoutField));
        }
    }

    private void buildCols() {
        this.isCol = true;
        List<LayoutField> layoutFields = this.zbQueryModel.getLayout().getCols();
        for (LayoutField layoutField : layoutFields) {
            this.gridDefine.getCols().add(this.buildFiled(layoutField));
        }
    }

    private FieldObject buildFiled(LayoutField layoutField) {
        FieldObject filedObject = null;
        QueryObject _queryObject = this.dsModelBuilder.getQueryModelBuilder().getModelFinder().getQueryObject(layoutField.getFullName());
        switch (layoutField.getType()) {
            case DIMENSION: 
            case GROUP: {
                filedObject = this.buildGroupField((FieldGroup)_queryObject);
                break;
            }
            case DIMENSIONATTRIBUTE: {
                throw new RuntimeException("\u6682\u4e0d\u652f\u6301\u7ef4\u5c5e\u6027\u5b57\u6bb5");
            }
            case ZB: 
            case FORMULA: {
                filedObject = this.buildMeasureField((QueryField)_queryObject);
            }
        }
        this.wrapperAttachmentFileHyperLink(filedObject, _queryObject);
        this.coverHyperLinkMsg(filedObject, _queryObject.getFullName());
        return filedObject;
    }

    private void wrapperAttachmentFileHyperLink(FieldObject filedObject, QueryObject _queryObject) {
        if (_queryObject instanceof QueryField && EnumTransfer.isFileDataType((QueryField)_queryObject)) {
            LinkField hyperLinkField = new LinkField();
            hyperLinkField.setFullName(_queryObject.getFullName());
            hyperLinkField.setResourceNode(new LinkResourceNode());
            hyperLinkField.setOpenType(OpenType.ATTACHMENT);
            if (filedObject instanceof FieldDefine) {
                ((FieldDefine)filedObject).setHyperlinkInfo(hyperLinkField.toJSON());
            }
        }
    }

    private FieldObject buildGroupField(FieldGroup _fieldGroup) {
        boolean isDimGroup = _fieldGroup.getGroupType() == FieldGroupType.DIMENSION || _fieldGroup.getGroupType() == FieldGroupType.CHILDDIMENSION;
        Object group = isDimGroup ? new DimensionGroupDefine(this.getAlias(_fieldGroup.getFullName()), _fieldGroup.getDisplayTitle()) : new MeasureGroupDefine(this.getAlias(_fieldGroup.getFullName()), _fieldGroup.getDisplayTitle(), this.getAlias(_fieldGroup.getParent()));
        if (_fieldGroup.getChildren().size() == 0 && isDimGroup) {
            List<QueryField> dimAttrs = this.dsModelBuilder.getQueryModelBuilder().getModelFinder().getAdditionalFieldByParent(_fieldGroup.getFullName());
            for (QueryField dimAttr : dimAttrs) {
                FieldObject fieldObject = this.buildDimensionField(_fieldGroup, (DimensionAttributeField)dimAttr);
                this.wrapperAttachmentFileHyperLink(fieldObject, dimAttr);
                this.coverHyperLinkMsg(fieldObject, dimAttr.getFullName());
                group.getChildren().add(fieldObject);
            }
        } else {
            for (QueryObject _queryObject : _fieldGroup.getChildren()) {
                FieldObject fieldObject;
                if (_queryObject.getType() == QueryObjectType.GROUP) {
                    FieldGroup _fieldGroupChild = (FieldGroup)_queryObject;
                    if (_fieldGroupChild.getGroupType() == FieldGroupType.CHILDDIMENSION) {
                        if (!_fieldGroupChild.isVisible() || this.dsModelBuilder.getQueryModelBuilder().getModelFinder().getLayoutDimension(_fieldGroupChild.getFullName()) != null) continue;
                        for (QueryObject _queryObjectChild : _fieldGroupChild.getChildren()) {
                            fieldObject = this.buildDimensionField(_fieldGroupChild, (DimensionAttributeField)_queryObjectChild);
                            group.getChildren().add(fieldObject);
                        }
                        continue;
                    }
                    fieldObject = this.buildGroupField(_fieldGroupChild);
                } else if (_queryObject.getType() == QueryObjectType.DIMENSIONATTRIBUTE) {
                    QueryDimension queryDim;
                    fieldObject = this.buildDimensionField(_fieldGroup, (DimensionAttributeField)_queryObject);
                    if (this.isCol && _fieldGroup.getGroupType() == FieldGroupType.CHILDDIMENSION && DimensionValueUtils.isPeriodChildDim(queryDim = this.dsModelBuilder.getQueryModelBuilder().getModelFinder().getQueryDimension(_queryObject.getParent()))) {
                        ((DimensionFieldDefine)fieldObject).setOrderMode(OrderMode.ASC);
                    }
                } else {
                    fieldObject = this.buildMeasureField((QueryField)_queryObject);
                }
                this.wrapperAttachmentFileHyperLink(fieldObject, _queryObject);
                this.coverHyperLinkMsg(fieldObject, _queryObject.getFullName());
                group.getChildren().add(fieldObject);
            }
        }
        return group;
    }

    private FieldObject coverHyperLinkMsg(FieldObject object, String fullName) {
        LinkField hyperLinkField = this.dsModelBuilder.getQueryModelBuilder().getModelFinder().getHyperLinkField(fullName);
        if (hyperLinkField != null && object instanceof FieldDefine) {
            ((FieldDefine)object).setHyperlinkInfo(hyperLinkField.toJSON());
        }
        return object;
    }

    private FieldObject buildDimensionField(FieldGroup _fieldGroup, DimensionAttributeField _dimAttrField) {
        QueryDimension queryDimension = this.dsModelBuilder.getQueryModelBuilder().getModelFinder().getQueryDimension(_fieldGroup.getFullName());
        DimensionFieldDefine dimensionField = new DimensionFieldDefine(this.getAlias(_dimAttrField.getFullName()), _dimAttrField.getDisplayTitle(), this.getAlias(_dimAttrField.getParent()));
        dimensionField.setVisible(_dimAttrField.isVisible());
        dimensionField.setColWidth(this.getColWidth(_dimAttrField));
        if (this.zbQueryModel.getOption().getRowHeaderMode() != HeaderMode.LIST) {
            if (queryDimension.isTreeStructure()) {
                if (!this.isCol && _fieldGroup.isDisplayIndent()) {
                    dimensionField.setHierarchyMode(HierarchyMode.INDENTED);
                } else if (this.isCol && !this.hasColTiered && _fieldGroup.isDisplayTiered()) {
                    dimensionField.setHierarchyMode(HierarchyMode.TIERED);
                    dimensionField.setHierarchyLevel(2);
                    this.hasColTiered = true;
                } else if (!this.isCol && _fieldGroup.isDisplayTiered()) {
                    dimensionField.setHierarchyMode(HierarchyMode.TIERED);
                    dimensionField.setHierarchyLevel(2);
                }
            }
            dimensionField.setDisplaySum(_dimAttrField.isDisplaySum());
        }
        if (FullNameWrapper.isParentField(_dimAttrField)) {
            dimensionField.setDisplayExpand(false);
        }
        boolean isMaster = queryDimension != null && queryDimension.getDimensionType() == QueryDimensionType.MASTER;
        boolean sumNodeBold = isMaster && QuerySystemOptionUtils.isBoldSumNode() && !FullNameWrapper.isParentField(_dimAttrField);
        new ConditionStyleBuilder(this.dsModelBuilder, sumNodeBold).buildCondStyles((FieldDefine)dimensionField, _dimAttrField);
        return dimensionField;
    }

    private FieldObject buildMeasureField(QueryField _queryField) {
        MagnitudeValue queryMagnitude;
        MeasureFieldDefine measureField = new MeasureFieldDefine(this.getAlias(_queryField.getFullName()), _queryField.getDisplayTitle(), this.getAlias(_queryField.getParent()));
        measureField.setVisible(_queryField.isVisible());
        measureField.setDisplaySum(_queryField.isDisplaySum());
        measureField.setDisplayRatio(false);
        measureField.setColWidth(this.getColWidth(_queryField));
        if (_queryField.getType() == QueryObjectType.ZB) {
            measureField.setDisplayCode(_queryField.getName());
        }
        if ((queryMagnitude = _queryField.getQueryMagnitude()) != null && queryMagnitude != MagnitudeValue.NONE) {
            MagnitudeValue fieldMagnitude = _queryField.getFieldMagnitude();
            if (fieldMagnitude == null || fieldMagnitude == MagnitudeValue.NONE) {
                measureField.setMagnitudeValue((double)queryMagnitude.value());
            } else {
                measureField.setMagnitudeValue((double)queryMagnitude.value() / (double)fieldMagnitude.value());
            }
            measureField.setMagnitudeTitle(queryMagnitude.title());
        }
        new ConditionStyleBuilder(this.dsModelBuilder).buildCondStyles((FieldDefine)measureField, _queryField);
        return measureField;
    }

    private void buildBizkeyOrderField() {
        if (!this.zbQueryModel.getOption().isQueryDetailRecord()) {
            return;
        }
        String bizkeyOrder = this.dsModelBuilder.getQueryModelBuilder().getBizkeyOrder();
        if (bizkeyOrder == null) {
            return;
        }
        for (FieldObject field : this.gridDefine.getCols()) {
            QueryDimension dim;
            if (!(field instanceof DimensionGroupDefine) || (dim = this.dsModelBuilder.getQueryModelBuilder().getModelFinder().getLayoutDimension(field.getName())) == null || dim.getDimensionType() != QueryDimensionType.INNER) continue;
            return;
        }
        List rows = this.gridDefine.getRows();
        int lastDimIndex = -1;
        for (int i = rows.size() - 1; i >= 0; --i) {
            FieldObject obj = (FieldObject)rows.get(i);
            if (!(obj instanceof DimensionGroupDefine)) continue;
            lastDimIndex = i;
            break;
        }
        if (lastDimIndex != -1) {
            DimensionFieldDefine bizkeyOrderFiled = new DimensionFieldDefine(this.getAlias(bizkeyOrder), "\u6392\u5e8f\u5b57\u6bb5");
            bizkeyOrderFiled.getProperties().put("HIDE_IN_REPORT", true);
            ((DimensionGroupDefine)rows.get(lastDimIndex)).getChildren().add(bizkeyOrderFiled);
        }
    }

    private void buildRowNum() {
        if (this.zbQueryModel.getOption().getRowHeaderMode() == HeaderMode.LIST) {
            DimensionGroupDefine group = new DimensionGroupDefine("SYS_ROWNUM", "SYS_ROWNUM");
            DimensionFieldDefine field = new DimensionFieldDefine("SYS_ROWNUM", "SYS_ROWNUM");
            field.getProperties().put("HIDE_IN_REPORT", true);
            group.getChildren().add(field);
            this.gridDefine.getRows().add(group);
        }
    }

    private void buildSetting() {
        GridSetting gridSetting = this.gridDefine.getSetting();
        gridSetting.setTopSum(this.zbQueryModel.getOption().getSumPosition() == SumPosition.TOP);
        gridSetting.setMeasureVertical(this.zbQueryModel.getLayout().isZbVertical());
        gridSetting.setHeaderMode(EnumTransfer.toHeaderMode(this.zbQueryModel.getOption().getRowHeaderMode()));
        gridSetting.setLockRowHead(this.zbQueryModel.getOption().isLockRowHead());
        gridSetting.setFontSize(10.5f);
        gridSetting.setDisplayZBCode(this.zbQueryModel.getOption().isDisplayZBCode());
        gridSetting.setNullConvertMode(this.zbQueryModel.getOption().getNullDisplayMode());
        gridSetting.setZeroConvertMode(this.zbQueryModel.getOption().getZeroDisplayMode());
    }

    private String getAlias(String fullName) {
        if (StringUtils.isEmpty((String)fullName)) {
            return null;
        }
        String alias = this.dsModelBuilder.getQueryModelBuilder().getFullNameAliasMapper().get(fullName);
        return StringUtils.isNotEmpty((String)alias) ? alias : fullName;
    }

    private int getColWidth(QueryField queryField) {
        if (queryField.getColWidth() == -1) {
            if (queryField.getType() == QueryObjectType.DIMENSIONATTRIBUTE) {
                QueryDimension dim;
                if ("NAME".equals(queryField.getName()) && (dim = this.dsModelBuilder.getQueryModelBuilder().getModelFinder().getQueryDimension(queryField.getParent())).getDimensionType() == QueryDimensionType.MASTER) {
                    return QuerySystemOptionUtils.getMainDimensionValueWidth();
                }
                return QuerySystemOptionUtils.getDimensionValueWidth();
            }
            return QuerySystemOptionUtils.getZbValueWidth();
        }
        return queryField.getColWidth();
    }
}

