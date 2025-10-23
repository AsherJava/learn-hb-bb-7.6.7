/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam
 *  com.jiuqi.nr.dataentry.bean.ExecuteTaskParam
 *  com.jiuqi.nr.dataentry.service.IMultcheckService
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.multcheck2.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.ExecuteTaskParam;
import com.jiuqi.nr.dataentry.service.IMultcheckService;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.multcheck2.service.IMCDimService;
import com.jiuqi.nr.multcheck2.service.IMCExecuteUploadMultiService;
import com.jiuqi.nr.multcheck2.service.IMCExecuteUploadSingleService;
import com.jiuqi.nr.multcheck2.web.result.MCUploadResult;
import com.jiuqi.nr.multcheck2.web.vo.MCRunVO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MCUploadServiceImpl
implements IMultcheckService {
    private static final Logger logger = LoggerFactory.getLogger(MCUploadServiceImpl.class);
    @Autowired
    IMCExecuteUploadMultiService multiUpload;
    @Autowired
    IMCExecuteUploadSingleService singleUpload;
    @Autowired
    IMCDimService mcDimService;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    IEntityMetaService entityMetaService;

    public String comprehensiveAudit(ExecuteTaskParam param, AsyncTaskMonitor asyncTaskMonitor) {
        MCRunVO vo = this.buildMCRunVO(param.getContext(), param.getContextEntityId());
        MCUploadResult result = this.singleUpload.uploadSingleExecute(null, vo);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString((Object)result);
        }
        catch (Exception e) {
            logger.error("\u6267\u884c\u7efc\u5408\u5ba1\u6838\u62a5\u9519", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public String bathComprehensiveAudit(BatchExecuteTaskParam param, AsyncTaskMonitor asyncTaskMonitor) {
        MCRunVO vo = this.buildMCRunVO(param.getContext(), param.getContextEntityId());
        MCUploadResult result = this.multiUpload.uploadMultiExecute(null, vo);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString((Object)result);
        }
        catch (Exception e) {
            logger.error("\u6267\u884c\u7efc\u5408\u5ba1\u6838\u62a5\u9519", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private MCRunVO buildMCRunVO(JtableContext context, String contextEntityId) {
        MCRunVO vo = new MCRunVO();
        vo.setTask(context.getTaskKey());
        vo.setPeriod(((DimensionValue)context.getDimensionSet().get("DATATIME")).getValue());
        String orgCodes = ((DimensionValue)context.getDimensionSet().get("MD_ORG")).getValue();
        vo.setOrgCodes(new ArrayList<String>(Arrays.asList(orgCodes.split(";"))));
        vo.setFormScheme(context.getFormSchemeKey());
        vo.setOrg(contextEntityId);
        if (!CollectionUtils.isEmpty(context.getDimensionSet())) {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(context.getTaskKey());
            DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
            List<DataDimension> otherDimsForReport = this.mcDimService.getOtherDimsForReport(dataScheme.getKey());
            if (CollectionUtils.isEmpty(otherDimsForReport)) {
                vo.setDimSetMap(context.getDimensionSet());
            } else {
                HashSet<String> otherDimNames = new HashSet<String>();
                for (DataDimension dim : otherDimsForReport) {
                    IEntityDefine entityDefine = this.entityMetaService.queryEntity(dim.getDimKey());
                    otherDimNames.add(entityDefine.getDimensionName());
                }
                HashMap<String, DimensionValue> dimSetMap = new HashMap<String, DimensionValue>();
                for (String dim : context.getDimensionSet().keySet()) {
                    if (otherDimNames.contains(dim)) continue;
                    dimSetMap.put(dim, new DimensionValue((DimensionValue)context.getDimensionSet().get(dim)));
                }
                vo.setDimSetMap(dimSetMap);
            }
        }
        return vo;
    }
}

