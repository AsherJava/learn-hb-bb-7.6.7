/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.reportdatasync.util.InvestBillTool
 *  com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtil
 *  nr.single.map.data.PathUtil
 */
package com.jiuqi.gcreport.billcore.reportsync;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.billcore.dto.CommonBillParamDTO;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.reportdatasync.util.InvestBillTool;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import nr.single.map.data.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BillDataSyncTool {
    private static final Logger LOGGER = LoggerFactory.getLogger(BillDataSyncTool.class);

    public static void exportCommonBillTxt(File rootFolder, CommonBillParamDTO commonBillParamDTO) {
        if (CollectionUtils.isEmpty(commonBillParamDTO.getUnitCodes())) {
            return;
        }
        try {
            String filePath = PathUtil.createNewPath((String)rootFolder.getPath(), (String)("GC-billdata-" + commonBillParamDTO.getMasterTable()));
            ReportDataSyncUtil.writeFileJson((Object)commonBillParamDTO, (String)(filePath + "/param.txt"));
            BillDataSyncTool.exportCommonBillTxtNoParam(commonBillParamDTO, filePath);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessRuntimeException(e.getMessage());
        }
    }

    public static void exportCommonBillTxtNoParam(CommonBillParamDTO commonBillParamDTO, String filePath) throws Exception {
        List masterList = InvestBillTool.listByUnitCode((String)commonBillParamDTO.getMasterTable(), commonBillParamDTO.getUnitCodes(), (Integer)commonBillParamDTO.getYear());
        if (CollectionUtils.isEmpty((Collection)masterList)) {
            return;
        }
        ReportDataSyncUtil.writeBase64((String)commonBillParamDTO.getMasterTable(), (List)masterList, (String)(filePath + "/" + commonBillParamDTO.getMasterTable() + ".txt"));
        Set masterIdSet = masterList.stream().map(DefaultTableEntity::getId).collect(Collectors.toSet());
        for (String itemTable : commonBillParamDTO.getItemTables()) {
            List itemList = InvestBillTool.listItemByMasterId(masterIdSet, (String)itemTable);
            ReportDataSyncUtil.writeBase64((String)itemTable, (List)itemList, (String)(filePath + "/" + itemTable + ".txt"));
        }
    }

    public static List<String> importCommonBillTxt(File rootFolder, String masterTable) {
        String filePath;
        ArrayList<String> logMsg = new ArrayList<String>();
        try {
            filePath = PathUtil.createNewPath((String)rootFolder.getPath(), (String)("GC-billdata-" + masterTable));
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage());
        }
        String json = ReportDataSyncUtil.readJsonFile((String)(filePath + "/param.txt"));
        if (ReportDataSyncUtil.isEmptyJson((String)json)) {
            return logMsg;
        }
        CommonBillParamDTO param = (CommonBillParamDTO)JsonUtils.readValue((String)json, CommonBillParamDTO.class);
        if (null == param.getMasterTable()) {
            return logMsg;
        }
        int deleteNum = BillDataSyncTool.deleteBillData(param);
        System.out.println(deleteNum);
        int insertNum = BillDataSyncTool.insertBillData(param, filePath);
        logMsg.add(String.format("%1$s\u6570\u636e\u6210\u529f\u5bfc\u5165%2$s\u6761", param.getFuncTitle(), insertNum));
        return logMsg;
    }

    public static int insertBillData(CommonBillParamDTO param, String filePath) {
        String masterTable = param.getMasterTable();
        int insertNum = ReportDataSyncUtil.readBase64Db((String)(filePath + "/" + masterTable + ".txt"), (String)masterTable);
        for (String itemTable : param.getItemTables()) {
            ReportDataSyncUtil.readBase64Db((String)(filePath + "/" + itemTable + ".txt"), (String)itemTable);
        }
        return insertNum;
    }

    public static int deleteBillData(CommonBillParamDTO param) {
        for (String itemTable : param.getItemTables()) {
            int deleteNum = InvestBillTool.deleteItemByUnitCode((String)param.getMasterTable(), (String)itemTable, param.getUnitCodes(), null);
            System.out.println(deleteNum);
        }
        return InvestBillTool.deleteMasterByUnitCode((String)param.getMasterTable(), param.getUnitCodes(), null);
    }
}

