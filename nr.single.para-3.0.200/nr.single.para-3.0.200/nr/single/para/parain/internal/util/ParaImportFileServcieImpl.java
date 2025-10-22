/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 */
package nr.single.para.parain.internal.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import nr.single.para.compare.internal.util.CompareUtil;
import nr.single.para.file.SingleParaFileAreaConfig;
import nr.single.para.file.SingleParaOSSUtils;
import nr.single.para.parain.util.IParaImportFileServcie;
import org.springframework.beans.factory.annotation.Autowired;

public class ParaImportFileServcieImpl
implements IParaImportFileServcie {
    @Autowired
    private FileService fileService;
    @Autowired
    private SingleParaFileAreaConfig fileConfig;

    @Override
    public String uploadFile(String fileName, byte[] fileData) throws Exception {
        SingleParaOSSUtils.initBucket();
        String SingleParaUploadArea = "SINGLEPARA";
        FileAreaService fileAreaService = this.fileService.area(SingleParaUploadArea);
        long fileSizeByte = fileAreaService.getAreaConfig().getMaxFileSize();
        double fileSizeM = (double)fileSizeByte / 1048576.0;
        FileInfo fileInfo = null;
        if (fileSizeByte < (long)fileData.length) {
            throw new Exception("\u6587\u4ef6\u5927\u5c0f\u5927\u4e8e\u914d\u7f6e\u503c" + fileSizeM + "M!!!");
        }
        fileInfo = fileAreaService.upload(fileName, fileData);
        if (fileInfo != null) {
            return fileInfo.getKey();
        }
        return null;
    }

    @Override
    public String uploadFile(String fileName, String filePath) throws Exception {
        SingleParaOSSUtils.initBucket();
        String SingleParaUploadArea = "SINGLEPARA";
        FileAreaService fileAreaService = this.fileService.area(SingleParaUploadArea);
        long fileSizeByte = fileAreaService.getAreaConfig().getMaxFileSize();
        double fileSizeM = (double)fileSizeByte / 1048576.0;
        FileInfo fileInfo = null;
        try (FileInputStream inputStream = new FileInputStream(SinglePathUtil.normalize((String)filePath));){
            if (fileSizeByte < (long)inputStream.available()) {
                throw new Exception("\u6587\u4ef6\u5927\u5c0f\u5927\u4e8e\u914d\u7f6e\u503c" + fileSizeM + "M!!!");
            }
            fileInfo = fileAreaService.upload(fileName, (InputStream)inputStream);
        }
        if (fileInfo != null) {
            return fileInfo.getKey();
        }
        return null;
    }

    @Override
    public String downFile(String filePath, String fileKey) throws JQException {
        String fileFullPath = null;
        if (StringUtils.isNotEmpty((String)fileKey)) {
            String SingleParaUploadArea = "SINGLEPARA";
            FileAreaService fileAreaService = this.fileService.area(SingleParaUploadArea);
            FileInfo fileInfo = fileAreaService.getInfo(fileKey);
            byte[] data = fileAreaService.download(fileKey);
            fileFullPath = CompareUtil.saveToFile(filePath, fileInfo.getName(), data);
        }
        return fileFullPath;
    }

    @Override
    public String downFile(String fileKey, OutputStream outputStream) {
        String fileName = "";
        if (StringUtils.isNotEmpty((String)fileKey)) {
            String SingleParaUploadArea = "SINGLEPARA";
            FileAreaService fileAreaService = this.fileService.area(SingleParaUploadArea);
            FileInfo fileInfo = fileAreaService.getInfo(fileKey);
            fileAreaService.download(fileKey, outputStream);
            fileName = fileInfo.getName();
        }
        return fileName;
    }

    @Override
    public FileInfo getFileInfo(String fileKey) {
        if (StringUtils.isNotEmpty((String)fileKey)) {
            String SingleParaUploadArea = "SINGLEPARA";
            FileAreaService fileAreaService = this.fileService.area(SingleParaUploadArea);
            return fileAreaService.getInfo(fileKey);
        }
        return null;
    }

    @Override
    public String downFile(String fileName, byte[] fileData, String fileKey) throws JQException {
        byte[] data = fileData;
        if (StringUtils.isNotEmpty((String)fileKey)) {
            String SingleParaUploadArea = "SINGLEPARA";
            FileAreaService fileAreaService = this.fileService.area(SingleParaUploadArea);
            data = fileAreaService.download(fileKey);
        }
        return CompareUtil.saveToFile(fileName, data);
    }

    @Override
    public String downFile(String filePath, String fileName, byte[] fileData, String fileKey) throws JQException {
        byte[] data = fileData;
        if (StringUtils.isNotEmpty((String)fileKey)) {
            String SingleParaUploadArea = "SINGLEPARA";
            FileAreaService fileAreaService = this.fileService.area(SingleParaUploadArea);
            data = fileAreaService.download(fileKey);
        }
        return CompareUtil.saveToFile(filePath, fileName, data);
    }

    @Override
    public void deleteFile(String fileKey) {
        if (StringUtils.isNotEmpty((String)fileKey)) {
            String SingleParaUploadArea = "SINGLEPARA";
            FileAreaService fileAreaService = this.fileService.area(SingleParaUploadArea);
            fileAreaService.delete(fileKey);
        }
    }
}

