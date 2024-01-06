package com.nst.domaci1.converter;

import java.util.List;

public interface DtoEntityConverter<D, E> {

    D toDTO(E e);

    E toEntity(D d);

    default List<D> entitiesToDTOs(List<E> entities){
        return entities.stream().map(this::toDTO).toList();
    }

    default List<E> DTOsToEntities(List<D> DTOs){
        return DTOs.stream().map(this::toEntity).toList();
    }
}
