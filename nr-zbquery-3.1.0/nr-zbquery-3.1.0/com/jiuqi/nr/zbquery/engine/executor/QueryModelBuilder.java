/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.query.model.DimFieldDescriptor
 *  com.jiuqi.bi.query.model.MeasureDescriptor
 *  com.jiuqi.bi.query.model.QueryModel
 *  com.jiuqi.bi.query.model.SortDescriptor
 *  com.jiuqi.bi.query.model.SortMode
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.bql.interpret.BiAdapTransResult
 *  com.jiuqi.nr.bql.interpret.BiAdaptFormulaParser
 *  com.jiuqi.nr.bql.interpret.BiAdaptParam
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  org.apache.shiro.util.CollectionUtils
 */
package com.jiuqi.nr.zbquery.engine.executor;

import com.jiuqi.bi.query.model.DimFieldDescriptor;
import com.jiuqi.bi.query.model.MeasureDescriptor;
import com.jiuqi.bi.query.model.QueryModel;
import com.jiuqi.bi.query.model.SortDescriptor;
import com.jiuqi.bi.query.model.SortMode;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.bql.interpret.BiAdapTransResult;
import com.jiuqi.nr.bql.interpret.BiAdaptFormulaParser;
import com.jiuqi.nr.bql.interpret.BiAdaptParam;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.zbquery.extend.IZBQueryExtendProvider;
import com.jiuqi.nr.zbquery.extend.ZBQueryExtendProviderManager;
import com.jiuqi.nr.zbquery.model.ConditionField;
import com.jiuqi.nr.zbquery.model.ConditionOperationItem;
import com.jiuqi.nr.zbquery.model.ConditionType;
import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nr.zbquery.model.CustomFilter;
import com.jiuqi.nr.zbquery.model.DefaultValueMode;
import com.jiuqi.nr.zbquery.model.DimensionAttributeField;
import com.jiuqi.nr.zbquery.model.FieldGroup;
import com.jiuqi.nr.zbquery.model.FieldGroupType;
import com.jiuqi.nr.zbquery.model.FilterField;
import com.jiuqi.nr.zbquery.model.FilterType;
import com.jiuqi.nr.zbquery.model.FormulaField;
import com.jiuqi.nr.zbquery.model.FormulaType;
import com.jiuqi.nr.zbquery.model.HeaderMode;
import com.jiuqi.nr.zbquery.model.HiddenDimension;
import com.jiuqi.nr.zbquery.model.LayoutField;
import com.jiuqi.nr.zbquery.model.MagnitudeValue;
import com.jiuqi.nr.zbquery.model.NullRowDisplayMode;
import com.jiuqi.nr.zbquery.model.OrderField;
import com.jiuqi.nr.zbquery.model.OrderMode;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryField;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.model.QueryOption;
import com.jiuqi.nr.zbquery.model.TableHeaderOrderField;
import com.jiuqi.nr.zbquery.model.ZBField;
import com.jiuqi.nr.zbquery.model.ZBFieldType;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.util.BiAdaptParamUtils;
import com.jiuqi.nr.zbquery.util.ConditionExpGenerator;
import com.jiuqi.nr.zbquery.util.DimensionValueUtils;
import com.jiuqi.nr.zbquery.util.FullNameWrapper;
import com.jiuqi.nr.zbquery.util.PeriodUtil;
import com.jiuqi.nr.zbquery.util.QueryModelFinder;
import com.jiuqi.nr.zbquery.util.ZBQueryI18nUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.shiro.util.CollectionUtils;

public class QueryModelBuilder {
    private ZBQueryModel zbQueryModel;
    private ConditionValues conditionValues;
    private QueryModelFinder modelFinder;
    private Map<String, String> aliasFullNameMapper;
    private Map<String, String> fullNameAliasMapper;
    private Map<String, String> keyColMapper;
    private Map<String, String> parentColMapper;
    private Map<String, List<String>> childDimFields;
    private List<String> finalQueryFieldAliases;
    private Set<String> detailZBMeasureAliases;
    private String bizkeyOrder = null;
    private String startPeriod = null;
    private String endPeriod = null;
    private String maxPeriod = null;
    private String calibreFilter = null;
    private BiAdaptParam biAdaptParam = null;
    private QueryModel _queryModel;
    private IZBQueryExtendProvider extendProvider = ((ZBQueryExtendProviderManager)SpringBeanUtils.getBean(ZBQueryExtendProviderManager.class)).getExtendProvider();
    private BiAdaptFormulaParser biAdaptFormulaParser = (BiAdaptFormulaParser)SpringBeanUtils.getBean(BiAdaptFormulaParser.class);
    private IRuntimeDataSchemeService dataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
    private Map<String, String> sortDimMapper = new HashMap<String, String>();

    public QueryModelBuilder(ZBQueryModel zbQueryModel, ConditionValues conditionValues) {
        this.zbQueryModel = zbQueryModel;
        this.conditionValues = conditionValues;
    }

    public void build() throws Exception {
        this.init();
        this.check();
        this.buildDimFields();
        this.buildMeasures();
        this.buildConditions();
        this.buildSorts();
        this.buildFroms();
        this.buildOptions();
    }

    public QueryModel getQueryModel() {
        return this._queryModel;
    }

    public QueryModelFinder getModelFinder() {
        return this.modelFinder;
    }

    public Map<String, String> getAliasFullNameMapper() {
        return this.aliasFullNameMapper;
    }

    public Map<String, String> getFullNameAliasMapper() {
        return this.fullNameAliasMapper;
    }

    public Map<String, String> getKeyColMapper() {
        return this.keyColMapper;
    }

    public Map<String, String> getParentColMapper() {
        return this.parentColMapper;
    }

    public Map<String, List<String>> getChildDimFields() {
        return this.childDimFields;
    }

    public List<String> getFinalQueryFieldAliases() {
        return this.finalQueryFieldAliases;
    }

    public Set<String> getDetailZBMeasureAliases() {
        return this.detailZBMeasureAliases;
    }

    private void init() {
        this.modelFinder = new QueryModelFinder(this.zbQueryModel);
        this.aliasFullNameMapper = new HashMap<String, String>();
        this.fullNameAliasMapper = new HashMap<String, String>();
        this.keyColMapper = new HashMap<String, String>();
        this.parentColMapper = new HashMap<String, String>();
        this.childDimFields = new HashMap<String, List<String>>();
        this.finalQueryFieldAliases = new ArrayList<String>();
        this.detailZBMeasureAliases = new HashSet<String>();
        this.bizkeyOrder = null;
        this.startPeriod = null;
        this.endPeriod = null;
        this.calibreFilter = null;
        this._queryModel = new QueryModel();
        if (this.conditionValues == null) {
            this.conditionValues = new ConditionValues();
        }
    }

    private void check() throws Exception {
        int dimCount = 0;
        boolean hasZB = false;
        for (LayoutField layoutField : this.zbQueryModel.getLayout().getRows()) {
            if (layoutField.getType() == QueryObjectType.DIMENSION) {
                ++dimCount;
                continue;
            }
            if (hasZB || !this.checkZB(layoutField)) continue;
            hasZB = true;
        }
        boolean hasColZB = false;
        for (LayoutField field : this.zbQueryModel.getLayout().getCols()) {
            if (field.getType() == QueryObjectType.DIMENSION) {
                if (hasColZB) {
                    throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.cannotQuery002", new Object[0]));
                }
                ++dimCount;
                continue;
            }
            if (hasColZB || !this.checkZB(field)) continue;
            hasColZB = true;
            hasZB = true;
        }
        if (!hasZB) {
            if (dimCount == 1) {
                if (this.modelFinder.getLayoutDimensions().get(0).getDimensionType() == QueryDimensionType.PERIOD) {
                    throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.cannotQuery004", new Object[0]));
                }
            } else if (dimCount > 1) {
                boolean bl;
                boolean bl2 = false;
                for (QueryDimension dim : this.modelFinder.getLayoutDimensions()) {
                    QueryDimension parentDim;
                    if (dim.getDimensionType() != QueryDimensionType.CHILD || (parentDim = this.modelFinder.getQueryDimension(dim.getParent())).getDimensionType() != QueryDimensionType.MDINFO) continue;
                    bl = true;
                    break;
                }
                if (!bl) {
                    throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.cannotQuery005", new Object[0]));
                }
            }
        }
        this.checkPeriod();
        this.checkScene();
        this.checkMetadata();
    }

    private boolean checkZB(LayoutField field) {
        QueryObjectType type = field.getType();
        if (type == QueryObjectType.ZB || type == QueryObjectType.FORMULA) {
            return true;
        }
        if (type == QueryObjectType.GROUP) {
            FieldGroup group = (FieldGroup)this.modelFinder.getQueryObject(field.getFullName());
            return this.checkZB(group);
        }
        return false;
    }

    private boolean checkZB(FieldGroup group) {
        if (group != null && group.getGroupType() == FieldGroupType.ZB) {
            for (QueryObject obj : group.getChildren()) {
                if (obj.getType() == QueryObjectType.ZB || obj.getType() == QueryObjectType.FORMULA) {
                    return true;
                }
                if (!this.checkZB((FieldGroup)obj)) continue;
                return true;
            }
        }
        return false;
    }

