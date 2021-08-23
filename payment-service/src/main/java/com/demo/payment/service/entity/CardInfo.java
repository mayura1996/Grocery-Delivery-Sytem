package com.demo.payment.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "card_infos")
@Entity
@Data
@NoArgsConstructor
public class CardInfo implements Serializable {

	private static final long serialVersionUID = -5431694926851254143L;

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "card_info")
	private String cardInfo;

	// Credit Card Information

	@Column(name = " credit_card_number")
	private String creditCardNumber;

	@Column(name = "expiration_date")
	private String expirationDate;

	@Column(name = "security_code")
	private String securityCode;

	@JsonCreator
	public CardInfo(@JsonProperty("cardNumber") String number, @JsonProperty("exp") String expirationDate,
			@JsonProperty("cvc") String cvc) {
		this.creditCardNumber = number;
		this.expirationDate = expirationDate;
		this.securityCode = cvc;
	}

	public boolean isValid() {
		if (creditCardNumber == null || expirationDate == null || securityCode == null) {
			return false;
		}
		return true;
	}

}
