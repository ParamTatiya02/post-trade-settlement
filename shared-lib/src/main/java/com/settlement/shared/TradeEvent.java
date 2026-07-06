package com.settlement.shared;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper=true)
public class TradeEvent extends TradeDTO implements Serializable {
    private String tradeId;
    private Instant receivedAt;
    private SettlementStatus status;
    private String currency;
    private String exchange;

    public static TradeEvent from(TradeDTO dto) {
        TradeEvent event = new TradeEvent();
        event.setInstrument(dto.getInstrument());
        event.setIsin(dto.getIsin());
        event.setSide(dto.getSide());
        event.setQuantity(dto.getQuantity());
        event.setPrice(dto.getPrice());
        event.setCounterpartyId(dto.getCounterpartyId());
        event.setClientTradeId(dto.getClientTradeId());
        event.setTradeId(UUID.randomUUID().toString());
        event.setReceivedAt(Instant.now());
        event.setStatus(SettlementStatus.RECEIVED);
        return event;
    }
}