    private void checkPeriod() throws Exception {
        List<QueryDimension> periodDims = this.modelFinder.getPeriodDimensions();
        if (periodDims == null || periodDims.size() == 0) {
            return;
        }
        boolean hasCustom = false;
        boolean hasWeek = false;
        boolean has13Period = false;
        for (QueryDimension periodDim : periodDims) {
            if (periodDim.getPeriodType() == PeriodType.WEEK) {
                hasWeek = true;
                continue;
            }
            if (periodDim.getPeriodType() == PeriodType.CUSTOM) {
                hasCustom = true;
                continue;
            }
            if (periodDim.getPeriodType() != PeriodType.MONTH || !PeriodUtil.is13Period(periodDim)) continue;
            has13Period = true;
        }
        if (periodDims.size() > 1) {
            if (hasCustom) {
                throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.notSupportQuery001", new Object[0]));
            }
            if (hasWeek) {
                throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.notSupportQuery002", new Object[0]));
            }
            if (has13Period) {
                throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.notSupportQuery003", new Object[0]));
            }
        }
    }

    private void checkScene() throws Exception {
        for (ConditionField condition : this.zbQueryModel.getConditions()) {
            QueryDimension dimension = this.modelFinder.getQueryDimension(condition.getFullName());
            if (dimension == null || dimension.getDimensionType() != QueryDimensionType.SCENE) continue;
            boolean multiValues = false;
            if (this.conditionValues.contains(condition.getFullName())) {
                String[] values = this.conditionValues.getValues(condition.getFullName());
                multiValues = values == null || values.length == 0 || values.length > 1;
            } else if (condition.getDefaultValueMode() == DefaultValueMode.NONE) {
                multiValues = true;
            } else if (condition.getDefaultValueMode() == DefaultValueMode.APPOINT) {
                String[] values = condition.getDefaultValues();
                boolean bl = multiValues = values == null || values.length == 0 || values.length > 1;
            }
            if (!multiValues) continue;
            boolean hasScene = false;
            boolean hasZB = false;
            List<LayoutField> fields = this.modelFinder.getAllLayoutFields();
            for (LayoutField field : fields) {
                if (field.getFullName().equals(condition.getFullName())) {
                    hasScene = true;
                    if (!hasZB) continue;
                    throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.dimMulti001", dimension.getDisplayTitle()));
                }
                if (hasZB) continue;
                hasZB = this.checkZB(field);
            }
            if (hasScene) continue;
            throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.dimMulti002", dimension.getDisplayTitle()));
        }
    }

    private void checkDataType(QueryField field) throws Exception {
        DataFieldType dataFieldType = DataFieldType.valueOf((int)field.getDataType());
        boolean correct = true;
        switch (dataFieldType) {
            case PICTURE: {
                correct = false;
                break;
            }
        }
        if (!correct) {
            throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.notSupportQuery004", dataFieldType.getTitle(), field.getDisplayTitle()));
        }
    }

    private void checkMetadata() throws Exception {
        for (ZBField zbField : this.modelFinder.getLayoutZBFields(null)) {
            FullNameWrapper fullNameWrapper = new FullNameWrapper(QueryObjectType.ZB, zbField.getFullName());
            DataTable dataTable = this.dataSchemeService.getDataTableByCode(fullNameWrapper.getTableName());
            DataField dataField = null;
            if (dataTable != null) {
                dataField = this.dataSchemeService.getDataFieldByTableKeyAndCode(dataTable.getKey(), fullNameWrapper.getFieldName());
            }
            if (dataField != null) continue;
            throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.fieldNotExist", zbField.getDisplayTitle(), zbField.getFullName()));
        }
    }

    private void buildDimFields() throws Exception {
        List<LayoutField> layoutFields = this.modelFinder.getAllLayoutFields();
        for (LayoutField layoutField : layoutFields) {
            if (layoutField.getType() != QueryObjectType.DIMENSION) continue;
            QueryDimension queryDim = this.modelFinder.getQueryDimension(layoutField.getFullName());
            if (queryDim.getDimensionType() == QueryDimensionType.PERIOD && !queryDim.isSpecialPeriodType()) {
                this.buildDimFields_Period(queryDim);
                continue;
            }
            if (queryDim.getDimensionType() == QueryDimensionType.CHILD) {
                this.buildDimFields_Child(queryDim, true);
                continue;
            }
            this.buildDimFields_Other(queryDim);
        }
        this.buildDimFields_BizkeyOrder();
    }

    private void buildDimFields_Period(QueryDimension queryDim) {
        List _dimFields = this._queryModel.getDimensions();
        FieldGroup group = (FieldGroup)this.modelFinder.getQueryObject(queryDim.getFullName());
        for (QueryObject obj : group.getChildren()) {
            if (!obj.isVisible() && this.modelFinder.getOrderField(obj.getFullName()) == null) continue;
            if (obj.getType() == QueryObjectType.GROUP) {
                if (this.modelFinder.getLayoutDimension(obj.getFullName()) != null) continue;
                this.buildDimFields_Child(this.modelFinder.getQueryDimension(obj.getFullName()), false);
                continue;
            }
            FullNameWrapper fullNameWrapper = new FullNameWrapper(QueryObjectType.DIMENSIONATTRIBUTE, obj.getFullName());
            DimFieldDescriptor _dimField = new DimFieldDescriptor(fullNameWrapper.getQueryName(), this.generateAlias(obj));
            _dimFields.add(_dimField);
            this.recordFinalQueryField(_dimField);
            this.keyColMapper.put(_dimField.getAlias(), _dimField.getAlias());
        }
    }

    private void buildDimFields_Child(QueryDimension childDim, boolean fromLayout) {
        DimensionAttributeField parentObject;
        List _dimFields = this._queryModel.getDimensions();
        FieldGroup group = (FieldGroup)this.modelFinder.getQueryObject(childDim.getFullName());
        QueryDimension parentDim = this.modelFinder.getQueryDimension(group.getParent());
        if (parentDim.getDimensionType() == QueryDimensionType.PERIOD) {
            this.buildDimFields_Child_Period(childDim);
            return;
        }
        DimensionAttributeField parentDimKeyObj = null;
        String parentDimKeyObjAlias = null;
        if (!fromLayout) {
            parentDimKeyObj = this.getKeyField(parentDim);
            parentDimKeyObjAlias = this.fullNameAliasMapper.get(parentDimKeyObj.getFullName());
        }
        DimensionAttributeField parentDimAtrr = group.getDimAttribute();
        FullNameWrapper fullNameWrapper = new FullNameWrapper(QueryObjectType.DIMENSIONATTRIBUTE, parentDimAtrr.getFullName(), parentDim.isOrgDimension());
        DimFieldDescriptor _dimField = new DimFieldDescriptor(fullNameWrapper.getQueryName(), this.generateAlias(parentDimAtrr));
        _dimField.getTags().add("HIDDEN");
        _dimFields.add(_dimField);
        this.recordFinalQueryField(_dimField);
        this.keyColMapper.put(_dimField.getAlias(), fromLayout ? _dimField.getAlias() : parentDimKeyObjAlias);
        ArrayList<String> _childDimFields = new ArrayList<String>();
        for (QueryObject obj : group.getChildren()) {
            if (!obj.isVisible()) continue;
            String alias = this.generateAlias(obj);
            this.recordFinalQueryField(alias);
            this.keyColMapper.put(alias, fromLayout ? _dimField.getAlias() : parentDimKeyObjAlias);
            _childDimFields.add(obj.getFullName());
        }
        if (fromLayout && (group.isDisplayIndent() || group.isDisplayTiered()) && (parentObject = this.getParentField(childDim)) != null && !this.fullNameAliasMapper.containsKey(parentObject.getFullName())) {
            String alias = this.generateAlias(parentObject);
            this.recordFinalQueryField(alias);
            this.keyColMapper.put(alias, alias);
            this.parentColMapper.put(_dimField.getAlias(), alias);
            _childDimFields.add(parentObject.getFullName());
        }
        this.childDimFields.put(childDim.getFullName(), _childDimFields);
        if (fromLayout && parentDim.isOrgDimension() && parentDim.getDimensionType() == QueryDimensionType.MASTER) {
            String hierarchy = "MD_ORG." + parentDim.getName();
            if (!this._queryModel.getHierarchies().contains(hierarchy)) {
                this._queryModel.getHierarchies().add(hierarchy);
            }
        }
    }

    private void buildDimFields_Child_Period(QueryDimension childDim) {
        List _dimFields = this._queryModel.getDimensions();
        FieldGroup group = (FieldGroup)this.modelFinder.getQueryObject(childDim.getFullName());
        for (QueryObject obj : group.getChildren()) {
            if (!obj.isVisible()) continue;
            FullNameWrapper fullNameWrapper = new FullNameWrapper(QueryObjectType.DIMENSIONATTRIBUTE, obj.getFullName());
            DimFieldDescriptor _dimField = new DimFieldDescriptor(fullNameWrapper.getQueryName(), this.generateAlias(obj));
            _dimFields.add(_dimField);
            this.recordFinalQueryField(_dimField);
            this.keyColMapper.put(_dimField.getAlias(), _dimField.getAlias());
        }
    }

