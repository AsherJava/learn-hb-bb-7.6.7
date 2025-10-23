/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.Violation
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.FieldValidator
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager
 *  javax.validation.ConstraintViolation
 *  javax.validation.Validator
 */
package com.jiuqi.nr.datascheme.internal.validator;

import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.Violation;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.FieldValidator;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.internal.service.impl.AbstractDataValidator;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Primary
public class DefaultFieldValidator
extends AbstractDataValidator
implements FieldValidator {
    @Autowired
    private Validator validator;
    @Autowired
    private IDataTableDao<DesignDataTableDO> dataTableDao;
    @Autowired
    private IDataFieldDao<DesignDataFieldDO> dataFieldDao;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IParamLevelManager paramLevelManager;
    private final Logger logger = LoggerFactory.getLogger(DefaultFieldValidator.class);

    public void checkField(DesignDataField field) throws SchemeDataException {
        if (field == null) {
            return;
        }
        this.checkField(Collections.singletonList(field));
    }

    private void checkFieldNotNull(DesignDataField field) {
        Assert.notNull((Object)field, "dataField must not be null.");
        Assert.notNull((Object)field.getCode(), "code must not be null.");
        Assert.notNull((Object)field.getTitle(), "title must not be null.");
        Assert.notNull((Object)field.getDataFieldType(), "type must not be null.");
        DataFieldKind dataFieldKind = field.getDataFieldKind();
        Assert.notNull((Object)dataFieldKind, "kind must not be null.");
        String key = field.getKey();
        if (key == null) {
            field.setKey(UUIDUtils.getKey());
        }
    }

    public <E extends DesignDataField> void checkField(Collection<E> fields) throws SchemeDataException {
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }
        DesignDataTableDO tableDO = this.getDataTable(fields);
        if (tableDO == null) {
            return;
        }
        Map<String, DesignDataField> codes = this.checkField0(fields);
        this.checkField1(tableDO, codes);
        this.checkField2(tableDO, fields);
        this.checkField3(tableDO, fields);
        this.checkField4(tableDO, fields);
        this.buildRefDataFieldKey(fields);
    }

    protected void checkField4(DesignDataTable table, Collection<? extends DesignDataField> fields) {
        DesignDataScheme dataScheme = this.iDesignDataSchemeService.getDataScheme(table.getDataSchemeKey());
        for (DesignDataField designDataField : fields) {
            this.checkField4(dataScheme, designDataField, true);
        }
        if (!StringUtils.hasText(dataScheme.getEncryptScene())) {
            return;
        }
        boolean checkData = this.iRuntimeDataSchemeService.dataTableCheckData(new String[]{table.getKey()});
        if (checkData) {
            HashMap<String, DesignDataField> hashMap = new HashMap<String, DesignDataField>();
            for (DesignDataField designDataField : fields) {
                hashMap.put(designDataField.getKey(), designDataField);
            }
            List runFields = this.iRuntimeDataSchemeService.getDataFieldByTable(table.getKey());
            for (DataField runField : runFields) {
                DesignDataField field;
                if (!runField.isEncrypted() || null == (field = (DesignDataField)hashMap.get(runField.getKey())) || field.isEncrypted()) continue;
                throw new SchemeDataException("\u5df2\u5f55\u5165\u6570\u636e\u4e0d\u5141\u8bb8\u53d6\u6d88\u52a0\u5bc6");
            }
        }
    }

    private void checkField3(DesignDataTable table, Collection<? extends DesignDataField> fields) {
        if (DataTableType.MD_INFO != table.getDataTableType()) {
            return;
        }
        for (DesignDataField designDataField : fields) {
            this.checkField3(table, designDataField, true);
        }
    }

    public void levelCheckField(DesignDataField field) throws SchemeDataException {
        if (field == null) {
            return;
        }
        boolean openParamLevel = this.paramLevelManager.isOpenParamLevel();
        if (openParamLevel) {
            int value = this.paramLevelManager.getLevel().getValue();
            this.levelCheck(field, value);
        }
    }

    public void levelCheckField(String field) throws SchemeDataException {
        if (field == null) {
            return;
        }
        DesignDataFieldDO designDataFieldDO = this.dataFieldDao.get(field);
        this.levelCheckField(designDataFieldDO);
    }

    private <E extends DesignDataField> void checkField2(DesignDataTableDO tableDO, Collection<E> fields) {
        if (DataTableType.ACCOUNT != tableDO.getDataTableType()) {
            return;
        }
        for (DesignDataField field : fields) {
            this.checkField2(tableDO, field, true);
        }
    }

    private <E extends DesignDataField> DesignDataTableDO getDataTable(Collection<E> fields) {
        Optional<E> first = fields.stream().findFirst();
        if (!first.isPresent()) {
            return null;
        }
        DesignDataField field = (DesignDataField)first.get();
        String tableKey = field.getDataTableKey();
        DesignDataTableDO tableDO = this.dataTableDao.get(tableKey);
        if (tableDO == null) {
            throw new SchemeDataException("\u672a\u627e\u5230\u8868,KEY = " + tableKey);
        }
        return tableDO;
    }

    private <E extends DesignDataField> Map<String, DesignDataField> checkField0(Collection<E> fields) {
        HashMap<String, DesignDataField> codes = new HashMap<String, DesignDataField>(fields.size());
        for (DesignDataField field : fields) {
            this.logger.debug("\u6821\u9a8c\u5143\u6570\u636e\uff1a{}", (Object)field);
            this.checkFieldNotNull(field);
            String code = field.getCode();
            if (codes.containsKey(code)) {
                throw new SchemeDataException(DataFieldKind.FIELD_ZB == field.getDataFieldKind() ? DataSchemeEnum.DATA_SCHEME_DF_1_2.getMessage(field.getCode()) : DataSchemeEnum.DATA_SCHEME_DF_1_3.getMessage(field.getCode()));
            }
            codes.put(code, field);
            this.checkField0(field, true);
            this.validate((Ordered)field);
        }
        return codes;
    }

    protected void checkField1(DesignDataTableDO tableDO, Map<String, DesignDataField> codes) {
        String tableKey = tableDO.getKey();
        String schemeKey = tableDO.getDataSchemeKey();
        DataTableType tableType = tableDO.getDataTableType();
        if (DataTableType.TABLE == tableType || DataTableType.MD_INFO == tableType) {
            List<DesignDataFieldDO> codeFields = this.dataFieldDao.getByCondition(schemeKey, new ArrayList<String>(codes.keySet()), DataFieldKind.TABLE);
            this.codeCheck(codes, codeFields);
        } else if (tableType == DataTableType.DETAIL || tableType == DataTableType.ACCOUNT || tableType == DataTableType.SUB_TABLE) {
            List<DesignDataFieldDO> codeFields = this.dataFieldDao.getByTableAndCode(tableKey, new ArrayList<String>(codes.keySet()), DataFieldKind.DETAIL);
            this.codeCheck(codes, codeFields);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    protected void codeCheck(Map<String, DesignDataField> codes, List<DesignDataFieldDO> codeFields) {
        Map<String, String> keys = codes.values().stream().collect(Collectors.toMap(Basic::getKey, Basic::getCode));
        for (DesignDataFieldDO codeField : codeFields) {
            keys.remove(codeField.getKey());
        }
        if (keys.isEmpty()) {
            return;
        }
        for (DesignDataFieldDO codeField : codeFields) {
            String code = codeField.getCode();
            DesignDataField field = codes.get(code);
            if (field == null) continue;
            if (DataFieldKind.PUBLIC_FIELD_DIM == field.getDataFieldKind() && DataFieldKind.PUBLIC_FIELD_DIM == codeField.getDataFieldKind()) {
                if (!codeField.getDataTableKey().equals(field.getDataTableKey())) continue;
                this.logger.error("\u6307\u6807\u6807\u8bc6\u91cd\u590d,\u6307\u6807\u4fe1\u606f:{}", (Object)field);
                throw new SchemeDataException(DataFieldKind.FIELD_ZB == field.getDataFieldKind() ? DataSchemeEnum.DATA_SCHEME_DF_1_2.getMessage(field.getCode()) : DataSchemeEnum.DATA_SCHEME_DF_1_3.getMessage(field.getCode()));
            }
            if (codeField.getKey().equals(field.getKey())) continue;
            this.logger.error("\u6307\u6807\u6807\u8bc6\u91cd\u590d,\u6307\u6807\u4fe1\u606f:{}", (Object)field);
            throw new SchemeDataException(DataFieldKind.FIELD_ZB == field.getDataFieldKind() ? DataSchemeEnum.DATA_SCHEME_DF_1_2.getMessage(field.getCode()) : DataSchemeEnum.DATA_SCHEME_DF_1_3.getMessage(field.getCode()));
        }
    }

    private <E extends DesignDataField> void buildRefDataFieldKey(Collection<E> dataFields) {
        HashMap<String, String> refKey = new HashMap<String, String>(0);
        for (DesignDataField dataField : dataFields) {
            String refDataEntityKey = dataField.getRefDataEntityKey();
            if (StringUtils.hasText(refDataEntityKey) && !AdjustUtils.isAdjust(refDataEntityKey).booleanValue()) {
                String refDataFieldKey = dataField.getRefDataFieldKey();
                if (StringUtils.hasText(refDataFieldKey)) continue;
                refDataFieldKey = (String)refKey.get(refDataEntityKey);
                if (!StringUtils.hasText(refDataFieldKey)) {
                    try {
                        TableModelDefine tableModel = this.periodEngineService.getPeriodAdapter().isPeriodEntity(refDataEntityKey) ? this.periodEngineService.getPeriodAdapter().getPeriodEntityTableModel(refDataEntityKey) : this.entityMetaService.getTableModel(refDataEntityKey);
                        refDataFieldKey = tableModel.getBizKeys();
                        refKey.put(refDataEntityKey, refDataFieldKey);
                    }
                    catch (Exception e) {
                        this.logger.error("\u6307\u6807\u5173\u8054\u5b9e\u4f53\uff0c\u5b9e\u4f53\u672a\u627e\u5230,\u6307\u6807\u4fe1\u606f:{}", (Object)dataField);
                        throw new SchemeDataException(" \u5b9e\u4f53 " + refDataEntityKey + " \u672a\u627e\u5230\u6216\u5df2\u4e22\u5931", (Throwable)e);
                    }
                }
                dataField.setRefDataFieldKey(refDataFieldKey);
                continue;
            }
            dataField.setRefDataFieldKey(null);
            dataField.setAllowUndefinedCode(null);
            dataField.setAllowUndefinedCode(null);
        }
    }

    public List<Violation> validator(DesignDataField field) {
        return null;
    }

    public Map<String, List<Violation>> validator(Collection<DesignDataField> field) {
        return null;
    }

    private void validate(Ordered dataScheme) throws SchemeDataException {
        Set validate = this.validator.validate((Object)dataScheme, new Class[0]);
        if (validate != null && !validate.isEmpty()) {
            this.logger.info("\u6570\u636e\u8bc1\u4e0d\u901a\u8fc7 {}", (Object)dataScheme);
            String message = ((ConstraintViolation)validate.stream().findFirst().get()).getMessage();
            throw new SchemeDataException(message);
        }
    }
}

