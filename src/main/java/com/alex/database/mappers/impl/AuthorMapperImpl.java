package com.alex.database.mappers.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.alex.database.domain.dto.AuthorDto;
import com.alex.database.domain.entities.AuthorEntity;
import com.alex.database.mappers.Mapper;

@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto> {

    private final ModelMapper modelMapper;

    public AuthorMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configMapper();
    }

    private void configMapper() {
        modelMapper.addMappings(new PropertyMap<AuthorEntity, AuthorDto>() {

            @Override
            protected void configure() {
                map().setNumberOfBooks(100);
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
