package com.catlovers.carbon_credits.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(value = "/game/home", produces = "application/json;charset=UTF-8")
    public String gameHome(){
        JSONObject jsonObject = gameService.getHomePage();
        return jsonObject.toString();
    }

    @GetMapping(value = "/game/event", produces = "application/json;charset=UTF-8")
    public String gameEvent(@RequestParam("user_id") int userId, @RequestParam("carbon_credits") int carbonCredits){
        JSONObject jsonObject = gameService.gameEvent(userId, carbonCredits);
        return jsonObject.toString();
    }

}
