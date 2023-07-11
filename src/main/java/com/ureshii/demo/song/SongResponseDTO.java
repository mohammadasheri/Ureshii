package com.ureshii.demo.song;


import com.ureshii.demo.artist.ArtistResponseDTO;

import java.util.List;

public record SongResponseDTO(Long id, String name, String songMediaType, String pictureMediaType, String language,
                              String bitrate, Long likes, Long playCount, List<ArtistResponseDTO> artist) {
}
