package com.catlovers.carbon_credits.filter;


import org.apache.log4j.Logger;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;


import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractMobileAuthenticationFilter extends AuthenticationFilter {

    public static final String TOKEN = "token";
    protected Logger log = Logger.getLogger(getClass());

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {

        log.info("安卓用户进入校验！" + getLoginUrl());

        HttpServletRequest req = (HttpServletRequest) request;

        String token = req.getParameter(TOKEN);
        if (isAccess(token)) {
            return onAccessSuccess(req, (HttpServletResponse) response);
        }

        return onAccessFail(req, (HttpServletResponse) response);
    }

    /**
     * 判断token的合法性
     *
     * @param token
     * @return
     */
    public abstract boolean isAccess(String token);

    /**
     * 认证成功进行的操作处理
     *
     * @param request
     * @param response
     * @return true 继续后续处理，false 不需要后续处理
     */
    public abstract boolean onAccessSuccess(HttpServletRequest request,
                                            HttpServletResponse response);

    /**
     * 认证失败时处理结果
     *
     * @param request
     * @param response
     * @return true 继续后续处理，false 不需要后续处理
     */
    public abstract boolean onAccessFail(HttpServletRequest request,
                                         HttpServletResponse response);

}
