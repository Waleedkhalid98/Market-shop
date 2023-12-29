package com.xantrix.webapp;

import com.xantrix.webapp.dtos.UtentiDto;
import com.xantrix.webapp.entities.Utenti;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xantrix.webapp.dtos.ArticoliDto;
import com.xantrix.webapp.dtos.BarcodeDto;
import com.xantrix.webapp.entities.Articoli;
import com.xantrix.webapp.entities.Barcode;

@Configuration
public class ModelMapperConfig 
{
	@Bean
    public ModelMapper modelMapper()
	{
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setSkipNullEnabled(true);

		modelMapper.addMappings(articoliMapping);
		modelMapper.addMappings(barcodeMapping);
		modelMapper.addConverter(articoliConverter);
		modelMapper.addConverter(utentiConverter);


		return modelMapper;
		
    }
	
	PropertyMap<Articoli, ArticoliDto> articoliMapping = new PropertyMap<Articoli,ArticoliDto>() 
	{
	      protected void configure() 
	      {
	         map().setDataCreazione(source.getDataCreaz());
	      }
	};
	PropertyMap<Barcode, BarcodeDto> barcodeMapping = new PropertyMap<Barcode,BarcodeDto>()
	{
		@Override
		protected void configure() {

					map().setTipo(source.getIdTipoArt());


		}

	};

	Converter<String, String> articoliConverter = new Converter<String, String>() 
	{
		  @Override
		  public String convert(MappingContext<String, String> context) 
		  {
			  return context.getSource() == null ? "" : context.getSource().trim();
		  }
	};

	Converter<Utenti, UtentiDto> utentiConverter = new Converter<Utenti, UtentiDto>() {
		@Override
		public UtentiDto convert(MappingContext<Utenti, UtentiDto> context) {
			Utenti source = context.getSource();
			UtentiDto destination =new UtentiDto();


			if (source != null) {
				// Map properties excluding Carta
				destination.setId_cliente(source.getId_cliente());
				destination.setNome(source.getNome());
				destination.setCognome(source.getCognome());
				destination.setEta(source.getEta());
				destination.setIndirizzo(source.getIndirizzo());
			}

			return destination;
		}
	};
	
	
}
