/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.zbquery.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.zbquery.model.ConditionField;
import com.jiuqi.nr.zbquery.model.ConditionStyleField;
import com.jiuqi.nr.zbquery.model.DimensionAttributeField;
import com.jiuqi.nr.zbquery.model.FieldGroup;
import com.jiuqi.nr.zbquery.model.FieldGroupType;
import com.jiuqi.nr.zbquery.model.FormulaField;
import com.jiuqi.nr.zbquery.model.LayoutField;
import com.jiuqi.nr.zbquery.model.LinkField;
import com.jiuqi.nr.zbquery.model.OrderField;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryField;
import com.jiuqi.nr.zbquery.model.QueryLayout;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.model.ZBField;
import com.jiuqi.nr.zbquery.model.ZBFieldType;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.util.FullNameWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.util.Assert;

public class QueryModelFinder {
    private ZBQueryModel queryModel;
    private Map<String, QueryObject> queryObjects = new HashMap<String, QueryObject>();
    private Set<String> layoutQueryObjects = new HashSet<String>();
    private List<String> layoutQueryDimensions = new ArrayList<String>();
    private Map<String, LinkField> hyperLinkFields = new HashMap<String, LinkField>();
    private List<QueryField> additionalFields = new ArrayList<QueryField>();
    private List<QueryDimension> virtualDimensions = new ArrayList<QueryDimension>();
    private Map<String, String> childDimensions = new HashMap<String, String>();
    private Map<String, ConditionStyleField> conditionStyleFields = new HashMap<String, ConditionStyleField>();

    public QueryModelFinder(ZBQueryModel queryModel) {
        Assert.notNull((Object)queryModel, "queryModel must not be null");
        this.queryModel = queryModel;
        this.init();
    }

    private void init() {
        for (QueryObject obj : this.queryModel.getQueryObjects()) {
            this.initQueryObject(obj);
        }
        for (LinkField hyperlinkField : this.queryModel.getHyperlinks()) {
            this.hyperLinkFields.put(hyperlinkField.getFullName(), hyperlinkField);
        }
        for (QueryDimension dim : this.queryModel.getDimensions()) {
            if (!dim.isVirtualDimension()) continue;
            this.virtualDimensions.add(dim);
        }
        for (LayoutField layoutFiled : this.getAllLayoutFields()) {
            this.initLayoutQueryObject(this.getQueryObject(layoutFiled.getFullName()));
            this.initLayoutQueryDimensions(layoutFiled);
        }
        for (ConditionStyleField condStyleField : this.queryModel.getConditionStyles()) {
            this.conditionStyleFields.put(condStyleField.getFullName(), condStyleField);
        }
    }

    private void initQueryObject(QueryObject obj) {
        this.queryObjects.put(obj.getFullName(), obj);
        if (obj.getType() == QueryObjectType.GROUP) {
            DimensionAttributeField parentAttr;
            FieldGroup group = (FieldGroup)obj;
            if (group.getGroupType() == FieldGroupType.CHILDDIMENSION && (parentAttr = group.getDimAttribute()) != null) {
                this.queryObjects.put(parentAttr.getFullName(), parentAttr);
                this.childDimensions.put(parentAttr.getFullName(), group.getFullName());
            }
            for (QueryObject _obj : group.getChildren()) {
                this.initQueryObject(_obj);
            }
        }
    }

    private void initLayoutQueryObject(QueryObject obj) {
        if (obj == null) {
            return;
        }
        if (obj.getType() == QueryObjectType.GROUP) {
            if (((FieldGroup)obj).getGroupType() == FieldGroupType.CHILDDIMENSION && ((FieldGroup)obj).getDimAttribute() != null) {
                this.layoutQueryObjects.add(((FieldGroup)obj).getDimAttribute().getFullName());
            }
            for (QueryObject _obj : ((FieldGroup)obj).getChildren()) {
                if (!_obj.isVisible()) continue;
                this.initLayoutQueryObject(_obj);
            }
        } else {
            this.layoutQueryObjects.add(obj.getFullName());
        }
    }

