/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.jiuqi.nr.dafafill.exception.DataFillRuntimeException;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.DataFillEntityAnslysisItemInfo;
import com.jiuqi.nr.dafafill.model.DataFillEntityData;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataAnalysisInfo;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataBase;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataResult;
import com.jiuqi.nr.dafafill.model.DataFillModel;
import com.jiuqi.nr.dafafill.model.FieldFormat;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.ShowContent;
import com.jiuqi.nr.dafafill.service.IDFDimensionQueryFieldParser;
import com.jiuqi.nr.dafafill.service.IDataFillEntityDataAdapter;
import com.jiuqi.nr.dafafill.service.IDataFillEntityDataService;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DataFillEntityDataServiceImpl
implements IDataFillEntityDataService {
    @Autowired
    private List<IDataFillEntityDataAdapter> dataFillEntityDataAdapters;
    @Autowired
    private IDFDimensionQueryFieldParser dFDimensionParser;
    @Autowired
    private IEntityMetaService entityMetaService;

    @Override
    public DataFillEntityDataResult query(DataFillEntityDataQueryInfo queryInfo) {
        DataFillContext context = queryInfo.getContext();
        Map<String, QueryField> queryFieldsMap = this.dFDimensionParser.getQueryFieldsMap(context);
        QueryField queryField = queryFieldsMap.get(queryInfo.getFullCode());
        IDataFillEntityDataAdapter entityDataAdapter = this.getEntityDataAdapter(queryField);
        DataFillEntityDataResult dataFillEntityDataResult = new DataFillEntityDataResult();
        ArrayList<DataFillEntityData> items = new ArrayList<DataFillEntityData>();
        List<DataFillEntityDataBase> simpleItems = entityDataAdapter.query(queryInfo);
        simpleItems = this.deleteBBLX7OrH(queryInfo, simpleItems);
        for (DataFillEntityDataBase dataFillEntityDataBase : simpleItems) {
            DataFillEntityData dataFillEntityData = this.addRowCaption(queryField, dataFillEntityDataBase);
            items.add(dataFillEntityData);
        }
        dataFillEntityDataResult.setItems(items);
        dataFillEntityDataResult.setMessage("\u67e5\u8be2\u6210\u529f");
        dataFillEntityDataResult.setSuccess(true);
        return dataFillEntityDataResult;
    }

    @Override
    public DataFillEntityData queryByIdOrCode(DataFillEntityDataQueryInfo queryInfo) {
        DataFillContext context = queryInfo.getContext();
        Map<String, QueryField> queryFieldsMap = this.dFDimensionParser.getQueryFieldsMap(context);
        QueryField queryField = queryFieldsMap.get(queryInfo.getFullCode());
        IDataFillEntityDataAdapter entityDataAdapter = this.getEntityDataAdapter(queryField);
        DataFillEntityDataBase dataFillEntityDataBase = entityDataAdapter.queryByIdOrCode(queryInfo);
        if (null != dataFillEntityDataBase) {
            return this.addRowCaption(queryField, dataFillEntityDataBase);
        }
        return null;
    }

    @Override
    public List<DataFillEntityData> queryMultiValByIdOrCode(DataFillEntityDataQueryInfo queryInfo) {
        ArrayList<DataFillEntityData> ret = new ArrayList<DataFillEntityData>();
        String[] codeArray = queryInfo.getCode().split(";");
        DataFillContext context = queryInfo.getContext();
        Map<String, QueryField> queryFieldsMap = this.dFDimensionParser.getQueryFieldsMap(context);
        QueryField queryField = queryFieldsMap.get(queryInfo.getFullCode());
        IDataFillEntityDataAdapter entityDataAdapter = this.getEntityDataAdapter(queryField);
        for (String eachCode : codeArray) {
            queryInfo.setCode(eachCode);
            DataFillEntityDataBase dataFillEntityDataBase = entityDataAdapter.queryByIdOrCode(queryInfo);
            if (null == dataFillEntityDataBase) continue;
            ret.add(this.addRowCaption(queryField, dataFillEntityDataBase));
        }
        return ret;
    }

    private IDataFillEntityDataAdapter getEntityDataAdapter(QueryField queryField) {
        Optional<IDataFillEntityDataAdapter> findFirst = this.dataFillEntityDataAdapters.stream().filter(e -> e.accept(queryField)).findFirst();
        if (findFirst.isPresent()) {
            return findFirst.get();
        }
        throw new DataFillRuntimeException("queryField:" + queryField + ";not found IDataFillEntityDataAdapter");
    }

    private DataFillEntityData addRowCaption(QueryField queryField, DataFillEntityDataBase dataFillEntityDataBase) {
        DataFillEntityData dataFillEntityData = new DataFillEntityData(dataFillEntityDataBase);
        FieldFormat showFormat = queryField.getShowFormat();
        if (null == showFormat || showFormat.getShowContent() == null) {
            dataFillEntityData.setRowCaption(dataFillEntityDataBase.getTitle());
        } else {
            ShowContent showContent = showFormat.getShowContent();
            if (ShowContent.NONE == showContent) {
                dataFillEntityData.setRowCaption(dataFillEntityDataBase.getTitle());
            } else if (ShowContent.CODE == showContent) {
                dataFillEntityData.setRowCaption(dataFillEntityDataBase.getCode());
            } else if (ShowContent.TITLE == showContent) {
                dataFillEntityData.setRowCaption(dataFillEntityDataBase.getTitle());
            } else if (ShowContent.CODE_TITLE == showContent) {
                dataFillEntityData.setRowCaption(dataFillEntityDataBase.getCode() + "|" + dataFillEntityDataBase.getTitle());
            } else if (ShowContent.TITLE_CODE == showContent) {
                dataFillEntityData.setRowCaption(dataFillEntityDataBase.getTitle() + "|" + dataFillEntityDataBase.getCode());
            }
        }
        return dataFillEntityData;
    }

    @Override
    public DataFillEntityData queryByPrimaryOrSearch(DataFillEntityDataQueryInfo queryInfo) {
        DataFillEntityDataQueryInfo byCodeInfo = new DataFillEntityDataQueryInfo();
        byCodeInfo.setContext(queryInfo.getContext());
        byCodeInfo.setFullCode(queryInfo.getFullCode());
        String code = queryInfo.getCode();
        String search = queryInfo.getSearch();
        if (!StringUtils.hasLength(code) && StringUtils.hasLength(search)) {
            code = search;
        }
        byCodeInfo.setCode(code);
        DataFillEntityData queryByIdOrCode = this.queryByIdOrCode(byCodeInfo);
        if (null != queryByIdOrCode) {
            return queryByIdOrCode;
        }
        byCodeInfo.setCode(null);
        byCodeInfo.setSearch(code);
        DataFillEntityDataResult query = this.query(byCodeInfo);
        if (query.isSuccess() && query.getItems() != null && query.getItems().size() > 0) {
            return query.getItems().get(0);
        }
        return null;
    }

    @Override
    public List<Object> queryByPrimaryOrSearch(DataFillEntityDataAnalysisInfo queryInfo) {
        ArrayList<Object> result = new ArrayList<Object>();
        List<DataFillEntityAnslysisItemInfo> items = queryInfo.getItems();
        DataFillModel model = queryInfo.getModel();
        DataFillContext context = new DataFillContext();
        context.setModel(model);
        String fullCode = queryInfo.getFullCode();
        block0: for (DataFillEntityAnslysisItemInfo dataFillEntityAnslysisItemInfo : items) {
            DataFillEntityData queryByPrimaryOrSearch = null;
            String search = dataFillEntityAnslysisItemInfo.getSearch();
            if (!StringUtils.hasLength(search) || !StringUtils.hasLength(search.trim())) continue;
            search = search.trim();
            DataFillEntityDataQueryInfo info = new DataFillEntityDataQueryInfo();
            context.setDimensionValues(dataFillEntityAnslysisItemInfo.getDimensionValues());
            info.setContext(context);
            info.setFullCode(fullCode);
            if (search.contains(";")) {
                String[] codeList = search.split(";");
                ArrayList<DataFillEntityData> tempDataFillEntityDataList = new ArrayList<DataFillEntityData>();
                String[] stringArray = codeList;
                int n = stringArray.length;
                block1: for (int i = 0; i < n; ++i) {
                    String eachCode = stringArray[i];
                    info.setCode(eachCode);
                    queryByPrimaryOrSearch = this.queryByPrimaryOrSearch(info);
                    if (null == queryByPrimaryOrSearch) {
                        String[] split;
                        if (!eachCode.contains("|")) continue;
                        for (String codeOrTitle : split = eachCode.split("\\|")) {
                            info.setCode(codeOrTitle);
                            queryByPrimaryOrSearch = this.queryByIdOrCode(info);
                            if (null == queryByPrimaryOrSearch) continue;
                            tempDataFillEntityDataList.add(queryByPrimaryOrSearch);
                            continue block1;
                        }
                        continue;
                    }
                    tempDataFillEntityDataList.add(queryByPrimaryOrSearch);
                }
                result.add(tempDataFillEntityDataList);
                continue;
            }
            info.setCode(search);
            queryByPrimaryOrSearch = this.queryByPrimaryOrSearch(info);
            if (null == queryByPrimaryOrSearch) {
                String[] split;
                if (!search.contains("|")) continue;
                for (String codeOrTitle : split = search.split("\\|")) {
                    info.setCode(codeOrTitle);
                    queryByPrimaryOrSearch = this.queryByIdOrCode(info);
                    if (null == queryByPrimaryOrSearch) continue;
                    result.add(queryByPrimaryOrSearch);
                    continue block0;
                }
                continue;
            }
            result.add(queryByPrimaryOrSearch);
        }
        return result;
    }

    private List<DataFillEntityDataBase> deleteBBLX7OrH(DataFillEntityDataQueryInfo queryInfo, List<DataFillEntityDataBase> entityDatas) {
        IEntityAttribute bblxField;
        TableModelDefine tableModel;
        DataFillContext context = queryInfo.getContext();
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
        List<QueryField> dwList = fieldTypeQueryFields.get((Object)FieldType.MASTER);
        List<QueryField> list = context.getModel().getQueryFields();
        String entityId = null;
        for (QueryField queryField : list) {
            if (!queryField.getFullCode().equals(queryInfo.getFullCode())) continue;
            entityId = queryField.getExpression();
            break;
        }
        String dwId = dwList.get(0).getSimplifyFullCode();
        if (entityDatas == null || entityDatas.isEmpty()) {
            return entityDatas;
        }
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(dwId);
        if (dwEntityModel != null && (tableModel = this.entityMetaService.getTableModel(entityId)) != null && (bblxField = dwEntityModel.getBblxField()) != null) {
            String referTableID = bblxField.getReferTableID();
            if (tableModel.getID().equals(referTableID)) {
                return entityDatas.stream().filter(e -> !e.getId().equals("7")).collect(Collectors.toList());
            }
        }
        return entityDatas;
    }
}

