/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.nr.message.manager;

import com.jiuqi.nr.message.manager.BusinessResponseEntity;
import com.jiuqi.nr.message.manager.pojo.MessageFormVO;
import com.jiuqi.nr.message.manager.pojo.MessageIdsDTO;
import com.jiuqi.nr.message.manager.pojo.MessageVO;
import com.jiuqi.nr.message.manager.pojo.PagingQueryVO;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface MessageMgrController {
    @PostMapping(value={"/push"})
    @ApiOperation(value="\u63a8\u9001\u6d88\u606f")
    public BusinessResponseEntity<Boolean> saveSubmit(@RequestBody MessageFormVO var1);

    @GetMapping(value={"/getUnread/{curPage}/{pageSize}"})
    @ApiOperation(value="\u5206\u9875\u67e5\u8be2\u672a\u8bfb\u6d88\u606f")
    public BusinessResponseEntity<PagingQueryVO> getUnread(@PathVariable(value="curPage") String var1, @PathVariable(value="pageSize") String var2);

    @GetMapping(value={"/getRead/{curPage}/{pageSize}"})
    @ApiOperation(value="\u5206\u9875\u67e5\u8be2\u5df2\u8bfb\u6d88\u606f")
    public BusinessResponseEntity<PagingQueryVO> getRead(@PathVariable(value="curPage") String var1, @PathVariable(value="pageSize") String var2);

    @GetMapping(value={"/getRubbish/{curPage}/{pageSize}"})
    @ApiOperation(value="\u5206\u9875\u67e5\u8be2\u56de\u6536\u7ad9\u6d88\u606f")
    public BusinessResponseEntity<PagingQueryVO> getRubbish(@PathVariable(value="curPage") String var1, @PathVariable(value="pageSize") String var2);

    @GetMapping(value={"/readUnread/{messageId}/{userId}"})
    @ApiOperation(value="\u8bfb\u53d6\u4e00\u6761\u672a\u8bfb\u6d88\u606f")
    public BusinessResponseEntity<MessageVO> readUnread(@PathVariable(value="messageId") String var1, @PathVariable(value="userId") String var2);

    @GetMapping(value={"/readRead/{messageId}"})
    @ApiOperation(value="\u8bfb\u53d6\u4e00\u6761\u5df2\u8bfb\u6d88\u606f")
    public BusinessResponseEntity<MessageVO> readRead(@PathVariable(value="messageId") String var1);

    @PostMapping(value={"/markRead"})
    @ApiOperation(value="\u6807\u8bb0\u5df2\u8bfb")
    public BusinessResponseEntity<Boolean> markRead(@RequestBody MessageIdsDTO var1);

    @PostMapping(value={"/unReadToRubbish"})
    @ApiOperation(value="\u5c06\u672a\u8bfb\u6d88\u606f\u653e\u5165\u56de\u6536\u7ad9")
    public BusinessResponseEntity<Boolean> unReadToRubbish(@RequestBody MessageIdsDTO var1);

    @PostMapping(value={"/readToRubbish"})
    @ApiOperation(value="\u5c06\u5df2\u8bfb\u6d88\u606f\u653e\u5165\u56de\u6536\u7ad9")
    public BusinessResponseEntity<Boolean> readToRubbish(@RequestBody MessageIdsDTO var1);

    @PostMapping(value={"/recover"})
    @ApiOperation(value="\u5c06\u56de\u6536\u7ad9\u6d88\u606f\u8fd8\u539f\u4e3a\u5df2\u8bfb\u6d88\u606f")
    public BusinessResponseEntity<Boolean> recover(@RequestBody MessageIdsDTO var1);

    @PostMapping(value={"/delete"})
    @ApiOperation(value="\u5220\u9664\u56de\u6536\u7ad9\u4e2d\u7684\u6d88\u606f")
    public BusinessResponseEntity<Boolean> delete(@RequestBody MessageIdsDTO var1);

    @GetMapping(value={"/getReadInfo/{messageId}"})
    @ApiOperation(value="\u67e5\u8be2\u5df2\u53d1\u9001\u6d88\u606f\u5df2\u8bfb\u7528\u6237\u5217\u8868")
    public BusinessResponseEntity<List<String>> getReadInfo(@PathVariable(value="messageId") String var1);

    @GetMapping(value={"/revoke/{messageId}"})
    @ApiOperation(value="\u64a4\u9500\u4e00\u6761\u5df2\u7ecf\u53d1\u9001\u7684\u6d88\u606f")
    public BusinessResponseEntity<Boolean> revoke(@PathVariable(value="messageId") String var1);

    @GetMapping(value={"/getUnreadByType/{curPage}/{pageSize}/{type}"})
    @ApiOperation(value="\u5206\u9875\u67e5\u8be2\u672a\u8bfb\u6d88\u606f")
    public BusinessResponseEntity<PagingQueryVO> getUnreadByType(@PathVariable(value="curPage") String var1, @PathVariable(value="pageSize") String var2, @PathVariable(value="type") int var3);

    @GetMapping(value={"/getReadByType/{curPage}/{pageSize}/{type}"})
    @ApiOperation(value="\u5206\u9875\u67e5\u8be2\u5df2\u8bfb\u6d88\u606f")
    public BusinessResponseEntity<PagingQueryVO> getReadByType(@PathVariable(value="curPage") String var1, @PathVariable(value="pageSize") String var2, @PathVariable(value="type") int var3);

    @GetMapping(value={"/getRubbishByType/{curPage}/{pageSize}/{type}"})
    @ApiOperation(value="\u5206\u9875\u67e5\u8be2\u56de\u6536\u7ad9\u6d88\u606f")
    public BusinessResponseEntity<PagingQueryVO> getRubbishByType(@PathVariable(value="curPage") String var1, @PathVariable(value="pageSize") String var2, @PathVariable(value="type") int var3);
}