    private void initLayoutQueryDimensions(LayoutField layoutFiled) {
        if (layoutFiled.getType() != QueryObjectType.DIMENSION) {
            return;
        }
        FieldGroup group = (FieldGroup)this.getQueryObject(layoutFiled.getFullName());
        if (group.getGroupType() == FieldGroupType.DIMENSION || group.getGroupType() == FieldGroupType.CHILDDIMENSION) {
            this.layoutQueryDimensions.add(group.getFullName());
        }
    }

    public ZBQueryModel getQueryModel() {
        return this.queryModel;
    }

    public QueryObject getQueryObject(String fullName) {
        return this.queryObjects.get(fullName);
    }

    public LinkField getHyperLinkField(String fullName) {
        return this.hyperLinkFields.get(fullName);
    }

    public QueryDimension getQueryDimension(String fullName) {
        for (QueryDimension dim : this.queryModel.getDimensions()) {
            if (!dim.getFullName().equals(fullName)) continue;
            return dim;
        }
        for (QueryDimension dim : this.virtualDimensions) {
            FullNameWrapper wrapper = new FullNameWrapper(QueryObjectType.ZB, dim.getFullName());
            if (!wrapper.getTableName().equals(fullName)) continue;
            return dim;
        }
        return null;
    }

    public QueryDimension getMasterDimension() {
        QueryDimension first = null;
        for (QueryDimension dim : this.queryModel.getDimensions()) {
            if (dim.getDimensionType() != QueryDimensionType.MASTER) continue;
            if (first == null) {
                first = dim;
            }
            if (this.getQueryObject(dim.getFullName()) == null) continue;
            return dim;
        }
        return first;
    }

    public QueryDimension getPeriodDimension() {
        for (QueryDimension dim : this.queryModel.getDimensions()) {
            if (dim.getDimensionType() != QueryDimensionType.PERIOD || !dim.isVisible() || this.getQueryObject(dim.getFullName()) == null) continue;
            return dim;
        }
        return null;
    }

    public List<QueryDimension> getPeriodDimensions() {
        ArrayList<QueryDimension> dims = new ArrayList<QueryDimension>();
        for (QueryDimension dim : this.queryModel.getDimensions()) {
            if (dim.getDimensionType() != QueryDimensionType.PERIOD || !dim.isVisible()) continue;
            dims.add(dim);
        }
        return dims;
    }

    public QueryDimension getChildDimension(String parentAtrrFullName) {
        if (this.childDimensions.containsKey(parentAtrrFullName)) {
            return this.getQueryDimension(this.childDimensions.get(parentAtrrFullName));
        }
        return null;
    }

    public List<LayoutField> getAllLayoutFields() {
        QueryLayout layout = this.queryModel.getLayout();
        ArrayList<LayoutField> fields = new ArrayList<LayoutField>();
        fields.addAll(layout.getRows());
        fields.addAll(layout.getCols());
        return fields;
    }

    public QueryObject getLayoutQueryObject(String fullName) {
        if (this.layoutQueryObjects.contains(fullName)) {
            return this.getQueryObject(fullName);
        }
        return null;
    }

    public List<ZBField> getLayoutZBFields(ZBFieldType zbFieldType) {
        ArrayList<ZBField> zbFields = new ArrayList<ZBField>();
        for (String fullName : this.layoutQueryObjects) {
            QueryObject queryObject = this.queryObjects.get(fullName);
            if (queryObject == null || queryObject.getType() != QueryObjectType.ZB) continue;
            ZBField zbField = (ZBField)queryObject;
            if (zbFieldType != null && zbField.getZbType() != zbFieldType) continue;
            zbFields.add(zbField);
        }
        return zbFields;
    }

