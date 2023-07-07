package adt.demo.rewardprogram.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import adt.demo.rewardprogram.entity.TransactionRecord;
import adt.demo.rewardprogram.repo.CustomerRepository;
import adt.demo.rewardprogram.repo.TransactionRecordRepository;
import adt.demo.rewardprogram.util.DataBuilder;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TransactionRecordRepositoryTest {

	@Autowired
	private CustomerRepository custRepo;

	private DataBuilder testData = new DataBuilder();

	@Autowired
	private TransactionRecordRepository testRepo;

	@Test
	void test_save_find() {

		custRepo.save(testData.createCustomer("Mary"));
		testRepo.save(testData.createTransactionRecord(custRepo.findByName("Mary"), "120", 3, 2));
		testRepo.save(testData.createTransactionRecord(custRepo.findByName("Mary"), "20", 4, 0));
		testRepo.save(testData.createTransactionRecord(custRepo.findByName("Mary"), "100", 1, 5));
		testRepo.save(testData.createTransactionRecord(custRepo.findByName("Mary"), "560", 2, 4));

		List<TransactionRecord> trans = testRepo.findAll();
		assertEquals(4, trans.size());

		trans = testRepo.findAllInLastThreeMonth();
		assertEquals(2, trans.size());

	}

}
