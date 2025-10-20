/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.nr.impl.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.nr.impl.dao.GcTaskDataCopyDAO;
import com.jiuqi.gcreport.nr.impl.dto.GcTaskDataCopyDTO;
import com.jiuqi.gcreport.nr.impl.dto.GcTaskDataFixedCollectDTO;
import com.jiuqi.gcreport.nr.impl.service.GcTaskDataCopyService;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcTaskDataCopyServiceImpl
implements GcTaskDataCopyService {
    private static final Logger logger = LoggerFactory.getLogger(GcTaskDataCopyServiceImpl.class);
    private static Map<String, List<GcTaskDataCopyDTO>> taskMapping = new HashMap<String, List<GcTaskDataCopyDTO>>();
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private GcTaskDataCopyDAO gcTaskDataCopyDAO;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void copyData(String taskCode, String unitCode, String datatime) {
        String mapDatatime;
        YearPeriodObject yp;
        TaskDefine zbmapTask = this.iRunTimeViewController.queryTaskDefineByCode("ZBMAP");
        String orgTableName = this.entityMetaService.getTableModel(zbmapTask.getDw()).getName();
        List orgTree = GcOrgPublicTool.getInstance((String)orgTableName, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)(yp = new YearPeriodObject(null, mapDatatime = datatime.substring(0, 4) + "N0001"))).getOrgTree();
        if (orgTree.size() != 1) {
            throw new BusinessRuntimeException("ZBMAP\u4efb\u52a1\u5173\u8054\u7684\u673a\u6784\u7c7b\u578b\u6709\u4e14\u53ea\u80fd\u6709\u4e00\u4e2a\u5355\u4f4d:" + orgTableName);
        }
        List<GcTaskDataCopyDTO> gcTaskDataCopyDTOS = taskMapping.get(taskCode);
        if (CollectionUtils.isEmpty(gcTaskDataCopyDTOS)) {
            throw new BusinessRuntimeException("\u4efb\u52a1" + taskCode + "\u672a\u627e\u5230\u76ee\u6807\u4efb\u52a1");
        }
        String tarTaskCode = gcTaskDataCopyDTOS.get(0).getTarTaskCode();
        List gcBaseData = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_ZB_LIST");
        List collectMapping = gcBaseData.stream().filter(item -> tarTaskCode.equals(item.getFieldVal("ZBMODEL")) && "1".equals(item.getFieldVal("ISTRANSFER").toString())).collect(Collectors.toList());
        Map gcBaseDataMap = collectMapping.stream().collect(Collectors.toMap(item -> item.getFieldVal("ZBCODE").toString(), Function.identity()));
        for (GcTaskDataCopyDTO gcTaskDataCopyDTO : gcTaskDataCopyDTOS) {
            HashSet deletedTable = new HashSet();
            ArrayList gcTaskDataFixedCollectDTOS = new ArrayList();
            List<Map<String, Object>> maps = gcTaskDataCopyDTO.getFloat() != false ? this.gcTaskDataCopyDAO.queryFloatMapping(gcTaskDataCopyDTO.getTableName(), ((GcOrgCacheVO)orgTree.get(0)).getCode(), mapDatatime) : this.gcTaskDataCopyDAO.queryMapping(gcTaskDataCopyDTO.getTableName(), ((GcOrgCacheVO)orgTree.get(0)).getCode(), mapDatatime);
            Map<String, List<Map>> srcFormGroup = maps.stream().collect(Collectors.groupingBy(item -> item.get("SRCRPCODE").toString()));
            srcFormGroup.forEach((tableName, mappingList) -> {
                List<Map<String, Object>> dataList;
                Map zbMap = mappingList.stream().collect(Collectors.toMap(item -> item.get("SRCZBCODE").toString(), Function.identity(), (k1, k2) -> k2));
                Set stdTable = mappingList.stream().map(item -> item.get("STDRPCODE")).collect(Collectors.toSet());
                if (stdTable.size() > 1) {
                    throw new BusinessRuntimeException("\u7269\u7406\u8868\u3010" + tableName + "\u3011\u5bf9\u5e94\u6620\u5c04\u7684\u7269\u7406\u8868\u5b58\u5728\u591a\u4e2a");
                }
                Map<String, String> tar2SrcMap = mappingList.stream().collect(Collectors.toMap(item -> item.get("STDZBCODE").toString(), item -> item.get("SRCZBCODE").toString(), (k1, k2) -> k2));
                ArrayList<String> fields = new ArrayList<String>(zbMap.keySet());
                if (gcTaskDataCopyDTO.getFloat().booleanValue()) {
                    fields.add("BIZKEYORDER");
                    fields.add("FLOATORDER");
                }
                try {
                    dataList = this.gcTaskDataCopyDAO.querySrcData((String)tableName, unitCode, datatime, (List<String>)fields);
                }
                catch (Exception e) {
                    logger.error("\u51b3\u7b97\u6c47\u67e5\u8be2\u6570\u636e\u5931\u8d25\uff0c\u7269\u7406\u8868\uff1a" + tableName + "\uff0c \u5355\u4f4d\uff1a" + unitCode + "\uff0c\u65f6\u671f\uff1a" + datatime + "\uff0c\u5b57\u6bb5\uff1a" + JsonUtils.writeValueAsString(fields), e);
                    return;
                }
                if (!CollectionUtils.isEmpty(dataList)) {
                    String tarTableName = ((Map)mappingList.get(0)).get("STDRPCODE").toString();
                    List<String> tarFieldCodes = mappingList.stream().map(item -> item.get("STDZBCODE").toString()).collect(Collectors.toList());
                    ArrayList<Object[]> values = new ArrayList<Object[]>();
                    if (gcTaskDataCopyDTO.getFloat().booleanValue()) {
                        tarFieldCodes.add("BIZKEYORDER");
                        tarFieldCodes.add("FLOATORDER");
                        tarFieldCodes.add("SRC");
                    }
                    for (Map<String, Object> data : dataList) {
                        Object[] objects = new Object[tarFieldCodes.size() + 2];
                        values.add(objects);
                        for (int i = 0; i < tarFieldCodes.size(); ++i) {
                            Object zbcategory;
                            String fieldCode = tarFieldCodes.get(i);
                            if (fieldCode.equals("BIZKEYORDER") || fieldCode.equals("FLOATORDER")) {
                                objects[i] = data.get(fieldCode);
                                continue;
                            }
                            if (fieldCode.equals("SRC")) {
                                objects[i] = ((Map)mappingList.get(0)).get("SRC");
                                continue;
                            }
                            Object value = data.get(tar2SrcMap.get(fieldCode));
                            if (Objects.isNull(value)) {
                                logger.info("\u51b3\u7b97\u6c47\u590d\u5236\u6307\u6807\u6570\u636e\uff0c\u6307\u6807\u503c\u4e3a\u7a7a\uff1a" + tar2SrcMap.get(fieldCode));
                            }
                            objects[i] = value;
                            if (!gcBaseDataMap.containsKey(fieldCode)) continue;
                            GcTaskDataFixedCollectDTO gcTaskDataFixedCollectDTO = new GcTaskDataFixedCollectDTO();
                            gcTaskDataFixedCollectDTOS.add(gcTaskDataFixedCollectDTO);
                            gcTaskDataFixedCollectDTO.setFieldCode(fieldCode);
                            Object zbname = ((GcBaseData)gcBaseDataMap.get(fieldCode)).getFieldVal("ZBNAME");
                            if (Objects.nonNull(zbname)) {
                                gcTaskDataFixedCollectDTO.setFieldName(zbname.toString());
                            }
                            if (Objects.nonNull(zbcategory = ((GcBaseData)gcBaseDataMap.get(fieldCode)).getFieldVal("ZBCATEGORY"))) {
                                gcTaskDataFixedCollectDTO.setFieldName(zbcategory.toString());
                            }
                            gcTaskDataFixedCollectDTO.setModelName(tarTaskCode);
                            gcTaskDataFixedCollectDTO.setFieldValue(value);
                            gcTaskDataFixedCollectDTO.setId(UUIDUtils.newUUIDStr());
                            gcTaskDataFixedCollectDTO.setFloatOrder(new Double(String.valueOf(i)));
                        }
                        objects[tarFieldCodes.size()] = unitCode;
                        objects[tarFieldCodes.size() + 1] = datatime;
                    }
                    tarFieldCodes.add("MDCODE");
                    tarFieldCodes.add("DATATIME");
                    try {
                        if (!deletedTable.contains(tarTableName)) {
                            deletedTable.add(tarTableName);
                            this.gcTaskDataCopyDAO.deleteData(tarTableName, unitCode, datatime);
                        }
                        this.gcTaskDataCopyDAO.saveData(tarTableName, tarFieldCodes, values);
                    }
                    catch (Exception e) {
                        logger.error("\u51b3\u7b97\u6c47\u4fdd\u5b58\u6570\u636e\u5931\u8d25\uff0c\u7269\u7406\u8868\uff1a" + tarTableName + "\uff0c \u5355\u4f4d\uff1a" + unitCode + "\uff0c\u65f6\u671f\uff1a" + datatime + "\uff0c\u5b57\u6bb5\uff1a" + JsonUtils.writeValueAsString(tarFieldCodes), e);
                    }
                }
            });
            try {
                if (CollectionUtils.isEmpty(gcTaskDataFixedCollectDTOS)) continue;
                List<String> fieldCodes = gcTaskDataFixedCollectDTOS.stream().map(GcTaskDataFixedCollectDTO::getFieldCode).collect(Collectors.toList());
                this.gcTaskDataCopyDAO.deleteFloatData(gcTaskDataCopyDTO.getFloatTableName(), fieldCodes, unitCode, datatime);
                List<Object[]> floatValues = gcTaskDataFixedCollectDTOS.stream().map(item -> {
                    Object[] value = new Object[]{item.getModelName(), item.getFieldCode(), item.getFieldName(), item.getFieldKind(), item.getFieldValue(), unitCode, datatime, item.getFloatOrder(), item.getId()};
                    return value;
                }).collect(Collectors.toList());
                this.gcTaskDataCopyDAO.saveFloatData(gcTaskDataCopyDTO.getFloatTableName(), floatValues);
            }
            catch (Exception e) {
                logger.error("\u51b3\u7b97\u6c47\u4fdd\u5b58\u56fa\u5b9a\u8f6c\u6d6e\u52a8\u6307\u6807\u5931\u8d25\uff0c \u5355\u4f4d\uff1a" + unitCode + "\uff0c\u65f6\u671f\uff1a" + datatime + "\uff0c\u5b57\u6bb5\uff1a" + JsonUtils.writeValueAsString(gcTaskDataFixedCollectDTOS), e);
            }
        }
    }

    static {
        ArrayList<GcTaskDataCopyDTO> gzqyTaskDataCopyDTOS = new ArrayList<GcTaskDataCopyDTO>();
        gzqyTaskDataCopyDTOS.add(new GcTaskDataCopyDTO("GZFA_Z_ZBMAPPING", "GZFA_Z_ZBMAPPING", "GZFA", "GZQY", "GZFA_F_ZBVAL", false));
        gzqyTaskDataCopyDTOS.add(new GcTaskDataCopyDTO("GZFA_F_ZBMAPPING", "GZFA_F_ZBMAPPING", "GZFA", "GZQY", "GZFA_F_ZBVAL", true));
        taskMapping.put("GZQY", gzqyTaskDataCopyDTOS);
        ArrayList<GcTaskDataCopyDTO> gyqzTaskDataCopyDTOS = new ArrayList<GcTaskDataCopyDTO>();
        gyqzTaskDataCopyDTOS.add(new GcTaskDataCopyDTO("GZQR_Z_ZBMAPPING", "GZQR_Z_ZBMAPPING", "GZQR", "GYQZ", "GZQR_F_ZBVAL", false));
        taskMapping.put("GYQZ", gyqzTaskDataCopyDTOS);
        ArrayList<GcTaskDataCopyDTO> gzysTaskDataCopyDTOS = new ArrayList<GcTaskDataCopyDTO>();
        gzysTaskDataCopyDTOS.add(new GcTaskDataCopyDTO("GZBD_Z_ZBMAPPING", "GZBD_Z_ZBMAPPING", "GZBD", "GZYS", "GZBD_F_ZBVAL", false));
        taskMapping.put("GZYS", gzysTaskDataCopyDTOS);
    }
}

