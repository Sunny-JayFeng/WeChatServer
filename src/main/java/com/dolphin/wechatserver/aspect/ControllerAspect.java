package com.dolphin.wechatserver.aspect;

import com.dolphin.wechatserver.exception.RequestForbiddenException;
import com.dolphin.wechatserver.exception.ServerBusyException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求接口切面
 * @author Dolphin
 * @date 2025/01/21
 */
@Aspect
@Component
@Slf4j
public class ControllerAspect {

    // 存放每个线程来请求的开始时间 key 为线程 id
    private Map<Long, ThreadLocal<Long>> threadLocalMap = new HashMap<>(16384);

    /**
     * 配置切点
     * 所有的请求都必须判断是否来自浏览器
     * execution 表达式
     * 第一个 * 表示返回类型，* 表示所有类型。这里实则是：jayfeng.meituan.uploadfile.response.ResponseMessage
     * 接下来是包名
     * controller.类名  哪一个类, * 表示所有类
     * controller.类名.方法名(参数)  * 表示所有方法 (..) 表示任何参数
     */
    @Pointcut("execution(* com.dolphin.wechatserver.controller.*.*(..))")
    public void requestInterface() {

    }

    /**
     * 接口业务逻辑处理之前
     * @param joinPoint 切点
     */
    @Before("requestInterface()")
    public void requestInterfaceDoBefore(JoinPoint joinPoint) {
        ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();
        startTimeThreadLocal.set(System.currentTimeMillis());
        threadLocalMap.put(Thread.currentThread().getId(), startTimeThreadLocal);

        HttpServletRequest request = this.getHttpServletRequest();
        if (!isRequestFromHarmony(request)) {
            log.info("请求来源不被支持, 拒绝处理");
            throw new RequestForbiddenException("您无权访问该服务");
        }
    }

    /**
     * 获取 HttpServletRequest 对象
     * @return 返回 HttpServletRequest 对象
     */
    private HttpServletRequest getHttpServletRequest() {
        return this.getServletRequestAttributes().getRequest();
    }

    /**
     * 请求是否来自浏览器
     * @param request HttpServletRequest 用于获取请求头数据
     * @return 返回请求是否来自鸿蒙应用、桌面端应用
     */
    private Boolean isRequestFromHarmony(HttpServletRequest request) {
        String requestFrom = request.getHeader("request-from");
        return "harmony-application".equals(requestFrom);
    }

    /**
     * 获取 ServletRequestAttributes 对象
     * @return 返回 ServletRequestAttributes 对象
     */
    private ServletRequestAttributes getServletRequestAttributes() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        if (servletRequestAttributes == null) {
            log.info("请求无法获取到 ServletRequestAttributes 对象");
            throw new ServerBusyException("服务器繁忙");
        }
        return servletRequestAttributes;
    }

    /**
     * 接口业务逻辑处理之后
     * @param result 请求结果数据
     */
    @AfterReturning(returning = "result", pointcut = "requestInterface()")
    public void requestInterfaceDoAfterReturning(Object result) {
        ThreadLocal<Long> startTimeThreadLocal = threadLocalMap.get(Thread.currentThread().getId());
        log.info("请求耗时: {}ms", System.currentTimeMillis() - startTimeThreadLocal.get());
    }

}
