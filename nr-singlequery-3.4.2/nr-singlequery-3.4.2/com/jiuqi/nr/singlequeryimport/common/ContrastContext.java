/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.Base64
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.singlequeryimport.common;

import com.jiuqi.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.zip.ZipFile;
import org.springframework.web.multipart.MultipartFile;

public class ContrastContext
implements Serializable {
    private String fileName;
    private String file;
    private String charset;
    private String taskKey;
    private String formSchemeKey;
    private Integer size;

    public ContrastContext(MultipartFile multipartFile) {
        this.setFile(multipartFile);
    }

    public ContrastContext(MultipartFile multipartFile, String taskKey, String formSchemeKey) throws IOException {
        this.setFile(multipartFile);
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.size = this.getZipDocumentSize(multipartFile);
    }

    public byte[] getFile() {
        return Base64.base64ToByteArray((String)this.file);
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFile(MultipartFile file) {
        try {
            this.file = Base64.byteArrayToBase64((byte[])file.getBytes());
            this.fileName = file.getOriginalFilename();
            this.charset = this.codeString();
        }
        catch (IOException e) {
            this.file = null;
            this.fileName = null;
        }
    }

    public String getCharset() {
        return this.charset;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Integer getZipDocumentSize(MultipartFile multipartFile) throws IOException {
        if (null == multipartFile) return 0;
        String originalFilename = multipartFile.getOriginalFilename();
        String[] filename = originalFilename.split("\\.");
        File file = File.createTempFile(filename[0], filename[1] + ".");
        multipartFile.transferTo(file);
        file.deleteOnExit();
        try (ZipFile zipFile = new ZipFile(file);){
            Integer n = zipFile.size();
            return n;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String codeString() {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try (ByteArrayInputStream is = new ByteArrayInputStream(this.getFile());
             ByteArrayInputStream istmp = new ByteArrayInputStream(this.getFile());){
            boolean checked = false;
            int read = ((InputStream)is).read(first3Bytes, 0, 3);
            if (read == -1) {
                String string = charset;
                return string;
            }
            if (first3Bytes[0] == -1 && first3Bytes[1] == -2) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == -2 && first3Bytes[1] == -1) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == -17 && first3Bytes[1] == -69 && first3Bytes[2] == -65) {
                charset = "UTF-8";
                checked = true;
            } else if (first3Bytes[0] == 10 && first3Bytes[1] == 91 && first3Bytes[2] == 48) {
                charset = "UTF-8";
                checked = true;
            } else if (first3Bytes[0] == 13 && first3Bytes[1] == 10 && first3Bytes[2] == 91) {
                charset = "GBK";
                checked = true;
            } else if (first3Bytes[0] == 91 && first3Bytes[1] == 84 && first3Bytes[2] == 73) {
                charset = "windows-1251";
                checked = true;
            }
            if (!checked) {
                while ((read = ((InputStream)istmp).read()) != -1 && read < 240 && (128 > read || read > 191)) {
                    if (192 <= read && read <= 223) {
                        read = ((InputStream)istmp).read();
                        if (128 > read || read > 191) break;
                        continue;
                    }
                    if (224 > read || read > 239) continue;
                    read = ((InputStream)istmp).read();
                    if (128 > read || read > 191 || 128 > (read = ((InputStream)istmp).read()) || read > 191) break;
                    charset = "UTF-8";
                    break;
                }
            }
            ((InputStream)is).close();
            ((InputStream)istmp).close();
            return charset;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }
}

