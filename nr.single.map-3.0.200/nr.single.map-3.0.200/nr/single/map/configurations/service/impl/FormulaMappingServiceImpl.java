/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 */
package nr.single.map.configurations.service.impl;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.internal.bean.IllegalData;
import nr.single.map.configurations.service.MappingConfigService;
import nr.single.map.configurations.service.ParseMapping;
import nr.single.map.data.facade.SingleFileFormulaItem;
import nr.single.map.data.internal.SingleFileFormulaItemImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FormulaMappingServiceImpl
extends ParseMapping {
    private static final Logger logger = LoggerFactory.getLogger(FormulaMappingServiceImpl.class);
    private static final String NET_FORM_CODE = "\u7f51\u62a5\u62a5\u8868\u6807\u8bc6";
    private static final String NET_CODE = "\u7f51\u62a5\u516c\u5f0f\u6807\u8bc6";
    private static final String SINGLE_CODE = "\u5e73\u53f0\u516c\u5f0f\u6807\u8bc6";
    @Autowired
    private MappingConfigService mappingConfigService;
    @Autowired
    private IDesignTimeViewController designView;

    @Override
    public int parseFileMapping(byte[] files) {
        int count = 0;
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader((InputStream)new ByteArrayInputStream(files), "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        BufferedReader br = new BufferedReader(reader);
        String lineTxt = null;
        String formCode = null;
        boolean isFormCodeLine = false;
        try {
            int idx = 0;
            int formIdx = 0;
            while ((lineTxt = br.readLine()) != null) {
                isFormCodeLine = false;
                if ("".equals(lineTxt.trim()) || ++idx < 3) continue;
                String content = lineTxt.trim();
                SingleFileFormulaItemImpl item = new SingleFileFormulaItemImpl();
                if (content.contains("---")) {
                    formCode = content.replace("-", "").trim();
                    isFormCodeLine = true;
                    formIdx = idx;
                } else {
                    String[] formulaCode = content.split("\\s+");
                    if (formulaCode.length > 1) {
                        item.setNetFormulaCode(formulaCode[0].trim());
                        item.setSingleFormulaCode(formulaCode[1] == null ? "" : formulaCode[1].trim());
                        item.setNetFormCode(formCode);
                    }
                }
                if (isFormCodeLine) continue;
                item.setImportIndex(String.valueOf(formIdx) + "_" + String.valueOf(idx));
                this.addFormulaInfo(item);
                ++count;
            }
            br.close();
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return count;
    }

    @Override
    public void convertFromConfig(ISingleMappingConfig config) {
        List<SingleFileFormulaItem> infos = config.getFormulaInfos();
        this.setFormulaInfo(infos);
    }

    @Override
    public String buildTextFile() {
        List<SingleFileFormulaItem> infos = this.getFormulaInfo();
        StringBuffer sbs = new StringBuffer();
        sbs.append("---").append(NET_FORM_CODE).append("---").append("\r\n").append(NET_CODE).append(" ").append(SINGLE_CODE).append("\r\n");
        Map<String, List<SingleFileFormulaItem>> groupFilter = infos.stream().filter(e -> e.getNetFormCode() != null).collect(Collectors.groupingBy(SingleFileFormulaItem::getNetFormCode));
        List<DesignFormGroupDefine> groupDefines = this.designView.queryRootGroupsByFormScheme(this.getSchemeKey()).stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
        groupDefines.forEach(e -> {
            List<DesignFormDefine> forms = this.designView.getAllFormsInGroup(e.getKey(), true).stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
            forms.forEach(f -> {
                List formulaList = (List)groupFilter.get(f.getFormCode());
                if (formulaList != null) {
                    sbs.append("---" + f.getFormCode() + "---");
                    sbs.append("\r\n");
                    formulaList.forEach(formula -> {
                        sbs.append(formula.getNetFormulaCode());
                        sbs.append(" ");
                        sbs.append(formula.getSingleFormulaCode());
                        sbs.append("\r\n");
                    });
                }
            });
        });
        return sbs.toString();
    }

    @Override
    public IllegalData getErrorData() {
        return null;
    }
}

