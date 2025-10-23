/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 */
package com.jiuqi.nr.task.form.service.check;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.task.form.dto.CheckResult;
import com.jiuqi.nr.task.form.dto.ErrorData;
import com.jiuqi.nr.task.form.dto.FormDesignerDTO;
import com.jiuqi.nr.task.form.dto.FormParamType;
import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import com.jiuqi.nr.task.form.ext.face.ConfigExt;
import com.jiuqi.nr.task.form.link.dto.DataLinkSettingDTO;
import com.jiuqi.nr.task.form.service.IConfigExtCheckService;
import com.jiuqi.nr.task.form.service.IFormCheckService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkCheckService
implements IFormCheckService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IConfigExtCheckService configExtCheckService;

    private List<ConfigDTO> getConfigExtList(List<DataLinkSettingDTO> dataLinkSetting) {
        return dataLinkSetting.stream().filter(l -> l.getConfigData() != null).map(ConfigExt::getConfigData).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private ErrorData error(String message) {
        ErrorData errorData = new ErrorData();
        errorData.setParamType(FormParamType.LINK);
        errorData.setMessage(message);
        return errorData;
    }

    @Override
    public CheckResult doCheck(FormDesignerDTO formDesigner) {
        CheckResult checkExt;
        List<DataLinkSettingDTO> dataLinkSetting = formDesigner.getDataLinkSetting();
        String formKey = formDesigner.getForm().getKey();
        CheckResult checkResult = CheckResult.successResult();
        CheckResult checkLink = this.checkLink(formDesigner);
        if (checkLink.isError()) {
            checkResult.addErrorData(checkLink.getErrorData());
        }
        if ((checkExt = this.configExtCheckService.checkLinkConfigs(formKey, this.getConfigExtList(dataLinkSetting))).isError()) {
            checkResult.addErrorData(checkExt.getErrorData());
        }
        return checkResult;
    }

    @Override
    public CheckResult doCheck(String formKey) {
        return null;
    }

    private CheckResult checkLink(FormDesignerDTO formDesigner) {
        CheckResult checkResult = CheckResult.successResult();
        String formKey = formDesigner.getForm().getKey();
        List<DataLinkSettingDTO> datas = formDesigner.getDataLinkSetting();
        List dataLinkDefines = this.designTimeViewController.listDataLinkByForm(formKey);
        LinkNumChecker checker = new LinkNumChecker(dataLinkDefines.size() + datas.size());
        dataLinkDefines.forEach(dataLink -> checker.add(dataLink.getKey(), dataLink.getPosY(), dataLink.getPosX()));
        datas.forEach(data -> {
            switch (data.getStatus()) {
                case DELETE: {
                    checker.delete(data.getKey());
                    break;
                }
                case MODIFY: 
                case NEW: {
                    checker.add(data.getKey(), data.getPosY(), data.getPosX());
                    break;
                }
            }
        });
        List<String> check = checker.check();
        for (String s : check) {
            checkResult.addErrorData(this.error(String.format("\u94fe\u63a5\u7269\u7406\u5750\u6807\u3010%s\u3011\u91cd\u590d", s)));
        }
        return checkResult;
    }

    private static class LinkNumChecker {
        private final Map<String, String> map;

        public LinkNumChecker(int num) {
            this.map = new HashMap<String, String>(num);
        }

        public void add(String linkKey, int a, int b) {
            if (a <= 0 || b <= 0) {
                return;
            }
            String key = a + "_" + b;
            this.map.put(linkKey, key);
        }

        public void delete(String linkKey) {
            this.map.remove(linkKey);
        }

        public List<String> check() {
            ArrayList<String> error = new ArrayList<String>();
            Set<String> links = this.map.keySet();
            HashSet<String> data = new HashSet<String>(this.map.values());
            links.forEach(link -> {
                String key = this.map.get(link);
                if (data.contains(key)) {
                    this.map.remove(key);
                    data.remove(key);
                } else {
                    error.add(key);
                }
            });
            error.sort(String::compareTo);
            return error;
        }
    }
}

