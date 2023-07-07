package adt.demo.rewardprogram.service;

import java.util.List;

import adt.demo.rewardprogram.dto.CustomerDTO;
import adt.demo.rewardprogram.dto.TransactionRecordDTO;

public interface CustomerService {

	CustomerDTO findById(Long custId);

	List<TransactionRecordDTO> findThreeMonthTrans();

}