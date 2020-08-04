package com.stockMarket.UploadService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockMarket.UploadService.model.Company;
import com.stockMarket.UploadService.model.CompanyStockPrice;


public interface CompanyStockPriceRepository extends JpaRepository<CompanyStockPrice, Long> {

	List<CompanyStockPrice> findAllByCompany(Company company);
}