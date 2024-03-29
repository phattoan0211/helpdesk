package com.onlinehelpdesk.entities;
// Generated Oct 31, 2023, 11:57:37 AM by Hibernate Tools 4.3.6.Final

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


/**
 * Douutien generated by hbm2java
 */
@Entity
@Table(name = "douutien")
public class Douutien implements java.io.Serializable {

	private int madouutien;
	private String tendouutien;
	private Set<Yeucau> yeucaus = new HashSet<>(0);

	public Douutien() {
	}

	public Douutien(int madouutien) {
		this.madouutien = madouutien;
	}

	public Douutien(int madouutien, String tendouutien, Set<Yeucau> yeucaus) {
		this.madouutien = madouutien;
		this.tendouutien = tendouutien;
		this.yeucaus = yeucaus;
	}

	@Id

	@Column(name = "madouutien", unique = true, nullable = false)
	public int getMadouutien() {
		return this.madouutien;
	}

	public void setMadouutien(int madouutien) {
		this.madouutien = madouutien;
	}

	@Column(name = "tendouutien")
	public String getTendouutien() {
		return this.tendouutien;
	}

	public void setTendouutien(String tendouutien) {
		this.tendouutien = tendouutien;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "douutien")
	public Set<Yeucau> getYeucaus() {
		return this.yeucaus;
	}

	public void setYeucaus(Set<Yeucau> yeucaus) {
		this.yeucaus = yeucaus;
	}

}
