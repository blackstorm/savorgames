package com.savorgames.api.v1.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

public class ForgetPassword {
  
  public static class Rest extends ForgetPassword{
    @Email(message = "邮箱格式不正确")
    String email;

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }
  }
  
  public static class NewPassword extends ForgetPassword{
    @NotNull
    @Length(min = 32, max = 32, message = "验证码错误")
    private String key;
    @NotNull
    @Length(min = 6, max = 18, message = "您的新密码长度必须在6-18范围内")
    private String newPassword;
    public String getKey() {
      return key;
    }
    public void setKey(String key) {
      this.key = key;
    }
    public String getNewPassword() {
      return newPassword;
    }
    public void setNewPassword(String newPassword) {
      this.newPassword = newPassword;
    }
  }
}
