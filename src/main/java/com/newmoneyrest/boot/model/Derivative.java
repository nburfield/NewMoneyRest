package com.newmoneyrest.boot.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@IdClass(DerivativeId.class)
@Table(name = "derivative")
public class Derivative implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long derivativeId;
	
	@Id
    @Column(length=255, name = "header_form_4_id")
	private String headerForm4Id;
	
	@Column(length = 255)
	private String securityTitle;
	
	private Double conversionOrExercisePrice;

	@Column(length = 30)
	private String transactionDate;

	@Column(length = 30)
	private String deemedExecutionDate;
	
	private char transactionFormType;
	private char transactionCode;
	private Boolean equitySwapInvolved;
	private char transcationTimeliness;
	private Double transactionShares;
	private Double transactionTotalValue;
	private Double transactionPricePerShare;
	private char transactionAcquiredDisposedCode;

	@Column(length = 30)
	private String exerciseDate;

	@Column(length = 30)
	private String expirationDate;

	@Column(length = 255)
	private String underlyingSecurityTitle;
	private Double underlyingSecurityShares;
	private Double underlyingSecurityValue;
	private Double sharesOwnedFollowingTransaction;
	private Double valueOwnedFollowingTransaction;
	private char directOrIndirectOwnership;

	@Column(length = 255)
	private String natureOfOwnership;

	@Lob
	private byte[] securityTitleFootnote;

	@Lob
	private byte[] conversionOrExercisePriceFootnote;

	@Lob
	private byte[] transactionDateFootnote;

	@Lob
	private byte[] deemedExecutionDateFootnote;

	@Lob
	private byte[] transactionCodingFootnote;

	@Lob
	private byte[] transcationTimelinessFootnote;

	@Lob
	private byte[] transactionSharesFootnote;

	@Lob
	private byte[] transactionTotalValueFootnote;

	@Lob
	private byte[] transactionPricePerShareFootnote;

	@Lob
	private byte[] transactionAcquiredDisposedCodeFootnote;

	@Lob
	private byte[] exerciseDateFootnote;

	@Lob
	private byte[] expirationDateFootnote;

	@Lob
	private byte[] underlyingSecurityTitleFootnote;

	@Lob
	private byte[] underlyingSecuritySharesFootnote;

	@Lob
	private byte[] underlyingSecurityValueFootnote;

	@Lob
	private byte[] sharesOwnedFollowingTransactionFootnote;

	@Lob
	private byte[] valueOwnedFollowingTransactionFootnote;

	@Lob
	private byte[] directOrIndirectOwnershipFootnote;

	@Lob
	private byte[] natureOfOwnershipFootnote;

	public long getDerivativeId() {
		return derivativeId;
	}

	public void setDerivativeId(long derivativeId) {
		this.derivativeId = derivativeId;
	}

	public String getForm4() {
		return headerForm4Id;
	}

	public void setForm4(String headerForm4Id) {
		this.headerForm4Id = headerForm4Id;
	}

	public String getSecurityTitle() {
		return securityTitle;
	}

	public void setSecurityTitle(String securityTitle) {
		this.securityTitle = securityTitle;
	}

	public Double getConversionOrExercisePrice() {
		return conversionOrExercisePrice;
	}

	public void setConversionOrExercisePrice(Double conversionOrExercisePrice) {
		this.conversionOrExercisePrice = conversionOrExercisePrice;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getDeemedExecutionDate() {
		return deemedExecutionDate;
	}

	public void setDeemedExecutionDate(String deemedExecutionDate) {
		this.deemedExecutionDate = deemedExecutionDate;
	}

	public char getTransactionFormType() {
		return transactionFormType;
	}

	public void setTransactionFormType(char transactionFormType) {
		this.transactionFormType = transactionFormType;
	}

	public char getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(char transactionCode) {
		this.transactionCode = transactionCode;
	}

	public Boolean getEquitySwapInvolved() {
		return equitySwapInvolved;
	}

	public void setEquitySwapInvolved(Boolean equitySwapInvolved) {
		this.equitySwapInvolved = equitySwapInvolved;
	}

	public char getTranscationTimeliness() {
		return transcationTimeliness;
	}

	public void setTranscationTimeliness(char transcationTimeliness) {
		this.transcationTimeliness = transcationTimeliness;
	}

	public Double getTransactionShares() {
		return transactionShares;
	}

	public void setTransactionShares(Double transactionShares) {
		this.transactionShares = transactionShares;
	}

	public Double getTransactionTotalValue() {
		return transactionTotalValue;
	}

	public void setTransactionTotalValue(Double transactionTotalValue) {
		this.transactionTotalValue = transactionTotalValue;
	}

	public Double getTransactionPricePerShare() {
		return transactionPricePerShare;
	}

	public void setTransactionPricePerShare(Double transactionPricePerShare) {
		this.transactionPricePerShare = transactionPricePerShare;
	}

	public char getTransactionAcquiredDisposedCode() {
		return transactionAcquiredDisposedCode;
	}

	public void setTransactionAcquiredDisposedCode(char transactionAcquiredDisposedCode) {
		this.transactionAcquiredDisposedCode = transactionAcquiredDisposedCode;
	}

	public String getExerciseDate() {
		return exerciseDate;
	}

	public void setExerciseDate(String exerciseDate) {
		this.exerciseDate = exerciseDate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getUnderlyingSecurityTitle() {
		return underlyingSecurityTitle;
	}

	public void setUnderlyingSecurityTitle(String underlyingSecurityTitle) {
		this.underlyingSecurityTitle = underlyingSecurityTitle;
	}

	public Double getUnderlyingSecurityShares() {
		return underlyingSecurityShares;
	}

	public void setUnderlyingSecurityShares(Double underlyingSecurityShares) {
		this.underlyingSecurityShares = underlyingSecurityShares;
	}

	public Double getUnderlyingSecurityValue() {
		return underlyingSecurityValue;
	}

	public void setUnderlyingSecurityValue(Double underlyingSecurityValue) {
		this.underlyingSecurityValue = underlyingSecurityValue;
	}

	public Double getSharesOwnedFollowingTransaction() {
		return sharesOwnedFollowingTransaction;
	}

	public void setSharesOwnedFollowingTransaction(Double sharesOwnedFollowingTransaction) {
		this.sharesOwnedFollowingTransaction = sharesOwnedFollowingTransaction;
	}

	public Double getValueOwnedFollowingTransaction() {
		return valueOwnedFollowingTransaction;
	}

	public void setValueOwnedFollowingTransaction(Double valueOwnedFollowingTransaction) {
		this.valueOwnedFollowingTransaction = valueOwnedFollowingTransaction;
	}

	public char getDirectOrIndirectOwnership() {
		return directOrIndirectOwnership;
	}

	public void setDirectOrIndirectOwnership(char directOrIndirectOwnership) {
		this.directOrIndirectOwnership = directOrIndirectOwnership;
	}

	public String getNatureOfOwnership() {
		return natureOfOwnership;
	}

	public void setNatureOfOwnership(String natureOfOwnership) {
		this.natureOfOwnership = natureOfOwnership;
	}

	public byte[] getSecurityTitleFootnote() {
		return securityTitleFootnote;
	}

	public void setSecurityTitleFootnote(byte[] securityTitleFootnote) {
		this.securityTitleFootnote = securityTitleFootnote;
	}

	public byte[] getConversionOrExercisePriceFootnote() {
		return conversionOrExercisePriceFootnote;
	}

	public void setConversionOrExercisePriceFootnote(byte[] conversionOrExercisePriceFootnote) {
		this.conversionOrExercisePriceFootnote = conversionOrExercisePriceFootnote;
	}

	public byte[] getTransactionDateFootnote() {
		return transactionDateFootnote;
	}

	public void setTransactionDateFootnote(byte[] transactionDateFootnote) {
		this.transactionDateFootnote = transactionDateFootnote;
	}

	public byte[] getDeemedExecutionDateFootnote() {
		return deemedExecutionDateFootnote;
	}

	public void setDeemedExecutionDateFootnote(byte[] deemedExecutionDateFootnote) {
		this.deemedExecutionDateFootnote = deemedExecutionDateFootnote;
	}

	public byte[] getTransactionCodingFootnote() {
		return transactionCodingFootnote;
	}

	public void setTransactionCodingFootnote(byte[] transactionCodingFootnote) {
		this.transactionCodingFootnote = transactionCodingFootnote;
	}

	public byte[] getTranscationTimelinessFootnote() {
		return transcationTimelinessFootnote;
	}

	public void setTranscationTimelinessFootnote(byte[] transcationTimelinessFootnote) {
		this.transcationTimelinessFootnote = transcationTimelinessFootnote;
	}

	public byte[] getTransactionSharesFootnote() {
		return transactionSharesFootnote;
	}

	public void setTransactionSharesFootnote(byte[] transactionSharesFootnote) {
		this.transactionSharesFootnote = transactionSharesFootnote;
	}

	public byte[] getTransactionTotalValueFootnote() {
		return transactionTotalValueFootnote;
	}

	public void setTransactionTotalValueFootnote(byte[] transactionTotalValueFootnote) {
		this.transactionTotalValueFootnote = transactionTotalValueFootnote;
	}

	public byte[] getTransactionPricePerShareFootnote() {
		return transactionPricePerShareFootnote;
	}

	public void setTransactionPricePerShareFootnote(byte[] transactionPricePerShareFootnote) {
		this.transactionPricePerShareFootnote = transactionPricePerShareFootnote;
	}

	public byte[] getTransactionAcquiredDisposedCodeFootnote() {
		return transactionAcquiredDisposedCodeFootnote;
	}

	public void setTransactionAcquiredDisposedCodeFootnote(byte[] transactionAcquiredDisposedCodeFootnote) {
		this.transactionAcquiredDisposedCodeFootnote = transactionAcquiredDisposedCodeFootnote;
	}

	public byte[] getExerciseDateFootnote() {
		return exerciseDateFootnote;
	}

	public void setExerciseDateFootnote(byte[] exerciseDateFootnote) {
		this.exerciseDateFootnote = exerciseDateFootnote;
	}

	public byte[] getExpirationDateFootnote() {
		return expirationDateFootnote;
	}

	public void setExpirationDateFootnote(byte[] expirationDateFootnote) {
		this.expirationDateFootnote = expirationDateFootnote;
	}

	public byte[] getUnderlyingSecurityTitleFootnote() {
		return underlyingSecurityTitleFootnote;
	}

	public void setUnderlyingSecurityTitleFootnote(byte[] underlyingSecurityTitleFootnote) {
		this.underlyingSecurityTitleFootnote = underlyingSecurityTitleFootnote;
	}

	public byte[] getUnderlyingSecuritySharesFootnote() {
		return underlyingSecuritySharesFootnote;
	}

	public void setUnderlyingSecuritySharesFootnote(byte[] underlyingSecuritySharesFootnote) {
		this.underlyingSecuritySharesFootnote = underlyingSecuritySharesFootnote;
	}

	public byte[] getUnderlyingSecurityValueFootnote() {
		return underlyingSecurityValueFootnote;
	}

	public void setUnderlyingSecurityValueFootnote(byte[] underlyingSecurityValueFootnote) {
		this.underlyingSecurityValueFootnote = underlyingSecurityValueFootnote;
	}

	public byte[] getSharesOwnedFollowingTransactionFootnote() {
		return sharesOwnedFollowingTransactionFootnote;
	}

	public void setSharesOwnedFollowingTransactionFootnote(byte[] sharesOwnedFollowingTransactionFootnote) {
		this.sharesOwnedFollowingTransactionFootnote = sharesOwnedFollowingTransactionFootnote;
	}

	public byte[] getValueOwnedFollowingTransactionFootnote() {
		return valueOwnedFollowingTransactionFootnote;
	}

	public void setValueOwnedFollowingTransactionFootnote(byte[] valueOwnedFollowingTransactionFootnote) {
		this.valueOwnedFollowingTransactionFootnote = valueOwnedFollowingTransactionFootnote;
	}

	public byte[] getDirectOrIndirectOwnershipFootnote() {
		return directOrIndirectOwnershipFootnote;
	}

	public void setDirectOrIndirectOwnershipFootnote(byte[] directOrIndirectOwnershipFootnote) {
		this.directOrIndirectOwnershipFootnote = directOrIndirectOwnershipFootnote;
	}

	public byte[] getNatureOfOwnershipFootnote() {
		return natureOfOwnershipFootnote;
	}

	public void setNatureOfOwnershipFootnote(byte[] natureOfOwnershipFootnote) {
		this.natureOfOwnershipFootnote = natureOfOwnershipFootnote;
	}
}
