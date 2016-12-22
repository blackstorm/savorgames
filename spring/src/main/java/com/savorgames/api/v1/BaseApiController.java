package com.savorgames.api.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.savorgames.api.v1.resp.BaseResEntity;
import com.savorgames.api.v1.resp.ResValidFailEntity;


public class BaseApiController {
	protected BaseResEntity baseResEntity = new BaseResEntity();
	
	/**
	 * 判断错误 如果有错误返回一个ResponseValidError类型
	 * @param result
	 * @return
	 */
	protected ResValidFailEntity validHasErrors(BindingResult result) {
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
