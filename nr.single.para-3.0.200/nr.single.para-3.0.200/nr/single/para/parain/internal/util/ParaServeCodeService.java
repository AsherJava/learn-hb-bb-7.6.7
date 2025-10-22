/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package nr.single.para.parain.internal.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import nr.single.para.parain.util.IParaServeCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParaServeCodeService
implements IParaServeCodeService {
    private static final Logger log = LoggerFactory.getLogger(ParaServeCodeService.class);
    private static final String OPTION_SERVER_CODE = "NET_SERVER_CODE";
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;

    @Override
    public String getServeCode() throws JQException {
        return this.iNvwaSystemOptionService.get("other-group", OPTION_SERVER_CODE);
    }

    @Override
    public String getCurServeCode() {
        String serveCode = null;
        try {
            serveCode = this.getServeCode();
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return serveCode;
    }

    @Override
    public boolean isSameServeCode(String serveCode) throws JQException {
        if (StringUtils.isEmpty((String)serveCode)) {
            return true;
        }
        return serveCode.equals(this.getServeCode());
    }
}