    private void buildDimFields_Other(QueryDimension queryDim) {
        List _dimFields = this._queryModel.getDimensions();
        FullNameWrapper fullNameWrapper = null;
        DimensionAttributeField keyObject = this.getKeyField(queryDim);
        fullNameWrapper = new FullNameWrapper(QueryObjectType.DIMENSIONATTRIBUTE, keyObject.getFullName(), queryDim.isOrgDimension());
        DimFieldDescriptor _keyDimField = new DimFieldDescriptor(fullNameWrapper.getQueryName(), this.generateAlias(keyObject));
        if (this.modelFinder.getAdditionalField(keyObject.getFullName()) != null) {
            _keyDimField.getTags().add("HIDDEN");
        }
        _dimFields.add(_keyDimField);
        this.recordFinalQueryField(_keyDimField);
        this.keyColMapper.put(_keyDimField.getAlias(), _keyDimField.getAlias());
        DimensionAttributeField parentObject = this.getParentField(queryDim);
        FieldGroup group = (FieldGroup)this.modelFinder.getQueryObject(queryDim.getFullName());
        for (QueryObject obj : group.getChildren()) {
            if (!obj.isVisible() && this.modelFinder.getOrderField(obj.getFullName()) == null || keyObject != null && obj.getFullName().equals(keyObject.getFullName())) continue;
            if (obj.getType() == QueryObjectType.GROUP) {
                if (this.modelFinder.getLayoutDimension(obj.getFullName()) != null) continue;
                this.buildDimFields_Child(this.modelFinder.getQueryDimension(obj.getFullName()), false);
                continue;
            }
            fullNameWrapper = new FullNameWrapper(QueryObjectType.DIMENSIONATTRIBUTE, obj.getFullName(), queryDim.isOrgDimension());
            DimFieldDescriptor _dimField = new DimFieldDescriptor(fullNameWrapper.getQueryName(), this.generateAlias(obj));
            _dimFields.add(_dimField);
            this.recordFinalQueryField(_dimField);
            if (parentObject != null && parentObject.getFullName().equals(obj.getFullName())) {
                this.keyColMapper.put(_dimField.getAlias(), _dimField.getAlias());
                this.parentColMapper.put(_keyDimField.getAlias(), _dimField.getAlias());
                continue;
            }
            this.keyColMapper.put(_dimField.getAlias(), _keyDimField.getAlias());
        }
        if ((group.isDisplayIndent() || group.isDisplayTiered()) && parentObject != null && !this.fullNameAliasMapper.containsKey(parentObject.getFullName())) {
            fullNameWrapper = new FullNameWrapper(QueryObjectType.DIMENSIONATTRIBUTE, parentObject.getFullName(), queryDim.isOrgDimension());
            DimFieldDescriptor _dimField = new DimFieldDescriptor(fullNameWrapper.getQueryName(), this.generateAlias(parentObject));
            _dimField.getTags().add("HIDDEN");
            _dimFields.add(_dimField);
            this.recordFinalQueryField(_dimField);
            this.keyColMapper.put(_dimField.getAlias(), _dimField.getAlias());
            this.parentColMapper.put(_keyDimField.getAlias(), _dimField.getAlias());
        }
        if (queryDim.isOrgDimension() && queryDim.getDimensionType() == QueryDimensionType.MASTER) {
            String hierarchy = "MD_ORG." + queryDim.getName();
            if (!this._queryModel.getHierarchies().contains(hierarchy)) {
                this._queryModel.getHierarchies().add(hierarchy);
            }
        }
    }

    private void buildDimFields_BizkeyOrder() throws Exception {
        if (this.needBizkeyOrder()) {
            List<ZBField> zbFields = this.modelFinder.getLayoutZBFields(ZBFieldType.DETAIL);
            for (ZBField zbFiled : zbFields) {
                String fullName = FullNameWrapper.getBizkeyOrderFullName(zbFiled);
                if (this.fullNameAliasMapper.containsKey(fullName)) continue;
                DimensionAttributeField bizkeyOrderField = this.getBizkeyOrderField(fullName);
                String alias = this.generateAlias(bizkeyOrderField);
                DimFieldDescriptor _dimField = new DimFieldDescriptor(fullName, alias);
                this._queryModel.getDimensions().add(_dimField);
                this.recordFinalQueryField(_dimField);
                this.bizkeyOrder = fullName;
                break;
            }
        }
    }

    private boolean needBizkeyOrder() throws Exception {
        if (!this.zbQueryModel.getOption().isQueryDetailRecord()) {
            return false;
        }
        if (this.modelFinder.getLayoutFormulaFields().size() == 0) {
            return true;
        }
        BiAdaptParam _biAdaptParam = new BiAdaptParam();
        BiAdaptParamUtils.buildParamOfLayout(_biAdaptParam, this.zbQueryModel);
        return this.biAdaptFormulaParser.needBizkeyOrder(_biAdaptParam);
    }

    private void buildMeasures() throws Exception {
        if (this.zbQueryModel.getVersion() != 1) {
            this.buildBiAdaptParam();
        }
        List _measures = this._queryModel.getMeasures();
        List<LayoutField> layoutFields = this.modelFinder.getAllLayoutFields();
        for (LayoutField layoutField : layoutFields) {
            if (layoutField.getType() == QueryObjectType.DIMENSION) continue;
            if (layoutField.getType() == QueryObjectType.GROUP) {
                FieldGroup group = (FieldGroup)this.modelFinder.getQueryObject(layoutField.getFullName());
                this.buildMeasures(group, _measures);
                continue;
            }
            _measures.add(this.buildMeasure(this.modelFinder.getQueryObject(layoutField.getFullName())));
        }
        for (MeasureDescriptor measure : _measures) {
            this.recordFinalQueryField(measure);
        }
    }

    private void buildMeasures(FieldGroup group, List<MeasureDescriptor> _measures) throws Exception {
        for (QueryObject obj : group.getChildren()) {
            if (obj.getType() == QueryObjectType.GROUP) {
                this.buildMeasures((FieldGroup)obj, _measures);
                continue;
            }
            _measures.add(this.buildMeasure(obj));
        }
    }

    private MeasureDescriptor buildMeasure(QueryObject obj) throws Exception {
        MeasureDescriptor _measure = new MeasureDescriptor();
        _measure.setAlias(this.generateAlias(obj));
        if (obj.getType() == QueryObjectType.FORMULA) {
            FormulaField formulaField = (FormulaField)obj;
            String expression = null;
            FullNameWrapper wrapper = new FullNameWrapper(QueryObjectType.ZB, formulaField.getSrcField());
            switch (formulaField.getFormulaType()) {
                case YOY: {
                    this.checkPeriodDimension();
                    expression = "YOY(" + wrapper.getQueryName() + ")";
                    break;
                }
                case MOM: {
                    this.checkPeriodDimension();
                    expression = "MOM(" + wrapper.getQueryName() + ")";
                    break;
                }
                case PREYEAR: {
                    QueryDimension periodDim = this.checkPeriodDimension();
                    expression = "[" + wrapper.getQueryName() + ", LAG(" + periodDim.getFullName() + ".P_YEAR,1)]";
                    break;
                }
                case PREPERIOD: {
                    QueryDimension periodDim = this.checkPeriodDimension();
                    expression = "[" + wrapper.getQueryName() + ", LAG(" + periodDim.getFullName() + ".P_TIMEKEY,1)]";
                    break;
                }
                case CUSTOM: {
                    if (this.zbQueryModel.getVersion() == 1) {
                        expression = formulaField.getFormula();
                        break;
                    }
                    BiAdapTransResult res = this.biAdaptFormulaParser.transFormulaToBiSyntax(this.buildBiAdaptParam(), formulaField.getFormula());
                    expression = res.getBiFormula();
                    this.recordDetailZBMeasure(_measure.getAlias(), res.isHasFloatField());
                    break;
                }
                default: {
                    throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.unknownFormulaType", formulaField.getFormulaType().name()));
                }
            }
            _measure.setExpression(expression);
        } else {
            this.checkDataType((QueryField)obj);
            _measure.setExpression(new FullNameWrapper(obj.getType(), obj.getFullName()).getQueryName());
        }
        this.recordDetailZBMeasure(_measure.getAlias(), obj);
        return _measure;
    }

    private BiAdaptParam buildBiAdaptParam() {
        if (this.biAdaptParam == null) {
            this.biAdaptParam = new BiAdaptParam();
            BiAdaptParamUtils.buildParam(this.biAdaptParam, this.zbQueryModel);
        }
        return this.biAdaptParam;
    }

    private void buildConditions() throws Exception {
        for (ConditionField condition : this.zbQueryModel.getConditions()) {
            switch (condition.getObjectType()) {
                case DIMENSION: {
                    this.buildConditions_Dimension(condition);
                    break;
                }
                case ZB: {
                    this.buildConditions_ZB(condition);
                    break;
                }
            }
        }
        this.buildTableHeaderFilters();
    }

