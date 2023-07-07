package adt.demo.rewardprogram.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import adt.demo.rewardprogram.dto.CustomerDTO;
import adt.demo.rewardprogram.dto.TransactionRecordDTO;
import adt.demo.rewardprogram.exception.CustomerNotFoundException;
import adt.demo.rewardprogram.repo.CustomerRepository;
import adt.demo.rewardprogram.repo.TransactionRecordRepository;
import adt.demo.rewardprogram.service.CustomerService;
import adt.demo.rewardprogram.service.DataTransformer;
import adt.demo.rewardprogram.util.DataBuilder;
import io.micrometer.tracing.Tracer;

@Component
public class CustomerServiceImpl implements CustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerRepository custRepo;

	@Autowired
	private DataTransformer dataTransformer;

	private DataBuilder testData = new DataBuilder();

	@Autowired
	private TransactionRecordRepository tranRepo;

	@Autowired
    private Tracer tracer;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see adt.demo.rewardprogram.service.CustomerService#findById(java.lang.Long)
	 */
	@Override
	public CustomerDTO findById(Long custId) {
		Optional<adt.demo.rewardprogram.entity.Customer> foundCust = custRepo.findById(custId);
		
		if(foundCust.isEmpty()) {
			String traceId = tracer.currentSpan().context().traceId();
    		LOGGER.debug("Customer Details not found for given customer id");
    		CustomerNotFoundException customerNotFoundException = 
    				CustomerNotFoundException.builder()
    							.traceId(traceId)
    							.customerId(custId)
    							.build();
    		throw customerNotFoundException;
    	}
		else
		{ 
			return dataTransformer.toCustomerModel(foundCust.get());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see adt.demo.rewardprogram.service.CustomerService#findThreeMonthTrans()
	 */
	@Override
	public List<TransactionRecordDTO> findThreeMonthTrans() {
		List<TransactionRecordDTO> threeMonthTrans = new ArrayList<>();
		setup();
		List<adt.demo.rewardprogram.entity.TransactionRecord> threeMonthTranRecords = tranRepo
				.findAllInLastThreeMonth();

		threeMonthTranRecords.forEach(source -> {
			TransactionRecordDTO target = new TransactionRecordDTO();
			BeanUtils.copyProperties(source, target);
			target.setCustomer(dataTransformer.toCustomerModel(source.getCustomer()));
			threeMonthTrans.add(target);
		});

		return threeMonthTrans;
	}

	
	// Initial data setup
	// We can use SQL script to load data. 
	private void setup() {
		if (custRepo.findAll().isEmpty()) {
			adt.demo.rewardprogram.entity.Customer mary = custRepo.save(testData.createCustomer("Mary"));
			adt.demo.rewardprogram.entity.Customer tom = custRepo.save(testData.createCustomer("Tom"));

			tranRepo.save(testData.createTransactionRecord(mary, "20", 0, 2));
			tranRepo.save(testData.createTransactionRecord(mary, "120", 1, 2));
			tranRepo.save(testData.createTransactionRecord(mary, "100", 1, 5));
			tranRepo.save(testData.createTransactionRecord(mary, "20", 2, 0));
			tranRepo.save(testData.createTransactionRecord(mary, "60", 2, 4));
			tranRepo.save(testData.createTransactionRecord(mary, "120", 2, 4));

			tranRepo.save(testData.createTransactionRecord(tom, "120", 1, 3));
			tranRepo.save(testData.createTransactionRecord(tom, "20", 2, 1));
			tranRepo.save(testData.createTransactionRecord(tom, "100", 1, 15));
			tranRepo.save(testData.createTransactionRecord(tom, "56", 2, 14));
			tranRepo.save(testData.createTransactionRecord(tom, "110", 0, 15));
			tranRepo.save(testData.createTransactionRecord(tom, "66", 0, 14));
		}
	}

}
