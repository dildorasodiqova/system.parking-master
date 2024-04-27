package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 'Mukhtarov Sarvarbek' on 22.04.2024
 * @project system.parking
 * @contact @sarvargo
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardAddDTO {
    private Long cardId;
    private Long placeId;
}
