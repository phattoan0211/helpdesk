package com.onlinehelpdesk.services;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.onlinehelpdesk.entities.Nhanvien;
import com.onlinehelpdesk.entities.Yeucau;

public interface NhanvienService extends UserDetailsService {

	public List<Nhanvien> findall();

	public boolean save(Nhanvien nv);

	public boolean delete(String username);

	public Nhanvien findbyusername(String username);

	public List<Nhanvien> finduserbyrole(int role);

	public boolean isOwner(Authentication authentication, String username);
	
	public String getpassword(String username);
}
