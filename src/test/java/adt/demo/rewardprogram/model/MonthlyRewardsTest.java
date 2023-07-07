package adt.demo.rewardprogram.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import adt.demo.rewardprogram.dto.CustomerDTO;
import adt.demo.rewardprogram.dto.MonthlyRewards;
import adt.demo.rewardprogram.dto.TransactionRecordDTO;
import adt.demo.rewardprogram.util.DataBuilder;

public class MonthlyRewardsTest {
	private DataBuilder dataBuilder = new DataBuilder();

	private CustomerDTO mary = dataBuilder.createCustomerDTO("Mary");

	private MonthlyRewards testClass = new MonthlyRewards(mary);

	@Test
	void test_getMonthlyTotalRewardPoint() {

		List<TransactionRecordDTO> trans = new ArrayList<>();
		trans.add(dataBuilder.createTransactionRecordDTO(mary, "90", 2, 1));
		trans.add(dataBuilder.createTransactionRecordDTO(mary, "50", 2, 1));
		testClass.getMonthlyTrans().put(Month.JUNE, trans);

		assertEquals(0, testClass.getMonthlyTotalRewardPoint().get(Month.JUNE).compareTo(new BigDecimal("40")));

	}

}
