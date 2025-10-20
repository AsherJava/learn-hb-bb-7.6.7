/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.calculate.formula.service.impl.gcformula;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GcReportInputDataColmn {
    private List<Column<FieldDefine>> inputDataColmns = new ArrayList<Column<FieldDefine>>();

    public GcReportInputDataColmn(String ... tableNames) {
        this.initColmn(tableNames);
    }

    public List<Column<FieldDefine>> getColumns() {
        return this.inputDataColmns;
    }

    private void initColmn(String ... tableNames) {
        DefinitionsCache definitionsCache;
        ExecutorContext executorContext = new ExecutorContext((IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class));
        try {
            definitionsCache = new DefinitionsCache(executorContext);
        }
        catch (ParseException e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        for (String tableName : tableNames) {
            String dataSource = EntityTableCollector.getInstance().getDataSourceByName(tableName);
            TableModelDefine tableModelDefine = ((DataModelService)SpringBeanProvider.getBean(DataModelService.class)).getTableModelDefineByName(tableName, dataSource);
            if (Objects.isNull(tableModelDefine)) {
                throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u8868\u540d\u4e3a\u201c" + tableName + "\u201d\u7684\u8868\u5b9a\u4e49\u3002");
            }
            TableModelRunInfo table = definitionsCache.getDataModelDefinitionsCache().getTableInfo(tableModelDefine);
            if (table == null || table.getColumnFieldMap().isEmpty()) {
                throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u8868\u540d\u4e3a\u201c" + tableName + "\u201d\u7684\u8868\u5b9a\u4e49\u3002");
            }
            table.getColumnFieldMap().forEach((columnModelDefine, fieldDefine) -> {
                try {
                    int dataType = 6;
                    if (fieldDefine != null) {
                        switch (fieldDefine.getType()) {
                            case FIELD_TYPE_FLOAT: {
                                dataType = 3;
                                break;
                            }
                            case FIELD_TYPE_STRING: {
                                dataType = 6;
                                break;
                            }
                            case FIELD_TYPE_INTEGER: {
                                dataType = 5;
                                break;
                            }
                            case FIELD_TYPE_LOGIC: {
                                dataType = 1;
                                break;
                            }
                            case FIELD_TYPE_DATE: {
                                dataType = 2;
                                break;
                            }
                            case FIELD_TYPE_DATE_TIME: {
                                dataType = 2;
                                break;
                            }
                            case FIELD_TYPE_TIME: {
                                dataType = 2;
                                break;
                            }
                            case FIELD_TYPE_UUID: {
                                dataType = 6;
                                break;
                            }
                            case FIELD_TYPE_DECIMAL: {
                                dataType = 3;
                                break;
                            }
                            case FIELD_TYPE_TEXT: {
                                dataType = 3;
                                break;
                            }
                            case FIELD_TYPE_GENERAL: 
                            case FIELD_TYPE_TIME_STAMP: 
                            case FIELD_TYPE_PICTURE: 
                            case FIELD_TYPE_BINARY: 
                            case FIELD_TYPE_LATITUDE_LONGITUDE: 
                            case FIELD_TYPE_QRCODE: 
                            case FIELD_TYPE_OBJECT_ARRAY: 
                            case FIELD_TYPE_ERROR: {
                                dataType = 0;
                            }
                        }
                    }
                    this.inputDataColmns.add((Column<FieldDefine>)new Column(columnModelDefine.getName(), dataType, fieldDefine));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

