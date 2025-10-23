/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.internal.entity.DataTableDO
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.datascheme.fix.web.rest;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.fix.core.DeployExCheckResultDTO;
import com.jiuqi.nr.datascheme.fix.core.DeployFailFixHelper;
import com.jiuqi.nr.datascheme.fix.core.DeployFixResultDTO;
import com.jiuqi.nr.datascheme.fix.entity.DeployFailFixLogDO;
import com.jiuqi.nr.datascheme.fix.progress.DSFixProgressCacheService;
import com.jiuqi.nr.datascheme.fix.service.IDataSchemeDeployFixService;
import com.jiuqi.nr.datascheme.fix.service.Impl.DataSchemeDeployFixLogServiceImpl;
import com.jiuqi.nr.datascheme.fix.utils.Tools;
import com.jiuqi.nr.datascheme.fix.web.facade.FixCheckVO;
import com.jiuqi.nr.datascheme.fix.web.facade.FixParamVO;
import com.jiuqi.nr.datascheme.fix.web.facade.FixResultVO;
import com.jiuqi.nr.datascheme.fix.web.facade.ProgressVO;
import com.jiuqi.nr.datascheme.fix.web.transUtil.TransUtil;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import com.jiuqi.nvwa.definition.common.ProgressItem;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/datascheme/"})
@Api(tags={"\u6570\u636e\u65b9\u6848\uff1a\u6570\u636e\u65b9\u6848\u53d1\u5e03\u5931\u8d25\u4fee\u590d"})
public class DataSchemeDeployFixRestController {
    @Autowired
    private IDataSchemeDeployFixService deployFixService;
    @Autowired
    private DeployFailFixHelper fixHelper;
    @Autowired
    private Tools tools;
    @Autowired
    private DataSchemeDeployFixLogServiceImpl fixLogService;
    @Autowired
    private DSFixProgressCacheService fixProgressCacheService;
    private static final Logger logger = LoggerFactory.getLogger(DataSchemeDeployFixRestController.class);

    @ApiOperation(value="\u53d1\u5e03\u5931\u8d25\u4fee\u590d\u68c0\u67e5")
    @GetMapping(value={"scheme/fix-check/{key}"})
    public List<FixCheckVO> dataSchemeDeployFailCheck(@PathVariable(value="key") String dataSchemeKey) {
        ArrayList<FixCheckVO> fixCheckVOS = new ArrayList<FixCheckVO>();
        List<Object> exResults = new ArrayList();
        try {
            exResults = this.deployFixService.doDataSchemeDeployExCheck(dataSchemeKey);
        }
        catch (Exception e) {
            logger.info("\u672a\u77e5\u60c5\u51b5\uff0c\u53c2\u6570\u68c0\u67e5\u5931\u8d25", (Object)e.getMessage(), (Object)e);
            return fixCheckVOS;
        }
        if (exResults.size() != 0) {
            for (DeployExCheckResultDTO deployExCheckResultDTO : exResults) {
                FixCheckVO fixCheckVO;
                if (deployExCheckResultDTO.isScheme()) {
                    fixCheckVO = new FixCheckVO(null, deployExCheckResultDTO);
                    fixCheckVOS.add(fixCheckVO);
                    continue;
                }
                fixCheckVO = new FixCheckVO(this.fixHelper.getFixParamType(deployExCheckResultDTO.getExType()), deployExCheckResultDTO);
                fixCheckVOS.add(fixCheckVO);
            }
        } else {
            return fixCheckVOS;
        }
        return fixCheckVOS;
    }

    @ApiOperation(value="\u6267\u884c\u4fee\u590d\u64cd\u4f5c")
    @PostMapping(value={"scheme/dofix"})
    public List<FixResultVO> dataSchemeDeployFailDoFix(@RequestBody List<FixParamVO> fixParamVOS) {
        List<DeployFixResultDTO> fixResultDTOs = this.deployFixService.doDataSchemeDeployFix(TransUtil.paramVO2DTO(fixParamVOS), p -> this.fixHelper.updateProgressItem(((FixParamVO)fixParamVOS.get(0)).getDataSchemeKey(), (ProgressItem)p));
        List<FixResultVO> fixResults = TransUtil.resultDTO2VO(fixResultDTOs);
        return fixResults;
    }

