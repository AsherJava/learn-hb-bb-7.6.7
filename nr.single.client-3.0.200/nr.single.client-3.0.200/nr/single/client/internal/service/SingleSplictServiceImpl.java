/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.single.core.common.InOutDataType
 *  com.jiuqi.nr.single.core.data.bean.SingleDataFileInfo
 *  com.jiuqi.nr.single.core.data.bean.SingleDataSplictInfo
 *  com.jiuqi.nr.single.core.data.bean.SingleUnitInfo
 *  com.jiuqi.nr.single.core.dbf.DbfTableUtil
 *  com.jiuqi.nr.single.core.dbf.IDbfTable
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.file.SingleFile
 *  com.jiuqi.nr.single.core.internal.file.SingleFileImpl
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  com.jiuqi.nr.single.core.util.ZipUtil
 *  com.jiuqi.nr.single.core.util.datatable.DataColumn
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  nr.single.data.datain.service.ITaskFileReadDataService
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.exception.SingleDataException
 */
package nr.single.client.internal.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.single.core.common.InOutDataType;
import com.jiuqi.nr.single.core.data.bean.SingleDataFileInfo;
import com.jiuqi.nr.single.core.data.bean.SingleDataSplictInfo;
import com.jiuqi.nr.single.core.data.bean.SingleUnitInfo;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.file.SingleFile;
import com.jiuqi.nr.single.core.internal.file.SingleFileImpl;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nr.single.core.util.ZipUtil;
import com.jiuqi.nr.single.core.util.datatable.DataColumn;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nr.single.client.internal.service.SingleFuncExecuteServiceImpl;
import nr.single.client.service.ISingleSplictService;
import nr.single.data.datain.service.ITaskFileReadDataService;
import nr.single.map.data.PathUtil;
import nr.single.map.data.exception.SingleDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleSplictServiceImpl
implements ISingleSplictService {
    private static final Logger log = LoggerFactory.getLogger(SingleFuncExecuteServiceImpl.class);
    @Autowired
    private ITaskFileReadDataService jioReadService;

    @Override
    public void splitSingleFile(String sourceFile, String paramFile, String dataFile, AsyncTaskMonitor asyncMonitor) throws SingleDataException {
        SingleFileImpl singleFile = new SingleFileImpl();
        asyncMonitor.progressAndMessage(0.1, "\u5f00\u59cb\u62c6\u5206\u6587\u4ef6");
        try {
            singleFile.infoLoad(sourceFile);
        }
        catch (SingleFileException e) {
            log.error(e.getMessage(), e);
            throw new SingleDataException(e.getMessage(), (Throwable)e);
        }
        this.splitSingleFile((SingleFile)singleFile, sourceFile, paramFile, dataFile, asyncMonitor, null);
        asyncMonitor.progressAndMessage(1.0, "\u62c6\u5206\u6587\u4ef6\u5b8c\u6210");
    }

    private void splitSingleFile(SingleFile singleFile, String sourceFile, String paramFile, String dataFile, AsyncTaskMonitor asyncMonitor, SingleDataSplictInfo jioSplictInfo) throws SingleDataException {
        try {
            String sourceFile1 = SinglePathUtil.normalize((String)sourceFile);
            File sourceFileObj = new File(sourceFile1);
            String workPath = sourceFileObj.getParent() + File.separator;
            if (jioSplictInfo != null && StringUtils.isNotEmpty((String)jioSplictInfo.getWorkPath())) {
                workPath = jioSplictInfo.getWorkPath();
            }
            String destFile1 = workPath + sourceFileObj.getName() + OrderGenerator.newOrder();
            String destFile1Zip = destFile1 + ".zip";
            asyncMonitor.progressAndMessage(0.2, "\u8bfb\u53d6\u6570\u636e");
            String path = destFile1 + ".TSK" + File.separator;
            SinglePathUtil.makeDir((String)path);
            try {
                singleFile.infoLoad(sourceFile1);
                singleFile.unMakeJio(sourceFile1, destFile1Zip);
                asyncMonitor.progressAndMessage(0.3, "\u89e3\u538b\u6570\u636e");
                ZipUtil.unzipFile((String)path, (String)destFile1Zip, (String)"GBK");
                SinglePathUtil.deleteFile((String)destFile1Zip);
                singleFile.writeTaskSign(path);
                List inOutDatas = singleFile.getInOutData();
                asyncMonitor.progressAndMessage(0.3, "\u62c6\u5206\u53c2\u6570");
                this.splictJioToParam(singleFile, path, paramFile, workPath, inOutDatas, asyncMonitor);
                asyncMonitor.progressAndMessage(0.4, "\u62c6\u5206\u6570\u636e");
                this.splictJioToDataFile(singleFile, path, dataFile, workPath, inOutDatas, asyncMonitor, jioSplictInfo);
                asyncMonitor.progressAndMessage(0.9, "\u62c6\u5206\u6570\u636e\u5b8c\u6210");
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new SingleDataException(e.getMessage(), (Throwable)e);
            }
            finally {
                SinglePathUtil.deleteDir((String)path);
            }
        }
        catch (SingleFileException e1) {
            log.error(e1.getMessage(), e1);
            throw new SingleDataException(e1.getMessage(), (Throwable)e1);
        }
    }

    private void splictJioToDataFile(SingleFile singleFile, String oldTaskPath, String dataFile, String workPath, List<InOutDataType> inOutDatas, AsyncTaskMonitor asyncMonitor, SingleDataSplictInfo jioSplictInfo) throws FileNotFoundException, IOException, SingleFileException, SingleDataException {
        if (StringUtils.isNotEmpty((String)dataFile) && inOutDatas.contains(InOutDataType.QYSJ)) {
            int splictType = 0;
            if (jioSplictInfo != null) {
                splictType = jioSplictInfo.getSplictType();
            }
            if (splictType == 0) {
                this.splictJioToData(singleFile, oldTaskPath, dataFile, workPath, inOutDatas, null, jioSplictInfo);
            } else {
                String fileFlag = singleFile.getInfo().readString("General", "FileFlag", "");
                SingleDataFileInfo unitFileInfo = new SingleDataFileInfo();
                unitFileInfo.setHasData(true);
                unitFileInfo.setHasParam(inOutDatas.contains(InOutDataType.BBCS));
                this.jioReadService.readFMDMUnitInfosByPath(singleFile, unitFileInfo, oldTaskPath, fileFlag, false);
                Map<String, Set<String>> fileZdms = this.getFileZdms(jioSplictInfo, unitFileInfo, splictType);
                File dataFileObj = new File(SinglePathUtil.normalize((String)dataFile));
                String datafileName = dataFileObj.getName();
                String dataFileExt = "";
                int id = datafileName.indexOf(46);
                if (id > 0) {
                    dataFileExt = datafileName.substring(id, datafileName.length());
                    datafileName = datafileName.substring(0, id);
                } else {
                    dataFileExt = ".jio";
                }
                double progress = 0.5 / (double)(fileZdms.size() + 1);
                int pNum = 0;
                for (Map.Entry<String, Set<String>> entry : fileZdms.entrySet()) {
                    asyncMonitor.progressAndMessage(0.4 + progress * (double)(++pNum), "\u62c6\u5206\u6570\u636e");
                    String newDataFile = dataFileObj.getParent() + File.separator + datafileName + "_" + entry.getKey() + dataFileExt;
                    this.splictJioToData(singleFile, oldTaskPath, newDataFile, workPath, inOutDatas, entry.getValue(), jioSplictInfo);
                }
            }
        } else if (inOutDatas.contains(InOutDataType.WQHZ) || inOutDatas.contains(InOutDataType.SHSM) || inOutDatas.contains(InOutDataType.CSJG)) {
            this.splictJioToData(singleFile, oldTaskPath, dataFile, workPath, inOutDatas, null, jioSplictInfo);
        }
    }

    private Map<String, Set<String>> getFileZdms(SingleDataSplictInfo jioSplictInfo, SingleDataFileInfo unitFileInfo, int splictType) {
        List unitList = unitFileInfo.getUnitList();
        HashMap<String, Set<String>> fileZdms = new HashMap<String, Set<String>>();
        if (splictType == 1) {
            HashSet periodDic = new HashSet();
            periodDic.addAll(jioSplictInfo.getPeriodList());
            for (SingleUnitInfo unitInfo : unitList) {
                if (!periodDic.contains(unitInfo.getSinglePeriod())) continue;
                Set<String> periodUnits = null;
                if (fileZdms.containsKey(unitInfo.getSinglePeriod())) {
                    periodUnits = (Set)fileZdms.get(unitInfo.getSinglePeriod());
                } else {
                    periodUnits = new HashSet();
                    fileZdms.put(unitInfo.getSinglePeriod(), periodUnits);
                }
                periodUnits.add(unitInfo.getSingleZdm());
            }
        } else if (splictType == 2) {
            HashSet selectZdms = new HashSet();
            selectZdms.addAll(jioSplictInfo.getUnitList());
            fileZdms.put("\u5df2\u9009" + selectZdms.size() + "\u5bb6", selectZdms);
            HashSet<String> unSelectZdms = new HashSet<String>();
            for (SingleUnitInfo unitInfo : unitList) {
                if (selectZdms.contains(unitInfo.getSingleZdm())) continue;
                unSelectZdms.add(unitInfo.getSingleZdm());
            }
            if (!unSelectZdms.isEmpty()) {
                fileZdms.put("\u672a\u9009" + unSelectZdms.size() + "\u5bb6", unSelectZdms);
            }
        } else if (splictType == 3) {
            this.getFileZdmsByCount(jioSplictInfo, unitFileInfo, splictType, fileZdms);
        }
        return fileZdms;
    }

    private void getFileZdmsByCount(SingleDataSplictInfo jioSplictInfo, SingleDataFileInfo unitFileInfo, int splictType, Map<String, Set<String>> fileZdms) {
        List unitList = unitFileInfo.getUnitList();
        int splictSize = jioSplictInfo.getUnitSize();
        if (splictSize <= 0) {
            splictSize = 1000;
        }
        int fileNum = 1;
        HashSet<String> zdmList = new HashSet<String>();
        fileZdms.put("0" + fileNum, zdmList);
        for (SingleUnitInfo unitInfo : unitList) {
            if (zdmList.size() >= splictSize) {
                zdmList = new HashSet();
                fileZdms.put("0" + ++fileNum, zdmList);
            }
            zdmList.add(unitInfo.getSingleZdm());
        }
    }

    private void splictJioToParam(SingleFile singleFile, String oldTaskPath, String paramFile, String workPath, List<InOutDataType> inOutDatas, AsyncTaskMonitor asyncMonitor) throws FileNotFoundException, IOException, SingleFileException {
        if (StringUtils.isNotEmpty((String)paramFile) && (inOutDatas.contains(InOutDataType.BBCS) || inOutDatas.contains(InOutDataType.CXMB) || inOutDatas.contains(InOutDataType.CSCS))) {
            String analDataDir;
            ArrayList<InOutDataType> inOutDatas2 = new ArrayList<InOutDataType>();
            inOutDatas2.addAll(inOutDatas);
            String destFile2 = workPath + "source" + OrderGenerator.newOrder();
            String destFile2Zip = destFile2 + ".zip";
            String path2 = destFile2 + ".TSK" + File.separator;
            SinglePathUtil.makeDir((String)path2);
            SinglePathUtil.copyDir((String)oldTaskPath, (String)path2);
            String dataDir = path2 + "DATA" + File.separator;
            if (SinglePathUtil.getFileExists((String)dataDir)) {
                SinglePathUtil.deleteDir((String)dataDir);
            }
            if (SinglePathUtil.getFileExists((String)(analDataDir = path2 + "ANALDATA" + File.separator))) {
                SinglePathUtil.deleteDir((String)analDataDir);
            }
            asyncMonitor.progressAndMessage(0.4, "\u538b\u7f29\u53c2\u6570\u6587\u4ef6");
            try (FileOutputStream outStream = new FileOutputStream(SinglePathUtil.normalize((String)destFile2Zip));){
                ZipUtil.zipDirectory((String)path2, (OutputStream)outStream, null, (String)"GBK");
            }
            SinglePathUtil.deleteDir((String)path2);
            this.removeInOutDatas(inOutDatas2, new InOutDataType[]{InOutDataType.QYSJ, InOutDataType.WQHZ, InOutDataType.SHSM});
            asyncMonitor.progressAndMessage(0.5, "\u751f\u6210\u53c2\u6570\u6587\u4ef6");
            singleFile.setInOutData(inOutDatas2);
            singleFile.makeJio(destFile2Zip, paramFile);
            SinglePathUtil.deleteFile((String)destFile2Zip);
        }
    }

    private void splictJioToData(SingleFile singleFile, String oldTaskPath, String dataFile, String workPath, List<InOutDataType> inOutDatas, Set<String> checkZdms, SingleDataSplictInfo jioSplictInfo) throws FileNotFoundException, IOException, SingleFileException, SingleDataException {
        if (StringUtils.isNotEmpty((String)dataFile) && (inOutDatas.contains(InOutDataType.QYSJ) || inOutDatas.contains(InOutDataType.WQHZ) || inOutDatas.contains(InOutDataType.SHSM) || inOutDatas.contains(InOutDataType.CSJG))) {
            String dataDir;
            String analParaDir;
            String queryDir;
            ArrayList<InOutDataType> inOutDatas2 = new ArrayList<InOutDataType>();
            inOutDatas2.addAll(inOutDatas);
            String destFile2 = workPath + "source" + OrderGenerator.newOrder();
            String destFile2Zip = destFile2 + ".zip";
            String path2 = destFile2 + ".TSK" + File.separator;
            SinglePathUtil.makeDir((String)path2);
            SinglePathUtil.copyDir((String)oldTaskPath, (String)path2);
            String paraDir = path2 + "PARA" + File.separator;
            if (SinglePathUtil.getFileExists((String)paraDir)) {
                SinglePathUtil.deleteDir((String)paraDir);
            }
            if (SinglePathUtil.getFileExists((String)(queryDir = path2 + "QUERY" + File.separator))) {
                SinglePathUtil.deleteDir((String)queryDir);
            }
            if (SinglePathUtil.getFileExists((String)(analParaDir = path2 + "ANALPARA" + File.separator))) {
                SinglePathUtil.deleteDir((String)analParaDir);
            }
            if (SinglePathUtil.getFileExists((String)(dataDir = path2 + "DATA" + File.separator)) && checkZdms != null) {
                this.deleteDataByZdms(dataDir, checkZdms);
            }
            if (jioSplictInfo != null && !jioSplictInfo.isSplictAttachment()) {
                String fjDir = path2 + "DATA" + File.separator + "SYS_DOC" + File.separator;
                SinglePathUtil.deleteDir((String)fjDir);
            }
            try (FileOutputStream outStream = new FileOutputStream(SinglePathUtil.normalize((String)destFile2Zip));){
                ZipUtil.zipDirectory((String)path2, (OutputStream)outStream, null, (String)"GBK");
            }
            SinglePathUtil.deleteDir((String)path2);
            this.removeInOutDatas(inOutDatas2, new InOutDataType[]{InOutDataType.BBCS, InOutDataType.CXMB, InOutDataType.CSCS});
            singleFile.setInOutData(inOutDatas2);
            singleFile.makeJio(destFile2Zip, dataFile);
            SinglePathUtil.deleteFile((String)destFile2Zip);
        }
    }

    private void removeInOutDatas(List<InOutDataType> inOutDatas, InOutDataType[] delTypes) {
        for (InOutDataType delType : delTypes) {
            if (!inOutDatas.contains(delType)) continue;
            inOutDatas.remove(delType);
        }
    }

    private void deleteDataByZdms(String dataDir, Set<String> checkZdms) throws SingleDataException, SingleFileException {
        List dbfFiles = PathUtil.getFileList((String)dataDir, (boolean)false, (String)"DBF");
        for (String dbfFile : dbfFiles) {
            this.deleteDataByZdmsInDbf(dataDir, dbfFile, checkZdms);
        }
        this.deleteFjFiles(dataDir, "SYS_DOC", checkZdms);
        this.deleteFjFiles(dataDir, "SYS_IMG", checkZdms);
        this.deleteFjFiles(dataDir, "SYS_RPT", checkZdms);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean deleteDataByZdmsInDbf(String dataDir, String dbfFile, Set<String> checkZdms) throws SingleDataException {
        boolean result = true;
        try (IDbfTable dbf = DbfTableUtil.getDbfTable((String)dbfFile);){
            if (!dbf.getTable().getColumns().isEmpty() && !"SYS_ZDM".equalsIgnoreCase(((DataColumn)dbf.getTable().getColumns().get(0)).getColumnName())) {
                boolean bl = result = false;
                return bl;
            }
            ArrayList<Integer> textFields = new ArrayList<Integer>();
            for (int i = 0; i < dbf.getTable().getColumns().size(); ++i) {
                if (((DataColumn)dbf.getTable().getColumns().get(i)).getDataTypeChar() != 'R') continue;
                textFields.add(i);
            }
            this.deleteDataByZdmInRow(dataDir, textFields, dbf, checkZdms);
            dbf.saveData();
            return result;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SingleDataException(e.getMessage(), (Throwable)e);
        }
    }

    private void deleteDataByZdmInRow(String dataDir, List<Integer> textFields, IDbfTable dbf, Set<String> checkZdms) throws SingleFileException {
        String textFilePath = dataDir + "SYS_TXT" + File.separator;
        for (int i = dbf.getRecordCount() - 1; i >= 0; --i) {
            DataRow dbfRow = dbf.getRecordByIndex(i);
            String zdm = dbfRow.getValueString(0);
            if (StringUtils.isEmpty((String)zdm) || checkZdms.contains(zdm)) continue;
            this.deleteDataWithFjFields(textFields, dbf, dbfRow, textFilePath);
            dbf.deleteRecordByIndex(i);
        }
    }

    private void deleteDataWithFjFields(List<Integer> textFields, IDbfTable dbf, DataRow dbfRow, String textFilePath) throws SingleFileException {
        if (!textFields.isEmpty()) {
            if (!dbf.isHasLoadAllRec()) {
                dbf.loadDataRow(dbfRow);
            }
            for (int fieldIndex : textFields) {
                String fieldFileName = dbfRow.getValueString(fieldIndex);
                if (!StringUtils.isNotEmpty((String)fieldFileName)) continue;
                PathUtil.deleteFile((String)(textFilePath + fieldFileName));
            }
            if (!dbf.isHasLoadAllRec()) {
                dbf.clearDataRow(dbfRow);
            }
        }
    }

    private void deleteFjFiles(String dataDir, String subName, Set<String> checkZdms) throws SingleDataException, SingleFileException {
        String fjPath = dataDir + subName + File.separator;
        File fjPathObj = new File(SinglePathUtil.normalize((String)fjPath));
        if (fjPathObj.exists()) {
            File[] fs;
            for (File f : fs = fjPathObj.listFiles()) {
                if (!f.isDirectory() || checkZdms.contains(f.getName())) continue;
                PathUtil.deleteDir((String)f.getPath());
            }
        }
    }

    @Override
    public void splitSingleFileByOption(SingleDataSplictInfo jioSplictInfo, AsyncTaskMonitor asyncMonitor) throws SingleDataException {
        String sourceFile = jioSplictInfo.getSoureFilePath();
        String paramFile = jioSplictInfo.getDestParamFile();
        String dataFile = jioSplictInfo.getDestDataFile();
        SingleFileImpl singleFile = new SingleFileImpl();
        asyncMonitor.progressAndMessage(0.1, "\u5f00\u59cb\u62c6\u5206\u6587\u4ef6");
        try {
            singleFile.infoLoad(sourceFile);
        }
        catch (SingleFileException e) {
            log.error(e.getMessage(), e);
            throw new SingleDataException(e.getMessage(), (Throwable)e);
        }
        this.splitSingleFile((SingleFile)singleFile, sourceFile, paramFile, dataFile, asyncMonitor, jioSplictInfo);
        asyncMonitor.progressAndMessage(1.0, "\u62c6\u5206\u6587\u4ef6\u5b8c\u6210");
    }
}

