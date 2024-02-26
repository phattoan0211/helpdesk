package com.onlinehelpdesk.services;

import com.onlinehelpdesk.entities.Douutien;

public interface DouutienService {

	public Douutien finddouutien(int madouutien);
	public Iterable<Douutien> findall();
}
