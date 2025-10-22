/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 */
package nr.midstore.core.internal.util.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import nr.midstore.core.common.MidstoreErrorEnum;
import nr.midstore.core.util.IMidstoreFileServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreFileServcieImpl
implements IMidstoreFileServcie {
    @Autowired
    private FileService fileService;

    @Override
    public String uploadFile(String fileName, byte[] fileData) throws Exception {
        String midstoreArea = "MIDSTORE";
        FileAreaService fileAreaService = this.fileService.area(midstoreArea);
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
        String midstoreArea = "MIDSTORE";
        FileAreaService fileAreaService = this.fileService.area(midstoreArea);
        long fileSizeByte = fileAreaService.getAreaConfig().getMaxFileSize();
        double fileSizeM = (double)fileSizeByte / 1048576.0;
        FileInfo fileInfo = null;
        try (FileInputStream inputStream = new FileInputStream(filePath);){
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
    public String uploadFile(String fileName, InputStream inputStream) throws Exception {
        String midstoreArea = "MIDSTORE";
        FileAreaService fileAreaService = this.fileService.area(midstoreArea);
        long fileSizeByte = fileAreaService.getAreaConfig().getMaxFileSize();
        double fileSizeM = (double)fileSizeByte / 1048576.0;
        FileInfo fileInfo = null;
        if (fileSizeByte < (long)inputStream.available()) {
            throw new Exception("\u6587\u4ef6\u5927\u5c0f\u5927\u4e8e\u914d\u7f6e\u503c" + fileSizeM + "M!!!");
        }
        fileInfo = fileAreaService.upload(fileName, inputStream);
        if (fileInfo != null) {
            return fileInfo.getKey();
        }
        return null;
    }

    @Override
    public String downFile(String filePath, String fileKey) throws JQException {
        String fileFullPath = null;
        if (StringUtils.isNotEmpty((String)fileKey)) {
            String midstoreArea = "MIDSTORE";
            FileAreaService fileAreaService = this.fileService.area(midstoreArea);
            FileInfo fileInfo = fileAreaService.getInfo(fileKey);
            byte[] data = fileAreaService.download(fileKey);
            try {
                fileFullPath = this.saveToFile(data, filePath, fileInfo.getName());
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)MidstoreErrorEnum.MIDSTORE_EXCEPTION_000, (Throwable)e);
            }
        }
        return fileFullPath;
    }

    @Override
    public String downFile(String fileKey, OutputStream outputStream) {
        String fileName = "";
        if (StringUtils.isNotEmpty((String)fileKey)) {
            String midstoreArea = "MIDSTORE";
            FileAreaService fileAreaService = this.fileService.area(midstoreArea);
            FileInfo fileInfo = fileAreaService.getInfo(fileKey);
            fileAreaService.download(fileKey, outputStream);
            fileName = fileInfo.getName();
        }
        return fileName;
    }

    @Override
    public FileInfo getFileInfo(String fileKey) {
        if (StringUtils.isNotEmpty((String)fileKey)) {
            String midstoreArea = "MIDSTORE";
            FileAreaService fileAreaService = this.fileService.area(midstoreArea);
            return fileAreaService.getInfo(fileKey);
        }
        return null;
    }

    @Override
    public void deleteFile(String fileKey) {
        if (StringUtils.isNotEmpty((String)fileKey)) {
            String midstoreArea = "MIDSTORE";
            FileAreaService fileAreaService = this.fileService.area(midstoreArea);
            fileAreaService.delete(fileKey);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String saveToFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String fileAllName = filePath + fileName;
        try (FileOutputStream out = new FileOutputStream(fileAllName);){
            out.write(file);
            out.flush();
        }
        return fileAllName;
    }
}

