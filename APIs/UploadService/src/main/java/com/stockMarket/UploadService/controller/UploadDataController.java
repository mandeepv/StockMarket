package com.stockMarket.UploadService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stockMarket.UploadService.constants.ResponseCode;
import com.stockMarket.UploadService.model.CompanyStockPrice;
import com.stockMarket.UploadService.repository.CompanyRepository;
import com.stockMarket.UploadService.response.serverResponse;
import com.stockMarket.UploadService.service.UploadFileService;
import com.stockMarket.UploadService.util.UploadHelper;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/data")
public class UploadDataController {
	
	@Autowired CompanyRepository companyRepository;
	
	@Autowired
	private UploadFileService fileService;
	
	@GetMapping("/excelData")
	public ResponseEntity<List<CompanyStockPrice>> getAllStockPrices(@RequestParam("id") Long companyId) {
	    try {
	      List<CompanyStockPrice> stockPrices = fileService.getAllStockPrices(companyRepository.findById(companyId).orElse(null));

	      if (stockPrices.isEmpty()) {
	    	  System.out.println("Stock is Empty Baby!!!");
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(stockPrices, HttpStatus.OK);
	    } catch (Exception e) {
	    	e.printStackTrace();
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

	@PostMapping("/uploadDataInUpload")
	public ResponseEntity<serverResponse> uploadUploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("id") String id) {
		
		serverResponse resp = new serverResponse();
		Long companyId = Long.parseLong(id);
		if (UploadHelper.hasUploadFormat(file)) {
			try {
				String response = fileService.save(file, companyRepository.findById(companyId).orElse(null));
				if(response.contains("Invalid")) {
					resp.setStatus("600");
					resp.setMessage(response);
				}
				else {
					resp.setMessage("UPLOAD SUCCESSFUL");
					resp.setStatus(ResponseCode.SUCCESS_CODE);
				}
			} catch (Exception e) {
				e.printStackTrace();
				resp.setMessage(e.getMessage());
				resp.setStatus(ResponseCode.FAILURE_CODE);
			}
		}
		else {
			resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
			resp.setMessage("UPLOAD EXCEL FILE ONLY");
		}
		return new ResponseEntity<serverResponse>(resp, HttpStatus.ACCEPTED);
	}
}
