package com.savorgames.entity;

/**
 * 用户权限
 * 
 * @author Haozi
 *
 */
public class Role {
  private long roleId;
  private String code;
  private String name;

  public long getRoleId() {
    return roleId;
  }

  public void setRoleId(long roleId) {
    this.roleId = roleId;
  }

  public String getCode() {
    return code;
  }

  @Override
  public String toString() {
    return "Role [roleId=" + roleId + ", code=" + code + ", name=" + name + "]";
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
