package com.onlinehelpdesk.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onlinehelpdesk.entities.Douutien;
import com.onlinehelpdesk.entities.Nhanvien;
import com.onlinehelpdesk.entities.Yeucau;

@Repository
public interface YeucauRepository extends CrudRepository<Yeucau, Integer> {
	@Query("from Yeucau where nhanvienByNvGui = :nhanvien or nhanvienByNvXuly=:nhanvien order by mayeucau desc")
	public List<Yeucau> findYeucauByNhanvien(@Param("nhanvien") Nhanvien nhanvien);

	@Query("from Yeucau where nhanvienByNvGui = :nhanvien and ngaygui >= :from and ngaygui <= :to and douutien = :douutien ORDER BY mayeucau DESC")
	public List<Yeucau> findYeucauBydate(@Param("nhanvien") Nhanvien nhanvien, @Param("douutien") Douutien douutien,
			@Param("from") Date from, @Param("to") Date to);

	@Query("from Yeucau where nhanvienByNvXuly = :nhanvien and ngaygui >= :from and ngaygui <= :to and douutien = :douutien ORDER BY mayeucau DESC")
	public List<Yeucau> findYeucauBydate2(@Param("nhanvien") Nhanvien nhanvien, @Param("douutien") Douutien douutien,
			@Param("from") Date from, @Param("to") Date to);

	@Query("from Yeucau where mayeucau= :id")
	public Yeucau findYeucauById(@Param("id") int id);

	@Query("from Yeucau  where nhanvienByNvXuly is null order by mayeucau desc")
	public List<Yeucau> findnew();

	@Query("from Yeucau where nhanvienByNvXuly=:nhanvien order by mayeucau desc")
	public List<Yeucau> findYeucaubysupporter(@Param("nhanvien") Nhanvien nhanvien);

	@Query("from Yeucau where nhanvienByNvGui=:nhanvien order by mayeucau desc")
	public List<Yeucau> findYeucaubyemployee(@Param("nhanvien") Nhanvien nhanvien);

	@Query("from Yeucau where nhanvienByNvXuly = :nhanvien and douutien = :douutien ORDER BY mayeucau DESC")
	public List<Yeucau> findYeucauBydouutiennvxl(@Param("nhanvien") Nhanvien nhanvien,
			@Param("douutien") Douutien douutien);

	@Query("from Yeucau where nhanvienByNvGui = :nhanvien and douutien = :douutien ORDER BY mayeucau DESC")
	public List<Yeucau> findYeucauBydouutiennvg(@Param("nhanvien") Nhanvien nhanvien,
			@Param("douutien") Douutien douutien);

	@Query("from Yeucau where ngaygui >= :from and ngaygui <= :to and douutien = :douutien ORDER BY mayeucau DESC")
	public List<Yeucau> findYeucauBydateAdmin(@Param("douutien") Douutien douutien, @Param("from") Date from,
			@Param("to") Date to);

	@Query("from Yeucau where douutien = :douutien ORDER BY mayeucau DESC")
	public List<Yeucau> findYeucauBydouutienAdmin(@Param("douutien") Douutien douutien);
}
