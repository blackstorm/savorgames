package com.savorgames.api.v1.dto;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class MemberDto {
  @NotEmpty
  @Length(min = 3, max = 12, message = "用户名3-12")
  @Pattern(regexp = "^[a-z0-9_-]{3,12}$", message = "您的用户名必须在a-zA-Z、数字0-9、字符_-范围内。")
  private String username;

  // TODO 注意密码不能为中文
  @NotEmpty
  @Length(min = 6, max = 18, message = "您的密码长度必须要6-18范围内")
  private String password;

  public static @ToString class Signup extends MemberDto {
    @NotEmpty(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private @Getter @Setter String Email;
    private @Getter @Setter int avatar;
    @NotEmpty(message = "验证码不能为空")
    private @Getter @Setter String captcha;
    @NotEmpty(message = "bad captchaid")
    private @Getter @Setter String captchaid;
  }

  public static @ToString class Signin {
    @NotEmpty(message = "用户名或邮箱不能为空")
    private @Getter @Setter String username;
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 18, message = "您的密码长度必须要6-18范围内")
    private @Getter @Setter String password;
  }

  public static @ToString class Me {
    private @Getter @Setter long uid;
    private @Getter @Setter String username;
    private @Getter @Setter String email;
    private @Getter @Setter String avatar;
    private @Getter @Setter String nickname;
    private @Getter @Setter Date regdate;
  }
  
  /**
   * 用于开发环境下七牛回调json
   * @author inf2
   * 
   */
  public static @ToString class HashAndKey{
    @NotEmpty()
    private @Getter @Setter String hash;
    @NotEmpty()
    private @Getter @Setter String key;
    private @Getter @Setter long uid;
  }
  
  public static @ToString class UploadNickname{
    @NotEmpty(message = "昵称不能为空")
    @Length(min = 1, max = 6, message = "昵称最少为一个字符，最多6个字符")
    private @Getter @Setter String nickname;
    private @Getter @Setter long uid;
  }
}
