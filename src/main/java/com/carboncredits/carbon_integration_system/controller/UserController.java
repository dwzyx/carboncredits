package com.carboncredits.carbon_integration_system.controller;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.carboncredits.carbon_integration_system.enumeration.ErrorEnum;
import com.carboncredits.carbon_integration_system.service.UserService;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.logging.Logger;


@RestController
public class UserController {

    private Gson gson = new Gson();
    private JSONObject jsonObject;
    public static Logger logger = Logger.getLogger(String.valueOf(UserController.class));
    private UserService userService;
    private String answer = null;
    HashMap<String, Object> map = new HashMap<>();

    //构造函数
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户信息：
     * 通过@ResponseBody 方式，需要在@RequestMapping 中，添加produces = "application/json;charset=UTF-8",设定返回值的类型。
     * @return {"result":
     *              {"user":"...",
     *              "token":"..."
     *              }
     *user:用户的基本信息
     *token:用户登录后返回的token，需要用户授权的接口必须传此参数
     *
     *
     * 记录：暂不清楚内嵌app运行流程，访问/app/user/getUserInfo的是外部的应用还是此程序，结果有返回给谁，故暂时不考虑这个方法
     */
    @PostMapping(value = "/app/user/getUserInfo", produces = "application/json;charset=UTF-8")
    public String userInfo(@RequestBody String request){
        jsonObject = JSONObject.parseObject(request);

        try{
            int userId = (int) jsonObject.get("user_id");
            int termType = (int) jsonObject.get("term_type");
            String termToken = (String) jsonObject.get("term_token");
            jsonObject.clear();

            map = userService.getUserInfo(userId);

            jsonObject.put("result", map);
        }catch (Exception e){
            e.printStackTrace();
        }finally {

            answer = jsonObject.toString();
        }
        return answer;
    }

    /**
     *通过用户的id获取用户在此程序中的首页消息
     * @param request 得到用户的user_id
     * @return 返回首页的信息
     */
    @PostMapping(value = "/app/user/home", produces = "application/json;charset=UTF-8")
    public String home(@RequestBody String request){
        try{
            //处理请求，获取参数
            jsonObject = JSONObject.parseObject(request);//接受数据并将其转化为JSONObject对象
            HashMap<String, Object> jsonMap = JSONObject.parseObject(request, new TypeReference<HashMap<String, Object>>(){});
            int userId = (int) jsonMap.get("user_id");//获取用户id
            jsonObject.clear();//清空jsonObject对象

            map = userService.getUserHomeMessage(userId);//查询主页信息，并将其用map形式带回

            jsonObject.put("result", map);//添加处理结果

        } catch (ClassCastException e){
            //错误异常处理，一般都是json数据传输错误
            jsonObject.clear();
            map.put("error", ErrorEnum.valueOf("FAILED").getCoding());
        } catch (Exception e){
            jsonObject.clear();
            map.put("error", ErrorEnum.valueOf("UNKNOWN_ERROR").getCoding());
            e.printStackTrace();
        } finally {
            //返回结果
            jsonObject.put("result", map);
            answer = jsonObject.toString();
            map.clear();
        }
        return answer;

    }

}
