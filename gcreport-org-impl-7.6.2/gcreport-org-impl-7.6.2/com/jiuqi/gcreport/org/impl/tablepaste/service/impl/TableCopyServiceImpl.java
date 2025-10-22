/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.impl.tablepaste.service.impl;

import com.jiuqi.gcreport.org.impl.tablepaste.common.ColumnBaseDataParser;
import com.jiuqi.gcreport.org.impl.tablepaste.common.ColumnIntegerParser;
import com.jiuqi.gcreport.org.impl.tablepaste.common.ColumnNumberParser;
import com.jiuqi.gcreport.org.impl.tablepaste.common.ColumnOrgParser;
import com.jiuqi.gcreport.org.impl.tablepaste.common.ColumnStringParser;
import com.jiuqi.gcreport.org.impl.tablepaste.service.TableCopyService;
import com.jiuqi.gcreport.org.impl.tablepaste.vo.ColumnDefineVO;
import com.jiuqi.gcreport.org.impl.tablepaste.vo.ColumnValueType;
import com.jiuqi.gcreport.org.impl.tablepaste.vo.PasteDataVO;
import com.jiuqi.gcreport.org.impl.tablepaste.vo.PasteParamVO;
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
                if (StringUtils.hasText(refTableName) && refTableName.toUpperCase().startsWith("MD_ORG")) {
                    ColumnOrgParser columnOrgParser = new ColumnOrgParser();
                    parse = columnOrgParser.parse(pasteDataVO);
                } else if (StringUtils.hasText(refTableName)) {
                    ColumnBaseDataParser columnBaseDataParser = new ColumnBaseDataParser();
                    parse = columnBaseDataParser.parse(pasteDataVO);
                } else if (valueType.equals((Object)ColumnValueType.DOUBLE)) {
                    ColumnNumberParser columnNumberParser = new ColumnNumberParser();
                    parse = columnNumberParser.parse(pasteDataVO);
                } else if (valueType.equals((Object)ColumnValueType.STRING)) {
                    ColumnStringParser columnStringParser = new ColumnStringParser();
                    parse = columnStringParser.parse(pasteDataVO);
                } else if (valueType.equals((Object)ColumnValueType.INTEGER)) {
                    ColumnIntegerParser columnIntegerParser = new ColumnIntegerParser();
                    parse = columnIntegerParser.parse(pasteDataVO);
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

