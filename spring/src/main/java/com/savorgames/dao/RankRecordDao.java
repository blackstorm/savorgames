package com.savorgames.dao;

public interface RankRecordDao {
  public Integer selectMemberIsRank(String type, long memeberID, long targetId);
  public void insert(String type, long memeberID, long targetId);
}
