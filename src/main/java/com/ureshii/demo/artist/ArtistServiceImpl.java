package com.ureshii.demo.artist;

import com.ureshii.demo.config.file.ImageWriterGateway;
import com.ureshii.demo.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public record ArtistServiceImpl(ArtistRepository repository, ImageWriterGateway gateway) implements ArtistService {

    @Override
    public Artist createArtist(CreateArtistDTO dto) {
        log.info("Artist service: create Artist");
        String artistFileAddress = generateNewFileAddress(dto.pictureFileType());
        String pictureFileAddress = generateNewFileAddress(dto.pictureFileType());
        gateway.saveFile(pictureFileAddress, dto.pictureBytes());
        Artist artist = Artist.builder().name(dto.name()).countryOfOrigin(dto.countryOfOrigin())
                .pictureAddress(artistFileAddress).build();
        return repository.save(artist);
    }

    @Override
    public Artist getArtistById(Long id) throws NotFoundException {
        Optional<Artist> artistOptional = repository.findById(id);
        return artistOptional.orElseThrow(()-> new NotFoundException("Artist not found."));
    }

    private String generateNewFileAddress(String fileType) {
        log.debug("generate new file address");
        SimpleDateFormat formatter = new SimpleDateFormat("/yyyy/MM/dd/HH/mm/");
        Date date = new Date();
        String address = formatter.format(date) + UUID.randomUUID() + "." + fileType;
        log.debug("generated address {}", address);
        return address;
    }
}
