package com.stemcloud.smart.web.dao;

import com.stemcloud.smart.web.domain.SensorCameraPhotos;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/17
 * Description:
 */
public interface SensorCameraPhotosRepository extends CrudRepository<SensorCameraPhotos, Integer> {
    List<SensorCameraPhotos> findByVideoIdOrderByTimeInVideo(long videoId);
}