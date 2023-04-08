package com.blogsite.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogsite.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer>{
	@Query("from Blog as b where b.user.id =:userid")
	public Page<Blog> findBlogsByUser(@Param("userid")int userid, Pageable pageable);
}
