package blockchain4j_;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import com.google.gson.Gson;

public final class Chain {

    private ArrayList<Block> chain;

    public Chain () {
        chain = new ArrayList<>();
        addGenesisBlock();
    }

    ArrayList<Block> getChain () {
        return chain;
    }

    void setChain (ArrayList<Block> chain) {
        this.chain = chain;
    }
        
    Block createBlock (int nonce, String previousHash, String[] data) {
        Block block = new Block ( chain.size() + 1,
                                    new Date().toString(),
                                    nonce,
                                    data,
                                    previousHash );
        return block;
    }

    void addBlock (Block newBlock) {
        if (isChainValid(newBlock))
            chain.add(newBlock);
    }

    void addBlock (Gson jsonBlock) {
        if (isChainValid(JSONToBlock(jsonBlock)))
            chain.add(JSONToBlock(jsonBlock));
    }
    
    private void addGenesisBlock () {
        chain.add(createBlock(1, "0", new String[]{"0"}));
    }

    Block getBlockByIndex (int index) {
        try {
            return chain.get(index - 1);
        } catch (Exception exception) {
            System.err.println("Error : " + exception);
            return null;
        }
    }

    Block getPreviousBlock () {
        return chain.get(chain.size() - 1);
    }
    
    String getPreviousHash () {
        return hash(toJSONString(getPreviousBlock()));
    }
    
    private String hash (String value) {
        return SHA256.sha256(value);
    }
    
    private String puzzleQuestion (Block newBlock) {
        return hash(toJSONString(newBlock));
    }
    
    private boolean puzzleAnswer (String question) {
        return question.substring(63, 64).equals("0") && !Objects.isNull(question);
    }

    int proofOfWork (Block newBlock) {
        int testingProof = 0;
        boolean validity = false;

        while (!validity) {
            String question = puzzleQuestion(newBlock);

            System.out.println("sha256: " + question);
            System.out.println("Testing Proof: " + testingProof);
            
            if (puzzleAnswer(question)) validity = true;
            else testingProof += 1;
            
            newBlock.setNonce(testingProof);
        }

        return testingProof;
    }
    
    boolean isChainValid (Block newBlock) {
        Block previousBlock = getPreviousBlock();
        int blockIndex = 1;
        while (blockIndex < chain.size()) {
            Block block = chain.get(blockIndex - 1);
            
            if (block.getPreviousHash()
                    .equals(hash(toJSONString(previousBlock))))
                return false;   
            
            String question = puzzleQuestion(newBlock);
            
            if (!puzzleAnswer(question)) return false;
            
            previousBlock = block;
            blockIndex++;
        }
        
        return true;
    }
    
    private Block JSONToBlock (Gson jsonBlock){
        return new Gson().fromJson(jsonBlock.toString(), Block.class);
    }
    
    Gson toGson () {
        return new Gson().fromJson(toJSONString(), Gson.class);
    }
    
    String toJSONString () {
        return new Gson().toJson(chain);
    }
    
    String toJSONString (Block block) {
        return new Gson().toJson(block);
    }
    
    String toJSONString (Gson jsonBlock) {
        return new Gson().toString();
    }
}