    private void buildConditions_Dimension(ConditionField condition) throws Exception {
        FullNameWrapper fullNameWrapper;
        DimFieldDescriptor _dimField;
        if (condition.isCalibreCondition()) {
            this.buildConditions_Dimension_CalibreCondition(condition);
            return;
        }
        QueryDimension dimension = this.modelFinder.getQueryDimension(condition.getFullName());
        if (dimension.getDimensionType() == QueryDimensionType.SCENE && this.modelFinder.getLayoutDimension(dimension.getFullName()) == null && !this.modelFinder.existLayoutZBOrFormula()) {
            return;
        }
        String keyFullName = FullNameWrapper.getKeyFullName(dimension);
        boolean isOrgDim = dimension.isOrgDimension();
        if (dimension.getDimensionType() == QueryDimensionType.CHILD) {
            FieldGroup group = (FieldGroup)this.modelFinder.getQueryObject(dimension.getFullName());
            isOrgDim = this.modelFinder.getQueryDimension(group.getParent()).isOrgDimension();
        }
        if ((_dimField = this.getDimField((fullNameWrapper = new FullNameWrapper(QueryObjectType.DIMENSIONATTRIBUTE, keyFullName, isOrgDim)).getQueryName())) == null) {
            _dimField = new DimFieldDescriptor(fullNameWrapper.getQueryName(), this.generateAlias(QueryObjectType.DIMENSIONATTRIBUTE, keyFullName));
            _dimField.setVisible(false);
            this._queryModel.getDimensions().add(_dimField);
        }
        switch (condition.getConditionType()) {
            case SINGLE: 
            case MULTIPLE: {
                this.buildConditions_Dimension_Multiple(condition, _dimField);
                break;
            }
            case RANGE: {
                if (dimension.getDimensionType() != QueryDimensionType.PERIOD && !DimensionValueUtils.isPeriodChildDim(dimension)) {
                    throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.notSupportDimRangeValue", dimension.getDisplayTitle()));
                }
                this.buildConditions_Dimension_Range(condition, _dimField);
                break;
            }
            case MATCH: {
                this.buildConditions_Dimension_Match(condition, _dimField);
                break;
            }
            default: {
                throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.unknownSelectMethod", condition.getConditionType().name()));
            }
        }
    }

    private void buildConditions_Dimension_Multiple(ConditionField condition, DimFieldDescriptor _dimField) throws Exception {
        QueryDimension dimension = this.modelFinder.getQueryDimension(condition.getFullName());
        String[] values = null;
        String binding = null;
        if (this.conditionValues.contains(condition.getFullName())) {
            values = this.conditionValues.getValues(condition.getFullName());
            binding = this.conditionValues.getBinding(condition.getFullName());
        } else if (dimension.getDimensionType() == QueryDimensionType.MASTER && dimension.isEnableVersion()) {
            values = this.getRealValues(dimension, condition.getDefaultValueMode(), condition.getDefaultValues(), this.getConditionTimekey());
        } else {
            values = this.getRealValues(dimension, condition.getDefaultValueMode(), condition.getDefaultValues());
            binding = condition.getDefaultBinding();
        }
        values = this.removeEmptyValue(values);
        if (values != null && values.length > 0) {
            if (condition.getConditionType() == ConditionType.SINGLE) {
                values = new String[]{values[0]};
            }
            for (String value : values) {
                if (dimension.getDimensionType() == QueryDimensionType.PERIOD && !dimension.isSpecialPeriodType()) {
                    value = PeriodUtil.toBIPeriod(value, dimension.getPeriodType());
                }
                _dimField.getValues().add(value);
            }
            this.buildCondition_Adjust(condition, binding);
        } else {
            if (condition.getConditionType() == ConditionType.SINGLE) {
                throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.queryConditionNotNUll", dimension.getDisplayTitle()));
            }
            if (StringUtils.isNotEmpty((String)condition.getCandidateValueFilter())) {
                BiAdapTransResult res = this.biAdaptFormulaParser.transFormulaToBiSyntax(this.buildBiAdaptParam(), condition.getCandidateValueFilter());
                _dimField.setFilter(res.getBiFormula());
            }
            this.buildCondition_Adjust(condition, null);
        }
    }

    private void buildConditions_Dimension_Range(ConditionField condition, DimFieldDescriptor _dimField) throws Exception {
        QueryDimension dimension = this.modelFinder.getQueryDimension(condition.getFullName());
        String[] values = null;
        if (this.conditionValues.contains(condition.getFullName())) {
            values = this.conditionValues.getValues(condition.getFullName());
        } else {
            String[] minValues = this.getRealValues(dimension, condition.getDefaultValueMode(), condition.getDefaultValues(), condition.getDefaultPreviousN(), null);
            String[] maxValues = this.getRealValues(dimension, condition.getDefaultMaxValueMode(), new String[]{condition.getDefaultMaxValue()}, condition.getDefaultMaxPreviousN(), null);
            values = new String[]{null, null};
            if (minValues != null && minValues.length > 0) {
                values[0] = minValues[0];
            }
            if (maxValues != null && maxValues.length > 0) {
                values[1] = maxValues[0];
            }
        }
        if (values != null && values.length > 0) {
            String quotes = "\"";
            if (DimensionValueUtils.isPeriodChildDim(dimension)) {
                quotes = "";
            }
            String firstValue = values[0];
            String secondValue = values.length > 1 ? values[1] : null;
            boolean needToBIPeriod = dimension.getDimensionType() == QueryDimensionType.PERIOD && !dimension.isSpecialPeriodType();
            StringBuilder filter = new StringBuilder();
            if (StringUtils.isNotEmpty((String)firstValue) && firstValue.equals(secondValue)) {
                if (needToBIPeriod) {
                    firstValue = PeriodUtil.toBIPeriod(firstValue, dimension.getPeriodType());
                }
                filter.append(_dimField.getName()).append("=").append(quotes).append(firstValue).append(quotes);
                this.startPeriod = this.endPeriod = firstValue;
            } else {
                if (StringUtils.isNotEmpty((String)firstValue)) {
                    if (needToBIPeriod) {
                        firstValue = PeriodUtil.toBIPeriod(firstValue, dimension.getPeriodType());
                    }
                    filter.append(_dimField.getName()).append(">=").append(quotes).append(firstValue).append(quotes);
                    this.startPeriod = firstValue;
                }
                if (StringUtils.isNotEmpty((String)secondValue)) {
                    if (needToBIPeriod) {
                        secondValue = PeriodUtil.toBIPeriod(secondValue, dimension.getPeriodType());
                    }
                    if (filter.length() > 0) {
                        filter.append(" AND ");
                    }
                    filter.append(_dimField.getName()).append("<=").append(quotes).append(secondValue).append(quotes);
                    this.endPeriod = secondValue;
                }
            }
            _dimField.setFilter(filter.toString());
            this.buildCondition_Adjust(condition, null);
        }
    }

    private void buildConditions_Dimension_Match(ConditionField condition, DimFieldDescriptor _dimField) {
        String value = null;
        if (this.conditionValues.contains(condition.getFullName())) {
            value = this.conditionValues.getValue(condition.getFullName());
        } else {
            String[] dValues = condition.getDefaultValues();
            if (dValues != null && dValues.length > 0) {
                value = dValues[0];
            }
        }
        value = StringUtils.trim((String)value);
        if (StringUtils.isNotEmpty((String)value)) {
            String filter = _dimField.getName() + " LIKE \"%" + value + "%\"";
            _dimField.setFilter(filter);
        }
    }

    private void buildConditions_Dimension_CalibreCondition(ConditionField condition) throws Exception {
        QueryDimension dimension = new QueryDimension();
        dimension.setName(condition.getCalibreName());
        dimension.setFullName(condition.getCalibreName());
        dimension.setCalibreDimension(true);
        dimension.setDimensionType(QueryDimensionType.MASTER);
        dimension.setTreeStructure(true);
        String[] values = null;
        values = this.conditionValues.contains(condition.getCalibreName()) ? this.conditionValues.getValues(condition.getCalibreName()) : this.getRealValues(dimension, condition.getDefaultValueMode(), condition.getDefaultValues());
        values = this.removeEmptyValue(values);
        if (values != null && values.length > 0) {
            StringBuilder strBuilder = new StringBuilder();
            for (String value : values) {
                strBuilder.append(value).append(",");
            }
            if (strBuilder.length() > 0) {
                strBuilder.deleteCharAt(strBuilder.length() - 1);
                strBuilder.insert(0, ":").insert(0, condition.getCalibreName());
                this.calibreFilter = strBuilder.toString();
            }
        } else if (condition.getConditionType() == ConditionType.SINGLE) {
            throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.queryConditionNotNUll", condition.getCalibreTitle()));
        }
    }

