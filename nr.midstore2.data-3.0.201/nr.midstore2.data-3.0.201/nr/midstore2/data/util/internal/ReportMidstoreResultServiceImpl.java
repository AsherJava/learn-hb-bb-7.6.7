/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFailInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFormInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkResultObject
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkUnitInfo
 *  com.jiuqi.nvwa.midstore.core.definition.common.ExchangeModeType
 */
package nr.midstore2.data.util.internal;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFailInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFormInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkResultObject;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkUnitInfo;
import com.jiuqi.nvwa.midstore.core.definition.common.ExchangeModeType;
import java.util.Map;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;
import nr.midstore2.data.util.IReportMidstoreResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportMidstoreResultServiceImpl
implements IReportMidstoreResultService {
    private static final Logger logger = LoggerFactory.getLogger(ReportMidstoreResultServiceImpl.class);
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;

    @Override
    public void addUnitErrorInfo(MistoreWorkResultObject workResult, String message, String unitCode, String unitTitle) {
        MistoreWorkFailInfo failInfo = null;
        if (workResult.getFailInfos().containsKey(message)) {
            failInfo = (MistoreWorkFailInfo)workResult.getFailInfos().get(message);
        } else {
            failInfo = new MistoreWorkFailInfo();
            failInfo.setMessage(message);
            workResult.addFailInfo(failInfo);
        }
        MistoreWorkUnitInfo unitInfo = new MistoreWorkUnitInfo();
        unitInfo.setUnitCode(unitCode);
        unitInfo.setUnitTitle(unitTitle);
        failInfo.getUnitInfos().put(unitCode, unitInfo);
    }

    @Override
    public void addUnitErrorInfo(ReportMidstoreContext context, MistoreWorkResultObject workResult, String failMsg, String unitCode, String unitTitle) {
        if (workResult == null) {
            return;
        }
        MistoreWorkFailInfo failInfo = null;
        if (workResult.getFailInfos().containsKey(failMsg)) {
            failInfo = (MistoreWorkFailInfo)workResult.getFailInfos().get(failMsg);
        } else {
            failInfo = new MistoreWorkFailInfo();
            failInfo.setMessage(failMsg);
            workResult.addFailInfo(failInfo);
        }
        MistoreWorkUnitInfo unitInfo = null;
        if (failInfo.getUnitInfos().containsKey(unitCode)) {
            unitInfo = (MistoreWorkUnitInfo)failInfo.getUnitInfos().get(unitCode);
        } else {
            String newUnitTitle = unitTitle;
            if (StringUtils.isEmpty((String)newUnitTitle) && context.getUnitCache().containsKey(unitCode)) {
                newUnitTitle = ((MistoreWorkUnitInfo)context.getUnitCache().get(unitCode)).getUnitTitle();
            }
            unitInfo = new MistoreWorkUnitInfo();
            unitInfo.setSuccess(false);
            unitInfo.setUnitCode(unitCode);
            unitInfo.setUnitTitle(newUnitTitle);
            unitInfo.setMessage(failMsg);
            failInfo.getUnitInfos().put(unitCode, unitInfo);
        }
    }

    @Override
    public void addUnitErrorInfo(ReportMidstoreContext context, MistoreWorkResultObject workResult, String failMsg, String message) {
        if (workResult == null) {
            return;
        }
        MistoreWorkFailInfo failInfo = null;
        if (workResult.getFailInfos().containsKey(failMsg)) {
            failInfo = (MistoreWorkFailInfo)workResult.getFailInfos().get(failMsg);
        } else {
            failInfo = new MistoreWorkFailInfo();
            failInfo.setMessage(failMsg);
        }
        for (String unitCode : context.getExchangeEnityCodes()) {
            MistoreWorkUnitInfo unitInfo = null;
            if (failInfo.getUnitInfos().containsKey(unitCode)) {
                unitInfo = (MistoreWorkUnitInfo)failInfo.getUnitInfos().get(unitCode);
                continue;
            }
            String unitTitle = null;
            if (StringUtils.isEmpty(unitTitle) && context.getUnitCache().containsKey(unitCode)) {
                unitTitle = ((MistoreWorkUnitInfo)context.getUnitCache().get(unitCode)).getUnitTitle();
            }
            unitInfo = new MistoreWorkUnitInfo();
            unitInfo.setSuccess(false);
            unitInfo.setUnitCode(unitCode);
            unitInfo.setUnitTitle(unitTitle);
            unitInfo.setMessage(failMsg);
            failInfo.getUnitInfos().put(unitCode, unitInfo);
        }
    }

    @Override
    public void addFormErrorInfo(ReportMidstoreContext context, MistoreWorkResultObject workResult, String failMsg, String message, String unitCode, String unitTitle, String formCode, String formTitle) {
        if (workResult == null) {
            return;
        }
        MistoreWorkFailInfo failInfo = null;
        if (workResult.getFailInfos().containsKey(failMsg)) {
            failInfo = (MistoreWorkFailInfo)workResult.getFailInfos().get(failMsg);
        } else {
            failInfo = new MistoreWorkFailInfo();
            failInfo.setMessage(failMsg);
            workResult.addFailInfo(failInfo);
        }
        MistoreWorkUnitInfo unitInfo = null;
        if (failInfo.getUnitInfos().containsKey(unitCode)) {
            unitInfo = (MistoreWorkUnitInfo)failInfo.getUnitInfos().get(unitCode);
        } else {
            String newUnitTitle = unitTitle;
            if (StringUtils.isEmpty((String)newUnitTitle) && context.getUnitCache().containsKey(unitCode)) {
                newUnitTitle = ((MistoreWorkUnitInfo)context.getUnitCache().get(unitCode)).getUnitTitle();
            }
            unitInfo = new MistoreWorkUnitInfo();
            unitInfo.setSuccess(false);
            unitInfo.setUnitCode(unitCode);
            unitInfo.setUnitTitle(newUnitTitle);
            failInfo.getUnitInfos().put(unitCode, unitInfo);
        }
        MistoreWorkFormInfo forminfo = null;
        if (unitInfo.getFormInfos().containsKey(formCode)) {
            forminfo = (MistoreWorkFormInfo)unitInfo.getFormInfos().get(formCode);
        } else {
            FormDefine formDefine = null;
            try {
                formDefine = this.viewController.queryFormByCodeInScheme(context.getFormSchemeKey(), formCode);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            forminfo = new MistoreWorkFormInfo();
            forminfo.setSuccess(false);
            forminfo.setFormCode(formCode);
            forminfo.setFormTitle(formTitle);
            if (formDefine != null) {
                forminfo.setFormTitle(formDefine.getTitle());
                forminfo.setFormKey(formDefine.getKey());
            }
            forminfo.setMessage(message);
            unitInfo.getFormInfos().put(formCode, forminfo);
        }
    }

    @Override
    public void addTableErrorInfo(ReportMidstoreContext context, MistoreWorkResultObject workResult, String failMsg, String message, String tableCode, String tableTitle) {
        for (String unitCode : context.getExchangeEnityCodes()) {
            this.addTableErrorInfo(context, workResult, failMsg, message, unitCode, "", tableCode, tableTitle);
        }
    }

    @Override
    public void addTableErrorInfo(ReportMidstoreContext context, MistoreWorkResultObject workResult, String failMsg, String message, String unitCode, String unitTitle, String tableCode, String tableTitle) {
        if (workResult == null) {
            return;
        }
        MistoreWorkFailInfo failInfo = null;
        if (workResult.getFailInfos().containsKey(failMsg)) {
            failInfo = (MistoreWorkFailInfo)workResult.getFailInfos().get(failMsg);
        } else {
            failInfo = new MistoreWorkFailInfo();
            failInfo.setMessage(failMsg);
            workResult.addFailInfo(failInfo);
        }
        MistoreWorkUnitInfo unitInfo = null;
        if (failInfo.getUnitInfos().containsKey(unitCode)) {
            unitInfo = (MistoreWorkUnitInfo)failInfo.getUnitInfos().get(unitCode);
        } else {
            String newUnitTitle = unitTitle;
            if (StringUtils.isEmpty((String)newUnitTitle) && context.getUnitCache().containsKey(unitCode)) {
                newUnitTitle = ((MistoreWorkUnitInfo)context.getUnitCache().get(unitCode)).getUnitTitle();
            }
            unitInfo = new MistoreWorkUnitInfo();
            unitInfo.setSuccess(false);
            unitInfo.setUnitCode(unitCode);
            unitInfo.setUnitTitle(newUnitTitle);
            failInfo.getUnitInfos().put(unitCode, unitInfo);
        }
        MistoreWorkFormInfo tableInfo = null;
        if (unitInfo.getTableInfos().containsKey(tableCode)) {
            tableInfo = (MistoreWorkFormInfo)unitInfo.getTableInfos().get(tableCode);
        } else {
            DataTable dataTable = this.dataSchemeSevice.getDataTableByCode(tableCode);
            tableInfo = new MistoreWorkFormInfo();
            tableInfo.setSuccess(false);
            tableInfo.setFormCode(tableCode);
            tableInfo.setFormTitle(tableTitle);
            if (dataTable != null) {
                tableInfo.setFormTitle(dataTable.getTitle());
                tableInfo.setFormKey(dataTable.getKey());
            }
            tableInfo.setMessage(message);
            unitInfo.getTableInfos().put(tableCode, tableInfo);
        }
        Map tableFormList = (Map)context.getExcuteParams().get("TABLEFORMLIST");
        if (tableFormList != null && tableFormList.containsKey(tableCode)) {
            String formKey = (String)tableFormList.get(tableCode);
            MistoreWorkFormInfo formInfo = null;
            if (unitInfo.getFormInfos().containsKey(formKey)) {
                formInfo = (MistoreWorkFormInfo)unitInfo.getFormInfos().get(tableCode);
            } else {
                FormDefine form = this.viewController.queryFormById(formKey);
                formInfo = new MistoreWorkFormInfo();
                formInfo.setSuccess(false);
                formInfo.setFormKey(formKey);
                formInfo.setFormCode(form.getTitle());
                formInfo.setFormTitle(form.getTitle());
                formInfo.setMessage(message);
                unitInfo.getFormInfos().put(formKey, formInfo);
            }
        }
    }

    @Override
    public void reSetErrorInfo(ReportMidstoreContext context) {
        MistoreWorkResultObject workResult = context.getWorkResult();
        if (workResult != null) {
            Object unitTitle;
            if (context.getMidstoreScheme() != null && context.getMidstoreScheme().getExchangeMode() == ExchangeModeType.EXCHANGE_GET && !context.isDeleteEmptyData()) {
                for (String unitCode : context.getExchangeEnityCodes()) {
                    if (workResult.getMidstoreTableUnits().contains(unitCode)) continue;
                    unitTitle = null;
                    if (context.getUnitCache().containsKey(unitCode)) {
                        unitTitle = ((MistoreWorkUnitInfo)context.getUnitCache().get(unitCode)).getUnitTitle();
                    }
                    this.addUnitErrorInfo(context, workResult, "\u5355\u4f4d\u5339\u914d\u5931\u8d25", unitCode, (String)unitTitle);
                }
            }
            if (workResult.getFailInfoList() != null) {
                for (MistoreWorkFailInfo failInfo : workResult.getFailInfoList()) {
                    for (MistoreWorkUnitInfo unitInfo : failInfo.getUnitInfos().values()) {
                        if (unitInfo.getFormInfos() != null && unitInfo.getFormInfos().size() > 0) {
                            workResult.getFailUnits().put(unitInfo.getUnitCode(), unitInfo);
                            continue;
                        }
                        if (unitInfo.isSuccess()) {
                            workResult.getSuccessUnits().put(unitInfo.getUnitCode(), unitInfo);
                            continue;
                        }
                        workResult.getFailUnits().put(unitInfo.getUnitCode(), unitInfo);
                    }
                }
            }
            for (String unitCode : context.getExchangeEnityCodes()) {
                MistoreWorkUnitInfo unitInfo;
                if (workResult.getSuccessUnits().containsKey(unitCode) || workResult.getFailUnits().containsKey(unitCode)) continue;
                unitTitle = null;
                if (context.getUnitCache().containsKey(unitCode)) {
                    unitTitle = ((MistoreWorkUnitInfo)context.getUnitCache().get(unitCode)).getUnitTitle();
                }
                unitInfo = new MistoreWorkUnitInfo();
                unitInfo.setSuccess(true);
                unitInfo.setUnitCode(unitCode);
                unitInfo.setUnitTitle((String)unitTitle);
                workResult.getSuccessUnits().put(unitInfo.getUnitCode(), unitInfo);
            }
            int unitCount = workResult.getFailUnits().size() + workResult.getSuccessUnits().size();
            workResult.setUnitCount(unitCount);
        }
    }
}

