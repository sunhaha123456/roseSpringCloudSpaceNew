package com.rose.controler;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.rose.data.dto.TestDto;
import com.rose.parent.common.data.response.StringResponse;
import com.rose.parent.common.exception.SentinelCaputeException;
import com.rose.parent.common.util.JsonUtil;
import com.rose.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user/test")
public class TestControler {

    @Inject
    private TestService testService;

    @GetMapping(value = "/b1")
    public void b1(@RequestParam(value = "param1") Long param1, @RequestParam(value = "param2") String param2) {
        log.info("服务rose-content-server-b，/user/test/b1接口，接收到参数：{},{}", param1, param2);
    }

    @PostMapping(value = "/b2")
    public void b2(@RequestBody TestDto param) {
        log.info("服务rose-content-server-b，/user/test/b2接口，接收到参数：{}", JsonUtil.objectToJson(param));
    }

    @GetMapping(value = "/b3")
    public void b3(@RequestParam(value = "param1") String param1) {
        log.info("服务rose-content-server-b，/user/test/b3接口，接收到参数：{}", param1);
    }

    @PostMapping("/b4")
    public StringResponse b4() throws BlockException {
        Entry entry = null;
        try {
            // 定义sentinel 资源标志名称
            String sentinelFlagName = "user-sentinelTest1";
            ContextUtil.enter(sentinelFlagName);
            entry = SphU.entry(sentinelFlagName);

            // 被保护的业务逻辑

            return new StringResponse("success");
            //需要降级处理时，抛此异常
//            throw new SentinelCaputeException(ResponseResultCode.OPERT_ERROR);
        } catch (BlockException e) {
            // 如果被保护的资源被限流或者降级了，就会抛BlockException
            log.error("接口-/user/test/b4，发生了限流，或者降级了，原因：{}", e);
            throw e;
        } catch (SentinelCaputeException e) {
            // 统计SentinelCaputeException【发生的次数、发生占比...】
            Tracer.trace(e);
            throw e;
        } finally {
            // 退出 sentinel
            if (entry != null) {
                entry.exit();
            }
            ContextUtil.exit();
        }
    }
}