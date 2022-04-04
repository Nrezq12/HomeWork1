/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication4;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 *
 * @author pc
 */
public class JavaApplication4 {
    private static SimpleDateFormat format = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
    private static String FilePath = "C://Users//pc//Documents//NetBeansProjects//JavaApplication4//src//javaapplication4//block.json";
    public static void main(String[] args) throws NoSuchAlgorithmException,
            NoSuchAlgorithmException, JSONException, java.text.ParseException,
            IOException {
        
        
        Blockchain blockchain = new Blockchain();
        blockchain.loadingBlocks();
        blockchain.addBlock(new Block(1, "Welcome", new Date())); 
        blockchain.addBlock(new Block(2, "Noor", new Date()));
        blockchain.addBlock(new Block(3, "NOORrezq", new Date()));
        blockchain.addBlock(new Block(4, "Albetar", new Date()));
        blockchain.AllBlocks(); 
        blockchain.saveBlocksToJSON(); 
    }

    
    
    
    public static class Blockchain {

        private LinkedList<Block> Blockchain = new LinkedList();
        private int difficulty;

        public Blockchain() throws NoSuchAlgorithmException {
            Blockchain.addFirst(GenesisBlock());
            this.difficulty = 2;
        }

        public Block GenesisBlock() throws NoSuchAlgorithmException {
            return new Block(0, "genesisBlock", new Date());
        }

        public Block getBlock() {
            return Blockchain.getLast();
        }

        public void addBlock(Block newBlock) throws NoSuchAlgorithmException {
            newBlock.setPreviousHash(this.getBlock().getCurrentHash()); 
            boolean isFound = false;
            for (Block block : Blockchain) {
                if (block.getIndex() == newBlock.getIndex()) {
                    isFound = true;
                    break;
                }
            }
            if (isFound == false) {
                newBlock.mine(this.difficulty);
                Blockchain.addLast(newBlock);
            } else {
                System.err.println("it is not allowed to add block with index " + newBlock.getIndex() + "it's already exist");
            }
        }

        private void loadingBlocks() throws JSONException, java.text.ParseException, IOException {
            JSONParser jsonParser = new JSONParser();
            if (Files.lines(Paths.get(FilePath)).count() != 0) {
                try (FileReader reader = new FileReader(FilePath)) {
                    Object object = jsonParser.parse(reader);
                    JSONArray blocksList = (JSONArray) object;
                    blocksList.forEach(block -> {
                        try {
                            decode((JSONObject) block);
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        } catch (java.text.ParseException ex) {
                            ex.printStackTrace();
                        }
                    });
                } catch (FileNotFoundException excpetion) {
                    excpetion.printStackTrace();
                } catch (IOException excpetion) {
                    excpetion.printStackTrace();
                } catch (ParseException excpetion) {
                    excpetion.printStackTrace();
                }
            } else {
                System.err.println("json File is Empty");
            }
        }

        private void decode(JSONObject jsonBlock) throws JSONException, java.text.ParseException {
           
            JSONObject blockObject = (JSONObject) jsonBlock.get("block");
            Block tempBlock = new Block();
            tempBlock.setIndex((Long) blockObject.get("id"));
            tempBlock.setData((String) blockObject.get("data"));
             tempBlock.setNonce((Long) blockObject.get("nonce"));
            tempBlock.setCurrentHash((String) blockObject.get("HashCurrent"));
            tempBlock.setPreviousHash((String) blockObject.get("previousHash"));
            tempBlock.setTimestamp(format.parse((String) blockObject.get("timestamp")));
            boolean isFound = false;
            for (Block block : Blockchain) {
                if ((long) block.getIndex() == tempBlock.getIndex()) {
                    isFound = true;
                    break;
                }
            }
            if (isFound == false) {
                Blockchain.addLast(tempBlock);
            }
        }

        
         public void AllBlocks() {
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>");
            for (Block block : Blockchain) {
                System.out.println("Index : " + block.index
                        + "\nNonce : " + block.nonce
                        + "\nData : " + block.data
                        + "\nTime Stamp : " + block.timestamp
                        + "\nPrevious Hash : " + block.previousHash
                        + "\nHash : " + block.currentHash);
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>");
            }
        }

      
        private void saveBlocksToJSON() throws JSONException {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < Blockchain.size(); i++) {
                JSONObject object = new JSONObject();
                JSONObject objectItem = new JSONObject();
                objectItem.put("id", Blockchain.get(i).getIndex());
                objectItem.put("data", Blockchain.get(i).getData());
                 objectItem.put("nonce", Blockchain.get(i).getNonce());
                objectItem.put("timestamp", Blockchain.get(i).getTimestamp().toString());
                objectItem.put("previousHash", Blockchain.get(i).getPreviousHash());
                objectItem.put("HashCurrent", Blockchain.get(i).getCurrentHash());
               
                object.put("block", objectItem);
                jsonArray.add(object);
            }
            try (FileWriter file = new FileWriter(FilePath)) {
                file.write(jsonArray.toString());
                System.out.println("Succes Copied Blocks to file");
                System.out.println("\nJSON file: " + jsonArray);
            } catch (Exception exception) {
                System.out.println(exception);
            }
        }

       
    }

    public static class Block {

      
        private long index;
        private String data;
         private String currentHash;
        private long nonce;
        private Date timestamp;
        private String previousHash;
       

        public Block() {
        
        }

        public Block(int index, String data, Date timestamp) throws NoSuchAlgorithmException {
            this.index = index;
            this.data = data;
            this.timestamp = timestamp;
            this.previousHash = "0000000000000000000000000000000000000000000000000000000000";
            this.currentHash = calculateHash();
        }

        private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        }

        private static String toHex(byte[] hash) {
            BigInteger number = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        }

        private String calculateHash() throws NoSuchAlgorithmException {
            return toHex(getSHA(this.index + this.data + this.previousHash + this.timestamp + this.nonce));
        }

        public long getIndex() {
            return index;
        }

        public void setIndex(long index) {
            this.index = index;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public String getPreviousHash() {
            return previousHash;
        }

        public void setPreviousHash(String previousHash) {
            this.previousHash = previousHash;
        }

        public String getCurrentHash() {
            return currentHash;
        }

        public void setCurrentHash(String currentHash) {
            this.currentHash = currentHash;
        }

        public long getNonce() {
            return nonce;
        }

        public void setNonce(long nonce) {
            this.nonce = nonce;
        }

        private void mine(int diffculty) throws NoSuchAlgorithmException {
            String[] array = new String[diffculty + 1];
            for (int i = 0; i < array.length; i++) {
                array[i] = "";
            }
            this.currentHash = this.currentHash.replaceAll(this.currentHash.substring(0, diffculty), String.join("0", array));
            nonce = (int) Math.round(Math.random() * 10);
        }
    }
}