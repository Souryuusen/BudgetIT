package com.soursoft.budgetit.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_transactions")
@Data
@NoArgsConstructor @AllArgsConstructor
public class AccountTransaction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trn_id")
    @Getter
    private long transactionId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trn_source_acc_id")
    private UserAccount sourceAccount;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trn_destination_acc_id")
    private UserAccount destinationAccount;

    @Column(name = "trn_value")
    private BigDecimal transactionValue;

    @Enumerated(EnumType.STRING)
    @Column(name = " trn_status")
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "trn_type")
    private TransactionType type;

    @Column(name = "trn_creation_date")
    private LocalDateTime creationDate;
    @Column(name = "trn_modification_date")
    private LocalDateTime modificationDate;
    @Column(name = "trn_removal_date")
    private LocalDateTime removalDate;

    @Data
    public static class AccountTransactionBuilder {

        private UserAccount sourceAccount;
        private UserAccount destinationAccount;

        private BigDecimal transactionValue;

        private TransactionStatus status;

        private TransactionType type;

        private LocalDateTime creationDate;
        private LocalDateTime modificationDate;
        private LocalDateTime removalDate;

        public static AccountTransactionBuilder getInstance() {
            return new AccountTransactionBuilder();
        }

        public AccountTransaction build() {
            AccountTransaction accountTransaction = new AccountTransaction();

            accountTransaction.setSourceAccount(this.getSourceAccount());
            accountTransaction.setDestinationAccount(this.getDestinationAccount());
            accountTransaction.setTransactionValue(this.getTransactionValue());
            accountTransaction.setStatus(this.getStatus());
            accountTransaction.setType(this.getType());
            accountTransaction.setCreationDate(this.getCreationDate());
            accountTransaction.setModificationDate(this.getModificationDate());
            accountTransaction.setRemovalDate(this.getRemovalDate());

            return accountTransaction;
        }

        public AccountTransactionBuilder withSourceAccount(UserAccount sourceAccount) {
            this.setSourceAccount(sourceAccount);

            return this;
        }

        public AccountTransactionBuilder withDestinationAccount(UserAccount destinationAccount) {
            this.setDestinationAccount(destinationAccount);

            return this;
        }

        public AccountTransactionBuilder withTransactionValue(BigDecimal transactionValue) {
            this.setTransactionValue(transactionValue);

            return this;
        }

        public AccountTransactionBuilder withStatus(TransactionStatus status) {
            this.setStatus(status);

            return this;
        }

        public AccountTransactionBuilder withType(TransactionType type) {
            this.setType(type);

            return this;
        }

        public AccountTransactionBuilder withCreationDate(LocalDateTime creationDate) {
            this.setCreationDate(creationDate);

            return this;
        }

        public AccountTransactionBuilder withModificationDate(LocalDateTime modificationDate) {
            this.setModificationDate(modificationDate);

            return this;
        }

        public AccountTransactionBuilder withRemovalDate(LocalDateTime removalDate) {
            this.setRemovalDate(removalDate);

            return this;
        }

    }

}

