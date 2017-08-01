package com.carledwinti.springbootrestsecurity.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carledwinti.springbootrestsecurity.model.User;
import com.carledwinti.springbootrestsecurity.repository.UserRepository;

@RestController
public class UserResource {

	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ResponseEntity<List<User>> list(){
		List<User> list = userRepository.findAll();
		
		if(list != null){
			for (User user : list) {
				user.setPassword("******");
			}
		}else{
			list = new ArrayList<>();
		}
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
}
