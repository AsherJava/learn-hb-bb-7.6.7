/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.single.core.dbf.DbfTableUtil
 *  com.jiuqi.nr.single.core.dbf.IDbfTable
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.exception.SingleDataException
 */
package nr.single.client.internal.service.querycheck;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nr.single.client.service.querycheck.ISingleImportQueryCheckService;
import nr.single.client.service.querycheck.bean.QueryModalCheckInfo;
import nr.single.client.service.querycheck.bean.QueryModalCheckParam;
import nr.single.client.service.querycheck.bean.QueryModalCheckResult;
import nr.single.client.service.querycheck.extend.ISingleQueryCheckWriteService;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.exception.SingleDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleImportQueryCheckServiceImpl
implements ISingleImportQueryCheckService {
    private static final Logger logger = LoggerFactory.getLogger(SingleImportQueryCheckServiceImpl.class);
    @Autowired(required=false)
    private ISingleQueryCheckWriteService writeQueryCheckSerice;

    @Override
    public void importQueryCheckResult(TaskDataContext context, String taskDataPath, Map<String, DimensionValue> dimensionSet, AsyncTaskMonitor asyncTaskMonitor) throws SingleDataException {
        if (this.writeQueryCheckSerice == null) {
            return;
        }
        try {
            logger.info("\u67e5\u8be2\u6a21\u7248\u5ba1\u6838\u5bfc\u5165\uff1a");
            String checkFile = taskDataPath + "JQModalCheckDesc.DBF";
            List<QueryModalCheckInfo> list = this.loadQueryCheckInfos(context, checkFile);
            QueryModalCheckResult resulst = this.writeQueryCheckInfo(context, dimensionSet, list);
            if (resulst != null) {
                logger.info("\u67e5\u8be2\u6a21\u7248\u5ba1\u6838\u5bfc\u5165\uff1a\u8bb0\u5f55\u6570=" + list.size() + ",\u72b6\u6001=" + resulst.getMessage() + ",\u4fe1\u606f\uff1a" + resulst.getMessage());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SingleDataException(e.getMessage(), (Throwable)e);
        }
    }

    private QueryModalCheckResult writeQueryCheckInfo(TaskDataContext context, Map<String, DimensionValue> dimensionSet, List<QueryModalCheckInfo> list) throws SingleDataException {
        QueryModalCheckResult result = null;
        if (this.writeQueryCheckSerice != null) {
            QueryModalCheckParam param = new QueryModalCheckParam();
            result = this.writeQueryCheckSerice.writeQueryCheckResult(context.getFormSchemeKey(), dimensionSet, list, param);
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<QueryModalCheckInfo> loadQueryCheckInfos(TaskDataContext context, String checkFile) throws SingleDataException, SingleFileException {
        ArrayList<QueryModalCheckInfo> checkInfos = new ArrayList<QueryModalCheckInfo>();
        if (SinglePathUtil.getFileExists((String)checkFile)) {
            logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a\u5bfc\u5165\u67e5\u8be2\u5ba1\u6838\u6a21\u7248\u8bf4\u660e");
            try (IDbfTable checkDbf = DbfTableUtil.getDbfTable((String)checkFile);){
                Map uploadEntityZdmKeyMap = context.getUploadEntityZdmKeyMap();
                for (int i = 0; i < checkDbf.getDataRowCount(); ++i) {
                    DataRow dbfRow = (DataRow)checkDbf.getTable().getRows().get(i);
                    if (!checkDbf.isHasLoadAllRec()) {
                        checkDbf.loadDataRow(dbfRow);
                    }
                    try {
                        String zdm = dbfRow.getValueString("ZDM");
                        String modalName = dbfRow.getValueString("MODALNAME");
                        String checkDesc = dbfRow.getValueString("DESC");
                        String zdmKey = zdm;
                        if (!uploadEntityZdmKeyMap.containsKey(zdm)) continue;
                        zdmKey = (String)uploadEntityZdmKeyMap.get(zdm);
                        QueryModalCheckInfo checkInfo = new QueryModalCheckInfo();
                        checkInfo.setEntityCode(zdmKey);
                        checkInfo.setZdm(zdm);
                        checkInfo.setModalTitle(modalName);
                        checkInfo.setInfo(checkDesc);
                        checkInfos.add(checkInfo);
                        continue;
                    }
                    finally {
                        if (!checkDbf.isHasLoadAllRec()) {
                            checkDbf.clearDataRow(dbfRow);
                        }
                    }
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new SingleDataException(e.getMessage(), (Throwable)e);
            }
        }
        return checkInfos;
    }
}

