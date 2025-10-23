/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  com.jiuqi.nr.datascheme.api.core.DataFieldComparator
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.FieldValidator
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.core.DataFieldComparator;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.FieldValidator;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.common.BizKeyOrder;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dto.MoveDataFieldDTO;
import com.jiuqi.nr.datascheme.internal.dto.SearchDataFieldDTO;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.internal.service.DataFieldDesignService;
import com.jiuqi.nr.datascheme.web.param.DataFieldMovePM;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class DataFieldServiceImpl
implements DataFieldDesignService {
    @Autowired
    private IDataFieldDao<DesignDataFieldDO> dataFieldDao;
    @Autowired
    private IDataTableDao<DesignDataTableDO> dataTableDao;
    @Autowired
    private FieldValidator fieldValidator;

    @Override
    public List<DesignDataField> filterField(FieldSearchQuery fieldSearchQuery) {
        if (fieldSearchQuery == null) {
            return Collections.emptyList();
        }
        List<DesignDataFieldDO> doList = this.dataFieldDao.filterField(fieldSearchQuery);
        return new ArrayList<DesignDataField>(doList);
    }

    @Override
    public int countBy(FieldSearchQuery fieldSearchQuery) {
        if (fieldSearchQuery == null) {
            return 0;
        }
        return this.dataFieldDao.countBy(fieldSearchQuery);
    }

    @Override
    public List<SearchDataFieldDTO> searchFieldByScheme(FieldSearchQuery fieldSearchQuery) {
        if (fieldSearchQuery == null) {
            return Collections.emptyList();
        }
        List<DesignDataField> fields = this.filterField(fieldSearchQuery);
        HashMap<String, DesignDataTableDO> tableTemp = new HashMap<String, DesignDataTableDO>();
        for (DesignDataField designDataField : fields) {
            String dataTableKey = designDataField.getDataTableKey();
            tableTemp.put(dataTableKey, null);
        }
        List<DesignDataTableDO> list = this.dataTableDao.batchGet(new ArrayList<String>(tableTemp.keySet()));
        for (DesignDataTableDO dataTableDO : list) {
            tableTemp.put(dataTableDO.getKey(), dataTableDO);
        }
        ArrayList<SearchDataFieldDTO> arrayList = new ArrayList<SearchDataFieldDTO>();
        for (DesignDataField numDataFieldDO : fields) {
            SearchDataFieldDTO dataField = new SearchDataFieldDTO(numDataFieldDO);
            DesignDataTableDO dataTableDO = (DesignDataTableDO)tableTemp.get(numDataFieldDO.getDataTableKey());
            if (dataTableDO == null) continue;
            dataField.setDesignDataTable(dataTableDO);
            arrayList.add(dataField);
        }
        return arrayList;
    }

    @Override
    public int position(DesignDataField dataFieldDO, int pageCount) {
        String dataTableKey = dataFieldDO.getDataTableKey();
        String key = dataFieldDO.getKey();
        Assert.notNull((Object)key, "key must not be null.");
        Assert.notNull((Object)dataTableKey, "dataTableKey must not be null.");
        int rowNumber = this.dataFieldDao.getRowNumber(dataTableKey, key, DataFieldKind.FIELD_ZB.getValue() | DataFieldKind.FIELD.getValue() | DataFieldKind.TABLE_FIELD_DIM.getValue());
        return rowNumber / pageCount + 1;
    }

    @Override
    public List<DesignDataField> queryCandidate(String table) {
        Assert.notNull((Object)table, "table must not be null.");
        List<DesignDataFieldDO> fields = this.dataFieldDao.getByTable(table);
        return fields.stream().filter(Objects::nonNull).filter(r -> r.getRefDataFieldKey() != null || r.getDataFieldType() == DataFieldType.STRING || r.getDataFieldType() == DataFieldType.DATE).filter(r -> DataFieldKind.FIELD.equals((Object)r.getDataFieldKind()) || DataFieldKind.TABLE_FIELD_DIM.equals((Object)r.getDataFieldKind())).filter(r -> !r.isEncrypted()).collect(Collectors.toList());
    }

    @Override
    public List<MoveDataFieldDTO> move(DataFieldMovePM move) {
        long count;
        int i1;
        DesignDataField field;
        int i;
        String tmpOrder;
        ArrayList<DesignDataFieldDO> list;
        List<DesignDataFieldDO> designDataFields;
        List<String> keys = move.getKeys();
        Integer skip = move.getSkip();
        Integer limit = move.getLimit();
        String table = move.getTable();
        Integer pageCount = move.getPageCount();
        if (limit == null || skip == null || table == null || pageCount == null) {
            return null;
        }
        int index = skip;
        FieldSearchQuery fieldSearchQuery = new FieldSearchQuery();
        fieldSearchQuery.setTable(table);
        fieldSearchQuery.setSkip(skip);
        fieldSearchQuery.setLimit(Integer.valueOf(limit + 1));
        fieldSearchQuery.setOrder("DF_KIND DESC , DF_ORDER");
        boolean preGet = false;
        boolean moveUp = move.isMoveUp();
        if (moveUp) {
            if (skip > 0) {
                preGet = true;
                fieldSearchQuery.setSkip(Integer.valueOf(skip - 1));
            }
        } else {
            fieldSearchQuery.setLimit(Integer.valueOf(limit + 2));
        }
        if (CollectionUtils.isEmpty(designDataFields = this.dataFieldDao.filterField(fieldSearchQuery))) {
            throw new SchemeDataException("\u79fb\u52a8\u5931\u8d25\uff0c\u8bf7\u5237\u65b0\u9875\u9762\u91cd\u8bd5!");
        }
        if (designDataFields.size() == 1) {
            throw new SchemeDataException("\u79fb\u52a8\u5931\u8d25\uff0c\u81f3\u5c11\u9700\u8981\u6709\u4e24\u4e2a\u6307\u6807\u624d\u53ef\u4ee5\u79fb\u52a8\u987a\u5e8f!");
        }
        HashSet<String> keySet = new HashSet<String>(keys);
        int beginIndex = -1;
        boolean outside = false;
        for (int i2 = 0; i2 < designDataFields.size(); ++i2) {
            DesignDataFieldDO field2 = designDataFields.get(i2);
            this.fieldValidator.levelCheckField((DesignDataField)field2);
            String key = field2.getKey();
            if (keySet.isEmpty()) break;
            boolean remove = keySet.remove(key);
            if (remove) {
                if (moveUp) {
                    if (i2 == 0) {
                        throw new SchemeDataException("\u79fb\u52a8\u5931\u8d25\uff0c\u8bf7\u5237\u65b0\u9875\u9762\u91cd\u8bd5!");
                    }
                    if (designDataFields.size() - 1 > limit - skip && i2 == 1) {
                        outside = true;
                    }
                } else {
                    if (i2 == designDataFields.size() - 1) {
                        throw new SchemeDataException("\u79fb\u52a8\u5931\u8d25\uff0c\u8bf7\u5237\u65b0\u9875\u9762\u91cd\u8bd5!");
                    }
                    if (designDataFields.size() - 1 > limit - skip && i2 == designDataFields.size() - 2) {
                        outside = true;
                    }
                }
                if (beginIndex != -1) continue;
                beginIndex = i2;
                continue;
            }
            if (beginIndex == -1) continue;
            throw new SchemeDataException("\u79fb\u52a8\u5931\u8d25\uff0c\u8bf7\u5237\u65b0\u9875\u9762\u91cd\u8bd5!");
        }
        if (!keySet.isEmpty()) {
            throw new SchemeDataException("\u79fb\u52a8\u5931\u8d25\uff0c\u8bf7\u5237\u65b0\u9875\u9762\u91cd\u8bd5!");
        }
        if (moveUp) {
            list = new ArrayList<DesignDataFieldDO>(designDataFields.subList(beginIndex - 1, beginIndex + keys.size()));
            tmpOrder = null;
            for (i = list.size() - 1; i >= 0; --i) {
                field = (DesignDataField)list.get(i);
                if (tmpOrder == null) {
                    tmpOrder = field.getOrder();
                }
                if ((i1 = i - 1) >= 0) {
                    field.setOrder(((DesignDataFieldDO)list.get(i1)).getOrder());
                } else {
                    field.setOrder(tmpOrder);
                }
                field.setUpdateTime(null);
            }
        } else {
            list = new ArrayList<DesignDataFieldDO>(designDataFields.subList(beginIndex, beginIndex + keys.size() + 1));
            tmpOrder = null;
            for (i = 0; i < list.size(); ++i) {
                field = (DesignDataField)list.get(i);
                if (tmpOrder == null) {
                    tmpOrder = field.getOrder();
                }
                if ((i1 = i + 1) < list.size()) {
                    field.setOrder(((DesignDataFieldDO)list.get(i1)).getOrder());
                } else {
                    field.setOrder(tmpOrder);
                }
                field.setUpdateTime(null);
            }
        }
        if ((count = list.stream().map(DataFieldDO::getDataFieldKind).distinct().count()) > 1L) {
            throw new SchemeDataException("\u5df2\u7ecf\u79fb\u52a8\u5230\u9009\u4e2d\u7c7b\u578b\u7684\u8fb9\u754c\uff0c\u4e0d\u53ef\u79fb\u52a8\uff01");
        }
        this.dataFieldDao.batchUpdate(list);
        designDataFields.sort((Comparator<DesignDataFieldDO>)new DataFieldComparator());
        int rSkip = 0;
        int rLimit = pageCount;
        if (outside) {
            if (moveUp) {
                if (index > 0) {
                    --index;
                }
            } else {
                ++index;
                rSkip = preGet ? 2 : 1;
            }
        } else if (preGet) {
            rSkip = 1;
        }
        if (outside) {
            if (moveUp) {
                return this.getField(table, skip - pageCount, skip);
            }
            return this.getField(table, limit + 1, limit + pageCount + 1);
        }
        designDataFields = designDataFields.stream().skip(rSkip).limit(rLimit).collect(Collectors.toList());
        ArrayList<MoveDataFieldDTO> fields = new ArrayList<MoveDataFieldDTO>(pageCount);
        for (DesignDataFieldDO dataFieldDO : designDataFields) {
            MoveDataFieldDTO moveDataFieldDTO = new MoveDataFieldDTO();
            moveDataFieldDTO.setIndex(++index);
            fields.add(moveDataFieldDTO);
            BeanUtils.copyProperties(dataFieldDO, moveDataFieldDTO);
        }
        return fields;
    }

    private List<MoveDataFieldDTO> getField(String table, int skip, int limit) {
        FieldSearchQuery fieldSearchQuery = new FieldSearchQuery();
        fieldSearchQuery.setTable(table);
        fieldSearchQuery.setSkip(Integer.valueOf(skip));
        fieldSearchQuery.setLimit(Integer.valueOf(limit));
        fieldSearchQuery.setOrder("DF_KIND DESC , DF_ORDER");
        List<DesignDataFieldDO> designDataFields = this.dataFieldDao.filterField(fieldSearchQuery);
        ArrayList<MoveDataFieldDTO> fields = new ArrayList<MoveDataFieldDTO>(limit + 1);
        for (DesignDataFieldDO dataFieldDO : designDataFields) {
            MoveDataFieldDTO moveDataFieldDTO = new MoveDataFieldDTO();
            moveDataFieldDTO.setIndex(++skip);
            fields.add(moveDataFieldDTO);
            BeanUtils.copyProperties(dataFieldDO, moveDataFieldDTO);
        }
        return fields;
    }

    private void maintenance(List<DesignDataFieldDO> list) {
        if (null == list || list.isEmpty()) {
            return;
        }
        for (DesignDataFieldDO field : list) {
            if (DataFieldKind.TABLE_FIELD_DIM != field.getDataFieldKind()) continue;
            DesignDataTableDO table = this.dataTableDao.get(field.getDataTableKey());
            String[] bizKeys = table.getBizKeys();
            HashSet<DesignDataFieldDO> bizKeyFields = new HashSet<DesignDataFieldDO>(this.dataFieldDao.batchGet(Arrays.stream(bizKeys).collect(Collectors.toList())));
            table.setBizKeys(BizKeyOrder.order(bizKeyFields));
            this.dataTableDao.update(table);
            break;
        }
    }
}

