package com.savorgames.api.v1.captcha;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

public class RegisterCaptcha {
  
  private Properties props = new Properties();
  private Producer kaptchaProducer = null;

  public RegisterCaptcha() {
    // Switch off disk based caching.
    ImageIO.setUseCache(false);

    this.props.put("kaptcha.border", "no");
    this.props.put("kaptcha.textproducer.font.color", "black");
    this.props.put("kaptcha.textproducer.char.space", "5");
//    this.props.put("kaptcha.textproducer.font.names", "Impact");

    Config config = new Config(this.props);
    this.kaptchaProducer = config.getProducerImpl();
  }

  public void captcha(HttpServletRequest req, HttpServletResponse resp,String capText)
      throws ServletException, IOException {
    // Set standard HTTP/1.1 no-cache headers.
    resp.setHeader("Cache-Control", "no-store, no-cache");

    // return a jpeg
    resp.setContentType("image/jpeg");

    // create the image with the text
    BufferedImage bi = this.kaptchaProducer.createImage(capText);

    ServletOutputStream out = resp.getOutputStream();

    // write the data out
    ImageIO.write(bi, "jpg", out);
  }
  
  public String generateCreateText(){
    return this.kaptchaProducer.createText();
  }
}
