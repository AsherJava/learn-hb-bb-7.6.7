/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.impl.ncell.service.impl;

import com.jiuqi.dc.base.impl.ncell.common.ColumnBaseDataParser;
import com.jiuqi.dc.base.impl.ncell.common.ColumnNumberParser;
import com.jiuqi.dc.base.impl.ncell.common.ColumnOrgParser;
import com.jiuqi.dc.base.impl.ncell.common.ColumnStringParser;
import com.jiuqi.dc.base.impl.ncell.service.TableCopyService;
import com.jiuqi.dc.base.impl.ncell.vo.ColumnDefineVO;
import com.jiuqi.dc.base.impl.ncell.vo.ColumnValueType;
import com.jiuqi.dc.base.impl.ncell.vo.PasteDataVO;
import com.jiuqi.dc.base.impl.ncell.vo.PasteParamVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TableCopyServiceImpl
implements TableCopyService {
    public static final String ORG_TABLE_PREFIX = "MD_ORG";

    @Override
    public List<Map<String, Object>> transformData(PasteParamVO paramVO) {
        ArrayList<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        List<Map<String, PasteDataVO>> rows = paramVO.getRows();
        for (Map<String, PasteDataVO> row : rows) {
            Set<String> keySet = row.keySet();
            HashMap<String, Object> retRow = new HashMap<String, Object>(16);
            for (String key : keySet) {
                PasteDataVO pasteDataVO = row.get(key);
                String pastValue = pasteDataVO.getColumnValue();
                ColumnDefineVO columnDefine = pasteDataVO.getColumnDefine();
                ColumnValueType valueType = columnDefine.getValueType();
                String refTableName = columnDefine.getRefTableName();
                Object parse = new Object();
                if (StringUtils.hasText(refTableName) && refTableName.toUpperCase().startsWith(ORG_TABLE_PREFIX)) {
                    ColumnOrgParser columnOrgParser = new ColumnOrgParser();
                    parse = columnOrgParser.parse(pasteDataVO);
                } else if (StringUtils.hasText(refTableName)) {
                    ColumnBaseDataParser columnBaseDataParser = new ColumnBaseDataParser();
                    parse = columnBaseDataParser.parse(pasteDataVO);
                } else if (ColumnValueType.DOUBLE.equals((Object)valueType)) {
                    ColumnNumberParser columnNumberParser = new ColumnNumberParser();
                    parse = columnNumberParser.parse(pasteDataVO);
                } else if (ColumnValueType.STRING.equals((Object)valueType)) {
                    ColumnStringParser columnStringParser = new ColumnStringParser();
                    parse = columnStringParser.parse(pasteDataVO);
                } else {
                    parse = pastValue;
                }
                retRow.put(columnDefine.getColumnCode(), parse);
            }
            ret.add(retRow);
        }
        return ret;
    }
}

