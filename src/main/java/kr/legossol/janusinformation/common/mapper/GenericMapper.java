package kr.legossol.janusinformation.common.mapper;

import java.util.List;

public interface GenericMapper<E, D> {

    D toDto(E e);

    E toEntity(D d);

    List<E> toEntityList(List<D> d);

    List<D> toDtoList(List<E> e);
}

