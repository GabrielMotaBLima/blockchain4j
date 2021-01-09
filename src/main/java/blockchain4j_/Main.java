package blockchain4j_;

public class Main {

    public static void main(String[] args) {
        Chain blockchain = new Chain();
        Block newBlock;
        String jsonResponse;

        newBlock = Handler.mine(blockchain,
                                new String[]{"Walk around with dog",
                                                "Feed the cats"});
        
        blockchain.addBlock(newBlock);
        
        newBlock = Handler.mine(blockchain,
                                new String[]{"Test some new frameworks",
                                                "Learn ReactJS",
                                                "Create a reminder for soma meetings"});
        blockchain.addBlock(newBlock);
        
        jsonResponse = blockchain.toJSONString()
                        .replace("[{", "[\n\t{\n\t\t")
                        .replace("}]", "}\n]")
                        .replace(",{", ",\n\t{\n\t\t")
                        .replace("}", "\n\t}")
                        .replace(",\"", ",\n\t\t\"");

        System.out.println("\nBlockchain in JSON format: \n" + jsonResponse);
    }
}
