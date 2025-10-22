/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.service.internal;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.common.InOutDataType;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.file.SingleFile;
import com.jiuqi.nr.single.core.internal.file.SingleFileImpl;
import com.jiuqi.nr.single.core.service.SingleFileHelper;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nr.single.core.util.SingleSecurityUtils;
import com.jiuqi.nr.single.core.util.ZipUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

public class SingleFileHelperImpl
implements SingleFileHelper {
    @Override
    public void splitSingleFile(String sourceFile, String paramFile, String dataFile) throws SingleFileException {
        SingleFileImpl singleFile = new SingleFileImpl();
        try {
            singleFile.infoLoad(sourceFile);
            this.splitSingleFile(singleFile, sourceFile, paramFile, dataFile);
        }
        catch (Exception e) {
            throw new SingleFileException(e.getMessage(), e);
        }
    }

    @Override
    public void splitSingleFile(SingleFile singleFile, String sourceFile, String paramFile, String dataFile) throws SingleFileException {
        String destFile1 = sourceFile + OrderGenerator.newOrder();
        String destFile1Zip = destFile1 + ".zip";
        try {
            singleFile.infoLoad(sourceFile);
            singleFile.unMakeJio(sourceFile, destFile1Zip);
        }
        catch (Exception e1) {
            throw new SingleFileException(e1.getMessage(), e1);
        }
        String path = destFile1 + ".TSK" + File.separatorChar;
        SinglePathUtil.makeDir(path);
        try {
            ZipUtil.unzipFile(path, destFile1Zip, "GBK");
            SinglePathUtil.deleteFile(destFile1Zip);
            singleFile.writeTaskSign(path);
            this.splitSingleFileByPara(singleFile, sourceFile, paramFile, path);
            this.splitSingleFileByData(singleFile, sourceFile, dataFile, path);
        }
        catch (Exception e) {
            throw new SingleFileException(e.getMessage(), e);
        }
        finally {
            SinglePathUtil.deleteDir(path);
        }
    }

    private void splitSingleFileByPara(SingleFile singleFile, String sourceFile, String paramFile, String path) throws FileNotFoundException, IOException, SingleFileException {
        List<InOutDataType> inOutDatas = singleFile.getInOutData();
        if (StringUtils.isNotEmpty((String)paramFile) && (inOutDatas.contains((Object)InOutDataType.BBCS) || inOutDatas.contains((Object)InOutDataType.CXMB) || inOutDatas.contains((Object)InOutDataType.CSCS))) {
            String analDataDir;
            ArrayList<InOutDataType> inOutDatas2 = new ArrayList<InOutDataType>();
            inOutDatas2.addAll(inOutDatas);
            String destFile2 = sourceFile + OrderGenerator.newOrder();
            String destFile2Zip = destFile2 + ".zip";
            String path2 = destFile2 + ".TSK" + File.separatorChar;
            SinglePathUtil.makeDir(path2);
            SinglePathUtil.copyDir(path, path2);
            String dataDir = path2 + "DATA" + File.separatorChar;
            if (SinglePathUtil.getFileExists(dataDir)) {
                SinglePathUtil.deleteDir(dataDir);
            }
            if (SinglePathUtil.getFileExists(analDataDir = path2 + "ANALDATA" + File.separatorChar)) {
                SinglePathUtil.deleteDir(analDataDir);
            }
            String destFile2ZipFile = FilenameUtils.normalize(destFile2Zip);
            SingleSecurityUtils.validatePathManipulation(destFile2ZipFile);
            try (FileOutputStream outStream = new FileOutputStream(destFile2ZipFile);){
                ZipUtil.zipDirectory(path2, outStream, null, "GBK");
            }
            SinglePathUtil.deleteDir(path2);
            if (inOutDatas2.contains((Object)InOutDataType.QYSJ)) {
                inOutDatas2.remove((Object)InOutDataType.QYSJ);
            }
            if (inOutDatas2.contains((Object)InOutDataType.WQHZ)) {
                inOutDatas2.remove((Object)InOutDataType.WQHZ);
            }
            if (inOutDatas2.contains((Object)InOutDataType.SHSM)) {
                inOutDatas2.remove((Object)InOutDataType.SHSM);
            }
            singleFile.setInOutData(inOutDatas2);
            singleFile.makeJio(destFile2Zip, paramFile);
            SinglePathUtil.deleteFile(destFile2Zip);
        }
    }

    private void splitSingleFileByData(SingleFile singleFile, String sourceFile, String dataFile, String path) throws FileNotFoundException, IOException, SingleFileException {
        List<InOutDataType> inOutDatas = singleFile.getInOutData();
        if (StringUtils.isNotEmpty((String)dataFile) && (inOutDatas.contains((Object)InOutDataType.QYSJ) || inOutDatas.contains((Object)InOutDataType.WQHZ) || inOutDatas.contains((Object)InOutDataType.SHSM) || inOutDatas.contains((Object)InOutDataType.CSJG))) {
            String analParaDir;
            String queryDir;
            ArrayList<InOutDataType> inOutDatas2 = new ArrayList<InOutDataType>();
            inOutDatas2.addAll(inOutDatas);
            String destFile2 = sourceFile + OrderGenerator.newOrder();
            String destFile2Zip = destFile2 + ".zip";
            String path2 = destFile2 + ".TSK" + File.separatorChar;
            SinglePathUtil.makeDir(path2);
            SinglePathUtil.copyDir(path, path2);
            String paraDir = path2 + "PARA" + File.separatorChar;
            if (SinglePathUtil.getFileExists(paraDir)) {
                SinglePathUtil.deleteDir(paraDir);
            }
            if (SinglePathUtil.getFileExists(queryDir = path2 + "QUERY" + File.separatorChar)) {
                SinglePathUtil.deleteDir(queryDir);
            }
            if (SinglePathUtil.getFileExists(analParaDir = path2 + "ANALPARA" + File.separatorChar)) {
                SinglePathUtil.deleteDir(analParaDir);
            }
            String destFile2ZipFile = FilenameUtils.normalize(destFile2Zip);
            SingleSecurityUtils.validatePathManipulation(destFile2ZipFile);
            try (FileOutputStream outStream = new FileOutputStream(destFile2ZipFile);){
                ZipUtil.zipDirectory(path2, outStream, null, "GBK");
            }
            SinglePathUtil.deleteDir(path2);
            if (inOutDatas2.contains((Object)InOutDataType.BBCS)) {
                inOutDatas2.remove((Object)InOutDataType.BBCS);
            }
            if (inOutDatas2.contains((Object)InOutDataType.CXMB)) {
                inOutDatas2.remove((Object)InOutDataType.CXMB);
            }
            if (inOutDatas2.contains((Object)InOutDataType.CSCS)) {
                inOutDatas2.remove((Object)InOutDataType.CSCS);
            }
            singleFile.setInOutData(inOutDatas2);
            singleFile.makeJio(destFile2Zip, dataFile);
            SinglePathUtil.deleteFile(destFile2Zip);
        }
    }
}

