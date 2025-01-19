package gym.service;

import gym.blockchain.Block;
import gym.blockchain.Blockchain;
import gym.entity.EquipmentTransaction;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import gym.repository.BlockRepository;
import gym.repository.TransactionRepository;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final Blockchain blockchain;

    private final BlockRepository blockRepository;

    private final TransactionRepository transactionRepository;

    public void minePendingTransactions() {
        List<EquipmentTransaction> pendingTransactions = blockchain.getPendingTransactions();

        if (!pendingTransactions.isEmpty() && isBlockchainValid()) {
            Block newBlock = new Block(blockchain.getChain().size(), System.currentTimeMillis(), new ArrayList<>(pendingTransactions), blockchain.getLastBlock().getHash());
            blockchain.addBlock(newBlock);
            blockRepository.save(newBlock);
            pendingTransactions.clear();
        } else {
            throw new RuntimeException("Invalid blockchain. Mining new block aborted.");
        }
    }

    public boolean isBlockchainValid() {
        return blockchain.isChainValid();
    }

    public int getBlockchainSize() {
        return blockchain.getChain().size();
    }

    public void addTransaction(EquipmentTransaction transaction) {
        blockchain.getPendingTransactions().add(transaction);
    }
}
