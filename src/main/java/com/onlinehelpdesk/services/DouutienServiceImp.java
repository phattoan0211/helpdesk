package com.onlinehelpdesk.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.onlinehelpdesk.entities.Douutien;
import com.onlinehelpdesk.entities.Nhanvien;
import com.onlinehelpdesk.entities.Yeucau;
import com.onlinehelpdesk.repositories.DouutienRepository;
import com.onlinehelpdesk.repositories.NhanvienRepository;
import com.onlinehelpdesk.repositories.YeucauRepository;

@Service
public class DouutienServiceImp implements DouutienService {
	@Autowired
	private DouutienRepository douutienRepository;

	@Override
	public Douutien finddouutien(int madouutien) {
		// TODO Auto-generated method stub
		return douutienRepository.findmadouutien(madouutien);
	}

	@Override
	public Iterable<Douutien> findall() {
		// TODO Auto-generated method stub
		return douutienRepository.findAll();
	}

}
