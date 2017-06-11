package shashi.uomtrust.ac.mu.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shashi.uomtrust.ac.mu.dto.CarDetailsDTO;
import shashi.uomtrust.ac.mu.entity.Account;
import shashi.uomtrust.ac.mu.entity.CarDetails;
import shashi.uomtrust.ac.mu.enums.UserRole;
import shashi.uomtrust.ac.mu.repository.AccountRepository;
import shashi.uomtrust.ac.mu.repository.CarDetailsRepository;

@Service
public class CarDetailsImp implements CarDetailsService{
	
	@Autowired
	private CarDetailsRepository carDetailsRepository;
	
	@Autowired
	private AccountRepository accountRepository ;
	

	@Override
	public CarDetailsDTO saveCardetails(CarDetailsDTO carDetailsDTO) {
		
		Account account = accountRepository.findByAccountId(carDetailsDTO.getAccountId());
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
		
		CarDetails newCardetails = carDetailsRepository.save(carDetails);
		
		CarDetailsDTO newCarDetailsDTO = new CarDetailsDTO();
		newCarDetailsDTO.setCarId(newCardetails.getCarId());
		newCarDetailsDTO.setAccountId(newCardetails.getAccount().getAccountId());
		newCarDetailsDTO.setMake(newCardetails.getMake());
		newCarDetailsDTO.setNumOfPassenger(newCardetails.getNumOfPassenger());
		
		if(newCardetails.getPicture1() != null)
			newCarDetailsDTO.setPicture1(Base64.encodeBase64(newCardetails.getPicture1()));
		
		if(newCardetails.getPicture2() != null)
			newCarDetailsDTO.setPicture2(Base64.encodeBase64(newCardetails.getPicture2()));
		
		if(newCardetails.getPicture3() != null)
			newCarDetailsDTO.setPicture3(Base64.encodeBase64(newCardetails.getPicture3()));
		
		if(newCardetails.getPicture4() != null)
			newCarDetailsDTO.setPicture4(Base64.encodeBase64(newCardetails.getPicture4()));
		
		return newCarDetailsDTO;
	}

	
}
