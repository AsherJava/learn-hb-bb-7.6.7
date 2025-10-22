/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.common.db.DatabaseInstance
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptFactory
 */
package com.jiuqi.np.dataengine;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.config.DataEngineProperties;
import com.jiuqi.np.dataengine.event.OperateRowEventListener;
import com.jiuqi.np.dataengine.intf.EntityResetCacheService;
import com.jiuqi.np.dataengine.intf.IDataChangeListener;
import com.jiuqi.np.dataengine.intf.IDataMaskingProcesser;
import com.jiuqi.np.dataengine.intf.IEncryptDecryptProcesser;
import com.jiuqi.np.dataengine.intf.IValueValidateHandler;
import com.jiuqi.np.dataengine.intf.SplitTableHelper;
import com.jiuqi.np.dataengine.multi.IMultiDimAdapter;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptFactory;
import java.sql.Connection;
import java.util.List;

public class QueryParam {
    private IEntityViewRunTimeController entityViewRunTimeController;
    private IConnectionProvider connectionProvider;
    private IDataDefinitionRuntimeController runtimeController;
    private IDataDefinitionDesignTimeController designTimeController;
    private EntityIdentityService entityLinkService;
    private OperateRowEventListener eventListener;
    private Connection connection;
    private IDatabase database;
    private boolean sameConnection;
    private NedisCacheManager cacheManager;
    private EntityResetCacheService entityResetCacheService;
    private IEncryptDecryptProcesser encryptDecryptProcesser;
    private List<IValueValidateHandler> validateHandlers;
    private List<IDataChangeListener> dataChangeListeners;
    private SplitTableHelper splitTableHelper;
    private NrdbHelper nrdbHelper;
    private SymmetricEncryptFactory symmetricEncryptFactory;
    private DataEngineProperties properties;
    private IMultiDimAdapter multiDimAdapter;
    private IDataMaskingProcesser dataMaskingProcesser;

    public QueryParam(IConnectionProvider connectionProvider, IDataDefinitionRuntimeController runtimeController, IDatabase database) {
        this.connectionProvider = connectionProvider;
        this.runtimeController = runtimeController;
        this.database = database;
    }

    public QueryParam(IConnectionProvider connectionProvider, IDataDefinitionRuntimeController runtimeController) {
        this(connectionProvider, runtimeController, null);
    }

    public QueryParam(IConnectionProvider connectionProvider, IDataDefinitionRuntimeController runtimeController, IDataDefinitionDesignTimeController designTimeController, IEntityViewRunTimeController entityViewRunTimeController, IDatabase database) {
        this.connectionProvider = connectionProvider;
        this.runtimeController = runtimeController;
        this.designTimeController = designTimeController;
        this.entityViewRunTimeController = entityViewRunTimeController;
        this.database = database;
    }

    public QueryParam(IConnectionProvider connectionProvider, IDataDefinitionRuntimeController runtimeController, IDataDefinitionDesignTimeController designTimeController, IEntityViewRunTimeController entityViewRunTimeController) {
        this(connectionProvider, runtimeController, designTimeController, entityViewRunTimeController, null);
    }

    public QueryParam(IConnectionProvider connectionProvider, IDataDefinitionRuntimeController runtimeController, IDataDefinitionDesignTimeController designTimeController, IEntityViewRunTimeController entityViewRunTimeController, NedisCacheManager cacheManager, EntityResetCacheService entityResetCacheService, IDatabase database) {
        this.connectionProvider = connectionProvider;
        this.runtimeController = runtimeController;
        this.designTimeController = designTimeController;
        this.entityViewRunTimeController = entityViewRunTimeController;
        this.cacheManager = cacheManager;
        this.entityResetCacheService = entityResetCacheService;
        this.database = database;
    }

    public QueryParam(IConnectionProvider connectionProvider, IDataDefinitionRuntimeController runtimeController, IDataDefinitionDesignTimeController designTimeController, IEntityViewRunTimeController entityViewRunTimeController, NedisCacheManager cacheManager, EntityResetCacheService entityResetCacheService) {
        this(connectionProvider, runtimeController, designTimeController, entityViewRunTimeController, cacheManager, entityResetCacheService, null);
    }

    private QueryParam(IConnectionProvider connectionProvider, IDataDefinitionRuntimeController runtimeController, IDataDefinitionDesignTimeController designTimeController, IEntityViewRunTimeController entityViewRunTimeController, EntityIdentityService entityLinkService, IDatabase database) {
        this.connectionProvider = connectionProvider;
        this.runtimeController = runtimeController;
        this.designTimeController = designTimeController;
        this.entityViewRunTimeController = entityViewRunTimeController;
        this.entityLinkService = entityLinkService;
        this.database = database;
    }

    public IEntityViewRunTimeController getEntityViewRunTimeController() {
        return this.entityViewRunTimeController;
    }

    public void setEntityViewRunTimeController(IEntityViewRunTimeController entityViewRunTimeController) {
        this.entityViewRunTimeController = entityViewRunTimeController;
    }

    public IConnectionProvider getConnectionProvider() {
        return this.connectionProvider;
    }

