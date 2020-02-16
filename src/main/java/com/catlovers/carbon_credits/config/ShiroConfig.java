package com.catlovers.carbon_credits.config;

import com.catlovers.carbon_credits.filter.JWTFilter;
import com.catlovers.carbon_credits.model.MerchantVO;
import com.catlovers.carbon_credits.realm.MerchantRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean("shiroFilter")
    //ShiroFilterFactoryBean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
//        //创建过滤器工厂
//        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean( );
//        //设置安全管理器
//        bean.setSecurityManager( defaultWebSecurityManager);
//
//        //通用配置（跳转登陆页面）
//        bean.setLoginUrl("/Merchant/login");
//        //设置过滤器集合
//        Map<String ,String > filterMap = new LinkedHashMap<>();
//        //当前地址可以匿名访问；
//        filterMap.put("/Merchant/signUp","anon");
//        //当前地址必须登录后访问
//        filterMap.put("/Merchant/**","authc");
//
//           return bean;

        // 添加自己的过滤器并且取名为jwt
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new JWTFilter());
        factoryBean.setFilters(filterMap);

        factoryBean.setSecurityManager(securityManager);
        factoryBean.setLoginUrl("/Merchant/homeFalse");
        factoryBean.setUnauthorizedUrl("/Merchant/homeFalse");

        /*
         * 自定义url规则
         * http://shiro.apache.org/web.html#urls-
         */
        Map<String, String> filterRuleMap = new HashMap<>();
        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/Merchant/home", "jwt");
        // 访问401和404页面不通过我们的Filter
        filterRuleMap.put("/401", "anon");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }


    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(MerchantRealm realm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自己的realm
        manager.setRealm(realm);

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }


    //创建realm 对象 自定义类
    @Bean
    public MerchantRealm merchantRealm(){
        return new MerchantRealm();
    }

    //开启对shior注解的支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(defaultWebSecurityManager);
        return advisor;
    }

}
