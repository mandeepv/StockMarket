package com.stockMarket.UploadService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stockMarket.UploadService.model.Company;
import com.stockMarket.UploadService.model.StockExchange;


@Repository
@Transactional
public interface CompanyRepository extends JpaRepository<Company, Long>{

	Company findByCompanyCode(String companyCode);
	List<Company> findAllByStockExchanges(StockExchange stockExchange);
}
