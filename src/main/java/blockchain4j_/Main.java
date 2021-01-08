package blockchain4j_;

public class Main {

    public static void main(String[] args) {
        Chain blockchain = new Chain();
        Block newBlock;
        
        newBlock = Handler.mine(blockchain,
                                new String[]{"Walk around with dog",
                                                "Feed the cats"});
        
        blockchain.addBlock(newBlock);
        
        newBlock = Handler.mine(blockchain,
                                new String[]{"Test some new frameworks",
                                                "Learn ReactJS",
                                                "Create a reminder for soma meetings"});
        blockchain.addBlock(newBlock);
        
        System.out.println("\nBlockchain in JSON format: " + blockchain.toJSONString());
    }
}
