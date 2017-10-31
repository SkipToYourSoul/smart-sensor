package com.stemcloud.smart.web.service;

import com.stemcloud.smart.web.dao.data.SensorCameraPhotosRepository;
import com.stemcloud.smart.web.dao.data.SensorCameraRepository;
import com.stemcloud.smart.web.dao.data.SensorDataRepository;
import com.stemcloud.smart.web.domain.data.SensorCamera;
import com.stemcloud.smart.web.domain.data.SensorCameraPhotos;
import com.stemcloud.smart.web.domain.data.SensorData;
import com.stemcloud.smart.web.view.Chart;
import com.stemcloud.smart.web.view.Video;
import com.stemcloud.smart.web.view.chart.TimeSeries;
import com.stemcloud.smart.web.view.timeline.Era;
import com.stemcloud.smart.web.view.timeline.Event;
import com.stemcloud.smart.web.view.timeline.Timeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/17
 * Description:
 */
@Service
public class SensorDataService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SensorDataRepository dataRepository;
    private final SensorCameraRepository cameraRepository;
    private final SensorCameraPhotosRepository cameraPhotosRepository;

    @Autowired
    public SensorDataService(SensorDataRepository dataRepository, SensorCameraRepository cameraRepository, SensorCameraPhotosRepository cameraPhotosRepository) {
        this.dataRepository = dataRepository;
        this.cameraRepository = cameraRepository;
        this.cameraPhotosRepository = cameraPhotosRepository;
    }

    public List<SensorData> getSensorDataByAppId(int appId){
        return dataRepository.findSensorDataByAppId(appId);
    }

    /**
     * time series data for echarts
     * @param sensorId
     * @return list of time series data for chart
     */
    public List<Chart> getSensorTimeSeriesDataBySensorId(long sensorId, long expId) {
        List<SensorData> sensorData = dataRepository.findBySensorIdAndExpIdOrderByDataTime(sensorId, expId);
        Map<String, List<TimeSeries>> map = new HashMap<String, List<TimeSeries>>();

        for (SensorData data : sensorData) {
            Date date = data.getDataTime();
            String value = data.getValue();
            String flag = data.getFlag();

            if (!map.containsKey(flag)){
                List<TimeSeries> list = new ArrayList<TimeSeries>();
                list.add(new TimeSeries(date, value));
                map.put(flag, list);
            } else {
                List<TimeSeries> list = map.get(flag);
                list.add(new TimeSeries(date, value));
                map.put(flag, list);
            }
        }

        List<Chart> charts = new ArrayList<Chart>();
        for (Map.Entry<String, List<TimeSeries>> entry: map.entrySet()){
            String flag = entry.getKey();
            List<TimeSeries> timeSeries = entry.getValue();
            Date startDate = (Date) timeSeries.get(0).getValue().get(0);
            Date endDate = (Date) timeSeries.get(timeSeries.size() - 1).getValue().get(0);

            charts.add(new Chart(startDate, endDate, flag, timeSeries));
        }

        return charts;
    }

    /**
     * get camera data: video, timeline
     * @param sensorId sensor id
     * @return List<Video>
     */
    public List<Video> getSensorCameraBySensorId(long sensorId, long expId){
        List<Video> videos = new ArrayList<Video>();
        List<SensorCamera> sensorCameras = cameraRepository.findBySensorIdAndExpId(sensorId, expId);

        int count = 0;
        for (SensorCamera camera : sensorCameras){
            count += 1;
            long videoId = camera.getId();
            List<SensorCameraPhotos> cameraPhotos = cameraPhotosRepository.findByVideoIdOrderByTimeInVideo(videoId);

            String sourcePath = camera.getSourcePath();
            String poster = "/img/oceans.png";
            int duration = camera.getDuration();
            Date startDate = camera.getDataTime();
            Date endDate = new Date(startDate.getTime() + duration*1000);

            // --- title
            Event title = new Event();
            title.setText("视频片段: " + count, "视频时长: " + duration + "s");

            // --- era
            Era era = new Era();
            era.setStart_date(startDate);
            era.setEnd_date(endDate);

            // --- events
            List<Event> events = new ArrayList<Event>();
            if (cameraPhotos.size() > 0){
                poster = cameraPhotos.get(0).getSourcePath();
                for (int i=1; i < cameraPhotos.size(); i++){
                    SensorCameraPhotos photo = cameraPhotos.get(i);
                    int timeInVideo = photo.getTimeInVideo();
                    Date startTime = new Date(startDate.getTime() + timeInVideo*1000);
                    String url = photo.getSourcePath();

                    Event event = new Event();
                    event.setMedia(url, timeInVideo);
                    event.setStart_date(startTime);
                    event.setEnd_date(startTime);
                    event.setUnique_id(i);

                    events.add(event);
                    // logger.info(event.toString());
                }
            }

            Timeline cameraTimeline = new Timeline(title, events, era);
            videos.add(new Video(poster, sourcePath, cameraTimeline, startDate, endDate));
        }
        return videos;
    }

    public void generateRandomData(int sensorId) {
        Random random = new Random(10);

        List<SensorData> list = new ArrayList<SensorData>();

        for (int i=0; i<10; i++){
            SensorData sensorData = new SensorData();
            sensorData.setSensorId(sensorId);
            sensorData.setType(1);
            sensorData.setEntrance("192.0.0.1:8080");
            sensorData.setFlag("光照");
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

    public void generateCameraData(int sensorId){
        SensorCamera camera = new SensorCamera();
        camera.setDataTime(new Date());
        camera.setEntrance("192.0.0.1:8080");
        camera.setType(2);
        camera.setSensorId(sensorId);
        camera.setDuration(46);
        camera.setSourcePath("/img/oceans.mp4");
        SensorCamera saveCamera = cameraRepository.save(camera);


        for (int i=1; i<=3; i++) {
            SensorCameraPhotos cameraPhotos = new SensorCameraPhotos();
            cameraPhotos.setSensorId(sensorId);
            cameraPhotos.setTimeInVideo(i*5);
            cameraPhotos.setVideoId(saveCamera.getId());
            cameraPhotos.setSourcePath("/img/" + i + ".jpg");
            cameraPhotosRepository.save(cameraPhotos);
        }

    }
}
