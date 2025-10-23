/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.DataTableValidator
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager
 *  javax.validation.ConstraintViolation
 *  javax.validation.Validator
 */
package com.jiuqi.nr.datascheme.internal.validator;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.DataTableValidator;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.common.DataSchemeUtils;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class DefaultDataTableValidator
implements DataTableValidator {
    @Autowired
    private Validator validator;
    @Autowired
    private IDataTableDao<DesignDataTableDO> dataTableDao;
    @Autowired
    private IParamLevelManager paramLevelManager;
    private final Logger logger = LoggerFactory.getLogger(DefaultDataTableValidator.class);

    public void checkTable(DesignDataTable designDataTable) throws SchemeDataException {
        if (designDataTable == null) {
            return;
        }
        this.checkTable(Collections.singletonList(designDataTable));
    }

    public void levelCheckTable(String table) throws SchemeDataException {
        if (table == null) {
            return;
        }
        DesignDataTableDO designDataTableDO = this.dataTableDao.get(table);
        this.levelCheckTable(designDataTableDO);
    }

    public void levelCheckTable(DesignDataTable table) throws SchemeDataException {
        int value;
        if (table == null) {
            return;
        }
        boolean openParamLevel = this.paramLevelManager.isOpenParamLevel();
        if (openParamLevel && (value = this.paramLevelManager.getLevel().getValue()) != 0 && DataSchemeUtils.isSet(table.getLevel()) && !String.valueOf(value).equals(table.getLevel())) {
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_7.getMessage());
        }
    }

    private void validate(DataTable dataTable) throws SchemeDataException {
        Set validate = this.validator.validate((Object)dataTable, new Class[0]);
        if (validate != null && !validate.isEmpty()) {
            this.logger.info("\u6570\u636e\u9a8c\u8bc1\u4e0d\u901a\u8fc7: {}", (Object)dataTable);
            Iterator iterator = validate.iterator();
            if (iterator.hasNext()) {
                ConstraintViolation item = (ConstraintViolation)iterator.next();
                String message = item.getMessage();
                throw new SchemeDataException(message);
            }
        }
    }

    public <E extends DesignDataTable> void checkTable(Collection<E> tables) throws SchemeDataException {
        if (CollectionUtils.isEmpty(tables)) {
            return;
        }
        HashSet<String> codes = new HashSet<String>();
        HashSet<TitleRepeat> titleSet = new HashSet<TitleRepeat>();
        for (DesignDataTable designDataTable : tables) {
            Assert.notNull((Object)designDataTable, "designDataTable must not be null.");
            String code = designDataTable.getCode();
            Assert.notNull((Object)code, "code must not be null.");
            Assert.notNull((Object)designDataTable.getTitle(), "title must not be null.");
            Assert.notNull((Object)designDataTable.getDataSchemeKey(), "schemeKey must not be null.");
            Assert.notNull((Object)designDataTable.getDataTableType(), "dataTableType must not be null.");
            code = code.toUpperCase();
            designDataTable.setCode(code);
            if (designDataTable.getOrder() == null) {
                designDataTable.setOrder(OrderGenerator.newOrder());
            }
            if (codes.contains(code)) {
                throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DT_1_1.getMessage(designDataTable.getCode()));
            }
            codes.add(code);
            DesignDataTableDO byCode = this.dataTableDao.getByCode(code);
            if (null != byCode && !byCode.getKey().equals(designDataTable.getKey())) {
                throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DT_1_1.getMessage(designDataTable.getCode()));
            }
            if (designDataTable.getDataSchemeKey().equals(designDataTable.getDataGroupKey())) {
                designDataTable.setDataGroupKey(null);
            }
            String title = designDataTable.getTitle();
            TitleRepeat repeat = new TitleRepeat(designDataTable.getKey(), title, designDataTable.getDataSchemeKey(), designDataTable.getDataGroupKey());
            if (titleSet.contains(repeat)) {
                throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DT_1_2.getMessage(designDataTable.getTitle()));
            }
            titleSet.add(repeat);
            if (DataTableType.DETAIL == designDataTable.getDataTableType() || DataTableType.ACCOUNT == designDataTable.getDataTableType()) {
                if (designDataTable.getRepeatCode() == null) {
                    designDataTable.setRepeatCode(Boolean.FALSE);
                }
                if (designDataTable.getDataTableGatherType() == null) {
                    designDataTable.setDataTableGatherType(DataTableGatherType.NONE);
                }
            } else if (DataTableType.TABLE.equals((Object)designDataTable.getDataTableType())) {
                if (designDataTable.getDataTableGatherType() == null) {
                    designDataTable.setDataTableGatherType(DataTableGatherType.CLASSIFY);
                }
            } else if (DataTableType.MD_INFO == designDataTable.getDataTableType()) {
                this.checkTableForMdInfo(designDataTable);
            }
            this.validate((DataTable)designDataTable);
        }
        for (TitleRepeat titleRepeat : titleSet) {
            List<DesignDataTableDO> by = this.dataTableDao.getBy(titleRepeat.getSchemeKey(), titleRepeat.getTitle(), titleRepeat.getGroupKey());
            if (CollectionUtils.isEmpty(by)) continue;
            for (DesignDataTableDO dataTableDO : by) {
                if (dataTableDO.getKey().equals(titleRepeat.getKey())) continue;
                throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DT_1_2.getMessage(dataTableDO.getTitle()));
            }
        }
    }

    private <E extends DesignDataTable> void checkTableForMdInfo(E designDataTable) {
        designDataTable.setRepeatCode(Boolean.FALSE);
        designDataTable.setDataTableGatherType(DataTableGatherType.CLASSIFY);
        List<DesignDataTableDO> oldTables = this.dataTableDao.getByDataSchemeAndTypes(designDataTable.getDataSchemeKey(), DataTableType.MD_INFO);
        DesignDataTableDO oldMdInfoTable = oldTables.stream().filter(t -> !t.getKey().equals(designDataTable.getKey())).findAny().orElse(null);
        if (null != oldMdInfoTable) {
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DT_1_3.getMessage());
        }
    }

    private static class TitleRepeat {
        private String title;
        private String schemeKey;
        private String groupKey;
        private String key;

        public TitleRepeat() {
        }

        public TitleRepeat(String key, String title, String schemeKey, String groupKey) {
            this.key = key;
            this.title = title;
            this.schemeKey = schemeKey;
            this.groupKey = groupKey;
        }

        public String getTitle() {
            return this.title;
        }

        public String getSchemeKey() {
            return this.schemeKey;
        }

        public String getGroupKey() {
            return this.groupKey;
        }

        public String getKey() {
            return this.key;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setSchemeKey(String schemeKey) {
            this.schemeKey = schemeKey;
        }

        public void setGroupKey(String groupKey) {
            this.groupKey = groupKey;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            TitleRepeat that = (TitleRepeat)o;
            if (!Objects.equals(this.title, that.title)) {
                return false;
            }
            if (!Objects.equals(this.schemeKey, that.schemeKey)) {
                return false;
            }
            return Objects.equals(this.groupKey, that.groupKey);
        }

        public int hashCode() {
            int result = this.title != null ? this.title.hashCode() : 0;
            result = 31 * result + (this.schemeKey != null ? this.schemeKey.hashCode() : 0);
            result = 31 * result + (this.groupKey != null ? this.groupKey.hashCode() : 0);
            return result;
        }
    }
}

