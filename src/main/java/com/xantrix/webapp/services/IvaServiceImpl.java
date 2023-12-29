package com.xantrix.webapp.services;

import com.xantrix.webapp.dtos.IvaDto;
import com.xantrix.webapp.entities.Iva;
import com.xantrix.webapp.repository.IvaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IvaServiceImpl implements IvaService {


    private IvaRepository ivaRepository;
    private ModelMapper modelMapper;

    @Autowired
    public IvaServiceImpl(IvaRepository ivaRepository, ModelMapper modelMapper) {
        this.ivaRepository = ivaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<IvaDto> SelTutti() {
        List<Iva> iva = ivaRepository.findAll();

        List<IvaDto> retVal = iva
                .stream()
                .map(source -> modelMapper.map(source, IvaDto.class))
                .collect(Collectors.toList());

        return retVal;
    }

}