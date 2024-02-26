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
import com.onlinehelpdesk.repositories.NhanvienRepository;
import com.onlinehelpdesk.repositories.YeucauRepository;

@Service
public class YeucauServiceImp implements YeucauService {
	@Autowired
	private YeucauRepository yeucauRepository;

	@Override
	public List<Yeucau> findnew() {
		return yeucauRepository.findnew();
	}

	@Override
	public List<Yeucau> findYeucauByDate(Nhanvien nhanvien, Douutien douutien, Date from, Date to) {
		return yeucauRepository.findYeucauBydate(nhanvien, douutien, from, to);
	}

	@Override
	public List<Yeucau> findYeucauByDate2(Nhanvien nhanvien, Douutien douutien, Date from, Date to) {
		return yeucauRepository.findYeucauBydate2(nhanvien, douutien, from, to);
	}

	@Override
	public Yeucau findbyId(int id) {
		return yeucauRepository.findYeucauById(id);
	}

	public boolean save(Yeucau yeucau) {
		try {
			yeucau = yeucauRepository.save(yeucau);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Yeucau> findYeucauByNhanvien(Nhanvien nhanvien) {
		return yeucauRepository.findYeucauByNhanvien(nhanvien);
	}

	@Override
	public List<Yeucau> findYeucaubysupporter(Nhanvien nhanvien) {
		return yeucauRepository.findYeucaubysupporter(nhanvien);
	}
	@Override
	public List<Yeucau> findYeucaubyemployee(Nhanvien nhanvien) {
		return yeucauRepository.findYeucaubyemployee(nhanvien);
	}

	@Override
	public Iterable<Yeucau> findall() {
		return yeucauRepository.findAll();
	}

	@Override
	public List<Yeucau> findYeucauBydouutiennvxl(Nhanvien nhanvien, Douutien douutien) {
		return yeucauRepository.findYeucauBydouutiennvxl(nhanvien, douutien);
	}

	@Override
	public List<Yeucau> findYeucauBydouutiennvg(Nhanvien nhanvien, Douutien douutien) {
		return yeucauRepository.findYeucauBydouutiennvg(nhanvien, douutien);
	}

	@Override
	public List<Yeucau> findYeucauBydateAdmin(Douutien douutien, Date from, Date to) {
		return yeucauRepository.findYeucauBydateAdmin(douutien, from, to);
	}

	@Override
	public List<Yeucau> findYeucauBydouutienAdmin(Douutien douutien) {
		return yeucauRepository.findYeucauBydouutienAdmin(douutien);
	}

}