    protected String getConditionTimekey() {
        FullNameWrapper fullNameWrapper;
        DimFieldDescriptor periodDimField;
        if (StringUtils.isNotEmpty((String)this.maxPeriod)) {
            return this.maxPeriod;
        }
        QueryDimension periodDim = this.modelFinder.getPeriodDimension();
        if (periodDim != null && (periodDimField = this.getDimField((fullNameWrapper = new FullNameWrapper(QueryObjectType.DIMENSIONATTRIBUTE, FullNameWrapper.getKeyFullName(periodDim))).getQueryName())) != null) {
            String timekey = null;
            if (StringUtils.isNotEmpty((String)periodDimField.getFilter())) {
                timekey = this.endPeriod;
            } else {
                List values = periodDimField.getValues();
                if (values != null && values.size() > 0) {
                    for (String value : values) {
                        if (timekey == null) {
                            timekey = value;
                            continue;
                        }
                        if (value == null) continue;
                        timekey = value.compareTo(timekey) > 0 ? value : timekey;
                    }
                }
            }
            if (StringUtils.isNotEmpty((String)timekey)) {
                this.maxPeriod = PeriodUtil.toNrPeriod(timekey, periodDim.getPeriodType());
            }
        }
        return this.maxPeriod;
    }

    private void buildCondition_Adjust(ConditionField condition, String adjustValue) throws Exception {
        QueryDimension adjustDim;
        QueryDimension dimension = this.modelFinder.getQueryDimension(condition.getFullName());
        if (dimension.getDimensionType() == QueryDimensionType.PERIOD && dimension.isEnableAdjust() && (adjustDim = this.modelFinder.getQueryDimension("ADJUST")) != null) {
            if (this.modelFinder.getAllLayoutFields().size() == 1 && this.modelFinder.getLayoutDimensions().size() == 1) {
                return;
            }
            String keyFullName = FullNameWrapper.getKeyFullName(adjustDim);
            FullNameWrapper fullNameWrapper = new FullNameWrapper(QueryObjectType.DIMENSIONATTRIBUTE, keyFullName, false);
            DimFieldDescriptor _adjustDimField = this.getDimField(fullNameWrapper.getQueryName());
            boolean hasAdjustDimField = true;
            if (_adjustDimField == null) {
                _adjustDimField = new DimFieldDescriptor(fullNameWrapper.getQueryName(), this.generateAlias(QueryObjectType.DIMENSIONATTRIBUTE, keyFullName));
                _adjustDimField.setVisible(false);
                this._queryModel.getDimensions().add(_adjustDimField);
                hasAdjustDimField = false;
            }
            if (condition.getConditionType() == ConditionType.SINGLE) {
                _adjustDimField.getValues().add(StringUtils.isNotEmpty((String)adjustValue) ? adjustValue : "0");
            } else if (!hasAdjustDimField) {
                _adjustDimField.getValues().add("0");
            }
        }
    }

    private void buildConditions_ZB(ConditionField condition) throws Exception {
        switch (condition.getConditionType()) {
            case SINGLE: 
            case MULTIPLE: {
                this.buildConditions_ZB_Multiple(condition);
                break;
            }
            case RANGE: {
                this.buildConditions_ZB_Range(condition);
                break;
            }
            case MATCH: {
                this.buildConditions_ZB_Match(condition);
                break;
            }
            default: {
                throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.notSupportSelectMethodForZBCondition", condition.getConditionType().name()));
            }
        }
    }

    private void buildConditions_ZB_Multiple(ConditionField condition) throws Exception {
        QueryField zbObj;
        String[] values = null;
        values = this.conditionValues.contains(condition.getFullName()) ? this.conditionValues.getValues(condition.getFullName()) : condition.getDefaultValues();
        if ((values = this.removeEmptyValue(values)) != null && values.length > 0) {
            String queryName = new FullNameWrapper(QueryObjectType.ZB, condition.getFullName()).getQueryName();
            if (condition.getConditionType() == ConditionType.SINGLE) {
                values = new String[]{values[0]};
            }
            StringBuilder filter = new StringBuilder();
            for (String value : values) {
                if (filter.length() == 0) {
                    filter.append(queryName).append(" IN {");
                } else {
                    filter.append(",");
                }
                filter.append("\"").append(value).append("\"");
            }
            if (filter.length() > 0) {
                filter.append("}");
                if (StringUtils.isEmpty((String)this._queryModel.getHaving())) {
                    this._queryModel.setHaving(filter.toString());
                } else {
                    this._queryModel.setHaving(this._queryModel.getHaving() + " AND " + filter.toString());
                }
            }
        } else if (condition.getConditionType() == ConditionType.SINGLE && StringUtils.isNotEmpty((String)(zbObj = (QueryField)this.modelFinder.getQueryObject(condition.getFullName())).getRelatedDimension())) {
            throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.queryConditionNotNUll", zbObj.getDisplayTitle()));
        }
    }

    private void buildConditions_ZB_Range(ConditionField condition) throws Exception {
        String[] values = null;
        if (this.conditionValues.contains(condition.getFullName())) {
            values = this.conditionValues.getValues(condition.getFullName());
        } else {
            values = new String[]{null, null};
            if (condition.getDefaultValues() != null && condition.getDefaultValues().length > 0) {
                values[0] = condition.getDefaultValues()[0];
            }
            values[1] = condition.getDefaultMaxValue();
        }
        if (values != null && values.length > 0) {
            FullNameWrapper fullNameWrapper = new FullNameWrapper(QueryObjectType.ZB, condition.getFullName());
            StringBuilder filter = new StringBuilder();
            if (StringUtils.isNotEmpty((String)values[0])) {
                filter.append(fullNameWrapper.getQueryName()).append(">=").append(values[0]);
            }
            if (StringUtils.isNotEmpty((String)values[1])) {
                if (filter.length() > 0) {
                    filter.append(" AND ");
                }
                filter.append(fullNameWrapper.getQueryName()).append("<=").append(values[1]);
            }
            if (filter.length() > 0) {
                if (StringUtils.isEmpty((String)this._queryModel.getHaving())) {
                    this._queryModel.setHaving(filter.toString());
                } else {
                    this._queryModel.setHaving(this._queryModel.getHaving() + " AND " + filter.toString());
                }
            }
        }
    }

    private void buildConditions_ZB_Match(ConditionField condition) throws Exception {
        String value = null;
        if (this.conditionValues.contains(condition.getFullName())) {
            value = this.conditionValues.getValue(condition.getFullName());
        } else {
            String[] dValues = condition.getDefaultValues();
            if (dValues != null && dValues.length > 0) {
                value = dValues[0];
            }
        }
        value = StringUtils.trim((String)value);
        if (StringUtils.isNotEmpty((String)value)) {
            FullNameWrapper fullNameWrapper = new FullNameWrapper(QueryObjectType.ZB, condition.getFullName());
            String filter = fullNameWrapper.getQueryName() + " LIKE \"%" + value + "%\"";
            if (StringUtils.isEmpty((String)this._queryModel.getHaving())) {
                this._queryModel.setHaving(filter);
            } else {
                this._queryModel.setHaving(this._queryModel.getHaving() + " AND " + filter);
            }
        }
    }

    private void buildTableHeaderFilters() throws Exception {
        if (this.conditionValues.getTableHeaderFilters() == null) {
            return;
        }
        StringBuffer allBuf = new StringBuffer();
        for (FilterField filter : this.conditionValues.getTableHeaderFilters()) {
            QueryObject queryObj = this.modelFinder.getQueryObject(filter.getFullName());
            if (queryObj == null) continue;
            List<FilterType> types = filter.getFilterTypes();
            String queryExp = this.findRealQueryExp(filter.getFullName());
            if (StringUtils.isEmpty((String)queryExp)) continue;
            if (queryObj.getType() == QueryObjectType.FORMULA) {
                queryExp = "(" + queryExp + ")";
            }
            if (!CollectionUtils.isEmpty(types)) {
                StringBuffer typeBuf = new StringBuffer();
                for (FilterType type : types) {
                    String exp = this.buildFilterExp(queryExp, type);
                    if (exp == null) continue;
                    if (typeBuf.length() > 0) {
                        typeBuf.append(" OR ");
                    }
                    typeBuf.append(exp);
                }
                if (typeBuf.length() <= 0) continue;
                if (allBuf.length() > 0) {
                    allBuf.append(" AND ");
                }
                allBuf.append("(").append(typeBuf).append(")");
                continue;
            }
            QueryField queryField = (QueryField)this.modelFinder.getQueryObject(filter.getFullName());
            CustomFilter customFilter = filter.getCustomFilter();
            if (customFilter == null) continue;
            MagnitudeValue queryMagnitude = queryField.getQueryMagnitude();
            MagnitudeValue fieldMagnitude = queryField.getFieldMagnitude();
            List<ConditionOperationItem> operationItems = customFilter.getOperationItems();
            if (CollectionUtils.isEmpty(operationItems)) continue;
            ArrayList<String> cusExpList = new ArrayList<String>();
            for (ConditionOperationItem operationItem : operationItems) {
                double magnitudeValue = 0.0;
                String cusExp = null;
                if (queryMagnitude != null && queryMagnitude != MagnitudeValue.NONE) {
                    magnitudeValue = fieldMagnitude == null || fieldMagnitude == MagnitudeValue.NONE ? (double)queryMagnitude.value() : (double)queryMagnitude.value() / (double)fieldMagnitude.value();
                    cusExp = ConditionExpGenerator.generate(operationItem.getOperation(), ((QueryField)queryObj).getDataType(), queryExp, magnitudeValue, operationItem.getValues().toArray());
                } else {
                    cusExp = ConditionExpGenerator.generate(operationItem.getOperation(), ((QueryField)queryObj).getDataType(), queryExp, null, operationItem.getValues().toArray());
                }
                cusExpList.add(cusExp);
            }
            String cusFinalExp = (String)cusExpList.get(0);
            if (cusExpList.size() > 1) {
                cusFinalExp = String.join((CharSequence)(" " + customFilter.getLogic().name() + " "), cusExpList);
            }
            if (allBuf.length() > 0) {
                allBuf.append(" AND ");
            }
            allBuf.append("(").append(cusFinalExp).append(")");
        }
        if (allBuf.length() > 0) {
            if (StringUtils.isEmpty((String)this._queryModel.getHaving())) {
                this._queryModel.setHaving(allBuf.toString());
            } else {
                this._queryModel.setHaving(this._queryModel.getHaving() + " AND " + allBuf.toString());
            }
        }
    }

