package com.paro.siparisverbot.service;

import com.paro.siparisverbot.repository.PictureRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class PictureService {


    private final PictureRepository pictureRepository;

    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public List<String> lookForDate(Date date){
        List<String> pictures = pictureRepository.lookByDate(date);
        return pictures;
    }

    public List<String> lookForDate(String botState, Date date){
        List<String> pictures = pictureRepository.lookByDate(botState, date);
        return pictures;
    }

}
