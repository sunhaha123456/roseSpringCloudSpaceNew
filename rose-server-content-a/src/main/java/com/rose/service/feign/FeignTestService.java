package com.rose.service.feign;

import com.rose.data.dto.TestDto;
import com.rose.parent.common.data.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "rose-content-server-b")
public interface FeignTestService {

    /**
     * 功能：feigen 以 get 方法调用
     * @param param1
     * @param param2
     * @return
     */
    @GetMapping(value = "/rose-content-server-b/user/test/b1")
    ResponseResult b1(@RequestParam(value = "param1") Long param1, @RequestParam(value = "param2") String param2);

    /**
     * 功能：feigen 以 post 方法调用
     * @param param
     * @return
     */
    @PostMapping(value = "/rose-content-server-b/user/test/b2")
    ResponseResult b2(@RequestBody TestDto param, @RequestParam(value = "token") Long token, @RequestParam(value = "userId") String userId);
}