    private String buildFilterExp(String queryExp, FilterType filterType) {
        switch (filterType) {
            case NULL: {
                return " ISNULL(" + queryExp + ")";
            }
            case NOT_NULL: {
                return " NOT ISNULL(" + queryExp + ")";
            }
            case POSITIVE: {
                return queryExp + " > 0";
            }
            case NEGATIVE: {
                return queryExp + " < 0";
            }
            case ZERO: {
                return queryExp + " = 0";
            }
            case TRUE: {
                return queryExp + " = TRUE";
            }
            case FALSE: {
                return queryExp + " = FALSE";
            }
        }
        return null;
    }

    private void buildSorts() {
        this.sortDimMapper.clear();
        List _sorts = this._queryModel.getOrderBys();
        for (OrderField orderField : this.getRealSorts()) {
            QueryObject queryObject = this.modelFinder.getQueryObject(orderField.getFullName());
            if (queryObject == null) continue;
            QueryDimension queryDim = null;
            boolean orgDimension = false;
            if (queryObject.getType() == QueryObjectType.DIMENSIONATTRIBUTE) {
                queryDim = this.modelFinder.getQueryDimension(queryObject.getParent());
                if (queryDim.getDimensionType() == QueryDimensionType.CHILD) {
                    orgDimension = this.modelFinder.getQueryDimension(queryDim.getParent()).isOrgDimension();
                    queryObject = this.getKeyField(queryDim);
                } else if (queryDim.getDimensionType() == QueryDimensionType.PERIOD) {
                    if (queryDim.getPeriodType() == PeriodType.CUSTOM || queryDim.getPeriodType() == PeriodType.WEEK) {
                        queryObject = this.getKeyField(queryDim);
                    }
                } else {
                    orgDimension = queryDim.isOrgDimension();
                }
            }
            if (!this.finalQueryFieldAliases.contains(this.fullNameAliasMapper.get(queryObject.getFullName()))) continue;
            SortDescriptor _sort = null;
            if (queryObject.getType() == QueryObjectType.FORMULA) {
                _sort = new SortDescriptor(this.fullNameAliasMapper.get(queryObject.getFullName()), orderField.getMode() == OrderMode.ASC ? SortMode.ASC : SortMode.DESC);
            } else {
                FullNameWrapper wrapper = new FullNameWrapper(queryObject.getType(), queryObject.getFullName(), orgDimension);
                _sort = new SortDescriptor(wrapper.getQueryName(), orderField.getMode() == OrderMode.ASC ? SortMode.ASC : SortMode.DESC);
                if (queryObject.getType() == QueryObjectType.DIMENSIONATTRIBUTE) {
                    if (this.sortDimMapper.containsKey(_sort.getExpression())) continue;
                    this.sortDimMapper.put(_sort.getExpression(), queryDim.getFullName());
                }
            }
            _sorts.add(_sort);
        }
    }

    private List<OrderField> getRealSorts() {
        ArrayList<OrderField> realSorts = new ArrayList<OrderField>();
        TableHeaderOrderField thOrder = this.conditionValues.getTableHeaderOrder();
        if (thOrder != null) {
            realSorts.add(thOrder);
        } else {
            for (OrderField orderField : this.zbQueryModel.getOrders()) {
                QueryObject queryObj = this.modelFinder.getQueryObject(orderField.getFullName());
                if (queryObj == null) continue;
                realSorts.add(orderField);
            }
        }
        if (realSorts.size() > 0) {
            if (this.zbQueryModel.getOption().getRowHeaderMode() == HeaderMode.MERGE) {
                boolean hasDimAtrrSort = false;
                for (OrderField realSort3 : realSorts) {
                    QueryObject queryObj = this.modelFinder.getQueryObject(realSort3.getFullName());
                    if (queryObj.getType() != QueryObjectType.DIMENSIONATTRIBUTE) continue;
                    hasDimAtrrSort = true;
                    break;
                }
                if (!hasDimAtrrSort) {
                    int n = this.modelFinder.getLayoutIndexOnRow(((OrderField)realSorts.get(0)).getFullName());
                    List<LayoutField> rowFields = this.zbQueryModel.getLayout().getRows();
                    int dimIndex = -1;
                    dimIndex = n != -1 ? n - 1 : rowFields.size() - 1;
                    for (int i = dimIndex; i >= 0; --i) {
                        QueryDimension queryDim;
                        LayoutField rowField = rowFields.get(i);
                        if (rowField.getType() != QueryObjectType.DIMENSION || (queryDim = this.modelFinder.getQueryDimension(rowField.getFullName())).getDimensionType() == QueryDimensionType.SCENE) continue;
                        dimIndex = i;
                        break;
                    }
                    if (dimIndex != -1) {
                        QueryDimension queryDim = this.modelFinder.getQueryDimension(rowFields.get(dimIndex).getFullName());
                        boolean isPeriodDim = queryDim.getDimensionType() == QueryDimensionType.PERIOD;
                        ArrayList<OrderField> extSorts = new ArrayList<OrderField>();
                        for (int i = 0; i < (isPeriodDim ? dimIndex + 1 : dimIndex); ++i) {
                            QueryDimension queryDim2;
                            LayoutField rowField = rowFields.get(i);
                            if (rowField.getType() != QueryObjectType.DIMENSION || (queryDim2 = this.modelFinder.getQueryDimension(rowField.getFullName())).getDimensionType() == QueryDimensionType.SCENE) continue;
                            List<String> mcFileds = this.getMasterControlFileds(rowField);
                            for (String mcField : mcFileds) {
                                OrderField orderFiled = new OrderField();
                                orderFiled.setFullName(mcField);
                                orderFiled.setMode(OrderMode.ASC);
                                extSorts.add(orderFiled);
                            }
                        }
                        if (isPeriodDim) {
                            extSorts.remove(extSorts.size() - 1);
                        }
                        if (extSorts.size() > 0) {
                            extSorts.addAll(realSorts);
                            realSorts = extSorts;
                        }
                    }
                }
            }
            if (this.modelFinder.existColLayoutDimension()) {
                HashMap realSortsMap = new HashMap();
                realSorts.forEach(realSort -> realSortsMap.put(realSort.getFullName(), realSort));
                List<LayoutField> list = this.zbQueryModel.getLayout().getRows();
                ArrayList<OrderField> extSorts = new ArrayList<OrderField>();
                for (int i = 0; i < list.size(); ++i) {
                    LayoutField rowField = list.get(i);
                    if (rowField.getType() != QueryObjectType.DIMENSION) continue;
                    List<String> mcFileds = this.getMasterControlFileds(rowField);
                    for (String mcField : mcFileds) {
                        OrderField orderFiled;
                        if (realSortsMap.containsKey(mcField)) {
                            orderFiled = (OrderField)realSortsMap.get(mcField);
                            extSorts.add(orderFiled);
                            realSorts.remove(orderFiled);
                            continue;
                        }
                        orderFiled = new OrderField();
                        orderFiled.setFullName(mcField);
                        orderFiled.setMode(OrderMode.ASC);
                        extSorts.add(orderFiled);
                    }
                }
                realSorts.addAll(0, extSorts);
            }
        }
        return realSorts;
    }

