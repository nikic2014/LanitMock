package com.example.stub.controllers;

import com.example.stub.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;


import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {
    private final ModelMapper modelMapper;

    public MainController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private Object add(RequestAddDTO requestAddDTO){
        Add add = (Add) requestAddDTO.getMethod();
        List<Ticker> tickerList = requestAddDTO.getInfo().getTickers();
        String tickerName = add.getName();
        int timeFrame = add.getTimeFrame();
        int percent = add.getPercent();

        boolean f = true;
        for(int i=0;i<tickerList.size();i++) {
            Ticker ticker = tickerList.get(i);
            if (ticker.getTicker().equals(tickerName)) {
                ticker.getAlerts().add(new Alert(timeFrame, percent));
                f=false;
            }
        }
        if (f){
            tickerList.add(new Ticker(tickerName, List.of(new Alert(timeFrame, percent))));
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDate = dateFormat.format(new Date());
        return new ResponseDTO(requestAddDTO.getInfo(),requestAddDTO.getUuid(), formattedDate);
    }
    private Object delete(RequestDeleteDTO requestDeleteDTO){
        Delete delete = (Delete) requestDeleteDTO.getMethod();
        String tickerName = delete.getTickerName();
        int alertIndex = delete.getAlertIndex();
        List<Ticker> tickerList = requestDeleteDTO.getInfo().getTickers();

        boolean f=true;
        for(int i=0;i<tickerList.size();i++) {
            Ticker ticker = tickerList.get(i);
            if (ticker.getTicker().equals(tickerName)){
                f=false;
                if (0<= alertIndex && alertIndex < ticker.getAlerts().size()) {
                    ticker.getAlerts().remove(alertIndex);
                    break;
                }
                else
                    return Map.of("status", "error", "message","Передан некорректный индекс");
            }
        }
        if (f)
            return Map.of("status", "error", "message","Передан некорректный тикер");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDate = dateFormat.format(new Date());
        return new ResponseDTO(requestDeleteDTO.getInfo(),requestDeleteDTO.getUuid(), formattedDate);
    }

    @PostMapping("/json")
    public Object stud(@RequestParam("action") String action, @RequestBody RequestDTO requestDTO) {
        if (action.equals("add"))
            return add(convertToAdd(requestDTO));
        if (action.equals("delete"))
            return delete(convertToDelete(requestDTO));

        return Map.of("status", "error", "message", "Передан некорректный action " + action);
    }

    private RequestAddDTO convertToAdd(RequestDTO requestDTO) {
        return this.modelMapper.map(requestDTO, RequestAddDTO.class);
    }
    private RequestDeleteDTO convertToDelete(RequestDTO requestDTO) {
        return this.modelMapper.map(requestDTO, RequestDeleteDTO.class);
    }

}
