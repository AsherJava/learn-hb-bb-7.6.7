/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DataTableRel
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataTableRel
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.DeployResult
 *  com.jiuqi.nr.datascheme.api.core.DeployStatusEnum
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeListener
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nr.datascheme.api.event.RefreshScheme
 *  com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.loader.des.LevelLoader
 *  com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor
 *  com.jiuqi.nr.datascheme.api.service.DataSchemeValidator
 *  com.jiuqi.nr.datascheme.api.service.DataTableValidator
 *  com.jiuqi.nr.datascheme.api.service.FieldValidator
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.CompareType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  javax.validation.ConstraintViolation
 *  javax.validation.Validator
 *  org.springframework.dao.DataAccessException
 *  org.springframework.dao.DataIntegrityViolationException
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DataTableRel;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.DesignDataTableRel;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.event.DataSchemeListener;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.api.event.RefreshScheme;
import com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.loader.des.LevelLoader;
import com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor;
import com.jiuqi.nr.datascheme.api.service.DataSchemeValidator;
import com.jiuqi.nr.datascheme.api.service.DataTableValidator;
import com.jiuqi.nr.datascheme.api.service.FieldValidator;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.CompareType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.BizKeyOrder;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.common.DataSchemeLoggerHelper;
import com.jiuqi.nr.datascheme.i18n.dao.DesignDataSchemeI18nDao;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.dao.IDataDimDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataGroupDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataSchemeCalResultDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataSchemeDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl;
import com.jiuqi.nr.datascheme.internal.deploy.progress.DSProgressCacheService;
import com.jiuqi.nr.datascheme.internal.dto.DataDimDesignDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDeployInfoDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDesignDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataGroupDesignDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDesignDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataTableDesignDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataTableRelDesignDTO;
import com.jiuqi.nr.datascheme.internal.dto.ValidationRuleDTO;
import com.jiuqi.nr.datascheme.internal.entity.DataDimDO;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.datascheme.internal.entity.DataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataDimDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataSchemeDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.internal.service.DataTableDesignService;
import com.jiuqi.nr.datascheme.internal.service.DataTableRelDesignService;
import com.jiuqi.nr.datascheme.internal.service.impl.CopyAttributes;
import com.jiuqi.nr.datascheme.internal.service.impl.CopyNodeSchemeVisitor;
import com.jiuqi.nr.datascheme.internal.service.impl.DeleteAttributes;
import com.jiuqi.nr.datascheme.internal.service.impl.DeployDataSchemeProxy;
import com.jiuqi.nr.datascheme.internal.service.impl.cache.DataFieldDefaultValueCache;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.common.ProgressItem;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Primary
public class DesignDataSchemeServiceImpl
implements IDesignDataSchemeService {
    @Value(value="${jiuqi.nr.datascheme.account.enable:false}")
    private boolean enableAccount;
    @Value(value="${jiuqi.nr.datascheme.service.log.enable:false}")
    private boolean enableServiceLog;
    @Autowired
    private IDataSchemeDao<DesignDataSchemeDO> dataSchemeDao;
    @Autowired
    private IDataGroupDao<DesignDataGroupDO> dataGroupDao;
    @Autowired
    private IDataTableDao<DesignDataTableDO> dataTableDao;
    @Autowired
    private DataTableDesignService dataTableService;
    @Autowired
    private IDataDimDao<DesignDataDimDO> dataDimDao;
    @Autowired
    private IDataFieldDao<DesignDataFieldDO> dataFieldDao;
    @Autowired(required=false)
    private List<DataSchemeListener> dataSchemeListeners;
    @Autowired
    private NpApplication npApplication;
    @Autowired
    private LevelLoader levelLoader;
    @Autowired
    private SchemeNodeVisitor<CopyAttributes> copyVisitor;
    @Autowired
    private SchemeNodeVisitor<DeleteAttributes> deleteVisitor;
    @Autowired
    private Validator validator;
    @Autowired
    private DesignDataSchemeI18nDao i18nDao;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DesignDataModelService dataModelService;
    @Autowired
    private DataModelService runtimeDataModelService;
    @Autowired
    private INvwaDataAccessProvider dataAccessProvider;
    @Autowired
    private FieldValidator fieldValidator;
    @Autowired
    private DataSchemeValidator dataSchemeValidator;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private IDataDimDao<DataDimDO> runDataDimDao;
    @Autowired
    private DataTableValidator dataTableValidator;
    @Autowired
    private DataTableRelDesignService dataTableRelDesignService;
    @Autowired
    private DataFieldDeployInfoDaoImpl dataFieldDeployInfoDao;
    @Autowired
    private IDataSchemeAuthService iDataSchemeAuthService;
    @Autowired
    private IDataSchemeDeployService dataSchemeDeployService;
    @Autowired
    private DataFieldDefaultValueCache dataFieldDefaultValueCache;
    @Autowired
    private DSProgressCacheService progressCacheService;
    @Autowired
    private DeployDataSchemeProxy deployDataSchemeProxy;
    @Autowired
    private IDataSchemeCalResultDao dataSchemeCalResultDao;
    @Autowired
    protected ApplicationContext applicationContext;
    private final Logger logger = LoggerFactory.getLogger(DesignDataSchemeServiceImpl.class);
    private static final byte SAVE = 0;
    private static final byte UPDATE = 1;
    private static final byte DELETE = 2;

    private static String newId() {
        return UUIDUtils.getKey();
    }

    public DesignDataScheme initDataScheme() {
        DataSchemeDesignDTO dataScheme = new DataSchemeDesignDTO();
        dataScheme.setKey(DesignDataSchemeServiceImpl.newId());
        return dataScheme;
    }

    @Transactional(rollbackFor={Exception.class})
    public String insertDataScheme(DesignDataScheme dataScheme) throws SchemeDataException {
        String insert = this.insertDataScheme0(dataScheme);
        this.savePostProcess(dataScheme, null);
        this.runListener(null, dataScheme, null, null, 0);
        return insert;
    }

    private void savePostProcess(DesignDataScheme dataScheme, List<DesignDataDimension> dims) {
    }

    private String insertDataScheme0(DesignDataScheme dataScheme) {
        String insert;
        this.dataSchemeValidator.checkDataScheme(dataScheme);
        DesignDataSchemeDO designDataSchemeDO = Convert.iDs2Do(dataScheme);
        try {
            insert = this.dataSchemeDao.insert(designDataSchemeDO);
        }
        catch (DataIntegrityViolationException e) {
            this.logger.warn("\u65b0\u589e\u6570\u636e\u65b9\u6848 code \u91cd\u590d", e);
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_1.getMessage(), (Throwable)e);
        }
        dataScheme.setKey(insert);
        this.iDataSchemeAuthService.grantAllPrivileges(dataScheme);
        return insert;
    }

    @Transactional(rollbackFor={Exception.class})
    public String insertDataScheme(DesignDataScheme dataScheme, List<DesignDataDimension> dimensions) throws SchemeDataException {
        if (CollectionUtils.isEmpty(dimensions)) {
            return this.insertDataScheme(dataScheme);
        }
        String scheme = this.insertDataScheme0(dataScheme);
        for (DesignDataDimension dimension : dimensions) {
            dimension.setOrder(OrderGenerator.newOrder());
            dimension.setDataSchemeKey(scheme);
            DesignDataDimDO designDataDimDO = Convert.iDm2Do(dimension);
            this.dataDimDao.insert(designDataDimDO);
        }
        this.savePostProcess(dataScheme, dimensions);
        this.runListener(null, dataScheme, dimensions, null, 0);
        return scheme;
    }

    private void validate(Ordered dataScheme) throws SchemeDataException {
        Set validate = this.validator.validate((Object)dataScheme, new Class[0]);
        if (validate != null && !validate.isEmpty()) {
            this.logger.info("\u6570\u636e\u9a8c\u8bc1\u4e0d\u901a\u8fc7: {}", (Object)dataScheme);
            Iterator iterator = validate.iterator();
            if (iterator.hasNext()) {
                ConstraintViolation item = (ConstraintViolation)iterator.next();
                String message = item.getMessage();
                throw new SchemeDataException(message);
            }
        }
    }

    private void runListener(DesignDataScheme srcDataScheme, DesignDataScheme dataScheme, List<DesignDataDimension> add, List<DesignDataDimension> delete, int type) {
        if (this.dataSchemeListeners == null) {
            return;
        }
        if (add != null && add.isEmpty()) {
            add = null;
        }
        if (delete != null && delete.isEmpty()) {
            add = null;
        }
        for (DataSchemeListener dataSchemeListener : this.dataSchemeListeners) {
            List<DesignDataDimension> finalAdd = add;
            this.npApplication.asyncRun(() -> {
                try {
                    switch (type) {
                        case 0: {
                            dataSchemeListener.savePostProcess(dataScheme, finalAdd);
                            break;
                        }
                        case 1: {
                            dataSchemeListener.updatePostProcess(srcDataScheme, dataScheme, finalAdd, delete);
                            break;
                        }
                        case 2: {
                            dataSchemeListener.deletePostProcess(srcDataScheme);
                            break;
                        }
                    }
                }
                catch (Exception e) {
                    this.logger.error("\u65b9\u6848\u540e\u5904\u7406\u5f02\u5e38", e);
                }
            });
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void deleteDataScheme(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        DesignDataScheme dataScheme = this.getDataScheme(key);
        if (dataScheme == null) {
            return;
        }
        this.checkDeployStatus(key);
        this.levelLoader.walkDataSchemeTree(dataScheme, this.deleteVisitor);
        this.iDataSchemeAuthService.revokeAll(dataScheme);
        this.dataSchemeCalResultDao.deleteResult(dataScheme.getKey());
        this.runListener(dataScheme, null, null, null, 2);
    }

    @Transactional(rollbackFor={Exception.class})
    public void updateDataScheme(DesignDataScheme dataScheme) throws SchemeDataException {
        DesignDataSchemeDO srcDataScheme = this.updateDataScheme0(dataScheme);
        this.updatePostProcess(srcDataScheme, dataScheme, null, null);
        this.runListener(srcDataScheme, dataScheme, null, null, 1);
    }

    private void updatePostProcess(DesignDataSchemeDO srcDataScheme, DesignDataScheme dataScheme, List<DesignDataDimension> srcDims, List<DesignDataDimension> dims) {
    }

    private DesignDataSchemeDO updateDataScheme0(DesignDataScheme dataScheme) {
        Assert.notNull((Object)dataScheme, "dataScheme must not be null.");
        String key = dataScheme.getKey();
        Assert.notNull((Object)key, "key must not be null.");
        Assert.notNull((Object)dataScheme.getCode(), "code must not be null.");
        Assert.notNull((Object)dataScheme.getTitle(), "title must not be null.");
        this.checkDeployStatus(key);
        this.dataSchemeValidator.checkDataScheme(dataScheme);
        DesignDataSchemeDO designDataSchemeDO = Convert.iDs2Do(dataScheme);
        DesignDataSchemeDO srcDataScheme = this.dataSchemeDao.get(designDataSchemeDO.getKey());
        designDataSchemeDO.setCreator(srcDataScheme.getCreator());
        if (!StringUtils.hasText(dataScheme.getEncryptScene()) && StringUtils.hasText(srcDataScheme.getEncryptScene())) {
            this.dataFieldDao.cancelEncrypted(dataScheme.getKey());
        }
        try {
            this.dataSchemeDao.update(designDataSchemeDO);
        }
        catch (DataIntegrityViolationException e) {
            this.logger.warn("\u66f4\u65b0\u6570\u636e\u65b9\u6848 code \u91cd\u590d", e);
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_1.getMessage(), (Throwable)e);
        }
        return srcDataScheme;
    }

    @Transactional(rollbackFor={Exception.class})
    public void updateDataScheme(DesignDataScheme dataScheme, List<DesignDataDimension> dimensions) throws SchemeDataException {
        Assert.notNull(dimensions, "dimensions must not be null.");
        DesignDataSchemeDO srcDataScheme = this.updateDataScheme0(dataScheme);
        LinkedHashSet<DesignDataDimDO> dimensionsSet = new LinkedHashSet<DesignDataDimDO>(dimensions.size());
        LinkedHashSet<DesignDataDimDO> add = new LinkedHashSet<DesignDataDimDO>(dimensions.size());
        String key = dataScheme.getKey();
        for (DesignDataDimension dimension : dimensions) {
            dimension.setOrder(OrderGenerator.newOrder());
            dimension.setDataSchemeKey(key);
            DesignDataDimDO designDataDimDO = Convert.iDm2Do(dimension);
            dimensionsSet.add(designDataDimDO);
            add.add(designDataDimDO);
        }
        List<DesignDataDimDO> delete = this.dataDimDao.getByDataScheme(key);
        this.dataDimDao.deleteByDataScheme(key);
        this.dataDimDao.batchInsert(new ArrayList(add));
        HashSet<DesignDataDimDO> deletes = new HashSet<DesignDataDimDO>(delete.size());
        for (DesignDataDimDO designDataDimDO : delete) {
            add.remove(designDataDimDO);
            deletes.add(designDataDimDO);
        }
        deletes.removeAll(dimensionsSet);
        this.updatePostProcess(srcDataScheme, dataScheme, new ArrayList<DesignDataDimension>(delete), dimensions);
        this.updatePostDim(add, deletes, dataScheme.getKey());
        this.runListener(srcDataScheme, dataScheme, new ArrayList<DesignDataDimension>(add), new ArrayList<DesignDataDimension>(delete), 1);
    }

    private void updatePostDim(Set<DesignDataDimDO> add, Set<DesignDataDimDO> delete, String dataSchemeKey) {
        if (add.isEmpty() && delete.isEmpty()) {
            return;
        }
        List<DesignDataTable> tables = this.getAllDataTable(dataSchemeKey);
        if (tables.isEmpty()) {
            return;
        }
        DesignDataDimDO unit = null;
        DesignDataDimDO oldUnit = null;
        DesignDataDimDO period = null;
        DesignDataDimDO oldPeriod = null;
        Iterator<DesignDataDimDO> iterator = add.iterator();
        while (iterator.hasNext()) {
            DesignDataDimDO next = iterator.next();
            if (DimensionType.UNIT == next.getDimensionType()) {
                unit = next;
                iterator.remove();
                continue;
            }
            if (DimensionType.PERIOD == next.getDimensionType()) {
                period = next;
                iterator.remove();
                continue;
            }
            if (DimensionType.UNIT_SCOPE != next.getDimensionType()) continue;
            iterator.remove();
        }
        Iterator<DesignDataDimDO> doIterator = delete.iterator();
        while (doIterator.hasNext()) {
            DesignDataDimDO next = doIterator.next();
            if (DimensionType.UNIT == next.getDimensionType()) {
                oldUnit = next;
                doIterator.remove();
                continue;
            }
            if (DimensionType.PERIOD == next.getDimensionType()) {
                oldPeriod = next;
                doIterator.remove();
                continue;
            }
            if (DimensionType.UNIT_SCOPE != next.getDimensionType()) continue;
            doIterator.remove();
        }
        if (unit == null && oldUnit != null || oldUnit == null && unit != null) {
            throw new SchemeDataException("\u66f4\u65b0\u4e3b\u7ef4\u5ea6\u5931\u8d25");
        }
        if (period == null && oldPeriod != null || oldPeriod == null && period != null) {
            throw new SchemeDataException("\u66f4\u65b0\u65f6\u671f\u5931\u8d25");
        }
        this.updateDim(tables, unit, period, add, delete);
    }

    private void updateDim(List<DesignDataTable> tables, DesignDataDimDO unit, DesignDataDimDO period, Set<DesignDataDimDO> add, Set<DesignDataDimDO> delete) {
        ArrayList<DesignDataField> update = new ArrayList<DesignDataField>();
        ArrayList<DesignDataTableDO> updateTable = new ArrayList<DesignDataTableDO>();
        ArrayList<DesignDataField> addFields = new ArrayList<DesignDataField>();
        ArrayList<String> deleteFields = new ArrayList<String>();
        for (DesignDataTable table : tables) {
            DataTableType dataTableType = table.getDataTableType();
            if (dataTableType == null) continue;
            List<DesignDataField> dataFields = this.getDataFieldByTableKeyAndKind(table.getKey(), DataFieldKind.PUBLIC_FIELD_DIM, DataFieldKind.TABLE_FIELD_DIM, DataFieldKind.BUILT_IN_FIELD);
            HashMap<String, DesignDataField> dimField = new HashMap<String, DesignDataField>(dataFields.size());
            HashSet<DesignDataField> dataFieldSet = new HashSet<DesignDataField>(dataFields.size());
            for (DesignDataField dataField : dataFields) {
                if (!table.isRepeatCode() && DataFieldKind.BUILT_IN_FIELD == dataField.getDataFieldKind()) continue;
                dimField.put(dataField.getCode(), dataField);
                dataFieldSet.add(dataField);
            }
            if (unit != null) {
                DesignDataField field = (DesignDataField)dimField.remove("MDCODE");
                String dimKey = unit.getDimKey();
                this.updateUnitField(field, dimKey);
                update.add(field);
            }
            if (period != null) {
                String dimKey = period.getDimKey();
                DesignDataField field = (DesignDataField)dimField.remove("DATATIME");
                this.updatePeriodField(dimKey, field);
                update.add(field);
            }
            if (delete.isEmpty() && add.isEmpty() || DataTableType.MD_INFO == table.getDataTableType()) continue;
            HashMap<String, DesignDataField> dimKeyMap = new HashMap<String, DesignDataField>(dimField.size());
            for (DesignDataField value : dimField.values()) {
                dimKeyMap.put(value.getRefDataEntityKey(), value);
            }
            for (DesignDataDimDO designDataDimDO : delete) {
                DesignDataField field = (DesignDataField)dimKeyMap.get(designDataDimDO.getDimKey());
                if (field == null) {
                    throw new SchemeDataException("\u7ef4\u62a4\u8868 BizKey \u5931\u8d25");
                }
                String key = field.getKey();
                deleteFields.add(key);
                dataFieldSet.remove(field);
            }
            for (DesignDataDimDO designDataDimDO : add) {
                String dimKey = designDataDimDO.getDimKey();
                DesignDataFieldDO fieldDO = this.updateDimField(table, dimKey);
                addFields.add(fieldDO);
                String id = UUIDUtils.getKey();
                fieldDO.setKey(id);
                dataFieldSet.add(Convert.iDf2Dto(fieldDO));
            }
            table.setBizKeys(BizKeyOrder.order(dataFieldSet));
            table.setUpdateTime(null);
            updateTable.add(Convert.iDt2Do(table));
        }
        this.updateDimField(update, updateTable, addFields, deleteFields);
    }

    private DesignDataFieldDO updateDimField(DesignDataTable table, String dimKey) {
        IEntityAttribute bizKeyField;
        IEntityDefine iEntityDefine;
        if ("ADJUST".equals(dimKey)) {
            return Convert.dimFieldBuild(table, "ADJUST", "\u8c03\u6574\u671f", null, dimKey, DataFieldKind.PUBLIC_FIELD_DIM, 40);
        }
        try {
            iEntityDefine = this.entityMetaService.queryEntity(dimKey);
            IEntityModel entityModel = this.entityMetaService.getEntityModel(iEntityDefine.getId());
            bizKeyField = entityModel.getBizKeyField();
        }
        catch (Exception e) {
            throw new SchemeDataException(" \u5b9e\u4f53 " + dimKey + " \u672a\u627e\u5230\u6216\u5df2\u4e22\u5931", (Throwable)e);
        }
        TableModelDefine tableModel = this.entityMetaService.getTableModel(dimKey);
        String bizKeys = tableModel.getBizKeys();
        Integer precision = null;
        if (bizKeyField != null) {
            precision = bizKeyField.getPrecision();
        }
        String code = iEntityDefine.getCode();
        if (null != this.dataFieldDao.getByTableAndCode(table.getKey(), code)) {
            code = code + "_" + OrderGenerator.newOrder();
        }
        String title = iEntityDefine.getTitle();
        return Convert.dimFieldBuild(table, code, title, bizKeys, dimKey, DataFieldKind.PUBLIC_FIELD_DIM, precision);
    }

    private void updatePeriodField(String dimKey, DesignDataField field) {
        field.setRefDataEntityKey(dimKey);
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(dimKey);
        TableModelDefine tableModel = periodAdapter.getPeriodEntityTableModel(dimKey);
        String bizKeys = tableModel.getBizKeys();
        String title = periodEntity.getTitle();
        DesignColumnModelDefine bizKeyField = this.dataModelService.getColumnModelDefine(bizKeys);
        Integer precision = null;
        if (null != bizKeyField) {
            precision = bizKeyField.getPrecision();
        }
        field.setTitle(title);
        field.setRefDataFieldKey(bizKeys);
        if (precision != null) {
            field.setPrecision(precision);
        }
        field.setUpdateTime(Instant.now());
    }

    private void updateUnitField(DesignDataField field, String dimKey) {
        field.setRefDataEntityKey(dimKey);
        try {
            IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(dimKey);
            IEntityModel entityModel = this.entityMetaService.getEntityModel(iEntityDefine.getId());
            IEntityAttribute bizKeyField = entityModel.getBizKeyField();
            TableModelDefine tableModel = this.entityMetaService.getTableModel(dimKey);
            String bizKeys = tableModel.getBizKeys();
            Integer precision = null;
            if (bizKeyField != null) {
                precision = bizKeyField.getPrecision();
            }
            String title = iEntityDefine.getTitle();
            field.setTitle(title);
            field.setRefDataFieldKey(bizKeys);
            if (precision != null) {
                field.setPrecision(precision);
            }
            field.setUpdateTime(Instant.now());
        }
        catch (Exception e) {
            throw new SchemeDataException(" \u5b9e\u4f53 " + dimKey + " \u672a\u627e\u5230\u6216\u5df2\u4e22\u5931", (Throwable)e);
        }
    }

    private void updateDimField(List<DesignDataField> update, List<DesignDataTableDO> updateTable, List<DesignDataField> addFields, List<String> deleteFields) {
        DesignDataFieldDO fieldDO;
        if (!updateTable.isEmpty()) {
            this.dataTableDao.batchUpdate(updateTable);
        }
        if (!update.isEmpty()) {
            ArrayList<DesignDataFieldDO> updateDO = new ArrayList<DesignDataFieldDO>(update.size());
            for (DesignDataField e : update) {
                fieldDO = Convert.iDf2Do(e);
                updateDO.add(fieldDO);
            }
            this.dataFieldDao.batchUpdate(updateDO);
        }
        if (!addFields.isEmpty()) {
            ArrayList<DesignDataFieldDO> addDO = new ArrayList<DesignDataFieldDO>(addFields.size());
            for (DesignDataField e : addFields) {
                fieldDO = Convert.iDf2Do(e);
                addDO.add(fieldDO);
            }
            this.dataFieldDao.batchInsert(addDO);
        }
        if (!deleteFields.isEmpty()) {
            this.dataFieldDao.batchDelete(deleteFields);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void updateSubLevelDataScheme(DesignDataScheme scheme) {
        DesignDataSchemeDO schemeDO = this.dataSchemeDao.get(scheme.getKey());
        schemeDO.setGatherDB(scheme.getGatherDB());
        this.dataSchemeDao.update(schemeDO);
    }

    public DesignDataScheme getDataScheme(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        return Convert.iDs2Dto(this.dataSchemeDao.get(key));
    }

    public DesignDataScheme getDataSchemeByCode(String code) {
        Assert.notNull((Object)code, "code must not be null.");
        return Convert.iDs2Dto(this.dataSchemeDao.getByCode(code));
    }

    public DesignDataScheme getDataSchemeByPrefix(String prefix) {
        Assert.notNull((Object)prefix, "prefix must not be null.");
        return Convert.iDs2Dto(this.dataSchemeDao.getByPrefix(prefix));
    }

    public List<DesignDataScheme> getDataSchemeByParent(String parent) {
        if (parent == null) {
            parent = "00000000-0000-0000-0000-000000000000";
        }
        List<DesignDataSchemeDO> byParent = this.dataSchemeDao.getByParent(parent);
        return byParent.stream().map(Convert::iDs2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<DesignDataScheme> getDataSchemeByPeriodType(PeriodType periodType) {
        Assert.notNull((Object)periodType, "periodType must not be null.");
        List<DesignDataDimDO> dim = this.dataDimDao.getByPeriodType(periodType);
        if (dim.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<String> dataSchemeKeys = new ArrayList<String>();
        for (DesignDataDimDO designDataDimDO : dim) {
            String dataSchemeKey = designDataDimDO.getDataSchemeKey();
            dataSchemeKeys.add(dataSchemeKey);
        }
        List<DesignDataSchemeDO> dataSchemes = this.dataSchemeDao.batchGet(dataSchemeKeys);
        if (dataSchemes == null || dataSchemes.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<DesignDataScheme>(dataSchemes);
    }

    @Transactional(rollbackFor={Exception.class})
    public <E extends DesignDataScheme> void insertDataSchemes(List<E> dataScheme) throws SchemeDataException {
        Assert.notNull(dataScheme, "dataScheme must not be null.");
        for (DesignDataScheme e : dataScheme) {
            this.insertDataScheme(e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void deleteDataSchemes(List<String> keys) {
        Assert.notNull(keys, "keys must not be null.");
        for (String key : keys) {
            this.deleteDataScheme(key);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public <E extends DesignDataScheme> void updateDataSchemes(List<E> dataScheme) throws SchemeDataException {
        Assert.notNull(dataScheme, "dataScheme must not be null.");
        for (DesignDataScheme e : dataScheme) {
            this.updateDataScheme(e);
        }
    }

    public List<DesignDataScheme> getDataSchemes(List<String> keys) {
        Assert.notNull(keys, "keys must not be null.");
        List<DesignDataSchemeDO> designDataScheme = this.dataSchemeDao.batchGet(keys);
        return designDataScheme.stream().map(Convert::iDs2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<DesignDataScheme> getAllDataScheme() {
        List<DesignDataSchemeDO> all = this.dataSchemeDao.getAll();
        return all.stream().map(Convert::iDs2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Transactional(rollbackFor={Exception.class})
    public void copyDataScheme(String copyKey, DesignDataScheme dataScheme, List<DesignDataDimension> dims) throws SchemeDataException {
        Assert.notNull((Object)copyKey, "copyKey must not be null.");
        Assert.notNull((Object)dataScheme, "dataScheme must not be null.");
        Assert.notNull((Object)dataScheme.getCode(), "code must not be null.");
        Assert.notNull((Object)dataScheme.getTitle(), "title must not be null.");
        dataScheme.setOrder(OrderGenerator.newOrder());
        DesignDataScheme copy = this.getDataScheme(copyKey);
        if (copy == null) {
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_5.getMessage());
        }
        SchemeNode root = new SchemeNode(copyKey, NodeType.SCHEME.getValue());
        if (dataScheme.getDataGroupKey() == null) {
            dataScheme.setDataGroupKey(copy.getDataGroupKey());
        }
        String schemeKey = this.insertDataScheme(dataScheme);
        CopyAttributes copyAttributes = new CopyAttributes(schemeKey, copy, dataScheme, dims);
        root.setOther((Object)copyAttributes);
        try {
            this.levelLoader.walkDataSchemeTree(root, this.copyVisitor);
        }
        catch (DataIntegrityViolationException e) {
            this.logger.warn("\u62f7\u8d1d\u65b9\u6848 code \u91cd\u590d", e);
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_5.getMessage(), (Throwable)e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void copyDataScheme(DataSchemeNode target, List<DataSchemeNode> copyData) throws SchemeDataException {
        CopyNodeSchemeVisitor copyVisitor;
        SchemeNode root;
        Assert.notNull((Object)target, "target must not be null.");
        Assert.notEmpty(copyData, "copyData must not be null.");
        String key = target.getKey();
        Assert.notNull((Object)key, "key must not be null.");
        NodeType targetNodeType = null;
        String schemeKey = null;
        if ((target.getType() & NodeType.GROUP.getValue()) != 0) {
            targetNodeType = NodeType.GROUP;
            DesignDataGroupDO dataGroupDO = this.dataGroupDao.get(key);
            if (dataGroupDO == null) {
                throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_5.getMessage());
            }
            schemeKey = dataGroupDO.getDataSchemeKey();
        }
        if ((target.getType() & NodeType.SCHEME.getValue()) != 0) {
            targetNodeType = NodeType.SCHEME;
            schemeKey = key;
        }
        Assert.notNull(schemeKey, "schemeKey must not be null.");
        DesignDataScheme targetScheme = this.getDataScheme(schemeKey);
        if (targetScheme == null) {
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_5.getMessage());
        }
        ArrayList<String> groupKeys = new ArrayList<String>();
        ArrayList<String> tableKeys = new ArrayList<String>();
        for (DataSchemeNode copyDatum : copyData) {
            if (NodeType.GROUP.getValue() == copyDatum.getType()) {
                groupKeys.add(copyDatum.getKey());
            }
            if (((NodeType.TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.MD_INFO.getValue()) & copyDatum.getType()) == 0) continue;
            tableKeys.add(copyDatum.getKey());
        }
        List<DesignDataGroupDO> group = this.dataGroupDao.batchGet(groupKeys);
        List<DesignDataTableDO> table = this.dataTableDao.batchGet(tableKeys);
        HashSet<String> ok = new HashSet<String>(64);
        for (DesignDataGroupDO designDataGroupDO : group) {
            root = new SchemeNode(designDataGroupDO.getKey(), NodeType.GROUP.getValue());
            copyVisitor = new CopyNodeSchemeVisitor(key, targetNodeType, targetScheme, this);
            copyVisitor.setOk(ok);
            this.levelLoader.walkDataSchemeTree(root, (SchemeNodeVisitor)copyVisitor);
        }
        for (DesignDataTableDO dataTableDO : table) {
            root = new SchemeNode(dataTableDO.getKey(), NodeType.MUL_DIM_TABLE.getValue());
            copyVisitor = new CopyNodeSchemeVisitor(key, targetNodeType, targetScheme, this);
            copyVisitor.setOk(ok);
            this.levelLoader.walkDataSchemeTree(root, (SchemeNodeVisitor)copyVisitor);
        }
    }

    public DesignDataGroup initDataGroup() {
        DataGroupDesignDTO designDataGroupDO = new DataGroupDesignDTO();
        designDataGroupDO.setKey(DesignDataSchemeServiceImpl.newId());
        designDataGroupDO.setDataGroupKind(DataGroupKind.TABLE_GROUP);
        return designDataGroupDO;
    }

    @Transactional(rollbackFor={Exception.class})
    public String insertDataGroup(DesignDataGroup dataGroup) throws SchemeDataException {
        if (dataGroup.getKey() == null) {
            dataGroup.setKey(UUIDUtils.getKey());
        }
        this.checkDataGroup(dataGroup);
        DesignDataGroupDO dataGroupDO = Convert.iDg2Do(dataGroup);
        this.iDataSchemeAuthService.grantAllPrivileges(dataGroup);
        return this.dataGroupDao.insert(dataGroupDO);
    }

    private void checkDataGroup(DesignDataGroup dataGroup) throws SchemeDataException {
        Assert.notNull((Object)dataGroup, "dataGroup must not be null.");
        Assert.notNull((Object)dataGroup.getTitle(), "title must not be null.");
        String code = dataGroup.getCode();
        if (dataGroup.getOrder() == null) {
            dataGroup.setOrder(OrderGenerator.newOrder());
        }
        if (!StringUtils.hasLength(code)) {
            dataGroup.setCode(OrderGenerator.newOrder());
        }
        if (dataGroup.getParentKey() != null && dataGroup.getParentKey().equals(dataGroup.getKey())) {
            throw new IllegalArgumentException("key cannot equal parentKey ");
        }
        if (dataGroup.getDataGroupKind() == null) {
            throw new IllegalArgumentException("dataGroupKind must not be null. ");
        }
        if (DataGroupKind.SCHEME_GROUP.equals((Object)dataGroup.getDataGroupKind())) {
            dataGroup.setDataSchemeKey(null);
            if (dataGroup.getParentKey() == null) {
                dataGroup.setParentKey("00000000-0000-0000-0000-000000000000");
            }
        } else if (DataGroupKind.TABLE_GROUP.equals((Object)dataGroup.getDataGroupKind())) {
            Assert.notNull((Object)dataGroup.getDataSchemeKey(), "dataSchemeKey must not be null.");
            if (dataGroup.getDataSchemeKey().equals(dataGroup.getParentKey())) {
                dataGroup.setParentKey(null);
            }
            this.checkDeployStatus(dataGroup.getDataSchemeKey());
        }
        this.validate((Ordered)dataGroup);
        List<DesignDataGroupDO> by = this.dataGroupDao.getBy(dataGroup.getDataSchemeKey(), dataGroup.getTitle(), dataGroup.getParentKey());
        if (!CollectionUtils.isEmpty(by)) {
            for (DesignDataGroupDO dataGroupDO : by) {
                if (dataGroupDO.getKey().equals(dataGroup.getKey())) continue;
                throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DG_1_1.getMessage());
            }
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void deleteDataGroup(String key) throws SchemeDataException {
        Assert.notNull((Object)key, "key must not be null.");
        DesignDataGroupDO dataGroupDO = this.dataGroupDao.get(key);
        if (dataGroupDO == null) {
            return;
        }
        DataGroupKind dataGroupKind = dataGroupDO.getDataGroupKind();
        if (dataGroupKind == DataGroupKind.SCHEME_GROUP || dataGroupKind == DataGroupKind.QUERY_SCHEME_GROUP) {
            String next;
            LinkedList<String> delete = new LinkedList<String>();
            Stack<String> stack = new Stack<String>();
            stack.push(key);
            while (!stack.isEmpty() && (next = (String)stack.pop()) != null) {
                delete.add(next);
                List<DesignDataSchemeDO> scheme = this.dataSchemeDao.getByParent(next);
                if (!scheme.isEmpty()) {
                    throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DG_2_1.getMessage());
                }
                List<DesignDataGroupDO> byParent = this.dataGroupDao.getByParent(next);
                List children = byParent.stream().map(DataGroupDO::getKey).collect(Collectors.toList());
                if (!children.isEmpty()) {
                    stack.addAll(children);
                }
                if (stack.size() <= 1000) continue;
                throw new RuntimeException("\u5206\u7ec4\u7ea7\u6b21\u5b58\u5728\u73af\u5f62\u6570\u636e");
            }
            this.dataGroupDao.batchDelete(delete);
            this.iDataSchemeAuthService.revokeAllForSchemeGroup(delete);
        } else {
            this.checkDeployStatus(dataGroupDO.getDataSchemeKey());
            this.levelLoader.walkDataSchemeTree((DesignDataGroup)dataGroupDO, this.deleteVisitor);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void updateDataGroup(DesignDataGroup dataGroup) throws SchemeDataException {
        Assert.notNull((Object)dataGroup, "dataGroup must not be null.");
        this.checkDataGroup(dataGroup);
        DesignDataGroupDO designDataGroupDO = Convert.iDg2Do(dataGroup);
        this.dataGroupDao.update(designDataGroupDO);
    }

    public DesignDataGroup getDataGroup(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        return Convert.iDg2Dto(this.dataGroupDao.get(key));
    }

    @Transactional(rollbackFor={Exception.class})
    public <E extends DesignDataGroup> String[] insertDataGroups(List<E> dataGroup) throws SchemeDataException {
        Assert.notNull(dataGroup, "dataGroup must not be null.");
        ArrayList<DesignDataGroupDO> list = new ArrayList<DesignDataGroupDO>(dataGroup.size());
        for (DesignDataGroup designDataGroup : dataGroup) {
            Assert.notNull((Object)designDataGroup, "dataGroup must not be null.");
            if (designDataGroup.getKey() == null) {
                designDataGroup.setKey(UUIDUtils.getKey());
            }
            this.checkDataGroup(designDataGroup);
            DesignDataGroupDO designDataGroupDO = Convert.iDg2Do(designDataGroup);
            list.add(designDataGroupDO);
            this.iDataSchemeAuthService.grantAllPrivileges(designDataGroup);
        }
        return this.dataGroupDao.batchInsert(list);
    }

    @Transactional(rollbackFor={Exception.class})
    public void deleteDataGroups(List<String> keys) throws SchemeDataException {
        Assert.notNull(keys, "keys must not be null.");
        for (String key : keys) {
            this.deleteDataGroup(key);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public <E extends DesignDataGroup> void updateDataGroups(List<E> dataGroup) throws SchemeDataException {
        Assert.notNull(dataGroup, "dataGroup must not be null.");
        ArrayList<DesignDataGroupDO> list = new ArrayList<DesignDataGroupDO>();
        for (DesignDataGroup designDataGroup : dataGroup) {
            this.checkDataGroup(designDataGroup);
            list.add(Convert.iDg2Do(designDataGroup));
        }
        this.dataGroupDao.batchUpdate(list);
    }

    public List<DesignDataGroup> getDataGroups(List<String> keys) {
        Assert.notNull(keys, "keys must not be null.");
        List<DesignDataGroupDO> list = this.dataGroupDao.batchGet(keys);
        return list.stream().map(Convert::iDg2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<DesignDataGroup> getAllDataGroup(String scheme) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        List<DesignDataGroupDO> root = this.dataGroupDao.getByScheme(scheme);
        return root.stream().map(Convert::iDg2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<DesignDataGroup> getDataGroupByKind(int kind) {
        List<DesignDataGroupDO> root = this.dataGroupDao.getByKind(kind);
        return root.stream().map(Convert::iDg2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<DesignDataGroup> getDataGroupByScheme(String scheme) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        List<DesignDataGroupDO> root = this.dataGroupDao.getByCondition(scheme, null);
        return root.stream().map(Convert::iDg2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<DesignDataGroup> getDataGroupByParent(String parentKey) {
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        List<DesignDataGroupDO> byParent = this.dataGroupDao.getByParent(parentKey);
        return byParent.stream().map(Convert::iDg2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<DesignDataGroup> getDataGroupForNrSchemeRoot() {
        List<DesignDataGroupDO> byParent = this.dataGroupDao.getByParent("00000000-0000-0000-0000-000000000000");
        return byParent.stream().map(Convert::iDg2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<DesignDataGroup> getDataGroupForQuerySchemeRoot() {
        List<DesignDataGroupDO> byParent = this.dataGroupDao.getByParent("00000000-0000-0000-0000-111111111111");
        return byParent.stream().map(Convert::iDg2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public DesignDataTable initDataTable() {
        DataTableDesignDTO designDataTable = new DataTableDesignDTO();
        designDataTable.setKey(DesignDataSchemeServiceImpl.newId());
        return designDataTable;
    }

    @Transactional(rollbackFor={Exception.class})
    public String insertDataTable(DesignDataTable dataTable) throws SchemeDataException {
        Assert.notNull((Object)dataTable, "dataTable must not be null.");
        return this.insertDataTable(dataTable, true);
    }

    @Transactional(rollbackFor={Exception.class})
    public String insertDataTable(DesignDataTable dataTable, boolean initialization) throws SchemeDataException {
        Assert.notNull((Object)dataTable, "dataTable must not be null.");
        this.checkDeployStatus(dataTable.getDataSchemeKey());
        if (initialization) {
            String[] newIds = this.insertDataTables(Collections.singletonList(dataTable));
            if (newIds == null || newIds.length == 0) {
                return null;
            }
            return dataTable.getKey();
        }
        try {
            DesignDataTableDO designDataTableDO = Convert.iDt2Do(dataTable);
            this.dataTableValidator.checkTable((DesignDataTable)designDataTableDO);
            return this.dataTableDao.insert(designDataTableDO);
        }
        catch (DataIntegrityViolationException e) {
            this.logger.warn("\u65b0\u589e\u6570\u636e\u8868 code \u91cd\u590d", e);
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DT_1_1.getMessage(), (Throwable)e);
        }
        catch (DataAccessException e) {
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DT_1.getMessage(), (Throwable)e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void deleteDataTable(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        DesignDataTableDO dataTableDO = this.dataTableDao.get(key);
        if (dataTableDO == null) {
            return;
        }
        this.checkDeployStatus(dataTableDO.getDataSchemeKey());
        this.deleteDataTableAtt(dataTableDO);
        this.dataTableDao.delete(key);
        this.sysLogForDeleteTable(dataTableDO);
    }

    private void deleteDataTableAtt(DesignDataTable dataTable) {
        List<DesignDataTableRel> dataTableRels;
        this.i18nDao.deleteByTableKey(dataTable.getKey());
        this.dataFieldDao.deleteByTable(dataTable.getKey());
        if (DataTableType.SUB_TABLE == dataTable.getDataTableType()) {
            this.deleteDataTableRelsBySrcTable(dataTable.getKey());
        } else if (DataTableType.DETAIL == dataTable.getDataTableType() && CollectionUtils.isEmpty(dataTableRels = this.getDataTableRelByDesTable(dataTable.getKey()))) {
            this.deleteDataTables(dataTableRels.stream().map(DataTableRel::getSrcTableKey).collect(Collectors.toList()));
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void updateDataTable(DesignDataTable dataTable) throws SchemeDataException {
        Assert.notNull((Object)dataTable, "dataTable must not be null.");
        Assert.notNull((Object)dataTable.getKey(), "dataTable::key must not be null.");
        this.checkDeployStatus(dataTable.getDataSchemeKey());
        this.updateDataTables(Collections.singletonList(dataTable));
    }

    @Transactional(rollbackFor={Exception.class})
    public void refreshDataTableUpdateTime(String tableKey) {
        this.dataTableDao.refreshUpdateTime(tableKey);
    }

    public DesignDataTable getDataTable(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        return Convert.iDt2Dto(this.dataTableDao.get(key));
    }

    @Deprecated
    public DesignDataTable getFmDataTableBySchemeAndDimKey(String scheme, String dimKey) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        Assert.notNull((Object)dimKey, "dimKey must not be null.");
        List<DesignDataFieldDO> idFields = this.dataFieldDao.getByCondition(scheme, "ID", DataFieldKind.BUILT_IN_FIELD.getValue());
        if (CollectionUtils.isEmpty(idFields)) {
            return null;
        }
        for (DesignDataFieldDO idField : idFields) {
            String refDataEntityKey = idField.getRefDataEntityKey();
            if (!dimKey.equals(refDataEntityKey)) continue;
            String dataTableKey = idField.getDataTableKey();
            return this.getDataTable(dataTableKey);
        }
        return null;
    }

    public DesignDataTable getDataTableByCode(String code) {
        Assert.notNull((Object)code, "code must not be null.");
        return Convert.iDt2Dto(this.dataTableDao.getByCode(code));
    }

    public DesignDataTable getDataTableBy(String schemeKey, String dataGroupKey, String title) {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        Assert.notNull((Object)dataGroupKey, "dataGroupKey must not be null.");
        Assert.notNull((Object)title, "title must not be null.");
        return this.dataTableDao.getBy(schemeKey, title, dataGroupKey).stream().findFirst().orElse(null);
    }

    @Transactional(rollbackFor={Exception.class})
    public <E extends DesignDataTable> String[] insertDataTables(List<E> dataTable) throws SchemeDataException {
        Assert.notNull(dataTable, "dataTable must not be null.");
        HashSet<String> schemeKeySet = new HashSet<String>();
        for (DesignDataTable designDataTable : dataTable) {
            schemeKeySet.add(designDataTable.getDataSchemeKey());
        }
        for (String schemeKey : schemeKeySet) {
            this.checkDeployStatus(schemeKey);
        }
        this.dataTableValidator.checkTable(dataTable);
        try {
            return this.dataTableService.insertDataTables(dataTable);
        }
        catch (DataIntegrityViolationException e) {
            this.logger.warn("\u65b0\u589e\u6570\u636e\u8868 code \u91cd\u590d", e);
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DT_1_1.getMessage(), (Throwable)e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void deleteDataTables(List<String> keys) {
        Assert.notNull(keys, "keys must not be null.");
        List<DesignDataTableDO> list = this.dataTableDao.batchGet(keys);
        Set dataSchemes = list.stream().filter(Objects::nonNull).map(DataTableDO::getDataSchemeKey).filter(Objects::nonNull).collect(Collectors.toSet());
        for (String dataScheme : dataSchemes) {
            this.checkDeployStatus(dataScheme);
        }
        this.dataTableDao.batchDelete(keys);
        for (DesignDataTableDO dataTableDO : list) {
            this.deleteDataTableAtt(dataTableDO);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public <E extends DesignDataTable> void updateDataTables(List<E> dataTable) throws SchemeDataException {
        Assert.notNull(dataTable, "dataTable must not be null.");
        HashSet<String> schemeKeySet = new HashSet<String>();
        for (DesignDataTable designDataTable : dataTable) {
            Assert.notNull((Object)designDataTable.getKey(), "key must not be null.");
            schemeKeySet.add(designDataTable.getDataSchemeKey());
        }
        for (String schemeKey : schemeKeySet) {
            this.checkDeployStatus(schemeKey);
        }
        this.dataTableValidator.checkTable(dataTable);
        try {
            this.dataTableService.updateDataTables(dataTable);
        }
        catch (DataIntegrityViolationException e) {
            this.logger.warn("\u66f4\u65b0\u6570\u636e\u8868 code \u91cd\u590d", e);
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DT_1_1.getMessage(), (Throwable)e);
        }
        this.sysLogForUpdateTable(dataTable);
    }

    @Deprecated
    public List<DesignDataTable> getFmDataTableBySchemeKey(String scheme) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        List<DesignDataFieldDO> idFields = this.dataFieldDao.getByCondition(scheme, "ID", DataFieldKind.BUILT_IN_FIELD.getValue());
        if (CollectionUtils.isEmpty(idFields)) {
            return Collections.emptyList();
        }
        return this.getDataTables(idFields.stream().map(DataField::getDataTableKey).filter(Objects::nonNull).collect(Collectors.toList()));
    }

    public List<DesignDataTable> getDataTables(List<String> keys) {
        Assert.notNull(keys, "keys must not be null.");
        List<DesignDataTableDO> list = this.dataTableDao.batchGet(keys);
        return list.stream().map(Convert::iDt2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<DesignDataTable> getAllDataTable() {
        List<DesignDataTableDO> list = this.dataTableDao.getAll();
        return list.stream().map(Convert::iDt2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<DesignDataTable> getAllDataTable(String scheme) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        List<DesignDataTableDO> list = this.dataTableDao.getByDataScheme(scheme);
        return list.stream().map(Convert::iDt2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<DesignDataTable> getDataTableByGroup(String parentKey) {
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        List<DesignDataTableDO> list = this.dataTableDao.getByGroup(parentKey);
        return list.stream().map(Convert::iDt2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<DesignDataTable> getDataTableByScheme(String schemeKey) {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        List<DesignDataTableDO> list = this.dataTableDao.getByCondition(schemeKey, null);
        return list.stream().map(Convert::iDt2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<DesignDataTable> getAllDataTableBySchemeAndTypes(String schemeKey, DataTableType ... types) {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        if (null == types || 0 == types.length) {
            return this.getAllDataTable(schemeKey);
        }
        List<DesignDataTableDO> list = this.dataTableDao.getByDataSchemeAndTypes(schemeKey, types);
        return list.stream().map(Convert::iDt2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public DesignDataTable getDataTableForMdInfo(String schemeKey) {
        return this.getAllDataTableBySchemeAndTypes(schemeKey, DataTableType.MD_INFO).stream().findAny().orElse(null);
    }

    public List<DesignDataTable> getDataTableByCondition(String schemeKey, String parentKey) {
        List<DesignDataTableDO> list = this.dataTableDao.getByCondition(schemeKey, parentKey);
        return list.stream().map(Convert::iDt2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public DesignDataField initDataField() {
        DataFieldDesignDTO dto = new DataFieldDesignDTO();
        dto.setUseAuthority(false);
        dto.setNullable(true);
        return dto;
    }

    @Transactional(rollbackFor={Exception.class})
    public String insertDataField(DesignDataField dataField) throws SchemeDataException {
        Assert.notNull((Object)dataField, "dataField must not be null.");
        this.checkDeployStatus(dataField.getDataSchemeKey());
        this.fieldValidator.checkField(dataField);
        if (dataField.getOrder() == null) {
            dataField.setOrder(OrderGenerator.newOrder());
        }
        DesignDataFieldDO designDataFieldDO = Convert.iDf2Do(dataField);
        String dataTableKey = dataField.getDataTableKey();
        DesignDataTableDO designDataTableDO = this.dataTableDao.get(dataTableKey);
        if (designDataTableDO == null) {
            throw new SchemeDataException("\u8868\u5df2\u5220\u9664\uff0c" + DataSchemeEnum.DATA_SCHEME_DF_1.getMessage());
        }
        this.maintenance(designDataTableDO, designDataFieldDO);
        this.fieldValidationRule(designDataFieldDO);
        return this.dataFieldDao.insert(designDataFieldDO);
    }

    @Transactional(rollbackFor={Exception.class})
    public void deleteDataField(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        DesignDataFieldDO designDataFieldDO = this.dataFieldDao.get(key);
        if (designDataFieldDO == null) {
            return;
        }
        this.checkDeployStatus(designDataFieldDO.getDataSchemeKey());
        String dataTableKey = designDataFieldDO.getDataTableKey();
        DataFieldKind dataFieldKind = designDataFieldDO.getDataFieldKind();
        if (((DataFieldKind.PUBLIC_FIELD_DIM.getValue() | DataFieldKind.BUILT_IN_FIELD.getValue()) & dataFieldKind.getValue()) != 0) {
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DF_2.getMessage());
        }
        if (dataTableKey == null) {
            this.dataFieldDao.delete(key);
            this.i18nDao.deleteByFieldKey(key);
            return;
        }
        DesignDataTableDO dataTableDO = this.dataTableDao.get(dataTableKey);
        if (dataTableDO == null) {
            this.dataFieldDao.delete(key);
            this.i18nDao.deleteByFieldKey(key);
            return;
        }
        if (dataFieldKind == DataFieldKind.TABLE_FIELD_DIM) {
            this.maintenance(dataTableDO, key);
        }
        dataTableDO.setUpdateTime(null);
        this.dataTableDao.update(dataTableDO);
        this.dataFieldDao.delete(key);
        this.i18nDao.deleteByFieldKey(key);
        this.sysLogForDeleteField(designDataFieldDO);
    }

    @Transactional(rollbackFor={Exception.class})
    public void deleteDataFieldByTableKey(String tableKey, boolean deleteTable) {
        Assert.notNull((Object)tableKey, "key must not be null.");
        DesignDataTableDO dataTableDO = this.dataTableDao.get(tableKey);
        if (dataTableDO == null) {
            return;
        }
        this.checkDeployStatus(dataTableDO.getDataSchemeKey());
        if (deleteTable) {
            this.dataTableDao.delete(tableKey);
            this.dataFieldDao.deleteByTable(tableKey);
            this.sysLogForDeleteTable(dataTableDO);
        } else {
            if (dataTableDO.getDataTableType() == DataTableType.DETAIL || dataTableDO.getDataTableType() == DataTableType.ACCOUNT) {
                this.maintenance(dataTableDO, (String)null);
            }
            dataTableDO.setUpdateTime(null);
            this.dataTableDao.update(dataTableDO);
            this.dataFieldDao.deleteByTableAndKind(tableKey, DataFieldKind.TABLE_FIELD_DIM.getValue() | DataFieldKind.FIELD_ZB.getValue() | DataFieldKind.FIELD.getValue());
            this.sysLogForDeleteFieldByTable(dataTableDO);
        }
        this.i18nDao.deleteByTableKey(tableKey);
    }

    @Transactional(rollbackFor={Exception.class})
    public void updateDataField(DesignDataField dataField) throws SchemeDataException {
        Assert.notNull((Object)dataField, "dataField must not be null.");
        Assert.notNull((Object)dataField.getKey(), "key must not be null.");
        this.checkDeployStatus(dataField.getDataSchemeKey());
        this.fieldValidator.checkField(dataField);
        DesignDataFieldDO designDataFieldDO = Convert.iDf2Do(dataField);
        this.fieldValidationRule(designDataFieldDO);
        this.dataFieldDao.update(designDataFieldDO);
        String dataTableKey = dataField.getDataTableKey();
        DesignDataTableDO dataTableDO = this.dataTableDao.get(dataTableKey);
        if (dataTableDO == null) {
            throw new SchemeDataException("\u8868\u5df2\u5220\u9664\uff0c" + DataSchemeEnum.DATA_SCHEME_DF_1.getMessage());
        }
        this.maintenance(dataTableDO, designDataFieldDO);
        this.sysLogForUpdateField((DataField)dataField);
    }

    public DesignDataField getDataField(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        DesignDataFieldDO designDataFieldDO = this.dataFieldDao.get(key);
        DataFieldDesignDTO dataFieldDesignDTO = Convert.iDf2Dto(designDataFieldDO);
        this.fieldValidationRule(dataFieldDesignDTO);
        return dataFieldDesignDTO;
    }

    protected <FIELD extends DesignDataField> void fieldValidationRule(FIELD fieldDTO) {
        if (fieldDTO == null) {
            return;
        }
        this.fieldValidationRule(Collections.singletonList(fieldDTO));
    }

    private <FIELD extends DesignDataField> void fieldValidationRule(List<FIELD> fields) {
        if (fields == null || fields.isEmpty()) {
            return;
        }
        Map<String, List<DesignDataField>> fieldByTable = fields.stream().collect(Collectors.groupingBy(DataField::getDataTableKey));
        for (Map.Entry<String, List<DesignDataField>> fieldBy : fieldByTable.entrySet()) {
            String tableKey = fieldBy.getKey();
            List<DesignDataField> fieldList = fieldBy.getValue();
            DesignDataTableDO dataTable = this.dataTableDao.get(tableKey);
            if (dataTable == null) continue;
            for (DesignDataField dataFieldDTO : fieldList) {
                List validationRules = dataFieldDTO.getValidationRules();
                if (validationRules == null || validationRules.isEmpty()) continue;
                validationRules.removeIf(r -> r.getCompareType() == CompareType.EQUAL);
                for (ValidationRule validationRule : validationRules) {
                    ValidationRuleDTO dto = (ValidationRuleDTO)validationRule;
                    dto.setFieldCode(dataFieldDTO.getCode());
                    dto.setTableCode(dataTable.getCode());
                    dto.setFieldType(dataFieldDTO.getDataFieldType());
                    dto.setFieldTitle(dataFieldDTO.getTitle());
                }
            }
        }
    }

    public DesignDataField getDataFieldByTableKeyAndCode(String table, String code) {
        Assert.notNull((Object)table, "table must not be null.");
        Assert.notNull((Object)code, "code must not be null.");
        DesignDataFieldDO designDataFieldDO = this.dataFieldDao.getByTableAndCode(table, code);
        DataFieldDesignDTO dataFieldDesignDTO = Convert.iDf2Dto(designDataFieldDO);
        this.fieldValidationRule(dataFieldDesignDTO);
        return dataFieldDesignDTO;
    }

    public DesignDataField getZbKindDataFieldBySchemeKeyAndCode(String scheme, String code) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        Assert.notNull((Object)code, "code must not be null.");
        DesignDataFieldDO designDataFieldDO = this.dataFieldDao.getByCondition(scheme, code, DataFieldKind.FIELD_ZB.getValue()).stream().findFirst().orElse(null);
        DataFieldDesignDTO dataFieldDesignDTO = Convert.iDf2Dto(designDataFieldDO);
        this.fieldValidationRule(dataFieldDesignDTO);
        return dataFieldDesignDTO;
    }

    private List<DesignDataField> buildResult(List<DesignDataFieldDO> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        List<DesignDataField> dtoList = list.stream().map(Convert::iDf2Dto).filter(Objects::nonNull).collect(Collectors.toList());
        this.fieldValidationRule(dtoList);
        return dtoList;
    }

    public List<DesignDataField> getDataFieldByTableCode(String tableCode) {
        Assert.notNull((Object)tableCode, "tableCode must not be null.");
        DesignDataTable table = this.getDataTableByCode(tableCode);
        if (table != null) {
            List<DesignDataFieldDO> list = this.dataFieldDao.getByTable(table.getKey());
            return this.buildResult(list);
        }
        return Collections.emptyList();
    }

    public List<DesignDataField> getBizDataFieldByTableKey(String tableKey) {
        Assert.notNull((Object)tableKey, "tableKey must not be null.");
        DesignDataTable designDataTable = this.getDataTable(tableKey);
        if (designDataTable == null) {
            return Collections.emptyList();
        }
        String[] bizKeys = designDataTable.getBizKeys();
        if (bizKeys == null || bizKeys.length == 0) {
            return Collections.emptyList();
        }
        return this.getDataFields(Arrays.asList(bizKeys));
    }

    public List<DesignDataField> getBizDataFieldByTableCode(String tableCode) {
        Assert.notNull((Object)tableCode, "tableCode must not be null.");
        DesignDataTable designDataTable = this.getDataTableByCode(tableCode);
        if (designDataTable != null) {
            String[] bizKeys = designDataTable.getBizKeys();
            if (bizKeys == null || bizKeys.length == 0) {
                return Collections.emptyList();
            }
            return this.getDataFields(Arrays.asList(bizKeys));
        }
        return Collections.emptyList();
    }

    public List<DesignDataField> getDataFieldByTableKeyAndType(String tableKey, DataFieldType ... dataFieldType) {
        Assert.notNull((Object)tableKey, "tableKey must not be null.");
        if (dataFieldType == null) {
            return this.getDataFieldByTable(tableKey);
        }
        List<DesignDataFieldDO> list = this.dataFieldDao.getByTableAndType(tableKey, dataFieldType);
        return this.buildResult(list);
    }

    public List<DesignDataField> getDataFieldByTableKeyAndKind(String tableKey, DataFieldKind ... dataFieldKinds) {
        Assert.notNull((Object)tableKey, "tableKey must not be null.");
        if (dataFieldKinds == null) {
            return this.getDataFieldByTable(tableKey);
        }
        int selectionKey = 0;
        for (DataFieldKind dataFieldKind : dataFieldKinds) {
            selectionKey |= dataFieldKind.getValue();
        }
        List<DesignDataFieldDO> list = this.dataFieldDao.getByTableAndKind(tableKey, selectionKey);
        return this.buildResult(list);
    }

    public List<DesignDataField> getDataFieldByTableCodeAndType(String tableCode, DataFieldType ... dataFieldType) {
        Assert.notNull((Object)tableCode, "tableCode must not be null.");
        DesignDataTable table = this.getDataTableByCode(tableCode);
        if (table != null) {
            if (dataFieldType == null) {
                List<DesignDataFieldDO> list = this.dataFieldDao.getByTable(table.getKey());
                return this.buildResult(list);
            }
            List<DesignDataFieldDO> list = this.dataFieldDao.getByTableAndType(table.getKey(), dataFieldType);
            return this.buildResult(list);
        }
        return Collections.emptyList();
    }

    public List<DesignDataField> getDataFieldByTableCodeAndKind(String tableCode, DataFieldKind ... dataFieldKinds) {
        Assert.notNull((Object)tableCode, "tableCode must not be null.");
        DesignDataTable table = this.getDataTableByCode(tableCode);
        if (table != null) {
            if (dataFieldKinds == null) {
                List<DesignDataFieldDO> list = this.dataFieldDao.getByTable(table.getKey());
                return this.buildResult(list);
            }
            int selectionKey = 0;
            for (DataFieldKind dataFieldKind : dataFieldKinds) {
                selectionKey |= dataFieldKind.getValue();
            }
            List<DesignDataFieldDO> list = this.dataFieldDao.getByTableAndKind(table.getKey(), selectionKey);
            return this.buildResult(list);
        }
        return Collections.emptyList();
    }

    @Transactional(rollbackFor={Exception.class})
    public <E extends DesignDataField> void insertDataFields(List<E> dataField) throws SchemeDataException {
        this.insertDataFields(dataField, true);
    }

    @Transactional(rollbackFor={Exception.class})
    public <E extends DesignDataField> void insertDataFields(List<E> dataField, boolean maintenance) throws SchemeDataException {
        Map<String, List<DesignDataField>> table2Fields = this.pretreatmentDataFields(dataField);
        if (table2Fields == null) {
            return;
        }
        for (Map.Entry<String, List<DesignDataField>> table2List : table2Fields.entrySet()) {
            String dataTableKey = table2List.getKey();
            List<DesignDataField> value = table2List.getValue();
            DesignDataTableDO designDataTableDO = this.checkDesignDataTable(dataTableKey);
            this.fieldValidator.checkField(value);
            List<DesignDataFieldDO> list = this.maintenance(designDataTableDO, value, maintenance);
            try {
                this.dataFieldDao.batchInsert(list);
            }
            catch (DataAccessException e) {
                throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DF_1.getMessage(), (Throwable)e);
            }
        }
    }

    protected DesignDataTableDO checkDesignDataTable(String dataTableKey) {
        DesignDataTableDO designDataTableDO = this.dataTableDao.get(dataTableKey);
        if (designDataTableDO == null) {
            throw new SchemeDataException("\u8868\u5df2\u5220\u9664\uff0c" + DataSchemeEnum.DATA_SCHEME_DF_1.getMessage());
        }
        return designDataTableDO;
    }

    protected <E extends DesignDataField> Map<String, List<DesignDataField>> pretreatmentDataFields(List<E> dataField) {
        Assert.notNull(dataField, "dataField must not be null.");
        if (dataField.isEmpty()) {
            return null;
        }
        HashMap<String, List<DesignDataField>> table2Fields = new HashMap<String, List<DesignDataField>>(3);
        HashSet<String> dataSchemeSet = new HashSet<String>();
        for (DesignDataField e : dataField) {
            dataSchemeSet.add(e.getDataSchemeKey());
            String tableKey = e.getDataTableKey();
            List list = table2Fields.computeIfAbsent(tableKey, f -> new ArrayList());
            list.add(e);
        }
        for (String dataSchemeKey : dataSchemeSet) {
            this.checkDeployStatus(dataSchemeKey);
        }
        this.fieldValidationRule(dataField);
        return table2Fields;
    }

    @Transactional(rollbackFor={Exception.class})
    public void deleteDataFields(List<String> keys) {
        List<DesignDataFieldDO> list;
        Assert.notNull(keys, "keys must not be null.");
        int number = 1000;
        int limit = (keys.size() + number - 1) / number;
        ArrayList doList = new ArrayList();
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
            List<String> subIds = keys.stream().skip(i * number).limit(number).collect(Collectors.toList());
            List<DesignDataFieldDO> fields = this.dataFieldDao.batchGet(subIds);
            doList.addAll(fields);
        });
        HashMap deleteBizKeys = new HashMap(3);
        HashSet<String> tableKeys = new HashSet<String>();
        for (DesignDataFieldDO designDataFieldDO : doList) {
            tableKeys.add(designDataFieldDO.getDataTableKey());
            this.checkDeployStatus(designDataFieldDO.getDataSchemeKey());
            DataFieldKind dataFieldKind = designDataFieldDO.getDataFieldKind();
            if (((DataFieldKind.PUBLIC_FIELD_DIM.getValue() | DataFieldKind.BUILT_IN_FIELD.getValue()) & dataFieldKind.getValue()) != 0) {
                throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DF_2.getMessage());
            }
            if (dataFieldKind != DataFieldKind.TABLE_FIELD_DIM) continue;
            list = (List)deleteBizKeys.get(designDataFieldDO.getDataTableKey());
            if (list == null) {
                list = new ArrayList<DesignDataFieldDO>();
            }
            deleteBizKeys.put(designDataFieldDO.getDataTableKey(), list);
            list.add(designDataFieldDO);
        }
        for (Map.Entry entry : deleteBizKeys.entrySet()) {
            String tableKey = (String)entry.getKey();
            list = (ArrayList<DesignDataFieldDO>)entry.getValue();
            DesignDataTableDO dataTableDO = this.dataTableDao.get(tableKey);
            if (dataTableDO == null) continue;
            for (DesignDataFieldDO designDataFieldDO : list) {
                String key = designDataFieldDO.getKey();
                this.maintenance(dataTableDO, key);
            }
            this.dataTableDao.update(dataTableDO);
            tableKeys.remove(tableKey);
        }
        this.dataFieldDao.batchDelete(keys);
        this.i18nDao.deleteByFieldKey(keys);
        if (!tableKeys.isEmpty()) {
            List<DesignDataTableDO> tableList = this.dataTableDao.batchGet(new ArrayList<String>(tableKeys));
            for (DesignDataTableDO designDataTableDO : tableList) {
                designDataTableDO.setUpdateTime(null);
            }
            this.dataTableDao.batchUpdate(tableList);
        }
        this.sysLogForDeleteField(doList);
    }

    protected void maintenance(DesignDataTableDO designDataTableDO, DesignDataFieldDO dataField) {
        designDataTableDO.setUpdateTime(null);
        if (designDataTableDO.getDataTableType() == DataTableType.DETAIL || designDataTableDO.getDataTableType() == DataTableType.ACCOUNT) {
            String[] bizKeys = designDataTableDO.getBizKeys();
            HashSet<DesignDataField> bizKeyFields = new HashSet<DesignDataField>(this.dataFieldDao.batchGet(Arrays.stream(bizKeys).collect(Collectors.toList())));
            this.ifBizKey(bizKeyFields, dataField);
            designDataTableDO.setBizKeys(BizKeyOrder.order(bizKeyFields));
        }
        this.dataTableDao.update(designDataTableDO);
    }

    private void maintenance(DesignDataTableDO dataTableDO, String key) {
        String[] bizKeys;
        if (dataTableDO == null) {
            return;
        }
        if ((dataTableDO.getDataTableType() == DataTableType.DETAIL || dataTableDO.getDataTableType() == DataTableType.ACCOUNT) && (bizKeys = dataTableDO.getBizKeys()) != null) {
            if (key == null) {
                LinkedHashSet<String> bizKeySet = new LinkedHashSet<String>(Arrays.asList(bizKeys));
                List<DesignDataFieldDO> byTableAndKind = this.dataFieldDao.getByTableAndKind(dataTableDO.getKey(), DataFieldKind.TABLE_FIELD_DIM.getValue());
                for (DesignDataFieldDO fieldDO : byTableAndKind) {
                    bizKeySet.remove(fieldDO.getKey());
                }
                dataTableDO.setBizKeys(bizKeySet.toArray(new String[0]));
                return;
            }
            ArrayList<String> bizList = new ArrayList<String>(bizKeys.length);
            for (String bizKey : bizKeys) {
                if (bizKey.equals(key)) continue;
                bizList.add(bizKey);
            }
            dataTableDO.setBizKeys(bizList.toArray(new String[0]));
        }
    }

    protected List<DesignDataFieldDO> maintenance(DesignDataTableDO designDataTableDO, List<DesignDataField> value, boolean maintenance) {
        boolean detail = designDataTableDO.getDataTableType() == DataTableType.DETAIL || designDataTableDO.getDataTableType() == DataTableType.ACCOUNT;
        String[] bizKeys = designDataTableDO.getBizKeys();
        HashSet<DesignDataField> bizKeyFields = null;
        if (detail && maintenance) {
            bizKeyFields = new HashSet<DesignDataField>(this.dataFieldDao.batchGet(Arrays.stream(bizKeys).collect(Collectors.toList())));
        }
        ArrayList<DesignDataFieldDO> list = new ArrayList<DesignDataFieldDO>();
        for (DesignDataField field : value) {
            DesignDataFieldDO fieldDO = Convert.iDf2Do(field);
            list.add(fieldDO);
            if (!detail || !maintenance) continue;
            this.ifBizKey(bizKeyFields, fieldDO);
            designDataTableDO.setBizKeys(BizKeyOrder.order(bizKeyFields));
        }
        if (maintenance) {
            designDataTableDO.setUpdateTime(null);
            this.dataTableDao.update(designDataTableDO);
        }
        return list;
    }

    private void maintenanceGatherFieldKeys(DesignDataTableDO designDataTableDO, List<String> fieldKeys) {
        String[] unclassifiedKeysSet = fieldKeys.toArray(new String[0]);
        designDataTableDO.setGatherFieldKeys(unclassifiedKeysSet);
        designDataTableDO.setUpdateTime(null);
        this.dataTableDao.update(designDataTableDO);
    }

    private void ifBizKey(Set<DesignDataField> bizKeyFields, DesignDataFieldDO fieldDO) {
        DataFieldKind kind = fieldDO.getDataFieldKind();
        if (DataFieldKind.TABLE_FIELD_DIM == kind) {
            bizKeyFields.add(fieldDO);
            fieldDO.setNullable(false);
        } else if (DataFieldKind.FIELD == kind) {
            bizKeyFields.remove(fieldDO);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void deleteDataFieldByTableKeys(List<String> tableKeys, boolean deleteTable) {
        Assert.notNull(tableKeys, "tableKeys must not be null.");
        List<DesignDataTableDO> list = this.dataTableDao.batchGet(tableKeys);
        for (DesignDataTableDO dataTableDO : list) {
            this.checkDeployStatus(dataTableDO.getDataSchemeKey());
            this.i18nDao.deleteByTableKey(dataTableDO.getKey());
        }
        if (deleteTable) {
            this.dataTableDao.batchDelete(tableKeys);
            this.dataFieldDao.batchDeleteByTable(tableKeys);
            this.sysLogForDeleteTable(list);
        } else {
            for (DesignDataTableDO dataTableDO : list) {
                dataTableDO.setUpdateTime(null);
                this.maintenance(dataTableDO, (String)null);
            }
            this.dataTableDao.batchUpdate(list);
            int dataFieldKinds = DataFieldKind.TABLE_FIELD_DIM.getValue() | DataFieldKind.FIELD_ZB.getValue() | DataFieldKind.FIELD.getValue();
            for (String tableKey : tableKeys) {
                this.dataFieldDao.deleteByTableAndKind(tableKey, dataFieldKinds);
            }
            this.sysLogForDeleteFieldByTable(list);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public <E extends DesignDataField> void updateDataFields(List<E> dataField) throws SchemeDataException {
        Map<String, List<DesignDataField>> table2Fields = this.pretreatmentDataFields(dataField);
        if (table2Fields == null) {
            return;
        }
        for (Map.Entry<String, List<DesignDataField>> table2List : table2Fields.entrySet()) {
            String dataTableKey = table2List.getKey();
            List<DesignDataField> value = table2List.getValue();
            DesignDataTableDO designDataTableDO = this.checkDesignDataTable(dataTableKey);
            this.fieldValidator.checkField(value);
            List<DesignDataFieldDO> list = this.maintenance(designDataTableDO, value, true);
            this.dataFieldDao.batchUpdate(list);
        }
        this.sysLogForUpdateField(dataField);
    }

    @Transactional(rollbackFor={Exception.class})
    public <E extends DesignDataField> void updateGatherFieldKeys(String tableKey, List<String> fieldKeys) throws SchemeDataException {
        DesignDataTableDO designDataTableDO = this.checkDesignDataTable(tableKey);
        this.maintenanceGatherFieldKeys(designDataTableDO, fieldKeys);
        this.sysLogForUpdateTable(designDataTableDO);
    }

    @Transactional(rollbackFor={Exception.class})
    public void updateGatherFieldKeys(String tableKey, List<String> keys, DesignDataTable designDataTable) {
        DesignDataTableDO designDataTableDO = this.checkDesignDataTable(tableKey);
        if (designDataTable.getSyncError() != null) {
            designDataTableDO.setSyncError(designDataTable.getSyncError());
        } else {
            designDataTableDO.setSyncError(Boolean.FALSE);
        }
        designDataTableDO.setDataTableGatherType(designDataTable.getDataTableGatherType());
        this.maintenanceGatherFieldKeys(designDataTableDO, keys);
        this.sysLogForUpdateTable(designDataTableDO);
    }

    public List<DesignDataField> getDataFields(List<String> keys) {
        Assert.notNull(keys, "keys must not be null.");
        ArrayList<DesignDataFieldDO> list = new ArrayList<DesignDataFieldDO>();
        int number = 1000;
        int limit = (keys.size() + number - 1) / number;
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
            List<String> subIds = keys.stream().skip(i * number).limit(number).collect(Collectors.toList());
            list.addAll(this.dataFieldDao.batchGet(subIds));
        });
        return this.buildResult(list);
    }

    public List<DesignDataField> getAllDataField(String scheme) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        List<DesignDataFieldDO> list = this.dataFieldDao.getByScheme(scheme);
        return this.buildResult(list);
    }

    public List<DesignDataField> getAllDataFieldByKind(String scheme, DataFieldKind ... kind) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        Assert.notNull((Object)scheme, "kind must not be null.");
        if (kind.length == 0) {
            return Collections.emptyList();
        }
        int dataFieldKinds = 0;
        for (DataFieldKind dataFieldKind : kind) {
            dataFieldKinds |= dataFieldKind.getValue();
        }
        List<DesignDataFieldDO> list = this.dataFieldDao.getBySchemeAndKind(scheme, dataFieldKinds);
        return this.buildResult(list);
    }

    public List<DesignDataField> getDataFieldByTable(String tableKey) {
        Assert.notNull((Object)tableKey, "tableKey must not be null.");
        List<DesignDataFieldDO> list = this.dataFieldDao.getByTable(tableKey);
        return this.buildResult(list);
    }

    public List<DesignDataField> getDataFieldByTables(List<String> tableKeys) {
        Assert.notNull(tableKeys, "tableKeys must not be null.");
        List<DesignDataFieldDO> list = this.dataFieldDao.batchGetByTable(tableKeys);
        return this.buildResult(list);
    }

    @Deprecated
    public List<DesignDataField> getFmKindDataFieldsBySchemeAndKeys(String scheme, String bizKey) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        Assert.notNull((Object)bizKey, "bizKey must not be null.");
        List<DesignDataFieldDO> idFields = this.dataFieldDao.getByCondition(scheme, "ID", DataFieldKind.BUILT_IN_FIELD.getValue());
        if (CollectionUtils.isEmpty(idFields)) {
            return Collections.emptyList();
        }
        for (DesignDataFieldDO idField : idFields) {
            String refDataFieldKey = idField.getRefDataFieldKey();
            if (!bizKey.equals(refDataFieldKey)) continue;
            String tableKey = idField.getDataTableKey();
            return this.getDataFieldByTable(tableKey);
        }
        return Collections.emptyList();
    }

    @Deprecated
    public List<DesignDataField> getFmKindDataFieldsBySchemeAndDimKey(String scheme, String dimKey) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        Assert.notNull((Object)dimKey, "dimKey must not be null.");
        List<DesignDataFieldDO> idFields = this.dataFieldDao.getByCondition(scheme, "ID", DataFieldKind.BUILT_IN_FIELD.getValue());
        if (CollectionUtils.isEmpty(idFields)) {
            return Collections.emptyList();
        }
        for (DesignDataFieldDO idField : idFields) {
            String dimensionKey = idField.getRefDataEntityKey();
            if (!dimKey.equals(dimensionKey)) continue;
            String tableKey = idField.getDataTableKey();
            return this.getDataFieldByTable(tableKey);
        }
        return Collections.emptyList();
    }

    public DesignDataDimension initDataSchemeDimension() {
        return new DataDimDesignDTO();
    }

    public List<DesignDataDimension> getDataSchemeDimension(String dataSchemeKey) {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null.");
        List<DesignDataDimDO> dataScheme = this.dataDimDao.getByDataScheme(dataSchemeKey);
        return dataScheme.stream().map(Convert::iDm2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<DesignDataDimension> getDataSchemeDimension(String dataSchemeKey, DimensionType dimensionType) {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null.");
        Assert.notNull((Object)dimensionType, "dimensionType must not be null.");
        List<DesignDataDimension> dimension = this.getDataSchemeDimension(dataSchemeKey);
        return dimension.stream().filter(r -> r.getDimensionType() == dimensionType).collect(Collectors.toList());
    }

    public List<DesignDataDimension> getReportDimension(String dataSchemeKey) {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null.");
        List<DesignDataDimDO> dataScheme = this.dataDimDao.getByDataScheme(dataSchemeKey);
        return dataScheme.stream().filter(DataDimension::getReportDim).collect(Collectors.toList());
    }

    public Boolean enableAdjustPeriod(String dataSchemeKey) {
        List<DesignDataDimDO> dimensions = this.dataDimDao.getByDataScheme(dataSchemeKey);
        if (dimensions == null) {
            return Boolean.FALSE;
        }
        return dimensions.stream().anyMatch(x -> "ADJUST".equals(x.getDimKey()));
    }

    public void checkDeployStatus(String dataSchemeKey) throws SchemeDataException {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null.");
        DeployStatusEnum deployStatus = this.iRuntimeDataSchemeService.getDataSchemeDeployStatus(dataSchemeKey);
        if (DeployStatusEnum.PARAM_LOCKING.equals((Object)deployStatus)) {
            throw new SchemeDataException("\u53c2\u6570\u9501\u5b9a\uff0c\u7981\u6b62\u4fee\u6539\uff01");
        }
        if (DeployStatusEnum.DEPLOY.equals((Object)deployStatus)) {
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_5.getMessage());
        }
    }

    public DesignDataTableRel initDataTableRel() {
        DataTableRelDesignDTO r = new DataTableRelDesignDTO();
        r.setKey(DesignDataSchemeServiceImpl.newId());
        return r;
    }

    @Transactional(rollbackFor={Exception.class})
    public String insertDataTableRel(DesignDataTableRel dataTableRel) {
        this.insertDataTableRels(Collections.singletonList(dataTableRel));
        return dataTableRel.getKey();
    }

    @Transactional(rollbackFor={Exception.class})
    public List<String> insertDataTableRels(List<DesignDataTableRel> dataTableRels) {
        ArrayList<DataTableRelDesignDTO> dots = new ArrayList<DataTableRelDesignDTO>();
        for (DesignDataTableRel dataTableRel : dataTableRels) {
            if (!StringUtils.hasText(dataTableRel.getKey())) {
                dataTableRel.setKey(DesignDataSchemeServiceImpl.newId());
            }
            dots.add(Convert.iDtr2Dto(dataTableRel));
        }
        return this.dataTableRelDesignService.insertDataTableRels(dots);
    }

    @Transactional(rollbackFor={Exception.class})
    public void updateDataTableRel(DesignDataTableRel dataTableRel) {
        this.updateDataTableRels(Collections.singletonList(dataTableRel));
    }

    @Transactional(rollbackFor={Exception.class})
    public void updateDataTableRels(List<DesignDataTableRel> dataTableRels) {
        ArrayList<DataTableRelDesignDTO> dots = new ArrayList<DataTableRelDesignDTO>();
        for (DesignDataTableRel dataTableRel : dataTableRels) {
            if (!StringUtils.hasText(dataTableRel.getKey())) {
                dataTableRel.setKey(DesignDataSchemeServiceImpl.newId());
            }
            dots.add(Convert.iDtr2Dto(dataTableRel));
        }
        this.dataTableRelDesignService.updateDataTableRels(dots);
    }

    @Transactional(rollbackFor={Exception.class})
    public void deleteDataTableRel(String key) {
        this.dataTableRelDesignService.deleteDataTableRel(key);
    }

    @Transactional(rollbackFor={Exception.class})
    public void deleteDataTableRels(List<String> keys) {
        this.dataTableRelDesignService.deleteDataTableRels(keys);
    }

    @Transactional(rollbackFor={Exception.class})
    public void deleteDataTableRelsBySrcTable(String srcTableKey) {
        this.dataTableRelDesignService.deleteBySrcTable(srcTableKey);
    }

    @Transactional(rollbackFor={Exception.class})
    public void deleteDataTableRelsByDesTable(String desTableKey) {
        this.dataTableRelDesignService.deleteByDesTable(desTableKey);
    }

    public DesignDataTableRel getDataTableRelBySrcTable(String srcTableKey) {
        return this.dataTableRelDesignService.getBySrcTable(srcTableKey);
    }

    public List<DesignDataTableRel> getDataTableRelByDesTable(String desTableKey) {
        return this.dataTableRelDesignService.getByDesTable(desTableKey);
    }

    @Transactional(rollbackFor={Exception.class})
    public void insertDataTable(DesignDataTable dataTable, List<DesignDataField> dataFields, List<DataFieldDeployInfo> infos) {
        this.insertDataTable(dataTable, false);
        if (!CollectionUtils.isEmpty(dataFields) && !CollectionUtils.isEmpty(infos)) {
            this.insertDataFields(dataFields, false);
            DataFieldDeployInfoDO[] array = (DataFieldDeployInfoDO[])infos.stream().map(DataFieldDeployInfoDO::valueOf).toArray(DataFieldDeployInfoDO[]::new);
            this.dataFieldDeployInfoDao.insert(array);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void insertDataFields(List<? extends DesignDataField> dataFields, List<? extends DataFieldDeployInfo> infos) {
        if (!CollectionUtils.isEmpty(dataFields) && !CollectionUtils.isEmpty(infos)) {
            this.insertDataFields(dataFields);
            DataFieldDeployInfoDO[] array = (DataFieldDeployInfoDO[])infos.stream().map(DataFieldDeployInfoDO::valueOf).toArray(DataFieldDeployInfoDO[]::new);
            this.dataFieldDeployInfoDao.insert(array);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void updateDataFields(List<? extends DesignDataField> dataFields, List<DataFieldDeployInfo> infos) {
        this.updateDataFields(dataFields);
        if (!CollectionUtils.isEmpty(infos)) {
            DataFieldDeployInfoDO[] array = (DataFieldDeployInfoDO[])infos.stream().map(DataFieldDeployInfoDO::valueOf).toArray(DataFieldDeployInfoDO[]::new);
            this.dataFieldDeployInfoDao.update(array);
        }
    }

    public DataFieldDeployInfo initDataFieldDeployInfo() {
        return new DataFieldDeployInfoDTO();
    }

    public void setReportDimensionData(DesignDataDimension dim, String dimKey, String orgDimKey) {
        IEntityRefer refer;
        List entityRefer = this.entityMetaService.getEntityRefer(orgDimKey);
        if (!CollectionUtils.isEmpty(entityRefer) && Objects.nonNull(refer = (IEntityRefer)entityRefer.stream().filter(x -> dimKey.equals(x.getReferEntityId())).findFirst().orElse(null))) {
            dim.setDimAttribute(refer.getOwnField());
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void addPublicDimToDataScheme(String dataSchemeKey, Set<DesignDataDimension> dimensions, Map<String, String> defaultFieldValues) {
        List<DesignDataTable> tables;
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null.");
        this.preDeploy(dataSchemeKey);
        if (CollectionUtils.isEmpty(dimensions)) {
            return;
        }
        if (defaultFieldValues == null) {
            defaultFieldValues = Collections.emptyMap();
        }
        if (CollectionUtils.isEmpty(tables = this.getAllDataTable(dataSchemeKey))) {
            return;
        }
        ArrayList<DesignDataFieldDO> addFields = new ArrayList<DesignDataFieldDO>();
        Set<DesignDataDimDO> dataDimDOS = dimensions.stream().map(DesignDataDimDO::valueOf).collect(Collectors.toSet());
        this.updateSchemeParam(dataSchemeKey, defaultFieldValues, dataDimDOS, tables, addFields);
        DeployResult deployResult = this.dataSchemeDeployService.unsafeDeployDataScheme(dataSchemeKey, p -> this.updateProgressItem(dataSchemeKey, (ProgressItem)p), null);
        if (!deployResult.isSuccess()) {
            if (!deployResult.getCheckState()) {
                this.rollBack(dataSchemeKey, addFields, tables);
            }
            throw new SchemeDataException(deployResult.getDeployErrorMessage());
        }
    }

    private void updateSchemeParam(String dataSchemeKey, Map<String, String> defaultFieldValues, Set<DesignDataDimDO> dataDimDOSet, List<DesignDataTable> tables, List<DesignDataFieldDO> addFields) {
        List<DesignDataDimDO> dataDimDOS = this.dataDimDao.getByDataScheme(dataSchemeKey);
        Optional<DesignDataDimDO> first = dataDimDOS.stream().filter(dataDimDOSet::contains).findFirst();
        if (first.isPresent()) {
            throw new SchemeDataException(String.format("\u6dfb\u52a0\u7ef4\u5ea6\u5931\u8d25\uff0c\u7ef4\u5ea6\u91cd\u590d\uff1a%s", first.get()));
        }
        ArrayList<DesignDataTableDO> updateTables = new ArrayList<DesignDataTableDO>();
        for (DesignDataTable table : tables) {
            DataTableType dataTableType = table.getDataTableType();
            if (null == dataTableType || DataTableType.MD_INFO == dataTableType) continue;
            List<DesignDataField> dataFields = this.getDataFieldByTableKeyAndKind(table.getKey(), DataFieldKind.PUBLIC_FIELD_DIM, DataFieldKind.TABLE_FIELD_DIM, DataFieldKind.BUILT_IN_FIELD);
            HashSet<DesignDataField> dataFieldSet = new HashSet<DesignDataField>(dataFields.size());
            dataFieldSet.addAll(dataFields);
            for (DesignDataDimDO designDataDimDO : dataDimDOSet) {
                String dimKey = designDataDimDO.getDimKey();
                DesignDataFieldDO fieldDO = this.updateDimField(table, dimKey);
                this.dataFieldDefaultValueCache.setDefaultValue(dataSchemeKey, fieldDO.getKey(), defaultFieldValues.getOrDefault(dimKey, null));
                addFields.add(fieldDO);
                dataFieldSet.add(Convert.iDf2Dto(fieldDO));
            }
            table.setBizKeys(BizKeyOrder.order(this.filterDataFields(table, dataFieldSet)));
            table.setUpdateTime(Instant.now());
            updateTables.add(Convert.iDt2Do(table));
        }
        this.deployDataSchemeProxy.update(dataDimDOSet, addFields, updateTables);
    }

    private void preDeploy(String dataSchemeKey) {
        DeployResult preDeploy = this.dataSchemeDeployService.deployDataScheme(dataSchemeKey, p -> this.updateProgressItem(dataSchemeKey, (ProgressItem)p), null);
        if (!preDeploy.isSuccess()) {
            throw new SchemeDataException(preDeploy.getDeployMessage());
        }
    }

    private void rollBack(String dataSchemeKey, List<DesignDataFieldDO> addFields, List<DesignDataTable> tables) {
        this.dataDimDao.delete(dataSchemeKey, AdjustUtils.getAdjustDimensionName());
        List<String> collect = addFields.stream().map(DataFieldDO::getKey).collect(Collectors.toList());
        this.dataFieldDao.batchDelete(collect);
        this.dataTableDao.batchUpdate(tables.stream().map(Convert::iDt2Do).collect(Collectors.toList()));
    }

    private void updateProgressItem(String dataSchemeKey, ProgressItem progressItem) {
        if (progressItem == null) {
            return;
        }
        this.progressCacheService.setProgress(dataSchemeKey, progressItem);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Transactional(rollbackFor={Exception.class})
    public void addTableDimToTable(String tableKey, Set<DesignDataField> dataFields, Map<String, String> defaultFieldValues) throws SQLException {
        if (tableKey == null || CollectionUtils.isEmpty(dataFields)) {
            return;
        }
        if (defaultFieldValues == null) {
            defaultFieldValues = Collections.emptyMap();
        }
        DesignDataTable dataTable = this.getDataTable(tableKey);
        this.checkDataIntegrity(dataTable, dataFields);
        DesignDataTableDO newTable = DesignDataTableDO.valueOf((DataTable)dataTable);
        List<DesignDataFieldDO> newFields = dataFields.stream().map(DesignDataFieldDO::valueOf).collect(Collectors.toList());
        List<DesignDataField> dataFieldList = this.getDataFieldByTableKeyAndKind(tableKey, DataFieldKind.PUBLIC_FIELD_DIM, DataFieldKind.TABLE_FIELD_DIM, DataFieldKind.BUILT_IN_FIELD);
        dataFieldList.addAll(newFields);
        newFields.forEach(x -> {
            x.setDataFieldKind(DataFieldKind.TABLE_FIELD_DIM);
            x.setNullable(false);
            x.setUpdateTime(Instant.now());
        });
        String[] order = BizKeyOrder.order(this.filterDataFields(dataTable, dataFieldList));
        newTable.setBizKeys(order);
        newTable.setUpdateTime(Instant.now());
        this.deployDataSchemeProxy.update(Collections.emptySet(), Collections.emptyList(), newFields, Collections.singletonList(newTable));
        for (DesignDataField dataField : dataFields) {
            this.dataFieldDefaultValueCache.setDefaultValue(dataTable.getDataSchemeKey(), dataField.getKey(), defaultFieldValues.getOrDefault(dataField.getCode(), null));
        }
        String message = null;
        try {
            this.dataSchemeDeployService.deployDataTable(dataTable.getKey(), false);
            this.sysLogForUpdateField(dataFields);
        }
        catch (Exception e) {
            this.deployDataSchemeProxy.update(Collections.emptySet(), Collections.emptyList(), dataFields.stream().map(DesignDataFieldDO::valueOf).collect(Collectors.toList()), Collections.singletonList(DesignDataTableDO.valueOf((DataTable)dataTable)));
            message = e.getMessage();
        }
        finally {
            this.dataFieldDefaultValueCache.clear(dataTable.getDataSchemeKey());
        }
        if (message != null) {
            throw new SchemeDataException(message);
        }
    }

    private void checkDataIntegrity(DesignDataTable dataTable, Set<DesignDataField> dataFields) throws SQLException {
        MemoryDataSet rows;
        if (dataTable == null) {
            throw new SchemeDataException("\u6570\u636e\u8868\u4e0d\u5b58\u5728");
        }
        TableModelDefine table = this.runtimeDataModelService.getTableModelDefineByCode(dataTable.getCode());
        List columnModels = this.runtimeDataModelService.getColumnModelDefinesByTable(table.getID());
        Set fieldCodes = dataFields.stream().map(Basic::getCode).collect(Collectors.toSet());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        queryModel.setMainTableName(table.getName());
        List columns = queryModel.getColumns();
        columnModels.forEach(c -> {
            if (fieldCodes.contains(c.getCode())) {
                columns.add(new NvwaQueryColumn(c));
            }
        });
        if (columns.isEmpty()) {
            return;
        }
        INvwaDataAccess readOnlyDataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.runtimeDataModelService);
        try {
            rows = readOnlyDataAccess.executeQuery(context);
        }
        catch (Exception e) {
            throw new SchemeDataException((Throwable)e);
        }
        if (rows != null) {
            for (DataRow row : rows) {
                for (int i = 0; i < columns.size(); ++i) {
                    if (row.getValue(i) != null) continue;
                    throw new SQLIntegrityConstraintViolationException("\u6dfb\u52a0\u8868\u5185\u7ef4\u5ea6\u5b57\u6bb5\u6709\u7a7a\u503c\uff0c\u6dfb\u52a0\u5931\u8d25");
                }
            }
        }
    }

    private Collection<DesignDataField> filterDataFields(DesignDataTable dataTable, Collection<DesignDataField> dataFieldList) {
        if (!dataTable.isRepeatCode()) {
            return dataFieldList.stream().filter(x -> !"BIZKEYORDER".equals(x.getCode())).collect(Collectors.toList());
        }
        return dataFieldList;
    }

    public List<DesignDataField> searchField(FieldSearchQuery fieldSearchQuery) {
        if (fieldSearchQuery == null) {
            return Collections.emptyList();
        }
        List<DesignDataFieldDO> doList = this.dataFieldDao.filterField(fieldSearchQuery);
        return new ArrayList<DesignDataField>(doList);
    }

    public List<DesignDataTable> searchTable(List<String> schemes, String keyword, int type) {
        if (!StringUtils.hasText(keyword)) {
            return Collections.emptyList();
        }
        List<DesignDataTableDO> doList = this.dataTableDao.searchBy(schemes, keyword, type);
        return new ArrayList<DesignDataTable>(doList);
    }

    @Transactional(rollbackFor={Exception.class})
    public void clearSchemeGroup(String schemeKey) {
        List<DesignDataTable> dataTables = this.getAllDataTable(schemeKey);
        Set tableGroupSet = dataTables.stream().map(DataTable::getDataGroupKey).filter(Objects::nonNull).collect(Collectors.toSet());
        HashSet allKeys = new HashSet();
        HashSet<String> hasKeys = new HashSet<String>();
        HashMap map1 = new HashMap();
        this.getAllDataGroup(schemeKey).stream().filter(x -> x.getDataGroupKind().equals((Object)DataGroupKind.TABLE_GROUP)).forEach(x -> {
            map1.put(x.getKey(), x.getParentKey());
            allKeys.add(x.getKey());
        });
        Iterator iterator = tableGroupSet.iterator();
        while (iterator.hasNext()) {
            String tableGroup;
            String s = tableGroup = (String)iterator.next();
            while (map1.containsKey(s)) {
                hasKeys.add(s);
                s = (String)map1.get(s);
            }
        }
        allKeys.removeAll(hasKeys);
        this.deleteDataGroups(new ArrayList<String>(allKeys));
    }

    @Transactional(rollbackFor={Exception.class})
    public boolean addDataSchemeDimension(DesignDataDimension dimension, String defaultValue) {
        String dataSchemeKey = dimension.getDataSchemeKey();
        if (!StringUtils.hasText(dataSchemeKey) || DimensionType.DIMENSION != dimension.getDimensionType()) {
            throw new SchemeDataException();
        }
        List<DesignDataDimDO> dims = this.dataDimDao.getByDataScheme(dataSchemeKey);
        if (CollectionUtils.isEmpty(dims)) {
            throw new SchemeDataException("\u6570\u636e\u65b9\u6848\u53c2\u6570\u5f02\u5e38");
        }
        for (DesignDataDimDO dim : dims) {
            if (!dim.getDimKey().equals(dimension.getDimKey())) continue;
            throw new SchemeDataException("\u60c5\u666f\u5df2\u7ecf\u5b58\u5728");
        }
        this.preDeploy(dataSchemeKey);
        this.addDimensionFields(dimension, defaultValue, dataSchemeKey);
        DeployResult deployResult = this.dataSchemeDeployService.unsafeDeployDataScheme(dataSchemeKey, progress -> this.updateProgressItem(dataSchemeKey, (ProgressItem)progress), null);
        return deployResult.isSuccess();
    }

    private void addDimensionFields(DesignDataDimension dimension, String defaultValue, String dataSchemeKey) {
        List<DesignDataTableDO> dataTables = this.dataTableDao.getByDataScheme(dataSchemeKey);
        ArrayList<DesignDataFieldDO> insertDataFields = new ArrayList<DesignDataFieldDO>();
        ArrayList<DesignDataFieldDO> updateDataFields = new ArrayList<DesignDataFieldDO>();
        if (!CollectionUtils.isEmpty(dataTables)) {
            Map<String, DesignDataFieldDO> dimensionFields = this.initDimensionFields(dataTables, dimension.getDimKey());
            for (DesignDataTableDO dataTable : dataTables) {
                if (DataTableType.MD_INFO == dataTable.getDataTableType()) continue;
                HashSet<DesignDataField> bizKeyFields = new HashSet<DesignDataField>(this.getDataFieldByTableKeyAndKind(dataTable.getKey(), DataFieldKind.PUBLIC_FIELD_DIM, DataFieldKind.TABLE_FIELD_DIM, DataFieldKind.BUILT_IN_FIELD));
                DesignDataFieldDO dimensionField = dimensionFields.get(dataTable.getKey());
                DesignDataFieldDO dataField = this.dataFieldDao.getByTableAndCode(dataTable.getKey(), dimensionField.getCode());
                if (null == dataField) {
                    insertDataFields.add(dimensionField);
                    bizKeyFields.add(dimensionField);
                    this.dataFieldDefaultValueCache.setDefaultValue(dimensionField.getDataSchemeKey(), dimensionField.getKey(), defaultValue);
                } else {
                    DesignDataFieldDO updateDataField = this.updateDimensionField(dataTable, dimensionField, dataField);
                    updateDataFields.add(updateDataField);
                    bizKeyFields.add(updateDataField);
                    this.dataFieldDefaultValueCache.setDefaultValue(updateDataField.getDataSchemeKey(), updateDataField.getKey(), defaultValue);
                }
                dataTable.setBizKeys(BizKeyOrder.order(this.filterDataFields(dataTable, bizKeyFields)));
                dataTable.setUpdateTime(Instant.now());
            }
        }
        this.deployDataSchemeProxy.update(Collections.singleton(DesignDataDimDO.valueOf(dimension)), insertDataFields, updateDataFields, dataTables);
    }

    private DesignDataFieldDO updateDimensionField(DesignDataTableDO dataTable, DesignDataFieldDO dimensionField, DesignDataFieldDO dataField) {
        boolean error = false;
        if (!dimensionField.getCode().equals(dataField.getCode())) {
            error = true;
        }
        if (!dimensionField.getRefDataEntityKey().equals(dataField.getRefDataEntityKey())) {
            error = true;
        }
        if (error) {
            throw new SchemeDataException("\u6570\u636e\u8868" + dataTable.getCode() + "\u7684\u5b57\u6bb5" + dataField.getCode() + "\u4e0e\u7ef4\u5ea6\u5b57\u6bb5\u51b2\u7a81");
        }
        if (dimensionField.getPrecision() < dataField.getPrecision()) {
            dimensionField.setPrecision(dataField.getPrecision());
        }
        dimensionField.setKey(dataField.getKey());
        return dimensionField;
    }

    private Map<String, DesignDataFieldDO> initDimensionFields(List<? extends DesignDataTable> dataTables, String dimKey) {
        HashMap<String, DesignDataFieldDO> dimensionFields = new HashMap<String, DesignDataFieldDO>();
        for (DesignDataTable designDataTable : dataTables) {
            DesignDataFieldDO dimensionField;
            if ("ADJUST".equals(dimKey)) {
                dimensionField = Convert.dimFieldBuild(designDataTable, "ADJUST", "\u8c03\u6574\u671f", null, dimKey, DataFieldKind.PUBLIC_FIELD_DIM, 40);
            } else {
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(dimKey);
                IEntityModel entityModel = this.entityMetaService.getEntityModel(dimKey);
                TableModelDefine tableModel = this.entityMetaService.getTableModel(dimKey);
                if (null == entityDefine || null == entityModel || null == tableModel) {
                    throw new SchemeDataException("\u60c5\u666f" + dimKey + "\u5bf9\u5e94\u5b9e\u4f53\u4e0d\u5b58\u5728");
                }
                String dimensionName = entityDefine.getDimensionName();
                String dimensionTitle = entityDefine.getTitle();
                String bizKeys = tableModel.getBizKeys();
                IEntityAttribute bizKeyField = entityModel.getBizKeyField();
                Integer precision = null == bizKeyField ? null : Integer.valueOf(bizKeyField.getPrecision());
                dimensionField = Convert.dimFieldBuild(designDataTable, dimensionName, dimensionTitle, bizKeys, dimKey, DataFieldKind.PUBLIC_FIELD_DIM, precision);
            }
            dimensionField.setUpdateTime(Instant.now());
            dimensionFields.put(designDataTable.getKey(), dimensionField);
        }
        return dimensionFields;
    }

    @Transactional(rollbackFor={Exception.class})
    public String insertDataTableForMdInfo(String dataSchemeKey, String dataTableCode, String dataTableTitle) {
        List<DesignDataTableDO> dataTables = this.dataTableDao.getByDataSchemeAndTypes(dataSchemeKey, DataTableType.MD_INFO);
        if (!CollectionUtils.isEmpty(dataTables)) {
            return dataTables.get(0).getKey();
        }
        DesignDataScheme dataScheme = this.getDataScheme(dataSchemeKey);
        if (!StringUtils.hasText(dataTableCode)) {
            dataTableCode = DesignDataSchemeServiceImpl.getTableCodeForMdInfo(dataScheme);
        } else if (StringUtils.hasText(dataScheme.getPrefix()) && !dataTableCode.startsWith(dataScheme.getPrefix())) {
            dataTableCode = dataScheme.getPrefix() + "_" + dataTableCode;
        }
        if (!StringUtils.hasText(dataTableTitle)) {
            dataTableTitle = "\u5355\u4f4d\u4fe1\u606f";
        }
        DesignDataTable dataTable = this.initDataTable();
        dataTable.setCode(dataTableCode);
        dataTable.setTitle(dataTableTitle);
        dataTable.setDataTableType(DataTableType.MD_INFO);
        dataTable.setDataTableGatherType(DataTableGatherType.CLASSIFY);
        dataTable.setRepeatCode(Boolean.valueOf(false));
        dataTable.setDataSchemeKey(dataSchemeKey);
        dataTable.setDataGroupKey(null);
        dataTable.setOrder(OrderGenerator.newOrder());
        dataTable.setUpdateTime(Instant.now());
        dataTable.setDesc(null);
        String[] keys = this.dataTableService.insertDataTables(Collections.singletonList(Convert.iDt2Do(dataTable)));
        return keys[0];
    }

    public boolean enableAccountTable() {
        return this.enableAccount;
    }

    private static String getTableCodeForMdInfo(DesignDataScheme dataScheme) {
        return (StringUtils.hasText(dataScheme.getPrefix()) ? dataScheme.getPrefix() + "_" : "") + "MD_INFO" + "_" + dataScheme.getBizCode();
    }

    @Transactional(rollbackFor={Exception.class})
    public void addTableDimToTable(DesignDataField dataField, String defaultValue) {
        Assert.notNull((Object)dataField, "\u7ef4\u5ea6\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)dataField.getDataSchemeKey(), "\u6570\u636e\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)dataField.getDataTableKey(), "\u6570\u636e\u8868\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)defaultValue, "\u9ed8\u8ba4\u503c\u4e0d\u80fd\u4e3a\u7a7a");
        DesignDataTable dataTable = this.getDataTable(dataField.getDataTableKey());
        if (dataTable == null) {
            throw new SchemeDataException("\u6570\u636e\u8868\u4e0d\u5b58\u5728");
        }
        try {
            this.logger.debug("\u9884\u53d1\u5e03");
            this.dataSchemeDeployService.deployDataTable(dataField.getDataTableKey(), true);
        }
        catch (JQException e) {
            throw new SchemeDataException(e.getMessage());
        }
        DesignDataTableDO newTable = DesignDataTableDO.valueOf((DataTable)dataTable);
        List<DesignDataField> dataFieldList = this.getDataFieldByTableKeyAndKind(dataField.getDataTableKey(), DataFieldKind.PUBLIC_FIELD_DIM, DataFieldKind.TABLE_FIELD_DIM, DataFieldKind.BUILT_IN_FIELD);
        dataField.setDataFieldKind(DataFieldKind.TABLE_FIELD_DIM);
        dataField.setNullable(Boolean.valueOf(false));
        dataField.setUpdateTime(Instant.now());
        if (dataField.getKey() == null) {
            dataField.setKey(UUID.randomUUID().toString());
        }
        if (dataField.getOrder() == null) {
            dataField.setOrder(OrderGenerator.newOrder());
        }
        dataFieldList.add(dataField);
        String[] order = BizKeyOrder.order(this.filterDataFields(dataTable, dataFieldList));
        newTable.setBizKeys(order);
        newTable.setUpdateTime(Instant.now());
        this.dataFieldDefaultValueCache.setDefaultValue(dataTable.getDataSchemeKey(), dataField.getKey(), defaultValue);
        try {
            this.dataFieldDao.insert(DesignDataFieldDO.valueOf((DataField)dataField));
            this.dataTableDao.update(newTable);
            this.dataSchemeDeployService.deployDataTable(dataTable.getKey(), true);
            this.sysLogForUpdateField((DataField)dataField);
        }
        catch (Exception e) {
            throw new SchemeDataException(e.getMessage());
        }
    }

    private String logMessage(Basic basic) {
        return String.format("Key: %s, \u6807\u8bc6: %s, \u540d\u79f0: %s", basic.getKey(), basic.getCode(), basic.getTitle());
    }

    private String logMessage(Collection<? extends Basic> basics) {
        return basics.stream().map(this::logMessage).collect(Collectors.joining("\r\n"));
    }

    private void logForInsertTable(DataTable dataTable) {
        if (!this.enableServiceLog) {
            return;
        }
        DataSchemeLoggerHelper.info("\u65b0\u589e\u6570\u636e\u8868", this.logMessage((Basic)dataTable));
    }

    private void logForInsertTable(Collection<? extends DataTable> dataTable) {
        if (!this.enableServiceLog) {
            return;
        }
        DataSchemeLoggerHelper.info("\u65b0\u589e\u6570\u636e\u8868", this.logMessage(dataTable));
    }

    private void sysLogForUpdateTable(DataTable dataTable) {
        if (!this.enableServiceLog) {
            return;
        }
        DataSchemeLoggerHelper.info("\u4fee\u6539\u6570\u636e\u8868", this.logMessage((Basic)dataTable));
    }

    private void sysLogForUpdateTable(Collection<? extends DataTable> dataTable) {
        if (!this.enableServiceLog) {
            return;
        }
        DataSchemeLoggerHelper.info("\u4fee\u6539\u6570\u636e\u8868", this.logMessage(dataTable));
    }

    private void sysLogForDeleteTable(DataTable dataTable) {
        if (!this.enableServiceLog) {
            return;
        }
        DataSchemeLoggerHelper.info("\u5220\u9664\u6570\u636e\u8868", this.logMessage((Basic)dataTable));
    }

    private void sysLogForDeleteTable(Collection<? extends DataTable> dataTable) {
        if (!this.enableServiceLog) {
            return;
        }
        DataSchemeLoggerHelper.info("\u5220\u9664\u6570\u636e\u8868", this.logMessage(dataTable));
    }

    private void sysLogForInsertField(DataField dataField) {
        if (!this.enableServiceLog) {
            return;
        }
        DataSchemeLoggerHelper.info("\u65b0\u589e\u6307\u6807\u6216\u5b57\u6bb5", this.logMessage((Basic)dataField));
    }

    private void sysLogForInsertField(Collection<? extends DataField> dataField) {
        if (!this.enableServiceLog) {
            return;
        }
        DataSchemeLoggerHelper.info("\u65b0\u589e\u6307\u6807\u6216\u5b57\u6bb5", this.logMessage(dataField));
    }

    private void sysLogForUpdateField(DataField dataField) {
        if (!this.enableServiceLog) {
            return;
        }
        DataSchemeLoggerHelper.info("\u4fee\u6539\u6307\u6807\u6216\u5b57\u6bb5", this.logMessage((Basic)dataField));
    }

    private void sysLogForUpdateField(Collection<? extends DataField> dataField) {
        if (!this.enableServiceLog) {
            return;
        }
        DataSchemeLoggerHelper.info("\u4fee\u6539\u6307\u6807\u6216\u5b57\u6bb5", this.logMessage(dataField));
    }

    private void sysLogForDeleteField(DataField dataField) {
        if (!this.enableServiceLog) {
            return;
        }
        DataSchemeLoggerHelper.info("\u5220\u9664\u6307\u6807\u6216\u5b57\u6bb5", this.logMessage((Basic)dataField));
    }

    private void sysLogForDeleteField(Collection<? extends DataField> dataField) {
        if (!this.enableServiceLog) {
            return;
        }
        DataSchemeLoggerHelper.info("\u5220\u9664\u6307\u6807\u6216\u5b57\u6bb5", this.logMessage(dataField));
    }

    private void sysLogForDeleteFieldByTable(DataTable dataTable) {
        if (!this.enableServiceLog) {
            return;
        }
        DataSchemeLoggerHelper.info("\u5220\u9664\u6570\u636e\u8868\u4e0b\u6240\u6709\u6307\u6807\u6216\u5b57\u6bb5", this.logMessage((Basic)dataTable));
    }

    private void sysLogForDeleteFieldByTable(Collection<? extends DataTable> dataTable) {
        if (!this.enableServiceLog) {
            return;
        }
        DataSchemeLoggerHelper.info("\u5220\u9664\u6570\u636e\u8868\u4e0b\u6240\u6709\u6307\u6807\u6216\u5b57\u6bb5", this.logMessage(dataTable));
    }

    @Transactional(rollbackFor={Exception.class})
    public void updateDataSchemeUnitScope(String dataScheme, List<DesignDataDimension> dimensions, boolean updateRunScope) {
        DesignDataScheme scheme = this.getDataScheme(dataScheme);
        if (null == scheme) {
            throw new SchemeDataException("\u6570\u636e\u65b9\u6848\u4e0d\u5b58\u5728");
        }
        List<DesignDataDimension> dims = this.getDataSchemeDimension(dataScheme);
        dims.removeIf(d -> d.getDimensionType() == DimensionType.UNIT_SCOPE);
        dims.addAll(dimensions);
        this.updateDataScheme(scheme, dims);
        if (updateRunScope) {
            List<DesignDataDimDO> insert = this.dataDimDao.getByDataScheme(dataScheme);
            insert.removeIf(d -> d.getDimensionType() != DimensionType.UNIT_SCOPE);
            this.runDataDimDao.delete(dataScheme, DimensionType.UNIT_SCOPE);
            for (DesignDataDimDO dim : insert) {
                this.runDataDimDao.insertNoUpdateTime(dim);
            }
            RefreshCache refreshCache = new RefreshCache(Collections.singletonMap(new RefreshScheme(scheme.getKey(), scheme.getCode()), null));
            this.applicationContext.publishEvent((ApplicationEvent)new RefreshSchemeCacheEvent(refreshCache));
        }
    }

    public List<DesignDataField> getDataFieldsByEntity(List<String> entityKeys) {
        List<DesignDataFieldDO> byEntity = this.dataFieldDao.getByEntity(entityKeys);
        return new ArrayList<DesignDataField>(byEntity);
    }
}

