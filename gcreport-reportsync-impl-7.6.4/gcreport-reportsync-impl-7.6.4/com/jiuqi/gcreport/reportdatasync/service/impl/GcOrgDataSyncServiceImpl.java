/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.ZipUtils
 *  com.jiuqi.common.billbasedopsorg.service.GcOrgsSyncToBillService
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.reportdatasync.dto.GcMultilevelSyncProgressDTO
 *  com.jiuqi.gcreport.reportdatasync.enums.UploadStatusEnum
 *  com.jiuqi.gcreport.reportdatasync.vo.MultilevelOrgDataSyncLogVO
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.va.biz.intf.action.ActionManager
 *  com.jiuqi.va.organization.service.OrgDataService
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.ZipUtils;
import com.jiuqi.common.billbasedopsorg.service.GcOrgsSyncToBillService;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.reportdatasync.contextImpl.OrgDataContext;
import com.jiuqi.gcreport.reportdatasync.dao.MultilevelOrgDataLogDao;
import com.jiuqi.gcreport.reportdatasync.dto.GcMultilevelSyncProgressDTO;
import com.jiuqi.gcreport.reportdatasync.entity.MultilevelOrgDataSyncLogEO;
import com.jiuqi.gcreport.reportdatasync.enums.UploadStatusEnum;
import com.jiuqi.gcreport.reportdatasync.service.GcOrgDataSyncService;
import com.jiuqi.gcreport.reportdatasync.vo.MultilevelOrgDataSyncLogVO;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.va.biz.intf.action.ActionManager;
import com.jiuqi.va.organization.service.OrgDataService;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GcOrgDataSyncServiceImpl
implements GcOrgDataSyncService {
    private final Logger logger = LoggerFactory.getLogger(GcOrgDataSyncServiceImpl.class);
    @Autowired
    private MultilevelOrgDataLogDao multilevelOrgDataLogDao;
    @Autowired
    private ProgressService<GcMultilevelSyncProgressDTO, List<String>> progressService;
    @Autowired
    ActionManager actionManager;
    @Autowired
    OrgDataService orgDataService;
    @Autowired
    GcOrgsSyncToBillService orgsSyncToBillService;
    @Autowired
    UserService<User> userService;
    public static final String ORG_DATA_META_FILENAME = "OrgDataMeta.JSON";

    @Override
    public PageInfo<MultilevelOrgDataSyncLogVO> listOrgDataSyncLogs(int pageNum, int pageSize) {
        PageInfo<MultilevelOrgDataSyncLogEO> pageInfo = this.multilevelOrgDataLogDao.listOrgDataSyncLogs(pageNum, pageSize);
        List logVO = pageInfo.getList().stream().map(eo -> {
            MultilevelOrgDataSyncLogVO vo = new MultilevelOrgDataSyncLogVO();
            BeanUtils.copyProperties(eo, vo);
            ProgressData progressData = this.progressService.queryProgressData(vo.getSyncDataAttachId());
            vo.setUploadTime(DateUtils.format((Date)eo.getUploadTime(), (String)"yyyy-MM-dd HH:mm:ss"));
            if (progressData != null && progressData.getProgressValue() < 1.0 && progressData.getProgressValue() > 0.0) {
                vo.setShowProgress(Boolean.valueOf(true));
                vo.setProgress(Double.valueOf(progressData.getProgressValue()));
            }
            return vo;
        }).collect(Collectors.toList());
        PageInfo voPageInfo = PageInfo.of(logVO, (int)pageInfo.getSize());
        return voPageInfo;
    }

    @Override
    public Boolean rejectOrgData(MultilevelOrgDataSyncLogVO vo) {
        String id = vo.getSyncLogId();
        MultilevelOrgDataSyncLogEO uploadLogEO = (MultilevelOrgDataSyncLogEO)this.multilevelOrgDataLogDao.get((Serializable)((Object)id));
        if (uploadLogEO == null) {
            this.logger.error("\u7ec4\u7ec7\u673a\u6784\u9000\u56de\u672a\u627e\u5230id\u4e3a" + id + "\u7684\u4e0a\u4f20\u8bb0\u5f55");
            return false;
        }
        uploadLogEO.setLoadStatus(UploadStatusEnum.REJECTED.getCode());
        uploadLogEO.setUploadMsg(vo.getUploadMsg());
        this.multilevelOrgDataLogDao.update((BaseEntity)uploadLogEO);
        return true;
    }

    @Override
    public Boolean importOrgDataFile(MultipartFile multipartFile) {
        try {
            byte[] bytes = null;
            try {
                bytes = ZipUtils.unzipFileBytesAndDeleteTempFileOnExit((MultipartFile)multipartFile, (String)ORG_DATA_META_FILENAME);
            }
            catch (Exception e) {
                this.logger.error("\u6570\u636e\u5305\u5185\u5bb9\u4e0d\u6b63\u786e\uff0c\u7f3a\u5c11\u6587\u4ef6\uff1aOrgDataMeta.JSON");
            }
            if (bytes == null) {
                throw new BusinessRuntimeException("\u6570\u636e\u5305\u5185\u5bb9\u4e0d\u6b63\u786e\uff0c\u7f3a\u5c11\u6587\u4ef6\uff1aOrgDataMeta.JSON");
            }
            OrgDataContext context = (OrgDataContext)((Object)JsonUtils.readValue((byte[])bytes, OrgDataContext.class));
            List<Object> orgDOS = new ArrayList();
            byte[] dataBytes = null;
            try {
                dataBytes = ZipUtils.unzipFileBytesAndDeleteTempFileOnExit((MultipartFile)multipartFile, (String)"orgData");
            }
            catch (Exception e) {
                this.logger.error("[\u7ec4\u7ec7\u673a\u6784\u540c\u6b65]\u672a\u627e\u5230orgData\u6587\u4ef6");
            }
            if (dataBytes != null) {
                orgDOS = (List)JsonUtils.readValue((byte[])dataBytes, (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
            }
            byte[] newDataBytes = null;
            try {
                newDataBytes = ZipUtils.unzipFileBytesAndDeleteTempFileOnExit((MultipartFile)multipartFile, (String)"ORG_DATA.csv");
            }
            catch (Exception e) {
                this.logger.error("[\u7ec4\u7ec7\u673a\u6784\u540c\u6b65]\u672a\u627e\u5230ORG_DATA.csv\u6587\u4ef6");
            }
            if (newDataBytes != null) {
                try (ByteArrayInputStream bis = new ByteArrayInputStream(newDataBytes);){
                    XSSFWorkbook workbook = new XSSFWorkbook(bis);
                    orgDOS = this.getOrgDataFromWorkBook(workbook);
                }
                catch (Exception e) {
                    throw new BusinessRuntimeException("\u7ec4\u7ec7\u673a\u6784\u4fe1\u606f\u83b7\u53d6\u5931\u8d25" + e.getMessage());
                }
            }
            this.createPushOrgsBill(context, orgDOS);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u7ec4\u7ec7\u673a\u6784\u5bfc\u5165\u5931\u8d25" + e.getMessage(), (Throwable)e);
        }
        return true;
    }

    private boolean createPushOrgsBill(OrgDataContext context, List<Map<String, Object>> orgDOS) {
        HashMap<String, Object> masterData = new HashMap<String, Object>();
        masterData.put("BILLDATE", new Date());
        masterData.put("SYNCORGTYPE", context.getOrgType());
        masterData.put("SYNCPARENTCODE", context.getOrgCode());
        masterData.put("CREATEUSER", NpContextHolder.getContext().getUserId());
        masterData.put("SYNCLOGID", context.getSn());
        User user = (User)this.userService.find(NpContextHolder.getContext().getUserId()).get();
        masterData.put("UNITCODE", user.getOrgCode());
        masterData.put("BILLDATE", new Date());
        YearPeriodObject yp = new YearPeriodObject(null, context.getPeriodValue());
        Date date = yp.formatYP().getEndDate();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        String periodStr = df.format(date);
        masterData.put("SYNCPERIOD", periodStr);
        ArrayList subItemList = new ArrayList();
        for (Map<String, Object> orgDataMap : orgDOS) {
            HashMap<String, Object> convertOrgData = new HashMap<String, Object>();
            for (String key : orgDataMap.keySet()) {
                String newKey = "ORG_" + key.toUpperCase();
                convertOrgData.put(newKey, orgDataMap.get(key));
            }
            subItemList.add(convertOrgData);
        }
        this.orgsSyncToBillService.orgsSync(masterData, subItemList);
        return true;
    }

    private List<Map<String, Object>> getOrgDataFromWorkBook(Workbook workbook) {
        int i;
        ArrayList<Map<String, Object>> orgDOS = new ArrayList<Map<String, Object>>();
        Sheet sheetAt = workbook.getSheetAt(0);
        int allRows = sheetAt.getPhysicalNumberOfRows();
        Row row = sheetAt.getRow(0);
        int allColumns = row.getPhysicalNumberOfCells();
        HashMap<Integer, String> updateCode = new HashMap<Integer, String>();
        for (i = 0; i < allColumns; ++i) {
            Cell cell = row.getCell(i);
            updateCode.put(i, cell.getStringCellValue());
        }
        for (i = 1; i < allRows; ++i) {
            Row dataRow = sheetAt.getRow(i);
            HashMap orgData = new HashMap();
            for (int j = 0; j < allColumns; ++j) {
                Cell cell = dataRow.getCell(j);
                orgData.put(updateCode.get(j), cell.getStringCellValue());
            }
            orgDOS.add(orgData);
        }
        return orgDOS;
    }
}

