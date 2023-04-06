package com.medicate.medicatemanager;

public class PricesDataItems {
    Double CompAmount,
            PercentageComp, PercentageClinic, PercentageClinicBenClinic,
            CompAmountApproved, CompAmountReject, CompAmountRecipient, CompAmountPaid,
            CompAmountInReview, ClaimAmountOutstanding, PercentageClinicBenComp;
    int ContractID;

    public PricesDataItems() {
    }

    public PricesDataItems(int contractID , Double compAmount, Double percentageComp, Double percentageClinic, Double percentageClinicBenClinic, Double compAmountApproved, Double compAmountReject, Double compAmountRecipient, Double compAmountPaid, Double compAmountInReview, Double claimAmountOutstanding, Double percentageClinicBenComp) {
        CompAmount = compAmount;
        PercentageComp = percentageComp;
        PercentageClinic = percentageClinic;
        PercentageClinicBenClinic = percentageClinicBenClinic;
        CompAmountApproved = compAmountApproved;
        CompAmountReject = compAmountReject;
        CompAmountRecipient = compAmountRecipient;
        CompAmountPaid = compAmountPaid;
        CompAmountInReview = compAmountInReview;
        ClaimAmountOutstanding = claimAmountOutstanding;
        PercentageClinicBenComp = percentageClinicBenComp;
        ContractID = contractID;
    }

    public Double getCompAmount() {
        return CompAmount;
    }

    public Double getPercentageComp() {
        return PercentageComp;
    }

    public Double getPercentageClinic() {
        return PercentageClinic;
    }

    public Double getPercentageClinicBenClinic() {
        return PercentageClinicBenClinic;
    }

    public Double getCompAmountApproved() {
        return CompAmountApproved;
    }

    public Double getCompAmountReject() {
        return CompAmountReject;
    }

    public Double getCompAmountRecipient() {
        return CompAmountRecipient;
    }

    public Double getCompAmountPaid() {
        return CompAmountPaid;
    }

    public Double getCompAmountInReview() {
        return CompAmountInReview;
    }

    public Double getClaimAmountOutstanding() {
        return ClaimAmountOutstanding;
    }

    public Double getPercentageClinicBenComp() {
        return PercentageClinicBenComp;
    }

    public int getContractID() {
        return ContractID;
    }

    public void setCompAmount(Double compAmount) {
        CompAmount = compAmount;
    }

    public void setPercentageComp(Double percentageComp) {
        PercentageComp = percentageComp;
    }

    public void setPercentageClinic(Double percentageClinic) {
        PercentageClinic = percentageClinic;
    }

    public void setPercentageClinicBenClinic(Double percentageClinicBenClinic) {
        PercentageClinicBenClinic = percentageClinicBenClinic;
    }

    public void setCompAmountApproved(Double compAmountApproved) {
        CompAmountApproved = compAmountApproved;
    }

    public void setCompAmountReject(Double compAmountReject) {
        CompAmountReject = compAmountReject;
    }

    public void setCompAmountRecipient(Double compAmountRecipient) {
        CompAmountRecipient = compAmountRecipient;
    }

    public void setCompAmountPaid(Double compAmountPaid) {
        CompAmountPaid = compAmountPaid;
    }

    public void setCompAmountInReview(Double compAmountInReview) {
        CompAmountInReview = compAmountInReview;
    }

    public void setClaimAmountOutstanding(Double claimAmountOutstanding) {
        ClaimAmountOutstanding = claimAmountOutstanding;
    }

    public void setPercentageClinicBenComp(Double percentageClinicBenComp) {
        PercentageClinicBenComp = percentageClinicBenComp;
    }

    public void setContractID(int contractID) {
        ContractID = contractID;
    }
}
