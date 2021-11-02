package com.apitest.controller;

import com.apitest.dto.LeagueEntryDTO;
import com.apitest.dto.SummonerDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/riot/")
@RequiredArgsConstructor
public class RiotController {


    SummonerDTO summonerDTO;
    LeagueEntryDTO leagueEntryDTO;

    private final String api_key = "RGAPI-fbbd0d1e-dbac-4405-976d-2da608ac552a";

    @GetMapping("/search/{summonerName}")
    public Mono<SummonerDTO> search(@PathVariable String summonerName) throws JsonProcessingException {

        WebClient webClient = WebClient.create();
        Mono<SummonerDTO> mono = webClient.get()
                .uri("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                                + summonerName + "?api_key={api_key}"
                        , api_key)
                .retrieve()
                .bodyToMono(SummonerDTO.class);

        ObjectMapper objectMapper = new ObjectMapper();



//        log.info("summonerName={}",);
//        log.info("Summoner Information={}",mono.block());

        return mono;
    }

    @GetMapping("/entry/{encryptedSummonerId}")
    public String entry(@PathVariable String encryptedSummonerId) throws JsonProcessingException {

        WebClient webClient = WebClient.create();
        Mono<String> mono = webClient.get()
                .uri("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/"
                                + encryptedSummonerId  + "?api_key={api_key}"
                        , api_key)
                .retrieve()
                .bodyToMono(String.class);


//        List<LeagueEntryDTO> list = Arrays.asList(objectMapper.readValue(mono.block(), LeagueEntryDTO.class));

//        log.info("LeagueEntry Information={}", list);

        return mono.block();
    }

}
