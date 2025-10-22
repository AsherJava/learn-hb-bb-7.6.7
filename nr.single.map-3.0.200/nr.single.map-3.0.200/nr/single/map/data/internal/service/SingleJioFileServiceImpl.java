/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.single.core.common.InOutDataType
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.file.SingleFile
 *  com.jiuqi.nr.single.core.file.SingleFileConfigInfo
 *  com.jiuqi.nr.single.core.internal.file.SingleFileImpl
 *  com.jiuqi.nr.single.core.util.Ini
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 */
package nr.single.map.data.internal.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.common.InOutDataType;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.file.SingleFile;
import com.jiuqi.nr.single.core.file.SingleFileConfigInfo;
import com.jiuqi.nr.single.core.internal.file.SingleFileImpl;
import com.jiuqi.nr.single.core.util.Ini;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.map.data.PathUtil;
import nr.single.map.data.exception.SingleDataException;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileTaskInfo;
import nr.single.map.data.internal.SingleFileTaskInfoImpl;
import nr.single.map.data.service.SingleJioFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SingleJioFileServiceImpl
implements SingleJioFileService {
    private static final Logger logger = LoggerFactory.getLogger(SingleJioFileServiceImpl.class);

    @Override
    public SingleFileTaskInfo getTaskInfoByJioData(byte[] fileData) throws SingleDataException {
        SingleFileTaskInfo taskInfo = null;
        try {
            String filePath = PathUtil.getExportTempFilePath("JioParaImport");
            String fileName = OrderGenerator.newOrder() + ".jio";
            this.uploadFile(fileData, filePath, fileName);
            String file = filePath + fileName;
            taskInfo = this.getTaskInfoByJioFile(file);
        }
        catch (Exception e) {
            throw new SingleDataException(e.getMessage(), e);
        }
        return taskInfo;
    }

    @Override
    public SingleFileTaskInfo getTaskInfoByJioFile(String file) throws SingleDataException {
        SingleFileTaskInfo taskInfo = new SingleFileTaskInfoImpl();
        SingleFileImpl singleFile = new SingleFileImpl();
        try {
            logger.info("\u89e3\u6790JIO\u6587\u4ef6:" + file + ",\u65f6\u95f4:" + new Date().toString());
            singleFile.infoLoad(file);
            taskInfo = this.getTaskInfoFromSingle((SingleFile)singleFile);
        }
        catch (Exception e) {
            throw new SingleDataException(e.getMessage(), e);
        }
        return taskInfo;
    }

    @Override
    public SingleFileTaskInfo getTaskInfoByJioFile(byte[] fileData) throws SingleDataException {
        SingleFileTaskInfo taskInfo = null;
        SingleFileImpl singleFile = new SingleFileImpl();
        try {
            logger.info("\u89e3\u6790JIO\u4fe1\u606f:" + fileData.length + ",\u65f6\u95f4:" + new Date().toString());
            singleFile.infoLoad(fileData);
            taskInfo = this.getTaskInfoFromSingle((SingleFile)singleFile);
        }
        catch (Exception e) {
            throw new SingleDataException(e.getMessage(), e);
        }
        return taskInfo;
    }

    @Override
    public SingleFileConfigInfo getSingleInfoByJioFile(String file) throws SingleDataException {
        SingleFileConfigInfo sinlgeInfo = new SingleFileConfigInfo();
        SingleFileImpl singleFile = new SingleFileImpl();
        try {
            logger.info("\u89e3\u6790JIO\u6587\u4ef6:" + file + ",\u65f6\u95f4:" + new Date().toString());
            singleFile.infoLoad(file);
            sinlgeInfo = this.getSingleInfoFromSingle((SingleFile)singleFile);
            for (InOutDataType opion : singleFile.getInOutData()) {
                sinlgeInfo.getInOutData().add(opion);
            }
        }
        catch (Exception e) {
            throw new SingleDataException(e.getMessage(), e);
        }
        return sinlgeInfo;
    }

    @Override
    public SingleFileConfigInfo getSingleInfoByTaskDir(String taskDir) throws SingleDataException {
        SingleFileConfigInfo sinlgeInfo = new SingleFileConfigInfo();
        logger.info("\u89e3\u6790JIO\u4efb\u52a1\u76ee\u5f55:" + taskDir + ",\u65f6\u95f4:" + new Date().toString());
        SingleFile singleFile = this.getSingleFileByTaskDir(taskDir);
        sinlgeInfo = this.getSingleInfoFromSingle(singleFile);
        this.loadSingleDataBasesByTaskDir(sinlgeInfo, taskDir);
        for (InOutDataType opion : singleFile.getInOutData()) {
            sinlgeInfo.getInOutData().add(opion);
        }
        return sinlgeInfo;
    }

    @Override
    public SingleFile getSingleFileByTaskDir(String taskDir) throws SingleDataException {
        SingleFileImpl singleFile = new SingleFileImpl();
        try {
            String taskQueryDir;
            String queryMdlFile;
            String taskParaDir;
            String bbbtDbfFile;
            String cCSHSMDbfFile;
            String taskDataDir;
            String fmdmDbfFile;
            String taskSignFile = SinglePathUtil.getNewFilePath((String)taskDir, (String)"TASKSIGN.TSK");
            String fileFlag = "";
            if (SinglePathUtil.getFileExists((String)taskSignFile)) {
                Ini ini = new Ini();
                ini.loadIniFile(taskSignFile);
                String taskCode = ini.readString("General", "Flag", "");
                fileFlag = ini.readString("General", "FileFlag", "");
                String taskYear = ini.readString("General", "Year", "");
                String taskTitle = ini.readString("General", "Name", "");
                String periodType = ini.readString("General", "Period", "");
                String taskTime = ini.readString("General", "Time", "");
                String taskGroup = ini.readString("General", "Group", "");
                String taskVersion = ini.readString("General", "Version", "");
                String taskInputClien = ini.readString("General", "InputClien", "");
                String taskNetPeriodT = ini.readString("General", "NetPeriodT", "");
                singleFile.getInfo().writeString("General", "Flag", taskCode);
                singleFile.getInfo().writeString("General", "FileFlag", fileFlag);
                singleFile.getInfo().writeString("General", "Year", taskYear);
                singleFile.getInfo().writeString("General", "Name", taskTitle);
                singleFile.getInfo().writeString("General", "Period", periodType);
                singleFile.getInfo().writeString("General", "Time", taskTime);
                singleFile.getInfo().writeString("General", "Group", taskGroup);
                singleFile.getInfo().writeString("General", "Version", taskVersion);
                singleFile.getInfo().writeString("General", "InputClien", taskInputClien);
                singleFile.getInfo().writeString("General", "NetPeriodT", taskNetPeriodT);
            }
            if (SinglePathUtil.getFileExists((String)(fmdmDbfFile = SinglePathUtil.getNewFilePath((String)(taskDataDir = SinglePathUtil.getNewPath((String)taskDir, (String)"DATA")), (String)(fileFlag + "FMDM.DBF"))))) {
                singleFile.getInOutData().add(InOutDataType.QYSJ);
            }
            if (SinglePathUtil.getFileExists((String)(cCSHSMDbfFile = SinglePathUtil.getNewFilePath((String)taskDataDir, (String)"Sys_CCSHSM.DBF")))) {
                singleFile.getInOutData().add(InOutDataType.SHSM);
            }
            if (SinglePathUtil.getFileExists((String)(bbbtDbfFile = SinglePathUtil.getNewFilePath((String)(taskParaDir = SinglePathUtil.getNewPath((String)taskDir, (String)"PARA")), (String)"BBBT.DBF")))) {
                singleFile.getInOutData().add(InOutDataType.BBCS);
            }
            if (SinglePathUtil.getFileExists((String)(queryMdlFile = SinglePathUtil.getNewFilePath((String)(taskQueryDir = SinglePathUtil.getNewPath((String)taskDir, (String)"QUERY")), (String)"QueryMdl.Lst")))) {
                singleFile.getInOutData().add(InOutDataType.CXMB);
            }
        }
        catch (IOException e) {
            throw new SingleDataException(e.getMessage(), e);
        }
        catch (SingleFileException e) {
            throw new SingleDataException(e.getMessage(), e);
        }
        return singleFile;
    }

    public void loadSingleDataBasesByTaskDir(SingleFileConfigInfo sinlgeInfo, String taskDir) throws SingleDataException {
        try {
            String taskCfgFile = SinglePathUtil.getNewFilePath((String)taskDir, (String)"TASKCFG.TSK");
            if (SinglePathUtil.getFileExists((String)taskCfgFile)) {
                Ini ini = new Ini();
                ini.loadIniFile(taskCfgFile);
                sinlgeInfo.setSubDataDir(ini.readString("Sys_CurrentSetting", "Sys_CurDataBase", ""));
                List sectionKeys = ini.readSection("Sys_DataBases");
                if (sectionKeys != null) {
                    for (String dataBase : sectionKeys) {
                        sinlgeInfo.getSubDataDirs().add(dataBase);
                    }
                }
            }
        }
        catch (IOException e) {
            throw new SingleDataException(e.getMessage(), e);
        }
        catch (SingleFileException e) {
            throw new SingleDataException(e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Map<String, SingleFileFieldInfo>> getTableFieldMap(List<SingleFileFieldInfo> fields, boolean isSingle) {
        return this.getTableFieldMapEx(fields, isSingle, true);
    }

    @Override
    public Map<String, Map<String, SingleFileFieldInfo>> getTableFieldMapEx(List<SingleFileFieldInfo> fields, boolean isSingle, boolean hasTable) {
        HashMap<String, Map<String, SingleFileFieldInfo>> tableDic = new HashMap<String, Map<String, SingleFileFieldInfo>>();
        for (SingleFileFieldInfo field : fields) {
            boolean isExist;
            boolean bl = isExist = StringUtils.isNotEmpty((String)field.getFieldCode()) && StringUtils.isNotEmpty((String)field.getNetFieldCode());
            if (!isExist) continue;
            Map<String, SingleFileFieldInfo> tableFields = null;
            String tableCode = field.getFormCode();
            String fieldCode = field.getFieldCode();
            String fieldFlag = tableCode + "." + fieldCode;
            if (!isSingle) {
                tableCode = field.getNetFormCode();
                fieldCode = field.getNetFieldCode();
                fieldFlag = field.getNetTableCode() + "." + fieldCode;
            } else if (StringUtils.isEmpty((String)tableCode)) {
                tableCode = field.getTableCode();
                fieldFlag = tableCode + "." + fieldCode;
            }
            if (!hasTable) {
                fieldFlag = fieldCode;
            }
            if (!StringUtils.isNotEmpty((String)tableCode) || !StringUtils.isNotEmpty((String)fieldFlag) || !isExist) continue;
            if (tableDic.containsKey(tableCode)) {
                tableFields = (Map)tableDic.get(tableCode);
            } else {
                tableFields = new HashMap();
                tableDic.put(tableCode, tableFields);
            }
            tableFields.put(fieldFlag, field);
        }
        return tableDic;
    }

    @Override
    public SingleFileTaskInfo getTaskInfoFromSingle(SingleFile singleFile) {
        SingleFileTaskInfoImpl taskInfo = new SingleFileTaskInfoImpl();
        taskInfo.setSingleTaskFlag(singleFile.getInfo().readString("General", "Flag", ""));
        taskInfo.setSingleTaskTitle(singleFile.getInfo().readString("General", "Name", ""));
        taskInfo.setSingleTaskYear(singleFile.getInfo().readString("General", "Year", ""));
        taskInfo.setSingleFileFlag(singleFile.getInfo().readString("General", "FileFlag", ""));
        taskInfo.setSingleTaskPeriod(singleFile.getInfo().readString("General", "Period", ""));
        taskInfo.setSingleTaskTime(singleFile.getInfo().readString("General", "Time", ""));
        taskInfo.setUpdateTime(new Date());
        return taskInfo;
    }

    private SingleFileConfigInfo getSingleInfoFromSingle(SingleFile singleFile) {
        SingleFileConfigInfo singleInfo = new SingleFileConfigInfo();
        singleInfo.setTaskFlag(singleFile.getInfo().readString("General", "Flag", ""));
        singleInfo.setTaskName(singleFile.getInfo().readString("General", "Name", ""));
        singleInfo.setTaskYear(singleFile.getInfo().readString("General", "Year", ""));
        singleInfo.setFileFlag(singleFile.getInfo().readString("General", "FileFlag", ""));
        singleInfo.setTaskPeriod(singleFile.getInfo().readString("General", "Period", ""));
        singleInfo.setTaskTime(singleFile.getInfo().readString("General", "Time", ""));
        return singleInfo;
    }

    private void uploadFile(byte[] file, String filePath, String fileName) throws SingleFileException, FileNotFoundException, IOException {
        File targetFile = new File(SinglePathUtil.normalize((String)filePath));
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try (FileOutputStream out = new FileOutputStream(SinglePathUtil.normalize((String)(filePath + fileName)));){
            out.write(file);
            out.flush();
        }
    }
}

