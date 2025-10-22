/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.Config
 *  com.jiuqi.bi.core.midstore.dataexchange.DataExchangeEngineFactory
 *  com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException
 *  com.jiuqi.bi.core.midstore.dataexchange.IDataExchangeEngine
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.SysFieldType
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETaskInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.SysFieldInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeProvider
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IFileDataExchangeEngine
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package nr.midstore.core.internal.param.service;

import com.jiuqi.bi.core.midstore.dataexchange.Config;
import com.jiuqi.bi.core.midstore.dataexchange.DataExchangeEngineFactory;
import com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException;
import com.jiuqi.bi.core.midstore.dataexchange.IDataExchangeEngine;
import com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType;
import com.jiuqi.bi.core.midstore.dataexchange.enums.SysFieldType;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETaskInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.SysFieldInfo;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeProvider;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.bi.core.midstore.dataexchange.services.IFileDataExchangeEngine;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.common.StorageModeType;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import nr.midstore.core.definition.service.IMidstoreSchemeInfoService;
import nr.midstore.core.definition.service.IMidstoreSchemeService;
import nr.midstore.core.internal.publish.service.MidstoreDataExchangeProvider;
import nr.midstore.core.param.service.IMistoreExchangeTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MistoreExchangeTaskServiceImpl
implements IMistoreExchangeTaskService {
    private static final Logger logger = LoggerFactory.getLogger(MistoreExchangeTaskServiceImpl.class);
    @Autowired
    private IMidstoreSchemeService midstoreSchemeSevice;
    @Autowired
    private IMidstoreSchemeInfoService schemeInfoSevice;

    @Override
    public IDataExchangeTask getExchangeTask(MidstoreContext context) throws MidstoreException {
        IDataExchangeTask exchangeask = null;
        MidstoreSchemeInfoDTO schemeInfo = context.getSchemeInfo();
        MidstoreSchemeDTO midstoreScheme = context.getMidstoreScheme();
        if (midstoreScheme.getStorageMode() == StorageModeType.STOREMODE_DATABASE) {
            if (StringUtils.isEmpty((String)midstoreScheme.getDataBaseLink())) {
                throw new MidstoreException("\u672a\u5b9a\u4e49\u6570\u636e\u5e93\u94fe\u63a5\uff01");
            }
            try {
                MidstoreDataExchangeProvider provider = new MidstoreDataExchangeProvider(midstoreScheme.getDataBaseLink());
                try (Connection connection = provider.getConnection();){
                    if (connection == null) {
                        throw new MidstoreException("\u6570\u636e\u5e93\u94fe\u63a5\u5931\u8d25," + midstoreScheme.getDataBaseLink());
                    }
                }
                IDataExchangeEngine dbEngine = DataExchangeEngineFactory.getInstance().getDBEngine((IDataExchangeProvider)provider);
                if (StringUtils.isEmpty((String)schemeInfo.getExchangeTaskKey())) {
                    schemeInfo.setExchangeTaskKey(UUID.randomUUID().toString());
                    this.schemeInfoSevice.update(schemeInfo);
                }
                DETaskInfo deTaskInfo = new DETaskInfo(schemeInfo.getExchangeTaskKey(), midstoreScheme.getTablePrefix());
                deTaskInfo.setTitle(midstoreScheme.getTitle());
                deTaskInfo.setDescription(midstoreScheme.getTitle());
                exchangeask = dbEngine.openTask(deTaskInfo, this.getMidstoreConfig(), false);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new MidstoreException(e.getMessage(), e);
            }
        }
        if (midstoreScheme.getStorageMode() == StorageModeType.STOREMODE_FILE) {
            if (StringUtils.isEmpty((String)midstoreScheme.getStorageInfo())) {
                throw new MidstoreException("\u672a\u5b9a\u4e49\u6587\u4ef6\u8def\u5f84\uff01");
            }
            try {
                IFileDataExchangeEngine fileEngine = DataExchangeEngineFactory.getInstance().getFileEngine(midstoreScheme.getStorageInfo());
                if (StringUtils.isEmpty((String)schemeInfo.getExchangeTaskKey())) {
                    schemeInfo.setExchangeTaskKey(UUID.randomUUID().toString());
                    this.schemeInfoSevice.update(schemeInfo);
                }
                DETaskInfo deTaskInfo = new DETaskInfo(schemeInfo.getExchangeTaskKey(), midstoreScheme.getTablePrefix());
                deTaskInfo.setTitle(midstoreScheme.getTitle());
                deTaskInfo.setDescription(midstoreScheme.getTitle());
                exchangeask = fileEngine.openTask(deTaskInfo, this.getMidstoreConfig(), false);
            }
            catch (DataExchangeException e) {
                logger.error(e.getMessage(), e);
                throw new MidstoreException(e.getMessage());
            }
        }
        return exchangeask;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public MidstoreResultObject deleteExchangeTask(MidstoreSchemeDTO midstoreScheme) throws MidstoreException {
        MidstoreResultObject result = new MidstoreResultObject(true, "");
        if (midstoreScheme.getStorageMode() == StorageModeType.STOREMODE_DATABASE) {
            if (StringUtils.isEmpty((String)midstoreScheme.getDataBaseLink())) {
                throw new MidstoreException("\u672a\u5b9a\u4e49\u6570\u636e\u5e93\u94fe\u63a5\uff01");
            }
            try {
                MidstoreDataExchangeProvider provider = new MidstoreDataExchangeProvider(midstoreScheme.getDataBaseLink());
                IDataExchangeEngine dbEngine = DataExchangeEngineFactory.getInstance().getDBEngine((IDataExchangeProvider)provider);
                if (!StringUtils.isNotEmpty((String)midstoreScheme.getTablePrefix())) return result;
                dbEngine.deleteTask(midstoreScheme.getTablePrefix());
                return result;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new MidstoreException(e.getMessage(), e);
            }
        }
        if (midstoreScheme.getStorageMode() != StorageModeType.STOREMODE_FILE) return result;
        if (StringUtils.isEmpty((String)midstoreScheme.getStorageInfo())) {
            throw new MidstoreException("\u672a\u5b9a\u4e49\u6587\u4ef6\u8def\u5f84\uff01");
        }
        try {
            IFileDataExchangeEngine fileEngine = DataExchangeEngineFactory.getInstance().getFileEngine(midstoreScheme.getStorageInfo());
            if (!StringUtils.isNotEmpty((String)midstoreScheme.getTablePrefix())) return result;
            fileEngine.deleteTask(midstoreScheme.getTablePrefix());
            return result;
        }
        catch (DataExchangeException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException(e.getMessage());
        }
    }

    @Override
    public Config getMidstoreConfig() {
        Config config = new Config();
        SysFieldInfo updateUser = new SysFieldInfo("UPDATE_USER", DEDataType.STRING, 200, 0, SysFieldType.UPDATE_USER);
        SysFieldInfo updateTime = new SysFieldInfo("UPDATE_DATE", DEDataType.DATE, 20, 0, SysFieldType.UPDATE_DATE);
        List<SysFieldInfo> sysFields = Arrays.asList(updateUser, updateTime);
        config.setSysFields(sysFields);
        config.setOrgColName("MDCODE");
        config.setTimeKeyColName("DATATIME");
        config.setTransferLogicColumn(true);
        config.setBatchSize(10000);
        config.setUpdateUser(NpContextHolder.getContext().getIdentityId());
        config.setUpdateUser("");
        return config;
    }

    @Override
    public Config getExchangeConfig(MidstoreSchemeDTO midstoreScheme) {
        return this.getMidstoreConfig();
    }

    @Override
    public IDataExchangeProvider getExchangeProvider(MidstoreSchemeDTO midstoreScheme) throws MidstoreException {
        MidstoreDataExchangeProvider provider = null;
        if (midstoreScheme.getStorageMode() == StorageModeType.STOREMODE_DATABASE) {
            if (StringUtils.isEmpty((String)midstoreScheme.getDataBaseLink())) {
                throw new MidstoreException("\u672a\u5b9a\u4e49\u6570\u636e\u5e93\u94fe\u63a5\uff01");
            }
            provider = new MidstoreDataExchangeProvider(midstoreScheme.getDataBaseLink());
        }
        return provider;
    }
}

