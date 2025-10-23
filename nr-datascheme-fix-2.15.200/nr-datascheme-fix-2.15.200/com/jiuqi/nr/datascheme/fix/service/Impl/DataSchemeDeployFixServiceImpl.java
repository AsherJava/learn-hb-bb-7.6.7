/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.core.DeployResult
 *  com.jiuqi.nr.datascheme.api.core.DeployResultDetail
 *  com.jiuqi.nr.datascheme.api.core.DeployStatusEnum
 *  com.jiuqi.nr.datascheme.internal.dao.impl.DataSchemeDeployStatusDaoImpl
 *  com.jiuqi.nr.datascheme.internal.entity.DataSchemeDeployStatusDO
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 */
package com.jiuqi.nr.datascheme.fix.service.Impl;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.core.DeployResultDetail;
import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import com.jiuqi.nr.datascheme.fix.common.DeployExType;
import com.jiuqi.nr.datascheme.fix.common.DeployFixType;
import com.jiuqi.nr.datascheme.fix.core.DeployExCheckResultDTO;
import com.jiuqi.nr.datascheme.fix.core.DeployFailFixHelper;
import com.jiuqi.nr.datascheme.fix.core.DeployFixCheckExResult;
import com.jiuqi.nr.datascheme.fix.core.DeployFixDataTable;
import com.jiuqi.nr.datascheme.fix.core.DeployFixDetailsDTO;
import com.jiuqi.nr.datascheme.fix.core.DeployFixParamDTO;
import com.jiuqi.nr.datascheme.fix.core.DeployFixResultDTO;
import com.jiuqi.nr.datascheme.fix.core.DeployFixTableModel;
import com.jiuqi.nr.datascheme.fix.progress.DSFixProgressUpdater;
import com.jiuqi.nr.datascheme.fix.service.IDataSchemeDeployFixService;
import com.jiuqi.nr.datascheme.fix.service.Impl.DataTableErrorFixService;
import com.jiuqi.nr.datascheme.fix.utils.ParamModelBuilder;
import com.jiuqi.nr.datascheme.fix.utils.Tools;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataSchemeDeployStatusDaoImpl;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDeployStatusDO;
import com.jiuqi.nvwa.definition.common.ProgressItem;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSchemeDeployFixServiceImpl
implements IDataSchemeDeployFixService {
    @Autowired
    private Tools tools;
    @Autowired
    private ParamModelBuilder modelBuilder;
    @Autowired
    private DeployFailFixHelper fixHelper;
    @Autowired
    private DataTableErrorFixService dataTableErrorFixService;
    @Autowired
    private DataSchemeDeployStatusDaoImpl dataSchemeDeployStatusDao;

    @Override
    public List<DeployExCheckResultDTO> doDataSchemeDeployExCheck(String dataSchemeKey) {
        ArrayList<DeployExCheckResultDTO> exResults = new ArrayList<DeployExCheckResultDTO>();
        List<DeployResultDetail> detials = this.tools.getFailDetailsByDataSchemeKey(dataSchemeKey);
        if (detials != null) {
            for (DeployResultDetail detial : detials) {
                DeployFixDataTable fixDataTable = this.modelBuilder.initFixDataTable(detial.getTableKey());
                fixDataTable = this.fixHelper.modelCheck(fixDataTable);
                fixDataTable = this.modelBuilder.getTableModelKey(fixDataTable);
                List<DeployFixTableModel> fixTableModel = this.modelBuilder.initFixTableModels(fixDataTable.getTableModelKey());
                fixTableModel = this.fixHelper.modelCheck(fixTableModel);
                DeployFixCheckExResult checkExResult = this.fixHelper.giveErrorType(fixDataTable, fixTableModel);
                DeployExCheckResultDTO exResult = new DeployExCheckResultDTO(dataSchemeKey, detial, checkExResult, false);
                exResults.add(exResult);
            }
        } else {
            String deployMessage = this.tools.getDeployMessage(dataSchemeKey);
            DesignDataScheme dataScheme = this.tools.getDataScheme(dataSchemeKey);
            if (deployMessage != null) {
                DeployExCheckResultDTO exResult = new DeployExCheckResultDTO(dataScheme, deployMessage, true);
                exResults.add(exResult);
            } else {
                DeployExCheckResultDTO exResult = new DeployExCheckResultDTO();
                exResults.add(exResult);
            }
        }
        return exResults;
    }

    @Override
    public List<DeployFixResultDTO> doDataSchemeDeployFix(List<DeployFixParamDTO> deployFixParams, Consumer<ProgressItem> progressConsumer) {
        ArrayList<DeployFixResultDTO> fixResults = new ArrayList<DeployFixResultDTO>();
        DSFixProgressUpdater fixProgressUpdater = this.fixHelper.getDSFixProgressUpdater(deployFixParams.get(0).getDataSchemeKey(), progressConsumer);
        Instant fixTime = Instant.now();
        for (DeployFixParamDTO fixParam : deployFixParams) {
            fixProgressUpdater.update(String.format("%s:%s[%s]", "\u6b63\u5728\u4fee\u590d\u6570\u636e\u8868", fixParam.getDataTableCode(), fixParam.getDataTableTitle()), 50);
            DeployFixResultDTO fixResult = this.fixHelper.doFix(fixParam, fixTime);
            fixResults.add(fixResult);
        }
        this.fixHelper.deployDataScheme(deployFixParams.get(0).getDataSchemeKey(), fixProgressUpdater);
        return fixResults;
    }

    @Override
    public List<DeployExCheckResultDTO> doDataSchemeDeployExCheck(EnumMap<DeployExType, DeployFixType> fixStrategy) {
        ArrayList<DeployExCheckResultDTO> deployFailDsCheckResults = new ArrayList<DeployExCheckResultDTO>();
        Set<String> deployFailDsKeys = this.tools.getAllDeployFailDsKey();
        for (String deployFailDsKey : deployFailDsKeys) {
            List<DeployExCheckResultDTO> fixResult = this.doDataSchemeDeployExCheck(deployFailDsKey);
            if (fixResult.size() == 1 && fixResult.get(0).getDataTableKey() == null) continue;
            deployFailDsCheckResults.addAll(fixResult);
        }
        EnumMap<DeployExType, DeployFixType> strategy = !fixStrategy.isEmpty() ? fixStrategy : this.fixHelper.firstStrategy();
        for (DeployExCheckResultDTO exResult : deployFailDsCheckResults) {
            for (Map.Entry<DeployExType, DeployFixType> entry : strategy.entrySet()) {
                if (exResult.getExType() != entry.getKey()) continue;
                exResult.setExType(entry.getKey());
                exResult.setExDesc(entry.getKey().getTitle());
            }
        }
        return deployFailDsCheckResults;
    }

    @Override
    public List<DeployFixResultDTO> doDsDeployFixWithPercentage(List<DeployFixParamDTO> deployFixParams, Consumer<ProgressItem> progressConsumer) {
        ArrayList<DeployFixResultDTO> fixResults = new ArrayList<DeployFixResultDTO>();
        DSFixProgressUpdater fixProgressUpdater = this.fixHelper.getDSFixProgressUpdater("allDs", progressConsumer);
        Instant fixTime = Instant.now();
        int count = 0;
        for (DeployFixParamDTO fixParam : deployFixParams) {
            fixProgressUpdater.update(String.format("%s:%s[%s]", "\u6b63\u5728\u4fee\u590d\u6570\u636e\u8868", fixParam.getDataTableCode(), fixParam.getDataTableTitle()), 50);
            DeployFixResultDTO fixResult = this.fixHelper.doFix(fixParam, fixTime);
            fixProgressUpdater.updateProgress((int)((double)Math.round(++count * 100 / deployFixParams.size()) / 100.0 * 100.0) - 1);
            fixResults.add(fixResult);
        }
        Set<String> deployFailDsKeys = this.tools.getAllDeployFailDsKey(deployFixParams);
        this.fixHelper.deployDataScheme(deployFailDsKeys, fixProgressUpdater);
        return fixResults;
    }

    @Override
    public List<DeployFixResultDTO> doDataSchemeDeployCheckAndFix(String dataSchemeKey, EnumMap<DeployExType, DeployFixType> fixStrategy, Consumer<ProgressItem> progressConsumer) {
        List<DeployExCheckResultDTO> exResults = this.doDataSchemeDeployExCheck(dataSchemeKey);
        ArrayList<DeployFixParamDTO> fixParams = new ArrayList<DeployFixParamDTO>();
        EnumMap<DeployExType, DeployFixType> strategy = !fixStrategy.isEmpty() ? fixStrategy : this.fixHelper.firstStrategy();
        for (DeployExCheckResultDTO exResult : exResults) {
            for (Map.Entry<DeployExType, DeployFixType> entry : strategy.entrySet()) {
                if (exResult.getExType() != entry.getKey()) continue;
                DeployFixParamDTO fixParam = new DeployFixParamDTO(exResult, entry.getValue());
                fixParams.add(fixParam);
            }
        }
        return this.doDataSchemeDeployFix(fixParams, progressConsumer);
    }

    @Override
    public List<DeployFixResultDTO> doDataSchemeDeployCheckAndFix(EnumMap<DeployExType, DeployFixType> fixStrategy, Consumer<ProgressItem> progressConsumer) {
        DSFixProgressUpdater fixProgressUpdater = this.fixHelper.getAllDsFixProgressUpdater("allDs", progressConsumer);
        ArrayList<DeployExCheckResultDTO> deployFailDsCheckResults = new ArrayList<DeployExCheckResultDTO>();
        Set<String> deployFailDsKeys = this.tools.getAllDeployFailDsKey();
        for (String deployFailDsKey : deployFailDsKeys) {
            DesignDataScheme dataScheme = this.tools.getDataScheme(deployFailDsKey);
            fixProgressUpdater.update(String.format("%s:%s", "\u6b63\u5728\u68c0\u67e5\u6570\u636e\u65b9\u6848", dataScheme.getTitle()), 30);
            List<DeployExCheckResultDTO> fixResult = this.doDataSchemeDeployExCheck(deployFailDsKey);
            deployFailDsCheckResults.addAll(fixResult);
        }
        ArrayList<DeployFixParamDTO> allFixParams = new ArrayList<DeployFixParamDTO>();
        EnumMap<DeployExType, DeployFixType> strategy = !fixStrategy.isEmpty() ? fixStrategy : this.fixHelper.firstStrategy();
        for (DeployExCheckResultDTO exResult : deployFailDsCheckResults) {
            for (Map.Entry<DeployExType, DeployFixType> entry : strategy.entrySet()) {
                if (exResult.getExType() != entry.getKey()) continue;
                DeployFixParamDTO fixParam = new DeployFixParamDTO(exResult, entry.getValue());
                allFixParams.add(fixParam);
            }
        }
        fixProgressUpdater.nextStep();
        ArrayList<DeployFixResultDTO> fixResults = new ArrayList<DeployFixResultDTO>();
        Instant fixTime = Instant.now();
        int count = 0;
        for (DeployFixParamDTO fixParam : allFixParams) {
            fixProgressUpdater.update(String.format("%s:%s[%s]", "\u6b63\u5728\u4fee\u590d\u6570\u636e\u8868", fixParam.getDataTableCode(), fixParam.getDataTableTitle()), (int)((double)Math.round(++count * 100 / allFixParams.size()) / 100.0 * 100.0));
            DeployFixResultDTO fixResult = this.fixHelper.doFix(fixParam, fixTime);
            fixResults.add(fixResult);
        }
        this.fixHelper.deployDataScheme(deployFailDsKeys, fixProgressUpdater);
        return fixResults;
    }

    @Override
    public List<DeployFixDetailsDTO> doDataSchemeDeployFix(String dataSchemeKey, BiConsumer<Integer, String> consumer) throws JQException {
        consumer.accept(0, "\u5206\u6790\u53d1\u5e03\u5f02\u5e38");
        DataSchemeDeployStatusDO status = this.dataSchemeDeployStatusDao.getByDataSchemeKey(dataSchemeKey);
        if (DeployStatusEnum.FAIL != status.getDeployStatus()) {
            consumer.accept(100, "\u4fee\u590d\u5b8c\u6210");
            return Collections.emptyList();
        }
        ArrayList<DeployFixDetailsDTO> fixDetails = new ArrayList<DeployFixDetailsDTO>();
        DeployResult deployResult = status.getDeployResult();
        if (!deployResult.getCheckState()) {
            List checkDetails = deployResult.getCheckDetials();
            int size = checkDetails.size();
            for (int i = 0; i < size; ++i) {
                DeployResultDetail detail = (DeployResultDetail)checkDetails.get(i);
                if (detail.isSuccess()) continue;
                consumer.accept(i / size * 80 + 10, "\u4fee\u590d\u6570\u636e\u8868" + detail.getTableTitle());
                DeployFixDetailsDTO fixDetail = this.dataTableErrorFixService.fix(dataSchemeKey, false, detail.getTableKey());
                fixDetails.add(fixDetail);
            }
        } else {
            List deployDetails = deployResult.getDeployDetials();
            int size = deployDetails.size();
            for (int i = 0; i < size; ++i) {
                DeployResultDetail detail = (DeployResultDetail)deployDetails.get(i);
                if (detail.isSuccess()) continue;
                consumer.accept(i / size * 80 + 10, "\u4fee\u590d\u6570\u636e\u8868" + detail.getTableTitle());
                DeployFixDetailsDTO fixDetail = this.dataTableErrorFixService.fix(dataSchemeKey, true, detail.getTableKey());
                fixDetails.add(fixDetail);
            }
        }
        status.setDeployStatus(DeployStatusEnum.SUCCESS);
        this.dataSchemeDeployStatusDao.update(status);
        consumer.accept(100, "\u4fee\u590d\u5b8c\u6210");
        return fixDetails;
    }
}

