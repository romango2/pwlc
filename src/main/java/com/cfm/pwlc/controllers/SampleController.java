package com.cfm.pwlc.controllers;

import com.cfm.pwlc.service.SongNamesListCreatorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SampleController implements Initializable {

    private final SongNamesListCreatorService listCreatorService = new SongNamesListCreatorService();

    private static final String SONG_ROOT_PATH = "C://Work/Cantece/";
    private static final String PRAISE = "Praise/";
    private static final String WORSHIP = "Worship/";


    private final String DATE_FORMAT = "yyyy-MM-dd hh-mm";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);


    @FXML
    private Button CreateListButton;


    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void createList(ActionEvent actionEvent) throws IOException {

        List<String> songsList = listCreatorService.createSongList(Arrays.asList("1", "2", "3", "1", "2", "4", "5", "3"));


        File originalFile = new File("C://Work//Cantece/List.pptx");

        String timeStamp = LocalDateTime.now().format(formatter);
        File file = new File("C://Work//Cantece/List " + timeStamp + ".pptx");

        copyFileUsingApacheCommonsIO(originalFile, file);

        //opening an existing slide show
        FileInputStream originalContent = new FileInputStream(file);
        XMLSlideShow ppt = new XMLSlideShow(originalContent);

        //taking the two presentations that are to be merged
        String praiseSongsFile1 = SONG_ROOT_PATH + PRAISE + songsList.get(0);
        String praiseSongsFile2 = SONG_ROOT_PATH + PRAISE + songsList.get(1);
        String praiseSongsFile3 = SONG_ROOT_PATH + PRAISE + songsList.get(2);
        String worshipSongsFile1 = SONG_ROOT_PATH + WORSHIP + songsList.get(3);
        String worshipSongsFile2 = SONG_ROOT_PATH + WORSHIP + songsList.get(4);
        String praiseSongsFile4 = SONG_ROOT_PATH + PRAISE + songsList.get(5);
        String praiseSongsFile5 = SONG_ROOT_PATH + PRAISE + songsList.get(6);
        String worshipSongsFile3 = SONG_ROOT_PATH + WORSHIP + songsList.get(7);
        String[] inputs = {praiseSongsFile1, praiseSongsFile2, praiseSongsFile3,
                worshipSongsFile1, worshipSongsFile2,
                praiseSongsFile4, praiseSongsFile5,
                worshipSongsFile3};

        for (String input : inputs) {

            FileInputStream inputstream = new FileInputStream(input);
            XMLSlideShow src = new XMLSlideShow(inputstream);

            for (XSLFSlide srcSlide : src.getSlides()) {

                //merging the contents
                ppt.createSlide().importContent(srcSlide);
            }
        }

//        String file3 = "C://Work/Cantece/combinedpresentation.pptx";

        //creating the file object
        FileOutputStream out = new FileOutputStream(file);

        // saving the changes to a file
        ppt.write(out);
        System.out.println("Merging done successfully");
        out.close();



    /*    //opening an existing slide show
        File file = new File("C://Work//Cantece/List.pptx");
        FileInputStream inputstream = new FileInputStream(file);
        XMLSlideShow ppt = new XMLSlideShow(inputstream);

        //adding slides to the slodeshow
        XSLFSlide slide1 = ppt.createSlide();
        XSLFSlide slide2 = ppt.createSlide();

        //saving the changes
        FileOutputStream out = new FileOutputStream(file);
        ppt.write(out);

        System.out.println("Presentation edited successfully");
        out.close();
*/
    }

    private static void copyFileUsingApacheCommonsIO(File source, File dest) throws IOException {
        FileUtils.copyFile(source, dest);
    }
}
