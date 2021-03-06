package com.cfm.pwlc.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by roman-ionut.goga on 8/31/2018.
 */
public class SongNamesListCreatorService {

    private static final String PROPERTIES_ROOT_PATH = "src/main/resources/";
    private static final String PRAISE_SONGS = "praise-songs.properties";
    private static final String WORSHIP_SONGS = "worship-songs.properties";


    public List<String> createSongList(List<String> songNumbers) {

        List<String> songNames = new ArrayList<String>();

        Properties praiseSongs = new Properties();
        Properties worshipSongs = new Properties();
        InputStream input = null;

        try {

            // load a properties file
            praiseSongs.load(new FileInputStream(PROPERTIES_ROOT_PATH + PRAISE_SONGS));
            worshipSongs.load(new FileInputStream(PROPERTIES_ROOT_PATH + WORSHIP_SONGS));

            int index = 1;
            for (String songNumber : songNumbers) {

                if ((index == 4) || (index == 5) || (index == 8)) {
                    // get the property value and print it out
                    songNames.add(worshipSongs.getProperty(songNumber));
                } else {
                    // get the property value and print it out
                    songNames.add(praiseSongs.getProperty(songNumber));
                }
                index++;
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


         return songNames;
    }

}

