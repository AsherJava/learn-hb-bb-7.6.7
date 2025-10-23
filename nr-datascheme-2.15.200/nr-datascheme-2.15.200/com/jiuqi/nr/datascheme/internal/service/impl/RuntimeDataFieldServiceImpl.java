/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import com.jiuqi.nr.datascheme.internal.service.DataFieldService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service(value="RuntimeDataFieldServiceImpl-NO_CACHE")
public class RuntimeDataFieldServiceImpl
implements DataFieldService {
    @Autowired
    private IDataFieldDao<DataFieldDO> dataFieldDao;
    @Autowired
    private IDataTableDao<DataTableDO> dataTableDao;
    private final Function<DataFieldDO, DataFieldDTO> toDto = Convert::iDf2Dto;
    private final Function<List<DataFieldDO>, List<DataFieldDTO>> list2Dto = r -> r.stream().map(Convert::iDf2Dto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public DataFieldDTO getDataField(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        return this.toDto.apply(this.dataFieldDao.get(key));
    }

    @Override
    public DataFieldDTO getDataFieldByTableKeyAndCode(String table, String code) {
        Assert.notNull((Object)table, "table must not be null.");
        Assert.notNull((Object)code, "code must not be null.");
        return this.toDto.apply(this.dataFieldDao.getByTableAndCode(table, code));
    }

    @Override
    public DataFieldDTO getZbKindDataFieldBySchemeKeyAndCode(String scheme, String code) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        Assert.notNull((Object)code, "code must not be null.");
        return this.toDto.apply(this.dataFieldDao.getByCondition(scheme, code, DataFieldKind.FIELD_ZB.getValue()).stream().findFirst().orElse(null));
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTableCode(String tableCode) {
        Assert.notNull((Object)tableCode, "tableCode must not be null.");
        DataTableDO table = this.dataTableDao.getByCode(tableCode);
        if (table != null) {
            return this.list2Dto.apply(this.dataFieldDao.getByTable(table.getKey()));
        }
        return Collections.emptyList();
    }

    @Override
    public List<DataFieldDTO> getBizDataFieldByTableKey(String tableKey) {
        Assert.notNull((Object)tableKey, "tableKey must not be null.");
        DataTableDO designDataTable = this.dataTableDao.get(tableKey);
        if (designDataTable == null) {
            return null;
        }
        String[] bizKeys = designDataTable.getBizKeys();
        if (bizKeys == null || bizKeys.length == 0) {
            return Collections.emptyList();
        }
        return this.getDataFields(Arrays.asList(bizKeys));
    }

    @Override
    public List<DataFieldDTO> getBizDataFieldByTableCode(String tableCode) {
        Assert.notNull((Object)tableCode, "tableCode must not be null.");
        DataTableDO designDataTable = this.dataTableDao.getByCode(tableCode);
        if (designDataTable != null) {
            String[] bizKeys = designDataTable.getBizKeys();
            if (bizKeys == null || bizKeys.length == 0) {
                return Collections.emptyList();
            }
            return this.getDataFields(Arrays.asList(bizKeys));
        }
        return Collections.emptyList();
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTableKeyAndType(String tableKey, DataFieldType ... dataFieldType) {
        Assert.notNull((Object)tableKey, "tableKey must not be null.");
        if (dataFieldType == null || 0 == dataFieldType.length) {
            return this.getDataFieldByTable(tableKey);
        }
        return this.list2Dto.apply(this.dataFieldDao.getByTableAndType(tableKey, dataFieldType));
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTableKeyAndKind(String tableKey, DataFieldKind ... dataFieldKinds) {
        Assert.notNull((Object)tableKey, "tableKey must not be null.");
        if (dataFieldKinds == null || 0 == dataFieldKinds.length) {
            return this.getDataFieldByTable(tableKey);
        }
        int selectionKey = 0;
        for (DataFieldKind dataFieldKind : dataFieldKinds) {
            selectionKey |= dataFieldKind.getValue();
        }
        return this.list2Dto.apply(this.dataFieldDao.getByTableAndKind(tableKey, selectionKey));
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTableCodeAndType(String tableCode, DataFieldType ... dataFieldType) {
        Assert.notNull((Object)tableCode, "tableCode must not be null.");
        DataTableDO table = this.dataTableDao.getByCode(tableCode);
        if (table != null) {
            if (dataFieldType == null || 0 == dataFieldType.length) {
                return this.list2Dto.apply(this.dataFieldDao.getByTable(table.getKey()));
            }
            return this.list2Dto.apply(this.dataFieldDao.getByTableAndType(table.getKey(), dataFieldType));
        }
        return Collections.emptyList();
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTableCodeAndKind(String tableCode, DataFieldKind ... dataFieldKinds) {
        Assert.notNull((Object)tableCode, "tableCode must not be null.");
        DataTableDO table = this.dataTableDao.getByCode(tableCode);
        if (table != null) {
            if (dataFieldKinds == null || 0 == dataFieldKinds.length) {
                return this.list2Dto.apply(this.dataFieldDao.getByTable(table.getKey()));
            }
            int selectionKey = 0;
            for (DataFieldKind dataFieldKind : dataFieldKinds) {
                selectionKey |= dataFieldKind.getValue();
            }
            return this.list2Dto.apply(this.dataFieldDao.getByTableAndKind(table.getKey(), selectionKey));
        }
        return Collections.emptyList();
    }

    @Override
    public List<DataFieldDTO> getDataFieldBySchemeAndCode(String scheme, String code, DataFieldKind ... dataFieldKinds) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        Assert.notNull((Object)code, "code must not be null.");
        if (dataFieldKinds == null) {
            dataFieldKinds = DataFieldKind.values();
        }
        int interestKeys = 0;
        for (DataFieldKind dataFieldKind : dataFieldKinds) {
            interestKeys |= dataFieldKind.getValue();
        }
        List<DataFieldDO> list = this.dataFieldDao.getByCondition(scheme, code, interestKeys);
        return this.list2Dto.apply(list);
    }

    @Override
    public List<DataFieldDTO> searchField(FieldSearchQuery fieldSearchQuery) {
        Assert.notNull((Object)fieldSearchQuery, "fieldSearchQuery must not be null.");
        String scheme = fieldSearchQuery.getScheme();
        List<String> schemes = fieldSearchQuery.getSchemes();
        if (CollectionUtils.isEmpty(schemes) && scheme != null) {
            schemes = Collections.singletonList(scheme);
        }
        String keyword = fieldSearchQuery.getKeyword();
        Integer kind = fieldSearchQuery.getKind();
        Integer skip = fieldSearchQuery.getSkip();
        Integer limit = fieldSearchQuery.getLimit();
        String table = fieldSearchQuery.getTable();
        Assert.notNull((Object)keyword, "keyword must not be null.");
        Assert.notNull((Object)kind, "kind must not be null.");
        skip = skip == null ? 0 : skip;
        limit = limit == null ? 0 : limit;
        List<DataFieldDO> fields = this.dataFieldDao.searchBy(schemes, table, keyword, kind, skip, limit);
        if (fields.isEmpty()) {
            return Collections.emptyList();
        }
        return this.list2Dto.apply(fields);
    }

    @Override
    public List<DataFieldDTO> getDataFields(List<String> keys) {
        Assert.notNull(keys, "keys must not be null.");
        int number = 1000;
        int limit = (keys.size() + number - 1) / number;
        ArrayList list = new ArrayList();
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
            List<String> subIds = keys.stream().skip(i * number).limit(number).collect(Collectors.toList());
            List<DataFieldDO> fields = this.dataFieldDao.batchGet(subIds);
            list.addAll(fields);
        });
        return this.list2Dto.apply(list);
    }

    @Override
    public List<DataFieldDTO> getAllDataField(String scheme) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        return this.list2Dto.apply(this.dataFieldDao.getByScheme(scheme));
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTable(String tableKey) {
        Assert.notNull((Object)tableKey, "tableKey must not be null.");
        return this.list2Dto.apply(this.dataFieldDao.getByTable(tableKey));
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTables(List<String> tableKeys) {
        Assert.notNull(tableKeys, "tableKey must not be null.");
        return this.list2Dto.apply(this.dataFieldDao.batchGetByTable(tableKeys));
    }

    @Override
    public List<DataFieldDTO> getDataFieldBySchemeAndKind(String scheme, DataFieldKind ... dataFieldKinds) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        if (dataFieldKinds == null || 0 == dataFieldKinds.length) {
            dataFieldKinds = DataFieldKind.values();
        }
        int interestKeys = 0;
        for (DataFieldKind dataFieldKind : dataFieldKinds) {
            interestKeys |= dataFieldKind.getValue();
        }
        return this.list2Dto.apply(this.dataFieldDao.getBySchemeAndKind(scheme, interestKeys));
    }

    @Override
    public DataFieldDTO getDataFieldFromMdInfoByCode(String dataSchemeKey, String dataFieldCode) {
        DataFieldDTO dataField = this.getZbKindDataFieldBySchemeKeyAndCode(dataSchemeKey, dataFieldCode);
        if (null == dataField) {
            return null;
        }
        DataTableDO dataTable = this.dataTableDao.get(dataField.getDataTableKey());
        if (null != dataTable && DataTableType.MD_INFO == dataTable.getDataTableType()) {
            return dataField;
        }
        return null;
    }
}

