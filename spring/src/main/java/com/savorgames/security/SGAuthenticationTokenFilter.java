package com.savorgames.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.savorgames.security.model.SGUser;

/**
 * 验证 token 是否正确
 */
public class SGAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter{
	
	@Value("${sg.token.header}")
	private String tokenHeader; // token头
	
	@Autowired private SGTokenUtils sgTokenUtils;
	
	@Autowired private UserDetailsService userDetailsService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
	    String authToken = httpRequest.getHeader(this.tokenHeader); // 获取token
	    String username = this.sgTokenUtils.getUsernameFromToken(authToken);// 通过token获取用户名
	    
	    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	    	
	    	UserDetails userDetails = this.userDetailsService.loadUserByUsername(username); // 从数据库查询用户
	    	SGUser sguser = (SGUser) userDetails; // 强制转换类型
	    	sguser.setIp(httpRequest.getRemoteAddr()); //获取客户端IP
	    	sguser.setUserAgent(httpRequest.getHeader("User-Agent")); // 获取User-Agent
	    	
	    	/**
	    	 * 验证token
	    	 */
	    	if (this.sgTokenUtils.validateToken(authToken, userDetails)) {
	    		UsernamePasswordAuthenticationToken authentication = 
	            		new UsernamePasswordAuthenticationToken(sguser, null, sguser.getAuthorities());
	            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	    	}
	    }
	    chain.doFilter(request, response);
	}
}
