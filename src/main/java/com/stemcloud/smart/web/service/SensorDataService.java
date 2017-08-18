package com.stemcloud.smart.web.service;

import com.stemcloud.smart.web.dao.SensorCameraPhotosRepository;
import com.stemcloud.smart.web.dao.SensorCameraRepository;
import com.stemcloud.smart.web.dao.SensorDataRepository;
import com.stemcloud.smart.web.domain.SensorCamera;
import com.stemcloud.smart.web.domain.SensorCameraPhotos;
import com.stemcloud.smart.web.domain.SensorData;
import com.stemcloud.smart.web.domain.view.Event;
import com.stemcloud.smart.web.domain.view.Timeline;
import com.stemcloud.smart.web.domain.view.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/17
 * Description:
 */
@Service
public class SensorDataService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SensorDataRepository dataRepository;

    @Autowired
    SensorCameraRepository cameraRepository;

    @Autowired
    SensorCameraPhotosRepository cameraPhotosRepository;

    public List<Video> getSensorCameraBySensorId(int id){
        List<Video> videos = new ArrayList<Video>();
        List<SensorCamera> sensorCameras = cameraRepository.findBySensorId(id);

        for (SensorCamera camera : sensorCameras){
            long videoId = camera.getId();
            List<SensorCameraPhotos> cameraPhotos = cameraPhotosRepository.findByVideoIdOrderByTimeInVideo(videoId);

            String sourcePath = camera.getSourcePath();
            String poster = "";
            int duration = camera.getDuration();
            Date date = camera.getDataTime();

            Event title = new Event("视频片段: " + videoId, "视频时长: " + duration);
            List<Event> events = new ArrayList<Event>();

            if (cameraPhotos.size() > 0){
                poster = cameraPhotos.get(0).getSourcePath();
                for (int i=1; i <= cameraPhotos.size(); i++){
                    SensorCameraPhotos photo = cameraPhotos.get(i-1);
                    int timeInVideo = photo.getTimeInVideo();
                    Date startTime = new Date(date.getTime() + timeInVideo*1000);
                    String url = photo.getSourcePath();

                    events.add(new Event(startTime, url, i, timeInVideo));
                }
            }

            Timeline cameraTimeline = new Timeline(title, events);
            videos.add(new Video(poster, sourcePath, cameraTimeline));
        }
        return videos;
    }

    public void generateRandomData() {
        Random random = new Random(10);

        List<SensorData> list = new ArrayList<SensorData>();

        for (int i=0; i<10; i++){
            SensorData sensorData = new SensorData();
            sensorData.setSensorId(1);
            sensorData.setType(1);
            sensorData.setEntrance("192.0.0.1:8080");
            sensorData.setValue(String.valueOf(random.nextInt(30)));
            sensorData.setDataTime(new Date());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list.add(sensorData);
            logger.info("generate data: " + i);
        }

        logger.info("list size: " + list.size());
        dataRepository.save(list);
    }

    public void generateCameraData(){
        SensorCamera camera = new SensorCamera();
        camera.setDataTime(new Date());
        camera.setEntrance("192.0.0.1:8080");
        camera.setType(2);
        camera.setSensorId(2);
        camera.setDuration(46);
        camera.setSourcePath("//vjs.zencdn.net/v/oceans.mp4");
        cameraRepository.save(camera);


        for (int i=1; i<=3; i++) {
            SensorCameraPhotos cameraPhotos = new SensorCameraPhotos();
            cameraPhotos.setSensorId(2);
            cameraPhotos.setTimeInVideo(i*5);
            cameraPhotos.setVideoId(1);
            cameraPhotos.setSourcePath("/img/" + i + ".jpg");
            cameraPhotosRepository.save(cameraPhotos);
        }

    }
}
