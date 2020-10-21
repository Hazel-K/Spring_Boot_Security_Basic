package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;

// @Repository라는 어노테이션이 없어도 IoC된다. JpaRepository를 상속했기 때문에 자동으로 빈으로 등록됨
public interface UserRepository extends JpaRepository<User, Integer> { // 사용되는 Object는 User, User의 PK값의 형태는 Integer
	// CRUD 함수를 JpaRepository가 들고 있으므로 따로 적을 필요가 없음
}
