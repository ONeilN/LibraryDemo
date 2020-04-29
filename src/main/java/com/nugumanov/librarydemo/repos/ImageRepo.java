package com.nugumanov.librarydemo.repos;

import com.nugumanov.librarydemo.domain.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepo extends CrudRepository<Image, Long> {
}
