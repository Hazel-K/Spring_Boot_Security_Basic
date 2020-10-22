package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;

// 이 클래스는 기본 MVC 패턴의 Mapper에 해당하는 기능을 수행한다.
// @Repository라는 어노테이션이 없어도 IoC된다. JpaRepository를 상속했기 때문에 자동으로 빈으로 등록됨
public interface UserRepository extends JpaRepository<User, Integer> { // 사용되는 Object는 User, User의 PK값의 형태는 Integer
	// 기본 CRUD 함수를 JpaRepository가 들고 있으므로 따로 적을 필요가 없음
	
	/**
	 * findBy 규칙 (JPA Query Method 참조)
	 * findBy + ColumnName(Type Name) 이라는 메소드를 만드는 것으로 SELECT문을 자동으로 호출할 수 있다.
	 */
	User findByUsername(String username); // SELECT * FROM user WHERE username = ? 이 호출된다.
}
