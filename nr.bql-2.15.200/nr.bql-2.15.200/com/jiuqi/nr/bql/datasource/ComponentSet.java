/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.nrdb.query.DBQueryExecutorProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.calibre2.ICalibreDataService
 *  com.jiuqi.nr.calibre2.ICalibreDefineService
 *  com.jiuqi.nr.common.intf.ISecretLevelFilter
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nvwa.dataanalysis.dataset.storage.DataSetStorageProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.nr.bql.datasource;

import com.jiuqi.np.dataengine.nrdb.query.DBQueryExecutorProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.bql.dataengine.IDataAccessProvider;
import com.jiuqi.nr.bql.datasource.UnitFilter;
import com.jiuqi.nr.bql.datasource.reader.EntityDimTableReader;
import com.jiuqi.nr.bql.datasource.reader.PeriodDimTableReader;
import com.jiuqi.nr.bql.intf.ITableDimensionAdapter;
import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.common.intf.ISecretLevelFilter;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nvwa.dataanalysis.dataset.storage.DataSetStorageProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.feign.client.BaseDataClient;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComponentSet {
    @Autowired
    public IDataAccessProvider dataAccessProvider;
    @Autowired
    public IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    public IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    public DefinitionAuthorityProvider definitionAuthorityProvider;
    @Autowired
    public PeriodDimTableReader periodDimTableReader;
    @Autowired
    public EntityDimTableReader entityDimTableReader;
    @Autowired(required=false)
    public ISecretLevelFilter secretLevelFilter;
    @Autowired
    public DataSource dataSource;
    @Autowired
    public UnitFilter unitfilter;
    @Autowired
    public BaseDataClient baseDataClient;
    @Autowired
    public ICalibreDefineService calibreDefineService;
    @Autowired
    public ICalibreDataService calibreDataService;
    @Autowired
    public IAdjustPeriodService adjustPeriodService;
    @Autowired
    public DataSetStorageProvider dataSetStorageProvider;
    @Autowired
    public INvwaDataAccessProvider nvwaDataAccessProvider;
    @Autowired
    public DataModelService dataModelService;
    @Autowired
    public NrdbHelper nrdbHelper;
    @Autowired(required=false)
    public DBQueryExecutorProvider dbQueryExecutorProvider;
    @Autowired(required=false)
    public ITableDimensionAdapter tableDimensionAdapter;
}

