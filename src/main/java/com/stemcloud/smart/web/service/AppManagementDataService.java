package com.stemcloud.smart.web.service;

import com.stemcloud.smart.web.dao.base.AppInfoRepository;
import com.stemcloud.smart.web.dao.base.SensorInfoRepository;
import com.stemcloud.smart.web.domain.base.AppInfo;
import com.stemcloud.smart.web.domain.base.SensorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/4
 * Description: some basic operation of app and sensor data
 * @author liye
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

    public List<AppInfo> getSharedAppInfo(){
        return appInfoRepository.findByIsShare(1);
    }

    public List<SensorInfo> getSensorInfoByCreator(String creator){
        return sensorInfoRepository.findByCreator(creator);
    }

    /**
     * add a app, save to mysql
     * @param queryParams
     * @param user
     * @return app id
     */
    public long saveNewApp(Map<String, String> queryParams, String user){
        String name = queryParams.get("new-app-name");
        String description = queryParams.get("new-app-description");

        AppInfo appInfo = new AppInfo();
        appInfo.setCreator(user);
        appInfo.setName(name);
        appInfo.setDescription(description);

        return appInfoRepository.save(appInfo).getId();
    }

    /**
     * edit app info, save to mysql
     * @param queryParams
     * @return appId
     */
    public int saveEditApp(Map<String, String> queryParams){
        int id = Integer.valueOf(queryParams.get("id"));
        String name = queryParams.get("new-app-name");
        String description = queryParams.get("new-app-description");

        return appInfoRepository.updateAppInfo(id, name, description);
    }

    /**
     * delete a app
     * @param id
     * @return appId
     */
    public int deleteApp(long id){
        return appInfoRepository.deleteById(id);
    }

    /**
     * add a sensor, save to mysql
     * @param queryParams
     * @param user
     * @return
     */
    public SensorInfo saveNewSensor(Map<String, String> queryParams, String user){
        String name = queryParams.get("new-sensor-name");
        String code = queryParams.get("new-sensor-code");
        String description = queryParams.get("new-sensor-description");

        SensorInfo sensorInfo = new SensorInfo();
        sensorInfo.setName(name);
        sensorInfo.setCode(code);
        sensorInfo.setDescription(description);
        sensorInfo.setCreator(user);

        String city = queryParams.get("new-sensor-city");
        String longitude = queryParams.get("new-sensor-longitude").trim();
        String latitude = queryParams.get("new-sensor-latitude").trim();
        if (!longitude.isEmpty()){
            sensorInfo.setLongitude(Double.parseDouble(queryParams.get("new-sensor-longitude")));
        }
        if (!latitude.isEmpty()){
            sensorInfo.setLatitude(Double.parseDouble(queryParams.get("new-sensor-latitude")));
        }
        if (!city.trim().isEmpty()) {
            sensorInfo.setCity(city);
        }

        return sensorInfoRepository.save(sensorInfo);
    }

    /**
     * edit sensor info, save to mysql
     * @param sensorId
     * @param queryParams
     * @return
     */
    public int saveEditSensor(int sensorId, Map<String, String> queryParams){
        String name = queryParams.get("new-sensor-name");
        String code = queryParams.get("new-sensor-code");
        int type = Integer.parseInt(queryParams.get("new-sensor-type"));
        String city = queryParams.get("new-sensor-city");
        double longitude = Double.parseDouble(queryParams.get("new-sensor-longitude"));
        double latitude = Double.parseDouble(queryParams.get("new-sensor-latitude"));
        String description = queryParams.get("new-sensor-description");

        return sensorInfoRepository.updateSensorInfo(sensorId, name, code, type, description);
    }

    /**
     * delete a sensor
     * @param id
     * @return
     */
    public int deleteSensor(long id){
        return sensorInfoRepository.deleteById(id);
    }
}
