package com.paro.siparisverbot.service;

import com.paro.siparisverbot.repository.PictureRepository;
import com.paro.siparisverbot.utils.BotState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {PictureService.class})
@ExtendWith(SpringExtension.class)
public class PictureServiceTest {
    @MockBean
    private PictureRepository pictureRepository;

    @Autowired
    private PictureService pictureService;

    @Test
    public void testLookForDate() {
        ArrayList<String> stringList = new ArrayList<String>();
        String botState = BotState.A101_SELECTED.state;
        when(this.pictureRepository.lookByDate(botState, (java.util.Date) any())).thenReturn(stringList);

        List<String> actualLookForDateResult = this.pictureService.lookForDate(botState, (Date) new java.util.Date(1L));
        assertSame(stringList, actualLookForDateResult);
        assertTrue(actualLookForDateResult.isEmpty());
        verify(this.pictureRepository).lookByDate(botState, (java.util.Date) any());
    }
}

