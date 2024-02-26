package com.onlinehelpdesk.services;

import java.util.Date;
import java.util.List;

import com.onlinehelpdesk.entities.Douutien;
import com.onlinehelpdesk.entities.Nhanvien;
import com.onlinehelpdesk.entities.Yeucau;

public interface YeucauService {

	public List<Yeucau> findnew();
	public Iterable<Yeucau> findall();
	public List<Yeucau> findYeucauByNhanvien(Nhanvien nhanvien);

	public List<Yeucau> findYeucauByDate(Nhanvien nhanvien, Douutien douutien, Date from, Date to);
	public List<Yeucau> findYeucauByDate2(Nhanvien nhanvien, Douutien douutien, Date from, Date to);
	public List<Yeucau> findYeucauBydouutiennvxl(Nhanvien nhanvien, Douutien douutien);
	public List<Yeucau> findYeucauBydouutiennvg(Nhanvien nhanvien, Douutien douutien);
	public Yeucau findbyId(int id);
	public boolean save(Yeucau yeucau);
	public List<Yeucau> findYeucaubysupporter(Nhanvien nhanvien);
	public List<Yeucau> findYeucaubyemployee(Nhanvien nhanvien);
	public List<Yeucau> findYeucauBydateAdmin(Douutien douutien, Date from, Date to);
	public List<Yeucau> findYeucauBydouutienAdmin(Douutien douutien);
}
