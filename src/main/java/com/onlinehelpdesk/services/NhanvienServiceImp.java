package com.onlinehelpdesk.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.onlinehelpdesk.entities.Nhanvien;
import com.onlinehelpdesk.entities.Yeucau;
import com.onlinehelpdesk.repositories.NhanvienRepository;

@Service
public class NhanvienServiceImp implements NhanvienService {

	@Autowired
	private NhanvienRepository nhanvienRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Nhanvien nv = nhanvienRepository.findusername(username);
		if (nv == null) {
			throw new UsernameNotFoundException("username not found");
		} else if (!nv.getKichhoat()) {
			throw new UsernameNotFoundException("Account is not active");
		} else {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			if (nv.getQuyen() == 1) {
				authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

			} else if (nv.getQuyen() == 2) {
				authorities.add(new SimpleGrantedAuthority("ROLE_SUPPORTER"));

			} else {
				authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
			}
			return new User(username, nv.getPassword(), authorities);
		}
	}

	@Override
	public List<Nhanvien> findall() {
		return nhanvienRepository.findall();
	}

	@Override
	public boolean save(Nhanvien nv) {
		try {
			nv = nhanvienRepository.save(nv);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(String username) {
		// TODO Auto-generated method stub
		try {
			Nhanvien nv = findbyusername(username);
			nhanvienRepository.delete(nv);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Nhanvien findbyusername(String username) {
		try {
			Nhanvien nv = nhanvienRepository.findusername(username);
			return nv;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Nhanvien> finduserbyrole(int role) {
		return nhanvienRepository.findbyrole(role);
	}

	@Override
	public boolean isOwner(Authentication authentication, String username) {

		username = authentication.getName();
		Nhanvien nv = nhanvienRepository.findusername(username);
		return nv != null && username.equals(nv.getUsername());

	}

	public String getpassword(String username) {
		Nhanvien nv = nhanvienRepository.findusername(username);
		return nv.getPassword();
	}

}