    public void setConnectionProvider(IConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public IDataDefinitionRuntimeController getRuntimeController() {
        return this.runtimeController;
    }

    public void setRuntimeController(IDataDefinitionRuntimeController runtimeController) {
        this.runtimeController = runtimeController;
    }

    public IDataDefinitionDesignTimeController getDesignTimeController() {
        return this.designTimeController;
    }

    public void setDesignTimeController(IDataDefinitionDesignTimeController designTimeController) {
        this.designTimeController = designTimeController;
    }

    public Connection getConnection() {
        if (this.connection == null) {
            this.connection = this.connectionProvider.getConnection();
        }
        return this.connection;
    }

    public void closeConnection() {
        if (this.sameConnection) {
            return;
        }
        if (this.connection != null) {
            this.connectionProvider.closeConnection(this.connection);
        }
        this.connection = null;
    }

    public QueryParam clone() {
        QueryParam queryParam = new QueryParam(this.connectionProvider, this.runtimeController, this.designTimeController, this.entityViewRunTimeController, this.entityLinkService, this.database);
        queryParam.setEventListener(this.eventListener);
        queryParam.setCacheManager(this.cacheManager);
        queryParam.setEntityResetCacheService(this.entityResetCacheService);
        queryParam.setEncryptDecryptProcesser(this.encryptDecryptProcesser);
        queryParam.setValueValidateHandlers(this.validateHandlers);
        queryParam.setDataChangeListeners(this.dataChangeListeners);
        queryParam.setSplitTableHelper(this.splitTableHelper);
        queryParam.setNrdbHelper(this.nrdbHelper);
        queryParam.setSymmetricEncryptFactory(this.symmetricEncryptFactory);
        queryParam.setProperties(this.properties);
        queryParam.setDataMaskingProcesser(this.dataMaskingProcesser);
        queryParam.setMultiDimAdapter(this.multiDimAdapter);
        return queryParam;
    }

    public EntityIdentityService getEntityLinkService() {
        return this.entityLinkService;
    }

    public void setEntityLinkService(EntityIdentityService entityLinkService) {
        this.entityLinkService = entityLinkService;
    }

    public IDatabase getDatabase() {
        if (this.database == null) {
            this.database = DatabaseInstance.getDatabase();
        }
        return this.database;
    }

    public void setDatabase(IDatabase database) {
        this.database = database;
    }

    public void setEventListener(OperateRowEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public OperateRowEventListener getEventListener() {
        return this.eventListener;
    }

    public boolean isSameConnection() {
        return this.sameConnection;
    }

    public void setSameConnection(boolean sameConnection) {
        this.sameConnection = sameConnection;
    }

    public NedisCacheManager getCacheManager() {
        return this.cacheManager;
    }

    public void setCacheManager(NedisCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public EntityResetCacheService getEntityResetCacheService() {
        return this.entityResetCacheService;
    }

    public void setEntityResetCacheService(EntityResetCacheService entityResetCacheService) {
        this.entityResetCacheService = entityResetCacheService;
    }

    public IEncryptDecryptProcesser getEncryptDecryptProcesser() {
        return this.encryptDecryptProcesser;
    }

    public void setEncryptDecryptProcesser(IEncryptDecryptProcesser encryptDecryptProcesser) {
        this.encryptDecryptProcesser = encryptDecryptProcesser;
    }

    public List<IValueValidateHandler> getValueValidateHandlers() {
        return this.validateHandlers;
    }

    public void setValueValidateHandlers(List<IValueValidateHandler> valueValidateHandlers) {
        this.validateHandlers = valueValidateHandlers;
    }

    public List<IDataChangeListener> getDataChangeListeners() {
        return this.dataChangeListeners;
    }

    public void setDataChangeListeners(List<IDataChangeListener> dataChangeListeners) {
        this.dataChangeListeners = dataChangeListeners;
    }

    public SplitTableHelper getSplitTableHelper() {
        return this.splitTableHelper;
    }

    public void setSplitTableHelper(SplitTableHelper splitTableHelper) {
        this.splitTableHelper = splitTableHelper;
    }

    public NrdbHelper getNrdbHelper() {
        return this.nrdbHelper;
    }

    public void setNrdbHelper(NrdbHelper nrdbHelper) {
        this.nrdbHelper = nrdbHelper;
    }

    public SymmetricEncryptFactory getSymmetricEncryptFactory() {
        return this.symmetricEncryptFactory;
    }

    public void setSymmetricEncryptFactory(SymmetricEncryptFactory symmetricEncryptFactory) {
        this.symmetricEncryptFactory = symmetricEncryptFactory;
    }

    public boolean canModifyKey() {
        if (this.properties != null && this.properties.getCanModifyKey() != null) {
            return this.properties.getCanModifyKey();
        }
        return !this.getDatabase().isDatabase("GaussDB");
    }

    public DataEngineProperties getProperties() {
        return this.properties;
    }

    public void setProperties(DataEngineProperties properties) {
        this.properties = properties;
    }

    public IDataMaskingProcesser getDataMaskingProcesser() {
        return this.dataMaskingProcesser;
    }

    public void setDataMaskingProcesser(IDataMaskingProcesser dataMaskingProcesser) {
        this.dataMaskingProcesser = dataMaskingProcesser;
    }

    public IMultiDimAdapter getMultiDimAdapter() {
        return this.multiDimAdapter;
    }

    public void setMultiDimAdapter(IMultiDimAdapter multiDimAdapter) {
        this.multiDimAdapter = multiDimAdapter;
    }
}

