package com.soursoft.budgetit.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_accounts")
@Data
@NoArgsConstructor @AllArgsConstructor
public class UserAccount {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acc_id")
    @Getter
    private long accountId;

    @Column(name = "acc_name")
    private String name;

    @Column(name = "acc_current_balance")
    private BigDecimal currentBalance;

    @Column(name = "acc_creation_date")
    private LocalDateTime creationDate;
    @Column(name = "acc_modification_date")
    private LocalDateTime modificationDate;
    @Column(name = "acc_removal_date")
    private LocalDateTime removalDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acc_owner_id", nullable = false)
    private UserEntity owner;

    @OneToMany(mappedBy = "sourceAccount")
    private List<AccountTransaction> sendTransactions;

    @OneToMany(mappedBy = "destinationAccount")
    private List<AccountTransaction> receiveTransactions;

    @Data
    public static class UserAccountBuilder {

        private String name;

        private BigDecimal currentBalance;

        private LocalDateTime creationDate;
        private LocalDateTime modificationDate;
        private LocalDateTime removalDate;

        private UserEntity owner;

        private List<AccountTransaction> sendTransactions;

        private List<AccountTransaction> receivedTransactions;

        public static UserAccountBuilder getInstance() {
            return new UserAccountBuilder();
        }

        public UserAccount build() {
            UserAccount result = new UserAccount();

            result.setName(this.getName());
            result.setCurrentBalance(this.getCurrentBalance());
            result.setCreationDate(this.getCreationDate());
            result.setModificationDate(this.getModificationDate());
            result.setRemovalDate(this.getRemovalDate());
            result.setOwner(this.getOwner());
            result.setSendTransactions(this.getSendTransactions());
            result.setReceiveTransactions(this.getReceivedTransactions());

            return result;
        }

        public UserAccountBuilder withName(String name) {
            this.setName(name);

            return this;
        }

        public UserAccountBuilder withCurrentBalance(BigDecimal currentBalance) {
            this.setCurrentBalance(currentBalance);

            return this;
        }

        public UserAccountBuilder withCreationDate(LocalDateTime creationDate) {
            this.setCreationDate(creationDate);

            return this;
        }

        public UserAccountBuilder withModificationDate(LocalDateTime modificationDate) {
            this.setModificationDate(modificationDate);

            return this;
        }
        public UserAccountBuilder withRemovalDate(LocalDateTime removalDate) {
            this.setRemovalDate(removalDate);

            return this;
        }

        public UserAccountBuilder withOwner(UserEntity owner) {
            this.setOwner(owner);

            return this;
        }

        public UserAccountBuilder withSendTransactions(List<AccountTransaction> sendTransactions) {
            this.setSendTransactions(sendTransactions);

            return this;
        }

        public UserAccountBuilder withReceivedTransactions(List<AccountTransaction> receivedTransactions) {
            this.setReceivedTransactions(receivedTransactions);

            return this;
        }

    }


}
