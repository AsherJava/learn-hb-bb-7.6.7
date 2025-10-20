/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.datasource.database.QueryDataBaseHandler
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 */
package com.jiuqi.va.query.datasource.gather.impl;

import com.jiuqi.va.query.datasource.database.DamengDataBaseHandler;
import com.jiuqi.va.query.datasource.database.GaussDataBaseHandler;
import com.jiuqi.va.query.datasource.database.GbasedbtDataBaseHandler;
import com.jiuqi.va.query.datasource.database.HanaDataBaseHandler;
import com.jiuqi.va.query.datasource.database.KingbaseDataBaseHandler;
import com.jiuqi.va.query.datasource.database.MysqlDataBaseHandler;
import com.jiuqi.va.query.datasource.database.OracleDataBaseHandler;
import com.jiuqi.va.query.datasource.database.QueryDataBaseHandler;
import com.jiuqi.va.query.datasource.database.ShentongOscarDataBaseHandler;
import com.jiuqi.va.query.datasource.database.SqlServerDataBaseHandler;
import com.jiuqi.va.query.datasource.gather.DataBaseHandleGather;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataBaseHandleGatherImpl
implements DataBaseHandleGather,
InitializingBean {
    private final Map<String, QueryDataBaseHandler> typeNameToHandlerMap = new ConcurrentHashMap<String, QueryDataBaseHandler>();
    private final Map<String, String> productNameToTypeNameMap = new ConcurrentHashMap<String, String>();
    private static List<QueryDataBaseHandler> staticDataBaseHandle = new ArrayList<QueryDataBaseHandler>();
    private static final String NOT_SUPPORT_MESSAGE = "\u6682\u4e0d\u652f\u6301\u8be5\u6570\u636e\u5e93\u7c7b\u578b";
    @Autowired(required=false)
    private List<QueryDataBaseHandler> handleList;

    @Override
    public void afterPropertiesSet() {
        this.registerQueryDataBaseHandles();
    }

    @Override
    public QueryDataBaseHandler getHandleServiceByTypeName(String dataBaseType) {
        if (DCQueryStringHandle.isEmpty(dataBaseType)) {
            throw new DefinedQueryRuntimeException("\u83b7\u53d6\u6570\u636e\u5e93\u5904\u7406\u5668\u5931\u8d25\uff0c\u6570\u636e\u5e93\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        QueryDataBaseHandler handleService = this.typeNameToHandlerMap.get(dataBaseType);
        if (handleService == null) {
            throw new DefinedQueryRuntimeException("\u6682\u4e0d\u652f\u6301\u8be5\u6570\u636e\u5e93\u7c7b\u578b\uff1a" + dataBaseType);
        }
        return handleService;
    }

    @Override
    public QueryDataBaseHandler getHandleServiceByProductName(String productName) {
        if (DCQueryStringHandle.isEmpty(productName)) {
            throw new DefinedQueryRuntimeException("\u83b7\u53d6\u6570\u636e\u5e93\u5904\u7406\u5668\u5931\u8d25\uff0c\u6570\u636e\u5e93\u4ea7\u54c1\u540d\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        String dataBaseType = this.productNameToTypeNameMap.get(productName.toLowerCase());
        if (DCQueryStringHandle.isEmpty(dataBaseType)) {
            throw new DefinedQueryRuntimeException("\u6682\u4e0d\u652f\u6301\u8be5\u6570\u636e\u5e93\u7c7b\u578b\uff1a" + productName);
        }
        return this.getHandleServiceByTypeName(dataBaseType);
    }

    @Override
    public List<QueryDataBaseHandler> getAllHandle() {
        return this.handleList;
    }

    @Override
    public String getTypeNameByProductName(String productName) {
        if (DCQueryStringHandle.isEmpty(productName)) {
            throw new DefinedQueryRuntimeException("\u83b7\u53d6\u6570\u636e\u5e93\u5904\u7406\u5668\u5931\u8d25\uff0c\u6570\u636e\u5e93\u4ea7\u54c1\u540d\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        String dataBaseType = this.productNameToTypeNameMap.get(productName.toLowerCase());
        if (DCQueryStringHandle.isEmpty(dataBaseType)) {
            throw new DefinedQueryRuntimeException("\u6682\u4e0d\u652f\u6301\u8be5\u6570\u636e\u5e93\u7c7b\u578b\uff1a" + productName);
        }
        return dataBaseType;
    }

    private synchronized void registerQueryDataBaseHandles() {
        if (this.handleList == null) {
            this.handleList = new ArrayList<QueryDataBaseHandler>();
        }
        this.handleList.addAll(staticDataBaseHandle);
        this.typeNameToHandlerMap.clear();
        this.productNameToTypeNameMap.clear();
        for (QueryDataBaseHandler handle : this.handleList) {
            if (handle.getTypeName() == null) {
                throw new IllegalArgumentException(handle + ".DataBaseType must not be null.");
            }
            if (this.typeNameToHandlerMap.containsKey(handle.getTypeName())) {
                throw new IllegalArgumentException("Need one QueryDataBaseHandle '" + handle.getTypeName() + "',But Find two.");
            }
            if (this.productNameToTypeNameMap.containsKey(handle.getProductName())) {
                throw new IllegalArgumentException("Need one QueryDataBaseHandle '" + handle.getProductName() + "',But Find two.");
            }
            this.typeNameToHandlerMap.put(handle.getTypeName(), handle);
            this.productNameToTypeNameMap.put(handle.getProductName().toLowerCase(), handle.getTypeName());
        }
    }

    static {
        staticDataBaseHandle.add((QueryDataBaseHandler)new DamengDataBaseHandler());
        staticDataBaseHandle.add((QueryDataBaseHandler)new HanaDataBaseHandler());
        staticDataBaseHandle.add((QueryDataBaseHandler)new KingbaseDataBaseHandler());
        staticDataBaseHandle.add((QueryDataBaseHandler)new MysqlDataBaseHandler());
        staticDataBaseHandle.add((QueryDataBaseHandler)new OracleDataBaseHandler());
        staticDataBaseHandle.add((QueryDataBaseHandler)new SqlServerDataBaseHandler());
        staticDataBaseHandle.add((QueryDataBaseHandler)new GbasedbtDataBaseHandler());
        staticDataBaseHandle.add((QueryDataBaseHandler)new GaussDataBaseHandler());
        staticDataBaseHandle.add((QueryDataBaseHandler)new ShentongOscarDataBaseHandler());
    }
}

