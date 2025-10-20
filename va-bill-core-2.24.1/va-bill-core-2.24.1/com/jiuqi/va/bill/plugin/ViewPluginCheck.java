/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.domain.PluginCheckResultDTO
 *  com.jiuqi.va.biz.domain.PluginCheckResultVO
 *  com.jiuqi.va.biz.domain.PluginCheckType
 *  com.jiuqi.va.biz.impl.action.ActionManagerImpl
 *  com.jiuqi.va.biz.impl.data.DataDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataFieldDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataTableDefineImpl
 *  com.jiuqi.va.biz.impl.value.NamedContainerImpl
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.PluginCheck
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.biz.view.impl.CompositeImpl
 *  com.jiuqi.va.biz.view.impl.ControlManagerImpl
 *  com.jiuqi.va.biz.view.impl.ViewDefineImpl
 *  com.jiuqi.va.biz.view.intf.Composite
 */
package com.jiuqi.va.bill.plugin;

import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.biz.domain.PluginCheckResultDTO;
import com.jiuqi.va.biz.domain.PluginCheckResultVO;
import com.jiuqi.va.biz.domain.PluginCheckType;
import com.jiuqi.va.biz.impl.action.ActionManagerImpl;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.value.NamedContainerImpl;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginCheck;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.view.impl.CompositeImpl;
import com.jiuqi.va.biz.view.impl.ControlManagerImpl;
import com.jiuqi.va.biz.view.impl.ViewDefineImpl;
import com.jiuqi.va.biz.view.intf.Composite;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class ViewPluginCheck
implements PluginCheck {
    private static final Logger logger = LoggerFactory.getLogger(ViewPluginCheck.class);
    @Autowired
    private ActionManagerImpl actionManager;

    public String getName() {
        return "view";
    }

    public Class<? extends PluginDefine> getPluginDefine() {
        return ViewDefineImpl.class;
    }

    public PluginCheckResultVO checkPlugin(PluginDefine pluginDefine, ModelDefine modelDefine) {
        ViewDefineImpl viewDefine = (ViewDefineImpl)pluginDefine;
        PluginCheckResultVO pluginCheckResultVO = new PluginCheckResultVO();
        pluginCheckResultVO.setPluginName(this.getName());
        ArrayList<PluginCheckResultDTO> checkResults = new ArrayList<PluginCheckResultDTO>();
        if (viewDefine.getSchemes() == null) {
            PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u6a21\u677f\u4e0d\u80fd\u4e3a\u7a7a", "");
            checkResults.add(checkResultDTO);
            pluginCheckResultVO.setCheckResults(checkResults);
            return pluginCheckResultVO;
        }
        int n = viewDefine.getSchemes().size();
        for (int m = 0; m < n; ++m) {
            CompositeImpl composite;
            int i;
            Map scheme = (Map)viewDefine.getSchemes().get(m);
            Object schemeTitle = scheme.get("title");
            Object schemeCode = scheme.get("code");
            Composite template = null;
            if (scheme.get("template") != null && ((Map)scheme.get("template")).size() != 0) {
                template = (Composite)ControlManagerImpl.createControl((Map)((Map)scheme.get("template")));
            }
            if (template == null) {
                PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + schemeTitle + "\u3011\uff1a\uff1a\u754c\u9762\u6a21\u677f\u4e0d\u80fd\u4e3a\u7a7a", "");
                checkResults.add(checkResultDTO);
                pluginCheckResultVO.setCheckResults(checkResults);
                continue;
            }
            if ("wizard".equals(scheme.get("type"))) {
                CompositeImpl composite2 = (CompositeImpl)template;
                List wizardInfo = composite2.getWizardInfo();
                if (wizardInfo.size() == 0) {
                    PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + schemeTitle + "\u3011\uff1a\uff1a\u5411\u5bfc\u914d\u7f6e\u4e0d\u80fd\u4e3a\u7a7a", "");
                    checkResults.add(checkResultDTO);
                    pluginCheckResultVO.setCheckResults(checkResults);
                    continue;
                }
                List schemes = viewDefine.getSchemes();
                Map<String, String> schemeMap = schemes.stream().collect(Collectors.toMap(o -> (String)o.get("code"), o -> (String)o.get("title")));
                i = 1;
                HashSet<String> nodeMaps = new HashSet<String>();
                HashSet<String> nodes = new HashSet<String>();
                for (Map guid : wizardInfo) {
                    PluginCheckResultDTO checkResultDTO;
                    Object curView = guid.get("curView");
                    Object toView = guid.get("toView");
                    if (ObjectUtils.isEmpty(curView) && ObjectUtils.isEmpty(toView)) {
                        checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + schemeTitle + "\u3011\uff1a" + String.format("\u7b2c%s\u884c\u5f53\u524d\u754c\u9762\u4e0e\u8df3\u8f6c\u754c\u9762\u4e0d\u80fd\u4e3a\u7a7a", i), "");
                        checkResults.add(checkResultDTO);
                    } else if (ObjectUtils.isEmpty(curView) && !ObjectUtils.isEmpty(toView)) {
                        checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + schemeTitle + "\u3011\uff1a" + String.format("\u7b2c%s\u884c\u5f53\u524d\u754c\u9762\u4e0d\u80fd\u4e3a\u7a7a", i), "");
                        checkResults.add(checkResultDTO);
                    } else if (!ObjectUtils.isEmpty(curView) && ObjectUtils.isEmpty(toView)) {
                        checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + schemeTitle + "\u3011\uff1a" + String.format("\u7b2c%s\u884c\u8df3\u8f6c\u754c\u9762\u4e0d\u80fd\u4e3a\u7a7a", i), "");
                        checkResults.add(checkResultDTO);
                    } else {
                        if (curView.equals(toView)) {
                            checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + schemeTitle + "\u3011\uff1a" + String.format("\u7b2c%s\u884c\u5f53\u524d\u754c\u9762\u4e0e\u8df3\u8f6c\u754c\u9762\u4e0d\u80fd\u76f8\u540c", i), "");
                            checkResults.add(checkResultDTO);
                        }
                        if (!nodeMaps.add(curView + "||" + toView)) {
                            checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + schemeTitle + "\u3011\uff1a\u5b58\u5728\u91cd\u590d\u884c", "");
                            checkResults.add(checkResultDTO);
                        }
                        nodes.add((String)curView);
                        nodes.add((String)toView);
                    }
                    if (!ObjectUtils.isEmpty(curView) && !schemeMap.containsKey(curView)) {
                        checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + schemeTitle + "\u3011\uff1a" + String.format("\u7b2c%s\u884c\u5f53\u524d\u754c\u9762\u4e0d\u5b58\u5728", i), "");
                        checkResults.add(checkResultDTO);
                    }
                    if (!ObjectUtils.isEmpty(toView) && !schemeMap.containsKey(toView)) {
                        checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + schemeTitle + "\u3011\uff1a" + String.format("\u7b2c%s\u884c\u8df3\u8f6c\u754c\u9762\u4e0d\u5b58\u5728", i), "");
                        checkResults.add(checkResultDTO);
                    }
                    ++i;
                }
                if (checkResults.size() > 0) {
                    pluginCheckResultVO.setCheckResults(checkResults);
                    continue;
                }
                int matrixSize = nodes.size();
                if (matrixSize == 0) continue;
                ArrayList<String> checkNodes = new ArrayList<String>();
                int[][] adjacencyMatrix = new int[matrixSize][matrixSize];
                ArrayList<String> values = new ArrayList<String>();
                for (String nodeMap : nodeMaps) {
                    String[] temp = nodeMap.split("\\|\\|");
                    String string = temp[0];
                    String toView = temp[1];
                    values.add(toView);
                    int startIndex = this.addNode(string, checkNodes);
                    int endIndex = this.addNode(toView, checkNodes);
                    if (startIndex < 0 || endIndex < 0) continue;
                    adjacencyMatrix[startIndex][endIndex] = 1;
                }
                ArrayList<Integer> trace = new ArrayList<Integer>();
                ArrayList<String> resluts = new ArrayList<String>();
                this.findCycle(0, trace, resluts, checkNodes, adjacencyMatrix, schemeMap);
                for (String string : resluts) {
                    PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + schemeTitle + "\u3011\uff1a\u51fa\u73b0\u5faa\u73af\uff1a" + string, "");
                    checkResults.add(checkResultDTO);
                }
                HashSet<String> entrances = new HashSet<String>();
                for (String node : nodes) {
                    if (values.contains(node)) continue;
                    entrances.add(schemeMap.get(node));
                }
                if (entrances.size() > 1) {
                    PluginCheckResultDTO pluginCheckResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + schemeTitle + "\u3011\uff1a\u5b58\u5728\u591a\u4e2a\u5165\u53e3\uff1a" + entrances, "");
                    checkResults.add(pluginCheckResultDTO);
                }
                pluginCheckResultVO.setCheckResults(checkResults);
                continue;
            }
            List controls = template.getChildren().stream().filter(control -> "v-tool-bar".equals(control.getType())).collect(Collectors.toList());
            if (controls.size() == 1 && (composite = (CompositeImpl)controls.get(0)) != null && composite.getChildren().size() > 0) {
                HashSet titleSet = new HashSet();
                composite.getChildren().stream().forEach(control -> {
                    String title = (String)Convert.cast(control.getProps().get("title"), String.class);
                    String action = (String)Convert.cast(((Map)control.getProps().get("action")).get("type"), String.class);
                    if (!StringUtils.hasText(title)) {
                        PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + schemeTitle + "\u3011\uff1a\u52a8\u4f5c\u6807\u9898\u4e0d\u80fd\u4e3a\u7a7a\uff1a" + action, control.getId().toString() + "," + schemeCode);
                        checkResults.add(checkResultDTO);
                    } else if (!titleSet.add(title)) {
                        PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.WARN, "\u754c\u9762\u65b9\u6848\u3010" + schemeTitle + "\u3011\uff1a\u52a8\u4f5c\u6807\u9898\u91cd\u590d\uff1a" + title, control.getId().toString() + "," + schemeCode);
                        checkResults.add(checkResultDTO);
                    }
                });
            }
            DataDefineImpl dataDefine = (DataDefineImpl)modelDefine.getPlugins().get(DataDefineImpl.class);
            List tableList = dataDefine.getTableList();
            HashMap<String, NamedContainerImpl<? extends DataFieldDefineImpl>> tableFields = new HashMap<String, NamedContainerImpl<? extends DataFieldDefineImpl>>();
            for (i = 0; i < tableList.size(); ++i) {
                DataTableDefineImpl dataTableDefine = (DataTableDefineImpl)tableList.get(i);
                tableFields.put(dataTableDefine.getName(), dataTableDefine.getFields());
            }
            Map props = template.getProps();
            List<PluginCheckResultDTO> check = this.check(schemeTitle, schemeCode, props, checkResults, tableFields, modelDefine);
            pluginCheckResultVO.setCheckResults(check);
        }
        return pluginCheckResultVO;
    }

    private int addNode(String nodeName, List<String> nodes) {
        if (!nodes.contains(nodeName)) {
            nodes.add(nodeName);
            return nodes.size() - 1;
        }
        return nodes.indexOf(nodeName);
    }

    private void findCycle(int v, List<Integer> trace, List<String> reslut, List<String> nodes, int[][] adjacencyMatrix, Map<String, String> schemeMap) {
        int j = trace.indexOf(v);
        if (j != -1) {
            StringBuffer sb = new StringBuffer();
            String startNode = nodes.get(trace.get(j));
            while (j < trace.size()) {
                sb.append(schemeMap.get(nodes.get(trace.get(j))) + "->");
                ++j;
            }
            reslut.add(sb + schemeMap.get(startNode));
            return;
        }
        trace.add(v);
        for (int i = 0; i < nodes.size(); ++i) {
            if (adjacencyMatrix[v][i] != 1) continue;
            this.findCycle(i, trace, reslut, nodes, adjacencyMatrix, schemeMap);
        }
        trace.remove(trace.size() - 1);
    }

    public List<PluginCheckResultDTO> check(Object title, Object code, Map<String, Object> props, List<PluginCheckResultDTO> checkResults, Map<String, NamedContainerImpl<? extends DataFieldDefineImpl>> tableFields, ModelDefine modelDefine) {
        List children;
        String type = (String)props.get("type");
        if (StringUtils.hasText(type)) {
            HashMap<String, Object> prop;
            Map binding2;
            if ("v-grid".equals(type)) {
                this.checkBindingFields(title, props, checkResults, tableFields);
            }
            if ("v-tool-item".equals(type) || "v-button".equals(type)) {
                Map attachmentNum;
                Map action = (Map)props.get("action");
                if (CollectionUtils.isEmpty(action)) {
                    return null;
                }
                Action actionType = (Action)this.actionManager.find(BillUtils.valueToString(action.get("type")));
                if (actionType == null) {
                    logger.error("\u5f53\u524d\u52a8\u4f5c\u4e0d\u5b58\u5728: " + action.get("type"));
                    return null;
                }
                Object actionParams = action.get("params");
                List pluginCheckResultDTOS = null;
                if (actionParams instanceof Map) {
                    pluginCheckResultDTOS = actionType.checkActionConfig(modelDefine, (Map)actionParams);
                }
                if (pluginCheckResultDTOS != null) {
                    checkResults.addAll(pluginCheckResultDTOS);
                }
                if (!action.get("type").equals("bill-attachment-manage")) {
                    return null;
                }
                Map params = (Map)action.get("params");
                if (CollectionUtils.isEmpty(params)) {
                    return null;
                }
                Map attachmentCode = (Map)params.get("attachmentCode");
                if (attachmentCode != null) {
                    this.checkBinding(title, attachmentCode, checkResults, tableFields);
                }
                if ((attachmentNum = (Map)params.get("attachmentNum")) != null) {
                    this.checkBinding(title, attachmentNum, checkResults, tableFields);
                }
                if (params.get("version") != null && ObjectUtils.isEmpty(params.get("attType"))) {
                    PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + title + "\u3011\uff1a\u9644\u4ef6\u7ba1\u7406\u6309\u94ae\u4e2d\u9644\u4ef6\u7c7b\u578b\u5fc5\u586b", props.get("id").toString() + "," + code);
                    checkResults.add(checkResultDTO);
                }
            }
            if ("v-upload-list".equals(type)) {
                this.checkUploadField(title, props, checkResults);
                this.checkBinding(title, props, checkResults, tableFields);
                binding2 = (Map)props.get("binding2");
                prop = new HashMap<String, Object>();
                prop.put("binding", binding2);
                this.checkBinding(title, prop, checkResults, tableFields);
                if (props.get("version") != null) {
                    List attTypes = (List)props.get("attType");
                    for (Map attType : attTypes) {
                        if (!ObjectUtils.isEmpty(attType.get("attType"))) continue;
                        PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + title + "\u3011\uff1a\u9644\u4ef6\u5217\u8868\u4e2d\u9644\u4ef6\u7c7b\u578b\u5fc5\u586b", "");
                        checkResults.add(checkResultDTO);
                    }
                }
            }
            if ("v-upload".equals(type)) {
                this.checkBinding(title, props, checkResults, tableFields);
                if (props.get("version") != null && ObjectUtils.isEmpty(props.get("attType"))) {
                    PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + title + "\u3011\uff1a\u9644\u4ef6\u6309\u94ae\u4e2d\u9644\u4ef6\u7c7b\u578b\u5fc5\u586b", props.get("id").toString() + "," + code);
                    checkResults.add(checkResultDTO);
                }
            }
            if ("v-tree-table".equals(type)) {
                this.checkBindingFields(title, props, checkResults, tableFields);
            }
            if ("v-detail-area".equals(type)) {
                this.checkBindingFields(title, props, checkResults, tableFields);
            }
            if ("bill-picture".equals(type)) {
                Map attQuote = (Map)props.get("attQuote");
                if (attQuote != null) {
                    this.checkBinding(title, attQuote, checkResults, tableFields);
                }
                if (props.get("bindAttachment") != null && ((Boolean)props.get("bindAttachment")).booleanValue() && props.get("version") != null && ObjectUtils.isEmpty(props.get("attType"))) {
                    PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + title + "\u3011\uff1a\u56fe\u7247\u63a7\u4ef6\u4e2d\u9644\u4ef6\u7c7b\u578b\u5fc5\u586b", props.get("id").toString() + "," + code);
                    checkResults.add(checkResultDTO);
                }
            }
            if ("v-bill-show-main".equals(type)) {
                this.checkBinding(title, props, checkResults, tableFields);
                binding2 = (Map)props.get("binding2");
                prop = new HashMap();
                prop.put("binding", binding2);
                this.checkBinding(title, prop, checkResults, tableFields);
            } else {
                this.checkBinding(title, props, checkResults, tableFields);
            }
        }
        if ((children = (List)props.get("children")) != null) {
            children.forEach(o -> this.check(title, code, (Map<String, Object>)o, checkResults, tableFields, modelDefine));
        }
        return checkResults;
    }

    private void checkUploadField(Object title, Map<String, Object> props, List<PluginCheckResultDTO> checkResults) {
        Map binding = (Map)props.get("binding");
        Map binding2 = (Map)props.get("binding2");
        Object tableName = binding.get("tableName");
        if (ObjectUtils.isEmpty(tableName)) {
            PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + title + "\u3011\uff1a\u9644\u4ef6\u5217\u8868\u5b57\u6bb5\u672a\u7ed1\u5b9a", "");
            checkResults.add(checkResultDTO);
            return;
        }
        Object tableName2 = binding2.get("tableName");
        if (ObjectUtils.isEmpty(tableName2)) {
            PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + title + "\u3011\uff1a\u9644\u4ef6\u5217\u8868\u5b57\u6bb5\u672a\u7ed1\u5b9a", "");
            checkResults.add(checkResultDTO);
            return;
        }
        Object fieldName = binding.get("fieldName");
        if (ObjectUtils.isEmpty(fieldName)) {
            PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + title + "\u3011\uff1a\u9644\u4ef6\u5217\u8868\u5b57\u6bb5\u672a\u7ed1\u5b9a", "");
            checkResults.add(checkResultDTO);
            return;
        }
        Object fieldName2 = binding2.get("fieldName");
        if (ObjectUtils.isEmpty(fieldName2)) {
            PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + title + "\u3011\uff1a\u9644\u4ef6\u5217\u8868\u5b57\u6bb5\u672a\u7ed1\u5b9a", "");
            checkResults.add(checkResultDTO);
            return;
        }
    }

    public void checkBinding(Object title, Map<String, Object> props, List<PluginCheckResultDTO> checkResults, Map<String, NamedContainerImpl<? extends DataFieldDefineImpl>> tableFields) {
        Map binding = (Map)props.get("binding");
        if (binding == null) {
            return;
        }
        String tableName = (String)binding.get("tableName");
        if (!StringUtils.hasText(tableName)) {
            return;
        }
        if (tableFields.get(tableName) == null) {
            PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + title + "\u3011\uff1a\u8868\u5728\u5355\u636e\u8bbe\u8ba1\u4e2d\u5fc5\u987b\u5b58\u5728\uff1a" + tableName, "");
            checkResults.add(checkResultDTO);
        } else {
            String fieldName = (String)binding.get("fieldName");
            if (!StringUtils.hasText(fieldName)) {
                return;
            }
            NamedContainerImpl<? extends DataFieldDefineImpl> namedContainer = tableFields.get(tableName);
            for (int i = 0; i < namedContainer.size(); ++i) {
                if (!((DataFieldDefineImpl)namedContainer.get(i)).getName().equals(fieldName)) continue;
                return;
            }
            PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + title + "\u3011\uff1a\u5b57\u6bb5\u5728\u5355\u636e\u8bbe\u8ba1\u4e2d\u5fc5\u987b\u5b58\u5728\uff1a\n" + tableName + "\n\u3010" + fieldName + "\u3011", "");
            checkResults.add(checkResultDTO);
        }
    }

    public void checkBindingFields(Object title, Map<String, Object> props, List<PluginCheckResultDTO> checkResults, Map<String, NamedContainerImpl<? extends DataFieldDefineImpl>> tableFields) {
        Map binding = (Map)props.get("binding");
        if (binding == null) {
            return;
        }
        String tableName = (String)binding.get("tableName");
        if (!StringUtils.hasText(tableName)) {
            return;
        }
        if (tableFields.get(tableName) == null) {
            PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + title + "\u3011\uff1a\u8868\u5728\u5355\u636e\u8bbe\u8ba1\u4e2d\u5fc5\u987b\u5b58\u5728\uff1a" + tableName, "");
            checkResults.add(checkResultDTO);
        } else {
            boolean flag;
            int i;
            List fields = (List)binding.get("fields");
            if (CollectionUtils.isEmpty(fields)) {
                return;
            }
            NamedContainerImpl<? extends DataFieldDefineImpl> namedContainer = tableFields.get(tableName);
            for (i = 0; i < fields.size(); ++i) {
                String name = (String)((Map)fields.get(i)).get("name");
                if (!StringUtils.hasText(name)) continue;
                flag = true;
                for (int i1 = 0; i1 < namedContainer.size(); ++i1) {
                    if (!((DataFieldDefineImpl)namedContainer.get(i1)).getName().equals(name)) continue;
                    flag = false;
                    break;
                }
                if (!flag) continue;
                PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + title + "\u3011\uff1a\u5b57\u6bb5\u5728\u5355\u636e\u8bbe\u8ba1\u4e2d\u5fc5\u987b\u5b58\u5728\uff1a\n" + tableName + "\n\u3010" + name + "\u3011", "");
                checkResults.add(checkResultDTO);
            }
            if (((Map)fields.get(0)).get("fieldName") == null) {
                return;
            }
            for (i = 0; i < fields.size(); ++i) {
                String fieldName = (String)((Map)fields.get(i)).get("fieldName");
                if (!StringUtils.hasText(fieldName)) continue;
                flag = true;
                for (int i1 = 0; i1 < namedContainer.size(); ++i1) {
                    if (!((DataFieldDefineImpl)namedContainer.get(i1)).getName().equals(fieldName)) continue;
                    flag = false;
                    break;
                }
                if (!flag) continue;
                PluginCheckResultDTO checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u754c\u9762\u65b9\u6848\u3010" + title + "\u3011\uff1a\u5b57\u6bb5\u5728\u5355\u636e\u8bbe\u8ba1\u4e2d\u5fc5\u987b\u5b58\u5728\uff1a\n" + tableName + "\n\u3010" + fieldName + "\u3011", "");
                checkResults.add(checkResultDTO);
            }
        }
    }

    private PluginCheckResultDTO getPluginCheckResultDTO(PluginCheckType checkType, String message, String objectPath) {
        PluginCheckResultDTO pluginCheckResultDTO = new PluginCheckResultDTO();
        pluginCheckResultDTO.setObjectpath(objectPath);
        pluginCheckResultDTO.setType(checkType);
        pluginCheckResultDTO.setMessage(message);
        return pluginCheckResultDTO;
    }
}

