package com.paro.siparisverbot.repository;

import com.paro.siparisverbot.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, String> {
    //SELECT PICTURE_ID FROM PICTURE WHERE START_DATE <= '2021-02-27' AND END_DATE >= '2021-02-27'
    @Query("select picture_id from Picture where market=?1 and start_date <=?2 and end_date>=?2")
    List<String> lookByDate(String botState, Date start_date);

    @Query("select picture_id from Picture where start_date <=?2 and end_date>=?2")
    List<String> lookByDate(Date start_date);
}