    @ApiOperation(value="\u662f\u5426\u663e\u793a\u67e5\u770b\u5386\u53f2\u4fee\u590d\u7ed3\u679c\u6309\u94ae")
    @GetMapping(value={"scheme/show-fix-result/{key}"})
    public boolean showFixResultBtn(@PathVariable(value="key") String dataSchemeKey) {
        boolean showFixResult = false;
        List<DeployFailFixLogDO> logs = this.fixLogService.getFixLogByScheme(dataSchemeKey);
        if (logs != null && logs.size() > 0) {
            showFixResult = true;
        }
        return showFixResult;
    }

    @ApiOperation(value="\u67e5\u770b\u5386\u53f2\u4fee\u590d\u7ed3\u679c")
    @GetMapping(value={"scheme/fix-logs/{key}"})
    public List<FixResultVO> dataSchemeDeployFixLogs(@PathVariable(value="key") String dataSchemeKey) {
        List<DeployFailFixLogDO> deployFailFixLogs = this.fixLogService.getFixLogByScheme(dataSchemeKey);
        List<FixResultVO> fixResults = this.logDO2VO(deployFailFixLogs);
        return fixResults;
    }

    @ApiOperation(value="\u5220\u9664\u5907\u4efd\u8868")
    @PostMapping(value={"scheme/droptable"})
    public boolean dropNewTable(@RequestBody List<FixResultVO> fixResults) {
        boolean isDrop = false;
        int ret = 0;
        for (FixResultVO fixResult : fixResults) {
            if (fixResult.getNewTableName() != null) {
                try {
                    this.fixLogService.deleteBackUpTablesAndLogsByTableAndTime(fixResult.getFixTime(), fixResult.getDataTableKey());
                }
                catch (Exception e) {
                    ++ret;
                    isDrop = false;
                }
                continue;
            }
            this.fixLogService.deleteLogsByTableAndTime(fixResult.getFixTime(), fixResult.getDataTableKey());
        }
        if (ret == 0) {
            isDrop = true;
        }
        return isDrop;
    }

    @ApiOperation(value="\u5168\u90e8\u5220\u9664\u5907\u4efd\u8868")
    @GetMapping(value={"scheme/droptableall/{key}"})
    public boolean dropNewTables(@PathVariable(value="key") String dataSchemeKey) {
        boolean isDrop = false;
        int ret = 0;
        try {
            this.fixLogService.deleteAllBackUpTablesAndLogsByScheme(dataSchemeKey);
        }
        catch (Exception e) {
            ++ret;
            isDrop = false;
        }
        if (ret == 0) {
            isDrop = true;
        }
        return isDrop;
    }

    @ApiOperation(value="\u83b7\u53d6\u4fee\u590d\u8fdb\u5ea6")
    @GetMapping(value={"scheme/fixprogress/{key}"})
    public ProgressVO getFixProcess(@PathVariable(value="key") String dataSchemeKey) {
        ProgressItem progressItem = this.fixProgressCacheService.getProgress(dataSchemeKey);
        ProgressVO progress = new ProgressVO(progressItem);
        return progress;
    }

    public List<FixResultVO> logDO2VO(List<DeployFailFixLogDO> fixLogs) {
        ArrayList<FixResultVO> fixResults = new ArrayList<FixResultVO>();
        for (DeployFailFixLogDO deployFailFixLog : fixLogs) {
            String dataTableCode;
            String dataTableTitle;
            DataTableDO dataTable = this.tools.getDataTableByTableKey(deployFailFixLog.getDataTableKey());
            List<DesignTableModelDefine> tableModels = this.tools.getTableModelByDataTableKey(deployFailFixLog.getDataTableKey());
            ArrayList<String> ptNameList = new ArrayList<String>();
            String ptName = " ";
            if (dataTable != null) {
                dataTableTitle = this.tools.getDataTableByTableKey(deployFailFixLog.getDataTableKey()).getTitle();
                dataTableCode = this.tools.getDataTableByTableKey(deployFailFixLog.getDataTableKey()).getCode();
            } else {
                dataTableTitle = "\u6b64\u8868\u4e0d\u5b58\u5728";
                dataTableCode = null;
            }
            if (tableModels != null && tableModels.size() != 0) {
                for (DesignTableModelDefine tableModel : tableModels) {
                    if (tableModel == null) continue;
                    ptNameList.add(tableModel.getName());
                }
                ptName = StringUtil.join(ptNameList.toArray(), "\uff0c");
            } else {
                ptName = null;
            }
            FixResultVO fixResult = new FixResultVO(deployFailFixLog);
            fixResult.setDataTableTitle(dataTableTitle);
            fixResult.setDataTableCode(dataTableCode);
            fixResult.setPtName(ptName);
            fixResults.add(fixResult);
        }
        return fixResults;
    }
}

