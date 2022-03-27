/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication18;

/**
 *
 * @author pc
 */
public class JavaApplication17 {
    
    public static Blockchain blockchain;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            blockchain = new Blockchain();
        mine();
        System.out.println(blockchain.getChain());
        System.out.println(blockchain.validChain());
    }

    public static void mine() {
        Block laskBlock = blockchain.getLastBlock();
        int lastProof = laskBlock.getProof();

        blockchain.newTransaction("0", "1", (long)1.0);
        int proof = blockchain.calculatePoW(lastProof);
        blockchain.newBlock(proof, laskBlock.hash());
    }
    }
    

