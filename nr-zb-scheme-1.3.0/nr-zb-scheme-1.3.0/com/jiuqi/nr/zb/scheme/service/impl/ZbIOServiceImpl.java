/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.zb.scheme.service.impl;

import com.jiuqi.nr.zb.scheme.common.Consts;
import com.jiuqi.nr.zb.scheme.core.MetaItem;
import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.core.ZbGroup;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion;
import com.jiuqi.nr.zb.scheme.exception.ZbSchemeException;
import com.jiuqi.nr.zb.scheme.internal.dao.IZbGroupDao;
import com.jiuqi.nr.zb.scheme.internal.dao.IZbInfoDao;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbGroupDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbInfoDTO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbGroupDO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbInfoDO;
import com.jiuqi.nr.zb.scheme.internal.excel.ExcelSheetReader;
import com.jiuqi.nr.zb.scheme.internal.excel.ExcelSheetWriter;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWrapper;
import com.jiuqi.nr.zb.scheme.internal.excel.impl.ExcelRowReaderProviderImpl;
import com.jiuqi.nr.zb.scheme.internal.excel.impl.ExcelRowWrapperImpl;
import com.jiuqi.nr.zb.scheme.internal.excel.impl.GroupExcelRowWriterImpl;
import com.jiuqi.nr.zb.scheme.internal.excel.impl.ZbExcelRowWriterImpl;
import com.jiuqi.nr.zb.scheme.service.IZbIOService;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeService;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeSyncProvider;
import com.jiuqi.nr.zb.scheme.utils.ExcelUtils;
import com.jiuqi.nr.zb.scheme.utils.ZbSchemeConvert;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class ZbIOServiceImpl
implements IZbIOService {
    private static final Logger log = LoggerFactory.getLogger(ZbIOServiceImpl.class);
    @Autowired
    private IZbSchemeService zbSchemeService;
    @Autowired
    private IZbSchemeSyncProvider zbSchemeSyncProvider;
    private static final int MAX_WIDTH = 65280;
    @Autowired
    private IZbInfoDao zbInfoDao;
    @Autowired
    private IZbGroupDao zbGroupDao;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void importZbInfo(String schemeKey, String versionKey, Sheet sheet) throws Exception {
        List<PropInfo> propInfos = this.zbSchemeService.listPropInfoLinkedByScheme(schemeKey);
        Set<String> codes = this.getRefCodes(schemeKey, versionKey);
        List<ZbGroupDO> oldGroups = this.zbGroupDao.listByVersion(versionKey);
        List<ZbInfoDO> oldInfos = this.zbInfoDao.listByVersion(versionKey);
        Map<String, ZbInfo> oldInfoMap = oldInfos.stream().filter(info -> codes.contains(info.getCode())).collect(Collectors.toMap(ZbInfoDO::getCode, z -> z, (o, n) -> n));
        ExcelRowReaderProviderImpl readerProvider = new ExcelRowReaderProviderImpl(propInfos, oldInfoMap, schemeKey, versionKey);
        ExcelSheetReader reader = new ExcelSheetReader(sheet, readerProvider);
        reader.addHeader(Consts.EXCEL_ATTRS);
        reader.addExtHeader(propInfos.stream().map(MetaItem::getTitle).collect(Collectors.toList()));
        reader.read();
        if (!reader.isSuccess()) {
            this.writeErrorSheet(reader, sheet);
            throw new ZbSchemeException("\u5bfc\u5165\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u9519\u8bef\u4fe1\u606f");
        }
        this.doImport(reader, versionKey, codes, oldGroups, oldInfos);
    }

    private void writeErrorSheet(ExcelSheetReader reader, Sheet sheet) {
        List<IExcelRowWrapper> rows = reader.getRows();
        short max = reader.getReadHeaders().values().stream().max(Short::compare).orElseThrow(() -> new ZbSchemeException("\u4e0d\u662f\u6b63\u786e\u7684\u6a21\u7248"));
        int rightCol = max + 1;
        int firstRowNum = sheet.getFirstRowNum();
        sheet.getRow(firstRowNum).createCell(rightCol).setCellValue("\u9519\u8bef\u4fe1\u606f");
        for (IExcelRowWrapper rowWrapper : rows) {
            if (rowWrapper.isValid()) continue;
            sheet.getRow(rowWrapper.getRowNum()).createCell(rightCol).setCellValue(rowWrapper.getErrors().toString());
        }
        sheet.autoSizeColumn(rightCol);
    }

    private void doImport(ExcelSheetReader reader, String versionKey, Set<String> codes, List<ZbGroupDO> oldGroups, List<ZbInfoDO> oldInfos) throws Exception {
        int size = reader.getRows().size();
        HashMap<String, ZbGroup> readGroupMap = new HashMap<String, ZbGroup>(size / 2);
        HashMap<String, ZbInfo> readInfoMap = new HashMap<String, ZbInfo>(size);
        this.transData(readGroupMap, readInfoMap, reader);
        this.removeData(codes, oldGroups, oldInfos, readInfoMap);
        Map<String, List<ZbInfoDO>> oldInfoMap = oldInfos.stream().collect(Collectors.groupingBy(ZbInfoDO::getParentKey));
        Map<String, ZbGroupDO> oldGroupMap = oldGroups.stream().collect(Collectors.toMap(ZbGroupDO::getKey, z -> z, (o, n) -> n));
        this.transCode(oldGroups, oldInfoMap, "00000000-0000-0000-0000-000000000000", "GROUP_00");
        ArrayList<ZbGroup> newGroups = new ArrayList<ZbGroup>(readGroupMap.size() + oldGroups.size());
        ArrayList<ZbInfo> newZbInfos = new ArrayList<ZbInfo>(readInfoMap.size() + oldInfos.size());
        HashSet<String> groupCodes = new HashSet<String>(readGroupMap.keySet());
        groupCodes.addAll(oldGroupMap.keySet());
        Map<String, List<ZbInfo>> readInfoListMap = readInfoMap.values().stream().collect(Collectors.groupingBy(ZbInfo::getParentKey));
        this.transKey(readGroupMap, readInfoListMap, oldGroupMap, oldInfoMap, "00000000-0000-0000-0000-000000000000", "GROUP_00", newGroups, newZbInfos, groupCodes);
        this.zbInfoDao.deleteByVersion(versionKey);
        this.zbGroupDao.deleteByVersion(versionKey);
        this.zbGroupDao.insert(newGroups.stream().map(ZbSchemeConvert::cdo).collect(Collectors.toList()));
        this.zbInfoDao.insert(newZbInfos.stream().map(ZbSchemeConvert::cdo).collect(Collectors.toList()));
    }

    private void transData(Map<String, ZbGroup> readGroupMap, Map<String, ZbInfo> readInfoMap, ExcelSheetReader reader) {
        List<IExcelRowWrapper> rows = reader.getRows();
        for (IExcelRowWrapper rowWrapper : rows) {
            if (rowWrapper.isGroup()) {
                ZbGroupDTO groupDTO = (ZbGroupDTO)rowWrapper.getData();
                readGroupMap.put(groupDTO.getKey(), groupDTO);
                continue;
            }
            ZbInfoDTO zbInfoDTO = (ZbInfoDTO)rowWrapper.getData();
            readInfoMap.put(zbInfoDTO.getCode(), zbInfoDTO);
        }
    }

    private void removeData(Set<String> codes, List<ZbGroupDO> oldGroups, List<ZbInfoDO> oldInfos, Map<String, ZbInfo> readInfoMap) {
        int i;
        Map<String, ZbGroupDO> groupMap = oldGroups.stream().collect(Collectors.toMap(ZbGroupDO::getKey, f -> f, (o, n) -> n));
        HashSet<String> holdGroupKeys = new HashSet<String>();
        for (i = 0; i < oldInfos.size(); ++i) {
            ZbInfoDO zbInfo = oldInfos.get(i);
            if (codes.contains(zbInfo.getCode()) && !readInfoMap.containsKey(zbInfo.getCode())) {
                String holdKey = zbInfo.getParentKey();
                if (holdGroupKeys.contains(holdKey)) continue;
                while (holdKey != null && groupMap.containsKey(holdKey)) {
                    holdGroupKeys.add(holdKey);
                    ZbGroupDO hold = groupMap.get(holdKey);
                    holdKey = hold != null ? hold.getParentKey() : null;
                }
                continue;
            }
            oldInfos.set(i, null);
        }
        for (i = 0; i < oldGroups.size(); ++i) {
            ZbGroupDO zbGroupDO = oldGroups.get(i);
            if (holdGroupKeys.contains(zbGroupDO.getKey())) continue;
            oldGroups.set(i, null);
        }
        oldInfos.removeIf(Objects::isNull);
        oldGroups.removeIf(Objects::isNull);
    }

    private void transCode(List<ZbGroupDO> zbGroups, Map<String, List<ZbInfoDO>> zbMap, String parent, String groupCode) {
        if (parent == null) {
            return;
        }
        for (int i = 0; i < zbGroups.size(); ++i) {
            ZbGroupDO group = zbGroups.get(i);
            if (group.getKey().startsWith("GROUP_") || !parent.equals(group.getParentKey())) continue;
            if ("00000000-0000-0000-0000-000000000000".equals(group.getParentKey())) {
                group.setParentKey(groupCode);
            } else {
                group.setParentKey(ExcelUtils.parent(groupCode));
            }
            groupCode = ExcelUtils.next(groupCode);
            String nextGroup = group.getKey();
            group.setKey(groupCode);
            List<ZbInfoDO> zbInfos = zbMap.get(nextGroup);
            if (zbInfos != null) {
                for (ZbInfoDO zbInfo : zbInfos) {
                    zbInfo.setParentKey(groupCode);
                }
            }
            this.transCode(zbGroups, zbMap, nextGroup, ExcelUtils.child(groupCode));
        }
    }

    private void transKey(Map<String, ZbGroup> readGroupMap, Map<String, List<ZbInfo>> readInfoMap, Map<String, ZbGroupDO> oldGroupMap, Map<String, List<ZbInfoDO>> oldInfoMap, String rootKey, String groupRootCode, List<ZbGroup> newGroups, List<ZbInfo> newZbInfos, Set<String> groupCodes) {
        if (rootKey == null) {
            return;
        }
        while (!groupCodes.isEmpty()) {
            if (!groupCodes.remove(groupRootCode = ExcelUtils.next(groupRootCode))) {
                return;
            }
            ZbGroup newGroup = readGroupMap.get(groupRootCode);
            ZbGroup oldGroup = oldGroupMap.get(groupRootCode);
            if (newGroup == null && oldGroup == null) {
                return;
            }
            String nextKey = UUID.randomUUID().toString();
            if (oldGroup != null) {
                oldGroup.setParentKey(rootKey);
                oldGroup.setKey(nextKey);
                newGroups.add(oldGroup);
                if (oldInfoMap.containsKey(groupRootCode)) {
                    this.updateAndAddInfos(oldInfoMap.get(groupRootCode), nextKey, newZbInfos);
                }
                if (newGroup != null && !newGroup.getTitle().equals(oldGroup.getTitle())) {
                    newGroup.setParentKey(rootKey);
                    newGroup.setKey(UUID.randomUUID().toString());
                    newGroups.add(newGroup);
                    if (readInfoMap.containsKey(groupRootCode)) {
                        this.updateAndAddInfos(readInfoMap.get(groupRootCode), newGroup.getKey(), newZbInfos);
                    }
                }
            } else {
                newGroup.setParentKey(rootKey);
                newGroup.setKey(nextKey);
                newGroups.add(newGroup);
                if (readInfoMap.containsKey(groupRootCode)) {
                    this.updateAndAddInfos(readInfoMap.get(groupRootCode), nextKey, newZbInfos);
                }
            }
            this.transKey(readGroupMap, readInfoMap, oldGroupMap, oldInfoMap, nextKey, ExcelUtils.child(groupRootCode), newGroups, newZbInfos, groupCodes);
        }
    }

    private <T extends ZbInfo> void updateAndAddInfos(List<T> infoDOS, String parentKey, List<ZbInfo> newZbInfos) {
        for (ZbInfo infoDO : infoDOS) {
            infoDO.setParentKey(parentKey);
            infoDO.setKey(UUID.randomUUID().toString());
        }
        newZbInfos.addAll(infoDOS);
    }

    private Set<String> getRefCodes(String schemeKey, String versionKey) {
        List<String> codes = this.zbSchemeSyncProvider.listSyncZbCode(schemeKey, versionKey);
        if (CollectionUtils.isEmpty(codes)) {
            return Collections.emptySet();
        }
        return new HashSet<String>(codes);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void exportZbInfo(String schemeKey, String versionKey, Sheet sheet) {
        ZbSchemeVersion zbSchemeVersion = this.zbSchemeService.getZbSchemeVersion(versionKey);
        if (zbSchemeVersion == null) {
            throw new ZbSchemeException("\u7248\u672c\u4e0d\u5b58\u5728");
        }
        ExcelSheetWriter excelSheetWriter = new ExcelSheetWriter(sheet);
        List<PropInfo> propInfos = this.zbSchemeService.listPropInfoLinkedByScheme(zbSchemeVersion.getSchemeKey());
        excelSheetWriter.addHeader(Consts.EXCEL_ATTRS);
        excelSheetWriter.addExtHeader(propInfos.stream().map(MetaItem::getTitle).collect(Collectors.toList()));
        List<ZbGroup> zbGroups = this.zbSchemeService.listZbGroupByVersion(versionKey);
        List<ZbInfo> list = this.zbSchemeService.listZbInfoByVersion(versionKey);
        Map<String, List<ZbInfo>> zbMap = list.stream().collect(Collectors.groupingBy(ZbInfo::getParentKey));
        if (!zbGroups.isEmpty()) {
            this.addRow(excelSheetWriter, zbGroups, zbMap, "00000000-0000-0000-0000-000000000000", "GROUP_00");
        }
        excelSheetWriter.registerWriter(ZbInfoDTO.class, new ZbExcelRowWriterImpl());
        excelSheetWriter.registerWriter(ZbGroupDTO.class, new GroupExcelRowWriterImpl());
        excelSheetWriter.write();
        List<String> headers = excelSheetWriter.getAllHeaders();
        for (int i = 0; i < headers.size(); ++i) {
            sheet.autoSizeColumn(i);
            int cWidth = sheet.getColumnWidth(i) * 17 / 10;
            if (cWidth > 65280) {
                cWidth = 65280;
            }
            sheet.setColumnWidth(i, cWidth);
        }
    }

    private void addRow(ExcelSheetWriter excelSheetWriter, List<ZbGroup> zbGroups, Map<String, List<ZbInfo>> zbMap, String parent, String groupCode) {
        if (parent == null) {
            return;
        }
        for (int i = 0; i < zbGroups.size(); ++i) {
            ZbGroup group = zbGroups.get(i);
            if (group == null || !parent.equals(group.getParentKey())) continue;
            zbGroups.set(i, null);
            groupCode = ExcelUtils.next(groupCode);
            String nextGroup = group.getKey();
            group.setKey(groupCode);
            if ("00000000-0000-0000-0000-000000000000".equals(group.getParentKey())) {
                group.setParentKey(null);
            } else {
                group.setParentKey(ExcelUtils.parent(groupCode));
            }
            excelSheetWriter.addRow(new ExcelRowWrapperImpl(group));
            List<ZbInfo> zbInfos = zbMap.get(nextGroup);
            if (zbInfos != null) {
                for (ZbInfo zbInfo : zbInfos) {
                    zbInfo.setParentKey(groupCode);
                    excelSheetWriter.addRow(new ExcelRowWrapperImpl(zbInfo));
                }
            }
            this.addRow(excelSheetWriter, zbGroups, zbMap, nextGroup, ExcelUtils.child(groupCode));
        }
    }
}

