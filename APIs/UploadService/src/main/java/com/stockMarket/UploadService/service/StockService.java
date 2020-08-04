package com.stockMarket.UploadService.service;

import java.util.List;

import com.stockMarket.UploadService.model.StockExchange;


public interface StockService {
	
	List<StockExchange> findAll();

	StockExchange save(StockExchange newStock);

	StockExchange findById(Long stockExchangeId);

	StockExchange findByName(String name);
}
