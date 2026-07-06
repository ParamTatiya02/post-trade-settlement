package com.settlement.shared;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeDTO {
    @NotBlank(message = "Instrument is required")
    private String instrument;
    @NotBlank
    private String isin;
    @NotNull
    private TradeSide side;
    @Positive(message = "Quantity must be positive")
    private long quantity;
    @DecimalMin(value = "0.01", message = "Price must be > 0")
    private BigDecimal price;
    @NotBlank
    private String counterpartyId;
    private String clientTradeId;

}
