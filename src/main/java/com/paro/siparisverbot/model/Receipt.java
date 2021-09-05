package com.paro.siparisverbot.model;

import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;

public class Receipt {

    public InputFile getReceipt(int userId){
        String pathName = "";
        InputFile inputFile = new InputFile();
        File file = new File(pathName);
        inputFile.setMedia(file);
        return inputFile;
    }


}
