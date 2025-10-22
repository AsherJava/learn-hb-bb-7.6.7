/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  javax.annotation.Resource
 *  nr.single.map.data.exception.SingleDataException
 */
package nr.single.client.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import nr.single.client.internal.service.export.ExportDataCheckResultImpl;
import nr.single.client.service.ISingleDataCheckService;
import nr.single.map.data.exception.SingleDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleDataCheckServiceImpl
implements ISingleDataCheckService {
    private static final Logger logger = LoggerFactory.getLogger(ExportDataCheckResultImpl.class);
    @Resource
    private IRunTimeViewController runtimeView;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    private static final Set<String> cFlagStrings2 = new HashSet<String>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "X", "Y", "Z", "_", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));

    @Override
    public Set<String> analFormulaExpToTables(String aExression, String curTableFlag, String curFormKey, String formSchemeKey, boolean isOnlyFloatTable) throws SingleDataException {
        HashSet<String> tables = new HashSet<String>();
        if (StringUtils.isNotEmpty((String)aExression)) {
            try {
                String firstTable = "";
                String otherExp = aExression;
                int id1 = otherExp.indexOf(91);
                int id2 = otherExp.indexOf(93);
                int id5 = otherExp.indexOf(64);
                while (id1 >= 0 && id2 > id1) {
                    String leftS = otherExp.substring(0, id1);
                    String exp = otherExp.substring(id1 + 1, id2);
                    otherExp = otherExp.substring(id2 + 1, otherExp.length());
                    String netFormCode = "";
                    String netTableCode = "";
                    String netFormKey = "";
                    for (int i = leftS.length() - 1; i >= 0 && cFlagStrings2.contains(String.valueOf(leftS.charAt(i))); --i) {
                        netFormCode = String.valueOf(leftS.charAt(i)) + netFormCode;
                    }
                    if (StringUtils.isEmpty((String)netFormCode) && StringUtils.isNotEmpty((String)curTableFlag)) {
                        netFormCode = curTableFlag;
                        netFormKey = curFormKey;
                    } else if (StringUtils.isNotEmpty((String)netFormCode)) {
                        try {
                            FormDefine formDefine = this.runtimeView.queryFormByCodeInScheme(formSchemeKey, netFormCode);
                            if (formDefine != null) {
                                netFormKey = formDefine.getKey();
                            }
                        }
                        catch (Exception e) {
                            logger.error(e.getMessage(), e);
                            throw new SingleDataException((Throwable)e);
                        }
                    }
                    if (!isOnlyFloatTable) {
                        if (StringUtils.isNotEmpty((String)netFormCode)) {
                            tables.add(netFormCode);
                        }
                    } else if (StringUtils.isNotEmpty((String)netFormKey)) {
                        int id3 = exp.indexOf(",");
                        if (id3 > 0) {
                            String rowNum = exp.substring(0, id3);
                            String colNum = exp.substring(id3 + 1, exp.length());
                            int id4 = colNum.indexOf(",");
                            if (id4 >= 0) {
                                colNum = colNum.substring(0, id4);
                            }
                            if (StringUtils.isNotEmpty((String)colNum)) {
                                colNum = colNum.trim();
                            }
                            if (StringUtils.isNotEmpty((String)rowNum)) {
                                rowNum = rowNum.trim();
                            }
                            if (StringUtils.isNotEmpty((String)colNum) && StringUtils.isNotEmpty((String)rowNum) && StringUtils.isNumeric((String)rowNum) && StringUtils.isNumeric((String)colNum)) {
                                DataRegionDefine region;
                                DataLinkDefine link = this.runtimeView.queryDataLinkDefineByColRow(netFormKey, Integer.parseInt(colNum), Integer.parseInt(rowNum));
                                if (link != null && (region = this.runtimeView.queryDataRegionDefine(link.getRegionKey())) != null && region.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
                                    Object expFormCode = netFormCode;
                                    if (id5 == id2 + 1) {
                                        expFormCode = (String)expFormCode + "@";
                                    }
                                    tables.add((String)expFormCode);
                                }
                            } else if (StringUtils.isNotEmpty((String)colNum) && !StringUtils.isNumeric((String)colNum)) {
                                String fieldCode = colNum;
                                if (!"*".equalsIgnoreCase(colNum)) {
                                    List regions = this.runtimeView.getAllRegionsInForm(netFormKey);
                                    for (DataRegionDefine region : regions) {
                                        List fieldKeys = this.runtimeView.getFieldKeysInRegion(region.getKey());
                                        List fieldList = this.dataSchemeSevice.getDataFields(fieldKeys);
                                        List collect = fieldList.stream().filter(o -> StringUtils.isNotEmpty((String)o.getCode()) && o.getCode().equalsIgnoreCase(fieldCode)).collect(Collectors.toList());
                                        if (collect == null || collect.isEmpty()) continue;
                                        if (region.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
                                            String expFormCode = netFormCode;
                                            if (id5 == id2 + 1) {
                                                expFormCode = expFormCode + "@";
                                            }
                                            tables.add(expFormCode);
                                        }
                                        break;
                                    }
                                }
                            }
                        } else {
                            String fieldCode = exp;
                            List regions = this.runtimeView.getAllRegionsInForm(netFormKey);
                            for (DataRegionDefine region : regions) {
                                List fieldKeys = this.runtimeView.getFieldKeysInRegion(region.getKey());
                                List fieldList = this.dataSchemeSevice.getDataFields(fieldKeys);
                                List collect = fieldList.stream().filter(o -> StringUtils.isNotEmpty((String)o.getCode()) && o.getCode().equalsIgnoreCase(fieldCode)).collect(Collectors.toList());
                                if (collect == null || collect.isEmpty()) continue;
                                if (region.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) break;
                                String expFormCode = netFormCode;
                                if (id5 == id2 + 1) {
                                    expFormCode = expFormCode + "@";
                                }
                                tables.add(expFormCode);
                                break;
                            }
                        }
                    }
                    if (!StringUtils.isEmpty((String)otherExp)) {
                        id1 = otherExp.indexOf(91);
                        id2 = otherExp.indexOf(93);
                        id5 = otherExp.indexOf(64);
                        continue;
                    }
                    break;
                }
            }
            catch (Exception e2) {
                logger.error("\u5206\u6790\u516c\u5f0f\u6d89\u53ca\u4e24\u4e2a\u6d6e\u52a8\u533a\u57df\u51fa\u9519\uff1a" + e2.getMessage() + "," + aExression, e2);
            }
        }
        return tables;
    }
}

