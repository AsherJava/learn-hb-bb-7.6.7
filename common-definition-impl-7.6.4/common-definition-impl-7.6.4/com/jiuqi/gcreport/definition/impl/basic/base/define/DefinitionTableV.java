/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.param.hypermodel.domain.enums.ModelType
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.common.TableModelType
 */
package com.jiuqi.gcreport.definition.impl.basic.base.define;

import com.jiuqi.budget.param.hypermodel.domain.enums.ModelType;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBValue;
import com.jiuqi.gcreport.definition.impl.anno.intf.IDefaultValueEnum;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionGroupV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableIndexV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionValueV;
import com.jiuqi.gcreport.definition.impl.basic.base.util.UUIDTool;
import com.jiuqi.gcreport.definition.impl.enums.TemporaryTableTypeEnum;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.common.TableModelType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class DefinitionTableV {
    private String key;
    private String code;
    private String title;
    private String description;
    private String tableName;
    private TableModelKind kind = TableModelKind.DEFAULT;
    private String dataAreaSetting;
    private boolean hugeRecord;
    private boolean supportDatedVersion;
    private DefinitionGroupV ownerGroup;
    private String periodFieldId;
    private String bizKeyFieldsId;
    private String[] bizKeyFields;
    private boolean needSynchronize = true;
    private String dictTreeStruct;
    private Date updateTime;
    private String dimensionName;
    private String dimensionList;
    private String order;
    private String version;
    private String ownerLevelAndId;
    private List<DefinitionFieldV> fields;
    private List<DefinitionTableIndexV> indexs;
    private List<DefinitionValueV> initDatas;
    private TableModelType tableType;
    private List<String> pkeys;
    private boolean primaryRequired;
    private TemporaryTableTypeEnum tempTableType;
    private String modelTableName;
    private ModelType budModelType;
    private String dataSource;
    private static final String ENTITY_DESCRIPTION = "\u6b64\u8bb0\u5f55\u662f\u901a\u8fc7\u5b9e\u4f53\u81ea\u52a8\u8f6c\u5316\u751f\u6210\uff0c\u7981\u6b62\u4fee\u6539\uff01\uff01";
    private static final String ENTITY_DEFAULT_KEY = "ID";

    public DefinitionTableV(DBTable entity, String tableName) {
        tableName = tableName.toUpperCase();
        this.setKey(UUIDTool.newUUIDString36());
        this.setCode(tableName);
        this.setTableName(tableName);
        this.setTitle(entity.title());
        this.setDescription(ENTITY_DESCRIPTION);
        this.setKind(entity.kind());
        this.setTableType(entity.tableType());
        this.setBizKeyFields(entity.bizkeyfields());
        this.setPrimaryRequired(entity.primaryRequired());
        this.setTempTableType(entity.tempTableType());
        entity.initDatas();
        if (entity.initDatas().length > 0) {
            for (DBValue dv : entity.initDatas()) {
                this.addInitData(dv);
            }
        }
        if (entity.initDataEnum().isEnum() && IDefaultValueEnum.class.isAssignableFrom(entity.initDataEnum())) {
            IDefaultValueEnum[] enumConstants;
            for (IDefaultValueEnum dve : enumConstants = entity.initDataEnum().getEnumConstants()) {
                this.addInitData(dve);
            }
        }
        this.setOwnerGroup(new DefinitionGroupV(entity.ownerGroupID(), this));
        this.modelTableName = !StringUtils.isEmpty((String)entity.sourceTable()) ? entity.sourceTable() : (entity.convertToBudModel() ? entity.name() : null);
        ModelType modelType = this.budModelType = entity.convertToBudModel() ? entity.modelType() : null;
        if (!StringUtils.isEmpty((String)entity.dataSource())) {
            this.dataSource = OuterDataSourceUtils.getOuterDataSourceCode((String)entity.dataSource());
        }
    }

    public DefinitionTableV() {
    }

    private void addInitData(IDefaultValueEnum dve) {
        if (dve.getID() == null || ObjectUtils.isEmpty(dve.getTableName()) || dve.getFieldValues().size() < 1) {
            return;
        }
        DefinitionValueV v = new DefinitionValueV(dve);
        this.addInitData(v);
    }

    private void addInitData(DBValue value) {
        if (ObjectUtils.isEmpty(value.id()) || value.fields().length < 1) {
            return;
        }
        DefinitionValueV v = new DefinitionValueV(this, value);
        this.addInitData(v);
    }

    public void addIndex(DBIndex field) {
        DefinitionTableIndexV f = new DefinitionTableIndexV(field);
        this.addIndex(f);
    }

    public DefinitionFieldV addField(DBColumn field, String entityFieldName) {
        DefinitionFieldV f = new DefinitionFieldV(this, field, entityFieldName);
        return this.addField(f);
    }

    private DefinitionFieldV addField(DefinitionFieldV field) {
        if (this.fields == null) {
            this.fields = new ArrayList<DefinitionFieldV>();
        }
        this.fields.add(field);
        return field;
    }

    private void addIndex(DefinitionTableIndexV index) {
        if (this.indexs == null) {
            this.indexs = new ArrayList<DefinitionTableIndexV>();
        }
        this.indexs.add(index);
    }

    private void addInitData(DefinitionValueV value) {
        if (this.initDatas == null) {
            this.initDatas = new ArrayList<DefinitionValueV>();
        }
        this.initDatas.add(value);
    }

    public String getKey() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getTableName() {
        return this.tableName;
    }

    public TableModelKind getKind() {
        return this.kind;
    }

    public String getDataAreaSetting() {
        return this.dataAreaSetting;
    }

    public boolean isHugeRecord() {
        return this.hugeRecord;
    }

    public boolean isSupportDatedVersion() {
        return this.supportDatedVersion;
    }

    public DefinitionGroupV getOwnerGroup() {
        return this.ownerGroup;
    }

    public String getPeriodFieldId() {
        return this.periodFieldId;
    }

    public String getBizKeyFieldsId() {
        return this.bizKeyFieldsId;
    }

    public boolean isNeedSynchronize() {
        return this.needSynchronize;
    }

    public String getDictTreeStruct() {
        return this.dictTreeStruct;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public String getDimensionList() {
        return this.dimensionList;
    }

    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return this.version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public List<DefinitionFieldV> getFields() {
        return this.fields;
    }

    public List<DefinitionTableIndexV> getIndexs() {
        return this.indexs;
    }

    public List<DefinitionValueV> getInitDatas() {
        return this.initDatas;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public TableModelType getTableType() {
        return this.tableType;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setKind(TableModelKind kind) {
        this.kind = kind;
    }

    public void setDataAreaSetting(String dataAreaSetting) {
        this.dataAreaSetting = dataAreaSetting;
    }

    public void setHugeRecord(boolean hugeRecord) {
        this.hugeRecord = hugeRecord;
    }

    public void setSupportDatedVersion(boolean supportDatedVersion) {
        this.supportDatedVersion = supportDatedVersion;
    }

    public void setOwnerGroup(DefinitionGroupV ownerGroup) {
        this.ownerGroup = ownerGroup;
    }

    public void setPeriodFieldId(String periodFieldId) {
        this.periodFieldId = periodFieldId;
    }

    public void setBizKeyFieldsId(String bizKeyFieldsId) {
        this.bizKeyFieldsId = bizKeyFieldsId;
    }

    public void setNeedSynchronize(boolean needSynchronize) {
        this.needSynchronize = needSynchronize;
    }

    public void setDictTreeStruct(String dictTreeStruct) {
        this.dictTreeStruct = dictTreeStruct;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public void setDimensionList(String dimensionList) {
        this.dimensionList = dimensionList;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public void setFields(List<DefinitionFieldV> fields) {
        this.fields = fields;
    }

    public void setIndexs(List<DefinitionTableIndexV> indexs) {
        this.indexs = indexs;
    }

    public void setInitDatas(List<DefinitionValueV> initDatas) {
        this.initDatas = initDatas;
    }

    public void setTableType(TableModelType tableType) {
        this.tableType = tableType;
    }

    public void setBizKeyFields(String[] bizKeyFields) {
        this.bizKeyFields = bizKeyFields;
    }

    public String[] getBizKeyFields() {
        if (this.bizKeyFields == null || this.bizKeyFields.length <= 0) {
            this.bizKeyFields = new String[]{ENTITY_DEFAULT_KEY};
        }
        return this.bizKeyFields;
    }

    public List<String> getPkeys() {
        if (CollectionUtils.isEmpty(this.pkeys) && this.primaryRequired) {
            this.pkeys = new ArrayList<String>();
            this.pkeys.add(ENTITY_DEFAULT_KEY);
        }
        return this.pkeys;
    }

    public void setPkeys(List<String> pkeys) {
        this.pkeys = pkeys;
    }

    public void initPrimaryIndex() {
        ArrayList<String> priFields = new ArrayList<String>();
        this.fields.forEach(f -> {
            if (f.isPrimary()) {
                priFields.add(f.getFieldName());
                f.setNullable(false);
            }
        });
        if (!CollectionUtils.isEmpty(priFields)) {
            this.setPkeys(priFields);
        }
    }

    public boolean isPrimaryRequired() {
        return this.primaryRequired;
    }

    public void setPrimaryRequired(boolean primaryRequired) {
        this.primaryRequired = primaryRequired;
    }

    public TemporaryTableTypeEnum getTempTableType() {
        return this.tempTableType;
    }

    public void setTempTableType(TemporaryTableTypeEnum tempTableType) {
        this.tempTableType = tempTableType;
    }

    public String getModelTableName() {
        return this.modelTableName;
    }

    public void setModelTableName(String modelTableName) {
        this.modelTableName = modelTableName;
    }

    public ModelType getBudModelType() {
        return this.budModelType;
    }

    public void setBudModelType(ModelType budModelType) {
        this.budModelType = budModelType;
    }

    public String getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}

