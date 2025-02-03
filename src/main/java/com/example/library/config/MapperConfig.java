package com.example.library.config;

import com.example.library.dto.Book.CreateUpdateBookDto;
import com.example.library.dto.WishlistDto;
import com.example.library.entity.Book;
import com.example.library.entity.Wishlist;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Custom mapping for Book to BookDto
        modelMapper.addMappings(new PropertyMap<Book, CreateUpdateBookDto>() {
            @Override
            protected void configure() {
                map().setGroup(source.getGroup().getName());
            }
        });

        modelMapper.addMappings(new PropertyMap<Wishlist, WishlistDto>() {
            @Override
            protected void configure() {
                map().setUser(source.getUser().getUsername());
                map().setBook(source.getBook().getTitle());
            }
        });

        return modelMapper;
    }
}