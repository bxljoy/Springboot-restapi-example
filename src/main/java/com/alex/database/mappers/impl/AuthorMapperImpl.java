package com.alex.database.mappers.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.alex.database.domain.dto.AuthorDto;
import com.alex.database.domain.entities.AuthorEntity;
import com.alex.database.mappers.Mapper;
import com.alex.database.services.BookService;

@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto> {

    private final ModelMapper modelMapper;
    private final BookService bookService;

    public AuthorMapperImpl(ModelMapper modelMapper, BookService bookService) {
        this.modelMapper = modelMapper;
        this.bookService = bookService;
        configMapper();
    }

    private void configMapper() {
        modelMapper.addMappings(new PropertyMap<AuthorEntity, AuthorDto>() {

            @Override
            protected void configure() {
                using(ctx -> bookService.getNumberOfBooksByAuthorId(((AuthorEntity) ctx.getSource()).getId()))
                        .map(source, destination.getNumberOfBooks());
            }
        });
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
    }

}
