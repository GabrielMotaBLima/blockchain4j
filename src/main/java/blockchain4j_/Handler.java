package blockchain4j_;

public class Handler {
    static Block mine (Chain chain, String[] data) {
        System.out.println("\nWait a second while the block is being mined...");
        String previousHash = chain.getPreviousHash();
        
        Block newBlock = chain.createBlock(0, previousHash, data);
        int proof = chain.proofOfWork(newBlock);
        
        newBlock.setNonce(proof);
        return newBlock;
    }
}
