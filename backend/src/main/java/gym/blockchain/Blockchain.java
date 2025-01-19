package gym.blockchain;

import gym.entity.EquipmentTransaction;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class Blockchain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<Block> chain;

    @Transient
    private List<EquipmentTransaction> pendingTransactions = new ArrayList<>();

    public Blockchain() {
        chain = new ArrayList<>();
        chain.add(createFirstBlock());
    }

    private Block createFirstBlock() {
        List<EquipmentTransaction> transactions = new ArrayList<>();
        return new Block(0, System.currentTimeMillis(), transactions, "0");
    }

    public void addBlock(Block newBlock) {
        if (isValidNewBlock(newBlock, getLastBlock())) {
            chain.add(newBlock);
        } else {
            throw new RuntimeException("New block invalid. Adding new block aborted");
        }
    }

    public Block getLastBlock() {
        return chain.get(chain.size() - 1);
    }

    public boolean isValidNewBlock(Block newBlock, Block previousBlock) {
        if (newBlock.getIndex() != previousBlock.getIndex() + 1) {
            return false;
        }

        if (!newBlock.getHash().equals(newBlock.calculateHash())) {
            return false;
        }

        if (!newBlock.getPreviousHash().equals(previousBlock.getHash())) {
            return false;
        }

        return true;
    }


    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }

            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }

            if (!isValidNewBlock(currentBlock, previousBlock)) {
                return false;
            }
        }
        return true;
    }
}