    private List<String> getMasterControlFileds(LayoutField layoutFiled) {
        ArrayList<String> fields = new ArrayList<String>();
        if (layoutFiled.getType() == QueryObjectType.DIMENSION) {
            FieldGroup group = (FieldGroup)this.modelFinder.getQueryObject(layoutFiled.getFullName());
            boolean exist = false;
            for (QueryObject queryObject : group.getChildren()) {
                if (!queryObject.isVisible()) continue;
                if (queryObject.getType() == QueryObjectType.GROUP) {
                    if (this.modelFinder.getLayoutDimension(queryObject.getFullName()) != null) continue;
                    for (QueryObject subQueryObject : ((FieldGroup)queryObject).getChildren()) {
                        if (!subQueryObject.isVisible()) continue;
                        exist = true;
                        break;
                    }
                } else {
                    exist = true;
                }
                if (!exist) continue;
                break;
            }
            if (exist) {
                QueryDimension queryDim = this.modelFinder.getLayoutDimension(layoutFiled.getFullName());
                if (queryDim.getDimensionType() == QueryDimensionType.PERIOD) {
                    for (QueryObject queryObject : group.getChildren()) {
                        if (!queryObject.isVisible()) continue;
                        if (queryObject.getType() == QueryObjectType.GROUP) {
                            if (this.modelFinder.getLayoutDimension(queryObject.getFullName()) != null) continue;
                            for (QueryObject subQueryObject : ((FieldGroup)queryObject).getChildren()) {
                                if (!subQueryObject.isVisible()) continue;
                                fields.add(subQueryObject.getFullName());
                            }
                            continue;
                        }
                        fields.add(queryObject.getFullName());
                    }
                } else {
                    DimensionAttributeField dimensionAttributeField = this.getKeyField(queryDim);
                    fields.add(dimensionAttributeField.getFullName());
                }
            }
        }
        return fields;
    }

    private String findRealFormula(FormulaField formulaField) {
        String alias = this.fullNameAliasMapper.get(formulaField.getFullName());
        for (MeasureDescriptor measure : this._queryModel.getMeasures()) {
            if (!measure.getAlias().equals(alias)) continue;
            return measure.getExpression();
        }
        return formulaField.getFormula();
    }

    private String findRealQueryExp(String fullName) {
        String alias = this.fullNameAliasMapper.get(fullName);
        QueryObject queryObj = this.modelFinder.getQueryObject(fullName);
        if (queryObj.getType() == QueryObjectType.FORMULA || queryObj.getType() == QueryObjectType.ZB) {
            for (MeasureDescriptor measure : this._queryModel.getMeasures()) {
                if (!measure.getAlias().equals(alias)) continue;
                return measure.getExpression();
            }
        } else {
            for (DimFieldDescriptor dim : this._queryModel.getDimensions()) {
                if (!dim.getAlias().equals(alias)) continue;
                return dim.getName();
            }
        }
        return null;
    }

    private void buildFroms() {
        List<QueryDimension> queryDims;
        List _froms = this._queryModel.getFroms();
        boolean hasFormula = false;
        for (String alias : this.finalQueryFieldAliases) {
            String fullName = this.aliasFullNameMapper.get(alias);
            QueryObject queryObject = this.modelFinder.getQueryObject(fullName);
            if (queryObject == null) continue;
            if (queryObject.getType() == QueryObjectType.ZB) {
                String schemeName;
                FullNameWrapper wrapper = new FullNameWrapper(QueryObjectType.ZB, fullName);
                String string = schemeName = StringUtils.isNotEmpty((String)queryObject.getSchemeName()) ? queryObject.getSchemeName() : wrapper.getSchemeName();
                String _from = "NR_" + schemeName + "." + wrapper.getTableName();
                if (_froms.contains(_from)) continue;
                _froms.add(_from);
                continue;
            }
            if (queryObject.getType() != QueryObjectType.FORMULA) continue;
            hasFormula = true;
        }
        if (_froms.size() == 0 && hasFormula) {
            return;
        }
        if (_froms.size() == 0 && !(queryDims = this.modelFinder.getLayoutDimensions()).isEmpty()) {
            QueryDimension queryDim = queryDims.get(queryDims.size() - 1);
            if (queryDims.size() > 1) {
                for (QueryDimension _queryDim : queryDims) {
                    QueryDimension parentDim;
                    if (_queryDim.getDimensionType() != QueryDimensionType.CHILD || (parentDim = this.modelFinder.getQueryDimension(_queryDim.getParent())).getDimensionType() != QueryDimensionType.MDINFO) continue;
                    queryDim = _queryDim;
                }
            }
            if (queryDim.getDimensionType() == QueryDimensionType.CHILD) {
                queryDim = this.modelFinder.getQueryDimension(queryDim.getParent());
            }
            FullNameWrapper wrapper = new FullNameWrapper(QueryObjectType.DIMENSION, queryDim.getFullName(), queryDim.isOrgDimension());
            String _from = wrapper.getTableName();
            if (queryDim.isOrgDimension() && StringUtils.isNotEmpty((String)queryDim.getSchemeName())) {
                _from = "NR_" + queryDim.getSchemeName() + "." + wrapper.getTableName();
            }
            _froms.add(_from);
        }
    }

