package com.idfinance.cryptocurrencywatcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.idfinance.cryptocurrencywatcher.HasIdAndEmail;
import com.idfinance.cryptocurrencywatcher.View;
import com.idfinance.cryptocurrencywatcher.util.NoHtml;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class User extends NamedEntity implements HasIdAndEmail, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 100)
    @NoHtml
    private String email;

    @ToString.Exclude
    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "symbol", nullable = false)
    @NotBlank
    @Size(min = 2, max = 5)
    @NoHtml
    private String symbol;

    @Column(name = "price_usd", nullable = false)
    @NotNull(groups = View.Persist.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal priceUsd;

    @Column(name = "price_usd_registration", nullable = false)
    @NotNull(groups = View.Persist.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal priceUsdUponRegistration;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date registered = new Date();

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_roles"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles;

    public User(Integer id, String name, String email, String password, String symbol, Role role, Role... roles) {
        this(id, name, email, password, symbol, null, null, new Date(), EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, String symbol,
                BigDecimal priceUsd, BigDecimal priceUsdUponRegistration, Date registered,
                Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.symbol = symbol;
        this.priceUsd = priceUsd;
        this.priceUsdUponRegistration = priceUsdUponRegistration;
        this.registered = registered;
        setRoles(roles);
    }


    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }
}