package com.onlinehelpdesk.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onlinehelpdesk.entities.Nhanvien;

@Repository
public interface NhanvienRepository extends CrudRepository<Nhanvien, Integer> {
	@Query("from Nhanvien where username=:username")
	public Nhanvien findusername(@Param("username") String username);

	@Query("from Nhanvien where quyen=:role")
	public List<Nhanvien> findbyrole(@Param("role") int role);
	
	@Query("from Nhanvien order by quyen desc")
	public List<Nhanvien> findall();

}