    private void buildOptions() {
        QueryDimension masterDim;
        QueryOption queryOption = this.zbQueryModel.getOption();
        if (queryOption.getNullRowDisplayMode() != null && queryOption.getNullRowDisplayMode() != NullRowDisplayMode.DEFAULT) {
            this._queryModel.getOptions().add("Using(\"expandMode=" + queryOption.getNullRowDisplayMode().value() + "\")");
            if (StringUtils.isNotEmpty((String)this.startPeriod) && StringUtils.isEmpty((String)this.endPeriod)) {
                this._queryModel.getOptions().add("Using(\"startTimeKey=" + this.startPeriod + "\")");
            } else if (StringUtils.isEmpty((String)this.startPeriod) && StringUtils.isNotEmpty((String)this.endPeriod)) {
                this._queryModel.getOptions().add("Using(\"endTimeKey=" + this.endPeriod + "\")");
            }
        }
        if (queryOption.isExpendInnerDimension() && (queryOption.getNullRowDisplayMode() == NullRowDisplayMode.DEFAULT || queryOption.getNullRowDisplayMode() == NullRowDisplayMode.DISPLAY_ALLNULL)) {
            StringBuffer innerDims = new StringBuffer();
            for (QueryDimension queryDimension : this.modelFinder.getLayoutDimensions()) {
                if (!this.needExpendDimension(queryDimension)) continue;
                innerDims.append(queryDimension.getName()).append(";");
            }
            if (innerDims.length() > 0) {
                this._queryModel.getOptions().add("Using(\"expandByDimensions=" + innerDims.toString() + "\")");
            }
        }
        if (StringUtils.isNotEmpty((String)queryOption.getReportScheme())) {
            this._queryModel.getOptions().add("Using(\"NR.reportScheme=" + queryOption.getReportScheme() + "\")");
            if (StringUtils.isNotEmpty((String)queryOption.getOrgFilter())) {
                this._queryModel.getOptions().add("Using(\"NR.orgFilter=" + Pattern.compile("\"").matcher(queryOption.getOrgFilter()).replaceAll("\\\\\"") + "\")");
            }
        }
        this._queryModel.getOptions().add("Using(\"DIM_REORDER\")");
        StringBuffer dims = new StringBuffer();
        for (QueryDimension queryDimension : this.modelFinder.getLayoutDimensions()) {
            if (queryDimension.getDimensionType() != QueryDimensionType.MASTER && queryDimension.getDimensionType() != QueryDimensionType.INNER && queryDimension.getDimensionType() != QueryDimensionType.SCENE) continue;
            if (dims.length() > 0) {
                dims.append(",");
            }
            dims.append(new FullNameWrapper(QueryObjectType.DIMENSION, queryDimension.getFullName(), queryDimension.isOrgDimension()).getTableName());
        }
        if (dims.length() > 0) {
            this._queryModel.getOptions().add("Using(\"REORDERED_DIMS=" + dims.toString() + "\")");
        }
        if (this.extendProvider != null && this.zbQueryModel.getExtendedDatas() != null && this.zbQueryModel.getExtendedDatas().size() > 0) {
            List<String> extendOptions = this.extendProvider.getQueryOptions(this.zbQueryModel.getExtendedDatas());
            this._queryModel.getOptions().addAll(extendOptions);
        }
        if (StringUtils.isNotEmpty((String)this.calibreFilter)) {
            this._queryModel.getOptions().add("Using(\"calibreFilter=" + this.calibreFilter + "\")");
        }
        if (queryOption.getHiddenDimensions().size() > 0) {
            StringBuilder strBuf = new StringBuilder();
            for (HiddenDimension hiddenDimension : queryOption.getHiddenDimensions()) {
                if (!StringUtils.isNotEmpty((String)hiddenDimension.getValue()) || this.modelFinder.getQueryDimension(hiddenDimension.getName()) != null) continue;
                strBuf.append(hiddenDimension.getName()).append(":\\\"").append(hiddenDimension.getValue()).append("\\\";");
            }
            if (strBuf.length() > 0) {
                this._queryModel.getOptions().add("Using(\"dimDefaultValue=" + strBuf.toString() + "\")");
            }
        }
        if (this.modelFinder.existColLayoutDimension()) {
            ArrayList<String> pageFields = new ArrayList<String>();
            List<LayoutField> rowFields = this.zbQueryModel.getLayout().getRows();
            for (LayoutField rowField : rowFields) {
                List<String> mcFields = this.getMasterControlFileds(rowField);
                for (String mcField : mcFields) {
                    String queryExp = this.findRealQueryExp(mcField);
                    if (!StringUtils.isNotEmpty((String)queryExp)) continue;
                    pageFields.add(queryExp);
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (String pageField : pageFields) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(pageField);
            }
            if (stringBuilder.length() > 0) {
                this._queryModel.getOptions().add("Using(\"PageFields=" + stringBuilder.toString() + "\")");
            }
        }
        if (this._queryModel.getHierarchies().size() == 0 && (masterDim = this.modelFinder.getMasterDimension()) != null && masterDim.isOrgDimension()) {
            String hierarchy = "MD_ORG." + masterDim.getName();
            this._queryModel.getHierarchies().add(hierarchy);
        }
        this._queryModel.setLanguage(NpContextHolder.getContext().getLocale().getLanguage());
    }

    private boolean needExpendDimension(QueryDimension queryDim) {
        if (queryDim.getDimensionType() == QueryDimensionType.INNER && !queryDim.isVirtualDimension()) {
            for (ConditionField condition : this.zbQueryModel.getConditions()) {
                FullNameWrapper fullNameWrapper;
                DimFieldDescriptor dimField;
                if (!condition.getFullName().equals(queryDim.getFullName()) || !StringUtils.isNotEmpty((String)condition.getCandidateValueFilter()) || condition.getConditionType() != ConditionType.MULTIPLE || !(dimField = this.getDimField((fullNameWrapper = new FullNameWrapper(QueryObjectType.DIMENSIONATTRIBUTE, FullNameWrapper.getKeyFullName(queryDim), queryDim.isOrgDimension())).getQueryName())).getValues().isEmpty()) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    private String generateAlias(QueryObject obj) {
        return this.generateAlias(obj.getType(), obj.getFullName());
    }

    private String generateAlias(QueryObjectType objectType, String fullName) {
        if (this.fullNameAliasMapper.containsKey(fullName)) {
            return this.fullNameAliasMapper.get(fullName);
        }
        String alias = StringUtils.replace((String)fullName, (String)".", (String)"_");
        if (this.aliasFullNameMapper.containsKey(alias)) {
            String newAlias;
            int n = 1;
            while (this.aliasFullNameMapper.containsKey(newAlias = alias + n)) {
                ++n;
            }
            alias = newAlias;
        }
        this.aliasFullNameMapper.put(alias, fullName);
        this.fullNameAliasMapper.put(fullName, alias);
        return alias;
    }

    private DimensionAttributeField getKeyField(QueryDimension dimension) {
        String keyFullName = FullNameWrapper.getKeyFullName(dimension);
        DimensionAttributeField keyField = (DimensionAttributeField)this.modelFinder.getQueryObject(keyFullName);
        if (keyField == null) {
            FullNameWrapper wrapper = new FullNameWrapper(QueryObjectType.DIMENSIONATTRIBUTE, keyFullName);
            keyField = new DimensionAttributeField();
            keyField.setType(QueryObjectType.DIMENSIONATTRIBUTE);
            keyField.setName(wrapper.getFieldName());
            keyField.setTitle(dimension.getDisplayTitle());
            keyField.setFullName(keyFullName);
            keyField.setParent(dimension.getFullName());
            keyField.setDataType(6);
            if (dimension.getDimensionType() == QueryDimensionType.PERIOD) {
                keyField.setPeriodType(dimension.getPeriodType());
                keyField.setTimekey(!dimension.isSpecialPeriodType());
            }
            this.modelFinder.addAdditionalField(keyField);
        }
        return keyField;
    }

    private DimensionAttributeField getParentField(QueryDimension dimension) {
        String parentFullName = FullNameWrapper.getParentFullName(dimension);
        if (parentFullName == null) {
            return null;
        }
        DimensionAttributeField parentField = (DimensionAttributeField)this.modelFinder.getQueryObject(parentFullName);
        if (parentField == null) {
            FullNameWrapper wrapper = new FullNameWrapper(QueryObjectType.DIMENSIONATTRIBUTE, parentFullName);
            parentField = new DimensionAttributeField();
            parentField.setName(wrapper.getFieldName());
            parentField.setTitle("\u7236\u5c5e\u6027");
            parentField.setFullName(parentFullName);
            parentField.setParent(dimension.getFullName());
            parentField.setDataType(6);
            this.modelFinder.addAdditionalField(parentField);
        }
        return parentField;
    }

    private DimensionAttributeField getBizkeyOrderField(String fullName) {
        DimensionAttributeField boField = (DimensionAttributeField)this.modelFinder.getQueryObject(fullName);
        if (boField == null) {
            boField = new DimensionAttributeField();
            boField.setName("BIZKEYORDER");
            boField.setTitle("\u6392\u5e8f\u5b57\u6bb5");
            boField.setFullName(fullName);
            boField.setDataType(6);
            boField.setMessageAlias(fullName);
            this.modelFinder.addAdditionalField(boField);
        }
        return boField;
    }

    public String getBizkeyOrder() {
        return this.bizkeyOrder;
    }

    private DimFieldDescriptor getDimField(String name) {
        for (DimFieldDescriptor _dimField : this._queryModel.getDimensions()) {
            if (!_dimField.getName().equals(name)) continue;
            return _dimField;
        }
        return null;
    }

    private QueryDimension checkPeriodDimension() throws Exception {
        QueryDimension dim = this.modelFinder.getPeriodDimension();
        if (dim != null) {
            return dim;
        }
        throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.notFoundPeriodField", new Object[0]));
    }

    private void recordFinalQueryField(DimFieldDescriptor dimFiled) {
        dimFiled.setOrderIndex(this.finalQueryFieldAliases.size());
        this.finalQueryFieldAliases.add(dimFiled.getAlias());
    }

    private void recordFinalQueryField(MeasureDescriptor measureFiled) {
        measureFiled.setOrderIndex(this.finalQueryFieldAliases.size());
        this.finalQueryFieldAliases.add(measureFiled.getAlias());
    }

    private void recordFinalQueryField(String filedAlias) {
        this.finalQueryFieldAliases.add(filedAlias);
    }

    private void recordDetailZBMeasure(String filedAlias, QueryObject obj) {
        if (obj.getType() == QueryObjectType.FORMULA) {
            QueryObject srcField;
            FormulaField formulaField = (FormulaField)obj;
            if (formulaField.getFormulaType() == FormulaType.CUSTOM) {
                return;
            }
            if (formulaField.getSrcFieldZbType() == ZBFieldType.DETAIL) {
                this.detailZBMeasureAliases.add(filedAlias);
            } else if (formulaField.getSrcFieldZbType() == null && (srcField = this.modelFinder.getQueryObject(formulaField.getSrcField())) != null && obj.getType() == QueryObjectType.ZB && ((ZBField)obj).getZbType() == ZBFieldType.DETAIL) {
                this.detailZBMeasureAliases.add(filedAlias);
            }
        } else if (obj.getType() == QueryObjectType.ZB && ((ZBField)obj).getZbType() == ZBFieldType.DETAIL) {
            this.detailZBMeasureAliases.add(filedAlias);
        }
    }

    private void recordDetailZBMeasure(String filedAlias, boolean isDetaill) {
        if (isDetaill) {
            this.detailZBMeasureAliases.add(filedAlias);
        }
    }

    private String[] getRealValues(QueryDimension dimension, DefaultValueMode defaultValueMode, String[] defaultValues, int defaultPreviousN, String timekey) throws Exception {
        String[] values = null;
        String value = null;
        switch (defaultValueMode) {
            case NONE: {
                break;
            }
            case FIRST: {
                value = DimensionValueUtils.getFirstValue(dimension, timekey);
                break;
            }
            case FIRST_CHILD: {
                values = DimensionValueUtils.getFirstAndChildValue(dimension, timekey);
                break;
            }
            case FIRST_ALLCHILD: {
                values = DimensionValueUtils.getFirstAndAllChildValue(dimension, timekey);
                break;
            }
            case CURRENT: {
                value = DimensionValueUtils.getCurrentPeriod(dimension);
                break;
            }
            case PREVIOUS: {
                value = DimensionValueUtils.getCurrentPeriod(dimension, -1);
                break;
            }
            case PREVIOUS_N: {
                value = DimensionValueUtils.getCurrentPeriod(dimension, -defaultPreviousN);
                break;
            }
            case APPOINT: {
                values = defaultValues;
            }
        }
        if (StringUtils.isNotEmpty(value)) {
            values = new String[]{value};
        }
        return values;
    }

    private String[] getRealValues(QueryDimension dimension, DefaultValueMode defaultValueMode, String[] defaultValues, String timekey) throws Exception {
        return this.getRealValues(dimension, defaultValueMode, defaultValues, 2, null);
    }

    private String[] getRealValues(QueryDimension dimension, DefaultValueMode defaultValueMode, String[] defaultValues) throws Exception {
        return this.getRealValues(dimension, defaultValueMode, defaultValues, null);
    }

    private String[] removeEmptyValue(String[] values) {
        if (values == null || values.length == 0) {
            return values;
        }
        ArrayList<String> list = new ArrayList<String>();
        for (String value : values) {
            if (!StringUtils.isNotEmpty((String)value)) continue;
            list.add(value);
        }
        return list.toArray(new String[list.size()]);
    }
}

