package gym.blockchain;

import gym.entity.EquipmentTransaction;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor

public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int index;
    private long timestamp;
    @OneToMany(cascade = CascadeType.ALL)
    private List<EquipmentTransaction> transactions;
    private String previousHash;
    private String hash;

    public Block(int index, long timestamp, List<EquipmentTransaction> transactions, String previousHash) {
        this.index = index;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.previousHash = previousHash;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return HashUtil.applySHA256(
            previousHash +
                timestamp +
                index +
                transactions.toString()
        );
    }
}
