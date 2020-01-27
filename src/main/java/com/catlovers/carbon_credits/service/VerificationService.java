package com.catlovers.carbon_credits.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.xml.bind.util.JAXBSource;
import java.awt.*;
import java.awt.image.BufferedImage;

public interface VerificationService {
    Color randomColor();
    Font randomFont();
    void drawLine(BufferedImage image);

    BufferedImage createImage();
    JSONObject getImage(int userId,String str);

}
