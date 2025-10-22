/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.single.core.data.SingleQueryCheckUtil
 *  com.jiuqi.nr.single.core.dbf.DbfTableUtil
 *  com.jiuqi.nr.single.core.dbf.IDbfTable
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  nr.single.map.data.DataEntityInfo
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.exception.SingleDataException
 */
package nr.single.client.internal.service.querycheck;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.single.core.data.SingleQueryCheckUtil;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nr.single.client.service.querycheck.ISingleExportQueryCheckService;
import nr.single.client.service.querycheck.bean.QueryModalCheckInfo;
import nr.single.client.service.querycheck.bean.QueryModalCheckParam;
import nr.single.client.service.querycheck.extend.ISingleQueryCheckReadService;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.exception.SingleDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleExportQueryCheckServiceImpl
implements ISingleExportQueryCheckService {
    private static final Logger logger = LoggerFactory.getLogger(SingleExportQueryCheckServiceImpl.class);
    @Autowired(required=false)
    private ISingleQueryCheckReadService readQueryCheckSerice;

    @Override
    public void exportQueryCheckResult(TaskDataContext context, String taskDataPath, Map<String, DimensionValue> dimensionSet, AsyncTaskMonitor asyncTaskMonitor) throws SingleDataException {
        if (this.readQueryCheckSerice == null) {
            return;
        }
        logger.info("\u67e5\u8be2\u6a21\u7248\u5ba1\u6838\u5bfc\u51fa\uff1a");
        List<QueryModalCheckInfo> list = this.getQueryCheckInfo(context, dimensionSet);
        try {
            if (list != null && !list.isEmpty()) {
                context.setHasQueryCheck(true);
                SingleQueryCheckUtil.createCheckDBFFile((boolean)true, (String)taskDataPath, (int)context.getMapingCache().getZdmLength());
                String checkFile = taskDataPath + "JQModalCheckDesc.DBF";
                this.saveQueryCheckInfos(context, taskDataPath, checkFile, list);
            }
            logger.info("\u67e5\u8be2\u6a21\u7248\u5ba1\u6838\u5bfc\u51fa\uff1a\u8bb0\u5f55\u6570=" + list.size());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SingleDataException(e.getMessage(), (Throwable)e);
        }
    }

    private List<QueryModalCheckInfo> getQueryCheckInfo(TaskDataContext context, Map<String, DimensionValue> dimensionSet) throws SingleDataException {
        ArrayList<QueryModalCheckInfo> list = new ArrayList<QueryModalCheckInfo>();
        if (this.readQueryCheckSerice != null) {
            QueryModalCheckParam param = new QueryModalCheckParam();
            List<QueryModalCheckInfo> list2 = this.readQueryCheckSerice.getQueryCheckResult(context.getFormSchemeKey(), dimensionSet, param);
            if (list2 != null && !list2.isEmpty()) {
                list.addAll(list2);
            }
        }
        return list;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void saveQueryCheckInfos(TaskDataContext context, String taskDataPath, String checkFile, List<QueryModalCheckInfo> checkInfos) throws SingleDataException, SingleFileException {
        if (SinglePathUtil.getFileExists((String)checkFile)) {
            logger.info("\u5bfc\u51faJIO\u6570\u636e\uff1a\u5bfc\u51fa\u67e5\u8be2\u6a21\u7248\u5ba1\u6838\u8bf4\u660e");
            try (IDbfTable checkDbf = DbfTableUtil.getDbfTable((String)checkFile);){
                for (int i = 0; i < checkInfos.size(); ++i) {
                    QueryModalCheckInfo checkInfo = checkInfos.get(i);
                    DataRow dbfRow = checkDbf.getTable().newRow();
                    String zdmKey = checkInfo.getEntityCode();
                    String zdm = checkInfo.getZdm();
                    DataEntityInfo entity = context.getEntityCache().findEntityByCode(zdmKey);
                    if (null == entity) {
                        entity = context.getEntityCache().findEntityByKey(zdmKey);
                    }
                    if (context.getEntityKeyZdmMap().containsKey(zdmKey)) {
                        zdm = (String)context.getEntityKeyZdmMap().get(zdmKey);
                    } else if (null != entity) {
                        zdm = entity.getSingleZdm();
                    }
                    dbfRow.setValue("ZDM", (Object)zdm);
                    dbfRow.setValue("MODALNAME", (Object)checkInfo.getModalTitle());
                    dbfRow.setValue("DESC", (Object)checkInfo.getInfo());
                    checkDbf.getTable().getRows().add((Object)dbfRow);
                }
                int rowCount = checkDbf.getDataRealRowCount();
                checkDbf.saveData();
                SingleQueryCheckUtil.createCheckInexByFile((boolean)true, (String)checkFile, (int)context.getMapingCache().getZdmLength(), (int)rowCount, (long)checkDbf.getFileSize());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new SingleDataException(e.getMessage(), (Throwable)e);
            }
        }
    }
}

