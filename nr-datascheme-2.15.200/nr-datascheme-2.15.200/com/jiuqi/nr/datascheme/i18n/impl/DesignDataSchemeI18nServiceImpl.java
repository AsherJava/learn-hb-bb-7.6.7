/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.i18n.impl;

import com.jiuqi.nr.datascheme.common.io.SheetUtils;
import com.jiuqi.nr.datascheme.i18n.IDesignDataSchemeI18nService;
import com.jiuqi.nr.datascheme.i18n.dao.DesignDataSchemeI18nDao;
import com.jiuqi.nr.datascheme.i18n.dto.DSI18nExcelDataDTO;
import com.jiuqi.nr.datascheme.i18n.dto.DesignDataFieldInfoDTO;
import com.jiuqi.nr.datascheme.i18n.dto.DesignDataSchemeI18nDTO;
import com.jiuqi.nr.datascheme.i18n.entity.DesignDataSchemeI18nDO;
import com.jiuqi.nr.datascheme.i18n.language.ILanguageType;
import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DesignDataSchemeI18nServiceImpl
implements IDesignDataSchemeI18nService {
    @Autowired
    private DesignDataSchemeI18nDao i18nDesignDao;

    @Override
    public List<DesignDataSchemeI18nDTO> getBySchemeKey(String schemeKey) {
        return this.i18nDesignDao.getBySchemeKey(schemeKey);
    }

    @Override
    public List<DesignDataSchemeI18nDTO> getByTableKey(String tableKey, String type) {
        return this.i18nDesignDao.getByTableKey(tableKey, type);
    }

    @Override
    public void save(String tableKey, String type, List<DesignDataSchemeI18nDO> dos) {
        if (null == dos || dos.isEmpty()) {
            return;
        }
        if ((dos = dos.stream().filter(i -> StringUtils.hasText(i.getTitle()) || StringUtils.hasText(i.getDesc())).collect(Collectors.toList())).isEmpty()) {
            return;
        }
        this.i18nDesignDao.deleteByTableKey(tableKey, type);
        this.i18nDesignDao.insert(dos.toArray());
    }

    @Override
    public void deleteBySchemeKey(String schemeKey) {
        this.i18nDesignDao.deleteBySchemeKey(schemeKey);
    }

    @Override
    public void deleteByTableKey(String tableKey) {
        this.i18nDesignDao.deleteByTableKey(tableKey);
    }

    @Override
    public void deleteByFieldKey(String fieldKey) {
        this.i18nDesignDao.deleteByFieldKey(fieldKey);
    }

    @Override
    public void doExport(String schemeKey, Sheet tableSheet) {
        if (null == tableSheet) {
            return;
        }
        List<ILanguageType> allNoDefault = LanguageType.allValues().stream().filter(l -> !l.isDefault()).collect(Collectors.toList());
        if (allNoDefault.isEmpty()) {
            return;
        }
        ArrayList rowDatas = new ArrayList();
        this.addHeadData(rowDatas, allNoDefault);
        HashMap<String, DSI18nExcelDataDTO> dataMap = new HashMap<String, DSI18nExcelDataDTO>();
        List<DesignDataSchemeI18nDTO> dtos = this.i18nDesignDao.getBySchemeKey(schemeKey);
        if (null == dtos || dtos.isEmpty()) {
            return;
        }
        for (DesignDataSchemeI18nDTO dto : dtos) {
            if (dataMap.containsKey(DSI18nExcelDataDTO.getIndex(dto))) {
                ((DSI18nExcelDataDTO)dataMap.get(DSI18nExcelDataDTO.getIndex(dto))).add(dto);
                continue;
            }
            dataMap.put(DSI18nExcelDataDTO.getIndex(dto), new DSI18nExcelDataDTO(allNoDefault, dto));
        }
        dataMap.values().stream().sorted((d1, d2) -> d1.getIndex().compareTo(d2.getIndex())).forEach(rowDatas::add);
        SheetUtils.trans(tableSheet, rowDatas);
        int colNum = 0;
        tableSheet.addMergedRegion(new CellRangeAddress(0, 1, colNum, colNum++));
        tableSheet.addMergedRegion(new CellRangeAddress(0, 1, colNum, colNum++));
        tableSheet.addMergedRegion(new CellRangeAddress(0, 0, colNum++, colNum++));
        for (int i = 0; i < allNoDefault.size(); ++i) {
            tableSheet.addMergedRegion(new CellRangeAddress(0, 0, colNum++, colNum++));
        }
    }

    private void addHeadData(List<SheetUtils.IRowData<?>> rowDatas, List<ILanguageType> allNoDefault) {
        int i = 0;
        final HashMap<Integer, String> head1 = new HashMap<Integer, String>();
        final HashMap<Integer, String> head2 = new HashMap<Integer, String>();
        head1.put(i++, "\u8868\u6807\u8bc6");
        head1.put(i++, "\u6807\u8bc6");
        head1.put(i, "\u4e2d\u6587\uff08\u53ea\u8bfb\uff09");
        head2.put(i++, "\u540d\u79f0");
        head2.put(i++, "\u63cf\u8ff0");
        for (ILanguageType iLanguageType : allNoDefault) {
            head1.put(i, iLanguageType.getLanguage());
            head2.put(i++, "\u540d\u79f0");
            head2.put(i++, "\u63cf\u8ff0");
        }
        rowDatas.add(new SheetUtils.IRowData<Object>(){

            @Override
            public Map<Integer, Object> read() {
                return head1;
            }

            @Override
            public Object write(Map<Integer, Object> data) {
                return null;
            }
        });
        rowDatas.add(new SheetUtils.IRowData<Object>(){

            @Override
            public Map<Integer, Object> read() {
                return head2;
            }

            @Override
            public Object write(Map<Integer, Object> data) {
                return null;
            }
        });
    }

    @Override
    public void doImport(String schemeKey, Sheet tableSheet) {
        if (null == tableSheet) {
            return;
        }
        ArrayList<ILanguageType> types = new ArrayList<ILanguageType>();
        Row row = tableSheet.getRow(0);
        for (int i = 4; i < row.getLastCellNum(); ++i) {
            String cellValue = (String)SheetUtils.getCellValue(row.getCell(i));
            ILanguageType valueOfName = LanguageType.valueOfName(cellValue);
            if (null == valueOfName) continue;
            types.add(valueOfName);
        }
        List<DSI18nExcelDataDTO> datas = SheetUtils.trans(tableSheet, r -> r.getRowNum() >= 2, () -> new DSI18nExcelDataDTO(types));
        Map<String, String> fieldKeyMap = this.i18nDesignDao.getFieldInfo(schemeKey).stream().collect(Collectors.toMap(DSI18nExcelDataDTO::getIndex, DesignDataFieldInfoDTO::getFieldKey));
        ArrayList<DesignDataSchemeI18nDO> allDos = new ArrayList<DesignDataSchemeI18nDO>();
        for (DSI18nExcelDataDTO data : datas) {
            allDos.addAll(data.getDOs(schemeKey, fieldKeyMap));
        }
        this.update(schemeKey, allDos);
    }

    private void update(String dataSchemeKey, List<DesignDataSchemeI18nDO> dos) {
        if (null == dos || dos.isEmpty()) {
            return;
        }
        List oldDos = this.i18nDesignDao.getBySchemeKey(dataSchemeKey, null);
        if (null == oldDos || oldDos.isEmpty()) {
            this.i18nDesignDao.insert(dos.toArray());
            return;
        }
        Map<String, DesignDataSchemeI18nDO> oldDosMap = oldDos.stream().collect(Collectors.toMap(this::getIndex, v -> v));
        ArrayList<DesignDataSchemeI18nDO> insertList = new ArrayList<DesignDataSchemeI18nDO>();
        ArrayList<DesignDataSchemeI18nDO> updateList = new ArrayList<DesignDataSchemeI18nDO>();
        for (DesignDataSchemeI18nDO d : dos) {
            if (oldDosMap.containsKey(this.getIndex(d))) {
                DesignDataSchemeI18nDO update = oldDosMap.get(this.getIndex(d));
                if (StringUtils.hasText(d.getTitle())) {
                    update.setTitle(d.getTitle());
                }
                if (StringUtils.hasText(d.getDesc())) {
                    update.setDesc(d.getDesc());
                }
                updateList.add(update);
                continue;
            }
            insertList.add(d);
        }
        if (!insertList.isEmpty()) {
            this.i18nDesignDao.insert(insertList.toArray());
        }
        this.i18nDesignDao.update(updateList);
    }

    private String getIndex(DesignDataSchemeI18nDO d) {
        return d.getKey() + d.getType();
    }
}

