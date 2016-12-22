package com.savorgames.api.v1.valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.savorgames.api.v1.resp.ResValidFailEntity;

public class ReQBodyValid {
  
  public ResValidFailEntity validHasErrors(BindingResult result) {
    if(result.hasErrors()){
        ResValidFailEntity resve = new ResValidFailEntity();
        Map<String, String> es = new HashMap<>();
        List<FieldError> lserror = result.getFieldErrors();
        for(FieldError e : lserror){
            es.put(e.getField(), e.getDefaultMessage());
        }
        resve.setErrors(es);
        return resve;
    }
    return null;
}
}