    public List<FormulaField> getLayoutFormulaFields() {
        ArrayList<FormulaField> formulaFields = new ArrayList<FormulaField>();
        for (String fullName : this.layoutQueryObjects) {
            QueryObject queryObject = this.queryObjects.get(fullName);
            if (queryObject == null || queryObject.getType() != QueryObjectType.FORMULA) continue;
            FormulaField formulaField = (FormulaField)queryObject;
            formulaFields.add(formulaField);
        }
        return formulaFields;
    }

    public List<QueryDimension> getLayoutDimensions() {
        ArrayList<QueryDimension> dims = new ArrayList<QueryDimension>();
        for (String fullName : this.layoutQueryDimensions) {
            dims.add(this.getQueryDimension(fullName));
        }
        return dims;
    }

    public QueryDimension getLayoutDimension(String fullName) {
        if (this.layoutQueryDimensions.contains(fullName)) {
            return this.getQueryDimension(fullName);
        }
        return null;
    }

    public int getLayoutIndexOnRow(String fullName) {
        List<LayoutField> rowFields = this.queryModel.getLayout().getRows();
        for (int i = 0; i < rowFields.size(); ++i) {
            LayoutField rowField = rowFields.get(i);
            if (rowField.getFullName().equals(fullName)) {
                return i;
            }
            if (rowField.getType() != QueryObjectType.GROUP && rowField.getType() != QueryObjectType.DIMENSION || !this.existQueryObject((FieldGroup)this.getQueryObject(rowField.getFullName()), fullName)) continue;
            return i;
        }
        return -1;
    }

    public boolean existColLayoutDimension() {
        for (LayoutField colField : this.queryModel.getLayout().getCols()) {
            if (colField.getType() != QueryObjectType.DIMENSION) continue;
            return true;
        }
        return false;
    }

    public boolean existLayoutZBOrFormula() {
        for (String fullName : this.layoutQueryObjects) {
            QueryObject queryObject = this.queryObjects.get(fullName);
            if (queryObject == null || queryObject.getType() != QueryObjectType.ZB && queryObject.getType() != QueryObjectType.FORMULA) continue;
            return true;
        }
        return false;
    }

    private boolean existQueryObject(FieldGroup group, String fullName) {
        for (QueryObject queryObject : group.getChildren()) {
            if (queryObject.getFullName().equals(fullName)) {
                return true;
            }
            if (queryObject.getType() != QueryObjectType.GROUP) continue;
            return this.existQueryObject((FieldGroup)queryObject, fullName);
        }
        return false;
    }

    public void addAdditionalField(QueryField queryField) {
        if (!this.queryObjects.containsKey(queryField.getFullName())) {
            this.additionalFields.add(queryField);
            this.queryObjects.put(queryField.getFullName(), queryField);
        }
    }

    public QueryField getAdditionalField(String fullName) {
        for (QueryField queryField : this.additionalFields) {
            if (!queryField.getFullName().equals(fullName)) continue;
            return queryField;
        }
        return null;
    }

    public List<QueryField> getAdditionalFieldByParent(String parent) {
        ArrayList<QueryField> queryFields = new ArrayList<QueryField>();
        if (StringUtils.isNotEmpty((String)parent)) {
            for (QueryField queryField : this.additionalFields) {
                if (!parent.equals(queryField.getParent())) continue;
                queryFields.add(queryField);
            }
        }
        return queryFields;
    }

    public OrderField getOrderField(String fullName) {
        for (OrderField orderField : this.queryModel.getOrders()) {
            if (!orderField.getFullName().equals(fullName)) continue;
            return orderField;
        }
        return null;
    }

    public ConditionField getConditionField(String fullName) {
        for (ConditionField c : this.queryModel.getConditions()) {
            if (!c.getFullName().equals(fullName)) continue;
            return c;
        }
        return null;
    }

    public ConditionStyleField getConditionStyleField(String fullName) {
        return this.conditionStyleFields.get(fullName);
    }
}

