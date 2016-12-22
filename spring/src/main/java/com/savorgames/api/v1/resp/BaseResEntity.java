package com.savorgames.api.v1.resp;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

/*
 * 返回请求信息
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY) // 值为空不进行序列化
public class BaseResEntity implements Serializable {

  private static final long serialVersionUID = 219940087419685228L;

  public static final String STATUS_FAILED = "failed";
  public static final String STATUS_SUCESS = "success";

  private String status;
  private String message;

  /**
   * 返回token的实体
   * 
   * @author inf2
   *
   */
  public static class Token extends BaseResEntity {
    private static final long serialVersionUID = -501975302239793053L;
    private @Getter @Setter String token;
    
    public BaseResEntity.Token setStatusSucess(String message, String token) {
      this.setStatus(BaseResEntity.STATUS_SUCESS);
      this.setMessage(message);
      this.setToken(token);
      return this;
    }
  }
  
  public static class ObjectData extends BaseResEntity {
    private static final long serialVersionUID = 8512242001664433954L;
    private @Getter @Setter Object result;
    
    public BaseResEntity.ObjectData setObjectDataStatusSucess(Object res) {
      super.setStatusSucess();
      this.setResult(res);
      return this;
    }
  }
  
  public static class UploadAvatarToen extends BaseResEntity {
    private static final long serialVersionUID = -2732283792812896828L;
    private @Getter @Setter String token;
    private @Getter @Setter String key;
    public UploadAvatarToen setUploadAvatarToenStatusSucess(String token,String key){
      super.setStatusSucess();
      this.setToken(token);
      this.setKey(key);
      return this;
    }
  }

  /**
   * 返回一个实体类型
   */
  public static class NodeRespEntity extends BaseResEntity {
    private static final long serialVersionUID = 7931837628779096073L;
    private @Getter @Setter Object nodes;

    public BaseResEntity.NodeRespEntity setStatusSucess(String message, Object nodes) {
      this.setStatus(BaseResEntity.STATUS_SUCESS);
      this.setMessage(message);
      this.setNodes(nodes);
      return this;
    }

    public BaseResEntity.NodeRespEntity setStatusSucess(Object nodes) {
      this.setStatus(BaseResEntity.STATUS_SUCESS);
      this.setNodes(nodes);
      return this;
    }

  }

  public BaseResEntity() {}

  public BaseResEntity(String status, String message) {
    this.status = status;
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public BaseResEntity set(String status, String message) {
    this.clean();
    this.status = status;
    this.message = message;
    return this;
  }

  public BaseResEntity set(String status) {
    this.clean();
    this.status = status;
    return this;
  }

  public BaseResEntity setStatusSucess(String message) {
    this.set(STATUS_SUCESS, message);
    return this;
  }

  public BaseResEntity setStatusSucess() {
    this.set(STATUS_SUCESS);
    return this;
  }

  public BaseResEntity setStatusFailed(String message) {
    this.set(STATUS_FAILED, message);
    return this;
  }

  public BaseResEntity setStatusFailed() {
    this.set(STATUS_FAILED);
    return this;
  }

  public void clean() {
    this.status = null;
    this.message = null;
  }

  @Override
  public String toString() {
    return "ResponseMessage [status=" + status + ", message=" + message + "]";
  }

}
