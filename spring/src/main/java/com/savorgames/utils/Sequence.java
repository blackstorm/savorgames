package com.savorgames.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Sequence {
  
  private int hourMs = 3600000;
  
  public Map<String, Object>[] ordeyByRank(List<Map<String, Object>> datas) {
    int datasSize = datas.size();

    for (int i = 0; i < datasSize; i++) {
      Map<String, Object> data = datas.get(i);
      int t = this.differentHourByMillisecond((Date) data.get("created"));
      long vote = (long) data.get("rank");
      double sum = ((vote - 1) / Math.pow((t + 0.5), 1.5));
      data.put("score", sum);
    }
//    this.sort(datas);
    return this.sort(datas);
  }
  
  private int differentHourByMillisecond(Date date) {
    Date now = new Date();
    long diff;
    diff = now.getTime() - date.getTime();
    int hour = (int) (diff / this.hourMs);
    return hour;
  }
  
  private Map<String, Object>[] copListToArray (List<Map<String, Object>> datas) {
    int len = datas.size(); // 获取LIST大小
    @SuppressWarnings("unchecked")
    Map<String, Object>[] maps =  new Map[len]; // 创建一个相同大小的数组
    for (int i = 0; i < len; i++) { // 将List的数据替换包替换到数组中
      maps[i] = datas.get(i);
    }
    return maps;
  }
  
  private Map<String, Object>[] sort(List<Map<String, Object>> datas) {
    Map<String, Object>[] arrays = this.copListToArray(datas);
    int len = arrays.length;
    for (int i = 0; i < len; i++){
//      System.out.println("i"+i);
      for (int j = i; j < len; j ++) {
//        System.out.println("j"+j);
        double d1 = (double) arrays[i].get("score");
        double d2 = (double) arrays[j].get("score");
        if (d1 < d2) {
          Map<String, Object> temp =  arrays[i];
          arrays[i] =  arrays[j];
          arrays[j] = temp;
        }
      }
    }
//    for (Map<String, Object> s : arrays) {
//      System.out.println(s.toString());
//    }
    return arrays;
  }
}
