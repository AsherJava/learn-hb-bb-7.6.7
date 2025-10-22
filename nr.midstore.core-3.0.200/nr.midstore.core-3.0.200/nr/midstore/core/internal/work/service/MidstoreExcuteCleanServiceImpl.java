/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.TableType
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  org.apache.commons.lang3.StringUtils
 */
package nr.midstore.core.internal.work.service;

import com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException;
import com.jiuqi.bi.core.midstore.dataexchange.enums.TableType;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.ArrayList;
import java.util.List;
import nr.midstore.core.dataset.IMidstoreBatchImportDataService;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.bean.MidstoreParam;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.service.IMidstoreFieldService;
import nr.midstore.core.definition.service.IMidstoreOrgDataFieldService;
import nr.midstore.core.definition.service.IMidstoreOrgDataService;
import nr.midstore.core.definition.service.IMidstoreSchemeInfoService;
import nr.midstore.core.definition.service.IMidstoreSchemeService;
import nr.midstore.core.param.service.IMidstoreCheckParamService;
import nr.midstore.core.param.service.IMidstoreMappingService;
import nr.midstore.core.param.service.IMistoreExchangeTaskService;
import nr.midstore.core.util.IMidstoreDimensionService;
import nr.midstore.core.work.service.IMidstoreExcuteCleanService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreExcuteCleanServiceImpl
implements IMidstoreExcuteCleanService {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreExcuteCleanServiceImpl.class);
    @Autowired
    private IMidstoreSchemeService midstoreSchemeSevice;
    @Autowired
    private IMidstoreSchemeInfoService schemeInfoSevice;
    @Autowired
    private IMidstoreOrgDataFieldService orgDataFieldSevice;
    @Autowired
    private IMidstoreCheckParamService checkParamService;
    @Autowired
    private IMistoreExchangeTaskService exchangeTaskService;
    @Autowired
    private IMidstoreOrgDataService orgDataService;
    @Autowired
    private IMidstoreFieldService fieldService;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private IMidstoreMappingService midstoreMappingService;
    @Autowired
    private IMidstoreBatchImportDataService batchImportService;
    @Autowired
    private IMidstoreDimensionService dimensionService;
    @Autowired
    private FileService fileService;

    @Override
    public MidstoreResultObject excuteCleanData(String midstoreSchemeId, AsyncTaskMonitor monitor) throws MidstoreException {
        MidstoreContext context = this.getContext(midstoreSchemeId, monitor);
        return this.excuteGetData2(context, monitor);
    }

    @Override
    public MidstoreResultObject excuteCleanData(MidstoreParam param, AsyncTaskMonitor monitor) throws MidstoreException {
        MidstoreContext context = this.getContext(param.getMidstoreSchemeId(), monitor);
        context.getExcuteParams().putAll(param.getExcuteParams());
        return this.excuteGetData2(context, monitor);
    }

    private MidstoreResultObject excuteGetData2(MidstoreContext context, AsyncTaskMonitor monitor) throws MidstoreException {
        MidstoreResultObject checkResult = this.checkParamService.doCheckParamsBeforeCleanData(context);
        if (checkResult == null) {
            return new MidstoreResultObject(false, "\u53c2\u6570\u68c0\u67e5\u6709\u5f02\u5e38");
        }
        if (checkResult.isSuccess()) {
            MidstoreSchemeDTO midstoreScheme = context.getMidstoreScheme();
            TaskDefine taskDefine = context.getTaskDefine();
            if (taskDefine == null) {
                taskDefine = this.viewController.queryTaskDefine(midstoreScheme.getTaskKey());
            }
            String dataSchemeKey = taskDefine.getDataScheme();
            IDataExchangeTask dataExchangeTask = this.exchangeTaskService.getExchangeTask(context);
            this.cleanFieldDataFromMidstore(context, dataExchangeTask);
            return new MidstoreResultObject(true, "");
        }
        return checkResult;
    }

    private void readOrgDataFromMidstore(MidstoreContext context, IDataExchangeTask dataExchangeTask) throws MidstoreException {
        try {
            DETableInfo deTableInfo = dataExchangeTask.getTableInfoByName("ORG_OTHERDATA");
            if (deTableInfo != null) {
                int month = 6;
                if (context.getSchemeInfo().getCleanMonth() != null && context.getSchemeInfo().getCleanMonth() > 0 && context.getSchemeInfo().getCleanMonth() <= 120) {
                    month = context.getSchemeInfo().getCleanMonth();
                }
                String delContition = String.format("UPDATE_DATE < add_months(trunc(sysdate), -%s)", month);
                dataExchangeTask.deleteData("ORG_OTHERDATA", delContition);
            }
        }
        catch (DataExchangeException e) {
            logger.error(e.getMessage(), e);
            logger.info("\u4e2d\u95f4\u5e93\u8bfb\u53d6\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u5931\u8d25");
        }
    }

    private void cleanFieldDataFromMidstore(MidstoreContext context, IDataExchangeTask dataExchangeTask) throws MidstoreException {
        this.midstoreMappingService.initZbMapping(context);
        try {
            List tableModes = dataExchangeTask.getAllTableModel();
            ArrayList<DETableModel> fixTableModes = new ArrayList<DETableModel>();
            ArrayList<DETableModel> floatTableModes = new ArrayList<DETableModel>();
            for (DETableModel tableModel : tableModes) {
                List fieldInfos = tableModel.getFields();
                DETableInfo tableInfo = tableModel.getTableInfo();
                TableType tableType = tableInfo.getType();
                if (tableType == TableType.ZB) {
                    fixTableModes.add(tableModel);
                    continue;
                }
                if (tableType != TableType.BIZ) continue;
                floatTableModes.add(tableModel);
            }
            for (DETableModel tableModel : fixTableModes) {
                this.cleanFixFieldDataFromMidstore(context, dataExchangeTask, tableModel);
            }
            for (DETableModel tableModel : floatTableModes) {
                this.cleanFloatFieldDataFromMidstore(context, dataExchangeTask, tableModel);
            }
        }
        catch (DataExchangeException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException(e.getMessage(), e);
        }
    }

    private void cleanFixFieldDataFromMidstore(MidstoreContext context, IDataExchangeTask dataExchangeTask, DETableModel tableModel) throws MidstoreException {
        String deTableCode;
        DETableInfo tableInfo = tableModel.getTableInfo();
        String nrTableCode = deTableCode = tableInfo.getName();
        String tablePrefix = context.getMidstoreScheme().getTablePrefix();
        if (StringUtils.isNotEmpty((CharSequence)tablePrefix) && nrTableCode.startsWith(tablePrefix + "_")) {
            nrTableCode = nrTableCode.substring(tablePrefix.length() + 1, nrTableCode.length());
        }
        try {
            int month = 6;
            if (context.getSchemeInfo().getCleanMonth() != null && context.getSchemeInfo().getCleanMonth() > 0 && context.getSchemeInfo().getCleanMonth() <= 120) {
                month = context.getSchemeInfo().getCleanMonth();
            }
            String delContition = String.format("UPDATE_DATE < add_months(trunc(sysdate), -%s)", month);
            dataExchangeTask.deleteData(deTableCode, delContition);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException("\u4e2d\u95f4\u5e93\u5220\u9664\u6570\u636e\u6709\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
    }

    private void cleanFloatFieldDataFromMidstore(MidstoreContext context, IDataExchangeTask dataExchangeTask, DETableModel tableModel) throws MidstoreException {
        String deTableCode;
        DETableInfo tableInfo = tableModel.getTableInfo();
        String nrTableCode = deTableCode = tableInfo.getName();
        String tablePrefix = context.getMidstoreScheme().getTablePrefix();
        if (StringUtils.isNotEmpty((CharSequence)tablePrefix) && nrTableCode.startsWith(tablePrefix + "_")) {
            nrTableCode = nrTableCode.substring(tablePrefix.length() + 1, nrTableCode.length());
        }
        try {
            int month = 6;
            if (context.getSchemeInfo().getCleanMonth() != null && context.getSchemeInfo().getCleanMonth() > 0 && context.getSchemeInfo().getCleanMonth() <= 120) {
                month = context.getSchemeInfo().getCleanMonth();
            }
            String delContition = String.format("UPDATE_DATE  < add_months(trunc(sysdate), -%s)", month);
            dataExchangeTask.deleteData(deTableCode, delContition);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException("\u4e2d\u95f4\u5e93\u5220\u9664\u6570\u636e\u6709\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
    }

    private MidstoreContext getContext(String midstoreSchemeId, AsyncTaskMonitor monitor) {
        MidstoreContext context = new MidstoreContext();
        context.setSchemeKey(midstoreSchemeId);
        context.setAsyncMonitor(monitor);
        context.setMidstoreScheme(this.midstoreSchemeSevice.getByKey(midstoreSchemeId));
        context.setSchemeInfo(this.schemeInfoSevice.getBySchemeKey(midstoreSchemeId));
        return context;
    }
}

