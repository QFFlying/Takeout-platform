package com.sky.service;

import com.sky.dto.SetmealDTO;
import org.springframework.stereotype.Service;

@Service
public interface SetmealService {

    /**
     * 新增套餐及对应菜品
     * @param setmealDTO
     */
    public void saveWithDish(SetmealDTO setmealDTO);
}
