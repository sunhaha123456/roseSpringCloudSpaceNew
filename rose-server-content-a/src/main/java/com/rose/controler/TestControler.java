package com.rose.controler;

import com.rose.data.dto.TestDto;
import com.rose.parent.common.data.response.ResponseResult;
import com.rose.parent.common.data.response.StringResponse;
import com.rose.parent.common.exception.BusinessException;
import com.rose.parent.common.util.JsonUtil;
import com.rose.parent.common.util.StringUtil;
import com.rose.parent.common.util.ValueHolder;
import com.rose.service.TestService;
import com.rose.service.feign.FeignTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

@Slf4j
@RestController
@RequestMapping("/user/test")
public class TestControler {

    @Inject
    private FeignTestService feignTestService;

    @Autowired
    private RestTemplate restTemplate;

    @LoadBalanced
    @Autowired
    private RestTemplate loadBalance;

    @Inject
    private ValueHolder valueHolder;

    @GetMapping(value = "/a1")
    public StringResponse a1() {
        return new StringResponse("success");
    }

    /**
     * 功能：以feign 方式，调用rose-content-server-b服务/user/test/b2接口
     * 备注：系统已设置：
     *          1、让feign使用apache httpclient做请求，而不是默认的url connection
     *          2、设置了链接超时时间，防止请求长时间不返回
     *          3、使用链接池提供效率，设置了最大、最小链接数
     * @return
     */
    @GetMapping(value = "/a2")
    public StringResponse a2() {
        TestDto param = new TestDto();
        param.setParam1("111");
        param.setParam2("222");
        param.setParam3("333");

        String resStr = feignTestService.b2(param, valueHolder.getUserIdHolder(), valueHolder.getTokenHolder());
        ResponseResult res = JsonUtil.jsonToObject(resStr, ResponseResult.class);

        return (res != null && res.getCode() == 200 ) ? new StringResponse("success") : new StringResponse(resStr);
    }

    /**
     * 功能：以restTemplate ribbon负载均衡 方式，调用rose-content-server-b服务user/test/b2接口
     * 备注：系统已设置，链接超时时间，防止请求长时间不返回
     * @return
     */
    @GetMapping(value = "/a3")
    public StringResponse a3() {
        String targetUrl = "http://rose-content-server-b/rose-content-server-b/user/test/b2?userId=" + valueHolder.getUserIdHolder() + "&token=" + valueHolder.getTokenHolder();
        TestDto param = new TestDto();
        param.setParam1("111");
        param.setParam2("222");
        param.setParam3("333");

        String resStr = loadBalance.postForObject(targetUrl, param, String.class);
        ResponseResult res = JsonUtil.jsonToObject(resStr, ResponseResult.class);

        return (res != null && res.getCode() == 200 ) ? new StringResponse("success") : new StringResponse(resStr);
    }

    /**
     * 功能：以restTemplate 非ribbon负载均衡的直连 方式，调用rose-content-server-b服务user/test/b2接口
     * @return
     */
    @GetMapping(value = "/a4")
    public StringResponse a4() {
        String targetUrl = "http://127.0.0.1:8003/rose-content-server-b/user/test/b2?userId=" + valueHolder.getUserIdHolder() + "&token=" + valueHolder.getTokenHolder();
        TestDto param = new TestDto();
        param.setParam1("111");
        param.setParam2("222");
        param.setParam3("333");

        String resStr = restTemplate.postForObject(targetUrl, param, String.class);
        ResponseResult res = JsonUtil.jsonToObject(resStr, ResponseResult.class);

        return (res != null && res.getCode() == 200 ) ? new StringResponse("success") : new StringResponse(resStr);
    }

    @GetMapping(value = "/a5")
    public StringResponse a5() {
        return new StringResponse("success");
    }
}