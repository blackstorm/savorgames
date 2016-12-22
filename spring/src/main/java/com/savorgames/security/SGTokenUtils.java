package com.savorgames.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.savorgames.security.model.SGUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class SGTokenUtils {
	private static final String AUDIENCE_UNKNOWN   = "unknown";
	
	@Value("${sg.token.secret}")
	private String secret; // 密匙
	
	@Value("${sg.token.expiration}")
	private Long expiration; // 过期时间
	
	/**
	 * 前台传递封装token
	 * @param userDetails
	 * @param ip
	 * @param userAgent
	 * @return
	 */
	public String generateToken(UserDetails userDetails, String ip, String userAgent){
		SGUser sguser = (SGUser)userDetails;
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", sguser.getUsername()); // 用户名
		claims.put("audience", this.generateAudience(userAgent)); // agent
		claims.put("created", this.generateCurrentDate()); // 创建日期
		claims.put("ip", ip); //ip
		return this.generateToken(claims);
	}
	
	/**
	 * 使用 userDetails 封装token
	 * @param userDetails
	 * @return
	 */
	public String generateToken(UserDetails userDetails){
		SGUser sguser = (SGUser) userDetails;
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", userDetails.getUsername());
		claims.put("audience", this.generateAudience(sguser.getUserAgent()));
		claims.put("created", this.generateCurrentDate());
		claims.put("ip", this.generateUserIp(sguser.getIp()));
		return this.generateToken(claims);
	}
	
	/**
	 * 验证token
	 * @param token
	 * @param userDetails
	 * @return
	 */
	public Boolean validateToken(String token, UserDetails userDetails){
		SGUser sguser = (SGUser) userDetails;
		final String username = this.getUsernameFromToken(token);
		final String userIp = this.getUseripFromToken(token);
		final String userAgent = this.getUserAgentFromToken(token);
		final Date created = this.getCreatedDateFromToken(token);
//		final Date expiration = this.getExpirationDateFromToken(token);
		/**
		 * 判断用户名
		 * 密码
		 * ip
		 * userAgent
		 * 等是否相等
		 */
		return (
				username.equals(sguser.getUsername())
				&& userIp.equals(sguser.getIp())
				&& userAgent.equals(sguser.getUserAgent())
				&& !(this.isTokenExpired(token)) 
				&& !(this.isCreatedBeforeLastPasswordReset(created, sguser.getLastPasswordReset()))
				);
	}
	
	/**
	 * 判断用户密码是否修改
	 * @param created
	 * @param lastPasswordReset
	 * @return
	 */
	private boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	/**
	 * 判断是否过期
	 * @param token
	 * @return
	 */
	private boolean isTokenExpired(String token) {
	    final Date expiration = this.getExpirationDateFromToken(token);
	    return expiration.before(this.generateCurrentDate());
	}

	/**
	 * 通过token获取过期时间
	 * @param token
	 * @return
	 */
	private Date getExpirationDateFromToken(String token) {
	    Date expiration;
	    try {
	      final Claims claims = this.getClaimsFromToken(token);
	      expiration = claims.getExpiration();
	    } catch (Exception e) {
	      expiration = null;
	    }
	    return expiration;
	}

	/**
	 * 通过token获取创建日期
	 * @param token
	 * @return
	 */
	private Date getCreatedDateFromToken(String token) {
	    Date created;
	    try {
	      final Claims claims = this.getClaimsFromToken(token);
	      created = new Date((Long) claims.get("created"));
	    } catch (Exception e) {
	      created = null;
	    }
	    return created;
	}

	/**
	 * 获取用户 user-agent
	 * @param token
	 * @return
	 */
	private String getUserAgentFromToken(String token) {
		return this.getStringValueFromToken(token, "audience");
	}

	/**
	 * 通过token获取用户ip
	 * @param token
	 * @return
	 */
	private String getUseripFromToken(String token) {
		return this.getStringValueFromToken(token, "ip");
	}

	/**
	 * 通过 key获取value
	 * @param token
	 * @param key
	 * @return
	 */
	private String getStringValueFromToken(String token, String key){
		String value;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			value = new String((String) claims.get(key));
		} catch (Exception e) {
			value = null;
		}
		return value;
	}


	/**
	 * 通过token获取用户名
	 * @param token
	 * @return
	 */
	public String getUsernameFromToken(String token) {
	    String username;
	    try {
	      final Claims claims = this.getClaimsFromToken(token);
	      username = claims.getSubject();
	    } catch (Exception e) {
	      username = null;
	    }
	    return username;
	}
	
	/**
	 * 通过token获取封装map
	 * @param token
	 * @return
	 */
	private Claims getClaimsFromToken(String token) {
	    Claims claims;
	    try {
	      claims = Jwts.parser()
	        .setSigningKey(this.secret)
	        .parseClaimsJws(token)
	        .getBody();
	    } catch (Exception e) {
	      claims = null;
	    }
	    return claims;
	}

	/**
	 * 生成用户IP
	 * @param ip
	 * @return
	 */
	private String generateUserIp(String ip) {
//		if(ip == null || ip == "127.0.0.1"){
//			return USER_IP_UNKNOWN;
//		}
		return ip;
	}

	/**
	 *生成 token
	 * @param claims
	 * @return
	 */
	private String generateToken(Map<String, Object> claims) {
	    return Jwts.builder()
	    	      .setClaims(claims)
	    	      .setExpiration(this.generateExpirationDate())
	    	      .signWith(SignatureAlgorithm.HS512, this.secret)
	    	      .compact();
	}

	/**
	 * 生成过期时间
	 * @return
	 */
	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + this.expiration * 1000);
	}

	/**
	 * 生成创建日期
	 * @return
	 */
	private Date generateCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 生成useragent
	 * @param userAgent
	 * @return
	 */
	private Object generateAudience(String userAgent) {
		if (userAgent == null){
			return AUDIENCE_UNKNOWN;
		}
		return userAgent;
	}
}
