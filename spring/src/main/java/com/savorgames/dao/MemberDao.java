package com.savorgames.dao;

import java.util.List;
import java.util.Map;

import com.savorgames.entity.Member;
import com.savorgames.entity.Role;

public interface MemberDao {
	public List<Member> test();
	public Integer findUidByEmail(String email);
	public Integer findUidByUsername(String username);
	
	public Member findMemberByEmail(String email);
	public Member findMemberByUsername(String username);
	public Map<String, Object> selectMemberByUsername(String username);
	public Map<String, Object> selectMemberByUid(long uid);
    public Member findMemberByEmailAndPws(Member member);
    public Member findMemberByUsernameAndPws(Member member);
    public Member findMemberByUid(long uid);
    public void uploadPasswordByUid(long uid, String password);
    
	public Integer save(Member member);
	public Integer insertAndGetUid(Member member);
	
	public List<Role> findMemberRoleByUid(long uid);
	public List<Role> findMemberRoleByUsername(String username);
	public List<Role> findMemberRoleByEmail(String email);
	
	public Integer uploadMemberAvatarByUid(long uid, String avatarLink);
	public Integer uploadNicknameByUid(long uid, String nickname);
	
	public String findMemberQiniuAvatarByUid(long uid);
}
