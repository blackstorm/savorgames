package com.savorgames.entity;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * member 数据库结构 CREATE TABLE "public"."user" ( "username" varchar(16) COLLATE "default" NOT NULL,
 * "uid" int4 DEFAULT nextval('topic_uid_seq'::regclass) NOT NULL, "password" varchar(64) COLLATE
 * "default" NOT NULL, "email" varchar(255) COLLATE "default" NOT NULL, "nickname" varchar(12)
 * COLLATE "default" NOT NULL, "regdate" timestamptz(6), CONSTRAINT "user_pkey" PRIMARY KEY ("uid")
 * ) WITH (OIDS=FALSE) ;
 * 
 * ALTER TABLE "public"."user" OWNER TO "postgres";
 * 
 * @author Haozi
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY) // 值为空不进行序列化
public class Member {

  private long uid;
  private String username;
  private String password;
  private String email;
  private String nickname;
  private Date regdate;
  private Date lastpasswordreset;
  private String avatar;
  
  public long getUid() {
    return uid;
  }
  public void setUid(long uid) {
    this.uid = uid;
  }
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getNickname() {
    return nickname;
  }
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }
  public Date getRegdate() {
    return regdate;
  }
  public void setRegdate(Date regdate) {
    this.regdate = regdate;
  }
  public Date getLastpasswordreset() {
    return lastpasswordreset;
  }
  public void setLastpasswordreset(Date lastpasswordreset) {
    this.lastpasswordreset = lastpasswordreset;
  }
  public String getAvatar() {
    return avatar;
  }
  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }
  @Override
  public String toString() {
    return "Member [uid=" + uid + ", username=" + username + ", password=" + password + ", email="
        + email + ", nickname=" + nickname + ", regdate=" + regdate + ", lastpasswordreset="
        + lastpasswordreset + ", avatar=" + avatar + "]";
  }
}
