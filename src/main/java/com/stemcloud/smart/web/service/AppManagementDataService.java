package com.stemcloud.smart.web.service;

import com.stemcloud.smart.web.dao.AppInfoRepository;
import com.stemcloud.smart.web.dao.SensorDataRepository;
import com.stemcloud.smart.web.dao.SensorInfoRepository;
import com.stemcloud.smart.web.domain.AppInfo;
import com.stemcloud.smart.web.domain.SensorData;
import com.stemcloud.smart.web.domain.SensorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/4
 * Description: some basic operation of app and sensor data
 */
@Service
@Transactional
public class AppManagementDataService {
    private final SensorInfoRepository sensorInfoRepository;
    private final AppInfoRepository appInfoRepository;

    @Autowired
    public AppManagementDataService(SensorInfoRepository sensorInfoRepository, AppInfoRepository appInfoRepository) {
        this.sensorInfoRepository = sensorInfoRepository;
        this.appInfoRepository = appInfoRepository;
    }

    public List<SensorInfo> getSharedSensorInfo(){
        return sensorInfoRepository.findByIsShare(1);
    }

    public List<SensorInfo> getSensorInfoByCreator(String creator){
        return sensorInfoRepository.findByCreator(creator);
    }

    public List<SensorInfo> getSensorInfoByCreatorAndShared(String creator){
        return sensorInfoRepository.findByCreatorOrIsShare(creator, 1);
    }

    public long saveNewApp(Map<String, String> queryParams, String user){
        String name = queryParams.get("new-app-name");
        String description = queryParams.get("new-app-description");

        AppInfo appInfo = new AppInfo();
        appInfo.setCreator(user);
        appInfo.setName(name);
        appInfo.setDescription(description);

        return appInfoRepository.save(appInfo).getId();
    }

    public int saveEditApp(Map<String, String> queryParams){
        int id = Integer.valueOf(queryParams.get("id"));
        String name = queryParams.get("new-app-name");
        String description = queryParams.get("new-app-description");

        return appInfoRepository.updateAppInfo(id, name, description);
    }

    public SensorInfo saveNewSensor(Map<String, String> queryParams, String user){
        int appId = Integer.parseInt(queryParams.get("appId"));

        String name = queryParams.get("new-sensor-name");
        String code = queryParams.get("new-sensor-code");
        int type = Integer.parseInt(queryParams.get("new-sensor-type"));
        String city = queryParams.get("new-sensor-city");
        String description = queryParams.get("new-sensor-description");

        SensorInfo sensorInfo = new SensorInfo();
        sensorInfo.setName(name);
        sensorInfo.setCode(code);
        sensorInfo.setType(type);
        sensorInfo.setDescription(description);
        sensorInfo.setAppId(appId);
        sensorInfo.setCreator(user);

        if (!queryParams.get("new-sensor-longitude").trim().isEmpty()){
            sensorInfo.setLongitude(Double.parseDouble(queryParams.get("new-sensor-longitude")));
        }
        if (!queryParams.get("new-sensor-latitude").trim().isEmpty()){
            sensorInfo.setLatitude(Double.parseDouble(queryParams.get("new-sensor-latitude")));
        }
        if (!city.trim().isEmpty())
            sensorInfo.setCity(city);

        return sensorInfoRepository.save(sensorInfo);
    }

    public int saveEditSensor(int sensorId, Map<String, String> queryParams){
        String name = queryParams.get("new-sensor-name");
        String code = queryParams.get("new-sensor-code");
        int type = Integer.parseInt(queryParams.get("new-sensor-type"));
        String city = queryParams.get("new-sensor-city");
        double longitude = Double.parseDouble(queryParams.get("new-sensor-longitude"));
        double latitude = Double.parseDouble(queryParams.get("new-sensor-latitude"));
        String description = queryParams.get("new-sensor-description");

        return sensorInfoRepository.updateSensorInfo(sensorId, name, code, type, longitude, latitude, city, description);
    }

    public int deleteSensor(long id){
        return sensorInfoRepository.deleteById(id);
    }

    public int deleteApp(long id){
        return appInfoRepository.deleteById(id);
    }
}
