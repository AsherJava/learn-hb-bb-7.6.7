/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.internal.file;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.common.InOutDataType;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.file.SingleFile;
import com.jiuqi.nr.single.core.file.SingleFileDataCrypt;
import com.jiuqi.nr.single.core.file.SingleFileHead;
import com.jiuqi.nr.single.core.file.SingleFileInfoContain;
import com.jiuqi.nr.single.core.util.BitConverter;
import com.jiuqi.nr.single.core.util.Ini;
import com.jiuqi.nr.single.core.util.ReadUtil;
import com.jiuqi.nr.single.core.util.Section;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleFileImpl
implements SingleFile {
    private static final Logger logger = LoggerFactory.getLogger(SingleFileImpl.class);
    private static String EncodingCode = "GB2312";
    private SingleFileInfoContain info = new SingleFileInfoContain();
    private SingleFileHead head = new SingleFileHead();
    private String sourceFile;
    private String destFile;
    private String usageSign;
    private String jobName;
    private boolean havePW;
    private String pW;
    private boolean canceled;
    private List<InOutDataType> inOutData;

    @Override
    public SingleFileInfoContain getInfo() {
        return this.info;
    }

    @Override
    public SingleFileHead getHead() {
        return this.head;
    }

    @Override
    public String getSourceFile() {
        return this.sourceFile;
    }

    @Override
    public void setSourceFile(String value) {
        this.sourceFile = value;
    }

    @Override
    public String getDestFile() {
        return this.destFile;
    }

    @Override
    public void setDestFile(String value) {
        this.destFile = value;
    }

    @Override
    public String getUsageSign() {
        return this.usageSign;
    }

    @Override
    public void setUsageSign(String value) {
        this.usageSign = value;
    }

    @Override
    public String getJobName() {
        return this.jobName;
    }

    @Override
    public void setJobName(String value) {
        this.jobName = value;
    }

    @Override
    public boolean getHavePW() {
        return this.havePW;
    }

    @Override
    public void setHavePW(boolean value) {
        this.havePW = value;
    }

    @Override
    public String getPW() {
        return this.pW;
    }

    @Override
    public void setPW(String value) {
        this.pW = value;
    }

    @Override
    public boolean getCanceled() {
        return this.canceled;
    }

    @Override
    public void setCanceled(boolean value) {
        this.canceled = value;
    }

    public SingleFileImpl() {
        this.head.setHaveNext(false);
    }

    @Override
    public void makeJio(String zipFile, String destFile) {
        try {
            String zipFile1 = SinglePathUtil.normalize(zipFile);
            File file = new File(zipFile1);
            try (FileInputStream soureStream = new FileInputStream(zipFile1);
                 FileOutputStream destStream = new FileOutputStream(SinglePathUtil.normalize(destFile));){
                this.makeJio2(soureStream, destStream, file.length());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void makeJio(FileInputStream soureStream, FileOutputStream destStream) throws SingleFileException {
        try {
            this.info.writeString("Version", "Major", "1");
            byte[] infodatas = this.info.getCombineText().getBytes(EncodingCode);
            this.setNewHead(soureStream.available(), infodatas.length);
            this.writeHead(destStream);
            destStream.write(infodatas);
            byte[] Buffer2 = new byte[soureStream.available()];
            soureStream.read(Buffer2, 0, soureStream.available());
            SingleFileDataCrypt.encodeBuffer(Buffer2, 0);
            destStream.write(Buffer2, 0, Buffer2.length);
        }
        catch (Exception e) {
            throw new SingleFileException(e.getMessage(), e);
        }
    }

    private void makeJio2(FileInputStream soureStream, FileOutputStream destStream, long fileSize) throws SingleFileException {
        this.info.writeString("Version", "Major", "1");
        try {
            byte[] infodatas = this.info.getCombineText().getBytes(EncodingCode);
            this.setNewHead(fileSize, infodatas.length);
            this.writeHead(destStream);
            destStream.write(infodatas);
            SingleFileDataCrypt.encodeStream(soureStream, destStream);
        }
        catch (Exception e) {
            throw new SingleFileException(e.getMessage(), e);
        }
    }

    private void setNewHead(long fileSize, int infoLen) throws UnsupportedEncodingException {
        this.head.setHeadSize((short)86);
        this.head.setVersion((short)1);
        this.head.setUsageSign(this.getUsageSign() != null ? this.getUsageSign() : "");
        this.head.setFileSize(fileSize);
        this.head.setJobName(this.jobName != null ? this.jobName : "");
        this.head.setJobID(this.getJobID());
        this.head.setJobTime(new Date().getTime());
        if (this.havePW) {
            this.head.setHavePW((byte)1);
        } else {
            this.head.setHavePW((byte)0);
        }
        this.head.setPW(this.pW);
        this.head.setInfoSize(infoLen);
        this.head.setJobType((byte)0);
        this.head.setDiskNo(0);
        this.head.setHaveNext(false);
    }

    @Override
    public void unMakeJio(String jioFile, String zipFile) throws SingleFileException {
        this.sourceFile = jioFile;
        try (FileInputStream soureStream = new FileInputStream(SinglePathUtil.normalize(jioFile));
             FileOutputStream destStream = new FileOutputStream(SinglePathUtil.normalize(zipFile));){
            this.unMakeJio2(soureStream, destStream);
        }
        catch (IOException e) {
            throw new SingleFileException(e.getMessage(), e);
        }
    }

    private void unMakeJio2(FileInputStream soureStream, FileOutputStream destStream) throws SingleFileException {
        this.readHead(soureStream);
        try {
            ReadUtil.skipStream(soureStream, this.head.getInfoSize());
            int version = this.getJioVersion();
            SingleFileDataCrypt.decodeStream(soureStream, destStream, version);
        }
        catch (IOException e) {
            throw new SingleFileException(e.getMessage(), e);
        }
    }

    private int getJioVersion() {
        String code;
        int version = 1;
        if (this.getInfo() != null && StringUtils.isNotEmpty((String)(code = this.getInfo().readString("Version", "Major", "1")))) {
            version = Integer.parseInt(code);
        }
        return version;
    }

    @Override
    public void unMakeJio(FileInputStream soureStream, FileOutputStream destStream) throws SingleFileException {
        try {
            this.readHead(soureStream);
            ReadUtil.skipStream(soureStream, this.head.getInfoSize());
            int FileFree = soureStream.available();
            byte[] Bu = new byte[FileFree];
            soureStream.read(Bu, 0, FileFree);
            SingleFileDataCrypt.decodeBuffer(Bu, 0);
            destStream.write(Bu, 0, Bu.length);
            Bu = null;
        }
        catch (IOException e) {
            throw new SingleFileException(e.getMessage(), e);
        }
    }

    @Override
    public void readHead(FileInputStream stream) throws SingleFileException {
        try {
            this.head.setHeadSize((short)ReadUtil.readSmallIntValue(stream));
            this.head.setVersion((short)ReadUtil.readSmallIntValue(stream));
            this.head.setUsageSign(ReadUtil.readStringValue(stream, 9));
            this.head.setFileSize(ReadUtil.readLongValue2(stream));
            this.head.setInfoSize(ReadUtil.readIntValue(stream));
            this.head.setJobName(ReadUtil.readStringValue(stream, 31));
            this.head.setJobID(ReadUtil.readLongValue2(stream));
            this.head.setJobTime(ReadUtil.readLongValue2(stream));
            this.head.setJobType(ReadUtil.readByteValue(stream));
            this.head.setDiskNo(ReadUtil.readIntValue(stream));
            this.head.setHaveNext(ReadUtil.readBoolValue(stream));
            this.head.setHavePW(ReadUtil.readByteValue(stream));
            this.head.setPW(ReadUtil.readStringValue(stream, 7));
        }
        catch (IOException e) {
            throw new SingleFileException(e.getMessage(), e);
        }
    }

    @Override
    public void writeHead(FileOutputStream binaryWriter) throws SingleFileException {
        try {
            binaryWriter.write(BitConverter.getBytes(this.head.getHeadSize()));
            binaryWriter.write(BitConverter.getBytes(this.head.getVersion()));
            binaryWriter.write(this.getStringData(this.head.getUsageSign(), 9));
            binaryWriter.write(BitConverter.getBytes2(this.head.getFileSize()));
            binaryWriter.write(BitConverter.getBytes(this.head.getInfoSize()));
            binaryWriter.write(this.getStringData(this.head.getJobName(), 31));
            binaryWriter.write(BitConverter.getBytes2(this.head.getJobID()));
            binaryWriter.write(BitConverter.getBytes2((long)this.head.getJobTime()));
            binaryWriter.write(this.head.getJobType());
            binaryWriter.write(BitConverter.getBytes(this.head.getDiskNo()));
            binaryWriter.write(this.head.getHaveNext() ? 1 : 0);
            binaryWriter.write(this.head.getHavePW());
            binaryWriter.write(this.getStringData(this.head.getPW(), 7));
        }
        catch (IOException e) {
            throw new SingleFileException(e.getMessage(), e);
        }
    }

    @Override
    public long getJobID() {
        SecureRandom rn = new SecureRandom();
        return (long)(rn.nextInt(10000) + rn.nextInt(10000)) + 2L;
    }

    @Override
    public byte[] getStringData(String code, int len) throws SingleFileException {
        byte[] temp = new byte[len];
        if (StringUtils.isNotEmpty((String)code)) {
            byte[] temp2;
            try {
                temp2 = code.getBytes(EncodingCode);
            }
            catch (UnsupportedEncodingException e) {
                throw new SingleFileException(e.getMessage(), e);
            }
            System.arraycopy(temp2, 0, temp, 0, temp2.length);
        }
        return temp;
    }

    @Override
    public void infoLoad(FileInputStream s) throws SingleFileException {
        try {
            this.info.clearCombineText();
            s.skip(0L);
            SingleFileHead head = new SingleFileHead();
            this.readHead(s, head);
            if (head.getHeadSize() != 86 && head.getJobType() != 0 && head.getJobType() != 1) {
                return;
            }
            String str = ReadUtil.readStringValue(s, head.getInfoSize());
            Ini i = new Ini();
            i.loadIniContent(str);
            List<Section> secList = i.readSections();
            for (Section sec : secList) {
                List<String> identList = i.readSection(sec.getName());
                HashMap<String, String> identdic = new HashMap<String, String>();
                for (String ident : identList) {
                    if (StringUtils.isEmpty((String)ident)) continue;
                    identdic.put(ident, i.readString(sec.getName(), ident, ""));
                }
                this.info.getInfoList().put(sec.getName(), identdic);
                if (!"Data".equalsIgnoreCase(sec.getName())) continue;
                this.loadInOutType(identdic);
            }
        }
        catch (IOException e) {
            throw new SingleFileException(e.getMessage(), e);
        }
    }

    @Override
    public void infoLoad(String fileName) throws SingleFileException {
        this.sourceFile = fileName;
        this.info.clearCombineText();
        try (FileInputStream s = new FileInputStream(SinglePathUtil.normalize(this.sourceFile));){
            s.skip(0L);
            SingleFileHead head = new SingleFileHead();
            this.readHead(s, head);
            if (head.getHeadSize() != 86 && head.getJobType() != 0 && head.getJobType() != 1) {
                return;
            }
            byte[] b1 = new byte[head.getInfoSize()];
            s.read(b1, 0, head.getInfoSize());
            String str = new String(b1, 0, b1.length, EncodingCode);
            Ini i = new Ini();
            i.loadIniContent(str);
            List<Section> secList = i.readSections();
            for (Section sec : secList) {
                List<String> identList = i.readSection(sec.getName());
                HashMap<String, String> identdic = new HashMap<String, String>();
                for (String ident : identList) {
                    if (StringUtils.isEmpty((String)ident)) continue;
                    identdic.put(ident, i.readString(sec.getName(), ident, ""));
                }
                this.info.getInfoList().put(sec.getName(), identdic);
                if (!"Data".equalsIgnoreCase(sec.getName())) continue;
                this.loadInOutType(identdic);
            }
            b1 = null;
        }
        catch (IOException e) {
            throw new SingleFileException(e.getMessage(), e);
        }
    }

    @Override
    public void infoLoad(byte[] fileData) throws SingleFileException {
        MemStream stream;
        this.info.clearCombineText();
        MemStream s = stream = new MemStream();
        try {
            stream.write(fileData, 0, fileData.length);
            s.seek(0L, 0);
            SingleFileHead head = new SingleFileHead();
            this.readHead(s, head);
            if (head.getHeadSize() != 86 && head.getJobType() != 0 && head.getJobType() != 1) {
                return;
            }
            byte[] b1 = new byte[head.getInfoSize()];
            s.read(b1, 0, head.getInfoSize());
            String str = new String(b1, 0, b1.length, EncodingCode);
            Ini i = new Ini();
            i.loadIniContent(str);
            List<Section> secList = i.readSections();
            for (Section sec : secList) {
                List<String> identList = i.readSection(sec.getName());
                HashMap<String, String> identdic = new HashMap<String, String>();
                for (String ident : identList) {
                    if (StringUtils.isEmpty((String)ident)) continue;
                    identdic.put(ident, i.readString(sec.getName(), ident, ""));
                }
                this.info.getInfoList().put(sec.getName(), identdic);
                if (!"Data".equalsIgnoreCase(sec.getName())) continue;
                this.loadInOutType(identdic);
            }
            b1 = null;
        }
        catch (StreamException | IOException e) {
            throw new SingleFileException(e.getMessage(), e);
        }
        finally {
            s = null;
        }
    }

    @Override
    public void writeTaskSign(String fileDir) throws SingleFileException {
        try {
            Map<String, Object> infos = this.info.getInfoList();
            Ini i = new Ini();
            i.loadIniContent(this.info.getCombineText());
            i.saveToFile(fileDir + File.separator + "TASKSIGN.TSK", "General");
        }
        catch (IOException e) {
            throw new SingleFileException(e.getMessage(), e);
        }
    }

    @Override
    public void readHead(FileInputStream s, SingleFileHead head) throws SingleFileException {
        try {
            int offset = 0;
            byte[] b = new byte[86];
            s.read(b, 0, 86);
            head.setHeadSize(BitConverter.toInt16(b, offset));
            head.setVersion(BitConverter.toInt16(b, offset += 2));
            String res = new String(b, offset += 2, 9, EncodingCode);
            head.setUsageSign(res);
            head.setFileSize(BitConverter.toInt32(b, offset += 9));
            head.setInfoSize(BitConverter.toInt32(b, offset += 8));
            head.setJobName(new String(b, offset += 4, 31, EncodingCode));
            head.setJobID(BitConverter.toInt32(b, offset += 31));
            head.setJobTime(BitConverter.toInt64(b, offset += 8));
            head.setJobType(b[offset += 8]);
            head.setDiskNo(BitConverter.toInt32(b, ++offset));
            head.setHaveNext(BitConverter.toBoolean(b, offset += 4));
            head.setHavePW(b[++offset]);
            head.setPW(new String(b, ++offset, 7, EncodingCode));
        }
        catch (IOException e) {
            throw new SingleFileException(e.getMessage(), e);
        }
    }

    @Override
    public void readHead(MemStream s, SingleFileHead head) throws SingleFileException {
        try {
            int offset = 0;
            byte[] b = new byte[86];
            s.read(b, 0, 86);
            head.setHeadSize(BitConverter.toInt16(b, offset));
            head.setVersion(BitConverter.toInt16(b, offset += 2));
            String res = new String(b, offset += 2, 9, EncodingCode);
            head.setUsageSign(res);
            head.setFileSize(BitConverter.toInt32(b, offset += 9));
            head.setInfoSize(BitConverter.toInt32(b, offset += 8));
            head.setJobName(new String(b, offset += 4, 31, EncodingCode));
            head.setJobID(BitConverter.toInt32(b, offset += 31));
            head.setJobTime(BitConverter.toInt64(b, offset += 8));
            head.setJobType(b[offset += 8]);
            head.setDiskNo(BitConverter.toInt32(b, ++offset));
            head.setHaveNext(BitConverter.toBoolean(b, offset += 4));
            head.setHavePW(b[++offset]);
            head.setPW(new String(b, ++offset, 7, EncodingCode));
        }
        catch (StreamException | IOException e) {
            throw new SingleFileException(e.getMessage(), e);
        }
    }

    @Override
    public List<InOutDataType> getInOutData() {
        if (this.inOutData == null) {
            this.inOutData = new ArrayList<InOutDataType>();
        }
        return this.inOutData;
    }

    @Override
    public void setInOutData(List<InOutDataType> inOutData) {
        this.inOutData = inOutData;
        for (String sec : this.info.getInfoList().keySet()) {
            if (!"Data".equalsIgnoreCase(sec)) continue;
            HashMap<String, String> identdic = new HashMap<String, String>();
            this.saveInOutType(identdic, inOutData);
            this.info.getInfoList().put(sec, identdic);
            break;
        }
    }

    private void saveInOutType(Map<String, String> identdic, List<InOutDataType> inOutData) {
        if (inOutData.contains((Object)InOutDataType.QYSJ)) {
            identdic.put("QYSJ", "1");
        }
        if (inOutData.contains((Object)InOutDataType.BBCS)) {
            identdic.put("BBCS", "1");
        }
        if (inOutData.contains((Object)InOutDataType.CXMB)) {
            identdic.put("CXMB", "1");
        }
        if (inOutData.contains((Object)InOutDataType.ZDYLR)) {
            identdic.put("ZDYLR", "1");
        }
        if (inOutData.contains((Object)InOutDataType.CSCS)) {
            identdic.put("CSCS", "1");
        }
        if (inOutData.contains((Object)InOutDataType.CSJG)) {
            identdic.put("CSJG", "1");
        }
        if (inOutData.contains((Object)InOutDataType.HSHD)) {
            identdic.put("HSHD", "1");
        }
        if (inOutData.contains((Object)InOutDataType.WQHZ)) {
            identdic.put("WQHZ", "1");
        }
        if (inOutData.contains((Object)InOutDataType.HLMB)) {
            identdic.put("HLMB", "1");
        }
        if (inOutData.contains((Object)InOutDataType.BAHD)) {
            identdic.put("BAHD", "1");
        }
        if (inOutData.contains((Object)InOutDataType.SHSM)) {
            identdic.put("SHSM", "1");
        }
        if (inOutData.contains((Object)InOutDataType.GXXS)) {
            identdic.put("GXXS", "1");
        }
        if (inOutData.contains((Object)InOutDataType.ABMPARA)) {
            identdic.put("ABMPara", "1");
        }
        if (inOutData.contains((Object)InOutDataType.ABMDATA)) {
            identdic.put("ABMData", "1");
        }
        if (inOutData.contains((Object)InOutDataType.SUBTASK)) {
            identdic.put("SubTask", "1");
        }
        if (inOutData.contains((Object)InOutDataType.PJFA)) {
            identdic.put("PJFA", "1");
        }
    }

    private void loadInOutType(Map<String, String> identdic) {
        this.getInOutData().clear();
        if ("1".equalsIgnoreCase(identdic.get("QYSJ"))) {
            this.getInOutData().add(InOutDataType.QYSJ);
        }
        if ("1".equalsIgnoreCase(identdic.get("BBCS"))) {
            this.getInOutData().add(InOutDataType.BBCS);
        }
        if ("1".equalsIgnoreCase(identdic.get("CXMB"))) {
            this.getInOutData().add(InOutDataType.CXMB);
        }
        if ("1".equalsIgnoreCase(identdic.get("ZDYLR"))) {
            this.getInOutData().add(InOutDataType.ZDYLR);
        }
        if ("1".equalsIgnoreCase(identdic.get("CSCS"))) {
            this.getInOutData().add(InOutDataType.CSCS);
        }
        if ("1".equalsIgnoreCase(identdic.get("CSJG"))) {
            this.getInOutData().add(InOutDataType.CSJG);
        }
        if ("1".equalsIgnoreCase(identdic.get("HSHD"))) {
            this.getInOutData().add(InOutDataType.HSHD);
        }
        if ("1".equalsIgnoreCase(identdic.get("WQHZ"))) {
            this.getInOutData().add(InOutDataType.WQHZ);
        }
        if ("1".equalsIgnoreCase(identdic.get("HLMB"))) {
            this.getInOutData().add(InOutDataType.HLMB);
        }
        if ("1".equalsIgnoreCase(identdic.get("BAHD"))) {
            this.getInOutData().add(InOutDataType.BAHD);
        }
        if ("1".equalsIgnoreCase(identdic.get("SHSM"))) {
            this.getInOutData().add(InOutDataType.SHSM);
        }
        if ("1".equalsIgnoreCase(identdic.get("GXXS"))) {
            this.getInOutData().add(InOutDataType.GXXS);
        }
        if ("1".equalsIgnoreCase(identdic.get("ABMPara"))) {
            this.getInOutData().add(InOutDataType.ABMPARA);
        }
        if ("1".equalsIgnoreCase(identdic.get("ABMData"))) {
            this.getInOutData().add(InOutDataType.ABMDATA);
        }
        if ("1".equalsIgnoreCase(identdic.get("SubTask"))) {
            this.getInOutData().add(InOutDataType.SUBTASK);
        }
        if ("1".equalsIgnoreCase(identdic.get("PJFA"))) {
            this.getInOutData().add(InOutDataType.PJFA);
        }
        if ("1".equalsIgnoreCase(identdic.get("MBSM"))) {
            this.getInOutData().add(InOutDataType.PJFA);
        }
    }

    @Override
    public String getTaskName() {
        return this.getInfo().readString("General", "Name", "");
    }

    @Override
    public void setTaskName(String taskName) {
        this.getInfo().writeString("General", "Name", taskName);
    }

    @Override
    public String getTaskFlag() {
        return this.getInfo().readString("General", "Flag", "");
    }

    @Override
    public void setTaskFlag(String taskFlag) {
        this.getInfo().writeString("General", "Flag", taskFlag);
    }

    @Override
    public String getFileFlag() {
        return this.getInfo().readString("General", "FileFlag", "");
    }

    @Override
    public void setFileFlag(String fileFlag) {
        this.getInfo().writeString("General", "FileFlag", fileFlag);
    }

    @Override
    public String getTaskYear() {
        return this.getInfo().readString("General", "Year", "");
    }

    @Override
    public void setTaskYear(String taskYear) {
        this.getInfo().writeString("General", "Year", taskYear);
    }

    @Override
    public String getTaskPeriod() {
        return this.getInfo().readString("General", "Period", "");
    }

    @Override
    public void setTaskPeriod(String taskPeriod) {
        this.getInfo().writeString("General", "Period", taskPeriod);
    }

    @Override
    public String getTaskTime() {
        return this.getInfo().readString("General", "Time", "");
    }

    @Override
    public void setTaskTime(String taskTime) {
        this.getInfo().writeString("General", "Time", taskTime);
    }

    @Override
    public String getTaskVersion() {
        return this.getInfo().readString("General", "Version", "");
    }

    @Override
    public void setTaskVersion(String taskVersion) {
        this.getInfo().writeString("General", "Version", taskVersion);
    }

    @Override
    public String getTaskGroup() {
        return this.getInfo().readString("General", "Group", "");
    }

    @Override
    public void setTaskGroup(String taskGroup) {
        this.getInfo().writeString("General", "Group", taskGroup);
    }

    @Override
    public boolean isInputClien() {
        String code = this.getInfo().readString("General", "InputClien", "");
        return "1".equalsIgnoreCase(code);
    }

    @Override
    public void setInputClien(boolean inputClien) {
        String code = "0";
        if (inputClien) {
            code = "1";
        }
        this.getInfo().writeString("General", "InputClien", code);
    }

    @Override
    public String getNetPeriodT() {
        return this.getInfo().readString("General", "NetPeriodT", "");
    }

    @Override
    public void setNetPeriodT(String netPeriodT) {
        this.getInfo().writeString("General", "NetPeriodT", netPeriodT);
    }
}

