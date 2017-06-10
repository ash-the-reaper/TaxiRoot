package shashi.uomtrust.ac.mu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shashi.uomtrust.ac.mu.dto.CarDetailsDTO;
import shashi.uomtrust.ac.mu.entity.Account;
import shashi.uomtrust.ac.mu.entity.CarDetails;
import shashi.uomtrust.ac.mu.enums.UserRole;
import shashi.uomtrust.ac.mu.enums.UserStatus;
import shashi.uomtrust.ac.mu.repository.AccountRepository;
import shashi.uomtrust.ac.mu.repository.CarDetailsRepository;

@Service
public class CarDetailsImp implements CarDetailsService{
	
	@Autowired
	private CarDetailsRepository carDetailsRepository;
	
	@Autowired
	private AccountRepository accountRepository ;
	

	@Override
	public Integer saveCardetails(CarDetailsDTO carDetailsDTO) {
		
		Account account = accountRepository.findById(carDetailsDTO.getAccounId());
		account.setRole(UserRole.TAXI_DRIVER);
		accountRepository.save(account);
		
		
		CarDetails carDetails = new CarDetails();
		carDetails.setAccount(account);
		carDetails.setMake(carDetailsDTO.getMake());
		carDetails.setNumOfPassenger(carDetailsDTO.getNumOfPassenger());
		carDetails.setPlateNum(carDetailsDTO.getPlateNum());
		
		carDetails.setPicture1(carDetailsDTO.getPicture1());
		carDetails.setPicture2(carDetailsDTO.getPicture2());
		carDetails.setPicture3(carDetailsDTO.getPicture3());
		carDetails.setPicture4(carDetailsDTO.getPicture4());

		CarDetails newCarDetails = carDetailsRepository.save(carDetails);
		
		return (int)newCarDetails.getCarId();
	}

	
}
