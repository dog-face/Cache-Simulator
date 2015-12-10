package com.vito;

import java.util.List;

public class CacheSimulator {
    List<Integer> addresses;
    int[][] cache;
    int[] lru;
    int lruTracker = 1;

    int blockSize = 4;//block size in words
    int numBlocks = 4;//number of blocks
    //Total words = blockSize * numBlocks
    int setSize = 2;

    public CacheSimulator(List<Integer> addrs){
        addresses = addrs;
        cache = initializeCache();//new int[numBlocks][blockSize];

    }

    public int[][] initializeCache(){
        lru = new int[numBlocks];
        for(int i = 0; i < numBlocks; i++){
                lru[i] = 0;
        }

        int[][] cache = new int[numBlocks][blockSize];
        for(int i = 0; i < numBlocks; i ++){
            for(int j = 0; j < blockSize; j++) {
                cache[i][j] = -1;//j + i * blockSize;//
            }
        }
        return cache;
    }

    public void simulate(){
        for(int i = 0; i < addresses.size(); i++){
            int addr = addresses.get(i);
            System.out.print("Checking for " + addr + " ... ");
            if(!load(addr)){
                System.out.println("\t\tHit!");
            }
            else{
                System.out.println("\tMiss");
            }
            printCache();
        }
    }

    public boolean load(int addr){
        int block = (addr/blockSize)%(numBlocks/setSize);
        for(int i = 0; i < setSize; i++){//for each set
            for(int j = 0; j < blockSize; j++){//for each word in this block
                //System.out.println("Block: " + block + " j: " + j);
                if(cache[block][j] == addr) {//if this word contains the desired data
                    lru[block] = lruTracker; //make this the most recently used.
                    lruTracker++;//increment tracker
                    return false;//we're done. False => did not need to load.
                }
            }
            block = block + (numBlocks/setSize);//get the block in this set //addr/(blockSize * (i+1))%(numBlocks/setSize);
        }
        block = getLRU(block);//find the best block
        for(int i = 0; i < blockSize; i++){//for each word in this block
            cache[block][i] = (addr - addr%blockSize) + i;//load the proper data
            lru[block] = lruTracker;//track LRU blocks etc.
            lruTracker++;
        }
        return true;//we did need to load.
    }

    public int getLRU(int block){
        int currentBlock = block%(numBlocks/setSize);

        int min = lru[currentBlock];
        int bestBlock = block%(numBlocks/setSize);
        for(int i = 0; i < setSize; i++){
            if(lru[currentBlock] < min){
                min = lru[bestBlock];
                bestBlock = currentBlock;
            }
            currentBlock += numBlocks/setSize;
        }
        return bestBlock;
    }

    public void printCache(){
        System.out.print("set | Block: ");
        for(int i = 0; i < (numBlocks*blockSize)/setSize; i++){
            System.out.print(i + "  ");
            if(i == blockSize-1)
                System.out.print("   ");
        }
        System.out.println("");
        System.out.print("----+--------");
        for(int i = 0; i < (numBlocks*blockSize)/setSize; i++){
            System.out.print("---");
            if(i == blockSize-1)
                System.out.print("---");
        }
        System.out.println();
        for(int i = 0; i < setSize; i++){
            System.out.print(i + "   |        ");
            for(int j = 0; j < numBlocks/setSize; j++){
                for(int k = 0; k < blockSize; k++){
                    System.out.print(cache[j+i*numBlocks/setSize][k] + " ");
                    if(cache[j+i*numBlocks/setSize][k] < 10 && cache[j+i*numBlocks/setSize][k] > -1)
                        System.out.print(" ");
                }
                System.out.print("\t");
            }
            //if(i%setSize )
            System.out.println();
        }
        System.out.println();
    }


}
