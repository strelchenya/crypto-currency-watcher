package com.idfinance.cryptocurrencywatcher.to;

import com.idfinance.cryptocurrencywatcher.HasIdAndEmail;
import com.idfinance.cryptocurrencywatcher.util.NoHtml;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserTo extends NamedTo implements HasIdAndEmail {
    @Email
    @NotBlank
    @Size(max = 100)
    @NoHtml
    String email;

    @NotBlank
    @Size(min = 5, max = 32)
    String password;

    @NotBlank
    @Size(min = 2, max = 5)
    @NoHtml
    String symbol;

    public UserTo(Integer id, String name, String email, String password, String symbol) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.symbol = symbol;
    }
}
