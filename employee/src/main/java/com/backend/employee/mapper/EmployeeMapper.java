package com.backend.employee.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.backend.employee.dto.EmployeeDTO;
import com.backend.employee.entity.Address;
import com.backend.employee.entity.BankDetail;
import com.backend.employee.entity.Contact;
import com.backend.employee.entity.Document;
import com.backend.employee.entity.Employee;

@Component
public class EmployeeMapper {

	public Employee toEntity(EmployeeDTO dto) {
		Employee e = new Employee();
		e.setSalutation(dto.getSalutation());
		e.setPhotoPath(dto.getPhotoPath());

		e.setFirstName(dto.getFirstName());
		e.setMiddleName(dto.getMiddleName());
		e.setLastName(dto.getLastName());
		e.setGender(dto.getGender());
		e.setNationality(dto.getNationality());
		e.setDob(dto.getDob());
		e.setEducation(dto.getEducation());
		e.setExperience(dto.getExperience());
		e.setDesignation(dto.getDesignation());
		e.setAccessStatus(dto.getAccessStatus());

		// Contact
		if (dto.getContact() != null) {
			Contact contact = new Contact();
			contact.setContactNumber(dto.getContact().getContactNumber());
			contact.setEmail(dto.getContact().getEmail());
			contact.setAltNumber(dto.getContact().getAltNumber());
			contact.setAltNumberType(dto.getContact().getAltNumberType());
			contact.setRelation(dto.getContact().getRelation());
			contact.setFatherName(dto.getContact().getFatherName());
			e.setContact(contact);
		}

		// Address
		if (dto.getAddress() != null) {
			Address address = new Address();
			address.setCountry(dto.getAddress().getCountry());
			address.setState(dto.getAddress().getState());
			address.setDistrict(dto.getAddress().getDistrict());
			address.setBlock(dto.getAddress().getBlock());
			address.setVillage(dto.getAddress().getVillage());
			address.setZipcode(dto.getAddress().getZipcode());
			e.setAddress(address);
		}

		// Bank Detail
		if (dto.getBankDetail() != null) {
			BankDetail bank = new BankDetail();
			bank.setBankName(dto.getBankDetail().getBankName());
			bank.setAccountNumber(dto.getBankDetail().getAccountNumber());
			bank.setIfscCode(dto.getBankDetail().getIfscCode());
			bank.setBranchName(dto.getBankDetail().getBranchName());
			bank.setPassbookPath(dto.getBankDetail().getPassbookFilePath());
			e.setBankDetail(bank);
		}
		// Documents
		if (dto.getDocuments() != null && !dto.getDocuments().isEmpty()) {
			List<Document> docs = dto.getDocuments().stream().map(d -> {
				Document doc = new Document();
				doc.setDocumentType(d.getDocumentType());
				doc.setNumber(d.getDocumentNumber());
				doc.setFilePath(d.getFilePath());
				doc.setEmployee(e);
				return doc;
			}).toList();
			e.setDocuments(docs);
		}

		return e;
	}
}
