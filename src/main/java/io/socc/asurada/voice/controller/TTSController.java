package io.socc.asurada.voice.controller;

import io.socc.asurada.voice.service.TTSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/api")
public class TTSController {

    @Autowired
    TTSService ttsService;

    @Autowired
    ServletContext servletContext;

    // Response for GET /api/address
    @RequestMapping(value = "/tts", method = {RequestMethod.GET})
    public String tts_response(@RequestParam(required = true)String text, HttpServletResponse response){
        // System.out.println("--playFile");
        File file = new File("src/main/resources/TTS/" + ttsService.tts_request(text) + ".mp3");
        FileInputStream fis;
        byte[] buffer=null;
        try {
            fis = new FileInputStream(file);
            buffer= new byte[fis.available()];
            fis.read(buffer);
            fis.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        response.setContentType("audio/mpeg");
        try{
            response.getOutputStream().write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "TTS";
    }
}
