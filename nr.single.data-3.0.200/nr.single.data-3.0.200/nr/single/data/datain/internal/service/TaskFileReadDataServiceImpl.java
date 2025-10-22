/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.single.core.common.InOutDataType
 *  com.jiuqi.nr.single.core.data.bean.SingleDataFileInfo
 *  com.jiuqi.nr.single.core.data.bean.SingleUnitInfo
 *  com.jiuqi.nr.single.core.dbf.DbfTableUtil
 *  com.jiuqi.nr.single.core.dbf.IDbfTable
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.file.SingleFile
 *  com.jiuqi.nr.single.core.para.parser.JIOParamParser
 *  com.jiuqi.nr.single.core.para.parser.table.FMRepInfo
 *  com.jiuqi.nr.single.core.service.SingleFileParserService
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.exception.SingleDataException
 */
package nr.single.data.datain.internal.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.common.InOutDataType;
import com.jiuqi.nr.single.core.data.bean.SingleDataFileInfo;
import com.jiuqi.nr.single.core.data.bean.SingleUnitInfo;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.file.SingleFile;
import com.jiuqi.nr.single.core.para.parser.JIOParamParser;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.service.SingleFileParserService;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nr.single.data.datain.service.ITaskFileImportDataService;
import nr.single.data.datain.service.ITaskFileReadDataService;
import nr.single.map.data.PathUtil;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.exception.SingleDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskFileReadDataServiceImpl
implements ITaskFileReadDataService {
    private static final Logger logger = LoggerFactory.getLogger(TaskFileReadDataServiceImpl.class);
    @Autowired
    private ITaskFileImportDataService jioImportService;
    @Autowired
    private SingleFileParserService singleParserService;

    @Override
    public void readFMDMunits(String path, String fileName, List<String> zdmList) throws SingleDataException {
        String jioFile = path + fileName;
        this.readFMDMunitsByOther(path, jioFile, zdmList);
    }

    @Override
    public void readFMDMunitsByOther(String path, String sourcefilePath, List<String> zdmList) throws SingleDataException {
        SingleDataFileInfo dataInfo = this.getFMDMUnitsByInfo(path, sourcefilePath, true);
        List list = dataInfo.getUnitList();
        zdmList.clear();
        if (!list.isEmpty()) {
            for (SingleUnitInfo unit : list) {
                zdmList.add(unit.getSingleZdm());
            }
        }
    }

    @Override
    public SingleDataFileInfo getFMDMUnits(String path, String sourcefilePath) throws SingleDataException {
        return this.getFMDMUnitsByInfo(path, sourcefilePath, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SingleDataFileInfo getFMDMUnitsByInfo(String path, String sourcefilePath, boolean onlyZdm) throws SingleDataException {
        SingleDataFileInfo result = new SingleDataFileInfo();
        TaskDataContext importContext = new TaskDataContext();
        try {
            String workPath = PathUtil.createNewPath((String)path, (String)"JIOIMPORT");
            String taskFilePath = PathUtil.createNewPath((String)workPath, (String)(OrderGenerator.newOrder() + ".TSK"));
            try {
                logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a\u89e3\u538bJIO\u6587\u4ef6,\u65f6\u95f4:" + new Date().toString());
                SingleFile singleFile = this.jioImportService.exactSingleFileToPath2(importContext, taskFilePath, sourcefilePath, false, true);
                result.setHasParam(singleFile.getInOutData().contains(InOutDataType.BBCS));
                result.setHasData(singleFile.getInOutData().contains(InOutDataType.QYSJ));
                result.setTaskFlag(importContext.getSingleTaskFlag());
                result.setTaskTitle(importContext.getSingleTaskTitle());
                result.setTaskFlag(importContext.getSingleTaskFlag());
                result.setTaskYear(importContext.getSingleTaskYear());
                result.setPeriodType(importContext.getSinglePeriodType());
                if (!result.isHasData()) {
                    SingleDataFileInfo singleDataFileInfo = result;
                    return singleDataFileInfo;
                }
                this.readFMDMUnitInfosByPath(singleFile, result, taskFilePath, importContext.getSingleFileFlag(), onlyZdm);
                return result;
            }
            finally {
                PathUtil.deleteDir((String)taskFilePath);
                PathUtil.deleteDir((String)workPath);
            }
        }
        catch (SingleFileException e) {
            logger.error(e.getMessage(), e);
            throw new SingleDataException(e.getMessage(), (Throwable)e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void readFMDMUnitInfosByPath(SingleFile singleFile, SingleDataFileInfo unitFileInfo, String taskFilePath, String fileFlag, boolean onlyZdm) throws SingleDataException {
        JIOParamParser jioParaser = null;
        if (!onlyZdm && unitFileInfo.isHasParam()) {
            try {
                singleFile.writeTaskSign(taskFilePath);
                jioParaser = this.singleParserService.getParaParaserByTaskDir(taskFilePath, singleFile);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new SingleDataException(e.getMessage(), (Throwable)e);
            }
        }
        try {
            String dataPath = PathUtil.createNewPath((String)taskFilePath, (String)"DATA");
            String dbfFileName = dataPath + fileFlag + "FMDM.DBF";
            try (IDbfTable dbf = DbfTableUtil.getDbfTable((String)dbfFileName);){
                List unitList = unitFileInfo.getUnitList();
                HashSet<String> periodList = new HashSet<String>();
                for (int i = 0; i < dbf.getRecordCount(); ++i) {
                    DataRow dbfRow = dbf.getRecordByIndex(i);
                    String zdm = dbfRow.getValueString(0);
                    if (StringUtils.isEmpty((String)zdm)) continue;
                    SingleUnitInfo unit = new SingleUnitInfo();
                    unit.setSingleZdm(zdm);
                    unitList.add(unit);
                    this.LoatUnitInfo(jioParaser, dbf, dbfRow, unit, onlyZdm, periodList);
                }
                unitFileInfo.getPeriodCodes().addAll(periodList);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SingleDataException(e.getMessage(), (Throwable)e);
        }
    }

    private void LoatUnitInfo(JIOParamParser jioParaser, IDbfTable dbf, DataRow dbfRow, SingleUnitInfo unit, boolean onlyZdm, Set<String> periodList) {
        if (!onlyZdm) {
            if (!dbf.isHasLoadAllRec()) {
                dbf.loadDataRow(dbfRow);
            }
            String fjd = dbfRow.getValueString("SYS_FJD");
            unit.setSingleParent(fjd);
            if (jioParaser != null) {
                FMRepInfo singleFmdm = jioParaser.getParaInfo().getFmRepInfo();
                unit.setSingleBBLX(dbfRow.getValueString(singleFmdm.getBBLXField()));
                unit.setSingleTitle(dbfRow.getValueString(singleFmdm.getDWMCFieldName()));
                unit.setSingleQYDM(dbfRow.getValueString(singleFmdm.getDWDMFieldName()));
                if (singleFmdm.getSQFieldId() > 0) {
                    unit.setSinglePeriod(dbfRow.getValueString(singleFmdm.getPeriodField()));
                }
            } else {
                unit.setSingleBBLX(dbfRow.getValueString("BBLX"));
                unit.setSingleTitle(dbfRow.getValueString("QYMC"));
                if (StringUtils.isEmpty((String)unit.getSingleTitle())) {
                    unit.setSingleTitle(dbfRow.getValueString("DWMC"));
                }
                unit.setSingleQYDM(dbfRow.getValueString("QYDM"));
                if (StringUtils.isEmpty((String)unit.getSingleTitle())) {
                    unit.setSingleQYDM(dbfRow.getValueString("DWDM"));
                }
                unit.setSinglePeriod(dbfRow.getValueString("YF"));
                if (StringUtils.isEmpty((String)unit.getSinglePeriod())) {
                    unit.setSinglePeriod(dbfRow.getValueString("SQ"));
                }
            }
            if (StringUtils.isNotEmpty((String)unit.getSinglePeriod()) && !periodList.contains(unit.getSinglePeriod())) {
                periodList.add(unit.getSinglePeriod());
            }
            if (!dbf.isHasLoadAllRec()) {
                dbf.clearDataRow(dbfRow);
            }
        }
    }
}

